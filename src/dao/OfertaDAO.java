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
    private String archivoOfertas;

    // Constructor por defecto que usa "ofertas.txt"
    public OfertaDAO() {
        this.archivoOfertas = "ofertas.txt";
    }

    // Constructor que permite especificar un archivo diferente
    public OfertaDAO(String archivoOfertas) {
        this.archivoOfertas = archivoOfertas;
    }

    public void guardarOfertas(List<Oferta> ofertas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoOfertas))) {
            oos.writeObject(ofertas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Oferta> cargarOfertas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoOfertas))) {
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
