package br.com.fiap.billing.api.transaction

import br.com.fiap.billing.api.person.Person
import com.fasterxml.jackson.annotation.JsonFormat
import com.opencsv.bean.CsvRecurse
import java.time.LocalDate
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        var transactionDate: LocalDate = LocalDate.now(),

        @get: NotNull
        var transactionValue: Double = 0.0,

        @get: NotNull
        var installments: Int = 1,

        var cancelled : Boolean = false

)