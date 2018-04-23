package com.liorregev.sparkpitfalls

import org.apache.spark.SparkContext

import scala.collection.mutable

object CallsiteContext {
  val Mock: CallsiteContext = new CallsiteContext()
}

class CallsiteContext {
  private var contextInitParams : Option[(String, String)] = None

  private val contextStack = mutable.Stack[String]()

  private var sparkContext : Option[SparkContext] = None

  def initContext(sc : SparkContext, jobGroup: String, jobDescription: String) : Unit = {
    sparkContext = Option(sc)
    contextInitParams = Option((jobGroup, jobDescription))
    contextStack.clear()
    sc.setJobGroup(jobGroup, jobDescription)
  }

  def enrichContext[T](enrichment : String)(f: => T) : T = {
    def informSparkContextOfCurrentContext(): Unit = {
      for {
        (_, description) <- contextInitParams
        sc <- sparkContext
      } yield sc.setJobDescription(s"$description (${contextStack.reverse.mkString(".")})")
    }

    contextStack.push(enrichment)
    informSparkContextOfCurrentContext()
    val retValue = f
    contextStack.pop()
    informSparkContextOfCurrentContext()
    retValue
  }
}