package io.sixhours.rx.server;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract variable names from template URI and matches URI against the template.
 */
public class UriTemplate {

    private final List<String> variableNames;

    private final Pattern matchPattern;

    /**
     * Instantiates a new {@code UriTemplate}.
     *
     * @param uriTemplate the uri template
     */
    public UriTemplate(String uriTemplate) {
        Objects.requireNonNull(uriTemplate, "'uriTemplate' cannot be null");

        UriTemplateParser parser = new UriTemplateParser(uriTemplate);

        this.variableNames = parser.getVariableNames();
        this.matchPattern = parser.getMatchPattern();
    }

    /**
     * Match a URI against the template.
     *
     * @param uri the uri to match against the template
     * @return the map where template variables are keys and template values are values
     */
    public Map<String, String> match(String uri) {
        Objects.requireNonNull(uri, "'uri' cannot be null");

        Map<String, String> result = new LinkedHashMap<String, String>(this.variableNames.size());
        Matcher matcher = this.matchPattern.matcher(uri);

        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String name = this.variableNames.get(i - 1);
                String value = matcher.group(i);

                result.put(name, value);
            }
        }
        return result;
    }

    /**
     * Matches a URI against the template.
     *
     * @param uri the uri to match against the template
     * @return true if the URI matches the template, otherwise false
     */
    public boolean matches(String uri) {
        return uri != null ? this.matchPattern.matcher(uri).matches() : false;
    }

    private class UriTemplateParser {
        private final List<String> variableNames = new ArrayList<>();
        private final Pattern matchPattern;

        private UriTemplateParser(String uriTemplate) {
            StringBuilder mathPatternBuilder = new StringBuilder();
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < uriTemplate.length(); i++) {
                char c = uriTemplate.charAt(i);

                if (c == '{') {
                    // start reading URI variable
                    mathPatternBuilder.append(quote(builder));
                    builder = new StringBuilder();

                    continue;
                } else if (c == '}') {
                    // end reading URI variable
                    String variable = builder.toString();

                    // replace variable with regex
                    mathPatternBuilder.append("(.*)");

                    this.variableNames.add(variable);
                    builder = new StringBuilder();

                    continue;
                }
                builder.append(c);
            }

            if (builder.length() > 0) {
                mathPatternBuilder.append(quote(builder));
            }

            matchPattern = Pattern.compile(mathPatternBuilder.toString());
        }

        /**
         * Gets variable names.
         *
         * @return the variable names
         */
        public List<String> getVariableNames() {
            return Collections.unmodifiableList(variableNames);
        }

        /**
         * Gets match pattern.
         *
         * @return the match pattern
         */
        public Pattern getMatchPattern() {
            return matchPattern;
        }

        private String quote(StringBuilder builder) {
            return (builder.length() > 0 ? Pattern.quote(builder.toString()) : "");
        }
    }

}
