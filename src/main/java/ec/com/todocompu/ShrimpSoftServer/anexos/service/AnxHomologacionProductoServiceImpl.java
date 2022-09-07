/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxHomologacionProductoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
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
public class AnxHomologacionProductoServiceImpl implements AnxHomologacionProductoService {

    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    @Autowired
    private AnxHomologacionProductoDao anxHomologacionProductoDao;

    @Override
    public boolean insertarListadoAnxHomologacionProducto(List<AnxHomologacionProducto> listado, List<SisSuceso> listadoSisSuceso) throws Exception {
        return anxHomologacionProductoDao.insertarListadoAnxHomologacionProducto(listado, listadoSisSuceso);
    }

    @Override
    public List<AnxHomologacionProducto> listarAnxHomologacionProducto(String empresa, String provCodigo, String busqueda, boolean incluirTodos) throws Exception {
        return anxHomologacionProductoDao.listarAnxHomologacionProducto(empresa, provCodigo, busqueda, incluirTodos);
    }

    @Override
    public AnxHomologacionProducto obtenerHomologacionProducto(String empresa, String codigoProductoProveedor, String identificacionProveedor) {
        return anxHomologacionProductoDao.obtenerHomologacionProducto(empresa, codigoProductoProveedor, identificacionProveedor);
    }

    @Override
    public String insertarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AnxHomologacionProducto homoloAux = obtenerHomologacionProducto(
                anxHomologacionProducto.getHomEmpresa(),
                anxHomologacionProducto.getHomCodigoProductoProveedor(),
                anxHomologacionProducto.getProvIdentificacion());
        if (homoloAux != null) {
            retorno = "FLa homologación de producto ya existe";
        } else {
            susClave = anxHomologacionProducto.getProvIdentificacion() + "|" + anxHomologacionProducto.getHomCodigoProductoProveedor();
            susDetalle = "Se insertó la homologación de producto con identificación de proveedor: "
                    + anxHomologacionProducto.getProvIdentificacion() + " y código de producto: "
                    + anxHomologacionProducto.getHomCodigoProductoProveedor();
            susSuceso = "INSERT";
            susTabla = "anexos.anx_homologacion_producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            anxHomologacionProducto.setUsrEmpresa(sisInfoTO.getEmpresa());
            anxHomologacionProducto.setUsrCodigo(sisInfoTO.getUsuario());
            anxHomologacionProducto.setUsrFechaInserta(UtilsDate.timestamp());
            if (anxHomologacionProductoDao.insertarAnxHomologacionProducto(anxHomologacionProducto, sisSuceso)) {
                retorno = "TLa homologación de producto, con secuencial: " + anxHomologacionProducto.getHomSecuencial() + " , se guardó correctamente.";
            } else {
                retorno = "FHubo un error al guardar la homologación de producto...\nIntente de nuevo o contacte con el administrador.";
            }
        }

        return retorno;
    }

    @Override
    public String modificarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susClave = anxHomologacionProducto.getProvIdentificacion() + "|" + anxHomologacionProducto.getHomCodigoProductoProveedor();
        susDetalle = "Se modificó la homologación de producto con identificación de proveedor: "
                + anxHomologacionProducto.getProvIdentificacion() + " y código de producto: "
                + anxHomologacionProducto.getHomCodigoProductoProveedor();
        susSuceso = "UPDATE";
        susTabla = "anexos.anx_homologacion_producto";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        if (anxHomologacionProductoDao.modificarAnxHomologacionProducto(anxHomologacionProducto, sisSuceso)) {
            retorno = "TLa homologación de producto, con secuencial: " + anxHomologacionProducto.getHomSecuencial() + " , se modificó correctamente.";
        } else {
            retorno = "FHubo un error al modificar la homologación de producto...\nIntente de nuevo o contacte con el administrador.";
        }
        return retorno;
    }

    @Override
    public String eliminarAnxHomologacionProducto(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susClave = secuencial + "";
        susDetalle = "Se eliminó la homologación de producto con secuencial: " + secuencial;
        susSuceso = "DELETE";
        susTabla = "anexos.anx_homologacion_producto";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        AnxHomologacionProducto anxHomologacionProducto = anxHomologacionProductoDao.getAnxHomologacionProducto(secuencial);
        if (anxHomologacionProductoDao.eliminarAnxHomologacionProducto(anxHomologacionProducto, sisSuceso)) {
            retorno = "TLa homologación de producto, con secuencial: " + anxHomologacionProducto.getHomSecuencial() + " , se eliminó correctamente.";
        } else {
            retorno = "FHubo un error al eliminar la homologación de producto...\nIntente de nuevo o contacte con el administrador.";
        }
        return retorno;
    }

}
