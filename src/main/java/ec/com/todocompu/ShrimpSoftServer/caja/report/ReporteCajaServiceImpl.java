package ec.com.todocompu.ShrimpSoftServer.caja.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCuadreCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.report.ReporteCajCajaVales;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.Map;

@Service
public class ReporteCajaServiceImpl implements ReporteCajaService {

    @Autowired
    private GenericReporteService genericReporteService;

    @Override
    public byte[] generarReporteCajCuadreDeCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String codigoMotivo, String descripcionMotivo, List<CajCuadreCajaTO> listCajCuadreCajaTOs)
            throws Exception {
        List<ReporteCajCuadreDeCajaDetallado> lista = new ArrayList<ReporteCajCuadreDeCajaDetallado>();
        for (CajCuadreCajaTO obj : listCajCuadreCajaTOs) {
            ReporteCajCuadreDeCajaDetallado reporteCajCuadreDeCajaDetallado = new ReporteCajCuadreDeCajaDetallado();
            reporteCajCuadreDeCajaDetallado.setCuadrarCaja(obj == null ? null : obj.getCuadrarCaja());
            reporteCajCuadreDeCajaDetallado.setCuaCantidad(obj == null ? null : obj.getCuaCantidad());
            reporteCajCuadreDeCajaDetallado.setCuaBase0(obj == null ? null : obj.getCuaBase0());
            reporteCajCuadreDeCajaDetallado.setCuaBaseImponible(obj == null ? null : obj.getCuaBaseImponible());
            reporteCajCuadreDeCajaDetallado.setCuaMontoIva(obj == null ? null : obj.getCuaMontoIva());
            reporteCajCuadreDeCajaDetallado.setCuaTotal(obj == null ? null : obj.getCuaTotal());
            reporteCajCuadreDeCajaDetallado.setCuaFecha(fechaHasta);
            reporteCajCuadreDeCajaDetallado.setCuaMotivo(codigoMotivo);
            reporteCajCuadreDeCajaDetallado.setDescripcionMotivo((codigoMotivo == null ? "" : descripcionMotivo));

            lista.add(reporteCajCuadreDeCajaDetallado);
        }

        return genericReporteService.generarReporte("caja", "reportCajCuadreDeCajaDetallado.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteCajVales(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String descripcionCajaConcepto, String codigoCajaConcepto, CajCajaValesTO cajCajaValesTo) throws Exception {
        List<ReporteCajCajaVales> listareporteCajCajaValeses = new ArrayList<ReporteCajCajaVales>();
        ReporteCajCajaVales reporteCajCajaVales = null;
        if (cajCajaValesTo != null) {
            reporteCajCajaVales = new ReporteCajCajaVales();
            reporteCajCajaVales.setValePeriodo(cajCajaValesTo.getValePeriodo());
            reporteCajCajaVales.setValeMotivo(cajCajaValesTo.getValeMotivo());
            reporteCajCajaVales.setValeNumero(cajCajaValesTo.getValeNumero());
            reporteCajCajaVales.setValeFecha(cajCajaValesTo.getValeFecha());
            reporteCajCajaVales.setValeValor(cajCajaValesTo.getValeValor());
            reporteCajCajaVales.setValeBeneficiario(cajCajaValesTo.getValeBeneficiario());
            reporteCajCajaVales.setValeObservaciones(cajCajaValesTo.getValeObservaciones());
            reporteCajCajaVales.setConcCodigo(descripcionCajaConcepto);
            reporteCajCajaVales.setConcEmpresa(codigoCajaConcepto);
            reporteCajCajaVales.setCajeroNombre(usuarioEmpresaReporteTO.getUsrNick() + " - "
                    + usuarioEmpresaReporteTO.getUsrNombre() + " " + usuarioEmpresaReporteTO.getUsrApellido());

            listareporteCajCajaValeses.add(reporteCajCajaVales);
        }
        return genericReporteService.generarReporte("caja", "reporteCajaVales.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), listareporteCajCajaValeses);
    }

    @Override
    public byte[] generarReporteCajValesListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CajCajaValesTO> listaCajValesListaTOs) throws Exception {
        List<ReporteCajValesListado> lista = new ArrayList<ReporteCajValesListado>();
        for (CajCajaValesTO obj : listaCajValesListaTOs) {
            ReporteCajValesListado reporteCajValesListado = new ReporteCajValesListado();
            reporteCajValesListado.setValePeriodo(obj.getValePeriodo());
            reporteCajValesListado.setValeFecha(obj.getValeFecha());
            reporteCajValesListado.setValeNumero(obj.getValeNumero());
            reporteCajValesListado.setValeValor(obj.getValeValor());
            reporteCajValesListado.setValeBeneficiario(obj.getValeBeneficiario());
            reporteCajValesListado.setValeObservaciones(obj.getValeObservaciones());
            reporteCajValesListado.setValeAnulado(obj.getValeAnulado());

            lista.add(reporteCajValesListado);
        }

        return genericReporteService.generarReporte("caja", "reporteCajValesListado.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);
    }

    @Override
    public Map<String, Object> exportarReporteCajCuadreCajaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CajCuadreCajaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de cuadre de caja");
            listaCabecera.add("S");
            listaCuerpo.add("SCaja" + "¬" + "SCantidad" + "¬" + "SBase 0" + "¬" + "SBase Imponible" + "¬" + "SMonto Iva" + "¬" + "STotal");
            if (listado != null && listado.size() > 0) {
                for (CajCuadreCajaTO cajCuadreCajaTO : listado) {
                    listaCuerpo.add((cajCuadreCajaTO.getCuadrarCaja() == null ? "B"
                            : "S" + cajCuadreCajaTO.getCuadrarCaja())
                            + "¬"
                            + (cajCuadreCajaTO.getCuaCantidad() == null ? "B"
                            : "D" + cajCuadreCajaTO.getCuaCantidad())
                            + "¬"
                            + (cajCuadreCajaTO.getCuaBase0() == null ? "B"
                            : "D" + cajCuadreCajaTO.getCuaBase0())
                            + "¬"
                            + (cajCuadreCajaTO.getCuaBaseImponible() == null ? "B"
                            : "D" + cajCuadreCajaTO.getCuaBaseImponible())
                            + "¬"
                            + (cajCuadreCajaTO.getCuaMontoIva() == null ? "B"
                            : "D" + cajCuadreCajaTO.getCuaMontoIva())
                            + "¬" + (cajCuadreCajaTO.getCuaTotal() == null ? "B"
                            : "D" + cajCuadreCajaTO.getCuaTotal()));
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
    public Map<String, Object> exportarReporteCajValesListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CajCajaValesTO> listaCajValesListaTOs,
             String fechaInicio, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Vales de Caja ");
            listaCabecera.add("SDesde: " + fechaInicio);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "SFecha" + "¬" + "SNúmero" + "¬" + "SValor" + "¬" + "SBeneficiario" + "¬"
                    + "SObservaciones" + "¬" + "SAnulado");
            if (listaCajValesListaTOs != null && listaCajValesListaTOs.size() > 0) {
                for (CajCajaValesTO listaCajCajaValesTO : listaCajValesListaTOs) {
                    listaCuerpo.add((listaCajCajaValesTO.getValePeriodo() == null ? "B"
                            : "S" + listaCajCajaValesTO.getValePeriodo()) + "¬"
                            + (listaCajCajaValesTO.getValeFecha() == null ? "B" : "S" + listaCajCajaValesTO.getValeFecha())
                            + "¬"
                            + (listaCajCajaValesTO.getValeNumero() == null ? "B"
                            : "S" + listaCajCajaValesTO.getValeNumero())
                            + "¬"
                            + (listaCajCajaValesTO.getValeValor() == null ? "B" : "D" + listaCajCajaValesTO.getValeValor())
                            + "¬"
                            + (listaCajCajaValesTO.getValeBeneficiario() == null ? "B"
                            : "S" + listaCajCajaValesTO.getValeBeneficiario())
                            + "¬"
                            + (listaCajCajaValesTO.getValeObservaciones() == null ? "B"
                            : "S" + listaCajCajaValesTO.getValeObservaciones())
                            + "¬" + (listaCajCajaValesTO.getValeAnulado() == null ? "B"
                            : "S" + listaCajCajaValesTO.getValeAnulado()));
                }
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }
}
