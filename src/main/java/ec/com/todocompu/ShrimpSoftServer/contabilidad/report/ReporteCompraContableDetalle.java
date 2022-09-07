/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import java.math.BigDecimal;

/**
 *
 * @author José
 */
public class ReporteCompraContableDetalle {

	//////////////////// COMPRAS CABECERA
	// private String empCodigo;////
	// private String perCodigo;///
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
	///////////////// COMPRAS DETALLE
	private String bodCodigo;
	private String proCodigoPrincipal;
	private String proNombre;
	private BigDecimal detCantidad;
	private String detMedida;
	private BigDecimal detPrecio;
	private BigDecimal detTotal;
	private Boolean detPendiente;
	private Boolean detIva;
	private String secCodigo;
	private String pisNumero;
	private String detFecha;
	// private Integer detSecuencia;
	// private Integer detOrden;
	private BigDecimal valorUltimaCompra;
	private BigDecimal detCantidadCaja;
	private String detEmpaque;
	private String detPresentacionUnidad;
	private String detPresentacionCaja;

	///////////////////////////////////////
	////////// titulo reporte
	private String titulo;

	/////////// contable
	private String empCodigo;
	private String perCodigo;
	private String tipCodigo;
	private String conNumero;
	private String conFecha;
	private Boolean conPendiente;
	private Boolean conRevisado;
	private Boolean conAnulado;
	private Boolean conGenerado;
	private String conConcepto;
	private String conDetalle;
	private String conObservaciones;
	private String usrInserta;
	private String usrFechaInserta;

	private String impElaborado;
	private String impAprobado;
	private String impRevisado;
	private String impContabilidad;

	/////////// detalle
	private String conCtaCodigoPadre;
	private String conNombreCuentaPadre;
	private String conCtaCodigo;
	private String conNombreCuenta;
	private String detCentroProduccion;
	private String detCentroCosto;
	private String detDocumento;
	private Character detDebitoCredito;
	private java.math.BigDecimal detValor;
	private Boolean detGenerado;
	private String detReferencia;
	private Integer detOrden;
	private Integer detSecuencia;

	public ReporteCompraContableDetalle() {

	}

