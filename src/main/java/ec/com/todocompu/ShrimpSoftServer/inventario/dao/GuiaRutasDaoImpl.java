/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRutas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class GuiaRutasDaoImpl extends GenericDaoImpl<InvGuiaRutas, Integer> implements GuiaRutasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean insertarInvRutasGuias(InvGuiaRutas invGuiaRutas, SisSuceso sisSuceso) throws Exception {
        insertar(invGuiaRutas);
        sucesoDao.insertar(sisSuceso);
        return true;
    }
    
    @Override
    public List<InvGuiaRutas> getListarInvGuiaRutas (String empresa) throws Exception {
        String sql = "SELECT * FROM inventario.inv_guia_rutas WHERE usr_empresa = '" + empresa + "'";
        return genericSQLDao.obtenerPorSql(sql, InvGuiaRutas.class);
    }

    @Override
    public boolean modificarInvGuiaRutas (InvGuiaRutas invGuiaRutas, SisSuceso sisSuceso) throws Exception {
        actualizar(invGuiaRutas);
        sucesoDao.insertar(sisSuceso);
        return true;
    }
    
    @Override
    public boolean eliminarInvGuiaRutas (InvGuiaRutas invGuiaRutas, SisSuceso sisSuceso) throws Exception {
        eliminar(invGuiaRutas);
        sucesoDao.insertar(sisSuceso);
        return true;
    }
    
    @Override
    public List<InvGuiaRutas> obtenerRuta (String empresa, BigDecimal guiaSecuencial) throws Exception {
        String sql = "SELECT * FROM inventario.inv_guia_rutas WHERE usr_empresa = '" + empresa+  "' AND guia_secuencial = '"+ guiaSecuencial +"'";
        return genericSQLDao.obtenerPorSql(sql, InvGuiaRutas.class);
    }
    
}
