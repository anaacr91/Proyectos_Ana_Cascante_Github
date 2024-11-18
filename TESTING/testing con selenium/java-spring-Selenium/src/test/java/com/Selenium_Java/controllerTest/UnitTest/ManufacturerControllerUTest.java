package com.Selenium_Java.controllerTest.UnitTest;

import com.Selenium_Java.controller.ManufacturerController;
import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.repository.ManufacturerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test unitario utilizando JUnit 5 y Mockito para los mocks.
 * No carga Spring, no inicializa la app Spring con base de datos.
 * Test: aislado, rápido, facil, unitario
 */
//extender Junit con Mockito
@ExtendWith(MockitoExtension.class)

class ManufacturerControllerUTest {

    //declarar la clase a testear
    //system under test-> SUT-> inyecta/ reconfigura los mocks
    //el que estas testeando es ManufacturerController-> testear el controlador
    @InjectMocks //inyectar los mocks
    private ManufacturerController manufacturerController;

    //dependencias/mocks-> escenario necesario-> fixture-> crea mocks
    //dependencias: usar dentro controlador: Repo y Modelo -> @Mock
    //si usa modelo y repositorio, pues se mockea ambos

    @Mock //configurar los mocks // doble de manufacturerRepository
    //simular una respuesta del repositorio para testear el controlador sin necesidad de arrancar spring + implementar la base de datos
    private ManufacturerRepository manufacturerRepository;

    @Mock //quieres que el modelo a probar sea 1 mock, "señuelo", para verificar la interacción con este objeto (funcion, accion)
    //Importar el modelo-> crea mocks del modelo
    private Model model;
    // el modelo es una interfaz
    //crear clase que implemente interfaz. No puedes crear 1 objeto de 1 interfaz
    //usar los mocks de mockito para evitar crear modelos y repositorio

