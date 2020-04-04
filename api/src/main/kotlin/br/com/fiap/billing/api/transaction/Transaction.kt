package br.com.fiap.billing.api.transaction

import br.com.fiap.billing.api.person.Person
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "transaction_")
data class Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @ManyToOne
        @JoinColumn(name = "person_id")
        var person : Person = Person(),

        @get: NotNull
        var dateTransaction: Date = Date(),

        @get: NotNull
        var valueTransaction: Double = 0.0,

        @get: NotNull
        var quantityPlots: Int = 1,

        var cancelled : Boolean = false

)