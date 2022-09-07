package ec.com.todocompu.ShrimpSoftServer.caja.report;

import java.math.BigDecimal;

public class ReporteCajCuadreDeCajaDetallado {

	private String cuadrarCaja;
	private Integer cuaCantidad;
	private BigDecimal cuaBase0;
	private BigDecimal cuaBaseImponible;
	private BigDecimal cuaMontoIva;
	private BigDecimal cuaTotal;
	private String cuaMotivo;
	private String descripcionMotivo;
	private String CuaFecha;

	public ReporteCajCuadreDeCajaDetallado() {
	}

	public ReporteCajCuadreDeCajaDetallado(String cuadrarCaja, Integer cuaCantidad, BigDecimal cuaBase0,
			BigDecimal cuaBaseImponible, BigDecimal cuaMontoIva, BigDecimal cuaTotal, String cuaMotivo,
			String descripcionMotivo, String CuaFecha) {
		this.cuadrarCaja = cuadrarCaja;
		this.cuaCantidad = cuaCantidad;
		this.cuaBase0 = cuaBase0;
		this.cuaBaseImponible = cuaBaseImponible;
		this.cuaMontoIva = cuaMontoIva;
		this.cuaTotal = cuaTotal;
		this.cuaMotivo = cuaMotivo;
		this.descripcionMotivo = descripcionMotivo;
		this.CuaFecha = CuaFecha;
	}

	public BigDecimal getCuaBase0() {
		return cuaBase0;
	}

	public void setCuaBase0(BigDecimal cuaBase0) {
		this.cuaBase0 = cuaBase0;
	}

	public BigDecimal getCuaBaseImponible() {
		return cuaBaseImponible;
	}

	public void setCuaBaseImponible(BigDecimal cuaBaseImponible) {
		this.cuaBaseImponible = cuaBaseImponible;
	}

	public BigDecimal getCuaMontoIva() {
		return cuaMontoIva;
	}

	public void setCuaMontoIva(BigDecimal cuaMontoIva) {
		this.cuaMontoIva = cuaMontoIva;
	}

	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}

	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}

	public String getCuaFecha() {
		return CuaFecha;
	}

	public void setCuaFecha(String CuaFecha) {
		this.CuaFecha = CuaFecha;
	}

	public String getCuaMotivo() {
		return cuaMotivo;
	}

	public void setCuaMotivo(String cuaMotivo) {
		this.cuaMotivo = cuaMotivo;
	}

	public Integer getCuaCantidad() {
		return cuaCantidad;
	}

	public void setCuaCantidad(Integer cuaCantidad) {
		this.cuaCantidad = cuaCantidad;
	}

	public BigDecimal getCuaTotal() {
		return cuaTotal;
	}

	public void setCuaTotal(BigDecimal cuaTotal) {
		this.cuaTotal = cuaTotal;
	}

	public String getCuadrarCaja() {
		return cuadrarCaja;
	}

	public void setCuadrarCaja(String cuadrarCaja) {
		this.cuadrarCaja = cuadrarCaja;
	}
}
