package br.com.fiap.billing.api.transaction.web

import br.com.fiap.billing.api.person.Person
import br.com.fiap.billing.api.person.PersonService
import br.com.fiap.billing.api.transaction.Transaction
import br.com.fiap.billing.api.transaction.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

@RestController
@RequestMapping("/transactions")
class TransactionRestService(private val service : TransactionService,
                             private val personService: PersonService) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody transaction: Transaction) : Transaction {
        return service.create(transaction)
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id : Long) : Boolean{
        return service.delete(id)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/extract/{doc}")
    fun findByPersonDoc(@PathVariable doc : String) : List<Transaction>{
        return service.findByPersonDoc(doc)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll() : List<Transaction> {
        return service.findAll()
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/simulate-data")
    fun simulate(){
        personService.findAll()
                .forEach { createSimulateData(it) }
    }

    fun createSimulateData(person: Person) {
        val numberOfTransactions = Random.nextInt(1, 10)

        for (i in 1 .. numberOfTransactions) {
            val date = LocalDate.of(2020, Random.nextInt(1, 3), Random.nextInt(1, 29))
            val transaction = Transaction(
                    transactionValue = Random.nextDouble(20.0, 120.0),
                    transactionDate = date,
                    installments = Random.nextInt(1, 12),
                    person = person)

            service.create(transaction)
        }
    }

}