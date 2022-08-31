package de.utopiamc.framework.common;

import okhttp3.*;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create("", MediaType.get("application/json; charset=utf-8"));
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .encodedPath("/player/join")
                .addEncodedQueryParameter("player", "58ea857e-f4b7-4e72-8487-05e9150e1404")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response execute = client.newCall(request).execute();
        System.out.println(execute.body().string());
    }

}
