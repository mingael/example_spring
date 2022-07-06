package com.example.demo.json.parse

class JsonLexer(val json: String) {
    val last = json.lastIndex
    var cursor = 0
        private set
    val curr get() = json[cursor]

    fun next() {
        if(cursor < last) {
            ++cursor
        }
    }
    fun skipWhite() {
        while(" \t\n\r".indexOf(curr) != -1 && cursor < last) {
            next()
        }
    }

    fun isComma() = curr == ','
    fun isOpenObject(): Boolean = '{' == curr
    fun isCloseObject(): Boolean = '}' == curr
    fun isOpenArray(): Boolean = '[' == curr
    fun isCloseArray(): Boolean = ']' == curr

    fun key(): String? {
        val result = string() ?: return null
        skipWhite()
        if(curr != ':') return null
        next()
        skipWhite()
        return result
    }

    fun string(): String? {
        if(curr != '"') return null

        next()
        val start = cursor
        var isSkip = false
        while(isSkip || curr != '"') {
            isSkip = if(isSkip) false else curr == '\\'
            next()
        }

        val result = json.substring(start, cursor)
        next()
        return result
    }

    fun number(): String? {
        val start = cursor
        while("-.0123456789".indexOf(curr) != -1) {
            next()
        }

        return if(start == cursor) null else json.substring(start, cursor)
    }
    fun int() = number()?.toInt()
    fun long() = number()?.toLong()
    fun double() = number()?.toDouble()
    fun float() = number()?.toFloat()

    fun boolean(): Boolean? {
        return when {
            json.substring(cursor, cursor + 4) == "true"-> {
                cursor+= 4
                true
            }
            json.substring(cursor, cursor + 5) == "false"-> {
                cursor+= 5
                false
            }
            else-> null
        }
    }
}