package service;

import com.google.gson.Gson;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import model.ApiResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpService <T> {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final CloseableHttpClient DEFAULT_CLIENT = HttpClients.createDefault();
    private static final ResponseService RESPONSE_SERVICE = new ResponseService();
    protected static final Gson GSON = new Gson();
    protected static final String HOST = "https://petstore.swagger.io/v2";

    public HttpRequest sendGet(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    public HttpRequest sendDelete(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
    }

    public  <T> HttpRequest requestWithBody(String methodName, String url, T entity) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-type", "application/json")
                .method(methodName, HttpRequest.BodyPublishers.ofString(GSON.toJson(entity)))
                .build();
    }

    public HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public ApiResponse sendMultipartEntity(String url, HttpEntity entity) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        String execute = DEFAULT_CLIENT.execute(post, RESPONSE_SERVICE);
        return GSON.fromJson(execute, ApiResponse.class);
    }

    public ApiResponse sendPostEncoded(String url, List<NameValuePair> form) throws IOException {
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        String execute = DEFAULT_CLIENT.execute(post, RESPONSE_SERVICE);
        return GSON.fromJson(execute, ApiResponse.class);
    }

}
