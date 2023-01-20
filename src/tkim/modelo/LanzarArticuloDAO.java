package tkim.modelo;

import java.util.List;

import tkim.dao.IArticuloDAO;

public class LanzarArticuloDAO {

	public String addArticulo(String codigo, String descripcion, float precioVenta, float gastosEnvio,
			int tiempoPreparacion) {

		try {
			Articulo articulo = new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
			IArticuloDAO dao = (IArticuloDAO) FactoryDAOs.getDAO("Articulo");
			return dao.save(articulo);

		} catch (Exception e) {
			return "El articulo no ha podido introducirse correctamente";
		}
	}
	
	public List<Articulo> mostrarArticulos(){
		IArticuloDAO dao = (IArticuloDAO) FactoryDAOs.getDAO("Articulo");
		return dao.mostrarArticulos();
		
	}
	
	public Boolean existeArticulo(String codigo) {
		IArticuloDAO dao = (IArticuloDAO) FactoryDAOs.getDAO("Articulo");
		return dao.existeArticulo(codigo);
	}
	
	public Articulo buscarArticulo(String codigoArticulo) {
		IArticuloDAO dao = (IArticuloDAO) FactoryDAOs.getDAO("Articulo");
		return dao.buscarArticulo(codigoArticulo);
	}

}
