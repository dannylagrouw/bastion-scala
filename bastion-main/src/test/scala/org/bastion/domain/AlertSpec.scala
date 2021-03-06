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

class AlertSpec extends Spec with ShouldMatchers {
  describe("An Alert") {
    describe("(when initialized with code and level)") {
      val alert = new Alert("X", Level.ERROR)
      it("should contain that code") {
        alert.code should be ("X")
      }
      it("should contain that level") {
        alert.level should be (Level.ERROR)
      }
      it("should not contain any parameters") {
        alert.params should be ('empty)
      }
    }

    describe("(when initialized with code, level and params)") {
      val alert = new Alert("X", Level.ERROR, "1", "2")
      it("should contain that code") {
        alert.code should be ("X")
      }
      it("should contain that level") {
        alert.level should be (Level.ERROR)
      }
      it("should contain those parameters") {
        alert.params should be (List("1", "2"))
      }
    }
  }

}