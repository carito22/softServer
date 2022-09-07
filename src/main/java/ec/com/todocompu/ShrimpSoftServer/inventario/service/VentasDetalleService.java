package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleVentasTO;

@Transactional
public interface VentasDetalleService {

	public List<InvListaDetalleVentasTO> getListaInvVentasDetalleTO(String empresa, String periodo, String motivo,
			String numeroVentas) throws Exception;

}
