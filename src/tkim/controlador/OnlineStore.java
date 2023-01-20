package tkim.controlador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import tkim.modelo.Articulo;
import tkim.modelo.Cliente;
import tkim.modelo.Exceptions;
import tkim.modelo.Pedido;

public class OnlineStore {

	Scanner teclado = new Scanner(System.in);
	Controlador contro = new Controlador();

	public static void main(String[] args) throws Exception {
		OnlineStore os = new OnlineStore();
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		os.inicio();
	}

	/**
	 * menu de inicio de la aplicacion
	 * 
	 * @throws Exception
	 */
	void inicio() throws Exception {
		boolean salir = false;
		String opcio;
		do {
			System.out.println(" 1. Añadir artículo");
			System.out.println(" 2. Mostrar artículos");
			System.out.println(" 3. Añadir clientes");
			System.out.println(" 4. Mostrar clientes");
			System.out.println(" 5. Mostrar clientes estandar");
			System.out.println(" 6. Mostrar clientes premium");
			System.out.println(" 7. Crear pedido");
			System.out.println(" 8. Eliminar pedido");
			System.out.println(" 9. Mostrar pedidos pendientes de envío por cliente");
			System.out.println("10. Mostrar pedidos enviados por cliente");
			System.out.println(" 0. Salir de la aplicación");
			opcio = pedirOpcioMenu();
			switch (opcio) {
			case "1":
				addArticulo();
				break;
			case "2":
				mostrarArticulos();
				break;
			case "3":
				addCliente();
				break;
			case "4":
				mostrarClientes();
				break;
			case "5":
				mostrarClientesEstandar();
				break;
			case "6":
				mostrarClientesPremium();
				break;
			case "7":
				addPedido();
				break;
			case "8":
				eliminarPedido();
				break;
			case "9":
				mostrarPedidosPendientes();
				break;
			case "10":
				mostrarPedidosEnviados();
				break;
			case "0":
				salir = true;
			}
		} while (!salir);
	}

	void pausar() {
		String entrada;
		do {
			System.out.println("Presiona intro para ir al menu principal...");
			entrada = teclado.nextLine();
			System.out.println(entrada);
		} while (!entrada.equals(""));
	}

	/**
	 * funcion que devuelve la opcion de menu escogida
	 * 
	 * @return retorna el numero indicad
	 */
	String pedirOpcioMenu() {
		String resp;
		teclado = new Scanner(System.in);
		System.out.print("Elige una opción (1,2,3,4,5,6,7,8,9,10 o 0 (Salir)): ");
		resp = teclado.nextLine();
		if (resp.isEmpty()) {
			resp = " ";
		}
		return resp;

	}

	/**
	 * metodo que creara un articulo
	 * 
	 * @param articulo clase articulo
	 */
	void addArticulo() {

		System.out.println("Codigo: ");
		String codigo = teclado.nextLine();
		if (contro.existeArticulo(codigo)) {
			System.out.println("El codigo " + codigo + " de artículo ya existe.");
			addArticulo();
		} else {

			System.out.println("Descripción: ");
			String descripcion = teclado.nextLine();
			
			String precioVenta;
			do {
				System.out.println("Precio de venta: ");
				precioVenta = teclado.nextLine();
			} while (!esFloat(precioVenta));
			
			String gastosEnvio;
			do {
				System.out.println("Gastos de envio: ");
				gastosEnvio = teclado.nextLine();
			} while (!esFloat(gastosEnvio));

			System.out.println("Tiempo de preparación EN MINUTOS: ");
			boolean prep = true;
			int tiempoPreparacion;
            do{
                tiempoPreparacion = teclado.nextInt();
                try{

                    if (tiempoPreparacion < 120){
                        throw new Exceptions("El tiempo de preparación no puede ser inferior a 120min. Vuelve a introducirlo:");

                    } else {
                    	if (precioVenta.contains(",")) {
                    		precioVenta = precioVenta.replace(",", ".");
            			}
                    	if (gastosEnvio.contains(",")) {
                    		gastosEnvio = gastosEnvio.replace(",", ".");
            			}
                        System.out.println(contro.addArticulo(codigo, descripcion, Float.parseFloat(precioVenta), Float.parseFloat(gastosEnvio), tiempoPreparacion));
                        prep = false;
                        pausar();
                     }

                } catch (Exceptions e) {
                    System.out.println(e.getMessage());
                }

            } while(prep);

			System.out.println("");
			
		}
	}

