package uce.edu.web.api.service.mapper;

import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.to.ProfesorTo;

public class ProfesorMapper {

    public static ProfesorTo toTo(Profesor profesor) {
        ProfesorTo profeTo = new ProfesorTo();
        profeTo.setId(profesor.getId());
        profeTo.setNombre(profesor.getNombre());
        profeTo.setApellido(profesor.getApellido());
        profeTo.setFechaNacimiento(profesor.getFechaNacimiento());
        profeTo.setSueldo(profesor.getSueldo());
        return profeTo;
    }

    public static Profesor toEntity(ProfesorTo profesorTo) {
        Profesor profe = new Profesor();
        profe.setId(profesorTo.getId());
        profe.setNombre(profesorTo.getNombre());
        profe.setApellido(profesorTo.getApellido());
        profe.setFechaNacimiento(profesorTo.getFechaNacimiento());
        profe.setSueldo(profesorTo.getSueldo());
        return profe;
    }

}
