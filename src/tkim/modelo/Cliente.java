package tkim.modelo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="Clientes")
@DiscriminatorColumn(name="tipo_cliente")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Cliente {
	@Column
	private String nombre;
	@Column
	private String domicilio;
	@Id
	private String nif;
	@Column
	private String email;
	@Column(insertable=false, updatable=false)
	private String tipo_cliente;
	@Column
	private float cuota_anual;
	@Column
	private float descuento_envio;
	
	public abstract String tipoCliente();
	public abstract float calcAnual();
	public abstract float descuentoEnv();

	/**
	 * @param nombre
	 * @param domicilio
	 * @param nif
	 * @param email
	 */
	public Cliente() {
		
	}
	public Cliente(String nombre, String domicilio, String nif, String email, String tipoCliente) {
		super();
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.nif = nif;
		this.email = email;
		this.tipo_cliente = tipoCliente;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}
	/**
	 * @param domicilio the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}
	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getTipo_cliente() {
		return tipo_cliente;
	}
	public void setTipo_cliente(String tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}
	
	
	public float getCuota_anual() {
		return cuota_anual;
	}
	public void setCuota_anual(float cuota_anual) {
		this.cuota_anual = cuota_anual;
	}
	public float getDescuento_envio() {
		return descuento_envio;
	}
	public void setDescuento_envio(float descuento_envio) {
		this.descuento_envio = descuento_envio;
	}
	@Override
	public String toString() {
		return "CLIENTE: Nombre = " + nombre + ", Domicilio = " + domicilio + ", NIF = " + nif + ", Email = " + email
				+ ", Tipo Cliente = " + tipoCliente() + ", CalcAnual = " + calcAnual() + ", Descuento Envío= "
				+ descuentoEnv(); 
	}
}
