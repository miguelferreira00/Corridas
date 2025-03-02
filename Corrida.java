package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//import org.jfree.data.xy.XYDataset;

import project.comparators.*;

/**
 * Guarda todos os dados de uma corrida e fornece métodos para analisar esses dados
 * 
 * @author Miguel Ferreira 61879
 */
 
public class Corrida {
    
    /**
     * Tempo de passagem de um atleta que não passou num posto, medido em minutos desde a partida
     * 
     * É um valor arbitrário que corresponde a 100 horas, muito superior ao tempo limite.
     * Aplica-se também a um atleta que não tenha chegado à meta, através da constante
     * TEMPO_DE_QUEM_NAO_CHEGOU_A_META.
     */
    public static final int MINUTOS_DE_QUEM_NAO_PASSOU = 6000;
    
    /**
     * Tempo de chegada de um atleta que não chegou à meta, expresso em {horas, minutos, segundos} desde a partida.
     * 
     * É um valor arbitrário consistente com MINUTOS_DE_QUEM_NAO_PASSOU.
     */
    public static final int[] TEMPO_DE_QUEM_NAO_CHEGOU_A_META = {MINUTOS_DE_QUEM_NAO_PASSOU / 60,
            MINUTOS_DE_QUEM_NAO_PASSOU % 60, 0};
    
    /**
     * Posição final de um atleta que não chegou à meta.
     * 
     * É um valor arbitrário, muito superior ao número de atletas em prova.
     * Serve para a posição absoluta e para a posição por escalão.
     */
    public static final int POSICAO_DE_QUEM_NAO_CHEGOU_A_META = 9999;
    
    // Todos os atletas que se apresentaram à partida, por ordem crescente de número de dorsal
    private final Atleta[] atletas;
    
    // Cada linha de registosPassagem corresponde a um posto de controlo.
    // Após leitura dos dados de ficheiroRegistoPassagens e processamento desses dados, cada linha deve ficar ordenada
    // por ordem crescente de tempo de passagem.
    private final RegistoPassagem[][] registosPassagem;
    
