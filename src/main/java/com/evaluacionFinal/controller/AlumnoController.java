package com.evaluacionFinal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evaluacionFinal.model.Alumno;
import com.evaluacionFinal.service.AlumnoService;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public List<Alumno> findAll() {
        return alumnoService.findAll();
    }

    @PostMapping
    public Alumno save(@RequestBody Alumno alumno) {
        return alumnoService.save(alumno);
    }
}
