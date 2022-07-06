package com.example.demo.sendLoop

import com.example.demo.sendLoop.schedules.Once
import com.example.demo.sendLoop.schedules.RepeatDay
import com.example.demo.sendLoop.schedules.core.Scheduler
import com.example.demo.sendLoop.send.GmailSender
import com.example.demo.sendLoop.send.PrintSender
import kotlinx.datetime.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SendLoopApplicationText {

    val threadLooper = Looper({
        val thread = Thread {
            while(it.isRunning && !Thread.currentThread().isInterrupted) {
                val now = Clock.System.now()
                Looper.users.forEach {
                    it.send(now)
                    Thread.sleep(1000)
                }
            }
        }
        if(!thread.isAlive) {
            thread.start()
        }
    }, {})

    fun reminder(username: String) {
        threadLooper.start()
        val user = User(username)
        do {
            println("add Item")
            val item = Item(read("title:"), read("content:"))
            readN("add Schedules", "1.once, 2.repeatDay", "1", "2") {
                val scheduler = getScheduler(it)
                setSender(scheduler)
                item.addSchedule(scheduler)
            }
            user.addItem(item)
        } while(true)
        while(threadLooper.isRunning) {}
    }

    fun read(msg: String, vararg answer: String, vali:((String)->Boolean)? = null): String {
        do {
            println(msg)
            val line = readLine()
            if(line != null && line.isNotBlank()) {
                if(answer.isEmpty() || answer.any { line == it }) {
                    if(vali == null || vali(line)) {
                        return line
                    }
                }
            }
        } while(true)
    }
    fun readN(title: String, msg: String, vararg vali: String, block: (String)->Unit) {
        var itemMsg = msg
        var itemVali = vali
        do {
            println(title)
            val line = read(itemMsg, *itemVali)
            if(line == "0") break
            block(line)
            itemMsg = "0.noMore " + itemMsg
            itemVali = itemVali.toMutableList().also{it+= "0"}.toTypedArray()
        } while(true)
    }

    fun getScheduler(type: String): Scheduler {
        return when(type) {
            "1"-> getOnce()
            "2"-> getRepeat()
            else-> throw Throwable()
        }
    }

    fun setSender(scheduler: Scheduler) {
        readN("add Sender", "1.print 2.gmail", "1", "2") {
            scheduler.addSender(when(it) {
                "1" -> PrintSender()
                "2" -> GmailSender(
                    read("user:"),
                    read("pw:"),
                    read("sender:"),
                    read("receiver:")
                )
                else -> throw Throwable()
            })
        }
    }

    fun getOnce(): Scheduler {
        println("once from now")
        println("unit:")
        val unit: DateTimeUnit.TimeBased = when(read("1.hours 2.minutes 3.seconds", "1", "2", "3")) {
            "1" -> DateTimeUnit.HOUR
            "2" -> DateTimeUnit.MINUTE
            "3" -> DateTimeUnit.SECOND
            else -> throw Throwable("")
        }
        val count = read("count(int):") {
            it.toIntOrNull() != null
        }.toInt()

        return Once(Clock.System.now().plus(count, unit))
    }

    fun getRepeat(): Scheduler {
        println("repeat day")

        val time = read("time(hh:mm):") {
            """^\d{2}:\d{2}$""".toRegex().matches(it)
        }.split(':').map { it.toInt() }

        return RepeatDay(time[0], time[1], *read("days(mon,tue,wed,thu,fri,sat,sun):") {
            it.split(',').all { el -> "mon,tue,wed,thu,fri,sat,sun".indexOf(el.trim()) != -1 }
        }.split(',').map {
            when(it) {
                "mon" -> DayOfWeek.MONDAY
                "tue" -> DayOfWeek.TUESDAY
                "wed" -> DayOfWeek.WEDNESDAY
                "thu" -> DayOfWeek.THURSDAY
                "fri" -> DayOfWeek.FRIDAY
                "sat" -> DayOfWeek.SATURDAY
                "sun" -> DayOfWeek.SUNDAY
                else -> throw Throwable()
            }
        }.toTypedArray())
    }
}