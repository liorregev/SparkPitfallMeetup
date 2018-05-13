package com.liorregev.sparkpitfalls

import scala.util.Random

object Pitfall2Skewed extends Pitfall {
  import spark.implicits._

  final case class TestResult(studentId: Int, result: Int)
  final case class StudentAverage(studentId: Int, average: Double)

  val N = 1000000

  val data = (0 until N)
    .map(_ => TestResult(Random.nextInt(100), Random.nextInt(100)))
  val skewedData = (0 until 2*N)
    .map(_ => TestResult(0, Random.nextInt(100)))

  val _ = spark.createDataset(data ++ skewedData)
    .groupByKey(_.studentId)
    .mapGroups {
      case (student, studentData) =>
        val studentDataSeq = studentData.toSeq
        val average = studentDataSeq.map(_.result).sum.toDouble / studentDataSeq.length
        StudentAverage(student, average)
    }
    .collect()

  println("Ready")
  Thread.sleep(60000)
}
