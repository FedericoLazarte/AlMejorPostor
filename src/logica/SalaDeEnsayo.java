package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dao.OfertaDAO;

public class SalaDeEnsayo {
	private List<Oferta> ofertas;
	private OfertaDAO ofertaDAO;
	
	public SalaDeEnsayo() {
		this.ofertaDAO = new OfertaDAO();
		this.ofertas = ofertaDAO.cargarOfertas();
		if(this.ofertas == null)
			this.ofertas = new ArrayList<>();
	}
	
	public void registrarOferta(Oferta oferta) {
		this.ofertas.add(oferta);
		ofertaDAO.guardarOfertas(ofertas);
	}
	
	public List<Oferta> encontrarOfertasOptimas() {
		ordenarOfertasPorHoraFin();
		List<Oferta> ofertasOptimas = new ArrayList<>();
		int ultimaHoraFin = 0;
		for(Oferta oferta : ofertas)
			if(oferta.getInicio() >= ultimaHoraFin) {
				ofertasOptimas.add(oferta);
				ultimaHoraFin = oferta.getFin();
			}
		return ofertasOptimas;
	}
	
	public int calcularGananciaTotal(List<Oferta> ofertasOptimas) {
		int gananciaTotal = 0;
		for(Oferta oferta : ofertasOptimas)
			gananciaTotal += oferta.getMonto();
		return gananciaTotal;
	}
	
	public List<String> obtenerOfertasComoTexto() {
		List<String> ofertasTexto = new ArrayList<>();
        for (Oferta oferta : ofertas) {
            String ofertaTexto = "Oferta: " + oferta.getInicio() + " - " + oferta.getFin() + ", Monto: $" + oferta.getMonto();
            ofertasTexto.add(ofertaTexto);
        }
        return ofertasTexto;
	}
	
	 public void ofertasPredeterminadas() {
	        registrarOferta(new Oferta(14, 16, 1200));
	        registrarOferta(new Oferta(15, 18, 1800));
	        registrarOferta(new Oferta(9, 17, 2600));
	        registrarOferta(new Oferta(18, 20, 1200));
	        registrarOferta(new Oferta(9, 12, 800));    
	        registrarOferta(new Oferta(5, 10, 1600));
	        registrarOferta(new Oferta(11, 14, 1400));
	        registrarOferta(new Oferta(12, 15, 900));
	        registrarOferta(new Oferta(13, 16, 1200));
	        registrarOferta(new Oferta(14, 17, 1500));
	        registrarOferta(new Oferta(11, 18, 1000));
	        registrarOferta(new Oferta(16, 20, 1300));
	        registrarOferta(new Oferta(17, 20, 1600));
	        registrarOferta(new Oferta(13, 22, 1100));
	        registrarOferta(new Oferta(19, 22, 1400));
	        registrarOferta(new Oferta(22, 23, 1200));
	        registrarOferta(new Oferta(1, 23, 20000));
	    }
	
	private void ordenarOfertasPorHoraFin() {
        Collections.sort(ofertas, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta o1, Oferta o2) {
                return Integer.compare(o1.getFin(), o2.getFin());
            }
        });
    }
}
