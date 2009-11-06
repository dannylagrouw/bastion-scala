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

import scala.collection._
import java.util.concurrent.locks.ReentrantLock

class MemoryStore {

  val store = mutable.Map[Int, mutable.Map[Any, AnyRef]]()

  val revStore = mutable.Map[Int, mutable.Map[AnyRef, Any]]()

  val lock = new ReentrantLock

  var lastId: Long = 0

  def clear = {
    lastId = 0
    store.clear
    revStore.clear
  }

  def getId: Long = {
    lock.lock
    try {
      lastId += 1
    } finally {
      lock.unlock
    }
    lastId
  }

  def register(domainObject: AnyRef): Any = {
    val id = getId
    getObjects(domainObject.getClass.hashCode).put(id, domainObject)
    getRevObjects(domainObject.getClass.hashCode).put(domainObject, id)
    id
  }

  def getObjects[T](domainClass: Int): mutable.Map[Any, T] = {
    store.get(domainClass) match {
      case Some(m) => m.asInstanceOf[mutable.Map[Any, T]]
      case None => {
        val newObjects = mutable.Map[Any, AnyRef]()
        store.put(domainClass, newObjects)
        newObjects.asInstanceOf[mutable.Map[Any, T]]
      }
    }
  }

  def getRevObjects[T](domainClass: Int): mutable.Map[T, Any] = {
    revStore.get(domainClass) match {
      case Some(m) => m.asInstanceOf[mutable.Map[T, Any]]
      case None => {
        val newObjects = mutable.Map[AnyRef, Any]()
        revStore.put(domainClass, newObjects)
        newObjects.asInstanceOf[mutable.Map[T, Any]]
      }
    }
  }

  def find[T](domainClass: Class[T], key: Any): Option[T] = {
    getObjects(domainClass.hashCode).get(key).asInstanceOf[Option[T]]
  }

  def dump {
    println(this)
  }

  def executeQuery[T](domainClass: Class[T]): Iterator[T] = {
    getObjects(domainClass.hashCode).valuesIterator
  }

  def generateId(domainObject: AnyRef): Any = {
    getRevObjects(domainObject.getClass.hashCode).get(domainObject).get
  }

  override def toString = {
    val sb = new StringBuilder
    store.foreach { entry1 =>
      val (key, objects) = entry1
      sb append key
      sb append "\n"
      objects.foreach { entry2 =>
        val (id, o) = entry2
        sb append "- "
        sb append id
        sb append " -> "
        sb append o.toString
        sb append "\n"
      }
    }
    sb.toString
  }
}
