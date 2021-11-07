package controller.command;

import model.ApiResponse;
import model.Order;
import model.Pet;
import model.User;
import service.OrderService;
import service.PetService;
import service.UserService;
import view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Create extends AbstractCommand implements Command {

    private View view;
    private OrderService orderService;
    private PetService petService;
    private UserService userService;

    private static final String MENU = """
            Please, enter the number according to list below
            1 - create user
            2 - create pet
            3 - create order
            return - go back to main menu
            """;
    private static final String USER_MENU = """
            Please, enter the number according to list below
            1 - create single user
            2 - create users with list
            return - go back to main menu
            """;

    public Create(View view, OrderService orderService, PetService petService, UserService userService) {
        super(view);
        this.view = view;
        this.orderService = orderService;
        this.petService = petService;
        this.userService = userService;
    }

    @Override
    public String commandName() {
        return "create";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(MENU);
            String section = view.read();
            switch (section) {
                case "1" -> userMenuProcess();
                case "2" -> createPet();
                case "3" -> createOrder();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void userMenuProcess() {
        boolean running = true;
        while (running) {
            view.write(USER_MENU);
            String section = view.read();
            switch (section) {
                case "1" -> createSingleUser();
                case "2" -> createUsersWithList();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void createSingleUser() {
        User user = readUserFromConsole();
        try {
            ApiResponse apiResponse = userService.createUser(user);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void createUsersWithList() {
        List<User> users = new ArrayList<>();
        User user;
        boolean running = true;
        view.write("""
                Please, enter the user info according to requests below
                Enter 'ok' when finish""");
        while (running) {
            user = readUserFromConsole();
            users.add(user);
            view.write(user + """
                                                
                    was successfully added to list
                    Press 'enter' to continue
                    Enter 'ok' when finish
                    """);
            if (view.read().equalsIgnoreCase("ok")) {
                running = false;
            }
        }
        try {
            ApiResponse apiResponse = userService.createUserArray(users);
            if (apiResponse.getCode() == 200) {
                view.write("Users :");
                users.forEach(u -> view.write(u.toString()));
            } else {
                view.write("""
                        Failed to create a user
                        Response -""" + apiResponse);
            }
            view.write("Were successfully created");
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void createPet() {
        Pet pet = readPetFromConsole();
        try {
            Pet created = petService.createPet(pet);
            view.write(created + """
                                            
                    was successfully created""");
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void createOrder() {
        Order order = readOrderFromConsole();
        try {
            Order created = orderService.createOrder(order);
            view.write(created + """
                                            
                    was successfully created""");
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }
}
