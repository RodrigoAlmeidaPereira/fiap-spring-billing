package br.com.fiap.billing.api.transaction

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByPersonDoc(doc: String): List<Transaction>
}
