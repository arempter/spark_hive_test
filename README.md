## Example Spark job with Hive and S3

Requires hive metastore and minio

### Run example:

```
git clone && sbt package 
```

Once packaged, use spark-submit to run job:

First download spark binaries. Put following in `conf/spark-defaults.conf` file:

```
spark.hadoop.mapreduce.outputcommitter.factory.scheme.s3a org.apache.hadoop.fs.s3a.commit.S3ACommitterFactory
spark.hadoop.fs.s3a.committer.name directory
spark.hadoop.fs.s3a.committer.staging.tmp.path /tmp/spark_staging
spark.hadoop.fs.s3a.buffer.dir /tmp/spark_local_buf
spark.hadoop.fs.s3a.committer.staging.conflict-mode fail
spark.hadoop.fs.s3a.impl org.apache.hadoop.fs.s3a.S3AFileSystem
spark.hadoop.fs.s3a.access.key accesskey
spark.hadoop.fs.s3a.secret.key secretkey
spark.hadoop.fs.s3a.endpoint http://localhost:9000
spark.hadoop.fs.s3a.connection.ssl.enabled false 
spark.hadoop.fs.s3a.path.style.access true
```

```
bin/spark-submit --master local --class SparkHiveTest $REPO_DIR/spark_hive_test/target/scala-2.12/spark_hive_test_2.12-0.1.jar
```



 