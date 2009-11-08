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

import org.bastion.query.DomainQuery

/**
 * DomainMessage indicating that a domain object needs to query objects from a
 * persistence service. Results from the query can be passed back in the
 * message.
 *
 * @param <T>
 *            type of the objects that are queried.
 */
class QueryMessage[T](val query: DomainQuery[T]) extends DomainMessage {

  var iterator: Iterator[T] = null

  def singleResult(): Option[T] = {
    if (iterator != null && iterator.hasNext) {
      val value = iterator.next
      iterator = null
      Some(value)
    } else {
      None
    }
  }

}

object QueryMessage {

  def byName[T](name: String)(implicit m: Manifest[T]) = {
    val q = new DomainQuery[T]
    q.name = Some(name)
    new QueryMessage[T](q)
  }

  def matches[T](matcher: T => Boolean)(implicit m: Manifest[T]) = {
    val q = DomainQuery[T](matcher)
    new QueryMessage[T](q)
  }

}