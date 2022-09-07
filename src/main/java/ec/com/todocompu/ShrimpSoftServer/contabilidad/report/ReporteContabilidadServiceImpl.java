package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.DetalleService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstructuraService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.TipoService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosVsInventarioDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaChequesPostfechadosProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorCobrarClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaCuentasPorPagarProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFlujoCajaValorizacionActivoBiologicoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceCentroProduccionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaContableDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosCuentasInconsistentes;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresCompra;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresConsumo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.report.ReporteContabilidad;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.report.ReporteContableDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TipoRRHH;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service
public class ReporteContabilidadServiceImpl implements ReporteContabilidadService {

    @Autowired
    private CuentasService cuentasService;
    @Autowired
    private TipoService tipoService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private EstructuraService estructuraService;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private EstructuraDao estructuraDao;
    private String modulo = "contabilidad";
    int totalDetalle = 0;

    @Override
    public Map<String, Object> generarReporteConTipoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConTipoTO> listConTipoTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("STipos contables");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo¬SDetalle");
            for (ConTipoTO conTipoTO : listConTipoTO) {
                listaCuerpo.add(
                        (conTipoTO.getTipCodigo() == null ? "B" : "S" + conTipoTO.getTipCodigo()) + "¬"
                        + (conTipoTO.getTipDetalle() == null ? "B" : "S" + conTipoTO.getTipDetalle()) + "¬"
                        + (conTipoTO.getTipInactivo() == true ? "SINACTIVO" : "S") + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteMayorAuxiliar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConMayorAuxiliarTO> listConMayorAuxiliarTO, String codigoCuenta, String codigoCuentaHasta, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            BigDecimal cero = new BigDecimal("0.00");
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SMayor Auxiliar de cuentas");
            listaCabecera.add("SCuenta Desde: " + codigoCuenta);
            if (codigoCuentaHasta != null) {
                listaCabecera.add("SCuenta Hasta: " + codigoCuentaHasta);
            }
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");

            List<String> rucsCopacigulf = Arrays.asList("0992879254001", "0992808519001", "0992809183001");
            boolean esCopacigulf = rucsCopacigulf.contains(usuarioEmpresaReporteTO.getEmpRuc());

            if (esCopacigulf) {
                listaCuerpo.add("SContable" + "¬" + "SCuenta" + "¬" + "SCuenta Detalle" + "¬" + "SFecha" + "¬" + "SDocumento" + "¬" + "SCP" + "¬" + "SCC" + "¬" + "SDebe" + "¬" + "SHaber" + "¬" + "SSaldo" + "¬" + "SConcepto" + "¬" + "SObservaciones");
            } else {
                listaCuerpo.add("SContable" + "¬" + "SCuenta" + "¬" + "SCuenta Detalle" + "¬" + "SFecha" + "¬" + "SDocumento" + "¬" + "SCP" + "¬" + "SCC" + "¬" + "SDebe" + "¬" + "SHaber" + "¬" + "SSaldo" + "¬" + "SObservaciones");
            }
            for (ConMayorAuxiliarTO conMayorAuxiliarTO : listConMayorAuxiliarTO) {
                String adicional = "";
                if (esCopacigulf) {
                    adicional = "¬" + (conMayorAuxiliarTO.getMaConcepto() == null ? "B" : "S" + conMayorAuxiliarTO.getMaConcepto())
                            + "¬" + (conMayorAuxiliarTO.getMaObservaciones() == null ? "B" : "S" + conMayorAuxiliarTO.getMaObservaciones());
                } else {
                    adicional = "¬" + (conMayorAuxiliarTO.getMaObservaciones() == null ? "B" : "S" + conMayorAuxiliarTO.getMaObservaciones());
                }
                listaCuerpo.add(
                        (conMayorAuxiliarTO.getMaContable() == null ? "B" : "S" + conMayorAuxiliarTO.getMaContable())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaCuenta() == null ? "B" : "S" + conMayorAuxiliarTO.getMaCuenta())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaCuentaDetalle() == null ? "B" : "S" + conMayorAuxiliarTO.getMaCuentaDetalle())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaFecha() == null ? "B" : "S" + conMayorAuxiliarTO.getMaFecha())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaDocumento() == null ? "B" : "S" + conMayorAuxiliarTO.getMaDocumento())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaCP() == null ? "B" : "S" + conMayorAuxiliarTO.getMaCP())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaCC() == null ? "B" : "S" + conMayorAuxiliarTO.getMaCC())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaDebe() == null ? "D" : "D" + conMayorAuxiliarTO.getMaDebe().add(cero).toString())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaHaber() == null ? "D" : "D" + conMayorAuxiliarTO.getMaHaber().add(cero).toString())
                        + "¬"
                        + (conMayorAuxiliarTO.getMaSaldo() == null ? "D" : "D" + conMayorAuxiliarTO.getMaSaldo().add(cero).toString())
                        + adicional
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteDiarioAuxiliar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConDiarioAuxiliarTO> listConDiarioAuxiliarTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SDiario Auxiliar");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            if (!usuarioEmpresaReporteTO.getEmpCodigo().equals("HX06")) {
                listaCuerpo.add("SOrden" + "¬" + "SCuenta" + "¬" + "SDetalle" + "¬" + "SCP" + "¬" + "SCC" + "¬"
                        + "SDocumento" + "¬" + "SDebe" + "¬" + "SHaber");
                for (ConDiarioAuxiliarTO conDiarioAuxiliarTO : listConDiarioAuxiliarTO) {

                    listaCuerpo.add(
                            (conDiarioAuxiliarTO.getDaOrden() == null ? "B" : "I" + conDiarioAuxiliarTO.getDaOrden().toString())
                            + "¬"
                            + (conDiarioAuxiliarTO.getDaCuenta() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaCuenta())
                            + "¬"
                            + (conDiarioAuxiliarTO.getDaDetalle() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaDetalle())
                            + "¬"
                            + (conDiarioAuxiliarTO.getDaCP() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaCP())
                            + "¬"
                            + (conDiarioAuxiliarTO.getDaCC() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaCC())
                            + "¬"
                            + (conDiarioAuxiliarTO.getDaDocumento() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaDocumento())
                            + "¬"
                            + (conDiarioAuxiliarTO.getDaDebe() == null ? "B" : comprobarNumericoString(conDiarioAuxiliarTO.getDaDebe()))
                            + "¬"
                            + (conDiarioAuxiliarTO.getDaHaber() == null ? "B" : comprobarNumericoString(conDiarioAuxiliarTO.getDaHaber()))
                    );
                }
            } else {
                for (int i = 0; i < listConDiarioAuxiliarTO.size(); i++) {
                    ConDiarioAuxiliarTO conDiarioAuxiliarTO = listConDiarioAuxiliarTO.get(i);
                    ConDiarioAuxiliarTO conDiarioAuxiliarTO1 = new ConDiarioAuxiliarTO();
                    if (i + 3 < listConDiarioAuxiliarTO.size()) {
                        conDiarioAuxiliarTO1 = listConDiarioAuxiliarTO.get(i + 3);
                        if (conDiarioAuxiliarTO1.getDaCuenta() == null || conDiarioAuxiliarTO1.getDaCuenta().equals("") || conDiarioAuxiliarTO1.getDaCuenta().charAt(0) == '*') {
                            conDiarioAuxiliarTO1 = new ConDiarioAuxiliarTO();
                        }
                    }
                    if (conDiarioAuxiliarTO.getDaCuenta() == null || conDiarioAuxiliarTO.getDaCuenta().equals("") || conDiarioAuxiliarTO.getDaCuenta().charAt(0) == '*') {
                        listaCuerpo.add(
                                (conDiarioAuxiliarTO.getDaCuenta() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaCuenta().substring(1))
                                + "¬"
                                + (conDiarioAuxiliarTO.getDaDetalle() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaDetalle())
                                + "¬"
                                + (conDiarioAuxiliarTO.getDaCP() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaCP())
                                + "¬"
                                + (conDiarioAuxiliarTO.getDaDocumento() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaDocumento())
                                + "¬"
                                + (conDiarioAuxiliarTO.getDaHaber() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaHaber())
                                + "¬"
                                + (conDiarioAuxiliarTO.getDaCC() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaCC())
                                + "¬"
                                + (conDiarioAuxiliarTO.getDaDebe() == null ? "B" : "S" + conDiarioAuxiliarTO.getDaDebe())
                                + "¬"
                                //aqui se divide la info
                                + (conDiarioAuxiliarTO1.getDaOrden() == null ? "B" : "I" + conDiarioAuxiliarTO1.getDaOrden().toString())
                                + "¬"
                                + (conDiarioAuxiliarTO1.getDaCuenta() == null ? "B" : "S" + conDiarioAuxiliarTO1.getDaCuenta())
                                + "¬"
                                + (conDiarioAuxiliarTO1.getDaDetalle() == null ? "B" : "S" + conDiarioAuxiliarTO1.getDaDetalle())
                                + "¬"
                                + (conDiarioAuxiliarTO1.getDaCP() == null ? "B" : "S" + conDiarioAuxiliarTO1.getDaCP())
                                + "¬"
                                + (conDiarioAuxiliarTO1.getDaCC() == null ? "B" : "S" + conDiarioAuxiliarTO1.getDaCC())
                                + "¬"
                                + (conDiarioAuxiliarTO1.getDaDocumento() == null ? "B" : "S" + conDiarioAuxiliarTO1.getDaDocumento())
                                + "¬"
                                + (conDiarioAuxiliarTO1.getDaDebe() == null ? "B" : "S" + conDiarioAuxiliarTO1.getDaDebe())
                                + "¬"
                                + (conDiarioAuxiliarTO1.getDaHaber() == null ? "B" : "S" + conDiarioAuxiliarTO1.getDaHaber())
                        );
                        //poner i+1
                    }
                }
            }

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteContablesVerificacionesCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunContablesVerificacionesComprasTO> listConFunContablesVerificacionesComprasTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SFecha Desde: " + fechaDesde);
            listaCabecera.add("SFecha Hasta: " + fechaHasta);
            listaCabecera.add("SVerificacion de Compras");
            listaCabecera.add("S");
            listaCuerpo.add("SCompra" + "¬" + "SFecha Contabilidad" + "¬" + "SFecha Inventario" + "¬" + "SMonto" + "¬" + "SObservación");
            for (ConFunContablesVerificacionesComprasTO conFunContablesVerificacionesComprasTO : listConFunContablesVerificacionesComprasTO) {
                listaCuerpo
                        .add((conFunContablesVerificacionesComprasTO.getContabilidadSecuencial() == null ? "B"
                                : "S" + conFunContablesVerificacionesComprasTO.getContabilidadSecuencial())
                                + "¬"
                                + (conFunContablesVerificacionesComprasTO.getContabilidadFecha() == null ? "B"
                                : "S" + conFunContablesVerificacionesComprasTO.getContabilidadFecha())
                                + "¬"
                                + (conFunContablesVerificacionesComprasTO.getInventarioFecha() == null ? "B"
                                : "S" + conFunContablesVerificacionesComprasTO.getInventarioFecha())
                                + "¬"
                                + (conFunContablesVerificacionesComprasTO.getInventarioMonto() == null ? "B"
                                : "D" + conFunContablesVerificacionesComprasTO.getInventarioMonto().toString())
                                + "¬"
                                + (conFunContablesVerificacionesComprasTO.getInventarioObservacion() == null ? "B"
                                : "S" + conFunContablesVerificacionesComprasTO.getInventarioObservacion())
                                + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteConCuentasSobregiradasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceGeneralTO> listConBalanceGeneralTO, String fecha, String codigoSector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SEstado de Situación Financiera");
            listaCabecera.add("TCP: " + codigoSector + " Fecha: " + fecha);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo");
            for (ConBalanceGeneralTO conBalanceGeneralTO : listConBalanceGeneralTO) {
                listaCuerpo.add(
                        (conBalanceGeneralTO.getBgCuenta() == null ? "B" : "S" + conBalanceGeneralTO.getBgCuenta()) + "¬"
                        + (conBalanceGeneralTO.getBgDetalle() == null ? "B" : "S" + conBalanceGeneralTO.getBgDetalle()) + "¬"
                        + (conBalanceGeneralTO.getBgSaldo() == null ? "B" : "D" + conBalanceGeneralTO.getBgSaldo().toString())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Map<String, Object> generarReporteContablesVerificacionesErroresExcel(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SVerificacion de contables con errores");
            listaCabecera.add("S");
            listaCuerpo.add("SPeríodo" + "¬" + "STipo" + "¬" + "SNúmero" + "¬" + "SFecha" + "SPendiente" + "SBloqueado" + "SAnulado" + "SDébitos" + "¬" + "SCréditos" + "¬" + "SObservaciones");
            for (ConFunContablesVerificacionesTO conFunContablesVerificacionesTO : listConFunContablesVerificacionesTO) {
                listaCuerpo.add(
                        (conFunContablesVerificacionesTO.getVcPeriodo() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcPeriodo()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcTipo() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcTipo()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcNumero() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcNumero()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcFecha() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcFecha()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcPendiente() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcPendiente()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcBloqueado() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcBloqueado()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcAnulado() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcAnulado()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcDebitos() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcDebitos()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcCreditos() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcCreditos()) + "¬"
                        + (conFunContablesVerificacionesTO.getVcObservaciones() == null ? "B" : "S" + conFunContablesVerificacionesTO.getVcObservaciones()) + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Map<String, Object> generarReporteMayorGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConMayorGeneralTO> listConMayorGeneralTO, String codigoCuenta, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Mayor General");
            listaCabecera.add("SCuenta: " + codigoCuenta);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo 6" + "¬" + "SSaldo 5" + "¬" + "SSaldo 4" + "¬"
                    + "SSaldo 3" + "¬" + "SSaldo 2" + "¬" + "SSaldo 1");
            for (ConMayorGeneralTO conMayorGeneralTO : listConMayorGeneralTO) {
                listaCuerpo.add((conMayorGeneralTO.getBgCuenta() == null ? "B"
                        : "S" + conMayorGeneralTO.getBgCuenta())
                        + "¬"
                        + (conMayorGeneralTO.getBgDetalle() == null ? "B"
                        : "S" + conMayorGeneralTO.getBgDetalle())
                        + "¬"
                        + (conMayorGeneralTO.getBgSaldo6() == null ? "B"
                        : "D" + conMayorGeneralTO.getBgSaldo6().toString())
                        + "¬"
                        + (conMayorGeneralTO.getBgSaldo5() == null ? "B"
                        : "D" + conMayorGeneralTO.getBgSaldo5().toString())
                        + "¬"
                        + (conMayorGeneralTO.getBgSaldo4() == null ? "B"
                        : "D" + conMayorGeneralTO.getBgSaldo4().toString())
                        + "¬"
                        + (conMayorGeneralTO.getBgSaldo3() == null ? "B"
                        : "D" + conMayorGeneralTO.getBgSaldo3().toString())
                        + "¬"
                        + (conMayorGeneralTO.getBgSaldo2() == null ? "B"
                        : "D" + conMayorGeneralTO.getBgSaldo2().toString())
                        + "¬" + (conMayorGeneralTO.getBgSaldo1() == null ? "B"
                        : "D" + conMayorGeneralTO.getBgSaldo1().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Map<String, Object> generarReporteConCuenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConCuentasTO> listConCuentasTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SPlan de Cuentas");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo¬SDetalle");
            for (ConCuentasTO conCuentasTO : listConCuentasTO) {
                boolean activo = conCuentasTO.getCuentaActivo() == null ? false : conCuentasTO.getCuentaActivo();
                listaCuerpo.add(
                        (conCuentasTO.getCuentaCodigo() == null ? "B" : "S" + conCuentasTO.getCuentaCodigo()) + "¬"
                        + (conCuentasTO.getCuentaDetalle() == null ? "B" : "S" + conCuentasTO.getCuentaDetalle()) + "¬"
                        + (activo == true ? "S" : "SINACTIVO") + "¬"
                        + (conCuentasTO.getCuentaBloqueada() == false ? "S" : "SBLOQUEADA") + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarPlantillaConCuentasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConCuentasTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            listaCuerpo.add("SCódigo¬SDetalle");
            listado.forEach((conCuentasTO) -> {
                listaCuerpo.add(
                        (conCuentasTO.getCuentaCodigo() == null ? "B" : "S" + conCuentasTO.getCuentaCodigo()) + "¬"
                        + (conCuentasTO.getCuentaDetalle() == null ? "B" : "S" + conCuentasTO.getCuentaDetalle())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteEstadosComprobacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceComprobacionTO> listado, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SBalance de Comprobación");
            listaCabecera.add("TDesde: " + fechaDesde + "  Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo Anterior" + "¬" + "STotal Débito" + "¬" + "STotal Crédito" + "¬" + "SSaldo Final");
            for (ConBalanceComprobacionTO conBalanceComprobacionTO : listado) {
                listaCuerpo.add((conBalanceComprobacionTO.getBcCuenta() == null ? "B"
                        : "S" + conBalanceComprobacionTO.getBcCuenta())
                        + "¬"
                        + (conBalanceComprobacionTO.getBcDetalle() == null ? "B"
                        : "S" + conBalanceComprobacionTO.getBcDetalle())
                        + "¬"
                        + (conBalanceComprobacionTO.getBcSaldoAnterior() == null ? "B"
                        : "D" + conBalanceComprobacionTO.getBcSaldoAnterior().toString())
                        + "¬"
                        + (conBalanceComprobacionTO.getBcTotalDebito() == null ? "B"
                        : "D" + conBalanceComprobacionTO.getBcTotalDebito().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (conBalanceComprobacionTO.getBcTotalCredito() == null ? "B"
                        : "D" + conBalanceComprobacionTO.getBcTotalCredito().add(BigDecimal.ZERO).toString())
                        + "¬" + (conBalanceComprobacionTO.getBcSaldoFinal() == null ? "B"
                        : "D" + conBalanceComprobacionTO.getBcSaldoFinal().add(BigDecimal.ZERO).toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteEstadoResultadoIntegralComparativo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceResultadoComparativoTO> listaConBalanceResultadoComparativoTO, String fechaDesde, String fechaHasta, String sector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("TEstado de Resultados Integral Comparativo");
            listaCabecera.add("TCP: " + sector + "  Desde: " + fechaDesde + "   Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo1" + "¬" + "SSaldo2");
            for (ConBalanceResultadoComparativoTO conBalanceResultadoComparativoTO : listaConBalanceResultadoComparativoTO) {
                listaCuerpo.add((conBalanceResultadoComparativoTO.getBrcCuenta() == null ? "B"
                        : "S" + conBalanceResultadoComparativoTO.getBrcCuenta())
                        + "¬"
                        + (conBalanceResultadoComparativoTO.getBrcDetalle() == null ? "B"
                        : "S" + conBalanceResultadoComparativoTO.getBrcDetalle())
                        + "¬"
                        + (conBalanceResultadoComparativoTO.getBrcSaldo() == null ? "B"
                        : "D" + conBalanceResultadoComparativoTO.getBrcSaldo().toString())
                        + "¬"
                        + (conBalanceResultadoComparativoTO.getBrcSaldo2() == null ? "B"
                        : "D" + conBalanceResultadoComparativoTO.getBrcSaldo2().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteEstadoSituacionFinanciera(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceGeneralTO> listaBalanceGeneral, String fechaHasta, String sector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("TEstado de Situación Financiera");
            listaCabecera.add("TCP: " + sector + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo");
            for (ConBalanceGeneralTO conFunBalanceGeneralNecTO : listaBalanceGeneral) {
                listaCuerpo.add((conFunBalanceGeneralNecTO.getBgCuenta() == null ? "B"
                        : "S" + conFunBalanceGeneralNecTO.getBgCuenta())
                        + "¬"
                        + (conFunBalanceGeneralNecTO.getBgDetalle() == null ? "B"
                        : "S" + conFunBalanceGeneralNecTO.getBgDetalle())
                        + "¬" + (conFunBalanceGeneralNecTO.getBgSaldo() == null ? "B"
                        : "D" + conFunBalanceGeneralNecTO.getBgSaldo().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteEstadoResultadoIntegral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceResultadoTO> listaConBalanceResultadoTO, String fechaDesde, String fechaHasta, String sector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("TEstado de Resultados Integral");
            listaCabecera.add("TCP: " + sector + " Desde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo");
            for (ConBalanceResultadoTO conFunBalanceResultadosNecTO : listaConBalanceResultadoTO) {
                listaCuerpo.add((conFunBalanceResultadosNecTO.getBgCuenta() == null ? "B"
                        : "S" + conFunBalanceResultadosNecTO.getBgCuenta())
                        + "¬"
                        + (conFunBalanceResultadosNecTO.getBgDetalle() == null ? "B"
                        : "S" + conFunBalanceResultadosNecTO.getBgDetalle())
                        + "¬" + (conFunBalanceResultadosNecTO.getBgSaldo() == null ? "B"
                        : "D" + conFunBalanceResultadosNecTO.getBgSaldo().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteEstadoSituacionFinancieraComparativo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceGeneralComparativoTO> listaBalanceGeneralComparativo, String fechaDesde, String fechaHasta, String sector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("TEstado de Situación Financiera Comparativo");
            listaCabecera.add("TCP: " + sector + " Fecha Anterior: " + fechaDesde);
            listaCabecera.add("TCP: " + sector + " Fecha Actual: " + fechaHasta);
            listaCabecera.add("T");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo Anterior" + "¬" + "SSaldo Actual");
            for (ConBalanceGeneralComparativoTO balanceGeneralComparativoTO : listaBalanceGeneralComparativo) {
                listaCuerpo.add((balanceGeneralComparativoTO.getBgCuenta() == null ? "B"
                        : "S" + balanceGeneralComparativoTO.getBgCuenta())
                        + "¬"
                        + (balanceGeneralComparativoTO.getBgDetalle() == null ? "B"
                        : "S" + balanceGeneralComparativoTO.getBgDetalle())
                        + "¬"
                        + (balanceGeneralComparativoTO.getBgSaldoAnterior() == null ? "B"
                        : "D" + balanceGeneralComparativoTO.getBgSaldoAnterior().toString())
                        + "¬" + (balanceGeneralComparativoTO.getBgSaldoActual() == null ? "B"
                        : "D" + balanceGeneralComparativoTO.getBgSaldoActual().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteEstadoResultadosVsInventario(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConListaBalanceResultadosVsInventarioTO> listaBalanceResultadosVsInventarioTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Verificación de Balance Resultado Vs Inventario");
            listaCabecera.add("SDesde: " + fechaDesde + "   Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SNombre" + "¬" + "SContabilidad" + "¬" + "SInventario" + "¬" + "SDiferencia");
            for (ConListaBalanceResultadosVsInventarioTO conListaBalanceResultadosVsInventarioTO : listaBalanceResultadosVsInventarioTO) {
                listaCuerpo.add((conListaBalanceResultadosVsInventarioTO.getVriCuentaContable() == null ? "B"
                        : "S" + conListaBalanceResultadosVsInventarioTO.getVriCuentaContable())
                        + "¬"
                        + (conListaBalanceResultadosVsInventarioTO.getVriNombre() == null ? "B"
                        : "S" + conListaBalanceResultadosVsInventarioTO.getVriNombre())
                        + "¬"
                        + (conListaBalanceResultadosVsInventarioTO.getVriSaldoContable() == null ? "B"
                        : "D" + conListaBalanceResultadosVsInventarioTO.getVriSaldoContable().toString())
                        + "¬"
                        + (conListaBalanceResultadosVsInventarioTO.getVriInventarioInicial() == null ? "B"
                        : "D" + conListaBalanceResultadosVsInventarioTO.getVriInventarioInicial()
                                .toString())
                        + "¬"
                        + (conListaBalanceResultadosVsInventarioTO.getVriDiferencia() == null ? "B"
                        : "D" + conListaBalanceResultadosVsInventarioTO.getVriDiferencia().toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteConBalanceResultadosVsInventarioDetalladoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceResultadosVsInventarioDetalladoTO> listaBalanceResultadosVsInventarioTO, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Verificación de Balance Resultado Vs Inventario");
            listaCabecera.add("SDesde: " + fechaDesde + "   Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SNombre" + "¬" + "SContabilidad" + "¬" + "SInventario" + "¬" + "SDiferencia");
            for (ConBalanceResultadosVsInventarioDetalladoTO conListaBalanceResultadosVsInventarioTO : listaBalanceResultadosVsInventarioTO) {
                listaCuerpo.add((conListaBalanceResultadosVsInventarioTO.getVriCuentaContable() == null ? "B"
                        : "S" + conListaBalanceResultadosVsInventarioTO.getVriCuentaContable())
                        + "¬"
                        + (conListaBalanceResultadosVsInventarioTO.getVriNombre() == null ? "B"
                        : "S" + conListaBalanceResultadosVsInventarioTO.getVriNombre())
                        + "¬"
                        + (conListaBalanceResultadosVsInventarioTO.getVriSaldoContable() == null ? "B"
                        : "D" + conListaBalanceResultadosVsInventarioTO.getVriSaldoContable().toString())
                        + "¬"
                        + (conListaBalanceResultadosVsInventarioTO.getVriInventarioInicial() == null ? "B"
                        : "D" + conListaBalanceResultadosVsInventarioTO.getVriInventarioInicial()
                                .toString())
                        + "¬"
                        + (conListaBalanceResultadosVsInventarioTO.getVriDiferencia() == null ? "B"
                        : "D" + conListaBalanceResultadosVsInventarioTO.getVriDiferencia().toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteBalanceResultadoMensualizado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceResultadosMensualizadosTO> lista, String fechaInicio, String fechaFin, String codigoSector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            int inicio = UtilsDate.getNumeroMes(fechaInicio, "yyyy-MM-dd");
            int fin = UtilsDate.getNumeroMes(fechaFin, "yyyy-MM-dd");
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SSector: " + codigoSector);
            listaCabecera.add("SFecha Desde: " + fechaInicio);
            listaCabecera.add("SFecha Fin: " + fechaFin);
            listaCabecera.add("SBalance de Resultado Mensualizado");
            listaCabecera.add("S");
            String cuerpo = "";
            cuerpo = inicio <= 0 && fin >= 0 ? cuerpo + "¬" + "SEnero" : cuerpo;
            cuerpo = inicio <= 1 && fin >= 1 ? cuerpo + "¬" + "SFebrero" : cuerpo;
            cuerpo = inicio <= 2 && fin >= 2 ? cuerpo + "¬" + "SMarzo" : cuerpo;
            cuerpo = inicio <= 3 && fin >= 3 ? cuerpo + "¬" + "SAbril" : cuerpo;
            cuerpo = inicio <= 4 && fin >= 4 ? cuerpo + "¬" + "SMayo" : cuerpo;
            cuerpo = inicio <= 5 && fin >= 5 ? cuerpo + "¬" + "SJunio" : cuerpo;
            cuerpo = inicio <= 6 && fin >= 6 ? cuerpo + "¬" + "SJulio" : cuerpo;
            cuerpo = inicio <= 7 && fin >= 7 ? cuerpo + "¬" + "SAgosto" : cuerpo;
            cuerpo = inicio <= 8 && fin >= 8 ? cuerpo + "¬" + "SSeptiembre" : cuerpo;
            cuerpo = inicio <= 9 && fin >= 9 ? cuerpo + "¬" + "SOctubre" : cuerpo;
            cuerpo = inicio <= 10 && fin >= 10 ? cuerpo + "¬" + "SNoviembre" : cuerpo;
            cuerpo = inicio <= 11 && fin >= 11 ? cuerpo + "¬" + "SDiciembre" : cuerpo;
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + cuerpo + "¬" + "SSaldo");

            for (ConBalanceResultadosMensualizadosTO objeto : lista) {
                listaCuerpo.add(
                        (objeto.getBrCuenta() == null ? "B" : "S" + objeto.getBrCuenta())
                        + "¬" + (objeto.getBrDetalle() == null ? "B" : "S" + objeto.getBrDetalle())
                        + (inicio <= 0 && fin >= 0 ? "¬" + (objeto.getBrEnero() == null ? "B" : "D" + objeto.getBrEnero()) : "")
                        + (inicio <= 1 && fin >= 1 ? "¬" + (objeto.getBrFebrero() == null ? "B" : "D" + objeto.getBrFebrero()) : "")
                        + (inicio <= 2 && fin >= 2 ? "¬" + (objeto.getBrMarzo() == null ? "B" : "D" + objeto.getBrMarzo()) : "")
                        + (inicio <= 3 && fin >= 3 ? "¬" + (objeto.getBrAbril() == null ? "B" : "D" + objeto.getBrAbril()) : "")
                        + (inicio <= 4 && fin >= 4 ? "¬" + (objeto.getBrMayo() == null ? "B" : "D" + objeto.getBrMayo()) : "")
                        + (inicio <= 5 && fin >= 5 ? "¬" + (objeto.getBrJunio() == null ? "B" : "D" + objeto.getBrJunio()) : "")
                        + (inicio <= 6 && fin >= 6 ? "¬" + (objeto.getBrJulio() == null ? "B" : "D" + objeto.getBrJulio()) : "")
                        + (inicio <= 7 && fin >= 7 ? "¬" + (objeto.getBrAgosto() == null ? "B" : "D" + objeto.getBrAgosto()) : "")
                        + (inicio <= 8 && fin >= 8 ? "¬" + (objeto.getBrSeptiembre() == null ? "B" : "D" + objeto.getBrSeptiembre()) : "")
                        + (inicio <= 9 && fin >= 9 ? "¬" + (objeto.getBrOctubre() == null ? "B" : "D" + objeto.getBrOctubre()) : "")
                        + (inicio <= 10 && fin >= 10 ? "¬" + (objeto.getBrNoviembre() == null ? "B" : "D" + objeto.getBrNoviembre()) : "")
                        + (inicio <= 11 && fin >= 11 ? "¬" + (objeto.getBrDiciembre() == null ? "B" : "D" + objeto.getBrDiciembre()) : "")
                        + "¬" + (objeto.getBrSaldo() == null ? "B" : "D" + objeto.getBrSaldo())
                );
            }

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> generarReporteBalanceResultadoMensualizadoAntiguo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, String sector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SBalance de Resultado Mensualizado");
            listaCabecera.add("S");
            String nombresColumnas = "";

            for (int i = 0; i < columnas.size(); i++) {
                nombresColumnas = nombresColumnas + "S" + columnas.get(i) + "¬";
            }
            listaCuerpo.add(nombresColumnas);
            String dato = "";
            for (Object[] dato1 : datos) {
                dato = "S" + dato1[0] + "¬" + "S" + dato1[1] + "¬";
                for (int j = 2; j < columnas.size(); j++) {
                    dato += comprobarNumericoString(dato1[j] != null ? dato1[j].toString() : "");
                }
                listaCuerpo.add(dato);
            }

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    String comprobarNumericoString(String dato) {
        BigDecimal valor = null;
        try {
            valor = new java.math.BigDecimal(dato);
            if (valor != null) {
                return "D" + dato + "¬";
            } else {
                return "S" + dato + "¬";
            }
        } catch (Exception e) {
            return "S" + dato + "¬";
        }
    }

    @Override
    public byte[] generarReporteTipoContable(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConTipoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportTipoContable.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteContableDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConContableReporteTO> listConContableReporteTO) throws Exception {
        String referencia = listConContableReporteTO.get(0).getConContableTO().getConReferencia();
        if (referencia != null && TipoRRHH.getTipoRRHH(referencia) != null) {
            switch (TipoRRHH.getTipoRRHH(referencia)) {
                case LIQUIDACION:
                    listConContableReporteTO.get(0).setTitulo("CONTABLE DE LIQUIDACION DE TRABAJADOR");
            }
        }
        String nombreReporte = "reportComprobanteContable.jrxml";
        String codUsr = listConContableReporteTO.get(0).getConContableTO().getUsrInsertaContable();
        String elaboradoPor = usuarioDetalleService.getUsuarioNombreApellido(codUsr.trim());
        List<ReporteContableDetalle> listReporteContableDetalle = ReporteContabilidad.generarColeccionContableDetalle(usuarioEmpresaReporteTO, listConContableReporteTO, elaboradoPor);
        if (listReporteContableDetalle != null) {
            totalDetalle = 0;
            List<ReporteContableDetalle> cuentasPadre = new ArrayList<>();
            listReporteContableDetalle.forEach((detalle) -> {
                List<ReporteContableDetalle> result = cuentasPadre.stream()
                        .filter(item -> item != detalle && item.getConCtaCodigoPadre().equals(detalle.getConCtaCodigoPadre()))
                        .collect(Collectors.toList());
                if (result == null || result.isEmpty()) {
                    cuentasPadre.add(detalle);
                }
            });
            totalDetalle = cuentasPadre.size() + listReporteContableDetalle.size();
            if (totalDetalle > 8) {
                nombreReporte = "reportComprobanteContableExtendido.jrxml";
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listReporteContableDetalle);
    }

    @Override
    public String obtenerNombreReporteContable(List<ReporteContableDetalle> listReporteContableDetalle) throws Exception {
        String nombreReporte = UtilsArchivos.getRutaReportes() + modulo + UtilsArchivos.sep + "subreportComprobanteContable.jasper";
        return nombreReporte;
    }

    @Override
    public List<ReporteContableDetalle> generarReporteContableDetalleParaCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContablePK pk) throws Exception {
        if (pk != null && pk.getConEmpresa() != null && pk.getConPeriodo() != null && pk.getConTipo() != null && pk.getConNumero() != null) {
            List<ConContablePK> listadoContablePK = new ArrayList<>();
            listadoContablePK.add(pk);
            List<ConContableReporteTO> listadoEnviar = generarListaConContableReporteTO(listadoContablePK, new SisInfoTO());
            if (listadoEnviar != null) {
                String codUsr = listadoEnviar.get(0).getConContableTO().getUsrInsertaContable();
                String elaboradoPor = usuarioDetalleService.getUsuarioNombreApellido(codUsr.trim());
                List<ReporteContableDetalle> listReporteContableDetalle = ReporteContabilidad.generarColeccionContableDetalle(usuarioEmpresaReporteTO, listadoEnviar, elaboradoPor);
                if (listReporteContableDetalle != null) {
                    return listReporteContableDetalle;
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public byte[] generarReporteMayorAuxiliar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String cuentaContableDesde, String cuentaContablePadreDesde, String cuentaContableHasta,
            String cuentaContablePadreHasta, List<ConMayorAuxiliarTO> listConMayorAuxiliarTO) throws Exception {

        List<String> rucsCopacigulf = Arrays.asList("0992879254001", "0992808519001", "0992809183001");
        boolean esCopacigulf = rucsCopacigulf.contains(usuarioEmpresaReporteTO.getEmpRuc());
        List<ReporteMayorAuxiliar> listaReporteMayorAuxiliarParametro = new ArrayList<ReporteMayorAuxiliar>();
        for (ConMayorAuxiliarTO cma : listConMayorAuxiliarTO) {
            ReporteMayorAuxiliar reporteMayorAuxiliar = new ReporteMayorAuxiliar();
            reporteMayorAuxiliar.setFechaDesde(fechaDesde);
            reporteMayorAuxiliar.setFechaHasta(fechaHasta);
            reporteMayorAuxiliar.setCuentaContableDesde(cuentaContableDesde);
            reporteMayorAuxiliar.setCuentaContablePadreDesde(cuentaContablePadreDesde);
            reporteMayorAuxiliar.setCuentaContableHasta(cuentaContableHasta);
            reporteMayorAuxiliar.setCuentaContablePadreHasta(cuentaContablePadreHasta);
            reporteMayorAuxiliar.setMaContable(cma.getMaContable());
            reporteMayorAuxiliar.setMaFecha(cma.getMaFecha());
            reporteMayorAuxiliar.setMaSecuencia(cma.getMaSecuencia());
            reporteMayorAuxiliar.setMaCuenta(cma.getMaCuenta() + " | " + cma.getMaCuentaDetalle());
            reporteMayorAuxiliar.setMaCP(cma.getMaCP());
            reporteMayorAuxiliar.setMaCC(cma.getMaCC());
            reporteMayorAuxiliar.setMaDocumento(cma.getMaDocumento());
            reporteMayorAuxiliar.setMaDebe(cma.getMaDebe());
            reporteMayorAuxiliar.setMaHaber(cma.getMaHaber());
            reporteMayorAuxiliar.setMaSaldo(cma.getMaSaldo());
            reporteMayorAuxiliar.setMaObservaciones(cma.getMaObservaciones());
            reporteMayorAuxiliar.setMaConcepto(cma.getMaConcepto());
            reporteMayorAuxiliar.setMaGenerado(cma.getMaGenerado());
            reporteMayorAuxiliar.setMaReferencia(cma.getMaReferencia());
            reporteMayorAuxiliar.setMaOrden(cma.getMaOrden());
            listaReporteMayorAuxiliarParametro.add(reporteMayorAuxiliar);
        }
        String nombreReporte = cuentaContableHasta != null && cuentaContableHasta != "" ? "reportContabilidadMayorAuxiliarMultiple.jrxml" : "reportContabilidadMayorAuxiliar.jrxml";
        if (esCopacigulf) {
            nombreReporte = cuentaContableHasta != null && cuentaContableHasta != "" ? "reportContabilidadMayorAuxiliarMultipleCopacigulf.jrxml" : "reportContabilidadMayorAuxiliarCopacigulf.jrxml";
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaReporteMayorAuxiliarParametro);
    }

    @Override
    public byte[] generarReporteMayorGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String cuentaContable, Object[][] datos) throws Exception {

        List<ReporteMayorGeneral> listaReporteMayorGeneralParametro = new ArrayList<ReporteMayorGeneral>();
        for (int i = 0; i < datos.length; i++) {
            ReporteMayorGeneral reporteMayorGeneral = new ReporteMayorGeneral();
            reporteMayorGeneral.setFechaHasta(fechaHasta);
            reporteMayorGeneral.setCuentaContable(cuentaContable);
            reporteMayorGeneral.setBgCuenta(datos[i][0] != null ? datos[i][0].toString() : "");
            reporteMayorGeneral.setBgDetalle(datos[i][1] != null ? datos[i][1].toString() : "");
            reporteMayorGeneral
                    .setBgSaldo6(new java.math.BigDecimal(datos[i][2] != null ? datos[i][2].toString() : "0.00"));
            reporteMayorGeneral
                    .setBgSaldo5(new java.math.BigDecimal(datos[i][3] != null ? datos[i][3].toString() : "0.00"));
            if (datos.length >= 125 && datos[i].length > 4) {
                reporteMayorGeneral
                        .setBgSaldo4(new java.math.BigDecimal(datos[i][4] != null ? datos[i][4].toString() : "0.00"));
            }
            if (datos.length >= 205) {
                reporteMayorGeneral
                        .setBgSaldo3(new java.math.BigDecimal(datos[i][5] != null ? datos[i][5].toString() : "0.00"));
            }
            if (datos.length >= 311) {
                reporteMayorGeneral
                        .setBgSaldo2(new java.math.BigDecimal(datos[i][6] != null ? datos[i][6].toString() : "0.00"));
            }
            if (datos.length >= 311) {
                reporteMayorGeneral
                        .setBgSaldo1(new java.math.BigDecimal(datos[i][7] != null ? datos[i][7].toString() : "0.00"));
            }
            listaReporteMayorGeneralParametro.add(reporteMayorGeneral);
        }

        return genericReporteService.generarReporte(modulo, "reportContabilidadMayorGeneral.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaReporteMayorGeneralParametro);
    }

    @Override
    public byte[] generarReporteMayorGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String cuentaContable, List<ConMayorGeneralTO> listConMayorGeneralTO) throws Exception {

        List<ReporteMayorGeneral> listaReporteMayorGeneralParametro = new ArrayList<ReporteMayorGeneral>();
        for (ConMayorGeneralTO cmgto : listConMayorGeneralTO) {
            ReporteMayorGeneral reporteMayorGeneral = new ReporteMayorGeneral();
            reporteMayorGeneral.setFechaHasta(fechaHasta);
            reporteMayorGeneral.setCuentaContable(cuentaContable);
            reporteMayorGeneral.setBgCuenta(cmgto.getBgCuenta());
            reporteMayorGeneral.setBgDetalle(cmgto.getBgDetalle());
            reporteMayorGeneral.setBgSaldo6(cmgto.getBgSaldo6());
            reporteMayorGeneral.setBgSaldo5(cmgto.getBgSaldo5());
            reporteMayorGeneral.setBgSaldo4(cmgto.getBgSaldo4());
            reporteMayorGeneral.setBgSaldo3(cmgto.getBgSaldo3());
            reporteMayorGeneral.setBgSaldo2(cmgto.getBgSaldo2());
            reporteMayorGeneral.setBgSaldo1(cmgto.getBgSaldo1());
            listaReporteMayorGeneralParametro.add(reporteMayorGeneral);
        }

        return genericReporteService.generarReporte(modulo, "reportContabilidadMayorGeneral.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaReporteMayorGeneralParametro);
    }

    @Override
    public byte[] generarReporteDiarioAuxiliar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String codigoTipo, List<ConDiarioAuxiliarTO> listConDiarioAuxiliarTO) throws Exception {

        List<ReporteDiarioAuxiliar> listaReporteDiarioAuxiliar = new ArrayList<ReporteDiarioAuxiliar>();
        for (ConDiarioAuxiliarTO cdato : listConDiarioAuxiliarTO) {
            ReporteDiarioAuxiliar reporteDiarioAuxiliar = new ReporteDiarioAuxiliar();
            reporteDiarioAuxiliar.setCodigoTipo(codigoTipo);
            reporteDiarioAuxiliar.setFechaDesde(fechaDesde);
            reporteDiarioAuxiliar.setFechaHasta(fechaHasta);
            reporteDiarioAuxiliar.setDaOrden(cdato.getDaOrden());
            reporteDiarioAuxiliar.setDaCuenta(cdato.getDaCuenta());
            reporteDiarioAuxiliar.setDaDetalle(cdato.getDaDetalle());
            reporteDiarioAuxiliar.setDaCP(cdato.getDaCP());
            reporteDiarioAuxiliar.setDaCC(cdato.getDaCC());
            reporteDiarioAuxiliar.setDaDocumento(cdato.getDaDocumento());
            reporteDiarioAuxiliar.setDaDebe(cdato.getDaDebe());
            reporteDiarioAuxiliar.setDaHaber(cdato.getDaHaber());
            listaReporteDiarioAuxiliar.add(reporteDiarioAuxiliar);
        }
        return genericReporteService.generarReporte(modulo, "reportContabilidadDiarioGeneral.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaReporteDiarioAuxiliar);
    }

    @Override
    public byte[] generarReporteBalanceComprobacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<ConBalanceComprobacionTO> listConBalanceComprobacionTO) throws Exception {

        List<ReporteBalanceComprobacion> listaReporteBalanceComprobacion = new ArrayList<ReporteBalanceComprobacion>();
        for (ConBalanceComprobacionTO cbcto : listConBalanceComprobacionTO) {
            ReporteBalanceComprobacion reporteBalanceComprobacion = new ReporteBalanceComprobacion();
            reporteBalanceComprobacion.setFechaDesde(fechaDesde);
            reporteBalanceComprobacion.setFechaHasta(fechaHasta);
            reporteBalanceComprobacion.setBcCuenta(cbcto.getBcCuenta());
            reporteBalanceComprobacion.setBcDetalle(cbcto.getBcDetalle());
            reporteBalanceComprobacion.setBcSaldoAnterior(cbcto.getBcSaldoAnterior());
            reporteBalanceComprobacion.setBcTotalDebito(cbcto.getBcTotalDebito());
            reporteBalanceComprobacion.setBcTotalCredito(cbcto.getBcTotalCredito());
            reporteBalanceComprobacion.setBcSaldoFinal(cbcto.getBcSaldoFinal());
            listaReporteBalanceComprobacion.add(reporteBalanceComprobacion);
        }
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarReporte(modulo, "reportContabilidadBalanceComprobacion.jrxml",
                usuarioEmpresaReporteTO, parametros, listaReporteBalanceComprobacion);
    }

    @Override
    public byte[] generarReporteBalanceGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String codigoSector, List<ConFunBalanceGeneralNecTO> listConFunBalanceGeneralNecTO,
            List<ConBalanceGeneralTO> listConBalanceGeneralTO) throws Exception {

        List<ReporteBalanceGeneral> listaReporteBalanceGeneral = new ArrayList<ReporteBalanceGeneral>();
        if (listConFunBalanceGeneralNecTO != null) {
            for (ConFunBalanceGeneralNecTO cfbgnto : listConFunBalanceGeneralNecTO) {
                ReporteBalanceGeneral reporteBalanceGeneral = new ReporteBalanceGeneral();
                reporteBalanceGeneral.setCodigoCP(codigoSector);
                reporteBalanceGeneral.setFechaHasta(fechaHasta);
                reporteBalanceGeneral.setBgCuenta(cfbgnto.getBgCuenta());
                reporteBalanceGeneral.setBgDetalle(cfbgnto.getBgDetalle());
                reporteBalanceGeneral.setBgSaldo1(cfbgnto.getBgSaldo1());
                listaReporteBalanceGeneral.add(reporteBalanceGeneral);
            }
        } else if (listConBalanceGeneralTO != null) {
            for (ConBalanceGeneralTO cbgto : listConBalanceGeneralTO) {
                ReporteBalanceGeneral reporteBalanceGeneral = new ReporteBalanceGeneral();
                reporteBalanceGeneral.setCodigoCP(codigoSector);
                reporteBalanceGeneral.setFechaHasta(fechaHasta);
                reporteBalanceGeneral.setBgCuenta(cbgto.getBgCuenta());
                reporteBalanceGeneral.setBgDetalle(cbgto.getBgDetalle());
                reporteBalanceGeneral.setBgSaldo1(cbgto.getBgSaldo());
                listaReporteBalanceGeneral.add(reporteBalanceGeneral);
            }
        }
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarReporte(modulo, "reportContabilidadBalanceGeneral.jrxml",
                usuarioEmpresaReporteTO, parametros, listaReporteBalanceGeneral);
    }

    @Override
    public byte[] generarReporteBalanceGeneralComparativo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaAnterior, String fechaActual, String codigoSector,
            List<ConBalanceGeneralComparativoTO> listConBalanceGeneralComparativoTO) throws Exception {

        List<ReporteBalanceGeneralComparativo> listaReporteBalanceGeneralComparativos = new ArrayList<ReporteBalanceGeneralComparativo>();
        for (ConBalanceGeneralComparativoTO cbgcto : listConBalanceGeneralComparativoTO) {
            ReporteBalanceGeneralComparativo reporteBalanceGeneralComparativo = new ReporteBalanceGeneralComparativo();
            reporteBalanceGeneralComparativo.setCodigoCP(codigoSector);
            reporteBalanceGeneralComparativo.setFechaAnterior(fechaAnterior);
            reporteBalanceGeneralComparativo.setFechaActual(fechaActual);
            reporteBalanceGeneralComparativo.setBgCuenta(cbgcto.getBgCuenta());
            reporteBalanceGeneralComparativo.setBgDetalle(cbgcto.getBgDetalle());
            reporteBalanceGeneralComparativo.setBgSaldoAnterior(cbgcto.getBgSaldoAnterior());
            reporteBalanceGeneralComparativo.setBgSaldoActual(cbgcto.getBgSaldoActual());
            listaReporteBalanceGeneralComparativos.add(reporteBalanceGeneralComparativo);
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarReporte(modulo, "reportContabilidadBalanceGeneralComparativo.jrxml",
                usuarioEmpresaReporteTO, parametros, listaReporteBalanceGeneralComparativos);
    }

    @Override
    public byte[] generarReporteBalanceResultado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String codigoSector, Integer columnasEstadosFinancieros,
            List<ConFunBalanceResultadosNecTO> listConFunBalanceResultadosNecTO) throws Exception {

        List<ReporteConFunBalanceResultadosNec> reporteConFunBalanceResultadosNecs = new ArrayList<ReporteConFunBalanceResultadosNec>();
        for (ConFunBalanceResultadosNecTO cfbrnto : listConFunBalanceResultadosNecTO) {
            ReporteConFunBalanceResultadosNec reporteConFunBalanceResultadosNec = new ReporteConFunBalanceResultadosNec();
            reporteConFunBalanceResultadosNec.setFechaDesde(fechaDesde);
            reporteConFunBalanceResultadosNec.setFechaHasta(fechaHasta);
            reporteConFunBalanceResultadosNec.setCodigoCP(codigoSector);
            reporteConFunBalanceResultadosNec.setBrCuenta(cfbrnto.getBrCuenta());
            reporteConFunBalanceResultadosNec.setBrDetalle(cfbrnto.getBrDetalle());
            reporteConFunBalanceResultadosNec.setBrSaldo1(cfbrnto.getBrSaldo1());
            reporteConFunBalanceResultadosNecs.add(reporteConFunBalanceResultadosNec);
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarReporte(modulo,
                columnasEstadosFinancieros == 0 ? "reportContabilidadBalanceResultadosNec.jrxml"
                        : "reportContabilidadBalanceResultados.jrxml",
                usuarioEmpresaReporteTO, parametros, reporteConFunBalanceResultadosNecs);
    }

    @Override
    public byte[] generarReporteBalanceResultadoComparativo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String fechaDesde2, String fechaHasta2, String codigoSector,
            List<ConBalanceResultadoComparativoTO> conBalanceResultadoComparativoTO) throws Exception {

        List<ReporteBalanceResultadosComparativo> listReporteBalanceResultadosComparativo = new ArrayList<ReporteBalanceResultadosComparativo>();
        for (ConBalanceResultadoComparativoTO brcto : conBalanceResultadoComparativoTO) {
            ReporteBalanceResultadosComparativo reporteConFunBalanceResultadosComparativo = new ReporteBalanceResultadosComparativo();
            reporteConFunBalanceResultadosComparativo.setCodigoCP(codigoSector);
            reporteConFunBalanceResultadosComparativo.setFechaDesde(fechaDesde);
            reporteConFunBalanceResultadosComparativo.setFechaHasta(fechaHasta);
            reporteConFunBalanceResultadosComparativo.setFechaDesde2(fechaDesde2);
            reporteConFunBalanceResultadosComparativo.setFechaHasta2(fechaHasta2);
            reporteConFunBalanceResultadosComparativo.setBrcCuenta(brcto.getBrcCuenta());
            reporteConFunBalanceResultadosComparativo.setBrcDetalle(brcto.getBrcDetalle());
            reporteConFunBalanceResultadosComparativo.setBrcSaldo(brcto.getBrcSaldo());
            reporteConFunBalanceResultadosComparativo.setBrcSaldo2(brcto.getBrcSaldo2());
            listReporteBalanceResultadosComparativo.add(reporteConFunBalanceResultadosComparativo);
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarReporte(modulo, "reportContabilidadBalanceResultadosComparativo.jrxml",
                usuarioEmpresaReporteTO, parametros, listReporteBalanceResultadosComparativo);
    }

    @Override
    public byte[] generarReporteContablesVerificacionesCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<ConFunContablesVerificacionesComprasTO> listConFunContablesVerificacionesComprasTO) throws Exception {

        List<ReporteFunContablesVerificacionesCompras> listaFunContablesVerificacionesComprases = new ArrayList<ReporteFunContablesVerificacionesCompras>();
        for (ConFunContablesVerificacionesComprasTO cfcvcto : listConFunContablesVerificacionesComprasTO) {
            ReporteFunContablesVerificacionesCompras reporteFunContablesVerificacionesCompras = new ReporteFunContablesVerificacionesCompras();
            reporteFunContablesVerificacionesCompras.setFechaDesde(fechaDesde);
            reporteFunContablesVerificacionesCompras.setFechaHasta(fechaHasta);
            reporteFunContablesVerificacionesCompras.setContabilidadFecha(cfcvcto.getContabilidadFecha());
            reporteFunContablesVerificacionesCompras.setContabilidadSecuencial(cfcvcto.getContabilidadSecuencial());
            reporteFunContablesVerificacionesCompras.setInventarioFecha(cfcvcto.getInventarioFecha());
            reporteFunContablesVerificacionesCompras.setInventarioMonto(cfcvcto.getInventarioMonto());
            reporteFunContablesVerificacionesCompras.setInventarioSecuencial(cfcvcto.getInventarioSecuencial());
            reporteFunContablesVerificacionesCompras.setInventarioObservacion(cfcvcto.getInventarioObservacion());
            listaFunContablesVerificacionesComprases.add(reporteFunContablesVerificacionesCompras);
        }

        return genericReporteService.generarReporte(modulo, "reporteFunContablesVerificacionesCompras.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaFunContablesVerificacionesComprases);
    }

    @Override
    public File generarReporteContablesVerificacionesComprasFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<ConFunContablesVerificacionesComprasTO> listConFunContablesVerificacionesComprasTO) throws Exception {

        List<ReporteFunContablesVerificacionesCompras> listaFunContablesVerificacionesComprases = new ArrayList<>();
        for (ConFunContablesVerificacionesComprasTO cfcvcto : listConFunContablesVerificacionesComprasTO) {
            ReporteFunContablesVerificacionesCompras reporteFunContablesVerificacionesCompras = new ReporteFunContablesVerificacionesCompras();
            reporteFunContablesVerificacionesCompras.setFechaDesde(fechaDesde);
            reporteFunContablesVerificacionesCompras.setFechaHasta(fechaHasta);
            reporteFunContablesVerificacionesCompras.setContabilidadFecha(cfcvcto.getContabilidadFecha());
            reporteFunContablesVerificacionesCompras.setContabilidadSecuencial(cfcvcto.getContabilidadSecuencial());
            reporteFunContablesVerificacionesCompras.setInventarioFecha(cfcvcto.getInventarioFecha());
            reporteFunContablesVerificacionesCompras.setInventarioMonto(cfcvcto.getInventarioMonto());
            reporteFunContablesVerificacionesCompras.setInventarioSecuencial(cfcvcto.getInventarioSecuencial());
            reporteFunContablesVerificacionesCompras.setInventarioObservacion(cfcvcto.getInventarioObservacion());
            listaFunContablesVerificacionesComprases.add(reporteFunContablesVerificacionesCompras);
        }

        return genericReporteService.generarFile(modulo, "reporteFunContablesVerificacionesCompras.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaFunContablesVerificacionesComprases);
    }

    @Override
    public byte[] generarReporteConListaBalanceResultadosVsInventario(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<ConListaBalanceResultadosVsInventarioTO> listConListaBalanceResultadosVsInventarioTO)
            throws Exception {

        List<ReporteConListaBalanceResultadosVsInventario> lista = new ArrayList<ReporteConListaBalanceResultadosVsInventario>();
        for (ConListaBalanceResultadosVsInventarioTO clbrvito : listConListaBalanceResultadosVsInventarioTO) {
            ReporteConListaBalanceResultadosVsInventario reporteSaldoBodegaDetallado = new ReporteConListaBalanceResultadosVsInventario(
                    fechaDesde, fechaHasta,
                    clbrvito.getVriCuentaContable() == null ? "" : clbrvito.getVriCuentaContable(),
                    clbrvito.getVriNombre() == null ? "" : clbrvito.getVriNombre(), clbrvito.getVriSaldoContable(),
                    clbrvito.getVriInventarioInicial(), clbrvito.getVriDiferencia());
            lista.add(reporteSaldoBodegaDetallado);
        }

        return genericReporteService.generarReporte(modulo, "reportBalanceResultadoVSInventario.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);

    }

    @Override
    public byte[] generarReporteConBalanceResultadosVsInventarioDetalladoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<ConBalanceResultadosVsInventarioDetalladoTO> listConListaBalanceResultadosVsInventarioTO)
            throws Exception {

        List<ReporteConListaBalanceResultadosVsInventario> lista = new ArrayList<>();
        for (ConBalanceResultadosVsInventarioDetalladoTO clbrvito : listConListaBalanceResultadosVsInventarioTO) {
            ReporteConListaBalanceResultadosVsInventario reporteSaldoBodegaDetallado = new ReporteConListaBalanceResultadosVsInventario(
                    fechaDesde, fechaHasta,
                    clbrvito.getVriCuentaContable() == null ? "" : clbrvito.getVriCuentaContable(),
                    clbrvito.getVriNombre() == null ? "" : clbrvito.getVriNombre(), clbrvito.getVriSaldoContable(),
                    clbrvito.getVriInventarioInicial(), clbrvito.getVriDiferencia());
            lista.add(reporteSaldoBodegaDetallado);
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarReporte(modulo, "reportBalanceResultadoVSInventario.jrxml", usuarioEmpresaReporteTO, parametros, lista);
    }

    @Override
    public File generarReporteConBalanceResultadosVsInventarioDetalladoTOFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<ConBalanceResultadosVsInventarioDetalladoTO> listConListaBalanceResultadosVsInventarioTO)
            throws Exception {

        List<ReporteConListaBalanceResultadosVsInventario> lista = new ArrayList<>();
        for (ConBalanceResultadosVsInventarioDetalladoTO clbrvito : listConListaBalanceResultadosVsInventarioTO) {
            ReporteConListaBalanceResultadosVsInventario reporteSaldoBodegaDetallado = new ReporteConListaBalanceResultadosVsInventario(
                    fechaDesde, fechaHasta,
                    clbrvito.getVriCuentaContable() == null ? "" : clbrvito.getVriCuentaContable(),
                    clbrvito.getVriNombre() == null ? "" : clbrvito.getVriNombre(), clbrvito.getVriSaldoContable(),
                    clbrvito.getVriInventarioInicial(), clbrvito.getVriDiferencia());
            lista.add(reporteSaldoBodegaDetallado);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarFile(modulo, "reportBalanceResultadoVSInventario.jrxml", usuarioEmpresaReporteTO, parametros, lista);
    }

    @Override
    public byte[] generarReporteContablesVerificacionesErrores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO) throws Exception {

        List<ReporteContablesVerificacion> lista = new ArrayList<ReporteContablesVerificacion>();
        for (ConFunContablesVerificacionesTO cfcvto : listConFunContablesVerificacionesTO) {
            ReporteContablesVerificacion reporteContablesVerificacion = new ReporteContablesVerificacion();
            reporteContablesVerificacion.setVcPeriodo(cfcvto.getVcPeriodo());
            reporteContablesVerificacion.setVcTipo(cfcvto.getVcTipo());
            reporteContablesVerificacion.setVcNumero(cfcvto.getVcNumero());
            reporteContablesVerificacion.setVcFecha(cfcvto.getVcFecha());
            reporteContablesVerificacion.setVcPendiente(cfcvto.getVcPendiente() ? "Sí" : "No");
            reporteContablesVerificacion.setVcBloqueado(cfcvto.getVcBloqueado() ? "Sí" : "No");
            reporteContablesVerificacion.setVcAnulado(cfcvto.getVcAnulado() ? "Sí" : "No");
            reporteContablesVerificacion.setVcDebitos(cfcvto.getVcDebitos());
            reporteContablesVerificacion.setVcCreditos(cfcvto.getVcCreditos());
            reporteContablesVerificacion.setVcObservaciones(cfcvto.getVcObservaciones());
            lista.add(reporteContablesVerificacion);
        }
        return genericReporteService.generarReporte(modulo, "ReporteContablesVerificacionesErrores.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public File generarReporteContablesVerificacionesErroresFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO) throws Exception {

        List<ReporteContablesVerificacion> lista = new ArrayList<>();
        for (ConFunContablesVerificacionesTO cfcvto : listConFunContablesVerificacionesTO) {
            ReporteContablesVerificacion reporteContablesVerificacion = new ReporteContablesVerificacion();
            reporteContablesVerificacion.setVcPeriodo(cfcvto.getVcPeriodo());
            reporteContablesVerificacion.setVcTipo(cfcvto.getVcTipo());
            reporteContablesVerificacion.setVcNumero(cfcvto.getVcNumero());
            reporteContablesVerificacion.setVcFecha(cfcvto.getVcFecha());
            reporteContablesVerificacion.setVcPendiente(cfcvto.getVcPendiente() ? "Sí" : "No");
            reporteContablesVerificacion.setVcBloqueado(cfcvto.getVcBloqueado() ? "Sí" : "No");
            reporteContablesVerificacion.setVcAnulado(cfcvto.getVcAnulado() ? "Sí" : "No");
            reporteContablesVerificacion.setVcDebitos(cfcvto.getVcDebitos());
            reporteContablesVerificacion.setVcCreditos(cfcvto.getVcCreditos());
            reporteContablesVerificacion.setVcObservaciones(cfcvto.getVcObservaciones());
            lista.add(reporteContablesVerificacion);
        }
        return genericReporteService.generarFile(modulo, "ReporteContablesVerificacionesErrores.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteCompraContableDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteCompraDetalle> reporteCompraDetalles, List<ConContableReporteTO> list) throws Exception {

        List<ReporteCompraContableDetalle> listReporteCompraContableDetalle = new ArrayList<ReporteCompraContableDetalle>();
        ReporteCompraContableDetalle reporteCompraContableDetalle = new ReporteCompraContableDetalle();
        for (ConContableReporteTO ccrto : list) {
            if (ccrto != null) {
                for (int i = 0; i < ccrto.getListConDetalleTablaTO().size(); i++) {

                    // /////////TITULO REPORTE
                    reporteCompraContableDetalle.setTitulo(ccrto.getTitulo());
                    // /////////CABECERA
                    reporteCompraContableDetalle.setEmpCodigo(ccrto.getConContableTO().getEmpCodigo());
                    reporteCompraContableDetalle.setPerCodigo(ccrto.getConContableTO().getPerCodigo());
                    reporteCompraContableDetalle.setTipCodigo(ccrto.getConContableTO().getTipCodigo());
                    reporteCompraContableDetalle.setConNumero(ccrto.getConContableTO().getConNumero());
                    reporteCompraContableDetalle.setConFecha(ccrto.getConContableTO().getConFecha());
                    reporteCompraContableDetalle.setConPendiente(ccrto.getConContableTO().getConPendiente());
                    reporteCompraContableDetalle.setConRevisado(ccrto.getConContableTO().getConAnulado());
                    reporteCompraContableDetalle.setConAnulado(ccrto.getConContableTO().getConAnulado());
                    reporteCompraContableDetalle.setConGenerado(ccrto.getConContableTO().getConGenerado());
                    reporteCompraContableDetalle.setConConcepto(ccrto.getConContableTO().getConConcepto());
                    reporteCompraContableDetalle.setConDetalle(ccrto.getConContableTO().getConDetalle());
                    reporteCompraContableDetalle.setConObservaciones(ccrto.getConContableTO().getConObservaciones());
                    reporteCompraContableDetalle.setUsrInserta(ccrto.getConContableTO().getUsrInsertaContable());
                    reporteCompraContableDetalle.setUsrFechaInserta(ccrto.getConContableTO().getUsrFechaInsertaContable());

                    reporteCompraContableDetalle.setImpElaborado(usuarioDetalleService.getUsuarioNombreApellido(ccrto.getConContableTO().getUsrInsertaContable()));

                    reporteCompraContableDetalle.setImpAprobado(usuarioEmpresaReporteTO.getEmpGerente());
                    reporteCompraContableDetalle.setImpRevisado("");
                    reporteCompraContableDetalle.setImpContabilidad(usuarioEmpresaReporteTO.getEmpContador());
                    // ///////////DETALLE
                    reporteCompraContableDetalle.setConCtaCodigoPadre(ccrto.getCuentaPadre().get(i).trim().toString().substring(0, ccrto.getCuentaPadre().get(i).trim().toString().indexOf("|")).trim());
                    reporteCompraContableDetalle.setConNombreCuentaPadre(ccrto.getCuentaPadre().get(i).trim().toString().substring(ccrto.getCuentaPadre().get(i).trim().toString().indexOf("|") + 1).trim());
                    reporteCompraContableDetalle.setConCtaCodigo(ccrto.getListConDetalleTablaTO().get(i).getCtaCodigo());
                    reporteCompraContableDetalle.setConNombreCuenta(ccrto.getListConDetalleTablaTO().get(i).getCtaDetalle());
                    reporteCompraContableDetalle.setDetCentroProduccion(ccrto.getListConDetalleTablaTO().get(i).getSecCodigo());
                    reporteCompraContableDetalle.setDetCentroCosto(ccrto.getListConDetalleTablaTO().get(i).getPisNumero() == null ? "" : ccrto.getListConDetalleTablaTO().get(i).getPisNumero());
                    reporteCompraContableDetalle.setDetDocumento(ccrto.getListConDetalleTablaTO().get(i).getDetDocumento() == null ? "" : ccrto.getListConDetalleTablaTO().get(i).getDetDocumento());
                    if (ccrto.getListConDetalleTablaTO().get(i).getDetDebitos() != null || ccrto.getListConDetalleTablaTO().get(i).getDetDebitos().compareTo(new BigDecimal("0.00")) != 0) {
                        reporteCompraContableDetalle.setDetDebitoCredito('D');
                        reporteCompraContableDetalle.setDetValor(ccrto.getListConDetalleTablaTO().get(i).getDetDebitos());
                    } else {
                        reporteCompraContableDetalle.setDetDebitoCredito('C');
                        reporteCompraContableDetalle.setDetValor(ccrto.getListConDetalleTablaTO().get(i).getDetCreditos());
                    }
                    reporteCompraContableDetalle.setDetGenerado(ccrto.getListConDetalleTablaTO().get(i).getDetGenerado());
                    reporteCompraContableDetalle.setDetReferencia(ccrto.getListConDetalleTablaTO().get(i).getDetReferencia());
                    reporteCompraContableDetalle.setDetOrden(i + 1);

                    // //////////////////////////////////////Compras Detalle
                    reporteCompraContableDetalle.setMotCodigo(reporteCompraDetalles.get(0).getMotCodigo());
                    reporteCompraContableDetalle.setCompNumero(reporteCompraDetalles.get(0).getCompNumero());
                    reporteCompraContableDetalle.setCompDocumentoTipo(reporteCompraDetalles.get(0).getCompDocumentoTipo());
                    reporteCompraContableDetalle.setCompDocumentoNumero(reporteCompraDetalles.get(0).getCompDocumentoNumero());
                    reporteCompraContableDetalle.setProvCodigo(reporteCompraDetalles.get(0).getProvCodigo());
                    reporteCompraContableDetalle.setProvNombre(reporteCompraDetalles.get(0).getProvRazonSocial());
                    reporteCompraContableDetalle.setCompFecha(reporteCompraDetalles.get(0).getCompFecha());
                    reporteCompraContableDetalle.setCompFechaVencimiento(reporteCompraDetalles.get(0).getCompFechaVencimiento());
                    reporteCompraContableDetalle.setCompIvaVigente(reporteCompraDetalles.get(0).getCompIvaVigente());
                    reporteCompraContableDetalle.setCompObservaciones(reporteCompraDetalles.get(0).getCompObservaciones());
                    reporteCompraContableDetalle.setCompPendiente(reporteCompraDetalles.get(0).getCompPendiente());
                    reporteCompraContableDetalle.setCompRevisado(reporteCompraDetalles.get(0).getCompRevisado());
                    reporteCompraContableDetalle.setCompAnulado(reporteCompraDetalles.get(0).getCompAnulado());
                    reporteCompraContableDetalle.setCompFormaPago(reporteCompraDetalles.get(0).getCompFormaPago());
                    reporteCompraContableDetalle.setCompBase0(reporteCompraDetalles.get(0).getCompBase0());
                    reporteCompraContableDetalle.setCompBaseimponible(reporteCompraDetalles.get(0).getCompBaseimponible());
                    reporteCompraContableDetalle.setCompMontoiva(reporteCompraDetalles.get(0).getCompMontoiva());
                    reporteCompraContableDetalle.setCompTotal(reporteCompraDetalles.get(0).getCompTotal());
                    reporteCompraContableDetalle.setCompValorretenido(reporteCompraDetalles.get(0).getCompValorretenido());
                    reporteCompraContableDetalle.setCompSaldo(reporteCompraDetalles.get(0).getCompSaldo());
                    reporteCompraContableDetalle.setCodigoSector(reporteCompraDetalles.get(0).getCodigoSector());
                    reporteCompraContableDetalle.setContPeriodo(reporteCompraDetalles.get(0).getContPeriodo());
                    reporteCompraContableDetalle.setContTipo(reporteCompraDetalles.get(0).getContTipo());
                    reporteCompraContableDetalle.setContNumero(reporteCompraDetalles.get(0).getContNumero());
                    reporteCompraContableDetalle.setUsrInsertaCompra(reporteCompraDetalles.get(0).getUsrInsertaCompra());
                    reporteCompraContableDetalle.setUsrFechaInsertaCompra(reporteCompraDetalles.get(0).getUsrFechaInsertaCompra());
                    reporteCompraContableDetalle.setCompElectronica(reporteCompraDetalles.get(0).getCompElectronica());
                    reporteCompraContableDetalle.setBodCodigo(reporteCompraDetalles.get(0).getBodCodigo());
                    reporteCompraContableDetalle.setProCodigoPrincipal(reporteCompraDetalles.get(0).getProCodigoPrincipal());
                    reporteCompraContableDetalle.setProNombre(reporteCompraDetalles.get(0).getProNombre());
                    reporteCompraContableDetalle.setDetCantidad(reporteCompraDetalles.get(0).getDetCantidad());
                    reporteCompraContableDetalle.setDetMedida(reporteCompraDetalles.get(0).getDetMedida());
                    reporteCompraContableDetalle.setDetPrecio(reporteCompraDetalles.get(0).getDetPrecio());
                    reporteCompraContableDetalle.setDetTotal(reporteCompraDetalles.get(0).getDetTotal());
                    reporteCompraContableDetalle.setDetPendiente(reporteCompraDetalles.get(0).getDetPendiente());
                    reporteCompraContableDetalle.setDetIva(reporteCompraDetalles.get(0).getDetIva());
                    reporteCompraContableDetalle.setSecCodigo(reporteCompraDetalles.get(0).getSecCodigo());
                    reporteCompraContableDetalle.setPisNumero(reporteCompraDetalles.get(0).getPisNumero());
                    reporteCompraContableDetalle.setDetFecha(reporteCompraDetalles.get(0).getDetFecha());
                    reporteCompraContableDetalle.setValorUltimaCompra(reporteCompraDetalles.get(0).getValorUltimaCompra());
                    reporteCompraContableDetalle.setDetCantidadCaja(reporteCompraDetalles.get(0).getDetCantidadCaja());
                    reporteCompraContableDetalle.setDetEmpaque(reporteCompraDetalles.get(0).getDetEmpaque());
                    reporteCompraContableDetalle.setDetPresentacionUnidad(reporteCompraDetalles.get(0).getDetPresentacionUnidad());
                    reporteCompraContableDetalle.setDetPresentacionCaja(reporteCompraDetalles.get(0).getDetPresentacionCaja());

                    listReporteCompraContableDetalle.add(reporteCompraContableDetalle);
                }
            }
        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteCompraContable.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), listReporteCompraContableDetalle);
    }

    //reporteh
    @Override
    public List<ConContableReporteTO> generarListaConContableReporteTO(List<ConContablePK> listContable, SisInfoTO usuario) throws Exception {
        short estGrupo1 = 0;
        short estGrupo2 = 0;
        short estGrupo3 = 0;
        short estGrupo4 = 0;
        short estGrupo5 = 0;
        short estGrupo6 = 0;
        List<ConContableReporteTO> list = new ArrayList<>();
        List<ConEstructuraTO> lista = estructuraService.getListaConEstructuraTO(listContable.get(0).getConEmpresa());
        estGrupo1 = lista.get(0).getEstGrupo1();
        estGrupo2 = lista.get(0).getEstGrupo2();
        estGrupo3 = lista.get(0).getEstGrupo3();
        estGrupo4 = lista.get(0).getEstGrupo4();
        estGrupo5 = lista.get(0).getEstGrupo5();
        estGrupo6 = lista.get(0).getEstGrupo6();
        for (ConContablePK conPK : listContable) {
            ConContableTO contable = contableService.getListaConContableTO(conPK.getConEmpresa(), conPK.getConPeriodo(), conPK.getConTipo(), conPK.getConNumero()).get(0);
            List<ConDetalleTablaTO> detalleContable = detalleService.getListaConDetalleTO(conPK.getConEmpresa(), conPK.getConPeriodo(), conPK.getConTipo(), conPK.getConNumero());
            String tipoDetalle = tipoService.getConTipoTO(conPK.getConEmpresa(), contable.getTipCodigo()).getTipDetalle();
            List<String> cuentaPadre = new ArrayList<>();
            for (ConDetalleTablaTO dc : detalleContable) {
                String cuenta = dc.getCtaCodigo().trim().substring(0,
                        ((estGrupo2 == 0 ? 0 : estGrupo1) + (estGrupo3 == 0 ? 0 : estGrupo2)
                        + (estGrupo4 == 0 ? 0 : estGrupo3) + (estGrupo5 == 0 ? 0 : estGrupo4)
                        + (estGrupo6 == 0 ? 0 : estGrupo5)));

                String nombre = this.cuentasService.buscarDetalleContablePadre(conPK.getConEmpresa(), cuenta);
                //String nombre = UtilsContable.buscarDetalleContablePadre(cuenta, conPK.getConEmpresa(), usuario, cuentasService.getListaBuscarConCuentasTO(conPK.getConEmpresa(), cuenta, null));
                cuentaPadre.add(cuenta + " | " + nombre);
            }
            list.add(new ConContableReporteTO(tipoDetalle, cuentaPadre, contable, detalleContable));
        }
        return list;
    }

    @Override
    public void generarTXT(List<ConCuentasTO> listadoConCuentasTO, HttpServletResponse response) throws Exception {
        try {
            File archivoCreado = File.createTempFile("xxx", ".txt");
            FileOutputStream fos = new FileOutputStream(archivoCreado);
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

            for (ConCuentasTO conCuentaTO : listadoConCuentasTO) {
                out.write(conCuentaTO.getCuentaCodigo() + "|" + conCuentaTO.getCuentaDetalle() + "\n");
            }
            out.close();
            fos.close();

            ServletOutputStream servletStream = response.getOutputStream();
            InputStream in = new FileInputStream(archivoCreado);
            int bit;
            while ((bit = in.read()) != -1) {
                servletStream.write(bit);
            }
            servletStream.flush();
            servletStream.close();
            in.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public byte[] generarReporteBalanceGeneralNecTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta, String codigoSector, List<ConFunBalanceGeneralNecTO> listConBalanceGeneralNecTO) throws Exception {

        List<ReporteBalanceGeneral> listaReporteBalanceGeneral = new ArrayList<ReporteBalanceGeneral>();
        for (ConFunBalanceGeneralNecTO cfbgnto : listConBalanceGeneralNecTO) {
            ReporteBalanceGeneral reporteBalanceGeneral = new ReporteBalanceGeneral();
            reporteBalanceGeneral.setCodigoCP(codigoSector);
            reporteBalanceGeneral.setFechaHasta(fechaHasta);
            reporteBalanceGeneral.setBgCuenta(cfbgnto.getBgCuenta());
            reporteBalanceGeneral.setBgDetalle(cfbgnto.getBgDetalle());
            reporteBalanceGeneral.setBgSaldo1(cfbgnto.getBgSaldo1());
            listaReporteBalanceGeneral.add(reporteBalanceGeneral);
        }

        List<ConEstructuraTO> conEstructuraTOs = estructuraDao.getListaConEstructuraTO(usuarioEmpresaReporteTO.getEmpCodigo());
        int tamaño_cuenta = conEstructuraTOs.get(0).getEstGrupo1() + conEstructuraTOs.get(0).getEstGrupo2()
                + conEstructuraTOs.get(0).getEstGrupo3() + conEstructuraTOs.get(0).getEstGrupo4()
                + conEstructuraTOs.get(0).getEstGrupo5() + conEstructuraTOs.get(0).getEstGrupo6();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        parametros.put("tamaño_cuenta", tamaño_cuenta);
        return genericReporteService.generarReporte(modulo, "reportContabilidadBalanceGeneral.jrxml",
                usuarioEmpresaReporteTO, parametros, listaReporteBalanceGeneral);
    }

    @Override
    public Map<String, Object> generarReporteEstadoSituacionFinancieraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunBalanceGeneralNecTO> listado, String fechaHasta, String sector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("TEstado de Situación Financiera");
            listaCabecera.add("TCP: " + sector + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo");
            for (ConFunBalanceGeneralNecTO conFunBalanceGeneralNecTO : listado) {
                listaCuerpo.add((conFunBalanceGeneralNecTO.getBgCuenta() == null ? "B"
                        : "S" + conFunBalanceGeneralNecTO.getBgCuenta())
                        + "¬"
                        + (conFunBalanceGeneralNecTO.getBgDetalle() == null ? "B"
                        : "S" + conFunBalanceGeneralNecTO.getBgDetalle())
                        + "¬" + (conFunBalanceGeneralNecTO.getBgSaldo1() == null ? "B"
                        : "D" + conFunBalanceGeneralNecTO.getBgSaldo1().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteBalanceResultadoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta,
            String codigoSector, Integer columnasEstadosFinancieros, List<ConFunBalanceResultadosNecTO> listConFunBalanceResultadosNecTO) throws Exception {
        List<ReporteConFunBalanceResultadosNec> reporteConFunBalanceResultadosNecs = new ArrayList<ReporteConFunBalanceResultadosNec>();
        for (ConFunBalanceResultadosNecTO cfbrnto : listConFunBalanceResultadosNecTO) {

            ReporteConFunBalanceResultadosNec reporteConFunBalanceResultadosNec = new ReporteConFunBalanceResultadosNec();
            reporteConFunBalanceResultadosNec.setFechaDesde(fechaDesde);
            reporteConFunBalanceResultadosNec.setFechaHasta(fechaHasta);
            reporteConFunBalanceResultadosNec.setCodigoCP(codigoSector);
            reporteConFunBalanceResultadosNec.setBrCuenta(cfbrnto.getBrCuenta());
            reporteConFunBalanceResultadosNec.setBrDetalle(cfbrnto.getBrDetalle());
            reporteConFunBalanceResultadosNec.setBrSaldo1(cfbrnto.getBrSaldo1());
            reporteConFunBalanceResultadosNecs.add(reporteConFunBalanceResultadosNec);
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarReporte(modulo,
                columnasEstadosFinancieros == 0 ? "reportContabilidadBalanceResultadosNec.jrxml" : "reportContabilidadBalanceResultados.jrxml",
                usuarioEmpresaReporteTO, parametros, reporteConFunBalanceResultadosNecs);
    }

    @Override
    public Map<String, Object> exportarReporteEstadoResultadoIntegralTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunBalanceResultadosNecTO> listConListaConBalanceResultadoTO, String fechaDesde, String fechaHasta, String sector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("TEstado de Resultados Integral");
            listaCabecera.add("TCP: " + sector + " Desde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo");
            for (ConFunBalanceResultadosNecTO conFunBalanceResultadosNecTO : listConListaConBalanceResultadoTO) {
                listaCuerpo.add((conFunBalanceResultadosNecTO.getBrCuenta() == null ? "B" : "S" + conFunBalanceResultadosNecTO.getBrCuenta())
                        + "¬" + (conFunBalanceResultadosNecTO.getBrDetalle() == null ? "B" : "S" + conFunBalanceResultadosNecTO.getBrDetalle())
                        + "¬" + (conFunBalanceResultadosNecTO.getBrSaldo1() == null ? "B" : "D" + conFunBalanceResultadosNecTO.getBrSaldo1().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConCuentasSobregiradasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunBalanceGeneralNecTO> listado, String fecha, String codigoSector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SEstado de Situación Financiera");
            listaCabecera.add("TCP: " + codigoSector + " Fecha: " + fecha);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo");
            for (ConFunBalanceGeneralNecTO conBalanceGeneralTO : listado) {
                listaCuerpo.add(
                        (conBalanceGeneralTO.getBgCuenta() == null ? "B" : "S" + conBalanceGeneralTO.getBgCuenta()) + "¬"
                        + (conBalanceGeneralTO.getBgDetalle() == null ? "B" : "S" + conBalanceGeneralTO.getBgDetalle()) + "¬"
                        + (conBalanceGeneralTO.getBgSaldo1() == null ? "B" : "D" + conBalanceGeneralTO.getBgSaldo1().toString())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteConsumosVerificacionesErrores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosEnProcesoErroresConsumo> listConFunConsumoVerificacionesTO) throws Exception {

        List<ReporteConFunConsumoError> lista = new ArrayList<ReporteConFunConsumoError>();

        for (InventarioProductosEnProcesoErroresConsumo cfcvto : listConFunConsumoVerificacionesTO) {
            ReporteConFunConsumoError reporteConsumoVerificacion = new ReporteConFunConsumoError();
            reporteConsumoVerificacion.setIppCompra(cfcvto.getIppCompra());
            reporteConsumoVerificacion.setIppFecha(cfcvto.getIppFecha());
            reporteConsumoVerificacion.setIppProductoCodigo(cfcvto.getIppProductoCodigo());
            reporteConsumoVerificacion.setIppProductoNombre(cfcvto.getIppProductoNombre());
            reporteConsumoVerificacion.setIppValor(cfcvto.getIppValor());
            reporteConsumoVerificacion.setIppSector(cfcvto.getIppSector());
            reporteConsumoVerificacion.setIppPiscina(cfcvto.getIppPiscina());
            lista.add(reporteConsumoVerificacion);
        }
        return genericReporteService.generarReporte(modulo, "ReporteConsumoVerificacionesErrores.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public File generarReporteConsumosVerificacionesErroresFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosEnProcesoErroresConsumo> listConFunConsumoVerificacionesTO) throws Exception {

        List<ReporteConFunConsumoError> lista = new ArrayList<>();

        for (InventarioProductosEnProcesoErroresConsumo cfcvto : listConFunConsumoVerificacionesTO) {
            ReporteConFunConsumoError reporteConsumoVerificacion = new ReporteConFunConsumoError();
            reporteConsumoVerificacion.setIppCompra(cfcvto.getIppCompra());
            reporteConsumoVerificacion.setIppFecha(cfcvto.getIppFecha());
            reporteConsumoVerificacion.setIppProductoCodigo(cfcvto.getIppProductoCodigo());
            reporteConsumoVerificacion.setIppProductoNombre(cfcvto.getIppProductoNombre());
            reporteConsumoVerificacion.setIppValor(cfcvto.getIppValor());
            reporteConsumoVerificacion.setIppSector(cfcvto.getIppSector());
            reporteConsumoVerificacion.setIppPiscina(cfcvto.getIppPiscina());
            lista.add(reporteConsumoVerificacion);
        }
        return genericReporteService.generarFile(modulo, "ReporteConsumoVerificacionesErrores.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public File generarReporteComprasVerificacionesErroresFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosEnProcesoErroresCompra> listConFunConsumoVerificacionesTO) throws Exception {

        List<ReporteConFunConsumoError> lista = new ArrayList<>();

        for (InventarioProductosEnProcesoErroresCompra cfcvto : listConFunConsumoVerificacionesTO) {
            ReporteConFunConsumoError reporteConsumoVerificacion = new ReporteConFunConsumoError();
            reporteConsumoVerificacion.setIppCompra(cfcvto.getIppCompra());
            reporteConsumoVerificacion.setIppFecha(cfcvto.getIppFecha());
            reporteConsumoVerificacion.setIppProductoCodigo(cfcvto.getIppProductoCodigo());
            reporteConsumoVerificacion.setIppProductoNombre(cfcvto.getIppProductoNombre());
            reporteConsumoVerificacion.setIppValor(cfcvto.getIppValor());
            reporteConsumoVerificacion.setIppSector(cfcvto.getIppSector());
            reporteConsumoVerificacion.setIppPiscina(cfcvto.getIppPiscina());
            lista.add(reporteConsumoVerificacion);
        }
        return genericReporteService.generarFile(modulo, "ReporteConsumoVerificacionesErrores.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public Map<String, Object> exportarReporteConsumoErrores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosEnProcesoErroresConsumo> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de errores");
            listaCabecera.add("S");
            listaCuerpo.add("SCompra" + "¬" + "SFecha" + "¬" + "SCódigo del producto" + "¬" + "SNombre de producto" + "¬" + "SSector" + "¬" + "SPiscina" + "¬" + "SValor");
            for (InventarioProductosEnProcesoErroresConsumo conFunVerificacionesTO : listado) {
                listaCuerpo.add(
                        (conFunVerificacionesTO.getIppCompra() == null ? "B" : "S" + conFunVerificacionesTO.getIppCompra()) + "¬"
                        + (conFunVerificacionesTO.getIppFecha() == null ? "B" : "S" + conFunVerificacionesTO.getIppFecha()) + "¬"
                        + (conFunVerificacionesTO.getIppProductoCodigo() == null ? "B" : "S" + conFunVerificacionesTO.getIppProductoCodigo()) + "¬"
                        + (conFunVerificacionesTO.getIppProductoNombre() == null ? "B" : "S" + conFunVerificacionesTO.getIppProductoNombre()) + "¬"
                        + (conFunVerificacionesTO.getIppSector() == null ? "B" : "S" + conFunVerificacionesTO.getIppSector()) + "¬"
                        + (conFunVerificacionesTO.getIppPiscina() == null ? "B" : "S" + conFunVerificacionesTO.getIppPiscina()) + "¬"
                        + (conFunVerificacionesTO.getIppValor() == null ? "B" : "I" + conFunVerificacionesTO.getIppValor()) + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteProductosInconsistencias(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosCuentasInconsistentes> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "ReporteProductosInconsistencias.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] imprimirReporteProyectoFlujoCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> resultado) throws Exception {
        ReporteProyectoFlujo proyecto = new ReporteProyectoFlujo();
        List<ReporteProyectoFlujo> flujo = new ArrayList<>();
        proyecto.setSumaBanco(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaBanco")));
        proyecto.setSumaClientes(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaClientes")));
        proyecto.setSumaProveedores(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaProveedores")));
        proyecto.setSumaCheque(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaCheque")));
        proyecto.setSumaValorizacion(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaValorizacion")));
        proyecto.setSumaEfectivo(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaEfectivo")));
        proyecto.setSumaCuentasPorPagar(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaCuentasPorPagar")));
        proyecto.setSubtotal(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("subtotal")));
        flujo.add(proyecto);
        return genericReporteService.generarReporte(modulo, "reportFlujoCaja.jrxml", usuarioEmpresaReporteTO, resultado, flujo);
    }

    @Override
    public Map<String, Object> exportarReporteProductosInconsistencias(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosCuentasInconsistentes> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de errores");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo del producto" + "¬" + "SNombre de producto" + "¬" + "STipo" + "¬" + "SCuenta" + "¬" + "SDescripcion");
            for (InventarioProductosCuentasInconsistentes conFunVerificacionesTO : listado) {
                listaCuerpo.add(
                        (conFunVerificacionesTO.getErrorProductoCodigo() == null ? "B" : "S" + conFunVerificacionesTO.getErrorProductoCodigo()) + "¬"
                        + (conFunVerificacionesTO.getErrorProductoNombre() == null ? "B" : "S" + conFunVerificacionesTO.getErrorProductoNombre()) + "¬"
                        + (conFunVerificacionesTO.getErrorProductoTipo() == null ? "B" : "S" + conFunVerificacionesTO.getErrorProductoTipo()) + "¬"
                        + (conFunVerificacionesTO.getErrorCuentaCodigo() == null ? "B" : "S" + conFunVerificacionesTO.getErrorCuentaCodigo()) + "¬"
                        + (conFunVerificacionesTO.getErrorCuentaDescripcion() == null ? "B" : "S" + conFunVerificacionesTO.getErrorCuentaDescripcion()) + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteFlujoDeCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> resultado, String hasta) throws Exception {
        try {
            ReporteProyectoFlujo proyecto = new ReporteProyectoFlujo();
            proyecto.setSumaBanco(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaBanco")));
            proyecto.setSumaClientes(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaClientes")));
            proyecto.setSumaProveedores(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaProveedores")));
            proyecto.setSumaCheque(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaCheque")));
            proyecto.setSumaValorizacion(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaValorizacion")));
            proyecto.setSumaEfectivo(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaEfectivo")));
            proyecto.setSumaCuentasPorPagar(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("sumaCuentasPorPagar")));
            proyecto.setSubtotal(UtilsJSON.jsonToObjeto(BigDecimal.class, resultado.get("subtotal")));
            List<ConFlujoCajaTO> listaFlujoCaja = UtilsJSON.jsonToList(ConFlujoCajaTO.class, resultado.get("listaFlujoCaja"));
            List<ConFlujoCajaCuentasPorCobrarClientesTO> listaCuentasPorCobrarClientes = UtilsJSON.jsonToList(ConFlujoCajaCuentasPorCobrarClientesTO.class, resultado.get("listaCuentasPorCobrarClientes"));
            List<ConFlujoCajaCuentasPorPagarProveedoresTO> listaCuentasPorPagarProveedores = UtilsJSON.jsonToList(ConFlujoCajaCuentasPorPagarProveedoresTO.class, resultado.get("listaCuentasPorPagarProveedores"));
            List<ConFlujoCajaChequesPostfechadosProveedoresTO> listaChequesPostfechados = UtilsJSON.jsonToList(ConFlujoCajaChequesPostfechadosProveedoresTO.class, resultado.get("listaChequesPostfechados"));
            List<ConFlujoCajaValorizacionActivoBiologicoTO> listaValorizacion = UtilsJSON.jsonToList(ConFlujoCajaValorizacionActivoBiologicoTO.class, resultado.get("listaValorizacion"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SFlujo de Caja Actual");
            listaCabecera.add("SHasta " + hasta);
            listaCabecera.add("S");
            listaCabecera.add("SIngresos y gastos");
            listaCabecera.add("SEfectivo y equivalentes al efectivo" + "¬" + "D" + proyecto.getSumaEfectivo());
            listaCuerpo.add("SBanco" + "¬" + "D" + proyecto.getSumaBanco());
            if (listaFlujoCaja != null && !listaFlujoCaja.isEmpty()) {
                listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSaldo" + "¬");
                listaFlujoCaja.forEach((flujoCaja) -> {
                    listaCuerpo.add(
                            (flujoCaja.getFcCuenta() == null ? "B" : "S" + flujoCaja.getFcCuenta()) + "¬"
                            + (flujoCaja.getFcDetalle() == null ? "B" : "S" + flujoCaja.getFcDetalle()) + "¬"
                            + (flujoCaja.getFcSaldo() == null ? "B" : "D" + flujoCaja.getFcSaldo())
                    );
                });
            }
            listaCuerpo.add("S");
            listaCuerpo.add("SCuentas por cobrar clientes" + "¬" + "D" + proyecto.getSumaClientes());
            if (listaCuentasPorCobrarClientes != null && !listaCuentasPorCobrarClientes.isEmpty()) {
                listaCuerpo.add("SCliente" + "¬" + "SRazon Social" + "¬" + "SSaldo" + "¬");
                listaCuentasPorCobrarClientes.forEach((cliente) -> {
                    listaCuerpo.add(
                            (cliente.getCxcdClienteId() == null ? "B" : "S" + cliente.getCxcdClienteId()) + "¬"
                            + (cliente.getCxcdClienteRazonSocial() == null ? "B" : "S" + cliente.getCxcdClienteRazonSocial()) + "¬"
                            + (cliente.getCxcdSaldo() == null ? "B" : "D" + cliente.getCxcdSaldo())
                    );
                });
            }
            listaCuerpo.add("S");
            listaCuerpo.add("SCuentas por pagar" + "¬" + "D" + proyecto.getSumaCuentasPorPagar());
            listaCuerpo.add("SProveedores varios" + "¬" + "D" + proyecto.getSumaProveedores());
            if (listaCuentasPorPagarProveedores != null && !listaCuentasPorPagarProveedores.isEmpty()) {
                listaCuerpo.add("SProveedor" + "¬" + "SRazon Social" + "¬" + "SSaldo" + "¬");
                listaCuentasPorPagarProveedores.forEach((proveedor) -> {
                    listaCuerpo.add(
                            (proveedor.getCxpdProveedorId() == null ? "B" : "S" + proveedor.getCxpdProveedorId()) + "¬"
                            + (proveedor.getCxpdProveedorRazonSocial() == null ? "B" : "S" + proveedor.getCxpdProveedorRazonSocial()) + "¬"
                            + (proveedor.getCxpdSaldo() == null ? "B" : "D" + proveedor.getCxpdSaldo())
                    );
                });
            }
            listaCuerpo.add("S");
            listaCuerpo.add("SCheques Postfechados" + "¬" + "D" + proyecto.getSumaCheque());
            if (listaChequesPostfechados != null && !listaChequesPostfechados.isEmpty()) {
                listaCuerpo.add("SCheque" + "¬" + "SBeneficiario" + "¬" + "SFecha" + "¬" + "SValor" + "¬");
                listaChequesPostfechados.forEach((cheque) -> {
                    listaCuerpo.add(
                            (cheque.getChqNumero() == null ? "B" : "S" + cheque.getChqNumero()) + "¬"
                            + (cheque.getChqBeneficiario() == null ? "B" : "S" + cheque.getChqBeneficiario()) + "¬"
                            + (cheque.getChqFecha() == null ? "B" : "S" + cheque.getChqFecha()) + "¬"
                            + (cheque.getChqValor() == null ? "B" : "D" + cheque.getChqValor())
                    );
                });
            }
            listaCuerpo.add("STotal flujo de caja real" + "¬" + "D" + proyecto.getSubtotal());
            listaCuerpo.add("S");
            listaCuerpo.add("S");
            listaCuerpo.add("SIngresos y gastos proyectados");
            listaCuerpo.add("SIngresos proyectados" + "¬" + "D" + proyecto.getSumaValorizacion());
            listaCuerpo.add("SProyeccion de ventas" + "¬" + "D" + proyecto.getSumaValorizacion());
            if (listaValorizacion != null && !listaValorizacion.isEmpty()) {
                listaCuerpo.add("SSector" + "¬" + "SPiscina" + "¬" + "SCorrida" + "¬" + "SFecha" + "¬" + "SGramos promedio"
                        + "¬" + "SSobrevivencia" + "¬" + "SBiomasa" + "¬" + "SVenta" + "¬" + "STalla 1"
                        + "¬" + "STalla1 %" + "¬" + "STalla1 precio" + "¬" + "STalla 2" + "¬" + "STalla2 %" + "¬" + "STalla2 precio"
                );
                listaValorizacion.forEach((venta) -> {
                    listaCuerpo.add(
                            (venta.getFcSector() == null ? "B" : "S" + venta.getFcSector()) + "¬"
                            + (venta.getFcPiscina() == null ? "B" : "S" + venta.getFcPiscina()) + "¬"
                            + (venta.getFcCorrida() == null ? "B" : "S" + venta.getFcCorrida()) + "¬"
                            + (venta.getFcFecha() == null ? "B" : "S" + venta.getFcFecha()) + "¬"
                            + (venta.getFcGramosPromedio() == null ? "B" : "D" + venta.getFcGramosPromedio()) + "¬"
                            + (venta.getFcSobrevivencia() == null ? "B" : "D" + venta.getFcSobrevivencia()) + "¬"
                            + (venta.getFcBiomasa() == null ? "B" : "D" + venta.getFcBiomasa()) + "¬"
                            + (venta.getFcVenta() == null ? "B" : "D" + venta.getFcVenta()) + "¬"
                            + (venta.getFcTalla1() == null ? "B" : "S" + venta.getFcTalla1()) + "¬"
                            + (venta.getFcTalla1Porcentaje() == null ? "B" : "D" + venta.getFcTalla1Porcentaje()) + "¬"
                            + (venta.getFcTalla1Precio() == null ? "B" : "D" + venta.getFcTalla1Precio()) + "¬"
                            + (venta.getFcTalla2() == null ? "B" : "S" + venta.getFcTalla2()) + "¬"
                            + (venta.getFcTalla2Porcentaje() == null ? "B" : "D" + venta.getFcTalla2Porcentaje()) + "¬"
                            + (venta.getFcTalla2Precio() == null ? "B" : "D" + venta.getFcTalla2Precio())
                    );
                });
            }

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarPlantillaContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        List<ConListaContableDetalleTO> listado = UtilsJSON.jsonToList(ConListaContableDetalleTO.class, map.get("listado"));
        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaCabecera = new ArrayList<>();
        List<String> listaCuerpo = new ArrayList<>();
        listaCuerpo.add("SCUENTA" + "¬" + "SDETALLE" + "¬" + "SCP" + "¬" + "SCC" + "¬" + "SDOCUMENTO" + "¬" + "SDÉBITOS" + "¬" + "SCRÉDITOS" + "¬" + "SOBSERVACIONES" + "¬");
        listado.stream().forEach((item) -> {
            listaCuerpo.add(
                    "S" + item.getCtaCodigo()
                    + "¬" + "S" + item.getCtaDetalle()
                    + "¬" + "S" + (item.getSectorSeleccionado() != null ? item.getSectorSeleccionado().getSecCodigo() : "")
                    + "¬" + "S" + (item.getPiscinaSeleccionada() != null ? item.getPiscinaSeleccionada().getPisNumero() : "")
                    + "¬" + "S" + item.getDetDocumento()
                    + "¬" + (item.getDetDebitos() == null ? "B" : "D" + item.getDetDebitos())
                    + "¬" + (item.getDetCreditos() == null ? "B" : "D" + item.getDetCreditos())
                    + "¬" + (item.getDetObservaciones() == null ? "S" : "S" + item.getDetObservaciones())
            );
        });
        respuesta.put("listaCabecera", listaCabecera);
        respuesta.put("listaCuerpo", listaCuerpo);
        return respuesta;
    }

    @Override
    public Map<String, Object> exportarReporteEstadoResultadoCentroProduccion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunBalanceCentroProduccionTO> listEstadoResultadoCentroProduccion, String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("TEstado de resultado integral por centro de producción");
            listaCabecera.add(" Desde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SCentro de produccion" + "¬" + "SSaldo");
            for (ConFunBalanceCentroProduccionTO estadoResultadoCentroProduccion: listEstadoResultadoCentroProduccion) {
                listaCuerpo.add((estadoResultadoCentroProduccion.getBrcCuenta() == null ? "B" : "S" + estadoResultadoCentroProduccion.getBrcCuenta())
                        + "¬" + (estadoResultadoCentroProduccion.getBrcDetalle()== null ? "B" : "S" + estadoResultadoCentroProduccion.getBrcDetalle())
                        + "¬" + (estadoResultadoCentroProduccion.getBrcCentroProduccion() == null ? "B" : "S" + estadoResultadoCentroProduccion.getBrcCentroProduccion())
                        + "¬" + (estadoResultadoCentroProduccion.getBrcSaldo() == null ? "B" : "D" + estadoResultadoCentroProduccion.getBrcSaldo())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }
    
    @Override
    public byte[] generarReporteBalanceResultadoCentroProduccion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<ConFunBalanceCentroProduccionTO> listEstadoResultadoCentroProduccion) throws Exception {
        List<ReporteConFunBalanceResultadosNec> reporteConFunBalanceResultadosNecs = new ArrayList<ReporteConFunBalanceResultadosNec>();
        for (ConFunBalanceCentroProduccionTO estadoResultadoCentroProduccion : listEstadoResultadoCentroProduccion) {

            ReporteConFunBalanceResultadosNec reporteConFunBalanceResultadosNec = new ReporteConFunBalanceResultadosNec();
            reporteConFunBalanceResultadosNec.setFechaDesde(fechaDesde);
            reporteConFunBalanceResultadosNec.setFechaHasta(fechaHasta);
            reporteConFunBalanceResultadosNec.setCodigoCP(estadoResultadoCentroProduccion.getBrcCentroProduccion());
            reporteConFunBalanceResultadosNec.setBrCuenta(estadoResultadoCentroProduccion.getBrcCuenta());
            reporteConFunBalanceResultadosNec.setBrDetalle(estadoResultadoCentroProduccion.getBrcDetalle());
            reporteConFunBalanceResultadosNec.setBrSaldo1(estadoResultadoCentroProduccion.getBrcSaldo());
            reporteConFunBalanceResultadosNecs.add(reporteConFunBalanceResultadosNec);
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("p_contador", usuarioEmpresaReporteTO.getEmpContador());
        parametros.put("p_gerente", usuarioEmpresaReporteTO.getEmpGerente());
        return genericReporteService.generarReporte(modulo,"reportContabilidadBalanceResultadosCentroProduccion.jrxml", usuarioEmpresaReporteTO, parametros, reporteConFunBalanceResultadosNecs);
    }
}
