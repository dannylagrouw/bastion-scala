package org.bastion.adapter

import org.bastion.message.AlertMessage
import org.bastion.service.AlertService

class AlertAdapter(service: AlertService) extends Adapter[AlertMessage] {

  override def handle(alertMessage: AlertMessage) {
    println("AlertAdapter.handle, message = " + alertMessage)
    service.alert(alertMessage.alert)
  }

}
