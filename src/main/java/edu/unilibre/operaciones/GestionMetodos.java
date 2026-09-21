package edu.unilibre.operaciones;

import edu.unilibre.datos.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class GestionMetodos{

    public static final double ValorPorMinuto = 10.0;
    private int totalBicicletas;
    private double dineroTotal;

    public Cupo registrarIngreso(Parqueadero parqueadero, String serial, String placa, String cedula, String nombre) {
        if (parqueadero == null || serial == null || placa == null || cedula == null || nombre == null)  {
            return null;
        }

        Cupo cupoLibre = buscarUnCupo(parqueadero);
        if (cupoLibre == null) {
            return null;
        }

        Bicicleta bicicleta = new Bicicleta(serial, placa);
        Propietario propietario = new Propietario(cedula, nombre);
        Hora hora = new Hora();
        hora.asignarHoraIngreso(LocalDateTime.now());

        cupoLibre.asignarBicicleta(bicicleta);
        cupoLibre.asignarPropietario(propietario);
        cupoLibre.asignarHora(hora);
        cupoLibre.asignarOcupado(true);

        parqueadero.asignarEspaciosLibres(parqueadero.obtenerEspaciosLibres() - 1);
        return cupoLibre;
    }

    private Cupo buscarUnCupo(Parqueadero parqueadero) {
        List<Cupo> cupos = parqueadero.obtenerCupos();
        for (int i = 0; i < cupos.size(); i++) {
            Cupo cupo = cupos.get(i);
            if (!cupo.estaOcupado()) {
                return cupo;
            }
        }
        return null;
    }

    public double registrarSalida(Parqueadero parqueadero, String serial, String cedula) {
        Cupo cupo = buscarCupoOcupado(parqueadero, serial, cedula);
        if (cupo == null) {
            return -1;
        }
        cupo.obtenerHora().asignarHoraSalida(LocalDateTime.now());
        return calcularValor(cupo.obtenerHora());
    }

    public double calcularValor(Hora hora) {
        if (hora == null || hora.obtenerHoraIngreso() == null || hora.obtenerHoraSalida() == null) {
            return 0;
        }
        long minutos = Duration.between(hora.obtenerHoraIngreso(), hora.obtenerHoraSalida()).toMinutes();
        return minutos * ValorPorMinuto;
    }

    private Cupo buscarCupoOcupado(Parqueadero parqueadero, String serial, String cedula) {
        if (parqueadero == null || serial == null || cedula == null) {
            return null;
        }
        List<Cupo> cupos = parqueadero.obtenerCupos();
        for (int i=0; i<cupos.size(); i++){
            Cupo cupo  = cupos.get(i);
            if (cupo.estaOcupado()
                    && cupo.obtenerBicicleta() != null
                    && cupo.obtenerBicicleta().obtenerSerial().equals(serial)
                    && cupo.obtenerPropietario() != null
                    && cupo.obtenerPropietario().obtenerCedula().equals(cedula)) {
                return cupo;
            }
        }
        return null;
    }

    public boolean liberarCupo(Parqueadero parqueadero, String serial, String cedula) {
        Cupo cupo = buscarCupoOcupado(parqueadero, serial, cedula);
        if (cupo == null || cupo.obtenerPago() == null) {
            return false;
        }
        totalBicicletas++;
        dineroTotal += cupo.obtenerPago().obtenerValor();

        cupo.asignarOcupado(false);
        cupo.asignarBicicleta(null);
        cupo.asignarPropietario(null);
        cupo.asignarHora(null);
        cupo.asignarPago(null);

        parqueadero.asignarEspaciosLibres(parqueadero.obtenerEspaciosLibres() + 1);
        return true;
    }

    public boolean registrarPago(Parqueadero parqueadero, String serial, String cedula, String metodoPago) {
        if (metodoPago == null || metodoPago.isEmpty()) {
            return false;
        }

        Cupo cupo = buscarCupoOcupado(parqueadero, serial, cedula);
        if (cupo == null || cupo.obtenerHora().obtenerHoraSalida() == null) {
            return false;
        }

        double valor = calcularValor(cupo.obtenerHora());
        Pago pago = new Pago(valor, metodoPago, LocalDateTime.now());
        cupo.asignarPago(pago);
        return true;
    }

    public String generarReporte(){
        return "Reporte del dia: " + "\n" + " - Bicicletas registradas: " + totalBicicletas + "\n" + " - Valor total recaudado: " + dineroTotal + "Pesos";
    }
}