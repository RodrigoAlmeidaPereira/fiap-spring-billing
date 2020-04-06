package br.com.fiap.billing.api.transaction.web

import br.com.fiap.billing.api.person.Person
import javax.validation.constraints.NotNull

data class TransactionPostRequest (

        var personDoc : String,

        @get: NotNull
        var transactionDate: String = "",

        @get: NotNull
        var transactionValue: Double = 0.0,

        @get: NotNull
        var installments: Int = 1

)