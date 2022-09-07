package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface BodegaService {

    public InvBodega buscarInvBodega(String empresa, String codigoBodega) throws Exception;

    public String insertarInvBodegaTO(InvBodegaTO invBodegaTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvBodegaTO(InvBodegaTO invBodegaTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String modificarInvBodegaTO(InvBodegaTO invBodegaTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarInvBodegaTO(InvBodegaTO invBodegaTO, SisInfoTO sisInfoTO) throws Exception;

    public List<InvListaBodegasTO> getListaBodegasTO(String empresa, boolean inacivo) throws Exception;

    public List<InvBodega> getListaBodegas(String empresa, boolean inacivo) throws Exception;

    public List<InvComboBodegaTO> getInvComboBodegaTO(String empresa) throws Exception;

    public List<InvBodegaTO> getBodegaTO(String empresa, String codigo) throws Exception;

    public List<SaldoBodegaTO> getListaSaldoBodegaTO(String empresa, String bodega, String hasta, String categoria) throws Exception;

    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionTO(String empresa, String bodega,
            String desde, String hasta) throws Exception;

    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionCantidadesTO(String empresa, String bodega,
            String desde, String hasta) throws Exception;

    public List<InvListaBodegasTO> buscarBodegasTO(String empresa, boolean inactivo, String busqueda) throws Exception;

    public List<InvListaBodegasTO> buscarBodegasPorSector(String empresa, boolean inactivo, String sector) throws Exception;
}
