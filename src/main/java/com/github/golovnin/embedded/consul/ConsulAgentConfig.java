/*
 * Copyright (c) 2017, Andrej Golovnin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  Neither the name of fontviewer nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.golovnin.embedded.consul;

import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.TypedProperty;
import de.flapdoodle.embed.process.config.IExecutableProcessConfig;
import de.flapdoodle.embed.process.config.ISupportConfig;
import de.flapdoodle.embed.process.distribution.IVersion;

import java.util.concurrent.TimeUnit;

/**
 * @author Andrej Golovnin
 */
public final class ConsulAgentConfig implements IExecutableProcessConfig {

    private final IVersion version;
    private final long startupTimeout;
    private final String advertise;
    private final String bind;
    private final int dnsPort;
    private final int httpPort;
    private final ConsulLogLevel logLevel;

    ConsulAgentConfig(IVersion version, long startupTimeout,
        String advertise, String bind, int dnsPort, int httpPort,
        ConsulLogLevel logLevel)
    {
        this.version = version;
        this.startupTimeout = startupTimeout;
        this.advertise = advertise;
        this.bind = bind;
        this.dnsPort = dnsPort;
        this.httpPort = httpPort;
        this.logLevel = logLevel;
    }

    public static final class Builder extends AbstractBuilder<ConsulAgentConfig> {

        private static final TypedProperty<IVersion> VERSION =
            TypedProperty.with("version", IVersion.class);

        private static final TypedProperty<Long> STARTUP_TIMEOUT =
            TypedProperty.with("startup-timeout", Long.class);

        private static final TypedProperty<String> ADVERTISE =
            TypedProperty.with("advertise", String.class);

        private static final TypedProperty<String> BIND =
            TypedProperty.with("bind", String.class);

        private static final TypedProperty<Integer> DNS_PORT =
            TypedProperty.with("dns-port", Integer.class);

        private static final TypedProperty<Integer> HTTP_PORT =
            TypedProperty.with("http-port", Integer.class);

        private static final TypedProperty<ConsulLogLevel> LOG_LEVEL =
            TypedProperty.with("log-level", ConsulLogLevel.class);

        public Builder() {
            property(VERSION).setDefault(ConsulVersion.V0_8_3);
            property(STARTUP_TIMEOUT).setDefault(60000L);
            property(ADVERTISE).setDefault("127.0.0.1");
            property(BIND).setDefault("127.0.0.1");
            property(DNS_PORT).setDefault(8600);
            property(HTTP_PORT).setDefault(8500);
            property(LOG_LEVEL).setDefault(ConsulLogLevel.INFO);
        }

        public Builder version(IVersion version) {
            property(VERSION).set(version);
            return this;
        }

        public Builder startupTimeout(long startupTimeout, TimeUnit unit) {
            property(STARTUP_TIMEOUT).set(unit.toMillis(startupTimeout));
            return this;
        }

        public Builder advertise(String address) {
            property(ADVERTISE).set(address);
            return this;
        }

        public Builder bind(String address) {
            property(BIND).set(address);
            return this;
        }

        public Builder dnsPort(int port) {
            property(DNS_PORT).set(port);
            return this;
        }

        public Builder httpPort(int port) {
            property(HTTP_PORT).set(port);
            return this;
        }

        public Builder logLevel(ConsulLogLevel level) {
            property(LOG_LEVEL).set(level);
            return this;
        }

        @Override
        public ConsulAgentConfig build() {
            return new ConsulAgentConfig(
                property(VERSION).get(),
                property(STARTUP_TIMEOUT).get(),
                property(ADVERTISE).get(),
                property(BIND).get(),
                property(DNS_PORT).get(),
                property(HTTP_PORT).get(),
                property(LOG_LEVEL).get());
        }

    }

    public long getStartupTimeout() {
        return startupTimeout;
    }

    public String getAdvertise() {
        return advertise;
    }

    public String getBind() {
        return bind;
    }

    public int getDnsPort() {
        return dnsPort;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public ConsulLogLevel getLogLevel() {
        return logLevel;
    }

    @Override
    public IVersion version() {
        return version;
    }

    @Override
    public ISupportConfig supportConfig() {
        return new ConsulSupportConfig();
    }

}
