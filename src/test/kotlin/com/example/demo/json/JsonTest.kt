package com.example.demo.json

import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class JsonTest {

    @Test
    fun jsonBuilderTest() {
        val builder = JsonBuilder()
        val json = JsonData(21, "love", listOf("A"))
        println(builder.stringify(json))
    }

}