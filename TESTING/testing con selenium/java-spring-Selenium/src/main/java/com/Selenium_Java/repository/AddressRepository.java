package com.Selenium_Java.repository;

import com.Selenium_Java.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
  //Entidad adress, long id
}