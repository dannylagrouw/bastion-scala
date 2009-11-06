package org.bastion.domain

import org.bastion.adapter._
import org.bastion.message._
import org.bastion.service.impl._

object TestDomain {

  def main(args: Array[String]): Unit = {
    run
  }

  def run {
    val alertService = new BasicAlertService
    Domain.instance.addAdapter(new AlertAdapter(alertService))
    var x = Domain.instance ! AlertMessage("Error in line ", Level.ERROR, "18")
    x = Domain.instance ! AlertMessage("Error in line ", Level.WARNING, "27")
        
    println("Alerts:")
    alertService.alerts.foreach(s => println("- " + s))
    println("Alerts:")
    alertService.alerts.foreach(s => println("- " + s))
    println("Alerts:")
    alertService.getAlertsClearAfterwards.foreach(s => println("- " + s))
    println("Alerts:")
    alertService.getAlertsClearAfterwards.foreach(s => println("- " + s))
    
    println(new Alert("X123", Level.INFO, "x", "y"))
  }

}
