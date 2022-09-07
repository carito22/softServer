/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.todocompu.ShrimpSoftServer.produccion.report;

import java.math.BigDecimal;

/**
 *
 * @author charly
 */
public class ReporteListadoFunAnalisisPeso {
	private String fecha;
	private String apSector;// character(7),
	private String apPiscina;// character(7),
	private String apCorrida;// numeric(6,2),
	private BigDecimal apHectareas;// numeric(6,2),
	private String apFechaSiembra;// character(7),
	private String apFormaSiembra;// character(7),
	private BigDecimal apDensidadMetroCuadrado;// numeric(6,2),
	private BigDecimal apSobrevivenciaMetroCuadrado;// numeric(6,2),

	private BigDecimal apRaleo;// numeric(6,2),
	private BigDecimal apEdad;// numeric(6,2),
	private BigDecimal apPesoIdeal;// numeric(6,2),
	private BigDecimal apPesoSobreIdeal;// numeric(6,2),
	private BigDecimal apPesoPromedioActual;// numeric(6,2),
	private BigDecimal apPesoIncrementoSemana4;// numeric(6,2),
	private BigDecimal apPesoIncrementoSemana3;// numeric(6,2),
	private BigDecimal apPesoIncrementoSemana2;// numeric(6,2),
	private BigDecimal apPesoIncrementoSemana1;// numeric(6,2),
	private BigDecimal apPesoIncrementoPromedio;// numeric(6,2),
	private String apBalanceadoTipo;// character(50),
	private BigDecimal apBalanceadoSacos; // numeric(6,2),
	private BigDecimal apBalanceadoKilosHectarea; // numeric(6,2),
	private BigDecimal apBalanceadoAcumulado; // numeric(6,2),
	private BigDecimal apBiomasaProyectada; // numeric(6,2),
	private BigDecimal apConversionAlimenticia; // numeric(6,2),
	private String apLaboratorio;// character(25),
	private String apNauplio;// character(25),

	public ReporteListadoFunAnalisisPeso() {
	}

	public ReporteListadoFunAnalisisPeso(String fecha, String apSector, String apPiscina, String apCorrida,
			BigDecimal apHectareas, String apFechaSiembra, String apFormaSiembra, BigDecimal apDensidadMetroCuadrado,
			BigDecimal apSobrevivenciaMetroCuadrado, BigDecimal apRaleo, BigDecimal apEdad, BigDecimal apPesoIdeal,
			BigDecimal apPesoSobreIdeal, BigDecimal apPesoPromedioActual, BigDecimal apPesoIncrementoSemana4,
			BigDecimal apPesoIncrementoSemana3, BigDecimal apPesoIncrementoSemana2, BigDecimal apPesoIncrementoSemana1,
			BigDecimal apPesoIncrementoPromedio, String apBalanceadoTipo, BigDecimal apBalanceadoSacos,
			BigDecimal apBalanceadoKilosHectarea, BigDecimal apBalanceadoAcumulado, BigDecimal apBiomasaProyectada,
			BigDecimal apConversionAlimenticia, String apLaboratorio, String apNauplio) {
		this.fecha = fecha;
		this.apSector = apSector;
		this.apPiscina = apPiscina;
		this.apCorrida = apCorrida;
		this.apHectareas = apHectareas;
		this.apFechaSiembra = apFechaSiembra;
		this.apFormaSiembra = apFormaSiembra;
		this.apDensidadMetroCuadrado = apDensidadMetroCuadrado;
		this.apSobrevivenciaMetroCuadrado = apSobrevivenciaMetroCuadrado;
		this.apRaleo = apRaleo;
		this.apEdad = apEdad;
		this.apPesoIdeal = apPesoIdeal;
		this.apPesoSobreIdeal = apPesoSobreIdeal;
		this.apPesoPromedioActual = apPesoPromedioActual;
		this.apPesoIncrementoSemana4 = apPesoIncrementoSemana4;
		this.apPesoIncrementoSemana3 = apPesoIncrementoSemana3;
		this.apPesoIncrementoSemana2 = apPesoIncrementoSemana2;
		this.apPesoIncrementoSemana1 = apPesoIncrementoSemana1;
		this.apPesoIncrementoPromedio = apPesoIncrementoPromedio;
		this.apBalanceadoTipo = apBalanceadoTipo;
		this.apBalanceadoSacos = apBalanceadoSacos;
		this.apBalanceadoKilosHectarea = apBalanceadoKilosHectarea;
		this.apBalanceadoAcumulado = apBalanceadoAcumulado;
		this.apBiomasaProyectada = apBiomasaProyectada;
		this.apConversionAlimenticia = apConversionAlimenticia;
		this.apLaboratorio = apLaboratorio;
		this.apNauplio = apNauplio;
	}

	public BigDecimal getApBalanceadoAcumulado() {
		return apBalanceadoAcumulado;
	}

	public void setApBalanceadoAcumulado(BigDecimal apBalanceadoAcumulado) {
		this.apBalanceadoAcumulado = apBalanceadoAcumulado;
	}

	public BigDecimal getApBalanceadoKilosHectarea() {
		return apBalanceadoKilosHectarea;
	}

	public void setApBalanceadoKilosHectarea(BigDecimal apBalanceadoKilosHectarea) {
		this.apBalanceadoKilosHectarea = apBalanceadoKilosHectarea;
	}

	public BigDecimal getApBalanceadoSacos() {
		return apBalanceadoSacos;
	}

