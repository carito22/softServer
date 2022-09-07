/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;

import java.math.BigDecimal;

/**
 *
 * @author Trabajo
 */
public class DetallesReembolsoReporte {

    protected String documento;
    protected String identificacionProveedor;
    protected String nombreProveedor;
    protected String fechaEmision;
    protected BigDecimal subIva = BigDecimal.ZERO;
    protected BigDecimal sub0 = BigDecimal.ZERO;
    protected BigDecimal iva = BigDecimal.ZERO;
    protected BigDecimal total = BigDecimal.ZERO;

    public DetallesReembolsoReporte() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getIdentificacionProveedor() {
        return identificacionProveedor;
    }

    public void setIdentificacionProveedor(String identificacionProveedor) {
        this.identificacionProveedor = identificacionProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getSubIva() {
        return subIva;
    }

    public void setSubIva(BigDecimal subIva) {
        this.subIva = subIva;
    }

    public BigDecimal getSub0() {
        return sub0;
    }

    public void setSub0(BigDecimal sub0) {
        this.sub0 = sub0;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
