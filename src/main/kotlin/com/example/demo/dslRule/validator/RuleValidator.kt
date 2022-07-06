package com.example.demo.dslRule.validator

import com.example.demo.dslRule.RuleDSL
import com.example.demo.dslRule.RuleResult
import com.example.demo.dslRule.validator.core.Validator

class RuleValidator(block: RuleDSL.()->Unit): Validator {
    private val ruleDSL: RuleDSL by lazy {
        RuleDSL(block)
    }

    override fun <T: Any> check(v: Any): Result<T> {
        return when(val result = ruleDSL.check(v)) {
            is RuleResult.Value<*> -> (result.value as? T)?.let {
                Result.success(it)
            } ?: Result.failure(Throwable("invalid Type: ${result.value}"))
            is RuleResult.Fail -> Result.failure(Throwable(result.msg))
        }
    }
}