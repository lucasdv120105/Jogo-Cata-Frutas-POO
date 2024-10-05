package src.com.cataFrutas;

import java.util.Random;

public class Floresta {
    private static final int tamanho = 8;  // Matriz 8x8
    private String[][] matrizFloresta;
    private Random random;

    public Floresta() {
        matrizFloresta = new String[tamanho][tamanho];
        random = new Random();
        inicializarFloresta();
    }

    private void inicializarFloresta() { // Preenche a floresta com grama
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                matrizFloresta[i][j] = "Grama";
            }
        }

        adicionarElementoAleatorio("Abacate");
        adicionarElementoAleatorio("Acerola");
        adicionarElementoAleatorio("Amora");
        adicionarElementoAleatorio("Coco");
        adicionarElementoAleatorio("Goiaba");
        adicionarElementoAleatorio("Laranja");
        adicionarElementoAleatorio("Maracuja");
        adicionarElementoAleatorio("Arvore");
    }

    // Método que adiciona um elemento em uma posição aleatória
    private void adicionarElementoAleatorio(String elemento) {
        int x, y;
        do {
            x = random.nextInt(tamanho);
            y = random.nextInt(tamanho);
        } while (!matrizFloresta[x][y].equals("Grama"));  // Verifica se está sobre grama

        matrizFloresta[x][y] = elemento;  // Coloca o elemento na posição aleatória
    }

    // Método para obter o estado da floresta (usado na InterfaceGrafica)
    public String[][] getMatrizFloresta() {
        return matrizFloresta;
    }
}