package com.example.demo.crud
import com.example.demo.crud.domain.Customer
import com.fasterxml.jackson.databind.ObjectMapper
import kotlin.test.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest @Autowired constructor (val mvc: MockMvc, val mapper: ObjectMapper) {

    @Test
    fun crudTest() {
        var nickname = "TEST"
        var age = 10

        mvc.perform(
            get("/customer/save")
                .param("nickname", nickname)
                .param("age", age.toString()))
            .andExpect(status().isOk)

        mvc.perform(
            get("/customer/1"))
            .andExpect(status().isOk)

        nickname = "LOVE"
        age = 20
        val content = mapper.writeValueAsString(Customer(nickname, age))

        mvc.perform(
            put("/customer/{id}", 1)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)

        mvc.perform(get("/customer/list"))
            .andExpect(status().isOk)

        mvc.perform(delete("/customer/{id}", 1))
            .andExpect(status().isOk)
    }

}