package com.example.demo.dslRule.rules

import com.example.demo.dslRule.RuleResult
import com.example.demo.dslRule.rules.core.Rule

class Length(private val length: Int, private val msg: String?): Rule {
    override fun check(target: RuleResult): RuleResult {
        return if(
            target is RuleResult.Value<*> &&
            target.value is String &&
            target.value.length == length) target
        else RuleResult.fail(msg)
    }
}

fun AddRules.length(length: Int, msg: String?) {
    this += Length(length, msg)
}