package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PreLiquidacionMotivoDaoImpl extends GenericDaoImpl<PrdPreLiquidacionMotivo, PrdPreLiquidacionMotivoPK>
        implements PreLiquidacionMotivoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public boolean insertarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisSuceso sisSuceso)
            throws Exception {
        insertar(prdPreLiquidacionMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo,
            SisSuceso sisSuceso) throws Exception {
        actualizar(prdPreLiquidacionMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisSuceso sisSuceso)
            throws Exception {
        eliminar(prdPreLiquidacionMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdPreLiquidacionMotivo getPrdPreLiquidacionMotivo(String empresa, String codigo) throws Exception {
        return obtenerPorId(PrdPreLiquidacionMotivo.class, new PrdPreLiquidacionMotivoPK(empresa, codigo));
    }

    @Override
    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivo(String empresa) throws Exception {
        String consulta = "SELECT plm FROM PrdPreLiquidacionMotivo plm inner join plm.prdPreLiquidacionMotivoPK plmpk "
                + "WHERE plmpk.plmEmpresa=?1 ORDER BY plmpk.plmCodigo";
        return obtenerPorHql(consulta, new Object[]{empresa});
    }

    @Override
    public boolean banderaEliminarPreLiquidacionMotivo(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM produccion.fun_sw_preliquidacion_motivo_eliminar('" + empresa + "','" + codigo
                + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivoTO(String empresa, boolean inactivo) throws Exception {
        String consulta = "";
        if (inactivo) {
            consulta = "SELECT plm FROM PrdPreLiquidacionMotivo plm inner join plm.prdPreLiquidacionMotivoPK plmpk "
                    + "WHERE plmpk.plmEmpresa=?1 ORDER BY plmpk.plmCodigo";
            return obtenerPorHql(consulta, new Object[]{empresa});
        } else {
            consulta = "SELECT plm FROM PrdPreLiquidacionMotivo plm inner join plm.prdPreLiquidacionMotivoPK plmpk "
                    + "WHERE plmpk.plmEmpresa=?1 and plm.plmInactivo =?2 ORDER BY plmpk.plmCodigo";
            return obtenerPorHql(consulta, new Object[]{empresa, inactivo});
        }
    }
}
