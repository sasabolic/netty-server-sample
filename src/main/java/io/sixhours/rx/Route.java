package io.sixhours.rx;

import io.netty.handler.codec.http.FullHttpRequest;

import static io.netty.util.CharsetUtil.UTF_8;

public class Route {

    private final String httpMethod;
    private final UriTemplate uriTemplate;
    private final String signature;

    public Route(String httpMethod, String uriTemplate, String signature) {
        this.httpMethod = httpMethod;
        this.uriTemplate = new UriTemplate(uriTemplate);
        this.signature = signature;

    }

    public boolean matches(FullHttpRequest request) {
        return this.httpMethod.equals(request.method().name()) && this.uriTemplate.matches(request.uri());
    }

    public Response process(FullHttpRequest request) {
        System.out.println("BODY: " + request.content().toString(UTF_8));
        System.out.println("REQUEST: " + this.uriTemplate.match(request.uri()));
        return new Action(this.signature, this.uriTemplate.match(request.uri()), request.content().toString(UTF_8)).invoke();
    }

}