package com.evaluacionFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evaluacionFinal.model.Alumno;
import com.evaluacionFinal.repository.AlumnoRepository;

@Service
public class AlumnoService {
	
    @Autowired
    private AlumnoRepository alumnoRepository;
    
    public Alumno save(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    public List<Alumno> findAll() {
        return alumnoRepository.findAll();
    }
}