	/**
	 * metodo que muestra los clientes
	 */
	void mostrarArticulos() {
		// Aqui guardaremos en un arraylist la lista de clientes que nos devolvera el
		// controlador
		// y despues lo mostraremos por pantalla con un foreach
		List<Articulo> articulos = contro.mostrarArticulos();
		if (!articulos.isEmpty()) {
			for (Articulo articulo : articulos) {
				System.out.print("- Código: " + articulo.getCodigo() + " ");
				System.out.print(", Descripción: " + articulo.getDescripcion() + " ");
				System.out.print(", Precio de venta: " + articulo.getPrecioVenta() + " ");
				System.out.print(", Gastos de envio: " + articulo.getGastosEnvio() + " ");
				System.out.print(", Tiempo de preparacion: " + articulo.getTiempoPreparacion() + "\n");
			}
		} else {
			System.out.println("Ha habido algun fallo a la hora de recuperar los datos");
		}
		pausar();
	}

	/**
	 * metodo que creara un nuevo cliente especificando su nif, nombre, domicilio,
	 * email y el tipo de cliente que sera, o estandar o premium
	 * 
	 */
	void addCliente() throws Exception {

		System.out.println("Nif: ");
		String nif = teclado.nextLine();
		if (contro.existeCliente(nif)) {
			System.out.println("El cliente con el nif " + nif + " ya existe.");
			addCliente();
		} else {
			System.out.println("Nombre: ");
			String nombre = teclado.nextLine();

			System.out.println("Domicilio: ");
			String domicilio = teclado.nextLine();

			System.out.println("Email: ");
			String email;
	        boolean bool = true;

	        do{
	              email = teclado.nextLine();
	            try{

	                if (!email.contains("@")){
	                    throw new Exceptions("El email debe contener @. Vuelve a introducir su email:");

	                } else {
	                    //System.out.println("El email ha sido aceptado");
	                    bool = false;}

	            } catch (Exceptions e) {
	                System.out.println(e.getMessage());
	            }

	        } while(bool);

			String tipoCliente;
			System.out.println("Escoge el tipo de cliente: (1) Estandar (2) Premium");
			do {
				tipoCliente = teclado.nextLine();
			} while (!"12".contains(tipoCliente));

			// Aqui enviaremos el nif, nombre, domicilio, email y tipo de cliente al
			// controlador

			System.out.println(contro.addCliente(nombre, domicilio, nif, email, tipoCliente));
			System.out.println("");
			pausar();
		}
	}

	/**
	 * metodo que muestra los clientes
	 */
	void mostrarClientes() {
		// Aqui guardaremos en un arraylist la lista de clientes que nos devolvera el
		// controlador
		// y despues lo mostraremos por pantalla con un foreach
		System.out.println("##########################################################################");
		System.out.println("############################# CLIENTES ###################################");
		System.out.println("##########################################################################");
		System.out.println("");
		for (Cliente cliente : contro.mostrarClientesTodos()) {
			System.out.println(cliente + "\n");
		}
		System.out.println("");
		System.out.println("##########################################################################");
		System.out.println("############################# CLIENTES ###################################");
		System.out.println("##########################################################################");
		System.out.println("");
		pausar();
	}

	void mostrarClientesEstandar() {
		// Aqui guardaremos en un arraylist la lista de clientes estandar que nos
		// devolvera el controlador
		// y despues lo mostraremos por pantalla con un foreach
		System.out.println("##########################################################################");
		System.out.println("######################## CLIENTES ESTANDAR ###############################");
		System.out.println("##########################################################################");
		System.out.println("");
		for (Cliente cliente : contro.mostrarClientesEstandar()) {
			System.out.println(cliente.getNombre() + "\n");
		}
		System.out.println("");
		System.out.println("##########################################################################");
		System.out.println("######################## CLIENTES ESTANDAR ###############################");
		System.out.println("##########################################################################");
		System.out.println("");
		pausar();
	}

	void mostrarClientesPremium() {
		// Aqui guardaremos en un arraylist la lista de clientes premium que nos
		// devolvera el controlador
		// y despues lo mostraremos por pantalla con un foreach
		System.out.println("##########################################################################");
		System.out.println("######################## CLIENTES PREMIUM ###############################");
		System.out.println("##########################################################################");
		System.out.println("");
		for (Cliente cliente : contro.mostrarClientesPremium()) {
			System.out.println(cliente.getNombre() + "\n");
		}
		System.out.println("");
		System.out.println("##########################################################################");
		System.out.println("######################## CLIENTES PREMIUM ###############################");
		System.out.println("##########################################################################");
		System.out.println("");
		pausar();
	}

