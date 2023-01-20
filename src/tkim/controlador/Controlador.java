package tkim.controlador;

import java.time.LocalDateTime;
import java.util.List;

import tkim.modelo.Articulo;
import tkim.modelo.Cliente;

import tkim.modelo.LanzarArticuloDAO;
import tkim.modelo.LanzarClienteDAO;
import tkim.modelo.LanzarPedidoDAO;
import tkim.modelo.Pedido;

public class Controlador {
	
	
	LanzarArticuloDAO lad = new LanzarArticuloDAO();
	LanzarClienteDAO lcd = new LanzarClienteDAO();
	LanzarPedidoDAO lpd = new LanzarPedidoDAO();
	
	public String addCliente(String nombre, String domi, String nif, String mail, String tipoCliente) {
		try {
			return lcd.save(nif, nombre, domi, mail, tipoCliente);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	
	public String addArticulo(String codigo, String descripcion, float precioVenta, float gastosEnvio, int tiempoPreparacion) {
		try {
			return lad.addArticulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	public String addPedido(int numeroPedido, int unidadesPedido, LocalDateTime fecha_hora_pedido, String cli, String art) {
		try {
			Cliente cliente = lcd.buscarCliente(cli);
			Articulo articulo = lad.buscarArticulo(art);
			return lpd.addPedido(numeroPedido, unidadesPedido, fecha_hora_pedido, cliente, articulo);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	public List<Articulo> mostrarArticulos(){
		return lad.mostrarArticulos();
	}
	
	public List<Cliente> mostrarClientesTodos(){
		return lcd.mostrarClientesTodos();	
	}
	
	public List<Cliente> mostrarClientesEstandar(){
		return lcd.mostrarClientesXtipo("Cliente Estandar");
	}
	
	public List<Cliente> mostrarClientesPremium(){
		return lcd.mostrarClientesXtipo("Cliente Premium");
		
	}
	
	public Boolean existeArticulo(String codigo) {
		return lad.existeArticulo(codigo);
		
	}
	
	public Boolean existeCliente(String nif) {	   
		return lcd.existeCliente(nif);
	}
	
	public Boolean existePedido(int codigoPedido) {
		return lpd.existePedido(codigoPedido);
		
	}
	
	public String eliminarPedido(int codigoPedido) {
		return lpd.eliminarPedido(codigoPedido);
	}
	
	public List<Pedido> mostrarPedEnviados(String nif) {
		return lpd.mostrarPedidosEnviados(nif);
	}
	
	public List<Pedido> mostrarPedPendientes(String nif) {
		return lpd.mostrarPedidosPendiente(nif);
	}
	
	public Cliente buscarCliente(String nif) {
		return lcd.buscarCliente(nif);
	}
	
	public Articulo buscarArticulo(String codigoArticulo) {
		return lad.buscarArticulo(codigoArticulo);
	}
}
