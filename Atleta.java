package project;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import project.comparators.ComparaAtletaDorsal;
import project.comparators.ComparaAtletaEscalao;
import project.comparators.ComparaAtletaNacionalidade;
import project.comparators.ComparaRegistoPassagemTempo;

public class Atleta implements Comparable<Atleta> {
    
    private final int dorsal; // usado na comparação de Atletas por omissão através do compareTo()
    private final String nome;
    private final String nacionalidade;
    private final String escalao;
    private int posicaoFinalAbsoluta;
    private int posicaoFinalEscalao;
    private int[] tempoNaMeta; // {horas, minutos, segundos}
    private int[] temposPassagem;  // tempos de passagem nos postos de controlo, em minutos desde a partida
    
    /**
     * Cria um novo objeto Atleta com as informações especificadas.
     * @param dorsal dorsal do atleta
     * @param nome nome do atleta
     * @param nacionalidade nacionalidade do alteta
     * @param escalao escalao do atleta
     */
    public Atleta(int dorsal, String nome, String nacionalidade, String escalao) {
    	this.dorsal = dorsal;
    	this.nome = nome;
    	this.nacionalidade = nacionalidade;
    	this.escalao = escalao;
    }
    
    /**
     * Retorna a dorsal do atleta
     * @return dorsal do atleta
     */
    public int getDorsal() {
    	return dorsal;
    }
    
    /**
     * Retorna o nome do atleta
     * @return nome do atleta
     */
    public String getNome() {
    	return nome;
    }
    
    /**
     * Retorna a nacionalidade do atleta
     * @return nacionalidade do atleta
     */
    public String getNacionalidade() {
    	return nacionalidade;
    }
    
    /**
     * Retorna o escalão do atleta
     * @return escalão do atleta
     */
    public String getEscalao() {
    	return escalao;
    }
    
    /**
     * Retorna a posição final do atleta na corrida
     * @return posição final do atleta na corrida
     */
    public int getPosicaoFinalAbsoluta() {
    	return posicaoFinalAbsoluta;
    }
    
    /**
     * Retorna a posicao final do escalão do atleta
     * @return posicao final do escalão do atleta
     */
    public int getPosicaoFinalEscalao() {
    	return posicaoFinalEscalao;
    }
    
    /**
     * Retorna o vetor de tempo na meta do atleta
     * @return vetor de tempo na meta do atleta (horas; minutos; segundos)
     */
    public int[] getTempoNaMeta() {
    	return tempoNaMeta;
    }
    
    /**
     * Retorna o vetor de tempos de passagem do atleta
     * @return vetor de tempos de passagem de cada posto do atleta
     */
    public int[] getTemposPassagem() {
    	return temposPassagem;
    }
    
    /**
     * Atualiza o a posição final absoluta do atleta
     * @param posicaoFinalAbsoluta posição em que o atleta terminou na corrida
     * @requires {@code posicaoFinalAbsoluta > 0}
     */
    public void setPosicaoFinalAbsoluta(int posicaoFinalAbsoluta) {
    	this.posicaoFinalAbsoluta = posicaoFinalAbsoluta; 
    }
    
    /**
     * Atualiza o a posição final de escalão do atleta
     * @param posicaoFinalEscalao posição em que o atleta terminou no seu escalão
     * @requires {@code posicaoFinalEscalao > 0}
     */
    public void setPosicaoFinalEscalao(int posicaoFinalEscalao) {
    	this.posicaoFinalEscalao = posicaoFinalEscalao;
    }
    
    /**
     * Atualiza o tempo na meta do atleta
     * @param tempoNaMeta vetor com o tempo na meta {horas; minutos; segundoa}
     * @requires {@code tempoNaMeta.length == 3 && tempoNaMeta != null}
     */
    public void setTempoNaMeta(int[] tempoNaMeta) {
    	this.tempoNaMeta = tempoNaMeta.clone();
    }
    
    /**
     * Atualiza os tempos de passagem do atleta
     * @param temposPassagem vetor com os tempos de passagem do atleta
     */
    public void setTemposPassagem(int[] temposPassagem) {
    	this.temposPassagem = temposPassagem;
    }
    
    /**
     * Compara este objeto com o objeto especificado para fins de ordenação
     * @param outro objeto a ser comparado com este objeto
     * @return um valor negativo se este objeto for menor que o outro objeto,
     *         zero se forem iguais e um valor positivo se este objeto for maior
     * @throws ClassCastException se o tipo do objeto especificado for
     *         incompatível com este objeto
     */
    public int compareTo(Atleta outro) {
    	if(this.getDorsal() < outro.getDorsal()) {
    		return -1;
    	}
    	if(this.getDorsal() > outro.getDorsal()) {
    		return 1;
    	}
    	return 0;
    }
    
    /**
     * Retorna o código hash para este objeto.
     * @return o hashCode para 	este objeto
     */
    public int hashCode() {
    	return this.hashCode();
    }
    
    /**
     * Verifica a igualdade de obejtos
     * @param object objeto que se quer comparar
     * @requires {@code object != null}
     */
    public boolean equals(Object object) {
    	return ((Atleta) object).getDorsal() == dorsal;
    }
    
