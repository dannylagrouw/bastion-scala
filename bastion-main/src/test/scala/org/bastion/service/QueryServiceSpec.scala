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
package org.bastion.service

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec
import org.bastion.service.QueryService

class QueryServiceSpec extends Spec with ShouldMatchers {

  val service = new QueryService {

    def executeQuery[T](domainClass: Class[T]) = List("aaa", "bbb", "ccc").iterator.asInstanceOf[Iterator[T]]
    
  }

  describe("A QueryService") {

    describe("(when sent a predicate query)") {
      it("should execute the query by applying the predicate to the entire result list") {
        val iter = service.executePredicateQuery(classOf[String]) { s => s.startsWith("a") }
        iter.toList should be (List("aaa"))
      }
    }

    describe("(when sent a named query)") {
      it("should throw an UnsupportedOperationException") {
        evaluating { service.executeNamedQuery(classOf[String], "name", Map.empty[String, Any])} should produce [UnsupportedOperationException]
      }
    }
  }

}