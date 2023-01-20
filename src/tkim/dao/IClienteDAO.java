package tkim.dao;

import java.util.List;

import tkim.modelo.Cliente;

public interface IClienteDAO {
	public List<Cliente> mostrarCliente();
	public Boolean existeCliente(String nif);
	public String save(Cliente cliente);
	public List<Cliente> mostrarClientesXtipo(String tipoCliente);
	public List<Cliente> mostrarClientesTodos();
	public Cliente buscarCliente(String codigo_cliente);
}
