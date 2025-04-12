package com.Selenium_Java.controller;

import com.Selenium_Java.model.Address;
import com.Selenium_Java.model.Manufacturer;
import com.Selenium_Java.repository.AddressRepository;
import com.Selenium_Java.repository.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Controller
public class ManufacturerController {

    // Objeto inicializado e inyectado por Spring
    private ManufacturerRepository manufacturerRepo;
    private final AddressRepository addressRepository;


    // findAll
    @GetMapping("manufacturers")
    public String findAll(Model model) {
        model.addAttribute("manufacturers", manufacturerRepo.findAll());
        return "manufacturer-list"; // vista
    }

    // findById
    @GetMapping("manufacturers/{id}")
    public String findById(@PathVariable Long id, Model model) {
        manufacturerRepo.findById(id)
                .ifPresent(manufacturer -> model.addAttribute("manufacturer", manufacturer));
// Extra: podemos cargar más datos, por ejemplo products de este manufacturer
        return "manufacturer-detail";
    }
    // getFormCreation //create in form
    @GetMapping("manufacturers/new")
    public String getFormToCreate(Model model) {
        Manufacturer manufacturer = new Manufacturer();//cambiamos metodo para añadir la asociacion
        manufacturer.setAddress(new Address());//inicializa nueva direccion para l fabricante
       model.addAttribute("manufacturer", manufacturer);
        return "manufacturer-form";
    }

    // getFormUpdate //update in form
    @GetMapping("manufacturers/update/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String getFormToUpdate(Model model, @PathVariable Long id) {
        manufacturerRepo.findById(id)
                .ifPresentOrElse(manufacturer-> {//si fabricante existe
                    if (manufacturer.getAddress() == null) {//si la direccion es nula
                        manufacturer.setAddress(new Address());//inicializa 1 nueva direccion
                    }
                    model.addAttribute("manufacturer", manufacturer);//añadir el modelo a la vista
                },
                () -> {//si el fabricante no se encuentra
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");//lanza error 404
                });
        return "manufacturer-form";
    }

    // save
    @PostMapping("manufacturers")
    public String save(@ModelAttribute Manufacturer manufacturer) {//parametro manufacturer->formulario P.Entrada

        // crear
        if (manufacturer.getId() == null) {//verifica que id_manufacturer sea nulo
            manufacturerRepo.save(manufacturer);//guarda manufacturer bdd
        }
        // editar
        manufacturerRepo.findById(manufacturer.getId())//busca por id
                .ifPresent(manufacturerDB -> {//si esta el fabricante en BD
            BeanUtils.copyProperties(manufacturer, manufacturerDB, "id", "address");
            //copia propiedades de manufacturer a manufacturerDB excepto id y address
            // ->Porque actualizaremos la direccion asociada
            if (manufacturer.getAddress() != null){//si en el formulario de manufacturer hay una direccion nueva
                if (manufacturerDB.getAddress() == null){//si el fabricante de la BBDD no hay direccion
                    manufacturerDB.setAddress(new Address());//le damos una nueva direccion al fabricante de la BBDD
                }else{
                    BeanUtils.copyProperties(manufacturer.getAddress(), manufacturerDB.getAddress(), "id");
                    //copia propiedades de la direccion del formulario a la direccion del fabricante de la BBDD excepto id
                }
            }
           // BeanUtils.copyProperties(manufacturer, manufacturerDB);//->guardado sin asociacion
            manufacturerRepo.save(manufacturerDB);//guarda cambios del fabricante en BBDD
        });

//ambas situaciones vuelves al listado
        return "redirect:/manufacturers";
    }


    @PostMapping("manufacturers2")
    public String saveAndGoDetail(@ModelAttribute Manufacturer manufacturer) {//metodo recibe manufacturer del formulario
        if (manufacturer.getId() == null) { //verifica si es nulo
            manufacturerRepo.save(manufacturer);//guarda el manufacturer del form
        } else { // si no es nulo, (editar manufacturer existente)
            manufacturerRepo.findById(manufacturer.getId()).ifPresent(manufacturerDB -> {
                BeanUtils.copyProperties(manufacturer, manufacturerDB, "id", "address");
                //copia del form a la bbdd excepto id, address
                    if (manufacturer.getAddress() != null){//si en el formulario de manufacturer hay una direccion nueva
                        if (manufacturerDB.getAddress() == null){//si el fabricante de la BBDD no hay direccion
                            manufacturerDB.setAddress(new Address());//le damos una nueva direccion al fabricante de la BBDD
                        }else {//si ya tiene una direccion
                            BeanUtils.copyProperties(manufacturer.getAddress(), manufacturerDB.getAddress(), "id");
                        }//copia propiedades de la direccion del formulario a la direccion del fabricante de la BBDD excepto id
                        }
               // BeanUtils.copyProperties(manufacturer, manufacturerDB);//guardado sin asociacion
                manufacturerRepo.save(manufacturerDB);//guarda cambios del fabricante en BBDD
            });
        }
        return "redirect:/manufacturers/" + manufacturer.getId();
        //une dos métodos, save y findById
    }


    // deleteById
    @GetMapping("/manufacturers/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        manufacturerRepo.deleteById(id);
        return "redirect:/manufacturers";
    }

}

