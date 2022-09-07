package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.listaBanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoDebito;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoDebitoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author KevinQuispe
 */
@Repository
public class BancoDebitoDaoImpl extends GenericDaoImpl<BanBancoDebito, BanBancoDebitoPK> implements BancoDebitoDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<listaBanBancoDebitoTO> getListaBanBancoDebitoTO(String empresa) throws Exception {

        String sql = "SELECT ban_codigo, ban_nombre " + "FROM banco.ban_banco_debito " + "WHERE (ban_empresa = '" + empresa + "') " + "ORDER BY ban_nombre";

        return genericSQLDao.obtenerPorSql(sql, listaBanBancoDebitoTO.class);
    }

    @Override
    public boolean insertarBancoDebito(BanBancoDebito banBanco, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);
        insertar(banBanco);
        return true;
    }

    @Override
    public boolean modificarBancoDebito(BanBancoDebito banBanco, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(banBanco);
        return true;
    }

    @Override
    public boolean eliminarBancoDebito(BanBancoDebito banBanco, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(banBanco);
        return true;
    }

}
