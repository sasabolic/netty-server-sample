package io.sixhours.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Handler for HTTP requests.
 */
public class RequestHandler {

    private RequestHandler() {

    }

    /**
     * Handles request and returns response.
     *
     * @param ctx the ctx
     * @param msg the msg
     * @return the response
     */
    public static Response handle(ChannelHandlerContext ctx, Object msg) {
        Response res = null;

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;

            final Optional<Route> route = RouteProvider.routes.stream().filter(r -> r.matches(request)).findAny();

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
