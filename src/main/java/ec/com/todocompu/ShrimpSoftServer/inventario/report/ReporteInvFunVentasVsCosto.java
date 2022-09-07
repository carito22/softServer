/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.math.BigDecimal;

/**
 *
 * @author misayo
 */
public class ReporteInvFunVentasVsCosto {
	// CABECERA
	private String fechaDesde;
	private String fechaHasta;
	private String bodega;
	private String cliente;
	// DETALLE
	private String vcProducto;
	private String vcCodigo;
	private String vcMedida;
	private java.math.BigDecimal vcCantidad;
	private java.math.BigDecimal vcTotalVentas;
	private java.math.BigDecimal vcTotalCosto;
	private java.math.BigDecimal vcPorcentaje;

	public ReporteInvFunVentasVsCosto() {
	}

	public ReporteInvFunVentasVsCosto(String fechaDesde, String fechaHasta, String bodega, String cliente,
			String vcProducto, String vcCodigo, String vcMedida, BigDecimal vcCantidad, BigDecimal vcTotalVentas,
			BigDecimal vcTotalCosto, BigDecimal vcPorcentaje) {
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.bodega = bodega;
		this.cliente = cliente;
		this.vcProducto = vcProducto;
		this.vcCodigo = vcCodigo;
		this.vcMedida = vcMedida;
		this.vcCantidad = vcCantidad;
		this.vcTotalVentas = vcTotalVentas;
		this.vcTotalCosto = vcTotalCosto;
		this.vcPorcentaje = vcPorcentaje;
	}

	public String getBodega() {
		return bodega;
	}

	public void setBodega(String bodega) {
		this.bodega = bodega;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
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

	public BigDecimal getVcCantidad() {
		return vcCantidad;
	}

	public void setVcCantidad(BigDecimal vcCantidad) {
		this.vcCantidad = vcCantidad;
	}

	public String getVcCodigo() {
		return vcCodigo;
	}

	public void setVcCodigo(String vcCodigo) {
		this.vcCodigo = vcCodigo;
	}

	public String getVcMedida() {
		return vcMedida;
	}

	public void setVcMedida(String vcMedida) {
		this.vcMedida = vcMedida;
	}

	public BigDecimal getVcPorcentaje() {
		return vcPorcentaje;
	}

	public void setVcPorcentaje(BigDecimal vcPorcentaje) {
		this.vcPorcentaje = vcPorcentaje;
	}

	public String getVcProducto() {
		return vcProducto;
	}

	public void setVcProducto(String vcProducto) {
		this.vcProducto = vcProducto;
	}

	public BigDecimal getVcTotalCosto() {
		return vcTotalCosto;
	}

	public void setVcTotalCosto(BigDecimal vcTotalCosto) {
		this.vcTotalCosto = vcTotalCosto;
	}

	public BigDecimal getVcTotalVentas() {
		return vcTotalVentas;
	}

	public void setVcTotalVentas(BigDecimal vcTotalVentas) {
		this.vcTotalVentas = vcTotalVentas;
	}

}
