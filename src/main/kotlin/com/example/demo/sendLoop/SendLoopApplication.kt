package com.example.demo.sendLoop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.Clock
import java.time.LocalDateTime


@SpringBootApplication
class SendLoopApplication

fun main(args: Array<String>) {
    runApplication<SendLoopApplication>(*args)
}

