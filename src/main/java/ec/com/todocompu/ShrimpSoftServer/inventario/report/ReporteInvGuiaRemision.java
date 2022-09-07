/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import java.math.BigDecimal;

/**
 *
 * @author CarolValdiviezo
 */
public class ReporteInvGuiaRemision {

    private String guiaFechaEmision;
    private String guiaDocumentoNumero;
    private String guiaPlaca;
    private String guiaFechaInicioTransporte;
    private String guiaFechaFinTransporte;
    private String guiaPuntoPartida;
    private String guiaPuntoLlegada;
    private String nroDocumento;
    private String tipoDocumento;
    private String nroAutorizacion;
    private String guiaMotivoTraslado;
    private String guiaCodigoEstablecimiento;
    private String guiaDocumentoAduanero;
    private String guiaRuta;
    private BigDecimal guiaRutaPrecio;
    //transportista
    private String identificacionTransportista;
    private String nombresTransportista;
    //destinatario
    private String identificacionDestinatario;
    private String nombresDestinatario;

    //detalle
    private BigDecimal detCantidad;
    private String proCodigoPrincipal;
    private String nombreProducto;
    
    private String guiaTipoMovil;
    private String guiaRecibidor;
    private String guiaSello;
    
    private BigDecimal guiaGramos;
    private BigDecimal guiaLibras;
    private String guiaHora;
    
    private String guiaNumero;
    private String guiaInp;

    public ReporteInvGuiaRemision() {
    }

    public ReporteInvGuiaRemision(String guiaFechaEmision, String guiaDocumentoNumero, String guiaPlaca, String guiaFechaInicioTransporte, String guiaFechaFinTransporte, String guiaPuntoPartida, String guiaPuntoLlegada, String nroDocumento, String tipoDocumento, String nroAutorizacion, String guiaMotivoTraslado, String guiaCodigoEstablecimiento, String guiaDocumentoAduanero, String guiaRuta, String identificacionTransportista, String nombresTransportista, String identificacionDestinatario, String nombresDestinatario, BigDecimal detCantidad, String proCodigoPrincipal, String nombreProducto) {
        this.guiaFechaEmision = guiaFechaEmision;
        this.guiaDocumentoNumero = guiaDocumentoNumero;
        this.guiaPlaca = guiaPlaca;
        this.guiaFechaInicioTransporte = guiaFechaInicioTransporte;
        this.guiaFechaFinTransporte = guiaFechaFinTransporte;
        this.guiaPuntoPartida = guiaPuntoPartida;
        this.guiaPuntoLlegada = guiaPuntoLlegada;
        this.nroDocumento = nroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.nroAutorizacion = nroAutorizacion;
        this.guiaMotivoTraslado = guiaMotivoTraslado;
        this.guiaCodigoEstablecimiento = guiaCodigoEstablecimiento;
        this.guiaDocumentoAduanero = guiaDocumentoAduanero;
        this.guiaRuta = guiaRuta;
        this.identificacionTransportista = identificacionTransportista;
        this.nombresTransportista = nombresTransportista;
        this.identificacionDestinatario = identificacionDestinatario;
        this.nombresDestinatario = nombresDestinatario;
        this.detCantidad = detCantidad;
        this.proCodigoPrincipal = proCodigoPrincipal;
        this.nombreProducto = nombreProducto;
    }

    public String getGuiaFechaEmision() {
        return guiaFechaEmision;
    }

    public void setGuiaFechaEmision(String guiaFechaEmision) {
        this.guiaFechaEmision = guiaFechaEmision;
    }

    public String getGuiaDocumentoNumero() {
        return guiaDocumentoNumero;
    }

    public void setGuiaDocumentoNumero(String guiaDocumentoNumero) {
        this.guiaDocumentoNumero = guiaDocumentoNumero;
    }

    public String getGuiaPlaca() {
        return guiaPlaca;
    }

    public void setGuiaPlaca(String guiaPlaca) {
        this.guiaPlaca = guiaPlaca;
    }

    public String getGuiaFechaInicioTransporte() {
        return guiaFechaInicioTransporte;
    }

    public void setGuiaFechaInicioTransporte(String guiaFechaInicioTransporte) {
        this.guiaFechaInicioTransporte = guiaFechaInicioTransporte;
    }

    public String getGuiaFechaFinTransporte() {
        return guiaFechaFinTransporte;
    }

    public void setGuiaFechaFinTransporte(String guiaFechaFinTransporte) {
        this.guiaFechaFinTransporte = guiaFechaFinTransporte;
    }

