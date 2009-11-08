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

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec

class GenerateKeyMessageSpec extends Spec with ShouldMatchers {

  describe("A GenerateKeyMessage") {

    describe("(when initialized with a DomainClass)") {
      val message = new GenerateKeyMessage(classOf[String].asInstanceOf[Class[AnyRef]])

      it("should contain that DomainClass") {
        message.domainClass should be (classOf[String])
      }

    }

    describe("(when initialized)") {
      val message = new GenerateKeyMessage(classOf[AnyRef])

      it("should have a key None") {
        message.key should be (None)
      }

    }

    describe("(when passed a new key)") {
      val message = new GenerateKeyMessage(classOf[AnyRef])
      val KEY = "123"
      message.key = Some(KEY)

      it("should contain that key") {
        message.key should be (Some(KEY))
      }
    }

  }

}