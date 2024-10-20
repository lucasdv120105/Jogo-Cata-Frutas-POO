package Frutas;

import Jogador.Jogador;

public class Abacate extends Fruta {

    public Abacate(int x, int y) {
        super("Abacate", x, y);
    }

    @Override
    public void aplicarEfeito(Jogador jogador) {
        // Aplica o efeito especial: dobra a força do competidor
        jogador.dobrarForca();
        jogador.aumentarForca(1);
    }
}
