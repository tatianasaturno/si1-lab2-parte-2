package controllers;

import models.bd.BD;
import models.Meta;
import models.Semana;
import play.*;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;
import play.Logger;
import views.html.*;

import java.util.List;

public class Application extends Controller {

	private static final BD bd = new BD();
	private static Form<Meta> metas = Form.form(Meta.class);

	public static Result index() {
		return redirect(routes.Application.metas());
	}

	@Transactional
	public static Result metas() {
		List<Semana> semanas = bd.findAllByClass(Semana.class);
		return ok(views.html.index.render(semanas));
	}

	@Transactional
	public static Result addMeta() {
		Form<Meta> formulario = metas.bindFromRequest();

		if (formulario.hasErrors()) {

			List<Semana> semanas = bd.findAllByClass(Semana.class);
			return badRequest(views.html.index.render(semanas));

		} else {

			Meta novaMeta = new Meta();
			novaMeta.setNome(formulario.field("nome").value());
			novaMeta.setDescricao(formulario.field("descricao").value());
			novaMeta.setPrioridade(formulario.field("prioridade").value());

			Long semanaId = Long.parseLong(formulario.field("semana").value());
			Semana semana = bd.findByEntityId(Semana.class, semanaId);

			semana.addMeta(novaMeta);

			bd.merge(semana);
			bd.flush();

			Logger.debug("Adicionada meta " + novaMeta.getNome()
					+ " na semana " + semana.intervalAsString());

		}

		return redirect(routes.Application.metas());
	}

	@Transactional
	public static Result deleteMeta(Long semanaId, Long metaId) {
		Semana semana = bd.findByEntityId(Semana.class, semanaId);
		Meta metaDesejada = bd.findByEntityId(Meta.class, metaId);

		semana.deleteMeta(metaDesejada);

		Logger.debug("Removendo meta:" + metaDesejada.getNome());
		bd.merge(semana);
		bd.flush();

		return redirect(routes.Application.metas());
	}

	@Transactional
	public static Result setAlcancada(Long id) {
		Meta meta = bd.findByEntityId(Meta.class, id);
		meta.setMetaAlcancada(true);

		bd.merge(meta);
		bd.flush();

		return redirect(routes.Application.metas());
	}
}
