# condition-order-sample

DDD sample, a minimized condition order system.

## Technologies

- spring-boot
- guava(event bus)/gson
- spring-jpa(using hibernate)/spring-data-redis/spring-rabbit
- mysql/redis/rabbitmq

## TODO

- soft delete
- pause/resume
- monitor context(delay sync, trigger lock)
- trigger context
- turn up buy order
- grid trade order
- batching order (abort state)
- new shares purchase order
- mock entrust interface