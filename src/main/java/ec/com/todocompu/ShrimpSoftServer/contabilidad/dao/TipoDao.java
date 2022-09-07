package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface TipoDao extends GenericDao<ConTipo, ConTipoPK> {

    public boolean insertarConTipo(ConTipo conTipo, SisSuceso sisSuceso) throws Exception;

    public boolean modificarConTipo(ConTipo conTipo, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarConTipo(ConTipo conTipo, SisSuceso sisSuceso) throws Exception;

    public Boolean buscarTipoContable(String empCodigo, String tipCodigo) throws Exception;

    public List<ConTipoTO> getListaConTipoTO(String empresa) throws Exception;

    public ConTipoTO getConTipoTO(String empresa, String tipCodigo) throws Exception;

    public List<ConTipoTO> getListaConTipoRrhhTO(String empresa) throws Exception;

    public List<ConTipoTO> getListaConTipoCarteraTO(String empresa) throws Exception;

    public List<ConTipoTO> getListaConTipoCarteraAnticiposTO(String empresa) throws Exception;

    public String buscarTipoContablePorMotivoCompra(String empresa, String codigoMotivo) throws Exception;

    public String buscarTipoContablePorMotivoVenta(String empresa, String codigoMotivo) throws Exception;

    public List<ConTipo> getListaConTipo(String empresa) throws Exception;

    public List<ConTipo> getListaConTipo(String empresa, String referencia);

    public List<ConTipoTO> getListaConTipoTOFiltrado(String empresa, String filtro) throws Exception;

    public List<ConTipoTO> getListaConTipoCodigo(String empresa, String codigo) throws Exception;

}
