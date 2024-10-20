package Frutas;

import Jogador.Jogador;

public abstract class Fruta {
    private String nome;
    private boolean bichada;
    private int x, y;

    // Construtor da fruta, sem o parâmetro 'bichada'
    public Fruta(String nome, int x, int y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
        this.bichada = false; // Inicia como não bichada, o estado será definido posteriormente
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public boolean isBichada() {
        return bichada;
    }

    public void setBichada(boolean bichada) {
        this.bichada = bichada;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Método abstrato para aplicar o efeito específico da fruta no jogador
    public abstract void aplicarEfeito(Jogador jogador);
}