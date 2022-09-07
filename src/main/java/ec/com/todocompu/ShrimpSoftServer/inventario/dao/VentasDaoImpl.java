package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxVentaExportacionDao;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoVentaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaPK;
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
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplemento;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplementoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoVenta;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.util.ArrayList;

@Repository
public class VentasDaoImpl extends GenericDaoImpl<InvVentas, InvVentasPK> implements VentasDao {

    @Autowired
    private VentasComplementoDao ventasComplementoDao;
    @Autowired
    private VentasDetalleDao ventasDetalleDao;
    @Autowired
    private VentasMotivoAnulacionDao ventasMotivoAnulacionDao;
    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private VentasDetalleSeriesDao ventasDetalleSeriesDao;
    @Autowired
    private VentaGuiaRemisionDao ventaGuiaRemisionDao;
    @Autowired
    private VentasLiquidacionDao ventasLiquidacionDao;
    @Autowired
    private AnxVentaExportacionDao anxVentaExportacionDao;
    @Autowired
    private ProductoFormulaDao productoFormulaDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private GenericoDao<InvVentasDatosAdjuntos, Integer> invVentasDatosAdjuntosDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private SucesoVentaDao sucesoVentaDao;
    @Autowired
    private GenericoDao<AnxVentaReembolso, Integer> anxVentaReembolsoDao;

    @Override
    public void actualizarPendientePorSql(InvVentas invVentas) {
        genericSQLDao.ejecutarSQL("UPDATE inventario.inv_ventas SET vta_pendiente=false WHERE vta_empresa='"
                + invVentas.getInvVentasPK().getVtaEmpresa() + "' and " + "vta_periodo='"
                + invVentas.getInvVentasPK().getVtaPeriodo() + "' and vta_motivo='"
                + invVentas.getInvVentasPK().getVtaMotivo() + "' and vta_numero='"
                + invVentas.getInvVentasPK().getVtaNumero() + "';");
    }

    @Override
    public List<InvValidarStockTO> getListaInvVentasValidarStockTO(String empresa, String periodo, String motivo,
            String numero, String accion) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_ventas_validar_stock('" + empresa + "','" + periodo + "','"
                + motivo + "','" + numero + "','" + accion + "');";
        return genericSQLDao.obtenerPorSql(sql, InvValidarStockTO.class);
    }

