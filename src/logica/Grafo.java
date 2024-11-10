package logica;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Clase que representa el grafo de ofertas
class Grafo {
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
            maxGanancia.put(v, 0.0);
            predecesor.put(v, null);
        }

        for (Oferta actual : vertices) {
            double gananciaActual = actual.getMonto();
            
            // Exploramos todos los vértices siguientes compatibles
            for (Arista arista : listaAdyacencia.get(actual)) {
                Oferta siguiente = arista.getDestino();
                double nuevaGanancia = gananciaActual + maxGanancia.get(siguiente);
                
                if (nuevaGanancia > maxGanancia.get(actual)) {
                    maxGanancia.put(actual, nuevaGanancia);
                    predecesor.put(actual, siguiente);
                }
            }
        }

        // Encontramos el vértice con la máxima ganancia
        Oferta inicio = vertices.stream()
            .max(Comparator.comparingDouble(v -> maxGanancia.get(v)))
            .orElse(null);

        // Reconstruimos el camino
        return reconstruirCamino(inicio, predecesor);
    }

    private List<Oferta> reconstruirCamino(Oferta inicio, Map<Oferta, Oferta> predecesor) {
        List<Oferta> camino = new ArrayList<>();
        Oferta actual = inicio;
        
        while (actual != null) {
            camino.add(actual);
            actual = predecesor.get(actual);
        }
        
        return camino;
    }
}