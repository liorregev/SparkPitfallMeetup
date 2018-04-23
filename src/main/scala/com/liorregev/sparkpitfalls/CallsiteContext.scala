package com.liorregev.sparkpitfalls

import org.apache.spark.SparkContext

final case class CallsiteContext(sparkContext: SparkContext, jobGroup: String, enrichments: Seq[String]) {
  private def getDescription: String = enrichments.mkString(".")

  def enrichContext[T](enrichment : String)(f: CallsiteContext => T) : T = {
    val newCallsiteContext = this.copy(enrichments = enrichments :+ enrichment)
    sparkContext.setJobGroup(jobGroup, newCallsiteContext.getDescription)
    val retValue = f(newCallsiteContext)
    sparkContext.setJobGroup(jobGroup, this.getDescription)
    retValue
  }
}