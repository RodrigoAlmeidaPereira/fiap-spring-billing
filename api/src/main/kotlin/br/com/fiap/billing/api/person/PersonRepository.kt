package br.com.fiap.billing.api.person

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository : JpaRepository <Person, Long> {
    fun findByDoc(doc: String): Person?
}