/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.pedidos.report;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Developer4
 */
public class ReporteInvPedidos {

    private String pedEmpresa;
    private String pedSector;
    private String pedSectorDetalle;
    private String pedMotivo;
    private String pedMotivoDetalle;
    private String pedNumero;
    private String estado;
    private String receptores;
    private String observacionRegistrador;
    private String observacionAprobador;
    private String observacionEjecutor;
    //CLIENTE
    private String cliente;
    private String lugarDeEntrega;
    private String fechaDeEntrega;
    private String nombreDeContacto;
    private String horaDeEntrega;
    private String telefonoDeContacto;
    private Date fechaElaboracion;
    private Date fechaAprobada;
    //Categoria
    private String catCodigo;
    private String catDescripcion;
    ///// DETALLE
    private String icodigo;
    private String iDescripcion;
    private String medDescripcion;
    private String proMedidaCodigo;
    private java.math.BigDecimal detCantidadSolicitada;
    private java.math.BigDecimal detCantidadAprobada;
    private java.math.BigDecimal detCantidadAdquirida;
    private java.math.BigDecimal detPrecioReal;
    private java.math.BigDecimal pedMontoTotal;
    private String observacionItemRegistrador;
    private String observacionItemAprobador;
    private String observacionItemEjecutor;
    private String registrador;
    private String aprobador;
    private String ejecutor;
    private Date pedFecha;
    private Integer nroItem;
    //Campos solo para gagroup
    private String presentacionCaja;
    private java.math.BigDecimal cantidadDivididaFactorConversion;

    // ORDEN DE COMPRA
    private String pedOrdenCompra;

    public ReporteInvPedidos() {
    }

    public Date getFechaAprobada() {
        return fechaAprobada;
    }

    public void setFechaAprobada(Date fechaAprobada) {
        this.fechaAprobada = fechaAprobada;
    }

    public String getPedEmpresa() {
        return pedEmpresa;
    }

    public void setPedEmpresa(String pedEmpresa) {
        this.pedEmpresa = pedEmpresa;
    }

    public String getPedSector() {
        return pedSector;
    }

    public void setPedSector(String pedSector) {
        this.pedSector = pedSector;
    }

    public String getPedMotivo() {
        return pedMotivo;
    }

    public void setPedMotivo(String pedMotivo) {
        this.pedMotivo = pedMotivo;
    }

    public String getPedNumero() {
        return pedNumero;
    }

