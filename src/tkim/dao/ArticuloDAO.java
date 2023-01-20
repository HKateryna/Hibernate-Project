package tkim.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tkim.hibernate.util.HibernateUtil;
import tkim.modelo.Articulo;


public class ArticuloDAO implements IArticuloDAO {
    
	Transaction transaction = null;
	Session session = null;

	public Boolean existeArticulo(String codigo) {
		session = HibernateUtil.getSessionFactory().openSession();
		Boolean existeArticulo = false;
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Articulo> cr = cb.createQuery(Articulo.class);
		Root<Articulo> root = cr.from(Articulo.class);
		cr.select(root);
		cr.select(root).where(cb.equal(root.get("codigo"), codigo));
		Query query = session.createQuery(cr);
		
		if (!query.getResultList().isEmpty()) {
			existeArticulo = true;
		}
		
		//Cerramos la sesion
		session.close();
		return existeArticulo;
	}


	@Override
	public String save(Articulo articulo) {
		
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		try {
			session.save(articulo);
			//Hacemos commit de la transaccion en BBDD para que se guarden 
			transaction.commit();
			//Cerramos la sesion
			session.close();
			return "Articulo guardado con exito";
		} catch (RuntimeException re) {
			//Hacemos rollback de los datos introducidos por si hay algun fallo en la insercion del articulo
			transaction.rollback();
			return "La insercion del articulo ha fallado" + re;
		}
	}


	@Override
	public List<Articulo> mostrarArticulos() {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		try {
			//Creamos la query de select all indicandole con el .class la clase que queremos
	        CriteriaQuery<Articulo> criteriaQuery = session.getCriteriaBuilder().createQuery(Articulo.class);
	        criteriaQuery.from(Articulo.class);
	        //Aqui los datos que nos ha devuelto lo metemos en una lista tipo arraylist
	        List<Articulo> articulos = session.createQuery(criteriaQuery).getResultList();
	        //Cerramos la sesion
	        session.close();
	        //Y la devolvemos	
			return articulos;
		} catch (RuntimeException re) {
			System.out.println("fallo al mostrar los articulos."+re);
			return null;
		}
	}


	@Override
	public Articulo buscarArticulo(String codigoArticulo) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Articulo> cr = cb.createQuery(Articulo.class);
			Root<Articulo> root = cr.from(Articulo.class);
			cr.select(root);
			cr.select(root).where(cb.equal(root.get("codigo"), codigoArticulo));
			Query query = session.createQuery(cr);
			Articulo articulo = (Articulo) query.getSingleResult();
			return articulo;
		} catch (RuntimeException re) {
			System.out.println("fallo al mostrar los articulos." + re);
			return null;
		}
	}

}
