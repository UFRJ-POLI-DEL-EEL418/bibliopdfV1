package ufrj.bibliopdfv1;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Iniciador implements ServletContextListener {

    public static String caminho_completo_arquivos;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        URL url = null;
        try{
            ServletContext ctx =  sce.getServletContext();
            url = ctx.getResource("/caminho_completo_arquivos.properties");
System.out.println("===== caminho_completo_arquivos.properties: "+url.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Properties prop = new Properties();
        try (InputStream in = url.openStream()) {
            if(in!=null){
                prop.load(in);
                caminho_completo_arquivos = prop.getProperty("caminho_completo_arquivos");
System.out.println("===== caminho_completo_arquivos: "+caminho_completo_arquivos);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("===== Fechando contexto");
    }
}
