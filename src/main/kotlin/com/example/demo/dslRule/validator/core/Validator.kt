package com.example.demo.dslRule.validator.core

/**
 * Result: Kotlin 에서 제공하는 계수타입
 */
interface Validator {
    fun <T: Any> check(v: Any): Result<T>
}