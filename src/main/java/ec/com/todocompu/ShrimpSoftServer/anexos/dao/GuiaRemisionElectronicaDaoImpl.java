/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class GuiaRemisionElectronicaDaoImpl extends GenericDaoImpl<AnxGuiaRemisionElectronica, Integer> implements GuiaRemisionElectronicaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public List<AnxListaGuiaRemisionPendientesTO> getListaGuiaRemisionPendientesTO(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.fun_guia_remision_electronicas_listado('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxListaGuiaRemisionPendientesTO.class);
    }

    @Override
    public List<AnxListaGuiaRemisionElectronicaTO> getListaAnxListaGuiaRemisionElectronicaTO(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM anexo.fun_guia_remision_electronicas_autorizadas_listado('" + empresa + "','" + fechaDesde + "','" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxListaGuiaRemisionElectronicaTO.class);
    }

    @Override
    public AnxGuiaRemisionElectronica buscarAnxGuiaRemisionElectronica(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT e_secuencial  " + "FROM anexo.anx_guia_remision_electronica  " + "WHERE guia_empresa = '" + empresa
                + "' AND guia_periodo = '" + periodo + "' and guia_numero = '" + numero + "';";
        Object obj = genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return obtenerPorId(AnxGuiaRemisionElectronica.class, (Integer) obj);
        }
        return null;
    }

    @Override
    public Boolean accionAnxGuiaRemisionElectronica(AnxGuiaRemisionElectronica anxGuiaRemisionElectronica, SisSuceso sisSuceso, char accion) throws Exception {
        if (accion == 'I') {
            insertar(anxGuiaRemisionElectronica);
        }
        if (accion == 'M') {
            actualizar(anxGuiaRemisionElectronica);
        }
        if (accion == 'E') {
            eliminar(anxGuiaRemisionElectronica);
        }
        if (sisSuceso != null) {
            sisSucesoDao.insertar(sisSuceso);
        }
        return true;
    }

    @Override
    public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eNumero) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        ePeriodo = ePeriodo == null ? ePeriodo : "'" + ePeriodo + "'";
        eNumero = eNumero == null ? eNumero : "'" + eNumero + "'";
        String sql = "SELECT e_xml FROM anexo.anx_guia_remision_electronica WHERE guia_empresa = " + empresa + " and guia_periodo = " + ePeriodo + " and guia_numero = " + eNumero;
        return new String((byte[]) genericSQLDao.obtenerObjetoPorSql(sql), "UTF-8");
    }

    @Override
    public boolean comprobarAnxGuiaRemisionElectronica(String empresa, String ePeriodo, String eNumero) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        ePeriodo = ePeriodo == null ? ePeriodo : "'" + ePeriodo + "'";
        eNumero = eNumero == null ? eNumero : "'" + eNumero + "'";
        String sql = "SELECT COUNT(*)!=0 FROM anexo.anx_guia_remision_electronica " + "WHERE (guia_empresa = " + empresa
                + " AND guia_periodo = " + ePeriodo + " AND guia_numero = " + eNumero + ")";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String comprobarAnxGuiaRemisionElectronicaAutorizacion(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT e_estado FROM anexo.anx_guia_remision_electronica " + "WHERE (guia_empresa = '" + empresa
                + "' AND guia_periodo = '" + periodo + "' AND guia_numero = '" + numero + "')";
        return (String) genericSQLDao.obtenerObjetoPorSql(sql);
    }

}
