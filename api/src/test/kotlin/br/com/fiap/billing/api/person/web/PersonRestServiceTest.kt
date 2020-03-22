package br.com.fiap.billing.api.person.web

import br.com.fiap.billing.api.person.Person
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

import org.assertj.core.api.Assertions.*
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import javax.annotation.PostConstruct
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class PersonRestServiceTest {

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    val url = "/bills/persons"
    var entity: Person? = null

    @PostConstruct
    fun config() {
        entity = testRestTemplate.postForEntity(url,
                Person(name = "Rodrigo", doc = "1234", enrollment = "4321"),
                Person::class.java)
                .body
    }

    @Test
    fun should_get_with_success() {
        val result = testRestTemplate.getForEntity("$url/${entity?.id}", Person::class.java)

        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(result.body?.name, entity?.name)
    }

    @Test
    fun should_find_all_with_success() {
        val result = testRestTemplate.getForEntity(url, List::class.java)

        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(result.body?.size, 2)
    }

    @Test
    fun should_post_with_success() {
        val result = testRestTemplate.postForEntity("/bills/persons", entity, Person::class.java)

        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.CREATED)
        assertEquals(result.body?.name, entity?.name)
    }

    @Test
    fun should_put_with_success() {
        val toUpdate = entity?.copy(name = "Rodolfo")
        testRestTemplate.put("$url/${toUpdate?.id}", toUpdate)

        val result = testRestTemplate.getForEntity("$url/${toUpdate?.id}", Person::class.java)

        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.OK)
        assertEquals(result.body?.name, toUpdate?.name)
    }

    @Test
    fun delete() {
        testRestTemplate.delete("$url/${entity?.id}")

        val result = testRestTemplate.getForEntity("$url/${entity?.id}", Person::class.java)

        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.NOT_FOUND)
    }
}