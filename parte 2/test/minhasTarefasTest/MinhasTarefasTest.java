package minhasTarefasTest;
import static org.fest.assertions.Assertions.*;
import models.NOTProcrastination;
import models.Semanas;
import models.dao.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.GlobalSettings;
import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FakeApplication;
import play.test.Helpers;
import scala.Option;

import javax.persistence.EntityManager;

import java.util.List;

/**
 * Created by Tatiana Saturno da Silva
 */
public class MinhasTarefasTest {

    private GenericDAO dao = new GenericDAO();

    @Test
    public void deveIniciarSemDisciplinas() throws Exception {
        List<NOTProcrastination> metas = dao.findAllByClassName(NOTProcrastination.class.getName());
        assertThat(metas).isEmpty();
    }

    @Test
    public void deveSalvarMetaNoBD() throws Exception {
        NOTProcrastination meta = new NOTProcrastination(0, "fazer lab2 si", 1, 5);
        dao.persist(meta);
        List<NOTProcrastination> metas = dao.findAllByClassName(NOTProcrastination.class.getName());
        assertThat(metas.size()).isEqualTo(1);
    }
    
    @Test
    public void deveGuardarOsDadosCorretamente(){
    	NOTProcrastination meta = new NOTProcrastination(0, "fazer lab2 si", 1, 5);
        dao.persist(meta);
        List<NOTProcrastination> metas = dao.findAllByClassName(NOTProcrastination.class.getName());
        assertThat(metas.get(0).getCodigo()).isEqualTo(0);
        assertThat(metas.get(0).getSemana()).isEqualTo(5);
        assertThat(metas.get(0).getDescricao()).isEqualTo("fazer lab2 si");
        assertThat(metas.get(0).getPrioridade()).isEqualTo(1);
    }
    
    @Test
    public void deveSalvarSemanasNoBD() throws Exception {
    	NOTProcrastination meta = new NOTProcrastination(0, "fazer lab2 si", 1, 5);
        dao.persist(meta);

        Semanas semana = new Semanas(meta);

        dao.persist(meta);

        List<Semanas> semanas = dao.findAllByClassName(Semanas.class.getName());
        assertThat(semanas.size()).isEqualTo(1);
        assertThat(semanas.get(0).getSemana5().size()).isEqualTo(1);
    }

    public EntityManager em;

    @Before
    public void setUp() {
        FakeApplication app = Helpers.fakeApplication(new GlobalSettings());
        Helpers.start(app);
        Option<JPAPlugin> jpaPlugin = app.getWrappedApplication().plugin(JPAPlugin.class);
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

}
