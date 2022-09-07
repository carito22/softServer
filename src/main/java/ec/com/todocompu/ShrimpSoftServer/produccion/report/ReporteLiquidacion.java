/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
public class ReporteLiquidacion {
	private String rcSector;
	private String rcPiscina;
	private String rcCorrida;
	private java.math.BigDecimal rcHectareaje;
	private String rcFechaSiembra;
	private String rcFechaPesca;
	private java.math.BigDecimal rcDiasCultivo;
	private java.math.BigDecimal rcDiasInactivos;
	private Integer rcDensidad;
	private String rcLaboratorio;
	private String rcNauplio;
	private java.math.BigDecimal rcBiomasaReal;
	private java.math.BigDecimal rcConversionAlim;
	private java.math.BigDecimal rcGramos;
	private String rcSobrevivencia;

	private java.math.BigDecimal rcDirecto;
	private java.math.BigDecimal rcIndirecto;
	private java.math.BigDecimal rcSubtotal1;
	private java.math.BigDecimal rcAdministrativo;
	private java.math.BigDecimal rcFinanciero;
	private java.math.BigDecimal rcGND;
	private java.math.BigDecimal rcSubtotal2;
	private java.math.BigDecimal rcTotal;

	public ReporteLiquidacion() {
	}

	public ReporteLiquidacion(String rcSector, String rcPiscina, String rcCorrida, BigDecimal rcHectareaje,
			String rcFechaSiembra, String rcFechaPesca, BigDecimal rcDiasCultivo, BigDecimal rcDiasInactivos,
			Integer rcDensidad, String rcLaboratorio, String rcNauplio, BigDecimal rcBiomasaReal,
			BigDecimal rcConversionAlim, BigDecimal rcGramos, String rcSobrevivencia, BigDecimal rcDirecto,
			BigDecimal rcIndirecto, BigDecimal rcSubtotal1, BigDecimal rcAdministrativo, BigDecimal rcFinanciero,
			BigDecimal rcGND, BigDecimal rcSubtotal2, BigDecimal rcTotal) {
		this.rcSector = rcSector;
		this.rcPiscina = rcPiscina;
		this.rcCorrida = rcCorrida;
		this.rcHectareaje = rcHectareaje;
		this.rcFechaSiembra = rcFechaSiembra;
		this.rcFechaPesca = rcFechaPesca;
		this.rcDiasCultivo = rcDiasCultivo;
		this.rcDiasInactivos = rcDiasInactivos;
		this.rcDensidad = rcDensidad;
		this.rcLaboratorio = rcLaboratorio;
		this.rcNauplio = rcNauplio;
		this.rcBiomasaReal = rcBiomasaReal;
		this.rcConversionAlim = rcConversionAlim;
		this.rcGramos = rcGramos;
		this.rcSobrevivencia = rcSobrevivencia;
		this.rcDirecto = rcDirecto;
		this.rcIndirecto = rcIndirecto;
		this.rcSubtotal1 = rcSubtotal1;
		this.rcAdministrativo = rcAdministrativo;
		this.rcFinanciero = rcFinanciero;
		this.rcGND = rcGND;
		this.rcSubtotal2 = rcSubtotal2;
		this.rcTotal = rcTotal;
	}

	public String getRcSector() {
		return rcSector;
	}

	public void setRcSector(String rcSector) {
		this.rcSector = rcSector;
	}

	public String getRcPiscina() {
		return rcPiscina;
	}

	public void setRcPiscina(String rcPiscina) {
		this.rcPiscina = rcPiscina;
	}

	public String getRcCorrida() {
		return rcCorrida;
	}

	public void setRcCorrida(String rcCorrida) {
		this.rcCorrida = rcCorrida;
	}

	public BigDecimal getRcHectareaje() {
		return rcHectareaje;
	}

	public void setRcHectareaje(BigDecimal rcHectareaje) {
		this.rcHectareaje = rcHectareaje;
	}

