package br.com.fiap.billing.api.person.web

import br.com.fiap.billing.api.core.exception.ResourceNotFoundException
import br.com.fiap.billing.api.person.Person
import br.com.fiap.billing.api.person.PersonService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@Api("API para Pessoas")
@RequestMapping("/bills/persons")
class PersonRestService(private val service: PersonService) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Busca o registro da pessoa com o id informado")
    fun get(@PathVariable id: Long): Person {
        val entity = service.getById(id)

        if (!entity.active) {
            throw ResourceNotFoundException("Resource with id $id was not found.")
        }

        return entity;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Busca todas as pessoas cadastradas")
    fun findAll(): List<Person> {
        return service.findAll()
                .filter { it -> it.active }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Cadastra um novo registro de pessoa")
    fun ṕost(@RequestBody person: Person): Person {
        return service.create(person)
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza o registro de pessoa com o id informado")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun put(@PathVariable id: Long,
            @RequestBody person: Person): Person {
        return service.update(id, person)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Remove o registro de pessoaa com o id informado")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }

}