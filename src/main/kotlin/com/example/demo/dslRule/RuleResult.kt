package com.example.demo.dslRule

/**
 * 대수타입 (ADT)
 * - 곱타입: 동시에 여러 타입이 충족됨. 여러 인터페이스를 상속한 클래스
 * - 합타입(대수타입): 여러 타입이 하나의 타입으로 귀결됨. Enum, Sealed Class
 * - 대수타입 연산: 대수타입을 받아 대수타입을 반환
 */
sealed interface RuleResult {
    companion object {
        private const val defaultMsg = "invalid"

        // 편의함수
        fun <T: Any> value(v: T): RuleResult = Value(v)
        fun fail(msg: String?): RuleResult = Fail(msg ?: defaultMsg)
    }
    data class Value<T:Any>(val value: T): RuleResult
    data class Fail(val msg: String): RuleResult
}