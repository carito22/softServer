/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaChequesPostfechadosProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorCobrarClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorPagarProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaValorizacionActivoBiologicoTO;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author mario
 */
public class ReporteProyectoFlujo {

    List<ConFlujoCajaTO> listaFlujoCaja;
    List<ConFlujoCajaCuentasPorCobrarClientesTO> listaCuentasPorCobrarClientes;
    List<ConFlujoCajaCuentasPorPagarProveedoresTO> listaCuentasPorPagarProveedores;
    List<ConFlujoCajaChequesPostfechadosProveedoresTO> listaChequesPostfechados;
    List<ConFlujoCajaValorizacionActivoBiologicoTO> listaValorizacion;
    BigDecimal sumaBanco = BigDecimal.ZERO;
    BigDecimal sumaClientes = BigDecimal.ZERO;
    BigDecimal sumaProveedores = BigDecimal.ZERO;
    BigDecimal sumaCheque = BigDecimal.ZERO;
    BigDecimal sumaValorizacion = BigDecimal.ZERO;
    BigDecimal sumaEfectivo = BigDecimal.ZERO;//banco + clientes
    BigDecimal sumaCuentasPorPagar = BigDecimal.ZERO;//proveedores + cheques
    BigDecimal subtotal = BigDecimal.ZERO;//efectivo + cuentas por pagar
    BigDecimal total = BigDecimal.ZERO;//subtotal + valorizacion
    String fechaHasta;

    public ReporteProyectoFlujo() {
    }

    public List<ConFlujoCajaTO> getListaFlujoCaja() {
        return listaFlujoCaja;
    }

    public void setListaFlujoCaja(List<ConFlujoCajaTO> listaFlujoCaja) {
        this.listaFlujoCaja = listaFlujoCaja;
    }

    public List<ConFlujoCajaCuentasPorCobrarClientesTO> getListaCuentasPorCobrarClientes() {
        return listaCuentasPorCobrarClientes;
    }

    public void setListaCuentasPorCobrarClientes(List<ConFlujoCajaCuentasPorCobrarClientesTO> listaCuentasPorCobrarClientes) {
        this.listaCuentasPorCobrarClientes = listaCuentasPorCobrarClientes;
    }

    public List<ConFlujoCajaCuentasPorPagarProveedoresTO> getListaCuentasPorPagarProveedores() {
        return listaCuentasPorPagarProveedores;
    }

    public void setListaCuentasPorPagarProveedores(List<ConFlujoCajaCuentasPorPagarProveedoresTO> listaCuentasPorPagarProveedores) {
        this.listaCuentasPorPagarProveedores = listaCuentasPorPagarProveedores;
    }

    public List<ConFlujoCajaChequesPostfechadosProveedoresTO> getListaChequesPostfechados() {
        return listaChequesPostfechados;
    }

    public void setListaChequesPostfechados(List<ConFlujoCajaChequesPostfechadosProveedoresTO> listaChequesPostfechados) {
        this.listaChequesPostfechados = listaChequesPostfechados;
    }

    public List<ConFlujoCajaValorizacionActivoBiologicoTO> getListaValorizacion() {
        return listaValorizacion;
    }

    public void setListaValorizacion(List<ConFlujoCajaValorizacionActivoBiologicoTO> listaValorizacion) {
        this.listaValorizacion = listaValorizacion;
    }

    public BigDecimal getSumaBanco() {
        return sumaBanco;
    }

    public void setSumaBanco(BigDecimal sumaBanco) {
        this.sumaBanco = sumaBanco;
    }

    public BigDecimal getSumaClientes() {
        return sumaClientes;
    }

    public void setSumaClientes(BigDecimal sumaClientes) {
        this.sumaClientes = sumaClientes;
    }

    public BigDecimal getSumaProveedores() {
        return sumaProveedores;
    }

    public void setSumaProveedores(BigDecimal sumaProveedores) {
        this.sumaProveedores = sumaProveedores;
    }

    public BigDecimal getSumaCheque() {
        return sumaCheque;
    }

    public void setSumaCheque(BigDecimal sumaCheque) {
        this.sumaCheque = sumaCheque;
    }

    public BigDecimal getSumaValorizacion() {
        return sumaValorizacion;
    }

    public void setSumaValorizacion(BigDecimal sumaValorizacion) {
        this.sumaValorizacion = sumaValorizacion;
    }

    public BigDecimal getSumaEfectivo() {
        return sumaEfectivo;
    }

    public void setSumaEfectivo(BigDecimal sumaEfectivo) {
        this.sumaEfectivo = sumaEfectivo;
    }

    public BigDecimal getSumaCuentasPorPagar() {
        return sumaCuentasPorPagar;
    }

    public void setSumaCuentasPorPagar(BigDecimal sumaCuentasPorPagar) {
        this.sumaCuentasPorPagar = sumaCuentasPorPagar;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    
    
    
}
