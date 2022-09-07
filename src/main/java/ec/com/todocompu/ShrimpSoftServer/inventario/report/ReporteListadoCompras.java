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
public class ReporteListadoCompras {
	private String desde;
	private String hasta;
	private String motivo;
	private String proveedor;
	private String tipoDocumento;

	///////////// DETALLE
	private String compNumeroSistema;
	private String compFecha;
	private String compProveedorRuc;
	private String compProveedorNombre;
	private String compDocumentoTipo;
	private String compDocumentoNumero;
	private String compDocumentoAutorizacion;
	private java.math.BigDecimal compBase0;
	private java.math.BigDecimal compBaseImponible;
	private java.math.BigDecimal compMontoIva;
	private java.math.BigDecimal compTotal;
	private String compFormaPago;
	private String compObservaciones;
	private Boolean compPendiente;
	private Boolean compAnulado;

	public ReporteListadoCompras() {
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

	public BigDecimal getCompBaseImponible() {
		return compBaseImponible;
	}

	public void setCompBaseImponible(BigDecimal compBaseImponible) {
		this.compBaseImponible = compBaseImponible;
	}

	public String getCompDocumentoNumero() {
		return compDocumentoNumero;
	}

	public void setCompDocumentoNumero(String compDocumentoNumero) {
		this.compDocumentoNumero = compDocumentoNumero;
	}

	public String getCompFecha() {
		return compFecha;
	}

	public void setCompFecha(String compFecha) {
		this.compFecha = compFecha;
	}

	public String getCompFormaPago() {
		return compFormaPago;
	}

	public void setCompFormaPago(String compFormaPago) {
		this.compFormaPago = compFormaPago;
	}

	public BigDecimal getCompMontoIva() {
		return compMontoIva;
	}

	public void setCompMontoIva(BigDecimal compMontoIva) {
		this.compMontoIva = compMontoIva;
	}

	public String getCompNumeroSistema() {
		return compNumeroSistema;
	}

	public void setCompNumeroSistema(String compNumeroSistema) {
		this.compNumeroSistema = compNumeroSistema;
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

        public String getCompProveedorRuc() {
            return compProveedorRuc;
        }

        public void setCompProveedorRuc(String compProveedorRuc) {
            this.compProveedorRuc = compProveedorRuc;
        }

        public String getCompProveedorNombre() {
            return compProveedorNombre;
        }

        public void setCompProveedorNombre(String compProveedorNombre) {
            this.compProveedorNombre = compProveedorNombre;
        }

        public String getCompDocumentoTipo() {
            return compDocumentoTipo;
        }

        public void setCompDocumentoTipo(String compDocumentoTipo) {
            this.compDocumentoTipo = compDocumentoTipo;
        }

        public String getCompDocumentoAutorizacion() {
            return compDocumentoAutorizacion;
        }

        public void setCompDocumentoAutorizacion(String compDocumentoAutorizacion) {
            this.compDocumentoAutorizacion = compDocumentoAutorizacion;
        }

	public BigDecimal getCompTotal() {
		return compTotal;
	}

	public void setCompTotal(BigDecimal compTotal) {
		this.compTotal = compTotal;
	}

	public String getDesde() {
		return desde;
	}

	public void setDesde(String desde) {
		this.desde = desde;
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

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
