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
package org.bastion.adapter

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.bastion.service.RegisterService
import org.bastion.message.RegisterMessage

class RegisterAdapterCheck extends Spec with ShouldMatchers {
  describe("A RegisterAdapter") {

    describe("(when sent a register message)") {
      val service = new RegisterService {
        def register(domainObject: AnyRef) = Some(12345)
      }
      val adapter = new RegisterAdapter(service)
      val message = new RegisterMessage("X")
      adapter.handle(message)
      it("should register the message's domain object with its service") {
        message.id should be (Some(12345))
      }
    }

    describe("(when its service does not return ids)") {
      val service = new RegisterService {
        def register(domainObject: AnyRef) = None
      }
      val adapter = new RegisterAdapter(service)
      val message = new RegisterMessage("X")
      adapter.handle(message)
      it("should register the message's domain object with its service") {
        message.id should be (None)
      }
    }
  }

}