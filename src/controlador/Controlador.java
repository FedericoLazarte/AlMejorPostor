package controlador;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dao.OfertaDAO;
import logica.Oferta;
import logica.SalaDeEnsayo;
import logica.Grafo;

public class Controlador {
	private SalaDeEnsayo sala;
	private Grafo grafo;

// Constructor que recibe una instancia de SalaDeEnsayo, para los tests
	public Controlador(SalaDeEnsayo sala) {
		this.sala = sala;
		this.grafo = new Grafo();
	}

	public Controlador() {
		this(new SalaDeEnsayo());
	}

	public void crearOferta(Oferta oferta) {
		cargarOfertas(oferta);
	}

	public void cargarOfertas(Oferta oferta) {
		this.sala.registrarOferta(oferta);
		this.grafo.agregarVertice(oferta);
	}

	public void cargarOfertasSerializadas() {
		this.sala.cargarOfertasSerializadas(new OfertaDAO());
		construirGrafo();
	}

	private void construirGrafo() {
		this.grafo = new Grafo();
		List<Oferta> ofertas = sala.getOfertas();
		for (Oferta oferta : ofertas) {
			grafo.agregarVertice(oferta);
		}
		for (Oferta origen : ofertas) {
			for (Oferta destino : ofertas) {
				if (!origen.equals(destino)) {
					grafo.agregarArista(origen, destino);
				}
			}
		}
	}

	public void obtenerAdjudicadasComoTexto(Date fechaActual) {
		List<Oferta> ofertasOptimas = sala.encontrarOfertasOptimas();
		if (!ofertasOptimas.isEmpty()) {
			double gananciaTotal = sala.calcularGananciaTotal(ofertasOptimas);
			StringBuilder sb = new StringBuilder();
			for (Oferta oferta : ofertasOptimas) {
				sb.append(oferta).append("\n");
			}
			sb.append("Ganancia total: $").append(gananciaTotal);
			JOptionPane.showMessageDialog(null, sb.toString());
		} else {
			JOptionPane.showMessageDialog(null, "No se encontraron ofertas para la fecha seleccionada.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public String obtenerOfertasDeFecha(Date fechaActual) {
		List<String> ofertasRegistradas = sala.obtenerOfertasDeFechaComoTexto(fechaActual);
		StringBuilder sb = new StringBuilder();
		for (String oferta : ofertasRegistradas) {
			sb.append(oferta).append("\n");
		}
		return sb.toString();
	}

	public String obtenerOfertasRegistradas() {
		List<String> ofertasRegistradas = sala.obtenerOfertasRegistradasComoTexto();
		StringBuilder sb = new StringBuilder();
		for (String oferta : ofertasRegistradas) {
			sb.append(oferta).append("\n");
		}
		return sb.toString();
	}

	public List<Oferta> obtenerCaminoMaximoEnGrafo() {
		return grafo.encontrarCaminoMaximo();
	}
}