	public void setApBalanceadoSacos(BigDecimal apBalanceadoSacos) {
		this.apBalanceadoSacos = apBalanceadoSacos;
	}

	public String getApBalanceadoTipo() {
		return apBalanceadoTipo;
	}

	public void setApBalanceadoTipo(String apBalanceadoTipo) {
		this.apBalanceadoTipo = apBalanceadoTipo;
	}

	public BigDecimal getApBiomasaProyectada() {
		return apBiomasaProyectada;
	}

	public void setApBiomasaProyectada(BigDecimal apBiomasaProyectada) {
		this.apBiomasaProyectada = apBiomasaProyectada;
	}

	public BigDecimal getApConversionAlimenticia() {
		return apConversionAlimenticia;
	}

	public void setApConversionAlimenticia(BigDecimal apConversionAlimenticia) {
		this.apConversionAlimenticia = apConversionAlimenticia;
	}

	public String getApCorrida() {
		return apCorrida;
	}

	public void setApCorrida(String apCorrida) {
		this.apCorrida = apCorrida;
	}

	public BigDecimal getApDensidadMetroCuadrado() {
		return apDensidadMetroCuadrado;
	}

	public void setApDensidadMetroCuadrado(BigDecimal apDensidadMetroCuadrado) {
		this.apDensidadMetroCuadrado = apDensidadMetroCuadrado;
	}

	public BigDecimal getApEdad() {
		return apEdad;
	}

	public void setApEdad(BigDecimal apEdad) {
		this.apEdad = apEdad;
	}

	public String getApFechaSiembra() {
		return apFechaSiembra;
	}

	public void setApFechaSiembra(String apFechaSiembra) {
		this.apFechaSiembra = apFechaSiembra;
	}

	public String getApFormaSiembra() {
		return apFormaSiembra;
	}

	public void setApFormaSiembra(String apFormaSiembra) {
		this.apFormaSiembra = apFormaSiembra;
	}

	public BigDecimal getApHectareas() {
		return apHectareas;
	}

	public void setApHectareas(BigDecimal apHectareas) {
		this.apHectareas = apHectareas;
	}

	public String getApLaboratorio() {
		return apLaboratorio;
	}

	public void setApLaboratorio(String apLaboratorio) {
		this.apLaboratorio = apLaboratorio;
	}

	public String getApNauplio() {
		return apNauplio;
	}

	public void setApNauplio(String apNauplio) {
		this.apNauplio = apNauplio;
	}

	public BigDecimal getApPesoIdeal() {
		return apPesoIdeal;
	}

	public void setApPesoIdeal(BigDecimal apPesoIdeal) {
		this.apPesoIdeal = apPesoIdeal;
	}

	public BigDecimal getApPesoIncrementoPromedio() {
		return apPesoIncrementoPromedio;
	}

	public void setApPesoIncrementoPromedio(BigDecimal apPesoIncrementoPromedio) {
		this.apPesoIncrementoPromedio = apPesoIncrementoPromedio;
	}

	public BigDecimal getApPesoIncrementoSemana1() {
		return apPesoIncrementoSemana1;
	}

	public void setApPesoIncrementoSemana1(BigDecimal apPesoIncrementoSemana1) {
		this.apPesoIncrementoSemana1 = apPesoIncrementoSemana1;
	}

	public BigDecimal getApPesoIncrementoSemana2() {
		return apPesoIncrementoSemana2;
	}

	public void setApPesoIncrementoSemana2(BigDecimal apPesoIncrementoSemana2) {
		this.apPesoIncrementoSemana2 = apPesoIncrementoSemana2;
	}

	public BigDecimal getApPesoIncrementoSemana3() {
		return apPesoIncrementoSemana3;
	}

	public void setApPesoIncrementoSemana3(BigDecimal apPesoIncrementoSemana3) {
		this.apPesoIncrementoSemana3 = apPesoIncrementoSemana3;
	}

	public BigDecimal getApPesoIncrementoSemana4() {
		return apPesoIncrementoSemana4;
	}

	public void setApPesoIncrementoSemana4(BigDecimal apPesoIncrementoSemana4) {
		this.apPesoIncrementoSemana4 = apPesoIncrementoSemana4;
	}

	public BigDecimal getApPesoPromedioActual() {
		return apPesoPromedioActual;
	}

	public void setApPesoPromedioActual(BigDecimal apPesoPromedioActual) {
		this.apPesoPromedioActual = apPesoPromedioActual;
	}

	public BigDecimal getApPesoSobreIdeal() {
		return apPesoSobreIdeal;
	}

	public void setApPesoSobreIdeal(BigDecimal apPesoSobreIdeal) {
		this.apPesoSobreIdeal = apPesoSobreIdeal;
	}

	public String getApPiscina() {
		return apPiscina;
	}

	public void setApPiscina(String apPiscina) {
		this.apPiscina = apPiscina;
	}

	public BigDecimal getApRaleo() {
		return apRaleo;
	}

	public void setApRaleo(BigDecimal apRaleo) {
		this.apRaleo = apRaleo;
	}

	public String getApSector() {
		return apSector;
	}

	public void setApSector(String apSector) {
		this.apSector = apSector;
	}

	public BigDecimal getApSobrevivenciaMetroCuadrado() {
		return apSobrevivenciaMetroCuadrado;
	}

	public void setApSobrevivenciaMetroCuadrado(BigDecimal apSobrevivenciaMetroCuadrado) {
		this.apSobrevivenciaMetroCuadrado = apSobrevivenciaMetroCuadrado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
