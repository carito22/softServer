package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.factura;

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

import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.InfoTributaria;

/**
 *
 * @author Ing. Carlos Ajila
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"infoTributaria", "infoFactura", "detalles", "infoAdicional", "infoSustitutivaGuiaRemision"})
@XmlRootElement(name = "factura")
public class Factura {

    @XmlElement(required = true)
    protected InfoTributaria infoTributaria;

    @XmlElement(required = true)
    protected InfoFactura infoFactura;

    @XmlElement(required = true)
    public Detalles detalles;
    protected InfoAdicional infoAdicional;
    protected InfoSustitutivaGuiaRemision infoSustitutivaGuiaRemision;
    @XmlAttribute
    protected String id;

    @XmlAttribute
    @XmlSchemaType(name = "anySimpleType")
    protected String version;

    public InfoTributaria getInfoTributaria() {
        return this.infoTributaria;
    }

    public void setInfoTributaria(InfoTributaria value) {
        this.infoTributaria = value;
    }

    public InfoFactura getInfoFactura() {
        return this.infoFactura;
    }

    public void setInfoFactura(InfoFactura value) {
        this.infoFactura = value;
    }

    public Detalles getDetalles() {
        return this.detalles;
    }

    public void setDetalles(Detalles value) {
        this.detalles = value;
    }

    public InfoAdicional getInfoAdicional() {
        return this.infoAdicional;
    }

    public void setInfoAdicional(InfoAdicional value) {
        this.infoAdicional = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public InfoSustitutivaGuiaRemision getInfoSustitutivaGuiaRemision() {
        return infoSustitutivaGuiaRemision;
    }

    public void setInfoSustitutivaGuiaRemision(InfoSustitutivaGuiaRemision infoSustitutivaGuiaRemision) {
        this.infoSustitutivaGuiaRemision = infoSustitutivaGuiaRemision;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"fechaEmision", "dirEstablecimiento", "contribuyenteEspecial",
        "obligadoContabilidad", "comercioExterior", "incoTermFactura", "lugarIncoTerm", "paisOrigen", "puertoEmbarque",
        "puertoDestino", "paisDestino", "paisAdquisicion",
        "tipoIdentificacionComprador", "guiaRemision", "razonSocialComprador",
        "identificacionComprador", "totalSinImpuestos", "incoTermTotalSinImpuestos", "totalDescuento", "totalConImpuestos", "propina",
        "fleteInternacional", "seguroInternacional", "gastosAduaneros", "gastosTransporteOtros",
        "importeTotal", "moneda", "pagos"})
    public static class InfoFactura {

        @XmlElement(required = true)
        protected String fechaEmision;

        @XmlElement(required = true)
        protected String dirEstablecimiento;
        protected String contribuyenteEspecial;
        protected String obligadoContabilidad;

        //exportacion
        protected String comercioExterior;//OBL
        protected String incoTermFactura;//OBL
        protected String lugarIncoTerm;//OBL
        protected Integer paisOrigen;//OBL
        protected String puertoEmbarque;//OBL
        protected String puertoDestino;//OBL
        protected Integer paisDestino;
        protected Integer paisAdquisicion;
        //
        @XmlElement(required = true)
        protected String tipoIdentificacionComprador;
        protected String guiaRemision;

        @XmlElement(required = true)
        protected String razonSocialComprador;

        @XmlElement(required = true)
        protected String identificacionComprador;

        @XmlElement(required = true)
        protected BigDecimal totalSinImpuestos;
        //exportacion
        protected String incoTermTotalSinImpuestos;//OBL
        //
        @XmlElement(required = true)
        protected BigDecimal totalDescuento;

        @XmlElement(required = true)
        protected TotalConImpuestos totalConImpuestos;

        @XmlElement(required = true)
        protected Pagos pagos;

        @XmlElement(required = true)
        protected BigDecimal propina;
        //exportacion
        protected BigDecimal fleteInternacional;
        protected BigDecimal seguroInternacional;
        protected BigDecimal gastosAduaneros;
        protected BigDecimal gastosTransporteOtros;
        //

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

        public TotalConImpuestos getTotalConImpuestos() {
            return this.totalConImpuestos;
        }

        public void setTotalConImpuestos(TotalConImpuestos value) {
            this.totalConImpuestos = value;
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

        public String getComercioExterior() {
            return comercioExterior;
        }

        public void setComercioExterior(String comercioExterior) {
            this.comercioExterior = comercioExterior;
        }

        public String getIncoTermFactura() {
            return incoTermFactura;
        }

        public void setIncoTermFactura(String incoTermFactura) {
            this.incoTermFactura = incoTermFactura;
        }

        public String getLugarIncoTerm() {
            return lugarIncoTerm;
        }

        public void setLugarIncoTerm(String lugarIncoTerm) {
            this.lugarIncoTerm = lugarIncoTerm;
        }

        public Integer getPaisOrigen() {
            return paisOrigen;
        }

        public void setPaisOrigen(Integer paisOrigen) {
            this.paisOrigen = paisOrigen;
        }

        public String getPuertoEmbarque() {
            return puertoEmbarque;
        }

        public void setPuertoEmbarque(String puertoEmbarque) {
            this.puertoEmbarque = puertoEmbarque;
        }

        public String getPuertoDestino() {
            return puertoDestino;
        }

        public void setPuertoDestino(String puertoDestino) {
            this.puertoDestino = puertoDestino;
        }

        public Integer getPaisDestino() {
            return paisDestino;
        }

        public void setPaisDestino(Integer paisDestino) {
            this.paisDestino = paisDestino;
        }

        public Integer getPaisAdquisicion() {
            return paisAdquisicion;
        }

        public void setPaisAdquisicion(Integer paisAdquisicion) {
            this.paisAdquisicion = paisAdquisicion;
        }

        public String getIncoTermTotalSinImpuestos() {
            return incoTermTotalSinImpuestos;
        }

        public void setIncoTermTotalSinImpuestos(String incoTermTotalSinImpuestos) {
            this.incoTermTotalSinImpuestos = incoTermTotalSinImpuestos;
        }

        public BigDecimal getFleteInternacional() {
            return fleteInternacional;
        }

        public void setFleteInternacional(BigDecimal fleteInternacional) {
            this.fleteInternacional = fleteInternacional;
        }

        public BigDecimal getSeguroInternacional() {
            return seguroInternacional;
        }

        public void setSeguroInternacional(BigDecimal seguroInternacional) {
            this.seguroInternacional = seguroInternacional;
        }

        public BigDecimal getGastosAduaneros() {
            return gastosAduaneros;
        }

        public void setGastosAduaneros(BigDecimal gastosAduaneros) {
            this.gastosAduaneros = gastosAduaneros;
        }

        public BigDecimal getGastosTransporteOtros() {
            return gastosTransporteOtros;
        }

        public void setGastosTransporteOtros(BigDecimal gastosTransporteOtros) {
            this.gastosTransporteOtros = gastosTransporteOtros;
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
            @XmlType(name = "", propOrder = {"codigo", "codigoPorcentaje", "baseImponible", "tarifa", "valor"})
            public static class TotalImpuesto {

                @XmlElement(required = true)
                protected String codigo;

                @XmlElement(required = true)
                protected String codigoPorcentaje;

                @XmlElement(required = true)
                protected BigDecimal baseImponible;
                protected BigDecimal tarifa;

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

                public BigDecimal getTarifa() {
                    return this.tarifa;
                }

                public void setTarifa(BigDecimal value) {
                    this.tarifa = value;
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
        @XmlType(name = "", propOrder = {"codigoPrincipal", "codigoAuxiliar", "descripcion", "cantidad",
            "precioUnitario", "descuento", "precioTotalSinImpuesto", "detallesAdicionales", "impuestos"})
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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"dirPartida", "dirDestinatario", "fechaIniTransporte",
        "fechaFinTransporte", "razonSocialTransportista", "tipoIdentificacionTransportista", "rucTransportista",
        "placa", "destinos"})
    public static class InfoSustitutivaGuiaRemision {

        @XmlElement(required = true)
        protected String dirPartida;

        @XmlElement(required = true)
        protected String dirDestinatario;
        protected String fechaIniTransporte;
        protected String fechaFinTransporte;
        protected String razonSocialTransportista;
        protected String tipoIdentificacionTransportista;
        protected String rucTransportista;
        protected String placa;
        protected Destinos destinos;

        public String getDirPartida() {
            return dirPartida;
        }

        public void setDirPartida(String dirPartida) {
            this.dirPartida = dirPartida;
        }

        public String getDirDestinatario() {
            return dirDestinatario;
        }

        public void setDirDestinatario(String dirDestinatario) {
            this.dirDestinatario = dirDestinatario;
        }

        public String getFechaIniTransporte() {
            return fechaIniTransporte;
        }

        public void setFechaIniTransporte(String fechaIniTransporte) {
            this.fechaIniTransporte = fechaIniTransporte;
        }

        public String getFechaFinTransporte() {
            return fechaFinTransporte;
        }

        public void setFechaFinTransporte(String fechaFinTransporte) {
            this.fechaFinTransporte = fechaFinTransporte;
        }

        public String getRazonSocialTransportista() {
            return razonSocialTransportista;
        }

        public void setRazonSocialTransportista(String razonSocialTransportista) {
            this.razonSocialTransportista = razonSocialTransportista;
        }

        public String getTipoIdentificacionTransportista() {
            return tipoIdentificacionTransportista;
        }

        public void setTipoIdentificacionTransportista(String tipoIdentificacionTransportista) {
            this.tipoIdentificacionTransportista = tipoIdentificacionTransportista;
        }

        public String getRucTransportista() {
            return rucTransportista;
        }

        public void setRucTransportista(String rucTransportista) {
            this.rucTransportista = rucTransportista;
        }

        public String getPlaca() {
            return placa;
        }

        public void setPlaca(String placa) {
            this.placa = placa;
        }

        public Destinos getDestinos() {
            return destinos;
        }

        public void setDestinos(Destinos destinos) {
            this.destinos = destinos;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"destino"})
        public static class Destinos {

            @XmlElement(required = true)
            protected List<Destino> destino;

            public List<Destino> getDestino() {
                if (this.destino == null) {
                    this.destino = new ArrayList();
                }
                return this.destino;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"motivoTraslado", "codEstabDestino", "docAduaneroUnico", "ruta"})
            public static class Destino {

                protected String motivoTraslado;
                protected String codEstabDestino;
                protected String docAduaneroUnico;
                protected String ruta;

                public String getMotivoTraslado() {
                    return motivoTraslado;
                }

                public void setMotivoTraslado(String motivoTraslado) {
                    this.motivoTraslado = motivoTraslado;
                }

                public String getCodEstabDestino() {
                    return codEstabDestino;
                }

                public void setCodEstabDestino(String codEstabDestino) {
                    this.codEstabDestino = codEstabDestino;
                }

                public String getRuta() {
                    return ruta;
                }

                public void setRuta(String ruta) {
                    this.ruta = ruta;
                }

                public String getDocAduaneroUnico() {
                    return docAduaneroUnico;
                }

                public void setDocAduaneroUnico(String docAduaneroUnico) {
                    this.docAduaneroUnico = docAduaneroUnico;
                }

            }
        }
    }

}
