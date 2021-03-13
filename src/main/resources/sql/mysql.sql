create table orders
(
    order_id      int          null,
    order_date    timestamp    null,
    customer_name varchar(100) null,
    price         decimal      null,
    product_id    int          null,
    order_status  tinyint(1)   null
);