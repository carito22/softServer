package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PresupuestoPescaMotivoDaoImpl extends
        GenericDaoImpl<PrdPresupuestoPescaMotivo, PrdPresupuestoPescaMotivoPK> implements PresupuestoPescaMotivoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public boolean insertarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisSuceso sisSuceso) throws Exception {
        insertar(prdPresupuestoPescaMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisSuceso sisSuceso) throws Exception {
        actualizar(prdPresupuestoPescaMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisSuceso sisSuceso) throws Exception {
        eliminar(prdPresupuestoPescaMotivo);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdPresupuestoPescaMotivo getPrdPresupuestoPescaMotivo(String empresa, String codigo) throws Exception {
        return obtenerPorId(PrdPresupuestoPescaMotivo.class, new PrdPresupuestoPescaMotivoPK(empresa, codigo));
    }

    @Override
    public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivo(String empresa) throws Exception {
        String consulta = "SELECT ppm FROM PrdPresupuestoPescaMotivo ppm inner join ppm.prdPresupuestoPescaMotivoPK ppmpk "
                + "WHERE ppmpk.presuEmpresa=?1 ORDER BY ppmpk.presuCodigo";
        return obtenerPorHql(consulta, new Object[]{empresa});
    }

    @Override
    public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivoTO(String empresa, boolean inactivo) throws Exception {
        String consulta = "";
        if (inactivo) {
            consulta = "SELECT ppm FROM PrdPresupuestoPescaMotivo ppm inner join ppm.prdPresupuestoPescaMotivoPK ppmpk "
                    + "WHERE ppmpk.presuEmpresa=?1 ORDER BY ppmpk.presuCodigo";
            return obtenerPorHql(consulta, new Object[]{empresa});
        } else {
            consulta = "SELECT ppm FROM PrdPresupuestoPescaMotivo ppm inner join ppm.prdPresupuestoPescaMotivoPK ppmpk "
                    + "WHERE ppmpk.presuEmpresa=?1 and ppm.presuInactivo=?2 ORDER BY ppmpk.presuCodigo";
            return obtenerPorHql(consulta, new Object[]{empresa, inactivo});
        }
    }

    @Override
    public boolean banderaEliminarPresupuestoPescaMotivo(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM produccion.fun_sw_presupuesto_pesca_motivo_eliminar('" + empresa + "','" + codigo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

}
