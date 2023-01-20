package tkim.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tkim.dao.ClienteDAO;
import tkim.modelo.Cliente;
import tkim.modelo.ClienteEstandar;

class ClienteDAOTest {
	
	ClienteDAO cliDAO;
	Cliente cliente; 

	@Test
	void testExisteClienteTrue() {
		cliDAO = new ClienteDAO();
		// insertamos datos de un cleinte existente en la bbdd
		cliente = new ClienteEstandar("Marta", "calle Sant Pere numero 5", "X367178", "marta@gmail.com", "Cliente Estandar");
		assertTrue(cliDAO.existeCliente("X367178") == true);
	}

	@Test
	void testMostrarClientes() {
		cliDAO = new ClienteDAO();
		List<Cliente> clientes = cliDAO.mostrarCliente();
		List<Cliente> clientesCopia = new ArrayList<>();
		
		for (Cliente cliente : clientes) {			
			clientesCopia.add(cliente);
		}
		assertEquals(clientesCopia, clientes);
		
	}

	@Test
	void testSave() {
		cliDAO = new ClienteDAO();
		cliente = new ClienteEstandar("Antonio", "calle Sabadell numero 14", "Z718278", "antonio@gmail.com", "Cliente Estandar");
		assertEquals("Cliente guardado con exito", cliDAO.save(cliente));
	}

	@Test
	void testMostrarClientesXtipo() {
		cliDAO = new ClienteDAO();
		List<Cliente> clientes = cliDAO.mostrarClientesXtipo("Cliente Estandar");
		List<Cliente> clientesCopy = new ArrayList<>();
		
		for (Cliente cliente : clientes) {
			clientesCopy.add(cliente);
		}
		assertEquals(clientesCopy,clientes );
	}


	@Test
	void testBuscarCliente() {
		cliDAO = new ClienteDAO();
		cliente = new ClienteEstandar("Antonio", "calle Sabadell numero 14", "Z718278", "antonio@gmail.com", "Cliente Estandar");
		Cliente clienteA = cliDAO.buscarCliente("Z718278");
		Cliente clienteB = cliDAO.buscarCliente("Z718278");
		assertEquals(clienteA.toString(),clienteB.toString());
		
	}

}
