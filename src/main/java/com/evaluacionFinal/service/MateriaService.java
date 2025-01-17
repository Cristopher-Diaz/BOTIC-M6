package com.evaluacionFinal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evaluacionFinal.model.Materia;
import com.evaluacionFinal.repository.MateriaRepository;

@Service
public class MateriaService {
    @Autowired
    private MateriaRepository materiaRepository;

    public Materia save(Materia materia) {
        return materiaRepository.save(materia);
    }

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }
}
