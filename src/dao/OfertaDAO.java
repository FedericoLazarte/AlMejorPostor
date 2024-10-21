package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import logica.Oferta;

public class OfertaDAO {
	private static final String ARCHIVO_OFERTAS = "ofertas.txt";
	
	public void guardarOfertas(List<Oferta> ofertas) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_OFERTAS))) {
            oos.writeObject(ofertas);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
	public List<Oferta> cargarOfertas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_OFERTAS))) {
            return (List<Oferta>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de ofertas no encontrado, iniciando con lista vacía.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
