/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.report;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaExportacionTO;

/**
 *
 * @author CarolValdiviezo
 */
public class ReporteAnxListaVentaExportacionTO extends AnxListaVentaExportacionTO {

    String fechaDesde;
    String fechaHasta;

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

}
