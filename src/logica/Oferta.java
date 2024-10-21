package logica;

import java.io.Serializable;

public class Oferta implements Serializable{
	private static final long serialVersionUID = 1L;
	private int inicio;
	private int fin;
	private double monto;
	
	public Oferta(int inicio, int fin, double monto) {
		esHorarioValido(inicio);
		esHorarioValido(fin);
		esInicioFinValido(inicio, fin);
		esMontoValido(monto);
		this.inicio = inicio;
		this.fin = fin;
		this.monto = monto;
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
	
	@Override
    public String toString() {
        return "Oferta de " + inicio + " a " + fin + "hs - Monto: $" + monto;
    }
	
	private void esHorarioValido(int horario) {
		if(horario < 0 || horario > 25)
			throw new IllegalArgumentException("El horario es inválido, debería encontrarse entre las 00 y 24hs");
	}
	
	private void esInicioFinValido(int inicio, int fin) {
		if(inicio >= fin)
			throw new IllegalArgumentException("El horario de inicio no puede ser igual ni mayor al horario de fin");
	}
	
	private void esMontoValido(double monto) {
		if(monto <= 0)
			throw new IllegalArgumentException("La oferta debe sermayor a 0");
	}
}
