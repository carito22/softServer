package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCaja;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class BancoCajaDaoImpl extends GenericDaoImpl<BanCaja, BanCajaPK> implements BancoCajaDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<ListaBanCajaTO> getListaBanCajaTO(String empresa) throws Exception {
        String sql = "SELECT caja_codigo, caja_nombre, cta_cuenta_contable " + "FROM banco.ban_caja "
                + "WHERE (caja_empresa = '" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, ListaBanCajaTO.class);
    }

    @Override
    public List<BanCajaTO> getListBanCajaTO(String empresa) throws Exception {
        String sql = "SELECT caja_empresa,caja_codigo, caja_nombre, cta_cuenta_contable, usr_codigo as usr_inserta_caja,usr_fecha_inserta FROM banco.ban_caja WHERE (caja_empresa = '" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, BanCajaTO.class);
    }

    @Override
    public boolean insertarBanCaja(BanCaja banCaja, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(banCaja);
        return true;
    }

    @Override
    public boolean modificarBanCaja(BanCaja banCaja, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(banCaja);
        return true;
    }

    @Override
    public boolean eliminarBanCaja(BanCaja banCaja, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(banCaja);
        return true;
    }
}
