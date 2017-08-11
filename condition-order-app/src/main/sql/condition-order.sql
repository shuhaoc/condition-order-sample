DROP TABLE IF EXISTS `condition_order`;
CREATE TABLE `condition_order` (
  `order_id` BIGINT PRIMARY KEY,
  `order_state` INT NOT NULL,
  `security_type` INT NOT NULL,
  `security_code` CHAR(6) NOT NULL,
  `security_exchange` CHAR(2) NOT NULL,
  `security_name` VARCHAR(4) NOT NULL,
  `strategy_id` INT NOT NULL,
  `condition_properties` TEXT,
  `dynamic_properties` TEXT,
  `exchange_type` INT NOT NULL,
  `entrust_strategy` INT NOT NULL,
  `entrust_amount` DECIMAL(13, 2) NOT NULL,
  `create_time` TIMESTAMP DEFAULT current_timestamp(),
  `update_time` TIMESTAMP DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP
);