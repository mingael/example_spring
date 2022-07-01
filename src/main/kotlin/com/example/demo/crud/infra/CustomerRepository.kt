package com.example.demo.crud.infra

import com.example.demo.crud.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, Long> {

}