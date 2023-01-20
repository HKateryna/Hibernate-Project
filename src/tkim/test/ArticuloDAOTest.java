package tkim.test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tkim.controlador.Controlador;
import tkim.dao.ArticuloDAO;
import tkim.modelo.Articulo;


class ArticuloDAOTest {
	
	Controlador contro;
	Articulo articulo;
	ArticuloDAO artDAO;	
	
	
	@Test
	void testExisteArticuloTrue() {
		
		artDAO  = new ArticuloDAO();
		
		articulo = new Articulo("a4", "Mesa", 35.15f, 4.95f, 352);
		//artDAO.save(articulo);
		assertTrue(artDAO.existeArticulo("a4")== true);
		
	}
	@Test
	void testExisteArticuloFalse() {
		
		artDAO  = new ArticuloDAO();
		articulo = new Articulo("a5", "Silla", 20.99f, 3.87f, 133);
		//artDAO.save(articulo);
		
		assertFalse(artDAO.existeArticulo("a5")== false);
		
	}	
	
	@Test
	void testSaveArticulo() {
		artDAO  = new ArticuloDAO();
		articulo = new Articulo("a6", "Sofa", 250.99f, 3.87f, 128);
		assertEquals("Articulo guardado con exito",artDAO.save(articulo));
		}

	@Test
	void testMostrarArticulos() {
		artDAO  = new ArticuloDAO();
		List<Articulo> articulos = artDAO.mostrarArticulos();
		List<Articulo> artCopia = new ArrayList<Articulo>();
		
		for (Articulo articulo : articulos) {
			
			artCopia.add(articulo);
		}
		assertEquals(artCopia, articulos); 
		
	}

	@Test
	void testBuscarArticulo() {
		artDAO  = new ArticuloDAO();
		contro = new Controlador();
		Articulo articulo = new Articulo("12a", "pantalon", 48.98f, 3.3f, 131);
		Articulo articuloBuscar = contro.buscarArticulo("12a");	
		assertEquals(articulo.toString(), articuloBuscar.toString()); 
		
	}

}




