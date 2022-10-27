//웹 소켓 통신
package com.project.siso.httpserver;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetHttpClient implements Runnable {

    String serverUri = "http://10.0.2.2:8080/";

    String msg;

    String result;

    public GetHttpClient(String msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            String responseString = null;
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(serverUri + msg).get();
            builder.addHeader("Content-type", "application/json");
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                responseString = body.string();
                body.close();
            }
            result = responseString;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return this.result;
    }
}
