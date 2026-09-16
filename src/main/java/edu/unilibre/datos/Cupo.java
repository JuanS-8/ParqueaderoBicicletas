package edu.unilibre.datos;

public class Cupo {
    private int numero;
    private boolean ocupado;

    // Datos ocupado = true
    private Bicicleta bicicleta;
    private Propietario propietario;
    private Hora hora;
    private Pago pago;

    public Cupo() {
        this.ocupado = false;
    }

    public Cupo(int numero) {
        this.numero = numero;
        this.ocupado = false;
    }

    public int obtenerNumero() {
        return numero;
    }

    public void asignarNumero(int numero) {
        this.numero = numero;
    }

    public boolean estaOcupado() {
        return ocupado;
    }

    public void asignarOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public Bicicleta obtenerBicicleta() {
        return bicicleta;
    }

    public void asignarBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    public Propietario obtenerPropietario() {
        return propietario;
    }

    public void asignarPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Hora obtenerHora() {
        return hora;
    }

    public void asignarHora(Hora hora) {
        this.hora = hora;
    }

    public Pago obtenerPago() {
        return pago;
    }

    public void asignarPago(Pago pago) {
        this.pago = pago;
    }

}