package com.example.demo.crud.application

import com.example.demo.crud.domain.Customer
import com.example.demo.crud.infra.CustomerRepository
import com.example.demo.crud.ui.CustomerRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomerService @Autowired constructor (val customerRepository: CustomerRepository) {

    fun save(request: CustomerRequest): Long? {
        return customerRepository.save(request.toEntity()).id
    }

    fun findCustomer(id: Long): Customer {
        return customerRepository.findById(id).get()
    }

    fun findCustomerList(): List<Customer> {
        return customerRepository.findAll()
    }

    fun update(id: Long, request: CustomerRequest): Long? {
        val customer = customerRepository.findById(id).get()
        customer.nickname = request.nickname
        customer.age = request.age
        customerRepository.save(customer)
        return customer.id
    }

    fun delete(id: Long) {
        customerRepository.deleteById(id)
    }
}