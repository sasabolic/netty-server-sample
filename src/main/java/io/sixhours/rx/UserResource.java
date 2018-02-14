package io.sixhours.rx;

import org.json.JSONObject;

import java.util.Map;

import static io.netty.util.CharsetUtil.UTF_8;

public class UserResource {

    public Response get(String id) {
        String body = new JSONObject()
                .put("firstName", "Sasa")
                .put("lastName", "Bolic")
                .put("email", "sasa.bolic@sixhours.io").toString();

        return new Response.Builder()
                .body(body.getBytes(UTF_8))
                .headers(
                        Map.of("Content-Type", "application/json; charset=utf-8", "Content-Length", String.valueOf(body.length()))
                )
                .build();
    }

    public Response save(String data) {
        System.out.println("SAVING DATA: " + data);
        return new Response.Builder()
                .statusCode(201)
                .headers(
                        Map.of("Location", "/123", "Content-Length", "0")
                )
                .build();
    }

    public Response update(String id, String data) {
        System.out.println("UPDATING DATA: " + data);
        return new Response.Builder()
                .statusCode(204)
                .headers(
                        Map.of("Content-length", "0")
                )
                .build();
    }
}
