package org.bastion.domain

import scala.collection._
import org.bastion.adapter.Adapter
import org.bastion.message.{QueryMessage, RegisterMessage, DomainMessage}

class Domain {
  
  var adapters = mutable.Map[String, List[Adapter[DomainMessage]]]()

  def addAdapter[T <: DomainMessage](adapter: Adapter[T])(implicit messageClass: Manifest[T]) {
    val newClass = messageClass.erasure.getName
    val newAdapter = adapter.asInstanceOf[Adapter[DomainMessage]]
    adapters(newClass) =
        (adapters.get(newClass) match {
          case Some(l) => newAdapter :: l
          case None => List(newAdapter)
        })
  }

  def ![T <: DomainMessage](message: T): T = {
    println("Received message " + message)
    adapters.get(message.getClass.getName) match {
      case Some(mal) => mal.foreach { _.handle(message) }
      case None => println("No adapter registered for message " + message.getClass)
    }
    message
  }

}

object Domain {
  
  val threadGlobal = new ThreadGlobal[Domain]
  
  def instance = {
    if (threadGlobal.value == null) {
      threadGlobal.set(new Domain)
    }
    threadGlobal.value
  }

  def addAdapter[T <: DomainMessage](adapter: Adapter[T])(implicit messageClass: Manifest[T]) {
    instance.addAdapter[T](adapter)(messageClass)
  }

  def ![T <: DomainMessage](message: T): T = instance ! message

}