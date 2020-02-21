package tests

import java.io.File
import org.apache.spark.sql.{SaveMode, SparkSession}

object SparkHiveTest {
  def main(args: Array[String]): Unit = {

    // this should be perhaps some more permanent location :)
    val warehouseLocation = new File("/tmp/spark-warehouse").getAbsolutePath

    val spark = SparkSession
      .builder()
      .appName("SparkHiveTest")
      .config("hive.metastore.uris", "thrift://localhost:9083")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()

    spark.sparkContext.setLogLevel("INFO")
    import spark.implicits._
    import spark.sql

    spark.sql("CREATE DATABASE IF NOT EXISTS spark_tests")
    sql("CREATE EXTERNAL TABLE IF NOT EXISTS spark_tests.s3_table_1 (key INT, value STRING) STORED AS PARQUET LOCATION 's3a://spark/example_table3'")

    val df = (1 to 30).map(e=>(e, s"elem_$e")).toDF()

    df
      .write.mode(SaveMode.Overwrite)
      .option("path","s3a://spark/s3_table_1")
      .saveAsTable("spark_tests.s3_table_1")

    val r=sql("SELECT * FROM spark_tests.s3_table_1").count()
    println("Rows in table: "+r)

    spark.stop()
  }
}
