/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.report;

import java.math.BigDecimal;

/**
 *
 * @author Andres Guachisaca
 */
/// rrhh.reporte.ReporteConsolidadoRol
public class ReporteConsolidadoRol {

    private String desde;
    private String hasta;
    private String categoria;
    private String empleado;
    private String crpCategoria;
    private String crpId;
    private String crpNombres;
    private BigDecimal crpDl;
    private BigDecimal crpDf;
    private BigDecimal crpDe;
    private BigDecimal crpDd;// -----
    private BigDecimal crpDp;
    private BigDecimal crpSueldo;
    private BigDecimal crpBonos;
    private BigDecimal crpBonosnd;
    private BigDecimal crpBonoFijo;
    private BigDecimal crpBonoFijoNd;
    private BigDecimal crpOtrosIngresos;
    private BigDecimal crpOtrosIngresosNd;
    private BigDecimal crpSubtotalBonos;
    private BigDecimal crpSubtotalIngresos;
    private BigDecimal crpViaticos;
    private BigDecimal crpFondoReserva;
    private BigDecimal crpLiquidacion;
    private BigDecimal crpIngresos;
    private BigDecimal crpAnticipos;
    private BigDecimal crpPrestamos;
    private BigDecimal crpIess;
    private BigDecimal crpRetencion;
    private BigDecimal crpDescuentosFondoReserva;
    private BigDecimal crpDescuentos;
    private BigDecimal crpTotal;
    private BigDecimal crpHE50;
    private BigDecimal crpHE100;
    private BigDecimal crpHEExtraordinarias100;
    private BigDecimal crpSaldoHE50;
    private BigDecimal crpSaldoHE100;
    private BigDecimal crpSaldoHEExtraordinarias100;
    private BigDecimal crpPermisoMedico;
    private BigDecimal crpPrestamoQuirografario;
    private BigDecimal crpIessExtension;
    private boolean excluirLiquidacion;

    public ReporteConsolidadoRol() {
    }

    public ReporteConsolidadoRol(String desde, String hasta, String categoria, String empleado, String crpCategoria,
            String crpId, String crpNombres, BigDecimal crpDl, BigDecimal crpDf, BigDecimal crpDe, BigDecimal crpDd,
            BigDecimal crpDp, BigDecimal crpSueldo, BigDecimal crpBonos, BigDecimal crpBonosnd, BigDecimal crpBonoFijo,
            BigDecimal crpBonoFijoNd, BigDecimal crpOtrosIngresos, BigDecimal crpOtrosIngresosNd,
            BigDecimal crpSubtotalBonos, BigDecimal crpSubtotalIngresos, BigDecimal crpViaticos,
            BigDecimal crpFondoReserva, BigDecimal crpLiquidacion, BigDecimal crpIngresos, BigDecimal crpAnticipos,
            BigDecimal crpPrestamos, BigDecimal crpIess, BigDecimal crpRetencion, BigDecimal crpDescuentosFondoReserva,
            BigDecimal crpDescuentos, BigDecimal crpTotal, BigDecimal crpPermisoMedico, BigDecimal crpPrestamoQuirografario, BigDecimal crpIessExtension) {
        this.desde = desde;
        this.hasta = hasta;
        this.categoria = categoria;
        this.empleado = empleado;
        this.crpCategoria = crpCategoria;
        this.crpId = crpId;
        this.crpNombres = crpNombres;
        this.crpDl = crpDl;
        this.crpDf = crpDf;
        this.crpDe = crpDe;
        this.crpDd = crpDd;
        this.crpDp = crpDp;
        this.crpSueldo = crpSueldo;
        this.crpBonos = crpBonos;
        this.crpBonosnd = crpBonosnd;
        this.crpBonoFijo = crpBonoFijo;
        this.crpBonoFijoNd = crpBonoFijoNd;
        this.crpOtrosIngresos = crpOtrosIngresos;
        this.crpOtrosIngresosNd = crpOtrosIngresosNd;
        this.crpSubtotalBonos = crpSubtotalBonos;
        this.crpSubtotalIngresos = crpSubtotalIngresos;
        this.crpViaticos = crpViaticos;
        this.crpFondoReserva = crpFondoReserva;
        this.crpLiquidacion = crpLiquidacion;
        this.crpIngresos = crpIngresos;
        this.crpAnticipos = crpAnticipos;
        this.crpPrestamos = crpPrestamos;
        this.crpIess = crpIess;
        this.crpRetencion = crpRetencion;
        this.crpDescuentosFondoReserva = crpDescuentosFondoReserva;
        this.crpDescuentos = crpDescuentos;
        this.crpPermisoMedico = crpPermisoMedico;
        this.crpTotal = crpTotal;
        this.crpPrestamoQuirografario = crpPrestamoQuirografario;
        this.crpIessExtension = crpIessExtension;
    }

