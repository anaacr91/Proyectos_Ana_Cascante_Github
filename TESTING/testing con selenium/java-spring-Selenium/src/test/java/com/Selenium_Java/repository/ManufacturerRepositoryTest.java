package com.Selenium_Java.repository;

import com.Selenium_Java.model.Manufacturer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional//-> anotacion que se encarga de hacer rollback de las transacciones->test no afectan a la bbdd
@DataJpaTest//anotacion para pruebas de repositorios
class ManufacturerRepositoryTest {

    @Autowired//crea el repo automaticamente
    private ManufacturerRepository manufacturerRepository;

    @Test
    @DisplayName("Test Find By Year")
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
        //verificar resultado
        assertNotNull(manufacturer);
        assertEquals(2, manufacturer.size(), "tenemos 2 con el fabricante a");
        assertEquals(manufacturer1, manufacturer.get(0));
    }
}