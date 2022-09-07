/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class PrdReprocesoMotivoDaoImpl extends GenericDaoImpl<PrdReprocesoMotivo, PrdReprocesoMotivoPK> implements PrdReprocesoMotivoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public List<PrdReprocesoMotivo> listarPrdReprocesoMotivo(String empresa, boolean incluirTodos) {
        String sql;
        if (!incluirTodos) {
            sql = "SELECT * FROM produccion.prd_reproceso_motivo "
                    + "WHERE prd_empresa = ('" + empresa + "') AND not prd_inactivo ORDER BY prd_detalle";
        } else {
            sql = "SELECT * FROM produccion.prd_reproceso_motivo "
                    + "WHERE prd_empresa = ('" + empresa + "')  ORDER BY prd_detalle";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdReprocesoMotivo.class);
    }

    @Override
    public boolean comprobarPrdReprocesoMotivo(String empresa, String motCodigo) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM produccion.prd_reproceso_motivo WHERE prd_empresa = " + "('" + empresa
                + "') AND prd_codigo = ('" + motCodigo + "')";
        Object obj = genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return ((int) UtilsConversiones.convertir(obj) == 0) ? false : true;
        }
        return false;
    }

    @Override
    public boolean insertarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisSuceso sisSuceso) throws Exception {
        insertar(motivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisSuceso sisSuceso) throws Exception {
        actualizar(motivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisSuceso sisSuceso) throws Exception {
        eliminar(motivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean banderaEliminarPrdReprocesoMotivo(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM produccion.fun_sw_reproceso_motivo_eliminar('" + empresa + "','" + codigo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

}
