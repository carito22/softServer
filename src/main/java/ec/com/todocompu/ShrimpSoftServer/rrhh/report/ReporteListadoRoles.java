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
public class ReporteListadoRoles {

    private String desde;
    private String hasta;
    private String categoria;
    private String empleado;

    //// Detalle
    private String lrpId;
    private String lrpNombres;
    private String lrpCargo;
    private String lrpDesde;
    private String lrpHasta;
    private BigDecimal lrpSueldo;
    private Integer lrpDl;
    private Integer lrpDf;
    private Integer lrpDe;
    private Integer lrpDd;
    private Integer lrpDp;
    private BigDecimal lrpSaldo;
    private BigDecimal lrpHE50;
    private BigDecimal lrpHE100;
    private BigDecimal lrpHEExtraordinarias100;
    private BigDecimal lrpSaldoHE50;
    private BigDecimal lrpSaldoHE100;
    private BigDecimal lrpSaldoHEExtraordinarias100;
    private BigDecimal lrpIngresos;
    private BigDecimal lrpBonos;
    private BigDecimal lrpBonosnd;
    private BigDecimal lrpBonoFijo;
    private BigDecimal lrpBonoFijoNd;
    private BigDecimal lrpViaticos;
    private BigDecimal lrpFondoReserva;
    private Boolean lrpAcumulaFondoReserva;
    private BigDecimal lrpLiquidacionXiii;
    private BigDecimal lrpLiquidacionXiv;
    private BigDecimal lrpLiquidacionVacaciones;
    private BigDecimal lrpLiquidacionDesahucio;
    private BigDecimal lrpLiquidacion;
    private BigDecimal lrpTotalIngresos;
    private BigDecimal lrpAnticipos;
    private BigDecimal lrpPrestamos;
    private BigDecimal lrpIess;
    private BigDecimal lrpRetencion;
    private BigDecimal lrpDescuentos;
    private BigDecimal lrpTotal;
    private BigDecimal lrpTotalBonos;
    private BigDecimal lrpTotalHE;
    private BigDecimal lrpTotalBonosNg;
    private String lrpFormaPago;
    private String lrpDocumento;
    private String lrpContable;
    private String lrpObservaciones;
    private String nacionalidad;
    private BigDecimal lrpXiiiSueldo;
    private BigDecimal lrpXivSueldo;
    private BigDecimal lrpPrestamoHipotecario;
    private BigDecimal lrpPrestamoQuirografario;

    public ReporteListadoRoles() {
    }

