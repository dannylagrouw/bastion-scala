package org.bastion.service

trait RegisterService {

  def register(domainObject: AnyRef): Option[Any]
  
}