/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.banco.report;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author paca94
 */
public class ReporteConciliacion {

    private String concHasta;
    private String concCodigoConciliacion;
    private String concBanco;
    private String concCuentaContable;
    private String concCuentaBancaria;
    private String concTitular;
    private BigDecimal concSaldoEstadoCuenta;
    private BigDecimal concChequesEnTransito;
    private BigDecimal concDepositosEnTransito;
    private BigDecimal concSaldoSistema;
    private BigDecimal concDiferenciaNoConciliada;
    private String cbContable;
    private String cbFecha;
    private String cbDocumento;
    private java.math.BigDecimal cbValor;
    private String cbObservaciones;
    private String cbMarcaConciliado;
    private String cbDC;
    private String usrFechaInserta;
    private String usrInserta;

    public ReporteConciliacion() {
    }

    public ReporteConciliacion(String concHasta, String concCodigoConciliacion, String concBanco,
            String concCuentaContable, String concCuentaBancaria, String concTitular, BigDecimal concSaldoEstadoCuenta,
            BigDecimal concChequesEnTransito, BigDecimal concDepositosEnTransito, BigDecimal concSaldoSistema,
            BigDecimal concDiferenciaNoConciliada, String cbContable, String cbFecha, String cbDocumento,
            BigDecimal cbValor, String cbObservaciones, String cbMarcaConciliado, String cbDC, String usrFechaInserta) {
        this.concHasta = concHasta;
        this.concCodigoConciliacion = concCodigoConciliacion;
        this.concBanco = concBanco;
        this.concCuentaContable = concCuentaContable;
        this.concCuentaBancaria = concCuentaBancaria;
        this.concTitular = concTitular;
        this.concSaldoEstadoCuenta = concSaldoEstadoCuenta;
        this.concChequesEnTransito = concChequesEnTransito;
        this.concDepositosEnTransito = concDepositosEnTransito;
        this.concSaldoSistema = concSaldoSistema;
        this.concDiferenciaNoConciliada = concDiferenciaNoConciliada;
        this.cbContable = cbContable;
        this.cbFecha = cbFecha;
        this.cbDocumento = cbDocumento;
        this.cbValor = cbValor;
        this.cbObservaciones = cbObservaciones;
        this.cbMarcaConciliado = cbMarcaConciliado;
        this.cbDC = cbDC;
        this.usrFechaInserta = usrFechaInserta;
    }

    public String getCbContable() {
        return cbContable;
    }

    public void setCbContable(String cbContable) {
        this.cbContable = cbContable;
    }

    public String getCbDC() {
        return cbDC;
    }

    public void setCbDC(String cbDC) {
        this.cbDC = cbDC;
    }

    public String getCbDocumento() {
        return cbDocumento;
    }

    public void setCbDocumento(String cbDocumento) {
        this.cbDocumento = cbDocumento;
    }

    public String getCbFecha() {
        return cbFecha;
    }

    public void setCbFecha(String cbFecha) {
        this.cbFecha = cbFecha;
    }

    public String getCbMarcaConciliado() {
        return cbMarcaConciliado;
    }

    public void setCbMarcaConciliado(String cbMarcaConciliado) {
        this.cbMarcaConciliado = cbMarcaConciliado;
    }

    public String getCbObservaciones() {
        return cbObservaciones;
    }

    public void setCbObservaciones(String cbObservaciones) {
        this.cbObservaciones = cbObservaciones;
    }

    public BigDecimal getCbValor() {
        return cbValor;
    }

    public void setCbValor(BigDecimal cbValor) {
        this.cbValor = cbValor;
    }

    public String getConcBanco() {
        return concBanco;
    }

    public void setConcBanco(String concBanco) {
        this.concBanco = concBanco;
    }

    public BigDecimal getConcChequesEnTransito() {
        return concChequesEnTransito;
    }

    public void setConcChequesEnTransito(BigDecimal concChequesEnTransito) {
        this.concChequesEnTransito = concChequesEnTransito;
    }

    public String getConcCodigoConciliacion() {
        return concCodigoConciliacion;
    }

    public void setConcCodigoConciliacion(String concCodigoConciliacion) {
        this.concCodigoConciliacion = concCodigoConciliacion;
    }

    public String getConcCuentaBancaria() {
        return concCuentaBancaria;
    }

    public void setConcCuentaBancaria(String concCuentaBancaria) {
        this.concCuentaBancaria = concCuentaBancaria;
    }

    public String getConcCuentaContable() {
        return concCuentaContable;
    }

    public void setConcCuentaContable(String concCuentaContable) {
        this.concCuentaContable = concCuentaContable;
    }

    public BigDecimal getConcDepositosEnTransito() {
        return concDepositosEnTransito;
    }

    public void setConcDepositosEnTransito(BigDecimal concDepositosEnTransito) {
        this.concDepositosEnTransito = concDepositosEnTransito;
    }

    public BigDecimal getConcDiferenciaNoConciliada() {
        return concDiferenciaNoConciliada;
    }

    public void setConcDiferenciaNoConciliada(BigDecimal concDiferenciaNoConciliada) {
        this.concDiferenciaNoConciliada = concDiferenciaNoConciliada;
    }

    public String getConcHasta() {
        return concHasta;
    }

    public void setConcHasta(String concHasta) {
        this.concHasta = concHasta;
    }

    public BigDecimal getConcSaldoEstadoCuenta() {
        return concSaldoEstadoCuenta;
    }

    public void setConcSaldoEstadoCuenta(BigDecimal concSaldoEstadoCuenta) {
        this.concSaldoEstadoCuenta = concSaldoEstadoCuenta;
    }

    public BigDecimal getConcSaldoSistema() {
        return concSaldoSistema;
    }

    public void setConcSaldoSistema(BigDecimal concSaldoSistema) {
        this.concSaldoSistema = concSaldoSistema;
    }

    public String getConcTitular() {
        return concTitular;
    }

    public void setConcTitular(String concTitular) {
        this.concTitular = concTitular;
    }

    public String getUsrFechaInserta() {
        return usrFechaInserta;
    }

    public void setUsrFechaInserta(String usrFechaInserta) {
        this.usrFechaInserta = usrFechaInserta;
    }

    public String getUsrInserta() {
        return usrInserta;
    }

    public void setUsrInserta(String usrInserta) {
        this.usrInserta = usrInserta;
    }

}
