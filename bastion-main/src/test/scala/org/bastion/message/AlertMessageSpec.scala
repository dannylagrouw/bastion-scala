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
import org.bastion.domain.{Level, Alert}

class AlertMessageSpec extends Spec with ShouldMatchers {

  describe("An AlertMessage") {

    describe("(when initialized with an Alert)") {
      val alert = new Alert("X", Level.ERROR)
      val alertMessage = new AlertMessage(alert)

      it("should contain that Alert") {
        alertMessage.alert should be (alert)
      }
    }

    describe("(when initialized with parameters by its companion object)") {
      val alertMessage = AlertMessage("X", Level.ERROR, "1", "2", "3")

      it("should contain an Alert with those parameters") {
        val alert = alertMessage.alert
        alert.code should be ("X")
        alert.level should be (Level.ERROR)
        alert.args should be (List("1", "2", "3"))
      }
    }
  }
}