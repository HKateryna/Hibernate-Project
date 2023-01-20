package tkim.dao;
import java.util.List;
import tkim.modelo.Pedido;

public interface IPedidoDAO {
	
public Boolean existePedido(Integer codigo);
	
	public String save(Pedido pedido);
	public String eliminarPedido(int numPedido);
	public List<Pedido> pedidosEnviados(String nif);
	public List<Pedido> pedidosPendientes(String nif);
	public int devolverTiempoPreparacion(String codigoArticulo);
	public Pedido devolverPedido(int numeroPedido);
	public List<Pedido> devolverPedidosXCliente(String nif);


}
