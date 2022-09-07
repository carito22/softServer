package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaReembolso;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaVentasFacturasPorNumeroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosCoberturaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVsCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvValidarStockTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplemento;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VentasDao extends GenericDao<InvVentas, InvVentasPK> {

    public void actualizarPendientePorSql(InvVentas invVentas);

    public void insertarInvVenta(InvVentas invVentas, List<InvVentasDetalle> listaInvVentasDetalle, SisSuceso sisSuceso,
            AnxVenta anxVenta, InvVentasComplemento invVentasComplemento, AnxVentaElectronica anxVentaElectronica)
            throws Exception;

    public void insertarInvVenta(InvVentas invVentas, List<InvVentasDetalle> listaInvVentasDetalle, SisSuceso sisSuceso,
            AnxVenta anxVenta, InvVentasComplemento invVentasComplemento, AnxVentaElectronica anxVentaElectronica, InvVentaGuiaRemision invVentaGuiaRemision,
            List<InvVentasLiquidacion> listaInvVentasLiquidacion, AnxVentaExportacion anexo, List<AnxVentaReembolso> listAnxVentaReembolso)
            throws Exception;

    public int buscarConteoUltimaNumeracionVenta(String empCodigo, String perCodigo, String motCodigo) throws Exception;

    public boolean insertarTransaccionInvVenta(InvVentas invVentas, List<InvVentasDetalle> listaInvVentasDetalles,
            SisSuceso sisSuceso, AnxVenta anxVenta, InvVentasComplemento invVentasComplemento,
            AnxVentaElectronica anxVentaElectronica) throws Exception;

    @Transactional
    public boolean insertarTransaccionInvVenta(InvVentas invVentas, List<InvVentasDetalle> listaInvVentasDetalles,
            SisSuceso sisSuceso, AnxVenta anxVenta, InvVentasComplemento invVentasComplemento,
            AnxVentaElectronica anxVentaElectronica, InvVentaGuiaRemision invVentaGuiaRemision, List<InvVentasLiquidacion> listaInvVentasLiquidacion,
            AnxVentaExportacion anexo, List<AnxVentaReembolso> listAnxVentaReembolso) throws Exception;

    public boolean modificarInvVentas(InvVentas invVentas, List<InvVentasDetalle> listaInvDetalle,
            List<InvVentasDetalle> listaInvDetalleEliminar, SisSuceso sisSuceso, List<SisSuceso> listaSisSuceso,
            ConContable conContable, AnxVenta anxVentas, InvVentasComplemento invVentasComplemento, String complemento,
            InvVentasMotivoAnulacion invVentasMotivoAnulacion, boolean eliminarMotivoAnulacion, boolean desmayorizar, boolean ignorarSeries)
            throws Exception;//escritorio

    @Transactional
    public boolean modificarInvVentas(InvVentas invVentas, List<InvVentasDetalle> listaInvDetalle,
            List<InvVentasDetalle> listaInvDetalleEliminar, SisSuceso sisSuceso, List<SisSuceso> listaSisSuceso,
            ConContable conContable, AnxVenta anxVentas, InvVentasComplemento invVentasComplemento, String complemento,
            InvVentasMotivoAnulacion invVentasMotivoAnulacion, boolean eliminarMotivoAnulacion, boolean desmayorizar, boolean ignorarSeries,
            InvVentaGuiaRemision invVentaGuiaRemision,
            List<InvVentasLiquidacion> listaInvVentasLiquidacion,
            List<InvVentasLiquidacion> listaInvVentasLiquidacionEliminar,
            List<AnxVentaReembolso> listAnxVentaReembolso,
            List<AnxVentaReembolso> listAnxVentaReembolsoEliminar,
            AnxVentaExportacion anexo)
            throws Exception;

    @Transactional
    public InvVentas buscarInvVentas(String empresa, String perCodigo, String motCodigo, String compNumero)
            throws Exception;

    public InvConsultaVentasFacturasPorNumeroTO getConsultaVentasFacturasPorNumeroTO(String codigoEmpresa, String numFactura) throws Exception;

    public InvConsultaVentasFacturasPorNumeroTO getConsultaVentasFacturasPorTipoYNumeroTO(String codigoEmpresa, String tipoDocumento, String numero) throws Exception;

    public InvVentas buscarVentaPorDocumentoNumero(String empresaCodigo, String cliCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception;

    public InvVentas obtenerVentaPorNumero(String empresaCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception;

    public List<InvVentasDetalle> obtenerVentaDetallePorNumero(String empresa, String periodo, String motivo,
            String numero) throws Exception;

    public String getConteoNotaCreditoVenta(String empresaCodigo, String cliCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception;

    public String getConteoNumeroFacturaVenta(String empresaCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception;

    public InvVentaCabeceraTO getInvVentaCabeceraTO(String empresa, String periodo, String motivo, String numeroVenta)
            throws Exception;

    public Object[] getVenta(String empresa, String periodo, String conTipo, String numero);

    public List<InvListaConsultaVentaTO> getFunVentasListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception;

    public List<InvFunVentasConsolidandoProductosTO> getInvFunVentasConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega, String cliente) throws Exception;

    public List<InvFunVentasConsolidandoProductosCoberturaTO> getInvFunVentasConsolidandoProductosCoberturaTO(String empresa,
            String desde, String hasta, String sector, String bodega, String motivo, String cliente) throws Exception;

    public List<InvFunVentasConsolidandoClientesTO> getInvFunVentasConsolidandoClientesTO(String empresa, String sector,
            String desde, String hasta) throws Exception;

    public List<InvFunVentasVsCostoTO> getInvFunVentasVsCostoTO(String empresa, String desde, String hasta,
            String bodega, String cliente) throws Exception;

    public List<InvListaConsultaVentaTO> getListaInvConsultaVenta(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros) throws Exception;

    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaPorTipoDoc(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, String tipoDocumento) throws Exception;

    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaFiltrado(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, String tipoDocumento) throws Exception;

    public List<InvFunVentasTO> getInvFunVentasTO(String empresa, String desde, String hasta, String motivo,
            String cliente, String documento, String grupo_empresarial) throws Exception;

    public BigDecimal getInvVentaTotal(String empresa, String desde, String hasta, String cliente) throws Exception;

    public List<InvListadoCobrosTO> invListadoCobrosTO(String empresa, String periodo, String motivo, String numero)
            throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception;

    public List<InvValidarStockTO> getListaInvVentasValidarStockTO(String empresa, String periodo, String motivo,
            String numero, String accion) throws Exception;

    public List<InvFunVentasTO> listarInvFunVentasTO(String empresa, String desde, String hasta, String motivo, String cliente,
            String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception;

    public List<InvFunVentasTO> listarInvFunVentasTOAgrupadoTipoDocumento(String empresa, String desde, String hasta, String motivo, String cliente,
            String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception;

    public List<InvFunVentasTO> listarInvFunVentasTOAgrupadoTipoContribuyente(String empresa, String desde, String hasta, String motivo, String cliente,
            String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception;

    public List<InvFunVentasVendedorTO> listarInvFunVentasVendedorTO(String empresa, String desde, String hasta) throws Exception;

    public String eliminarVentas(String empresa, String periodo, String motivo, String numero, SisSuceso sisSuceso) throws Exception;

    public void actualizar(InvVentas venta, SisSuceso sisSuceso) throws Exception;

    public InvVentasTO getInvVentasTO(String empresa, String tipo, String numeroDocumento) throws Exception;

    public Boolean actualizarClaveExternaVenta(InvVentasPK pk, String clave, SisSuceso sisSuceso) throws Exception;
//SALDOS POR COBRAR

    public List<InvVentas> obtenerListadoVentasSaldosImportados(String empresa, String motivo, String sector, String fecha) throws Exception;

    public boolean insertarInvVentas(InvVentas invVentas, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvVentas(InvVentas invVentas, SisSuceso sisSuceso) throws Exception;

    //IMAGENES
    public List<InvVentasDatosAdjuntos> listarImagenesDeVenta(InvVentasPK pk) throws Exception;

    public boolean actualizarImagenesVenta(List<InvVentasDatosAdjuntos> listado, InvVentasPK pk, List<SisSuceso> sisSucesos) throws Exception;

    public InvVentas obtenerVentaPorContable(String empresa, String conPeriodo, String conTipo, String conNumero) throws Exception;

    public InvVentasTO getInvVentasTO(String empresa, String periodo, String motivo, String numero) throws Exception;
}
