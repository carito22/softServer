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
public class ReporteInvPedidosOrdenCompra {

    private Integer nroItem;
    private String ocEmpresa;
    private String ocNumero;
    private String ocmCodigo;
    private Date ocFecha;
    private String ocmDetalle;
    private String formaPago;
    private String ocObservacionesRegistra;
    private String ocObservacionesRegistraPedido;
    private Date fechahoraAprobado;
    //Orden pedido
    private String opEmpresa;
    private String opNumero;
    private String opmCodigo;
    private String opSector;
    private Date fechaPedido;
    private Date fechaElaboracion;
    private String opUsrRegistra;
    private String opUsrAprueba;
    //Usuario modifica
    private String ocUsrModifica;
    private Date ocUsrFechaModifica;
    //Sector
    private String ocSector;
    private String ocSectorDetalle;
    //Motivo
    private String ocMotivo;
    private String ocMotivoDetalle;
    //Proveedor
    private String provCodigo;
    private String provRazonSocial;
    private String provDireccion;
    private String provProvincia;
    private String provCiudad;
    private String provCorreo;
    private String provRUC;
    private String provTelefono;
    //Producto
    private String proCodigoPrincipal;
    private String proNombre;
    private String proMedida;
    private String proMedidaCodigo;
    private String proIva;

    //Cliente
    private String nombreCliente;
    private String documentoCliente;
    //Contacto
    private Date fechahoraEntrega;
    private String lugarEntrega;
    private String nombreContacto;
    private String telefonoContacto;
    //Elaborado
    private String elaboradoPor;
    private String aprobadoPor;
    private Date userFechaInserta;
    //Cantidades
    private BigDecimal detCantidadAdquirida;
    private BigDecimal retencion;
    private BigDecimal precioReal;
    private BigDecimal total;
    private BigDecimal ocBase0;
    private BigDecimal ocBaseImponible;
    private BigDecimal ocMontoIva;
    private BigDecimal ocIvaVigente;
    private BigDecimal ocMontoTotal;
    //Obs de detalle
    private String detObservaciones;
    //Orden de compra
    private String pedOrdenCompra;
    //Campos solo para gagroup
    private String presentacionCaja;
    private java.math.BigDecimal cantidadDivididaFactorConversion;
    private BigDecimal detSacos;

    public ReporteInvPedidosOrdenCompra() {
    }

    public BigDecimal getOcMontoTotal() {
        return ocMontoTotal;
    }

    public void setOcMontoTotal(BigDecimal ocMontoTotal) {
        this.ocMontoTotal = ocMontoTotal;
    }

    public String getOcEmpresa() {
        return ocEmpresa;
    }

    public void setOcEmpresa(String ocEmpresa) {
        this.ocEmpresa = ocEmpresa;
    }

    public String getOcSector() {
        return ocSector;
    }

    public void setOcSector(String ocSector) {
        this.ocSector = ocSector;
    }

    public String getOcMotivo() {
        return ocMotivo;
    }

    public void setOcMotivo(String ocMotivo) {
        this.ocMotivo = ocMotivo;
    }

    public String getOcNumero() {
        return ocNumero;
    }

    public void setOcNumero(String ocNumero) {
        this.ocNumero = ocNumero;
    }

    public Date getOcFecha() {
        return ocFecha;
    }

    public void setOcFecha(Date ocFecha) {
        this.ocFecha = ocFecha;
    }

    public String getProvCodigo() {
        return provCodigo;
    }

    public void setProvCodigo(String provCodigo) {
        this.provCodigo = provCodigo;
    }

    public String getProvRazonSocial() {
        return provRazonSocial;
    }

    public void setProvRazonSocial(String provRazonSocial) {
        this.provRazonSocial = provRazonSocial;
    }

    public String getOcmCodigo() {
        return ocmCodigo;
    }

    public void setOcmCodigo(String ocmCodigo) {
        this.ocmCodigo = ocmCodigo;
    }

    public String getProMedidaCodigo() {
        return proMedidaCodigo;
    }

    public void setProMedidaCodigo(String proMedidaCodigo) {
        this.proMedidaCodigo = proMedidaCodigo;
    }

    public String getOcmDetalle() {
        return ocmDetalle;
    }

    public void setOcmDetalle(String ocmDetalle) {
        this.ocmDetalle = ocmDetalle;
    }

    public String getOcObservacionesRegistra() {
        return ocObservacionesRegistra;
    }

    public void setOcObservacionesRegistra(String ocObservacionesRegistra) {
        this.ocObservacionesRegistra = ocObservacionesRegistra;
    }

    public String getProCodigoPrincipal() {
        return proCodigoPrincipal;
    }

