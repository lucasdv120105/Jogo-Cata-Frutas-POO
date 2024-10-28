package Frutas;

import java.awt.Point;

/**
 * A classe Acerola representa uma fruta específica que possui um poder associado
 * É uma subclasse de FrutaComPoder e herda suas propriedades, como nome, posição, e se está bichada
 * Além disso, define o poder de valor 2
 */

public class Acerola extends FrutaSemPoder {
	
	/**
     * Construtor da classe Acerola
     * Inicializa um objeto Acerola com o nome, posição, e estado (bichada ou não)
     * 
     * @param nome O nome da fruta.
     * @param posicao A posição da fruta na floresta, representada por um array de inteiros [X, Y]
     * @param bichada Um booleano que indica se a fruta está bichada (true) ou não (false)
     */
	
	public Acerola(Point posicao, boolean bichada) {
		super(posicao, bichada);
	}
	
	public Acerola(boolean bichada) {
		super(bichada);
	}
	
    public String toString() {
    	return "acerola";
    }
	
	public String getImg() {
		return "/resources/acerola.png";
	}
	
	public String getImgMochila() {
		return "/resources/acerola_mochila.png";
	}	
	
	public String getImgArvore() {
		return "/resources/aceroleira.png";
	}
	
	public String arvore() {
		return "Aceroleira";
	}
}