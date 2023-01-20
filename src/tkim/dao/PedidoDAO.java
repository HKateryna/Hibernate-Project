package tkim.dao;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import tkim.hibernate.util.HibernateUtil;
import tkim.modelo.Articulo;
import tkim.modelo.Pedido;


public class PedidoDAO implements IPedidoDAO {

	Transaction transaction = null;
	Session session = null;

	@Override
	public Boolean existePedido(Integer codigo) {
		session = HibernateUtil.getSessionFactory().openSession();
		Boolean existePedido = false;
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Pedido> cr = cb.createQuery(Pedido.class);
		Root<Pedido> root = cr.from(Pedido.class);
		cr.select(root);
		cr.select(root).where(cb.equal(root.get("numero_pedido"), codigo));
		Query query = session.createQuery(cr);

		if (!query.getResultList().isEmpty()) {
			existePedido = true;
		}

		// Cerramos la sesion
		session.close();
		return existePedido;
	}

	@Override
	public String save(Pedido pedido) {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		try {
			session.save(pedido);
			// Hacemos commit de la transaccion en BBDD para que se guarden
			transaction.commit();
			// Cerramos la sesion
			session.close();
			return "Pedido guardado con exito";
		} catch (RuntimeException re) {
			// Hacemos rollback de los datos introducidos por si hay algun fallo en la
			// insercion del articulo
			transaction.rollback();
			return "La insercion del pedido ha fallado" + re;
		}
	}

	@Override
	public String eliminarPedido(int numPedido) {
		Pedido pedido = devolverPedido(numPedido);
		if (pedido !=null) {

			int tiempoPreparacion = devolverTiempoPreparacion(pedido.getArticulo());

			LocalDateTime localDateTime = pedido.getFechaHoraPedido().minusHours(1);

			Duration duration = Duration.between(localDateTime, LocalDateTime.now());
			long diff = Math.abs(duration.toMinutes());
			// System.out.println("la diferencia en min:" + diff);
			boolean enviado_pendiente = tiempoPreparacion > diff;
			// calculos
			if (enviado_pendiente) {
				try {
					session = HibernateUtil.getSessionFactory().openSession();
					transaction = session.beginTransaction();
					CriteriaBuilder cb = session.getCriteriaBuilder();
					CriteriaDelete<Pedido> delete = cb.createCriteriaDelete(Pedido.class);
					Root<Pedido> p = delete.from(Pedido.class);
					delete.where(cb.equal(p.get("numero_pedido"), numPedido));
					session.createQuery(delete).executeUpdate();
					transaction.commit(); 
					session.close();
					return "El pedido se ha borrado correctamente";
				} catch (RuntimeException re) { 
					System.out.println(re);
					return "fallo al borrar el pedido" + re;
				}
			} else {
				return "el pedido ya ha sido enviado y no se puede eliminar";
			}
		} else {
			return "El pedido no existe";
		}

	}

	@Override
	public List<Pedido> pedidosEnviados(String nif) {
		List<Pedido> pedidosEnviados = new ArrayList<Pedido>();
		List<Pedido>pedidoXcliente = devolverPedidosXCliente(nif);
		
		for (Pedido pedido : pedidoXcliente) {
			int tiempoPreparacion = devolverTiempoPreparacion(pedido.getArticulo());
			LocalDateTime localDateTime = pedido.getFechaHoraPedido().minusHours(1);
			Duration duration = Duration.between(localDateTime, LocalDateTime.now());
			long diff = Math.abs(duration.toMinutes());
			boolean enviado_pendiente = tiempoPreparacion < diff;
			
			if(enviado_pendiente) {
				pedidosEnviados.add(pedido);
				
			}
		}	
	
		return pedidosEnviados;
	}

	@Override
	public List<Pedido> pedidosPendientes(String nif) {
		List<Pedido> pedidosPendientes = new ArrayList<Pedido>();
		List<Pedido>pedidoXcliente = devolverPedidosXCliente(nif);
		
		for (Pedido pedido : pedidoXcliente) {
			int tiempoPreparacion = devolverTiempoPreparacion(pedido.getArticulo());
			LocalDateTime localDateTime = pedido.getFechaHoraPedido().minusHours(1);
			Duration duration = Duration.between(localDateTime, LocalDateTime.now());
			long diff = Math.abs(duration.toMinutes());
			boolean enviado_pendiente = tiempoPreparacion > diff;
			
			if(enviado_pendiente) {
				pedidosPendientes.add(pedido);
				
			}
		}	
	
		return pedidosPendientes;
	}

	@Override
	public int devolverTiempoPreparacion(String codigoArticulo) {
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Articulo> cr = cb.createQuery(Articulo.class);
			Root<Articulo> root = cr.from(Articulo.class);
			cr.select(root);
			cr.select(root).where(cb.equal(root.get("codigo"), codigoArticulo));
			Query query = session.createQuery(cr);
			Articulo articulo = (Articulo) query.getSingleResult();
			session.close();
			return articulo.getTiempoPreparacion();

		} catch (RuntimeException re) {
			System.out.println("fallo al conectarse a la BBDD" + re);
			return 0;
		}
	}

	@Override
	public Pedido devolverPedido(int numeroPedido) {
		Pedido pedido = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Pedido> cr = cb.createQuery(Pedido.class);
			Root<Pedido> root = cr.from(Pedido.class);
			cr.select(root);
			cr.select(root).where(cb.equal(root.get("numero_pedido"), numeroPedido));
			Query query = session.createQuery(cr);
			if (query.getSingleResult() != null) {
				pedido = (Pedido) query.getSingleResult();
			}
			
			return pedido;
		} catch (RuntimeException re) {
			//System.out.println("fallo recuperar pedido." + re);
			return null;
		}
	}
	
	// funcion devuelve pedido segun el nif
	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> devolverPedidosXCliente(String nif) {
		List<Pedido> pedidos = null;
		try {

			session = HibernateUtil.getSessionFactory().openSession();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Pedido> cr = cb.createQuery(Pedido.class);
			Root<Pedido> root = cr.from(Pedido.class);
			cr.select(root);
			cr.select(root).where(cb.equal(root.get("cliente"), nif));
			Query query = session.createQuery(cr);
			pedidos = query.getResultList();		
			
			return pedidos;
		} catch (RuntimeException re) {
			System.out.println("fallo recuperar pedido." + re);
			return null;
		}
	}
	
		

}
