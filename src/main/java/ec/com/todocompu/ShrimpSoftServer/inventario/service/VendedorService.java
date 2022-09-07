package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface VendedorService {

    public List<InvVendedorComboListadoTO> getComboinvListaVendedorTOs(String empresa, String busqueda) throws Exception;

    public String accionInvVendedorTO(InvVendedorTO invVendedorTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarInvVendedor(InvVendedorPK invVendedorPK, SisInfoTO sisInfoTO) throws Exception;

}
