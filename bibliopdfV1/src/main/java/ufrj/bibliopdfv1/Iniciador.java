package ufrj.bibliopdfv1;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Iniciador implements ServletContextListener {

    public static String FILES_DIRECTORY_FULL_PATH;
    public static String NRO_ITEMS_PER_PAGE;
    
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
                FILES_DIRECTORY_FULL_PATH = prop.getProperty("FILES_DIRECTORY_FULL_PATH");
System.out.println("===== FILES_DIRECTORY_FULL_PATH: "+FILES_DIRECTORY_FULL_PATH);
                NRO_ITEMS_PER_PAGE = prop.getProperty("NRO_ITEMS_PER_PAGE");
System.out.println("===== NRO_ITEMS_PER_PAGE: "+NRO_ITEMS_PER_PAGE);
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
