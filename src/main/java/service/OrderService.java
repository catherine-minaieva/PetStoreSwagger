package service;

import com.google.gson.reflect.TypeToken;
import model.ApiResponse;
import model.Order;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class OrderService extends HttpService <Order>{
    private static final String CREATE_ORDER = "/store/order";
    private static final String READ_ORDER = "/store/order/";
    private static final String READ_ORDER_BY_STATUS = "/store/inventory";
    private static final String DELETE_ORDER = "/store/order/";

    public Order createOrder(Order order) throws IOException, InterruptedException {
        HttpRequest request = requestWithBody("POST",
                String.format("%s%s", HOST, CREATE_ORDER), order);
        return getOrder(request);
    }

    private Order getOrder(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = getResponse(request);
        return GSON.fromJson(response.body(), Order.class);
    }

    public Order getOrderById(int id) throws IOException, InterruptedException {
        HttpRequest request = sendGet(String.format("%s%s%d", HOST, READ_ORDER, id));
        return getOrder(request);
    }

    public ApiResponse delete(int id) throws IOException, InterruptedException {
        HttpRequest request = sendDelete(String.format("%s%s%d", HOST, DELETE_ORDER, id));
        HttpResponse<String> response = getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public HashMap<String, Integer> getInventoriesByStatus() throws IOException, InterruptedException {
        HttpRequest request = sendGet(String.format("%s%s", HOST, READ_ORDER_BY_STATUS));
        HttpResponse<String> response = getResponse(request);
        return GSON.fromJson(response.body(), new TypeToken<HashMap<String, Integer>>() {
        }.getType());
    }
}