	/**
	 * metodo que creara un pedido nuevo
	 */
	void addPedido() {

		String nifClientes = "";
		String codigoArticulos = "";
		String nif = "";
		String art = "";
		
		String numeroPedido;
		do {
			System.out.println("Número de pedido: ");
			numeroPedido = teclado.nextLine();
		} while (!esInteger(numeroPedido));
		
		if (contro.existePedido(Integer.parseInt(numeroPedido))) {
			System.out.println("Ya existe un pedido con ese código");
			addPedido();
		} else {
			System.out.println("Unidades: ");
			  int unidadesPedido ;

		        boolean unid = true;

		        do{
		             unidadesPedido = Integer.parseInt(teclado.nextLine());
		            try{

		                if (unidadesPedido <= 0 || unidadesPedido > 10){
		                    throw new Exceptions("El número de unidades debe ser superior a 0 e inferior a 10. Vuelve a introducirlo:");

		                } else {
		                    //System.out.println("El número de unidades ha sido aceptado");
		                    unid = false;}

		            } catch (Exceptions e) {
		                System.out.println(e.getMessage());
		            }

		        } while(unid);

			System.out.println("Escoge el cliente del pedido.");
			System.out.println("");
			// Aqui llamaremos al controlador para que nos devuelva la lista de clientes y
			// listarlos
			List <Cliente> clientes = contro.mostrarClientesTodos();
			for (Cliente cliente : clientes) {
				System.out.println("nif: "+cliente.getNif()+" nombre: "+cliente.getNombre()+" mail: "+cliente.getEmail());
				nifClientes += cliente.getNif() + ",";
			}

			System.out.println("");
			do {
				System.out.println("Elige un nif de cliente (" + nifClientes + "): ");
				nif = teclado.nextLine();
			} while (!nifClientes.contains(nif+","));

			System.out.println("Escoge el artículo del pedido.");
			System.out.println("");
			// Aqui llamaremos al controlador para que nos devuelva la lista de articulos y
			// listarlos
			List <Articulo> articulos = contro.mostrarArticulos();
			for (Articulo articulo : articulos) {
				System.out.println("Código: "+articulo.getCodigo()+" descripción: "+articulo.getDescripcion());
				codigoArticulos += articulo.getCodigo() + ",";
			}

			System.out.println("");
			do {
				System.out.println("Elige un código de artículo (" + codigoArticulos + "): ");
				art = teclado.nextLine();
			} while (!codigoArticulos.contains(art+","));

			System.out.println(contro.addPedido(Integer.parseInt(numeroPedido), unidadesPedido, LocalDateTime.now(), nif, art));
			System.out.println("");
			pausar();
		}
	}

	/**
	 * metodo que nos devolvera todos los pedidos enviados
	 */
	void eliminarPedido() {
		// Enviaremos al controlador el pedido que queremos eliminar
		String numeroPedido;
		do {
			System.out.println("Número de pedido: ");
			numeroPedido = teclado.nextLine();
		} while (!esInteger(numeroPedido));
		
		System.out.println(contro.eliminarPedido(Integer.parseInt(numeroPedido)));
		System.out.println("");
		pausar();
	}

	/**
	 * metodo que nos devolvera todos los pedidos enviados
	 */
	void mostrarPedidosEnviados() {
		// Aqui llamaremos al controlador para que nos devuelva la lista de pedidos con
		// el filtro de enviados
		// y lo mostraremos
		String nifClientes = "";
		String nif = "";
		System.out.println("Escoge un cliente:");
		System.out.println("");

		// Aqui llamaremos al controlador para que nos devuelva la lista de clientes y
		// listarlos
		/*for (int i = 0; i < contro.datos.getClientes().getDato().size(); i++) {
			System.out.println(i + 1 + ". " + contro.datos.getClientes().getDato().get(i).getNombre() + "\n");
			numeroClientes += String.valueOf(i + 1) + ",";
		}*/
		System.out.println("");

		System.out.println("");
		List <Cliente> clientes = contro.mostrarClientesTodos();
		for (Cliente cliente : clientes) {
			System.out.println("nif: "+cliente.getNif()+" nombre: "+cliente.getNombre()+" mail: "+cliente.getEmail());
			nifClientes += cliente.getNif() + ",";
		}

		System.out.println("");
		do {
			System.out.println("Elige un nif de cliente (" + nifClientes + "): ");
			nif = teclado.nextLine();
		} while (!nifClientes.contains(nif+","));
		
		List<Pedido> pedidos = contro.mostrarPedEnviados(nif);
		System.out.println("##########################################################################");
		System.out.println("######################## PEDIDOS ENVIADOS ################################");
		System.out.println("##########################################################################");
		System.out.println("");
		Cliente cli= contro.buscarCliente(nif);
		System.out.println("Nif de cliente: " + cli.getNif()+","+ "Nombre del cliente: " +  cli.getNombre());
		System.out.println("");
		for (Pedido pedido : pedidos) {
			Articulo articulo = contro.buscarArticulo(pedido.getArticulo());
			System.out.println("Numero pedido: "+pedido.getNumero_pedido() + ", Unidades pedido: "+pedido.getUnidadesPedido() + ", Total pedido: "+ pedido.getTotalPedido()+
					", Codigo articulo: "+articulo.getCodigo() + ", Descripcion articulo: "+articulo.getDescripcion()+"\n");
		}
		if (pedidos.isEmpty()) {
			System.out.println("El cliente no tiene ningún pedido enviado");
		}
		System.out.println("");
		System.out.println("##########################################################################");
		System.out.println("######################## PEDIDOS ENVIADOS ################################");
		System.out.println("##########################################################################");
		System.out.println("");
		pausar();
	}

