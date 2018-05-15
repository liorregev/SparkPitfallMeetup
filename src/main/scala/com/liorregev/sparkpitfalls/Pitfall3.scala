package com.liorregev.sparkpitfalls

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{functions => F}

object Pitfall3 extends Pitfall {
  import spark.implicits._

  val N = 10000

  type PartitionIndex = Int
  type PartitionLastValue = Int

  val ds = spark.createDataset(0 to N).repartition(100).sort($"value" asc)
  spark.sparkContext.setJobDescription("Calculate using window")
  val differencesWindowed = ds
    .select($"value" - (F.lag($"value", 1) over Window.orderBy($"value" asc)))
    .as[Option[Int]]
    .collect()
    .flatten
  spark.sparkContext.setJobDescription("Calculate using RDD")
  val differencesRdd = {
    val lastValues: Broadcast[Map[PartitionIndex, PartitionLastValue]] = spark.sparkContext.broadcast(
      ds.rdd
        .mapPartitionsWithIndex((idx, it) => {
          Seq(it.foldLeft(idx -> 0) {
            case (_, newValue) => idx -> newValue
          }).iterator
        })
        .collect()
        .toMap
    )

    ds.rdd
      .mapPartitionsWithIndex((idx, it) => {
        if(!it.hasNext) {
          Iterator.empty
        } else {
          val previousPartitionLastValue = lastValues.value.get(idx - 1)

          (Seq(previousPartitionLastValue).flatten.iterator ++ it)
            .sliding(2)
            .map(_.reverse.reduce(_ - _))
        }
      })
      .collect()
      .drop(1)
  }

  println("Ready")
  Thread.sleep(60000)
}
