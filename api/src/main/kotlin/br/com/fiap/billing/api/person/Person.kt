package br.com.fiap.billing.api.person

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Person (

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @get: NotBlank
        var name: String = "",

        @get: NotBlank
        var doc: String = "",

        @get: NotBlank
        var enrollment: String = "",

        var active: Boolean = true

)