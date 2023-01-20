package tkim.modelo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Cliente Premium")
public class ClientePremium extends Cliente {
	
	public ClientePremium() {
		
	}
	public ClientePremium(String nombre, String domicilio, String nif, String email, String tipo_cliente) {
		super(nombre, domicilio, nif, email, tipo_cliente);
	}

	@Override
	public String tipoCliente() {
		// TODO Auto-generated method stub
		return "Cliente Premium";
	}

	@Override
	public float calcAnual() {
		// TODO Auto-generated method stub
		return 30;
	}

	@Override
	public float descuentoEnv() {
		// TODO Auto-generated method stub
		return 20;
	}
}
