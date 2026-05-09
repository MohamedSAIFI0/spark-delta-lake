package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.col;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder()
                .appName("delta-lake")
                .master("local[*]")
                .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
                .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
                .getOrCreate();

        spark.sparkContext().setLogLevel("Error");

        spark.sql("CREATE TABLE IF NOT EXISTS test USING DELTA LOCATION 'file:/tmp/delta-test'");

        System.out.println("Spark + Delta fonctionne");

        //lire CSV
        Dataset<Row> df = spark.read()
                        .option("header","true")
                                .option("inferSchema","true")
                                        .csv("hdfs://localhost:8020/data-lake/raw/data.csv");

        //Nettoyage
        Dataset<Row> cleanDf = df
                .na().fill(0, new String[]{"age","salary"})
                        .withColumn("salary",col("salary").cast("double"))
                                .withColumn("age", col("age").cast("int"));
        //Sauvegaede en Delta
        String path = "hdfs://localhost:8020/data-lakehouse/";
        cleanDf.write()
                        .format("delta")
                                .mode("overwrite")
                                        .save(path);

        //Lecture Delta
        Dataset<Row> deltaDf = spark.read()
                        .format("delta")
                                .load(path);

        //SQL
        deltaDf.createOrReplaceTempView("employee");

        Dataset<Row> result = spark.sql(
                "SELECT name, salary FROM employee"
        );

        result.show();


        spark.stop();

    }
}