    /**
     * Cria um novo objeto Corrid com as informações especificadas nos ficheiros.
     * @param ficheiroListaAtletas ficheiro com as informações dos atletas
     * @param ficheiroClassificacoes ficheiro com as informações das classificações
     * @param ficheiroRegistoPassagens ficheiro com os tempos de passagem nos postos de controlo
     * @requires {@code ficheiroListaAtletas != null && ficheiroClassificacoes != null & ficheiroRegistoPassagens !=! null}
     * @throws FileNotFoundException
     */
    public Corrida(String ficheiroListaAtletas, String ficheiroClassificacoes,
    		String ficheiroRegistoPassagens) throws FileNotFoundException {
    	
    	//Todos os ficheiros
    	File listaAtletas = new File(ficheiroListaAtletas);
    	File classificacoes = new File(ficheiroClassificacoes);
    	File registoPassagens = new File(ficheiroRegistoPassagens);
    	
    	//Todos os scanners
    	Scanner scAtletas = new Scanner(listaAtletas);
    	Scanner scPassagens = new Scanner(registoPassagens);
    	Scanner scClassificacoes = new Scanner(classificacoes);
    	
    	//Salta as linhas sem informações de cada ficheiro
    	scAtletas.nextLine();
    	scAtletas.nextLine();
    	scAtletas.nextLine();
    	scPassagens.nextLine();
    	scClassificacoes.nextLine();
    	scClassificacoes.nextLine();
    	scClassificacoes.nextLine();
    	
    	//Informação Corrida
    	int i = 0;
    	int numeroDeAtletas = numeroDeAtletas(listaAtletas);
    	
    	//Criação dos vetores
    	atletas = new Atleta[numeroDeAtletas];
    	registosPassagem = new RegistoPassagem[24][numeroDeAtletas];//24 postos de controlo
    	
    	//Iteração sobre todos os atletas
    	while(scAtletas.hasNextLine()) {
    		
    		//Organização das informações
    		String[] infoAtleta = scAtletas.nextLine().split(";");
    		String[] infoPassagem = scPassagens.nextLine().split(";");
 
    		//Criação dos atletas
    		Atleta novoAtleta = processamentoAtleta(infoAtleta);
    		atletas[i] = novoAtleta;
    		
    		
    		//Criação registo de Passagens
    		int[] vecPassagens = processamentoRegistoPassagem(infoPassagem, i);
    		
    		
    		
    		
    		//Atualização dos tempos de Passagem e define inicialmente o tempo de toda a gente como
    		//	se não chegaram
    		atletas[i].setTemposPassagem(vecPassagens);
    		atletas[i].setTempoNaMeta(TEMPO_DE_QUEM_NAO_CHEGOU_A_META);
			
			atletas[i].setPosicaoFinalAbsoluta(POSICAO_DE_QUEM_NAO_CHEGOU_A_META);
    		atletas[i].setPosicaoFinalEscalao(POSICAO_DE_QUEM_NAO_CHEGOU_A_META);
    		i++;
    	}
    	
    	//Iteração sobre todos os atletas que acabaram a corrida
    	int j = 0;
    	while(scClassificacoes.hasNextLine()) {
    		
    		//Organização das informações
    		String[] infoClassificacoes = scClassificacoes.nextLine().split(";");
    		int posicaoAbsoluta = Integer.parseInt(infoClassificacoes[0]);
    		int dorsalClassificacao = Integer.parseInt(infoClassificacoes[1]);
       	 	int posicaoPorEscalao = Integer.parseInt(infoClassificacoes[5]);
       	 	String[] tempoCorrida = infoClassificacoes[6].split(":");
       	 	int[]tempoNaMeta = tempoNaMeta(tempoCorrida);
       	 	
       	 	//Atualização das posições e do tempo na meta do atletas que acabaram a corrida
       	 	//		(quem acabou a corrida tem a dorsal no ficheiro das calssificações)
    		for (Atleta atleta : atletas) {
                if (atleta.getDorsal() == dorsalClassificacao) {
                	//Atualização  das posições e do tempo na meta
                    atleta.setPosicaoFinalAbsoluta(posicaoAbsoluta);
                    atleta.setPosicaoFinalEscalao(posicaoPorEscalao);
                    atleta.setTempoNaMeta(tempoNaMeta);
                    break;
                }
            }
    		 j++;
    	}
    	
    	//Fechar os scanners
    	scAtletas.close();
    	scPassagens.close();
    	
    	// Ordenar vetor registos passagem
    	for(int k = 0 ; k < registosPassagem.length; k++) {
    		registosPassagem[k] = ordena(registosPassagem[k]);
    	}

    }
    
    /**
     * Retorna o vetor com todos os atletas
     * @return vetor com todos os atletas
     */
    public Atleta[] getAtletas() {
    	return atletas;
    }
    
    /**
     * Retorna o atleta na posição indice do vetor
     * @param indice posição no vetor
     * @return atleta na posição indice
     * @requires {@code indice >= 0}
     */
    public Atleta getAtletaPorIndice(int indice) {
    	return atletas[indice];
    }
    
    /**
     * Retorna os vetores com os registos de passagem
     * @return vetores com registos de passagem
     */
    public RegistoPassagem[][] getRegistosPassagem(){
    	return registosPassagem;
    }
    
    /**
     * Retorna o número de atletas na corrida
     * @return número de atletas na corrida
     */
    public int getNumeroDeAtletas() {
    	return atletas.length;
    }
    
    /**
     * Retorna o número total de postos de controlo
     * @return número total de postos de controlo
     */
    public int getNumeroPostosControlo() {
    	return registosPassagem.length;
    }
    
