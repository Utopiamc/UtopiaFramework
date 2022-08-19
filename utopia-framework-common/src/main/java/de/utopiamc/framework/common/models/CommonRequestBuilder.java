package de.utopiamc.framework.common.models;

import com.google.gson.Gson;
import de.utopiamc.framework.api.config.request.RequestConfig;
import de.utopiamc.framework.api.model.RequestBodyType;
import de.utopiamc.framework.api.model.RequestBuilder;
import okhttp3.*;

import java.io.IOException;

public class CommonRequestBuilder implements RequestBuilder {

    private final HttpUrl.Builder httpUrl;

    private final OkHttpClient client;
    private final String method;

    private RequestBody body;

    public CommonRequestBuilder(String method, String path, OkHttpClient client, RequestConfig config) {
        this.client = client;
        this.method = method;

        this.httpUrl = new HttpUrl.Builder()
                .host(config.getHost())
                .port(config.getPort())
                .scheme("http")
                .encodedPath(path);
    }

    @Override
    public RequestBuilder queryParam(String key, String value) {
        httpUrl.addEncodedQueryParameter(key, value);
        return this;
    }

    @Override
    public RequestBuilder body(Object obj, RequestBodyType type) {
        body = RequestBody.create(String.valueOf(obj), MediaType.get(type.toString() + "; charset=utf-8"));
        return this;
    }

    @Override
    public <T> T execute(Class<T> entity) throws IOException {
        Response exec = exec();
        Gson gson = new Gson();
        return gson.fromJson(exec.body().charStream(), entity);
    }

    @Override
    public String execute() throws IOException {
        return exec().body().string();
    }

    private Response exec() throws IOException {
        Request req = resolveMethod(new Request.Builder())
                .url(httpUrl.build())
                .build();
        return client.newCall(req).execute();
    }

    private Request.Builder resolveMethod(Request.Builder builder) {
        return switch (method) {
            case "get" -> builder.get();
            case "post" -> builder.post(body);
            case "delete" -> builder.delete(body);
            case "patch" -> builder.patch(body);
            case "put" -> builder.put(body);
            case "head" -> builder.head();
            default -> builder.get();
        };
    }

}
