package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTallaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;

public interface TallaDao extends GenericDao<PrdTalla, PrdTallaPK> {

    public PrdLiquidacionTallaTO getPrdLiquidacionTallaTO(String empresa,
            PrdLiquidacionTallaTablaTO liquidacionTallaTablaTO) throws Exception;

    public boolean insertarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisSuceso sisSuceso)
            throws Exception;

    public boolean modificarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisSuceso sisSuceso)
            throws Exception;

    public boolean eliminarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisSuceso sisSuceso)
            throws Exception;

    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, BigDecimal desde, BigDecimal hasta, String codigo) throws Exception;

    public PrdTalla getPrdLiquidacionTalla(String empresa, String codigo) throws Exception;

    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, boolean presupuestoPesca)
            throws Exception;

    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, boolean presupuestoPesca, boolean inactivos)
            throws Exception;

    public boolean banderaEliminarLiquidacionTalla(String empresa, String motivo) throws Exception;

    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(String empresa) throws Exception;

    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(String empresa, String codigo)
            throws Exception;

    public List<PrdTalla> getListaPrdLiquidacionTallaDetalle(String empresa)
            throws Exception;

    public List<PrdTalla> getListaPrdLiquidacionTallaDetalle(String empresa, String busqueda) throws Exception;
}
