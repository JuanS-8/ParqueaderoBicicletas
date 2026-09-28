package edu.unilibre.gui;

import edu.unilibre.datos.Cupo;
import edu.unilibre.datos.Parqueadero;
import edu.unilibre.operaciones.GestionMetodos;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private final GestionMetodos gestion = new GestionMetodos();
    private final Parqueadero parqueadero = new Parqueadero();

    // Panel de Registrar Ingreso
    private JTextField txtSerialIngreso, txtPlacaIngreso, txtCedulaIngreso, txtNombreIngreso;
    private JButton btnRegistrarIngreso;

    // Panel de Registrar Salida
    private JTextField txtSerialSalida, txtCedulaSalida;
    private JButton btnRegistrarSalida;
    private JLabel lblValorAPagar;

    // Panel de Registrar Pago
    private JTextField txtSerialPago, txtCedulaPago;
    private JComboBox<String> cbMetodoPago;
    private JButton btnRegistrarPago;

    // PanelReporte
    private JButton btnVerReporte;

    // Panel derecho (el estado)
    private JTextArea txtAreaConsola;

    public MenuPrincipal() {
        setTitle("Parqueadero de Bicicletas");
        setSize(950, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponentes();
        actualizarAreaTexto();
    }

    private void initComponentes() {
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ingreso
        JPanel pnlIngreso = new JPanel(new GridLayout(5, 2, 6, 6));
        pnlIngreso.setBorder(BorderFactory.createTitledBorder("1. Registrar Ingreso"));
        pnlIngreso.add(new JLabel("Serial:"));
        txtSerialIngreso = new JTextField();
        pnlIngreso.add(txtSerialIngreso);
        pnlIngreso.add(new JLabel("Placa:"));
        txtPlacaIngreso = new JTextField();
        pnlIngreso.add(txtPlacaIngreso);
        pnlIngreso.add(new JLabel("Cedula propietario:"));
        txtCedulaIngreso = new JTextField();
        pnlIngreso.add(txtCedulaIngreso);
        pnlIngreso.add(new JLabel("Nombre propietario:"));
        txtNombreIngreso = new JTextField();
        pnlIngreso.add(txtNombreIngreso);
        btnRegistrarIngreso = crearBoton("Registrar Ingreso");
        pnlIngreso.add(new JLabel());
        pnlIngreso.add(btnRegistrarIngreso);

        // Salida
        JPanel pnlSalida = new JPanel(new GridLayout(4, 2, 6, 6));
        pnlSalida.setBorder(BorderFactory.createTitledBorder("2. Registrar Salida"));
        pnlSalida.add(new JLabel("Serial:"));
        txtSerialSalida = new JTextField();
        pnlSalida.add(txtSerialSalida);
        pnlSalida.add(new JLabel("Cedula propietario:"));
        txtCedulaSalida = new JTextField();
        pnlSalida.add(txtCedulaSalida);
        btnRegistrarSalida = crearBoton("Registrar Salida");
        pnlSalida.add(new JLabel());
        pnlSalida.add(btnRegistrarSalida);
        lblValorAPagar = new JLabel("Valor a pagar: -");
        pnlSalida.add(new JLabel());
        pnlSalida.add(lblValorAPagar);

        // Pago
        JPanel pnlPago = new JPanel(new GridLayout(4, 2, 6, 6));
        pnlPago.setBorder(BorderFactory.createTitledBorder("3. Registrar Pago y Liberar Cupo"));
        pnlPago.add(new JLabel("Serial:"));
        txtSerialPago = new JTextField();
        pnlPago.add(txtSerialPago);
        pnlPago.add(new JLabel("Cedula propietario:"));
        txtCedulaPago = new JTextField();
        pnlPago.add(txtCedulaPago);
        pnlPago.add(new JLabel("Metodo de pago:"));
        cbMetodoPago = new JComboBox<>(new String[]{"EFECTIVO", "TRANSFERENCIA"});
        pnlPago.add(cbMetodoPago);
        btnRegistrarPago = crearBoton("Registrar Pago");
        pnlPago.add(new JLabel());
        pnlPago.add(btnRegistrarPago);

        // Reporte diario
        JPanel pnlReporte = new JPanel(new GridLayout(1, 2, 6, 6));
        pnlReporte.setBorder(BorderFactory.createTitledBorder("4. Reporte del dia"));
        btnVerReporte = crearBoton("Ver Reporte Diario");
        pnlReporte.add(new JLabel());
        pnlReporte.add(btnVerReporte);

        panelIzquierdo.add(pnlIngreso);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(pnlSalida);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(pnlPago);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(pnlReporte);

        // Panel derecho (estado del parqueadero)
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Estado del Parqueadero"));
        txtAreaConsola = new JTextArea();
        txtAreaConsola.setEditable(false);
        txtAreaConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelDerecho.add(new JScrollPane(txtAreaConsola), BorderLayout.CENTER);

        add(new JScrollPane(panelIzquierdo), BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        // Manejo de eventos

        btnRegistrarIngreso.addActionListener(e -> {
            String serial = txtSerialIngreso.getText().trim();
            String placa = txtPlacaIngreso.getText().trim();
            String cedula = txtCedulaIngreso.getText().trim();
            String nombre = txtNombreIngreso.getText().trim();

            Cupo cupo = gestion.registrarIngreso(parqueadero, serial, placa, cedula, nombre);
            if (cupo == null) {
                JOptionPane.showMessageDialog(this,
                        "No fue posible registrar el ingreso (datos invalidos o parqueadero lleno).",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Ingreso registrado en el cupo #" + cupo.obtenerNumero());
            txtSerialIngreso.setText("");
            txtPlacaIngreso.setText("");
            txtCedulaIngreso.setText("");
            txtNombreIngreso.setText("");
            actualizarAreaTexto();
        });

        btnRegistrarSalida.addActionListener(e -> {
            String serial = txtSerialSalida.getText().trim();
            String cedula = txtCedulaSalida.getText().trim();

            double valor = gestion.registrarSalida(parqueadero, serial, cedula);
            if (valor < 0) {
                JOptionPane.showMessageDialog(this,
                        "No se encontro una bicicleta con esos datos.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            lblValorAPagar.setText(String.format("Valor a pagar: $%.0f", valor));
            // Se pasan los datos al panel de pago.
            txtSerialPago.setText(serial);
            txtCedulaPago.setText(cedula);
            actualizarAreaTexto();
        });

        btnRegistrarPago.addActionListener(e -> {
            String serial = txtSerialPago.getText().trim();
            String cedula = txtCedulaPago.getText().trim();
            String metodo = (String) cbMetodoPago.getSelectedItem();

            boolean pagoOk = gestion.registrarPago(parqueadero, serial, cedula, metodo);
            if (!pagoOk) {
                JOptionPane.showMessageDialog(this,
                        "Primero debe registrarse la salida de esta bicicleta.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean liberadoOk = gestion.liberarCupo(parqueadero, serial, cedula);
            if (liberadoOk) {
                JOptionPane.showMessageDialog(this, "Pago registrado y cupo liberado correctamente.");
                txtSerialPago.setText("");
                txtCedulaPago.setText("");
                lblValorAPagar.setText("Valor a pagar: -");
            } else {
                JOptionPane.showMessageDialog(this, "No fue posible liberar el cupo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            actualizarAreaTexto();
        });

        // generarReporte()
        btnVerReporte.addActionListener(e ->
                JOptionPane.showMessageDialog(this, gestion.generarReporte(),
                        "Reporte del dia", JOptionPane.INFORMATION_MESSAGE));
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(51, 111, 158));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
        return boton;
    }

    private void actualizarAreaTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("        ESTADO DEL PARQUEADERO           \n");
        sb.append("=========================================\n\n");
        sb.append("Capacidad total: ").append(parqueadero.obtenerCapacidad()).append(" cupos\n");
        sb.append("Espacios libres: ").append(parqueadero.obtenerEspaciosLibres()).append("\n");
        sb.append("-----------------------------------------\n");

        for (Cupo cupo : parqueadero.obtenerCupos()) {
            if (cupo.estaOcupado()) {
                sb.append(String.format(" Cupo [%02d]: OCUPADO - Serial: %s - Propietario: %s%n",
                        cupo.obtenerNumero(),
                        cupo.obtenerBicicleta().obtenerSerial(),
                        cupo.obtenerPropietario().obtenerNombre()));
            } else {
                sb.append(String.format(" Cupo [%02d]: Libre%n", cupo.obtenerNumero()));
            }
        }
        txtAreaConsola.setText(sb.toString());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel del sistema.");
        }

        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}