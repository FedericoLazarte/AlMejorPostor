package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Oferta;
import logica.SalaDeEnsayo;

import java.util.List;

class SalaDeEnsayoTest {

    private SalaDeEnsayo salaDeEnsayo;
    private Oferta oferta1;
    private Oferta oferta2;
    private Oferta oferta3;

    @BeforeEach
    void setUp() {
        salaDeEnsayo = new SalaDeEnsayo();
        setUpOfertas();
    }

    void setUpOfertas() {
        oferta1 = new Oferta(9, 11, 200.0, "Goku", "Proyector");
        oferta2 = new Oferta(11, 13, 250.0, "Vegeta", "Micrófono");
        oferta3 = new Oferta(10, 12, 150.0, "Gohan", "Altavoces");

        salaDeEnsayo.registrarOferta(oferta1);
        salaDeEnsayo.registrarOferta(oferta2);
        salaDeEnsayo.registrarOferta(oferta3);
    }
    
    @Test
    void cantidadOfertasTest() {
    	assertEquals(3, salaDeEnsayo.getOfertas().size());
    }

    @Test
    void registrarOfertaTest() {
        Oferta nuevaOferta = new Oferta(14, 16, 300.0, "Trunks", "Iluminación");
        salaDeEnsayo.registrarOferta(nuevaOferta);
        
        List<String> ofertasTexto = salaDeEnsayo.obtenerOfertasComoTexto();
        //assertEquals(4, ofertasTexto.size()); // 3 del setup + 1 nueva
        assertTrue(ofertasTexto.get(3).contains("Trunks"));
        assertTrue(ofertasTexto.get(3).contains("300.0"));
    }

    @Test
    void ofertasOptimasTest() {
        List<Oferta> ofertasOptimas = salaDeEnsayo.encontrarOfertasOptimas();

        assertEquals(2, ofertasOptimas.size());
        assertTrue(ofertasOptimas.contains(oferta1));
        assertTrue(ofertasOptimas.contains(oferta2));
    }

    @Test
    void gananciaTotalTest() {
        List<Oferta> ofertasOptimas = salaDeEnsayo.encontrarOfertasOptimas();
        double gananciaTotal = salaDeEnsayo.calcularGananciaTotal(ofertasOptimas);

        assertEquals(450.0, gananciaTotal);
    }

    @Test
    void ofertasComoTextoTest() {
        List<String> ofertasTexto = salaDeEnsayo.obtenerOfertasComoTexto();

        assertEquals(3, ofertasTexto.size());
        assertEquals("Oferta: Goku - 9 - 11 - Proyector - , Monto: $200.0", ofertasTexto.get(0));
        assertEquals("Oferta: Vegeta - 11 - 13 - Micrófono - , Monto: $250.0", ofertasTexto.get(1));
        assertEquals("Oferta: Gohan - 10 - 12 - Altavoces - , Monto: $150.0", ofertasTexto.get(2));
    }

    @Test
    void ordenarOfertasTest() {
        List<Oferta> ofertasOptimas = salaDeEnsayo.encontrarOfertasOptimas();

        assertEquals(oferta1, ofertasOptimas.get(0));
        assertEquals(oferta2, ofertasOptimas.get(1));
    }
}
