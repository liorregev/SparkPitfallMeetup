package com.liorregev.sparkpitfalls

import scala.util.Random

object Pitfall1 extends Pitfall {
  import spark.implicits._

  final case class SummationTask(data: Seq[Int])
  final case class MultiplicationTask(data: Seq[Int])
  final case class AverageTask(data: Seq[Int])

  val tasks = Seq(
    SummationTask((0 until 100).map(_ => Random.nextInt(10))),
    MultiplicationTask((0 until 100).map(_ => Random.nextInt(10))),
    AverageTask((0 until 100).map(_ => Random.nextInt(10)))
  )

  spark.createDataset(tasks)
    .map {
      case SummationTask(data) => data.sum.toDouble
      case MultiplicationTask(data) => data.product.toDouble
      case AverageTask(data) => data.sum.toDouble / data.length
    }
}
