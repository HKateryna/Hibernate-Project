package tkim.modelo;

import tkim.dao.ArticuloDAO;
import tkim.dao.ClienteDAO;
import tkim.dao.PedidoDAO;

public class FactoryDAOs {

	public static Object getDAO(String tipo) {
		
	switch (tipo) {
	case "Articulo":
		return new ArticuloDAO();
	case "Cliente":
		return new ClienteDAO();
	case "Pedido":
		return new PedidoDAO();
	default: 
		return null;
		
	}

//		if (dao instanceof Articulo) {
//			return new ArticuloDAO();
//		} else if (dao instanceof Cliente) {
//			return null;
//		} else if (dao instanceof Pedido) {
//			return null;
//		}
//		
//		return null;
	}

}
