package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;

import java.math.BigDecimal;
/**
*
* @author Ing. Carlos Ajila
*/
public class TotalComprobante {

	private String subtotal12;
	private String subtotal0;
	private String subtotalNoSujetoIva;
	private BigDecimal subtotal;
	private String iva12;
	private String totalIce;
	private String ivaVigente;
	



	public String getIvaVigente() {
		return ivaVigente;
	}

	public void setIvaVigente(String ivaVigente) {
		this.ivaVigente = ivaVigente;
	}

	public String getSubtotal12() {
		return this.subtotal12;
	}

	public void setSubtotal12(String subtotal12) {
		this.subtotal12 = subtotal12;
	}

	public String getSubtotal0() {
		return this.subtotal0;
	}

	public void setSubtotal0(String subtotal0) {
		this.subtotal0 = subtotal0;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getIva12() {
		return this.iva12;
	}

	public void setIva12(String iva12) {
		this.iva12 = iva12;
	}

	public String getTotalIce() {
		return this.totalIce;
	}

	public void setTotalIce(String totalIce) {
		this.totalIce = totalIce;
	}

	public String getSubtotalNoSujetoIva() {
		return this.subtotalNoSujetoIva;
	}

	public void setSubtotalNoSujetoIva(String subtotalNoSujetoIva) {
		this.subtotalNoSujetoIva = subtotalNoSujetoIva;
	}
}
