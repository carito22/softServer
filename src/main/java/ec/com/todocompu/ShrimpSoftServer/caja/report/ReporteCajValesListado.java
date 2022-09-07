package ec.com.todocompu.ShrimpSoftServer.caja.report;

import java.math.BigDecimal;

public class ReporteCajValesListado {

	private String valeEmpresa;
	private String valePeriodo;
	private String valeMotivo;
	private String valeNumero;
	private String valeFecha;
	private BigDecimal valeValor;
	private String valeBeneficiario;
	private String valeObservaciones;
	private Boolean valeAnulado;
	private String usrEmpresa;
	private String usrCodigo;
	private String usrFechaInserta;
	private String concEmpresa;
	private String concCodigo;

	public ReporteCajValesListado() {
	}

	public ReporteCajValesListado(String valePeriodo, String valeFecha, String valeNumero, BigDecimal valeValor,
			String valeBeneficiario, String valeObservaciones, Boolean valeAnulado) {
		this.valePeriodo = valePeriodo;
		this.valeFecha = valeFecha;
		this.valeNumero = valeNumero;
		this.valeValor = valeValor;
		this.valeBeneficiario = valeBeneficiario;
		this.valeObservaciones = valeObservaciones;
		this.valeAnulado = valeAnulado;
	}

	public String getConcCodigo() {
		return concCodigo;
	}

	public void setConcCodigo(String concCodigo) {
		this.concCodigo = concCodigo;
	}

	public String getConcEmpresa() {
		return concEmpresa;
	}

	public void setConcEmpresa(String concEmpresa) {
		this.concEmpresa = concEmpresa;
	}

	public String getUsrCodigo() {
		return usrCodigo;
	}

	public void setUsrCodigo(String usrCodigo) {
		this.usrCodigo = usrCodigo;
	}

	public String getUsrEmpresa() {
		return usrEmpresa;
	}

	public void setUsrEmpresa(String usrEmpresa) {
		this.usrEmpresa = usrEmpresa;
	}

	public String getUsrFechaInserta() {
		return usrFechaInserta;
	}

	public void setUsrFechaInserta(String usrFechaInserta) {
		this.usrFechaInserta = usrFechaInserta;
	}

	public Boolean getValeAnulado() {
		return valeAnulado;
	}

	public void setValeAnulado(Boolean valeAnulado) {
		this.valeAnulado = valeAnulado;
	}

	public String getValeBeneficiario() {
		return valeBeneficiario;
	}

	public void setValeBeneficiario(String valeBeneficiario) {
		this.valeBeneficiario = valeBeneficiario;
	}

	public String getValeEmpresa() {
		return valeEmpresa;
	}

	public void setValeEmpresa(String valeEmpresa) {
		this.valeEmpresa = valeEmpresa;
	}

	public String getValeFecha() {
		return valeFecha;
	}

	public void setValeFecha(String valeFecha) {
		this.valeFecha = valeFecha;
	}

	public String getValeMotivo() {
		return valeMotivo;
	}

	public void setValeMotivo(String valeMotivo) {
		this.valeMotivo = valeMotivo;
	}

	public String getValeNumero() {
		return valeNumero;
	}

	public void setValeNumero(String valeNumero) {
		this.valeNumero = valeNumero;
	}

	public String getValeObservaciones() {
		return valeObservaciones;
	}

	public void setValeObservaciones(String valeObservaciones) {
		this.valeObservaciones = valeObservaciones;
	}

	public String getValePeriodo() {
		return valePeriodo;
	}

	public void setValePeriodo(String valePeriodo) {
		this.valePeriodo = valePeriodo;
	}

	public BigDecimal getValeValor() {
		return valeValor;
	}

	public void setValeValor(BigDecimal valeValor) {
		this.valeValor = valeValor;
	}
}
