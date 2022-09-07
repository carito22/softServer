package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleProformasTO;

@Transactional
public interface ProformasDetalleService {

	public List<InvListaDetalleProformasTO> getListaInvProformasDetalleTO(String empresa, String periodo, String motivo,
			String numeroProformas) throws Exception;

}
