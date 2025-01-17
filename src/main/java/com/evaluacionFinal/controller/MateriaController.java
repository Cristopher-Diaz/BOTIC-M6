package com.evaluacionFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacionFinal.model.Materia;
import com.evaluacionFinal.service.MateriaService;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    @PostMapping
    public Materia save(@RequestBody Materia materia) {
        return materiaService.save(materia);
    }
}
