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
import org.bastion.service.QueryService
import collection.immutable.Map
import java.lang.{String, Class}
import org.bastion.message.QueryMessage
import org.bastion.query.DomainQuery
import org.bastion.exception.UnsupportedQueryException

class QueryAdapterSpec extends Spec with ShouldMatchers {

  describe("A QueryAdapter") {
    val service = new QueryService {
      var lastCall = ""
      var prefersNamedQueriesInternal = false
      var prefersPredicateQueriesInternal = false
      var supportsNamedQueriesInternal = false
      var supportsPredicateQueriesInternal = false

      override def prefersNamedQueries = prefersNamedQueriesInternal
      override def prefersPredicateQueries = prefersPredicateQueriesInternal
      override def supportsNamedQueries = supportsNamedQueriesInternal
      override def supportsPredicateQueries = supportsPredicateQueriesInternal

      def executeQuery[T](domainClass: Class[T]) = {
        lastCall = "executeQuery"
        null
      }

      override def executeNamedQuery[T](domainClass: Class[T], queryName: String, parameters: Map[String, Any]) = {
        lastCall = "executeNamedQuery"
        null
      }

      override def executePredicateQuery[T](domainClass: Class[T])(matcher: (T) => Boolean) = {
        lastCall = "executePredicateQuery"
        null
      }

      def reset(prefersNamedQueries: Boolean = false, prefersPredicateQueries: Boolean = false,
          supportsNamedQueries: Boolean = false, supportsPredicateQueries: Boolean = false) = {
        lastCall = ""
        this.prefersNamedQueriesInternal = prefersNamedQueries
        this.prefersPredicateQueriesInternal = prefersPredicateQueries
        this.supportsNamedQueriesInternal = supportsNamedQueries
        this.supportsPredicateQueriesInternal = supportsPredicateQueries
      }

      override def toString: String = {
        "MockQueryService:" + (if (prefersNamedQueries) " prefersNamedQueries" else "") +
                (if (prefersPredicateQueries) " prefersPredicateQueries" else "") +
                (if (supportsNamedQueries) " supportsNamedQueries" else "") +
                (if (supportsPredicateQueries) " supportsPredicateQueries" else "")
      }
    }

    describe("(when initialized with a service that prefers named queries)") {
      val adapter = new QueryAdapter(service)

      describe("(and sent a named query)") {
        it("should execute that query") {
          service.reset(prefersNamedQueries = true, supportsNamedQueries = true)
          val message = QueryMessage.byName[Any]("x")
          adapter.handle(message)
          service.lastCall should be ("executeNamedQuery")
        }
      }
      describe("(and sent a query without name or predicate)") {
        it("should execute a query to return all domain objects of the specified class") {
          service.reset(prefersNamedQueries = true, supportsNamedQueries = true)
          val query = new DomainQuery[Any]
          val message = new QueryMessage(query)
          adapter.handle(message)
          service.lastCall should be ("executeQuery")
        }
      }
      describe("(and sent a predicate query)") {
        describe("(when its service supports predicate queries)") {
          it("should execute the query") {
            service.reset(prefersNamedQueries = true, supportsNamedQueries = true, supportsPredicateQueries = true)
            val message = QueryMessage.matches[Any] { _ => true }
            adapter.handle(message)
            service.lastCall should be ("executePredicateQuery")
          }
        }
        describe("(when its service does not support predicate queries)") {
          it("should throw an exception") {
            service.reset(prefersNamedQueries = true, supportsNamedQueries = true, supportsPredicateQueries = false)
            val message = QueryMessage.matches[Any] { _ => true }
            evaluating { adapter.handle(message) } should produce [UnsupportedQueryException]
          }
        }
      }
    }

    describe("(when initialized with a service that prefers predicate queries)") {
      val adapter = new QueryAdapter(service)

      describe("(and sent a predicate query)") {
        it("should execute that query") {
          service.reset(prefersPredicateQueries = true, supportsPredicateQueries = true)
          val message = QueryMessage.matches[Any] { _ => true }
          adapter.handle(message)
          service.lastCall should be ("executePredicateQuery")
        }
      }
      describe("(and sent a query without name or predicate)") {
        it("should execute a query to return all domain objects of the specified class") {
          service.reset(prefersPredicateQueries = true, supportsPredicateQueries = true)
          val query = new DomainQuery[Any]
          val message = new QueryMessage(query)
          adapter.handle(message)
          service.lastCall should be ("executeQuery")
        }
      }
      describe("(and sent a named query)") {
        describe("(when its service supports named queries)") {
          it("should execute the query") {
            service.reset(prefersPredicateQueries = true, supportsNamedQueries = true, supportsPredicateQueries = true)
            val message = QueryMessage.byName[Any]("x")
            adapter.handle(message)
            service.lastCall should be ("executeNamedQuery")
          }
        }
        describe("(when its service does not support named queries)") {
          it("should throw an exception") {
            service.reset(prefersPredicateQueries = true, supportsNamedQueries = false, supportsPredicateQueries = true)
            val message = QueryMessage.byName[Any]("x")
            evaluating { adapter.handle(message) } should produce [UnsupportedQueryException]
          }
        }
      }
    }
  }

}