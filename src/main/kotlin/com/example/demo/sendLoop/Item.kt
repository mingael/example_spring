package com.example.demo.sendLoop

import com.example.demo.sendLoop.schedules.core.Scheduler
import kotlinx.datetime.Instant

class Item(var title: String, var content: String) {
    private val schedules = hashSetOf<Scheduler>()

    fun addSchedule(vararg scheduler: Scheduler) {
        schedules+= scheduler
    }
    fun send(now: Instant) {
        schedules.forEach {
            it.send(this, now)
        }
    }
}