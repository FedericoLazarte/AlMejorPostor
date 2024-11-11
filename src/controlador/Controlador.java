package controlador;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dao.OfertaDAO;
import logica.Oferta;
import logica.SalaDeEnsayo;


public class Controlador {
	private SalaDeEnsayo sala;

	 // Constructor que recibe una instancia de SalaDeEnsayo, para los tests
    public Controlador(SalaDeEnsayo sala) {
        this.sala = sala;
    }

    public Controlador() {
        this(new SalaDeEnsayo());
    }

	public void crearOferta(Oferta oferta) {
		cargarOfertas(oferta);
	}

	public void cargarOfertas(Oferta oferta) {
		this.sala.registrarOferta(oferta);
	}

	public void cargarOfertasSerializadas() {
		this.sala.cargarOfertasSerializadas(new OfertaDAO());
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
	        JOptionPane.showMessageDialog(null, "No se encontraron ofertas para la fecha seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
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
}