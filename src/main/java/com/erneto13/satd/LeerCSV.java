package com.erneto13.satd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeerCSV {
    private String archivo;

    public LeerCSV(String archivo) {
        this.archivo = archivo;
    }

    public List<Cliente> capturarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");

                String cliente_id = d[0].trim();
                int pcreditos = Integer.parseInt(d[1].trim());
                int anios_en_trabajo = Integer.parseInt(d[2].trim());
                String pro_vivienda = d[3].trim().toUpperCase();
                int num_probl_ban = Integer.parseInt(d[4].trim());

                Cliente c = new Cliente(cliente_id, pcreditos,
                        anios_en_trabajo, pro_vivienda, num_probl_ban);

                clientes.add(c);
            }
        } catch (IOException e) {
            System.out.println("error al leer el csv: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("error convertir datos num: " + e.getMessage());
        }
        return clientes;
    }
}
