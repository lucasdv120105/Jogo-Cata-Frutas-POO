package Frutas;

import Jogador.Jogador;

public class Amora extends Fruta {

    public Amora(int x, int y) {
        super("Amora", x, y);
    }

    @Override
    public void aplicarEfeito(Jogador jogador) {
        // Apenas aumenta a força em 1
        jogador.aumentarForca(1);
    }
}