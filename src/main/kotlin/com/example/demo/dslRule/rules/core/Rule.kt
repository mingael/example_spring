package com.example.demo.dslRule.rules.core

import com.example.demo.dslRule.RuleResult

/**
 * 1. trim 등 값의 변형도 허용
 * 2. 검사 결과 잘못된 경우 적절한 에러메세지를 얻을 수 있음
 * 3. 검사 조건을 여러 케이스로 나눠서 케이스 중 하나라도 일치하면 통과
 */
interface Rule {
    fun check(target: RuleResult): RuleResult
}