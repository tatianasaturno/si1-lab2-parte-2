package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;

import static java.util.GregorianCalendar.*;

@Entity(name="Semana")
public class Semana implements Comparable<Semana> {

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Calendar comecaData;

	@Temporal(TemporalType.DATE)
	private Calendar terminaData;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "METAS")
	private List<Meta> metas = new ArrayList<>();

	public Semana() {
	}

	public Semana(GregorianCalendar comeca) {
		int dayOfWeek = comeca.get(DAY_OF_WEEK);

		// Seleciona o inicio da semana para o domingo anterior a data
		// especificada
		switch (dayOfWeek) {
		case MONDAY:
			comeca.add(DAY_OF_WEEK, -1);
			break;
		case TUESDAY:
			comeca.add(DAY_OF_WEEK, -2);
			break;
		case WEDNESDAY:
			comeca.add(DAY_OF_WEEK, -3);
			break;
		case THURSDAY:
			comeca.add(DAY_OF_WEEK, -4);
			break;
		case FRIDAY:
			comeca.add(DAY_OF_WEEK, -5);
			break;
		case SATURDAY:
			comeca.add(DAY_OF_WEEK, -6);
			break;
		}

		Date data = comeca.getTime();

		comecaData = new GregorianCalendar();
		comecaData.setTime(data);

		terminaData = new GregorianCalendar();
		terminaData.setTime(data);
		terminaData.add(DAY_OF_MONTH, 6);
	}

	/**
	 * @param meta
	 */
	public void addMeta(Meta meta) {
		metas.add(meta);
	}

	/**
	 * @param meta
	 */
	public void removeMeta(Meta meta) {
		metas.remove(meta);
	}

	private String getStringId() {
		return String.valueOf(comecaData.get(DAY_OF_MONTH))
				+ String.valueOf(comecaData.get(MONTH) + 1)
				+ String.valueOf(comecaData.get(YEAR)) + "-"
				+ String.valueOf(terminaData.get(DAY_OF_MONTH))
				+ String.valueOf(terminaData.get(MONTH) + 1)
				+ String.valueOf(terminaData.get(YEAR));
	}

	/**
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return data de inicio
	 */
	public String getStringComecaData() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		return fmt.format(comecaData.getTime());
	}

	/**
	 * @return data de término
	 */
	public String getStringTerminaData() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		return fmt.format(terminaData.getTime());
	}

	/**
	 * @return duração
	 */
	public String intervalAsString() {
		return getStringComecaData() + " - " + getStringTerminaData();
	}

	/**
	 * @return metas
	 */
	public List<Meta> getMetas() {
		return metas;
	}

	/**
	 * @return número de metas
	 */
	public int getMetasTotal() {
		return metas.size();
	}

	/**
	 * @return se está vazia
	 */
	public boolean isEmpty() {
		return metas.isEmpty();
	}

	/**
	 * @return total metas alcançadas
	 */
	public int getTotalMetasAlcancadas() {
		int metasAlcancadas = 0;

		Iterator<Meta> it = metas.iterator();
		while (it.hasNext()) {
			Meta atual = it.next();
			if (atual.isAlcancada() == true)
				metasAlcancadas++;
		}

		return metasAlcancadas;
	}

	/**
	 * @return total de metas não alcançadas
	 */
	public int getTotalMetasNaoAlcancadas() {
		return this.getMetasTotal() - this.getTotalMetasAlcancadas();
	}

	@Override
	public int compareTo(Semana outraSemana) {
		// ordenar semana por data de inicio
		return this.comecaData.compareTo(outraSemana.comecaData);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Meta))
			return false;

		Semana semana = (Semana) obj;
		return semana.getStringComecaData().equals(this.getStringComecaData())
				&& semana.getStringTerminaData().equals(
						this.getStringTerminaData());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getStringComecaData(), this.getStringTerminaData());
	}

}
