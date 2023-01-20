package tkim.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Pedidos")
public class Pedido {
	
	@Id
	private int numero_pedido;
	@Column(name="unidades_pedido")
	private int unidadesPedido;
	@Column(name="fecha_hora_pedido")
	private LocalDateTime fechaHoraPedido;
	@Column(name="total_pedido")
	private float totalPedido;
	@Column(name="cliente_fk")
	private String cliente;
	@Column(name="articulo_fk")
	private String articulo;
	/**
	 * @param numeroPedido
	 * @param unidadesPedido
	 * @param fechaHoraPedido
	 * @param totalPedido
	 * @param cliente
	 * @param articulo
	 */
	
	public Pedido() {
		
	}
	
	public Pedido(int numeroPedido, int unidadesPedido, LocalDateTime fechaHoraPedido, float totalPedido,
			String cliente, String articulo) {
		super();
		this.numero_pedido = numeroPedido;
		this.unidadesPedido = unidadesPedido;
		this.fechaHoraPedido = fechaHoraPedido;
		this.totalPedido = totalPedido;
		this.cliente = cliente;
		this.articulo = articulo;
	}
	public int getNumero_pedido() {
		return numero_pedido;
	}
	public void setNumero_pedido(int numero_pedido) {
		this.numero_pedido = numero_pedido;
	}
	public int getUnidadesPedido() {
		return unidadesPedido;
	}
	public void setUnidadesPedido(int unidadesPedido) {
		this.unidadesPedido = unidadesPedido;
	}
	public LocalDateTime getFechaHoraPedido() {
		return fechaHoraPedido;
	}
	public void setFechaHoraPedido(LocalDateTime fechaHoraPedido) {
		this.fechaHoraPedido = fechaHoraPedido;
	}
	public float getTotalPedido() {
		return totalPedido;
	}
	public void setTotalPedido(float totalPedido) {
		this.totalPedido = totalPedido;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getArticulo() {
		return articulo;
	}
	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	@Override
	public String toString() {
		return "Pedido [getNumero_pedido()=" + getNumero_pedido() + ", getUnidadesPedido()=" + getUnidadesPedido()
				+ ", getFechaHoraPedido()=" + getFechaHoraPedido() + ", getTotalPedido()=" + getTotalPedido()
				+ ", getCliente()=" + getCliente() + ", getArticulo()=" + getArticulo() + "]";
	}
	
	
}
