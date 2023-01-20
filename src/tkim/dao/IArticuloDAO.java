package tkim.dao;


import java.util.List;
import tkim.modelo.Articulo;

public interface IArticuloDAO {
	
	public List<Articulo> mostrarArticulos();
	public Boolean existeArticulo(String codigo);
	public String save(Articulo articulo);
	public Articulo buscarArticulo(String codigoArticulo);

}
