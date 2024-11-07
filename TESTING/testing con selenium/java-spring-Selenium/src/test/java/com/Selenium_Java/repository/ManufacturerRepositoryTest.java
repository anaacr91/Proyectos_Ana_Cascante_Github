package com.Selenium_Java.repository;
import com.Selenium_Java.Main;
import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.dto.ManufacturerWithProductDataDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional//-> anotacion que se encarga de hacer rollback de las transacciones->test no afectan a la bbdd
@DataJpaTest//anotacion para pruebas de repositorios

class ManufacturerRepositoryTest {

    @Autowired//crea el repo automaticamente
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByYear() {
        Manufacturer manufacturer1 = Manufacturer.builder().name("Fabricante A").year(2020)
                .build();
        Manufacturer manufacturer2 = Manufacturer.builder().name("Fabricante B").year(2021)
                .build();
        Manufacturer manufacturer3 = Manufacturer.builder().name("Fabricante C").year(2020)
                .build();

        manufacturerRepository.saveAll(List.of(manufacturer1, manufacturer2, manufacturer3));
        //guardar Fabricantes en bbdd->mock repositorio

        //metodo a testear
        List<Manufacturer> manufacturers2020 = manufacturerRepository.findByYear(2020);
        //verificar resultado
        assertEquals(2, manufacturers2020.size(), "Deberia haber 2 del 2020");
        assertTrue(manufacturers2020.contains(manufacturer1));
        assertTrue(manufacturers2020.contains(manufacturer3));
    }

    @Test
    @DisplayName("Encontrar nombre ignorando mayusculas")
    void findByNameIgnoreCase() {
        Manufacturer manufacturer1 = Manufacturer.builder().name("Fabricante A").year(2020)
                .build();
        Manufacturer manufacturer2 = Manufacturer.builder().name("Fabricante B").year(2021)
                .build();
        Manufacturer manufacturer3 = Manufacturer.builder().name("Fabricante A").year(2020)
                .build();

        manufacturerRepository.saveAll(List.of(manufacturer1, manufacturer2, manufacturer3));
        //guardar Fabricantes en bbdd->mock repositorio

        //metodo a testear
        List <Manufacturer> manufacturer = manufacturerRepository.findByNameIgnoreCase("fabricante a");
        //verificar resultado; comparar el repositorio con la BBDD
        assertEquals(2, manufacturer.size(), "nº repeticiones nombre: fabricante a");
        assertNotNull(manufacturer);
        assertTrue(manufacturer.contains(manufacturer1));
        assertTrue(manufacturer.contains(manufacturer3));
        assertEquals(manufacturer1, manufacturer.get(0));
    }

    @Test
    void existsByName() {
        Manufacturer manufacturer1 = Manufacturer.builder().name("Existente").year(2020)
                .build();

        manufacturerRepository.save(manufacturer1);
        //guardar Fabricantes en bbdd->mock repositorio

        //metodo a testear
        boolean exists = manufacturerRepository.existsByName("Existente");
        //verificar resultado; comparar datos repositorio con bdd
        assertTrue(exists, "Existente esta en la bbdd");

        assertFalse(manufacturerRepository.existsByName("No existe"), "No existe en la bbdd");
    }

    @Test
    @DisplayName("Prueba de la consulta findByName con @Query")
    void findByName() {
        Manufacturer manufacturer1 = Manufacturer.builder().name("Fabricante A").year(2020)
                .build();
        Manufacturer manufacturer2 = Manufacturer.builder().name("Fabricante B").year(2021)
                .build();
        Manufacturer manufacturer3 = Manufacturer.builder().name("Fabricante A").year(2020)
                .build();

        manufacturerRepository.saveAll(List.of(manufacturer1, manufacturer2, manufacturer3));
        //guardar Fabricantes en bbdd->mock repositorio

        //metodo del repositorio a testear
        List<Manufacturer> manufacturers = manufacturerRepository.findByName("Fabricante A");
        //verificar resultado; comparar datos repositorio con bdd
        assertEquals(2, manufacturers.size(), "nº repeticiones nombre: 'fabricante A' ");
        assertNotNull(manufacturers);
    }
    @Test
    @DisplayName("Prueba de findAllWithCalculatedProductsStats con @Query")
    void findAllWithCalculatedProductsSTats() {
        Manufacturer manufacturer1 = Manufacturer.builder().name("Fabricante A").year(2020)
                .imageUrl("https://example.com/images1.png/").build();
        Manufacturer manufacturer2 = Manufacturer.builder().name("Fabricante B").year(2021)
                .imageUrl("https://example.com/images2.png/").build();
        Manufacturer manufacturer3 = Manufacturer.builder().name("Fabricante C").year(2020)
                .imageUrl("https://example.com/images3.png/").build();

        manufacturerRepository.saveAll(List.of(manufacturer1, manufacturer2, manufacturer3));
        //guardar Fabricantes en bbdd->mock repositorio

        Product product1 = Product.builder().name("Producto 1").price(50.0)
                .quantity(10).active(true).manufacturer(manufacturer1).build();
        Product product2 = Product.builder().name("Producto 2").price(75.0)
                .quantity(5).active(true).manufacturer(manufacturer2).build();
        Product product3 = Product.builder().name("Producto 3").price(25.0)
                .quantity(6).active(true).manufacturer(manufacturer3).build();
        productRepository.saveAll(List.of(product1, product2, product3));
        //metodo del repositorio a testear
        List<ManufacturerWithProductDataDTO> manufacturersDTO =
                manufacturerRepository.findAllWithCalculatedProductsStats();
        //verificar resultado; comparar datos repositorio con bdd
        assertEquals(3, manufacturersDTO.size(), "nº de fabricantes");
        assertNotNull(manufacturersDTO);
        // Obtener el primer elemento de la lista
        ManufacturerWithProductDataDTO dto = manufacturersDTO.get(0);
        // Verificar que el ID del fabricante coincide con el esperado
        assertEquals(manufacturer1.getId(), dto.manufacturerId(),
                "El ID del fabricante debería coincidir con el ID creado");
    }
}