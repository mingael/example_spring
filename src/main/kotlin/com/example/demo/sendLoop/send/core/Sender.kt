package com.example.demo.sendLoop.send.core

import com.example.demo.sendLoop.Item

interface Sender {

    fun send(item: Item)
}