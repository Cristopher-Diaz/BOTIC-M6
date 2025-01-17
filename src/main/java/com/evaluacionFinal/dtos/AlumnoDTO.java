package com.evaluacionFinal.dtos;

import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoDTO {
    private Long id;
    private String rut;
    private String nombre;
    private String direccion;
    private Set<MateriaDTO> materiaList;
}
