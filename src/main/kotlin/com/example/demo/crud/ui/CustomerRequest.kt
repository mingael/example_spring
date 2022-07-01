package com.example.demo.crud.ui

import com.example.demo.crud.domain.Customer

data class CustomerRequest(
    val nickname: String,
    val age: Int,
    val id: Long? = null
) {
    fun toEntity(): Customer = Customer(
        nickname = nickname,
        age = age,
        id = id
    )
}