/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.liquidacioncompra;

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.InfoTributaria;
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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author CarolValdiviezo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"infoTributaria", "infoLiquidacionCompra", "detalles", "reembolsos", "maquinaFiscal", "infoAdicional"})
@XmlRootElement(name = "liquidacionCompra")
public class LiquidacionCompra {

    @XmlElement(required = true)
    protected InfoTributaria infoTributaria;
    @XmlElement(required = true)
    protected InfoLiquidacionCompra infoLiquidacionCompra;
    @XmlElement(required = true)
    protected Detalles detalles;
    protected Reembolsos reembolsos;
    protected MaquinaFiscal maquinaFiscal;
    protected InfoAdicional infoAdicional;
    @XmlAttribute
    protected String id;

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String version;

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InfoLiquidacionCompra getInfoLiquidacionCompra() {
        return infoLiquidacionCompra;
    }

    public void setInfoLiquidacionCompra(InfoLiquidacionCompra infoLiquidacionCompra) {
        this.infoLiquidacionCompra = infoLiquidacionCompra;
    }

    public Detalles getDetalles() {
        return detalles;
    }

    public void setDetalles(Detalles detalles) {
        this.detalles = detalles;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"fechaEmision", "dirEstablecimiento", "contribuyenteEspecial",
        "obligadoContabilidad", "tipoIdentificacionProveedor", "razonSocialProveedor", "identificacionProveedor",
        "direccionProveedor", "totalSinImpuestos", "totalDescuento", "codDocReembolso", "totalComprobantesReembolso", "totalBaseImponibleReembolso","totalImpuestoReembolso", "totalConImpuestos",
        "importeTotal", "moneda", "pagos"})
    public static class InfoLiquidacionCompra {

        @XmlElement(required = true)
        protected String fechaEmision;
        @XmlElement(required = true)
        protected String dirEstablecimiento;
        protected String contribuyenteEspecial;
        protected String obligadoContabilidad;
        protected String tipoIdentificacionProveedor;
        @XmlElement(required = true)
        protected String razonSocialProveedor;
        @XmlElement(required = true)
        protected String identificacionProveedor;
        protected String direccionProveedor;
        @XmlElement(required = true)
        protected BigDecimal totalSinImpuestos;
        @XmlElement(required = true)
        protected BigDecimal totalDescuento;
        protected String codDocReembolso;
        protected BigDecimal totalComprobantesReembolso;
        protected BigDecimal totalBaseImponibleReembolso;
        protected BigDecimal totalImpuestoReembolso;
        @XmlElement(required = true)
        protected TotalConImpuestos totalConImpuestos;
        @XmlElement(required = true)
        protected BigDecimal importeTotal;
        @XmlElement(required = true)
        protected String moneda;
        @XmlElement(required = true)
        protected Pagos pagos;

        public String getFechaEmision() {
            return fechaEmision;
        }

        public void setFechaEmision(String fechaEmision) {
            this.fechaEmision = fechaEmision;
        }

        public String getDirEstablecimiento() {
            return dirEstablecimiento;
        }

        public void setDirEstablecimiento(String dirEstablecimiento) {
            this.dirEstablecimiento = dirEstablecimiento;
        }

        public String getContribuyenteEspecial() {
            return contribuyenteEspecial;
        }

        public void setContribuyenteEspecial(String contribuyenteEspecial) {
            this.contribuyenteEspecial = contribuyenteEspecial;
        }

        public String getObligadoContabilidad() {
            return obligadoContabilidad;
        }

        public void setObligadoContabilidad(String obligadoContabilidad) {
            this.obligadoContabilidad = obligadoContabilidad;
        }

        public String getTipoIdentificacionProveedor() {
            return tipoIdentificacionProveedor;
        }

        public void setTipoIdentificacionProveedor(String tipoIdentificacionProveedor) {
            this.tipoIdentificacionProveedor = tipoIdentificacionProveedor;
        }

        public String getRazonSocialProveedor() {
            return razonSocialProveedor;
        }

        public void setRazonSocialProveedor(String razonSocialProveedor) {
            this.razonSocialProveedor = razonSocialProveedor;
        }

        public String getIdentificacionProveedor() {
            return identificacionProveedor;
        }

        public void setIdentificacionProveedor(String identificacionProveedor) {
            this.identificacionProveedor = identificacionProveedor;
        }

        public String getDireccionProveedor() {
            return direccionProveedor;
        }

        public void setDireccionProveedor(String direccionProveedor) {
            this.direccionProveedor = direccionProveedor;
        }

        public BigDecimal getTotalSinImpuestos() {
            return totalSinImpuestos;
        }

        public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
            this.totalSinImpuestos = totalSinImpuestos;
        }

        public BigDecimal getTotalDescuento() {
            return totalDescuento;
        }

        public void setTotalDescuento(BigDecimal totalDescuento) {
            this.totalDescuento = totalDescuento;
        }

        public TotalConImpuestos getTotalConImpuestos() {
            return totalConImpuestos;
        }

        public void setTotalConImpuestos(TotalConImpuestos totalConImpuestos) {
            this.totalConImpuestos = totalConImpuestos;
        }

        public BigDecimal getImporteTotal() {
            return importeTotal;
        }

        public void setImporteTotal(BigDecimal importeTotal) {
            this.importeTotal = importeTotal;
        }

        public String getMoneda() {
            return moneda;
        }

        public void setMoneda(String moneda) {
            this.moneda = moneda;
        }

        public Pagos getPagos() {
            return pagos;
        }

        public void setPagos(Pagos pagos) {
            this.pagos = pagos;
        }

        public String getCodDocReembolso() {
            return codDocReembolso;
        }

        public void setCodDocReembolso(String codDocReembolso) {
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
        public static class TotalConImpuestos {

            @XmlElement(required = true)
            protected List<TotalImpuesto> totalImpuesto;

            public List<TotalImpuesto> getTotalImpuesto() {
                if (this.totalImpuesto == null) {
                    this.totalImpuesto = new ArrayList();
                }
                return this.totalImpuesto;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"codigo", "codigoPorcentaje", "descuentoAdicional", "baseImponible", "tarifa", "valor"})
            public static class TotalImpuesto {

                @XmlElement(required = true)
                protected String codigo;
                @XmlElement(required = true)
                protected String codigoPorcentaje;
                protected BigDecimal descuentoAdicional;
                @XmlElement(required = true)
                protected BigDecimal baseImponible;
                @XmlElement(required = true)
                protected BigDecimal tarifa;
                @XmlElement(required = true)
                protected BigDecimal valor;

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

                public BigDecimal getDescuentoAdicional() {
                    return descuentoAdicional;
                }

                public void setDescuentoAdicional(BigDecimal descuentoAdicional) {
                    this.descuentoAdicional = descuentoAdicional;
                }

                public BigDecimal getBaseImponible() {
                    return baseImponible;
                }

                public void setBaseImponible(BigDecimal baseImponible) {
                    this.baseImponible = baseImponible;
                }

                public BigDecimal getTarifa() {
                    return tarifa;
                }

                public void setTarifa(BigDecimal tarifa) {
                    this.tarifa = tarifa;
                }

                public BigDecimal getValor() {
                    return valor;
                }

                public void setValor(BigDecimal valor) {
                    this.valor = valor;
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
            @XmlType(name = "", propOrder = {"formaPago", "total", "plazo", "unidadTiempo"})
            public static class Pago {

                @XmlElement(required = true)
                protected String formaPago;
                @XmlElement(required = true)
                protected BigDecimal total;
                @XmlElement(required = true)
                protected BigDecimal plazo;
                protected String unidadTiempo;

                public String getFormaPago() {
                    return formaPago;
                }

                public void setFormaPago(String formaPago) {
                    this.formaPago = formaPago;
                }

                public BigDecimal getTotal() {
                    return total;
                }

                public void setTotal(BigDecimal total) {
                    this.total = total;
                }

                public BigDecimal getPlazo() {
                    return plazo;
                }

                public void setPlazo(BigDecimal plazo) {
                    this.plazo = plazo;
                }

                public String getUnidadTiempo() {
                    return unidadTiempo;
                }

                public void setUnidadTiempo(String unidadTiempo) {
                    this.unidadTiempo = unidadTiempo;
                }

            }
        }
    }

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
        @XmlType(name = "", propOrder = {"codigoPrincipal", "codigoAuxiliar", "descripcion", "unidadMedida", "cantidad",
            "precioUnitario", "descuento", "precioTotalSinImpuesto", "detallesAdicionales", "impuestos"})
        public static class Detalle {

            @XmlElement(required = true)
            protected String codigoPrincipal;
            protected String codigoAuxiliar;
            @XmlElement(required = true)
            protected String descripcion;
            protected String unidadMedida;
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
                return codigoPrincipal;
            }

            public void setCodigoPrincipal(String codigoPrincipal) {
                this.codigoPrincipal = codigoPrincipal;
            }

            public String getCodigoAuxiliar() {
                return codigoAuxiliar;
            }

            public void setCodigoAuxiliar(String codigoAuxiliar) {
                this.codigoAuxiliar = codigoAuxiliar;
            }

            public String getDescripcion() {
                return descripcion;
            }

            public void setDescripcion(String descripcion) {
                this.descripcion = descripcion;
            }

            public String getUnidadMedida() {
                return unidadMedida;
            }

            public void setUnidadMedida(String unidadMedida) {
                this.unidadMedida = unidadMedida;
            }

            public BigDecimal getCantidad() {
                return cantidad;
            }

            public void setCantidad(BigDecimal cantidad) {
                this.cantidad = cantidad;
            }

            public BigDecimal getPrecioUnitario() {
                return precioUnitario;
            }

            public void setPrecioUnitario(BigDecimal precioUnitario) {
                this.precioUnitario = precioUnitario;
            }

            public BigDecimal getDescuento() {
                return descuento;
            }

            public void setDescuento(BigDecimal descuento) {
                this.descuento = descuento;
            }

            public BigDecimal getPrecioTotalSinImpuesto() {
                return precioTotalSinImpuesto;
            }

            public void setPrecioTotalSinImpuesto(BigDecimal precioTotalSinImpuesto) {
                this.precioTotalSinImpuesto = precioTotalSinImpuesto;
            }

            public DetallesAdicionales getDetallesAdicionales() {
                return detallesAdicionales;
            }

            public void setDetallesAdicionales(DetallesAdicionales detallesAdicionales) {
                this.detallesAdicionales = detallesAdicionales;
            }

            public Impuestos getImpuestos() {
                return impuestos;
            }

            public void setImpuestos(Impuestos impuestos) {
                this.impuestos = impuestos;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"impuesto"})
            public static class Impuestos {

                @XmlElement(required = true)
                protected List<ImpuestoLiquidacion> impuesto;

                public List<ImpuestoLiquidacion> getImpuesto() {
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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"reembolsoDetalle"})
    public static class Reembolsos {

        @XmlElement(required = true)
        protected List<reembolsoDetalle> reembolsoDetalle;

        public List<reembolsoDetalle> getReembolsoDetalle() {
            if (this.reembolsoDetalle == null) {
                this.reembolsoDetalle = new ArrayList();
            }
            return this.reembolsoDetalle;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"tipoIdentificacionProveedorReembolso", "identificacionProveedorReembolso", "codPaisPagoProveedorReembolso", "tipoProveedorReembolso",
            "codDocReembolso", "estabDocReembolso", "ptoEmiDocReembolso", "secuencialDocReembolso", "fechaEmisionDocReembolso", "numeroautorizacionDocReemb", "detalleImpuestos"})
        public static class reembolsoDetalle {

            @XmlElement(required = true)
            protected String tipoIdentificacionProveedorReembolso;
            protected String identificacionProveedorReembolso;
            @XmlElement(required = true)
            protected String codPaisPagoProveedorReembolso;
            @XmlElement(required = true)
            protected String tipoProveedorReembolso;
            @XmlElement(required = true)
            protected String codDocReembolso;
            @XmlElement(required = true)
            protected String estabDocReembolso;
            @XmlElement(required = true)
            protected String ptoEmiDocReembolso;
            @XmlElement(required = true)
            protected String secuencialDocReembolso;
            @XmlElement(required = true)
            protected String fechaEmisionDocReembolso;
            @XmlElement(required = true)
            protected String numeroautorizacionDocReemb;
            @XmlElement(required = true)
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
                protected List<ImpuestoLiquidacion> detalleImpuesto;

                public List<ImpuestoLiquidacion> getDetalleImpuesto() {
                    if (this.detalleImpuesto == null) {
                        this.detalleImpuesto = new ArrayList();
                    }
                    return this.detalleImpuesto;
                }

            }

        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"marca", "modelo", "serie"})
    public static class MaquinaFiscal {

        protected String marca;
        protected String modelo;
        protected String serie;

        public String getMarca() {
            return marca;
        }

        public void setMarca(String marca) {
            this.marca = marca;
        }

        public String getModelo() {
            return modelo;
        }

        public void setModelo(String modelo) {
            this.modelo = modelo;
        }

        public String getSerie() {
            return serie;
        }

        public void setSerie(String serie) {
            this.serie = serie;
        }

    }

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

            public String getNombre() {
                return this.nombre;
            }

            public void setNombre(String value) {
                this.nombre = value;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

        }
    }

}
