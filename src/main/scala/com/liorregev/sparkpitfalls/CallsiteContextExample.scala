package com.liorregev.sparkpitfalls

object CallsiteContextExample extends Pitfall {
  import spark.implicits._
  val callsiteContext = CallsiteContext(spark.sparkContext, "Pitfall6", Nil)
  callsiteContext.enrichContext("enrich1") {
    newContext =>
      newContext.enrichContext("enrich2") {
        _ =>
          spark.createDataset(0 to 10).agg(Map("value" -> "sum")).show()
      }
      spark.createDataset(0 to 10).agg(Map("value" -> "sum")).show()
  }
  spark.createDataset(0 to 10).agg(Map("value" -> "sum")).show()
  Thread.sleep(60000)
}
