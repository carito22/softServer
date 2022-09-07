package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

public interface CuentaService {

	@Transactional
	public List<ListaBanCuentaTO> getListaBanCuentaTO(String empresa) throws Exception;

        @Transactional
	BanCuentaTO getBancoCuentaTO(String empresa, String cuentaContable) throws Exception;
        
        public Map<String, Object> obtenerDatosParaCrudCuentas(Map<String, Object> map) throws Exception;
        
	@Transactional
	public boolean insertarBanCuentaTO(BanCuentaTO banCuentaTO, String codigoBanco, SisInfoTO sisInfoTO)
			throws Exception;

	@Transactional
	public boolean modificarBanCuentaTO(BanCuentaTO banCuentaTO, String codigoBanco, SisInfoTO sisInfoTO)
			throws Exception;

	@Transactional
	public boolean eliminarBanCuentaTO(BanCuentaTO banCuentaTO, String codigoBanco, SisInfoTO sisInfoTO)
			throws Exception;

	@Transactional
	public List<BanComboBancoTO> getBanComboBancoTO(String empresa) throws Exception;

	@Transactional
	public BigDecimal getBanValorSaldoSistema(String empresa, String cuenta, String hasta) throws Exception;

	@Transactional
	public List<BanComboCuentaTO> getBanComboCuentaTO(String empresa) throws Exception;

	@Transactional
	public List<String> getBanCuentasContablesBancos(String empresa) throws Exception;
	
	@Transactional
	public List<String> getValidarCuentaContableConBancoExiste(String banEmpresa, String banCodigo) throws Exception;

}
