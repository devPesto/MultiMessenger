# MultiCache

[![](https://jitpack.io/v/PestoNotPasta/MultiCache.svg)](https://jitpack.io/#PestoNotPasta/MultiCache)
[![License: AGPL v3](https://img.shields.io/badge/License-AGPL_v3-blue.svg)](https://www.gnu.org/licenses/agpl-3.0)


A library that uses Hazelcast for caching on [MultiPaper](https://github.com/PureGero/MultiPaper).

## Installation 

Simply add this plugin into one MultiPaper node and add this into `multipaper.yml`:

```yaml
filesToSyncOnStartup:
- plugins/MultiCache.jar
- plugins/MultiCache/hazelcast.yml
```

Then just restart all servers.

## For Developers

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack</id>
        <url>https://jitpack.io/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.pestonotpasta</groupId>
        <artifactId>MultiCache</artifactId>
        <version>0.2</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

```
### Gradle

```groovy
repositories {
  maven { url "https://jitpack.io/"}
}

dependencies {
  compileOnly "com.github.pestonotpasta:multicache:0.3"
}

```

**Note:** Remember to include the dependency into your `plugin.yml`
```yaml
depend: [MultiCache]
```

### How To Use

The MultiCache api is a singleton and can be accessed like so:

```java
MultiCache multiCache = MultiCache.getInstance();
HazelcastInstance hz = multiCache.getHazelcast();

IMap map = hz.getMap("my-distributed-map");
map.put("name", "Pesto");
map.get("name");
```
