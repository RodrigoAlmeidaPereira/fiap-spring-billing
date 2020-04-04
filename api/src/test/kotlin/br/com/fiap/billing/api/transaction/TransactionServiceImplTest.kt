package br.com.fiap.billing.api.transaction

import br.com.fiap.billing.api.core.exception.ResourceNotFoundException
import br.com.fiap.billing.api.person.Person
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import javax.annotation.PostConstruct
import javax.persistence.EntityNotFoundException

@ExtendWith(SpringExtension::class)
internal class TransactionServiceImplTest {

    @MockBean
    private lateinit var repository : TransactionRepository

    private lateinit var service : TransactionService

    private val person: Person = Person(id = 1, name = "Rodrigo", enrollment = "1234", doc = "4321", active = true)
    private val entity : Transaction = Transaction(1, person, Date(), 425.5, 3)

    @PostConstruct
    fun init(){
        service = TransactionServiceImpl(repository)
    }

    @Test
    fun should_create_with_success() {
        every { repository.save(entity) } returns entity

        Assertions.assertThat(service.create(entity)).isEqualTo(entity)

        verify(exactly = 1) { repository.save(entity) }
    }

    @Test
    fun delete_with_success() {
        val deleted: Transaction = entity.copy(cancelled = true)

        every { repository.save(deleted) } returns deleted
        every { repository.getOne(entity.id) } returns entity

        Assertions.assertThat(service.delete(entity.id)).isEqualTo(true)

        verify(exactly = 1) { repository.save(deleted) }
        verify(exactly = 1) { repository.getOne(entity.id) }
    }

    @Test
    fun get_by_id_with_success() {
        every { repository.getOne(entity.id) } returns entity

        Assertions.assertThat(service.getById(entity.id)).isEqualTo(entity)

        verify(exactly = 1)  { repository.getOne(entity.id) }
    }

    @Test
    fun should_throw_an_exception_when_tries_to_get_one_and_resource_does_not_exists() {
        every { repository.getOne(entity.id) } throws EntityNotFoundException()

        Assertions.assertThatExceptionOfType(ResourceNotFoundException::class.java)
                .isThrownBy { service.getById(entity.id) }

        verify(exactly = 1)  { repository.getOne(entity.id) }
    }

    @Test
    fun findByIdWithSucess() {
        every { repository.findById(entity.id) } returns Optional.of(entity)

        Assertions.assertThat(service.findById(entity.id)).isEqualTo(entity)

        verify(exactly = 1)  { repository.findById(entity.id) }
    }

    @Test
    fun findByIdWhenResourceDoesNotExists() {
        every { repository.findById(entity.id) } returns Optional.empty()

        Assertions.assertThat(service.findById(entity.id)).isEqualTo(null)

        verify(exactly = 1)  { repository.findById(entity.id) }
    }

    @Test
    fun findByPersonDocWithSuccess() {
        val list = Arrays.asList(entity)

        every { repository.findAll() } returns list

        Assertions.assertThat(service.findByPersonDoc("4321")).isEqualTo(list)

        verify(exactly = 1) { repository.findAll() }
    }


}