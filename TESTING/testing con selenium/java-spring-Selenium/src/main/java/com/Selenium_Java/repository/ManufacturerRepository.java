package com.Selenium_Java.repository;

import com.Selenium_Java.dto.ManufacturerWithProductDataDTO;
import com.Selenium_Java.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//clases que heredan métodos de jpa repository, no necesitan implementar los metodos
//spring data jpa se encarga de implementar los metodos
//interfaz de spring-> interactuar bbdd en lugar de lenguaje sql
//JPQL consultas
// https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    //metodos derivados
    List<Manufacturer> findByYear(Integer year);

    List<Manufacturer> findByNameIgnoreCase(String name);

    boolean existsByName(String name);

    @Query("select m from Manufacturer m where m.name = :name")//JPQL consulta en lugar de sql nativo
    List<Manufacturer> findByName(String name);

    @Query("SELECT new com.Selenium_Java.dto.ManufacturerWithProductDataDTO" +
            "(m.id, m.name, " +
            "COUNT (p), " +
            "SUM (p.price))" +
            " FROM Manufacturer m " +
            "LEFT JOIN Product p ON m.id = p.manufacturer.id " +//de dos tablas, quedate con la 1ª-> manufacturer
            "GROUP BY m.id, m.name")
    List<ManufacturerWithProductDataDTO> findAllWithCalculatedProductsStats();

    Manufacturer findByAddress_Id(Long id);

@Query("Select count(m) from Manufacturer m where m.address.zipCode = :zipCode")
//?1-> siguiente parametro de entrada
    long countByAddress_ZipCode(String zipCode);


}
