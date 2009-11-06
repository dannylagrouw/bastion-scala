package org.bastion.query

/**
 * Encapsulates a query for domain objects from a persistent store outside the
 * domain. Queries can be specified using either a Predicate/Specification
 * pattern, or a query name. Some persistency services will be able to query by
 * Predicate; others will query using a named query; still others will support
 * both. Therefore, ideally a query will be specified using both methods. The
 * {@link QueryAdapter} will ask the {@link QueryService} which method is
 * supported or preferred.
 *
 * @author Danny Lagrouw
 *
 * @param <T>
 */
// TODO specialize
class DomainQuery[T](implicit m: Manifest[T]) {

  val domainClass = m.erasure

  private[this] var matcherInternal: T => Boolean = { t => true }

  def matcher = matcherInternal

  def matcher_=(f: T => Boolean): Unit = {
    matcherInternal = f
    hasMatcher = true
  }

  var name: Option[String] = None

  var parameters = Map[String, Any]()

  // TODO matcherInternal becomes Option[...]
  var hasMatcher: Boolean = false

}

object DomainQuery {

  def apply[T](matcher: T => Boolean)(implicit m: Manifest[T]) = {
    val q = new DomainQuery[T]
    q.matcher = matcher
    q
  }

}