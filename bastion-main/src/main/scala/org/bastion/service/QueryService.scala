package org.bastion.service

trait QueryService {

  def executeQuery[T](domainClass: Class[T]): Iterator[T]

  def executePredicateQuery[T](domainClass: Class[T])(matcher: T => Boolean): Iterator[T] = {
    executeQuery(domainClass).filter(matcher)
  }

  def executeNamedQuery[T](domainClass: Class[T], queryName: String, parameters: Map[String, Any]): Iterator[T] = {
    throw new UnsupportedOperationException
  }

  /**
   * Indicates if this Query Service prefers executing named queries.
   */
  def prefersNamedQueries: Boolean = false

  /**
   * Indicates if this Query Service supports executing predicate queries.
   */
  def supportsPredicateQueries: Boolean = false

  /**
   * Indicates if this Query Service prefers executing predicate queries.
   */
  def prefersPredicateQueries: Boolean = false

  /**
   * Indicates if this Query Service supports executing named queries.
   */
  def supportsNamedQueries: Boolean = false

}