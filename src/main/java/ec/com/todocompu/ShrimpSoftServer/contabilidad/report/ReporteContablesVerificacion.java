/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import java.math.BigDecimal;

/**
 *
 * @author paca94
 */
public class ReporteContablesVerificacion {

	private String vcPeriodo;
	private String vcTipo;
	private String vcNumero;
	private String vcFecha;
	private String vcPendiente;
	private String vcBloqueado;
	private String vcAnulado;
	private BigDecimal vcDebitos;
	private BigDecimal vcCreditos;
	private String vcObservaciones;

	public ReporteContablesVerificacion() {
	}

	public ReporteContablesVerificacion(String vcPeriodo, String vcTipo, String vcNumero, String vcFecha,
			String vcPendiente, String vcBloqueado, String vcAnulado, BigDecimal vcDebitos, BigDecimal vcCreditos,
			String vcObservaciones) {
		this.vcPeriodo = vcPeriodo;
		this.vcTipo = vcTipo;
		this.vcNumero = vcNumero;
		this.vcFecha = vcFecha;
		this.vcPendiente = vcPendiente;
		this.vcBloqueado = vcBloqueado;
		this.vcAnulado = vcAnulado;
		this.vcDebitos = vcDebitos;
		this.vcCreditos = vcCreditos;
		this.vcObservaciones = vcObservaciones;
	}

	public String getVcAnulado() {
		return vcAnulado;
	}

	public void setVcAnulado(String vcAnulado) {
		this.vcAnulado = vcAnulado;
	}

	public String getVcBloqueado() {
		return vcBloqueado;
	}

	public void setVcBloqueado(String vcBloqueado) {
		this.vcBloqueado = vcBloqueado;
	}

	public BigDecimal getVcCreditos() {
		return vcCreditos;
	}

	public void setVcCreditos(BigDecimal vcCreditos) {
		this.vcCreditos = vcCreditos;
	}

	public BigDecimal getVcDebitos() {
		return vcDebitos;
	}

	public void setVcDebitos(BigDecimal vcDebitos) {
		this.vcDebitos = vcDebitos;
	}

	public String getVcFecha() {
		return vcFecha;
	}

	public void setVcFecha(String vcFecha) {
		this.vcFecha = vcFecha;
	}

	public String getVcNumero() {
		return vcNumero;
	}

	public void setVcNumero(String vcNumero) {
		this.vcNumero = vcNumero;
	}

	public String getVcObservaciones() {
		return vcObservaciones;
	}

	public void setVcObservaciones(String vcObservaciones) {
		this.vcObservaciones = vcObservaciones;
	}

	public String getVcPendiente() {
		return vcPendiente;
	}

	public void setVcPendiente(String vcPendiente) {
		this.vcPendiente = vcPendiente;
	}

	public String getVcPeriodo() {
		return vcPeriodo;
	}

	public void setVcPeriodo(String vcPeriodo) {
		this.vcPeriodo = vcPeriodo;
	}

	public String getVcTipo() {
		return vcTipo;
	}

	public void setVcTipo(String vcTipo) {
		this.vcTipo = vcTipo;
	}
}
