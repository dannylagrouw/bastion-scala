package org.bastion.adapter

import org.bastion.message.DomainMessage

trait Adapter[T <: DomainMessage] {

    def handle(message: T)

}