    @Test//testear el metodo
    void findAll() {
        //1.configurar repuesta mocks= ej. devuelva unos fabricantes
        //crear 2 fabricantes
        Manufacturer man1 = Manufacturer.builder().id(1L).build();
        Manufacturer man2 = Manufacturer.builder().id(2L).build();
        //las listas son inmutables, es mejor usar un arrayList
        List<Manufacturer> manufacturers = List.of(man1, man2);
        //cuando alguien llame al findAll, entonces devolvemos lista de manufacturers
        Mockito.when(manufacturerRepository.findAll()).thenReturn(manufacturers);
        //2.ejecutar (invocar) metodo a testear
        String view = manufacturerController.findAll(model);
        //3. verificacion o resultado esperado

        //3.1-asert de view
        Assertions.assertEquals("manufacturer-list", view);
        //Tambien se puede hacer con Assertions.assertEquals("manufacturer-list.html", view); o importar Asertions
        //3.2-verificar que el model tiene 2 manufacturer
        Mockito.verify(model).addAttribute("manufacturers", manufacturers);
        //3.3-verificar que el Manufacturer repository ha ejecutado 1 vez el metodo findall
        Mockito.verify(manufacturerRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("si tiene fabricante")//cuando el fabricante existe
    void findById_WhenManufacturerExists() {
        // 1. configurar respuestas mocks
        //cuando se invoque metodo findById del manufacturer con id 1, devolvuelve 1 optional con el manufacturer
      /*  Manufacturer man1 = Manufacturer.builder().id(1L).build();
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(man1));*/
        // segundo formato
        Manufacturer adidas = Manufacturer.builder().id(2L).name("Adidas").build();//construir objeto
        Optional<Manufacturer> manufacturerOptional = Optional.of(adidas);
        //utiliza la clase Optional de Java para encapsular un objeto Manufacturer llamado adidas.
        //Se usa Optional para representar de manera segura el resultado de operaciones que pueden no tener un valor
        when(manufacturerRepository.findById(2L)).thenReturn(manufacturerOptional);//cuando encuentres objeto, devolvuelve objeto
    //2.ejecutar metodo a testear
        String view = manufacturerController.findById(2L, model);
        //comprueba que el controlador devolverá la vista que muestra los detalles del modelo fabricante, id 2L
    //3.verificacion y assert
        assertEquals("manufacturer-detail", view);//comprueva que la vista es manufacturer-detail
        verify(manufacturerRepository).findById(2L);// verifica que el controlador haya intentado buscar un fabricante
        // con el ID 2L usando el repositorio simulado/mock.
        verify(model).addAttribute("manufacturer", adidas);//La línea verify(model).addAttribute("manufacturer", adidas);
        // se asegura de que el controlador haya añadido al modelo un atributo llamado "manufacturer" con el objeto adidas
    }
    @Test
    @DisplayName("No tiene fabricante")//cuando el fabricante no existe
   //1-configurar respuestas mocks
    void findById_WhenManufacturerNotExists() {
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());//optional empty= null
        //cuando no encuentres un objeto devuelve un optional vacio (ya que el fabricante no existe)
    //2-ejecutar metodo a testear
        String view = manufacturerController.findById(1L, model);
        //comprueba que el controlador devolverá una vista que muestra los detalles del modelo fabricante, id 1L.
        //guardar ese string q devuelve metodo FindId, cuando invocas findById, devuelve un string, para hacerle el assert
        //controlador te lleva a la vista manufacturer-detail del fabricante 1l
    //3-verificacion y assert
        assertEquals("manufacturer-detail", view);//comprueva que la vista es manufacturer-detail
        verify(manufacturerRepository).findById(1L);// verifica que el controlador haya intentado buscar un fabricante
        // con el ID 1L usando el repositorio simulado/mock.
        verify(model, never()).addAttribute(anyString(), any());//nunca con ninguna string
        //verifica que el model no se ha invocado nunca add Atribute-> metodo never
        //metodo never: le pasas el mock, (model), y el verification mode (never)
        //never: verificar que el modelo nunca haya invocado nunca el metodo addAttribute
    }

    @Test
    @DisplayName("metodo que desplaza al formulario manufacturer vacio/ fabricante nuevo")
    void getFormToCreate() {
        //2.metodo a testear
        String view= manufacturerController.getFormToCreate(model);
        //comprueba si el metodo del controlador getFormToCreate te devuelve la vista manufacturer-form y la guarda la string

        //3.verificacion y assert
        assertEquals("manufacturer-form", view);//comprueba que la vista es manufacturer-form
       verify(model).addAttribute(eq("manufacturer"), any (Manufacturer.class));
       //comprueba que el controlador haya añadido al modelo un atributo llamado "manufacturer" con un objeto nuevo
        //comprueba que el modelo haya invocado el metodo addAttribute(añadiendo el valor manufacturer)
        //param2: y que creando un objeto manufacturer nuevo
        //parametro 1; comprobar q en addattribute del controlador, se añade el atributo manufacturer

    }

    @Test
    void getFormToUpdate_Exist() {
            // 1. configurar respuestas mocks
            Manufacturer adidas = Manufacturer.builder().id(1L).name("Adidas").build();//construir objeto
            Optional<Manufacturer> adidasOpt = Optional.of(adidas);
            //cuando alguien llame al findById con id 1, devolvuelve el objeto adidas
        //se usa optional para representar de manera segura el resultado de operaciones que pueden no tener un valor
            when(manufacturerRepository.findById(1L)).thenReturn(adidasOpt);
//cuando alguien llame al findById con id 1, devolvuelve el objeto adidas
            // 2. ejecutar metodo a testear
            String view = manufacturerController.getFormToUpdate(model, 1L);
//comprueba que el controlador devolverá la vista que muestra el formulario de actualización del fabricante con id 1L
            // 3. assert y verify
            assertEquals("manufacturer-form", view);
            //comprueba que la vista es manufacturer-form
            verify(manufacturerRepository).findById(1L);
            //verifica que el controlador haya intentado buscar un fabricante con el ID 1L usando el repositorio simulado/mock.
            verify(model).addAttribute("manufacturer", adidas);
            //verifica que el controlador haya añadido al modelo un atributo llamado "manufacturer" con el objeto adidas


    }
    @Test
    void getFormToUpdate_NotExist() {
        //1. configurar respuestas mocks
        when(manufacturerRepository.findById(1L)).thenReturn(Optional.empty());
        //cuando no encuentres un objeto devuelve un optional vacio (ya que el fabricante no existe en el form)

        //CORRECCION-> Debido a la modificacion del controller
        // Ejecutar y capturar la excepción esperada
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            manufacturerController.getFormToUpdate(model, 1L);
        });

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode()); // Comprobar que se lanza un 404
        assertEquals("not found", exception.getReason()); // Comprobar el mensaje de la excepción
        verify(manufacturerRepository).findById(1L); // Verificar que se llama al repositorio para buscar el fabricante id1
        //verifica que el controlador haya intentado buscar un fabricante con el ID 1L usando el repositorio simulado/mock.
        verify(model, never()).addAttribute(anyString(), any()); // Comprobar que no se añade nada al modelo
        // Verificar que el model.addAttribute no ha sido invocado nunca
        // con cualquier string y cualquier objeto.
    }

        //CODIGO ANTERIOR
        // 2. ejecutar metodo a testear
        //String view = manufacturerController.getFormToUpdate(model, 1L);
        //comprueba que el controlador devolverá la vista que muestra el formulario de actualización del fabricante con id 1L
        //y lo guarda en la string view
        // 3 assert y verify
    // assertEquals("manufacturer-form", view);//comprueba que la vista es manufacturer-form

    @Test
    void save_ManufacturerNew() {
      //  when()
        //1.preparar datos y mocks-> crear objeto de fabricante y dependencia repositorio
        Manufacturer manufacturer = new Manufacturer();

    // invocar metodo a testear
        String view = manufacturerController.save(manufacturer);
        //comprueba que el controlador haya llamado al metodo save del controlador y haya guardado el manufacturer
        //y que el controlador devolvera la vista que muestra el listado de manufacturers y lo guarda en la string view

        // 2. verificar y assert
        assertEquals("redirect:/manufacturers", view);
        verify(manufacturerRepository).save(manufacturer);
        // comprueba que el repositorio haya llamado al metodo save del repositorio
        // con un objeto fabricante de la clase manufacturer.
    }

    @DisplayName("cuando manufacturer tiene un id/existe")
    @Test
    void save_ManufacturerExistsUpdate() {

    //1-preparar datos y mocks
    //Manufacturer que viene en los parámetros del controlador (ej. nombre nuevo)
    Manufacturer manufacturerToUpdate = new Manufacturer();
    manufacturerToUpdate.setId(1L);
    manufacturerToUpdate.setName("Adidas Modificado");

    //manufacturerDB viene de la base de datos (ej. nombre original)
    Manufacturer manufacturerDB = new Manufacturer();
    manufacturerDB.setId(1L);
   manufacturerDB.setName("Adidas");//Nombre original DB

//ambos van con el mismo id, para actualizar al fabricante Id 1->porque sino creas otro o modificas otro fabricante

    when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturerDB));
