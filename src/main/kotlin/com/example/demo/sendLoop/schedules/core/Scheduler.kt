package com.example.demo.sendLoop.schedules.core

import com.example.demo.sendLoop.Item
import com.example.demo.sendLoop.send.core.Sender
import kotlinx.datetime.Instant

abstract class Scheduler {

    private val senders = hashSetOf<Sender>()

    fun addSender(sender: Sender) {
        senders+= sender
    }
    fun send(item: Item, now: Instant) {
        if(!isSend(now)) {
            senders.forEach {
                it.send(item)
            }
        }
    }

    protected abstract fun isSend(now: Instant): Boolean
}