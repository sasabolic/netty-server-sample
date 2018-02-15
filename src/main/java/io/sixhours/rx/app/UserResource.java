package io.sixhours.rx.app;

import io.sixhours.rx.server.Response;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Map;

public class UserResource {

    public Response get(String id) {
        String body = new JSONObject()
                .put("firstName", "Sasa")
                .put("lastName", "Bolic")
                .put("email", "sasa.bolic@sixhours.io").toString();

        return new Response.Builder()
                .body(body.getBytes(Charset.forName("UTF-8")))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();
    }

    public Response save(String data) {
        System.out.println("SAVING DATA: " + data);
        return new Response.Builder()
                .statusCode(201)
                .header("Location", "/123")
                .build();
    }

    public Response update(String id, String data) {
        System.out.println("UPDATING DATA: " + data);
        return new Response.Builder()
                .statusCode(204)
                .build();
    }
}