    public ReporteListadoRoles(String desde, String hasta, String categoria, String empleado, String lrpId,
            String lrpNombres, String lrpCargo, String lrpDesde, String lrpHasta, BigDecimal lrpSueldo, Integer lrpDl,
            Integer lrpDf, Integer lrpDe, Integer lrpDd, Integer lrpDp, BigDecimal lrpSaldo, BigDecimal lrpIngresos,
            BigDecimal lrpBonos, BigDecimal lrpBonosnd, BigDecimal lrpBonoFijo, BigDecimal lrpBonoFijoNd,
            BigDecimal lrpViaticos, BigDecimal lrpFondoReserva, Boolean lrpAcumulaFondoReserva,
            BigDecimal lrpLiquidacionXiii, BigDecimal lrpLiquidacionXiv, BigDecimal lrpLiquidacionVacaciones,
            BigDecimal lrpLiquidacionDesahucio, BigDecimal lrpLiquidacion, BigDecimal lrpTotalIngresos,
            BigDecimal lrpAnticipos, BigDecimal lrpPrestamos, BigDecimal lrpIess, BigDecimal lrpRetencion,
            BigDecimal lrpDescuentos, BigDecimal lrpTotal, String lrpFormaPago, String lrpDocumento, String lrpContable,
            String lrpObservaciones, String nacionalidad) {
        this.desde = desde;
        this.hasta = hasta;
        this.categoria = categoria;
        this.empleado = empleado;
        this.lrpId = lrpId;
        this.lrpNombres = lrpNombres;
        this.lrpCargo = lrpCargo;
        this.lrpDesde = lrpDesde;
        this.lrpHasta = lrpHasta;
        this.lrpSueldo = lrpSueldo;
        this.lrpDl = lrpDl;
        this.lrpDf = lrpDf;
        this.lrpDe = lrpDe;
        this.lrpDd = lrpDd;
        this.lrpDp = lrpDp;
        this.lrpSaldo = lrpSaldo;
        this.lrpIngresos = lrpIngresos;
        this.lrpBonos = lrpBonos;
        this.lrpBonosnd = lrpBonosnd;
        this.lrpBonoFijo = lrpBonoFijo;
        this.lrpBonoFijoNd = lrpBonoFijoNd;
        this.lrpViaticos = lrpViaticos;
        this.lrpFondoReserva = lrpFondoReserva;
        this.lrpAcumulaFondoReserva = lrpAcumulaFondoReserva;
        this.lrpLiquidacionXiii = lrpLiquidacionXiii;
        this.lrpLiquidacionXiv = lrpLiquidacionXiv;
        this.lrpLiquidacionVacaciones = lrpLiquidacionVacaciones;
        this.lrpLiquidacionDesahucio = lrpLiquidacionDesahucio;
        this.lrpLiquidacion = lrpLiquidacion;
        this.lrpTotalIngresos = lrpTotalIngresos;
        this.lrpAnticipos = lrpAnticipos;
        this.lrpPrestamos = lrpPrestamos;
        this.lrpIess = lrpIess;
        this.lrpRetencion = lrpRetencion;
        this.lrpDescuentos = lrpDescuentos;
        this.lrpTotal = lrpTotal;
        this.lrpFormaPago = lrpFormaPago;
        this.lrpDocumento = lrpDocumento;
        this.lrpContable = lrpContable;
        this.lrpObservaciones = lrpObservaciones;
        this.nacionalidad = nacionalidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public Boolean getLrpAcumulaFondoReserva() {
        return lrpAcumulaFondoReserva;
    }

    public void setLrpAcumulaFondoReserva(Boolean lrpAcumulaFondoReserva) {
        this.lrpAcumulaFondoReserva = lrpAcumulaFondoReserva;
    }

    public BigDecimal getLrpAnticipos() {
        return lrpAnticipos;
    }

    public void setLrpAnticipos(BigDecimal lrpAnticipos) {
        this.lrpAnticipos = lrpAnticipos;
    }

    public BigDecimal getLrpBonoFijo() {
        return lrpBonoFijo;
    }

    public void setLrpBonoFijo(BigDecimal lrpBonoFijo) {
        this.lrpBonoFijo = lrpBonoFijo;
    }

    public BigDecimal getLrpBonoFijoNd() {
        return lrpBonoFijoNd;
    }

    public void setLrpBonoFijoNd(BigDecimal lrpBonoFijoNd) {
        this.lrpBonoFijoNd = lrpBonoFijoNd;
    }

    public BigDecimal getLrpBonos() {
        return lrpBonos;
    }

    public void setLrpBonos(BigDecimal lrpBonos) {
        this.lrpBonos = lrpBonos;
    }

    public BigDecimal getLrpBonosnd() {
        return lrpBonosnd;
    }

    public void setLrpBonosnd(BigDecimal lrpBonosnd) {
        this.lrpBonosnd = lrpBonosnd;
    }

    public String getLrpCargo() {
        return lrpCargo;
    }

    public void setLrpCargo(String lrpCargo) {
        this.lrpCargo = lrpCargo;
    }

    public String getLrpContable() {
        return lrpContable;
    }

    public void setLrpContable(String lrpContable) {
        this.lrpContable = lrpContable;
    }

    public Integer getLrpDd() {
        return lrpDd;
    }

    public void setLrpDd(Integer lrpDd) {
        this.lrpDd = lrpDd;
    }

    public Integer getLrpDe() {
        return lrpDe;
    }

    public void setLrpDe(Integer lrpDe) {
        this.lrpDe = lrpDe;
    }

    public BigDecimal getLrpDescuentos() {
        return lrpDescuentos;
    }

    public void setLrpDescuentos(BigDecimal lrpDescuentos) {
        this.lrpDescuentos = lrpDescuentos;
    }

    public String getLrpDesde() {
        return lrpDesde;
    }

    public void setLrpDesde(String lrpDesde) {
        this.lrpDesde = lrpDesde;
    }

    public Integer getLrpDf() {
        return lrpDf;
    }

    public void setLrpDf(Integer lrpDf) {
        this.lrpDf = lrpDf;
    }

    public Integer getLrpDl() {
        return lrpDl;
    }

    public void setLrpDl(Integer lrpDl) {
        this.lrpDl = lrpDl;
    }

    public String getLrpDocumento() {
        return lrpDocumento;
    }

    public void setLrpDocumento(String lrpDocumento) {
        this.lrpDocumento = lrpDocumento;
    }

    public Integer getLrpDp() {
        return lrpDp;
    }

    public void setLrpDp(Integer lrpDp) {
        this.lrpDp = lrpDp;
    }

    public BigDecimal getLrpFondoReserva() {
        return lrpFondoReserva;
    }

    public void setLrpFondoReserva(BigDecimal lrpFondoReserva) {
        this.lrpFondoReserva = lrpFondoReserva;
    }

    public String getLrpFormaPago() {
        return lrpFormaPago;
    }

    public void setLrpFormaPago(String lrpFormaPago) {
        this.lrpFormaPago = lrpFormaPago;
    }

    public String getLrpHasta() {
        return lrpHasta;
    }

    public void setLrpHasta(String lrpHasta) {
        this.lrpHasta = lrpHasta;
    }

    public String getLrpId() {
        return lrpId;
    }

    public void setLrpId(String lrpId) {
        this.lrpId = lrpId;
    }

    public BigDecimal getLrpIess() {
        return lrpIess;
    }

    public void setLrpIess(BigDecimal lrpIess) {
        this.lrpIess = lrpIess;
    }

    public BigDecimal getLrpIngresos() {
        return lrpIngresos;
    }

    public void setLrpIngresos(BigDecimal lrpIngresos) {
        this.lrpIngresos = lrpIngresos;
    }

    public BigDecimal getLrpLiquidacion() {
        return lrpLiquidacion;
    }

    public void setLrpLiquidacion(BigDecimal lrpLiquidacion) {
        this.lrpLiquidacion = lrpLiquidacion;
    }

    public BigDecimal getLrpLiquidacionDesahucio() {
        return lrpLiquidacionDesahucio;
    }

    public void setLrpLiquidacionDesahucio(BigDecimal lrpLiquidacionDesahucio) {
        this.lrpLiquidacionDesahucio = lrpLiquidacionDesahucio;
    }

    public BigDecimal getLrpLiquidacionVacaciones() {
        return lrpLiquidacionVacaciones;
    }

    public void setLrpLiquidacionVacaciones(BigDecimal lrpLiquidacionVacaciones) {
        this.lrpLiquidacionVacaciones = lrpLiquidacionVacaciones;
    }

    public BigDecimal getLrpLiquidacionXiii() {
        return lrpLiquidacionXiii;
    }

    public void setLrpLiquidacionXiii(BigDecimal lrpLiquidacionXiii) {
        this.lrpLiquidacionXiii = lrpLiquidacionXiii;
    }

    public BigDecimal getLrpLiquidacionXiv() {
        return lrpLiquidacionXiv;
    }

    public void setLrpLiquidacionXiv(BigDecimal lrpLiquidacionXiv) {
        this.lrpLiquidacionXiv = lrpLiquidacionXiv;
    }

    public String getLrpNombres() {
        return lrpNombres;
    }

    public void setLrpNombres(String lrpNombres) {
        this.lrpNombres = lrpNombres;
    }

    public String getLrpObservaciones() {
        return lrpObservaciones;
    }

    public void setLrpObservaciones(String lrpObservaciones) {
        this.lrpObservaciones = lrpObservaciones;
    }

    public BigDecimal getLrpPrestamos() {
        return lrpPrestamos;
    }

    public void setLrpPrestamos(BigDecimal lrpPrestamos) {
        this.lrpPrestamos = lrpPrestamos;
    }

    public BigDecimal getLrpRetencion() {
        return lrpRetencion;
    }

    public void setLrpRetencion(BigDecimal lrpRetencion) {
        this.lrpRetencion = lrpRetencion;
    }

    public BigDecimal getLrpSaldo() {
        return lrpSaldo;
    }

    public void setLrpSaldo(BigDecimal lrpSaldo) {
        this.lrpSaldo = lrpSaldo;
    }

    public BigDecimal getLrpSueldo() {
        return lrpSueldo;
    }

    public void setLrpSueldo(BigDecimal lrpSueldo) {
        this.lrpSueldo = lrpSueldo;
    }

    public BigDecimal getLrpTotal() {
        return lrpTotal;
    }

    public void setLrpTotal(BigDecimal lrpTotal) {
        this.lrpTotal = lrpTotal;
    }

    public BigDecimal getLrpTotalIngresos() {
        return lrpTotalIngresos;
    }

    public void setLrpTotalIngresos(BigDecimal lrpTotalIngresos) {
        this.lrpTotalIngresos = lrpTotalIngresos;
    }

    public BigDecimal getLrpViaticos() {
        return lrpViaticos;
    }

    public void setLrpViaticos(BigDecimal lrpViaticos) {
        this.lrpViaticos = lrpViaticos;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public BigDecimal getLrpHE50() {
        return lrpHE50;
    }

    public void setLrpHE50(BigDecimal lrpHE50) {
        this.lrpHE50 = lrpHE50;
    }

    public BigDecimal getLrpHE100() {
        return lrpHE100;
    }

    public void setLrpHE100(BigDecimal lrpHE100) {
        this.lrpHE100 = lrpHE100;
    }

    public BigDecimal getLrpSaldoHE50() {
        return lrpSaldoHE50;
    }

    public void setLrpSaldoHE50(BigDecimal lrpSaldoHE50) {
        this.lrpSaldoHE50 = lrpSaldoHE50;
    }

    public BigDecimal getLrpSaldoHE100() {
        return lrpSaldoHE100;
    }

    public void setLrpSaldoHE100(BigDecimal lrpSaldoHE100) {
        this.lrpSaldoHE100 = lrpSaldoHE100;
    }

    public BigDecimal getLrpHEExtraordinarias100() {
        return lrpHEExtraordinarias100;
    }

    public void setLrpHEExtraordinarias100(BigDecimal lrpHEExtraordinarias100) {
        this.lrpHEExtraordinarias100 = lrpHEExtraordinarias100;
    }

    public BigDecimal getLrpSaldoHEExtraordinarias100() {
        return lrpSaldoHEExtraordinarias100;
    }

    public void setLrpSaldoHEExtraordinarias100(BigDecimal lrpSaldoHEExtraordinarias100) {
        this.lrpSaldoHEExtraordinarias100 = lrpSaldoHEExtraordinarias100;
    }

    public BigDecimal getLrpTotalBonos() {
        return lrpTotalBonos;
    }

    public void setLrpTotalBonos(BigDecimal lrpTotalBonos) {
        this.lrpTotalBonos = lrpTotalBonos;
    }

    public BigDecimal getLrpTotalHE() {
        return lrpTotalHE;
    }

    public void setLrpTotalHE(BigDecimal lrpTotalHE) {
        this.lrpTotalHE = lrpTotalHE;
    }

    public BigDecimal getLrpTotalBonosNg() {
        return lrpTotalBonosNg;
    }

    public void setLrpTotalBonosNg(BigDecimal lrpTotalBonosNg) {
        this.lrpTotalBonosNg = lrpTotalBonosNg;
    }

    public BigDecimal getLrpXiiiSueldo() {
        return lrpXiiiSueldo;
    }

    public void setLrpXiiiSueldo(BigDecimal lrpXiiiSueldo) {
        this.lrpXiiiSueldo = lrpXiiiSueldo;
    }

    public BigDecimal getLrpXivSueldo() {
        return lrpXivSueldo;
    }

    public void setLrpXivSueldo(BigDecimal lrpXivSueldo) {
        this.lrpXivSueldo = lrpXivSueldo;
    }

    public BigDecimal getLrpPrestamoHipotecario() {
        return lrpPrestamoHipotecario;
    }

    public void setLrpPrestamoHipotecario(BigDecimal lrpPrestamoHipotecario) {
        this.lrpPrestamoHipotecario = lrpPrestamoHipotecario;
    }

    public BigDecimal getLrpPrestamoQuirografario() {
        return lrpPrestamoQuirografario;
    }

    public void setLrpPrestamoQuirografario(BigDecimal lrpPrestamoQuirografario) {
        this.lrpPrestamoQuirografario = lrpPrestamoQuirografario;
    }
  
}
