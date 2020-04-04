package br.com.fiap.billing.api.transaction

interface TransactionService {

    fun create(transaction: Transaction): Transaction

    fun delete(id: Long): Boolean

    fun getById(id: Long): Transaction

    fun findById(id: Long): Transaction?

    fun findByPersonDoc(doc : String) : List<Transaction>

}
