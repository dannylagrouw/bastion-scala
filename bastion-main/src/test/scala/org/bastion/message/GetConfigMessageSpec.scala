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

class GetConfigMessageSpec extends Spec with ShouldMatchers {

  describe("A GetConfigMessageSpec") {

    describe("(when initialized with a property name)") {
      val message = new GetConfigMessage("test")
      it("should contain that name") {
        message.propertyName should be ("test")
      }
      it("should not contain a filter") {
        message.propertyFilter should be (null)
      }
      it("should have an empty result map") {
        message.resultMap should be (None)
      }
    }

    describe("(when initialized with a property filter)") {
      val FILTER = ".*".r
      val message = new GetConfigMessage(FILTER)
      it("should contain that filter") {
        message.propertyFilter should be (FILTER)
      }
      it("should not contain a name") {
        message.propertyName should be (null)
      }
      it("should have an empty result map") {
        message.resultMap should be (None)
      }
      it("should return an empty single result") {
        message.singleResult should be (None)
      }
    }

    describe("(when passed a result map)") {
      val message = new GetConfigMessage("test")
      val MAP = Map("test" -> "value")
      message.resultMap = Some(MAP)
      it("should contain that result map") {
        message.resultMap should be (Some(MAP))
      }
      it("should be able return just the first result") {
        message.singleResult should be (Some("value"))
      }
    }

  }

}