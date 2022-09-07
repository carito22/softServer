package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodegaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface BodegaDao extends GenericDao<InvBodega, InvBodegaPK> {

    public InvBodega buscarInvBodega(String empresa, String codigoBodega) throws Exception;

    public boolean eliminarInvBodega(InvBodega invBodega, SisSuceso sisSuceso);

    public boolean insertarInvBodega(InvBodega invBodega, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvBodega(InvBodega invBodega, SisSuceso sisSuceso) throws Exception;

    public Boolean buscarInvBodega(String empresa, String codigoBodega, String nombreBodega) throws Exception;

    public List<InvBodegaTO> getBodegaTO(String empresa, String codigo) throws Exception;

    public List<InvComboBodegaTO> getInvComboBodegaTO(String empresa) throws Exception;

    public List<InvListaBodegasTO> getListaBodegasTO(String empresa, boolean inacivo) throws Exception;

    public List<InvBodega> getListaBodegas(String empresa, boolean inacivo) throws Exception;

    public Boolean getPuedeEliminarBodega(String empresa, String bodega) throws Exception;

    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionCantidadesTO(String empresa, String bodega,
            String desde, String hasta) throws Exception;

    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionTO(String empresa, String bodega,
            String desde, String hasta) throws Exception;

    public List<SaldoBodegaTO> getListaSaldoBodegaTO(String empresa, String bodega, String hasta, String categoria) throws Exception;

    public List<InvListaBodegasTO> buscarBodegasTO(String empresa, boolean inactivo, String busqueda) throws Exception;

}
