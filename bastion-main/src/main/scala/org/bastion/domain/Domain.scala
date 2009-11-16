/*
 *  Copyright 2009 Danny Lagrouw
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.bastion.domain

import scala.collection._
import org.bastion.adapter.Adapter
import org.bastion.message.DomainMessage

/**
 * Directs incoming messages to the appropriate {@link Adapter adapters} for
 * handling. For this to work, adapters must be registered with the domain for
 * a specific type of {@link DomainMessage}. Each thread works with its own
 * instance of Domain.
 */
class Domain {
  
  var adapters = mutable.Map[String, List[Adapter[DomainMessage]]]()

  /**
   * Registers an {@link Adapter} for a specific type T of @{link DomainMessage}
   * with the Domain.
   */
  def addAdapter[T <: DomainMessage](adapter: Adapter[T])(implicit messageClass: Manifest[T]) {
    val newClass = messageClass.erasure.getName
    val newAdapter = adapter.asInstanceOf[Adapter[DomainMessage]]
    adapters(newClass) =
        (adapters.get(newClass) match {
          case Some(l) => newAdapter :: l
          case None => List(newAdapter)
        })
  }

  /**
   * Sends a {@link DomainMessage message} to the Domain for handling by any
   * {@link Adapter adapters} that have been registered for that kind of message.
   * If multiple adapters have been registered for the same type of message, all
   * those adapters will be asked in turn to handle the message. An adapter may
   * add information to the message, depending on the message type. The enriched
   * message is then returned to the sender.
   */
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
  
  private[this] val threadGlobal = new ThreadGlobal[Domain]
  
  def instance = {
    if (threadGlobal.value == null) {
      threadGlobal.set(new Domain)
    }
    threadGlobal.value
  }

  /**
   * {@see Domain#addAdapter}
   */
  def addAdapter[T <: DomainMessage](adapter: Adapter[T])(implicit messageClass: Manifest[T]) {
    instance.addAdapter[T](adapter)(messageClass)
  }

  /**
   * {@see Domain#!}
   */
  def ![T <: DomainMessage](message: T): T = instance ! message

}