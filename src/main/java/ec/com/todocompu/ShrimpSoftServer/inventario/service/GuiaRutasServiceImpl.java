/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.GuiaRutasDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRutas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuiaRutasServiceImpl implements GuiaRutasService {

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GuiaRutasDao guiaRutasDao;

    @Override
    public String insertarInvRutasGuias(InvGuiaRutas invGuiaRutas, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        susClave = invGuiaRutas.getGuiaRuta();
        susDetalle = "Se insertó registro  " + invGuiaRutas.getGuiaRuta();
        susSuceso = "INSERT";
        susTabla = "inventario.inv_guia_rutas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        invGuiaRutas.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        if (guiaRutasDao.insertarInvRutasGuias(invGuiaRutas, sisSuceso)) {
            mensaje = "TSe a ingresar correctamente el registro";
        } else {
            mensaje = "FNo se pudo ingresar el registro";
        }
        return mensaje;
    }

    @Override
    public List<InvGuiaRutas> getListarInvGuiaRutas(String empresa) throws Exception {
        return guiaRutasDao.getListarInvGuiaRutas(empresa);
    }

    @Override
    public String modificarInvGuiaRutas(InvGuiaRutas invGuiaRutas, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susDetalle = "El registro de guia ruta  : Detalle " + invGuiaRutas.getGuiaRuta() + ", se ha modificado correctamente";
        susClave = invGuiaRutas.getGuiaRuta();
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_guia_rutas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        invGuiaRutas.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        if (guiaRutasDao.modificarInvGuiaRutas(invGuiaRutas, sisSuceso)) {
            retorno = "TEl registro se ha modificado correctamente.";
        } else {
            retorno = "Hubo un error al modificar el registro. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarInvGuiaRutas(InvGuiaRutas invGuiaRutas, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        susClave = invGuiaRutas.getGuiaRuta();
        susDetalle = "Se eliminó el registro  " + invGuiaRutas.getGuiaRuta();
        susSuceso = "DELETE";
        susTabla = "inventario.inv_guia_rutas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        invGuiaRutas.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        if (guiaRutasDao.eliminarInvGuiaRutas(invGuiaRutas, sisSuceso)) {
            mensaje = "TSe eliminó correctamente el registro";
        } else {
            mensaje = "FHubo un error al eliminar el registro";
        }
        return mensaje;
    }

    @Override
    public List<InvGuiaRutas> obtenerRuta(String empresa, BigDecimal guiaSecuencial) throws Exception {
        return guiaRutasDao.obtenerRuta(empresa, guiaSecuencial);
    }

}
