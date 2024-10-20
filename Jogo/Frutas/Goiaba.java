package Frutas;

import Jogador.Jogador;

public class Goiaba extends Fruta {

    public Goiaba(int x, int y) {
        super("Goiaba", x, y);
    }

    @Override
    public void aplicarEfeito(Jogador jogador) {
        // Apenas aumenta a força em 1
        jogador.aumentarForca(1);
    }
}