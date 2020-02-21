name := "spark_hive_test"

version := "0.1"

scalaVersion := "2.12.10"
val sparkVersion = "3.0.0-preview2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided"
)
