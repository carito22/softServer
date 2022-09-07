package ec.com.todocompu.ShrimpSoftServer.produccion.report;

import java.math.BigDecimal;

public class ReporteListadoGrameaje {
	private String codigoSector, codigoPiscina, corrida, fechaDesde, fechaHasta;

	private String graFecha;
	private BigDecimal gratPromedio;
	private BigDecimal graiPromedio;
	private BigDecimal graBiomasa;
	private BigDecimal graSobrevivencia;
	private BigDecimal gratBalanceadoAcumulado;
	private String graComentario;

	public ReporteListadoGrameaje() {

	}

	public ReporteListadoGrameaje(String codigoSector, String codigoPiscina, String corrida, String fechaDesde,
			String fechaHasta, String graFecha, BigDecimal gratPromedio, BigDecimal graiPromedio, BigDecimal graBiomasa,
			BigDecimal graSobrevivencia, BigDecimal gratBalanceadoAcumulado, String graComentario) {
		this.codigoSector = codigoSector;
		this.codigoPiscina = codigoPiscina;
		this.corrida = corrida;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.graFecha = graFecha;
		this.gratPromedio = gratPromedio;
		this.graiPromedio = graiPromedio;
		this.graBiomasa = graBiomasa;
		this.graSobrevivencia = graSobrevivencia;
		this.gratBalanceadoAcumulado = gratBalanceadoAcumulado;
		this.graComentario = graComentario;
	}

	public String getCodigoSector() {
		return codigoSector;
	}

	public void setCodigoSector(String codigoSector) {
		this.codigoSector = codigoSector;
	}

	public String getCodigoPiscina() {
		return codigoPiscina;
	}

	public void setCodigoPiscina(String codigoPiscina) {
		this.codigoPiscina = codigoPiscina;
	}

	public String getCorrida() {
		return corrida;
	}

	public void setCorrida(String corrida) {
		this.corrida = corrida;
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

	public String getGraFecha() {
		return graFecha;
	}

	public void setGraFecha(String graFecha) {
		this.graFecha = graFecha;
	}

	public BigDecimal getGratPromedio() {
		return gratPromedio;
	}

	public void setGratPromedio(BigDecimal gratPromedio) {
		this.gratPromedio = gratPromedio;
	}

	public BigDecimal getGraiPromedio() {
		return graiPromedio;
	}

	public void setGraiPromedio(BigDecimal graiPromedio) {
		this.graiPromedio = graiPromedio;
	}

	public BigDecimal getGraBiomasa() {
		return graBiomasa;
	}

	public void setGraBiomasa(BigDecimal graBiomasa) {
		this.graBiomasa = graBiomasa;
	}

	public BigDecimal getGraSobrevivencia() {
		return graSobrevivencia;
	}

	public void setGraSobrevivencia(BigDecimal graSobrevivencia) {
		this.graSobrevivencia = graSobrevivencia;
	}

	public BigDecimal getGratBalanceadoAcumulado() {
		return gratBalanceadoAcumulado;
	}

	public void setGratBalanceadoAcumulado(BigDecimal gratBalanceadoAcumulado) {
		this.gratBalanceadoAcumulado = gratBalanceadoAcumulado;
	}

	public String getGraComentario() {
		return graComentario;
	}

	public void setGraComentario(String graComentario) {
		this.graComentario = graComentario;
	}

}
