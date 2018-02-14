package io.sixhours.rx;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodSignatureUtil {

    public static String methodName(String signature) {
        final Pattern pattern = Pattern.compile("(\\w*)(\\()");
        final Matcher matcher = pattern.matcher(signature);

        String methodName = null;

        if (matcher.find()) {
            methodName = matcher.group(1);
        }
        return methodName;
    }

    public static String className(String signature) {
        final Pattern pattern = Pattern.compile("([\\w*.?]*)(\\.\\w*\\()");
        final Matcher matcher = pattern.matcher(signature);

        String className = null;

        if (matcher.find()) {
            className = matcher.group(1);
        }
        return className;
    }

    public static Map<String, String> arguments(String signature) {
        Map<String, String> arguments = new LinkedHashMap<>();
        final Pattern pattern = Pattern.compile("(\\()([^\\)]*)(\\))");
        final Matcher matcher = pattern.matcher(signature);

        if (matcher.find()) {
            final String[] paramSignatures = matcher.group(2).split(",");
            for (int j = 0; j < paramSignatures.length; j++) {
                final String[] split = paramSignatures[j].trim().split("\\s");
                arguments.put(split[1], split[0]);
            }
        }
        return arguments;
    }
}
