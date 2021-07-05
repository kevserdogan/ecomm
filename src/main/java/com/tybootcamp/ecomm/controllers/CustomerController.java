package com.tybootcamp.ecomm.controllers;

import com.tybootcamp.ecomm.entities.*;
import com.tybootcamp.ecomm.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @GetMapping(path = "/")
    public ResponseEntity<?> getCustomerById(@RequestParam(value = "id") long id)
    {
        try {
            Customer customer = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            System.out.println("The seller with id " + id + " = " + customer.toString());
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>("There isn't any seller with this name.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/")
    public ResponseEntity<Customer> addNewCustomer(@Valid @RequestBody Customer customer)
    {

        Customer customerEntity = new Customer(customer.getName());
        Profile profile = new Profile(customer.getProfile().getFirstName(), customer.getProfile().getLastName(), customer.getProfile().getGender(),customer);
        customerEntity.setProfile(profile);
        customerEntity.getProfile().setWebsite(customer.getProfile().getWebsite());
        customerEntity.getProfile().setAddress(customer.getProfile().getAddress());
        customerEntity.getProfile().setEmailAddress(customer.getProfile().getEmailAddress());
        customerEntity.getProfile().setBirthday(customer.getProfile().getBirthday());


        customerEntity = customerRepository.save(customerEntity);
        return new ResponseEntity<>(customerEntity, HttpStatus.OK);
    }

    @PutMapping(path = "/")
    public ResponseEntity<String> updateSeller(@Valid @RequestBody Customer customer)
    {
        Customer customerEntity = customerRepository.findById(customer.getId()).orElse(null);
        if (customerEntity == null)
        {
            return new ResponseEntity<>("This seller doesn't exists.", HttpStatus.NOT_FOUND);
        }

        customerEntity.getProfile().setFirstName(customer.getProfile().getFirstName());
        customerEntity.getProfile().setLastName(customer.getProfile().getLastName());
        customerEntity.getProfile().setWebsite(customer.getProfile().getWebsite());
        customerEntity.getProfile().setBirthday(customer.getProfile().getBirthday());
        customerEntity.getProfile().setAddress(customer.getProfile().getAddress());
        customerEntity.getProfile().setEmailAddress(customer.getProfile().getEmailAddress());
        customerEntity.getProfile().setGender(customer.getProfile().getGender());
        customerEntity = customerRepository.save(customerEntity);
        System.out.println("__________________________________________________________________");
        System.out.println("The row of " + customerEntity.toString() + " updated");
        return new ResponseEntity<>("The seller updated", HttpStatus.OK);
    }
}
