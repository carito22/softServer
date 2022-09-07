package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;
/**
*
* @author Ing. Carlos Ajila
*/
public class InformacionPago {

	private String valor;
	private String formaPago;

	public InformacionPago(String valor, String formaPago) {
		this.valor = valor;
		this.formaPago = formaPago;
	}

	public InformacionPago() {
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getFormaPago() {
		return this.formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
}
