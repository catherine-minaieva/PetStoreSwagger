package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Console implements View{

    private final Scanner scanner;
    private final OutputStream out;

    public Console(InputStream in, OutputStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }
    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }
}

