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
package org.bastion.query

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.bastion.message.DomainMessage

class DomainQuerySpec extends Spec with ShouldMatchers {

  class MockMessage extends DomainMessage

  describe("A DomainQuery") {

    describe("(when initialized)") {
      val query = new DomainQuery[MockMessage]

      it("contains an empty name") {
        query.name should be (None)
      }
      it("contains no matcher") {
        query.matcher should be (None)
      }
      it("contains an empty parameter list") {
        query.parameters should be ('empty)
      }
    }
    describe("(when initialized with a name)") {
      it("contains that name") {
        val query = new DomainQuery[MockMessage]
        query.name = Some("mock query")

        query.name should be(Some("mock query"))
      }
    }
    describe("(when initialized with a matcher)") {
      it("contains that matcher") {
        val matcher = { m: MockMessage => true }
        val query = new DomainQuery[MockMessage]
        query.matcher = matcher

        query.matcher should be (Some(matcher))
      }
    }
    describe("(when initialized with parameters)") {
      it("contains those parameters") {
        val parameters = Map("a" -> 1, "b" -> 2)
        val query = new DomainQuery[MockMessage]
        query.parameters = parameters

        query.parameters should be (parameters)
      }
    }

  }

}