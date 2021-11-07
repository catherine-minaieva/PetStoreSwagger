package controller.command;

import view.View;

public class Help implements Command{
    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public String commandName() {
        return "help";
    }

    @Override
    public void process() {
        view.write("""
            create - to create user/pet/order
            read - to get existing info
            update - to update existing info
            delete - to delete info
            exit - exit from an application""");
    }
}
