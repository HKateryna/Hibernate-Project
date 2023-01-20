package tkim.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Cliente Estandar")
public class ClienteEstandar extends Cliente{
	
	public ClienteEstandar() {
		
	}
	public ClienteEstandar(String nombre, String domicilio, String nif, String email, String tipo_cliente) {
		super(nombre, domicilio, nif, email, tipo_cliente);
	}

	@Override
	public String tipoCliente() {
		// TODO Auto-generated method stub
		return "Cliente Estandar";
	}



	@Override
	public float calcAnual() {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public float descuentoEnv() {
		// TODO Auto-generated method stub
		return 0;
	}

}
