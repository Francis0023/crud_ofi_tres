import javax.swing.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        JFrame login = new JFrame("Principal");
        login.setContentPane(new form1().login);
        login.pack();
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setSize(650,600);
        login.setVisible(true);
    }
}