package controller.command;

import model.Order;
import model.Pet;
import model.PetStatus;
import model.User;
import service.OrderService;
import service.PetService;
import service.UserService;
import view.View;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Read extends AbstractCommand implements Command{
    private View view;
    private OrderService orderService;
    private PetService petService;
    private UserService userService;

    private static final String MENU = """
            Please, enter the number according to list below
            1 - to get user info
            2 - to get pet info
            3 - to get order info
            return - go back to main menu
            """;
    private static final String PET_MENU = """
            Please, enter the number according to list below
            1 - to get pet by status
            2 - to get pet by id
            return - go back to main menu
            """;
    private static final String ORDER_MENU = """
            Please, enter the number according to list below
            1 - to get pet inventories by status
            2 - to get purchase order by id
            return - go back to main menu
            """;

    public Read(View view, OrderService orderService, PetService petService, UserService userService) {
        super(view);
        this.view = view;
        this.orderService = orderService;
        this.petService = petService;
        this.userService = userService;
    }

    @Override
    public String commandName() {
        return "read";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(MENU);
            String section = view.read();
            switch (section) {
                case "1" -> findUserByUserName();
                case "2" -> petMenuProcess();
                case "3" -> storeMenuProcess();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void findUserByUserName() {
        view.write("Enter user name");
        String userName = view.read();
        try {
            User user = userService.getUserByName(userName);
            view.write("Founded user:\n" + user);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void petMenuProcess() {
        boolean running = true;
        while (running) {
            view.write(PET_MENU);
            String section = view.read();
            switch (section) {
                case "1" -> getPetByStatus();
                case "2" -> getPetById();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void getPetByStatus() {
        PetStatus status = readPetStatus();
        try {
            List<Pet> pets = petService.getPetByStatus(status);
            pets.forEach(pet -> view.write(pet.toString()));
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void getPetById() {
        int id = readNumbers("Enter pet id");
        try {
            Pet pet = petService.getPetById(id);
            view.write("Founded pet:\n" + pet);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void storeMenuProcess() {
        boolean running = true;
        while (running) {
            view.write(ORDER_MENU);
            String section = view.read();
            switch (section) {
                case "1" -> getInventoriesByStatus();
                case "2" -> getOrderById();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void getInventoriesByStatus() {
        try {
            HashMap<String, Integer> inventories = orderService.getInventoriesByStatus();
            inventories.entrySet().forEach(status -> view.write(status.toString()));
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void getOrderById() {
        boolean running = true;
        while (running) {
            int id = readNumbers("Enter order id in range 1-10");
            if (id < 1 || id > 10) {
                view.write("Wrong data, please, enter order id in range 1-10");
            } else {
                try {
                    Order order = orderService.getOrderById(id);
                    view.write("Founded order:\n" + order);
                    running = false;
                } catch (IOException | InterruptedException ex) {
                    view.write(ex.getMessage());
                }
            }
        }
    }
}
