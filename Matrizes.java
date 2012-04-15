package gauss2;
/**
 * @author Roberto Kahn Pereira
 * @version 1.0/2012
 * Copyright 2012 Roberto Kahn
 */
public class Matrizes {

	private double matriz[][]; //matriz aumentada do sistema

	Matrizes(double matriz[][]) {
		this.matriz = matriz.clone();
		//this.matrizOriginal = matriz.clone();
	}

	/**
	 * Este metodo multiplica uma linha de uma matriz por um escalar
	 * @param linha
	 * @param num: Eh o numero que multiplica a linha
	 * @return A matriz com a linha multiplicada.
	 */
	public double[][] multiplicaLinhaPorEscalar(int linha, double num) {
		int numColunas = matriz[0].length;
		for (int colunaAtual=0; colunaAtual < numColunas; colunaAtual++) {
			matriz[linha][colunaAtual] = num * matriz[linha][colunaAtual];
		}
		return matriz;
	}
	/**
	 * Este metodo mantem a linha 1 e soma seus membros aos membros da linha 2,
	 * modificando os membros da linha 2 pelo resultado da soma.
	 * @param linha1
	 * @param linha2
	 * @return
	 */
	public double [][] somaDuasLinhas(int linha1, int linha2) {
		
		int numColunas = matriz[0].length;
		for (int colunaAtual=0; colunaAtual < numColunas; colunaAtual++) {
			matriz[linha2][colunaAtual] = matriz[linha2][colunaAtual] + matriz[linha1][colunaAtual];
			if ( ehZero( matriz[linha2][colunaAtual] ))
				matriz[linha2][colunaAtual] = (double)0;
		}
		return matriz;
	}

	private boolean ehZero(double num) {
		if (java.lang.Math.abs(num) < 1E-6)
			return true;
		return false;
	}
/**
 * Este metodo recebe um vetor e soma ele na linhaAtual da matriz da classe.
 */
	public double[][] somaDuasLinhas(double[] linhaPivo, int linhaAtual) {
		for (int i = 0; i < matriz[0].length; i++) {
			matriz[linhaAtual][i] += linhaPivo[i];
			if ( ehZero( matriz[linhaAtual][i] ))
				matriz[linhaAtual][i] = (double)0;
		}
		return matriz;
	}

}