package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversado;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversadoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class BancoChequeMotivoReversadoDaoImpl extends GenericDaoImpl<BanChequeMotivoReversado, BanChequeMotivoReversadoPK> implements BancoChequeMotivoReversadoDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean insertarBanChequeMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisSuceso sisSuceso) throws Exception {
        insertar(banChequeMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }
    
    @Override
    public List<BanChequeMotivoReversado> getListaMotivoReversadoCheque(String empresa) throws Exception{
        String consulta = "SELECT bcm FROM BanChequeMotivoReversado bcm inner join bcm.banChequeMotivoReversadoPK bcmpk "
                + "WHERE bcmpk.banEmpresa=?1 ORDER BY bcmpk.banCodigoMotivo";
        return obtenerPorHql(consulta, new Object[]{empresa});
    }
    
    @Override
    public boolean modificarMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisSuceso sisSuceso) throws Exception{
        actualizar(banChequeMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    } 
    
    @Override
    public boolean eliminarMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisSuceso sisSuceso) throws Exception{
        eliminar(banChequeMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }
    
    @Override
    public boolean modificarEstadoMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisSuceso sisSuceso)throws Exception{
        actualizar(banChequeMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
