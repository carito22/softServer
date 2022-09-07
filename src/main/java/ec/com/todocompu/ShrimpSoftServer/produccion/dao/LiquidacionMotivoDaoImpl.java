package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class LiquidacionMotivoDaoImpl extends GenericDaoImpl<PrdLiquidacionMotivo, PrdLiquidacionMotivoPK>
        implements LiquidacionMotivoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public PrdLiquidacionMotivoTO getPrdLiquidacionMotivoTO(String empresa,
            PrdLiquidacionMotivoTablaTO prdLiquidacionMotivoTablaTO) throws Exception {
        return ConversionesProduccion
                .convertirPrdLiquidacionMotivo_PrdLiquidacionMotivoTO(obtenerPorId(PrdLiquidacionMotivo.class,
                        new PrdLiquidacionMotivoPK(empresa, prdLiquidacionMotivoTablaTO.getLmCodigo())));
    }

    @Override
    public List<PrdLiquidacionMotivoTablaTO> getListaPrdLiquidacionMotivoTablaTO(String empresa) throws Exception {
        String sql = "SELECT lm_codigo, lm_detalle,lm_inactivo FROM produccion.prd_liquidacion_motivo "
                + "WHERE lm_empresa = ('" + empresa + "') ORDER BY lm_codigo";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionMotivoTablaTO.class);
    }

    public Boolean comprobarPrdLiquidacionMotivo(String empresa, String motCodigo) throws Exception {
        String sql = "SELECT COUNT(*) FROM produccion.prd_liquidacion_motivo " + "WHERE lm_empresa = ('" + empresa
                + "') AND lm_codigo = ('" + motCodigo + "')";
        return ((Integer) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql)) == 0) ? false : true;
    }

    @Override
    public List<PrdLiquidacionMotivoComboTO> getListaLiquidacionMotivoComboTO(String empresa, boolean filtrarInactivos)
            throws Exception {
        String sql = "";
        if (filtrarInactivos) {
            sql = "SELECT lm_codigo, lm_detalle FROM produccion.prd_liquidacion_motivo " + "WHERE lm_empresa = ('"
                    + empresa + "') AND NOT lm_inactivo ORDER BY lm_codigo";
        } else {
            sql = "SELECT lm_codigo, lm_detalle FROM produccion.prd_liquidacion_motivo " + "WHERE lm_empresa = ('"
                    + empresa + "') ORDER BY lm_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionMotivoComboTO.class);
    }

    @Override
    public boolean insertarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisSuceso sisSuceso)
            throws Exception {
        insertar(prdLiquidacionMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisSuceso sisSuceso)
            throws Exception {
        actualizar(prdLiquidacionMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisSuceso sisSuceso)
            throws Exception {
        eliminar(prdLiquidacionMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdLiquidacionMotivo getPrdLiquidacionMotivo(String empresa, String codigo) throws Exception {
        return obtenerPorId(PrdLiquidacionMotivo.class, new PrdLiquidacionMotivoPK(empresa, codigo));
    }

    @Override
    public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivo(String empresa) throws Exception {
        String consulta = "SELECT plm FROM PrdLiquidacionMotivo plm inner join plm.prdLiquidacionMotivoPK plmpk "
                + "WHERE plmpk.lmEmpresa=?1 ORDER BY plmpk.lmCodigo";
        return obtenerPorHql(consulta, new Object[]{empresa});

    }

    @Override
    public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivoTO(String empresa, boolean inactivo) throws Exception {
        String consulta = "";
        if (inactivo) {
            consulta = "SELECT plm FROM PrdLiquidacionMotivo plm inner join plm.prdLiquidacionMotivoPK plmpk "
                    + "WHERE plmpk.lmEmpresa=?1 ORDER BY plmpk.lmCodigo";
            return obtenerPorHql(consulta, new Object[]{empresa});
        } else {
            consulta = "SELECT plm FROM PrdLiquidacionMotivo plm inner join plm.prdLiquidacionMotivoPK plmpk "
                    + "WHERE plmpk.lmEmpresa=?1 and plm.lmInactivo =?2 ORDER BY plmpk.lmCodigo";
            return obtenerPorHql(consulta, new Object[]{empresa, inactivo});
        }
    }

    @Override
    public boolean banderaEliminarLiquidacionMotivo(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM produccion.fun_sw_liquidacion_motivo_eliminar('" + empresa + "','" + codigo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

}
