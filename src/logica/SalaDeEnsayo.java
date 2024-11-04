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
        if (ofertas == null)
            this.ofertas = new ArrayList<>();
    }

    public void registrarOferta(Oferta oferta) {
        this.ofertas.add(oferta);
        ofertaDAO.guardarOfertas(ofertas);
    }

    public List<Oferta> encontrarOfertasOptimas() {
        ordenarOfertasPorMontoYHoraFin();  // Ordenar ofertas de forma óptima

        List<Oferta> ofertasOptimas = new ArrayList<>();
        int ultimaHoraFin = 0;

        for (Oferta oferta : ofertas) {
            // Seleccionamos solo si no hay solapamiento con la última oferta seleccionada
            if (oferta.getInicio() >= ultimaHoraFin) {
                ofertasOptimas.add(oferta);
                ultimaHoraFin = oferta.getFin();  // Actualizamos la última hora de fin seleccionada
            }
        }
        return ofertasOptimas;
    }

    public double calcularGananciaTotal(List<Oferta> ofertasOptimas) {
        double gananciaTotal = 0;
        for (Oferta oferta : ofertasOptimas)
            gananciaTotal += oferta.getMonto();
        return gananciaTotal;
    }

    public List<String> obtenerOfertasRegistradasComoTexto() {
        List<String> ofertasTexto = new ArrayList<>();
        for (Oferta oferta : ofertas) {
            String ofertaTexto = "Oferta: " + oferta.getNombreOferente() + " - " + oferta.getInicio() + " - "
                    + oferta.getFin() + " - " + oferta.getEquipamiento() + " - " + ", Monto: $" + oferta.getMonto();
            ofertasTexto.add(ofertaTexto);
        }
        return ofertasTexto;
    }

    
    private void ordenarOfertasPorMontoYHoraFin() {
        // Ordenar por monto (descendente) y luego por hora de fin (ascendente)
        Collections.sort(ofertas, new Comparator<Oferta>() {
            @Override
            public int compare(Oferta o1, Oferta o2) {
                if (Double.compare(o2.getMonto(), o1.getMonto()) != 0) {
                    return Double.compare(o2.getMonto(), o1.getMonto()); // Primero monto descendente
                } else {
                    return Integer.compare(o1.getFin(), o2.getFin());    // Luego fin ascendente
                }
            }
        });
    }
}
