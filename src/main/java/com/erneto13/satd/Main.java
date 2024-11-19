package com.erneto13.satd;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ArbolDecision arbol = new ArbolDecision();

        String archivo = "C:\\Users\\Ernesto Amaral\\IdeaProjects\\satd-arbolesDecision\\src\\main\\java\\com\\erneto13\\satd\\credit_test.csv";
        LeerCSV leer = new LeerCSV(archivo);

        List<Cliente> c = leer.capturarClientes();

        for (Cliente cliente : c) {
            String r = arbol.clasificar(cliente);
            System.out.printf("Cliente %s: %s\n", cliente.clienteId , r);
        }
    }
}