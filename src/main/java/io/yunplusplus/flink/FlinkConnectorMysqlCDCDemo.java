package io.yunplusplus.flink;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

public class FlinkConnectorMysqlCDCDemo {

    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1); // source only supports parallelism of 1
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        // register a table in the catalog
        tEnv.executeSql(
                "CREATE TABLE orders (\n" +
                        "  order_id INT,\n" +
                        "  order_date TIMESTAMP(0),\n" +
                        "  customer_name STRING,\n" +
                        "  price DECIMAL(10, 5),\n" +
                        "  product_id INT,\n" +
                        "  order_status BOOLEAN\n" +
                        ") WITH (\n" +
                        "  'connector' = 'mysql-cdc',\n" +
                        "  'hostname' = 'localhost',\n" +
                        "  'port' = '3306',\n" +
                        "  'username' = 'flink',\n" +
                        "  'password' = 'flink',\n" +
                        "  'database-name' = 'flink',\n" +
                        "  'table-name' = 'orders'\n" +
                        ")");

        // define a dynamic aggregating query
        final Table result = tEnv.sqlQuery("SELECT * FROM orders");

        // print the result to the console
        tEnv.toRetractStream(result, Row.class).print();

        env.execute();
    }
}
