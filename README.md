# condition-order-sample

DDD sample, a minimized condition order system.

## Technologies

- spring-boot
- guava(event bus)/gson
- spring-jpa(using hibernate)/spring-data-redis/spring-rabbitmq
- mysql/redis/rabbitmq

## TODO

- use domain services
- order by amount
- monitor context(delay sync)
- turn up buy order(dynamic properties)
- time order
- grid trade order
- batching order (abort state)
- mock entrust interface
- new shares purchase order
- trade center concurrence control
- move dto package out
- pause/resume
- using DUBBO(split api layer and biz layer)
