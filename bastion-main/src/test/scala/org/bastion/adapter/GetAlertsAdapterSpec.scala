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
import org.bastion.service.AlertService
import org.bastion.domain.{Level, Alert}
import org.bastion.message.GetAlertsMessage

class GetAlertsAdapterSpec extends Spec with ShouldMatchers {

  describe("A GetAlertsAdapter") {
    val alertNoClear = new Alert("X", Level.ERROR)
    val alertClear = new Alert("Y", Level.ERROR)
    val service = new AlertService {
      def getAlertsClearAfterwards = List(alertClear)
      def alerts = List(alertNoClear)
      def alert(alert: Alert) = null
    }
    val adapter = new GetAlertsAdapter(service)

    describe("(when asked to get alerts without clearing afterwards)") {
      val message1 = new GetAlertsMessage(false)
      adapter.handle(message1)
      it("should return the appropriate alerts from its service") {
        message1.alerts should be (List(alertNoClear))
      }
    }

    describe("(when asked to get alerts with clearing afterwards)") {
      val message1 = new GetAlertsMessage(true)
      adapter.handle(message1)
      it("should return the appropriate alerts from its service") {
        message1.alerts should be (List(alertClear))
      }
    }
  }

}