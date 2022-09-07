
package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteDetallePrestamos implements Serializable {
	private static final long serialVersionUID = 1L;

	private String empCodigo;
	private String fechaDesde;
	private String fechaHasta;
	private String empCategoria;
	private String empId;
	private String dpCategoria;
	private String dpFecha;
	private String dpId;
	private String dpNombres;
	private BigDecimal dpValor;
	private String dpFormaPago;
	private String dpDocumento;
	private String dpContable;
	private Boolean dpAnulado;

	public ReporteDetallePrestamos() {
	}

	public ReporteDetallePrestamos(String empCodigo, String fechaDesde, String fechaHasta, String empCategoria,
			String empId, String dpCategoria, String dpFecha, String dpId, String dpNombres, BigDecimal dpValor,
			String dpFormaPago, String dpDocumento, String dpContable, Boolean dpAnulado) {
		this.empCodigo = empCodigo;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.empCategoria = empCategoria;
		this.empId = empId;
		this.dpCategoria = dpCategoria;
		this.dpFecha = dpFecha;
		this.dpId = dpId;
		this.dpNombres = dpNombres;
		this.dpValor = dpValor;
		this.dpFormaPago = dpFormaPago;
		this.dpDocumento = dpDocumento;
		this.dpContable = dpContable;
		this.dpAnulado = dpAnulado;
	}

	public Boolean getDpAnulado() {
		return dpAnulado;
	}

	public void setDpAnulado(Boolean dpAnulado) {
		this.dpAnulado = dpAnulado;
	}

	public String getDpCategoria() {
		return dpCategoria;
	}

	public void setDpCategoria(String dpCategoria) {
		this.dpCategoria = dpCategoria;
	}

	public String getDpContable() {
		return dpContable;
	}

	public void setDpContable(String dpContable) {
		this.dpContable = dpContable;
	}

	public String getDpDocumento() {
		return dpDocumento;
	}

	public void setDpDocumento(String dpDocumento) {
		this.dpDocumento = dpDocumento;
	}

	public String getDpFecha() {
		return dpFecha;
	}

	public void setDpFecha(String dpFecha) {
		this.dpFecha = dpFecha;
	}

	public String getDpFormaPago() {
		return dpFormaPago;
	}

	public void setDpFormaPago(String dpFormaPago) {
		this.dpFormaPago = dpFormaPago;
	}

	public String getDpId() {
		return dpId;
	}

	public void setDpId(String dpId) {
		this.dpId = dpId;
	}

	public String getDpNombres() {
		return dpNombres;
	}

	public void setDpNombres(String dpNombres) {
		this.dpNombres = dpNombres;
	}

	public BigDecimal getDpValor() {
		return dpValor;
	}

	public void setDpValor(BigDecimal dpValor) {
		this.dpValor = dpValor;
	}

	public String getEmpCategoria() {
		return empCategoria;
	}

	public void setEmpCategoria(String empCategoria) {
		this.empCategoria = empCategoria;
	}

	public String getEmpCodigo() {
		return empCodigo;
	}

	public void setEmpCodigo(String empCodigo) {
		this.empCodigo = empCodigo;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

}
