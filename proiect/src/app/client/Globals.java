package app.client;

import java.util.HashMap;
import java.util.Map;

public class Globals {
    private static Map< String, String > savedInstances = new HashMap<>();
    private static final Object mutex = new Object();

    public static void setValue ( String label, String value ) {
        synchronized (mutex) {
            savedInstances.put(label, value);
        }
    }

    public static boolean valueExists ( String label ) {
        synchronized (mutex) {
            return savedInstances.containsKey(label);
        }
    }

    public static String getValue (String label) {
        synchronized (mutex) {
            return savedInstances.get(label);
        }
    }
}
