package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ChequeNumeracionDaoImpl extends GenericDaoImpl<BanChequeNumeracion, Integer>
		implements ChequeNumeracionDao {

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public boolean insertarBanChequesNumeracion(BanChequeNumeracion banBanco, SisSuceso sisSuceso) throws Exception {
		sucesoDao.insertar(sisSuceso);
		insertar(banBanco);
		return true;
	}

        @Override
	public boolean modificarBanChequeNumeracion(BanChequeNumeracion banBanco, SisSuceso sisSuceso) throws Exception {
		sucesoDao.insertar(sisSuceso);
		actualizar(banBanco);
		return true;
	}

        @Override
	public boolean eliminarBanChequeNumeracion(BanChequeNumeracion banBanco, SisSuceso sisSuceso) throws Exception {
		sucesoDao.insertar(sisSuceso);
		eliminar(banBanco);
		return true;
	}
}
