package io.sixhours.netty.server;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RouteProviderTest {

    @Test
    public void givenRouteConfigWhenInitializedThenReturnRouteList()  {
        final List<Route> routeList = RouteProvider.routes;

        assertThat(routeList).isNotNull();
        assertThat(routeList).isNotEmpty().hasSize(4);
    }

}
