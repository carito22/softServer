package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteDetalleAnticiposPrestamos implements Serializable {
	private static final long serialVersionUID = 1L;

	private String empCodigo;
	private String fechaDesde;
	private String fechaHasta;
	private String empCategoria;
	private String empId;
	private String dapTipo;
	private String dapCategoria;
	private String dapFecha;
	private String dapId;
	private String dapNombres;
	private BigDecimal dapValor;
	private String dapFormaPago;
	private String dapDocumento;
	private String dapContable;
	private Boolean dapAnulado;

	public ReporteDetalleAnticiposPrestamos() {
	}

	public ReporteDetalleAnticiposPrestamos(String empCodigo, String fechaDesde, String fechaHasta, String empCategoria,
			String empId, String dapTipo, String dapCategoria, String dapFecha, String dapId, String dapNombres,
			BigDecimal dapValor, String dapFormaPago, String dapDocumento, String dapContable, Boolean dapAnulado) {
		this.empCodigo = empCodigo;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.empCategoria = empCategoria;
		this.empId = empId;
		this.dapTipo = dapTipo;
		this.dapCategoria = dapCategoria;
		this.dapFecha = dapFecha;
		this.dapId = dapId;
		this.dapNombres = dapNombres;
		this.dapValor = dapValor;
		this.dapFormaPago = dapFormaPago;
		this.dapDocumento = dapDocumento;
		this.dapContable = dapContable;
		this.dapAnulado = dapAnulado;
	}

	public Boolean getDapAnulado() {
		return dapAnulado;
	}

	public void setDapAnulado(Boolean dapAnulado) {
		this.dapAnulado = dapAnulado;
	}

	public String getDapCategoria() {
		return dapCategoria;
	}

	public void setDapCategoria(String dapCategoria) {
		this.dapCategoria = dapCategoria;
	}

	public String getDapContable() {
		return dapContable;
	}

	public void setDapContable(String dapContable) {
		this.dapContable = dapContable;
	}

	public String getDapDocumento() {
		return dapDocumento;
	}

	public void setDapDocumento(String dapDocumento) {
		this.dapDocumento = dapDocumento;
	}

	public String getDapFecha() {
		return dapFecha;
	}

	public void setDapFecha(String dapFecha) {
		this.dapFecha = dapFecha;
	}

	public String getDapFormaPago() {
		return dapFormaPago;
	}

	public void setDapFormaPago(String dapFormaPago) {
		this.dapFormaPago = dapFormaPago;
	}

	public String getDapId() {
		return dapId;
	}

	public void setDapId(String dapId) {
		this.dapId = dapId;
	}

	public String getDapNombres() {
		return dapNombres;
	}

	public void setDapNombres(String dapNombres) {
		this.dapNombres = dapNombres;
	}

	public String getDapTipo() {
		return dapTipo;
	}

	public void setDapTipo(String dapTipo) {
		this.dapTipo = dapTipo;
	}

	public BigDecimal getDapValor() {
		return dapValor;
	}

	public void setDapValor(BigDecimal dapValor) {
		this.dapValor = dapValor;
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
