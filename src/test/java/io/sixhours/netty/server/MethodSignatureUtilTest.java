package io.sixhours.netty.server;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MethodSignatureUtilTest {

    @Test
    public void givenMethodSignatureWhenMethodNameInvokedReturnCorrectResult() {
        final String signature = "io.sixhours.rx.app.UserResource.get(java.lang.String id)";

        final String methodName = MethodSignatureUtil.methodName(signature);

        assertThat(methodName).isEqualTo("get");
    }

    @Test
    public void givenMethodSignatureIsInRootPackageWhenMethodNameInvokedReturnCorrectResult() {
        final String signature = "get(java.lang.String id)";

        final String methodName = MethodSignatureUtil.methodName(signature);

        assertThat(methodName).isEqualTo("get");
    }

    @Test
    public void givenMethodSignatureWhenClassNameInvokedReturnCorrectResult() {
        final String signature = "io.sixhours.rx.app.UserResource.get(java.lang.String id)";

        final String methodName = MethodSignatureUtil.className(signature);

        assertThat(methodName).isEqualTo("io.sixhours.rx.app.UserResource");
    }

    @Test
    public void givenMethodSignatureWhenArgumentsInvokedReturnCorrectResult() {
        final String signature = "io.sixhours.rx.app.UserResource.get(java.lang.String id)";

        final Map<String, String> arguments = MethodSignatureUtil.arguments(signature);

        assertThat(arguments).extracting("id").contains("java.lang.String");
    }

    @Test
    public void givenMethodSignatureWithMultipleArgumentsWhenArgumentsInvokedReturnCorrectResult() {
        final String signature = "io.sixhours.rx.app.UserResource.get(java.lang.String id, java.lang.Integer type)";

        final Map<String, String> arguments = MethodSignatureUtil.arguments(signature);

        assertThat(arguments).extracting("id", "type").contains("java.lang.String", "java.lang.Integer");
    }

}
