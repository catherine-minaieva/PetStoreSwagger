package controller.command;

import model.*;
import view.View;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand {

    private final View view;

    public AbstractCommand(View view) {
        this.view = view;
    }

    public int readIntegerFromConsole(String message) {
        int number = 0;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write(message);
                number = Integer.parseInt(view.read());
                if (number < 0) {
                    view.write("Number is less, than zero, please, enter the correct one");
                } else {
                    isFieldBlank = false;
                }
            } catch (Exception e) {
                view.write("Wrong format, please, enter integer.");
            }
        }
        return number;
    }

    public User readUserFromConsole() {
        int id = readIntegerFromConsole("Enter user id");
        view.write("Enter user name");
        String userName = view.read();
        view.write("Enter user first name");
        String firstName = view.read();
        view.write("Enter user last name");
        String lastName = view.read();
        view.write("Enter user email");
        String email = view.read();
        view.write("Enter user password");
        String password = view.read();
        view.write("Enter user phone number");
        String phone = view.read();
        int status = readIntegerFromConsole("Enter user status");
        return new User(id, userName, firstName, lastName, email, password, phone, status);
    }

    public Pet readPetFromConsole() {
        int id = readIntegerFromConsole("Enter pet id");
        Category category = readCategoryFromConsole();
        view.write("Enter pet name");
        String name = view.read();
        List<String> photoUrls = readPhotoUrlsFromConsole();
        List<Tag> tags = readTagsFromConsole();
        PetStatus status = readPetStatusFromConsole();
        return new Pet(id, category, name, photoUrls, tags, status);
    }

    private Category readCategoryFromConsole() {
        int id = readIntegerFromConsole("Enter category id");
        view.write("Enter category name");
        String name = view.read();
        return new Category(id, name);
    }

    private List<Tag> readTagsFromConsole() {
        List<Tag> tags = new ArrayList<>();
        boolean running = true;
        while (running) {
            tags.add(readTagFromConsole());
            view.write("Successfully added.Press 'enter' to continue\nEnter 'ok' when finish");
            if (view.read().equalsIgnoreCase("ok")) {
                running = false;
            }
        }
        return tags;
    }

    public List<String> readPhotoUrlsFromConsole() {
        List<String> photoUrls = new ArrayList<>();
        boolean running = true;
        while (running) {
            view.write("Enter photo url");
            photoUrls.add(view.read());
            view.write("Successfully added. Press 'enter' to add another photo url\nEnter 'ok' when finish");
            if (view.read().equalsIgnoreCase("ok")) {
                running = false;
            }
        }
        return photoUrls;
    }

    public File readFileFromConsole() {
        File image = null;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write("Enter photo path");
                String imagePath = view.read();
                new FileReader(imagePath);
                String extension = FilenameUtils.getExtension(imagePath);
                if (extension.equalsIgnoreCase("jpeg") ||
                        extension.equalsIgnoreCase("png") ||
                        extension.equalsIgnoreCase("jpg")) {
                    image = new File(imagePath);
                    isFieldBlank = false;
                } else {
                    view.write("Following file is not an image");
                }
            } catch (FileNotFoundException ex) {
                view.write("Wrong format, please, enter the image path.");
            }
        }
        return image;
    }

    public Tag readTagFromConsole() {
        int id = readIntegerFromConsole("Enter tag id");
        view.write("Enter tag name");
        String name = view.read();
        return new Tag(id, name);
    }

    public Order readOrderFromConsole() {
        int id = readIntegerFromConsole("Enter order id");
        int petId = readIntegerFromConsole("Enter pet id");
        int quantity = readIntegerFromConsole("Enter quantity");
        String shipDate = LocalDate.now().toString();
        OrderStatus status = readOrderStatusFromConsole();
        boolean complete = readBooleanFromConsole();
        return new Order(id, petId, quantity, shipDate, status, complete);
    }

    public OrderStatus readOrderStatusFromConsole() {
        OrderStatus orderStatus = null;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write("Enter the order status");
                orderStatus = OrderStatus.valueOf(view.read().toUpperCase());
                isFieldBlank = false;
            } catch (Exception e) {
                view.write("Wrong data, choose from list below");
                Arrays.stream(OrderStatus.values()).map(OrderStatus::name).forEach(System.out::println);
            }
        }
        return orderStatus;
    }

    public PetStatus readPetStatusFromConsole() {
        PetStatus orderStatus = null;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write("Enter the order status");
                orderStatus = PetStatus.valueOf(view.read().toUpperCase());
                isFieldBlank = false;
            } catch (Exception e) {
                view.write("Wrong data, choose from list below");
                Arrays.stream(PetStatus.values()).map(PetStatus::name).forEach(System.out::println);
            }
        }
        return orderStatus;
    }

    public boolean readBooleanFromConsole() {
        boolean complete = false;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write("Enter 'true' if order is completed, else enter 'false'");
                complete = Boolean.parseBoolean(view.read());
                isFieldBlank = false;
            } catch (Exception e) {
                view.write("Wrong format, please, enter 'true' or 'false'.");
            }
        }
        return complete;
    }

    public void resultOutput(ApiResponse response) {
        if (response.getCode() == 200) {
            view.write("Updated successfully");
        } else {
            view.write("""
                    Failed to update
                    Response -""" + response.getMessage());
        }
    }
}
