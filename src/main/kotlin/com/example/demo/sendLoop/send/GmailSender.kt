package com.example.demo.sendLoop.send

import com.example.demo.sendLoop.Item
import com.example.demo.sendLoop.send.core.Sender

class GmailSender(user: String, pw: String, sender: String, receiver: String): Sender {

    override fun send(item: Item) {
        println("GmailSender")
    }
}