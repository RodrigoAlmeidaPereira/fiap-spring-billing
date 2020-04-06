package br.com.fiap.billing.api.transaction.web

import br.com.fiap.billing.api.person.Person
import br.com.fiap.billing.api.person.PersonService
import br.com.fiap.billing.api.transaction.Transaction
import br.com.fiap.billing.api.transaction.TransactionService
import com.fasterxml.jackson.annotation.JsonFormat
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
import java.time.LocalDate
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = ["spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=FALSE"])
internal class TransactionRestServiceTest {

    @Autowired
    lateinit var  service : TransactionService
    @Autowired
    lateinit var personService : PersonService
    @Autowired
    lateinit var mockMvc: MockMvc

    private val person: Person = Person(id = 1, name = "Rodrigo", enrollment = "1234", doc = "4321", active = true)
    private val entity : Transaction = Transaction(1, person, LocalDate.now(), 425.5, 3)
    private val psotRequest : PostRequest = PostRequest( person,"2019-01-12", 425.5, 3)


    @Test
    fun findByPersonDocSuccess() {
        personService.create(person)
        service.create(entity)

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/bills/transactions/extract/4321")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty)
    }

    @Test
    fun findAllWithSuccess() {
        personService.create(person)
        service.create(entity)

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/bills/transactions")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty)
    }

    @Test
    fun should_create_with_success(){
        mockMvc.perform(MockMvcRequestBuilders
                .post("/bills/transactions")
                .content(asJsonString(psotRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
    }


    @Test
    fun should_simulate_with_success(){
        mockMvc.perform(MockMvcRequestBuilders
                .post("/bills/transactions/simulate-data")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
    }

    @Test
    fun should_delete_with_success(){
        personService.create(person)
        service.create(entity)
        mockMvc.perform( MockMvcRequestBuilders.delete("/bills/transactions/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful);
    }

    fun asJsonString(obj: Any?): String {
        return try {
            ObjectMapper().writer()
                    .withDefaultPrettyPrinter()
                    .writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    data class PostRequest (
            var person : Person = Person(),

            @get: NotNull
            var transactionDate: String = "",

            @get: NotNull
            var transactionValue: Double = 0.0,

            @get: NotNull
            var installments: Int = 1,

            var cancelled : Boolean = false
    )

}