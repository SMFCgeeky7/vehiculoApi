package com.example.vehiculosgetcorp.Models;

import java.util.List;
import java.io.Serializable;

public class Vehiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private int precioBase;
    private int kilometraje;
    private String tipo;
    private int garantiaAnios;
    private int descuentoPromocional;
    private InformacionAdicional informacionAdicional;

    public Vehiculo() {
    }

    public Vehiculo(int id, String marca, String modelo, int anioFabricacion, int precioBase,
                    int kilometraje, String tipo, int garantiaAnios, int descuentoPromocional,
                    InformacionAdicional informacionAdicional) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.precioBase = precioBase;
        this.kilometraje = kilometraje;
        this.tipo = tipo;
        this.garantiaAnios = garantiaAnios;
        this.descuentoPromocional = descuentoPromocional;
        this.informacionAdicional = informacionAdicional;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(int anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getGarantiaAnios() {
        return garantiaAnios;
    }

    public void setGarantiaAnios(int garantiaAnios) {
        this.garantiaAnios = garantiaAnios;
    }

    public int getDescuentoPromocional() {
        return descuentoPromocional;
    }

    public void setDescuentoPromocional(int descuentoPromocional) {
        this.descuentoPromocional = descuentoPromocional;
    }

    public InformacionAdicional getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(InformacionAdicional informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    // Clase interna para representar la información adicional
    public static class InformacionAdicional implements Serializable {
        private static final long serialVersionUID = 2L;
        private int esperanzaVida;
        private List<Dato> datos;

        public InformacionAdicional() {
        }

        public InformacionAdicional(int esperanzaVida, List<Dato> datos) {
            this.esperanzaVida = esperanzaVida;
            this.datos = datos;
        }

        public int getEsperanzaVida() {
            return esperanzaVida;
        }

        public void setEsperanzaVida(int esperanzaVida) {
            this.esperanzaVida = esperanzaVida;
        }

        public List<Dato> getDatos() {
            return datos;
        }

        public void setDatos(List<Dato> datos) {
            this.datos = datos;
        }
    }

    // Clase interna para representar cada dato específico
    public static class Dato implements Serializable {
        private static final long serialVersionUID = 3L;
        private String nombreDato;
        private String valor;

        public Dato() {
        }

        public Dato(String nombreDato, String valor) {
            this.nombreDato = nombreDato;
            this.valor = valor;
        }

        public String getNombreDato() {
            return nombreDato;
        }

        public void setNombreDato(String nombreDato) {
            this.nombreDato = nombreDato;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }
}