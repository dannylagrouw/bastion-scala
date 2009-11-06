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

import scala.util.matching.Regex

/**
 * A service that can retrieve and store configurable properties.
 * 
 * @author Danny
 */
trait ConfigService {

	/**
	 * Returns the single value of a configurable property.
	 * 
	 * @param propertyName
	 *            a single property name.
	 * @return value of the property.
	 */
  def getConfig(propertyFilter: String): Option[String]

	/**
	 * Returns the value of a configurable property. This value can either be a
	 * map of name/value pairs, a collection (containing only the values) or a
	 * single value. Properties can be requested with a filter.
	 * 
	 * @param propertyFilter
	 *            regular expression for filtering properties by name.
	 * @return a map containing property names and values.
	 */
  def getConfig(propertyFilter: Regex): Map[String, String]
  
}
