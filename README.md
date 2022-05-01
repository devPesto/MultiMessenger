# MultiCache

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
        <version>1.0.0-RELEASE</version>
    </dependency>
</dependencies>

```
### Gradle

```groovy
repositories {
  maven { url "https://jitpack.io/"}
}

dependencies {
  compile "com.github.pestonotpasta:multicache:1.0.0-RELEASE"
}

```

### How To Use

The MultiCache API is a singleton and can be accessed like so:

```java
MultiCache multiCache = MultiCache.getInstance();
HazelcastInstance hz = multiCache.getHazelcast();

IMap map = hz.getMap("my-distributed-map");
map.put("name", "Pesto");
map.get("key");
```