    public String getGuiaPuntoPartida() {
        return guiaPuntoPartida;
    }

    public void setGuiaPuntoPartida(String guiaPuntoPartida) {
        this.guiaPuntoPartida = guiaPuntoPartida;
    }

    public String getGuiaPuntoLlegada() {
        return guiaPuntoLlegada;
    }

    public void setGuiaPuntoLlegada(String guiaPuntoLlegada) {
        this.guiaPuntoLlegada = guiaPuntoLlegada;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public String getGuiaMotivoTraslado() {
        return guiaMotivoTraslado;
    }

    public void setGuiaMotivoTraslado(String guiaMotivoTraslado) {
        this.guiaMotivoTraslado = guiaMotivoTraslado;
    }

    public String getGuiaCodigoEstablecimiento() {
        return guiaCodigoEstablecimiento;
    }

    public void setGuiaCodigoEstablecimiento(String guiaCodigoEstablecimiento) {
        this.guiaCodigoEstablecimiento = guiaCodigoEstablecimiento;
    }

    public String getGuiaDocumentoAduanero() {
        return guiaDocumentoAduanero;
    }

    public void setGuiaDocumentoAduanero(String guiaDocumentoAduanero) {
        this.guiaDocumentoAduanero = guiaDocumentoAduanero;
    }

    public String getGuiaRuta() {
        return guiaRuta;
    }

    public void setGuiaRuta(String guiaRuta) {
        this.guiaRuta = guiaRuta;
    }

    public String getIdentificacionTransportista() {
        return identificacionTransportista;
    }

    public void setIdentificacionTransportista(String identificacionTransportista) {
        this.identificacionTransportista = identificacionTransportista;
    }

    public String getNombresTransportista() {
        return nombresTransportista;
    }

    public void setNombresTransportista(String nombresTransportista) {
        this.nombresTransportista = nombresTransportista;
    }

    public String getIdentificacionDestinatario() {
        return identificacionDestinatario;
    }

    public void setIdentificacionDestinatario(String identificacionDestinatario) {
        this.identificacionDestinatario = identificacionDestinatario;
    }

    public String getNombresDestinatario() {
        return nombresDestinatario;
    }

    public void setNombresDestinatario(String nombresDestinatario) {
        this.nombresDestinatario = nombresDestinatario;
    }

    public BigDecimal getDetCantidad() {
        return detCantidad;
    }

    public void setDetCantidad(BigDecimal detCantidad) {
        this.detCantidad = detCantidad;
    }

    public String getProCodigoPrincipal() {
        return proCodigoPrincipal;
    }

    public void setProCodigoPrincipal(String proCodigoPrincipal) {
        this.proCodigoPrincipal = proCodigoPrincipal;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getGuiaTipoMovil() {
        return guiaTipoMovil;
    }

    public void setGuiaTipoMovil(String guiaTipoMovil) {
        this.guiaTipoMovil = guiaTipoMovil;
    }

    public String getGuiaRecibidor() {
        return guiaRecibidor;
    }

    public void setGuiaRecibidor(String guiaRecibidor) {
        this.guiaRecibidor = guiaRecibidor;
    }

    public String getGuiaSello() {
        return guiaSello;
    }

    public void setGuiaSello(String guiaSello) {
        this.guiaSello = guiaSello;
    }  

    public BigDecimal getGuiaGramos() {
        return guiaGramos;
    }

    public void setGuiaGramos(BigDecimal guiaGramos) {
        this.guiaGramos = guiaGramos;
    }

    public BigDecimal getGuiaLibras() {
        return guiaLibras;
    }

    public void setGuiaLibras(BigDecimal guiaLibras) {
        this.guiaLibras = guiaLibras;
    }

    public String getGuiaHora() {
        return guiaHora;
    }

    public void setGuiaHora(String guiaHora) {
        this.guiaHora = guiaHora;
    }  

    public String getGuiaNumero() {
        return guiaNumero;
    }

    public void setGuiaNumero(String guiaNumero) {
        this.guiaNumero = guiaNumero;
    }

    public String getGuiaInp() {
        return guiaInp;
    }

    public void setGuiaInp(String guiaInp) {
        this.guiaInp = guiaInp;
    }

    public BigDecimal getGuiaRutaPrecio() {
        return guiaRutaPrecio;
    }

    public void setGuiaRutaPrecio(BigDecimal guiaRutaPrecio) {
        this.guiaRutaPrecio = guiaRutaPrecio;
    }
   
}
