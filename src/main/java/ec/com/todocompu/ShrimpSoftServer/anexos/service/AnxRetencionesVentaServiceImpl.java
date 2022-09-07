/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxRetencionesVentaDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxRetencionesVenta;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class AnxRetencionesVentaServiceImpl implements AnxRetencionesVentaService {

    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    @Autowired
    private AnxRetencionesVentaDao anxRetencionesVentaDao;

    @Override
    public boolean insertarListadoAnxRetencionesVenta(List<AnxRetencionesVenta> listado, List<SisSuceso> listadoSisSuceso) throws Exception {
        return anxRetencionesVentaDao.insertarListadoAnxRetencionesVenta(listado, listadoSisSuceso);
    }

    @Override
    public List<AnxRetencionesVenta> listarAnxRetencionesVentaPendientesImportarSinAnexoVenta(String empresa) throws Exception {
        return anxRetencionesVentaDao.listarAnxRetencionesVentaPendientesImportarSinAnexoVenta(empresa);
    }

    @Override
    public AnxRetencionesVenta obtenerAnxRetencionesVenta(String empresa, String numeroDocumento, String identificacionCliente) {
        return anxRetencionesVentaDao.obtenerAnxRetencionesVenta(empresa, numeroDocumento, identificacionCliente);
    }

    @Override
    public List<AnxRetencionesVenta> listarAnxRetencionesVentaPendientesImportar(String empresa, String cliIdentificacion, String busqueda) throws Exception {
        return anxRetencionesVentaDao.listarAnxRetencionesVentaPendientesImportar(empresa, cliIdentificacion, busqueda);
    }

    @Override
    public String insertarAnxRetencionesVenta(AnxRetencionesVenta anxRetencionesVenta, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AnxRetencionesVenta homoloAux = obtenerAnxRetencionesVenta(
                anxRetencionesVenta.getRetEmpresa(),
                anxRetencionesVenta.getRetNumeroDocumento(),
                anxRetencionesVenta.getCliIdentificacion());
        if (homoloAux != null) {
            retorno = "FLa retención de venta ya existe";
        } else {
            susClave = anxRetencionesVenta.getCliIdentificacion() + "|" + anxRetencionesVenta.getRetNumeroDocumento();
            susDetalle = "Se insertó la retención de venta con identificación de cliente: "
                    + anxRetencionesVenta.getCliIdentificacion() + " y número de documento: "
                    + anxRetencionesVenta.getRetNumeroDocumento();
            susSuceso = "INSERT";
            susTabla = "anexos.anx_retenciones_venta";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            anxRetencionesVenta.setUsrEmpresa(sisInfoTO.getEmpresa());
            anxRetencionesVenta.setUsrCodigo(sisInfoTO.getUsuario());
            anxRetencionesVenta.setUsrFechaInserta(UtilsDate.timestamp());
            if (anxRetencionesVentaDao.insertarAnxRetencionesVenta(anxRetencionesVenta, sisSuceso)) {
                retorno = "TLa retención de venta, con secuencial: " + anxRetencionesVenta.getRetSecuencial() + " , se guardó correctamente.";
            } else {
                retorno = "FHubo un error al guardar la retención de venta...\nIntente de nuevo o contacte con el administrador.";
            }
        }

        return retorno;
    }

    @Override
    public String anularRetencionesComprobantes(String empresa, List<AnxRetencionesVenta> listado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        for (AnxRetencionesVenta registroPorAnular : listado) {
            susClave = registroPorAnular.getRetClaveAcceso();
            susSuceso = "UPDATE";
            susTabla = "anexo.anx_retenciones_venta";
            susDetalle = "Registro con clave de acceso " + registroPorAnular.getRetClaveAcceso() + " se anulo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (anxRetencionesVentaDao.anularRetencionesComprobantes(empresa, registroPorAnular.getRetClaveAcceso(), sisSuceso)) {
                retorno = "TLos registros han sido actualizados correctamente";
            } else {
                retorno = "FOcurrio un error al anular los registros";
            }
        }
        return retorno;
    }

}
