package com.Selenium_Java.controller.IntegratedTest;

import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.repository.ManufacturerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/* TEST DE INTEGRACIÓN CON SPRING TEST (PARCIAL)
No es un test unitario
Es un test de integración parcial porque se hace mocks con @MockBean
Hacemos mock de la capa repositorio
Se integra la capa controlador y vista con el contexto Spring
*/

@SpringBootTest
@AutoConfigureMockMvc//configura mock mvc-> objeto que hace peticiones http controladas x spring (testear controladores)
//@WebMvcTest //mock solo
class ManufacturerControllerPITest {//semi integracion-> usa el controlador con spring en marcha

    @Autowired //inyectar objetos donde queramos, con constructor:-> autocablear, autoinicializar
    //con atributos:-> inyectar objetos en atributos
    private MockMvc mockMvc;//objeto que hace peticiones http controladas x spring (testear controladores)

    @MockBean //mock de la capa repositorio-> no cargar bbbdd
    private ManufacturerRepository manufacturerRepository;

    @Test
    void findAll() throws Exception {
        when(manufacturerRepository.findAll()).thenReturn(List.of(
                Manufacturer.builder().id(1L).name("MSI").build(),
                Manufacturer.builder().id(2L).name("ASUS").build()
        ));//cuando se llame al metodo findAll, entonces devolvemos lista de manufacturers

        //lanzar peticion http al controlador y verificar con expect
        // -> simulando 1 peticion =que cuando entras al navegador a la vista y actúa el controlador
        mockMvc.perform(MockMvcRequestBuilders.get("/manufacturers"))
        .andExpect(view().name("manufacturer-list"))
        .andExpect(model().attributeExists("manufacturers"))
        .andExpect(model().attribute("manufacturers", hasSize(2)))
        .andExpect(model().attribute("manufacturers", hasItem(
                allOf(//comprobar valores
                        hasProperty("id", is(1L)),
                        hasProperty("name", is("MSI"))
                )
        )));
    }

