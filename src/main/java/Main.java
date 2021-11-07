import controller.MainController;
import view.Console;
import view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console(System.in, System.out);
        MainController controller = new MainController(view);
        controller.run();
    }
}
