import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class form3 {
    JPanel agregar;
    private JLabel txtAgregar;
    private JTextField txtNombre;
    private JTextField txtSalario;
    private JTextField txtCelular;
    private JButton agregarButton;
    private JTable table1;
    private JLabel txnombre;
    private JLabel txsalario;
    private JLabel txcelular;
    private JScrollPane tablemodule;
    private JButton regresarButton;

    private Connection connection;

    public form3() {
        // Conexión a la base de datos
        connectToDatabase();

        // Configuración de la tabla
        configureTable();

        //Agregar Boton
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposLlenos()) {
                    agregarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame menu = new JFrame("Menu - Crud");
                menu.setContentPane(new form2().menu);
                menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu.pack();
                menu.setSize(650,700);
                menu.setVisible(true);
                ((JFrame) SwingUtilities.getWindowAncestor(regresarButton)).dispose();
            }
        });
    }
    private void connectToDatabase() {
        try {
            // Conexión a la base de datos
            String url = "jdbc:mysql://localhost:3306/compania";
            String user = "root";
            String password = "f123456";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configureTable() {
        try {
            // Obtener datos de la base de datos y configurar la tabla
            String query = "SELECT * FROM empleados";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Crear un modelo de tabla para almacenar los datos
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre");
            model.addColumn("Salario");
            model.addColumn("Celular");

            // Llenar el modelo con los datos de la base de datos
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                double salario = resultSet.getDouble("salario");
                String celular = resultSet.getString("celular");
                model.addRow(new Object[]{nombre, salario, celular});
            }

            // Configurar la tabla con el modelo
            table1.setModel(model);

            // Cerrar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void agregarDatos() {
        try {
            // Obtener los valores ingresados por el usuario
            String nombre = txtNombre.getText();
            double salario = Double.parseDouble(txtSalario.getText());
            String celular = txtCelular.getText();

            // Insertar datos en la base de datos
            String insertQuery = "INSERT INTO empleados (nombre, salario, celular) VALUES (?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, nombre);
            insertStatement.setDouble(2, salario);
            insertStatement.setString(3, celular);
            insertStatement.executeUpdate();

            // Actualizar la tabla
            configureTable();

            // Limpiar los campos de entrada después de la inserción
            txtNombre.setText("");
            txtSalario.setText("");
            txtCelular.setText("");

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Datos agregados correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);

            // Cerrar recursos
            insertStatement.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean camposLlenos() {
        return !txtNombre.getText().isEmpty() && !txtSalario.getText().isEmpty() && !txtCelular.getText().isEmpty();
    }

}
