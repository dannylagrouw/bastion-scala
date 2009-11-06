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

import org.bastion.service.ConfigService
import org.bastion.message.GetConfigMessage

/**
 * Adapter that lets an instance of ConfigService handle GetConfigMessages.
 *
 * @author Danny
 */
class GetConfigAdapter(val service: ConfigService) extends Adapter[GetConfigMessage] {

  def handle(message: GetConfigMessage) = {
    message.resultMap =
      if (message.propertyName != null) {
        service.getConfig(message.propertyName) match {
          case Some(value) => Some(Map(message.propertyName -> value))
          case _ => None
        }
      } else if (message.propertyFilter != null) {
        Some(service.getConfig(message.propertyFilter))
      } else {
        None
      }
  }

}