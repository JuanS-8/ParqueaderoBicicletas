package edu.unilibre.gui;

import edu.unilibre.datos.Parqueadero;
import edu.unilibre.operaciones.GestionMetodos;


public class MenuPrincipal {

    private  Parqueadero parqueadero = new Parqueadero();
    private  GestionMetodos gestion = new GestionMetodos();

    public void mostrarMenu() {

    }

    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.mostrarMenu();
    }
}
