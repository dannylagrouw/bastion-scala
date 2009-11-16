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

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.bastion.message.DomainMessage
import org.bastion.adapter.Adapter
import scala.actors.Actor
import scala.actors.Actor._

class DomainSpec extends Spec with ShouldMatchers {

  class MockMessage extends DomainMessage

  class AnotherMockMessage extends DomainMessage

  class MockAdapter extends Adapter[MockMessage] {
    var handledMessage: MockMessage = null

    def handle(message: MockMessage) = {
      handledMessage = message
    }
  }

  describe("A Domain") {

    describe("(when an adapter is being added)") {

      it("should add it to its list of adapters") {
        val domain = new Domain
        val adapter = new MockAdapter
        domain.addAdapter[MockMessage](adapter)

        domain.adapters((new MockMessage).getClass.getName) should be (List(adapter))
      }

      it("should be able to store more than one adapter for the same type of message") {
        val domain = new Domain
        val adapter1 = new MockAdapter
        val adapter2 = new MockAdapter
        domain.addAdapter[MockMessage](adapter1)
        domain.addAdapter[MockMessage](adapter2)

        domain.adapters((new MockMessage).getClass.getName) should contain (adapter1.asInstanceOf[Adapter[DomainMessage]])
        domain.adapters((new MockMessage).getClass.getName) should contain (adapter2.asInstanceOf[Adapter[DomainMessage]])
      }
    }

    describe("(when sent a message)") {

      it("should let the appropriate adapter handle the message") {
        val domain = new Domain
        val adapter = new MockAdapter
        domain.addAdapter[MockMessage](adapter)
        val message = new MockMessage

        domain ! message

        adapter.handledMessage should be (message)
      }

      it("should return the sent and handled message") {
        val domain = new Domain
        val adapter = new MockAdapter
        domain.addAdapter[MockMessage](adapter)
        val message = new MockMessage

        val returnedMessage = domain ! message

        returnedMessage should be (message)
      }

      describe("(when no appropriate adapter has been registered)") {

        it("should do nothing") {
          val domain = new Domain
          val adapter = new MockAdapter
          domain.addAdapter[MockMessage](adapter)
          val message = new AnotherMockMessage

          domain ! message

          adapter.handledMessage should be (null)
        }
      }
    }

    describe("(when two threads use the Domain)") {

      describe("(when each thread sends a message)") {

        it("should let a separate instance of itself handle each message") {
          val actor2 = actor {
            receive {
              case actor: Actor => {
                println("actor2 received message from other actor for domain " + Domain.instance)
                actor ! Domain.instance
              }
            }
          }
          var otherDomain: Domain = null
          actor2 ! self
          self.receiveWithin(500) {
            case domain: Domain => {
              println("self received domain " + domain + "; its own domain is " + Domain.instance)
              otherDomain = domain
            }
          }
          otherDomain should not be (null)
          otherDomain should not be (Domain.instance)
        }
      }
    }
  }

}