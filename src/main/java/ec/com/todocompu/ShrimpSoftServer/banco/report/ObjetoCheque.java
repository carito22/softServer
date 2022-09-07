/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.banco.report;

/**
 *
 * @author Andres Guachisaca
 */
public class ObjetoCheque {
	private String chqEmpresa;
	private String chqCuenta;
	private String chqNumero;
	private String chqBeneficiario;
	private String chqCantidad;
	private String chqCiudad;
	private String chqFecha;
	private boolean chqImpreso;
	private boolean chqEntregado;
	private String ctaEmpresa;
	private String ctaCuentaContable;
	private String concEmpresa;
	private String concCuentaContable;
	private String concCodigo;

	private String valorLetra1;
	private String valorLetra2;

	public ObjetoCheque() {
	}

	public ObjetoCheque(String chqEmpresa, String chqCuenta, String chqNumero, String chqBeneficiario,
			String chqCantidad, String chqCiudad, String chqFecha, boolean chqImpreso, boolean chqEntregado,
			String ctaEmpresa, String ctaCuentaContable, String concEmpresa, String concCuentaContable,
			String concCodigo, String valorLetra1, String valorLetra2) {
		this.chqEmpresa = chqEmpresa;
		this.chqCuenta = chqCuenta;
		this.chqNumero = chqNumero;
		this.chqBeneficiario = chqBeneficiario;
		this.chqCantidad = chqCantidad;
		this.chqCiudad = chqCiudad;
		this.chqFecha = chqFecha;
		this.chqImpreso = chqImpreso;
		this.chqEntregado = chqEntregado;
		this.ctaEmpresa = ctaEmpresa;
		this.ctaCuentaContable = ctaCuentaContable;
		this.concEmpresa = concEmpresa;
		this.concCuentaContable = concCuentaContable;
		this.concCodigo = concCodigo;
		this.valorLetra1 = valorLetra1;
		this.valorLetra2 = valorLetra2;
	}

	public String getChqBeneficiario() {
		return chqBeneficiario;
	}

	public void setChqBeneficiario(String chqBeneficiario) {
		this.chqBeneficiario = chqBeneficiario;
	}

	public String getChqCantidad() {
		return chqCantidad;
	}

	public void setChqCantidad(String chqCantidad) {
		this.chqCantidad = chqCantidad;
	}

	public String getChqCiudad() {
		return chqCiudad;
	}

	public void setChqCiudad(String chqCiudad) {
		this.chqCiudad = chqCiudad;
	}

	public String getChqCuenta() {
		return chqCuenta;
	}

	public void setChqCuenta(String chqCuenta) {
		this.chqCuenta = chqCuenta;
	}

	public String getChqEmpresa() {
		return chqEmpresa;
	}

	public void setChqEmpresa(String chqEmpresa) {
		this.chqEmpresa = chqEmpresa;
	}

	public boolean isChqEntregado() {
		return chqEntregado;
	}

	public void setChqEntregado(boolean chqEntregado) {
		this.chqEntregado = chqEntregado;
	}

	public String getChqFecha() {
		return chqFecha;
	}

	public void setChqFecha(String chqFecha) {
		this.chqFecha = chqFecha;
	}

	public boolean isChqImpreso() {
		return chqImpreso;
	}

	public void setChqImpreso(boolean chqImpreso) {
		this.chqImpreso = chqImpreso;
	}

	public String getChqNumero() {
		return chqNumero;
	}

	public void setChqNumero(String chqNumero) {
		this.chqNumero = chqNumero;
	}

	public String getConcCodigo() {
		return concCodigo;
	}

	public void setConcCodigo(String concCodigo) {
		this.concCodigo = concCodigo;
	}

	public String getConcCuentaContable() {
		return concCuentaContable;
	}

	public void setConcCuentaContable(String concCuentaContable) {
		this.concCuentaContable = concCuentaContable;
	}

	public String getConcEmpresa() {
		return concEmpresa;
	}

	public void setConcEmpresa(String concEmpresa) {
		this.concEmpresa = concEmpresa;
	}

	public String getCtaCuentaContable() {
		return ctaCuentaContable;
	}

	public void setCtaCuentaContable(String ctaCuentaContable) {
		this.ctaCuentaContable = ctaCuentaContable;
	}

	public String getCtaEmpresa() {
		return ctaEmpresa;
	}

	public void setCtaEmpresa(String ctaEmpresa) {
		this.ctaEmpresa = ctaEmpresa;
	}

	public String getValorLetra1() {
		return valorLetra1;
	}

	public void setValorLetra1(String valorLetra1) {
		this.valorLetra1 = valorLetra1;
	}

	public String getValorLetra2() {
		return valorLetra2;
	}

	public void setValorLetra2(String valorLetra2) {
		this.valorLetra2 = valorLetra2;
	}
}
