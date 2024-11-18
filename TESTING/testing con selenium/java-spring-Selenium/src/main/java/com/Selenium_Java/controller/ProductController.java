package com.Selenium_Java.controller;

import com.Selenium_Java.model.Product;
import com.Selenium_Java.repository.ManufacturerRepository;
import com.Selenium_Java.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Arrays.stream;


@AllArgsConstructor
@Controller
public class ProductController {

    private ProductRepository productRepository; // Spring crea el objeto productRepository y lo inyecta aquí
    //agregar manufacturerRepository
    private ManufacturerRepository manufacturerRepository;


    /*
    cargar datos en un modelo: lista <> de productos, q sacamos del list repository
    productRepository.findAll(); metodo q saca lo de la bbdd de la tabla productos
    return "product-list"; cargarlo en la vista product-list.html
// List<Product> products = productRepository.findAll();
     */

    //http://localhost:8080/productos
    @GetMapping("productos")
    public String findAll(Model model) {
// Agregar los productos del repositorio en el Model productos
        //model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Lista de productos");
//Lo que hace es agregar un atributo llamado "titulo" al objeto model, con el valor "Lista de productos".
// Este atributo puede ser accedido desde la vista para mostrar el título de la página o de una sección.
        //<h1 th:text="${titulo}"></h1>
        model.addAttribute("productos", productRepository.findAll());
//La idea es que todos los productos de la base de datos sean recuperados mediante
// productRepository.findAll() y se agreguen al modelo bajo la clave "productos".
        //se pone lo q quieras q salga en el html que retorna el metodo-> lista de productos
        return "product-list"; // vista
    }


    @GetMapping("productos/{id}")
    public String findById(@PathVariable Long id, Model model) {
        //si existe lo cargas, sino no pasa nada
        Optional<Product> productOptional = productRepository.findById(id);
        /*opcion 1 sin lambda
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("producto", product);
        }
        -opcion 2 con lambda*/
        productOptional.ifPresent(product -> {
            model.addAttribute("producto", product);
        });
        return "product-detail";
    }

    @GetMapping("productos404/{id}")
    public String findById_NotExist(@PathVariable Long id, Model model) {
        return productRepository.findById(id)
        .map(product->{model.addAttribute("producto", product);
            return "product-detail";
        })
       // .orElse("error");
                .orElseThrow(()->
                        new NoSuchElementException("Producto no encontrado"));
    }

    //create     //update

    //GET /products/crear     //obtener formulario vacio para crear producto desde 0//http://localhost:8080/productos/crear
    @GetMapping("/productos/crear")
    public String obtenerFormularioParaNuevoProducto(Model model) {//Parámetros cargar cosas model
        model.addAttribute("product", new Product());
        //añadir fabricantes
        model.addAttribute("manufacturers", manufacturerRepository.findAll()
                .stream()//-> convertir a stream
                .map(m -> {
                    m.setName(m.getName().toUpperCase());//-> convertir a mayusculas
                    //.forEach(System.out::println);//-> imprimir en consola
                    return m;

                }).toList());
        //En ProductController en los métodos de obtener formulario agregamos la lista de manufacturers al modelo
        //para la asociación de productos-manufacturers-> para crear y editar producto
        return "product-form";
    }
    //GET /products/edit/3  //obtener formulario editando el producto con id 3
    @GetMapping("/productos/editar/{id}")
    public String obtenerFormularioParaEditarProducto(@PathVariable Long id, Model model) {//variable URL long Id

        // Optional<Product> productOptional = productRepository.findById(id);
        /*opcion1: sin lambda
        if (productOptional.isPresent()) {
           Product product = productOptional.get();
           model.addAttribute("producto", product);
       }
        -opcion2: con lambda*/
        productRepository.findById(id)
                .ifPresent(product -> model.addAttribute("product", product));
        //añadir fabricantes
        model.addAttribute("manufacturers", manufacturerRepository.findAll());
        return "product-form";
    }
    //POST /productos         //guardar el producto
    @PostMapping("productos")
    public String guardarProducto(@ModelAttribute Product product) {
        //La anotación @ModelAttribute en Spring MVC se utiliza para vincular automáticamente los datos del formulario de una
        // solicitud HTTP a un objeto Java (en este caso, un objeto de tipo Product).
        // Cuando se recibe una solicitud POST que envía datos (por ejemplo, un formulario HTML)
        // , Spring busca los campos en el formulario que coincidan con las propiedades del objeto Product y los llena automáticamente,
        // creando una instancia de este.
        boolean exists = false;
        if (product.getId() != null) {
            exists = productRepository.existsById(product.getId());
        }
        if (!exists) {
// Crear un nuevo producto
            productRepository.save(product);
        } else {
// Actualizar un producto existente
            productRepository.findById(product.getId()).ifPresent(productoDB -> {
//                productoDB.setName(product.getName());
//                productoDB.setPrice(product.getPrice());
//                productoDB.setQuantity(product.getQuantity());
//                productoDB.setActive(product.getActive());
                //copyproperties, para que se copien las propiedades
                BeanUtils.copyProperties(product, productoDB);
                productRepository.save(productoDB);
            });
        }

        return "redirect:/productos";
    }

    //GET/productos/delete/3 //borrar el producto con id 3

    @GetMapping("productos/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");

        }
        try {
            productRepository.deleteById(id);
            return "redirect:/productos";
        } catch (Exception e) {
            e.printStackTrace(); // Utilizar log.error en producción
            return "error";// Mostrar la vista de error si el producto no existe
        }
    }


    //GET /products/deleteAll
    //falta botón en el listado de productos

}








