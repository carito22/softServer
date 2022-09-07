/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdFitoplancton;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdFitoplanctonPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class FitoplanctonDaoImpl extends GenericDaoImpl<PrdFitoplancton, PrdFitoplanctonPK> implements FitoplanctonDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public boolean insertarPrdFitoplancton(PrdFitoplancton prdFitoplancton, SisSuceso sisSuceso) throws Exception {
        insertar(prdFitoplancton);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdFitoplancton(PrdFitoplancton prdFitoplancton, SisSuceso sisSuceso) throws Exception {
        eliminar(prdFitoplancton);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<PrdFitoplanctonTO> getListPrdFitoplanctonTO(String empresa, String sector, String fecha) throws Exception {
        String sql = "SELECT * FROM produccion.fun_plantilla_fitoplancton_lote('" + empresa + "','" + sector + "','" + fecha + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdFitoplanctonTO.class);
    }

    @Override
    public List<PrdListadoFitoplanctonTO> getListadoPrdFitoplanctonTO(String empresa, String sector, String piscina, String desde, String hasta) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";
        String sql = "SELECT * FROM produccion.fun_fitoplancton('" + empresa + "', '" + sector + "', " + "'"
                + piscina + "', " + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdListadoFitoplanctonTO.class);
    }

}
