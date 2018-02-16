package io.sixhours.netty.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provider of defined routes in project which are used to map HTTP requests to resource.
 */
public final class RouteProvider {

    public static List<Route> routes;

    private final static String routeRegex = "(\\S+)\\s+(\\S+)\\s+(\\S+\\(.*\\))";

    static {
        init();
    }

    private RouteProvider() {
    }

    private static void init() {
        try {
            final Pattern pattern = Pattern.compile(routeRegex);
            Path path = Paths.get(RouteProvider.class.getClassLoader().getResource("config/routes").toURI());
            Stream<String> lines = Files.lines(path);

            routes = lines.map(l -> {
                final Matcher matcher = pattern.matcher(l);

                System.out.println("Line: " + l);
                if (matcher.find()) {
                    return new Route(matcher.group(1), matcher.group(2), matcher.group(3));
                }
                return null;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
