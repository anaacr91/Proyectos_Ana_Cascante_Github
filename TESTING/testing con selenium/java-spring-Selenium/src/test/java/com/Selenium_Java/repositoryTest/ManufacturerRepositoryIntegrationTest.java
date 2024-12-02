package com.Selenium_Java.repositoryTest;
import com.Selenium_Java.dto.ManufacturerWithAddressDTO;
import com.Selenium_Java.model.Address;
import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.model.Product;
import com.Selenium_Java.dto.ManufacturerWithProductDataDTO;

import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional//-> anotacion que se encarga de hacer rollback de las transacciones->test no afectan a la bbdd
@DataJpaTest//anotacion para pruebas de repositorios

class ManufacturerRepositoryIntegrationTest {

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
                .quantity(5).active(true).manufacturer(manufacturer1).build();
        Product product3 = Product.builder().name("Producto 3").price(25.0)
                .quantity(6).active(true).manufacturer(manufacturer1).build();
        productRepository.saveAll(List.of(product1, product2, product3));
        //metodo del repositorio a testear
        List<ManufacturerWithProductDataDTO> manufacturersDTO =
                manufacturerRepository.findAllWithCalculatedProductsStats();
        //verificar resultado; comparar datos repositorio con bdd
        assertEquals(3, manufacturersDTO.size(), "nº de fabricantes");
        assertNotNull(manufacturersDTO);
        // Obtener el primer elemento de la lista
        ManufacturerWithProductDataDTO dto = manufacturersDTO.get(0);//obtiene manufacturer1
        // Verificar que el ID del fabricante coincide con el esperado
        assertEquals(manufacturer1.getId(), dto.manufacturerId(),
                "El ID del fabricante debería coincidir con el ID creado");
        assertEquals(manufacturer1.getName(), dto.manufacturerName(),
                "El nombre del fabricante debería coincidir con el nombre creado");
        assertEquals(3,dto.productsCount(), "El nº de productos debería ser 3");//cuenta sus productos
        assertEquals(150.0,dto.productsSumTotalPrice(), "La suma total de los productos debería ser 150.0");
    }
    @Test
    @DisplayName("test metodo findByAddress_Id")
    void findByAddress_Id() {
        Address address = Address.builder().street("corsega").city("barcelona")
                .zipCode("08037").build();
        Manufacturer manufacturer = Manufacturer.builder().name("Bodegas").address(address)
                .build();
        //preparar datos de prueba: Address y Manufacturer Asociados
        address.setManufacturer(manufacturer);
        //asociar address con manufacturer(relacion bidireccional)
        manufacturerRepository.save(manufacturer);
        //guarda manufacturer y cascadeara (guardara tambien) la adress
        //guardar Fabricantes en bbdd->mock repositorio

        //metodo del repositorio a testear
        Manufacturer foundManufacturer = manufacturerRepository.findByAddress_Id(address.getId());
        //verificar resultado; comparar datos repositorio con bdd
        assertNotNull(foundManufacturer, "El fabricante debería existir");
        assertEquals(manufacturer.getId(), foundManufacturer.getId(), "El ID del fabricante debería coincidir");
        assertEquals(address.getId(), foundManufacturer.getAddress().getId(), "El ID de la dirección debería coincidir");
    }

    @Test
    @DisplayName("test metodo countByAddress_ZipCode")
    void countByAddress_ZipCode() {
        Address address1 = Address.builder().zipCode("28012").state("Madrid").street("Gran Via").city("Madrid").build();
        Address address2 = Address.builder().zipCode("28012").state("Madrid").street("Puerta del Sol").city("Madrid").build();
        Address address3 = Address.builder().zipCode("08037").state("Barcelona").street("Carrer de la Diputació").city("Barcelona").build();

        Manufacturer manufacturer1 = Manufacturer.builder().name("Bodegas").address(address1)
                .build();
        Manufacturer manufacturer2 = Manufacturer.builder().name("Libros").address(address2)
                .build();
        Manufacturer manufacturer3 = Manufacturer.builder().name("Peliculas").address(address3)
                .build();
        //asociar address con manufacturer(relacion bidireccional)
        address1.setManufacturer(manufacturer1);
        address2.setManufacturer(manufacturer2);
        address3.setManufacturer(manufacturer3);

        //guardar manufacturers//->mock repositorio
        manufacturerRepository.saveAll(List.of(manufacturer1, manufacturer2, manufacturer3));

        //ejecutar metodo del repositorio a testear y guardar en una variable para la verificacion de despues
        long countZipCode28012 = manufacturerRepository.countByAddress_ZipCode("28012");
        long countZipCode08037 = manufacturerRepository.countByAddress_ZipCode("08037");

        //verificar resultado
        assertEquals(2, countZipCode28012, "El nº de fabricantes con zipCode 28012 debería ser 2");
        assertEquals(1, countZipCode08037, "El nº de fabricantes con zipCode 08037 debería ser 1");

    }
    @Test
    @DisplayName("test metodo countByAddress_Street")
    void findManufacturersInCityWithProductStats(){
        Address address1 = Address.builder().street("Calle Alfonso").city("Zaragoza").state("Aragón").zipCode("50001").build();

        Address address2 = Address.builder().street("Calle Amantes").city("Teruel").state("Aragón").zipCode("44001").build();

        Manufacturer manufacturer1 = Manufacturer.builder().name("Muebles Zaragoza").address(address1).build();

        Manufacturer manufacturer2 = Manufacturer.builder().name("Cerámica Teruel").address(address2).build();

        manufacturerRepository.saveAll(List.of(manufacturer1, manufacturer2));

        Product product1 = Product.builder().name("Mesa de comedor").price(150.0)
                .quantity(10).active(true).manufacturer(manufacturer1).build();
        Product product2 = Product.builder().name("Silla de madera").price(75.0)
                .quantity(5).active(true).manufacturer(manufacturer1).build();
        Product product3 = Product.builder().name("Jarrón de cerámica").price(60.0)
                .quantity(6).active(true).manufacturer(manufacturer2).build();

        productRepository.saveAll(List.of(product1, product2, product3));
        //testear metodo-> realizar consulta
        List<ManufacturerWithAddressDTO> resultZaragoza = manufacturerRepository.findManufacturerInCityWithProductStats("Zaragoza");
        //verificar resultados que igualen; repo que iguale los datos de la bbdd
        assertEquals(1,resultZaragoza.size(), "El nº de fabricantes en Zaragoza debería ser 1");
        ManufacturerWithAddressDTO dtoZaragoza = resultZaragoza.get(0);
        assertEquals( manufacturer1.getId(), dtoZaragoza.manufacturerId(), "Deberian coincidir los ids");
        assertEquals("Muebles Zaragoza", dtoZaragoza.manufacturerName(), "Debería coincidir el nombre");
        assertEquals("Zaragoza", dtoZaragoza.city(), "Debería coincidir la ciudad");
        assertEquals(2L, (long)  dtoZaragoza.productCount(), "La cuenta de productos debería ser 2");
        //2L long
        assertEquals(225.0, dtoZaragoza.totalProductPrice(), "El precio total de todos los productos debería ser 225.0");
    }
}