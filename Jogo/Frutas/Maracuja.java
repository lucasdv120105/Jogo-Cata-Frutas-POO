package Frutas;

import Jogador.Jogador;

public class Maracuja extends Fruta {

    public Maracuja(int x, int y) {
        super("Maracuja", x, y);
    }

    @Override
    public void aplicarEfeito(Jogador jogador) {
        // Aplica o efeito especial: concede um ponto de vitória
        jogador.incrementarPontosDeVitoria(1);
        jogador.aumentarForca(1);
    }
}