    @Test
    void findById() throws Exception{
        var manufacturer = Manufacturer.builder().id(1L).name("MSI").build();
        //creamos manufacturer
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturer));
        //cuando se llame al metodo findById, entonces devolvemos manufacturer

        mockMvc.perform(get("/manufacturers/{id}", 1L))
        //lanzar peticion http al controlador y verificar con expect
        .andExpect(status().isOk())//comprobar que la peticion ha ido bien
        .andExpect(view().name("manufacturer-detail"))
        //comprobar que la vista es manufacturer-detail
        .andExpect(model().attributeExists("manufacturer"))
        //comprobar que existe el atributo manufacturer
        .andExpect(model().attribute("manufacturer",
        allOf(
                hasProperty("id", is(1L)),
                hasProperty("name", is("MSI"))
        //comprobar que el atributo manufacturer tiene los valores esperados
        )));
        verify(manufacturerRepository).findById(1L);
    }


        @Test
        void getFormToUpdate_Empty() throws Exception {
            when(manufacturerRepository.findById(1L))
                    .thenReturn(Optional.empty());
            //cuando se llame al metodo findById, entonces devuelve optional vacio (ya que el fabricante no existe)

            mockMvc.perform(get("/manufacturers/update/{id}", 1L))
            //lanzar peticion http al controlador y verificar con expect
                    .andExpect(status().isBadRequest()); // 400 error not found
        }

    @Test
    @DisplayName("Testeamos el if del controlador del método save")
    void save_createNew() throws Exception {

        var manufacturer = Manufacturer.builder()
                .name("MSI").year(2024).build();
        //creamos manufacturer
        mockMvc.perform(post("/manufacturers")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        //contentype sirve para decirle al servidor que tipo de contenido estamos enviando
        //mediatype sirve para enviar datos en formato formulario, urlencoded sirve para enviar datos en la url
                .param("name", "MSI")
                .param("year", "2024"))
        //enviamos parametros
                .andExpect(status().is3xxRedirection())//comprobar que la peticion ha ido bien
                .andExpect(redirectedUrl("/manufacturers"));
        //comprobar que se ha redirigido a la url /manufacturers
        verify(manufacturerRepository).save(Mockito.any(Manufacturer.class));
        //verificar que se ha llamado al metodo save del repositorio con cualquier objeto manufacturer
        //importante poner Mockito.any porque sino se confunde por otro import
    }
    @Test
    @DisplayName("Testeamos el else del controlador del metodo save")
    void save_Update() throws Exception {

        var manufacturer = Manufacturer.builder()
                .id(1L)
                .name("MSI")
                .year(2024)
                .build();
        //creamos manufacturer
        when(manufacturerRepository.findById(1L)).thenReturn(
                Optional.of(manufacturer)
        );//cuando se llame al metodo findById, entonces devolvemos manufacturer

        mockMvc.perform(post("/manufacturers")
                //lanzar peticion http post al controlador, a la url /manufacturers
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("name", "MSI Modificado")
                        .param("year", "2025"))
                //enviamos parametros y verificamos con expect
                .andExpect(status().is3xxRedirection())//comprobar que la peticion ha ido bien
                .andExpect(redirectedUrl("/manufacturers"));
        //comprobar que se ha redirigido a la url /manufacturers

verify(manufacturerRepository).findById(1L);
//verificar que se ha llamado al metodo findById del repositorio con el id 1
verify(manufacturerRepository).save(manufacturer);
//verificar que se ha llamado al metodo save del repositorio con el objeto manufacturer
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(get("/manufacturers/delete/{id}", 1L))
    //perform-> realiza una solicitud HTTP simulada de tipo GET al endpoint /manufacturers/delete/{id}
    // con un valor de id igual a 1L
        .andExpect(status().is3xxRedirection())//comprobar que la peticion ha ido bien
                .andExpect(redirectedUrl("/manufacturers"));
        //comprobar que se ha redirigido a la url /manufacturers
        verify(manufacturerRepository).deleteById(1L);
        //verificar que se ha llamado al metodo deleteById del repositorio con el id 1
    }

    @Test
    @DisplayName("crear 1 nuevo fabricante que no existe en bbdd,-> con redireccion a Detail")
    void saveAndGoDetail_CreateNew() throws Exception {
    // cuando el metodo a testear no es void, y queremos caputurar el argumento
    //para modificarlo usamos, when(...).thenAnswer(...)
    when(manufacturerRepository.save(Mockito.any(Manufacturer.class)))
            .thenAnswer(invocation -> {//capturar el objeto manufacturer
                Manufacturer manufacturerToSave = invocation.getArgument(0);
                manufacturerToSave.setId(1L);// Retornar el objeto manufacturer modificado
                return manufacturerToSave;//retorno no haces nada con el objeto manufacturer
                //se puede devolver null o devolver el objeto
                //te hace devolver algo porque el metodo del controller Manufacturer no es void
            });
        mockMvc.perform(
                        post("/manufacturers2")
        //lanzar peticion http post al controlador, a la url /manufacturers2
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        //contentype sirve para decirle al servidor que tipo de contenido estamos enviando
        //mediatype sirve para enviar datos en formato formulario, urlencoded sirve para enviar datos en la url
        .param("name", "MSI")
        .param("year", "2024")
        //enviamos parametros y verificamos con expect
        ).andExpect(status().is3xxRedirection())//comprobar que la peticion ha ido bien
        .andExpect(redirectedUrl("/manufacturers/1"));//comprobar que la peticion ha ido bien
    }
    @DisplayName("actualizar fabricante existente,-> con redireccion a Detail")
    @Test
    void saveAndGoDetail_UpdateExistent() throws Exception {
        var manufacturer = Manufacturer.builder()
                .id(1L)
                .name("MSI")
                .year(2024)
                .build();
    //creamos manufacturer
        when(manufacturerRepository.findById(1L)).thenReturn(
                Optional.of(manufacturer)
        );
    //cuando se llame al metodo findById, entonces devolvemos manufacturer
        mockMvc.perform(
                        post("/manufacturers2")
    //lanzar peticion http post al controlador, a la url /manufacturers2
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    //contentype sirve para decirle al servidor que tipo de contenido estamos enviando
    //mediatype sirve para enviar datos en formato formulario, urlencoded sirve para enviar datos en la url
                                .param("id", "1") // poner id para que sea una actualización
                                .param("name", "MSI Modificado")
                                .param("year", "2025")
    //enviamos parametros y verificamos con expect
                ).andExpect(status().is3xxRedirection())
                //comprobar que la peticion ha ido bien
                .andExpect(redirectedUrl("/manufacturers/1"));
    //comprobar que se ha redirigido a la url (detail) /manufacturers/1
        verify(manufacturerRepository).findById(1L);
        //verificar que se ha llamado al metodo findById del repositorio con el id 1
        verify(manufacturerRepository).save(manufacturer);
        //verificar que se ha llamado al metodo save del repositorio con el objeto manufacturer
    }


}