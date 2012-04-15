package gauss2;

import java.text.DecimalFormat;
import javax.swing.JOptionPane;
/**
 * @author Roberto Kahn Pereira
 * @version 1.2/2012
 * Copyright 2012 Roberto Kahn
 * Esta classe escalona o sistema e depois resolve por
 * substituicao retroativa.
 */
public class Sistemas {
	
	//variaveis iniciais do objeto
	//matriz a ser escalonada
	private double[][] matrizExpandida;
	
	//variaveis durante o processamento
	private double[] termosIndep;
	private double [][] matrizQuadrada; //matriz das incognitas
	private double [] vetorSolucao;
	
	private DecimalFormat df = new DecimalFormat("0.00;-0.00"); //formata o número que o usuario vai ler.
	/**
	 * O construtor recebe a matriz expandida do sistema (matriz), inicializa o atributo matrizExpandida com ela,
	 *  e executa o processamento do sistema.
	 * @param matriz
	 */
	Sistemas(double[][] matriz){
		this.matrizExpandida = matriz.clone();
		escalonaUmSistema();
		divideMatrizEscalonada();
		try{
		resolveSistEscalonado();
		}
		catch (IllegalArgumentException erro) {
			JOptionPane.showMessageDialog(null, erro.getMessage(), "Gauss",JOptionPane.ERROR_MESSAGE);
			//System.exit(1); //programa devolve mensagem de erro para o sistema operacional.
		}
	}
	
	@Override
	public String toString() {
		int linhas = this.matrizExpandida.length;
		int colunas = this.matrizExpandida[0].length;
		String matrizEscalonada = "";
		String vetorSolucao = "";
		
		//Escreve a matriz expandida do sistema no formato decimal definido em df.
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				if ( !(j == colunas-1) )
					matrizEscalonada += df.format(this.matrizExpandida[i][j]) + "   ";	
				else matrizEscalonada += "| "+df.format(this.matrizExpandida[i][j]);
			}
			matrizEscalonada += "\n";
		}
		//Escreve o vetor solucao do sistema no formato decimal definido em df.
		for (int i = 0; i < this.vetorSolucao.length; i++) {
			if ( !(i == this.vetorSolucao.length) ) vetorSolucao +=df.format(this.vetorSolucao[i])+"   ";
			else vetorSolucao +=df.format(this.vetorSolucao[i]);
		}
		
		return "Matriz escalonada:\n"
				+ matrizEscalonada + "vetorSolucao= ["
				+ vetorSolucao+"]";
	}
	
	/**
	 * Transforma a matriz contida no atributo matrizExpandida em uma matriz escalonada, que eh salva no mesmo atributo,
	 * apagando a matriz original.
	 */
	private void escalonaUmSistema () {
		double [][]matriz = this.matrizExpandida.clone();
		Matrizes m = new Matrizes(matriz);
		final int numEquacoes = matriz.length;
		
		//Percorre todos os pivos
		for (int indicePivo = 0; indicePivo < numEquacoes-1; indicePivo++) {
			double pivo = matriz[indicePivo][indicePivo];
			
			//Percorre todas as equacoes do sistema de forma a escalonar o mesmo
			for (int indiceLinhaAtual = indicePivo + 1; indiceLinhaAtual < numEquacoes; indiceLinhaAtual++) {
				double multiplicador = -matriz[indiceLinhaAtual][indicePivo] / pivo;
				
				//Cria vetor igual a linha do pivo e multiplica ele pelo multiplicador
				double [] linhaPivo = new double[matriz[0].length];
				for (int i = 0; i < matriz[0].length; i++) {
					linhaPivo [i] = matriz[indicePivo][i] * multiplicador; 
				}				
				matriz = m.somaDuasLinhas(linhaPivo, indiceLinhaAtual).clone();
			}
		}
		this.matrizExpandida = matriz.clone();
	}
/**
 * Simplesmente divide a matriz aumentada do sistema em uma matriz quadrada e um vetor de termos independentes.
 */
	private void divideMatrizEscalonada() {
		this.termosIndep = new double[matrizExpandida.length];
		for (int i = 0; i < matrizExpandida.length; i++)
			termosIndep[i] = matrizExpandida[i][matrizExpandida[0].length-1];
		this.matrizQuadrada = new double[matrizExpandida.length][matrizExpandida[0].length-1];
		for (int i = 0; i < matrizExpandida.length; i++) {
			for (int j = 0; j < matrizExpandida[0].length-1; j++) {
				matrizQuadrada[i][j] = matrizExpandida[i][j];
			}
		}
	}
	
	/**
	 * Usa a matriz quadrada e o vetor com os termos independetes para resolver o sistema escalonado por
	 * substituicao retroativa e retorna um vetor com as soluções encontradas. No caso da matriz escalonada conter algum
	 * elemento nulo na diagonal principal, retorna um erro IllegalArgumentException.
	 * @return vetor solucao do sistema.
	 */
	private double[] resolveSistEscalonado() {
		this.vetorSolucao = new double[termosIndep.length];
		final int numEquacoes = ( termosIndep.length - 1 ); //número de equações do sistema
		final int numIncognitas = matrizQuadrada[0].length;
		String mensagemDeErro = "";
		for (int i = 0; i < numIncognitas;i++) {
			if ( matrizQuadrada[i][i] == 0 ) {
				if (termosIndep[i] == 0)
					mensagemDeErro+="Sistema Possivel e Indeterminado";
				else mensagemDeErro+="Sistema Impossivel";
				IllegalArgumentException erro =new IllegalArgumentException(mensagemDeErro);
				throw erro;
			}
		}
	
	//substituicao retroativa
		int i = numEquacoes;
		while ( i >= 0)
		{
			if (i == numEquacoes)
				vetorSolucao[i]= termosIndep[i]/matrizQuadrada[i][i];
			else {
				double temp = 0;

				for (int j = i+1; j <= numEquacoes; j++)
					temp += matrizQuadrada[i][j] * vetorSolucao[j];
				temp = ( termosIndep[i]-temp);
				temp /= matrizQuadrada [i][i];
				vetorSolucao[i] = temp;
			}
			i--;
		}
		return vetorSolucao;
	}
}
