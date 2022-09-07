/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.facturareembolso;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.InfoTributaria;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura.ImpuestoFactura;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Trabajo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "infoTributaria",
    "infoFactura",
    "detalles",
    "reembolsos",
    "infoAdicional"})
@XmlRootElement(name = "factura")
public class FacturaReembolso {

    @XmlAttribute
    protected String id;
    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String version;
    @XmlElement(required = true)
    protected InfoTributaria infoTributaria;
    @XmlElement(required = true)
    protected InfoFactura infoFactura;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "fechaEmision",
        "dirEstablecimiento",
        "contribuyenteEspecial",
        "obligadoContabilidad",
        "tipoIdentificacionComprador",
        "guiaRemision",
        "razonSocialComprador",
        "identificacionComprador",
        "direccionComprador",//nuevo campo
        "totalSinImpuestos",
        "totalDescuento",
        "codDocReembolso",//nuevo campo
        "totalComprobantesReembolso",//nuevo campo
        "totalBaseImponibleReembolso",//nuevo campo
        "totalImpuestoReembolso",//nuevo campo
        "totalConImpuestos",
        "propina",
        "importeTotal",
        "moneda",
        "pagos"})
    public static class InfoFactura {

        @XmlElement(required = true)
        protected String fechaEmision;
        @XmlElement(required = true)
        protected String dirEstablecimiento;
        protected String contribuyenteEspecial;
        protected String obligadoContabilidad;
        @XmlElement(required = true)
        protected String tipoIdentificacionComprador;
        protected String guiaRemision;
        @XmlElement(required = true)
        protected String razonSocialComprador;
        @XmlElement(required = true)
        protected String identificacionComprador;
        protected String direccionComprador;//nuevo campo
        @XmlElement(required = true)
        protected BigDecimal totalSinImpuestos;
        @XmlElement(required = true)
        protected BigDecimal totalDescuento;
        protected Integer codDocReembolso;//nuevo campo
        protected BigDecimal totalComprobantesReembolso;//nuevo campo
        protected BigDecimal totalBaseImponibleReembolso;//nuevo campo
        protected BigDecimal totalImpuestoReembolso;//nuevo campo
        @XmlElement(required = true)
        protected TotalConImpuesto totalConImpuestos;

        @XmlElement(required = true)
        protected Pagos pagos;

        @XmlElement(required = true)
        protected BigDecimal propina;

        @XmlElement(required = true)
        protected BigDecimal importeTotal;
        protected String moneda;

        public String getFechaEmision() {
            return this.fechaEmision;
        }

        public void setFechaEmision(String value) {
            this.fechaEmision = value;
        }

        public String getDirEstablecimiento() {
            return this.dirEstablecimiento;
        }

        public void setDirEstablecimiento(String value) {
            this.dirEstablecimiento = value;
        }

        public String getContribuyenteEspecial() {
            return this.contribuyenteEspecial;
        }

        public void setContribuyenteEspecial(String value) {
            this.contribuyenteEspecial = value;
        }

        public String getObligadoContabilidad() {
            return this.obligadoContabilidad;
        }

        public void setObligadoContabilidad(String value) {
            this.obligadoContabilidad = value;
        }

        public String getTipoIdentificacionComprador() {
            return this.tipoIdentificacionComprador;
        }

        public void setTipoIdentificacionComprador(String value) {
            this.tipoIdentificacionComprador = value;
        }

        public String getGuiaRemision() {
            return this.guiaRemision;
        }

        public void setGuiaRemision(String value) {
            this.guiaRemision = value;
        }

        public String getRazonSocialComprador() {
            return this.razonSocialComprador;
        }

        public void setRazonSocialComprador(String value) {
            this.razonSocialComprador = value;
        }

        public String getIdentificacionComprador() {
            return this.identificacionComprador;
        }

        public void setIdentificacionComprador(String value) {
            this.identificacionComprador = value;
        }

        public BigDecimal getTotalSinImpuestos() {
            return this.totalSinImpuestos;
        }

        public void setTotalSinImpuestos(BigDecimal value) {
            this.totalSinImpuestos = value;
        }

        public BigDecimal getTotalDescuento() {
            return this.totalDescuento;
        }

        public void setTotalDescuento(BigDecimal value) {
            this.totalDescuento = value;
        }

        public TotalConImpuesto getTotalConImpuestos() {
            return totalConImpuestos;
        }

        public void setTotalConImpuestos(TotalConImpuesto totalConImpuestos) {
            this.totalConImpuestos = totalConImpuestos;
        }

        public Pagos getPagos() {
            return this.pagos;
        }

        public void setPagos(Pagos value) {
            this.pagos = value;
        }

        public BigDecimal getPropina() {
            return this.propina;
        }

        public void setPropina(BigDecimal value) {
            this.propina = value;
        }

        public BigDecimal getImporteTotal() {
            return this.importeTotal;
        }

        public void setImporteTotal(BigDecimal value) {
            this.importeTotal = value;
        }

        public String getMoneda() {
            return this.moneda;
        }

        public void setMoneda(String value) {
            this.moneda = value;
        }

        public String getDireccionComprador() {
            return direccionComprador;
        }

        public void setDireccionComprador(String direccionComprador) {
            this.direccionComprador = direccionComprador;
        }

        public Integer getCodDocReembolso() {
            return codDocReembolso;
        }

        public void setCodDocReembolso(Integer codDocReembolso) {
            this.codDocReembolso = codDocReembolso;
        }

        public BigDecimal getTotalComprobantesReembolso() {
            return totalComprobantesReembolso;
        }

        public void setTotalComprobantesReembolso(BigDecimal totalComprobantesReembolso) {
            this.totalComprobantesReembolso = totalComprobantesReembolso;
        }

        public BigDecimal getTotalBaseImponibleReembolso() {
            return totalBaseImponibleReembolso;
        }

        public void setTotalBaseImponibleReembolso(BigDecimal totalBaseImponibleReembolso) {
            this.totalBaseImponibleReembolso = totalBaseImponibleReembolso;
        }

        public BigDecimal getTotalImpuestoReembolso() {
            return totalImpuestoReembolso;
        }

        public void setTotalImpuestoReembolso(BigDecimal totalImpuestoReembolso) {
            this.totalImpuestoReembolso = totalImpuestoReembolso;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"totalImpuesto"})
        public static class TotalConImpuesto {

            @XmlElement(required = true)
            protected List<TotalImpuesto> totalImpuesto;

            public List<TotalImpuesto> getTotalImpuesto() {
                if (this.totalImpuesto == null) {
                    this.totalImpuesto = new ArrayList();
                }
                return this.totalImpuesto;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"codigo", "codigoPorcentaje", "baseImponible", "valor"})
            public static class TotalImpuesto {

                @XmlElement(required = true)
                protected String codigo;
                @XmlElement(required = true)
                protected String codigoPorcentaje;
                @XmlElement(required = true)
                protected BigDecimal baseImponible;
                @XmlElement(required = true)
                protected BigDecimal valor;

                public String getCodigo() {
                    return this.codigo;
                }

                public void setCodigo(String value) {
                    this.codigo = value;
                }

                public String getCodigoPorcentaje() {
                    return this.codigoPorcentaje;
                }

                public void setCodigoPorcentaje(String value) {
                    this.codigoPorcentaje = value;
                }

                public BigDecimal getBaseImponible() {
                    return this.baseImponible;
                }

                public void setBaseImponible(BigDecimal value) {
                    this.baseImponible = value;
                }

                public BigDecimal getValor() {
                    return this.valor;
                }

                public void setValor(BigDecimal value) {
                    this.valor = value;
                }
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"pago"})
        public static class Pagos {

            @XmlElement(required = true)
            protected List<Pago> pago;

            public List<Pago> getPago() {
                if (this.pago == null) {
                    this.pago = new ArrayList();
                }
                return this.pago;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"formaPago", "total"})
            public static class Pago {

                @XmlElement(required = true)
                protected String formaPago;
                @XmlElement(required = true)
                protected BigDecimal total;

                public String getFormaPago() {
                    return this.formaPago;
                }

                public void setFormaPago(String value) {
                    this.formaPago = value;
                }

                public BigDecimal getTotal() {
                    return this.total;
                }

                public void setTotal(BigDecimal value) {
                    this.total = value;
                }
            }
        }
    }

    @XmlElement(required = true)
    public Detalles detalles;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"detalle"})
    public static class Detalles {

        @XmlElement(required = true)
        protected List<Detalle> detalle;

        public List<Detalle> getDetalle() {
            if (this.detalle == null) {
                this.detalle = new ArrayList();
            }
            return this.detalle;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "codigoPrincipal",
            "codigoAuxiliar",
            "descripcion",
            "cantidad",
            "precioUnitario",
            "descuento",
            "precioTotalSinImpuesto",
            "detallesAdicionales",
            "impuestos"})
        public static class Detalle {

            @XmlElement(required = true)
            protected String codigoPrincipal;
            protected String codigoAuxiliar;
            @XmlElement(required = true)
            protected String descripcion;
            @XmlElement(required = true)
            protected BigDecimal cantidad;
            @XmlElement(required = true)
            protected BigDecimal precioUnitario;
            @XmlElement(required = true)
            protected BigDecimal descuento;
            @XmlElement(required = true)
            protected BigDecimal precioTotalSinImpuesto;
            protected DetallesAdicionales detallesAdicionales;
            @XmlElement(required = true)
            protected Impuestos impuestos;

            public String getCodigoPrincipal() {
                return this.codigoPrincipal;
            }

            public void setCodigoPrincipal(String value) {
                this.codigoPrincipal = value;
            }

            public String getCodigoAuxiliar() {
                return this.codigoAuxiliar;
            }

            public void setCodigoAuxiliar(String value) {
                this.codigoAuxiliar = value;
            }

            public String getDescripcion() {
                return this.descripcion;
            }

            public void setDescripcion(String value) {
                this.descripcion = value;
            }

            public BigDecimal getCantidad() {
                return this.cantidad;
            }

            public void setCantidad(BigDecimal value) {
                this.cantidad = value;
            }

            public BigDecimal getPrecioUnitario() {
                return this.precioUnitario;
            }

            public void setPrecioUnitario(BigDecimal value) {
                this.precioUnitario = value;
            }

            public BigDecimal getDescuento() {
                return this.descuento;
            }

            public void setDescuento(BigDecimal value) {
                this.descuento = value;
            }

            public BigDecimal getPrecioTotalSinImpuesto() {
                return this.precioTotalSinImpuesto;
            }

            public void setPrecioTotalSinImpuesto(BigDecimal value) {
                this.precioTotalSinImpuesto = value;
            }

            public DetallesAdicionales getDetallesAdicionales() {
                return this.detallesAdicionales;
            }

            public void setDetallesAdicionales(DetallesAdicionales value) {
                this.detallesAdicionales = value;
            }

            public Impuestos getImpuestos() {
                return this.impuestos;
            }

            public void setImpuestos(Impuestos value) {
                this.impuestos = value;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"impuesto"})
            public static class Impuestos {

                @XmlElement(required = true)
                protected List<ImpuestoFactura> impuesto;

                public List<ImpuestoFactura> getImpuesto() {
                    if (this.impuesto == null) {
                        this.impuesto = new ArrayList();
                    }
                    return this.impuesto;
                }
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"detAdicional"})
            public static class DetallesAdicionales {

                @XmlElement(required = true)
                protected List<DetAdicional> detAdicional;

                public List<DetAdicional> getDetAdicional() {
                    if (this.detAdicional == null) {
                        this.detAdicional = new ArrayList();
                    }
                    return this.detAdicional;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "")
                public static class DetAdicional {

                    @XmlAttribute
                    protected String nombre;

                    @XmlAttribute
                    protected String valor;

                    public String getNombre() {
                        return this.nombre;
                    }

                    public void setNombre(String value) {
                        this.nombre = value;
                    }

                    public String getValor() {
                        return this.valor;
                    }

                    public void setValor(String value) {
                        this.valor = value;
                    }
                }
            }
        }
    }

    public Reembolsos reembolsos;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"reembolsoDetalle"})
    public static class Reembolsos {

        @XmlElement(required = true)
        protected List<ReembolsoDetalle> reembolsoDetalle;

        public List<ReembolsoDetalle> getReembolsoDetalle() {
            if (this.reembolsoDetalle == null) {
                this.reembolsoDetalle = new ArrayList();
            }
            return this.reembolsoDetalle;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "tipoIdentificacionProveedorReembolso",
            "identificacionProveedorReembolso",
            "codPaisPagoProveedorReembolso",
            "tipoProveedorReembolso",
            "codDocReembolso",
            "estabDocReembolso",
            "ptoEmiDocReembolso",
            "secuencialDocReembolso",
            "fechaEmisionDocReembolso",
            "numeroautorizacionDocReemb",
            "detalleImpuestos"})
        public static class ReembolsoDetalle {

            protected String tipoIdentificacionProveedorReembolso;
            protected String identificacionProveedorReembolso;
            protected String codPaisPagoProveedorReembolso;
            protected String tipoProveedorReembolso;
            protected String codDocReembolso;
            protected String estabDocReembolso;
            protected String ptoEmiDocReembolso;
            protected String secuencialDocReembolso;
            protected String fechaEmisionDocReembolso;
            protected String numeroautorizacionDocReemb;
            protected DetalleImpuestos detalleImpuestos;

            public String getTipoIdentificacionProveedorReembolso() {
                return tipoIdentificacionProveedorReembolso;
            }

            public void setTipoIdentificacionProveedorReembolso(String tipoIdentificacionProveedorReembolso) {
                this.tipoIdentificacionProveedorReembolso = tipoIdentificacionProveedorReembolso;
            }

            public String getIdentificacionProveedorReembolso() {
                return identificacionProveedorReembolso;
            }

            public void setIdentificacionProveedorReembolso(String identificacionProveedorReembolso) {
                this.identificacionProveedorReembolso = identificacionProveedorReembolso;
            }

            public String getCodPaisPagoProveedorReembolso() {
                return codPaisPagoProveedorReembolso;
            }

            public void setCodPaisPagoProveedorReembolso(String codPaisPagoProveedorReembolso) {
                this.codPaisPagoProveedorReembolso = codPaisPagoProveedorReembolso;
            }

            public String getTipoProveedorReembolso() {
                return tipoProveedorReembolso;
            }

            public void setTipoProveedorReembolso(String tipoProveedorReembolso) {
                this.tipoProveedorReembolso = tipoProveedorReembolso;
            }

            public String getCodDocReembolso() {
                return codDocReembolso;
            }

            public void setCodDocReembolso(String codDocReembolso) {
                this.codDocReembolso = codDocReembolso;
            }

            public String getEstabDocReembolso() {
                return estabDocReembolso;
            }

            public void setEstabDocReembolso(String estabDocReembolso) {
                this.estabDocReembolso = estabDocReembolso;
            }

            public String getPtoEmiDocReembolso() {
                return ptoEmiDocReembolso;
            }

            public void setPtoEmiDocReembolso(String ptoEmiDocReembolso) {
                this.ptoEmiDocReembolso = ptoEmiDocReembolso;
            }

            public String getSecuencialDocReembolso() {
                return secuencialDocReembolso;
            }

            public void setSecuencialDocReembolso(String secuencialDocReembolso) {
                this.secuencialDocReembolso = secuencialDocReembolso;
            }

            public String getFechaEmisionDocReembolso() {
                return fechaEmisionDocReembolso;
            }

            public void setFechaEmisionDocReembolso(String fechaEmisionDocReembolso) {
                this.fechaEmisionDocReembolso = fechaEmisionDocReembolso;
            }

            public String getNumeroautorizacionDocReemb() {
                return numeroautorizacionDocReemb;
            }

            public void setNumeroautorizacionDocReemb(String numeroautorizacionDocReemb) {
                this.numeroautorizacionDocReemb = numeroautorizacionDocReemb;
            }

            public DetalleImpuestos getDetalleImpuestos() {
                return detalleImpuestos;
            }

            public void setDetalleImpuestos(DetalleImpuestos detalleImpuestos) {
                this.detalleImpuestos = detalleImpuestos;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"detalleImpuesto"})
            public static class DetalleImpuestos {

                @XmlElement(required = true)
                protected List<DetalleImpuesto> detalleImpuesto;

                public List<DetalleImpuesto> getDetalleImpuesto() {
                    if (this.detalleImpuesto == null) {
                        this.detalleImpuesto = new ArrayList();
                    }
                    return this.detalleImpuesto;
                }

                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "codigo",
                    "codigoPorcentaje",
                    "tarifa",
                    "baseImponibleReembolso",
                    "impuestoReembolso"
                })
                public static class DetalleImpuesto {

                    protected String codigo;
                    protected String codigoPorcentaje;
                    protected BigDecimal tarifa;
                    protected BigDecimal baseImponibleReembolso;
                    protected BigDecimal impuestoReembolso;

                    public String getCodigo() {
                        return codigo;
                    }

                    public void setCodigo(String codigo) {
                        this.codigo = codigo;
                    }

                    public String getCodigoPorcentaje() {
                        return codigoPorcentaje;
                    }

                    public void setCodigoPorcentaje(String codigoPorcentaje) {
                        this.codigoPorcentaje = codigoPorcentaje;
                    }

                    public BigDecimal getTarifa() {
                        return tarifa;
                    }

                    public void setTarifa(BigDecimal tarifa) {
                        this.tarifa = tarifa;
                    }

                    public BigDecimal getBaseImponibleReembolso() {
                        return baseImponibleReembolso;
                    }

                    public void setBaseImponibleReembolso(BigDecimal baseImponibleReembolso) {
                        this.baseImponibleReembolso = baseImponibleReembolso;
                    }

                    public BigDecimal getImpuestoReembolso() {
                        return impuestoReembolso;
                    }

                    public void setImpuestoReembolso(BigDecimal impuestoReembolso) {
                        this.impuestoReembolso = impuestoReembolso;
                    }

                }
            }
        }
    }
    protected InfoAdicional infoAdicional;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"campoAdicional"})
    public static class InfoAdicional {

        @XmlElement(required = true)
        protected List<CampoAdicional> campoAdicional;

        public List<CampoAdicional> getCampoAdicional() {
            if (this.campoAdicional == null) {
                this.campoAdicional = new ArrayList();
            }
            return this.campoAdicional;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"value"})
        public static class CampoAdicional {

            @XmlValue
            protected String value;

            @XmlAttribute
            protected String nombre;

            public String getValue() {
                return this.value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getNombre() {
                return this.nombre;
            }

            public void setNombre(String value) {
                this.nombre = value;
            }
        }
    }

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoFactura getInfoFactura() {
        return infoFactura;
    }

    public void setInfoFactura(InfoFactura infoFactura) {
        this.infoFactura = infoFactura;
    }

    public Detalles getDetalles() {
        return detalles;
    }

    public void setDetalles(Detalles detalles) {
        this.detalles = detalles;
    }

    public Reembolsos getReembolsos() {
        return reembolsos;
    }

    public void setReembolsos(Reembolsos reembolsos) {
        this.reembolsos = reembolsos;
    }

    public InfoAdicional getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(InfoAdicional infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
