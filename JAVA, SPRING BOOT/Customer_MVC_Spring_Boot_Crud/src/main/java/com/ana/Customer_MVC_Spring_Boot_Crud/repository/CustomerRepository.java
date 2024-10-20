package com.ana.Customer_MVC_Spring_Boot_Crud.repository;

import com.ana.Customer_MVC_Spring_Boot_Crud.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
