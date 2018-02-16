package io.sixhours.netty.server;

import org.junit.Test;

public class RouteTest {

    @Test
    public void givenRouteParametersCreateRoute() {

    }

    @Test
    public void replace() {
        final String s = "/user/:name/password/:password".replaceAll(":(\\w+)", "{$1}");
        System.out.println("S: " + s);
    }
}
