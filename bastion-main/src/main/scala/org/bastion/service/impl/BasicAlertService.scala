package org.bastion.service.impl

import org.bastion.service.AlertService
import org.bastion.domain.Alert

class BasicAlertService extends AlertService {

  var alerts = List[Alert]()
  
  override def getAlertsClearAfterwards = {
    val clone = alerts
    alerts = List[Alert]()
    clone
  }
  
  override def alert(alert: Alert) {
    println("AlertService.alert, alert = " + alert)
    alerts = alert :: alerts
  }

}