    public BigDecimal getCrpOtrosIngresos() {
        return crpOtrosIngresos;
    }

    public void setCrpOtrosIngresos(BigDecimal crpOtrosIngresos) {
        this.crpOtrosIngresos = crpOtrosIngresos;
    }

    public BigDecimal getCrpOtrosIngresosNd() {
        return crpOtrosIngresosNd;
    }

    public void setCrpOtrosIngresosNd(BigDecimal crpOtrosIngresosNd) {
        this.crpOtrosIngresosNd = crpOtrosIngresosNd;
    }

    public BigDecimal getCrpSubtotalBonos() {
        return crpSubtotalBonos;
    }

    public void setCrpSubtotalBonos(BigDecimal crpSubtotalBonos) {
        this.crpSubtotalBonos = crpSubtotalBonos;
    }

    public BigDecimal getCrpSubtotalIngresos() {
        return crpSubtotalIngresos;
    }

    public void setCrpSubtotalIngresos(BigDecimal crpSubtotalIngresos) {
        this.crpSubtotalIngresos = crpSubtotalIngresos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getCrpAnticipos() {
        return crpAnticipos;
    }

    public void setCrpAnticipos(BigDecimal crpAnticipos) {
        this.crpAnticipos = crpAnticipos;
    }

    public BigDecimal getCrpBonoFijo() {
        return crpBonoFijo;
    }

    public void setCrpBonoFijo(BigDecimal crpBonoFijo) {
        this.crpBonoFijo = crpBonoFijo;
    }

    public BigDecimal getCrpBonoFijoNd() {
        return crpBonoFijoNd;
    }

    public void setCrpBonoFijoNd(BigDecimal crpBonoFijoNd) {
        this.crpBonoFijoNd = crpBonoFijoNd;
    }

    public BigDecimal getCrpBonos() {
        return crpBonos;
    }

    public void setCrpBonos(BigDecimal crpBonos) {
        this.crpBonos = crpBonos;
    }

    public BigDecimal getCrpBonosnd() {
        return crpBonosnd;
    }

    public void setCrpBonosnd(BigDecimal crpBonosnd) {
        this.crpBonosnd = crpBonosnd;
    }

    public String getCrpCategoria() {
        return crpCategoria;
    }

    public void setCrpCategoria(String crpCategoria) {
        this.crpCategoria = crpCategoria;
    }

    public BigDecimal getCrpDd() {
        return crpDd;
    }

    public void setCrpDd(BigDecimal crpDd) {
        this.crpDd = crpDd;
    }

    public BigDecimal getCrpDe() {
        return crpDe;
    }

    public void setCrpDe(BigDecimal crpDe) {
        this.crpDe = crpDe;
    }

    public BigDecimal getCrpDescuentos() {
        return crpDescuentos;
    }

    public void setCrpDescuentos(BigDecimal crpDescuentos) {
        this.crpDescuentos = crpDescuentos;
    }

    public BigDecimal getCrpDescuentosFondoReserva() {
        return crpDescuentosFondoReserva;
    }

    public void setCrpDescuentosFondoReserva(BigDecimal crpDescuentosFondoReserva) {
        this.crpDescuentosFondoReserva = crpDescuentosFondoReserva;
    }

    public BigDecimal getCrpDf() {
        return crpDf;
    }

    public void setCrpDf(BigDecimal crpDf) {
        this.crpDf = crpDf;
    }

    public BigDecimal getCrpDl() {
        return crpDl;
    }

    public void setCrpDl(BigDecimal crpDl) {
        this.crpDl = crpDl;
    }

    public BigDecimal getCrpDp() {
        return crpDp;
    }

    public void setCrpDp(BigDecimal crpDp) {
        this.crpDp = crpDp;
    }

    public BigDecimal getCrpFondoReserva() {
        return crpFondoReserva;
    }

    public void setCrpFondoReserva(BigDecimal crpFondoReserva) {
        this.crpFondoReserva = crpFondoReserva;
    }

    public String getCrpId() {
        return crpId;
    }

    public void setCrpId(String crpId) {
        this.crpId = crpId;
    }

    public BigDecimal getCrpIess() {
        return crpIess;
    }

    public void setCrpIess(BigDecimal crpIess) {
        this.crpIess = crpIess;
    }

    public BigDecimal getCrpIngresos() {
        return crpIngresos;
    }

    public void setCrpIngresos(BigDecimal crpIngresos) {
        this.crpIngresos = crpIngresos;
    }

    public BigDecimal getCrpLiquidacion() {
        return crpLiquidacion;
    }

    public void setCrpLiquidacion(BigDecimal crpLiquidacion) {
        this.crpLiquidacion = crpLiquidacion;
    }

    public String getCrpNombres() {
        return crpNombres;
    }

    public void setCrpNombres(String crpNombres) {
        this.crpNombres = crpNombres;
    }

    public BigDecimal getCrpPrestamos() {
        return crpPrestamos;
    }

    public void setCrpPrestamos(BigDecimal crpPrestamos) {
        this.crpPrestamos = crpPrestamos;
    }

    public BigDecimal getCrpRetencion() {
        return crpRetencion;
    }

    public void setCrpRetencion(BigDecimal crpRetencion) {
        this.crpRetencion = crpRetencion;
    }

    public BigDecimal getCrpSueldo() {
        return crpSueldo;
    }

    public void setCrpSueldo(BigDecimal crpSueldo) {
        this.crpSueldo = crpSueldo;
    }

    public BigDecimal getCrpTotal() {
        return crpTotal;
    }

    public void setCrpTotal(BigDecimal crpTotal) {
        this.crpTotal = crpTotal;
    }

    public BigDecimal getCrpViaticos() {
        return crpViaticos;
    }

    public void setCrpViaticos(BigDecimal crpViaticos) {
        this.crpViaticos = crpViaticos;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public BigDecimal getCrpHE50() {
        return crpHE50;
    }

    public void setCrpHE50(BigDecimal crpHE50) {
        this.crpHE50 = crpHE50;
    }

    public BigDecimal getCrpHE100() {
        return crpHE100;
    }

    public void setCrpHE100(BigDecimal crpHE100) {
        this.crpHE100 = crpHE100;
    }

    public BigDecimal getCrpHEExtraordinarias100() {
        return crpHEExtraordinarias100;
    }

    public void setCrpHEExtraordinarias100(BigDecimal crpHEExtraordinarias100) {
        this.crpHEExtraordinarias100 = crpHEExtraordinarias100;
    }

    public BigDecimal getCrpSaldoHE50() {
        return crpSaldoHE50;
    }

    public void setCrpSaldoHE50(BigDecimal crpSaldoHE50) {
        this.crpSaldoHE50 = crpSaldoHE50;
    }

    public BigDecimal getCrpSaldoHE100() {
        return crpSaldoHE100;
    }

    public void setCrpSaldoHE100(BigDecimal crpSaldoHE100) {
        this.crpSaldoHE100 = crpSaldoHE100;
    }

    public BigDecimal getCrpSaldoHEExtraordinarias100() {
        return crpSaldoHEExtraordinarias100;
    }

    public void setCrpSaldoHEExtraordinarias100(BigDecimal crpSaldoHEExtraordinarias100) {
        this.crpSaldoHEExtraordinarias100 = crpSaldoHEExtraordinarias100;
    }

    public BigDecimal getCrpPermisoMedico() {
        return crpPermisoMedico;
    }

    public void setCrpPermisoMedico(BigDecimal crpPermisoMedico) {
        this.crpPermisoMedico = crpPermisoMedico;
    }

    public BigDecimal getCrpPrestamoQuirografario() {
        return crpPrestamoQuirografario;
    }

    public void setCrpPrestamoQuirografario(BigDecimal crpPrestamoQuirografario) {
        this.crpPrestamoQuirografario = crpPrestamoQuirografario;
    }

    public BigDecimal getCrpIessExtension() {
        return crpIessExtension;
    }

    public void setCrpIessExtension(BigDecimal crpIessExtension) {
        this.crpIessExtension = crpIessExtension;
    }

    public boolean isExcluirLiquidacion() {
        return excluirLiquidacion;
    }

    public void setExcluirLiquidacion(boolean excluirLiquidacion) {
        this.excluirLiquidacion = excluirLiquidacion;
    }
}
