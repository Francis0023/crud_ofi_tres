import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Actualizar {
    JPanel form4;
    private JLabel txActualizar;
    private JTextField txtNombre;
    private JTextField txtSalario;
    private JTextField txtCelular;
    private JButton actualizarButton;
    private JTable table2;
    private JTextField txtId;
    private JScrollPane tablemodel2;
    private JButton buscarButton;
    private JButton regresarButton;

    private Connection connection;

    public Actualizar() {
        // Conexión a la base de datos
        connectToDatabase();

        // Configuración de la tabla
        configureTable();


        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEmpleado();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
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
            table2.setModel(model);

            // Cerrar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Buscar Empleado:
    private void buscarEmpleado() {
        try {
            // Obtener el ID ingresado
            String idInput = txtId.getText();

            // Verificar si se ingresó un ID
            if (!idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Consultar la existencia del ID en la base de datos
                if (employeeExists(id)) {
                    // Obtener la información del empleado por ID
                    String query = "SELECT * FROM empleados WHERE id=?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1, id);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            // Mostrar la información en los campos de texto
                            if (resultSet.next()) {
                                String nombre = resultSet.getString("nombre");
                                double salario = resultSet.getDouble("salario");
                                String celular = resultSet.getString("celular");

                                txtNombre.setText(nombre);
                                txtSalario.setText(String.valueOf(salario));
                                txtCelular.setText(celular);

                                String mensaje = "ID: " + id + "\nNombre: " + nombre + "\nSalario: " + salario + "\nCelular: " + celular;
                                JOptionPane.showMessageDialog(this.form4, mensaje, "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this.form4, "El empleado con ID " + id + " no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }



    //Funcion de ACtualizar
    // Modificar el método updateEmployee para usar el ID ingresado
    private void updateEmployee() {
        try {
            // Obtener el ID ingresado
            String idInput = JOptionPane.showInputDialog(this.form4, "Ingrese el ID del empleado que desea actualizar:", "Actualizar por ID", JOptionPane.QUESTION_MESSAGE);

            // Verificar si se ingresó un ID
            if (idInput != null && !idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Consultar la existencia del ID en la base de datos
                if (employeeExists(id)) {
                    // Obtener los nuevos valores de los campos de texto
                    String nombre = txtNombre.getText();
                    double salario = Double.parseDouble(txtSalario.getText());
                    String celular = txtCelular.getText();

                    // Actualizar el empleado en la base de datos por ID
                    String query = "UPDATE empleados SET nombre=?, salario=?, celular=? WHERE id=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setDouble(2, salario);
                    preparedStatement.setString(3, celular);
                    preparedStatement.setInt(4, id);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                    // Limpiar campos de texto
                    txtId.setText("");
                    txtNombre.setText("");
                    txtSalario.setText("");
                    txtCelular.setText("");

                    // Recargar datos en la tabla
                    configureTable();
                } else {
                    JOptionPane.showMessageDialog(this.form4, "El empleado con ID " + id + " no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private boolean employeeExists(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM empleados WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }

}
