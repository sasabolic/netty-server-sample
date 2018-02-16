package io.sixhours.netty.server;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ActionTest {

    @Test
    public void givenSignatureAndValueMapWhenInvokeThenReturnResult() {
        Action action = new Action("io.sixhours.netty.app.UserResource.get(java.lang.String id)");

        final Response response = action.invoke(Map.of("id", "123"), null);

        assertThat(response).isNotNull();
        assertThat(response).hasFieldOrPropertyWithValue("statusCode", 200);
        assertThat(response).hasFieldOrProperty("headers");
        assertThat(response).hasFieldOrProperty("body");

        assertThat(response.getHeaders()).extracting("Content-Type", "Content-Length")
                .contains("application/json; charset=utf-8", "72");
    }

    @Test
    public void givenSignatureSaveAndValueMapWhenInvokeThenReturnResult() {
        Action action = new Action("io.sixhours.netty.app.UserResource.save(java.lang.String data)");

        final Response response = action.invoke(Map.of("data", "123"), null);

        assertThat(response).isNotNull();
        assertThat(response).hasFieldOrPropertyWithValue("statusCode", 201);
        assertThat(response).hasFieldOrProperty("headers");

        assertThat(response.getHeaders()).extracting("Location")
                .contains("/123");
    }
}
