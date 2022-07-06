package com.example.demo.dslRule.rules

import com.example.demo.dslRule.RuleResult
import com.example.demo.dslRule.rules.core.Rule

/**
 * 인터페이스 델리게이션
 * - 인터페이스 선언시 by를 붙여 인스턴스를 넘기면
 *   인터페이스의 메소드를 모두 해당 인스턴스가 처리하게 자동으로 코드를 생성함
 *
 * 내부 클래스
 * - 생성하려면 반드시 외부 클래스의 인스턴스를 참조해야 함
 * - this@외부클래스 형식을 통해 외부 인스터스를 인식할 수 있음
 */
class AddRules(block: AddRules.()->Unit): MutableSet<Rule> by mutableSetOf() {
    init {
        block(this)
    }

    // 어휘를 내부클래스 (라이브러리 코드)로 구현
    inner class equal(private val base: Any, private val msg: String?): Rule {
        override fun check(target: RuleResult): RuleResult {
            return if(target is RuleResult.Value<*> && base == target.value) target
                   else RuleResult.fail(msg)
        }

        init {
            this@AddRules += this
        }
    }

    // 어휘를 확장함수로 구현
    // rules.Length
}