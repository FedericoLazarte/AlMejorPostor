package controlador;

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
   
    public void iniciarOfertas() {
	    List<Oferta> ofertasOptimas = sala.encontrarOfertasOptimas();
	    if (!ofertasOptimas.isEmpty()) {
	        int gananciaTotal = sala.calcularGananciaTotal(ofertasOptimas);
	        StringBuilder sb = new StringBuilder();
	        sb.append("Ofertas adjudicadas:\n");
	        for (Oferta oferta : ofertasOptimas) {
	            sb.append(oferta).append("\n");
	        }
	        sb.append("Ganancia total: $").append(gananciaTotal);
	        JOptionPane.showMessageDialog(null, sb.toString());  // Mostrar cuadro de diálogo
	    } else {
	        JOptionPane.showMessageDialog(null, "No se encontraron ofertas para la sala por el momento");
	    }
	}
    
    
    
    public List<String> obtenerOfertasComoTexto() {
        return sala.obtenerOfertasComoTexto();
    }

	public void ofertasPredeterminadas() {
		sala.ofertasPredeterminadas();
	}

	

	

	
}
