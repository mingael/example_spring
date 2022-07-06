package com.example.demo.dslRule

import com.example.demo.dslRule.rules.AddRules
import com.example.demo.dslRule.rules.length
import com.example.demo.dslRule.rules.trim
import com.example.demo.dslRule.validator.RuleValidator
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DslRuleApplication

fun main(args: Array<String>) {
    val vali = RuleValidator {
        case {
            trim()
            length(5, "not length 5")
        }
    }
    vali.check<String>(" abcde ")
        .onSuccess {
            println("ok $it")
        }
        .onFailure {
            println("fail ${it.message}")
        }
    /*
    vali.check<Int>(3).getOrNull()?.let {
        println("ok")
    }
    vali.check<Int>(4).onFailure {
        println("fail ${it.message}")
    }
    */

    RuleDSL {
        case {
            // AddRules의 어휘 공간
        }
        case {
            AddRules {
                equal(3, "not 3")
            }
        }
        case {
            AddRules {
                length(4, "LOVE")
            }
        }
    }
}