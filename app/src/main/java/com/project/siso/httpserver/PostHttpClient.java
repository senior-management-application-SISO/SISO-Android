//웹 소켓 통신
package com.project.siso.httpserver;

import com.project.siso.domain.Users;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PostHttpClient implements Runnable {

    String serverUri = "http://10.0.2.2:8080/";

    String msg;

    RequestBody formBody;

    String result;

    public PostHttpClient(String msg, RequestBody formBody) {
        this.msg = msg;
        this.formBody = formBody;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(serverUri + msg)
                    .post(formBody)
                    .build();
            //동기 처리시 execute함수 사용
            Response response = client.newCall(request).execute();
            //출력
            result = response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResult() {
        return this.result;
    }
}
