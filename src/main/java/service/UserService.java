package service;

import model.ApiResponse;
import model.User;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UserService extends HttpService <User>{
    private static final String CREATE_USER = "/user";
    private static final String READ_USER = "/user/";
    private static final String UPDATE_USER = "/user/";
    private static final String DELETE_USER = "/user/";
    private static final String CREATE_WITH_LIST = "/user/createWithList";

    public User getUserByName(String userName) throws IOException, InterruptedException {
        HttpRequest request = sendGet(String.format("%s%s%s", HOST, READ_USER, userName));
        HttpResponse<String> response = getResponse(request);
        return GSON.fromJson(response.body(), User.class);
    }

    public ApiResponse createUser(User user) throws IOException, InterruptedException {
        HttpRequest request = requestWithBody("POST", String.format("%s%s", HOST, CREATE_USER), user);
        return getApiResponse(request);
    }

    public ApiResponse createUserArray(List<User> users) throws IOException, InterruptedException {
        HttpRequest request = requestWithBody("POST", String.format("%s%s", HOST, CREATE_WITH_LIST), users);
        return getApiResponse(request);
    }
    public ApiResponse updateUser(String userName, User newUser) throws IOException, InterruptedException {
        HttpRequest request = requestWithBody("PUT", String.format("%s%s%s", HOST, UPDATE_USER,
                userName), newUser);
        return getApiResponse(request);
    }

    public ApiResponse delete(String userName) throws IOException, InterruptedException {
        HttpRequest request = sendDelete(String.format("%s%s%s", HOST, DELETE_USER, userName));
        return getApiResponse(request);
    }

    private ApiResponse getApiResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }
}
