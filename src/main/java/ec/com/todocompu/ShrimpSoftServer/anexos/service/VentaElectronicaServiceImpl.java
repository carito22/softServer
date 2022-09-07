package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaElectronicaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class VentaElectronicaServiceImpl implements VentaElectronicaService {

    @Autowired
    private VentaElectronicaDao ventaElectronicaDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GenericSQLDao genericSQLDao;

    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    private boolean comprobar = false;
    private String mensaje;

    @Override
    public String accionAnxVentaElectronica(AnxVentaElectronicaTO anxVentaElectronicaTO, char accion,
            SisInfoTO sisInfoTO) throws Exception {
        anxVentaElectronicaTO.setUsrCodigo(sisInfoTO.getUsuario());
        comprobar = false;
        boolean periodoCerrado = false;
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<>();
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(anxVentaElectronicaTO.getVtaEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(anxVentaElectronicaTO.getVtaFecha(), "yyyy-MM-dd")
                    .getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(anxVentaElectronicaTO.getVtaFecha(), "yyyy-MM-dd")
                            .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                            .getTime()) {
                comprobar = true;
                anxVentaElectronicaTO.setVtaPeriodo(sisListaPeriodoTO.getPerCodigo());
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (!periodoCerrado) {
            susClave = "CE " + anxVentaElectronicaTO.getVtaPeriodo() + " " + anxVentaElectronicaTO.getVtaMotivo()
                    + " " + anxVentaElectronicaTO.getVtaNumero();
            if (accion == 'I') {
                susDetalle = "Se insertó Comprobante Electronica Autorizados: " + " Tipo : Factura"
                        + " ,Clave de Autorizacion: " + anxVentaElectronicaTO.geteAutorizacionNumero()
                        + " ,Clave de Acceso" + anxVentaElectronicaTO.geteClaveAcceso();
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "Se optienen la Autorizados: " + " Tipo : Factura" + " ,Clave de Autorizacion: "
                        + anxVentaElectronicaTO.geteAutorizacionNumero() + " ,Clave de Acceso"
                        + anxVentaElectronicaTO.geteClaveAcceso();
                susSuceso = "UPDATE";
            }

            susTabla = "anexo.anx_venta_electronica";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            AnxVentaElectronica anxVentaElectronica = ConversionesAnexos
                    .convertirAnxVentaElectronicaTO_AnxVentaElectronica(anxVentaElectronicaTO);
            if (accion == 'I') {
                comprobar = ventaElectronicaDao.accionAnxVentaElectronica(anxVentaElectronica, sisSuceso, accion);
            }
            if (accion == 'M') {
                // //// BUSCANDO existencia Categoría
                if (ventaElectronicaDao.comprobarAnxVentaElectronica(anxVentaElectronicaTO.getUsrEmpresa().trim(),
                        anxVentaElectronicaTO.getVtaPeriodo().trim(), anxVentaElectronicaTO.getVtaMotivo().trim(),
                        anxVentaElectronicaTO.getVtaNumero().trim())) {

                    AnxVentaElectronica anxVentaElectronicaAux = ventaElectronicaDao.buscarAnxVentaElectronica(
                            anxVentaElectronicaTO.getUsrEmpresa().trim(),
                            anxVentaElectronicaTO.getVtaPeriodo().trim(),
                            anxVentaElectronicaTO.getVtaMotivo().trim(),
                            anxVentaElectronicaTO.getVtaNumero().trim());

                    anxVentaElectronica.setUsrFechaInserta(anxVentaElectronicaAux.getUsrFechaInserta());
                    anxVentaElectronica.setESecuencial(anxVentaElectronicaAux.getESecuencial());
                    comprobar = ventaElectronicaDao.accionAnxVentaElectronica(anxVentaElectronica, sisSuceso,
                            accion);

                } else {
                    comprobar = false;
                }
            }

            if (comprobar) {
                if (accion == 'I') {
                    mensaje = "TEl comprobante fue autorizado por el SRI y Guardado Correctamente...";
                }
                if (accion == 'M') {
                    mensaje = "TEl comprobante fue autorizado por el SRI y Guardado Correctamente...";
                }
            } else {
                mensaje = "FNo se encuentra la Venta Electrónica...";
            }

        } else {
            mensaje = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
        }
        return mensaje;
    }

    @Override
    public List<AnxListaVentasPendientesTO> getListaVentasPendientes(String empresa)
            throws Exception {
        return ventaElectronicaDao.getListaVentasPendientes(empresa);
    }

    @Override
    public List<AnxListaVentasPendientesTO> getListaVentasPendientesAutorizacionAutomatica(String empresa)
            throws Exception {
        return ventaElectronicaDao.getListaVentasPendientesAutorizacionAutomatica(empresa);
    }

    @Override
    public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eMotivo, String eNumero)
            throws Exception {
        return ventaElectronicaDao.getXmlComprobanteElectronico(empresa, ePeriodo, eMotivo, eNumero);
    }

    @Override
    public boolean comprobarAnxVentaElectronica(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return ventaElectronicaDao.comprobarAnxVentaElectronica(empresa, periodo, motivo, numero);
    }

    @Override
    public String comprobarAnxVentaElectronicaAutorizacion(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return ventaElectronicaDao.comprobarAnxVentaElectronicaAutorizacion(empresa, periodo, motivo, numero);
    }

    @Override
    public List<AnxVentaElectronicaNotificaciones> listarNotificacionesVentasElectronicas(String empresa, String motivo, String periodo, String numero) throws Exception {
        String query = "SELECT * FROM anexo.anx_venta_electronica_notificaciones n"
                + " WHERE n.vta_empresa = '" + empresa + "' AND n.vta_periodo = '" + periodo + "' AND n.vta_motivo = '" + motivo + "' AND n.vta_numero = '" + numero + "'  order by n.e_fecha;";
        List<AnxVentaElectronicaNotificaciones> notificaciones = genericSQLDao.obtenerPorSql(query, AnxVentaElectronicaNotificaciones.class);

        return notificaciones;
    }

}
