package com.Selenium_Java.repository;

import com.Selenium_Java.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//clases que heredan métodos de jpa repository, no necesitan implementar los metodos
//spring data jpa se encarga de implementar los metodos
//interfaz de spring-> interactuar bbdd en lugar de lenguaje sql
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    //metodos derivados
    List<Manufacturer> findByYear(Integer year);

    List<Manufacturer> findByNameIgnoreCase(String name);


}