    public void setPedNumero(String pedNumero) {
        this.pedNumero = pedNumero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReceptores() {
        return receptores;
    }

    public void setReceptores(String receptores) {
        this.receptores = receptores;
    }

    public String getIcodigo() {
        return icodigo;
    }

    public void setIcodigo(String icodigo) {
        this.icodigo = icodigo;
    }

    public String getiDescripcion() {
        return iDescripcion;
    }

    public void setiDescripcion(String iDescripcion) {
        this.iDescripcion = iDescripcion;
    }

    public String getMedDescripcion() {
        return medDescripcion;
    }

    public void setMedDescripcion(String medDescripcion) {
        this.medDescripcion = medDescripcion;
    }

    public BigDecimal getDetCantidadSolicitada() {
        return detCantidadSolicitada;
    }

    public void setDetCantidadSolicitada(BigDecimal detCantidadSolicitada) {
        this.detCantidadSolicitada = detCantidadSolicitada;
    }

    public BigDecimal getDetCantidadAprobada() {
        return detCantidadAprobada;
    }

    public void setDetCantidadAprobada(BigDecimal detCantidadAprobada) {
        this.detCantidadAprobada = detCantidadAprobada;
    }

    public BigDecimal getDetCantidadAdquirida() {
        return detCantidadAdquirida;
    }

    public void setDetCantidadAdquirida(BigDecimal detCantidadAdquirida) {
        this.detCantidadAdquirida = detCantidadAdquirida;
    }

    public BigDecimal getDetPrecioReal() {
        return detPrecioReal;
    }

    public void setDetPrecioReal(BigDecimal detPrecioReal) {
        this.detPrecioReal = detPrecioReal;
    }

    public BigDecimal getPedMontoTotal() {
        return pedMontoTotal;
    }

    public void setPedMontoTotal(BigDecimal pedMontoTotal) {
        this.pedMontoTotal = pedMontoTotal;
    }

    public String getRegistrador() {
        return registrador;
    }

    public void setRegistrador(String registrador) {
        this.registrador = registrador;
    }

    public String getAprobador() {
        return aprobador;
    }

    public void setAprobador(String aprobador) {
        this.aprobador = aprobador;
    }

    public String getEjecutor() {
        return ejecutor;
    }

    public void setEjecutor(String ejecutor) {
        this.ejecutor = ejecutor;
    }

    public String getProMedidaCodigo() {
        return proMedidaCodigo;
    }

    public void setProMedidaCodigo(String proMedidaCodigo) {
        this.proMedidaCodigo = proMedidaCodigo;
    }

    public String getObservacionRegistrador() {
        return observacionRegistrador;
    }

    public void setObservacionRegistrador(String observacionRegistrador) {
        this.observacionRegistrador = observacionRegistrador;
    }

    public String getObservacionAprobador() {
        return observacionAprobador;
    }

    public void setObservacionAprobador(String observacionAprobador) {
        this.observacionAprobador = observacionAprobador;
    }

    public String getObservacionEjecutor() {
        return observacionEjecutor;
    }

    public void setObservacionEjecutor(String observacionEjecutor) {
        this.observacionEjecutor = observacionEjecutor;
    }

    public Date getPedFecha() {
        return pedFecha;
    }

    public void setPedFecha(Date pedFecha) {
        this.pedFecha = pedFecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getLugarDeEntrega() {
        return lugarDeEntrega;
    }

    public void setLugarDeEntrega(String lugarDeEntrega) {
        this.lugarDeEntrega = lugarDeEntrega;
    }

    public String getNombreDeContacto() {
        return nombreDeContacto;
    }

    public void setNombreDeContacto(String nombreDeContacto) {
        this.nombreDeContacto = nombreDeContacto;
    }

    public String getTelefonoDeContacto() {
        return telefonoDeContacto;
    }

    public void setTelefonoDeContacto(String telefonoDeContacto) {
        this.telefonoDeContacto = telefonoDeContacto;
    }

    public String getFechaDeEntrega() {
        return fechaDeEntrega;
    }

    public void setFechaDeEntrega(String fechaDeEntrega) {
        this.fechaDeEntrega = fechaDeEntrega;
    }

    public String getHoraDeEntrega() {
        return horaDeEntrega;
    }

    public void setHoraDeEntrega(String horaDeEntrega) {
        this.horaDeEntrega = horaDeEntrega;
    }

    public Integer getNroItem() {
        return nroItem;
    }

    public void setNroItem(Integer nroItem) {
        this.nroItem = nroItem;
    }

    public String getObservacionItemRegistrador() {
        return observacionItemRegistrador;
    }

    public void setObservacionItemRegistrador(String observacionItemRegistrador) {
        this.observacionItemRegistrador = observacionItemRegistrador;
    }

    public String getObservacionItemAprobador() {
        return observacionItemAprobador;
    }

    public void setObservacionItemAprobador(String observacionItemAprobador) {
        this.observacionItemAprobador = observacionItemAprobador;
    }

    public String getObservacionItemEjecutor() {
        return observacionItemEjecutor;
    }

    public void setObservacionItemEjecutor(String observacionItemEjecutor) {
        this.observacionItemEjecutor = observacionItemEjecutor;
    }

    public String getPedSectorDetalle() {
        return pedSectorDetalle;
    }

    public void setPedSectorDetalle(String pedSectorDetalle) {
        this.pedSectorDetalle = pedSectorDetalle;
    }

    public String getPedMotivoDetalle() {
        return pedMotivoDetalle;
    }

    public void setPedMotivoDetalle(String pedMotivoDetalle) {
        this.pedMotivoDetalle = pedMotivoDetalle;
    }

    public String getPedOrdenCompra() {
        return pedOrdenCompra;
    }

    public void setPedOrdenCompra(String pedOrdenCompra) {
        this.pedOrdenCompra = pedOrdenCompra;
    }

    public String getPresentacionCaja() {
        return presentacionCaja;
    }

    public void setPresentacionCaja(String presentacionCaja) {
        this.presentacionCaja = presentacionCaja;
    }

    public BigDecimal getCantidadDivididaFactorConversion() {
        return cantidadDivididaFactorConversion;
    }

    public void setCantidadDivididaFactorConversion(BigDecimal cantidadDivididaFactorConversion) {
        this.cantidadDivididaFactorConversion = cantidadDivididaFactorConversion;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public String getCatCodigo() {
        return catCodigo;
    }

    public void setCatCodigo(String catCodigo) {
        this.catCodigo = catCodigo;
    }

    public String getCatDescripcion() {
        return catDescripcion;
    }

    public void setCatDescripcion(String catDescripcion) {
        this.catDescripcion = catDescripcion;
    }

}
