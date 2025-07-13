package uce.edu.web.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.IProfesorService;
import uce.edu.web.api.service.mapper.ProfesorMapper;
import uce.edu.web.api.service.to.ProfesorTo;

@Path("/profesores")
public class ProfesorController {

    @Inject
    private IProfesorService profesorService;

    @Inject
    private IHijoService hijoService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        ProfesorTo profeTo = ProfesorMapper.toTo(this.profesorService.buscarPorId(id));
        profeTo.buildURI(uriInfo);
        return Response.status(227).entity(profeTo).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarTodos(@QueryParam("sueldo") Float sueldo, @Context UriInfo uriInfo) {
        List<Profesor> listaProfe = this.profesorService.buscarTodos(sueldo);
        List<ProfesorTo> listaProfeTo = new ArrayList<>();

        for (Profesor p : listaProfe) {
            ProfesorTo profeTo = ProfesorMapper.toTo(p);
            profeTo.buildURI(uriInfo);
            listaProfeTo.add(profeTo);
        }
        return Response.status(Response.Status.OK).entity(listaProfeTo).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardar(@RequestBody ProfesorTo profesorTo, @Context UriInfo uriInfo) {
        this.profesorService.guardar(ProfesorMapper.toEntity(profesorTo));
        return Response.status(Response.Status.OK).entity("El profesor fue guardado exitosamente").build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarPorId(@RequestBody ProfesorTo profesorTo, @PathParam("id") Integer id) {
        profesorTo.setId(id);
        this.profesorService.actualizarPorId(ProfesorMapper.toEntity(profesorTo));
        return Response.status(Response.Status.OK).entity("Profesor actualizado exitosamente").build();
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarParcialPorId(@RequestBody ProfesorTo profesor, @PathParam("id") Integer id) {
        profesor.setId(id);
        ProfesorTo p = ProfesorMapper.toTo(this.profesorService.buscarPorId(id));
        if (profesor.getApellido() != null) {
            p.setApellido(profesor.getApellido());
        }
        if (profesor.getNombre() != null) {
            p.setNombre(profesor.getNombre());
        }
        if (profesor.getFechaNacimiento() != null) {
            p.setFechaNacimiento(profesor.getFechaNacimiento());
        }
        if (profesor.getSueldo() != null) {
            p.setSueldo(profesor.getSueldo());
        }
        this.profesorService.actualizarParcialPorId(ProfesorMapper.toEntity(p));
        return Response.status(Response.Status.OK).entity("Profesor actualizado parcialmente exitosamente").build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarPorId(@PathParam("id") Integer id) {
        this.profesorService.borrarPorId(id);
        return Response.status(Response.Status.OK).entity("Borrado exitosamente").build();
    }

    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return hijoService.buscarPorProfesorID(id);
    }
}
