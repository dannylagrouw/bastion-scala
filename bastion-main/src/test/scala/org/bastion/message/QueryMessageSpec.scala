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
import org.bastion.query.DomainQuery

class QueryMessageSpec extends Spec with ShouldMatchers {

  describe("A QueryMessage") {

    describe("(when initialized with a query)") {
      val query = new DomainQuery[String]()
      query.name = Some("queryname")
      val message = new QueryMessage(query)

      it("should contain that query") {
        message.query should be (query)
      }

      it("should contain an empty result iterator") {
        message.iterator should be (null)
      }

      it("should return an empty single result") {
        message.singleResult should be (None)
      }
    }

    describe("(when initialized with a query name)") {
      val message = QueryMessage.byName[String]("name")

      it("should contain a query with that name") {
        message.query.name should be (Some("name"))
      }
    }

    describe("(when initialized with a query matcher)") {
      val matcher: String => Boolean = { s => true }
      val message = QueryMessage.matches[String](matcher)

      it("should contain a query with that matcher") {
        message.query.matcher should be (Some(matcher))
      }
    }

    describe("(when passed a result iterator)") {
      val message = QueryMessage.byName[Int]("name")
      val iterator = List(1, 2, 3).iterator
      message.iterator = iterator

      it("should contain that iterator") {
        message.iterator should be (iterator)
      }

      it("should be able to return its first result once") {
        message.singleResult should be (Some(1))
        message.singleResult should be (None)
      }
    }

  }

}