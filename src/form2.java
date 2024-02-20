import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class form2 {
    JPanel menu;
    private JLabel tCRUD;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;

    public form2() {
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame agregar = new JFrame("Opcion Agregar");
                agregar.setContentPane(new form3().agregar);
                agregar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                agregar.pack();
                agregar.setSize(750,550);
                agregar.setVisible(true);
                ((JFrame) SwingUtilities.getWindowAncestor(agregarButton)).dispose();

            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame actualizar = new JFrame("Opcion Actualizar");
                actualizar.setContentPane(new Actualizar().form4);
                actualizar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                actualizar.pack();
                actualizar.setSize(750,550);
                actualizar.setVisible(true);
                ((JFrame) SwingUtilities.getWindowAncestor(actualizarButton)).dispose();

            }
        });
    }
}
