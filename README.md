# condition-order-sample

DDD sample, a minimized condition order system.

## Technologies

- spring-boot
- guava(event bus)/gson
- spring-jpa(using hibernate)/spring-data-redis/spring-rabbitmq
- mysql/redis/rabbitmq

## TODO

- grid trade order(simple grid, with turn-point, max/min positions number/amount)
- batching order (abort state)
- new shares purchase order
- pause/resume
- consumers exception handler
- clean commons module
- using DUBBO(split api layer and biz layer)
