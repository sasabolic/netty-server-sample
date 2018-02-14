package io.sixhours.rx;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UriTemplateTest {

    private UriTemplate template = new UriTemplate("/users/{id}/actions/{action}?delete={delete}");

    @Test
    public void givenUriWithMatchingVariablesWhenMatchThenReturnMapOfVariables() {
        final Map<String, String> result = template.match("/users/123/actions/delete?delete=true");

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();

        assertThat(result).extracting("id", "action", "delete")
                .contains("123", "delete", "true");
    }

    @Test
    public void givenUriNoMatchingVariablesWhenMatchThenReturnEmptyMap() {
        final Map<String, String> result = template.match("/users/actions?acquire=true");

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    public void givenUriWithMatchingVariablesWhenMatchesThenReturnTrue() {
        final boolean result = template.matches("/users/123/actions/delete?delete=true");

        assertThat(result).isTrue();
    }

    @Test
    public void givenUriWithNoMatchingVariablesWhenMatchesThenReturnFALSE() {
        final boolean result = template.matches("/users/actions/delete?delete=true");

        assertThat(result).isFalse();
    }
}
