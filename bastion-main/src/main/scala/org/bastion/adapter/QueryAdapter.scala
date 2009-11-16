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

import org.bastion.message.QueryMessage
import org.bastion.service.QueryService
import org.bastion.exception.{InvalidQueryServiceException, UnsupportedQueryException}

/**
 * Handles a {@link QueryMessage} by letting a {@link QueryService} execute its query.
 * Checks that the service is able to execute the query, otherwise an
 * {@link UnsupportedQueryException} is thrown.
 */
class QueryAdapter(val service: QueryService) extends Adapter[QueryMessage[Any]] {

  def handle(message: QueryMessage[Any]) = {
    val query = message.query
    if (service.prefersNamedQueries) {
      if (query.name != None) {
        message.iterator = service.executeNamedQuery(query.domainClass, query.name.get, query.parameters)
      } else if (!query.hasMatcher) {
        message.iterator = service.executeQuery(query.domainClass)
      } else if (service.supportsPredicateQueries) {
        message.iterator = service.executePredicateQuery(query.domainClass)(query.matcher.get)
      } else {
        throw new UnsupportedQueryException(service, query)
      }
    } else if (service.prefersPredicateQueries) {
      if (query.hasMatcher) {
        message.iterator = service.executePredicateQuery(query.domainClass)(query.matcher.get)
      } else if (query.name == None) {
        message.iterator = service.executeQuery(query.domainClass)
      } else if (service.supportsNamedQueries) {
        message.iterator = service.executeNamedQuery(query.domainClass, query.name.get, query.parameters)
      } else {
        throw new UnsupportedQueryException(service, query)
      }
    } else {
      throw new InvalidQueryServiceException(service)
    }
  }

}