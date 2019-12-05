package com.trilogyed.customerservice.dao;

//import com.trilogyed.customerservice.dao.model.Customer;

import com.trilogyed.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
