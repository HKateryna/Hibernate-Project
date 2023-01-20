package tkim.hibernate.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import tkim.modelo.Articulo;
//aqui las otras tablas, pedidos y clientes
import tkim.modelo.Cliente;
import tkim.modelo.ClienteEstandar;
import tkim.modelo.ClientePremium;
import tkim.modelo.Pedido;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		//sessionFactory = null;
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				// Creamos el archivo de propiedades de conexion a BBDD
				Properties settings = new Properties();


				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost/poo_uoc");

				settings.put(Environment.USER, "usuario_uoc");
				settings.put(Environment.PASS, "4321");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
				//settings.put(Environment.SHOW_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				/*settings.put(Environment.DRIVER, "oracle.jdbc.OracleDriver");
				settings.put(Environment.URL, "jdbc:oracle:thin:@localhost:1521:orcl");
				settings.put(Environment.USER, "drugo");
				settings.put(Environment.PASS, "4321");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.Oracle8iDialect");
				//settings.put(Environment.SHOW_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");*/


				//Ponemos todas las clases que hemos creado para obtener los datos de
				//sus respectivas tablas
				configuration.setProperties(settings);
				configuration.addAnnotatedClass(Articulo.class);
				configuration.addAnnotatedClass(Cliente.class);
				configuration.addAnnotatedClass(ClienteEstandar.class);
				configuration.addAnnotatedClass(ClientePremium.class);
				configuration.addAnnotatedClass(Pedido.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				//System.out.println("Hibernate Java Config serviceRegistry created");
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
				return sessionFactory;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}
