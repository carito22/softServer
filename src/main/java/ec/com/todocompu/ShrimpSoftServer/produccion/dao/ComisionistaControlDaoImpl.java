package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionComisionistaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdComisionista;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdComisionistaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ComisionistaControlDaoImpl extends GenericDaoImpl<PrdComisionista, PrdComisionistaPK> implements ComisionistaControlDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public boolean insertarComisionista(PrdComisionista prdComisionista, SisSuceso sisSuceso)
            throws Exception {
        insertar(prdComisionista);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<PrdComisionista> listarComisionista(String empresa) throws Exception {
        String sql = "SELECT * FROM produccion.prd_comisionista WHERE co_empresa = '" + empresa + "' ";
        return genericSQLDao.obtenerPorSql(sql, PrdComisionista.class);
    }

    @Override
    public boolean actualizarComisionista(PrdComisionista prdEquipoControl, SisSuceso sisSuceso) throws Exception {
        actualizar(prdEquipoControl);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<PrdLiquidacionComisionistaTO> buscarListadoLiquidacionComisionista(String empresa, String sector, String piscina, String fechaHasta, String fechaDesde, String comisionista, boolean incluirTodos) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        comisionista = comisionista == null ? comisionista : "'" + comisionista + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_comisionista(" + empresa + ", " + sector + ", " + piscina + ", " + fechaHasta + ", " + fechaDesde + ", " + comisionista + ", " + incluirTodos + ")";
        List<PrdLiquidacionComisionistaTO> prd = genericSQLDao.obtenerPorSql(sql, PrdLiquidacionComisionistaTO.class);
        return prd;
    }

}
