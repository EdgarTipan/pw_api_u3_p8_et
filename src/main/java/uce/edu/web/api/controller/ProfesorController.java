package uce.edu.web.api.controller;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IProfesorService;

@Path("/profesores")
public class ProfesorController {

    @Inject
    private IProfesorService profesorService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarPorId(@PathParam("id") Integer id) {
        return Response.status(227).entity(this.profesorService.buscarPorId(id)).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarTodos(@QueryParam("sueldo") Float sueldo) {
        return Response.status(Response.Status.OK).entity(this.profesorService.buscarTodos(sueldo)).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardar(@RequestBody Profesor profesor) {
        this.profesorService.guardar(profesor);
        return Response.status(Response.Status.OK).entity("El profesor fue guardado exitosamente").build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarPorId(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
        profesor.setId(id);
        this.profesorService.actualizarPorId(profesor);
        return Response.status(Response.Status.OK).entity("Profesor actualizado exitosamente").build();
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarParcialPorId(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
        profesor.setId(id);
        Profesor p = this.profesorService.buscarPorId(id);
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
        this.profesorService.actualizarParcialPorId(p);
        return Response.status(Response.Status.OK).entity("Profesor actualizado parcialmente exitosamente").build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarPorId(@PathParam("id") Integer id) {
        this.profesorService.borrarPorId(id);
        return Response.status(Response.Status.OK).entity("Borrado exitosamente").build();
    }
}
