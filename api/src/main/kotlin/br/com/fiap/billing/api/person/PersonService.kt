package br.com.fiap.billing.api.person

interface PersonService {

    fun create(person: Person): Person

    fun update(id: Long, person: Person): Person

    fun delete(id: Long): Boolean

    fun getById(id: Long): Person

    fun findById(id: Long): Person?

    fun findAll(): List<Person>

}