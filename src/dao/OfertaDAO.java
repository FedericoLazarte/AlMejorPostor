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
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Oferta> cargarOfertas() {
        List<Oferta> ofertas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoOfertas))) {
            ofertas = (List<Oferta>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ofertas;
    }
}
