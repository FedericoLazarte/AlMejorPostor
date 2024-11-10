package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logica.Oferta;
import logica.SalaDeEnsayo;
import dao.OfertaDAO;

import static org.junit.jupiter.api.Assertions.*;

class SalaDeEnsayoTest {

    private SalaDeEnsayo salaDeEnsayo;
    private File archivoOriginal = new File("ofertas.txt");
    private File archivoTemporal = new File("ofertas_temp.txt");

    @BeforeEach
    void setUp() throws IOException, ParseException {
        // Copiar el archivo original a un archivo temporal antes de cada prueba
        if (archivoOriginal.exists()) {
            Files.copy(archivoOriginal.toPath(), archivoTemporal.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            archivoTemporal.createNewFile(); // Crea un archivo vacío si el original no existe
        }

        // Crear instancia de SalaDeEnsayo usando el archivo temporal para evitar modificar datos reales
        OfertaDAO ofertaDAO = new OfertaDAO("ofertas_temp.txt");
        salaDeEnsayo = new SalaDeEnsayo(ofertaDAO);
    }

    @AfterEach
    void tearDown() {
        // Eliminar el archivo temporal después de cada prueba
        archivoTemporal.delete();
    }

    @Test
    void registrarOfertaTest() throws ParseException {
        Date fechaPrueba = new SimpleDateFormat("yyyy-MM-dd").parse("2024-11-09");
        Oferta oferta = new Oferta(9, 12, 100.0, "Goku", "Esfera", fechaPrueba);
        salaDeEnsayo.registrarOferta(oferta);

        List<String> ofertasTexto = salaDeEnsayo.obtenerOfertasRegistradasComoTexto(fechaPrueba);
        assertEquals(1, ofertasTexto.size());
        assertTrue(ofertasTexto.get(0).contains("Goku"));
    }

    @Test
    void horaInvalidaTest() throws ParseException {
        Date fechaPrueba = new SimpleDateFormat("yyyy-MM-dd").parse("2024-11-09");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(15, 12, 100.0, "Goku", "Esfera", fechaPrueba);  // Hora de inicio después de hora de fin
        });

        assertEquals("El horario de inicio no puede ser igual ni mayor al horario de fin", exception.getMessage());
    }

    @Test
    void equipamientoNuloTest() throws ParseException {
        Date fechaPrueba = new SimpleDateFormat("yyyy-MM-dd").parse("2024-11-09");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(9, 12, 100.0, "Goku", null, fechaPrueba);  // Equipamiento nulo
        });

        assertEquals("Por favor ingrese un equipamiento válido", exception.getMessage());
    }

    @Test
    void montoInvalidoTest() throws ParseException {
        Date fechaPrueba = new SimpleDateFormat("yyyy-MM-dd").parse("2024-11-09");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Oferta(9, 12, -100.0, "Goku", "Esfera", fechaPrueba);
        });

        assertEquals("La oferta debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void calcularGananciaTotalTest() {
        List<Oferta> ofertasOptimas = new ArrayList<>();
        ofertasOptimas.add(new Oferta(9, 12, 100.0, "Goku", "Esfera", new Date()));
        ofertasOptimas.add(new Oferta(13, 15, 200.0, "Vegeta", "Nave", new Date()));

        double gananciaTotal = salaDeEnsayo.calcularGananciaTotal(ofertasOptimas);
        assertEquals(300.0, gananciaTotal, 0.01);
    }

    @Test
    void obtenerOfertasRegistradasComoTextoTest() throws ParseException {
        Date fechaPrueba = new SimpleDateFormat("yyyy-MM-dd").parse("2024-11-09");

        Oferta oferta1 = new Oferta(9, 12, 100.0, "Goku", "Esfera", fechaPrueba);
        Oferta oferta2 = new Oferta(14, 16, 200.0, "Vegeta", "Nave", fechaPrueba);

        salaDeEnsayo.registrarOferta(oferta1);
        salaDeEnsayo.registrarOferta(oferta2);

        List<String> ofertasTexto = salaDeEnsayo.obtenerOfertasRegistradasComoTexto(fechaPrueba);

        assertEquals(2, ofertasTexto.size());
        assertTrue(ofertasTexto.get(0).contains("Goku"));
        assertTrue(ofertasTexto.get(1).contains("Vegeta"));
    }

    @Test
    void encontrarOfertasOptimasTest() {
        Date fechaActual = new Date();
        Oferta oferta1 = new Oferta(9, 12, 100.0, "Goku", "Esfera", fechaActual);
        Oferta oferta2 = new Oferta(13, 16, 200.0, "Vegeta", "Nave", fechaActual);
        Oferta oferta3 = new Oferta(16, 18, 150.0, "Trunks", "Espada", fechaActual);

        salaDeEnsayo.registrarOferta(oferta1);
        salaDeEnsayo.registrarOferta(oferta2);
        salaDeEnsayo.registrarOferta(oferta3);

        List<Oferta> ofertasOptimas = salaDeEnsayo.encontrarOfertasOptimas(fechaActual);

        assertEquals(2, ofertasOptimas.size());
        assertTrue(ofertasOptimas.contains(oferta2));
        assertTrue(ofertasOptimas.contains(oferta3));
    }
}