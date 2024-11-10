package logica;

class Arista {
    private Oferta origen;
    private Oferta destino;
    private double peso;  // El peso será la ganancia de la oferta origen

    public Arista(Oferta origen, Oferta destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public Oferta getOrigen() { return origen; }
    public Oferta getDestino() { return destino; }
    public double getPeso() { return peso; }
}