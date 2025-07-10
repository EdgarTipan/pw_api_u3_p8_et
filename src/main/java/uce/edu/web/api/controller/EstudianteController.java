package uce.edu.web.api.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
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
import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.service.IEstudianteService;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.mapper.EstudianteMapper;
import uce.edu.web.api.service.to.EstudianteTo;

@Path("/estudiantes")
public class EstudianteController {

    @Inject
    private IEstudianteService estudianteService;

    @Inject
    private IHijoService hijoService;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        EstudianteTo estu = EstudianteMapper.toTo(this.estudianteService.buscarPorId(id));
        estu.buildURI(uriInfo);
        return Response.status(227).entity(estu).build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarTodos(@QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia) {
        System.out.println(provincia);
        return Response.status(Response.Status.OK).entity(this.estudianteService.buscarTodos(genero)).build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "guardar estudiante", description = "Esta capacidad permite guardar un estudiante en la db")
    public Response guardar(@RequestBody Estudiante estudiante) {
        this.estudianteService.guardar(estudiante);
        return Response.status(Response.Status.OK).entity("El estudiante fue guardado exitosamente").build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarPorId(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        estudiante.setId(id);
        this.estudianteService.actualizarPorId(estudiante);
        return Response.status(Response.Status.OK).entity("Actualizado exitosamente").build();
    }

    /*
     * @PATCH
     * 
     * @Path("/{id}")
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * 
     * @Consumes(MediaType.APPLICATION_JSON)
     * public Response actualizarParcialPorId(@RequestBody Estudiante
     * estudiante, @PathParam("id") Integer id) {
     * estudiante.setId(id);
     * Estudiante e = this.estudianteService.buscarPorId(id);
     * if (estudiante.getApellido() != null) {
     * e.setApellido(estudiante.getApellido());
     * }
     * if (estudiante.getNombre() != null) {
     * e.setNombre(estudiante.getNombre());
     * }
     * if (estudiante.getFechaNacimiento() != null) {
     * e.setFechaNacimiento(estudiante.getFechaNacimiento());
     * }
     * if (estudiante.getGenero() != null) {
     * e.setGenero(estudiante.getGenero());
     * }
     * this.estudianteService.actualizarParcialPorId(e);
     * return
     * Response.status(Response.Status.OK).entity("Actualizacion parcial exitosa").
     * build();
     * }
     */

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarPorId(@PathParam("id") Integer id) {
        this.estudianteService.borrarPorId(id);
        return Response.status(Response.Status.OK).entity("Borrado exitosamente").build();
    }

    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return hijoService.buscarPorEstudianteID(id);
    }

}
