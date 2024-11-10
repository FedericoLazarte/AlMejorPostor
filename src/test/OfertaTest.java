package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import logica.Oferta;

class OfertaTest {

	 @Test
	    void rangoHorarioInvalidoTest() {
	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new Oferta(25, 30, 100.0, "Goku", "Esfera", new Date());  // Horario inválido (fuera del rango 0-24)
	        });
	        assertEquals("El horario es inválido, debería encontrarse entre las 01 y 24 hs", exception.getMessage());
	    }

	    @Test
	    void montoInvalidoTest() {
	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new Oferta(9, 12, -100.0, "Goku", "Esfera", new Date());  // Monto negativo
	        });
	        assertEquals("La oferta debe ser mayor a 0", exception.getMessage());
	    }

	    @Test
	    void equipamientoNuloTest() {
	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new Oferta(9, 12, 100.0, "Goku", null, new Date());  // Equipamiento nulo
	        });
	        assertEquals("Por favor ingrese un equipamiento válido", exception.getMessage());
	    }

	    @Test
	    void nombreVacioTest() {
	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new Oferta(9, 12, 100.0, "", "Esfera", new Date());  // Nombre vacío
	        });
	        assertEquals("Por favor ingrese un nombre valido", exception.getMessage());
	    }

	    @Test
	    void fechaNulaTest() {
	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new Oferta(9, 12, 100.0, "Goku", "Esfera", null);  // Fecha nula
	        });
	        assertEquals("La fecha no puede ser nula.", exception.getMessage());
	    }

	    @Test
	    void horarioInvalidoTest() {
	        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	            new Oferta(15, 12, 100.0, "Goku", "Esfera", new Date());  // Hora de inicio mayor que la hora de fin
	        });
	        assertEquals("El horario de inicio no puede ser igual ni mayor al horario de fin", exception.getMessage());
	    }

	}