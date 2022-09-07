/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.modelo.guiaremision;

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
@XmlType(name = "", propOrder = {"infoTributaria", "infoGuiaRemision", "destinatarios", "infoAdicional"})
@XmlRootElement(name = "guiaRemision")
public class GuiaRemision {

    @XmlElement(required = true)
    protected InfoTributaria infoTributaria;
    @XmlElement(required = true)
    protected InfoGuiaRemision infoGuiaRemision;
    @XmlElement(required = true)
    protected Destinatarios destinatarios;
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

    public InfoGuiaRemision getInfoGuiaRemision() {
        return infoGuiaRemision;
    }

    public void setInfoGuiaRemision(InfoGuiaRemision infoGuiaRemision) {
        this.infoGuiaRemision = infoGuiaRemision;
    }

    public Destinatarios getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(Destinatarios destinatarios) {
        this.destinatarios = destinatarios;
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
    @XmlType(name = "", propOrder = {"dirEstablecimiento", "dirPartida", "razonSocialTransportista", "tipoIdentificacionTransportista",
        "rucTransportista", "rise", "obligadoContabilidad", "contribuyenteEspecial",
        "fechaIniTransporte", "fechaFinTransporte", "placa"})
    public static class InfoGuiaRemision {

        @XmlElement(required = true)
        protected String dirEstablecimiento;
        @XmlElement(required = true)
        protected String dirPartida;
        @XmlElement(required = true)
        protected String razonSocialTransportista;
        @XmlElement(required = true)
        protected String tipoIdentificacionTransportista;
        @XmlElement(required = true)
        protected String rucTransportista;
        protected String rise;
        protected String obligadoContabilidad;
        protected String contribuyenteEspecial;
        @XmlElement(required = true)
        protected String fechaIniTransporte;
        @XmlElement(required = true)
        protected String fechaFinTransporte;
        @XmlElement(required = true)
        protected String placa;

        public String getDirEstablecimiento() {
            return dirEstablecimiento;
        }

        public void setDirEstablecimiento(String dirEstablecimiento) {
            this.dirEstablecimiento = dirEstablecimiento;
        }

        public String getDirPartida() {
            return dirPartida;
        }

        public void setDirPartida(String dirPartida) {
            this.dirPartida = dirPartida;
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

        public String getRise() {
            return rise;
        }

        public void setRise(String rise) {
            this.rise = rise;
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

        public String getPlaca() {
            return placa;
        }

        public void setPlaca(String placa) {
            this.placa = placa;
        }

        public String getRazonSocialTransportista() {
            return razonSocialTransportista;
        }

        public void setRazonSocialTransportista(String razonSocialTransportista) {
            this.razonSocialTransportista = razonSocialTransportista;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"destinatario"})
    public static class Destinatarios {

        @XmlElement(required = true)
        protected List<Destinatario> destinatario;

        public List<Destinatario> getDestinario() {
            if (this.destinatario == null) {
                this.destinatario = new ArrayList();
            }
            return this.destinatario;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"identificacionDestinatario", "razonSocialDestinatario", "dirDestinatario", "motivoTraslado",
            "docAduaneroUnico", "codEstabDestino", "ruta", "codDocSustento", "numDocSustento", "numAutDocSustento", "fechaEmisionDocSustento", "detalles"})
        public static class Destinatario {

            @XmlElement(required = true)
            protected String identificacionDestinatario;
            @XmlElement(required = true)
            protected String razonSocialDestinatario;
            @XmlElement(required = true)
            protected String dirDestinatario;
            @XmlElement(required = true)
            protected String motivoTraslado;
            protected String docAduaneroUnico;
            protected String codEstabDestino;
            protected String ruta;
            protected String codDocSustento;
            protected String numDocSustento;
            protected String numAutDocSustento;
            protected String fechaEmisionDocSustento;
            @XmlElement(required = true)
            protected Detalles detalles;

            public String getIdentificacionDestinatario() {
                return identificacionDestinatario;
            }

            public void setIdentificacionDestinatario(String identificacionDestinatario) {
                this.identificacionDestinatario = identificacionDestinatario;
            }

            public String getRazonSocialDestinatario() {
                return razonSocialDestinatario;
            }

            public void setRazonSocialDestinatario(String razonSocialDestinatario) {
                this.razonSocialDestinatario = razonSocialDestinatario;
            }

            public String getDirDestinatario() {
                return dirDestinatario;
            }

            public void setDirDestinatario(String dirDestinatario) {
                this.dirDestinatario = dirDestinatario;
            }

            public String getMotivoTraslado() {
                return motivoTraslado;
            }

            public void setMotivoTraslado(String motivoTraslado) {
                this.motivoTraslado = motivoTraslado;
            }

            public String getDocAduaneroUnico() {
                return docAduaneroUnico;
            }

            public void setDocAduaneroUnico(String docAduaneroUnico) {
                this.docAduaneroUnico = docAduaneroUnico;
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

            public String getCodDocSustento() {
                return codDocSustento;
            }

            public void setCodDocSustento(String codDocSustento) {
                this.codDocSustento = codDocSustento;
            }

            public String getNumDocSustento() {
                return numDocSustento;
            }

            public void setNumDocSustento(String numDocSustento) {
                this.numDocSustento = numDocSustento;
            }

            public String getNumAutDocSustento() {
                return numAutDocSustento;
            }

            public void setNumAutDocSustento(String numAutDocSustento) {
                this.numAutDocSustento = numAutDocSustento;
            }

            public String getFechaEmisionDocSustento() {
                return fechaEmisionDocSustento;
            }

            public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
                this.fechaEmisionDocSustento = fechaEmisionDocSustento;
            }

            public Detalles getDetalles() {
                return detalles;
            }

            public void setDetalles(Detalles detalles) {
                this.detalles = detalles;
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
                @XmlType(name = "", propOrder = {"codigoInterno", "codigoAdicional", "descripcion", "cantidad", "detallesAdicionales"})
                public static class Detalle {

                    @XmlElement(required = true)
                    protected String codigoInterno;
                    protected String codigoAdicional;
                    @XmlElement(required = true)
                    protected String descripcion;
                    @XmlElement(required = true)
                    protected BigDecimal cantidad;
                    protected DetallesAdicionales detallesAdicionales;

                    public String getCodigoInterno() {
                        return codigoInterno;
                    }

                    public void setCodigoInterno(String codigoInterno) {
                        this.codigoInterno = codigoInterno;
                    }

                    public String getCodigoAdicional() {
                        return codigoAdicional;
                    }

                    public void setCodigoAdicional(String codigoAdicional) {
                        this.codigoAdicional = codigoAdicional;
                    }

                    public String getDescripcion() {
                        return descripcion;
                    }

                    public void setDescripcion(String descripcion) {
                        this.descripcion = descripcion;
                    }

                    public BigDecimal getCantidad() {
                        return cantidad;
                    }

                    public void setCantidad(BigDecimal cantidad) {
                        this.cantidad = cantidad;
                    }

                    public DetallesAdicionales getDetallesAdicionales() {
                        return detallesAdicionales;
                    }

                    public void setDetallesAdicionales(DetallesAdicionales detallesAdicionales) {
                        this.detallesAdicionales = detallesAdicionales;
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