//cuando te piden fabricante con id 1, entonces devuelve 1 optional de manufacturer que esta en bbdd
//findById devuelve un optional de manufacturerDB pero con el metodo Optional.of()-> creas un optional***

//2.invocar metodo a testear
        String view = manufacturerController.save(manufacturerToUpdate);
        //comprueba que el controlador haya llamado al metodo save del controlador
        //y que el controlador devolvera la vista que muestra el listado de manufacturers y lo guarda en la string view

//3.aserciones y verificaciones
        assertEquals("redirect:/manufacturers", view);//comprueba que redirecciones a la vista
        verify(manufacturerRepository).findById(1L);
        //comprueba que el repositorio haya llamado al controlador para buscar el fabricante con id 1L
        verify(manufacturerRepository).save(manufacturerDB);
        //comprueba que el repositorio haya llamado al metodo save del controlador y guarda el objeto fabricante en la BBDD
        assertEquals("Adidas Modificado" , manufacturerDB.getName());//comprueba la actualizacion de nombre
}
    @Test
    void saveAndGoDetail_NewManufacturer() {
        // Si el metodo save no devuelve nada y solo modifica entonces no vale thenReturn y necesito un enfoque más avanzado
        Manufacturer manufacturer = new Manufacturer();
        doAnswer(invocation -> {
            Manufacturer manufacturerToSave = invocation.getArgument(0);
            manufacturerToSave.setId(1L);
            return null;
        }).when(manufacturerRepository).save(manufacturer);

    // 2. invocar metodo a testear
    String view = manufacturerController.saveAndGoDetail(manufacturer);
    //comprueba que el controlador haya llamado al metodo saveAndGoDetail del controlador
    // y devolverá la vista que muestra manufacturers-detail con id 1, y lo guarda en la string view

    // 3. verificaciones
    assertEquals("redirect:/manufacturers/1", view);//comprueba que la vista es manufacturers-detail con id 1
    verify(manufacturerRepository).save(manufacturer);
    //comprueba que el repositorio haya llamado al metodo save del controlador y guarda el objeto fabricante en la BBDD
}



    @Test
    void deleteById() {

        String view =manufacturerController.deleteById(1L);
        //comprueba que el controlador devolverá la vista que muestra el formulario de borrado del fabricante con id 1L
        //y lo guarda en la variable, string view el resultado del metodo deleteById del controlador

        //La línea String view = manufacturerController.deleteById(1L); llama al metodo deleteById
        // del manufacturerController con el ID 1L y guarda el nombre de la vista
        // Esta vista: devuelve manufacturers-list

        // 2. ejecutar metodo a testear
        assertEquals("redirect:/manufacturers", view);
        //comprueba que la vista es/ te redirige a: manufacturers-list
        // 3. verify y assert
        verify(manufacturerRepository).deleteById(1L);
        //comprueba que el controlador haya llamado al repositorio para borrar el fabricante con id 1L
        //Esto asegura que el metodo deleteById del repositorio fue
        // llamado correctamente, con el ID correcto, confirmando que la eliminación fue intentada.
    }
}