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

/**
 * Specification for a service that can be used to query domain objects.
 */
trait QueryService {

  /**
   * Executes a query for all domain objects of the specified type T.
   */
  def executeQuery[T](domainClass: Class[T]): Iterator[T]

  /**
   * Executes a query for domain objects of specified type T, for which
   * the predicate named matcher evaluates to true.
   */
  def executePredicateQuery[T](domainClass: Class[T])(matcher: T => Boolean): Iterator[T] = {
    executeQuery(domainClass).filter(matcher)
  }

  /**
   * Executes a predefined query that this service is able to identify by its
   * name.
   */
  def executeNamedQuery[T](domainClass: Class[T], queryName: String, parameters: Map[String, Any]): Iterator[T] = {
    throw new UnsupportedOperationException
  }

  /**
   * Indicates if this Query Service prefers executing named queries.
   */
  var prefersNamedQueries: Boolean = false

  /**
   * Indicates if this Query Service supports executing predicate queries.
   */
  var supportsPredicateQueries: Boolean = false

  /**
   * Indicates if this Query Service prefers executing predicate queries.
   */
  var prefersPredicateQueries: Boolean = false

  /**
   * Indicates if this Query Service supports executing named queries.
   */
  var supportsNamedQueries: Boolean = false

}