package app.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String address;
    private int port;
    private Map<String, String> params;
    private RequestCallback callback;

    public Request (String address, int port, Map< String, String > params) {
        this.address = address;
        this.port = port;
        this.params = params;
    }

    public void start (Net toNotify, int rId, RequestCallback callback) {
        new Thread(() -> {
            try {
                var s = new Socket(address, port);
                s.setSoTimeout(10000);
                var out = new PrintWriter(s.getOutputStream(), true);
                var in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                var sb = new StringBuilder().append(rId).append("&");
                if ( params != null )
                    params.forEach((k, v) -> sb.append(k).append("#").append(v).append("&"));
                out.println(sb);

                var sResp = in.readLine();

                if ( sResp != null ) {
                    var tokens = sResp.split("&");
                    var respCode = Integer.parseInt(tokens[0]);
                    var respParams = new HashMap<String, String>();

                    for (int i = 1; i < tokens.length; i++) {
                        respParams.put(tokens[i].split("#")[0], tokens[i].split("#")[1]);
                    }

                    callback.callback(respCode, respParams);
                    toNotify.removeRequest(this);
                } else
                    throw new SocketTimeoutException();
            } catch (SocketTimeoutException | ConnectException e) {
                callback.callback(Codes.serverTimeout, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
