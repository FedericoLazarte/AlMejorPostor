package controlador;

import java.util.Date;
import java.util.List;

import logica.Oferta;
import logica.SalaDeEnsayo;
import javax.swing.JOptionPane;

public class Controlador {
	private SalaDeEnsayo sala;

	public Controlador() {
		this.sala = new SalaDeEnsayo();
	}

	public void crearOferta(Oferta oferta) {
		cargarOfertas(oferta);
	}

	public void cargarOfertas(Oferta oferta) {
		this.sala.registrarOferta(oferta);
	}

	public String obtenerAdjudicadasComoTexto(Date fechaActual) {
		List<Oferta> ofertasOptimas = sala.encontrarOfertasOptimas(fechaActual);
		if (!ofertasOptimas.isEmpty()) {
			double gananciaTotal = sala.calcularGananciaTotal(ofertasOptimas);
			StringBuilder sb = new StringBuilder();
			for (Oferta oferta : ofertasOptimas) {
				sb.append(oferta).append("\n");
			}
			sb.append("Ganancia total: $").append(gananciaTotal);
			return sb.toString();
		} else {
			return "No se encontraron ofertas para la sala por el momento";
		}
	}

	public String obtenerRegistradasComoTexto(Date fechaActual) {
		List<String> ofertasRegistradas = sala.obtenerOfertasRegistradasComoTexto(fechaActual);
		StringBuilder sb = new StringBuilder();
		for (String oferta : ofertasRegistradas) {
			sb.append(oferta).append("\n");
		}
		return sb.toString();
	}
	
	public void imprimirOfertas() { //¡¡ES DE PRUEBA!!
		sala.imprimirOfertas();
	}
}