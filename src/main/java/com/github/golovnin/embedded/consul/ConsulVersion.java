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

import de.flapdoodle.embed.process.distribution.IVersion;

/**
 * @author Andrej Golovnin
 */
public enum ConsulVersion implements IVersion {

    @Deprecated
    V0_8_3("0.8.3"),

    @Deprecated
    V0_8_4("0.8.4"),

    @Deprecated
    V0_8_5("0.8.5"),

    @Deprecated
    V0_9_0("0.9.0"),

    @Deprecated
    V0_9_2("0.9.2"),

    @Deprecated
    V0_9_3("0.9.3"),

    @Deprecated
    V1_0_0("1.0.0"),

    @Deprecated
    V1_0_1("1.0.1"),

    @Deprecated
    V1_0_2("1.0.2"),

    @Deprecated
    V1_0_3("1.0.3"),

    @Deprecated
    V1_0_5("1.0.5"),

    @Deprecated
    V1_0_6("1.0.6"),

    V1_0_7("1.0.7");

    private final String version;

    ConsulVersion(String version) {
        this.version = version;
    }

    @Override
    public String asInDownloadPath() {
        return version;
    }

}
