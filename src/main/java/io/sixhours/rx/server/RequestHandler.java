package io.sixhours.rx.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestHandler {

    private List<Route> routes = List.of(
            new Route("GET", "/users/{id}", "io.sixhours.rx.app.UserResource.get(java.lang.String id)"),
            new Route("POST", "/users", "io.sixhours.rx.app.UserResource.save(java.lang.String data)"),
            new Route("PUT", "/users/{id}", "io.sixhours.rx.app.UserResource.update(java.lang.String id, java.lang.String data)")
    );

    public Response handle(ChannelHandlerContext ctx, Object msg) {
        Response res = null;

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;

            final Optional<Route> route = routes.stream().filter(r -> r.matches(request)).findAny();

            if (route.isPresent()) {
                res = route.get().process(request);
            } else {
                res = new Response.Builder()
                        .body("Bad Request".getBytes(UTF_8))
                        .statusCode(400)
                        .build();
            }
        }
        return res;
    }
}
