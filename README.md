# Embedded Consul

Embedded Consul provides a platform neutral way for running [Consul](https://www.consul.io) in tests.
This library is based on [Flapdoodle OSS's embed process](https://github.com/flapdoodle-oss/de.flapdoodle.embed.process). 

### Maven

Add the following dependency to your pom.xml:
```xml
<dependency>
    <groupId>com.github.golovnin</groupId>
    <artifactId>embedded-consul</artifactId>
    <version>0.8.3.1</version>
    <scope>test</scope>
</dependency>
```
### Gradle

Add a line to build.gradle:
```groovy
testCompile 'com.github.golovnin:embedded-consul:0.8.3.1'
```

## Usage

Here is the example of how to launch the Consul instance:
```java
ConsulAgentStarter starter = ConsulAgentStarter.getDefaultInstance();
ConsulAgentExecutable executable = starter.prepare(
    new ConsulAgentConfig.Builder().build());
ConsulAgentProcess process = executable.start();

// Execute your tests here

process.stop();
```
Here is the example of how to launch the Consul instance using a custom version:
```java
IVersion v0_7_5 = () -> "0.7.5";
ConsulAgentStarter starter = ConsulAgentStarter.getDefaultInstance();
ConsulAgentExecutable executable = starter.prepare(
    new ConsulAgentConfig.Builder()
        .version(v0_7_5)
        .build());

// Execute your tests here

process.stop();
```

### Supported Consul versions and platforms

Versions: 0.8.3 and any custom

Platforms: Mac OS X, FreeBSD, Linux, Solaris and Windows


Copyright (c) 2017, Andrej Golovnin
