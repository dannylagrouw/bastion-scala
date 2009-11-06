package org.bastion.message

import org.bastion.domain.{Level, Alert}

/**
 * DomainMessage for reporting an alert (error, warning etc) from within the
 * domain. This offers an alternative for throwing exceptions in case of
 * validation errors.
 */
class AlertMessage(val alert: Alert) extends DomainMessage {

  override def toString = alert.toString

}

object AlertMessage {

  def apply(code: String, level: Level.Value, args: String*) = new AlertMessage(new Alert(code, level, args: _*))

}