	public String getRcFechaSiembra() {
		return rcFechaSiembra;
	}

	public void setRcFechaSiembra(String rcFechaSiembra) {
		this.rcFechaSiembra = rcFechaSiembra;
	}

	public String getRcFechaPesca() {
		return rcFechaPesca;
	}

	public void setRcFechaPesca(String rcFechaPesca) {
		this.rcFechaPesca = rcFechaPesca;
	}

	public BigDecimal getRcDiasCultivo() {
		return rcDiasCultivo;
	}

	public void setRcDiasCultivo(BigDecimal rcDiasCultivo) {
		this.rcDiasCultivo = rcDiasCultivo;
	}

	public BigDecimal getRcDiasInactivos() {
		return rcDiasInactivos;
	}

	public void setRcDiasInactivos(BigDecimal rcDiasInactivos) {
		this.rcDiasInactivos = rcDiasInactivos;
	}

	public Integer getRcDensidad() {
		return rcDensidad;
	}

	public void setRcDensidad(Integer rcDensidad) {
		this.rcDensidad = rcDensidad;
	}

	public String getRcLaboratorio() {
		return rcLaboratorio;
	}

	public void setRcLaboratorio(String rcLaboratorio) {
		this.rcLaboratorio = rcLaboratorio;
	}

	public String getRcNauplio() {
		return rcNauplio;
	}

	public void setRcNauplio(String rcNauplio) {
		this.rcNauplio = rcNauplio;
	}

	public BigDecimal getRcBiomasaReal() {
		return rcBiomasaReal;
	}

	public void setRcBiomasaReal(BigDecimal rcBiomasaReal) {
		this.rcBiomasaReal = rcBiomasaReal;
	}

	public BigDecimal getRcConversionAlim() {
		return rcConversionAlim;
	}

	public void setRcConversionAlim(BigDecimal rcConversionAlim) {
		this.rcConversionAlim = rcConversionAlim;
	}

	public BigDecimal getRcGramos() {
		return rcGramos;
	}

	public void setRcGramos(BigDecimal rcGramos) {
		this.rcGramos = rcGramos;
	}

	public String getRcSobrevivencia() {
		return rcSobrevivencia;
	}

	public void setRcSobrevivencia(String rcSobrevivencia) {
		this.rcSobrevivencia = rcSobrevivencia;
	}

	public BigDecimal getRcDirecto() {
		return rcDirecto;
	}

	public void setRcDirecto(BigDecimal rcDirecto) {
		this.rcDirecto = rcDirecto;
	}

	public BigDecimal getRcIndirecto() {
		return rcIndirecto;
	}

	public void setRcIndirecto(BigDecimal rcIndirecto) {
		this.rcIndirecto = rcIndirecto;
	}

	public BigDecimal getRcSubtotal1() {
		return rcSubtotal1;
	}

	public void setRcSubtotal1(BigDecimal rcSubtotal1) {
		this.rcSubtotal1 = rcSubtotal1;
	}

	public BigDecimal getRcAdministrativo() {
		return rcAdministrativo;
	}

	public void setRcAdministrativo(BigDecimal rcAdministrativo) {
		this.rcAdministrativo = rcAdministrativo;
	}

	public BigDecimal getRcFinanciero() {
		return rcFinanciero;
	}

	public void setRcFinanciero(BigDecimal rcFinanciero) {
		this.rcFinanciero = rcFinanciero;
	}

	public BigDecimal getRcGND() {
		return rcGND;
	}

	public void setRcGND(BigDecimal rcGND) {
		this.rcGND = rcGND;
	}

	public BigDecimal getRcSubtotal2() {
		return rcSubtotal2;
	}

	public void setRcSubtotal2(BigDecimal rcSubtotal2) {
		this.rcSubtotal2 = rcSubtotal2;
	}

	public BigDecimal getRcTotal() {
		return rcTotal;
	}

	public void setRcTotal(BigDecimal rcTotal) {
		this.rcTotal = rcTotal;
	}
}
