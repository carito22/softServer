/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.math.BigDecimal;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteCompraDetalleContableDetalle {
	private String empCodigo;
	private String perCodigo;
	private String motCodigo;
	private String compNumero;
	private String compDocumentoTipo;
	private String compDocumentoNumero;
	private String provCodigo;
	private String provNombre;
	private String compFecha;
	private String compFechaVencimiento;
	private BigDecimal compIvaVigente;
	private String compObservaciones;
	private Boolean compPendiente;
	private Boolean compRevisado;
	private Boolean compAnulado;
	private String compFormaPago;

	private BigDecimal compBase0;
	private BigDecimal compBaseimponible;
	private BigDecimal compMontoiva;
	private BigDecimal compTotal;
	private BigDecimal compValorretenido;
	private BigDecimal compSaldo;

	private String codigoSector;

	private String contPeriodo;
	private String contTipo;
	private String contNumero;
	private String usrInsertaCompra;
	private String usrFechaInsertaCompra;
	private Boolean compElectronica;
	// ///////////////COMPRAS DETALLE
	private String bodCodigo;
	private String proCodigoPrincipal;
	private String proNombre;
	private Double detCantidad;
	private String detMedida;
	private Double detPrecio;
	private Double detTotal;
	private Boolean detPendiente;
	private Boolean detIva;
	private String secCodigo;
	private String pisNumero;
	private String detFecha;
	private Integer detSecuencia;
	private Integer detOrden;
	private BigDecimal valorUltimaCompra;
	private BigDecimal detCantidadCaja;
	private String detEmpaque;
	private String detPresentacionUnidad;
	private String detPresentacionCaja;

	List<ConContableReporteTO> listConContableReporteTO;

	public ReporteCompraDetalleContableDetalle(String empCodigo, String perCodigo, String motCodigo, String compNumero,
			String compDocumentoTipo, String compDocumentoNumero, String provCodigo,
			String provNombre, String compFecha, String compFechaVencimiento, BigDecimal compIvaVigente,
			String compObservaciones, Boolean compPendiente, Boolean compRevisado, Boolean compAnulado,
			String compFormaPago, BigDecimal compBase0, BigDecimal compBaseimponible, BigDecimal compMontoiva,
			BigDecimal compTotal, BigDecimal compValorretenido, BigDecimal compSaldo, String codigoSector,
			String contPeriodo, String contTipo, String contNumero, String usrInsertaCompra,
			String usrFechaInsertaCompra, Boolean compElectronica, String bodCodigo, String proCodigoPrincipal,
			String proNombre, Double detCantidad, String detMedida, Double detPrecio, Double detTotal,
			Boolean detPendiente, Boolean detIva, String secCodigo, String pisNumero, String detFecha,
			Integer detSecuencia, Integer detOrden, BigDecimal valorUltimaCompra, BigDecimal detCantidadCaja,
			String detEmpaque, String detPresentacionUnidad, String detPresentacionCaja,
			List<ConContableReporteTO> listConContableReporteTO) {
		this.empCodigo = empCodigo;
		this.perCodigo = perCodigo;
		this.motCodigo = motCodigo;
		this.compNumero = compNumero;
		this.compDocumentoTipo = compDocumentoTipo;
		this.compDocumentoNumero = compDocumentoNumero;
		this.provCodigo = provCodigo;
		this.provNombre = provNombre;
		this.compFecha = compFecha;
		this.compFechaVencimiento = compFechaVencimiento;
		this.compIvaVigente = compIvaVigente;
		this.compObservaciones = compObservaciones;
		this.compPendiente = compPendiente;
		this.compRevisado = compRevisado;
		this.compAnulado = compAnulado;
		this.compFormaPago = compFormaPago;
		this.compBase0 = compBase0;
		this.compBaseimponible = compBaseimponible;
		this.compMontoiva = compMontoiva;
		this.compTotal = compTotal;
		this.compValorretenido = compValorretenido;
		this.compSaldo = compSaldo;
		this.codigoSector = codigoSector;
		this.contPeriodo = contPeriodo;
		this.contTipo = contTipo;
		this.contNumero = contNumero;
		this.usrInsertaCompra = usrInsertaCompra;
		this.usrFechaInsertaCompra = usrFechaInsertaCompra;
		this.compElectronica = compElectronica;
		this.bodCodigo = bodCodigo;
		this.proCodigoPrincipal = proCodigoPrincipal;
		this.proNombre = proNombre;
		this.detCantidad = detCantidad;
		this.detMedida = detMedida;
		this.detPrecio = detPrecio;
		this.detTotal = detTotal;
		this.detPendiente = detPendiente;
		this.detIva = detIva;
		this.secCodigo = secCodigo;
		this.pisNumero = pisNumero;
		this.detFecha = detFecha;
		this.detSecuencia = detSecuencia;
		this.detOrden = detOrden;
		this.valorUltimaCompra = valorUltimaCompra;
		this.detCantidadCaja = detCantidadCaja;
		this.detEmpaque = detEmpaque;
		this.detPresentacionUnidad = detPresentacionUnidad;
		this.detPresentacionCaja = detPresentacionCaja;
		this.listConContableReporteTO = listConContableReporteTO;
	}

	public ReporteCompraDetalleContableDetalle() {
	}

	public String getBodCodigo() {
		return bodCodigo;
	}

	public void setBodCodigo(String bodCodigo) {
		this.bodCodigo = bodCodigo;
	}

	public String getCodigoSector() {
		return codigoSector;
	}

	public void setCodigoSector(String codigoSector) {
		this.codigoSector = codigoSector;
	}

	public Boolean getCompAnulado() {
		return compAnulado;
	}

	public void setCompAnulado(Boolean compAnulado) {
		this.compAnulado = compAnulado;
	}

	public BigDecimal getCompBase0() {
		return compBase0;
	}

	public void setCompBase0(BigDecimal compBase0) {
		this.compBase0 = compBase0;
	}

	public BigDecimal getCompBaseimponible() {
		return compBaseimponible;
	}

	public void setCompBaseimponible(BigDecimal compBaseimponible) {
		this.compBaseimponible = compBaseimponible;
	}

	public String getCompDocumentoNumero() {
		return compDocumentoNumero;
	}

	public void setCompDocumentoNumero(String compDocumentoNumero) {
		this.compDocumentoNumero = compDocumentoNumero;
	}

	public String getCompDocumentoTipo() {
		return compDocumentoTipo;
	}

	public void setCompDocumentoTipo(String compDocumentoTipo) {
		this.compDocumentoTipo = compDocumentoTipo;
	}

	public Boolean getCompElectronica() {
		return compElectronica;
	}

	public void setCompElectronica(Boolean compElectronica) {
		this.compElectronica = compElectronica;
	}

	public String getCompFecha() {
		return compFecha;
	}

	public void setCompFecha(String compFecha) {
		this.compFecha = compFecha;
	}

	public String getCompFechaVencimiento() {
		return compFechaVencimiento;
	}

	public void setCompFechaVencimiento(String compFechaVencimiento) {
		this.compFechaVencimiento = compFechaVencimiento;
	}

	public String getCompFormaPago() {
		return compFormaPago;
	}

	public void setCompFormaPago(String compFormaPago) {
		this.compFormaPago = compFormaPago;
	}

	public BigDecimal getCompIvaVigente() {
		return compIvaVigente;
	}

	public void setCompIvaVigente(BigDecimal compIvaVigente) {
		this.compIvaVigente = compIvaVigente;
	}

	public BigDecimal getCompMontoiva() {
		return compMontoiva;
	}

	public void setCompMontoiva(BigDecimal compMontoiva) {
		this.compMontoiva = compMontoiva;
	}

	public String getCompNumero() {
		return compNumero;
	}

	public void setCompNumero(String compNumero) {
		this.compNumero = compNumero;
	}

	public String getCompObservaciones() {
		return compObservaciones;
	}

	public void setCompObservaciones(String compObservaciones) {
		this.compObservaciones = compObservaciones;
	}

	public Boolean getCompPendiente() {
		return compPendiente;
	}

	public void setCompPendiente(Boolean compPendiente) {
		this.compPendiente = compPendiente;
	}

	public Boolean getCompRevisado() {
		return compRevisado;
	}

	public void setCompRevisado(Boolean compRevisado) {
		this.compRevisado = compRevisado;
	}

	public BigDecimal getCompSaldo() {
		return compSaldo;
	}

	public void setCompSaldo(BigDecimal compSaldo) {
		this.compSaldo = compSaldo;
	}

	public BigDecimal getCompTotal() {
		return compTotal;
	}

	public void setCompTotal(BigDecimal compTotal) {
		this.compTotal = compTotal;
	}

	public BigDecimal getCompValorretenido() {
		return compValorretenido;
	}

	public void setCompValorretenido(BigDecimal compValorretenido) {
		this.compValorretenido = compValorretenido;
	}

	public String getContNumero() {
		return contNumero;
	}

	public void setContNumero(String contNumero) {
		this.contNumero = contNumero;
	}

	public String getContPeriodo() {
		return contPeriodo;
	}

	public void setContPeriodo(String contPeriodo) {
		this.contPeriodo = contPeriodo;
	}

	public String getContTipo() {
		return contTipo;
	}

	public void setContTipo(String contTipo) {
		this.contTipo = contTipo;
	}

	public Double getDetCantidad() {
		return detCantidad;
	}

	public void setDetCantidad(Double detCantidad) {
		this.detCantidad = detCantidad;
	}

	public BigDecimal getDetCantidadCaja() {
		return detCantidadCaja;
	}

	public void setDetCantidadCaja(BigDecimal detCantidadCaja) {
		this.detCantidadCaja = detCantidadCaja;
	}

	public String getDetEmpaque() {
		return detEmpaque;
	}

	public void setDetEmpaque(String detEmpaque) {
		this.detEmpaque = detEmpaque;
	}

	public String getDetFecha() {
		return detFecha;
	}

	public void setDetFecha(String detFecha) {
		this.detFecha = detFecha;
	}

	public Boolean getDetIva() {
		return detIva;
	}

	public void setDetIva(Boolean detIva) {
		this.detIva = detIva;
	}

	public String getDetMedida() {
		return detMedida;
	}

	public void setDetMedida(String detMedida) {
		this.detMedida = detMedida;
	}

	public Integer getDetOrden() {
		return detOrden;
	}

	public void setDetOrden(Integer detOrden) {
		this.detOrden = detOrden;
	}

	public Boolean getDetPendiente() {
		return detPendiente;
	}

	public void setDetPendiente(Boolean detPendiente) {
		this.detPendiente = detPendiente;
	}

	public Double getDetPrecio() {
		return detPrecio;
	}

	public void setDetPrecio(Double detPrecio) {
		this.detPrecio = detPrecio;
	}

	public String getDetPresentacionCaja() {
		return detPresentacionCaja;
	}

	public void setDetPresentacionCaja(String detPresentacionCaja) {
		this.detPresentacionCaja = detPresentacionCaja;
	}

	public String getDetPresentacionUnidad() {
		return detPresentacionUnidad;
	}

	public void setDetPresentacionUnidad(String detPresentacionUnidad) {
		this.detPresentacionUnidad = detPresentacionUnidad;
	}

	public Integer getDetSecuencia() {
		return detSecuencia;
	}

	public void setDetSecuencia(Integer detSecuencia) {
		this.detSecuencia = detSecuencia;
	}

	public Double getDetTotal() {
		return detTotal;
	}

	public void setDetTotal(Double detTotal) {
		this.detTotal = detTotal;
	}

	public String getEmpCodigo() {
		return empCodigo;
	}

	public void setEmpCodigo(String empCodigo) {
		this.empCodigo = empCodigo;
	}

	public List<ConContableReporteTO> getListConContableReporteTO() {
		return listConContableReporteTO;
	}

	public void setListConContableReporteTO(List<ConContableReporteTO> listConContableReporteTO) {
		this.listConContableReporteTO = listConContableReporteTO;
	}

	public String getMotCodigo() {
		return motCodigo;
	}

	public void setMotCodigo(String motCodigo) {
		this.motCodigo = motCodigo;
	}

	public String getPerCodigo() {
		return perCodigo;
	}

	public void setPerCodigo(String perCodigo) {
		this.perCodigo = perCodigo;
	}

	public String getPisNumero() {
		return pisNumero;
	}

	public void setPisNumero(String pisNumero) {
		this.pisNumero = pisNumero;
	}

	public String getProCodigoPrincipal() {
		return proCodigoPrincipal;
	}

	public void setProCodigoPrincipal(String proCodigoPrincipal) {
		this.proCodigoPrincipal = proCodigoPrincipal;
	}

	public String getProNombre() {
		return proNombre;
	}

	public void setProNombre(String proNombre) {
		this.proNombre = proNombre;
	}

	public String getProvCodigo() {
		return provCodigo;
	}

	public void setProvCodigo(String provCodigo) {
		this.provCodigo = provCodigo;
	}

	public String getProvNombre() {
		return provNombre;
	}

	public void setProvNombre(String provNombre) {
		this.provNombre = provNombre;
	}

	public String getSecCodigo() {
		return secCodigo;
	}

	public void setSecCodigo(String secCodigo) {
		this.secCodigo = secCodigo;
	}

	public String getUsrFechaInsertaCompra() {
		return usrFechaInsertaCompra;
	}

	public void setUsrFechaInsertaCompra(String usrFechaInsertaCompra) {
		this.usrFechaInsertaCompra = usrFechaInsertaCompra;
	}

	public String getUsrInsertaCompra() {
		return usrInsertaCompra;
	}

	public void setUsrInsertaCompra(String usrInsertaCompra) {
		this.usrInsertaCompra = usrInsertaCompra;
	}

	public BigDecimal getValorUltimaCompra() {
		return valorUltimaCompra;
	}

	public void setValorUltimaCompra(BigDecimal valorUltimaCompra) {
		this.valorUltimaCompra = valorUltimaCompra;
	}

}
