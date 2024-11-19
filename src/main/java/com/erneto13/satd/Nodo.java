package com.erneto13.satd;

import java.util.HashMap;
import java.util.Map;

// clase modelo para el árbol
public class Nodo {
    String atributo;
    Map<String, Nodo> regla;
    String hoja;
    boolean tieneHoja;

    public Nodo() {
        this.regla = new HashMap<>();
        this.tieneHoja = false;
    }
}
