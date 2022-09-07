package ec.com.todocompu.ShrimpSoftServer.rrhh.report;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CarolValdiviezo
 */
public class ReporteSolicitudVacaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    private String vacCedulaEmpleado;
    private String vacFechaSolicitud;
    private String vacObservaciones;
    private String vacNombresEmpleado;
    private String vacPeriodoDesde;
    private String vacPeriodoHasta;
    private String vacNumero;
    private String vacCargo;

    public ReporteSolicitudVacaciones() {
    }

    public String getVacCedulaEmpleado() {
        return vacCedulaEmpleado;
    }

    public void setVacCedulaEmpleado(String vacCedulaEmpleado) {
        this.vacCedulaEmpleado = vacCedulaEmpleado;
    }

    public String getVacFechaSolicitud() {
        return vacFechaSolicitud;
    }

    public void setVacFechaSolicitud(String vacFechaSolicitud) {
        this.vacFechaSolicitud = vacFechaSolicitud;
    }

    public String getVacObservaciones() {
        return vacObservaciones;
    }

    public void setVacObservaciones(String vacObservaciones) {
        this.vacObservaciones = vacObservaciones;
    }

    public String getVacNombresEmpleado() {
        return vacNombresEmpleado;
    }

    public void setVacNombresEmpleado(String vacNombresEmpleado) {
        this.vacNombresEmpleado = vacNombresEmpleado;
    }

    public String getVacPeriodoDesde() {
        return vacPeriodoDesde;
    }

    public void setVacPeriodoDesde(String vacPeriodoDesde) {
        this.vacPeriodoDesde = vacPeriodoDesde;
    }

    public String getVacPeriodoHasta() {
        return vacPeriodoHasta;
    }

    public void setVacPeriodoHasta(String vacPeriodoHasta) {
        this.vacPeriodoHasta = vacPeriodoHasta;
    }

    public String getVacNumero() {
        return vacNumero;
    }

    public void setVacNumero(String vacNumero) {
        this.vacNumero = vacNumero;
    }

    public String getVacCargo() {
        return vacCargo;
    }

    public void setVacCargo(String vacCargo) {
        this.vacCargo = vacCargo;
    }

}
