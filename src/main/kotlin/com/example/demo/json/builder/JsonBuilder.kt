package com.example.demo.json.builder

import kotlin.reflect.KProperty

class JsonBuilder {
    fun stringify(value: Any) = StringBuilder().run {
        jsonValue(value)
        toString()
    }

    private fun StringBuilder.jsonValue(value: Any?) {
        when(value) {
            null -> null
            is String -> jsonString(value)
            is Boolean, is Number -> append("$value")
            is List<*> -> jsonList(value)
            else -> jsonObject(value)
        }
    }

    private fun StringBuilder.jsonString(str: String) = append(""""${str.replace("\"", "\\\"",)}"""")

    private fun StringBuilder.jsonObject(target: Any) {
        wrap('{', '}') {
            target::class.members.filterIsInstance<KProperty<*>>().joinTo({comma()}) {
                jsonValue(it.name)
                append(':')
                jsonValue(it.getter.call(target))
            }
        }
    }

    private fun StringBuilder.jsonList(target: List<*>) {
        wrap('{', '}') {
            target.joinTo({comma()}) {
                jsonValue(it)
            }
        }
    }

    private fun StringBuilder.wrap(begin: Char, end: Char, block: StringBuilder.()->Unit) {
        append(begin)
        block()
        append(end)
    }

    private fun <T> Iterable<T>.joinTo(sep: ()->Unit, transform: (T)->Unit) {
        forEachIndexed { count, element ->
            if(count != 0) sep()
            transform(element)
        }
    }

    private fun StringBuilder.comma() {
        append(',')
    }
}