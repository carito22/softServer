package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface PrdProductoService {

    public PrdLiquidacionProductoTO getPrdLiquidacionProductoTO(String empresa,
            PrdLiquidacionProductoTablaTO prdLiquidacionProductoTablaTO) throws Exception;

    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(String empresa) throws Exception;

    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(String empresa, String codigo)
            throws Exception;

    public String insertarPrdLiquidacionProductoTO(PrdLiquidacionProductoTO prdLiquidacionProductoTO,
            SisInfoTO sisInfoTO) throws Exception;

    public String modificarPrdLiquidacionProductoTO(PrdLiquidacionProductoTO prdLiquidacionProductoTO,
            SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoPrdLiquidacionProductoTO(PrdLiquidacionProductoTO prdLiquidacionProductoTO, boolean estado,
            SisInfoTO sisInfoTO) throws Exception;

    public String eliminarPrdLiquidacionProductoTO(PrdLiquidacionProductoTO prdLiquidacionProductoTO,
            SisInfoTO sisInfoTO) throws Exception;

    public String insertarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisInfoTO sisInfoTO)
            throws Exception;

    public String eliminarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisInfoTO sisInfoTO)
            throws Exception;

    public List<PrdProducto> getListaPrdLiquidacionProducto(String empresa) throws Exception;

    public List<PrdProducto> getListaPrdLiquidacionProductos(String empresa, boolean inactivos) throws Exception;

    public List<PrdProducto> getListaPrdLiquidacionProductos(String empresa, char clase, String tipo) throws Exception;

}
