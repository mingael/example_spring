package com.example.demo.dslRule

import com.example.demo.dslRule.rules.AddRules

/**
 * DSL Core 객체
 * - 다수의 케이스를 제공 (케이스 생성하는 어휘만 제공)
 */
class RuleDSL(block: RuleDSL.()->Unit) {
    //private val cases = mutableSetOf<Set<Rule>>()
    private val cases = mutableSetOf<AddRules>()

    fun case(block: AddRules.()->Unit) {
        cases += AddRules(block)
    }

    fun check(v: Any): RuleResult {
        var result = RuleResult.value(v)    // nullable 할 수 없어서

        // any: 하나라도 해당하면 true
        cases.any {
            result = RuleResult.value(v)    // 룰 사용값 초기화
            it.all { rule ->
                result = rule.check(result)
                when(result) {
                    is RuleResult.Value<*> -> true
                    is RuleResult.Fail -> false
                }
            }
        }
        return result
    }

    init {
        block()
    }
}