	public ReporteCompraContableDetalle(String empCodigo, String perCodigo, String motCodigo, String compNumero,
			String compDocumentoTipo, String compDocumentoNumero, String provCodigo,
			String provNombre, String compFecha, String compFechaVencimiento, BigDecimal compIvaVigente,
			String compObservaciones, Boolean compPendiente, Boolean compRevisado, Boolean compAnulado,
			String compFormaPago, BigDecimal compBase0, BigDecimal compBaseimponible, BigDecimal compMontoiva,
			BigDecimal compTotal, BigDecimal compValorretenido, BigDecimal compSaldo, String codigoSector,
			String contPeriodo, String contTipo, String contNumero, String usrInsertaCompra,
			String usrFechaInsertaCompra, Boolean compElectronica, String bodCodigo, String proCodigoPrincipal,
			String proNombre, BigDecimal detCantidad, String detMedida, BigDecimal detPrecio, BigDecimal detTotal,
			Boolean detPendiente, Boolean detIva, String secCodigo, String pisNumero, String detFecha,
			Integer detSecuencia, Integer detOrden, BigDecimal valorUltimaCompra, BigDecimal detCantidadCaja,
			String detEmpaque, String detPresentacionUnidad, String detPresentacionCaja, String titulo,
			String tipCodigo, String conNumero, String conFecha, Boolean conPendiente, Boolean conRevisado,
			Boolean conAnulado, Boolean conGenerado, String conConcepto, String conDetalle, String conObservaciones,
			String usrInserta, String usrFechaInserta, String impElaborado, String impAprobado, String impRevisado,
			String impContabilidad, String conCtaCodigoPadre, String conNombreCuentaPadre, String conCtaCodigo,
			String conNombreCuenta, String detCentroProduccion, String detCentroCosto, String detDocumento,
			Character detDebitoCredito, BigDecimal detValor, Boolean detGenerado, String detReferencia) {
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
		this.titulo = titulo;
		this.tipCodigo = tipCodigo;
		this.conNumero = conNumero;
		this.conFecha = conFecha;
		this.conPendiente = conPendiente;
		this.conRevisado = conRevisado;
		this.conAnulado = conAnulado;
		this.conGenerado = conGenerado;
		this.conConcepto = conConcepto;
		this.conDetalle = conDetalle;
		this.conObservaciones = conObservaciones;
		this.usrInserta = usrInserta;
		this.usrFechaInserta = usrFechaInserta;
		this.impElaborado = impElaborado;
		this.impAprobado = impAprobado;
		this.impRevisado = impRevisado;
		this.impContabilidad = impContabilidad;
		this.conCtaCodigoPadre = conCtaCodigoPadre;
		this.conNombreCuentaPadre = conNombreCuentaPadre;
		this.conCtaCodigo = conCtaCodigo;
		this.conNombreCuenta = conNombreCuenta;
		this.detCentroProduccion = detCentroProduccion;
		this.detCentroCosto = detCentroCosto;
		this.detDocumento = detDocumento;
		this.detDebitoCredito = detDebitoCredito;
		this.detValor = detValor;
		this.detGenerado = detGenerado;
		this.detReferencia = detReferencia;
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

	public Boolean getConAnulado() {
		return conAnulado;
	}

	public void setConAnulado(Boolean conAnulado) {
		this.conAnulado = conAnulado;
	}

	public String getConConcepto() {
		return conConcepto;
	}

	public void setConConcepto(String conConcepto) {
		this.conConcepto = conConcepto;
	}

	public String getConCtaCodigo() {
		return conCtaCodigo;
	}

	public void setConCtaCodigo(String conCtaCodigo) {
		this.conCtaCodigo = conCtaCodigo;
	}

	public String getConCtaCodigoPadre() {
		return conCtaCodigoPadre;
	}

	public void setConCtaCodigoPadre(String conCtaCodigoPadre) {
		this.conCtaCodigoPadre = conCtaCodigoPadre;
	}

	public String getConDetalle() {
		return conDetalle;
	}

	public void setConDetalle(String conDetalle) {
		this.conDetalle = conDetalle;
	}

	public String getConFecha() {
		return conFecha;
	}

	public void setConFecha(String conFecha) {
		this.conFecha = conFecha;
	}

	public Boolean getConGenerado() {
		return conGenerado;
	}

	public void setConGenerado(Boolean conGenerado) {
		this.conGenerado = conGenerado;
	}

	public String getConNombreCuenta() {
		return conNombreCuenta;
	}

	public void setConNombreCuenta(String conNombreCuenta) {
		this.conNombreCuenta = conNombreCuenta;
	}

	public String getConNombreCuentaPadre() {
		return conNombreCuentaPadre;
	}

	public void setConNombreCuentaPadre(String conNombreCuentaPadre) {
		this.conNombreCuentaPadre = conNombreCuentaPadre;
	}

	public String getConNumero() {
		return conNumero;
	}

	public void setConNumero(String conNumero) {
		this.conNumero = conNumero;
	}

	public String getConObservaciones() {
		return conObservaciones;
	}

	public void setConObservaciones(String conObservaciones) {
		this.conObservaciones = conObservaciones;
	}

	public Boolean getConPendiente() {
		return conPendiente;
	}

	public void setConPendiente(Boolean conPendiente) {
		this.conPendiente = conPendiente;
	}

	public Boolean getConRevisado() {
		return conRevisado;
	}

	public void setConRevisado(Boolean conRevisado) {
		this.conRevisado = conRevisado;
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

	public BigDecimal getDetCantidad() {
		return detCantidad;
	}

	public void setDetCantidad(BigDecimal detCantidad) {
		this.detCantidad = detCantidad;
	}

	public BigDecimal getDetCantidadCaja() {
		return detCantidadCaja;
	}

	public void setDetCantidadCaja(BigDecimal detCantidadCaja) {
		this.detCantidadCaja = detCantidadCaja;
	}

	public String getDetCentroCosto() {
		return detCentroCosto;
	}

	public void setDetCentroCosto(String detCentroCosto) {
		this.detCentroCosto = detCentroCosto;
	}

	public String getDetCentroProduccion() {
		return detCentroProduccion;
	}

	public void setDetCentroProduccion(String detCentroProduccion) {
		this.detCentroProduccion = detCentroProduccion;
	}

	public Character getDetDebitoCredito() {
		return detDebitoCredito;
	}

	public void setDetDebitoCredito(Character detDebitoCredito) {
		this.detDebitoCredito = detDebitoCredito;
	}

	public String getDetDocumento() {
		return detDocumento;
	}

	public void setDetDocumento(String detDocumento) {
		this.detDocumento = detDocumento;
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

	public Boolean getDetGenerado() {
		return detGenerado;
	}

	public void setDetGenerado(Boolean detGenerado) {
		this.detGenerado = detGenerado;
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

	public BigDecimal getDetPrecio() {
		return detPrecio;
	}

	public void setDetPrecio(BigDecimal detPrecio) {
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

	public String getDetReferencia() {
		return detReferencia;
	}

	public void setDetReferencia(String detReferencia) {
		this.detReferencia = detReferencia;
	}

	public Integer getDetSecuencia() {
		return detSecuencia;
	}

	public void setDetSecuencia(Integer detSecuencia) {
		this.detSecuencia = detSecuencia;
	}

	public BigDecimal getDetTotal() {
		return detTotal;
	}

	public void setDetTotal(BigDecimal detTotal) {
		this.detTotal = detTotal;
	}

	public BigDecimal getDetValor() {
		return detValor;
	}

	public void setDetValor(BigDecimal detValor) {
		this.detValor = detValor;
	}

	public String getEmpCodigo() {
		return empCodigo;
	}

	public void setEmpCodigo(String empCodigo) {
		this.empCodigo = empCodigo;
	}

	public String getImpAprobado() {
		return impAprobado;
	}

	public void setImpAprobado(String impAprobado) {
		this.impAprobado = impAprobado;
	}

	public String getImpContabilidad() {
		return impContabilidad;
	}

	public void setImpContabilidad(String impContabilidad) {
		this.impContabilidad = impContabilidad;
	}

	public String getImpElaborado() {
		return impElaborado;
	}

	public void setImpElaborado(String impElaborado) {
		this.impElaborado = impElaborado;
	}

	public String getImpRevisado() {
		return impRevisado;
	}

	public void setImpRevisado(String impRevisado) {
		this.impRevisado = impRevisado;
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

	public String getTipCodigo() {
		return tipCodigo;
	}

	public void setTipCodigo(String tipCodigo) {
		this.tipCodigo = tipCodigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUsrFechaInserta() {
		return usrFechaInserta;
	}

	public void setUsrFechaInserta(String usrFechaInserta) {
		this.usrFechaInserta = usrFechaInserta;
	}

	public String getUsrFechaInsertaCompra() {
		return usrFechaInsertaCompra;
	}

	public void setUsrFechaInsertaCompra(String usrFechaInsertaCompra) {
		this.usrFechaInsertaCompra = usrFechaInsertaCompra;
	}

	public String getUsrInserta() {
		return usrInserta;
	}

	public void setUsrInserta(String usrInserta) {
		this.usrInserta = usrInserta;
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
