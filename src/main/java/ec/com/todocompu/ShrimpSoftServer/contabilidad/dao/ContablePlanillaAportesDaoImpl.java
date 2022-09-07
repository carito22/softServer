/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaAportes;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class ContablePlanillaAportesDaoImpl extends GenericDaoImpl<ConContablePlanillaAportes, Integer> implements ContablePlanillaAportesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<ConContablePlanillaAportes> getListaConContablePlanillaAportes(String empresa, String sector, String periodo) throws Exception {
        String sqlSector = sector == null ? "" : " AND sec_codigo='" + sector + "'";
        String sqlPeriodo = periodo == null ? "" : " AND con_periodo='" + periodo + "'";
        String sql = "SELECT * FROM contabilidad.con_contable_planilla_aportes WHERE sec_empresa = ('"
                + empresa + "')" + sqlSector + sqlPeriodo;

        return genericSQLDao.obtenerPorSql(sql, ConContablePlanillaAportes.class);
    }

    @Override
    public boolean insertarContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisSuceso sisSuceso) throws Exception {
        insertar(conContablePlanillaAportes);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisSuceso sisSuceso) throws Exception {
        actualizar(conContablePlanillaAportes);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisSuceso sisSuceso) throws Exception {
        eliminar(conContablePlanillaAportes);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
