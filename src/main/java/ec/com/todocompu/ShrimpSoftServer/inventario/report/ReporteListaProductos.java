/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteListaProductos {
	private String desde;
	private String hasta;
	private String motivo;
	private String cliente;
	private String tipoDocumento;
	private String bodega;

	///////////// DETALLE
	private String proCodigoPrincipal;
	private String proCodigoBarra;
	private String proNombre;
	private String proCategoria;
	private java.math.BigDecimal stockSaldo;
	private java.math.BigDecimal stockCosto;
	private String detalleMedida;
	private java.math.BigDecimal stockPrecio1;
	private java.math.BigDecimal stockPrecio2;
	private java.math.BigDecimal stockPrecio3;
	private java.math.BigDecimal stockPrecio4;
	private java.math.BigDecimal stockPrecio5;
	private Boolean proGravaIva;

	public ReporteListaProductos() {
	}

	public ReporteListaProductos(String desde, String hasta, String motivo, String cliente, String tipoDocumento,
			String bodega, String proCodigoPrincipal, String proCodigoBarra, String proNombre, String proCategoria,
			BigDecimal stockSaldo, BigDecimal stockCosto, String detalleMedida, BigDecimal stockPrecio1,
			BigDecimal stockPrecio2, BigDecimal stockPrecio3, BigDecimal stockPrecio4, BigDecimal stockPrecio5,
			Boolean proGravaIva) {
		this.desde = desde;
		this.hasta = hasta;
		this.motivo = motivo;
		this.cliente = cliente;
		this.tipoDocumento = tipoDocumento;
		this.bodega = bodega;
		this.proCodigoPrincipal = proCodigoPrincipal;
		this.proCodigoBarra = proCodigoBarra;
		this.proNombre = proNombre;
		this.proCategoria = proCategoria;
		this.stockSaldo = stockSaldo;
		this.stockCosto = stockCosto;
		this.detalleMedida = detalleMedida;
		this.stockPrecio1 = stockPrecio1;
		this.stockPrecio2 = stockPrecio2;
		this.stockPrecio3 = stockPrecio3;
		this.stockPrecio4 = stockPrecio4;
		this.stockPrecio5 = stockPrecio5;
		this.proGravaIva = proGravaIva;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
	}

	public String getDetalleMedida() {
		return detalleMedida;
	}

	public void setDetalleMedida(String detalleMedida) {
		this.detalleMedida = detalleMedida;
	}

	public String getHasta() {
		return hasta;
	}

	public void setHasta(String hasta) {
		this.hasta = hasta;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getProCategoria() {
		return proCategoria;
	}

	public void setProCategoria(String proCategoria) {
		this.proCategoria = proCategoria;
	}

	public String getProCodigoBarra() {
		return proCodigoBarra;
	}

	public void setProCodigoBarra(String proCodigoBarra) {
		this.proCodigoBarra = proCodigoBarra;
	}

	public String getProCodigoPrincipal() {
		return proCodigoPrincipal;
	}

	public void setProCodigoPrincipal(String proCodigoPrincipal) {
		this.proCodigoPrincipal = proCodigoPrincipal;
	}

	public Boolean getProGravaIva() {
		return proGravaIva;
	}

	public void setProGravaIva(Boolean proGravaIva) {
		this.proGravaIva = proGravaIva;
	}

	public String getProNombre() {
		return proNombre;
	}

	public void setProNombre(String proNombre) {
		this.proNombre = proNombre;
	}

	public BigDecimal getStockCosto() {
		return stockCosto;
	}

	public void setStockCosto(BigDecimal stockCosto) {
		this.stockCosto = stockCosto;
	}

	public BigDecimal getStockPrecio1() {
		return stockPrecio1;
	}

	public void setStockPrecio1(BigDecimal stockPrecio1) {
		this.stockPrecio1 = stockPrecio1;
	}

	public BigDecimal getStockPrecio2() {
		return stockPrecio2;
	}

	public void setStockPrecio2(BigDecimal stockPrecio2) {
		this.stockPrecio2 = stockPrecio2;
	}

	public BigDecimal getStockPrecio3() {
		return stockPrecio3;
	}

	public void setStockPrecio3(BigDecimal stockPrecio3) {
		this.stockPrecio3 = stockPrecio3;
	}

	public BigDecimal getStockPrecio4() {
		return stockPrecio4;
	}

	public void setStockPrecio4(BigDecimal stockPrecio4) {
		this.stockPrecio4 = stockPrecio4;
	}

	public BigDecimal getStockPrecio5() {
		return stockPrecio5;
	}

	public void setStockPrecio5(BigDecimal stockPrecio5) {
		this.stockPrecio5 = stockPrecio5;
	}

	public BigDecimal getStockSaldo() {
		return stockSaldo;
	}

	public void setStockSaldo(BigDecimal stockSaldo) {
		this.stockSaldo = stockSaldo;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

}
