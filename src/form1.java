import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class form1 {
    JPanel login;
    private JLabel txtitulo;
    private JLabel txtcrud;
    private JButton continuarButton;
    private JButton salirButton;
    private JButton salirButton2;

    public form1() {
        continuarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame menu = new JFrame("Menu - Crud");
                menu.setContentPane(new form2().menu);
                menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu.pack();
                menu.setSize(650,700);
                menu.setVisible(true);
                ((JFrame) SwingUtilities.getWindowAncestor(continuarButton)).dispose();
            }
        });
    }


}
