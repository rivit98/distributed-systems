package services.rest;

import lombok.AllArgsConstructor;
import lombok.var;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.xml.bind.JAXBElement;

//@AllArgsConstructor
public class TodoResource {
    @Context
    private final UriInfo uriInfo;

    @Context
    private final Request request;

    private final String id;

    public TodoResource(UriInfo uriInfo, Request request, String id) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        System.out.println(request.getMethod());
        System.out.println(uriInfo.getPath());
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Todo getTodo(){
        return TodoDao.getModel().get(id);
    }

    @GET
    @Produces({MediaType.TEXT_XML})
    public Todo getTodoHTML(){
        return TodoDao.getModel().get(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public Response putTodo(JAXBElement<Todo> todo){
        return putAndGetResponse(todo.getValue());
    }

    @DELETE
    public void deleteTodo(){
        var c = TodoDao.getModel().remove(id);
    }

    private Response putAndGetResponse(Todo todo){
        Response res;
        if(TodoDao.getModel().containsKey(todo.getId())){
            res = Response.noContent().build();
        }else{
            res = Response.created(uriInfo.getAbsolutePath()).build();
        }
        TodoDao.getModel().put(todo.getId(), todo);
        return res;
    }
}
