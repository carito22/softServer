package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReporteDetalleAnticipos implements Serializable {

    private static final long serialVersionUID = 1L;

    private String empCodigo;
    private String fechaDesde;
    private String fechaHasta;
    private String empCategoria;
    private String empId;
    private String daCategoria;
    private String daFecha;
    private String daId;
    private String daNombres;
    private BigDecimal daValor;
    private String daFormaPago;
    private String daDocumento;
    private String daContable;
    private Boolean daAnulado;
    private String daObservacion;

    public ReporteDetalleAnticipos() {
    }

    public ReporteDetalleAnticipos(String empCodigo, String fechaDesde, String fechaHasta, String empCategoria,
            String empId, String daCategoria, String daFecha, String daId, String daNombres, BigDecimal daValor,
			String daFormaPago, String daDocumento, String daContable, Boolean daAnulado) {
        this.empCodigo = empCodigo;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.empCategoria = empCategoria;
        this.empId = empId;
        this.daCategoria = daCategoria;
        this.daFecha = daFecha;
        this.daId = daId;
        this.daNombres = daNombres;
        this.daValor = daValor;
        this.daFormaPago = daFormaPago;
        this.daDocumento = daDocumento;
        this.daContable = daContable;
        this.daAnulado = daAnulado;
    }

    public Boolean getDaAnulado() {
        return daAnulado;
    }

    public void setDaAnulado(Boolean daAnulado) {
        this.daAnulado = daAnulado;
    }

    public String getDaCategoria() {
        return daCategoria;
    }

    public void setDaCategoria(String daCategoria) {
        this.daCategoria = daCategoria;
    }

    public String getDaContable() {
        return daContable;
    }

    public void setDaContable(String daContable) {
        this.daContable = daContable;
    }

    public String getDaDocumento() {
        return daDocumento;
    }

    public void setDaDocumento(String daDocumento) {
        this.daDocumento = daDocumento;
    }

    public String getDaFecha() {
        return daFecha;
    }

    public void setDaFecha(String daFecha) {
        this.daFecha = daFecha;
    }

    public String getDaFormaPago() {
        return daFormaPago;
    }

    public void setDaFormaPago(String daFormaPago) {
        this.daFormaPago = daFormaPago;
    }

    public String getDaId() {
        return daId;
    }

    public void setDaId(String daId) {
        this.daId = daId;
    }

    public String getDaNombres() {
        return daNombres;
    }

    public void setDaNombres(String daNombres) {
        this.daNombres = daNombres;
    }

    public BigDecimal getDaValor() {
        return daValor;
    }

    public void setDaValor(BigDecimal daValor) {
        this.daValor = daValor;
    }

    public String getEmpCategoria() {
        return empCategoria;
    }

    public void setEmpCategoria(String empCategoria) {
        this.empCategoria = empCategoria;
    }

    public String getEmpCodigo() {
        return empCodigo;
    }

    public void setEmpCodigo(String empCodigo) {
        this.empCodigo = empCodigo;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public String getDaObservacion() {
        return daObservacion;
    }

    public void setDaObservacion(String daObservacion) {
        this.daObservacion = daObservacion;
    }

}
