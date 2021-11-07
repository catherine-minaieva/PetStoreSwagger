package controller.command;

import model.ApiResponse;
import service.OrderService;
import service.PetService;
import service.UserService;
import view.View;
import java.io.IOException;

public class Delete extends AbstractCommand implements Command{
    private final View view;

    public Delete(View view) {
        super(view);
        this.view = view;
    }

    private static final String MENU = """
            Please, enter the number according to list below
            1 - to delete user
            2 - to delete pet
            3 - to delete order
            return - go back to main menu
            """;

    @Override
    public String commandName() {
        return "delete";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(MENU);
            String section = view.read();
            switch (section) {
                case "1" -> deleteUser();
                case "2" -> deletePet();
                case "3" -> deleteOrder();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void deleteUser() {
        view.write("Enter user name you would like to delete");
        String userName = view.read();
        try {
            ApiResponse apiResponse = UserService.delete(userName);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void deletePet() {
        int id = readIntegerFromConsole("Enter pet id you would like to delete");
        try {
            ApiResponse apiResponse = PetService.delete(id);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void deleteOrder() {
        boolean running = true;
        while (running) {
            int id = readIntegerFromConsole("Enter order id to delete in range 1-10");
            if (id < 1 || id > 10) {
                view.write("Wrong data, please, enter order id in range 1-10");
                return;
            } else {
                try {
                    ApiResponse apiResponse = OrderService.delete(id);
                    if (apiResponse.getCode() == 200) {
                        view.write("Deleted successfully");
                        running = false;
                    } else {
                        view.write("""
                                Failed to delete the order
                                """ + apiResponse.getMessage());
                    }
                } catch (IOException | InterruptedException ex) {
                    view.write(ex.getMessage());
                }
            }
        }
    }
}