	/**
	 * metodo que nos devolvera todos los pedidos pendientes
	 */
	void mostrarPedidosPendientes() {
		// Aqui llamaremos al controlador para que nos devuelva la lista de pedidos con
		// el filtro de pendientes
		// y lo mostraremos
		String nifClientes = "";
		String nif = "";
		System.out.println("Escoge un cliente:");
		System.out.println("");

		// Aqui llamaremos al controlador para que nos devuelva la lista de clientes y
		// listarlos
		System.out.println("");

		List <Cliente> clientes = contro.mostrarClientesTodos();
		for (Cliente cliente : clientes) {
			System.out.println("nif: "+cliente.getNif()+" nombre: "+cliente.getNombre()+" mail: "+cliente.getEmail());
			nifClientes += cliente.getNif() + ",";
		}

		System.out.println("");
		do {
			System.out.println("Elige un nif de cliente (" + nifClientes + "): ");
			nif = teclado.nextLine();
		} while (!nifClientes.contains(nif+","));

		List<Pedido> pedidos = contro.mostrarPedPendientes(nif);
		System.out.println("##########################################################################");
		System.out.println("######################## PEDIDOS PENDIENTES ##############################");
		System.out.println("##########################################################################");
		System.out.println("");
		Cliente cli= contro.buscarCliente(nif);
		System.out.println("Nif de cliente: " + cli.getNif()+","+ "Nombre del cliente: " +  cli.getNombre());
		System.out.println("");
		for (Pedido pedido : pedidos) {
			Articulo articulo = contro.buscarArticulo(pedido.getArticulo());
			System.out.println("Numero pedido: "+pedido.getNumero_pedido() + ", Unidades pedido: "+pedido.getUnidadesPedido() + ", Total pedido: "+ pedido.getTotalPedido()+
					", Codigo articulo: "+articulo.getCodigo() + ", Descripcion articulo: "+articulo.getDescripcion()+"\n");
		}
		if (pedidos.isEmpty()) {
			System.out.println("El cliente no tiene ningún pedido pendiente de enviar");
		}
		System.out.println("");
		System.out.println("##########################################################################");
		System.out.println("######################## PEDIDOS PENDIENTES ##############################");
		System.out.println("##########################################################################");
		System.out.println("");
		pausar();
	}

	/**
	 * controlamos que el numero introducido sea un integer
	 * 
	 * @param numero le pasamos el numero a comprobar
	 * @return retornaremos verdadero o falso dependiendo si es integer o no
	 */
	boolean esInteger(String numero) {
		try {
			Integer.parseInt(numero);
			return true;
		} catch (NumberFormatException err) {
			System.out.println("El carácter debe ser numérico, introduce un número");
			return false;
		}
	}

	/**
	 * controlamos que el numero introducido sea un float
	 * 
	 * @param numero le pasamos el numero a comprobar
	 * @return retornaremos verdadero o falso dependiendo si es float o no
	 */
	boolean esFloat(String numero) {
		try {
			if (numero.contains(",")) {
				numero = numero.replace(",", ".");
			}
			Float.parseFloat(numero);
			return true;
		} catch (NumberFormatException err) {
			System.out.println("El carácter debe ser númerico, introduce un número");
			return false;
		}
	}
}