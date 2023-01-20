package tkim.modelo;

public class Exceptions extends Exception {

	private static final long serialVersionUID = 1L;

	public Exceptions(String message){
        super(message);}

    // 1. AÑADIR PEDIDO: El numero de unidades debe ser superior a 0 e inferior a 10.
    // 2. AÑADIR CLIENTE: El email debe contener @.
    // 3. AÑADIR ARTICULO: El tiempo de preparación de un articulo no puede ser inferior a 120min.

}