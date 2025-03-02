package project.comparators;

import java.util.Arrays;
import java.util.Comparator;

import project.Atleta;
/**
 * @author Miguel Ferreira 61879
 */
public class ComparaAtletaTempoNaMeta implements Comparator<Atleta> {
	/**
	 * Compara dois atletas para ordenar por tempo na meta.
	 * Retorna um valor negativo, zero ou um valor positivo
	 * conforme este objeto é menor, igual ou maior que o objeto especificado
	 *
	 * @param atleta atleta a ser comparado com outro atleta
	 * @param atleta2 outro atleta a ser comparado com o atleta
	 * @requires {@code atleta != null && atleta2 != null}
	 * @return um valor negativo se este objeto é menor, zero se eles são iguais,
	 *  e um valor positivo se este objeto é maior
	 */
	public int compare(Atleta atleta, Atleta atleta2) {
		int[] tempoNaMeta = atleta.getTempoNaMeta();
		int[] tempoNaMeta2 = atleta2.getTempoNaMeta();
		for (int i = 0; i < tempoNaMeta.length; i++) {
            if (tempoNaMeta[i] < tempoNaMeta2[i]) {
                return -1;
            } else if (tempoNaMeta[i] > tempoNaMeta2[i]) {
                return 1;
            }
        }
		
		return 0;
	}
}
