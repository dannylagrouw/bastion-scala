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

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
import org.bastion.service.AlertService
import org.bastion.message.AlertMessage
import org.bastion.domain.{Level, Alert}

class AlertAdapterSpec extends Spec with ShouldMatchers {

  describe("An AlertAdapter") {
    val service = new AlertService {
      def getAlertsClearAfterwards = null
      def alerts = null
      var alert: Alert = null
      def alert(alert: Alert) = {
        this.alert = alert
      }
    }
    val adapter = new AlertAdapter(service)
    describe("(when sent an AlertMessage)") {
      val alert = new Alert("X", Level.ERROR)
      adapter.handle(new AlertMessage(alert))
      it("should send the alert to its alert service") {
        service.alert should be (alert)
      }
    }
  }

}