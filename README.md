###创建用户和授权
```
CREATE USER 'flink'@'%' IDENTIFIED BY 'flink';
GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'flink' IDENTIFIED BY 'flink';
FLUSH PRIVILEGES;
```
###查询binlog是否开启
```
set global show_compatibility_56=on;
SELECT variable_value as "BINARY LOGGING STATUS (log-bin) ::"
FROM information_schema.global_variables WHERE variable_name='log_bin';
```
###查看mysql客户端超时参数
通过mysql客户端连接数据库是交互式连接，通过jdbc连接数据库是非交互式连接。
```
show global variables like 'wait_timeout';
show global variables like 'interactive_timeout';
```

