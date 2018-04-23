package com.liorregev.sparkpitfalls

import java.sql.Timestamp

import org.apache.spark.sql.test.SharedSQLContext
import org.scalatest.{BeforeAndAfterEachTestData, Matchers, TestData}

import scala.util.Random

trait SparkDriverFunSuite extends SharedSQLContext with Matchers with BeforeAndAfterEachTestData {
  protected var callsiteContext = CallsiteContext(spark.sparkContext, this.getClass.getName, Nil)
  private var _randomSeed: Long = Random.nextLong()
  protected def randomSeed: Long = _randomSeed
  protected val randomGenerator: Random = new Random(randomSeed)

  override protected def afterEach(testData: TestData): Unit = super.afterEach(testData)
  override protected def beforeEach(testData: TestData): Unit = {
    _randomSeed = Random.nextLong()
    randomGenerator.setSeed(_randomSeed)
    callsiteContext = CallsiteContext(spark.sparkContext, s"${this.getClass.getName}(${testData.name})", Nil)
  }

  final def randomString(length: Int): String = randomGenerator.alphanumeric.take(length).mkString

  final def randomDate(minDate: Timestamp, maxDate: Timestamp): Timestamp = {
    val offset = minDate.getTime
    val end = maxDate.getTime
    val diff = end - offset + 1
    new Timestamp(offset + (randomGenerator.nextDouble() * diff).toLong)
  }

  final def randomDate(): Timestamp = {
    randomDate(new Timestamp(0L), new Timestamp(Long.MaxValue))
  }
}
