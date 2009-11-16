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
package org.bastion.memory.service

import org.bastion.service.{QueryService, FindService, RegisterService}
import java.lang.Class

class MemoryService(val memoryStore: MemoryStore) extends RegisterService with FindService with QueryService {

  def register(domainObject: AnyRef): Option[Any] = {
    Some(memoryStore.register(domainObject))
  }

  def find[T](t: Class[T], key: Any): Option[T] = {
    memoryStore.find(t, key)
  }

  prefersPredicateQueries = true

  supportsPredicateQueries = true

  override def executeQuery[T](domainClass: Class[T]) = memoryStore.executeQuery(domainClass)

}