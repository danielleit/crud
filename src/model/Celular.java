package model;

public class Celular {
    Integer id;
    String modelo;
    String fabricante;
    Integer ano;

    public Celular() {

    }

    public Celular(String modelo, String fabricante, Integer ano) {
        this.ano = ano;
        this.modelo = modelo;
        this.fabricante = fabricante;
    }

    public Celular(Integer id, String modelo, String fabricante, Integer ano) {
        this.id = id;
        this.ano = ano;
        this.modelo = modelo;
        this.fabricante = fabricante;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String toString() {
        return "Modelo: " + this.modelo + " | Fabricante: " + this.fabricante + " | Ano: " + this.ano;
    }
}
