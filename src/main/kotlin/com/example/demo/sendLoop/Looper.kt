package com.example.demo.sendLoop

class Looper(
 private val started:(Looper)->Unit,
 private val ended:(Looper)->Unit
) {
    companion object {
        val users = hashSetOf<User>()
    }

    var isRunning = false
        private set
    fun start() {
        isRunning = true
        started(this)
    }
    fun end() {
        isRunning = false
        ended(this)
    }

}