package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;

@Transactional
public interface DetalleService {

	public int buscarConteoDetalleEliminarCuenta(String empCodigo, String cuentaCodigo) throws Exception;

	public ConContableConDetalle buscarConContableConDetalle(Long detSecuencia) throws Exception;

	public ConDetalle buscarDetalleContable(Long codDetalle) throws Exception;

	public List<ConDetalle> getListConDetalle(ConContablePK conContablePK) throws Exception;

	public List<ConDetalleTO> buscarConContable(String codEmpresa, String perCodigo, String tipCodigo, String conNumero)
			throws Exception;

	public List<ConDetalleTablaTO> getListaConDetalleTO(String empresa, String periodo, String tipo, String numero);
}
