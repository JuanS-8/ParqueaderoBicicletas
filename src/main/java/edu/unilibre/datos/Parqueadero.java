package edu.unilibre.datos;
import java.util.ArrayList;
import java.util.List;

public class Parqueadero {
    private int capacidad;
    private List<Cupo> cupos;

    public Parqueadero() {
        this.capacidad = 20;
        this.cupos = new ArrayList<>();
        for (int i = 1; i <= capacidad; i++) {
            cupos.add(new Cupo(i));
        }
    }

    public Parqueadero(int capacidad) {
        this.capacidad = capacidad;
        this.cupos = new ArrayList<>();
        for (int i = 1; i <= capacidad; i++) {
            cupos.add(new Cupo(i));
        }
    }

    public int obtenerCapacidad() {
        return capacidad;
    }

    public void asignarCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Cupo> obtenerCupos() {
        return cupos;
    }

    public void asignarCupos(List<Cupo> cupos) {
        this.cupos = cupos;
    }
}