    /**
     * Calcula a posição em que o atleta do dorsal dado chegou em cada posto
     * @param dorsal numero de dorsal do atleta pretendido 
     * @return vetor com as posições do atleta em cada posto
     * @requires {@code dorsal > 0}
     */
    public int[] calculaPosicoesPostos(int dorsal) {
    	Atleta atleta = atletaApartirDorsal(dorsal);
		int[] tempoPassagemVec = atleta.getTemposPassagem();
    	int[] posicoesPostos = new int[registosPassagem.length];
    	for(int i = 0; i < registosPassagem.length; i++) {
    		int counter = 0;
    		int tempoPassagem = tempoPassagemVec[i];
    		for(int j = 0; j < atletas.length; j++) {
    			int tempoOutroAtleta = registosPassagem[i][j].getTempoPassagem();
    			int dorsalOutroAtleta = registosPassagem[i][j].getDorsal();
    			if(tempoPassagem > tempoOutroAtleta) {
    				counter++;
    			}
    			else if(tempoPassagem == tempoOutroAtleta) {
    				if(dorsalOutroAtleta < atleta.getDorsal()) {
    					counter++;
    				}
    			}
    		}
    		posicoesPostos[i] = counter + 1;
    	}
    	return posicoesPostos ;
    }
    
    /**
     * Cria  gráfico em que estão sobrepostas as séries de posições de cada
     *  atleta ao longo dos postos de controlo
     * @param vec vetor de atletas
     * @requires {@code vec != null}
     */
    public void plotPosicoesPostos(Atleta[] vec) {
    	 XYDataset[] datasets = new XYDataset[vec.length];
         for (int i = 0; i < vec.length; i++) {
             Atleta atleta = vec[i];
             int[] posicoes = calculaPosicoesPostos(atleta.getDorsal());
             XYSeries series = new XYSeries("Atleta " + atleta.getDorsal());
             for (int j = 0; j < posicoes.length; j++) {
            	// Considera-se apenas posições válidas
                 if (posicoes[j] < POSICAO_DE_QUEM_NAO_CHEGOU_A_META) {
                     series.add(j + 1, posicoes[j]);
                 }
             }
             datasets[i] = new XYSeriesCollection(series);
         }
         XYPlotter.showXYPlot("Posições dos Atletas nos Postos de Controle", "Posto de Controle", "Posição", datasets);
    }
    
    /**
     * Cria  gráfico em que estão sobrepostas as séries de posições de cada
     *  atleta ao longo dos postos de controlo a partir dos dorsais de cada
     *  atleta
     * @param dorsais vetor com as dorsais dos atletas
     * @requires {@code dorsais != null}
     */
    public void plotPosicoesPostos(int[] dorsais) {
        Atleta[] vec = new Atleta[dorsais.length];
        for (int i = 0; i < dorsais.length; i++) {
            vec[i] = atletaApartirDorsal(dorsais[i]);
        }
        plotPosicoesPostos(vec);
    }
    
    /**
     * Calcula o numero total de atletas presentes na corrida
     * @param listaAtletas ficheiro com as informações dos atletas  
     * @return numero de atletas total na corrida
     * @throws FileNotFoundException
     */
    private static int numeroDeAtletas(File listaAtletas) throws FileNotFoundException {
    	Scanner sc = new Scanner(listaAtletas);
    	sc.nextLine();
    	sc.nextLine();
    	sc.nextLine();
    	int counter = 0;
    	while(sc.hasNextLine()) {
    		sc.nextLine();
    		counter++;
    	}
    	sc.close();
    	return counter;
    	
    }
    
    /**
     * Processa todas as informações de um atleta e cria-o
     * @param infoAtleta vetor com as informações todas de um atleta
     * @return atleta criado
     * @requires {@code infoAtleta != null}
     */
    private static Atleta processamentoAtleta(String[] infoAtleta) {
    	//Processamento informação do atleta
		int dorsal = Integer.parseInt(infoAtleta[0]);
		String nome = infoAtleta[1];
		String nacionalidade = infoAtleta[4];
		String escalao = infoAtleta[3];
		
		//Cria um novo atleta
		Atleta novoAtleta = new Atleta(dorsal, nome, nacionalidade, escalao);
    	return novoAtleta;
    }
    
