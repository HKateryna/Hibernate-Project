package tkim.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tkim.dao.PedidoDAO;
import tkim.modelo.Articulo;
import tkim.modelo.Cliente;
import tkim.modelo.ClienteEstandar;
import tkim.modelo.Pedido;

class PedidoDAOTest {
	
	PedidoDAO pedidoDAO;
	Pedido pedido;
	Cliente cliente;
	Articulo articulo;
	

	@Test
	void testExistePedidoTrue() {
		pedidoDAO = new PedidoDAO();
		articulo = new Articulo("a4", "Mesa", 35.15f, 4.95f, 352);
		cliente = new ClienteEstandar("Marta", "calle Sant Pere numero 5", "X367178", "marta@gmail.com", "Cliente Estandar");
		pedido = new Pedido(6123, 1,  LocalDateTime.now(), 40.1f, cliente.getNif(), articulo.getCodigo());
		// guardamos el pedido 6123 en la bbdd 
		pedidoDAO.save(pedido);
		assertTrue(pedidoDAO.existePedido(6123) == true);
	}

	@Test
	void testSave() {
		pedidoDAO = new PedidoDAO();
		articulo = new Articulo("a5", "Silla", 20.99f, 3.87f, 133);
		cliente = new ClienteEstandar("Antonio", "calle Sabadell numero 14", "Z718278", "antonio@gmail.com", "Cliente Estandar");
		pedido = new Pedido(4527, 1,  LocalDateTime.now(), 24.86f, cliente.getNif(), articulo.getCodigo());
		assertEquals("Pedido guardado con exito",pedidoDAO.save(pedido) ); 
		pedidoDAO.eliminarPedido(4527); // eliminamos el pedido de la bbdd después de que se ha guardado correctamente 
	}

	@Test
	void testEliminarPedido() {
		pedidoDAO = new PedidoDAO();
		articulo = new Articulo("a4", "Mesa", 35.15f, 4.95f, 352);
		cliente = new ClienteEstandar("Marta", "calle Sant Pere numero 5", "X367178", "marta@gmail.com", "Cliente Estandar");
		pedido = new Pedido(6123, 1,  LocalDateTime.now(), 40.1f, cliente.getNif(), articulo.getCodigo());
		// eliminamos el pedido 6123 de la bbdd (solo se puede eliminar su aún no ha sido enviado)
		assertEquals("El pedido se ha borrado correctamente", pedidoDAO.eliminarPedido(6123));
	}

	@Test
	void testPedidosEnviados() {
		pedidoDAO = new PedidoDAO();
		articulo = new Articulo("a6", "Sofa", 250.99f, 3.87f, 128);
		Articulo articuloB = new Articulo("a5", "Silla", 20.99f, 3.87f, 133);
		cliente = new ClienteEstandar("Antonio", "calle Sabadell numero 14", "Z718278", "antonio@gmail.com", "Cliente Estandar");
		
		// los pedidos son enviados si su tiempo de preparación ha transcurrido 
		pedido = new Pedido(1172, 2,  LocalDateTime.now().minusHours(3), 40.1f, cliente.getNif(), articulo.getCodigo());
		Pedido pedidoB = new Pedido(3332, 2,  LocalDateTime.now().minusHours(3), 40.1f, cliente.getNif(), articuloB.getCodigo());
		
		// estos pedidos deben estar en la bbdd, si no estan los añadimos para verificar este metodo
		pedidoDAO.save(pedido);
		pedidoDAO.save(pedidoB);
		
		List<Pedido> pedidosEnviados = pedidoDAO.pedidosEnviados("Z718278");
		List<Pedido> pedidosEnviadosCopia = new ArrayList<>();
		for (Pedido pedido : pedidosEnviados) {
			pedidosEnviadosCopia.add(pedido);
		}
		assertEquals(pedidosEnviadosCopia,pedidosEnviados); 	
		
	}
 
	@Test
	void testPedidosPendientes() {
		pedidoDAO = new PedidoDAO();
		articulo = new Articulo("a6", "Sofa", 250.99f, 3.87f, 128);
		Articulo articuloB = new Articulo("a5", "Silla", 20.99f, 3.87f, 133);
		cliente = new ClienteEstandar("Marta", "calle Sant Pere numero 5", "X367178", "marta@gmail.com", "Cliente Estandar");
		
		// los pedidos son enviados si su tiempo de preparación ha transcurrido 
		pedido = new Pedido(13131, 2,  LocalDateTime.now(), 40.1f, cliente.getNif(), articulo.getCodigo());
		Pedido pedidoB = new Pedido(52521, 2,  LocalDateTime.now(), 40.1f, cliente.getNif(), articuloB.getCodigo());
		// estos pedidos deben estar en la bbdd, si no estan los añadimos para verificar este método
		pedidoDAO.save(pedido);
		pedidoDAO.save(pedidoB);
		
		List<Pedido> pedidosPendientes = pedidoDAO.pedidosPendientes("X367178");
		List<Pedido> pedidosPendientesCopia = new ArrayList<>();
		for (Pedido pedido : pedidosPendientes) {
			pedidosPendientesCopia.add(pedido);
		}
		assertEquals(pedidosPendientesCopia,pedidosPendientes); 
		
		//eliminamos estos pedidos de la bbdd (se pueden eliminar porque su tiempo de preparación aún no ha transcurrido)
		pedidoDAO.eliminarPedido(13131);
		pedidoDAO.eliminarPedido(52521);
	}

	@Test
	void testDevolverTiempoPreparacion() {
		pedidoDAO = new PedidoDAO();
		articulo = new Articulo("a6", "Sofa", 250.99f, 3.87f, 128);
		assertEquals(128, pedidoDAO.devolverTiempoPreparacion("a6"));

	}

	@Test
	void testDevolverPedido() {
		
		pedidoDAO = new PedidoDAO();
		articulo = new Articulo("a6", "Sofa", 250.99f, 3.87f, 128);
		cliente = new ClienteEstandar("Antonio", "calle Sabadell numero 14", "Z718278", "antonio@gmail.com", "Cliente Estandar"); 
		pedido = new Pedido(1172, 2,  LocalDateTime.now().minusHours(3), 40.1f, cliente.getNif(), articulo.getCodigo());
		
		Pedido pedidoDevuelto = pedidoDAO.devolverPedido(1172);
		//Pedido pedidoCopia = pedidoDevuelto;
		assertEquals(pedido.getNumero_pedido(),pedidoDevuelto.getNumero_pedido());
		
		
	}

	@Test
	void testDevolverPedidosXCliente() {
		pedidoDAO = new PedidoDAO();
		
		// seleccionamos el cliente "Z718278" ya que tiene pedidos el la bbdd 
		List<Pedido> pedidosXcliente = pedidoDAO.devolverPedidosXCliente("Z718278");
		List<Pedido> pedidosCopia = new ArrayList<>();
		for (Pedido pedido : pedidosXcliente) {
			pedidosCopia.add(pedido);
		}
		assertEquals(pedidosCopia,pedidosXcliente); 		
		
	}

}
