package Frutas;

import Jogador.Jogador;

public class Coco extends Fruta {

    public Coco(int x, int y) {
        super("Coco", x, y);
    }

    @Override
    public void aplicarEfeito(Jogador jogador) {
        // Aplica o efeito especial: dobra os pontos de movimento
        jogador.dobrarPontosDeMovimento();
        jogador.aumentarForca(1);
    }
}
