/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.pedidos.report;

import java.sql.Time;

/**
 *
 * @author Developer4
 */
public class ReporteInvPedidosMotivo {

    private String pmCodigo;
    private String pmDetalle;
    private Time pmHoraInicio;
    private Time pmHoraFin;
    private boolean pmInactivo;

    public ReporteInvPedidosMotivo() {

    }

    public ReporteInvPedidosMotivo(String pmCodigo, String pmDetalle, Time pmHoraInicio, Time pmHoraFin, boolean pmInactivo) {
        this.pmCodigo = pmCodigo;
        this.pmDetalle = pmDetalle;
        this.pmHoraInicio = pmHoraInicio;
        this.pmHoraFin = pmHoraFin;
        this.pmInactivo = pmInactivo;
    }

    public String getPmCodigo() {
        return pmCodigo;
    }

    public void setPmCodigo(String pmCodigo) {
        this.pmCodigo = pmCodigo;
    }

    public String getPmDetalle() {
        return pmDetalle;
    }

    public void setPmDetalle(String pmDetalle) {
        this.pmDetalle = pmDetalle;
    }

    public Time getPmHoraInicio() {
        return pmHoraInicio;
    }

    public void setPmHoraInicio(Time pmHoraInicio) {
        this.pmHoraInicio = pmHoraInicio;
    }

    public Time getPmHoraFin() {
        return pmHoraFin;
    }

    public void setPmHoraFin(Time pmHoraFin) {
        this.pmHoraFin = pmHoraFin;
    }

    public boolean isPmInactivo() {
        return pmInactivo;
    }

    public void setPmInactivo(boolean pmInactivo) {
        this.pmInactivo = pmInactivo;
    }

}
