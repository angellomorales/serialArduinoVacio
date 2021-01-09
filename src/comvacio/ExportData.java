package comvacio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportData {

    public void escribir(String datos, String archivo) {
        PrintWriter escritor;
        try {
            escritor = new PrintWriter(new FileWriter("informes/" + archivo + ".txt"));
            escritor.write(datos);
            escritor.flush();
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String leerArchivo() {
        String nombre = "";
        BufferedReader lector;
        try {
            lector = new BufferedReader(new FileReader("data.txt"));
            nombre = lector.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, null, ex);
        }

        return nombre;
    }

}
