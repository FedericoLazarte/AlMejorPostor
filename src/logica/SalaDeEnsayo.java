package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import dao.OfertaDAO;

public class SalaDeEnsayo {
	private List<Oferta> ofertas;
    private OfertaDAO ofertaDAO;

    public SalaDeEnsayo() { 
    	this.ofertas = new ArrayList<>();
    }

    public void cargarOfertasSerializadas(OfertaDAO ofertaDAO) {
    	this.ofertaDAO = ofertaDAO;
        this.ofertas = ofertaDAO.cargarOfertas();
    }
    
    public void registrarOferta(Oferta oferta) {
        this.ofertas.add(oferta);
        if (ofertaDAO != null)
        	ofertaDAO.guardarOfertas(ofertas);
    }
    
    public double calcularGananciaTotal(List<Oferta> ofertasOptimas) {
        double gananciaTotal = 0;
        for (Oferta oferta : ofertasOptimas)
            gananciaTotal += oferta.getMonto();
        return gananciaTotal;
    }

    public List<String> obtenerOfertasDeFechaComoTexto(Date fechaActual) {
        List<String> ofertasTexto = new ArrayList<>();
        for (Oferta oferta : ofertas) {
        	if (oferta.getFecha().equals(fechaActual)) {
        		String ofertaTexto = "Oferta: " + oferta.getNombreOferente() + " - " + oferta.getInicio() + " - "
                        + oferta.getFin() + " - " + oferta.getEquipamiento() + " - " + ", Monto: $" + oferta.getMonto();
                ofertasTexto.add(ofertaTexto);
        	}  
        }
        return ofertasTexto;
    }

    public List<String> obtenerOfertasRegistradasComoTexto() {
    	List<String> ofertasTexto = new ArrayList<>();
    	for (Oferta oferta : ofertas) {
        		String ofertaTexto = "Oferta: " + oferta.getNombreOferente() + " - " + oferta.getInicio() + " - "
                        + oferta.getFin() + " - " + oferta.getEquipamiento() + " - " + ", Monto: $" + oferta.getMonto() + "\nFecha:" + oferta.getFechaTexto();
                ofertasTexto.add(ofertaTexto);  
        }
    	return ofertasTexto;
    }
    
    public List<Oferta> encontrarOfertasOptimas() {
        ordenarOfertasPorMontoYFin(); 
        List<Oferta> ofertasSeleccionadas = new ArrayList<>();
        int ultimaHoraFin = -1;  
        for (Oferta oferta : ofertas) {
            if (oferta.getInicio() >= ultimaHoraFin) {
                ofertasSeleccionadas.add(oferta);
                ultimaHoraFin = oferta.getFin(); 
            }
        }
        return ofertasSeleccionadas;
    }
    
    private void ordenarOfertasPorMontoYFin() {
        Collections.sort(ofertas, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta o1, Oferta o2) {
                int montoCompare = Double.compare(o2.getMonto(), o1.getMonto());
                if (montoCompare != 0) {
                    return montoCompare;
                }
                return Integer.compare(o1.getFin(), o2.getFin());
            }
        });
    }

	public List<Oferta> getOfertas() {
		return ofertas;
	}

}