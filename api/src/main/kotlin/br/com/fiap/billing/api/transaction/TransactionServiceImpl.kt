package br.com.fiap.billing.api.transaction

import br.com.fiap.billing.api.core.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class TransactionServiceImpl(private val repository: TransactionRepository) : TransactionService {
    override fun create(transaction: Transaction): Transaction {
        return repository.save(transaction)
    }

    override fun delete(id: Long): Boolean {
        val t = getById(id)
        t.cancelled = true
        repository.save(t)
        return true
    }

    override fun getById(id: Long): Transaction {
        try {
            return repository.getOne(id)
        } catch (e: EntityNotFoundException) {
            throw ResourceNotFoundException("Transaction with id $id not found")
        }
    }

    override fun findById(id: Long): Transaction? {
        return repository.findById(id)
                .orElse(null)
    }

    override fun findByPersonDoc(doc: String): List<Transaction> {
        return repository.findByPersonDoc(doc)
    }

    override fun findAll(): List<Transaction> {
        return repository.findAll()
    }

}
