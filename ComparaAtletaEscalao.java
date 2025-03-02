package project.comparators;

import java.util.Arrays;
import java.util.Comparator;

import project.Atleta;
/**
 * @author Miguel Ferreira 61879
 */
public class ComparaAtletaEscalao implements Comparator<Atleta> {
	/**
	 * Compara dois atletas para ordenar por escalão.
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
		int len1 = atleta.getEscalao().length();
        int len2 = atleta2.getEscalao().length();
        int minLength = Math.min(len1, len2);

        // Comparar caractere por caractere até encontrar uma diferença
        for (int i = 0; i < minLength; i++) {
            char c1 = atleta.getEscalao().charAt(i);
            char c2 = atleta2.getEscalao().charAt(i);
            if (c1 < c2) {
                return -1;
            }
            if(c1 > c2) {
            	return 1;
            }
        }
        return 0;
	}
}
