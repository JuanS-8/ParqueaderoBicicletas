package edu.unilibre.datos;

public class Bicicleta {
        private String serial;
        private String placa;

        public Bicicleta(String serial, String placa) {
            this.serial = serial;
            this.placa = placa;
        }

        public String obtenerSerial() {
            return serial;
        }

        public void asignarSerial(String serial) {
            this.serial = serial;
        }

        public String obtenerPlaca() {
            return placa;
        }

        public void asignarPlaca(String placa) {
            this.placa = placa;
        }
    }

