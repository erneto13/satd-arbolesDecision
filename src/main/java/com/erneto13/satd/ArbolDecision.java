package com.erneto13.satd;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArbolDecision {
    private Nodo root;

    public ArbolDecision() {
        this.root = construirArbol();
    }

    private Nodo construirArbol() {
        Nodo root = new Nodo();

        // regla 1: el cliente no debe de tener
        // >2 problemas con el banco
        root.atributo = "numProblemasBanco";

        Nodo conProblemas = new Nodo(); // nodo con una raíz que determina el problema
        conProblemas.tieneHoja = true;
        conProblemas.hoja = "RECHAZAR";
        root.regla.put(">=2", conProblemas);

        Nodo sinProblemas = new Nodo(); // nodo con raíz que determina que sigue
        sinProblemas.atributo = "puntosCredito"; // crea un nuevo nodo con el sig. atributo a clasificar
        root.regla.put("<2", sinProblemas);

        // regla 2: el cliente debe tener más
        // de 700 puntos de crédito
        Nodo puntosAltos = new Nodo();
        puntosAltos.tieneHoja = true;
        puntosAltos.hoja = "APROBAR";
        sinProblemas.regla.put(">=700", puntosAltos);

        Nodo puntosMediosBajos = new Nodo();
        puntosMediosBajos.atributo = "aniosEnTrabajo";
        sinProblemas.regla.put("<700", puntosMediosBajos);

        // regla 3: el cliente debe tener 2 o más
        // años trabajando en el mismo trabajo
        Nodo pocaEstabilidad = new Nodo();
        pocaEstabilidad.atributo = "propiedadVivienda";
        puntosMediosBajos.regla.put("<2", pocaEstabilidad);

        Nodo buenaEstabilidad = new Nodo();
        buenaEstabilidad.tieneHoja = true;
        buenaEstabilidad.hoja = "APROBAR";
        puntosMediosBajos.regla.put(">=2", buenaEstabilidad);

        // cuarta regla: el cliente debe tener
        // una propiedad de vivienda propia
        Nodo propietario = new Nodo();
        propietario.tieneHoja = true;
        propietario.hoja = "APROBAR";
        pocaEstabilidad.regla.put("PROPIA", propietario);

        Nodo noPropietario = new Nodo();
        noPropietario.tieneHoja = true;
        noPropietario.hoja = "RECHAZAR";
        pocaEstabilidad.regla.put("RENTADA", noPropietario);
        pocaEstabilidad.regla.put("HIPOTECADA", noPropietario);

        return root;
    }

    public String clasificar(Cliente cliente) {
        List<String> razonamiento = new ArrayList<>();
        String resultado = clasificarRecursivo(root, cliente, razonamiento);

        StringBuilder detalle = new StringBuilder("Resultado: " + resultado + "\nRazonamiento:\n");
        for (String razon : razonamiento) {
            detalle.append("- ").append(razon).append("\n");
        }
        return detalle.toString();
    }

    private String clasificarRecursivo(Nodo nodo, Cliente cliente, List<String> razonamiento) {
        if (nodo == null) {
            return "No se pudo clasificar el cliente, nodo nulo.";
        }

        if (nodo.tieneHoja) {
            razonamiento.add("Decisión final: " + nodo.hoja);
            return nodo.hoja;
        }

        String valor = "";
        switch (nodo.atributo) {
            case "numProblemasBanco":
                valor = cliente.numProblemasBanco >= 2 ? ">=2" : "<2";
                razonamiento.add("Verificación de problemas bancarios: " +
                        (cliente.numProblemasBanco >= 2 ? "Tiene 2 o más problemas bancarios, se considera riesgoso." : "Tiene menos de 2 problemas bancarios."));
                break;
            case "puntosCredito":
                valor = cliente.puntosCredito >= 700 ? ">=700" : "<700";
                razonamiento.add("Verificación de puntos de crédito: " +
                        (cliente.puntosCredito >= 700 ? "Puntos de crédito altos (>= 700)." : "Puntos de crédito bajos (< 700)."));
                break;
            case "aniosEnTrabajo":
                valor = cliente.aniosEnTrabajo >= 2 ? ">=2" : "<2";
                razonamiento.add("Verificación de años en el trabajo: " +
                        (cliente.aniosEnTrabajo >= 2 ? "Más de 2 años en el trabajo." : "Menos de 2 años en el trabajo."));
                break;
            case "propiedadVivienda":
                valor = cliente.propiedadVivienda;
                razonamiento.add("Verificación de propiedad de vivienda: " +
                        (cliente.propiedadVivienda.equals("PROPIA") ? "Vivienda propia." : "Vivienda no propia (rentada o hipotecada)."));
                break;
        }

        Nodo nextNodo = nodo.regla.get(valor);
        if (nextNodo == null) {
            razonamiento.add("nodo regla no encontrada para " + valor);
            return "camino no encontrado por el valor " + valor;
        }

        return clasificarRecursivo(nextNodo, cliente, razonamiento);
    }
}
