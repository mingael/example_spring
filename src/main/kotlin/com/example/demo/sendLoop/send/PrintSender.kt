package com.example.demo.sendLoop.send

import com.example.demo.sendLoop.Item
import com.example.demo.sendLoop.send.core.Sender

class PrintSender: Sender {
    override fun send(item: Item) {
        println("Reminder")
        println("[${item.title}]")
        println(item.content)
    }
}