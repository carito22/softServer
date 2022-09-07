/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.facturareembolso.FacturaReembolso;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public class FacturaReembolsoReporte {

    public FacturaReembolso facturaReembolso;
    private String detalle1;
    private String detalle2;
    private String detalle3;
    private List<DetallesAdicionalesReporte> detallesAdiciones;
    private List<DetallesReembolsoReporte> detallesReembolso;
    private List<InformacionAdicional> infoAdicional;
    private List<InformacionPago> formasPago;

    public FacturaReembolsoReporte(FacturaReembolso facturaReembolso) {
        this.facturaReembolso = facturaReembolso;
    }

    public FacturaReembolso getFacturaReembolso() {
        return facturaReembolso;
    }

    public void setFacturaReembolso(FacturaReembolso facturaReembolso) {
        this.facturaReembolso = facturaReembolso;
    }

    public String getDetalle1() {
        return detalle1;
    }

    public void setDetalle1(String detalle1) {
        this.detalle1 = detalle1;
    }

    public String getDetalle2() {
        return detalle2;
    }

    public void setDetalle2(String detalle2) {
        this.detalle2 = detalle2;
    }

    public String getDetalle3() {
        return detalle3;
    }

    public void setDetalle3(String detalle3) {
        this.detalle3 = detalle3;
    }

    public List<DetallesReembolsoReporte> getDetallesReembolso() {
        return this.detallesReembolso;
    }

    public void setDetallesReembolso(List<DetallesReembolsoReporte> detallesReembolso) {
        this.detallesReembolso = detallesReembolso;
    }

    public List<DetallesAdicionalesReporte> getDetallesAdiciones() {
        return this.detallesAdiciones;
    }

    public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
        this.detallesAdiciones = detallesAdiciones;
    }

    public List<InformacionAdicional> getInfoAdicional() {
        if (getFacturaReembolso().getInfoAdicional() != null) {
            this.infoAdicional = new ArrayList();
            if ((getFacturaReembolso().getInfoAdicional().getCampoAdicional() != null)
                    && (!this.facturaReembolso.getInfoAdicional().getCampoAdicional().isEmpty())) {
                for (FacturaReembolso.InfoAdicional.CampoAdicional ca : getFacturaReembolso().getInfoAdicional().getCampoAdicional()) {
                    this.infoAdicional.add(new InformacionAdicional(ca.getValue(), ca.getNombre()));
                }
            }
        }
        return this.infoAdicional;
    }

    public void setInfoAdicional(List<InformacionAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public List<InformacionPago> getFormasPago() {
        if (getFacturaReembolso().getInfoFactura().getPagos() != null) {
            this.formasPago = new ArrayList();
            if ((getFacturaReembolso().getInfoFactura().getPagos().getPago() != null)
                    && (!this.facturaReembolso.getInfoFactura().getPagos().getPago().isEmpty())) {
                for (FacturaReembolso.InfoFactura.Pagos.Pago pago : getFacturaReembolso().getInfoFactura().getPagos().getPago()) {

                    String formaPago = "";
                    if (pago.getFormaPago().compareTo("01") == 0) {
                        formaPago = "01 - SIN UTILIZACION DEL SISTEMA FINANCIERO";
                    } else if (pago.getFormaPago().compareTo("17") == 0) {
                        formaPago = "17 - DINERO ELECTRÓNICO";
                    } else if (pago.getFormaPago().compareTo("19") == 0) {
                        formaPago = "19 - TARJETA DE CRÉDITO";
                    } else if (pago.getFormaPago().compareTo("20") == 0) {
                        formaPago = "20 - OTROS CON UTILIZACION DEL SISTEMA FINANCIERO";
                    }

                    this.formasPago.add(new InformacionPago(pago.getTotal() + "", formaPago));
                }
            }
        }
        return this.formasPago;
    }

    public void setFormasPago(List<InformacionPago> formasPago) {
        this.formasPago = formasPago;
    }

}
