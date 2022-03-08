# rf
rf is a simple reflection library based on `Function<?,?>`s and
friendly-to-use enums.

if you understand the overhead of this (or the bytecode with all of these lambdas):
cry about it

if you don't know what that means, glhf


## Usage

### Maven
```xml
<dependency>
    <groupId>com.localhost22</groupId>
    <artifactId>rf</artifactId>
    <version>1.0</version>
</dependency>
```

### Java
```java
/**
 * Method `method()` from YourClass.
 * You can retreive methods by utilising their signatures.
 */
public static final Method RETREIVED_METHOD = Retreivers.METHOD.get(YourClass.class, "method()V");
```
