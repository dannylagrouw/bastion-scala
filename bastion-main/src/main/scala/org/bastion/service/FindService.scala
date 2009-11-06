package org.bastion.service

trait FindService {

  def find[T](clazz: Class[T], key: Any): Option[T]
  
}