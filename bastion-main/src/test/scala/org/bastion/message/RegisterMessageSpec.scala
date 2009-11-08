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
package org.bastion.message

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class RegisterMessageSpec extends Spec with ShouldMatchers {

  describe("A RegisterMessage") {

    describe("(when intialized with a domain object)") {
      val OBJECT = "object"
      val message = new RegisterMessage(OBJECT)
      it("should contain that object") {
        message.domainObject should be (OBJECT)
      }
      it("should contain an empty id") {
        message.id should be (None)
      }
    }

    describe("(when passed an id)") {
      val message = new RegisterMessage("object")
      message.id = Some(12345)
      it("should contain that id") {
        message.id should be (Some(12345))
      }
    }

  }

}