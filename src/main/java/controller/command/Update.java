package controller.command;

import model.ApiResponse;
import model.Pet;
import model.PetStatus;
import model.User;
import service.PetService;
import service.UserService;
import view.View;

import java.io.File;
import java.io.IOException;

public class Update extends AbstractCommand implements Command {

    private final View view;

    public Update(View view) {
        super(view);
        this.view = view;
    }

    private static final String MENU = """
            Please, enter the number according to list below
            1 - to update user
            2 - to update pet
            3 - add photos for pet
            return - go back to main menu
            """;

    @Override
    public String commandName() {
        return "update";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(MENU);
            String section = view.read();
            switch (section) {
                case "1" -> updateUser();
                case "2" -> updatePet();
                case "3" -> addPhotos();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void updateUser() {
        view.write("Enter user name you would like to update");
        String userName = view.read();
        try {
            UserService.getUserByName(userName);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
        User user = readUserFromConsole();
        try {
            ApiResponse apiResponse = UserService.updateUser(userName, user);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void updatePet() {
        int id = readIntegerFromConsole("Enter pet id you would like to update");
        try {
            Pet petToUpdate = PetService.getPetById(id);
            view.write("Enter pet new name");
            String newName = view.read();
            PetStatus newStatus = readPetStatusFromConsole();
            petToUpdate.setName(newName);
            petToUpdate.setStatus(newStatus);
            ApiResponse apiResponse = PetService.updatePet(id, petToUpdate);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void addPhotos() {
        int id = readIntegerFromConsole("Enter pet id you would like to update");
        view.write("Enter description to photo");
        String metaData = view.read();
        File image = readFileFromConsole();
        try {
            ApiResponse apiResponse = PetService.uploadImage(id, metaData, image);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }
}
