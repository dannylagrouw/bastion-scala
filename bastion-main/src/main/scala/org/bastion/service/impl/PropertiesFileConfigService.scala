package org.bastion.service.impl

import java.io.File
import java.io.FileReader
import java.io.Reader
import java.util.Properties
import scala.util.matching.Regex
import org.bastion.service.ConfigService

class PropertiesFileConfigService(val properties: Properties) extends ConfigService {

  def this(propertiesReader: Reader) = {
    this(new Properties)
    properties.load(propertiesReader)
  }
  
  def this(propertiesFile: File) = {
    this(new FileReader(propertiesFile))
  }
  
  override def getConfig(propertyFilter: String): Option[String] = {
    val p = properties.getProperty(propertyFilter)
    if (p == null) None else Some(p)
  }

  override def getConfig(propertyFilter: Regex): Map[String, String] = {
    /*
    var map = scala.collection.mutable.Map.empty[String,String]
    propMap filter {
      case (key: String, value) => propertyFilter.findFirstIn(key) != None
    } foreach {
      case (k:String,v:String) => map(k) = v
    }
    map
    */
    Map.empty[String,String]
  }

}
