import models.Meta.Prioridade;
import models.bd.BD;
import models.Meta;
import models.Semana;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.GlobalSettings;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import scala.Option;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.redirectLocation;
import static play.test.Helpers.status;

import javax.persistence.EntityManager;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationTest {
	BD bd = new BD();
	GregorianCalendar dataControle = new GregorianCalendar();
	Meta meta1 = new Meta("1", "Fazer lab 2 SI1", Prioridade.ALTA);
	Meta meta2 = new Meta("2", "Assistir séries", Prioridade.NORMAL);
	Semana semana1 = new Semana(dataControle);
	Semana semana2 = new Semana(dataControle);
	EntityManager em;
	List<Semana> semanas;

	@Before
	public void setUp() {
		FakeApplication app = Helpers.fakeApplication(new GlobalSettings());
		Helpers.start(app);
		Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(
				JPAPlugin.class);
		em = jpaPlugin.get().em("default");
		JPA.bindForCurrentThread(em);
		em.getTransaction().begin();
	}

	@After
	public void tearDown() {
		em.getTransaction().commit();
		JPA.bindForCurrentThread(null);
		em.close();
	}

	@Test
	public void deveIniciarSemMetas() {
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.size() == 0).isTrue();
	}
	
	@Test
	public void deveSalvarSemanaNoBD() {
		bd.persist(semana1);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.size() == 1).isTrue();
		assertThat(semanas.get(0).getMetasTotal()).isEqualTo(0);
		
		bd.persist(semana2);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.size() == 2).isTrue();
		assertThat(semanas.get(1).getMetasTotal()).isEqualTo(0);
	}
	
	@Test
	public void deveSalvarMetaNoBD() {
		semana1.addMeta(meta1);
		bd.persist(semana1);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.get(0).getMetasTotal() == 1).isTrue();
		
	}


	@Test
	public void deveAdicionarMetas() {
		semana1.addMeta(meta1);
		bd.persist(semana1);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.get(0).getMetas().get(0).getNome() == "1").isTrue();
		assertThat(semanas.get(0).getMetas().get(0).getDescricao() == "Fazer lab 2 SI1").isTrue();
		assertThat(semanas.get(0).getMetas().get(0).getPrioridade() == Prioridade.ALTA).isTrue();
	}

	@Test
	public void deveSetarMetaComoAlcancada() {
		semana1.addMeta(meta1);
		bd.persist(semana1);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.get(0).getMetas().get(0).isAlcancada()).isFalse();
		semana1.getMetas().get(0).setMetaAlcancada(true);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.get(0).getMetas().get(0).isAlcancada()).isTrue();
	}

	@Test
	public void deveRemoverMetas() {
		semana1.addMeta(meta1);
		semana1.addMeta(meta2);
		bd.persist(semana1);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.get(0).getMetasTotal() == 2).isTrue();
		
		semana1.removeMeta(meta2);
		bd.persist(semana1);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.get(0).getMetasTotal() == 1).isTrue();
		
		semana1.removeMeta(meta1);
		bd.persist(semana1);
		semanas = bd.findAllByClass(Semana.class);
		assertThat(semanas.get(0).isEmpty()).isTrue();
		
	}
}
