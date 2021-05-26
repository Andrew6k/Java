package app.communication;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Net {
    private List < Request >    activeRequests = new ArrayList<>();

    public synchronized void addRequest (Request r) {
        this.activeRequests.add(r);
    }

    public synchronized void removeRequest (Request r) {
        this.activeRequests.remove(r);
    }

    public void makeRequest (Request r, int rId, RequestCallback callback) {
        this.addRequest(r);
        r.start(this, rId, callback);
    }

    public void makeRequest (String address, int port, Map < String, String > parameters, int rId, RequestCallback callback) {
        this.makeRequest(new Request(address, port, parameters), rId, callback);
    }

    public void makeRequest (String address, Map < String, String > parameters, int rId, RequestCallback callback) {
        this.makeRequest(address.split(":")[0], Integer.parseInt(address.split(":")[1]), parameters, rId, callback);
    }
}
