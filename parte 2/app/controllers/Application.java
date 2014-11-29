package controllers;

import java.util.ArrayList;
import java.util.List;

import models.NOTProcrastination;
import models.Semanas;
import models.dao.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

    private static final GenericDAO dao = new GenericDAO();

    @Transactional
    public static Result index() {
        List<Semanas> semanas = dao.findAllByClassName(Semanas.class.getName());
        return ok(index.render(semanas));
    }

    @Transactional
    public static Result criaMeta(){
        DynamicForm form = Form.form().bindFromRequest();
        
        String codigo = form.get("codigo");
        String semana = form.get("semana");
        String descricao = form.get("descricao");
        String prioridade = form.get("prioridade");

        NOTProcrastination meta = new NOTProcrastination(Integer.parseInt(codigo), descricao, Integer.parseInt(prioridade), Integer.parseInt(semana));
        dao.persist(meta);
        Semanas metasPorSemana = new Semanas(meta);
        dao.persist(metasPorSemana);

        List<Semanas> semanas = dao.findAllByClassName(Semanas.class.getName());
        return ok(index.render(semanas));
    }
}
