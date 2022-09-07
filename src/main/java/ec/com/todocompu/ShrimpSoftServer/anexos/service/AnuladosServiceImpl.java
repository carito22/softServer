package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnexoNumeracionDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnuladosDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraElectronicaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoComprobanteDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaComprobanteAnuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxAnulados;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipocomprobante;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaComprasFacturasPorNumeroTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.TipoAmbienteEnum;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnuladosServiceImpl implements AnuladosService {

    @Autowired
    private AnuladosDao anuladosDao;
    @Autowired
    private CompraDao compraDao;
    @Autowired
    private CompraElectronicaDao compraElectronicaDao;
    @Autowired
    private AnexoNumeracionDao numeracionDao;
    @Autowired
    private TipoComprobanteDao tipoComprobanteDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private UrlWebServicesService urlWebServicesService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;

    @Override
    public List<AnxListaComprobanteAnuladoTO> getAnxListaComprobanteAnuladoTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        return anuladosDao.getAnxListaComprobanteAnuladoTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AnxAnuladoTablaTO> getListaAnxAnuladoTablaTO(String empresa) throws Exception {
        return anuladosDao.getListaAnxAnuladoTablaTO(empresa);
    }

    @Override
    public AnxAnuladosTO getAnxAnuladosTO(Integer secuencial) throws Exception {
        return anuladosDao.getAnxAnuladosTO(secuencial);
    }

    @Override
    public String insertarAnexoAnuladoTO(AnxAnuladosTO anxAnuladosTO, boolean validarRetencionElectronica, SisInfoTO sisInfoTO) throws Exception {
        String mensajeError = "";
        String comprobarEstadoPeriodo = periodoService.estadoPeriodo(sisInfoTO.getEmpresa(), anxAnuladosTO.getAnuFecha());
        if (!comprobarEstadoPeriodo.isEmpty()) {
            mensajeError = "F" + comprobarEstadoPeriodo;
        }
        Boolean autorizacionOcupada;
        Boolean esRetencionElectronica = anxAnuladosTO.getTcCodigo().trim().compareToIgnoreCase("07") == 0
                && (anxAnuladosTO.getAnuAutorizacion().trim().length() == 37 || anxAnuladosTO.getAnuAutorizacion().trim().length() == 49)
                && (anxAnuladosTO.getAnuSecuencialInicio().trim().compareToIgnoreCase(anxAnuladosTO.getAnuSecuencialFin().trim())) == 0;

        if (!esRetencionElectronica) {
            autorizacionOcupada = compraDao.getComprobanteVerificarAutorizacion(
                    anxAnuladosTO.getUsrEmpresa(), anxAnuladosTO.getTcCodigo(),
                    anxAnuladosTO.getAnuComprobanteEstablecimiento(), anxAnuladosTO.getAnuComprobantePuntoEmision(),
                    anxAnuladosTO.getAnuSecuencialInicio(), anxAnuladosTO.getAnuSecuencialFin());
            if (autorizacionOcupada) {
                mensajeError += "FLa NUMERACIÓN que esta eliminando se encuentra usada en una COMPRA o VENTA.";
            }
        } else {
            if (validarRetencionElectronica) {
                try {
                    RespuestaComprobante respuestaComprobanteRetencion = urlWebServicesService.getAutorizadocionComprobantes(
                            anxAnuladosTO.getAnuAutorizacion(),
                            TipoAmbienteEnum.PRODUCCION.getCode(), sisInfoTO);
                    if (respuestaComprobanteRetencion != null) {
                        if (!respuestaComprobanteRetencion.getAutorizaciones().getAutorizacion().isEmpty()) {
                            mensajeError += "FLa retención no se encuentra anulada en el SRI";

                        } else {
                            //actualizar fecha anxCompra
                            anxAnuladosTO.setActualizarFechaSri(true);
                        }
                    } else {
                        //actualizar fecha anxCompra
                        anxAnuladosTO.setActualizarFechaSri(true);
                    }
                } catch (Exception e) {
                    mensajeError += "FLa retención no se encuentra anulada en el SRI";
                }
            }
        }

        boolean sePuedeAnular = true;
        List<AnxAnuladoTablaTO> listaAnuladoTablaTO = anuladosDao.getListaAnxAnuladoTablaTO(anxAnuladosTO.getUsrEmpresa());
        if (listaAnuladoTablaTO != null && listaAnuladoTablaTO.size() > 0) {
            for (AnxAnuladoTablaTO aatto : listaAnuladoTablaTO) {
                if (aatto.getAnuComprobanteTipo().equals(anxAnuladosTO.getTcCodigo())) {
                    if (aatto.getAnuComprobanteEstablecimiento().equals(anxAnuladosTO.getAnuComprobanteEstablecimiento()) && aatto.getAnuComprobantePuntoEmision().equals(anxAnuladosTO.getAnuComprobantePuntoEmision())) {
                        if (Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) >= Integer.parseInt(aatto.getAnuSecuencialInicio()) && Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) <= Integer.parseInt(aatto.getAnuSecuencialFin())) {
                            sePuedeAnular = false;
                            break;
                        } else if (Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) >= Integer.parseInt(aatto.getAnuSecuencialInicio()) && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) <= Integer.parseInt(aatto.getAnuSecuencialFin())) {
                            sePuedeAnular = false;
                            break;
                        }
                    }
                }
            }
            if (!sePuedeAnular) {
                mensajeError += "FTodo o parte de la numeración ya ha sido anulado";
            }
            List<AnxNumeracionTablaTO> listaAnxNumeracionTablaTO = numeracionDao.getListaAnexoNumeracionTO(anxAnuladosTO.getUsrEmpresa());
            boolean existeNumeracion = false;
            if (listaAnxNumeracionTablaTO != null && listaAnxNumeracionTablaTO.size() > 0) {
                for (AnxNumeracionTablaTO antto : listaAnxNumeracionTablaTO) {
                    if (antto.getNumeroComprobante().equals(anxAnuladosTO.getTcCodigo())) {
                        if (antto.getNumeroDesde().substring(0, antto.getNumeroDesde().lastIndexOf("-")).trim().equals(anxAnuladosTO.getAnuComprobanteEstablecimiento() + "-" + anxAnuladosTO.getAnuComprobantePuntoEmision())) {
                            if (Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) >= Integer.parseInt(antto.getNumeroDesde().substring(antto.getNumeroDesde().lastIndexOf("-") + 1).trim())
                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) <= Integer.parseInt(antto.getNumeroHasta().substring(antto.getNumeroHasta().lastIndexOf("-") + 1).trim())
                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) >= Integer.parseInt(antto.getNumeroDesde().substring(antto.getNumeroDesde().lastIndexOf("-") + 1).trim())
                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) <= Integer.parseInt(antto.getNumeroHasta().substring(antto.getNumeroHasta().lastIndexOf("-") + 1).trim())) {
                                existeNumeracion = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!existeNumeracion) {
                mensajeError += "FNo existe la Numeración que quiere anular o la Autorizacion esta erronea.\nIngrésela mediante el formulario de numeración en el módulo Anexos.";
            }
        }
        if (!mensajeError.isEmpty()) {
            return mensajeError;
        } else {
            return insertarAnexoAnulado(anxAnuladosTO, sisInfoTO);
        }
    }

    private String insertarAnexoAnulado(AnxAnuladosTO anxAnuladosTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susClave = anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
        susDetalle = "Se anuló registro con el código " + anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
        susSuceso = "INSERT";
        susTabla = "anexos.anx_anulados";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        anxAnuladosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        Boolean esRetencionElectronica = anxAnuladosTO.getTcCodigo().compareToIgnoreCase("07") == 0
                && (anxAnuladosTO.getAnuAutorizacion().length() == 37 || anxAnuladosTO.getAnuAutorizacion().length() == 49)
                && (anxAnuladosTO.getAnuSecuencialInicio().compareToIgnoreCase(anxAnuladosTO.getAnuSecuencialFin()) == 0);
        if (esRetencionElectronica) {
            InvConsultaComprasFacturasPorNumeroTO retencionPK = compraElectronicaDao.getConsultaPKRetencion(anxAnuladosTO.getUsrEmpresa(), anxAnuladosTO.getAnuComprobanteEstablecimiento() + anxAnuladosTO.getAnuComprobantePuntoEmision() + anxAnuladosTO.getAnuSecuencialInicio(), anxAnuladosTO.getAnuAutorizacion());
            if (retencionPK != null && retencionPK.getCompEmpresa() != null && retencionPK.getCompNumero() != null) {
                AnxCompraElectronica anxCompraElectronica = compraElectronicaDao.buscarAnxCompraElectronica(retencionPK.getCompEmpresa(), retencionPK.getCompPeriodo(), retencionPK.getCompMotivo(), retencionPK.getCompNumero());
                AnxCompra anxCompra = anxCompraElectronica.getAnxCompra();
                anxCompra.setCompRetencionAutorizacion("ANULADA");
                if (anxAnuladosTO.isActualizarFechaSri()) {
                    anxCompra.setFechaUltimaValidacionSri(sistemaWebServicio.getFechaActual());
                }
                compraElectronicaDao.accionAnxCompraElectronica(anxCompraElectronica, anxCompra, sisSuceso, 'E');
            }
        }
        AnxAnulados anxAnulados = ConversionesAnexos.convertirAnxAnuladosTO_AnxAnulados(anxAnuladosTO);

        anxAnulados.setTcCodigo(tipoComprobanteDao.obtenerPorId(AnxTipocomprobante.class, anxAnuladosTO.getTcCodigo()));
        if (anuladosDao.insertarAnexoAnulados(anxAnulados, sisSuceso)) {
            retorno = "TLa anulación se guardó correctamente...";
        } else {
            retorno = "FHubo un error al guardar la anulación...\nIntente de nuevo o contacte con el administrador.";
        }
        return retorno;
    }

    @Override
    public String modificarAnexoAnuladoTO(AnxAnuladosTO anxAnuladosTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean sePuedeConsultar = false;
        String estadoPeriodo = periodoService.estadoPeriodo(anxAnuladosTO.getUsrEmpresa(), anxAnuladosTO.getAnuFecha());
        if (estadoPeriodo.isEmpty()) {
            List<AnxAnuladoTablaTO> listaAnuladoTablaTO = anuladosDao.getListaAnxAnuladoTablaTO(anxAnuladosTO.getUsrEmpresa());
            if (listaAnuladoTablaTO != null) {
                if (listaAnuladoTablaTO.size() > 0) {
                    for (int i = 0; i < listaAnuladoTablaTO.size(); i++) {
                        if (listaAnuladoTablaTO.get(i).getAnuComprobanteEstablecimiento().equals(anxAnuladosTO.getAnuComprobanteEstablecimiento()) && listaAnuladoTablaTO.get(i).getAnuComprobantePuntoEmision().equals(anxAnuladosTO.getAnuComprobantePuntoEmision())) {
                            if (Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) >= Integer.parseInt(listaAnuladoTablaTO.get(i).getAnuSecuencialInicio())
                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) <= Integer.parseInt(listaAnuladoTablaTO.get(i).getAnuSecuencialFin())
                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) >= Integer.parseInt(listaAnuladoTablaTO.get(i).getAnuSecuencialInicio())
                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) <= Integer.parseInt(listaAnuladoTablaTO.get(i).getAnuSecuencialFin())) {
                                if (anxAnuladosTO.getAnuSecuencial().compareTo(listaAnuladoTablaTO.get(i).getAnuSecuencial()) == 0) {
                                    sePuedeConsultar = true;
                                } else {
                                    sePuedeConsultar = false;
                                    i = listaAnuladoTablaTO.size();
                                }
                            } else {
                                sePuedeConsultar = true;
                            }
                        }
                    }
                    if (sePuedeConsultar) {
                        List<AnxNumeracionTablaTO> listaAnxNumeracionTablaTO = numeracionDao.getListaAnexoNumeracionTO(anxAnuladosTO.getUsrEmpresa());
                        boolean sePuedeGuardar = false;
                        if (listaAnxNumeracionTablaTO != null) {
                            if (listaAnxNumeracionTablaTO.size() > 0) {
                                for (int i = 0; i < listaAnxNumeracionTablaTO.size(); i++) {
                                    if (listaAnxNumeracionTablaTO.get(i).getNumeroComprobante().equals(anxAnuladosTO.getTcCodigo())) {
                                        if (listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(0, listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-")).trim().equals(anxAnuladosTO.getAnuComprobanteEstablecimiento() + "-" + anxAnuladosTO.getAnuComprobantePuntoEmision())) {
                                            if (Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) >= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-") + 1).trim())
                                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) <= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().substring(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().lastIndexOf("-") + 1).trim())
                                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) >= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-") + 1).trim())
                                                    && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) <= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().substring(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().lastIndexOf("-") + 1).trim())) {
                                                sePuedeGuardar = true;
                                                i = listaAnxNumeracionTablaTO.size();
                                            } else {
                                                sePuedeGuardar = false;
                                            }
                                        }
                                    } else {
                                        sePuedeGuardar = false;
                                    }
                                }
                                if (sePuedeGuardar) {
                                    // / PREPARANDO OBJETO SISSUCESO
                                    susClave = anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
                                    susDetalle = "Se modificó la anulación con código " + anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
                                    susSuceso = "UPDATE";
                                    susTabla = "anexos.anx_anulados";
                                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                    anxAnuladosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                    AnxAnulados anxAnulados = ConversionesAnexos.convertirAnxAnuladosTO_AnxAnulados(anxAnuladosTO);
                                    anxAnulados.setTcCodigo(tipoComprobanteDao.obtenerPorId(AnxTipocomprobante.class, anxAnuladosTO.getTcCodigo()));
                                    if (anuladosDao.modificarAnexoAnulados(anxAnulados, sisSuceso)) {
                                        retorno = "TLa anulación se modificó correctamente...";
                                    } else {
                                        retorno = "FHubo un error al modificar la anulación...\nIntente de nuevo o contacte con el administrador";
                                    }
                                } else {
                                    retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                                }
                            } else {
                                retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                            }
                        } else {
                            retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                        }
                    } else {
                        retorno = "FLa numeración que quiere anular ya ha sido anulada";
                    }
                } else {
                    List<AnxNumeracionTablaTO> listaAnxNumeracionTablaTO = numeracionDao.getListaAnexoNumeracionTO(anxAnuladosTO.getUsrEmpresa());
                    boolean sePuedeGuardar = false;
                    if (listaAnxNumeracionTablaTO != null) {
                        if (listaAnxNumeracionTablaTO.size() > 0) {
                            for (int i = 0; i < listaAnxNumeracionTablaTO.size(); i++) {
                                if (listaAnxNumeracionTablaTO.get(i).getNumeroComprobante().equals(anxAnuladosTO.getTcCodigo())) {
                                    if (listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(0, listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-")).trim().equals(anxAnuladosTO.getAnuComprobanteEstablecimiento() + "-" + anxAnuladosTO.getAnuComprobantePuntoEmision())) {
                                        if (Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) >= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-") + 1).trim())
                                                && Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) <= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().substring(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().lastIndexOf("-") + 1).trim())
                                                && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) >= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-") + 1).trim())
                                                && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) <= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().substring(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().lastIndexOf("-") + 1).trim())) {
                                            sePuedeGuardar = true;
                                            i = listaAnxNumeracionTablaTO.size();
                                        } else {
                                            sePuedeGuardar = false;
                                        }
                                    }
                                } else {
                                    sePuedeGuardar = false;
                                }
                            }
                            if (sePuedeGuardar) {
                                // / PREPARANDO OBJETO SISSUCESO
                                susClave = anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
                                susDetalle = "Se modificó la anulación con código " + anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
                                susSuceso = "UPDATE";
                                susTabla = "anexos.anx_anulados";
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                anxAnuladosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                                AnxAnulados anxAnulados = ConversionesAnexos.convertirAnxAnuladosTO_AnxAnulados(anxAnuladosTO);
                                anxAnulados.setTcCodigo(tipoComprobanteDao.obtenerPorId(AnxTipocomprobante.class, anxAnuladosTO.getTcCodigo()));
                                if (anuladosDao.insertarAnexoAnulados(anxAnulados, sisSuceso)) {
                                    retorno = "TLa anulación se modificó correctamente...";
                                } else {
                                    retorno = "FHubo un error al modificar la anulación...\nIntente de nuevo o contacte con el administrador";
                                }
                            } else {
                                retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                            }
                        } else {
                            retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                        }
                    } else {
                        retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                    }
                }
            } else {
                List<AnxNumeracionTablaTO> listaAnxNumeracionTablaTO = numeracionDao.getListaAnexoNumeracionTO(anxAnuladosTO.getUsrEmpresa());
                boolean sePuedeGuardar = false;
                if (listaAnxNumeracionTablaTO != null) {
                    if (listaAnxNumeracionTablaTO.size() > 0) {
                        for (int i = 0; i < listaAnxNumeracionTablaTO.size(); i++) {
                            if (listaAnxNumeracionTablaTO.get(i).getNumeroComprobante().equals(anxAnuladosTO.getTcCodigo())) {
                                if (listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(0, listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-")).trim().equals(anxAnuladosTO.getAnuComprobanteEstablecimiento() + "-" + anxAnuladosTO.getAnuComprobantePuntoEmision())) {
                                    if (Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) >= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-") + 1).trim())
                                            && Integer.parseInt(anxAnuladosTO.getAnuSecuencialInicio()) <= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().substring(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().lastIndexOf("-") + 1).trim())
                                            && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) >= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().substring(listaAnxNumeracionTablaTO.get(i).getNumeroDesde().lastIndexOf("-") + 1).trim())
                                            && Integer.parseInt(anxAnuladosTO.getAnuSecuencialFin()) <= Integer.parseInt(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().substring(listaAnxNumeracionTablaTO.get(i).getNumeroHasta().lastIndexOf("-") + 1).trim())) {
                                        sePuedeGuardar = true;
                                        i = listaAnxNumeracionTablaTO.size();
                                    } else {
                                        sePuedeGuardar = false;
                                    }
                                }
                            } else {
                                sePuedeGuardar = false;
                            }
                        }
                        if (sePuedeGuardar) {
                            susClave = anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
                            susDetalle = "Se modificó la anulación con código " + anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
                            susSuceso = "UPDATE";
                            susTabla = "anexos.anx_anulados";
                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                            anxAnuladosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                            AnxAnulados anxAnulados = ConversionesAnexos.convertirAnxAnuladosTO_AnxAnulados(anxAnuladosTO);
                            anxAnulados.setTcCodigo(tipoComprobanteDao.obtenerPorId(AnxTipocomprobante.class, anxAnuladosTO.getTcCodigo()));
                            if (anuladosDao.modificarAnexoAnulados(anxAnulados, sisSuceso)) {
                                retorno = "TLa anulación se modificó correctamente...";
                            } else {
                                retorno = "FHubo un error al modificar la anulación...\nIntente de nuevo o contacte con el administrador";
                            }
                        } else {
                            retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                        }
                    } else {
                        retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                    }
                } else {
                    retorno = "FNo existe la numeración que quiere anular.\nIngrésela mediante el formulario de numeración en el módulo Anexos";
                }
            }
        } else {
            retorno = "F" + estadoPeriodo;
        }
        return retorno;
    }

    @Override
    public String eliminarAnexoAnuladoTO(AnxAnuladosTO anxAnuladosTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        String estadoPeriodo = periodoService.estadoPeriodo(anxAnuladosTO.getUsrEmpresa(), anxAnuladosTO.getAnuFecha());
        if (estadoPeriodo.isEmpty()) {
            susClave = anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
            susDetalle = "Se eliminó la anulación con código " + anxAnuladosTO.getTcCodigo() + " | " + anxAnuladosTO.getAnuSecuencialInicio() + " | " + anxAnuladosTO.getAnuSecuencialFin();
            susSuceso = "DELETE";
            susTabla = "anexos.anx_anulados";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            anxAnuladosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            AnxAnulados anxAnulados = ConversionesAnexos.convertirAnxAnuladosTO_AnxAnulados(anxAnuladosTO);
            anxAnulados.setTcCodigo(tipoComprobanteDao.obtenerPorId(AnxTipocomprobante.class, anxAnuladosTO.getTcCodigo()));
            if (anuladosDao.eliminarAnexoAnulados(anxAnulados, sisSuceso)) {
                retorno = "TLa anulación se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar la anulación...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "F" + estadoPeriodo;
        }
        return retorno;
    }

    @Override
    public List<AnxAnuladosTO> obtenerAnexoAnulados(AnxAnuladosTO anxAnuladosTO, String empresa) throws Exception {
        return anuladosDao.obtenerAnexoAnulado(anxAnuladosTO, empresa);
    }

    @Override
    public Map<String, Object> obtenerDatosParaAnexoAnuladosTO(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        Integer secuencia = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencia"));

        AnxAnuladosTO anexoAnuladosTO = anuladosDao.getAnxAnuladosTO(secuencia);
        List<AnxTipoComprobanteComboTO> listaTipoComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboTOCompleto();

        campos.put("anexoAnuladosTO", anexoAnuladosTO);
        campos.put("listaTipoComprobante", listaTipoComprobante);
        return campos;
    }
}
