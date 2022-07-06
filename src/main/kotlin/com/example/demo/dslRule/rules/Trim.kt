package com.example.demo.dslRule.rules

import com.example.demo.dslRule.RuleResult
import com.example.demo.dslRule.rules.core.Rule

class Trim(private val msg: String?): Rule {
    override fun check(target: RuleResult): RuleResult {
        return if(
            target is RuleResult.Value<*> &&
            target.value is String
        ) RuleResult.value(target.value.trim())
        else RuleResult.fail(msg)
    }
}

fun AddRules.trim(msg: String? = null) {
    this += Trim(msg)
}