package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Clase que representa el grafo de ofertas
public class Grafo {
    private Map<Oferta, List<Arista>> listaAdyacencia;
    private List<Oferta> vertices;

    public Grafo() {
        this.listaAdyacencia = new HashMap<>();
        this.vertices = new ArrayList<>();
    }

    public void agregarVertice(Oferta oferta) {
        vertices.add(oferta);
        listaAdyacencia.putIfAbsent(oferta, new ArrayList<>());
    }

    public void agregarArista(Oferta origen, Oferta destino) {
    	if (!vertices.contains(origen)|| !vertices.contains(destino)) {
    		throw new IllegalArgumentException("Ambos vértices deben existir en el grafo.");
    	}
        // Solo agregamos arista si el destino comienza después de que termina el origen
        if (origen.getFin() <= destino.getInicio()) {
            listaAdyacencia.get(origen).add(new Arista(origen, destino, origen.getMonto()));
        }
    }

    public List<Oferta> encontrarCaminoMaximo() {
        // Ordenamos los vértices por hora de inicio
        vertices.sort(Comparator.comparingInt(Oferta::getInicio));
        
        Map<Oferta, Double> maxGanancia = new HashMap<>();
        Map<Oferta, Oferta> predecesor = new HashMap<>();
        
        for (Oferta v : vertices) {
            maxGanancia.put(v, v.getMonto());
            predecesor.put(v, null);
        }

        for (Oferta actual : vertices) {
            for (Arista arista : listaAdyacencia.get(actual)) {
                Oferta siguiente = arista.getDestino();
                double nuevaGanancia = maxGanancia.get(actual) + siguiente.getMonto();
                
                if (nuevaGanancia > maxGanancia.get(siguiente)) {
                    maxGanancia.put(siguiente, nuevaGanancia);
                    predecesor.put(siguiente, actual);
                }
            }
        }

        // Encontramos el vértice con la máxima ganancia
        Oferta fin = vertices.stream()
            .max(Comparator.comparingDouble(v -> maxGanancia.get(v)))
            .orElse(null);

        // Reconstruimos el camino
        return reconstruirCamino(fin, predecesor);
    }

    private List<Oferta> reconstruirCamino(Oferta fin, Map<Oferta, Oferta> predecesor) {
        List<Oferta> camino = new ArrayList<>();
        Oferta actual = fin;
        
        while (actual != null) {
            camino.add(actual);
            actual = predecesor.get(actual);
        }
        
        // Invertimos el camino para que quede en orden de inicio a fin
        Collections.reverse(camino);
        
        return camino;
    }

    public Map<Oferta, List<Arista>> getAristas() {
        return listaAdyacencia;
    }

    public List<Oferta> getVertices() {
        return vertices;
    }
}