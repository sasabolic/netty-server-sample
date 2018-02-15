package io.sixhours.rx.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ActionFactory {

    private static final Map<String, Action> actions = new ConcurrentHashMap<>();

    public static Action getBySignature(String signature) {
        Action action = actions.get(signature);

        if (action == null) {
            action = new Action(signature);
            actions.put(signature, action);
        }

        return action;
    }
}