    @Override
    public int buscarConteoUltimaNumeracionVenta(String empCodigo, String perCodigo, String motCodigo)
            throws Exception {
        String sql = "SELECT num_secuencia FROM inventario.inv_ventas_numeracion " + "WHERE num_empresa = ('"
                + empCodigo + "') AND num_periodo = " + "('" + perCodigo + "') AND num_motivo = ('" + motCodigo + "')";
        Object obj = genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return new Integer(String.valueOf(UtilsConversiones.convertir(obj)));
        }
        return 0;

    }

    @Override
    public boolean insertarTransaccionInvVenta(InvVentas invVentas, List<InvVentasDetalle> listaInvVentasDetalles,
            SisSuceso sisSuceso, AnxVenta anxVenta, InvVentasComplemento invVentasComplemento,
            AnxVentaElectronica anxVentaElectronica) throws Exception {

        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionVenta(invVentas.getInvVentasPK().getVtaEmpresa(),
                invVentas.getInvVentasPK().getVtaPeriodo(), invVentas.getInvVentasPK().getVtaMotivo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }
            invVentas.setInvVentasPK(new InvVentasPK(invVentas.getInvVentasPK().getVtaEmpresa(),
                    invVentas.getInvVentasPK().getVtaPeriodo(), invVentas.getInvVentasPK().getVtaMotivo(),
                    rellenarConCeros + numeracion));
        } while (buscarInvVentas(invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                invVentas.getInvVentasPK().getVtaMotivo(), rellenarConCeros + numeracion) != null);
        sisSuceso.setSusClave(invVentas.getInvVentasPK().getVtaPeriodo() + " "
                + invVentas.getInvVentasPK().getVtaMotivo() + " " + invVentas.getInvVentasPK().getVtaNumero());
        String detalle = "";
        if (invVentas.getVtaDocumentoTipo().equals("00")) {
            detalle = "Venta a " + invVentas.getInvCliente().getCliRazonSocial() + " por "
                    + invVentas.getVtaTotal().toPlainString();
        } else {
            detalle = "Venta a " + invVentas.getInvCliente().getCliRazonSocial() + " según documento # "
                    + invVentas.getVtaDocumentoNumero() + " por $" + invVentas.getVtaTotal().toPlainString();
        }
        sisSuceso.setSusDetalle(detalle.length() > 300 ? detalle.substring(0, 300) : detalle);

        if (anxVenta != null) {
            anxVenta.setAnxVentaPK(new AnxVentaPK(invVentas.getInvVentasPK().getVtaEmpresa(),
                    invVentas.getInvVentasPK().getVtaPeriodo(), invVentas.getInvVentasPK().getVtaMotivo(),
                    invVentas.getInvVentasPK().getVtaNumero()));
        }
        if (anxVentaElectronica != null) {
            anxVentaElectronica.setVtaNumero(invVentas.getInvVentasPK().getVtaNumero());
            anxVentaElectronica.setVtaEmpresa(invVentas.getInvVentasPK().getVtaEmpresa());
            anxVentaElectronica.setVtaPeriodo(invVentas.getInvVentasPK().getVtaPeriodo());
            anxVentaElectronica.setVtaMotivo(invVentas.getInvVentasPK().getVtaMotivo());
            anxVentaElectronica.setVtaNumero(invVentas.getInvVentasPK().getVtaNumero());

        }
        sisSuceso.setSusSecuencia(0);
        insertarInvVenta(invVentas, listaInvVentasDetalles, sisSuceso, anxVenta, invVentasComplemento,
                anxVentaElectronica);
        return true;
    }

    @Override
    public boolean insertarTransaccionInvVenta(InvVentas invVentas, List<InvVentasDetalle> listaInvVentasDetalles,
            SisSuceso sisSuceso, AnxVenta anxVenta, InvVentasComplemento invVentasComplemento,
            AnxVentaElectronica anxVentaElectronica, InvVentaGuiaRemision invVentaGuiaRemision, List<InvVentasLiquidacion> listaInvVentasLiquidacion,
            AnxVentaExportacion anexo, List<AnxVentaReembolso> listAnxVentaReembolso) throws Exception {

        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionVenta(invVentas.getInvVentasPK().getVtaEmpresa(),
                invVentas.getInvVentasPK().getVtaPeriodo(), invVentas.getInvVentasPK().getVtaMotivo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }
            invVentas.setInvVentasPK(new InvVentasPK(invVentas.getInvVentasPK().getVtaEmpresa(),
                    invVentas.getInvVentasPK().getVtaPeriodo(), invVentas.getInvVentasPK().getVtaMotivo(),
                    rellenarConCeros + numeracion));
        } while (buscarInvVentas(invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                invVentas.getInvVentasPK().getVtaMotivo(), rellenarConCeros + numeracion) != null);
        sisSuceso.setSusClave(invVentas.getInvVentasPK().getVtaPeriodo() + " "
                + invVentas.getInvVentasPK().getVtaMotivo() + " " + invVentas.getInvVentasPK().getVtaNumero());
        String detalle = "";
        if (invVentas.getVtaDocumentoTipo().equals("00")) {
            detalle = "Venta a " + invVentas.getInvCliente().getCliRazonSocial() + " por "
                    + invVentas.getVtaTotal().toPlainString();
        } else {
            detalle = "Venta a " + invVentas.getInvCliente().getCliRazonSocial() + " según documento # "
                    + invVentas.getVtaDocumentoNumero() + " por $" + invVentas.getVtaTotal().toPlainString();
        }
        sisSuceso.setSusDetalle(detalle.length() > 300 ? detalle.substring(0, 300) : detalle);

        if (anxVenta != null) {
            anxVenta.setAnxVentaPK(new AnxVentaPK(invVentas.getInvVentasPK().getVtaEmpresa(),
                    invVentas.getInvVentasPK().getVtaPeriodo(), invVentas.getInvVentasPK().getVtaMotivo(),
                    invVentas.getInvVentasPK().getVtaNumero()));
        }
        if (anxVentaElectronica != null) {
            anxVentaElectronica.setVtaNumero(invVentas.getInvVentasPK().getVtaNumero());
            anxVentaElectronica.setVtaEmpresa(invVentas.getInvVentasPK().getVtaEmpresa());
            anxVentaElectronica.setVtaPeriodo(invVentas.getInvVentasPK().getVtaPeriodo());
            anxVentaElectronica.setVtaMotivo(invVentas.getInvVentasPK().getVtaMotivo());
            anxVentaElectronica.setVtaNumero(invVentas.getInvVentasPK().getVtaNumero());

        }
        sisSuceso.setSusSecuencia(0);
        insertarInvVenta(invVentas, listaInvVentasDetalles, sisSuceso, anxVenta, invVentasComplemento,
                anxVentaElectronica, invVentaGuiaRemision, listaInvVentasLiquidacion, anexo, listAnxVentaReembolso);
        return true;
    }

    @Override
    public void insertarInvVenta(InvVentas invVentas, List<InvVentasDetalle> listaInvVentasDetalle, SisSuceso sisSuceso,
            AnxVenta anxVenta, InvVentasComplemento invVentasComplemento, AnxVentaElectronica anxVentaElectronica)
            throws Exception {

        invVentas.setVtaPendiente(true);
        insertar(invVentas);
        for (int i = 0; i < listaInvVentasDetalle.size(); i++) {
            listaInvVentasDetalle.get(i).setInvVentas(invVentas);
            ventasDetalleDao.insertar(listaInvVentasDetalle.get(i));
        }

        if (anxVenta != null) {
            ventaDao.insertar(anxVenta);
        }
        if (invVentasComplemento != null) {
            invVentasComplemento.setInvVentas(invVentas);
            invVentasComplemento.setInvVentasComplementoPK(new InvVentasComplementoPK(
                    invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                    invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero()));
            ventasComplementoDao.insertar(invVentasComplemento);
        }

        sucesoDao.insertar(sisSuceso);
    }

    @Override
    public void insertarInvVenta(InvVentas invVentas, List<InvVentasDetalle> listaInvVentasDetalle, SisSuceso sisSuceso,
            AnxVenta anxVenta, InvVentasComplemento invVentasComplemento, AnxVentaElectronica anxVentaElectronica,
            InvVentaGuiaRemision invVentaGuiaRemision,
            List<InvVentasLiquidacion> listaInvVentasLiquidacion,
            AnxVentaExportacion anexo,
            List<AnxVentaReembolso> listAnxVentaReembolso)
            throws Exception {

        invVentas.setVtaPendiente(true);
        insertar(invVentas);
        List<InvVentasDetalle> listaInvVentasDetalleFormula = new ArrayList<>();
        //EXPORTACION
        if (anexo != null) {
            anexo.setVtaEmpresa(invVentas.getInvVentasPK().getVtaEmpresa());
            anexo.setVtaPeriodo(invVentas.getInvVentasPK().getVtaPeriodo());
            anexo.setVtaMotivo(invVentas.getInvVentasPK().getVtaMotivo());
            anexo.setVtaNumero(invVentas.getInvVentasPK().getVtaNumero());
            anxVentaExportacionDao.insertar(anexo);
        }

        //guia remision
        if (invVentaGuiaRemision != null) {
            invVentaGuiaRemision.setInvVentas(invVentas);
            ventaGuiaRemisionDao.insertar(invVentaGuiaRemision);
        }
        //liquidacion
        for (int i = 0; i < listaInvVentasLiquidacion.size(); i++) {
            listaInvVentasLiquidacion.get(i).setInvVentas(invVentas);
            ventasLiquidacionDao.insertar(listaInvVentasLiquidacion.get(i));
        }
        //Reembolso
        for (int i = 0; i < listAnxVentaReembolso.size(); i++) {
            listAnxVentaReembolso.get(i).setInvVenta(invVentas);
            anxVentaReembolsoDao.insertar(listAnxVentaReembolso.get(i));
        }

        for (int i = 0; i < listaInvVentasDetalle.size(); i++) {
            listaInvVentasDetalle.get(i).setInvVentas(invVentas);
            ventasDetalleDao.insertar(listaInvVentasDetalle.get(i));
            //BUSCANDO PRODUCTO FORMULA
            List<InvProductoFormula> listaProductosFormula = productoFormulaDao.getListInvProductoFormula(invVentas.getInvVentasPK().getVtaEmpresa(), listaInvVentasDetalle.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal());
            if (listaProductosFormula != null && listaProductosFormula.size() > 0) {
                for (InvProductoFormula item : listaProductosFormula) {
                    InvVentasDetalle invVentasDetalleFormula = new InvVentasDetalle();
                    invVentasDetalleFormula.setInvProducto(item.getInvProductoFormula());
                    invVentasDetalleFormula.setProNombre(invVentasDetalleFormula.getInvProducto().getProNombre());
                    invVentasDetalleFormula.setSecEmpresa(listaInvVentasDetalle.get(i).getSecEmpresa());
                    invVentasDetalleFormula.setSecCodigo(listaInvVentasDetalle.get(i).getSecCodigo());
                    invVentasDetalleFormula.setDetCantidad(item.getPrCantidad().multiply(listaInvVentasDetalle.get(i).getDetCantidad()));
                    invVentasDetalleFormula.setDetPrecio(BigDecimal.ZERO);
                    invVentasDetalleFormula.setDetParcial(BigDecimal.ZERO);
                    invVentasDetalleFormula.setDetMontoIce(BigDecimal.ZERO);
                    invVentasDetalleFormula.setDetPorcentajeRecargo(BigDecimal.ZERO);
                    invVentasDetalleFormula.setDetPorcentajeDescuento(BigDecimal.ZERO);
                    invVentasDetalleFormula.setDetValorPromedio(BigDecimal.ZERO);
                    invVentasDetalleFormula.setIcePorcentaje(BigDecimal.ZERO);
                    invVentasDetalleFormula.setIceTarifaFija(BigDecimal.ZERO);
                    invVentasDetalleFormula.setProCreditoTributario(listaInvVentasDetalle.get(i).getProCreditoTributario());
                    invVentasDetalleFormula.setProComplementario(listaInvVentasDetalle.get(i).getDetSecuencial());
                    invVentasDetalleFormula.setDetPendiente(invVentas.getVtaPendiente());
                    invVentasDetalleFormula.setInvBodega(listaInvVentasDetalle.get(i).getInvBodega());
                    invVentasDetalleFormula.setDetObservaciones(listaInvVentasDetalle.get(i).getDetObservaciones());
                    invVentasDetalleFormula.setDetConversionPesoNeto(listaInvVentasDetalle.get(i).getDetConversionPesoNeto());
                    invVentasDetalleFormula.setDetEmpaque(listaInvVentasDetalle.get(i).getDetEmpaque());
                    invVentasDetalleFormula.setDetEmpaqueCantidad(listaInvVentasDetalle.get(i).getDetEmpaqueCantidad());
                    listaInvVentasDetalleFormula.add(invVentasDetalleFormula);
                }
            }
            //***********************
        }
        //***********************
        if (listaInvVentasDetalleFormula != null && listaInvVentasDetalleFormula.size() > 0) {
            for (int i = 0; i < listaInvVentasDetalleFormula.size(); i++) {
                listaInvVentasDetalleFormula.get(i).setInvVentas(invVentas);
                ventasDetalleDao.insertar(listaInvVentasDetalleFormula.get(i));
            }
        }
        //***********************

        if (anxVenta != null) {
            ventaDao.insertar(anxVenta);
        }
        if (invVentasComplemento != null) {
            invVentasComplemento.setInvVentas(invVentas);
            invVentasComplemento.setInvVentasComplementoPK(new InvVentasComplementoPK(
                    invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                    invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero()));
            ventasComplementoDao.insertar(invVentasComplemento);
        }
        sucesoDao.insertar(sisSuceso);
        ///////////////CREAR SUCESO
        SisSucesoVenta sucesoVenta = new SisSucesoVenta();
        InvVentas copia = ConversionesInventario.convertirInvVentas_InvVentas(invVentas);
        List<InvVentasDetalle> detalleCopia = new ArrayList<>();
        for (InvVentasDetalle en : listaInvVentasDetalle) {
            InvVentasDetalle det = ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalle(en);
            det.setInvVentas(null);
            detalleCopia.add(det);
        }
        copia.setInvVentasDetalleList(detalleCopia);
        String json = UtilsJSON.objetoToJson(copia);
        sucesoVenta.setSusJson(json);
        sucesoVenta.setSisSuceso(sisSuceso);
        sucesoVenta.setInvVentas(copia);
        sucesoVentaDao.insertar(sucesoVenta);
    }

    //ESCRITORIO
    @Override
    public boolean modificarInvVentas(InvVentas invVentas, List<InvVentasDetalle> listaInvDetalle, List<InvVentasDetalle> listaInvDetalleEliminar, SisSuceso sisSuceso, List<SisSuceso> listaSisSuceso, ConContable conContable, AnxVenta anxVentas, InvVentasComplemento invVentasComplemento, String complemento, InvVentasMotivoAnulacion invVentasMotivoAnulacion, boolean eliminarMotivoAnulacion, boolean desmayorizar, boolean ignorarSeries) throws Exception {
        if (!invVentas.getVtaAnulado() && !eliminarMotivoAnulacion) {
            invVentas.setVtaPendiente(true);
        }
        actualizar(invVentas);
        if (!invVentas.getVtaAnulado()) {
            if (!desmayorizar && listaInvDetalle != null) {
                for (int i = 0; i < listaInvDetalle.size(); i++) {
                    if (listaInvDetalle.get(i).getDetSecuencial() != null && !ignorarSeries) {
                        List<InvVentasDetalleSeries> seriesEnLaBase = ventasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(listaInvDetalle.get(i).getDetSecuencial());
                        if (seriesEnLaBase != null && !seriesEnLaBase.isEmpty()) {
                            List<InvVentasDetalleSeries> seriesEntrantes = listaInvDetalle.get(i).getInvVentasDetalleSeriesList();
                            if (seriesEntrantes == null) {
                                seriesEntrantes = new ArrayList<>();
                            }
                            seriesEnLaBase.removeAll(seriesEntrantes);
                            seriesEnLaBase.forEach((serie) -> {
                                ventasDetalleSeriesDao.eliminar(serie);
                            });
                        }
                    }
                    listaInvDetalle.get(i).setDetPendiente(true);
                    listaInvDetalle.get(i).setInvVentas(invVentas);
                    if (!eliminarMotivoAnulacion) {
                        ventasDetalleDao.saveOrUpdate(listaInvDetalle.get(i));
                    }
                }
            }
        }

        if (!listaInvDetalleEliminar.isEmpty()) {
            for (int i = 0; i < listaInvDetalleEliminar.size(); i++) {
                listaInvDetalleEliminar.get(i).setInvVentas(invVentas);
                ventasDetalleDao.eliminar(listaInvDetalleEliminar.get(i));
            }
        }

        if (!invVentas.getVtaDocumentoTipo().equals("00") && anxVentas != null) {
            ventaDao.saveOrUpdate(anxVentas);
        }

        if (sisSuceso != null) {
            sucesoDao.insertar(sisSuceso);
        }

        if (!listaSisSuceso.isEmpty()) {
            for (int i = 0; i < listaSisSuceso.size(); i++) {
                sucesoDao.saveOrUpdate(listaSisSuceso.get(i));
            }
        }

        if (invVentasComplemento != null) {
            if (complemento.trim().equals("CREAR")) {
                invVentasComplemento.setInvVentas(invVentas);
                invVentasComplemento.setInvVentasComplementoPK(new InvVentasComplementoPK(
                        invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                        invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero()));
                ventasComplementoDao.insertar(invVentasComplemento);
            } else if (complemento.trim().equals("MODIFICAR")) {
                invVentasComplemento.setInvVentas(invVentas);
                invVentasComplemento.setInvVentasComplementoPK(new InvVentasComplementoPK(
                        invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                        invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero()));
                ventasComplementoDao.saveOrUpdate(invVentasComplemento);
            } else if (complemento.trim().equals("ELIMINAR")) {
                invVentasComplemento.setInvVentas(invVentas);
                invVentasComplemento.setInvVentasComplementoPK(new InvVentasComplementoPK(
                        invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                        invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero()));
                ventasComplementoDao.eliminar(invVentasComplemento);
            }
        }
        if (invVentasMotivoAnulacion != null) {
            if (eliminarMotivoAnulacion) {
                // ventasMotivoAnulacionDao.eliminar(invVentasMotivoAnulacion);
            } else {
                ventasMotivoAnulacionDao.insertar(invVentasMotivoAnulacion);
            }
        }

        return true;
    }

    //WEB
    @Override
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
            throws Exception {
        List<InvVentasDetalle> listaInvVentasDetalleFormula = new ArrayList<>();
        if (!invVentas.getVtaAnulado() && !eliminarMotivoAnulacion) {
            invVentas.setVtaPendiente(true);
        }
        actualizar(invVentas);
        if (!invVentas.getVtaAnulado()) {
            if (!desmayorizar && listaInvDetalle != null) {
                for (int i = 0; i < listaInvDetalle.size(); i++) {
                    Integer secuencialAntesDeGuardar = listaInvDetalle.get(i).getDetSecuencial();
                    if (listaInvDetalle.get(i).getDetSecuencial() != null && !ignorarSeries) {
                        List<InvVentasDetalleSeries> seriesEnLaBase = ventasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(listaInvDetalle.get(i).getDetSecuencial());
                        if (seriesEnLaBase != null && !seriesEnLaBase.isEmpty()) {
                            List<InvVentasDetalleSeries> seriesEntrantes = listaInvDetalle.get(i).getInvVentasDetalleSeriesList();
                            if (seriesEntrantes == null) {
                                seriesEntrantes = new ArrayList<>();
                            }
                            seriesEnLaBase.removeAll(seriesEntrantes);
                            seriesEnLaBase.forEach((serie) -> {
                                ventasDetalleSeriesDao.eliminar(serie);
                            });
                        }
                    }
                    listaInvDetalle.get(i).setDetPendiente(true);
                    listaInvDetalle.get(i).setInvVentas(invVentas);
                    if (!eliminarMotivoAnulacion) {
                        ventasDetalleDao.saveOrUpdate(listaInvDetalle.get(i));
                    }
                    //BUSCANDO PRODUCTO FORMULA
                    //NUEVO
                    if (secuencialAntesDeGuardar == null || secuencialAntesDeGuardar == 0) {
                        List<InvProductoFormula> listaProductosFormula = productoFormulaDao.getListInvProductoFormula(invVentas.getInvVentasPK().getVtaEmpresa(), listaInvDetalle.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal());
                        if (listaProductosFormula != null && listaProductosFormula.size() > 0) {
                            for (InvProductoFormula item : listaProductosFormula) {
                                InvVentasDetalle invVentasDetalleFormula = new InvVentasDetalle();
                                invVentasDetalleFormula.setInvProducto(item.getInvProductoFormula());
                                invVentasDetalleFormula.setProNombre(invVentasDetalleFormula.getInvProducto().getProNombre());
                                invVentasDetalleFormula.setSecEmpresa(listaInvDetalle.get(i).getSecEmpresa());
                                invVentasDetalleFormula.setSecCodigo(listaInvDetalle.get(i).getSecCodigo());
                                invVentasDetalleFormula.setDetCantidad(item.getPrCantidad().multiply(listaInvDetalle.get(i).getDetCantidad()));
                                invVentasDetalleFormula.setDetPrecio(BigDecimal.ZERO);
                                invVentasDetalleFormula.setDetParcial(BigDecimal.ZERO);
                                invVentasDetalleFormula.setDetMontoIce(BigDecimal.ZERO);
                                invVentasDetalleFormula.setDetPorcentajeRecargo(BigDecimal.ZERO);
                                invVentasDetalleFormula.setDetPorcentajeDescuento(BigDecimal.ZERO);
                                invVentasDetalleFormula.setDetValorPromedio(BigDecimal.ZERO);
                                invVentasDetalleFormula.setIcePorcentaje(BigDecimal.ZERO);
                                invVentasDetalleFormula.setIceTarifaFija(BigDecimal.ZERO);
                                invVentasDetalleFormula.setProCreditoTributario(listaInvDetalle.get(i).getProCreditoTributario());
                                invVentasDetalleFormula.setProComplementario(listaInvDetalle.get(i).getDetSecuencial());
                                invVentasDetalleFormula.setDetPendiente(invVentas.getVtaPendiente());
                                invVentasDetalleFormula.setInvBodega(listaInvDetalle.get(i).getInvBodega());
                                invVentasDetalleFormula.setDetObservaciones(listaInvDetalle.get(i).getDetObservaciones());
                                invVentasDetalleFormula.setDetConversionPesoNeto(listaInvDetalle.get(i).getDetConversionPesoNeto());
                                invVentasDetalleFormula.setDetEmpaque(listaInvDetalle.get(i).getDetEmpaque());
                                invVentasDetalleFormula.setDetEmpaqueCantidad(listaInvDetalle.get(i).getDetEmpaqueCantidad());
                                listaInvVentasDetalleFormula.add(invVentasDetalleFormula);
                            }
                        }
                    }
                    //***********************
                }
                //***********************
                if (listaInvVentasDetalleFormula != null && listaInvVentasDetalleFormula.size() > 0) {
                    for (int i = 0; i < listaInvVentasDetalleFormula.size(); i++) {
                        listaInvVentasDetalleFormula.get(i).setInvVentas(invVentas);
                        ventasDetalleDao.saveOrUpdate(listaInvVentasDetalleFormula.get(i));
                    }
                }
                //***********************
            }
        }

        if (!listaInvDetalleEliminar.isEmpty()) {
            for (int i = 0; i < listaInvDetalleEliminar.size(); i++) {
                listaInvDetalleEliminar.get(i).setInvVentas(invVentas);
                ventasDetalleDao.eliminar(listaInvDetalleEliminar.get(i));
            }
        }

        if (!invVentas.getVtaDocumentoTipo().equals("00") && anxVentas != null) {
            ventaDao.saveOrUpdate(anxVentas);
        }

        if (sisSuceso != null) {
            sucesoDao.insertar(sisSuceso);
            /////////////////////crear suceso
            SisSucesoVenta sucesoVenta = new SisSucesoVenta();
            InvVentas copia = ConversionesInventario.convertirInvVentas_InvVentas(invVentas);
            List<InvVentasDetalle> detalleCopia = new ArrayList<>();
            for (InvVentasDetalle en : listaInvDetalle) {
                InvVentasDetalle det = ConversionesInventario.convertirInvVentasDetalle_InvVentasDetalle(en);
                det.setInvVentas(null);
                detalleCopia.add(det);
            }
            copia.setInvVentasDetalleList(detalleCopia);
            String json = UtilsJSON.objetoToJson(copia);
            sucesoVenta.setSusJson(json);
            sucesoVenta.setSisSuceso(sisSuceso);
            sucesoVenta.setInvVentas(copia);
            sucesoVentaDao.insertar(sucesoVenta);
        }

        if (!listaSisSuceso.isEmpty()) {
            for (int i = 0; i < listaSisSuceso.size(); i++) {
                sucesoDao.saveOrUpdate(listaSisSuceso.get(i));
            }
        }

        if (invVentasComplemento != null) {
            if (complemento.trim().equals("CREAR")) {
                invVentasComplemento.setInvVentas(invVentas);
                invVentasComplemento.setInvVentasComplementoPK(new InvVentasComplementoPK(
                        invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                        invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero()));
                ventasComplementoDao.insertar(invVentasComplemento);
            } else if (complemento.trim().equals("MODIFICAR")) {
                invVentasComplemento.setInvVentas(invVentas);
                invVentasComplemento.setInvVentasComplementoPK(new InvVentasComplementoPK(
                        invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                        invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero()));
                ventasComplementoDao.saveOrUpdate(invVentasComplemento);
            } else if (complemento.trim().equals("ELIMINAR")) {
                invVentasComplemento.setInvVentas(invVentas);
                invVentasComplemento.setInvVentasComplementoPK(new InvVentasComplementoPK(
                        invVentas.getInvVentasPK().getVtaEmpresa(), invVentas.getInvVentasPK().getVtaPeriodo(),
                        invVentas.getInvVentasPK().getVtaMotivo(), invVentas.getInvVentasPK().getVtaNumero()));
                ventasComplementoDao.eliminar(invVentasComplemento);
            }
        }
        if (invVentasMotivoAnulacion != null) {
            if (eliminarMotivoAnulacion) {
                // ventasMotivoAnulacionDao.eliminar(invVentasMotivoAnulacion);
            } else {
                ventasMotivoAnulacionDao.insertar(invVentasMotivoAnulacion);
            }
        }

        //GUIA REMISION
        if (invVentaGuiaRemision != null) {
            invVentaGuiaRemision.setInvVentas(invVentas);
            InvVentaGuiaRemision local = ventaGuiaRemisionDao.obtenerInvVentaGuiaRemision(invVentaGuiaRemision.getDetSecuencial());
            if (local != null) {
                ventaGuiaRemisionDao.actualizar(invVentaGuiaRemision);
            } else {
                ventaGuiaRemisionDao.insertar(invVentaGuiaRemision);
            }
        } else {
            //eliminar si no hay (SUPRIMIR)
        }

        //LIQUIDACION
        if (!listaInvVentasLiquidacion.isEmpty()) {
            for (int i = 0; i < listaInvVentasLiquidacion.size(); i++) {
                listaInvVentasLiquidacion.get(i).setInvVentas(invVentas);
                ventasLiquidacionDao.saveOrUpdate(listaInvVentasLiquidacion.get(i));
            }
        } else {
            //eliminar si no hay (SUPRIMIR)
        }

        if (!listaInvVentasLiquidacionEliminar.isEmpty()) {
            for (int i = 0; i < listaInvVentasLiquidacionEliminar.size(); i++) {
                listaInvVentasLiquidacionEliminar.get(i).setInvVentas(invVentas);
                ventasLiquidacionDao.eliminar(listaInvVentasLiquidacionEliminar.get(i));
            }
        }

        //EXPORTACION
        if (anexo != null) {
            anexo.setVtaEmpresa(invVentas.getInvVentasPK().getVtaEmpresa());
            anexo.setVtaPeriodo(invVentas.getInvVentasPK().getVtaPeriodo());
            anexo.setVtaMotivo(invVentas.getInvVentasPK().getVtaMotivo());
            anexo.setVtaNumero(invVentas.getInvVentasPK().getVtaNumero());
            anxVentaExportacionDao.saveOrUpdate(anexo);
        } else {
            //eliminar si no hay (SUPRIMIR)
        }

        //REEMBOLSO
        if (!listAnxVentaReembolso.isEmpty()) {
            for (int i = 0; i < listAnxVentaReembolso.size(); i++) {
                listAnxVentaReembolso.get(i).setInvVenta(invVentas);
                if (listAnxVentaReembolso.get(i).getReembSecuencial() != null && listAnxVentaReembolso.get(i).getReembSecuencial() != 0) {
                    anxVentaReembolsoDao.actualizar(listAnxVentaReembolso.get(i));
                } else {
                    anxVentaReembolsoDao.insertar(listAnxVentaReembolso.get(i));
                }
            }
        } else {
            //eliminar si no hay (SUPRIMIR)
        }

        if (!listAnxVentaReembolsoEliminar.isEmpty()) {
            for (int i = 0; i < listAnxVentaReembolsoEliminar.size(); i++) {
                listAnxVentaReembolsoEliminar.get(i).setInvVenta(invVentas);
                anxVentaReembolsoDao.eliminar(listAnxVentaReembolsoEliminar.get(i));
            }
        }
        return true;
    }

    @Override
    public InvVentas buscarInvVentas(String empresa, String perCodigo, String motCodigo, String vtaNumero)
            throws Exception {
        return obtenerPorId(InvVentas.class, new InvVentasPK(empresa, perCodigo, motCodigo, vtaNumero));
    }

    @Override
    public InvConsultaVentasFacturasPorNumeroTO getConsultaVentasFacturasPorNumeroTO(String codigoEmpresa,
            String numFactura) throws Exception {
        String sql = "SELECT vta_empresa, vta_periodo, vta_motivo, vta_numero " + "FROM inventario.inv_ventas "
                + "WHERE (vta_empresa = '" + codigoEmpresa
                + "' AND vta_documento_tipo='18' AND vta_documento_numero = '" + numFactura
                + "' AND vta_anulado = false )";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvConsultaVentasFacturasPorNumeroTO.class);
    }

    @Override
    public InvConsultaVentasFacturasPorNumeroTO getConsultaVentasFacturasPorTipoYNumeroTO(String codigoEmpresa, String tipoDocumento, String numero) throws Exception {
        String sql = "SELECT vta_empresa, vta_periodo, vta_motivo, vta_numero " + "FROM inventario.inv_ventas "
                + "WHERE (vta_empresa = '" + codigoEmpresa
                + "' AND vta_documento_tipo='" + tipoDocumento + "' AND vta_documento_numero = '" + numero
                + "' AND vta_anulado = false )";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvConsultaVentasFacturasPorNumeroTO.class);
    }

    @Override
    public InvVentas buscarVentaPorDocumentoNumero(String empresaCodigo, String cliCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception {
        String sql = "SELECT * " + "FROM inventario.inv_ventas WHERE vta_empresa = ('" + empresaCodigo + "') "
                + "AND cli_codigo = ('" + cliCodigo + "') AND vta_documento_tipo = ('" + compDocumentoTipo + "') "
                + "AND vta_documento_numero = ('" + compDocumentoNumero + "') AND NOT vta_anulado";

        return genericSQLDao.obtenerObjetoPorSql(sql, InvVentas.class);

    }

    @Override
    public InvVentas obtenerVentaPorNumero(String empresaCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception {
        String sql = "SELECT * " + "FROM inventario.inv_ventas WHERE vta_empresa = ('" + empresaCodigo + "') "
                + "AND vta_documento_tipo = ('" + compDocumentoTipo + "') "
                + "AND vta_documento_numero = ('" + compDocumentoNumero + "') AND NOT vta_anulado";

        return genericSQLDao.obtenerObjetoPorSql(sql, InvVentas.class);

    }

    @Override
    public List<InvVentasDetalle> obtenerVentaDetallePorNumero(String empresa, String periodo, String motivo,
            String numero) throws Exception {
        String sql = "SELECT * " + "FROM inventario.inv_ventas_detalle WHERE vta_empresa = ('" + empresa + "') "
                + "AND vta_periodo = ('" + periodo + "') "
                + "AND vta_motivo = ('" + motivo + "') AND vta_numero = ('" + numero + "') " + "Order by det_secuencial ASC";

        return genericSQLDao.obtenerPorSql(sql, InvVentasDetalle.class);

    }

    @Override
    public String getConteoNotaCreditoVenta(String empresaCodigo, String cliCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception {
        String retorno = "";
        String sql = "SELECT vta_empresa|| vta_periodo || vta_motivo || vta_numero "
                + "FROM inventario.inv_ventas WHERE vta_empresa = ('" + empresaCodigo + "') " + "AND cli_codigo = ('"
                + cliCodigo + "') AND vta_documento_tipo = ('" + compDocumentoTipo + "') "
                + "AND vta_documento_numero = ('" + compDocumentoNumero + "') AND NOT vta_anulado";

        Object objt = genericSQLDao.obtenerObjetoPorSql(sql);
        if (objt != null) {
            retorno = (String) objt;
        } else {
            retorno = "";
        }

        return retorno;
    }

    @Override
    public String getConteoNumeroFacturaVenta(String empresaCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception {
        String sql = "SELECT vta_empresa || vta_periodo || vta_motivo || vta_numero "
                + "FROM inventario.inv_ventas WHERE vta_empresa = ('" + empresaCodigo + "') "
                + "AND vta_documento_tipo = ('" + compDocumentoTipo + "') " + "AND vta_documento_numero = ('"
                + compDocumentoNumero + "')";
        //
        // Object[] array = (Object[]) genericSQLDao.obtenerObjetoPorSql(sql);
        // if (array != null) {
        // retorno = array[0].toString().trim() + array[1].toString().trim() +
        // array[2].toString().trim()
        // + array[3].toString().trim();
        // }

        String obj = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return obj == null ? "" : obj;
    }

    @Override
    public InvVentaCabeceraTO getInvVentaCabeceraTO(String empresa, String periodo, String motivo, String numeroVenta)
            throws Exception {
        String sql = "SELECT vta_documento_tipo, vta_documento_numero, "
                + "SUBSTRING(cast(vta_fecha as TEXT), 1, 10) as vta_fecha,"
                + "SUBSTRING(cast(vta_fecha_vencimiento as TEXT), 1, 10) as vta_fecha_vencimiento, "
                + "vta_iva_vigente, vta_observaciones, vta_informacion_adicional, "
                + "vta_pendiente, vta_revisado, vta_anulado, "
                + "vta_forma_pago, vta_base0, vta_base_imponible, vta_montoiva, vta_descuento_base0, "
                + "vta_descuento_base_imponible, vta_descuento_base_no_objeto, vta_descuento_base_exenta, "
                + "vta_subtotal_base0, vta_subtotal_base_imponible, vta_subtotal_base_no_objeto, "
                + "vta_subtotal_base_exenta, vta_base_no_objeto, vta_base_exenta, vta_total, "
                + "cli_codigo, sec_codigo, con_periodo, con_tipo, con_numero, usr_codigo, "
                + "usr_fecha_inserta, com_documento_tipo, com_documento_numero, "
                + "vta_pagado_efectivo,  vta_pagado_dinero_electronico, vta_pagado_tarjeta_credito, vta_pagado_otros, usr_modifica, usr_fecha_modifica, usr_comentario, "
                + "fc_secuencial, fc_banco, fc_cuenta, fc_cheque, fc_lote, fc_titular, bod_empresa, bod_codigo, vta_lista_de_precios, vta_vendedor, vta_consignacion, "
                + "cli_codigo_establecimiento, vta_monto_ice, fecha_ultima_validacion_sri, vta_negociable  "
                + "FROM inventario.inv_ventas LEFT JOIN inventario.inv_ventas_complemento "
                + "ON inv_ventas.vta_empresa = inv_ventas_complemento.vta_empresa AND "
                + "inv_ventas.vta_periodo = inv_ventas_complemento.vta_periodo AND "
                + "inv_ventas.vta_motivo = inv_ventas_complemento.vta_motivo AND "
                + "inv_ventas.vta_numero = inv_ventas_complemento.vta_numero "
                + "WHERE (inv_ventas.vta_empresa = '" + empresa + "') AND "
                + "inv_ventas.vta_periodo = ('" + periodo + "') AND "
                + "inv_ventas.vta_motivo = ('" + motivo + "') AND "
                + "inv_ventas.vta_numero = ('" + numeroVenta + "')";
        List<InvVentaCabeceraTO> lista = genericSQLDao.obtenerPorSql(sql, InvVentaCabeceraTO.class);
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Override
    public Object[] getVenta(String empresa, String periodo, String conTipo, String numero) {
        String sql = "select vta_empresa, vta_periodo, vta_motivo, vta_numero "
                + "from inventario.inv_ventas where con_empresa='" + empresa + "' AND " + "con_periodo='" + periodo
                + "' AND con_tipo='" + conTipo + "' AND con_numero='" + numero + "'";
        Object[] array = (Object[]) genericSQLDao.obtenerPorSql(sql, Object.class).get(0);
        return array != null ? array : null;
    }

    @Override
    public List<InvListaConsultaVentaTO> getFunVentasListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception {
        String pendiente = null;
        String anulado = null;
        if (status.equals("PENDIENTE")) {
            pendiente = "'PENDIENTE'";
            anulado = null;
        }
        if (status.equals("ANULADO")) {
            pendiente = null;
            anulado = "'ANULADO'";
        }
        if (status.equals("AMBOS")) {
            pendiente = "'PENDIENTE'";
            anulado = "'ANULADO'";
        }
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM inventario.fun_ventas_listado('" + empresa + "', null, null, null) "
                + "WHERE vta_fecha >= " + fechaDesde + " AND vta_fecha <= " + fechaHasta + " " + "AND vta_pendiente = "
                + pendiente + " OR vta_anulado = " + anulado;

        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaVentaTO.class);
    }

    @Override
    public List<InvFunVentasConsolidandoProductosTO> getInvFunVentasConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega, String cliente) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        bodega = bodega == null ? bodega : "'" + bodega + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        String sql = "SELECT * FROM inventario.fun_ventas_consolidando_productos('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + ", " + bodega + ", " + cliente + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunVentasConsolidandoProductosTO.class);
    }

    @Override
    public List<InvFunVentasConsolidandoProductosCoberturaTO> getInvFunVentasConsolidandoProductosCoberturaTO(String empresa,
            String desde, String hasta, String sector, String bodega, String motivo, String cliente) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        bodega = bodega == null ? bodega : "'" + bodega + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        String sql = "SELECT * FROM inventario.fun_ventas_consolidando_productos_cobertura('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + ", " + bodega + ", " + motivo + ", " + cliente + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunVentasConsolidandoProductosCoberturaTO.class);
    }

    @Override
    public List<InvFunVentasConsolidandoClientesTO> getInvFunVentasConsolidandoClientesTO(String empresa, String sector,
            String desde, String hasta) throws Exception {

        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM inventario.fun_ventas_consolidando_clientes('" + empresa + "', " + sector + ", "
                + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunVentasConsolidandoClientesTO.class);
    }

    @Override
    public List<InvFunVentasVsCostoTO> getInvFunVentasVsCostoTO(String empresa, String desde, String hasta,
            String bodega, String cliente) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        bodega = bodega == null ? bodega : "'" + bodega + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        String sql = "SELECT * FROM inventario.fun_ventas_vs_costo('" + empresa + "', " + desde + ", " + hasta + ", "
                + bodega + ", " + cliente + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunVentasVsCostoTO.class);
    }

    @Override
    public List<InvListaConsultaVentaTO> getListaInvConsultaVenta(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros) throws Exception {
        String limit = "";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_ventas_listado('" + empresa + "', '" + periodo + "', '" + motivo
                + "', '" + busqueda + "')" + limit;

        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaVentaTO.class);
    }

    @Override
    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaPorTipoDoc(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, String tipoDocumento) throws Exception {
        String limit = "";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_ventas_listado('" + empresa + "', '" + periodo + "', '" + motivo
                + "', '" + busqueda + "')";

        if (tipoDocumento != null && !tipoDocumento.equals("")) {
            sql = sql + " WHERE vta_documento_tipo = '" + tipoDocumento + "'";
        }
        if (!limit.equals("")) {
            sql = sql + limit;
        }

        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaVentaTO.class);
    }

    @Override
    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaFiltrado(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, String tipoDocumento) throws Exception {
        String limit = "";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_ventas_listadofiltrado('" + empresa + "', '" + periodo + "', '" + motivo
                + "', '" + busqueda + "', '" + tipoDocumento + "')" + limit;

        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaVentaTO.class);
    }

    @Override
    public List<InvFunVentasTO> getInvFunVentasTO(String empresa, String desde, String hasta, String motivo,
            String cliente, String documento, String grupo_empresarial) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        documento = documento == null ? documento : "'" + documento + "'";
        grupo_empresarial = grupo_empresarial == null ? grupo_empresarial : "'" + grupo_empresarial + "'";
        String sql = "SELECT * FROM inventario.fun_ventas('" + empresa + "', " + desde + ", " + hasta + ", " + motivo
                + ", " + cliente + ", " + documento + ", " + grupo_empresarial + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunVentasTO.class);
    }

    @Override
    public List<InvFunVentasTO> listarInvFunVentasTO(String empresa, String desde, String hasta, String motivo,
            String cliente, String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        documento = documento == null ? documento : "'" + documento + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        grupo_empresarial = grupo_empresarial == null ? grupo_empresarial : "'" + grupo_empresarial + "'";
        estado = estado == null ? estado : "'" + estado + "'";

        String sqlFC = formaCobro != null && !formaCobro.equals("") ? " WHERE vta_forma_pago='" + formaCobro + "'" : "";

        String sql = "SELECT * FROM inventario.fun_ventas_sector('" + empresa + "', " + desde + ", " + hasta + ", " + motivo
                + ", " + cliente + ", " + documento + ", " + sector + "," + estado + "," + grupo_empresarial + "," + incluirTodos + ")" + sqlFC;

        return genericSQLDao.obtenerPorSql(sql, InvFunVentasTO.class);
    }

    @Override
    public List<InvFunVentasTO> listarInvFunVentasTOAgrupadoTipoDocumento(String empresa, String desde, String hasta, String motivo, String cliente, String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        documento = documento == null ? documento : "'" + documento + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        grupo_empresarial = grupo_empresarial == null ? grupo_empresarial : "'" + grupo_empresarial + "'";
        estado = estado == null ? estado : "'" + estado + "'";

        String sqlFC = formaCobro != null && !formaCobro.equals("") ? " WHERE vta_forma_pago='" + formaCobro + "'" : "";

        String sql = "SELECT * FROM inventario.fun_ventas_sector_agrupado_tipo_documento('" + empresa + "', " + desde + ", " + hasta + ", " + motivo
                + ", " + cliente + ", " + documento + ", " + sector + "," + estado + "," + grupo_empresarial + "," + incluirTodos + ")" + sqlFC;

        return genericSQLDao.obtenerPorSql(sql, InvFunVentasTO.class);
    }

    @Override
    public List<InvFunVentasTO> listarInvFunVentasTOAgrupadoTipoContribuyente(String empresa, String desde, String hasta, String motivo, String cliente, String documento, String sector, String estado, String grupo_empresarial, String formaCobro, boolean incluirTodos) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        documento = documento == null ? documento : "'" + documento + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        grupo_empresarial = grupo_empresarial == null ? grupo_empresarial : "'" + grupo_empresarial + "'";
        estado = estado == null ? estado : "'" + estado + "'";

        String sqlFC = formaCobro != null && !formaCobro.equals("") ? " WHERE vta_forma_pago='" + formaCobro + "'" : "";

        String sql = "SELECT * FROM inventario.fun_ventas_sector_agrupado_tipo_contribuyente('" + empresa + "', " + desde + ", " + hasta + ", " + motivo
                + ", " + cliente + ", " + documento + ", " + sector + "," + estado + "," + grupo_empresarial + "," + incluirTodos + ")" + sqlFC;

        return genericSQLDao.obtenerPorSql(sql, InvFunVentasTO.class);
    }

    @Override
    public List<InvFunVentasVendedorTO> listarInvFunVentasVendedorTO(String empresa, String desde, String hasta) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT * FROM inventario.fun_ventas_vendedor('" + empresa + "', " + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunVentasVendedorTO.class);
    }

    @Override
    public BigDecimal getInvVentaTotal(String empresa, String desde, String hasta, String cliente) throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        String sql = "SELECT COALESCE(SUM(vta_total), 0.00) FROM inventario." + "fun_ventas('" + empresa + "', '"
                + desde + "', '" + hasta + "', NULL, '" + cliente + "', NULL) "
                + "WHERE not vta_pendiente AND not vta_anulado and vta_forma_pago = 'POR PAGAR'";
        List<BigDecimal> lista = genericSQLDao.obtenerPorSql(sql);
        if (lista != null) {
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        }
        return cero;
    }

    @Override
    public List<InvListadoCobrosTO> invListadoCobrosTO(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        String sql = "SELECT * FROM cartera.fun_obtener_cobros('" + empresa + "', '" + periodo + "', '" + motivo
                + "', '" + numero + "')";
        return genericSQLDao.obtenerPorSql(sql, InvListadoCobrosTO.class);
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception {
        String sql = "SELECT vta_pendiente as comp_pendiente, vta_anulado as comp_anulado, vta_pendiente as est_bloqueado, vta_revisado as est_generado, false as con_reversado FROM inventario.inv_ventas WHERE "
                + "vta_empresa = '" + empresa + "' AND vta_periodo = '" + periodo + "' " + "AND vta_motivo = '"
                + motivoTipo + "' AND vta_numero = '" + numero + "'";
        return genericSQLDao.obtenerPorSql(sql, InvEstadoCCCVT.class).isEmpty() ? null
                : genericSQLDao.obtenerPorSql(sql, InvEstadoCCCVT.class).get(0);
    }

    @Override
    public String eliminarVentas(String empresa, String periodo, String motivo, String numero, SisSuceso sisSuceso) throws Exception {
        String sql = "SELECT * FROM inventario.fun_eliminar_ventas('" + empresa + "', '" + periodo + "', '" + motivo + "', '" + numero + "')";
        String respuesta = "";
        try {
            String respuestaFuncion = (String) genericSQLDao.obtenerObjetoPorSql(sql);
            if (respuestaFuncion.substring(0, 1).equalsIgnoreCase("T")) {
                sucesoDao.insertar(sisSuceso);
                respuesta = "t";
            } else {
                respuesta = respuesta + ". N: " + sisSuceso.getSusClave();
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respuesta = e.getMessage() + ". N: " + sisSuceso.getSusClave();
        }
        return respuesta;
    }

    @Override
    public void actualizar(InvVentas venta, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(venta);
    }

    @Override
    public InvVentasTO getInvVentasTO(String empresa, String tipo, String numeroDocumento) throws Exception {
        InvVentas invVentas = obtenerVentaPorNumero(empresa, tipo, numeroDocumento);
        InvVentasTO ventasTO = null;
        if (invVentas != null) {
            ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(invVentas);
        }
        return ventasTO;
    }

    @Override
    public InvVentasTO getInvVentasTO(String empresa, String periodo, String motivo, String numero) throws Exception {
        InvVentas invVentas = buscarInvVentas(empresa, periodo, motivo, numero);
        InvVentasTO ventasTO = null;
        if (invVentas != null) {
            ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(invVentas);
        }
        return ventasTO;
    }

    @Override
    public Boolean actualizarClaveExternaVenta(InvVentasPK pk, String clave, SisSuceso sisSuceso) throws Exception {
        String sql = "UPDATE inventario.inv_ventas SET vta_clave_acceso_externa=" + clave
                + " WHERE vta_empresa='" + pk.getVtaEmpresa()
                + "' and vta_periodo='" + pk.getVtaPeriodo()
                + "' and vta_motivo='" + pk.getVtaMotivo()
                + "' and vta_numero='" + pk.getVtaNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
        sucesoDao.insertar(sisSuceso);
        return true;
    }
//SALDOS POR COBRAR

    @Override
    public List<InvVentas> obtenerListadoVentasSaldosImportados(String empresa, String motivo, String sector, String fecha) throws Exception {
        String sqlMotivo = motivo != null ? " AND vta_motivo='" + motivo + "'" : "";
        String sqlSector = sector != null ? " AND sec_codigo='" + sector + "'" : "";
        String sqlFecha = fecha != null ? " AND vta_fecha='" + fecha + "'" : "";
        String sql = "select * from inventario.inv_ventas where vta_empresa = '" + empresa + "'"
                + sqlMotivo
                + sqlSector
                + sqlFecha
                + " and vta_saldo_importado is true";
        List<InvVentas> ventas = genericSQLDao.obtenerPorSql(sql, InvVentas.class);

        if (ventas != null) {
            for (InvVentas venta : ventas) {
                venta.setInvVentasRecepcionList(null);
                venta.setInvVentasDetalleList(null);
                venta.setInvVentasComplementoList(null);
                venta.setInvVentasMotivoAnulacionList(null);
            }
        }
        return ventas;
    }

    @Override
    public boolean insertarInvVentas(InvVentas invVentas, SisSuceso sisSuceso) throws Exception {
        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionVenta(
                invVentas.getInvVentasPK().getVtaEmpresa(),
                invVentas.getInvVentasPK().getVtaPeriodo(),
                invVentas.getInvVentasPK().getVtaMotivo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }
            invVentas.setInvVentasPK(new InvVentasPK(
                    invVentas.getInvVentasPK().getVtaEmpresa(),
                    invVentas.getInvVentasPK().getVtaPeriodo(),
                    invVentas.getInvVentasPK().getVtaMotivo(),
                    rellenarConCeros + numeracion));
        } while (buscarInvVentas(
                invVentas.getInvVentasPK().getVtaEmpresa(),
                invVentas.getInvVentasPK().getVtaPeriodo(),
                invVentas.getInvVentasPK().getVtaMotivo(),
                rellenarConCeros + numeracion) != null);

        insertar(invVentas);

        String susClave = invVentas.getInvVentasPK().getVtaPeriodo() + " | " + invVentas.getInvVentasPK().getVtaMotivo() + " | " + invVentas.getInvVentasPK().getVtaNumero();
        String susDetalle = "Se guardó la venta con código: " + susClave + " desde saldos iniciales de Cuentas por cobrar";

        sisSuceso.setSusClave(susClave);
        sisSuceso.setSusDetalle(susDetalle);

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarInvVentas(InvVentas invVentas, SisSuceso sisSuceso) throws Exception {
        actualizar(invVentas);
        String susClave = invVentas.getInvVentasPK().getVtaPeriodo() + " | " + invVentas.getInvVentasPK().getVtaMotivo() + " | " + invVentas.getInvVentasPK().getVtaNumero();
        String susDetalle = "Se modificó la venta con código: " + susClave + " desde saldos por cobrar";

        sisSuceso.setSusClave(susClave);
        sisSuceso.setSusDetalle(susDetalle);

        sucesoDao.insertar(sisSuceso);
        return true;
    }

//IMAGENES
    @Override
    public List<InvVentasDatosAdjuntos> listarImagenesDeVenta(InvVentasPK pk) throws Exception {
        String sql = "select * from inventario.inv_ventas_datos_adjuntos "
                + "where vta_empresa = '" + pk.getVtaEmpresa() + "' and "
                + "vta_periodo = '" + pk.getVtaPeriodo() + "' and "
                + "vta_motivo = '" + pk.getVtaMotivo() + "' and "
                + "vta_numero = '" + pk.getVtaNumero() + "';";
        List<InvVentasDatosAdjuntos> imagenes = genericSQLDao.obtenerPorSql(sql, InvVentasDatosAdjuntos.class);
        if (imagenes != null && !imagenes.isEmpty()) {
            for (InvVentasDatosAdjuntos imagen : imagenes) {
                imagen.setInvVentas(new InvVentas(pk));
            }
        }
        return imagenes;
    }

    @Override
    public boolean actualizarImagenesVenta(List<InvVentasDatosAdjuntos> listado, InvVentasPK pk, List<SisSuceso> sisSucesos) throws Exception {
        SisPeriodo periodo = periodoService.buscarPeriodo(pk.getVtaEmpresa(), pk.getVtaPeriodo());
        if (periodo != null && !periodo.getPerCerrado()) {
            List<InvVentasDatosAdjuntos> listAdjuntosEnLaBase = listarImagenesDeVenta(pk);
            if (listado != null && !listado.isEmpty()) {
                if (listAdjuntosEnLaBase.size() > 0) {
                    //*******************VERIFICAR PERIODO**************************

                    listado.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                        listAdjuntosEnLaBase.removeIf(n -> (n.getAdjSecuencial().equals(item.getAdjSecuencial())));
                    });
                    if (listAdjuntosEnLaBase.size() > 0) {
                        listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                            invVentasDatosAdjuntosDao.eliminar(itemEliminar);
                            AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                        });
                    }
                }
                insertarImagenesVenta(listado, pk);
            } else {
                if (listAdjuntosEnLaBase.size() > 0) {
                    listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                        invVentasDatosAdjuntosDao.eliminar(itemEliminar);
                        AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                    });

                }
            }
            if (sisSucesos != null && sisSucesos.size() > 0) {
                sucesoDao.insertar(sisSucesos);
            }
        } else {
            throw new GeneralException("No se puede adjuntar imágenes debido a que el periodo se encuentra cerrado.");
        }

        return true;
    }

    public String insertarImagenesVenta(List<InvVentasDatosAdjuntos> listado, InvVentasPK pk) throws Exception {
        String bucket = sistemaWebServicio.obtenerRutaImagen(pk.getVtaEmpresa());
        Bucket b = AmazonS3Crud.crearBucket(bucket);
        String resp = "T";
        if (b != null) {
            for (InvVentasDatosAdjuntos invAdjunto : listado) {
                if (invAdjunto.getAdjSecuencial() == null) {
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(invAdjunto.getAdjClaveArchivo(), invAdjunto.getImagenString());
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "ventas/" + pk.getVtaPeriodo() + "/" + pk.getVtaMotivo() + "/" + pk.getVtaNumero() + "/";
                    invAdjunto.setInvVentas(new InvVentas(pk));
                    invAdjunto.setAdjBucket(bucket);
                    invAdjunto.setAdjClaveArchivo(carpeta + nombre);
                    invAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    invVentasDatosAdjuntosDao.insertar(invAdjunto);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, invAdjunto.getImagenString(), combo.getValor());
                }
            }

        } else {
            resp = "FError al crear contenedor de imágenes.";
        }
        return resp;
    }

    @Override
    public InvVentas obtenerVentaPorContable(String empresa, String conPeriodo, String conTipo, String conNumero) throws Exception {
        String sql = "SELECT * " + "FROM inventario.inv_ventas WHERE con_empresa = ('" + empresa + "') "
                + "AND con_periodo = ('" + conPeriodo + "') "
                + "AND con_tipo = ('" + conTipo + "') "
                + "AND con_numero = ('" + conNumero + "') ";

        return genericSQLDao.obtenerObjetoPorSql(sql, InvVentas.class);
    }

}
