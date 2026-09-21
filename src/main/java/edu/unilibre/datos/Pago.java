package edu.unilibre.datos;
import java.time.LocalDateTime;

public class Pago {
    private double valor;
    private String metodoPago;
    private LocalDateTime fecha;


    public Pago(double valor, String metodoPago, LocalDateTime fecha) {
        this.valor = valor;
        this.metodoPago = metodoPago;
        this.fecha = fecha;
    }

    public double obtenerValor() {
        return valor;
    }

    public void asignarValor(double valor) {
        this.valor = valor;
    }

    public String obtenerMetodoPago() {
        return metodoPago;
    }

    public void asignarMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public LocalDateTime obtenerFecha() {
        return fecha;
    }

    public void asignarFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
