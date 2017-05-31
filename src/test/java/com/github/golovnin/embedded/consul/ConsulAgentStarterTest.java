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

import org.junit.Assert;
import org.junit.Test;

import de.flapdoodle.embed.process.distribution.IVersion;

/**
 * @author Andrej Golovnin
 */
public class ConsulAgentStarterTest {

    @Test
    public void testDefault() throws IOException {
        ConsulAgentStarter starter = ConsulAgentStarter.getDefaultInstance();
        ConsulAgentExecutable executable = starter.prepare(
            new ConsulAgentConfig.Builder().build());
        try {
            ConsulAgentProcess process = executable.start();
            Assert.assertTrue(process.isProcessRunning());
            process.stop();
        } finally {
            executable.stop();
        }
    }

    @Test
    public void testCustomVersion() throws IOException {
        IVersion v0_7_5 = () -> "0.7.5";
        ConsulAgentStarter starter = ConsulAgentStarter.getDefaultInstance();
        ConsulAgentExecutable executable = starter.prepare(
            new ConsulAgentConfig.Builder()
                .version(v0_7_5)
                .build());
        try {
            ConsulAgentProcess process = executable.start();
            Assert.assertTrue(process.isProcessRunning());
            process.stop();
        } finally {
            executable.stop();
        }
    }

}
