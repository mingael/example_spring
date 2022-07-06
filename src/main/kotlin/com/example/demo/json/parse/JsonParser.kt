package com.example.demo.json.parse

import com.example.demo.json.anno.Name
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KType
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.createType
import kotlin.reflect.full.findAnnotation

class JsonParser {

    fun jsonValue(lexer: JsonLexer, type: KType): Any? {
        return when(val cls = type.classifier as? KClass<*> ?: return null) {
            String::class-> lexer.string()
            Int::class-> lexer.int()
            Long::class-> lexer.long()
            Float::class-> lexer.float()
            Double::class-> lexer.double()
            Boolean::class-> lexer.boolean()
            List::class-> parseList(lexer, type.arguments[0].type?.classifier as? KClass<*> ?: return null)
            else-> parseObject(lexer, cls.createInstance())
        }
    }

    fun <T:Any> parseObject(lexer: JsonLexer, target: T): T? {
        if(!lexer.isOpenObject()) return null

        lexer.next()
        val props = target::class.members
            .filterIsInstance<KMutableProperty<*>>()
            .associate {
                (it.findAnnotation<Name>()?.name ?: it.name) to it
            }

        while(!lexer.isCloseObject()) {
            lexer.skipWhite()
            val key = lexer.key() ?: return null
            val prop = props[key] ?: return null
            val value = jsonValue(lexer, prop.returnType) ?: return null
            prop.setter.call(target, value)
            lexer.skipWhite()
            if(lexer.isComma()) {
                lexer.next()
            }
        }
        lexer.next()

        return target
    }

    fun parseList(lexer: JsonLexer, cls: KClass<*>): List<*>? {
        if(!lexer.isOpenArray()) return null

        lexer.next()
        val result = mutableListOf<Any>()
        val cls2 = cls.createType()
        while(!lexer.isCloseArray()) {
            lexer.skipWhite()
            val v = jsonValue(lexer, cls2) ?: return null
            result += v
            lexer.skipWhite()
            if(lexer.isComma()) lexer.next()
        }
        lexer.next()

        return result
    }
}