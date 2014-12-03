import java.util.GregorianCalendar;

import play.*;
import models.Meta;
import models.Semana;
import models.Meta.Prioridade;
import models.bd.*;
import play.db.jpa.JPA;

public class Global extends GlobalSettings {

    private static BD bd = new BD();
    private Semana semana1, semana2, semana3, semana4, semana5, semana6;

    @Override
    public void onStart(Application app) {
        Logger.info("Iniciando...");

        JPA.withTransaction(new play.libs.F.Callback0() {
        	
            @Override
            public void invoke() throws Throwable {
            	GregorianCalendar dataControle = new GregorianCalendar();
                semana1 = new Semana(dataControle);
                bd.persist(semana1);
                bd.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana2 = new Semana(dataControle);
                bd.persist(semana2);
                bd.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana3 = new Semana(dataControle);
                bd.persist(semana3);
                bd.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana4 = new Semana(dataControle);
                bd.persist(semana4);
                bd.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana5 = new Semana(dataControle);
                bd.persist(semana5);
                bd.flush();
                
                dataControle.add(GregorianCalendar.DAY_OF_MONTH, 7);
                semana6 = new Semana(dataControle);
                bd.persist(semana6);
                bd.flush();
                
                semana1.addMeta(new Meta("Meta 1", "Fazer lab 2 SI1", Prioridade.ALTA));
                bd.merge(semana1);
                bd.flush();     
                
                semana1.addMeta(new Meta("Meta 2", "Assistir séries", Prioridade.NORMAL));
                bd.merge(semana1);
                bd.flush();    
                
                semana1.addMeta(new Meta("Meta 3", "Ir para aula", Prioridade.BAIXA));
                bd.merge(semana1);
                bd.flush();    
                
                semana1.addMeta(new Meta("Meta 4", "Dormir", Prioridade.ALTA));
                bd.merge(semana1);
                bd.flush();    
                
                semana2.addMeta(new Meta("Meta 5", "Comer sushi", Prioridade.ALTA));
                bd.merge(semana2);
                bd.flush();    
                
                semana2.addMeta(new Meta("Meta 6", "Assistir animes", Prioridade.BAIXA));
                bd.merge(semana2);
                bd.flush();    
                
                semana3.addMeta(new Meta("Meta 7", "Estudar", Prioridade.ALTA));
                bd.merge(semana3);
                bd.flush();    
                
                semana4.addMeta(new Meta("Meta 8", "Fazer comida", Prioridade.NORMAL));
                bd.merge(semana4);
                bd.flush();    
                
                semana5.addMeta(new Meta("Meta 9", "Ir no cinema", Prioridade.NORMAL));
                bd.merge(semana5);
                bd.flush();    
                
                semana6.addMeta(new Meta("Meta 10", "Atualizar TERA", Prioridade.BAIXA)); 
                bd.merge(semana6);
                bd.flush();  
            }
        });
    }
    
    
    @Override
    public void onStop(Application app) {
        Logger.info("Aplicação finalizada...");

        JPA.withTransaction(new play.libs.F.Callback0() {
        	
            @Override
            public void invoke() throws Throwable {
            	Logger.info("Aplicação finalizando...");
            	
            	bd.remove(semana1);
            	bd.flush();  
            	bd.remove(semana2);
            	bd.flush();  
            	bd.remove(semana3);
            	bd.flush();  
            	bd.remove(semana4);
            	bd.flush();  
            	bd.remove(semana5);
            	bd.flush();  
            	bd.remove(semana6);
            	bd.flush();  
            }
        });
    }
}
