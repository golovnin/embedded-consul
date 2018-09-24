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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import de.flapdoodle.embed.process.builder.AbstractBuilder;
import de.flapdoodle.embed.process.builder.TypedProperty;
import de.flapdoodle.embed.process.config.IExecutableProcessConfig;
import de.flapdoodle.embed.process.config.ISupportConfig;
import de.flapdoodle.embed.process.distribution.IVersion;
import de.flapdoodle.embed.process.runtime.Network;

import static java.util.Objects.requireNonNull;

/**
 * @author Andrej Golovnin
 */
public final class ConsulAgentConfig implements IExecutableProcessConfig {

    private final IVersion version;
    private final long startupTimeout;
    private final String advertise;
    private final String bind;
    private final String client;
    private final String configDir;
    private final String datacenter;
    private final int dnsPort;
    private final int httpPort;
    private final int serfLANPort;
    private final int serfWANPort;
    private final int serverPort;
    private final ConsulLogLevel logLevel;
    private final String node;
    private final String nodeID;
    private final Consumer<String> outConsumer;
    private final Consumer<String> errConsumer;

    ConsulAgentConfig(IVersion version, long startupTimeout,
        String advertise, String bind, String client, String configDir,
        String datacenter, int dnsPort, int httpPort, int serfLANPort,
        int serfWANPort, int serverPort, ConsulLogLevel logLevel, String node,
        String nodeID, Consumer<String> outConsumer, Consumer<String> errConsumer
    ) {
        this.version = version;
        this.startupTimeout = startupTimeout;
        this.advertise = advertise;
        this.bind = bind;
        this.client = client;
        this.configDir = configDir;
        this.datacenter = datacenter;
        this.dnsPort = dnsPort;
        this.httpPort = httpPort;
        this.serfLANPort = serfLANPort;
        this.serfWANPort = serfWANPort;
        this.serverPort = serverPort;
        this.logLevel = logLevel;
        this.node = node;
        this.nodeID = nodeID;
        this.outConsumer = outConsumer;
        this.errConsumer = errConsumer;
    }

    public static final class Builder extends AbstractBuilder<ConsulAgentConfig> {

        private static final String DEFAULT_ADDRESS = "127.0.0.1";

        private static final Consumer<String> NOP_CONSUMER = s -> {};

        private static final TypedProperty<IVersion> VERSION =
            TypedProperty.with("version", IVersion.class);

        private static final TypedProperty<Long> STARTUP_TIMEOUT =
            TypedProperty.with("startup-timeout", Long.class);

        private static final TypedProperty<String> ADVERTISE =
            TypedProperty.with("advertise", String.class);

        private static final TypedProperty<String> BIND =
            TypedProperty.with("bind", String.class);

        private static final TypedProperty<String> CLIENT =
            TypedProperty.with("client", String.class);

        private static final TypedProperty<String> CONFIG_DIR =
            TypedProperty.with("config-dir", String.class);

        private static final TypedProperty<String> DATACENTER =
            TypedProperty.with("datacenter", String.class);

        private static final TypedProperty<Integer> DNS_PORT =
            TypedProperty.with("dns-port", Integer.class);

        private static final TypedProperty<Integer> HTTP_PORT =
            TypedProperty.with("http-port", Integer.class);

        private static final TypedProperty<Integer> SERF_LAN_PORT =
            TypedProperty.with("serf-lan-port", Integer.class);

        private static final TypedProperty<Integer> SERF_WAN_PORT =
            TypedProperty.with("serf-wan-port", Integer.class);

        private static final TypedProperty<Integer> SERVER_PORT =
            TypedProperty.with("server-port", Integer.class);

        private static final TypedProperty<ConsulLogLevel> LOG_LEVEL =
            TypedProperty.with("log-level", ConsulLogLevel.class);

        private static final TypedProperty<String> NODE =
            TypedProperty.with("node", String.class);

        private static final TypedProperty<String> NODE_ID =
            TypedProperty.with("node-id", String.class);

        private static final TypedProperty<Consumer> OUT_CONSUMER =
            TypedProperty.with("out-consumer", Consumer.class);

        private static final TypedProperty<Consumer> ERR_CONSUMER =
            TypedProperty.with("err-consumer", Consumer.class);

