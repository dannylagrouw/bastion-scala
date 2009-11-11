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
import org.bastion.service.ConfigService
import java.lang.String
import util.matching.Regex
import org.bastion.message.GetConfigMessage

class GetConfigAdapterSpec extends Spec with ShouldMatchers {

  describe("A GetConfigAdapter") {
    val SINGLE_VALUE = "893823982312092398"
    val MAP_VALUE = Map("1" -> "8239823971298127284230932")
    val service = new ConfigService {
      def getConfig(propertyFilter: String) = Some(SINGLE_VALUE)
      def getConfig(propertyFilter: Regex) = MAP_VALUE
    }
    val adapter = new GetConfigAdapter(service)

    describe("(when sent a property name)") {
      val message = new GetConfigMessage("y")
      adapter.handle(message)

      it("should return the appropriate properties from its service") {
        message.resultMap should be (Some(Map("y" -> SINGLE_VALUE)))
      }
    }

    describe("(when sent a property filter)") {
      val message = new GetConfigMessage("x".r)
      adapter.handle(message)

      it("should return the appropriate properties from its service") {
        message.resultMap should be (Some(MAP_VALUE))
      }
    }

    describe("(when sent nothing)") {
      val message = new GetConfigMessage(null, null)
      adapter.handle(message)

      it("should return None") {
        message.resultMap should be (None)
      }
    }
  }
}