package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunPreLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PreLiquidacionDao extends GenericDao<PrdPreLiquidacion, PrdPreLiquidacionPK> {

    public PrdPreLiquidacion buscarPrdPreLiquidacion(PrdPreLiquidacionPK prdPreLiquidacionPK) throws Exception;

    public boolean insertarModificarPrdPreLiquidacion(PrdPreLiquidacion prdPreLiquidacion, List<PrdPreLiquidacionDetalle> listaPrdPreLiquidacionDetalle, SisSuceso sisSuceso) throws Exception;

    public int buscarConteoUltimaNumeracionPreLiquidacion(String empCodigo, String motCodigo) throws Exception;

    public PrdPreLiquidacion getPrdPreLiquidacion(PrdPreLiquidacionPK preLiquidacionPK);

    public List<PrdPreLiquidacion> getListaPrdPreLiquidacion(String empresa);

    public void desmayorizarPrdPreLiquidacion(PrdPreLiquidacionPK preLiquidacionPK);

    public void anularRestaurarPrdPreLiquidacion(PrdPreLiquidacionPK preLiquidacionPK, boolean anularRestaurar);

    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacion(String empresa, String sector, String piscina, String busqueda) throws Exception;

    public PrdPreLiquidacionTO getBuscaObjPreLiquidacionPorLote(String plLote) throws Exception;

    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacionTO(String empresa, String sector, String piscina, String busqueda, String nRegistros) throws Exception;

    //liquidacion detalle Producto
    public List<PrdPreLiquidacionDetalleProductoTO> getListadoPreLiquidacionDetalleProductoTO(String empresa, String sector, String piscina, String desde, String hasta, String cliente, String talla, String tipo) throws Exception;

    //Listado de PreLiquidacion Consolidando Tallas
    public List<PrdFunPreLiquidacionConsolidandoTallasTO> getPrdFunPreLiquidacionConsolidandoTallasTO(String empresa, String desde, String hasta, String sector, String cliente, String piscina) throws Exception;

    //liquidacioon consolidando clientes
    public List<PrdPreLiquidacionConsolidandoClientesTO> getPrdFunPreLiquidacionConsolidandoClientesTO(String empresa, String sector, String desde, String hasta, String cliente) throws Exception;

    //liquidaciob listado consultas
    public List<PrdPreLiquidacionConsultaTO> getPrdFunPreLiquidacionConsultaTO(String empresa, String sector, String piscina, String desde, String hasta, boolean incluirAnuladas) throws Exception;

    //Listado de preliquidacion detalle
    public List<PrdPreLiquidacionesDetalleTO> listarPreLiquidacionesDetalle(String empresa, String desde, String hasta) throws Exception;

}
