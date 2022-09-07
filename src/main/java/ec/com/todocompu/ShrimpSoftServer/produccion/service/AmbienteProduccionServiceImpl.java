package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.AmbienteProduccionDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdAmbienteProduccion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class AmbienteProduccionServiceImpl implements AmbienteProduccionService {

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private AmbienteProduccionDao ambienteProduccionDao;

    @Override
    public String insertarAmbienteProduccion(PrdAmbienteProduccion prdAmbienteProduccion, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        susClave = prdAmbienteProduccion.getCorNumero() + " - " + prdAmbienteProduccion.getPisNumero() + " - " + prdAmbienteProduccion.getSecCodigo();
        susDetalle = "Se insertó registro  " + prdAmbienteProduccion.getCorNumero() + " - " + prdAmbienteProduccion.getPisNumero() + " - " + prdAmbienteProduccion.getSecCodigo();
        susSuceso = "INSERT";
        susTabla = "produccion.prd_ambiente_produccion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdAmbienteProduccion.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        if (ambienteProduccionDao.insertarAmbienteProduccion(prdAmbienteProduccion, sisSuceso)) {
            mensaje = "TSe a ingresar correctamente el registro";
        } else {
            mensaje = "FNo se pudo ingresar el registro";
        }
        return mensaje;
    }

    @Override
    public List<PrdAmbienteProduccion> getListarPrdAmbienteProduccion(String empresa) throws Exception {
        List<PrdAmbienteProduccion> ambientes = ambienteProduccionDao.getListarPrdAmbienteProduccion(empresa);
        return ambientes;
    }

    @Override
    public String modificarAmbienteProduccion(PrdAmbienteProduccion prdAmbienteProduccion, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susDetalle = "El registro " + prdAmbienteProduccion.getCorNumero() + " - " + prdAmbienteProduccion.getPisNumero() + " - " + prdAmbienteProduccion.getSecCodigo() + ", se ha modificado correctamente";
        susClave = prdAmbienteProduccion.getCorNumero() + " - " + prdAmbienteProduccion.getPisNumero() + " - " + prdAmbienteProduccion.getSecCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_ambiente_produccion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdAmbienteProduccion.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        if (ambienteProduccionDao.modificarAmbienteProduccion(prdAmbienteProduccion, sisSuceso)) {
            retorno = "TEl registro se ha modificado correctamente.";
        } else {
            retorno = "Hubo un error al modificar el registro. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarAmbienteProduccion(PrdAmbienteProduccion prdAmbienteProduccion, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        susClave = prdAmbienteProduccion.getCorNumero() + " - " + prdAmbienteProduccion.getPisNumero() + " - " + prdAmbienteProduccion.getSecCodigo();
        susDetalle = "Se eliminó el registro  " + prdAmbienteProduccion.getCorNumero() + " - " + prdAmbienteProduccion.getPisNumero() + " - " + prdAmbienteProduccion.getSecCodigo();
        susSuceso = "DELETE";
        susTabla = "produccion.prd_ambiente_produccion";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdAmbienteProduccion.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        if (ambienteProduccionDao.eliminarAmbienteProduccion(prdAmbienteProduccion, sisSuceso)) {
            mensaje = "TSe eliminó correctamente el registro";
        } else {
            mensaje = "FHubo un error al eliminar el registro";
        }
        return mensaje;
    }

}
