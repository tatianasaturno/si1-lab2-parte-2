package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Semanas{
	
	@Id
    @GeneratedValue
    private Long id;
	
	@OneToOne
	NOTProcrastination np;
	
	@OneToMany
	private List<NOTProcrastination> semana1;
	@OneToMany
	private List<NOTProcrastination> semana2;
	@OneToMany
	private List<NOTProcrastination> semana3;
	@OneToMany
	private List<NOTProcrastination> semana4;
	@OneToMany
	private List<NOTProcrastination> semana5;
	@OneToMany
	private List<NOTProcrastination> semana6;
	
	public Semanas(NOTProcrastination np) {
		this.np = np;
		organizarPorSemana(np.getSemana());
		
	}

	public Semanas() {
		
	}
	
	private void organizarPorSemana(int semana) {
		switch (semana) {
		case 1:
			semana1 = new ArrayList<NOTProcrastination>();
			semana1.add(np);
			organizarSemanasPorPrioridade(semana1);
			break;
		case 2:
			semana2 = new ArrayList<>();
			semana2.add(np);
			organizarSemanasPorPrioridade(semana2);
			break;
		case 3:
			semana3 = new ArrayList<>();
			semana3.add(np);
			organizarSemanasPorPrioridade(semana3);
			break;
		case 4:
			semana4 = new ArrayList<>();
			semana4.add(np);
			organizarSemanasPorPrioridade(semana4);
			break;
		case 5:
			semana5 = new ArrayList<>();
			semana5.add(np);
			organizarSemanasPorPrioridade(semana5);
			break;
		case 6:
			semana6 = new ArrayList<>();
			semana6.add(np);
			organizarSemanasPorPrioridade(semana6);
			break;

		default:
			break;
		}
		
	}
	
	private void organizarSemanasPorPrioridade(List<NOTProcrastination> semana) {
		Collections.sort(semana);
	}
	

	public List<NOTProcrastination> getSemana1() {
		return semana1;
	}

	public List<NOTProcrastination> getSemana2() {
		return semana2;
	}

	public List<NOTProcrastination> getSemana3() {
		return semana3;
	}

	public List<NOTProcrastination> getSemana4() {
		return semana4;
	}

	public List<NOTProcrastination> getSemana5() {
		return semana5;
	}

	public List<NOTProcrastination> getSemana6() {
		return semana6;
	}
}