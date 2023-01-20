package net.israel.hibernate.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import net.israel.hibernate.model.*;

/**
 * Java based configuration
 * @author ramesh Fadatare
 *
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		//sessionFactory = null;
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "oracle.jdbc.OracleDriver");
				settings.put(Environment.URL, "jdbc:oracle:thin:@localhost:1521/orcl");
				settings.put(Environment.USER, "DRUGO");
				settings.put(Environment.PASS, "321");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.Oracle10gDialect");

				settings.put(Environment.SHOW_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				//Ponemos todas las clases que hemos creado para obtener los datos de
				//sus respectivas tablas
				configuration.setProperties(settings);
				configuration.addAnnotatedClass(TmaTerminales.class);
				configuration.addAnnotatedClass(TmaCuarta.class);
				configuration.addAnnotatedClass(Usuarios.class);
				
				

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				System.out.println("Hibernate Java Config serviceRegistry created");
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				return sessionFactory;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
