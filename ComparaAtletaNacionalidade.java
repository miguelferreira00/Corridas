package project.comparators;

import java.util.Comparator;

import project.Atleta;
/**
 * @author Miguel Ferreira 61879
 */
public class ComparaAtletaNacionalidade implements Comparator<Atleta> {
	/**
	 * Compara dois atletas para ordenar por nacionalidade.
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
		return atleta.getNacionalidade().compareTo(atleta2.getNacionalidade());
	}
}
