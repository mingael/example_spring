package com.example.demo.crud.domain

import javax.persistence.*

@Entity
class Customer (
    @Column(length = 30, nullable = false)
    var nickname: String,

    @Column
    var age: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {}