# Spring Cloud Stream Emtpy Binder

## Limit multi in/out stream with webflux
https://github.com/spring-cloud/spring-cloud-stream/blob/main/docs/src/main/asciidoc/spring-cloud-stream.adoc#functions-with-multiple-input-and-output-arguments

It is possible to implement the pipeline of two inputs as one pipeline.
```kotlin
    @Bean
    fun converter(): Function<Tuple2<Flux<String>, Flux<String>>, Flux<String>> {
        return Function<Tuple2<Flux<String>, Flux<String>>, Flux<String>> { tuple ->
            val in_0 = tuple.t1
            val in_1 = tuple.t2
            Flux.merge(in_0, in_1).map { // do something }
        }
      }
    }
```

But when you use multi it as a consumer
```kotlin
    @Bean
    fun converter(): Consumer<Tuple2<Flux<String>, Flux<String>>> {
        return Function<Tuple2<Flux<String>, Flux<String>>, Flux<String>> { tuple ->
            val in_0 = tuple.t1
            val in_1 = tuple.t2
            Flux.merge(in_0, in_1).map { // do something }
        }
      }
    }
```

Got following error
```shell
Caused by: java.lang.IllegalArgumentException: Function 'converter' is a Consumer which is not supported for multi-in/out reactive streams.
Only Functions are supported
```



# How to use multi in/out with webflux as a consumer
https://github.com/spring-cloud/spring-cloud-stream/blob/main/docs/src/main/asciidoc/spring-cloud-stream.adoc#spring-cloud-stream-overview-custom-binder-impl

We can implement custom binder to consumer message without nothing to do.

- Multi in/out
  - Only reactive streams supported
  - function only support
  
  So to combine two pipe-lines you have to use a function.
    You must set out bounds to use a function (kafka topic)

## Create empty binder
- EmptyMessageBinderProvisioner
- EmptyMessageProducer
- EmptyMessageBinderProvisioner
- EmptyMessageBinderConfiguration

Add the following configuration information to META-INF
The binder is named emptyBinder

```shell
// ./resources/META-INF/spring.binders
emptyBinder=com.oppalove.spring.binder.EmptyMessageBinderConfiguration
```


Set emtpy binder in configuration file
```shell
spring.cloud.stream.bindings:
  converter-in-0:
    destination: topic-v1
    group: topic-v1-LOCAL
    binder: kafka
  converter-in-1:
    destination: topic-v2
    group: topic-v2-LOCAL
    binder: kafka
  converter-out-0:
    destination: empty
    binder: emptyBinder
```

emptyBinder will not do anything.

```
--> topic-v1 --> (in-0)
                        --> consumer (out-0) 
--> topic-v2 --> (in-1)
```


You can use function to create the same function as consumer.

```kotlin
    @Bean
fun converter(): Function<Tuple2<Flux<String>, Flux<String>>, Flux<String>> {
  return Function<Tuple2<Flux<String>, Flux<String>>, Flux<String>> { tuple ->
    val in_0 = tuple.t1
    val in_1 = tuple.t2
    Flux.merge(in_0, in_1).map { // do something }
    }
  }
}
```
# spring-cloud-stream-emtpybinder
