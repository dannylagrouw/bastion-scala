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

import util.matching.Regex

// TODO Specialized classes?
class GetConfigMessage(val propertyName: String, val propertyFilter: Regex) extends DomainMessage {

  var resultMap: Option[Map[String, AnyRef]] = None

  def this(propertyName: String) = this(propertyName, null)

  def this(propertyFilter: Regex) = this(null, propertyFilter)

  def singleResult = resultMap match {
    case Some(m) if (!m.isEmpty) => Some(m.head._2)
    case _ => None
  }
  
}