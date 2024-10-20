package Frutas;

import Jogador.Jogador;

public class Laranja extends Fruta {

    public Laranja(int x, int y) {
        super("Laranja", x, y);
    }

    @Override
    public void aplicarEfeito(Jogador jogador) {
        // Aplica o efeito especial: anula o efeito de uma fruta bichada
        jogador.anularEfeitoBichada();
        jogador.aumentarForca(1);
    }
}
