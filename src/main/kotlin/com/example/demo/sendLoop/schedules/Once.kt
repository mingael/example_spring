package com.example.demo.sendLoop.schedules

import com.example.demo.sendLoop.schedules.core.Scheduler
import kotlinx.datetime.Instant

class Once(private val at: Instant): Scheduler() {
    private var isSent = false
    override fun isSend(now: Instant): Boolean {
        return if(!isSent && at <= now) {
            isSent = true
            false
        } else true
    }
}