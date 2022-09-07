/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.reportes;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.guiaremision.GuiaRemision;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public class GuiaRemisionReporte {

    private GuiaRemision guiaRemision;
    private String detalle1;
    private String detalle2;
    private String detalle3;
    private List<DetallesAdicionalesReporte> detallesAdiciones;
    private List<InformacionAdicional> infoAdicional;

    public GuiaRemisionReporte() {
    }

    public GuiaRemisionReporte(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public GuiaRemisionReporte(GuiaRemision guiaRemision, String detalle1, String detalle2, String detalle3, List<DetallesAdicionalesReporte> detallesAdiciones, List<InformacionAdicional> infoAdicional) {
        this.guiaRemision = guiaRemision;
        this.detalle1 = detalle1;
        this.detalle2 = detalle2;
        this.detalle3 = detalle3;
        this.detallesAdiciones = detallesAdiciones;
        this.infoAdicional = infoAdicional;
    }

    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
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

    public List<DetallesAdicionalesReporte> getDetallesAdiciones() {
        this.detallesAdiciones = new ArrayList<>();
        for (GuiaRemision.Destinatarios.Destinatario destinatario : getGuiaRemision().getDestinatarios().getDestinario()) {
            for (GuiaRemision.Destinatarios.Destinatario.Detalles.Detalle det : destinatario.getDetalles().getDetalle()) {
                DetallesAdicionalesReporte detAd = new DetallesAdicionalesReporte();
                detAd.setCodigoPrincipal(det.getCodigoInterno());
                detAd.setCodigoAuxiliar(det.getCodigoAdicional());
                detAd.setDescripcion(det.getDescripcion());
                detAd.setCantidad(det.getCantidad().toPlainString());

                int i = 0;
                if ((det.getDetallesAdicionales() != null) && (det.getDetallesAdicionales().getDetAdicional() != null) && (!det.getDetallesAdicionales().getDetAdicional().isEmpty())) {
                    for (GuiaRemision.Destinatarios.Destinatario.Detalles.Detalle.DetallesAdicionales.DetAdicional detAdicional : det.getDetallesAdicionales().getDetAdicional()) {
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
                this.detallesAdiciones.add(detAd);
            }
        }

        return this.detallesAdiciones;
    }

    public void setDetallesAdiciones(List<DetallesAdicionalesReporte> detallesAdiciones) {
        this.detallesAdiciones = detallesAdiciones;
    }

    public List<InformacionAdicional> getInfoAdicional() {
        if (getGuiaRemision().getInfoAdicional() != null) {
            this.infoAdicional = new ArrayList();
            if ((getGuiaRemision().getInfoAdicional().getCampoAdicional() != null) && (!this.guiaRemision.getInfoAdicional().getCampoAdicional().isEmpty())) {
                for (GuiaRemision.InfoAdicional.CampoAdicional ca : getGuiaRemision().getInfoAdicional().getCampoAdicional()) {
                    this.infoAdicional.add(new InformacionAdicional(ca.getValue(), ca.getNombre()));
                }
            }
        }
        return this.infoAdicional;
    }

    public void setInfoAdicional(List<InformacionAdicional> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

}
