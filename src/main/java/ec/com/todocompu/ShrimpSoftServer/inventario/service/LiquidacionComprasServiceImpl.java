/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.LiquidacionComprasDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class LiquidacionComprasServiceImpl implements LiquidacionComprasService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private AnexoNumeracionService numeracionService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private CompraDao compraDao;
    @Autowired
    private LiquidacionComprasDao liquidacionComprasDao;
    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    private boolean comprobar = false;
    private String mensaje;

    @Override
    public Map<String, Object> obtenerDatosParaLiquidacionCompra(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String fechaCompra = UtilsJSON.jsonToObjeto(String.class, map.get("fechaCompra"));
        String numeroDocumento = asignarNumeroDocumento(empresa, usuario);
        boolean verificarSiEstaEnRango = false;
        AnxNumeracionLineaTO anxNumeracion = null;

        if (numeroDocumento != null && !"".equals(numeroDocumento)) {
            verificarSiEstaEnRango = isNumeroDocumentoValidoRango(empresa, "03", numeroDocumento);
            if (verificarSiEstaEnRango) {
                anxNumeracion = ventaService.getNumeroAutorizacion(empresa, numeroDocumento, "03", fechaCompra);
            }
        }

        campos.put("numeroDocumento", numeroDocumento);
        campos.put("estaEnRangoPermitido", verificarSiEstaEnRango);
        campos.put("anxNumeracion", anxNumeracion);

        return campos;
    }

    public String asignarNumeroDocumento(String empresa, String usuario) throws Exception {
        String numeroDocumento = "";
        SisLoginTO sisLoginTO = usuarioService.getPermisoInventarioTO(empresa, usuario);
        if (sisLoginTO != null && sisLoginTO.getPerSecuencialPermitidoLiquidacionCompras() != null) {
            String ultimoSecuencial = ventaService.getUltimaNumeracionComprobante(empresa, "03", sisLoginTO.getPerSecuencialPermitidoLiquidacionCompras());
            if (ultimoSecuencial == null || "".equals(ultimoSecuencial)) {
                numeroDocumento = sisLoginTO.getPerSecuencialPermitidoLiquidacionCompras() + "-000000001";
            }
            if (ultimoSecuencial != null && !"".equals(ultimoSecuencial)) {
                String separado = ultimoSecuencial.substring(0, ultimoSecuencial.lastIndexOf("-") + 1);
                int numero = Integer.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
                int numeroParaDocumento = numero + 1;
                int digitosNumero = ("" + numeroParaDocumento).length();
                for (int i = 0; i < (9 - digitosNumero); i++) {
                    numeroDocumento = numeroDocumento + "0";
                }
                numeroDocumento = separado + numeroDocumento + numeroParaDocumento;
            }
        }
        return numeroDocumento;
    }

    public boolean isNumeroDocumentoValidoRango(String empresa, String tipoDocumento, String numeroDocumento) throws Exception {
        List<AnxNumeracionTablaTO> listaAnxNumeracionTablaTO = numeracionService.getListaAnexoNumeracionTO(empresa);
        String secuencialPermitido = numeroDocumento.substring(0, 7);
        String ultimosDigitos = numeroDocumento.substring(8);
        for (AnxNumeracionTablaTO item : listaAnxNumeracionTablaTO) {
            if (item.getNumeroComprobante().equalsIgnoreCase(tipoDocumento)
                    && item.getEstablecimientoPtoEmisionDesde().equalsIgnoreCase(secuencialPermitido)
                    && item.getEstablecimientoPtoEmisionHasta().equalsIgnoreCase(secuencialPermitido)) {
                if (Integer.parseInt(ultimosDigitos) >= Integer.parseInt(item.getNumeroDesde().substring(8)) && Integer.parseInt(ultimosDigitos) <= Integer.parseInt(item.getNumeroHasta().substring(8))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String accionAnxLiquidacionComprasElectronicaTO(AnxLiquidacionComprasElectronicaTO anxLiquidacionComprasElectronicaTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        anxLiquidacionComprasElectronicaTO.setUsrCodigo(sisInfoTO.getUsuario());
        comprobar = false;
        boolean periodoCerrado = false;
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<>();
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(anxLiquidacionComprasElectronicaTO.getCompEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(anxLiquidacionComprasElectronicaTO.getCompFecha(), "yyyy-MM-dd")
                    .getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(anxLiquidacionComprasElectronicaTO.getCompFecha(), "yyyy-MM-dd")
                            .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                            .getTime()) {
                comprobar = true;
                anxLiquidacionComprasElectronicaTO.setCompPeriodo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (!periodoCerrado) {
            susClave = "CE " + anxLiquidacionComprasElectronicaTO.getCompPeriodo() + " " + anxLiquidacionComprasElectronicaTO.getCompMotivo()
                    + " " + anxLiquidacionComprasElectronicaTO.getCompNumero();
            if (accion == 'I') {
                susDetalle = "Se insertó comprobante electrónica Autorizados: " + " Tipo : Liquidación de compras"
                        + " ,Clave de Autorizacion: " + anxLiquidacionComprasElectronicaTO.geteAutorizacionNumero()
                        + " ,Clave de Acceso" + anxLiquidacionComprasElectronicaTO.geteClaveAcceso();
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "Se obtienen la Autorizados: " + " Tipo : Liquidación de compras" + " ,Clave de Autorizacion: "
                        + anxLiquidacionComprasElectronicaTO.geteAutorizacionNumero() + " ,Clave de Acceso"
                        + anxLiquidacionComprasElectronicaTO.geteClaveAcceso();
                susSuceso = "UPDATE";
            }

            susTabla = "anexo.anx_liquidacion_compras_electronica";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            AnxLiquidacionComprasElectronica anxLiquidacionComprasElectronica = ConversionesAnexos.convertirAnxLiquidacionComprasElectronicaTO_AnxLiquidacionComprasElectronica(anxLiquidacionComprasElectronicaTO);

            //poner numero de autorizacion
            try {
                AnxCompra retencion = compraDao.obtenerPorId(AnxCompra.class, new AnxCompraPK(anxLiquidacionComprasElectronicaTO.getUsrEmpresa(),
                        anxLiquidacionComprasElectronicaTO.getCompPeriodo(), anxLiquidacionComprasElectronicaTO.getCompMotivo(),
                        anxLiquidacionComprasElectronicaTO.getCompNumero()));
                if (retencion != null && anxLiquidacionComprasElectronicaTO.geteAutorizacionNumero() != null && !anxLiquidacionComprasElectronicaTO.geteAutorizacionNumero().equals("")) {
                    retencion.setCompAutorizacion(anxLiquidacionComprasElectronicaTO.geteAutorizacionNumero());
                    compraDao.actualizar(retencion);
                }
            } catch (Exception e) {
            }

            if (accion == 'I') {
                comprobar = liquidacionComprasDao.accionAnxLiquidacionComprasElectronica(anxLiquidacionComprasElectronica, sisSuceso, accion);
            }
            if (accion == 'M') {
                // //// BUSCANDO existencia Categoría
                if (liquidacionComprasDao.comprobarAnxLiquidacionComprasElectronica(anxLiquidacionComprasElectronicaTO.getUsrEmpresa().trim(),
                        anxLiquidacionComprasElectronicaTO.getCompPeriodo().trim(), anxLiquidacionComprasElectronicaTO.getCompMotivo().trim(),
                        anxLiquidacionComprasElectronicaTO.getCompNumero().trim())) {

                    AnxLiquidacionComprasElectronica anxLiqComprasElectronicaAux = liquidacionComprasDao.buscarAnxLiquidacionComprasElectronica(
                            anxLiquidacionComprasElectronicaTO.getUsrEmpresa().trim(),
                            anxLiquidacionComprasElectronicaTO.getCompPeriodo().trim(),
                            anxLiquidacionComprasElectronicaTO.getCompMotivo().trim(),
                            anxLiquidacionComprasElectronicaTO.getCompNumero().trim());

                    anxLiquidacionComprasElectronica.setUsrFechaInserta(anxLiqComprasElectronicaAux.getUsrFechaInserta());
                    anxLiquidacionComprasElectronica.seteSecuencial(anxLiqComprasElectronicaAux.geteSecuencial());
                    comprobar = liquidacionComprasDao.accionAnxLiquidacionComprasElectronica(anxLiquidacionComprasElectronica, sisSuceso,
                            accion);

                } else {
                    comprobar = false;
                }
            }

            if (comprobar) {
                if (accion == 'I') {
                    mensaje = "TEl comprobante fue autorizado por el SRI y guardado correctamente...";
                }
                if (accion == 'M') {
                    mensaje = "TEl comprobante fue autorizado por el SRI y guardado correctamente...";
                }
            } else {
                mensaje = "FNo se encuentra la liquidación de compras electrónica...";
            }

        } else {
            mensaje = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
        }
        return mensaje;
    }

    @Override
    public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception {
        return liquidacionComprasDao.getXmlComprobanteElectronico(empresa, ePeriodo, eMotivo, eNumero);
    }

    @Override
    public boolean comprobarAnxLiquidacionComprasElectronica(String empresa, String periodo, String motivo, String numero) throws Exception {
        return liquidacionComprasDao.comprobarAnxLiquidacionComprasElectronica(empresa, periodo, motivo, numero);
    }

    @Override
    public String comprobarAnxLiquidacionComprasElectronicaAutorizacion(String empresa, String periodo, String motivo, String numero) throws Exception {
        return liquidacionComprasDao.comprobarAnxLiquidacionComprasElectronicaAutorizacion(empresa, periodo, motivo, numero);
    }

}
