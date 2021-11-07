package controller;

import controller.command.*;
import service.OrderService;
import service.PetService;
import service.UserService;
import view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {
    private final View view;
    private final List<Command> commands;

    public MainController(View view) {
        this.view = view;
        this.commands = new ArrayList<>(Arrays.asList(new Help(view),
                new Create(view, new OrderService(), new PetService(), new UserService()),
                new Read(view, new OrderService(), new PetService(), new UserService()),
                new Update(view, new OrderService(), new PetService(), new UserService()),
                new Delete(view, new OrderService(), new PetService(), new UserService())));
    }

    public void run() {
        view.write("Welcome to the Pet store application");
        doCommand();
    }

    private void doCommand() {
        boolean running = true;
        while (running) {
            view.write("Enter 'help' to get list of commands\nEnter 'exit' to leave the application");
            String inputCommand = view.read();
            for (Command command : commands) {
                if (command.canProcess(inputCommand)) {
                    command.process();
                    break;
                } else if (inputCommand.equalsIgnoreCase("exit")) {
                    view.write("Good Bye!");
                    running = false;
                    break;
                }
            }
        }
    }
}
