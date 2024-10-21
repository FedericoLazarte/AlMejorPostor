package controlador;

import java.util.List;

import logica.Oferta;
import logica.SalaDeEnsayo;

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
            System.out.println("Ofertas adjudicadas:");
            for (Oferta oferta : ofertasOptimas) {
                System.out.println(oferta);
            }
            System.out.println("Ganancia total: $" + gananciaTotal);
        } else {
            throw new IllegalArgumentException("No se encontraron ofertas para la sala por el momento");
        }
    }
    
    public List<String> obtenerOfertasComoTexto() {
        return sala.obtenerOfertasComoTexto();
    }

	public void ofertasPredeterminadas() {
		sala.ofertasPredeterminadas();
	}
}
