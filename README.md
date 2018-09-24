# Embedded Consul

Embedded Consul provides a platform neutral way for running [Consul](https://www.consul.io) in tests.
This library is based on [Flapdoodle OSS's embed process](https://github.com/flapdoodle-oss/de.flapdoodle.embed.process). 

### Gradle

The artifacts are available on JCenter. Therefore you must add JCenter to
your build script repositories:
```groovy
repositories {
    jcenter()
}
```
Add a Gradle compile dependency to the `build.gradle` file of your project:
```groovy
testCompile 'com.github.golovnin:embedded-consul:1.2.3.0'
```

### Usage

Here is the example of how to launch the Consul instance:
```java
ConsulAgentConfig config = new ConsulAgentConfig.Builder()
    .build();
ConsulAgentStarter starter = ConsulAgentStarter.getDefaultInstance();
ConsulAgentExecutable executable = starter.prepare(config);
ConsulAgentProcess process = executable.start();

// Execute your tests here

process.stop();
```
Here is the example of how to launch the Consul instance using a custom version:
```java
ConsulAgentConfig config = new ConsulAgentConfig.Builder()
    .version("0.7.5")
    .build();
ConsulAgentStarter starter = ConsulAgentStarter.getDefaultInstance();
ConsulAgentExecutable executable = starter.prepare(config);
ConsulAgentProcess process = executable.start();

// Execute your tests here

process.stop();
```

### Supported Consul versions and platforms

Versions: 1.2.3 and any custom

Platforms: Mac OS X, FreeBSD, Linux, Solaris and Windows


Copyright (c) 2018, Andrej Golovnin
