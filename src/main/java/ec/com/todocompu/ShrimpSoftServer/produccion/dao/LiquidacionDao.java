package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface LiquidacionDao extends GenericDao<PrdLiquidacion, PrdLiquidacionPK> {

    public PrdLiquidacion buscarPrdLiquidacion(PrdLiquidacionPK prdLiquidacionPK) throws Exception;

    public boolean insertarModificarPrdLiquidacion(PrdLiquidacion prdLiquidacion,
            List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle, SisSuceso sisSuceso) throws Exception;

    public void insertarPrdLiquidacion(PrdLiquidacion prdLiquidacion,
            List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle, SisSuceso sisSuceso,
            PrdLiquidacionNumeracion prdLiquidacionNumeracion, boolean nuevo) throws Exception;

    public boolean modificarPrdLiquidacion(PrdLiquidacion prdLiquidacion, List<PrdLiquidacionDetalle> listaPrdDetalle,
            List<PrdLiquidacionDetalle> listaPrdDetalleEliminar, SisSuceso sisSuceso) throws Exception;

    public boolean insertarTransaccionPrdLiquidacion(PrdLiquidacion prdLiquidacion,
            List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalles, SisSuceso sisSuceso,
            PrdLiquidacionNumeracion prdLiquidacionNumeracion) throws Exception;

    public PrdEstadoCCCVT getEstadoCCCVT(String empresa, String motivoTipo, String numero) throws Exception;

    public PrdLiquidacionCabeceraTO getPrdLiquidacionCabeceraTO(String empresa, String motivo, String numeroLiquidacion)
            throws Exception;

    public int buscarConteoUltimaNumeracionLiquidacion(String empCodigo, String motCodigo) throws Exception;

    public String obtenerSiguienteLote(String empCodigo, String motCodigo) throws Exception;

    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacion(String empresa, String sector,
            String piscina, String busqueda) throws Exception;

    public PrdLiquidacion getPrdLiquidacion(PrdLiquidacionPK liquidacionPK);

    public List<PrdLiquidacion> getListaPrdLiquidacion(String empresa);

    public void desmayorizarPrdLiquidacion(PrdLiquidacionPK liquidacionPK, SisSuceso sisSuceso);

    public void anularRestaurarPrdLiquidacion(PrdLiquidacionPK liquidacionPK, boolean anularRestaurar, SisSuceso sisSuceso);

    public PrdLiquidacionTO getBuscaObjLiquidacionPorLote(String liqLote, String empresa) throws Exception;

    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacionTO(String empresa, String sector, String piscina, String busqueda, String nRegistros) throws Exception;

    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacionTOSoloActivas(String empresa, String proveedor, String cliente, String motivo, String numero, String nRegistros) throws Exception;

    public List<PrdFunLiquidacionConsolidandoTallasTO> getPrdFunLiquidacionConsolidandoTallasTO(String empresa, String desde, String hasta, String sector, String cliente, String piscina) throws Exception;

    //liquidacioon consolidando clientes
    public List<PrdLiquidacionConsolidandoClientesTO> getPrdFunLiquidacionConsolidandoClientesTO(String empresa, String sector, String desde, String hasta, String cliente) throws Exception;

    //liquidacioon consolidando proveedores
    public List<PrdLiquidacionConsolidandoProveedoresTO> getPrdFunLiquidacionConsolidandoProveedoresTO(String empresa, String sector, String desde, String hasta, String proveedor, String comisionista) throws Exception;

    //liquidaciob listado consultas
    public List<PrdLiquidacionConsultaTO> getPrdFunLiquidacionConsultaTO(String empresa, String sector, String piscina, String desde, String hasta, boolean incluirAnuladas) throws Exception;

    //liquidaciob listado consultas
    public List<PrdLiquidacionConsultaEmpacadoraTO> getPrdFunLiquidacionConsultaEmpacadoraTO(String empresa, String sector, String piscina, String desde, String hasta, String proveedor, boolean incluirAnuladas, String comisionista) throws Exception;

    //liquidacion detalle Producto
    public List<PrdLiquidacionDetalleProductoTO> getListadoLiquidacionDetalleProductoTO(String empresa, String sector, String piscina, String desde, String hasta, String cliente, String talla, String tipo) throws Exception;

    //liquidacion detalle Producto Empacadora
    public List<PrdLiquidacionDetalleProductoEmpacadoraTO> getListadoLiquidacionDetalleProductoEmpacadoraTO(String empresa, String sector, String piscina, String desde, String hasta, String proveedor, String talla, String tipo, String comisionista) throws Exception;

    //liquidacion consolidado Producto
    public List<PrdLiquidacionDetalleProductoTO> getListadoLiquidacionConsolidadoProductoTO(String empresa, String sector, String piscina, String desde, String hasta, String cliente) throws Exception;

    public List<PrdLiquidacionesDetalleTO> listarLiquidacionesDetalle(String empresa, String desde, String hasta) throws Exception;

    public boolean verificarSiDesmayorizarAnularLiquidacion(String empresa, String motivo, String eNumero) throws Exception;

    public List<PrdLiquidacionDatosAdjuntos> listarImagenesDeLiquidacionPesca(PrdLiquidacionPK pk) throws Exception;

    public List<PrdFunLiquidacionConsolidandoTallasProveedorTO> getPrdFunLiquidacionConsolidandoTallasProveedor(String empresa, String desde, String hasta, String sector, String cliente, String piscina, String comisionista) throws Exception;

    public List<String> listadoComisionista(String empresa) throws Exception;
}
