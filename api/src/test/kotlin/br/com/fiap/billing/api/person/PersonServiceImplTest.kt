package br.com.fiap.billing.api.person

import br.com.fiap.billing.api.core.exception.ResourceNotFoundException
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.annotation.PostConstruct

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import java.util.*
import javax.persistence.EntityNotFoundException


@ExtendWith(SpringExtension::class)
internal class PersonServiceImplTest {

    @MockkBean
    private lateinit var repository: PersonRepository

    private lateinit var service: PersonService
    private val entity: Person = Person(id = 1, name = "Rodrigo", enrollment = "1234", doc = "4321", active = true)

    @PostConstruct
    fun init() {
        service = PersonServiceImpl(repository)
    }

    @Test
    fun should_create_with_success() {
        every { repository.save(entity) } returns entity

        assertThat(service.create(entity)).isEqualTo(entity)

        verify(exactly = 1) { repository.save(entity) }
    }

    @Test
    fun should_update_with_success() {
        every { repository.save(entity) } returns entity
        every { repository.getOne(entity.id) } returns entity

        assertThat(service.update(entity.id, entity)).isEqualTo(entity)

        verify(exactly = 1) { repository.save(entity) }
        verify(exactly = 1) { repository.getOne(entity.id) }
    }

    @Test
    fun delete_with_success() {
        val deleted: Person = entity.copy(active = false)

        every { repository.save(deleted) } returns deleted
        every { repository.getOne(entity.id) } returns entity

        assertThat(service.delete(entity.id)).isEqualTo(true)

        verify(exactly = 1) { repository.save(deleted) }
        verify(exactly = 1) { repository.getOne(entity.id) }
    }

    @Test
    fun get_by_id_with_success() {
        every { repository.getOne(entity.id) } returns entity

        assertThat(service.getById(entity.id)).isEqualTo(entity)

        verify(exactly = 1)  { repository.getOne(entity.id) }
    }

    @Test
    fun should_throw_an_exception_when_tries_to_get_one_and_resource_does_not_exists() {
        every { repository.getOne(entity.id) } throws EntityNotFoundException()

        assertThatExceptionOfType(ResourceNotFoundException::class.java)
                .isThrownBy { service.getById(entity.id) }

        verify(exactly = 1)  { repository.getOne(entity.id) }
    }

    @Test
    fun findByIdWithSucess() {
        every { repository.findById(entity.id) } returns Optional.of(entity)

        assertThat(service.findById(entity.id)).isEqualTo(entity)

        verify(exactly = 1)  { repository.findById(entity.id) }
    }

    @Test
    fun findByIdWhenResourceDoesNotExists() {
        every { repository.findById(entity.id) } returns Optional.empty()

        assertThat(service.findById(entity.id)).isEqualTo(null)

        verify(exactly = 1)  { repository.findById(entity.id) }
    }

    @Test
    fun findAllWithSuccess() {
        val list = Arrays.asList(entity)

        every { repository.findAll() } returns list

        assertThat(service.findAll()).isEqualTo(list)

        verify(exactly = 1) { repository.findAll() }
    }
}