    /**
     * Processa todos os registos de passagens de um atleta
     * @param infoPassagem vetor com os valores de passagem do atleta
     * @param indiceAtleta indice do atleta no vetor de atletas
     * @return vetor com os registos de passagem de um atleta
     * @requires {@code infoPassagem != null && indice >= 0}
     */
    private int[] processamentoRegistoPassagem(String[] infoPassagem, int indiceAtleta) {
    	int[] vecPassagens = new int[registosPassagem.length];
    	for(int j = 0; j < registosPassagem.length; j++) {
			int dorsalAtleta = Integer.parseInt(infoPassagem[0]);
			String tempoPassagem = infoPassagem[j+3];

			
			if(tempoPassagem.equals(".")) {
				registosPassagem[j][indiceAtleta] = new RegistoPassagem(dorsalAtleta, MINUTOS_DE_QUEM_NAO_PASSOU);
			}
			else {
				int tempoTotal = calculaTempoPassagem(tempoPassagem);
    			registosPassagem[j][indiceAtleta] = new RegistoPassagem(dorsalAtleta, tempoTotal);
			}
			

			vecPassagens[j] = registosPassagem[j][indiceAtleta].getTempoPassagem();
		}
    	return vecPassagens;
    }
    
    /**
     * Calcula o tempo de passagem de um posto (minutos desde a partida)
     * @param tempoPassagem tempo de passagem (horas, minutos)
     * @return o tempo de passagem em minutos
     * @requires {@code tempoPassagem != null}
     */
    private int calculaTempoPassagem(String tempoPassagem) {
    	String[] tempoVec = tempoPassagem.split(":");
    	String[] subPartes  = tempoVec[0].split(" ");
    	int hora = Integer.parseInt(subPartes[1]);
    	int dia = numeroDia(subPartes[0]);
		int minuto = Integer.parseInt(tempoVec[1]);
		Tempo tempo = new Tempo(2023, 9, dia, hora, minuto, 0);
		int tempoTotal = tempo.getMinutosEmProva();
		return tempoTotal;
    }
    
    /**
     * Verifica o número do dia da prova (1- Friday, 2- Saturday, 3-Monday)
     * @param diaSemana dia da Semana
     * @return numero do dia da prova
     */
    private static int numeroDia(String diaSemana) {
    	if(diaSemana.equals("Fri.")) {
    		return 1;
    	}
    	else if(diaSemana.equals("Sat.")) {
    		return 2;
    	}
    	else {
    		return 3;
    	}
    	
    }
    
    /**
     * Retorna o atleta a partir da dorsal
     * @param dorsal numero de dorsal do atleta
     * @return atleta com a dorsal dada
     * @requires {@code dorsal > 1}
     */
    private Atleta atletaApartirDorsal(int dorsal) {
    	for(int i = 0; i < atletas.length; i++) {
    		if(atletas[i].getDorsal() == dorsal ) {
    			return atletas[i];
    		}
    	}
    	return null;
    }
    
    /**
     * Converte o tempo de uma string para um vetor de inteiros
     *  {horas, minutos, segundos}
     * @param tempoString horas do tempo na meta
     * @return
     */
    private static int[] tempoNaMeta(String[] tempoString) {
        int[] tempoNaMeta = new int[tempoString.length];
        for (int i = 0; i < tempoString.length; i++) {
            tempoNaMeta[i] = Integer.parseInt(tempoString[i]);
        }
        return tempoNaMeta;
    }
    
    /**
     * Ordena o vetor de registos de passagens por ordem crescente
     * de tempo de passagem 
     * @param vec vetor com os tempos de passagem
     * @return vetor de registos de passagens ordenado
     * @requires {@code vec != null}
     */
    public static RegistoPassagem[] ordena(RegistoPassagem[] vec) {
    	ComparaRegistoPassagemTempo comparador = new ComparaRegistoPassagemTempo();
    	Arrays.sort(vec, comparador);
    	return vec;
    }
    
   

}
