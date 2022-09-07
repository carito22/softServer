package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class HorasExtrasConceptoDaoImpl extends GenericDaoImpl<RhHorasExtrasConcepto, Integer> implements HorasExtrasConceptoDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public RhHorasExtrasConcepto buscarHorasExtrasConcepto(Integer bcSecuencia) throws Exception {
        return obtenerPorId(RhHorasExtrasConcepto.class, bcSecuencia);
    }

    @Override
    public boolean insertarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisSuceso sisSuceso) throws Exception {
        insertar(rhHorasExtrasConcepto);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisSuceso sisSuceso) throws Exception {
        actualizar(rhHorasExtrasConcepto);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConcepto, SisSuceso sisSuceso) throws Exception {
        eliminar(rhHorasExtrasConcepto);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<RhHorasExtrasConcepto> getListaHorasExtrasConcepto(String empresa) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_horas_extras_concepto " + "WHERE (usr_empresa = '" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, RhHorasExtrasConcepto.class);
    }

    @Override
    public List<RhHorasExtrasConcepto> getListaHorasExtrasConceptos(String empresa, boolean inactivo) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_horas_extras_concepto " + "WHERE (usr_empresa = '" + empresa + "'" + (!inactivo ? " AND hec_inactivo=" + inactivo : "") + ")";
        return genericSQLDao.obtenerPorSql(sql, RhHorasExtrasConcepto.class);
    }

}
