package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazareno on 14/11/14.
 */
@Entity
public class NOTProcrastination implements Comparable<NOTProcrastination> {

	@Id
	private int codigo;
	private int semana;

	private String descricao;
	private int prioridade;

	public NOTProcrastination(int codigo, String descricao, int prioridade,
			int semana) {
		this.descricao = descricao;
		this.semana = semana;
		this.prioridade = prioridade;
		this.codigo = codigo;
	}

	public NOTProcrastination() {

	}



	public int getSemana() {
		return semana;
	}

	public void setSemana(int semana) {
		this.semana = semana;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public String getNome() {
		return descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public int compareTo(NOTProcrastination np) {
		int result = 0;
		if (this.getPrioridade() < np.getPrioridade())
			result = -1;
		else if (this.getPrioridade() > np.getPrioridade())
			result = 1;
		else
			result = 0;
		return result;
	}
}
