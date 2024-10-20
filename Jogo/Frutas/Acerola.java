package Frutas;

import Jogador.Jogador;

public class Acerola extends Fruta {

    public Acerola(int x, int y) {
        super("Acerola", x, y);
    }

    @Override
    public void aplicarEfeito(Jogador jogador) {
        // Apenas aumenta a força em 1
        jogador.aumentarForca(1);
    }
}