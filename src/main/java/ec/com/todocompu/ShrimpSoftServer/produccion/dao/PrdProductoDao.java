package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PrdProductoDao extends GenericDao<PrdProducto, PrdProductoPK> {

    public PrdLiquidacionProductoTO getPrdLiquidacionProductoTO(String empresa,
            PrdLiquidacionProductoTablaTO liquidacionProductoTablaTO) throws Exception;

    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(String empresa) throws Exception;

    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(String empresa, String codigo)
            throws Exception;

    public boolean insertarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisSuceso sisSuceso)
            throws Exception;

    public boolean modificarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisSuceso sisSuceso)
            throws Exception;

    public boolean eliminarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisSuceso sisSuceso)
            throws Exception;

    public PrdProducto getPrdLiquidacionProducto(String empresa, String codigo) throws Exception;

    public List<PrdProducto> getListaPrdLiquidacionProducto(String empresa) throws Exception;

    public List<PrdProducto> getListaPrdLiquidacionProductos(String empresa, boolean inactivos) throws Exception;

    public boolean banderaEliminarLiquidacionProducto(String empresa, String codigo) throws Exception;

    public List<PrdProducto> getListaPrdLiquidacionProductos(String empresa, char clase, String tipo) throws Exception;
}
