package unilibre;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.unilibre.datos.*;
import edu.unilibre.operaciones.GestionMetodos;

class GestionMetodosTest {

    private GestionMetodos gestion;
    private Parqueadero parqueadero;

    @BeforeEach
    void setUp() {
        gestion = new GestionMetodos();
        parqueadero = new Parqueadero();
    }

    @Test
    void RegistrarIngresoExitoso() {
        Cupo cupo = gestion.registrarIngreso(parqueadero, "SN123", "ABC-123", "100111", "Juan");
        assertNotNull(cupo);
        assertTrue(cupo.estaOcupado());
        assertEquals("SN123", cupo.obtenerBicicleta().obtenerSerial());
        assertEquals(19, parqueadero.obtenerEspaciosLibres());
    }

    @Test
    void RegistrarIngresoParametrosNulos() {
        assertNull(gestion.registrarIngreso(null, "SN123", "ABC-123", "100111", "Juan"));
        assertNull(gestion.registrarIngreso(parqueadero, null, "ABC-123", "100111", "Juan"));
    }

    @Test
    void RegistrarIngresoParqueaderoLleno() {
        //Llenar Cupos
        for (int i = 0; i < 20; i++) {
            gestion.registrarIngreso(parqueadero, "A" + i, "B" + i, "C" + i, "D" + i);
        }
        Cupo cupoExtra = gestion.registrarIngreso(parqueadero, "EXTRA", "EXT-99", "9999", "Pedro");
        assertNull(cupoExtra);
    }

    @Test
    void RegistrarSalidaExitoso() {
        gestion.registrarIngreso(parqueadero, "SN123", "ABC-123", "100111", "Juan");

        double valor = gestion.registrarSalida(parqueadero, "SN123", "1001");
        assertTrue(valor >= 0);
    }

    @Test
    void RegistrarSalidaNoEncontrado() {
        // Bicicleta que no existe
        double valor = gestion.registrarSalida(parqueadero, "FALSO", "0000");
        assertEquals(-1.0, valor);
    }

    @Test
    void CalcularValorHoraNula() {
        assertEquals(0.0, gestion.calcularValor(null));
        Hora horaIncompleta = new Hora();
        assertEquals(0.0, gestion.calcularValor(horaIncompleta));
    }

    @Test
    void RegistrarPagoExitoso() {
        gestion.registrarIngreso(parqueadero, "SN123", "ABC-123", "100111", "Juan");
        gestion.registrarSalida(parqueadero, "SN123", "100111");

        boolean resultado = gestion.registrarPago(parqueadero, "SN123", "100111", "Efectivo");
        assertTrue(resultado);
    }

    @Test
    void RegistrarPagoFallido() {
        assertFalse(gestion.registrarPago(parqueadero, "SN123", "1001", null));
        assertFalse(gestion.registrarPago(parqueadero, "SN123", "1001", ""));

        // Bicicleta no registrada
        assertFalse(gestion.registrarPago(parqueadero, "FALSO", "0000", "Tarjeta"));

        // Cupo sin hora de salida
        gestion.registrarIngreso(parqueadero, "SN123", "ABC-123", "100111", "Juan");
        assertFalse(gestion.registrarPago(parqueadero, "SN123", "100111", "Efectivo"));
    }

    @Test
    void LiberarCupoExitoso() {
        gestion.registrarIngreso(parqueadero, "SN123", "ABC-123", "100111", "Juan");
        gestion.registrarSalida(parqueadero, "SN123", "100111");
        gestion.registrarPago(parqueadero, "SN123", "100111", "Efectivo");

        boolean liberado = gestion.liberarCupo(parqueadero, "SN123", "100111");
        assertTrue(liberado);
        assertEquals(20, parqueadero.obtenerEspaciosLibres());
    }

    @Test
    void LiberarCupoSinPago() {
        gestion.registrarIngreso(parqueadero, "SN123", "ABC-12", "1001", "Juan");
        boolean liberado = gestion.liberarCupo(parqueadero, "SN123", "1001");
        assertFalse(liberado);
    }

    @Test
    void GenerarReporte() {
        gestion.registrarIngreso(parqueadero, "SN123", "ABC-12", "1001", "Juan");
        gestion.registrarSalida(parqueadero, "SN123", "1001");
        gestion.registrarPago(parqueadero, "SN123", "1001", "Efectivo");
        gestion.liberarCupo(parqueadero, "SN123", "1001");

        String reporte = gestion.generarReporte();
        assertNotNull(reporte);
        assertTrue(reporte.contains("Bicicletas registradas: 1"));
    }
}
