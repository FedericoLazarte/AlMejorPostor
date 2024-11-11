package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import logica.Arista;
import logica.Grafo;
import logica.Oferta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrafoTest {
    private Grafo grafo;
    private Oferta oferta1;
    private Oferta oferta2;
    private Oferta oferta3;
    private Oferta oferta4;

    @BeforeEach
    void setup() {
        oferta1 = new Oferta(9, 10, 100.0, "Goku", "Esfera", new Date());
        oferta2 = new Oferta(11, 13, 200.0, "Vegeta", "Nave", new Date());
        oferta3 = new Oferta(14, 15, 300.0, "Trunks", "Espada", new Date());
        oferta4 = new Oferta(18, 19, 250.0, "Gohan", "Libros", new Date());
        
        grafo = new Grafo();
        grafo.agregarVertice(oferta1);
        grafo.agregarVertice(oferta2);
        grafo.agregarVertice(oferta3);
        grafo.agregarVertice(oferta4);
    }

    @Test
    void agregarVerticeTest() {
        Oferta oferta5 = new Oferta(19, 21, 350.0, "Piccolo", "Semillas", new Date());
        grafo.agregarVertice(oferta5);
        
        assertEquals(5, grafo.getVertices().size());
        assertTrue(grafo.getVertices().contains(oferta5));
    }
    
    @Test
    void agregarAristaTest() {
        grafo.agregarArista(oferta1, oferta2);

        Map<Oferta, List<Arista>> listaAdyacencia = grafo.getAristas();
        assertTrue(listaAdyacencia.get(oferta1).stream().anyMatch(arista -> arista.getDestino().equals(oferta2)));
        assertEquals(1, listaAdyacencia.get(oferta1).size());
    }
    
	@Test
	void agregarAristaEntreVerticesInexistentesTest() {
		Oferta ofertaInexistente = new Oferta(20, 21, 400.0, "Piccolo", "Semillas", new Date());
		try {
			grafo.agregarArista(oferta1, ofertaInexistente);
			fail("Se esperaba una excepción para arista entre vértices inexistentes.");
		} catch (IllegalArgumentException e) {
			assertEquals("Ambos vértices deben existir en el grafo.", e.getMessage());
		}
	}

    
    @Test
    void encontrarCaminoMaximoTest() {
        // agregar aristas para construir el grafo
        grafo.agregarArista(oferta1, oferta2); // 100 -> 200
        grafo.agregarArista(oferta2, oferta3); // 200 -> 300
        grafo.agregarArista(oferta3, oferta4); // 300 -> 250

        // crear camino de máxima ganancia esperado: 100 + 200 + 300 + 250
        List<Oferta> caminoMaximo = grafo.encontrarCaminoMaximo();
        
        // verificar el tamaño del camino
        assertEquals(4, caminoMaximo.size());
        assertEquals(oferta1, caminoMaximo.get(0));
        assertEquals(oferta2, caminoMaximo.get(1));
        assertEquals(oferta3, caminoMaximo.get(2));
        assertEquals(oferta4, caminoMaximo.get(3));

        // verificar la ganancia total del camino
        double gananciaTotal = caminoMaximo.stream().mapToDouble(Oferta::getMonto).sum();
        assertEquals(850.0, gananciaTotal);  // 100 + 200 + 300 + 250
    }
}