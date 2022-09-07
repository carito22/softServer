package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import java.math.BigDecimal;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuenta;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuentaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface BancoCuentaDao extends GenericDao<BanCuenta, BanCuentaPK> {

	public BigDecimal getBanValorSaldoSistema(String empresa, String cuenta, String hasta) throws Exception;

	public boolean canDeleteCuenta(String empresa, String codigoContable) throws Exception;

	public List<ListaBanCuentaTO> getListaBanCuentaTO(String empresa) throws Exception;

	public List<BanComboBancoTO> getBanComboBancoTO(String empresa) throws Exception;

	public List<BanComboCuentaTO> getBanComboCuentaTO(String empresa) throws Exception;

	public List<String> getBanCuentasContablesBancos(String empresa) throws Exception;

	public String getNombreBanco(String empresa, String cuentaContable) throws Exception;

	public boolean insertarBanCuenta(BanCuenta banCuenta, SisSuceso sisSuceso) throws Exception;

	public boolean modificarBanCuenta(BanCuenta banCuenta, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarBanCuenta(BanCuenta banCuenta, SisSuceso sisSuceso) throws Exception;
	
	public List<String> getValidarCuentaContableConBancoExiste(String banEmpresa, String banCodigo) throws Exception;

}
