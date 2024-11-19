package com.erneto13.satd;

// clase modelo del cliente
public class Cliente {
    String clienteId;
    int puntosCredito;
    int aniosEnTrabajo;
    String propiedadVivienda;
    int numProblemasBanco;
    String clasificacion;

    public Cliente(String clienteId, int puntosCredito, int aniosEnTrabajo,
                   String propiedadVivienda, int numProblemasBanco) {
        this.clienteId = clienteId;
        this.puntosCredito = puntosCredito;
        this.aniosEnTrabajo = aniosEnTrabajo;
        this.propiedadVivienda = propiedadVivienda;
        this.numProblemasBanco = numProblemasBanco;
    }
}
