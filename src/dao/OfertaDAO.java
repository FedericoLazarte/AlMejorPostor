package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import logica.Oferta;

public class OfertaDAO {
    private String archivoOfertas;

    public OfertaDAO() {
        this.archivoOfertas = "ofertas.txt";
    }

    public OfertaDAO(String archivoOfertas) {
        this.archivoOfertas = archivoOfertas;
    }

    public void guardarOfertas(List<Oferta> ofertas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoOfertas))) {
            oos.writeObject(ofertas);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar las ofertas", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Oferta> cargarOfertas() {
        File archivo = new File(archivoOfertas);
        if (!archivo.exists() || archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoOfertas))) {
            return (List<Oferta>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar las ofertas", e);
        }
    }
}