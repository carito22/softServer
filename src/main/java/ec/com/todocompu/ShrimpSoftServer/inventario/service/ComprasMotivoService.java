package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ComprasMotivoService {

    public List<InvCompraMotivoComboTO> getListaCompraMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public InvComprasMotivo getInvComprasMotivo(String empresa, String cmCodigo) throws Exception;

    public InvComprasMotivoTO getInvComprasMotivoTO(String empresa, String cmCodigo) throws Exception;

    public List<InvCompraMotivoTablaTO> getListaInvComprasMotivoTablaTO(String empresa) throws Exception;

    public List<InvComprasMotivoTO> getListaInvComprasMotivoTO(String empresa, boolean soloActivos) throws Exception;

    public List<InvComprasMotivoTO> listarInvComprasMotivoTOAjusteInv(String empresa, boolean soloActivos) throws Exception;

    public String insertarInvComprasMotivoTO(InvComprasMotivoTO invComprasMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarInvComprasMotivoTO(InvComprasMotivoTO invComprasMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvComprasMotivoTO(InvComprasMotivoTO invComprasMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarInvComprasMotivoTO(InvComprasMotivoTO invComprasMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public Boolean comprobarInvComprasMotivo(String empresa, String motCodigo) throws Exception;

}
