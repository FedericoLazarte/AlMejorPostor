package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import logica.Oferta;

class OfertaTest {

    @Test
    void generarOfertaTest() {
        // Prueba con datos válidos
        Oferta oferta = new Oferta(10, 12, 500.0, "Oferente1", "Proyector");
        assertEquals(10, oferta.getInicio());
        assertEquals(12, oferta.getFin());
        assertEquals(500.0, oferta.getMonto());
        assertEquals("Oferente1", oferta.getNombreOferente());
        assertEquals("Proyector", oferta.getEquipamiento());
    }

    @Test
    void horaInicioTardeTest() {
        //lanza excepción cuando inicio es igual o mayor que fin
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(15, 15, 300.0, "Oferente2", "Proyector");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(16, 15, 300.0, "Oferente3", "Micrófono");
        });
    }

    @Test
    void horarioInvalidoTest() {
        //lanza excepción cuando el horario está fuera de rango
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(-1, 12, 300.0, "Oferente4", "Altavoces");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(10, 25, 300.0, "Oferente5", "Mesa de mezclas");
        });
    }

    @Test
    void montoVacioTest() {
        // Prueba que lanza excepción cuando el monto es menor o igual a 0
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(9, 12, 0, "Oferente6", "Micrófono");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(9, 12, -100, "Oferente7", "Monitor");
        });
    }

    @Test
    void nombreNuloTest() {
        // Prueba que lanza excepción cuando el nombre del oferente está vacío
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(8, 10, 100.0, "", "Monitor");
        });
    }

    @Test
    void equipamientoNuloTest() {
        // Prueba que lanza excepción cuando el equipamiento es inválido
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(8, 10, 100.0, "Oferente8", "");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(8, 10, 100.0, "Oferente9", "Equipamiento con más de veinte caracteres");
        });
    }

    @Test
    void toStringTest() {
        Oferta oferta = new Oferta(9, 11, 200.0, "Oferente10", "Proyector");
        String expected = "Oferta de 9 a 11 hs - Monto: $200.0 - Oferente: Oferente10 - Equipamiento: Proyector";
        assertEquals(expected, oferta.toString());
    }
}