        public Builder() {
            property(VERSION).setDefault(ConsulVersion.V1_2_3);
            property(STARTUP_TIMEOUT).setDefault(60000L);
            property(ADVERTISE).setDefault(DEFAULT_ADDRESS);
            property(BIND).setDefault(DEFAULT_ADDRESS);
            property(CLIENT).setDefault(DEFAULT_ADDRESS);
            property(CONFIG_DIR).setDefault("");
            property(DATACENTER).setDefault("dc1");
            property(DNS_PORT).setDefault(8600);
            property(HTTP_PORT).setDefault(8500);
            property(SERF_LAN_PORT).setDefault(8301);
            property(SERF_WAN_PORT).setDefault(8302);
            property(SERVER_PORT).setDefault(8300);
            property(LOG_LEVEL).setDefault(ConsulLogLevel.INFO);
            String node = "localhost";
            try {
                node = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                // Ignore
            }
            property(NODE).setDefault(node);
            property(NODE_ID).setDefault(UUID.randomUUID().toString());
            property(OUT_CONSUMER).setDefault(NOP_CONSUMER);
            property(ERR_CONSUMER).setDefault(NOP_CONSUMER);
        }

        public Builder version(IVersion version) {
            property(VERSION).set(version);
            return this;
        }

        public Builder version(String version) {
            return version(() -> version);
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

        public Builder client(String address) {
            property(CLIENT).set(address);
            return this;
        }

        public Builder configDir(String configDir) {
            property(CONFIG_DIR).set(configDir);
            return this;
        }

        public Builder datacenter(String datacenter) {
            property(DATACENTER).set(datacenter);
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

        public Builder serfLANPort(int port) {
            property(SERF_LAN_PORT).set(port);
            return this;
        }

        public Builder serfWANPort(int port) {
            property(SERF_WAN_PORT).set(port);
            return this;
        }

        public Builder serverPort(int port) {
            property(SERVER_PORT).set(port);
            return this;
        }

        public Builder randomPorts() {
            return randomPorts(DEFAULT_ADDRESS);
        }

        public Builder randomPorts(String address) {
            try {
                int[] ports = Network.getFreeServerPorts(
                    InetAddress.getByName(address), 5);
                advertise(address);
                bind(address);
                httpPort(ports[0]);
                dnsPort(ports[1]);
                serfLANPort(ports[2]);
                serfWANPort(ports[3]);
                serverPort(ports[4]);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            return this;
        }

        public Builder logLevel(ConsulLogLevel level) {
            property(LOG_LEVEL).set(level);
            return this;
        }

        public Builder node(String node) {
            property(NODE).set(node);
            return this;
        }

        public Builder nodeID(String nodeID) {
            property(NODE_ID).set(nodeID);
            return this;
        }

        public Builder outConsumer(Consumer<String> consumer) {
            property(OUT_CONSUMER).set(requireNonNull(consumer));
            return this;
        }

        public Builder errConsumer(Consumer<String> consumer) {
            property(ERR_CONSUMER).set(requireNonNull(consumer));
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public ConsulAgentConfig build() {
            return new ConsulAgentConfig(
                property(VERSION).get(),
                property(STARTUP_TIMEOUT).get(),
                property(ADVERTISE).get(),
                property(BIND).get(),
                property(CLIENT).get(),
                property(CONFIG_DIR).get(),
                property(DATACENTER).get(),
                property(DNS_PORT).get(),
                property(HTTP_PORT).get(),
                property(SERF_LAN_PORT).get(),
                property(SERF_WAN_PORT).get(),
                property(SERVER_PORT).get(),
                property(LOG_LEVEL).get(),
                property(NODE).get(),
                property(NODE_ID).get(),
                (Consumer<String>) property(OUT_CONSUMER).get(),
                (Consumer<String>) property(ERR_CONSUMER).get());
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

    public String getClient() {
        return client;
    }

    public String getConfigDir() {
        return configDir;
    }

    public String getDatacenter() {
        return datacenter;
    }

    public int getDnsPort() {
        return dnsPort;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public int getSerfLANPort() {
        return serfLANPort;
    }

    public int getSerfWANPort() {
        return serfWANPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public ConsulLogLevel getLogLevel() {
        return logLevel;
    }

    public String getNode() {
        return node;
    }

    public String getNodeID() {
        return nodeID;
    }

    public Consumer<String> getOutConsumer() {
        return outConsumer;
    }

    public Consumer<String> getErrConsumer() {
        return errConsumer;
    }

    @Override
    public IVersion version() {
        return version;
    }

    @Override
    public ISupportConfig supportConfig() {
        return ConsulSupportConfig.INSTANCE;
    }

    String toJson() {
        return "{\n" +
            "\t\"ports\": {\n" +
            "\t\t\"serf_lan\": " + getSerfLANPort() + ",\n" +
            "\t\t\"serf_wan\": " + getSerfWANPort() + ",\n" +
            "\t\t\"server\": " + getServerPort() + "\n" +
            "\t},\n" +
            "\t\"disable_update_check\": true\n" +
            "}\n";
    }

}
