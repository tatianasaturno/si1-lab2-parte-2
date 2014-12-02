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
                
                semana1.addMeta(new Meta("Atividade 1", "Minha atividade 1", Prioridade.ALTA));
                bd.merge(semana1);
                bd.flush();     
                
                semana1.addMeta(new Meta("Atividade 2", "Minha atividade 2", Prioridade.NORMAL));
                bd.merge(semana1);
                bd.flush();    
                
                semana1.addMeta(new Meta("Atividade 3", "Minha atividade 3", Prioridade.BAIXA));
                bd.merge(semana1);
                bd.flush();    
                
                semana1.addMeta(new Meta("Atividade 4", "Minha atividade 4", Prioridade.ALTA));
                bd.merge(semana1);
                bd.flush();    
                
                semana2.addMeta(new Meta("Atividade 5", "Minha atividade 5", Prioridade.ALTA));
                bd.merge(semana2);
                bd.flush();    
                
                semana2.addMeta(new Meta("Atividade 6", "Minha atividade 6", Prioridade.BAIXA));
                bd.merge(semana2);
                bd.flush();    
                
                semana3.addMeta(new Meta("Atividade 7", "Minha atividade 7", Prioridade.ALTA));
                bd.merge(semana3);
                bd.flush();    
                
                semana4.addMeta(new Meta("Atividade 8", "Minha atividade 8", Prioridade.NORMAL));
                bd.merge(semana4);
                bd.flush();    
                
                semana5.addMeta(new Meta("Atividade 9", "Minha atividade 9", Prioridade.NORMAL));
                bd.merge(semana5);
                bd.flush();    
                
                semana6.addMeta(new Meta("Atividade 10", "Minha atividade 10", Prioridade.BAIXA)); 
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
