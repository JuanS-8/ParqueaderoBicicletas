package edu.unilibre.operaciones;

import edu.unilibre.datos.Cupo;
import edu.unilibre.datos.Hora;
import edu.unilibre.datos.Parqueadero;

public class GestionMetodos {


    public Cupo registrarIngreso(Parqueadero parqueadero, String serial, String placa, String cedula, String nombre) {
        return null;
    }

    public double registrarSalida(Parqueadero parqueadero, String serial, String cedula) {
        return 0;
    }

    public double calcularValor(Hora hora) {
        return 0;
    }

    public boolean registrarPago(Cupo cupo, double valor, String metodoPago) {
        return false;
    }

    public boolean liberarCupo(Parqueadero parqueadero, Cupo cupo) {
        return false;
    }

    public String generarReporteDiario() {
        return null;
    }
}
