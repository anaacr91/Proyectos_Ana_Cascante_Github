package com.Selenium_Java.controller;

import com.Selenium_Java.model.Manufacturer;
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
        model.addAttribute("manufacturer", new Manufacturer());
        return "manufacturer-form";
    }

    // getFormUpdate //update in form
    @GetMapping("manufacturers/update/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String getFormToUpdate(Model model, @PathVariable Long id) {
        manufacturerRepo.findById(id)
                .ifPresentOrElse(manufacturer -> model.addAttribute("manufacturer", manufacturer),
        //si existe el manufacturer, cargarlo en el modelo update
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
                        });
        //si no existe el manufacturer, lanzar excepcion 404
        return "manufacturer-form";
    }

    // save
    @PostMapping("manufacturers")
    public String save(@ModelAttribute Manufacturer manufacturer) {

        // crear
        if (manufacturer.getId() == null) {
            manufacturerRepo.save(manufacturer);
        }
        // editar
        manufacturerRepo.findById(manufacturer.getId()).ifPresent(manufacturerDB -> {
            // manufacturerDB.setName(manufacturer.getName());
            BeanUtils.copyProperties(manufacturer, manufacturerDB);
            manufacturerRepo.save(manufacturerDB);
        });

//ambas situaciones vuelves al listado
        return "redirect:/manufacturers";
    }


    @PostMapping("manufacturers2")
    public String saveAndGoDetail(@ModelAttribute Manufacturer manufacturer) {
        if (manufacturer.getId() == null) { //crear nuevo, no hay id
            manufacturerRepo.save(manufacturer);
        } else { // editar la id del manufacturer
            manufacturerRepo.findById(manufacturer.getId()).ifPresent(manufacturerDB -> {
                BeanUtils.copyProperties(manufacturer, manufacturerDB);
                manufacturerRepo.save(manufacturerDB);
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

