package logica;

import java.io.Serializable;

public class Oferta implements Serializable {
    private static final long serialVersionUID = 1L;
    private int inicio;
    private int fin;
    private double monto;
    private String nombreOferente;
    private String equipamiento;

    public Oferta(int inicio, int fin, double monto, String nombreOferente, String equipamiento) {
        esHorarioValido(inicio);
        esHorarioValido(fin);
        esInicioFinValido(inicio, fin);
        esMontoValido(monto);
        esEquipamientoValido(equipamiento);
        esNombreValido(nombreOferente);
        this.inicio = inicio;
        this.fin = fin;
        this.monto = monto;
        this.nombreOferente = nombreOferente;
        this.equipamiento = equipamiento;
    }

    public int getInicio() {
        return this.inicio;
    }

    public int getFin() {
        return this.fin;
    }

    public double getMonto() {
        return this.monto;
    }

    public String getNombreOferente() {
        return nombreOferente; 
    }

    public String getEquipamiento() {
        return equipamiento; 
    }

    @Override
    public String toString() {
        return "Oferta de " + inicio + " a " + fin + " hs - Monto: $" + monto +
               " - Oferente: " + nombreOferente + " - Equipamiento: " + equipamiento;
    }

    private void esHorarioValido(int horario) {
        if (horario < 0 || horario > 24)
            throw new IllegalArgumentException("El horario es inválido, debería encontrarse entre las 01 y 24 hs");
    }

    private void esInicioFinValido(int inicio, int fin) {
        if (inicio >= fin)
            throw new IllegalArgumentException("El horario de inicio no puede ser igual ni mayor al horario de fin");
    }

    private void esMontoValido(double monto) {
        if (monto <= 0)
            throw new IllegalArgumentException("La oferta debe ser mayor a 0");
    }
    
    private void esEquipamientoValido(String equipamiento ) {
        if (equipamiento.length() <= 0 || equipamiento.length() > 20)
            throw new IllegalArgumentException("Por favor ingrese un equipamiento valido");
    }
    
    private void esNombreValido(String nombre ) {
        if (nombre.length() <= 0)
            throw new IllegalArgumentException("Por favor ingrese un nombre valido");
    }
}
