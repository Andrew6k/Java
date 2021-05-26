package app.communication;

import java.util.Map;

public interface RequestCallback {
    void callback (int code, Map < String, String > response);
}