    /**
     * Ordena o vetor pela dorsal
     * @param vec vetor de atletas
     * @requires {@code vec != null}
     */
    public static void ordena(Atleta[] vec) {
    	Arrays.sort(vec);
    }
    
    /**
     * Ordena o vetor dependendo do comparador
     * @param vec vetor de atletas
     * @param comparador comparador de atletas
     * @requires {@code vec != null}
     */
    public static void ordena(Atleta[] vec, Comparator<Atleta> comparador) {
    	Arrays.sort(vec, comparador);
    }
    
    /**
     * Retorna o indice do vetor em que se encontra o atleta pelo nome dado
     * @param vec vetor de atletas não ordenado
     * @param nome nome que se quer achar no vetor
     * @requires {@code vec != null && nome != null}
     * @return indice do atleta com o nome
     */
    public static int indiceAtletaPorNome(Atleta[] vec, String nome) {
    	return getIndex(vec, nome);
    }
    /**
     * Retorna o indice do vetor ordenado em que se encontra o atleta com o nome dado
     * @param vec vetor de atletas ordenado
     * @param nome nome que se quer achar no vetor
     * @requires {@code vec != null && nome != null}
     * @return indice do atleta com o nome
     */
    public static int indiceAtletaPorNomeArrayOrdenado(Atleta[] vec, String nome) {
    	Arrays.sort(vec);
    	return getIndex(vec, nome);
    }
    
    /**
     * Filtra o vetor, ou não, consoante as predefinições recebidas
     * @param vec vetor dos atletas
     * @param escalao o tipo de escalão que se quer filtrar
     * @param nacionalidade o tipo de nacionalidade que se quer filtrar
     * @requires {@code vec != null && escalao != null && nacionalidade != null}
     * @return vetor filtrado
     */
    public static Atleta[] seleccionaEscalaoEouNacionalidade(Atleta[] vec, String escalao, String nacionalidade) {
        Atleta[] resultado;
        if (nacionalidade.equals("todas") && !escalao.equals("todos")) {
            String classificacao = "escalao";
            resultado = vetorFiltrado(vec, escalao, classificacao);
        } 
        else if (escalao.equals("todos") && !nacionalidade.equals("todas")) {
            String classificacao = "nacionalidade";
            resultado = vetorFiltrado(vec, nacionalidade, classificacao);
        } 
        else if (!escalao.equals("todos") && !nacionalidade.equals("todas")) {
            String classificacao = "escalao";
            Atleta[] escalaoVetor = vetorFiltrado(vec, escalao, classificacao);
            resultado = vetorFiltrado(escalaoVetor, nacionalidade, "nacionalidade");
        } 
        else {
            resultado = vec;
        }
        return resultado;
    }
    
    /**
     * Retorna uma representação textual do atleta
     */
    public String toString() {
    	return (dorsal + " - " + nome);
    }
    
    /**
     * Procura que posição do vetor tem o mesmo nome que o pretendido, caso não existe retorna -1
     * @param vec vetor com todos os atletas
     * @param nome nome que irá ser procurado no vec
     * @return indice do atleta que tem o mesmo nome que "nome", ou -1 se não existir "nome" no vec
     * @requires	{@code vec != null && nome !=nome}
     */
    private static int getIndex(Atleta[] vec, String nome) {
    	int vecLength = vec.length;
    	int index = -1;
    	for(int i = 0; i < vecLength; i++) {
    		if(vec[i].getNome() == nome) {
    			index = i;
    		}
    	}
    	return index;
    }
    
    /**
     * Conta o número de vezes que o object aparece em vec
     * @param vec vetor com todos os atletas
     * @param object objeto que irá ser procurado no vec
     * @requires	{@code vec != null && object != null}a
     * @return numero de ocorrências do object em vec
     */
    private static int numeroDeOcorrencias(Atleta[] vec, String object, String classificacao) {
    	int newLength = 0;
    	
    	if(classificacao.equals("escalao")) {
    		for(Atleta atleta : vec) {
    			if(atleta.getEscalao().equals(object)) {
    				newLength++;
    			}
    		}
    		return newLength;
    	}
    	else {
    		for(Atleta atleta : vec) {
    			if(atleta.getNacionalidade().equals(object)) {
    				newLength++;
    			}
    		}
        	return newLength;
    	}
    	
    }
    
    /**
     * Cria um vetor apenas com os atletas com certa categoria
     * @param vec vetor com todos os atletas
     * @param categora categoria que irá ser procurada no vec
     * @param classificacao o tipo de classificação a ser considerado (nacionalidade ou escalão)
     * @requires	{@code vec != null && nacionalidade != null}
     * @return vetor com os atletas com um certa nacionalidade
     */
    private static Atleta[] vetorFiltrado(Atleta[] vec, String categoria, String classificacao) {
        int newLength = numeroDeOcorrencias(vec, categoria, classificacao);
        Atleta[] newVec = new Atleta[newLength];
        int i = 0;

        if (classificacao.equals("escalao")) {
            for (Atleta atleta : vec) {
                if (atleta.getEscalao().equals(categoria)) {
                    newVec[i] = atleta;
                    i++;
                }
            }
        } 
        else {
            for (Atleta atleta : vec) {
                if (atleta.getNacionalidade().equals(categoria)) {
                    newVec[i] = atleta;
                    i++;
                }
            }
        }
        return newVec;
    }


}