    public void setProCodigoPrincipal(String proCodigoPrincipal) {
        this.proCodigoPrincipal = proCodigoPrincipal;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public BigDecimal getDetCantidadAdquirida() {
        return detCantidadAdquirida;
    }

    public void setDetCantidadAdquirida(BigDecimal detCantidadAdquirida) {
        this.detCantidadAdquirida = detCantidadAdquirida;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Date getFechahoraEntrega() {
        return fechahoraEntrega;
    }

    public void setFechahoraEntrega(Date fechahoraEntrega) {
        this.fechahoraEntrega = fechahoraEntrega;
    }

    public Date getFechahoraAprobado() {
        return fechahoraAprobado;
    }

    public void setFechahoraAprobado(Date fechahoraAprobado) {
        this.fechahoraAprobado = fechahoraAprobado;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getElaboradoPor() {
        return elaboradoPor;
    }

    public void setElaboradoPor(String elaboradoPor) {
        this.elaboradoPor = elaboradoPor;
    }

    public String getAprobadoPor() {
        return aprobadoPor;
    }

    public void setAprobadoPor(String aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }

    public Date getUserFechaInserta() {
        return userFechaInserta;
    }

    public void setUserFechaInserta(Date userFechaInserta) {
        this.userFechaInserta = userFechaInserta;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }

    public BigDecimal getPrecioReal() {
        return precioReal;
    }

    public void setPrecioReal(BigDecimal precioReal) {
        this.precioReal = precioReal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getOcSectorDetalle() {
        return ocSectorDetalle;
    }

    public void setOcSectorDetalle(String ocSectorDetalle) {
        this.ocSectorDetalle = ocSectorDetalle;
    }

    public String getOcMotivoDetalle() {
        return ocMotivoDetalle;
    }

    public void setOcMotivoDetalle(String ocMotivoDetalle) {
        this.ocMotivoDetalle = ocMotivoDetalle;
    }

    public String getProMedida() {
        return proMedida;
    }

    public void setProMedida(String proMedida) {
        this.proMedida = proMedida;
    }

    public Integer getNroItem() {
        return nroItem;
    }

    public void setNroItem(Integer nroItem) {
        this.nroItem = nroItem;
    }

    public String getDetObservaciones() {
        return detObservaciones;
    }

    public void setDetObservaciones(String detObservaciones) {
        this.detObservaciones = detObservaciones;
    }

    public String getPedOrdenCompra() {
        return pedOrdenCompra;
    }

    public void setPedOrdenCompra(String pedOrdenCompra) {
        this.pedOrdenCompra = pedOrdenCompra;
    }

    public String getProvDireccion() {
        return provDireccion;
    }

    public void setProvDireccion(String provDireccion) {
        this.provDireccion = provDireccion;
    }

    public String getProvCorreo() {
        return provCorreo;
    }

    public void setProvCorreo(String provCorreo) {
        this.provCorreo = provCorreo;
    }

    public String getProvRUC() {
        return provRUC;
    }

    public void setProvRUC(String provRUC) {
        this.provRUC = provRUC;
    }

    public String getProvTelefono() {
        return provTelefono;
    }

    public void setProvTelefono(String provTelefono) {
        this.provTelefono = provTelefono;
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

    public String getOpEmpresa() {
        return opEmpresa;
    }

    public void setOpEmpresa(String opEmpresa) {
        this.opEmpresa = opEmpresa;
    }

    public String getOpNumero() {
        return opNumero;
    }

    public void setOpNumero(String opNumero) {
        this.opNumero = opNumero;
    }

    public String getOpmCodigo() {
        return opmCodigo;
    }

    public void setOpmCodigo(String opmCodigo) {
        this.opmCodigo = opmCodigo;
    }

    public String getOpSector() {
        return opSector;
    }

    public void setOpSector(String opSector) {
        this.opSector = opSector;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getOpUsrRegistra() {
        return opUsrRegistra;
    }

    public void setOpUsrRegistra(String opUsrRegistra) {
        this.opUsrRegistra = opUsrRegistra;
    }

    public String getOpUsrAprueba() {
        return opUsrAprueba;
    }

    public void setOpUsrAprueba(String opUsrAprueba) {
        this.opUsrAprueba = opUsrAprueba;
    }

    public String getProvProvincia() {
        return provProvincia;
    }

    public void setProvProvincia(String provProvincia) {
        this.provProvincia = provProvincia;
    }

    public String getProvCiudad() {
        return provCiudad;
    }

    public void setProvCiudad(String provCiudad) {
        this.provCiudad = provCiudad;
    }

    public String getOcObservacionesRegistraPedido() {
        return ocObservacionesRegistraPedido;
    }

    public void setOcObservacionesRegistraPedido(String ocObservacionesRegistraPedido) {
        this.ocObservacionesRegistraPedido = ocObservacionesRegistraPedido;
    }

    public String getOcUsrModifica() {
        return ocUsrModifica;
    }

    public void setOcUsrModifica(String ocUsrModifica) {
        this.ocUsrModifica = ocUsrModifica;
    }

    public Date getOcUsrFechaModifica() {
        return ocUsrFechaModifica;
    }

    public void setOcUsrFechaModifica(Date ocUsrFechaModifica) {
        this.ocUsrFechaModifica = ocUsrFechaModifica;
    }

    public BigDecimal getOcBase0() {
        return ocBase0;
    }

    public void setOcBase0(BigDecimal ocBase0) {
        this.ocBase0 = ocBase0;
    }

    public BigDecimal getOcBaseImponible() {
        return ocBaseImponible;
    }

    public void setOcBaseImponible(BigDecimal ocBaseImponible) {
        this.ocBaseImponible = ocBaseImponible;
    }

    public BigDecimal getOcMontoIva() {
        return ocMontoIva;
    }

    public void setOcMontoIva(BigDecimal ocMontoIva) {
        this.ocMontoIva = ocMontoIva;
    }

    public BigDecimal getOcIvaVigente() {
        return ocIvaVigente;
    }

    public void setOcIvaVigente(BigDecimal ocIvaVigente) {
        this.ocIvaVigente = ocIvaVigente;
    }

    public String getProIva() {
        return proIva;
    }

    public void setProIva(String proIva) {
        this.proIva = proIva;
    }

    public BigDecimal getDetSacos() {
        return detSacos;
    }

    public void setDetSacos(BigDecimal detSacos) {
        this.detSacos = detSacos;
    }

}
