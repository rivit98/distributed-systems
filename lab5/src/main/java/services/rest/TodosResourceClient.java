package services.rest;

import lombok.var;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class TodosResourceClient {
    private static void newLine(){
        System.out.println("--");
    }

    public static void main(String[] args) {
        var client = ClientBuilder.newClient();
        var target = client.target(getBaseURI());

        System.out.println(target.path("rest").path("todos").request(MediaType.TEXT_XML).get(String.class));
        newLine();
        System.out.println(target.path("rest").path("todos").request(MediaType.APPLICATION_JSON).get(String.class));
        newLine();
        System.out.println(target.path("rest").path("todos").request(MediaType.APPLICATION_XML).get(String.class));
        newLine();

        // Create a new todo through PUT
        var todo = new Todo("3", "Blablabla bla bla");
        var response = target.path("rest").path("todos")
                .path(todo.getId()).request(MediaType.APPLICATION_XML)
                .put(Entity.xml(todo));

        // Return code should be: 201 == created resource
        // or 204 == No Content if resource is already present
        System.out.println(response.getStatus());
        System.out.println(response.getStatusInfo().toString());
        newLine();

        // Get the Todos, number 3 should be created
        System.out.println(target.path("rest").path("todos").request()
                .accept(MediaType.TEXT_XML).get(String.class));
        newLine();

        // Get the Todo with id 1
        System.out.println(target.path("rest").path("todos/1")
                .request(MediaType.APPLICATION_XML).get(String.class));
        newLine();

        // Delete the Todo with id 1
        target.path("rest").path("todos/1").request().delete();
        System.out.println(response.getStatus());
        System.out.println(response.getStatusInfo().toString());
        newLine();

        // Get the all todos, id 1 should be deleted
        System.out.println(target.path("rest").path("todos")
                .request(MediaType.APPLICATION_XML).get(String.class));
        newLine();


        // Create a Todo through a Form
        Form form = new Form();
        form.param("id", "4");
        form.param("summary", "Demonstration of the client lib for forms");
        response = target.path("rest").path("todos")
                .request()
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
        System.out.println("Form response: " + response.readEntity(String.class));
        newLine();

        // Get the all todos, id 4 should be created
        System.out.println(target.path("rest").path("todos")
                .request(MediaType.APPLICATION_XML).get(String.class));
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/REST_Ex1").build();
    }
}
