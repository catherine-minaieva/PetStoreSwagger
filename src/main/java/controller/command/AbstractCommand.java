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

    public int readNumbers(String message) {
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

    public User readUsers() {
        int id = readNumbers("Enter user id");
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
        int status = readNumbers("Enter user status");
        return new User(id, userName, firstName, lastName, email, password, phone, status);
    }

    public Pet readPets() {
        int id = readNumbers("Enter pet id");
        Category category = readCategories();
        view.write("Enter pet name");
        String name = view.read();
        List<String> photoUrls = readPhotoUrls();
        List<Tag> tags = readTags();
        PetStatus status = readPetStatus();
        return new Pet(id, category, name, photoUrls, tags, status);
    }

    private Category readCategories() {
        int id = readNumbers("Enter category id");
        view.write("Enter category name");
        String name = view.read();
        return new Category(id, name);
    }

    private List<Tag> readTags() {
        List<Tag> tags = new ArrayList<>();
        boolean running = true;
        while (running) {
            tags.add(readTag());
            view.write("Successfully added.Press 'enter' to continue\nEnter 'ok' when finish");
            if (view.read().equalsIgnoreCase("ok")) {
                running = false;
            }
        }
        return tags;
    }

    public List<String> readPhotoUrls() {
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

    public File readFile() {
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

    public Tag readTag() {
        int id = readNumbers("Enter tag id");
        view.write("Enter tag name");
        String name = view.read();
        return new Tag(id, name);
    }

    public Order readOrder() {
        int id = readNumbers("Enter order id");
        int petId = readNumbers("Enter pet id");
        int quantity = readNumbers("Enter quantity");
        String shipDate = LocalDate.now().toString();
        OrderStatus status = readOrderStatus();
        boolean complete = readBoolean();
        return new Order(id, petId, quantity, shipDate, status, complete);
    }

    public OrderStatus readOrderStatus() {
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

    public PetStatus readPetStatus() {
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

    public boolean readBoolean() {
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
