package com.liorregev.sparkpitfalls

import org.apache.spark.sql.SparkSession

trait Pitfall extends App {
  val spark = SparkSession
    .builder()
    .master("local")
    .appName(this.getClass.getSimpleName)
    .getOrCreate()
}
