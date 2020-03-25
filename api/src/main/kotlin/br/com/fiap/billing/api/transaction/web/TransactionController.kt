package br.com.fiap.billing.api.transaction.web

import br.com.fiap.billing.api.transaction.Transaction
import br.com.fiap.billing.api.transaction.TransactionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController(private val service : TransactionService) {

    @PostMapping
    fun create(@RequestBody transaction: Transaction) : Transaction{
        return service.create(transaction)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : Long) : Boolean{
        return service.delete(id)
    }

    @GetMapping("/extract/{doc}")
    fun findByPersonDoc(@PathVariable doc : String) : List<Transaction>{
        return service.findByPersonDoc(doc)
    }



}