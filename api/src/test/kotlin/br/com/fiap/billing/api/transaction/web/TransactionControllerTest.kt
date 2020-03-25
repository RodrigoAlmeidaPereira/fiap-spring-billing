package br.com.fiap.billing.api.transaction.web

import br.com.fiap.billing.api.person.Person
import br.com.fiap.billing.api.person.PersonService
import br.com.fiap.billing.api.transaction.Transaction
import br.com.fiap.billing.api.transaction.TransactionService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = ["spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=FALSE"])
internal class TransactionControllerTest {

    @Autowired
    lateinit var  service : TransactionService
    @Autowired
    lateinit var personService : PersonService
    @Autowired
    lateinit var mockMvc: MockMvc

    private val person: Person = Person(id = 1, name = "Rodrigo", enrollment = "1234", doc = "4321", active = true)
    private val entity : Transaction = Transaction(1, person, Date(), 425.5, 3)

    @Test
    fun findByPersonDocSuccess() {
        personService.create(person)
        service.create(entity)

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/transactions/extract/4321")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty)
    }

    @Test
    fun should_create_with_success(){
        mockMvc.perform(MockMvcRequestBuilders
                .post("/transactions")

                .content(asJsonString(entity))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
    }

    @Test
    fun should_delete_with_success(){
        personService.create(person)
        service.create(entity)
        mockMvc.perform( MockMvcRequestBuilders.delete("/transactions/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful);
    }

    fun asJsonString(obj: Any?): String {
        return try {
            ObjectMapper().writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}