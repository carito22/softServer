package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.ComisionistaControlDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteLiquidacionComisionista;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionComisionistaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdComisionista;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.InformacionAdicional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ComisionistaControlServiceImpl implements ComisionistaControlService {

    @Autowired
    private ComisionistaControlDao comisionistaControlDao;
    @Autowired
    private GenericReporteService genericReporteService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private String modulo = "produccion";

    @Override
    public String insertarComisionista(PrdComisionista prdComisionista, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        /// PREPARANDO OBJETO SISSUCESO
        susDetalle = "Se insertó comisionista con el codigo: " + prdComisionista.getPrdComisionistaPK().getCoCodigo();
        susClave = prdComisionista.getPrdComisionistaPK().getCoCodigo();
        susSuceso = "INSERT";
        susTabla = "produccion.prd_comisionista";
        prdComisionista.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (comisionistaControlDao.insertarComisionista(prdComisionista, sisSuceso)) {
            retorno = "TEl registro se guardo correctamente...";
        } else {
            retorno = "FHubo un error al guardar el registro...\nIntente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public List<PrdComisionista> listarComisionista(String empresa) throws Exception {
        return comisionistaControlDao.listarComisionista(empresa);
    }

    @Override
    public String actualizarComisionista(PrdComisionista prdComisionista, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susDetalle = "Se modificó el registro con el codigo: " + prdComisionista.getPrdComisionistaPK().getCoCodigo();
        susClave = prdComisionista.getPrdComisionistaPK().getCoCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_comisionista";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdComisionista.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
        if (comisionistaControlDao.actualizarComisionista(prdComisionista, sisSuceso)) {
            retorno = "TEl registro con el Código: " + prdComisionista.getPrdComisionistaPK().getCoCodigo() + ", se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el registro. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    public Map<String, Object> exportarListadoComisionista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdComisionista> listadoComisionista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Comisionistas");
            listaCabecera.add("S");
            listaCuerpo.add("SCodigo" + "¬" + "SNombre" + "¬" + "SCedula" + "¬" + "SDireccion" + "¬" + "SComisión");
            listadoComisionista.forEach((comisionista) -> {
                listaCuerpo.add(
                        (comisionista.getPrdComisionistaPK().getCoCodigo() == null ? "B" : "S" + comisionista.getPrdComisionistaPK().getCoCodigo())
                        + "¬" + (comisionista.getCoNombre() == null ? "B" : "S" + comisionista.getCoNombre())
                        + "¬" + (comisionista.getPrdComisionistaPK().getCoCedula() == null ? "B" : "S" + comisionista.getPrdComisionistaPK().getCoCedula())
                        + "¬" + (comisionista.getCoDireccion() == null ? "B" : "S" + comisionista.getCoDireccion())
                        + "¬" + (comisionista.getCoComision() == null ? "B" : "D" + comisionista.getCoComision())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    public byte[] generarReporteComisionista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdComisionista> listadoComisionista, SisInfoTO sisInfoTO) throws Exception {
        String nombreReporte = "reporteListadoComisionista.jrxml";
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoComisionista);
    }

    public List<PrdLiquidacionComisionistaTO> buscarListadoLiquidacionComisionista(String empresa, String sector, String piscina, String fechaDesde, String fechaHasta, String comisionista, boolean incluirTodos) throws Exception {
        return comisionistaControlDao.buscarListadoLiquidacionComisionista(empresa, sector, piscina, fechaDesde, fechaHasta, comisionista, incluirTodos);
    }

    @Override
    public Map<String, Object> exportarLiquidacionComisionista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdLiquidacionComisionistaTO> listado = UtilsJSON.jsonToList(PrdLiquidacionComisionistaTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidación Comosionista");
            listaCabecera.add("SDesde: " + fechaDesde + "    Hasta: " + fechaHasta);
            listaCabecera.add("TCentro de producción: " + sector + "     Centro de costo: " + piscina);
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "SNúmero" + "¬" + "SPiscina" + "¬" + "SSector" + "¬" + "SFecha" + "¬" + "SProveedor"
                    + "¬" + "SCliente" + "¬" + "SComisionista" + "¬" + "SComisión"
                    + "¬" + "SLote" + "¬" + "SLibras Recibidas"
                    + "¬" + "SLibras Enviadas" + "¬" + "STotal" + "¬" + "SPendiente" + "¬" + "SAnulado");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getLiqMotivo() == null ? "B" : "S" + cons.getLiqMotivo())
                        + "¬" + (cons.getLiqNumero() == null ? "B" : "S" + cons.getLiqNumero())
                        + "¬" + (cons.getLiqPiscina() == null ? "B" : "S" + cons.getLiqPiscina())
                        + "¬" + (cons.getLiqSector() == null ? "B" : "S" + cons.getLiqSector())
                        + "¬" + (cons.getLiqFecha() == null ? "B" : "S" + cons.getLiqFecha())
                        + "¬" + (cons.getLiqProveedorNombre() == null ? "B" : "S" + cons.getLiqProveedorNombre())
                        + "¬" + (cons.getLiqClienteNombre() == null ? "B" : "S" + cons.getLiqClienteNombre())
                        + "¬" + (cons.getLiqComisionistaNombre() == null ? "B" : "S" + cons.getLiqComisionistaNombre())
                        + "¬" + (cons.getLiqComisionistaValor() == null ? "B" : "D" + cons.getLiqComisionistaValor())
                        + "¬" + (cons.getLiqLote() == null ? "B" : "S" + cons.getLiqLote())
                        + "¬" + (cons.getLiqLibrasRecibidas() == null ? "B" : "D" + cons.getLiqLibrasRecibidas())
                        + "¬" + (cons.getLiqLibrasEnviadas() == null ? "B" : "D" + cons.getLiqLibrasEnviadas())
                        + "¬" + (cons.getLiqMontoTotal() == null ? "B" : "D" + cons.getLiqMontoTotal())
                        + "¬" + (cons.getLiqPendiente() == null ? "B" : "S" + cons.getLiqPendiente())
                        + "¬" + (cons.getLiqAnulado() == null ? "B" : "S" + cons.getLiqAnulado()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteLiquidacionComisionista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionComisionistaTO> listado, List<InformacionAdicional> infoAdicional, String fechaInicio, String fechaFin, String centroProduccion, String centroCosto) throws Exception {
        List<ReporteLiquidacionComisionista> listadoReporte = new ArrayList<>();
        for (PrdLiquidacionComisionistaTO item : listado) {
            ReporteLiquidacionComisionista registro = new ReporteLiquidacionComisionista();
            registro.setLiqEmpresa(item.getLiqEmpresa());
            registro.setLiqMotivo(item.getLiqMotivo());
            registro.setLiqNumero(item.getLiqNumero());
            registro.setLiqLote(item.getLiqLote());
            registro.setLiqFecha(item.getLiqFecha());
            registro.setLiqCorrida(item.getLiqCorrida());
            registro.setLiqPiscina(item.getLiqPiscina());
            registro.setLiqSector(item.getLiqSector());
            registro.setLiqPendiente(item.getLiqPendiente());
            registro.setLiqAnulado(item.getLiqAnulado());
            registro.setLiqProveedorNombre(item.getLiqProveedorNombre());
            registro.setLiqProveedorCodigo(item.getLiqProveedorCodigo());
            registro.setLiqClienteNombre(item.getLiqClienteNombre());
            registro.setLiqClienteCodigo(item.getLiqClienteCodigo());
            registro.setLiqComisionistaCodigo(item.getLiqComisionistaCodigo());
            registro.setLiqComisionistaNombre(item.getLiqComisionistaNombre());
            registro.setLiqComisionistaValor(item.getLiqComisionistaValor());
            registro.setLiqLibrasEnviadas(item.getLiqLibrasEnviadas());
            registro.setLiqLibrasEntero(item.getLiqLibrasEntero());
            registro.setLiqLibrasColaProcesadas(item.getLiqLibrasColaProcesadas());
            registro.setFechaFin(fechaFin);
            registro.setFechaInicio(fechaInicio);
            registro.setCentroCosto(centroCosto);
            registro.setCentroProduccion(centroProduccion);
            listadoReporte.add(registro);
        }

        for (InformacionAdicional item : infoAdicional) {
            ReporteLiquidacionComisionista registro = new ReporteLiquidacionComisionista();
            registro.setDescripcion(item.getNombre());
            registro.setValor(item.getValor());
            listadoReporte.add(registro);
        }

        return genericReporteService.generarReporte(modulo, "reportLiquidacionListadComisionista.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listadoReporte);
    }

}
