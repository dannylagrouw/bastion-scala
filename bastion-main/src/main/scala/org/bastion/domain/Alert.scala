package org.bastion.domain

import java.io.Serializable

class Alert(val code: String, val level: Level.Value, val args: String*) extends Serializable {
  
  override def toString = {
    level + ": " + code + " " + args.mkString(", ")
  }
}
