package com.cataFrutas;

public class Fruta {
    private String nome;
    private int forca = 1; 
    private boolean bichada;
    private int[] posicao;

    public Fruta(String nome, boolean bichada, int x, int y) {
        this.nome = nome;
        this.bichada = bichada;
        this.posicao = new int[]{x, y};
    }

    public String getNome() {
        return nome;
    }

    public int getForca() {
        return forca; 
    }

    public boolean isBichada() {
        return bichada;
    }

    public void setBichada(boolean bichada) {
        this.bichada = bichada;
    }

    public int[] getPosicao() {
        return posicao;
    }

    public void setPosicao(int x, int y) {
        this.posicao[0] = x;
        this.posicao[1] = y;
    }

    public void descreverFruta() {
        String estadoBicho = bichada ? "bichada" : "saudável";
        System.out.println("Fruta: " + nome + ", Força: " + forca + ", Estado: " + estadoBicho);
    }
}