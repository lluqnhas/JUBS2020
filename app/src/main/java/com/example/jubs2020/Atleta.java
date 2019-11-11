package com.example.jubs2020;

import java.util.Arrays;

public class Atleta {

    private String modalidade, sexo, idade;
    private String[] fase_I, fase_II, fase_III;
    private int[] resultado;

    public Atleta(String modalidade, String sexo, String idade) {
        this.modalidade = modalidade;
        this.sexo = sexo;
        this.idade = idade;
        this.fase_I = new String[15];
        this.fase_II = new String[15];;
        this.fase_III = new String[22];;
        this.resultado = new int[3];;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String[] getFase_I() {
        return fase_I;
    }

    public void setFase_I(String[] fase_I) {
        this.fase_I = fase_I;
    }

    public String[] getFase_II() {
        return fase_II;
    }

    public void setFase_II(String[] fase_II) {
        this.fase_II = fase_II;
    }

    public String[] getFase_III() {
        return fase_III;
    }

    public void setFase_III(String[] fase_III) {
        this.fase_III = fase_III;
    }

    public int[] getResultado() {
        return resultado;
    }

    public void setResultado(int[] resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Atleta{" +
                "modalidade='" + modalidade + '\'' +
                ", sexo='" + sexo + '\'' +
                ", idade='" + idade + '\'' +
                ", fase_I=" + Arrays.toString(fase_I) +
                ", fase_II=" + Arrays.toString(fase_II) +
                ", fase_III=" + Arrays.toString(fase_III) +
                ", resultado=" + Arrays.toString(resultado) +
                '}';
    }
}
