package com.example.demo.crud.ui

import com.example.demo.crud.application.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer")
class CustomerController @Autowired constructor(private val customerService: CustomerService)  {

    @GetMapping("/save")
    fun save(request: CustomerRequest): ResponseEntity<Any> {
        val save = customerService.save(request)
        return ResponseEntity.ok().body(save)
    }

    @GetMapping("/{id}")
    fun findCustomer(@PathVariable id: Long): ResponseEntity<Any> {
        val customer = customerService.findCustomer(id)
        return ResponseEntity.ok().body(customer)
    }

    @GetMapping("/list")
    fun findCustomerList(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(customerService.findCustomerList())
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: CustomerRequest): ResponseEntity<Any> {
        println("updatedddd")
        val customer = customerService.update(id, request)
        return ResponseEntity.ok().body(customer)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        customerService.delete(id)
        return ResponseEntity.ok().body(true)
    }
}