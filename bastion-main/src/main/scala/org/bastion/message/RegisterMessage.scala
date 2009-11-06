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

/**
 * DomainMessage that indicates a new domain object being created, that needs to be
 * registered with the domain. A listening service may assign a unique ID number
 * to the object.
 */
class RegisterMessage(val domainObject: AnyRef, val id: Any) extends DomainMessage {

  def this(domainObject: AnyRef) = this(domainObject, null)

}

object RegisterMessage {

  def apply(domainObject: AnyRef) = new RegisterMessage(domainObject)
  
}