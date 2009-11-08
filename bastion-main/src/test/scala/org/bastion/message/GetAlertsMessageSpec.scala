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
import org.bastion.domain.{Level, Alert}

class GetAlertsMessageSpec extends Spec with ShouldMatchers {

  describe("A GetAlertsMessage") {

    describe("(when initialized with a value for clearAlertsAfterwards)") {
      val message = new GetAlertsMessage(true)

      it("should contain that value") {
        message.clearAlertsAfterwards should be (true)
      }
    }

    describe("(when initialized)") {
      val message = new GetAlertsMessage

      it("should contain an empty Alert list") {
        message.alerts should be ('empty)
      }
    }

    describe("(when passed an Alert list)") {
      val message = new GetAlertsMessage
      val alerts = List(new Alert("X", Level.ERROR))
      message.alerts = alerts

      it("should contain that list") {
        message.alerts should be (alerts)
      }
    }

  }

}