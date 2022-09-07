/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.liquidacioncompra.LiquidacionCompra;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public class LiquidacionCompraReporte {

    private LiquidacionCompra liquidacionCompra;
    private String detalle1;
    private String detalle2;
    private String detalle3;
    private List<DetallesAdicionalesReporte> detallesAdiciones;
    private List<InformacionAdicional> infoAdicional;
    private List<InformacionPago> formasPago;

    public LiquidacionCompraReporte(LiquidacionCompra liquidacionCompra) {
        this.liquidacionCompra = liquidacionCompra;
    }

    public LiquidacionCompra getLiquidacionCompra() {
        return liquidacionCompra;
    }

    public void setLiquidacionCompra(LiquidacionCompra liquidacionCompra) {
        this.liquidacionCompra = liquidacionCompra;
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

    public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
        this.detallesAdiciones = detallesAdiciones;
    }

    public void setInfoAdicional(List<InformacionAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public void setFormasPago(List<InformacionPago> formasPago) {
        this.formasPago = formasPago;
    }

    public List<DetallesAdicionalesReporte> getDetallesAdiciones() {
        this.detallesAdiciones = new ArrayList<>();

        for (LiquidacionCompra.Detalles.Detalle det : getLiquidacionCompra().getDetalles().getDetalle()) {
            DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
            detAd.setCodigoPrincipal(det.getCodigoPrincipal());
            detAd.setCodigoAuxiliar(det.getCodigoAuxiliar());
            detAd.setDescripcion(det.getDescripcion());
            detAd.setCantidad(det.getCantidad().toPlainString());
            detAd.setPrecioTotalSinImpuesto(det.getPrecioTotalSinImpuesto().toString());
            detAd.setPrecioUnitario(det.getPrecioUnitario().toString());
            if (det.getDescuento() != null) {
                detAd.setDescuento(det.getDescuento().toString());
            }
            int i = 0;
            if ((det.getDetallesAdicionales() != null) && (det.getDetallesAdicionales().getDetAdicional() != null)
                    && (!det.getDetallesAdicionales().getDetAdicional().isEmpty())) {
                for (LiquidacionCompra.Detalles.Detalle.DetallesAdicionales.DetAdicional detAdicional : det
                        .getDetallesAdicionales().getDetAdicional()) {
                    if (i == 0) {
                        detAd.setDetalle1(detAdicional.getValor());
                    }
                    if (i == 1) {
                        detAd.setDetalle2(detAdicional.getValor());
                    }
                    if (i == 2) {
                        detAd.setDetalle3(detAdicional.getValor());
                    }
                    i++;
                }
            }
            detAd.setInfoAdicional(getInfoAdicional());
            detAd.setFormasPago(getFormasPago());
            this.detallesAdiciones.add(detAd);
        }

        return this.detallesAdiciones;
    }

    public List<InformacionAdicional> getInfoAdicional() {
        if (getLiquidacionCompra().getInfoAdicional() != null) {
            this.infoAdicional = new ArrayList();
            if ((getLiquidacionCompra().getInfoAdicional().getCampoAdicional() != null)
                    && (!this.liquidacionCompra.getInfoAdicional().getCampoAdicional().isEmpty())) {
                for (LiquidacionCompra.InfoAdicional.CampoAdicional ca : getLiquidacionCompra().getInfoAdicional().getCampoAdicional()) {
                    this.infoAdicional.add(new InformacionAdicional(ca.getValue(), ca.getNombre()));
                }
            }
        }
        return this.infoAdicional;
    }

    public List<InformacionPago> getFormasPago() {
        if (getLiquidacionCompra().getInfoLiquidacionCompra().getPagos() != null) {
            this.formasPago = new ArrayList();
            if ((getLiquidacionCompra().getInfoLiquidacionCompra().getPagos().getPago() != null)
                    && (!this.liquidacionCompra.getInfoLiquidacionCompra().getPagos().getPago().isEmpty())) {
                for (LiquidacionCompra.InfoLiquidacionCompra.Pagos.Pago pago : getLiquidacionCompra().getInfoLiquidacionCompra().getPagos().getPago()) {
                    String formaPago = "20 - OTROS CON UTILIZACION DEL SISTEMA FINANCIERO";
                    this.formasPago.add(new InformacionPago(pago.getTotal() + "", formaPago));
                }
            }
        }
        return this.formasPago;
    }
}
