package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.ActivoFijoDao;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.UbicacionActivoFijoDao;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.service.CategoriaActivoFijoService;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.service.DepreciacionActivoFijoService;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.service.UbicacionActivoFijoService;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxHomologacionProductoDao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.ConceptoDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.SustentoDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoPorcentajeIceService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PorcentajeIvaService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.NumeracionVariosDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoCategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoMarcaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoMedidaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoPresentacionCajasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoPresentacionUnidadesDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoTipoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProveedorDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.CorridaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesActivoFijo;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfActivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfCategoriasTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfUbicacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivosPK;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfCategorias;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfCategoriasPK;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfUbicaciones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPorcentajeIce;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ImportarProductos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosProductosWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosImpresionPlacasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioCantidadTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCompraSustentoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvMedidaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoDAOTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoFormulaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoRelacionadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSincronizarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductosConErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoDetalleCuentaContable;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedida;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidades;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoRelacionados;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.DatoFunListaProductosImpresionPlaca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdBalanceadoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private NumeracionVariosDao numeracionVariosDao;
    @Autowired
    private ProductoCategoriaDao productoCategoriaDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private ProductoMarcaDao productoMarcaDao;
    @Autowired
    private ProductoMedidaDao productoMedidaDao;
    @Autowired
    private ProductoPresentacionCajasDao productoPresentacionCajasDao;
    @Autowired
    private ProductoPresentacionUnidadesDao productoPresentacionUnidadesDao;
    @Autowired
    private ProductoTipoDao productoTipoDao;
    @Autowired
    private CorridaDao corridaDao;
    @Autowired
    private ConceptoDao conceptoDao;
    @Autowired
    private GenericoDao<InvProducto, InvProductoPK> productoCriteriaDao;
    @Autowired
    private GenericoDao<InvProductoDetalleCuentaContable, Integer> productoDetalleCuentaContableDao;
    @Autowired
    private GenericoDao<InvProductoEtiquetas, String> productoEtiquetasDao;
    @Autowired
    private SustentoDao sustentoDao;
    @Autowired
    private EstructuraDao estructuraDao;
    @Autowired
    private PorcentajeIvaService porcentajeIvaService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private CuentasService cuentasService;
    @Autowired
    private UbicacionActivoFijoService ubicacionActivoFijoService;
    @Autowired
    private CategoriaActivoFijoService categoriaActivoFijoService;
    @Autowired
    private ActivoFijoDao activoDao;
    @Autowired
    private DepreciacionActivoFijoService depreciacionActivoFijoService;
    @Autowired
    private ProductoRelacionadoService productoRelacionadoService;
    @Autowired
    private ProductoFormulaService productoFormulaService;
    @Autowired
    private BodegaDao bodegaDao;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ProveedorDao proveedorDao;
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private ProductoCategoriaService productoCategoriaService;
    @Autowired
    private AnexoPorcentajeIceService anexoPorcentajeIceService;
    @Autowired
    private AnxHomologacionProductoDao anxHomologacionProductoDao;
    @Autowired
    private UbicacionActivoFijoDao ubicacionActivoFijoDao;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @Override
    public InvProductoDAOTO buscarInvProductoDAOTO(String empresa, String codigoProducto) throws Exception {
        return ConversionesInventario.convertirInvProducto_InvProductoDAOTO(productoDao.buscarInvProducto(empresa, codigoProducto));
    }

    @Override
    public AnxHomologacionProducto buscarInvProductoHomologado(String empresa, String codigoProducto, String codigoProveedor) throws Exception {
        return anxHomologacionProductoDao.obtenerHomologacionProducto(empresa, codigoProducto, codigoProveedor);
    }

    @Override
    public String evaluaAntesDeinsertarInvProductoTO(InvProductoTO invProductoTO, SisInfoTO sisInfoTO) throws Exception {
        return insertarInvProductoTO(invProductoTO, sisInfoTO);
    }

    @Override
    public Map<String, Object> insertarInvProducto(InvProducto invProducto, AfActivos afActivo, SisInfoTO sisInfoTO) throws Exception {
        String codigoGenerado = "";
        String retorno = "";
        Map<String, Object> respuesta = new HashMap<>();
        if (invProducto.getInvProductoPK().getProCodigoPrincipal() == null || invProducto.getInvProductoPK().getProCodigoPrincipal().trim().isEmpty()) {
            InvProductoTO invProductoTO = new InvProductoTO();
            invProductoTO.setProEmpresa(sisInfoTO.getEmpresa());
            invProducto.getInvProductoPK().setProCodigoPrincipal(productoDao.getInvProximaNumeracionProducto(sisInfoTO.getEmpresa(), invProductoTO));
            codigoGenerado = invProductoTO.getProCodigoPrincipal();
        }
        //****************************************************************************************************************************************************
        InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(sisInfoTO.getEmpresa(), invProducto.getInvProductoMedida().getInvProductoMedidaPK().getMedCodigo());
        InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(sisInfoTO.getEmpresa(), invProducto.getInvProductoMarca().getInvProductoMarcaPK().getMarCodigo());
        InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(sisInfoTO.getEmpresa(), invProducto.getInvProductoCategoria().getInvProductoCategoriaPK().getCatCodigo());
        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(sisInfoTO.getEmpresa(), invProducto.getInvProductoTipo().getInvProductoTipoPK().getTipCodigo());

        if (invProductoMedida != null) {

            if (invProductoMarca != null) {
                if (invProductoCategoria != null) {
                    if (invProductoTipo != null) {
                        // PREPARANDO OBJETO SISSUCESO
                        susClave = invProducto.getInvProductoPK().getProCodigoPrincipal();
                        susDetalle = "Se insertó el producto " + invProducto.getProNombre() + " con código " + invProducto.getInvProductoPK().getProCodigoPrincipal();
                        susSuceso = "INSERT";
                        susTabla = "inventario.inv_producto";
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        //Insertar
                        if (productoDao.buscarInvProducto(sisInfoTO.getEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()) == null) {
                            invProducto.getInvProductoPK().setProEmpresa(sisInfoTO.getEmpresa());
                            invProducto.setUsrEmpresa(sisInfoTO.getEmpresa());
                            invProducto.setUsrCodigo(sisInfoTO.getUsuario());
                            invProducto.setUsrFechaInserta(UtilsDate.timestamp());
                            invProducto.setProIncluirWebshop(-1);
                            invProducto.setProCuentaEmpresa(sisInfoTO.getEmpresa());
                            ///// BUSCAR POR ALTERNO
                            if (productoDao.getProductoRepetido("'" + sisInfoTO.getEmpresa() + "'", null,
                                    (invProducto.getProCodigoAlterno() == null || invProducto.getProCodigoAlterno().trim().isEmpty() ? null : "'" + invProducto.getProCodigoAlterno() + "'"),
                                    null, null, null, null, null, null)) {
                                retorno = "FEl código alterno del producto " + invProducto.getProCodigoAlterno() + " ya existe en los registros.\nIntente ingresando otro Código...";
                            } else ///// BUSCAR POR BARRAS
                            if (productoDao.getProductoRepetido("'" + sisInfoTO.getEmpresa() + "'", null, null,
                                    "'" + (invProducto.getProCodigoBarra() != null ? invProducto.getProCodigoBarra() : "") + "'",
                                    "'" + (invProducto.getProCodigoBarra2() != null ? invProducto.getProCodigoBarra2() : "") + "'",
                                    "'" + (invProducto.getProCodigoBarra3() != null ? invProducto.getProCodigoBarra3() : "") + "'",
                                    "'" + (invProducto.getProCodigoBarra4() != null ? invProducto.getProCodigoBarra4() : "") + "'",
                                    "'" + (invProducto.getProCodigoBarra5() != null ? invProducto.getProCodigoBarra5() : "") + "'", null)) {
                                retorno = "FEl código de barras que ingresó ya existe en los registros.\nIntente ingresando otro Código...";
                            } else //// BUSCAR POR NOMBRE
                            if (productoDao.getProductoRepetido("'" + sisInfoTO.getEmpresa() + "'", null, null, null,
                                    null, null, null, null, (invProducto.getProNombre() == null || invProducto.getProNombre().trim().isEmpty() ? null : "'" + invProducto.getProNombre() + "'"))) {
                                retorno = "FEl nombre del producto " + invProducto.getProNombre() + " ya existe en los registros.\nIntente ingresando otro Nombre...";
                            } else {
                                retorno = "T";
                            }
                            if (retorno.charAt(0) == 'T') {
                                InvNumeracionVarios invNumeracionVariosAux = numeracionVariosDao.obtenerPorId(InvNumeracionVarios.class, sisInfoTO.getEmpresa());
                                InvNumeracionVarios invNumeracionVarios = ConversionesInventario.convertirInvNumeracionVarios_InvNumeracionVarios(invNumeracionVariosAux);

                                if (invNumeracionVarios == null && codigoGenerado.trim().isEmpty()) {
                                    invNumeracionVarios = null;
                                } else if (invNumeracionVarios == null && !codigoGenerado.trim().isEmpty()) {
                                    invNumeracionVarios = new InvNumeracionVarios(sisInfoTO.getEmpresa(), "00000", "00000", codigoGenerado, sisInfoTO.getEmpresa());
                                } else if (invNumeracionVarios != null && codigoGenerado.trim().isEmpty()) {
                                    invNumeracionVarios = null;
                                } else if (invNumeracionVarios != null && !codigoGenerado.trim().isEmpty()) {
                                    invNumeracionVarios.setNumProductos(codigoGenerado);
                                }
                                if (!productoDao.buscarExisteNombreProducto(sisInfoTO.getEmpresa(), invProducto.getProNombre().trim())) {
                                    ////****************VALIDAR CUENTA COMPRA****************
                                    boolean inventarioValido = false;
                                    String cuentaInventario = invProducto.getProCuentaInventario();
                                    if (cuentaInventario == null || cuentaInventario.equals("") || invProducto.getInvProductoTipo().getCtaCodigo() == null || invProducto.getInvProductoTipo().getCtaCodigo().equals("")) {
                                        inventarioValido = true;
                                    } else {
                                        int tamanioGrupoTipoProducto = invProducto.getInvProductoTipo().getCtaCodigo().length();
                                        String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                        if (grupoSeleccionado.equals(invProducto.getInvProductoTipo().getCtaCodigo())) {
                                            inventarioValido = true;
                                        }
                                    }
                                    //************************************************
                                    //CONVERTIR ACTIVO FIJO
                                    SisSuceso sisSucesoAF = null;
                                    if (afActivo != null) {
                                        afActivo.setAfDescripcion(invProducto.getProNombre());
                                        afActivo.setAfActivosPK(new AfActivosPK(sisInfoTO.getEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                                        afActivo.setUsrEmpresa(sisInfoTO.getEmpresa());
                                        afActivo.setUsrCodigo(sisInfoTO.getUsuario());
                                        afActivo.setUsrFechaInserta(UtilsDate.timestamp());
                                        String susSuceso1 = "INSERT";
                                        String susTabla1 = "activosfijos.af_activos";
                                        sisSucesoAF = Suceso.crearSisSuceso(susTabla1, "", susSuceso1, "", sisInfoTO);
                                    }
                                    if (inventarioValido) {
                                        if (productoDao.insertarInvProductoImportacion(invProducto, afActivo, sisSuceso, sisSucesoAF, invNumeracionVarios)) {
                                            retorno = "T<html>Se ha guardado el siguiente producto:<br><br>Código: <font size = 5>"
                                                    + invProducto.getInvProductoPK().getProCodigoPrincipal().trim()
                                                    + "</font>.<br>Nombre: <font size = 5>"
                                                    + invProducto.getProNombre().trim() + "</font>.</html>";

                                            //*********************VALIDAR COSTO AUTOMATICO*****************************
                                            InvProductoTO invProductoTO = new InvProductoTO();
                                            invProductoTO.setProEmpresa(invProducto.getInvProductoPK().getProEmpresa());
                                            invProductoTO.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
                                            invProductoTO.setProCuentaCostoAutomatico(invProducto.getProCuentaCostoAutomatico());
                                            boolean seCambiCodigo = eliminarCostoAutomatico(invProductoTO, sisInfoTO);
                                            if (seCambiCodigo && invProductoTO != null && invProductoTO.getProCuentaCostoAutomatico() != null && !invProductoTO.getProCuentaCostoAutomatico().equals("")) {
                                                InvProductoPK pk = new InvProductoPK(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal());
                                                if (invProductoTO.getProCuentaInventario() == null || invProductoTO.getProCuentaInventario().equals("")) {
                                                    Criterio filtro;
                                                    filtro = Criterio.forClass(InvProductoDetalleCuentaContable.class);
                                                    filtro.createAlias("invProducto", "producto");
                                                    filtro.add(Restrictions.eq("detCuenta", invProductoTO.getProCuentaCostoAutomatico()));
                                                    filtro.add(Restrictions.eq("producto.invProductoPK.proEmpresa", pk.getProEmpresa()));
                                                    List<InvProductoDetalleCuentaContable> listado = productoDetalleCuentaContableDao.buscarPorCriteriaSinProyecciones(filtro);
                                                    if (listado == null || listado.isEmpty()) {
                                                        InvProductoDetalleCuentaContable cuentaCostoAutomatico = new InvProductoDetalleCuentaContable();
                                                        cuentaCostoAutomatico.setDetCuenta(invProductoTO.getProCuentaCostoAutomatico());
                                                        cuentaCostoAutomatico.setInvProducto(new InvProducto(pk));
                                                        /// PREPARANDO OBJETO SISSUCESO
                                                        String susClave1 = cuentaCostoAutomatico.getDetCuenta();
                                                        String susDetalle1 = "Se insertó el productoDetalleCuentaContable " + cuentaCostoAutomatico.getDetCuenta() + " con código " + cuentaCostoAutomatico.getDetSecuencial();
                                                        String susSuceso1 = "UPDATE";
                                                        String susTabla1 = "inventario.inv_producto_detalle_cuenta_contable";
                                                        SisSuceso sisSuceso1 = Suceso.crearSisSuceso(susTabla1, susClave1, susSuceso1, susDetalle1, sisInfoTO);
                                                        sucesoDao.insertar(sisSuceso1);
                                                        productoDetalleCuentaContableDao.insertar(cuentaCostoAutomatico);
                                                    } else {
                                                        filtro.add(Restrictions.eq("producto.invProductoPK.proCodigoPrincipal", pk.getProCodigoPrincipal()));
                                                        List<InvProductoDetalleCuentaContable> listadoDeCuentasDelProducto = productoDetalleCuentaContableDao.buscarPorCriteriaSinProyecciones(filtro);
                                                        if (listadoDeCuentasDelProducto == null || listadoDeCuentasDelProducto.isEmpty()) {
                                                            retorno += ", pero con el siguiente error:<br> la cuenta de costo automático ya se encuentra asociado a otro producto.";
                                                        }
                                                    }
                                                } else {
                                                    retorno += ", pero con el siguiente error:<br> la cuenta inventario no debe estar presente si se desea agregar una cuenta de costo automático.";
                                                }
                                            }
                                            //***********************************************************************************************
                                        } else {
                                            retorno = "FHubo un error al guardar el producto...\nIntente de nuevo o contacte con el administrador.";
                                        }
                                    } else {
                                        retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                                    }
                                } else {
                                    retorno = "FEl nombre del producto: " + invProducto.getProNombre() + ",ya existe...\nIntente con otro...";
                                }
                            }
                        } else {
                            //Actualizar
                            susDetalle = "Se modificó el producto " + invProducto.getProNombre() + " con código " + invProducto.getInvProductoPK().getProCodigoPrincipal();
                            susSuceso = "UPDATE";
                            sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                            InvProducto invProductoAux = productoDao.buscarInvProducto(sisInfoTO.getEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal().trim());
                            //setear valores
                            invProductoAux.setInvProductoMedida(invProducto.getInvProductoMedida());
                            invProductoAux.setInvProductoMarca(invProducto.getInvProductoMarca());
                            invProductoAux.setInvProductoCategoria(invProducto.getInvProductoCategoria());
                            invProductoAux.setInvProductoTipo(invProducto.getInvProductoTipo());
                            invProductoAux.setProNombre(invProducto.getProNombre());
                            invProductoAux.setProDetalle(invProducto.getProDetalle());
                            invProductoAux.setProIva(invProducto.getProIva());
                            invProductoAux.setProCreditoTributario(invProducto.getProCreditoTributario());
                            invProductoAux.setProCuentaInventario(invProducto.getProCuentaInventario());
                            invProductoAux.setProCuentaVenta(invProducto.getProCuentaVenta());
                            invProductoAux.setProCuentaCostoAutomatico(invProducto.getProCuentaCostoAutomatico());
                            invProductoAux.setProCuentaGasto(invProducto.getProCuentaGasto());

                            //****
                            boolean isActivoFijo = afActivo != null;
                            SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
                            String categoriaCodigo = invProductoAux.getInvProductoCategoria().getInvProductoCategoriaPK().getCatCodigo();
                            String cuentaInventario = invProductoAux.getProCuentaInventario() != null && !invProductoAux.getProCuentaInventario().equals("") ? invProductoAux.getProCuentaInventario() : null;
                            String cuentaVenta = invProductoAux.getProCuentaVenta() != null && !invProductoAux.getProCuentaVenta().equals("") ? invProductoAux.getProCuentaVenta() : null;
                            String cuentaCostoAutomatico = invProductoAux.getProCuentaCostoAutomatico() != null && !invProductoAux.getProCuentaCostoAutomatico().equals("") ? invProductoAux.getProCuentaCostoAutomatico() : null;
                            String cuentaGasto = invProductoAux.getProCuentaGasto() != null && !invProductoAux.getProCuentaGasto().equals("") ? invProductoAux.getProCuentaGasto() : null;
                            String cuentaVentaExterior = invProductoAux.getProCuentaVentaExterior() != null && !invProductoAux.getProCuentaVentaExterior().equals("") ? invProductoAux.getProCuentaVentaExterior() : null;

                            Map<String, Object> respuesCategorias = productoCategoriaService.validacionGrupoSegunCategoria(sisInfoTO.getEmpresa(), categoriaCodigo, cuentaInventario, cuentaVenta, cuentaCostoAutomatico, cuentaGasto, cuentaVentaExterior, empresaParametros.isIsExportadora(), isActivoFijo);

                            if (respuesCategorias != null) {
                                boolean cuentaCompraValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaCompraValido"));
                                boolean cuentaVentaValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaVentaValido"));
                                boolean cuentaCostoAutomaticoValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaCostoAutomaticoValido"));
                                boolean cuentaGastoValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaGastoValido"));
                                boolean cuentaVentaExteriorValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaVentaExteriorValido"));

                                String mensajeCuentas = UtilsJSON.jsonToObjeto(String.class, respuesCategorias.get("mensaje"));

                                if (mensajeCuentas != null) {
                                    if (mensajeCuentas.substring(0, 1).equals("T")) {
                                        if (cuentaCompraValido && cuentaVentaValido && cuentaCostoAutomaticoValido && cuentaGastoValido && cuentaVentaExteriorValido) {
                                            ////****************VALIDAR CUENTA COMPRA****************
                                            boolean inventarioValido = false;
                                            if (cuentaInventario == null || cuentaInventario.equals("") || invProductoTipo.getCtaCodigo() == null || invProductoTipo.getCtaCodigo().equals("")) {
                                                inventarioValido = true;
                                            } else {
                                                int tamanioGrupoTipoProducto = invProductoTipo.getCtaCodigo().length();
                                                String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                                if (grupoSeleccionado.equals(invProductoTipo.getCtaCodigo())) {
                                                    inventarioValido = true;
                                                }
                                            }
                                            //************************************************
                                            if (inventarioValido) {
                                                //CONVERTIR ACTIVO FIJO
                                                AfActivos afActivoEliminar = null;
                                                SisSuceso sisSucesoAF = null;
                                                if (afActivo != null) {
                                                    afActivo.setAfDescripcion(invProductoAux.getProNombre());
                                                    afActivo.setAfActivosPK(new AfActivosPK(sisInfoTO.getEmpresa(), invProductoAux.getInvProductoPK().getProCodigoPrincipal()));
                                                    afActivo.setUsrEmpresa(sisInfoTO.getEmpresa());
                                                    afActivo.setUsrCodigo(sisInfoTO.getUsuario());
                                                    afActivo.setUsrFechaInserta(UtilsDate.timestamp());
                                                    String susSuceso1 = "INSERT";
                                                    String susTabla1 = "activosfijos.af_activos";
                                                    sisSucesoAF = Suceso.crearSisSuceso(susTabla1, "", susSuceso1, "", sisInfoTO);
                                                } else {
                                                    afActivoEliminar = activoDao.obtenerPorId(AfActivos.class, new AfActivosPK(invProductoAux.getInvProductoPK().getProEmpresa(), invProductoAux.getInvProductoPK().getProCodigoPrincipal()));
                                                    if (afActivoEliminar != null) {
                                                        String susSuceso1 = "DELETE";
                                                        String susTabla1 = "activosfijos.af_activos";
                                                        sisSucesoAF = Suceso.crearSisSuceso(susTabla1, "", susSuceso1, "", sisInfoTO);
                                                    }
                                                }
                                                if (productoDao.modificarInvProductoImportacion(invProductoAux, afActivo, afActivoEliminar, sisSuceso, sisSucesoAF)) {
                                                    retorno = "T<html>Se ha modificado el siguiente producto:<br><br>Código: <font size = 5>"
                                                            + invProductoAux.getInvProductoPK().getProCodigoPrincipal().trim()
                                                            + "</font>.<br>Nombre: <font size = 5>"
                                                            + invProductoAux.getProNombre().trim() + "</font>.</html>";

                                                    //*********************VALIDAR COSTO AUTOMATICO*****************************
                                                    InvProductoTO invProductoTO = new InvProductoTO();
                                                    invProductoTO.setProEmpresa(invProducto.getInvProductoPK().getProEmpresa());
                                                    invProductoTO.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
                                                    invProductoTO.setProCuentaCostoAutomatico(invProducto.getProCuentaCostoAutomatico());
                                                    boolean seCambiCodigo = eliminarCostoAutomatico(invProductoTO, sisInfoTO);
                                                    if (seCambiCodigo && invProductoTO != null && invProductoTO.getProCuentaCostoAutomatico() != null && !invProductoTO.getProCuentaCostoAutomatico().equals("")) {
                                                        InvProductoPK pk = new InvProductoPK(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal());
                                                        if (invProductoTO.getProCuentaInventario() == null || invProductoTO.getProCuentaInventario().equals("")) {
                                                            Criterio filtro;
                                                            filtro = Criterio.forClass(InvProductoDetalleCuentaContable.class);
                                                            filtro.createAlias("invProducto", "producto");
                                                            filtro.add(Restrictions.eq("detCuenta", invProductoTO.getProCuentaCostoAutomatico()));
                                                            filtro.add(Restrictions.eq("producto.invProductoPK.proEmpresa", pk.getProEmpresa()));
                                                            List<InvProductoDetalleCuentaContable> listado = productoDetalleCuentaContableDao.buscarPorCriteriaSinProyecciones(filtro);
                                                            if (listado == null || listado.isEmpty()) {
                                                                InvProductoDetalleCuentaContable cuentaCostoAutom = new InvProductoDetalleCuentaContable();
                                                                cuentaCostoAutom.setDetCuenta(invProductoTO.getProCuentaCostoAutomatico());
                                                                cuentaCostoAutom.setInvProducto(new InvProducto(pk));
                                                                /// PREPARANDO OBJETO SISSUCESO
                                                                String susClave1 = cuentaCostoAutom.getDetCuenta();
                                                                String susDetalle1 = "Se insertó el productoDetalleCuentaContable " + cuentaCostoAutom.getDetCuenta() + " con código " + cuentaCostoAutom.getDetSecuencial();
                                                                String susSuceso1 = "UPDATE";
                                                                String susTabla1 = "inventario.inv_producto_detalle_cuenta_contable";
                                                                SisSuceso sisSuceso1 = Suceso.crearSisSuceso(susTabla1, susClave1, susSuceso1, susDetalle1, sisInfoTO);
                                                                sucesoDao.insertar(sisSuceso1);
                                                                productoDetalleCuentaContableDao.insertar(cuentaCostoAutom);
                                                            } else {
                                                                filtro.add(Restrictions.eq("producto.invProductoPK.proCodigoPrincipal", pk.getProCodigoPrincipal()));
                                                                List<InvProductoDetalleCuentaContable> listadoDeCuentasDelProducto = productoDetalleCuentaContableDao.buscarPorCriteriaSinProyecciones(filtro);
                                                                if (listadoDeCuentasDelProducto == null || listadoDeCuentasDelProducto.isEmpty()) {
                                                                    retorno += ", pero con el siguiente error:<br> la cuenta de costo automático ya se encuentra asociado a otro producto.";
                                                                }
                                                            }
                                                        } else {
                                                            retorno += ", pero con el siguiente error:<br> la cuenta inventario no debe estar presente si se desea agregar una cuenta de costo automático.";
                                                        }
                                                    }
                                                    //***********************************************************************************************
                                                } else {
                                                    retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                                }
                                            } else {
                                                retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                                            }

                                        } else {
                                            retorno = "FLa validación de las cuentas es incorrecta. Verifique";
                                        }
                                    } else {
                                        retorno = mensajeCuentas.substring(0);
                                    }
                                } else {
                                    retorno = "FEl producto " + invProducto.getInvProductoTipo().getInvProductoTipoPK().getTipCodigo() + " que escogió tuvo errores al validar las cuentas...\nIntente de nuevo o contacte con el administrador";
                                }
                            } else {
                                retorno = "FEl producto " + invProducto.getInvProductoTipo().getInvProductoTipoPK().getTipCodigo() + " que escogió tiene cuentas incorrectas...\nIntente de nuevo o contacte con el administrador";
                            }
                            //retorno = "FEl código del producto que va a ingresar ya existe...\nIntente con otro...";
                        }
                    } else {
                        retorno = "FEl tipo de producto " + invProducto.getInvProductoTipo().getInvProductoTipoPK().getTipCodigo() + " que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa categoría " + invProducto.getInvProductoCategoria().getInvProductoCategoriaPK().getCatCodigo() + " que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            } else {
                retorno = "FLa marca de producto " + invProducto.getInvProductoMarca().getInvProductoMarcaPK().getMarCodigo() + " que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FLa unidad de medida " + invProducto.getInvProductoMedida().getInvProductoMedidaPK().getMedCodigo() + " que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
        }
        //***************************************************************************************************************************************************
        respuesta.put("mensaje", retorno);
        respuesta.put("invProducto", invProducto);

        return respuesta;
    }

    @Override
    public String insertarInvProductoTO(InvProductoTO invProductoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "F";
        String codigoGenerado = "";
        InvProducto invProducto = null;
        if (invProductoTO.getProCodigoPrincipal().trim().isEmpty()) {
            invProductoTO.setProCodigoPrincipal(
                    productoDao.getInvProximaNumeracionProducto(invProductoTO.getProEmpresa(), invProductoTO));
            codigoGenerado = invProductoTO.getProCodigoPrincipal();
        }

        if (productoDao.buscarInvProducto(invProductoTO.getProEmpresa(),
                invProductoTO.getProCodigoPrincipal()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invProductoTO.getProCodigoPrincipal();
            susDetalle = "Se insertó el producto " + invProductoTO.getProNombre() + " con código "
                    + invProductoTO.getProCodigoPrincipal();
            susSuceso = "INSERT";
            susTabla = "inventario.inv_producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invProductoTO.setUsrFechaInsertaProducto(UtilsValidacion.fechaSistema());
            invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);

            ///// BUSCAR POR ALTERNO
            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", null,
                    (invProductoTO.getProCodigoAlterno().trim().isEmpty() ? null
                    : "'" + invProductoTO.getProCodigoAlterno() + "'"),
                    null, null, null, null, null, null)) {
                retorno = "FEl Código Alterno del producto ya existe en los registros.\nIntente ingresando otro Código...";
            } else ///// BUSCAR POR BARRAS
            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", null, null,
                    "'" + invProductoTO.getProCodigoBarra() + "'",
                    "'" + invProductoTO.getProCodigoBarra2() + "'",
                    "'" + invProductoTO.getProCodigoBarra3() + "'",
                    "'" + invProductoTO.getProCodigoBarra4() + "'",
                    "'" + invProductoTO.getProCodigoBarra5() + "'", null)) {
                retorno = "FEl Código de Barras que ingresó ya existe en los registros.\nIntente ingresando otro Código...";
            } else //// BUSCAR POR NOMBRE
            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", null, null, null,
                    null, null, null, null, (invProductoTO.getProNombre().trim().isEmpty() ? null
                    : "'" + invProductoTO.getProNombre() + "'"))) {
                retorno = "FEl Nombre del producto ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else {
                retorno = "T";
            }

            if (retorno.charAt(0) == 'T') {
                InvProductoMedida invProductoMedida = productoMedidaDao
                        .buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());

                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao
                        .buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(),
                                invProductoTO.getPresUCodigo());
                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao
                        .buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(),
                                invProductoTO.getPresCCodigo());
                InvProductoMarca invProductoMarca = productoMarcaDao
                        .buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());

                if (invProductoMedida != null) {
                    invProducto.setInvProductoMedida(invProductoMedida);
                    invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                    invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                    invProducto.setInvProductoMarca(invProductoMarca);

                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(
                            invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                    if (invProductoCategoria != null) {
                        invProducto.setInvProductoCategoria(invProductoCategoria);
                        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                        if (invProductoTipo != null) {
                            invProducto.setInvProductoTipo(invProductoTipo);
                            InvNumeracionVarios invNumeracionVariosAux = numeracionVariosDao
                                    .obtenerPorId(InvNumeracionVarios.class, invProductoTO.getProEmpresa());
                            InvNumeracionVarios invNumeracionVarios = ConversionesInventario
                                    .convertirInvNumeracionVarios_InvNumeracionVarios(invNumeracionVariosAux);

                            if (invNumeracionVarios == null && codigoGenerado.trim().isEmpty()) {
                                invNumeracionVarios = null;
                            } else if (invNumeracionVarios == null && !codigoGenerado.trim().isEmpty()) {
                                invNumeracionVarios = new InvNumeracionVarios(invProductoTO.getProEmpresa(),
                                        "00000", "00000", codigoGenerado, invProductoTO.getProEmpresa());
                            } else if (invNumeracionVarios != null && codigoGenerado.trim().isEmpty()) {
                                invNumeracionVarios = null;
                            } else if (invNumeracionVarios != null && !codigoGenerado.trim().isEmpty()) {
                                invNumeracionVarios.setNumProductos(codigoGenerado);
                            }
                            if (!productoDao.buscarExisteNombreProducto(invProductoTO.getProEmpresa(), invProductoTO.getProNombre().trim())) {
                                ////****************VALIDAR CUENTA COMPRA****************
                                boolean inventarioValido = false;
                                String cuentaInventario = invProducto.getProCuentaInventario();
                                if (cuentaInventario == null || cuentaInventario.equals("") || invProductoTipo.getCtaCodigo() == null || invProductoTipo.getCtaCodigo().equals("")) {
                                    inventarioValido = true;
                                } else {
                                    int tamanioGrupoTipoProducto = invProductoTipo.getCtaCodigo().length();
                                    String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                    if (grupoSeleccionado.equals(invProductoTipo.getCtaCodigo())) {
                                        inventarioValido = true;
                                    }
                                }
                                //************************************************
                                if (inventarioValido) {
                                    if (productoDao.insertarInvProducto(invProducto, sisSuceso, invNumeracionVarios)) {
                                        retorno = "T<html>Se ha guardado el siguiente producto:<br><br>Código: <font size = 5>"
                                                + invProducto.getInvProductoPK().getProCodigoPrincipal().trim()
                                                + "</font>.<br>Nombre: <font size = 5>"
                                                + invProducto.getProNombre().trim() + "</font>.</html>";
                                    } else {
                                        retorno = "FHubo un error al guardar el producto...\nIntente de nuevo o contacte con el administrador.";
                                    }
                                } else {
                                    retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                                }
                            } else {
                                retorno = "FEl nombre del producto ya existe.\nIntente con otro.";
                            }
                        } else {
                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else {
                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                    }
                } else if (invProductoMedida == null) {
                    retorno = "FLa Medida que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador.";
                }
            }
        } else {
            retorno = "FEl código del producto que va a ingresar ya existe...\nIntente con otro...";
        }
        if (retorno.charAt(0) == 'T' && invProducto != null) {
            invProductoTO.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
            verificarCuentaCostoAutomatica(invProductoTO, sisInfoTO);
        }
        return retorno;
    }

    @Override
    public Map<String, Object> insertarInvProductoTO(InvProductoTO invProductoTO, AfActivoTO afActivoTO, List<InvAdjuntosProductosWebTO> listadoImagenes, List<InvProductoRelacionadoTO> listaInvProductoRelacionados, List<InvProductoFormulaTO> listaInvProductoFormulaTO, SisInfoTO sisInfoTO) throws Exception {
        boolean isActivoFijo = afActivoTO != null;
        String retorno = "F";
        String codigoGenerado = "";
        InvProducto invProducto = null;
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "T");
        String categoriaCodigo = invProductoTO.getCatCodigo() != null && !invProductoTO.getCatCodigo().equals("") ? invProductoTO.getCatCodigo() : null;
        String cuentaInventario = invProductoTO.getProCuentaInventario() != null && !invProductoTO.getProCuentaInventario().equals("") ? invProductoTO.getProCuentaInventario() : null;
        String cuentaVenta = invProductoTO.getProCuentaVenta() != null && !invProductoTO.getProCuentaVenta().equals("") ? invProductoTO.getProCuentaVenta() : null;
        String cuentaCostoAutomatico = invProductoTO.getProCuentaCostoAutomatico() != null && !invProductoTO.getProCuentaCostoAutomatico().equals("") ? invProductoTO.getProCuentaCostoAutomatico() : null;
        String cuentaGasto = invProductoTO.getProCuentaGasto() != null && !invProductoTO.getProCuentaGasto().equals("") ? invProductoTO.getProCuentaGasto() : null;
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, invProductoTO.getProEmpresa());
        String cuentaVentaExterior = invProductoTO.getProCuentaVentaExterior() != null && !invProductoTO.getProCuentaVentaExterior().equals("") ? invProductoTO.getProCuentaVentaExterior() : null;
        Map<String, Object> respuesCategorias = productoCategoriaService.validacionGrupoSegunCategoria(invProductoTO.getCatEmpresa(), categoriaCodigo, cuentaInventario, cuentaVenta, cuentaCostoAutomatico, cuentaGasto, cuentaVentaExterior, empresaParametros.isIsExportadora(), isActivoFijo);
        if (respuesCategorias != null) {
            boolean cuentaCompraValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaCompraValido"));
            boolean cuentaVentaValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaVentaValido"));
            boolean cuentaCostoAutomaticoValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaCostoAutomaticoValido"));
            boolean cuentaGastoValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaGastoValido"));
            boolean cuentaVentaExteriorValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaVentaExteriorValido"));
            String mensajeCuentas = UtilsJSON.jsonToObjeto(String.class, respuesCategorias.get("mensaje"));

            if (mensajeCuentas != null) {
                if (mensajeCuentas.substring(0, 1).equals("T")) {
                    if (cuentaCompraValido && cuentaVentaValido && cuentaCostoAutomaticoValido && cuentaGastoValido && cuentaVentaExteriorValido) {
                        //INSERTANDO PRODUCTO
                        if (invProductoTO.getProCodigoPrincipal().trim().isEmpty()) {
                            invProductoTO.setProCodigoPrincipal(productoDao.getInvProximaNumeracionProducto(invProductoTO.getProEmpresa(), invProductoTO));
                            codigoGenerado = invProductoTO.getProCodigoPrincipal();
                        }
                        if (productoDao.buscarInvProducto(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal()) == null) {
                            //PREPARANDO SUCESO
                            susClave = invProductoTO.getProCodigoPrincipal();
                            susDetalle = "Se insertó el producto " + invProductoTO.getProNombre() + " con código " + invProductoTO.getProCodigoPrincipal();
                            susSuceso = "INSERT";
                            susTabla = "inventario.inv_producto";
                            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                            //PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                            invProductoTO.setUsrFechaInsertaProducto(UtilsValidacion.fechaSistema());
                            invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);
                            //BUSCAR POR ALTERNO
                            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", null, (invProductoTO.getProCodigoAlterno().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoAlterno() + "'"), null, null, null, null, null, null)) {
                                retorno = "FEl Código Alterno del producto ya existe en los registros.\nIntente ingresando otro Código...";
                            } else //BUSCAR POR BARRAS
                            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", null, null,
                                    "'" + invProductoTO.getProCodigoBarra() + "'",
                                    "'" + invProductoTO.getProCodigoBarra2() + "'",
                                    "'" + invProductoTO.getProCodigoBarra3() + "'",
                                    "'" + invProductoTO.getProCodigoBarra4() + "'",
                                    "'" + invProductoTO.getProCodigoBarra5() + "'", null)) {
                                retorno = "FEl Código de Barras que ingresó ya existe en los registros.\nIntente ingresando otro Código...";
                            } else //BUSCAR POR NOMBRE
                            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", null, null, null, null, null, null, null, (invProductoTO.getProNombre().trim().isEmpty() ? null : "'" + invProductoTO.getProNombre() + "'"))) {
                                retorno = "FEl Nombre del producto ya existe en los registros.\nIntente ingresando otro Nombre...";
                            } else {
                                retorno = "T";
                            }
                            if (retorno.charAt(0) == 'T') {
                                InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());
                                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao.buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(), invProductoTO.getPresUCodigo());
                                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao.buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(), invProductoTO.getPresCCodigo());
                                InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());

                                if (invProductoMedida != null) {
                                    invProducto.setInvProductoMedida(invProductoMedida);
                                    invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                                    invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                                    invProducto.setInvProductoMarca(invProductoMarca);
                                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                                    if (invProductoCategoria != null) {
                                        invProducto.setInvProductoCategoria(invProductoCategoria);
                                        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                                        if (invProductoTipo != null) {
                                            invProducto.setInvProductoTipo(invProductoTipo);
                                            InvNumeracionVarios invNumeracionVariosAux = numeracionVariosDao.obtenerPorId(InvNumeracionVarios.class, invProductoTO.getProEmpresa());
                                            InvNumeracionVarios invNumeracionVarios = ConversionesInventario.convertirInvNumeracionVarios_InvNumeracionVarios(invNumeracionVariosAux);

                                            if (invNumeracionVarios == null && codigoGenerado.trim().isEmpty()) {
                                                invNumeracionVarios = null;
                                            } else if (invNumeracionVarios == null && !codigoGenerado.trim().isEmpty()) {
                                                invNumeracionVarios = new InvNumeracionVarios(invProductoTO.getProEmpresa(), "00000", "00000", codigoGenerado, invProductoTO.getProEmpresa());
                                            } else if (invNumeracionVarios != null && codigoGenerado.trim().isEmpty()) {
                                                invNumeracionVarios = null;
                                            } else if (invNumeracionVarios != null && !codigoGenerado.trim().isEmpty()) {
                                                invNumeracionVarios.setNumProductos(codigoGenerado);
                                            }
                                            if (!productoDao.buscarExisteNombreProducto(invProductoTO.getProEmpresa(), invProductoTO.getProNombre().trim())) {
                                                ////****************VALIDAR CUENTA COMPRA****************
                                                boolean inventarioValido = false;
                                                if (cuentaInventario == null || cuentaInventario.equals("") || invProductoTipo.getCtaCodigo() == null || invProductoTipo.getCtaCodigo().equals("")) {
                                                    inventarioValido = true;
                                                } else {
                                                    int tamanioGrupoTipoProducto = invProductoTipo.getCtaCodigo().length();
                                                    String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                                    if (grupoSeleccionado.equals(invProductoTipo.getCtaCodigo())) {
                                                        inventarioValido = true;
                                                    }
                                                }
                                                //************************************************
                                                //CONVERTIR IMAGENES
                                                List<InvProductoDatosAdjuntos> listadoImagenesEnviar = new ArrayList<>();
                                                if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
                                                    String bucket = sistemaWebServicio.obtenerRutaImagen(invProductoTO.getProEmpresa());
                                                    Bucket b = AmazonS3Crud.crearBucket(bucket);
                                                    if (b != null) {
                                                        for (InvAdjuntosProductosWebTO invAdjuntosProductosWebTO : listadoImagenes) {
                                                            if (invAdjuntosProductosWebTO.getAdjSecuencial() == null) {
                                                                InvProductoDatosAdjuntos invProductoDatosAdjuntos = new InvProductoDatosAdjuntos();
                                                                String nombre = UtilsString.generarNombreAmazonS3() + ".jpg";
                                                                String carpeta = "productos/" + invProductoTO.getProCodigoPrincipal() + "/";
                                                                invProductoDatosAdjuntos.setAdjBucket(bucket);
                                                                invProductoDatosAdjuntos.setAdjClaveArchivo(carpeta + nombre);
                                                                invProductoDatosAdjuntos.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                                                                invProductoDatosAdjuntos.setAdjArchivo(invAdjuntosProductosWebTO.getImagenString().getBytes("UTF-8"));
                                                                listadoImagenesEnviar.add(invProductoDatosAdjuntos);
                                                            }
                                                        }
                                                    } else {
                                                        throw new GeneralException("Error al crear contenedor de imágenes.");
                                                    }
                                                }
                                                //CONVERTIR ACTIVO FIJO
                                                AfActivos afActivo = null;
                                                if (afActivoTO != null) {
                                                    afActivoTO.setAfDescripcion(invProductoTO.getProNombre());
                                                    afActivo = new AfActivos();
                                                    afActivo.convertirObjeto(afActivoTO);
                                                }
                                                //CONVERTIR PRODUCTOS RELACIONADOS
                                                List<InvProductoRelacionados> listaInvProductoRelacionadosEnviar = new ArrayList<>();
                                                for (InvProductoRelacionadoTO item : listaInvProductoRelacionados) {
                                                    InvProductoRelacionados producto = new InvProductoRelacionados();
                                                    producto.setPrSecuencial(item.getProSecuencial());

                                                    //RELACIONADO
                                                    InvProducto invProductoRelacionado = new InvProducto();
                                                    InvProductoPK pkRelacionado = new InvProductoPK();
                                                    pkRelacionado.setProCodigoPrincipal(item.getProRelacionadoCodigoPrincipal());
                                                    pkRelacionado.setProEmpresa(item.getProRelacionadoEmpresa());
                                                    invProductoRelacionado.setInvProductoPK(pkRelacionado);
                                                    producto.setInvProductoRelacionado(invProductoRelacionado);
                                                    listaInvProductoRelacionadosEnviar.add(producto);
                                                }
                                                //CONVERTIR PRODUCTOS FORMULA
                                                List<InvProductoFormula> listaInvProductoFormulaEnviar = new ArrayList<>();
                                                for (InvProductoFormulaTO item : listaInvProductoFormulaTO) {
                                                    InvProductoFormula producto = new InvProductoFormula();
                                                    producto.setPrSecuencial(item.getProSecuencial());
                                                    producto.setInvProducto(invProducto);
                                                    //FORMULA
                                                    InvProducto invProductoFormula = new InvProducto();
                                                    InvProductoPK pkFormula = new InvProductoPK();
                                                    pkFormula.setProCodigoPrincipal(item.getProFormulaCodigoPrincipal());
                                                    pkFormula.setProEmpresa(item.getProFormulaEmpresa());
                                                    invProductoFormula.setInvProductoPK(pkFormula);
                                                    producto.setInvProductoFormula(invProductoFormula);
                                                    producto.setPrCantidad(item.getProCantidad());
                                                    listaInvProductoFormulaEnviar.add(producto);
                                                }
                                                if (inventarioValido) {
                                                    if (productoDao.insertarInvProducto(invProducto, afActivo, listadoImagenesEnviar, listaInvProductoRelacionadosEnviar, listaInvProductoFormulaEnviar, sisSuceso, invNumeracionVarios)) {
                                                        retorno = "T<html>Se ha guardado el siguiente producto:<br><br>Código: <font size = 5>"
                                                                + invProducto.getInvProductoPK().getProCodigoPrincipal().trim()
                                                                + "</font>.<br>Nombre: <font size = 5>"
                                                                + invProducto.getProNombre().trim() + "</font>.</html>";
                                                        respuesta.put("invProducto", invProducto);
                                                    } else {
                                                        retorno = "FHubo un error al guardar el producto...\nIntente de nuevo o contacte con el administrador.";
                                                    }
                                                } else {
                                                    retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                                                }
                                            } else {
                                                retorno = "FEl nombre del producto ya existe.\nIntente con otro.";
                                            }
                                        } else {
                                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                        }
                                    } else {
                                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                    }
                                } else if (invProductoMedida == null) {
                                    retorno = "FLa Medida que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador.";
                                }
                            }
                        } else {
                            retorno = "FEl código del producto que va a ingresar ya existe...\nIntente con otro...";
                        }
                        if (retorno.charAt(0) == 'T' && invProducto != null) {
                            invProductoTO.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
                            verificarCuentaCostoAutomatica(invProductoTO, sisInfoTO);
                        }
                        respuesta.put("mensaje", retorno);
                    } else {
                        respuesta.put("mensaje", "FCuenta invalidas");
                        respuesta.put("cuentaCompraValido", respuesCategorias.get("cuentaCompraValido"));
                        respuesta.put("cuentaVentaValido", respuesCategorias.get("cuentaVentaValido"));
                        respuesta.put("cuentaCostoAutomaticoValido", respuesCategorias.get("cuentaCostoAutomaticoValido"));
                        respuesta.put("cuentaGastoValido", respuesCategorias.get("cuentaGastoValido"));
                        respuesta.put("cuentaVentaExteriorValido", respuesCategorias.get("cuentaVentaExteriorValido"));
                    }
                } else {
                    respuesta.put("mensaje", mensajeCuentas.substring(0));
                }
            } else {
                respuesta.put("mensaje", "FHubo un error con la validación de las cuentas. Comuniquese con el administrador");
            }
        } else {
            respuesta.put("mensaje", "FLa validación de las cuentas es incorrecta. verifique");
        }
        return respuesta;
    }

    @Override
    public boolean productoRepetidoCodigoBarra(String empresa, String barras) throws Exception {
        return productoDao.getProductoRepetido("'" + empresa + "'", null, null, "'" + barras + "'", null, null, null,
                null, null);
    }

    @Override
    public String modificarInvProductoTO(InvProductoTO invProductoTO, String codigoCambiarLlave, SisInfoTO sisInfoTO) throws Exception {

        String retorno = "NULO";
        InvProducto invProductoAux = null;

        if (codigoCambiarLlave.trim().isEmpty()) {
            invProductoAux = productoDao.buscarInvProducto(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal().trim());
        } else {
            invProductoAux = productoDao.buscarInvProducto(invProductoTO.getProEmpresa(), codigoCambiarLlave);
        }

        if (codigoCambiarLlave.trim().isEmpty()) {
            ///// BUSCAR POR ALTERNO
            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", null,
                    (invProductoTO.getProCodigoAlterno().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoAlterno() + "'"), null, null, null, null, null, null)) {
                retorno = "FEl Código Alterno del producto ya existe en los registros.\nIntente ingresando otro Código...";
            } else ///// BUSCAR POR BARRAS
            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'",
                    (invProductoTO.getProCodigoPrincipal().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoPrincipal() + "'"),
                    null, "'" + invProductoTO.getProCodigoBarra() + "'",
                    "'" + invProductoTO.getProCodigoBarra2() + "'",
                    "'" + invProductoTO.getProCodigoBarra3() + "'",
                    "'" + invProductoTO.getProCodigoBarra4() + "'",
                    "'" + invProductoTO.getProCodigoBarra5() + "'", null)) {
                retorno = "FEl Código de Barras que ingresó ya existe en los registros.\nIntente ingresando otro Código...";
            } else //// BUSCAR POR NOMBRE
            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'",
                    (invProductoTO.getProCodigoPrincipal().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoPrincipal() + "'"),
                    null, null, null, null, null, null, (invProductoTO.getProNombre().trim().isEmpty() ? null : "'" + invProductoTO.getProNombre() + "'"))) {
                retorno = "FEl Nombre del producto ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else {
                retorno = "T";
            }
        } else ///// BUSCAR POR ALTERNO
        if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'",
                (codigoCambiarLlave.trim().isEmpty() ? null : "'" + codigoCambiarLlave + "'"),
                (invProductoTO.getProCodigoAlterno().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoAlterno() + "'"), null, null, null, null, null, null)) {
            retorno = "FEl Código Alterno del producto ya existe en los registros.\nIntente ingresando otro Código...";
        } else ///// BUSCAR POR BARRAS
        if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'",
                (codigoCambiarLlave.trim().isEmpty() ? null : "'" + codigoCambiarLlave + "'"), null,
                "'" + invProductoTO.getProCodigoBarra() + "'",
                "'" + invProductoTO.getProCodigoBarra2() + "'",
                "'" + invProductoTO.getProCodigoBarra3() + "'",
                "'" + invProductoTO.getProCodigoBarra4() + "'",
                "'" + invProductoTO.getProCodigoBarra5() + "'", null)) {
            retorno = "FEl Código de Barras que ingresó ya existe en los registros.\nIntente ingresando otro Código...";
        } else //// BUSCAR POR NOMBRE
        if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'",
                (codigoCambiarLlave.trim().isEmpty() ? null : "'" + codigoCambiarLlave + "'"), null,
                null, null, null, null, null, (invProductoTO.getProNombre().trim().isEmpty() ? null : "'" + invProductoTO.getProNombre() + "'"))) {
            retorno = "FEl Nombre del producto ya existe en los registros.\nIntente ingresando otro Nombre...";
        } else {
            retorno = "T";
        }

        if (retorno.charAt(0) == 'T') {
            if (invProductoAux != null && codigoCambiarLlave.trim().isEmpty()) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invProductoTO.getProCodigoPrincipal();
                susDetalle = "Se modificó el producto " + invProductoTO.getProNombre() + " con código " + invProductoTO.getProCodigoPrincipal();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_producto";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invProductoTO.setUsrInsertaProducto(invProductoAux.getUsrCodigo());
                invProductoTO.setUsrFechaInsertaProducto(UtilsValidacion.fecha(invProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvProducto invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);
                /// SI CONTIENE LA PALABRA "BALANCEADO" EN EL NOMBRE DEL PRODUCTO
                InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());
                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao.buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(), invProductoTO.getPresUCodigo());
                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao.buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(), invProductoTO.getPresCCodigo());
                InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());

                if (invProductoMedida != null) {
                    invProducto.setInvProductoMedida(invProductoMedida);
                    invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                    invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                    invProducto.setInvProductoMarca(invProductoMarca);
                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                    if (invProductoCategoria != null) {
                        invProducto.setInvProductoCategoria(invProductoCategoria);
                        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                        if (invProductoTipo != null) {
                            invProducto.setInvProductoTipo(invProductoTipo);
                            ////****************VALIDAR CUENTA COMPRA****************
                            boolean inventarioValido = false;
                            String cuentaInventario = invProducto.getProCuentaInventario();
                            if (cuentaInventario == null || cuentaInventario.equals("") || invProductoTipo.getCtaCodigo() == null || invProductoTipo.getCtaCodigo().equals("")) {
                                inventarioValido = true;
                            } else {
                                int tamanioGrupoTipoProducto = invProductoTipo.getCtaCodigo().length();
                                String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                if (grupoSeleccionado.equals(invProductoTipo.getCtaCodigo())) {
                                    inventarioValido = true;
                                }
                            }
                            //************************************************
                            if (inventarioValido) {
                                if (productoDao.modificarInvProducto(invProducto, sisSuceso)) {
                                    retorno = "TEl producto se modificó correctamente...";
                                } else {
                                    retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                }
                            } else {
                                retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                            }
                        } else {
                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else {
                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Medida que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            } else if (invProductoAux != null && !codigoCambiarLlave.trim().isEmpty()) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invProductoTO.getProCodigoPrincipal();
                susDetalle = "Se modificó el código " + codigoCambiarLlave + " del producto " + invProductoTO.getProNombre() + " por el código " + invProductoTO.getProCodigoPrincipal();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_producto";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invProductoTO.setUsrInsertaProducto(invProductoAux.getUsrCodigo());
                invProductoTO.setUsrFechaInsertaProducto(UtilsValidacion.fecha(invProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvProducto invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);
                /// SI CONTIENE LA PALABRA "BALANCEADO" EN EL NOMBRE DEL PRODUCTO
                InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());
                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao.buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(), invProductoTO.getPresUCodigo());
                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao.buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(), invProductoTO.getPresCCodigo());
                InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());

                if (invProductoMedida != null) {
                    invProducto.setInvProductoMedida(invProductoMedida);
                    invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                    invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                    invProducto.setInvProductoMarca(invProductoMarca);
                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                    if (invProductoCategoria != null) {
                        invProducto.setInvProductoCategoria(invProductoCategoria);
                        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                        if (invProductoTipo != null) {
                            invProducto.setInvProductoTipo(invProductoTipo);
                            if (invProductoAux.getInvProductoPK().getProCodigoPrincipal().equals(invProducto.getInvProductoPK().getProCodigoPrincipal())) {

                                ////****************VALIDAR CUENTA COMPRA****************
                                boolean inventarioValido = false;
                                String cuentaInventario = invProducto.getProCuentaInventario();
                                if (cuentaInventario == null || cuentaInventario.equals("") || invProductoTipo.getCtaCodigo() == null || invProductoTipo.getCtaCodigo().equals("")) {
                                    inventarioValido = true;
                                } else {
                                    int tamanioGrupoTipoProducto = invProductoTipo.getCtaCodigo().length();
                                    String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                    if (grupoSeleccionado.equals(invProductoTipo.getCtaCodigo())) {
                                        inventarioValido = true;
                                    }
                                }
                                //************************************************
                                if (inventarioValido) {
                                    if (productoDao.modificarInvProducto(invProducto, sisSuceso)) {
                                        retorno = "TEl producto se modificó correctamente...";
                                    } else {
                                        retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                    }
                                } else {
                                    retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                                }
                            } else if (productoDao.modificarInvProductoLlavePrincipal(invProductoAux, invProducto, sisSuceso)) {
                                retorno = "TEl producto se modificó correctamente...";
                            } else {
                                retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                            }
                        } else {
                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else {
                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Medida que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            } else if (invProductoAux == null && codigoCambiarLlave.trim().isEmpty()) {
                retorno = "FEl código del producto que va a modificar ya no está disponible...\nIntente con otro...";
            } else {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invProductoTO.getProCodigoPrincipal();
                susDetalle = "Se modificó el código " + codigoCambiarLlave + " del producto " + invProductoTO.getProNombre() + " por el código " + invProductoTO.getProCodigoPrincipal();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_producto";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invProductoTO.setUsrInsertaProducto(invProductoAux.getUsrCodigo());
                invProductoTO.setUsrFechaInsertaProducto(UtilsValidacion.fecha(invProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvProducto invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);
                /// SI CONTIENE LA PALABRA "BALANCEADO" EN EL NOMBRE DEL PRODUCTO
                InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());
                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao.buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(), invProductoTO.getPresUCodigo());
                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao.buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(), invProductoTO.getPresCCodigo());
                InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());

                if (invProductoMedida != null) {
                    invProducto.setInvProductoMedida(invProductoMedida);
                    invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                    invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                    invProducto.setInvProductoMarca(invProductoMarca);
                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                    if (invProductoCategoria != null) {
                        invProducto.setInvProductoCategoria(invProductoCategoria);
                        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                        if (invProductoTipo != null) {
                            invProducto.setInvProductoTipo(invProductoTipo);
                            if (invProductoAux.getInvProductoPK().getProCodigoPrincipal().equals(invProducto.getInvProductoPK().getProCodigoPrincipal())) {
                                if (productoDao.modificarInvProducto(invProducto, sisSuceso)) {
                                    retorno = "TEl producto se modificó correctamente...";
                                } else {
                                    retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                }
                            } else if (productoDao.modificarInvProductoLlavePrincipal(invProductoAux, invProducto, sisSuceso)) {
                                retorno = "TEl producto se modificó correctamente...";
                            } else {
                                retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                            }
                        } else {
                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else {
                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                    }
                } else {
                    retorno = "FLa Medida que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            }
        }
        if (retorno.charAt(0) == 'T') {
            verificarCuentaCostoAutomatica(invProductoTO, sisInfoTO);
        }
        return retorno;
    }

    @Override
    public Map<String, Object> modificarInvProductoTO(InvProductoTO invProductoTO, String codigoCambiarLlave, AfActivoTO afActivoTO, List<InvAdjuntosProductosWebTO> listadoImagenes, List<InvAdjuntosProductosWebTO> listadoImagenesEliminar, List<InvProductoRelacionadoTO> listaInvProductoRelacionados, List<InvProductoRelacionadoTO> listaInvProductoRelacionadosEliminar, List<InvProductoFormulaTO> listaInvProductoFormulaTO, List<InvProductoFormulaTO> listaInvProductoFormulaTOEliminar, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "T");
        String retorno = "NULO";
        InvProducto invProductoAux = null;
        InvProducto invProducto = null;
        boolean isActivoFijo = afActivoTO != null;
        String categoriaCodigo = invProductoTO.getCatCodigo() != null && !invProductoTO.getCatCodigo().equals("") ? invProductoTO.getCatCodigo() : null;
        String cuentaInventario = invProductoTO.getProCuentaInventario() != null && !invProductoTO.getProCuentaInventario().equals("") ? invProductoTO.getProCuentaInventario() : null;
        String cuentaVenta = invProductoTO.getProCuentaVenta() != null && !invProductoTO.getProCuentaVenta().equals("") ? invProductoTO.getProCuentaVenta() : null;
        String cuentaCostoAutomatico = invProductoTO.getProCuentaCostoAutomatico() != null && !invProductoTO.getProCuentaCostoAutomatico().equals("") ? invProductoTO.getProCuentaCostoAutomatico() : null;
        String cuentaGasto = invProductoTO.getProCuentaGasto() != null && !invProductoTO.getProCuentaGasto().equals("") ? invProductoTO.getProCuentaGasto() : null;
        String cuentaVentaExterior = invProductoTO.getProCuentaVentaExterior() != null && !invProductoTO.getProCuentaVentaExterior().equals("") ? invProductoTO.getProCuentaVentaExterior() : null;

        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, invProductoTO.getProEmpresa());

        Map<String, Object> respuesCategorias = productoCategoriaService.validacionGrupoSegunCategoria(invProductoTO.getCatEmpresa(), categoriaCodigo, cuentaInventario, cuentaVenta, cuentaCostoAutomatico, cuentaGasto, cuentaVentaExterior, empresaParametros.isIsExportadora(), isActivoFijo);

        if (respuesCategorias != null) {
            boolean cuentaCompraValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaCompraValido"));
            boolean cuentaVentaValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaVentaValido"));
            boolean cuentaCostoAutomaticoValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaCostoAutomaticoValido"));
            boolean cuentaGastoValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaGastoValido"));
            boolean cuentaVentaExteriorValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaVentaExteriorValido"));
            String mensajeCuentas = UtilsJSON.jsonToObjeto(String.class, respuesCategorias.get("mensaje"));

            if (mensajeCuentas != null) {
                if (mensajeCuentas.substring(0, 1).equals("T")) {
                    if (cuentaCompraValido && cuentaVentaValido && cuentaCostoAutomaticoValido && cuentaGastoValido && cuentaVentaExteriorValido) {
                        if (codigoCambiarLlave.trim().isEmpty()) {
                            invProductoAux = productoDao.buscarInvProducto(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal().trim());
                        } else {
                            invProductoAux = productoDao.buscarInvProducto(invProductoTO.getProEmpresa(), codigoCambiarLlave);
                        }
                        if (codigoCambiarLlave.trim().isEmpty()) {
                            //BUSCAR POR ALTERNO
                            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", null, (invProductoTO.getProCodigoAlterno().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoAlterno() + "'"), null, null, null, null, null, null)) {
                                retorno = "FEl Código Alterno del producto ya existe en los registros.\nIntente ingresando otro Código...";
                            } else //BUSCAR POR BARRAS
                            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", (invProductoTO.getProCodigoPrincipal().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoPrincipal() + "'"), null,
                                    "'" + invProductoTO.getProCodigoBarra() + "'",
                                    "'" + invProductoTO.getProCodigoBarra2() + "'",
                                    "'" + invProductoTO.getProCodigoBarra3() + "'",
                                    "'" + invProductoTO.getProCodigoBarra4() + "'",
                                    "'" + invProductoTO.getProCodigoBarra5() + "'", null)) {
                                retorno = "FEl Código de Barras que ingresó ya existe en los registros.\nIntente ingresando otro Código...";
                            } else //BUSCAR POR NOMBRE
                            if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", (invProductoTO.getProCodigoPrincipal().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoPrincipal() + "'"), null, null, null, null, null, null, (invProductoTO.getProNombre().trim().isEmpty() ? null : "'" + invProductoTO.getProNombre() + "'"))) {
                                retorno = "FEl Nombre del producto ya existe en los registros.\nIntente ingresando otro Nombre...";
                            } else {
                                retorno = "T";
                            }
                        } else //BUSCAR POR ALTERNO
                        if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", (codigoCambiarLlave.trim().isEmpty() ? null : "'" + codigoCambiarLlave + "'"), (invProductoTO.getProCodigoAlterno().trim().isEmpty() ? null : "'" + invProductoTO.getProCodigoAlterno() + "'"), null, null, null, null, null, null)) {
                            retorno = "FEl Código Alterno del producto ya existe en los registros.\nIntente ingresando otro Código...";
                        } else //BUSCAR POR BARRAS
                        if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", (codigoCambiarLlave.trim().isEmpty() ? null : "'" + codigoCambiarLlave + "'"), null,
                                "'" + invProductoTO.getProCodigoBarra() + "'",
                                "'" + invProductoTO.getProCodigoBarra2() + "'",
                                "'" + invProductoTO.getProCodigoBarra3() + "'",
                                "'" + invProductoTO.getProCodigoBarra4() + "'",
                                "'" + invProductoTO.getProCodigoBarra5() + "'", null)) {
                            retorno = "FEl Código de Barras que ingresó ya existe en los registros.\nIntente ingresando otro Código...";
                        } else //BUSCAR POR NOMBRE
                        if (productoDao.getProductoRepetido("'" + invProductoTO.getProEmpresa() + "'", (codigoCambiarLlave.trim().isEmpty() ? null : "'" + codigoCambiarLlave + "'"), null, null, null, null, null, null, (invProductoTO.getProNombre().trim().isEmpty() ? null : "'" + invProductoTO.getProNombre() + "'"))) {
                            retorno = "FEl Nombre del producto ya existe en los registros.\nIntente ingresando otro Nombre...";
                        } else {
                            retorno = "T";
                        }

                        if (retorno.charAt(0) == 'T') {
                            if (invProductoAux != null && codigoCambiarLlave.trim().isEmpty()) {
                                //PREPARANDO OBJETO SISSUCESO
                                susClave = invProductoTO.getProCodigoPrincipal();
                                susDetalle = "Se modificó el producto " + invProductoTO.getProNombre() + " con código " + invProductoTO.getProCodigoPrincipal();
                                susSuceso = "UPDATE";
                                susTabla = "inventario.inv_producto";
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                //PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                                invProductoTO.setUsrInsertaProducto(invProductoAux.getUsrCodigo());
                                invProductoTO.setUsrFechaInsertaProducto(UtilsValidacion.fecha(invProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);
                                //SI CONTIENE LA PALABRA "BALANCEADO" EN EL NOMBRE DEL PRODUCTO
                                InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());
                                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao.buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(), invProductoTO.getPresUCodigo());
                                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao.buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(), invProductoTO.getPresCCodigo());
                                InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());

                                if (invProductoMedida != null) {
                                    invProducto.setInvProductoMedida(invProductoMedida);
                                    invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                                    invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                                    invProducto.setInvProductoMarca(invProductoMarca);
                                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                                    if (invProductoCategoria != null) {
                                        invProducto.setInvProductoCategoria(invProductoCategoria);
                                        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                                        if (invProductoTipo != null) {
                                            invProducto.setInvProductoTipo(invProductoTipo);
                                            ////****************VALIDAR CUENTA COMPRA****************
                                            boolean inventarioValido = false;
                                            if (cuentaInventario == null || cuentaInventario.equals("") || invProductoTipo.getCtaCodigo() == null || invProductoTipo.getCtaCodigo().equals("")) {
                                                inventarioValido = true;
                                            } else {
                                                int tamanioGrupoTipoProducto = invProductoTipo.getCtaCodigo().length();
                                                String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                                if (grupoSeleccionado.equals(invProductoTipo.getCtaCodigo())) {
                                                    inventarioValido = true;
                                                }
                                            }
                                            //************************************************
                                            if (inventarioValido) {
                                                //CONVERTIR IMAGENES
                                                List<InvProductoDatosAdjuntos> listadoImagenesEnviar = new ArrayList<>();
                                                List<InvProductoDatosAdjuntos> listadoImagenesEnviarEliminar = new ArrayList<>();
                                                if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
                                                    for (InvAdjuntosProductosWebTO invAdjuntosProductosWebTO : listadoImagenes) {
                                                        InvProductoDatosAdjuntos invProductoDatosAdjuntos = new InvProductoDatosAdjuntos();
                                                        invProductoDatosAdjuntos.setAdjSecuencial(invAdjuntosProductosWebTO.getAdjSecuencial());
                                                        invProductoDatosAdjuntos.setAdjBucket(invAdjuntosProductosWebTO.getAdjBucket());
                                                        invProductoDatosAdjuntos.setAdjClaveArchivo(invAdjuntosProductosWebTO.getAdjClaveArchivo());
                                                        invProductoDatosAdjuntos.setAdjUrlArchivo(invAdjuntosProductosWebTO.getAdjUrlArchivo());
                                                        invProductoDatosAdjuntos.setAdjVerificado(invAdjuntosProductosWebTO.isAdjVerificado());
                                                        if (invAdjuntosProductosWebTO.getImagenString() != null) {
                                                            invProductoDatosAdjuntos.setAdjArchivo(invAdjuntosProductosWebTO.getImagenString().getBytes("UTF-8"));
                                                        }
                                                        listadoImagenesEnviar.add(invProductoDatosAdjuntos);
                                                    }
                                                }
                                                if (listadoImagenesEliminar != null && !listadoImagenesEliminar.isEmpty()) {
                                                    for (InvAdjuntosProductosWebTO invAdjuntosProductosWebTO : listadoImagenesEliminar) {
                                                        InvProductoDatosAdjuntos invProductoDatosAdjuntos = new InvProductoDatosAdjuntos();
                                                        invProductoDatosAdjuntos.setAdjSecuencial(invAdjuntosProductosWebTO.getAdjSecuencial());
                                                        invProductoDatosAdjuntos.setAdjBucket(invAdjuntosProductosWebTO.getAdjBucket());
                                                        invProductoDatosAdjuntos.setAdjClaveArchivo(invAdjuntosProductosWebTO.getAdjClaveArchivo());
                                                        invProductoDatosAdjuntos.setAdjUrlArchivo(invAdjuntosProductosWebTO.getAdjUrlArchivo());
                                                        invProductoDatosAdjuntos.setAdjVerificado(invAdjuntosProductosWebTO.isAdjVerificado());
                                                        if (invAdjuntosProductosWebTO.getImagenString() != null) {
                                                            invProductoDatosAdjuntos.setAdjArchivo(invAdjuntosProductosWebTO.getImagenString().getBytes("UTF-8"));
                                                        }
                                                        listadoImagenesEnviarEliminar.add(invProductoDatosAdjuntos);
                                                    }
                                                }
                                                //CONVERTIR ACTIVO FIJO
                                                AfActivos afActivo = null;
                                                AfActivos afActivoEliminar = null;
                                                if (afActivoTO != null) {
                                                    afActivoTO.setAfCodigo(invProducto.getInvProductoPK().getProCodigoPrincipal());
                                                    afActivoTO.setAfDescripcion(invProductoTO.getProNombre());
                                                    afActivo = new AfActivos();
                                                    afActivo.convertirObjeto(afActivoTO);
                                                } else {
                                                    afActivoEliminar = activoDao.obtenerPorId(AfActivos.class, new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                                                }
                                                //CONVERTIR PRODUCTOS RELACIONADOS
                                                List<InvProductoRelacionados> listaInvProductoRelacionadosEnviar = new ArrayList<>();
                                                List<InvProductoRelacionados> listaInvProductoRelacionadosEnviarEliminar = new ArrayList<>();
                                                for (InvProductoRelacionadoTO item : listaInvProductoRelacionados) {
                                                    InvProductoRelacionados producto = new InvProductoRelacionados();
                                                    producto.setPrSecuencial(item.getProSecuencial());
                                                    producto.setInvProducto(invProducto);
                                                    //RELACIONADO
                                                    InvProducto invProductoRelacionado = new InvProducto();
                                                    InvProductoPK pkRelacionado = new InvProductoPK();
                                                    pkRelacionado.setProCodigoPrincipal(item.getProRelacionadoCodigoPrincipal());
                                                    pkRelacionado.setProEmpresa(item.getProRelacionadoEmpresa());
                                                    invProductoRelacionado.setInvProductoPK(pkRelacionado);
                                                    producto.setInvProductoRelacionado(invProductoRelacionado);
                                                    listaInvProductoRelacionadosEnviar.add(producto);
                                                }

                                                for (InvProductoRelacionadoTO item : listaInvProductoRelacionadosEliminar) {
                                                    InvProductoRelacionados producto = new InvProductoRelacionados();
                                                    producto.setPrSecuencial(item.getProSecuencial());
                                                    producto.setInvProducto(invProducto);
                                                    //RELACIONADO
                                                    InvProducto invProductoRelacionado = new InvProducto();
                                                    InvProductoPK pkRelacionado = new InvProductoPK();
                                                    pkRelacionado.setProCodigoPrincipal(item.getProRelacionadoCodigoPrincipal());
                                                    pkRelacionado.setProEmpresa(item.getProRelacionadoEmpresa());
                                                    invProductoRelacionado.setInvProductoPK(pkRelacionado);
                                                    producto.setInvProductoRelacionado(invProductoRelacionado);
                                                    listaInvProductoRelacionadosEnviarEliminar.add(producto);
                                                }
                                                //CONVERTIR PRODUCTOS FORMULA
                                                List<InvProductoFormula> listaInvProductoFormulaEnviar = new ArrayList<>();
                                                List<InvProductoFormula> listaInvProductoFormulaEliminar = new ArrayList<>();
                                                for (InvProductoFormulaTO item : listaInvProductoFormulaTO) {
                                                    InvProductoFormula producto = new InvProductoFormula();
                                                    producto.setPrSecuencial(item.getProSecuencial());
                                                    producto.setInvProducto(invProducto);
                                                    //RELACIONADO
                                                    InvProducto invProductoFormula = new InvProducto();
                                                    InvProductoPK pkFormula = new InvProductoPK();
                                                    pkFormula.setProCodigoPrincipal(item.getProFormulaCodigoPrincipal());
                                                    pkFormula.setProEmpresa(item.getProFormulaEmpresa());
                                                    invProductoFormula.setInvProductoPK(pkFormula);
                                                    producto.setInvProductoFormula(invProductoFormula);
                                                    producto.setPrCantidad(item.getProCantidad());
                                                    listaInvProductoFormulaEnviar.add(producto);
                                                }

                                                for (InvProductoFormulaTO item : listaInvProductoFormulaTOEliminar) {
                                                    InvProductoFormula producto = new InvProductoFormula();
                                                    producto.setPrSecuencial(item.getProSecuencial());
                                                    producto.setInvProducto(invProducto);
                                                    //RELACIONADO
                                                    InvProducto invProductoFormula = new InvProducto();
                                                    InvProductoPK pkRelacionado = new InvProductoPK();
                                                    pkRelacionado.setProCodigoPrincipal(item.getProFormulaCodigoPrincipal());
                                                    pkRelacionado.setProEmpresa(item.getProFormulaEmpresa());
                                                    invProductoFormula.setInvProductoPK(pkRelacionado);
                                                    producto.setInvProductoFormula(invProductoFormula);
                                                    producto.setPrCantidad(item.getProCantidad());
                                                    listaInvProductoFormulaEliminar.add(producto);
                                                }
                                                if (productoDao.modificarInvProducto(invProducto, afActivo, afActivoEliminar, listadoImagenesEnviar, listadoImagenesEnviarEliminar, listaInvProductoRelacionadosEnviar, listaInvProductoRelacionadosEnviarEliminar, listaInvProductoFormulaEnviar, listaInvProductoFormulaEliminar, sisSuceso)) {
                                                    retorno = "TEl producto se modificó correctamente...";
                                                } else {
                                                    retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                                }
                                            } else {
                                                retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                                            }
                                        } else {
                                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                        }
                                    } else {
                                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                    }
                                } else {
                                    retorno = "FLa Medida que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                }
                            } else if (invProductoAux != null && !codigoCambiarLlave.trim().isEmpty()) {
                                //PREPARANDO OBJETO SISSUCESO
                                susClave = invProductoTO.getProCodigoPrincipal();
                                susDetalle = "Se modificó el código " + codigoCambiarLlave + " del producto " + invProductoTO.getProNombre() + " por el código " + invProductoTO.getProCodigoPrincipal();
                                susSuceso = "UPDATE";
                                susTabla = "inventario.inv_producto";
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                //PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                                invProductoTO.setUsrInsertaProducto(invProductoAux.getUsrCodigo());
                                invProductoTO.setUsrFechaInsertaProducto(UtilsValidacion.fecha(invProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);
                                //SI CONTIENE LA PALABRA "BALANCEADO" EN EL NOMBRE DEL PRODUCTO
                                InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());
                                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao.buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(), invProductoTO.getPresUCodigo());
                                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao.buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(), invProductoTO.getPresCCodigo());
                                InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());

                                if (invProductoMedida != null) {
                                    invProducto.setInvProductoMedida(invProductoMedida);
                                    invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                                    invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                                    invProducto.setInvProductoMarca(invProductoMarca);
                                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                                    if (invProductoCategoria != null) {
                                        invProducto.setInvProductoCategoria(invProductoCategoria);
                                        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                                        if (invProductoTipo != null) {
                                            invProducto.setInvProductoTipo(invProductoTipo);
                                            if (invProductoAux.getInvProductoPK().getProCodigoPrincipal().equals(invProducto.getInvProductoPK().getProCodigoPrincipal())) {

                                                ////****************VALIDAR CUENTA COMPRA****************
                                                boolean inventarioValido = false;
                                                if (cuentaInventario == null || cuentaInventario.equals("") || invProductoTipo.getCtaCodigo() == null || invProductoTipo.getCtaCodigo().equals("")) {
                                                    inventarioValido = true;
                                                } else {
                                                    int tamanioGrupoTipoProducto = invProductoTipo.getCtaCodigo().length();
                                                    String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                                    if (grupoSeleccionado.equals(invProductoTipo.getCtaCodigo())) {
                                                        inventarioValido = true;
                                                    }
                                                }
                                                //************************************************

                                                if (inventarioValido) {
                                                    //CONVERTIR IMAGENES
                                                    List<InvProductoDatosAdjuntos> listadoImagenesEnviar = new ArrayList<>();
                                                    List<InvProductoDatosAdjuntos> listadoImagenesEnviarEliminar = new ArrayList<>();
                                                    if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
                                                        for (InvAdjuntosProductosWebTO invAdjuntosProductosWebTO : listadoImagenes) {
                                                            InvProductoDatosAdjuntos invProductoDatosAdjuntos = new InvProductoDatosAdjuntos();
                                                            invProductoDatosAdjuntos.setAdjSecuencial(invAdjuntosProductosWebTO.getAdjSecuencial());
                                                            invProductoDatosAdjuntos.setAdjArchivo(invAdjuntosProductosWebTO.getImagenString().getBytes("UTF-8"));
                                                            listadoImagenesEnviar.add(invProductoDatosAdjuntos);
                                                        }
                                                    }
                                                    if (listadoImagenesEliminar != null && !listadoImagenesEliminar.isEmpty()) {
                                                        for (InvAdjuntosProductosWebTO invAdjuntosProductosWebTO : listadoImagenesEliminar) {
                                                            InvProductoDatosAdjuntos invProductoDatosAdjuntos = new InvProductoDatosAdjuntos();
                                                            invProductoDatosAdjuntos.setAdjSecuencial(invAdjuntosProductosWebTO.getAdjSecuencial());
                                                            invProductoDatosAdjuntos.setAdjArchivo(invAdjuntosProductosWebTO.getImagenString().getBytes("UTF-8"));
                                                            listadoImagenesEnviarEliminar.add(invProductoDatosAdjuntos);
                                                        }
                                                    }
                                                    //CONVERTIR ACTIVO FIJO
                                                    AfActivos afActivo = null;
                                                    AfActivos afActivoEliminar = null;
                                                    if (afActivoTO != null) {
                                                        afActivoTO.setAfCodigo(invProducto.getInvProductoPK().getProCodigoPrincipal());
                                                        afActivoTO.setAfDescripcion(invProductoTO.getProNombre());
                                                        afActivo = new AfActivos();
                                                        afActivo.convertirObjeto(afActivoTO);
                                                    } else {
                                                        afActivoEliminar = activoDao.obtenerPorId(AfActivos.class, new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                                                    }
                                                    //CONVERTIR PRODUCTOS RELACIONADOS
                                                    List<InvProductoRelacionados> listaInvProductoRelacionadosEnviar = new ArrayList<>();
                                                    List<InvProductoRelacionados> listaInvProductoRelacionadosEnviarEliminar = new ArrayList<>();
                                                    for (InvProductoRelacionadoTO item : listaInvProductoRelacionados) {
                                                        InvProductoRelacionados producto = new InvProductoRelacionados();
                                                        producto.setPrSecuencial(item.getProSecuencial());
                                                        producto.setInvProducto(invProducto);
                                                        //RELACIONADO
                                                        InvProducto invProductoRelacionado = new InvProducto();
                                                        InvProductoPK pkRelacionado = new InvProductoPK();
                                                        pkRelacionado.setProCodigoPrincipal(item.getProRelacionadoCodigoPrincipal());
                                                        pkRelacionado.setProEmpresa(item.getProRelacionadoEmpresa());
                                                        invProductoRelacionado.setInvProductoPK(pkRelacionado);
                                                        producto.setInvProductoRelacionado(invProductoRelacionado);
                                                        listaInvProductoRelacionadosEnviar.add(producto);
                                                    }

                                                    for (InvProductoRelacionadoTO item : listaInvProductoRelacionadosEliminar) {
                                                        InvProductoRelacionados producto = new InvProductoRelacionados();
                                                        producto.setPrSecuencial(item.getProSecuencial());
                                                        producto.setInvProducto(invProducto);
                                                        //RELACIONADO
                                                        InvProducto invProductoRelacionado = new InvProducto();
                                                        InvProductoPK pkRelacionado = new InvProductoPK();
                                                        pkRelacionado.setProCodigoPrincipal(item.getProRelacionadoCodigoPrincipal());
                                                        pkRelacionado.setProEmpresa(item.getProRelacionadoEmpresa());
                                                        invProductoRelacionado.setInvProductoPK(pkRelacionado);
                                                        producto.setInvProductoRelacionado(invProductoRelacionado);
                                                        listaInvProductoRelacionadosEnviarEliminar.add(producto);
                                                    }
                                                    //CONVERTIR PRODUCTOS FORMULA
                                                    List<InvProductoFormula> listaInvProductoFormulaEnviar = new ArrayList<>();
                                                    List<InvProductoFormula> listaInvProductoFormulaEliminar = new ArrayList<>();
                                                    for (InvProductoFormulaTO item : listaInvProductoFormulaTO) {
                                                        InvProductoFormula producto = new InvProductoFormula();
                                                        producto.setPrSecuencial(item.getProSecuencial());
                                                        producto.setInvProducto(invProducto);
                                                        //FORMULA
                                                        InvProducto invProductoFormula = new InvProducto();
                                                        InvProductoPK pkFormula = new InvProductoPK();
                                                        pkFormula.setProCodigoPrincipal(item.getProFormulaCodigoPrincipal());
                                                        pkFormula.setProEmpresa(item.getProFormulaEmpresa());
                                                        invProductoFormula.setInvProductoPK(pkFormula);
                                                        producto.setInvProductoFormula(invProductoFormula);
                                                        producto.setPrCantidad(item.getProCantidad());
                                                        listaInvProductoFormulaEnviar.add(producto);
                                                    }

                                                    for (InvProductoFormulaTO item : listaInvProductoFormulaTOEliminar) {
                                                        InvProductoFormula producto = new InvProductoFormula();
                                                        producto.setPrSecuencial(item.getProSecuencial());
                                                        producto.setInvProducto(invProducto);
                                                        //FORMULA
                                                        InvProducto invProductoFormula = new InvProducto();
                                                        InvProductoPK pkRelacionado = new InvProductoPK();
                                                        pkRelacionado.setProCodigoPrincipal(item.getProFormulaCodigoPrincipal());
                                                        pkRelacionado.setProEmpresa(item.getProFormulaEmpresa());
                                                        invProductoFormula.setInvProductoPK(pkRelacionado);
                                                        producto.setInvProductoFormula(invProductoFormula);
                                                        producto.setPrCantidad(item.getProCantidad());
                                                        listaInvProductoFormulaEliminar.add(producto);
                                                    }
                                                    if (productoDao.modificarInvProducto(invProducto, afActivo, afActivoEliminar, listadoImagenesEnviar, listadoImagenesEnviarEliminar, listaInvProductoRelacionadosEnviar, listaInvProductoRelacionadosEnviarEliminar,
                                                            listaInvProductoFormulaEnviar, listaInvProductoFormulaEliminar, sisSuceso)) {
                                                        retorno = "TEl producto se modificó correctamente...";
                                                    } else {
                                                        retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                                    }
                                                } else {
                                                    retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                                                }
                                            } else if (productoDao.modificarInvProductoLlavePrincipal(invProductoAux, invProducto, sisSuceso)) {
                                                retorno = "TEl producto se modificó correctamente...";
                                            } else {
                                                retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                            }
                                        } else {
                                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                        }
                                    } else {
                                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                    }
                                } else {
                                    retorno = "FLa Medida que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                }
                            } else if (invProductoAux == null && codigoCambiarLlave.trim().isEmpty()) {
                                retorno = "FEl código del producto que va a modificar ya no está disponible...\nIntente con otro...";
                            } else {
                                //PREPARANDO OBJETO SISSUCESO
                                susClave = invProductoTO.getProCodigoPrincipal();
                                susDetalle = "Se modificó el código " + codigoCambiarLlave + " del producto " + invProductoTO.getProNombre() + " por el código " + invProductoTO.getProCodigoPrincipal();
                                susSuceso = "UPDATE";
                                susTabla = "inventario.inv_producto";
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                                invProductoTO.setUsrInsertaProducto(invProductoAux.getUsrCodigo());
                                invProductoTO.setUsrFechaInsertaProducto(UtilsValidacion.fecha(invProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                                invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);
                                /// SI CONTIENE LA PALABRA "BALANCEADO" EN EL NOMBRE DEL PRODUCTO
                                InvProductoMedida invProductoMedida = productoMedidaDao.buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());
                                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao.buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(), invProductoTO.getPresUCodigo());
                                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao.buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(), invProductoTO.getPresCCodigo());
                                InvProductoMarca invProductoMarca = productoMarcaDao.buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());

                                if (invProductoMedida != null) {
                                    invProducto.setInvProductoMedida(invProductoMedida);
                                    invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                                    invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                                    invProducto.setInvProductoMarca(invProductoMarca);
                                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                                    if (invProductoCategoria != null) {
                                        invProducto.setInvProductoCategoria(invProductoCategoria);
                                        InvProductoTipo invProductoTipo = productoTipoDao.buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                                        if (invProductoTipo != null) {
                                            invProducto.setInvProductoTipo(invProductoTipo);
                                            if (invProductoAux.getInvProductoPK().getProCodigoPrincipal().equals(invProducto.getInvProductoPK().getProCodigoPrincipal())) {
                                                ////****************VALIDAR CUENTA COMPRA****************
                                                boolean inventarioValido = false;
                                                if (cuentaInventario == null || cuentaInventario.equals("") || invProductoTipo.getCtaCodigo() == null || invProductoTipo.getCtaCodigo().equals("")) {
                                                    inventarioValido = true;
                                                } else {
                                                    int tamanioGrupoTipoProducto = invProductoTipo.getCtaCodigo().length();
                                                    String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupoTipoProducto);
                                                    if (grupoSeleccionado.equals(invProductoTipo.getCtaCodigo())) {
                                                        inventarioValido = true;
                                                    }
                                                }
                                                //************************************************

                                                if (inventarioValido) {
                                                    //CONVERTIR IMAGENES
                                                    List<InvProductoDatosAdjuntos> listadoImagenesEnviar = new ArrayList<>();
                                                    List<InvProductoDatosAdjuntos> listadoImagenesEnviarEliminar = new ArrayList<>();
                                                    if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
                                                        for (InvAdjuntosProductosWebTO invAdjuntosProductosWebTO : listadoImagenes) {
                                                            InvProductoDatosAdjuntos invProductoDatosAdjuntos = new InvProductoDatosAdjuntos();
                                                            invProductoDatosAdjuntos.setAdjSecuencial(invAdjuntosProductosWebTO.getAdjSecuencial());
                                                            invProductoDatosAdjuntos.setInvProducto(invProducto);
                                                            invProductoDatosAdjuntos.setAdjArchivo(invAdjuntosProductosWebTO.getImagenString().getBytes("UTF-8"));
                                                            listadoImagenesEnviar.add(invProductoDatosAdjuntos);
                                                        }
                                                    }
                                                    if (listadoImagenesEliminar != null && !listadoImagenesEliminar.isEmpty()) {
                                                        for (InvAdjuntosProductosWebTO invAdjuntosProductosWebTO : listadoImagenesEliminar) {
                                                            InvProductoDatosAdjuntos invProductoDatosAdjuntos = new InvProductoDatosAdjuntos();
                                                            invProductoDatosAdjuntos.setAdjSecuencial(invAdjuntosProductosWebTO.getAdjSecuencial());
                                                            invProductoDatosAdjuntos.setInvProducto(invProducto);
                                                            invProductoDatosAdjuntos.setAdjArchivo(invAdjuntosProductosWebTO.getImagenString().getBytes("UTF-8"));
                                                            listadoImagenesEnviarEliminar.add(invProductoDatosAdjuntos);
                                                        }
                                                    }
                                                    //CONVERTIR ACTIVO FIJO
                                                    AfActivos afActivo = null;
                                                    AfActivos afActivoEliminar = null;
                                                    if (afActivoTO != null) {
                                                        afActivoTO.setAfCodigo(invProducto.getInvProductoPK().getProCodigoPrincipal());
                                                        afActivoTO.setAfDescripcion(invProductoTO.getProNombre());
                                                        afActivo = new AfActivos();
                                                        afActivo.convertirObjeto(afActivoTO);
                                                    } else {
                                                        afActivoEliminar = activoDao.obtenerPorId(AfActivos.class, new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                                                    }
                                                    //CONVERTIR PRODUCTOS RELACIONADOS
                                                    List<InvProductoRelacionados> listaInvProductoRelacionadosEnviar = new ArrayList<>();
                                                    List<InvProductoRelacionados> listaInvProductoRelacionadosEnviarEliminar = new ArrayList<>();
                                                    for (InvProductoRelacionadoTO item : listaInvProductoRelacionados) {
                                                        InvProductoRelacionados producto = new InvProductoRelacionados();
                                                        producto.setPrSecuencial(item.getProSecuencial());
                                                        producto.setInvProducto(invProducto);
                                                        //RELACIONADO
                                                        InvProducto invProductoRelacionado = new InvProducto();
                                                        InvProductoPK pkRelacionado = new InvProductoPK();
                                                        pkRelacionado.setProCodigoPrincipal(item.getProRelacionadoCodigoPrincipal());
                                                        pkRelacionado.setProEmpresa(item.getProRelacionadoEmpresa());
                                                        invProductoRelacionado.setInvProductoPK(pkRelacionado);
                                                        producto.setInvProductoRelacionado(invProductoRelacionado);
                                                        listaInvProductoRelacionadosEnviar.add(producto);
                                                    }

                                                    for (InvProductoRelacionadoTO item : listaInvProductoRelacionadosEliminar) {
                                                        InvProductoRelacionados producto = new InvProductoRelacionados();
                                                        producto.setPrSecuencial(item.getProSecuencial());
                                                        producto.setInvProducto(invProducto);
                                                        //RELACIONADO
                                                        InvProducto invProductoRelacionado = new InvProducto();
                                                        InvProductoPK pkRelacionado = new InvProductoPK();
                                                        pkRelacionado.setProCodigoPrincipal(item.getProRelacionadoCodigoPrincipal());
                                                        pkRelacionado.setProEmpresa(item.getProRelacionadoEmpresa());
                                                        invProductoRelacionado.setInvProductoPK(pkRelacionado);
                                                        producto.setInvProductoRelacionado(invProductoRelacionado);
                                                        listaInvProductoRelacionadosEnviarEliminar.add(producto);
                                                    }
                                                    //CONVERTIR PRODUCTOS FORMULA
                                                    List<InvProductoFormula> listaInvProductoFormulaEnviar = new ArrayList<>();
                                                    List<InvProductoFormula> listaInvProductoFormulaEliminar = new ArrayList<>();
                                                    for (InvProductoFormulaTO item : listaInvProductoFormulaTO) {
                                                        InvProductoFormula producto = new InvProductoFormula();
                                                        producto.setPrSecuencial(item.getProSecuencial());
                                                        producto.setInvProducto(invProducto);
                                                        //FORMULA
                                                        InvProducto invProductoFormula = new InvProducto();
                                                        InvProductoPK pkFormula = new InvProductoPK();
                                                        pkFormula.setProCodigoPrincipal(item.getProFormulaCodigoPrincipal());
                                                        pkFormula.setProEmpresa(item.getProFormulaEmpresa());
                                                        invProductoFormula.setInvProductoPK(pkFormula);
                                                        producto.setInvProductoFormula(invProductoFormula);
                                                        producto.setPrCantidad(item.getProCantidad());
                                                        listaInvProductoFormulaEnviar.add(producto);
                                                    }

                                                    for (InvProductoFormulaTO item : listaInvProductoFormulaTOEliminar) {
                                                        InvProductoFormula producto = new InvProductoFormula();
                                                        producto.setPrSecuencial(item.getProSecuencial());
                                                        producto.setInvProducto(invProducto);
                                                        //FORMULA
                                                        InvProducto invProductoFormula = new InvProducto();
                                                        InvProductoPK pkRelacionado = new InvProductoPK();
                                                        pkRelacionado.setProCodigoPrincipal(item.getProFormulaCodigoPrincipal());
                                                        pkRelacionado.setProEmpresa(item.getProFormulaEmpresa());
                                                        invProductoFormula.setInvProductoPK(pkRelacionado);
                                                        producto.setInvProductoFormula(invProductoFormula);
                                                        producto.setPrCantidad(item.getProCantidad());
                                                        listaInvProductoFormulaEliminar.add(producto);
                                                    }

                                                    if (productoDao.modificarInvProducto(invProducto, afActivo, afActivoEliminar, listadoImagenesEnviar, listadoImagenesEnviarEliminar, listaInvProductoRelacionadosEnviar, listaInvProductoRelacionadosEnviarEliminar, listaInvProductoFormulaEnviar, listaInvProductoFormulaEliminar, sisSuceso)) {
                                                        retorno = "TEl producto se modificó correctamente...";
                                                    } else {
                                                        retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                                    }
                                                } else {
                                                    retorno = "FLa cuenta de compra no coincide con la cuenta de tipo de producto";
                                                }
                                            } else if (productoDao.modificarInvProductoLlavePrincipal(invProductoAux, invProducto, sisSuceso)) {
                                                retorno = "TEl producto se modificó correctamente...";
                                            } else {
                                                retorno = "FHubo un error al modificar el producto...\nIntente de nuevo o contacte con el administrador";
                                            }
                                        } else {
                                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                        }
                                    } else {
                                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                    }
                                } else {
                                    retorno = "FLa Medida que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                                }
                            }
                        }
                        if (retorno.charAt(0) == 'T') {
                            invProductoTO.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
                            verificarCuentaCostoAutomatica(invProductoTO, sisInfoTO);
                        }
                        respuesta.put("mensaje", retorno);
                    } else {
                        respuesta.put("mensaje", "FCuenta invalidas");
                        respuesta.put("cuentaCompraValido", respuesCategorias.get("cuentaCompraValido"));
                        respuesta.put("cuentaVentaValido", respuesCategorias.get("cuentaVentaValido"));
                        respuesta.put("cuentaCostoAutomaticoValido", respuesCategorias.get("cuentaCostoAutomaticoValido"));
                        respuesta.put("cuentaGastoValido", respuesCategorias.get("cuentaGastoValido"));
                        respuesta.put("cuentaVentaExteriorValido", respuesCategorias.get("cuentaVentaExteriorValido"));
                    }
                } else {
                    respuesta.put("mensaje", mensajeCuentas.substring(0));
                }
            } else {
                respuesta.put("mensaje", "FHubo un error con la validación de las cuentas. Comuniquese con el administrador");
            }
        } else {
            respuesta.put("mensaje", "FLa validación de las cuentas es incorrecta. Verifique");
        }

        return respuesta;
    }

    @Override
    public boolean modificarListaInvProducto(List<InvProducto> listaInvProducto, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        List<SisSuceso> listaSuceso = new ArrayList<>();
        listaInvProducto.forEach((producto) -> {
            susClave = producto.getInvProductoPK().getProCodigoPrincipal() + "";
            susDetalle = "Se modificó el producto " + producto.getProNombre() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            listaSuceso.add(sisSuceso);
        });
        productoDao.actualizar(listaInvProducto);
        sucesoDao.insertar(listaSuceso);
        return true;
    }

    @Override
    public String eliminarInvProductoTO(InvProductoTO invProductoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        Boolean puedeEliminar = false;
        InvProducto invProductoAux = productoDao.buscarInvProducto(invProductoTO.getProEmpresa(),
                invProductoTO.getProCodigoPrincipal());
        if (invProductoAux != null) {
            puedeEliminar = productoDao.getPuedeEliminarProducto(invProductoTO.getProEmpresa(),
                    invProductoTO.getProCodigoPrincipal());
            if (puedeEliminar) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invProductoTO.getProCodigoPrincipal();
                susDetalle = "Se eliminó el producto " + invProductoTO.getProNombre() + " con código "
                        + invProductoTO.getProCodigoPrincipal();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_producto";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invProductoTO.setUsrInsertaProducto(invProductoAux.getUsrCodigo());
                invProductoTO.setUsrFechaInsertaProducto(
                        UtilsValidacion.fecha(invProductoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvProducto invProducto = ConversionesInventario.convertirInvProductoTO_InvProducto(invProductoTO);
                InvProductoMedida invProductoMedida = productoMedidaDao
                        .buscarProductoMedida(invProductoTO.getMedEmpresa(), invProductoTO.getMedCodigo());
                invProducto.setInvProductoMedida(invProductoMedida);
                InvProductoPresentacionUnidades invProductoPresentacionUnidades = productoPresentacionUnidadesDao
                        .buscarProductoPresentacionUnidades(invProductoTO.getPresUEmpresa(),
                                invProductoTO.getPresUCodigo());
                invProducto.setInvProductoPresentacionUnidades(invProductoPresentacionUnidades);
                InvProductoPresentacionCajas invProductoPresentacionCajas = productoPresentacionCajasDao
                        .buscarProductoPresentacionCajas(invProductoTO.getPresCEmpresa(),
                                invProductoTO.getPresCCodigo());
                invProducto.setInvProductoPresentacionCajas(invProductoPresentacionCajas);
                InvProductoMarca invProductoMarca = productoMarcaDao
                        .buscarMarcaProducto(invProductoTO.getMarEmpresa(), invProductoTO.getMarCodigo());
                invProducto.setInvProductoMarca(invProductoMarca);

                if (invProductoTO.getProNombre().lastIndexOf("BALANCEADO") != -1) {
                    if (corridaDao.comprobarPrdBalanceado(invProductoTO.getProEmpresa(),
                            invProductoTO.getProNombre())) {
                        if (invProductoTO.getBalFactorLibras() > 0) {
                            PrdBalanceadoTO prdBalanceadoTO = new PrdBalanceadoTO();
                            prdBalanceadoTO.setBalEmpresa(invProductoTO.getProEmpresa());
                            prdBalanceadoTO.setBalCodigoPrincipal(invProductoTO.getProCodigoPrincipal());
                            prdBalanceadoTO.setBalFactorLibras(invProductoTO.getBalFactorLibras());

                        }
                    }
                }

                if (invProductoMedida != null) {
                    invProducto.setInvProductoMedida(invProductoMedida);
                    InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(
                            invProductoTO.getCatEmpresa(), invProductoTO.getCatCodigo());
                    if (invProductoCategoria != null) {
                        invProducto.setInvProductoCategoria(invProductoCategoria);
                        InvProductoTipo invProductoTipo = productoTipoDao
                                .buscarInvProductoTipo(invProductoTO.getTipEmpresa(), invProductoTO.getTipCodigo());
                        if (invProductoTipo != null) {
                            invProducto.setInvProductoTipo(invProductoTipo);
                            if (productoDao.eliminarInvProducto(invProducto, sisSuceso)) {
                                retorno = "TEl producto se eliminó correctamente...";
                            } else {
                                retorno = "FHubo un error al eliminar el producto...\nIntente de nuevo o contacte con el administrador";
                            }
                        } else {
                            retorno = "FEl Tipo de Producto que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                        }
                    } else {
                        retorno = "FLa Categoría que escogió ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                    }
                } else if (invProductoMedida == null) {
                    retorno = "FNo se puede eliminar este producto porque la Medida de este Producto ya no se encuentra disponible...\nIntente de nuevo o contacte con el administrador";
                }
            } else {
                retorno = "FNo se puede eliminar este producto debido a que tiene movimientos en Inventarios.\nIntente con otro...";
            }
        } else {
            retorno = "FEl Producto que va a eliminar ya no está disponible...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public List<InvProductoTO> getProductoTO(String empresa, String codigo) throws Exception {
        return productoDao.getProductoTO(empresa, codigo);
    }

    @Override
    public String obtenerCodigoPorNombre(String empresa, String nombre) throws Exception {
        return productoDao.obtenerCodigoPorNombre(empresa, nombre);
    }

    @Override
    public InvProductoTO obtenerProductoTO(String empresa, String codigo) throws Exception {
        return productoDao.obtenerProductoTO(empresa, codigo);
    }

    @Override
    public InvProducto obtenerPorId(String empresa, String codigo) throws Exception {
        return productoDao.obtenerPorId(InvProducto.class, new InvProductoPK(empresa, codigo));
    }

    @Override
    public List<InvFunListadoProductosTO> getInvFunListadoProductosTO(String empresa, String categoria,
            String busqueda) throws Exception {
        return productoDao.getInvFunListadoProductosTO(empresa, categoria, busqueda);
    }

    @Override
    public Boolean getPuedeEliminarProducto(String empresa, String producto) throws Exception {
        return productoDao.getPuedeEliminarProducto(empresa, producto);
    }

    @Override
    public BigDecimal getPrecioProductoPorCantidad(String empresa, String cliente,
            String codProducto, BigDecimal cantidad) throws Exception {
        return productoDao.getPrecioProductoPorCantidad(empresa, cliente, codProducto, cantidad);
    }

    @Override
    public BigDecimal getCantidad3(String empresa, String codProducto) throws Exception {
        return productoDao.getCantidad3(empresa, codProducto);
    }

    @Override
    public List<InvProductosConErrorTO> getListadoProductosConError(String empresa) throws Exception {
        return productoDao.getListadoProductosConError(empresa);
    }

    @Override
    public List<InvFunListaProductosImpresionPlacasTO> getInvFunListaProductosImpresionPlacasTO(String empresa, String producto,
            boolean estado) throws Exception {
        return productoDao.getInvFunListaProductosImpresionPlacasTO(empresa, producto, estado);
    }

    @Override
    public MensajeTO invCambiarPrecioProducto(String empresa, String usuario,
            List<InvListaProductosCambiarPrecioTO> invListaProductosCambiarPrecioTOs, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();
        List<InvProducto> invProductos = new ArrayList<InvProducto>();
        List<SisSuceso> sisSucesos = new ArrayList<SisSuceso>();
        for (InvListaProductosCambiarPrecioTO ipcpto : invListaProductosCambiarPrecioTOs) {
            InvProducto invProducto = productoDao.buscarInvProducto(empresa, ipcpto.getProCodigoPrincipal());
            if (invProducto != null) {
                ipcpto.setStockPrecio1(ipcpto.getStockPrecio1() == null ? cero : ipcpto.getStockPrecio1());
                ipcpto.setStockPrecio2(ipcpto.getStockPrecio2() == null ? cero : ipcpto.getStockPrecio2());
                ipcpto.setStockPrecio3(ipcpto.getStockPrecio3() == null ? cero : ipcpto.getStockPrecio3());
                ipcpto.setStockPrecio4(ipcpto.getStockPrecio4() == null ? cero : ipcpto.getStockPrecio4());
                ipcpto.setStockPrecio5(ipcpto.getStockPrecio5() == null ? cero : ipcpto.getStockPrecio5());

                ipcpto.setStockDescuento1(ipcpto.getStockDescuento1() == null ? cero : ipcpto.getStockDescuento1());
                ipcpto.setStockDescuento2(ipcpto.getStockDescuento2() == null ? cero : ipcpto.getStockDescuento2());
                ipcpto.setStockDescuento3(ipcpto.getStockDescuento3() == null ? cero : ipcpto.getStockDescuento3());
                ipcpto.setStockDescuento4(ipcpto.getStockDescuento4() == null ? cero : ipcpto.getStockDescuento4());
                ipcpto.setStockDescuento5(ipcpto.getStockDescuento5() == null ? cero : ipcpto.getStockDescuento5());

                ipcpto.setStockUtilidad1(ipcpto.getStockUtilidad1() == null ? cero : ipcpto.getStockUtilidad1());
                ipcpto.setStockUtilidad2(ipcpto.getStockUtilidad2() == null ? cero : ipcpto.getStockUtilidad2());
                ipcpto.setStockUtilidad3(ipcpto.getStockUtilidad3() == null ? cero : ipcpto.getStockUtilidad3());
                ipcpto.setStockUtilidad4(ipcpto.getStockUtilidad4() == null ? cero : ipcpto.getStockUtilidad4());
                ipcpto.setStockUtilidad5(ipcpto.getStockUtilidad5() == null ? cero : ipcpto.getStockUtilidad5());

                invProducto.setProNombre(ipcpto.getProNombre());
                invProducto.setProPrecio1(invProducto.getProPrecio1() == null ? cero : invProducto.getProPrecio1());
                invProducto.setProPrecio2(invProducto.getProPrecio2() == null ? cero : invProducto.getProPrecio2());
                invProducto.setProPrecio3(invProducto.getProPrecio3() == null ? cero : invProducto.getProPrecio3());
                invProducto.setProPrecio4(invProducto.getProPrecio4() == null ? cero : invProducto.getProPrecio4());
                invProducto.setProPrecio5(invProducto.getProPrecio5() == null ? cero : invProducto.getProPrecio5());

                invProducto.setProDescuento1(
                        invProducto.getProDescuento1() == null ? cero : invProducto.getProDescuento1());
                invProducto.setProDescuento2(
                        invProducto.getProDescuento2() == null ? cero : invProducto.getProDescuento2());
                invProducto.setProDescuento3(
                        invProducto.getProDescuento3() == null ? cero : invProducto.getProDescuento3());
                invProducto.setProDescuento4(
                        invProducto.getProDescuento4() == null ? cero : invProducto.getProDescuento4());
                invProducto.setProDescuento5(
                        invProducto.getProDescuento5() == null ? cero : invProducto.getProDescuento5());

                invProducto.setProMargenUtilidad1(
                        invProducto.getProMargenUtilidad1() == null ? cero : invProducto.getProMargenUtilidad1());
                invProducto.setProMargenUtilidad2(
                        invProducto.getProMargenUtilidad2() == null ? cero : invProducto.getProMargenUtilidad2());
                invProducto.setProMargenUtilidad3(
                        invProducto.getProMargenUtilidad3() == null ? cero : invProducto.getProMargenUtilidad3());
                invProducto.setProMargenUtilidad4(
                        invProducto.getProMargenUtilidad4() == null ? cero : invProducto.getProMargenUtilidad4());
                invProducto.setProMargenUtilidad5(
                        invProducto.getProMargenUtilidad5() == null ? cero : invProducto.getProMargenUtilidad5());

                if (invProducto.getProPrecio1().compareTo(ipcpto.getStockPrecio1()) != 0
                        || invProducto.getProDescuento1().compareTo(ipcpto.getStockDescuento1()) != 0
                        || invProducto.getProMargenUtilidad1().compareTo(ipcpto.getStockUtilidad1()) != 0
                        || invProducto.getProPrecio2().compareTo(ipcpto.getStockPrecio2()) != 0
                        || invProducto.getProDescuento2().compareTo(ipcpto.getStockDescuento2()) != 0
                        || invProducto.getProMargenUtilidad2().compareTo(ipcpto.getStockUtilidad2()) != 0
                        || invProducto.getProPrecio3().compareTo(ipcpto.getStockPrecio3()) != 0
                        || invProducto.getProDescuento3().compareTo(ipcpto.getStockDescuento3()) != 0
                        || invProducto.getProMargenUtilidad3().compareTo(ipcpto.getStockUtilidad3()) != 0
                        || invProducto.getProPrecio4().compareTo(ipcpto.getStockPrecio4()) != 0
                        || invProducto.getProDescuento4().compareTo(ipcpto.getStockDescuento4()) != 0
                        || invProducto.getProMargenUtilidad4().compareTo(ipcpto.getStockUtilidad4()) != 0
                        || invProducto.getProPrecio5().compareTo(ipcpto.getStockPrecio5()) != 0
                        || invProducto.getProDescuento5().compareTo(ipcpto.getStockDescuento5()) != 0
                        || invProducto.getProMargenUtilidad5().compareTo(ipcpto.getStockUtilidad5()) != 0) {

                    invProducto.setProPrecio1(ipcpto.getStockPrecio1());
                    invProducto.setProPrecio2(ipcpto.getStockPrecio2());
                    invProducto.setProPrecio3(ipcpto.getStockPrecio3());
                    invProducto.setProPrecio4(ipcpto.getStockPrecio4());
                    invProducto.setProPrecio5(ipcpto.getStockPrecio5());

                    invProducto.setProDescuento1(ipcpto.getStockDescuento1());
                    invProducto.setProDescuento2(ipcpto.getStockDescuento2());
                    invProducto.setProDescuento3(ipcpto.getStockDescuento3());
                    invProducto.setProDescuento4(ipcpto.getStockDescuento4());
                    invProducto.setProDescuento5(ipcpto.getStockDescuento5());

                    invProducto.setProMargenUtilidad1(ipcpto.getStockUtilidad1());
                    invProducto.setProMargenUtilidad2(ipcpto.getStockUtilidad2());
                    invProducto.setProMargenUtilidad3(ipcpto.getStockUtilidad3());
                    invProducto.setProMargenUtilidad4(ipcpto.getStockUtilidad4());
                    invProducto.setProMargenUtilidad5(ipcpto.getStockUtilidad5());

                    invProductos.add(invProducto);
                    susClave = invProducto.getInvProductoPK().getProCodigoPrincipal();
                    susDetalle = "Se actualiza el precio: " + invProducto.getProNombre();
                    susSuceso = "UPDATE";
                    susTabla = "inventario.inv_producto";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    sisSucesos.add(sisSuceso);
                }
            } else {
                mensajeClase.add("El producto " + ipcpto.getProNombre() + " no se encuentra disponible.");
            }
        }
        if (mensajeClase.isEmpty()) {
            if (productoDao.invCambiarPrecioProductos(invProductos, sisSucesos)) {
                mensajeTO.setMensaje(
                        "T<html>Los precios de los Productos fueron actualizados correctamente...</html>");
            } else {
                mensajeTO.setMensaje("F<html>Hubo un error al actualizar los precios de los productos...</html>");
                mensajeTO.setListaErrores1(mensajeClase);
            }
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO invCambiarPrecioCantidadProducto(String empresa, String usuario,
            List<InvListaProductosCambiarPrecioCantidadTO> invListaProductosCambiarPrecioCantidadTOs, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<>();
        List<InvProducto> invProductos = new ArrayList<>();
        List<SisSuceso> sisSucesos = new ArrayList<>();
        for (InvListaProductosCambiarPrecioCantidadTO invListaProductosCambiarPrecioCantidadTO : invListaProductosCambiarPrecioCantidadTOs) {
            InvProducto invProducto = productoDao.buscarInvProducto(empresa,
                    invListaProductosCambiarPrecioCantidadTO.getProCodigoPrincipal());
            if (invProducto != null) {
                invListaProductosCambiarPrecioCantidadTO
                        .setStockPrecio1(invListaProductosCambiarPrecioCantidadTO.getStockPrecio1() == null ? cero
                                : invListaProductosCambiarPrecioCantidadTO.getStockPrecio1());
                invListaProductosCambiarPrecioCantidadTO
                        .setStockPrecio2(invListaProductosCambiarPrecioCantidadTO.getStockPrecio2() == null ? cero
                                : invListaProductosCambiarPrecioCantidadTO.getStockPrecio2());
                invListaProductosCambiarPrecioCantidadTO
                        .setStockPrecio3(invListaProductosCambiarPrecioCantidadTO.getStockPrecio3() == null ? cero
                                : invListaProductosCambiarPrecioCantidadTO.getStockPrecio3());
                invListaProductosCambiarPrecioCantidadTO
                        .setStockPrecio4(invListaProductosCambiarPrecioCantidadTO.getStockPrecio4() == null ? cero
                                : invListaProductosCambiarPrecioCantidadTO.getStockPrecio4());
                invListaProductosCambiarPrecioCantidadTO
                        .setStockPrecio5(invListaProductosCambiarPrecioCantidadTO.getStockPrecio5() == null ? cero
                                : invListaProductosCambiarPrecioCantidadTO.getStockPrecio5());

                invListaProductosCambiarPrecioCantidadTO
                        .setStockCantidad1(invListaProductosCambiarPrecioCantidadTO.getStockCantidad1() == null
                                ? cero : invListaProductosCambiarPrecioCantidadTO.getStockCantidad1());
                invListaProductosCambiarPrecioCantidadTO
                        .setStockCantidad2(invListaProductosCambiarPrecioCantidadTO.getStockCantidad2() == null
                                ? cero : invListaProductosCambiarPrecioCantidadTO.getStockCantidad2());
                invListaProductosCambiarPrecioCantidadTO
                        .setStockCantidad3(invListaProductosCambiarPrecioCantidadTO.getStockCantidad3() == null
                                ? cero : invListaProductosCambiarPrecioCantidadTO.getStockCantidad3());
                invListaProductosCambiarPrecioCantidadTO
                        .setStockCantidad4(invListaProductosCambiarPrecioCantidadTO.getStockCantidad4() == null
                                ? cero : invListaProductosCambiarPrecioCantidadTO.getStockCantidad4());
                invListaProductosCambiarPrecioCantidadTO
                        .setStockCantidad5(invListaProductosCambiarPrecioCantidadTO.getStockCantidad5() == null
                                ? cero : invListaProductosCambiarPrecioCantidadTO.getStockCantidad5());

                invProducto.setProPrecio1(invProducto.getProPrecio1() == null ? cero : invProducto.getProPrecio1());
                invProducto.setProPrecio2(invProducto.getProPrecio2() == null ? cero : invProducto.getProPrecio2());
                invProducto.setProPrecio3(invProducto.getProPrecio3() == null ? cero : invProducto.getProPrecio3());
                invProducto.setProPrecio4(invProducto.getProPrecio4() == null ? cero : invProducto.getProPrecio4());
                invProducto.setProPrecio5(invProducto.getProPrecio5() == null ? cero : invProducto.getProPrecio5());

                invProducto.setProCantidad1(
                        invProducto.getProCantidad1() == null ? cero : invProducto.getProCantidad1());
                invProducto.setProCantidad2(
                        invProducto.getProCantidad2() == null ? cero : invProducto.getProCantidad2());
                invProducto.setProCantidad3(
                        invProducto.getProCantidad3() == null ? cero : invProducto.getProCantidad3());
                invProducto.setProCantidad4(
                        invProducto.getProCantidad4() == null ? cero : invProducto.getProCantidad4());
                invProducto.setProCantidad5(
                        invProducto.getProCantidad5() == null ? cero : invProducto.getProCantidad5());

                if (invProducto.getProPrecio1()
                        .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockPrecio1()) != 0
                        || invProducto.getProPrecio2()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockPrecio2()) != 0
                        || invProducto.getProPrecio3()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockPrecio3()) != 0
                        || invProducto.getProPrecio4()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockPrecio4()) != 0
                        || invProducto.getProPrecio5()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockPrecio5()) != 0
                        || invProducto.getProCantidad1()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockCantidad1()) != 0
                        || invProducto.getProCantidad2()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockCantidad2()) != 0
                        || invProducto.getProCantidad3()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockCantidad3()) != 0
                        || invProducto.getProCantidad4()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockCantidad4()) != 0
                        || invProducto.getProCantidad5()
                                .compareTo(invListaProductosCambiarPrecioCantidadTO.getStockCantidad5()) != 0) {

                    invProducto.setProPrecio1(invListaProductosCambiarPrecioCantidadTO.getStockPrecio1());
                    invProducto.setProPrecio2(invListaProductosCambiarPrecioCantidadTO.getStockPrecio2());
                    invProducto.setProPrecio3(invListaProductosCambiarPrecioCantidadTO.getStockPrecio3());
                    invProducto.setProPrecio4(invListaProductosCambiarPrecioCantidadTO.getStockPrecio4());
                    invProducto.setProPrecio5(invListaProductosCambiarPrecioCantidadTO.getStockPrecio5());

                    invProducto.setProCantidad1(invListaProductosCambiarPrecioCantidadTO.getStockCantidad1());
                    invProducto.setProCantidad2(invListaProductosCambiarPrecioCantidadTO.getStockCantidad2());
                    invProducto.setProCantidad3(invListaProductosCambiarPrecioCantidadTO.getStockCantidad3());
                    invProducto.setProCantidad4(invListaProductosCambiarPrecioCantidadTO.getStockCantidad4());
                    invProducto.setProCantidad5(invListaProductosCambiarPrecioCantidadTO.getStockCantidad5());
                    invProductos.add(invProducto);
                    susClave = invProducto.getInvProductoPK().getProCodigoPrincipal();
                    susDetalle = "Se actualiza el precio: " + invProducto.getProNombre();
                    susSuceso = "UPDATE";
                    susTabla = "inventario.inv_producto";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    sisSucesos.add(sisSuceso);
                }
            } else {
                mensajeClase.add("El producto " + invListaProductosCambiarPrecioCantidadTO.getProNombre()
                        + " no se encuentra disponible.");
            }
        }

        if (mensajeClase.isEmpty()) {
            if (productoDao.invCambiarPrecioProductos(invProductos, sisSucesos)) {
                mensajeTO.setMensaje(
                        "T<html>Los precios de los Productos fueron actualizados correctamente...</html>");
            } else {
                mensajeTO.setMensaje(
                        "F<html>Hubo un error al actualizar los precios de los productos...<br>Contacte con el administrador o intentelo de nuevo</html>");
            }
        } else {
            mensajeTO.setMensaje("F<html>Hubo un error al actualizar los precios de los productos...</html>");
            mensajeTO.setListaErrores1(mensajeClase);
        }
        return mensajeTO;
    }

    @Override
    public List<InvProductoSincronizarTO> invProductoSincronizar(String empresaOrigen, String empresaDestino,
            String usuario, SisInfoTO sisInfoTO) throws Exception {
        List<InvProductoSincronizarTO> invProductoSincronizarTOs = productoDao.invProductoSincronizar(empresaOrigen,
                empresaDestino);
        return invProductoSincronizarTOs;
    }

    @Override
    public List<InvListaProductosCompraSustentoConceptoTO> getInvProductoSustentoConcepto(String empresa, String fechaCompra,
            List<InvListaProductosCompraSustentoConceptoTO> invListaProductosCompraTOs) throws Exception {
        InvProducto invProducto = null;
        List<InvListaProductosCompraSustentoConceptoTO> listaSumaSubtotalesSustento = new ArrayList();
        List<InvListaProductosCompraSustentoConceptoTO> listaSumaSubtotalesConcepto = new ArrayList();
        List<InvListaProductosCompraSustentoConceptoTO> listaSustentoConcepto = new ArrayList();
        InvListaProductosCompraSustentoConceptoTO sumaSubtotalesSustento;
        InvListaProductosCompraSustentoConceptoTO sumaSubtotalesConcepto;
        AnxConceptoTO anxConceptoTO = new AnxConceptoTO();
        String sustentoCodigo = "";
        String auxAustentoCodigo = "";
        String conceptoCodigo = "";
        String auxConceptoCodigo = "";
        BigDecimal acumSubtotalSustentoNull = cero;
        BigDecimal acumBaseImponibleConceptoNull = cero;
        BigDecimal acumBase0ConceptoNull = cero;
        for (InvListaProductosCompraSustentoConceptoTO invListaProductosCompraTO : invListaProductosCompraTOs) {
            invProducto = productoDao.buscarInvProducto(empresa, invListaProductosCompraTO.getProCodigoPrincipal());
            invListaProductosCompraTO.setProCreditoTributario(invProducto.getProCreditoTributario());
            BigDecimal acumSubtotalSustento = cero;
            BigDecimal acumBaseImponible = cero;
            BigDecimal acumBase0 = cero;
            boolean banderaSustento = false;
            boolean banderaConcepto = false;
            //for
            for (InvListaProductosCompraSustentoConceptoTO invListaProductosCompraTOInterno : invListaProductosCompraTOs) {
                InvProducto invProductoAux = productoDao.buscarInvProducto(empresa, invListaProductosCompraTOInterno.getProCodigoPrincipal().trim());
                ///// SACAR EL CODIGO DE LOS SUSTENTOS, SUMA LOS TOTALES
                if (invProductoAux.getSusCodigo() != null && invProducto.getSusCodigo() != null) {
                    if (invProducto.getSusCodigo().equals(invProductoAux.getSusCodigo()) && !invProducto.getSusCodigo().equals(auxAustentoCodigo)) {
                        acumSubtotalSustento = acumSubtotalSustento.add(UtilsValidacion.redondeoDecimalBigDecimal(invListaProductosCompraTOInterno.getSubtotal(), 15, java.math.RoundingMode.HALF_UP));
                        sustentoCodigo = invProductoAux.getSusCodigo();
                        banderaSustento = true;
                    }
                }
                if (invProductoAux.getConCodigo() != null && invProducto.getConCodigo() != null) {
                    if (invProducto.getConCodigo().equals(invProductoAux.getConCodigo()) && !invProducto.getConCodigo().equals(auxConceptoCodigo)) {
                        conceptoCodigo = invProductoAux.getConCodigo();
                        banderaConcepto = true;
                        if (invProductoAux.getProIva().equals("GRAVA")) {
                            acumBaseImponible = acumBaseImponible.add(UtilsValidacion.redondeoDecimalBigDecimal(
                                    invListaProductosCompraTOInterno.getSubtotal(), 15,
                                    java.math.RoundingMode.HALF_UP));
                            // acumBaseImponible =
                            // acumBaseImponible.add(validaciones.UtilsValidacion.redondeoDecimalBigDecimal(invListaProductosCompraTOInterno.getSubtotal()));
                        } else {
                            acumBase0 = acumBase0.add(UtilsValidacion.redondeoDecimalBigDecimal(
                                    invListaProductosCompraTOInterno.getSubtotal(), 15,
                                    java.math.RoundingMode.HALF_UP));
                            // acumBase0 =
                            // acumBase0.add(validaciones.UtilsValidacion.redondeoDecimalBigDecimal(invListaProductosCompraTOInterno.getSubtotal()));
                        }
                    }
                }
            }
            //end for
            if (invProducto.getSusCodigo() == null) {
                acumSubtotalSustentoNull = acumSubtotalSustentoNull.add(UtilsValidacion.redondeoDecimalBigDecimal(
                        invListaProductosCompraTO.getSubtotal(), 15, java.math.RoundingMode.HALF_UP));
            }
            if (invProducto.getConCodigo() == null) {
                if (invProducto.getProIva().equals("GRAVA")) {
                    acumBaseImponibleConceptoNull = acumBaseImponibleConceptoNull
                            .add(UtilsValidacion.redondeoDecimalBigDecimal(invListaProductosCompraTO.getSubtotal(), 15,
                                    java.math.RoundingMode.HALF_UP));
                } else {
                    acumBase0ConceptoNull = acumBase0ConceptoNull.add(UtilsValidacion.redondeoDecimalBigDecimal(
                            invListaProductosCompraTO.getSubtotal(), 15, java.math.RoundingMode.HALF_UP));
                }
            }
            if (banderaSustento) {
                sumaSubtotalesSustento = new InvListaProductosCompraSustentoConceptoTO();
                sumaSubtotalesSustento.setSusCodigo(sustentoCodigo);
                sumaSubtotalesSustento.setConCodigo("-");
                sumaSubtotalesSustento.setSubtotal(UtilsValidacion.redondeoDecimalBigDecimal(acumSubtotalSustento, 6, java.math.RoundingMode.HALF_UP));
                sumaSubtotalesSustento.setBaseImponible(cero);
                sumaSubtotalesSustento.setBase0(cero);
                sumaSubtotalesSustento.setProCreditoTributario(invProducto.getProCreditoTributario());
                sumaSubtotalesSustento.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
                listaSumaSubtotalesSustento.add(sumaSubtotalesSustento);
                auxAustentoCodigo = sustentoCodigo;
            }
            if (banderaConcepto) {
                sumaSubtotalesConcepto = new InvListaProductosCompraSustentoConceptoTO();
                sumaSubtotalesConcepto.setSusCodigo("-");
                sumaSubtotalesConcepto.setConCodigo(conceptoCodigo);
                sumaSubtotalesConcepto.setBaseImponible(UtilsValidacion.redondeoDecimalBigDecimal(acumBaseImponible, 6, java.math.RoundingMode.HALF_UP));
                sumaSubtotalesConcepto.setBase0(UtilsValidacion.redondeoDecimalBigDecimal(acumBase0, 6, java.math.RoundingMode.HALF_UP));
                sumaSubtotalesConcepto.setProCreditoTributario(invProducto.getProCreditoTributario());
                sumaSubtotalesConcepto.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
                listaSumaSubtotalesConcepto.add(sumaSubtotalesConcepto);
                auxConceptoCodigo = conceptoCodigo;
            }
        }
        BigDecimal mayor = cero;
        if (acumSubtotalSustentoNull.compareTo(cero) == 1) {
            sumaSubtotalesSustento = new InvListaProductosCompraSustentoConceptoTO();
            sumaSubtotalesSustento.setProCreditoTributario(invProducto.getProCreditoTributario());
            sumaSubtotalesSustento.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
            sumaSubtotalesSustento.setSusCodigo(null);
            sumaSubtotalesSustento.setConCodigo("-");
            sumaSubtotalesSustento.setBaseImponible(cero);
            sumaSubtotalesSustento.setBase0(cero);
            sumaSubtotalesSustento.setSubtotal(UtilsValidacion.redondeoDecimalBigDecimal(acumSubtotalSustentoNull, 6,
                    java.math.RoundingMode.HALF_UP));
            listaSumaSubtotalesSustento.add(sumaSubtotalesSustento);
        }
        if (acumBaseImponibleConceptoNull.compareTo(cero) == 1 || acumBase0ConceptoNull.compareTo(cero) == 1) {
            sumaSubtotalesConcepto = new InvListaProductosCompraSustentoConceptoTO();
            sumaSubtotalesConcepto.setProCreditoTributario(invProducto.getProCreditoTributario());
            sumaSubtotalesConcepto.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
            sumaSubtotalesConcepto.setSusCodigo("-");
            sumaSubtotalesConcepto.setConCodigo(null);
            sumaSubtotalesConcepto.setBaseImponible(UtilsValidacion.redondeoDecimalBigDecimal(acumBaseImponibleConceptoNull, 6, java.math.RoundingMode.HALF_UP));
            sumaSubtotalesConcepto.setBase0(UtilsValidacion.redondeoDecimalBigDecimal(acumBase0ConceptoNull, 6, java.math.RoundingMode.HALF_UP));
            listaSumaSubtotalesConcepto.add(sumaSubtotalesConcepto);
        }
        for (InvListaProductosCompraSustentoConceptoTO invListaProductosCompraTO : listaSumaSubtotalesSustento) {
            if (invListaProductosCompraTO.getSubtotal().compareTo(mayor) > -1) {
                mayor = invListaProductosCompraTO.getSubtotal();
                sustentoCodigo = invListaProductosCompraTO.getSusCodigo();
            }
        }
        sumaSubtotalesSustento = new InvListaProductosCompraSustentoConceptoTO();
        if (invProducto != null) {
            sumaSubtotalesSustento.setProCreditoTributario(invProducto.getProCreditoTributario());
            sumaSubtotalesSustento.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
        }
        sumaSubtotalesSustento.setSusCodigo(sustentoCodigo);
        sumaSubtotalesSustento.setConCodigo("-");
        sumaSubtotalesSustento.setSubtotal(mayor);
        listaSustentoConcepto.add(sumaSubtotalesSustento);

        for (InvListaProductosCompraSustentoConceptoTO invListaProductosCompraTO : listaSumaSubtotalesConcepto) {
            if (invListaProductosCompraTO.getConCodigo() != null && !invListaProductosCompraTO.getConCodigo().equals("-")) {
                invListaProductosCompraTO.setDetBaseNoObjetoIva(cero);
                anxConceptoTO = conceptoDao.getBuscarAnexoConceptoTO(UtilsValidacion.fechaSistema(), invListaProductosCompraTO.getConCodigo());
                if (anxConceptoTO != null) {
                    invListaProductosCompraTO.setDetPorcentaje(anxConceptoTO.getConPorcentaje());
                    invListaProductosCompraTO.setDetValorRetenido(
                            invListaProductosCompraTO.getBaseImponible().add(invListaProductosCompraTO.getBase0())
                                    .multiply(anxConceptoTO.getConPorcentaje().divide(new java.math.BigDecimal("100"))));
                }
            }
            listaSustentoConcepto.add(invListaProductosCompraTO);
        }
        return listaSustentoConcepto;
    }

    @Override
    public boolean eliminarInvProducto(InvProductoPK pk, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        InvProducto invProducto = productoCriteriaDao.obtener(InvProducto.class, pk);
        if (invProducto != null) {
            Boolean puedeEliminar = productoDao.getPuedeEliminarProducto(pk.getProEmpresa(), pk.getProCodigoPrincipal());
            if (puedeEliminar) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = pk.getProCodigoPrincipal();
                susDetalle = "Se eliminó el producto " + invProducto.getProNombre() + " con código " + susClave;
                susSuceso = "DELETE";
                susTabla = "inventario.inv_producto";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                productoDao.eliminarInvProducto(invProducto, sisSuceso);
                return true;
            } else {
                throw new GeneralException("No se puede eliminar este producto debido a que tiene movimientos en Inventarios.\nIntente con otro...");
            }
        } else {
            throw new GeneralException("El Producto que va a eliminar ya no está disponible...\nIntente con otro...");
        }
    }

    @Override
    public boolean modificarEstadoInvProducto(InvProductoPK pk, boolean estado, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        InvProducto invProducto = productoCriteriaDao.obtener(InvProducto.class, pk);
        if (invProducto != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = pk.getProCodigoPrincipal();
            susDetalle = "Se modificó el estado del producto " + invProducto.getProNombre() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ACTUALIZAR ESTADO
            invProducto.setProInactivo(estado);
            productoDao.modificarInvProducto(invProducto, sisSuceso);
            return true;
        } else {
            throw new GeneralException("El producto ya no está disponible.");
        }
    }

    @Override
    public boolean activarEcommerceInvProducto(InvProductoPK pk, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        InvProducto invProducto = productoCriteriaDao.obtener(InvProducto.class, pk);
        if (invProducto != null) {
            AfActivos activoFijo = activoDao.getAfActivos(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal());
            if (activoFijo != null) {
                throw new GeneralException("El producto es de activo fijo. No se puede cambiar a ecommerce");
            }
            /// PREPARANDO OBJETO SISSUCESO
            susClave = pk.getProCodigoPrincipal();
            susDetalle = "El producto :" + invProducto.getProNombre() + ", con código " + susClave + " se modificó a producto ecommerce";
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_producto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ACTUALIZAR ESTADO
            invProducto.setProEcommerce(true);
            productoDao.modificarInvProducto(invProducto, sisSuceso);
            return true;
        } else {
            throw new GeneralException("El producto ya no está disponible.");
        }
    }

    @Override
    public List<InvProductoSimpleTO> listarProductos(String empresa, String busqueda,
            String categoria, boolean incluirInactivos, boolean limite) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(InvProducto.class);
        filtro.createAlias("invProductoCategoria", "cat");
        filtro.createAlias("invProductoMedida", "med");
        if (!incluirInactivos) {
            filtro.add(Restrictions.eq("proInactivo", Boolean.FALSE));
        }
        if (categoria != null && !categoria.equals("")) {
            filtro.add(Restrictions.eq("cat.invProductoCategoriaPK.catCodigo", categoria));
        }
        if (busqueda != null && !busqueda.equals("")) {
            filtro.add(Restrictions.or(
                    Restrictions.ilike("cat.invProductoCategoriaPK.catCodigo", '%' + busqueda + '%'),
                    Restrictions.ilike("proNombre", '%' + busqueda + '%'),
                    Restrictions.ilike("invProductoPK.proCodigoPrincipal", '%' + busqueda + '%')
            ));
        }
        filtro.add(Restrictions.eq("invProductoPK.proEmpresa", empresa));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("invProductoPK.proCodigoPrincipal"), "proCodigoPrincipal")
                .add(Projections.property("invProductoPK.proEmpresa"), "proEmpresa")
                .add(Projections.property("proNombre"), "proNombre")
                .add(Projections.property("proInactivo"), "proInactivo")
                .add(Projections.property("proCreditoTributario"), "proCreditoTributario")
                .add(Projections.property("proIva"), "proIva")
                .add(Projections.property("med.medDetalle"), "medDetalle")
                .add(Projections.property("med.invProductoMedidaPK.medEmpresa"), "medEmpresa")
                .add(Projections.property("med.invProductoMedidaPK.medCodigo"), "medCodigo")
                .add(Projections.property("proDetalle"), "proDetalle"));
        if (limite) {
            filtro.setMaxResults(1000);
        }
        filtro.addOrder(Order.asc("proNombre"));
        return productoCriteriaDao.proyeccionPorCriteria(filtro, InvProductoSimpleTO.class);
    }

    @Override
    public InvProductoEtiquetas traerEtiquetas(String empresa) throws Exception {
        return productoEtiquetasDao.obtener(InvProductoEtiquetas.class, empresa);
    }

    @Override
    public List<DatoFunListaProductosImpresionPlaca> convertirInvFunListaProductosImpresionPlacasTO_DatoFunListaProductosImpresionPlaca(List<InvFunListaProductosImpresionPlacasTO> listado) throws Exception {
        List<DatoFunListaProductosImpresionPlaca> listaFinal = new ArrayList<>();

        for (InvFunListaProductosImpresionPlacasTO item : listado) {
            DatoFunListaProductosImpresionPlaca datoFunListaProductosImpresionPlaca = new DatoFunListaProductosImpresionPlaca();
            datoFunListaProductosImpresionPlaca.setLpspCodigoBarra(item.getLpspCodigoBarra());
            datoFunListaProductosImpresionPlaca.setLpspCodigoPrincipal(item.getLpspCodigoPrincipal());
            datoFunListaProductosImpresionPlaca.setLpspNombre(item.getLpspNombre());
            datoFunListaProductosImpresionPlaca.setLpspPrecio1(item.getLpspPrecio1());
            datoFunListaProductosImpresionPlaca.setLpspPrecio2(item.getLpspPrecio2());
            datoFunListaProductosImpresionPlaca.setLpspPrecio3(item.getLpspPrecio3());
            datoFunListaProductosImpresionPlaca.setProEmpaque(item.getProEmpaque());
            datoFunListaProductosImpresionPlaca.setEstado(true);
            listaFinal.add(datoFunListaProductosImpresionPlaca);
        }
        return listaFinal;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudProductos(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        String fechaFactura = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFactura"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        boolean mostrarImagenes = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarImagenes"));
        boolean isActivoFijo = UtilsJSON.jsonToObjeto(boolean.class, map.get("isActivoFijo"));
        String rucEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("rucEmpresa"));

        List<InvProductoTipoComboTO> listadoTipos = new ArrayList<>();
        if (isActivoFijo) {
            listadoTipos = productoTipoDao.getInvProductoTipoComboListadoTOActivoFijo(empresa, accion);
        } else {
            listadoTipos = productoTipoDao.getInvProductoTipoComboListadoTO(empresa, accion);
        }

        List<InvProductoRelacionadoTO> listadoProductosRelacionadosTO = new ArrayList<>();
        List<InvProductoRelacionados> listadoProductosRelacionados = new ArrayList<>();
        List<InvProductoFormulaTO> listadoInvProductoFormulaTO = new ArrayList<>();
        List<InvProductoFormula> listadoInvProductoFormula = new ArrayList<>();
        List<InvProductoMarcaComboListadoTO> listadoMarcas = productoMarcaDao.getInvMarcaComboListadoTO(empresa);
        List<AnxSustentoComboTO> listadoSustentos = sustentoDao.getListaAnxSustentoComboTO(null);
        List<InvMedidaComboTO> listadoMedidas = productoMedidaDao.getListaInvMedidaTablaTO(empresa);
        List<InvProductoPresentacionUnidadesComboListadoTO> listadoPresentacionesMedida = productoPresentacionUnidadesDao.getListaPresentacionUnidadComboTO(empresa);
        List<AnxConceptoComboTO> listadoConceptos = conceptoDao.getListaAnxConceptoTO();
        List<InvProductoPresentacionCajasComboListadoTO> listadoPresentacionesCaja = productoPresentacionCajasDao.getListaPresentacionCajaComboTO(empresa);
        List<ConEstructuraTO> listadoEstructura = estructuraDao.getListaConEstructuraTO(empresa);
        InvProductoEtiquetas invProductoEtiquetas = productoEtiquetasDao.obtener(InvProductoEtiquetas.class, empresa);
        List<InvProductoCategoriaTO> categorias = productoCategoriaDao.getInvProductoCategoriaTO(empresa);
        List<AnxPorcentajeIce> porcentajes = anexoPorcentajeIceService.listarAnexoPorcentajeIce();
        BigDecimal iva = porcentajeIvaService.getValorAnxPorcentajeIvaTO(fechaFactura);
        InvProductoTO invProductoTO = null;
        AfActivoTO activoFijoTO = null;
        boolean puedeEliminar = false;
        boolean tieneDepreciacion = false;
        List<InvAdjuntosProductosWebTO> listadoImagenes = new ArrayList<>();
        List<ConCuentasTO> listaProCuentaInventario = new ArrayList<>();
        List<ConCuentasTO> listaProCuentaVenta = new ArrayList<>();
        List<ConCuentasTO> listaProCuentaCostoAutomatico = new ArrayList<>();
        List<ConCuentasTO> listaProCuentaCostoVenta = new ArrayList<>();
        List<ConCuentasTO> listaProCuentaVentaLocal = new ArrayList<>();
        List<ConCuentasTO> listaProCuentaVentaExterior = new ArrayList<>();
        List<ConCuentasTO> listaProCuentaTransferenciaIpp = new ArrayList();

        if (codigo != null && !codigo.equals("")) {
            invProductoTO = productoDao.obtenerProductoTO(empresa, codigo);
            if (invProductoTO != null) {
                puedeEliminar = productoDao.getPuedeEliminarProducto(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal());
                String proCuentaVenta = invProductoTO.getProCuentaVenta();
                String proCuentaInventario = invProductoTO.getProCuentaInventario();
                String proCuentaCostoAutomatico = invProductoTO.getProCuentaCostoAutomatico();
                String proCuentaCostoVenta = invProductoTO.getProCuentaGasto();
                String proCuentaVentaExterior = invProductoTO.getProCuentaVentaExterior();
                String proCuentaTransferenciaIpp = invProductoTO.getProCuentaTransferenciaIpp();

                if (proCuentaInventario != null) {
                    listaProCuentaInventario = cuentasService.getListaBuscarConCuentasTO(empresa, proCuentaInventario, null, false);
                }
                if (proCuentaVenta != null) {
                    listaProCuentaVenta = cuentasService.getListaBuscarConCuentasTO(empresa, proCuentaVenta, null, false);
                }
                if (proCuentaVentaExterior != null) {
                    listaProCuentaVentaExterior = cuentasService.getListaBuscarConCuentasTO(empresa, proCuentaVentaExterior, null, false);
                }
                if (proCuentaCostoAutomatico != null) {
                    listaProCuentaCostoAutomatico = cuentasService.getListaBuscarConCuentasTO(empresa, proCuentaCostoAutomatico, null, false);
                }
                if (proCuentaCostoVenta != null) {
                    listaProCuentaCostoVenta = cuentasService.getListaBuscarConCuentasTO(empresa, proCuentaCostoVenta, null, false);
                }
                if(proCuentaTransferenciaIpp != null){
                    listaProCuentaTransferenciaIpp = cuentasService.getListaBuscarConCuentasTO(empresa, proCuentaTransferenciaIpp, null, false);
                }

                AfActivos activoFijo = activoDao.getAfActivos(empresa, invProductoTO.getProCodigoPrincipal());
                if (activoFijo != null) {
                    activoFijoTO = ConversionesActivoFijo.convertirAfActivos_AfActivoTO(activoFijo);
                }
                tieneDepreciacion = depreciacionActivoFijoService.productoSeEncuentraDepreciacion(empresa, invProductoTO.getProCodigoPrincipal());

                /*si es ecommerce*/
                if (invProductoTO.isProEcommerce()) {
                    listadoProductosRelacionados = productoRelacionadoService.getListInvProductoRelacionados(empresa, invProductoTO.getProCodigoPrincipal());
                    if (listadoProductosRelacionados != null && listadoProductosRelacionados.size() > 0) {
                        for (int k = 0; k < listadoProductosRelacionados.size(); k++) {
                            InvProductoRelacionadoTO prodRelacionadoTO = new InvProductoRelacionadoTO();
                            prodRelacionadoTO.setProSecuencial(listadoProductosRelacionados.get(k).getPrSecuencial());
                            prodRelacionadoTO.setProCodigoPrincipal(listadoProductosRelacionados.get(k).getInvProducto().getInvProductoPK().getProCodigoPrincipal());
                            prodRelacionadoTO.setProEmpresa(listadoProductosRelacionados.get(k).getInvProducto().getInvProductoPK().getProEmpresa());

                            prodRelacionadoTO.setProRelacionadoCodigoPrincipal(listadoProductosRelacionados.get(k).getInvProductoRelacionado().getInvProductoPK().getProCodigoPrincipal());
                            prodRelacionadoTO.setProRelacionadoCodigoPrincipalCopia(listadoProductosRelacionados.get(k).getInvProductoRelacionado().getInvProductoPK().getProCodigoPrincipal());
                            prodRelacionadoTO.setProRelacionadoEmpresa(listadoProductosRelacionados.get(k).getInvProductoRelacionado().getInvProductoPK().getProEmpresa());
                            prodRelacionadoTO.setProRelacionadoNombre(listadoProductosRelacionados.get(k).getInvProductoRelacionado().getProNombre());

                            listadoProductosRelacionadosTO.add(prodRelacionadoTO);
                        }
                    }
                }
                /*PRODUCTO FORMULA*/
                listadoInvProductoFormula = productoFormulaService.getListInvProductoFormula(empresa, invProductoTO.getProCodigoPrincipal());
                if (listadoInvProductoFormula != null && listadoInvProductoFormula.size() > 0) {
                    for (int k = 0; k < listadoInvProductoFormula.size(); k++) {
                        InvProductoFormulaTO prodFormulaTO = new InvProductoFormulaTO();
                        prodFormulaTO.setProSecuencial(listadoInvProductoFormula.get(k).getPrSecuencial());
                        prodFormulaTO.setProCodigoPrincipal(listadoInvProductoFormula.get(k).getInvProducto().getInvProductoPK().getProCodigoPrincipal());
                        prodFormulaTO.setProEmpresa(listadoInvProductoFormula.get(k).getInvProducto().getInvProductoPK().getProEmpresa());

                        prodFormulaTO.setProFormulaCodigoPrincipal(listadoInvProductoFormula.get(k).getInvProductoFormula().getInvProductoPK().getProCodigoPrincipal());
                        prodFormulaTO.setProFormulaCodigoPrincipalCopia(listadoInvProductoFormula.get(k).getInvProductoFormula().getInvProductoPK().getProCodigoPrincipal());
                        prodFormulaTO.setProFormulaEmpresa(listadoInvProductoFormula.get(k).getInvProductoFormula().getInvProductoPK().getProEmpresa());
                        prodFormulaTO.setProFormulaNombre(listadoInvProductoFormula.get(k).getInvProductoFormula().getProNombre());
                        prodFormulaTO.setProCantidad(listadoInvProductoFormula.get(k).getPrCantidad());
                        prodFormulaTO.setProFormulaMedidaDetalle(listadoInvProductoFormula.get(k).getInvProductoFormula().getInvProductoMedida().getMedDetalle());
                        listadoInvProductoFormulaTO.add(prodFormulaTO);
                    }
                }
            }
            if (mostrarImagenes) {
                InvProductoPK invProductoPK = new InvProductoPK();
                invProductoPK.setProCodigoPrincipal(codigo);
                invProductoPK.setProEmpresa(empresa);
                listadoImagenes = convertirStringUTF8(invProductoPK);
            }

        } else {
            /*es nuevo*/
            if (rucEmpresa != null && rucEmpresa.equals("0704624758001")) {
                invProductoTO = new InvProductoTO();
                invProductoTO.setProCuentaInventario("1010301001");
                invProductoTO.setProCuentaVenta("4010101001");
                invProductoTO.setProCuentaGasto("5010100001");

                if (invProductoTO.getProCuentaInventario() != null) {
                    listaProCuentaInventario = cuentasService.getListaBuscarConCuentasTO(empresa, invProductoTO.getProCuentaInventario(), null, false);
                }
                if (invProductoTO.getProCuentaVenta() != null) {
                    listaProCuentaVenta = cuentasService.getListaBuscarConCuentasTO(empresa, invProductoTO.getProCuentaVenta(), null, false);
                }
                if (invProductoTO.getProCuentaGasto() != null) {
                    listaProCuentaCostoVenta = cuentasService.getListaBuscarConCuentasTO(empresa, invProductoTO.getProCuentaGasto(), null, false);
                }
            }
        }

        List<AfUbicacionesTO> listaUbicaciones = ubicacionActivoFijoService.getListaAfUbicaciones(empresa);
        List<AfCategoriasTO> listaCategoriasAF = categoriaActivoFijoService.getListaAfCategorias(empresa, false);
        /*credito tributario*/
        List<ComboGenericoTO> opcionesCreditoTributario = new ArrayList<>();
        opcionesCreditoTributario.add(new ComboGenericoTO("NO APLICA", "NO APLICA"));
        opcionesCreditoTributario.add(new ComboGenericoTO("DEVOLUCION DE IVA", "DEVOLUCION DE IVA"));
        opcionesCreditoTributario.add(new ComboGenericoTO("IVA CARGADO AL GASTO", "IVA CARGADO AL GASTO"));
        /*credito tributario*/
        List<ComboGenericoTO> incluirWorshop = new ArrayList<>();
        incluirWorshop.add(new ComboGenericoTO("-1", "NUNCA"));
        incluirWorshop.add(new ComboGenericoTO("0", "FUERA DE STOCK"));
        incluirWorshop.add(new ComboGenericoTO("1", "SIEMPRE"));

        /*Si existe una lista de estructura, sumar sus grupos y obtener el tamanio*/
        if (listadoEstructura != null && !listadoEstructura.isEmpty()) {
            Integer tamanioEstructura = listadoEstructura.get(0).getEstGrupo1() + listadoEstructura.get(0).getEstGrupo2() + listadoEstructura.get(0).getEstGrupo3() + listadoEstructura.get(0).getEstGrupo4() + listadoEstructura.get(0).getEstGrupo5() + listadoEstructura.get(0).getEstGrupo6();
            campos.put("tamanioEstructura", tamanioEstructura);
        }

        campos.put("incluirWorshop", incluirWorshop);
        campos.put("listaProCuentaInventario", listaProCuentaInventario);
        campos.put("listaProCuentaVenta", listaProCuentaVenta);
        campos.put("listaProCuentaVentaLocal", listaProCuentaVentaLocal);
        campos.put("listaProCuentaVentaExterior", listaProCuentaVentaExterior);
        campos.put("listaProCuentaCostoAutomatico", listaProCuentaCostoAutomatico);
        campos.put("listaProCuentaCostoVenta", listaProCuentaCostoVenta);
        campos.put("listaProCuentaTransferenciaIpp", listaProCuentaTransferenciaIpp);
        campos.put("listadoProductosRelacionadosTO", listadoProductosRelacionadosTO);
        campos.put("listadoInvProductoFormulaTO", listadoInvProductoFormulaTO);
        campos.put("invProductoTO", invProductoTO);
        campos.put("puedeEliminar", puedeEliminar);
        campos.put("listadoImagenes", listadoImagenes);
        campos.put("listadoTipos", listadoTipos);
        campos.put("listadoMarcas", listadoMarcas);
        campos.put("listadoSustentos", listadoSustentos);
        campos.put("listadoMedidas", listadoMedidas);
        campos.put("listadoPresentacionesMedida", listadoPresentacionesMedida);
        campos.put("listadoConceptos", listadoConceptos);
        campos.put("invProductoEtiquetas", invProductoEtiquetas);
        campos.put("listadoPresentacionesCaja", listadoPresentacionesCaja);
        campos.put("iva", iva);
        campos.put("categorias", categorias);
        campos.put("tieneDepreciacion", tieneDepreciacion);
        campos.put("fechaActual", sistemaWebServicio.getFechaActual());
        campos.put("listaUbicaciones", listaUbicaciones);
        campos.put("listaCategoriasAF", listaCategoriasAF);
        campos.put("activoFijoTO", activoFijoTO);
        campos.put("opcionesCreditoTributario", opcionesCreditoTributario);
        campos.put("porcentajes", porcentajes);
        return campos;
    }

    /*Imagenes*/
    @Override
    public List<InvAdjuntosProductosWebTO> convertirStringUTF8(InvProductoPK invProductoPK
    ) {
        List<InvProductoDatosAdjuntos> listadoAdjuntos = null;
        List<InvAdjuntosProductosWebTO> listaRespuesta = new ArrayList<InvAdjuntosProductosWebTO>();
        try {
            listadoAdjuntos = productoDao.getAdjuntosProducto(invProductoPK);
            for (InvProductoDatosAdjuntos invAdjuntoProducto : listadoAdjuntos) {
                InvAdjuntosProductosWebTO invAdjuntosProductosWebTO = new InvAdjuntosProductosWebTO();
                invAdjuntosProductosWebTO.setAdjSecuencial(invAdjuntoProducto.getAdjSecuencial());
                invAdjuntosProductosWebTO.setInvProducto(invAdjuntoProducto.getInvProducto());
                invAdjuntosProductosWebTO.setAdjBucket(invAdjuntoProducto.getAdjBucket());
                invAdjuntosProductosWebTO.setAdjVerificado(invAdjuntoProducto.isAdjVerificado());
                invAdjuntosProductosWebTO.setAdjClaveArchivo(invAdjuntoProducto.getAdjClaveArchivo());
                invAdjuntosProductosWebTO.setAdjUrlArchivo(invAdjuntoProducto.getAdjUrlArchivo());
                listaRespuesta.add(invAdjuntosProductosWebTO);
            }
        } catch (Exception ex) {
            Logger.getLogger(ComprasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaRespuesta;
    }

    @Override
    public InvFunListadoProductosTO obtenerInvFunListadoProductosTO(String empresa, String codigo) throws Exception {
        return productoDao.obtenerInvFunListadoProductosTO(empresa, codigo);
    }

    private void verificarCuentaCostoAutomatica(InvProductoTO invProductoTO, SisInfoTO sisInfoTO) throws GeneralException {
        boolean seCambiCodigo = eliminarCostoAutomatico(invProductoTO, sisInfoTO);
        if (seCambiCodigo && invProductoTO != null && invProductoTO.getProCuentaCostoAutomatico() != null && !invProductoTO.getProCuentaCostoAutomatico().equals("")) {
            InvProductoPK pk = new InvProductoPK(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal());
            if (invProductoTO.getProCuentaInventario() == null || invProductoTO.getProCuentaInventario().equals("")) {
                Criterio filtro;
                filtro = Criterio.forClass(InvProductoDetalleCuentaContable.class);
                filtro.createAlias("invProducto", "producto");
                filtro.add(Restrictions.eq("detCuenta", invProductoTO.getProCuentaCostoAutomatico()));
                filtro.add(Restrictions.eq("producto.invProductoPK.proEmpresa", pk.getProEmpresa()));
                List<InvProductoDetalleCuentaContable> listado = productoDetalleCuentaContableDao.buscarPorCriteriaSinProyecciones(filtro);
                if (listado == null || listado.isEmpty()) {
                    InvProductoDetalleCuentaContable cuentaCostoAutomatico = new InvProductoDetalleCuentaContable();
                    cuentaCostoAutomatico.setDetCuenta(invProductoTO.getProCuentaCostoAutomatico());
                    cuentaCostoAutomatico.setInvProducto(new InvProducto(pk));
                    /// PREPARANDO OBJETO SISSUCESO
                    susClave = cuentaCostoAutomatico.getDetCuenta();
                    susDetalle = "Se insertó el productoDetalleCuentaContable " + cuentaCostoAutomatico.getDetCuenta() + " con código " + cuentaCostoAutomatico.getDetSecuencial();
                    susSuceso = "UPDATE";
                    susTabla = "inventario.inv_producto_detalle_cuenta_contable";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sucesoDao.insertar(sisSuceso);
                    productoDetalleCuentaContableDao.insertar(cuentaCostoAutomatico);
                } else {
                    filtro.add(Restrictions.eq("producto.invProductoPK.proCodigoPrincipal", pk.getProCodigoPrincipal()));
                    List<InvProductoDetalleCuentaContable> listadoDeCuentasDelProducto = productoDetalleCuentaContableDao.buscarPorCriteriaSinProyecciones(filtro);
                    if (listadoDeCuentasDelProducto == null || listadoDeCuentasDelProducto.isEmpty()) {
                        throw new GeneralException("La cuenta de costo automático ya se encuentra asociado a otro producto.");
                    }
                }
            } else {
                throw new GeneralException("La cuenta inventario no debe estar presente si se desea agregar una cuenta de costo automático.");
            }
        }
    }

    private boolean eliminarCostoAutomatico(InvProductoTO invProductoTO, SisInfoTO sisInfoTO) {
        boolean seCambioCodigo;
        Criterio filtro;
        filtro = Criterio.forClass(InvProductoDetalleCuentaContable.class);
        filtro.createAlias("invProducto", "producto");
        filtro.add(Restrictions.eq("producto.invProductoPK.proEmpresa", invProductoTO.getProEmpresa()));
        filtro.add(Restrictions.eq("producto.invProductoPK.proCodigoPrincipal", invProductoTO.getProCodigoPrincipal()));
        InvProductoDetalleCuentaContable rpta = productoDetalleCuentaContableDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
        if (rpta == null) {
            return true;
        }
        if (!rpta.getDetCuenta().equals(invProductoTO.getProCuentaCostoAutomatico())) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = rpta.getInvProducto().getInvProductoPK().getProCodigoPrincipal();
            susDetalle = "Se eliminó el productoDetalleCuentaContable " + rpta.getDetCuenta() + " con código " + susClave;
            susSuceso = "DELETE";
            susTabla = "inventario.inv_producto_detalle_cuenta_contable";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sucesoDao.insertar(sisSuceso);
            productoDetalleCuentaContableDao.eliminar(rpta);
            seCambioCodigo = true;
        } else {
            seCambioCodigo = false;
        }
        return seCambioCodigo;
    }

    @Override
    public List<InvVentasDetalleProductoTO> getListadoVentasDetalleProducto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception {
        return productoDao.obtenerListadoVentasDetalleProducto(empresa, fechaDesde, fechaHasta, sector, piscina, cliente, bodega);
    }

    @Override
    public List<InvVentasDetalleProductoTO> getListadoVentasDetalleProductoAgrupadoCliente(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception {
        return productoDao.obtenerListadoVentasDetalleProductoAgrupadoCliente(empresa, fechaDesde, fechaHasta, sector, piscina, cliente, bodega);
    }

    @Override
    public List<InvVentasDetalleProductoTO> getListadoVentasDetalleProductoAgrupadoCC(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception {
        return productoDao.obtenerListadoVentasDetalleProductoAgrupadoCC(empresa, fechaDesde, fechaHasta, sector, piscina, cliente, bodega);
    }

    @Override
    public List<InvComprasDetalleProductoTO> getListadoComprasDetalleProducto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception {
        return productoDao.obtenerListadoComprasDetalleProducto(empresa, fechaDesde, fechaHasta, sector, piscina, bodega);
    }

    @Override
    public List<InvComprasDetalleProductoTO> getListadoComprasDetalleProductoAgrupadoProveedor(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception {
        return productoDao.obtenerListadoComprasDetalleProductoAgrupadoProveedor(empresa, fechaDesde, fechaHasta, sector, piscina, bodega);
    }

    @Override
    public List<InvComprasDetalleProductoTO> getListadoComprasDetalleProductoAgrupadoCentroCosto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception {
        return productoDao.obtenerListadoComprasDetalleProductoAgrupadoCentroCosto(empresa, fechaDesde, fechaHasta, sector, piscina, bodega);
    }

    @Override
    public List<InvComprasDetalleProductoTO> getListadoComprasDetalleProductoAgrupadoEquipoControl(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception {
        return productoDao.obtenerListadoComprasDetalleProductoAgrupadoEquipoControl(empresa, fechaDesde, fechaHasta, sector, piscina, bodega);
    }

    @Override
    public Map<String, Object> obtenerDatosParaProductosPrecio(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));

        InvProductoEtiquetas invProductoEtiquetas = productoEtiquetasDao.obtener(InvProductoEtiquetas.class, empresa);
        InvProductoTO invProductoTO = productoDao.obtenerProductoTO(empresa, codigo);
        BigDecimal iva = porcentajeIvaService.getValorAnxPorcentajeIvaTO(UtilsDate.fechaFormatoString(sistemaWebServicio.getFechaActual(), "yyyy-MM-dd"));
        campos.put("invProductoEtiquetas", invProductoEtiquetas);
        campos.put("invProductoTO", invProductoTO);
        campos.put("iva", iva);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaModificacionPrecios(String empresa) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        InvProductoEtiquetas invProductoEtiquetas = productoEtiquetasDao.obtener(InvProductoEtiquetas.class, empresa);
        List<InvListaBodegasTO> bodegas = bodegaDao.buscarBodegasTO(empresa, false, null);
        campos.put("invProductoEtiquetas", invProductoEtiquetas);
        campos.put("bodegas", bodegas);
        return campos;
    }

    @Override
    public List<InvProductoSinMovimientoTO> obtenerProductosSinMovimientos(String empresa) throws Exception {
        return productoDao.obtenerProductosSinMovimientos(empresa);
    }

    @Override
    public Map<String, Object> obtenerInventarioReporte(String empresa) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<InvProductoSinMovimientoTO> listadoProductos = obtenerProductosSinMovimientos(empresa);
        List<InvClienteSinMovimientoTO> listadoClientes = clienteDao.obtenerClientesSinMovimientos(empresa);
        List<InvProveedorSinMovimientoTO> listadoProveedores = proveedorDao.obtenerProveedoresSinMovimientos(empresa);
        campos.put("listadoProductos", listadoProductos);
        campos.put("listadoProveedores", listadoProveedores);
        campos.put("listadoClientes", listadoClientes);
        return campos;
    }

    @Override
    public Map<String, Object> verificarExistenciaEnProductos(String empresa, List<ImportarProductos> productosExcel) throws Exception {
        Map<String, Object> mapResultadoEnviar = null;
        List<String> listaMensajesEnviar = new ArrayList<>();
        List<ImportarProductos> listaProductos = new ArrayList<>();
        if (productosExcel != null && !productosExcel.isEmpty()) {
            for (int i = 0; i < productosExcel.size(); i++) {
                String codigo = productosExcel.get(i).getProducto().getInvProductoPK().getProCodigoPrincipal();
                InvProducto producto = null;
                boolean isActivoFijo = productosExcel.get(i).getActivoFijo() != null ? true : false;
                //creando productos
                producto = new InvProducto();
                producto.setInvProductoPK(new InvProductoPK(empresa, codigo));
                producto.setProNombre(productosExcel.get(i).getProducto().getProNombre().toUpperCase());
                producto.setProDetalle(productosExcel.get(i).getProducto().getProDetalle() != null ? productosExcel.get(i).getProducto().getProDetalle().toUpperCase() : null);
                // existe categoria
                String codigoCategoria = productosExcel.get(i).getProducto().getInvProductoCategoria() != null
                        ? productosExcel.get(i).getProducto().getInvProductoCategoria().getInvProductoCategoriaPK().getCatCodigo() : null;
                if (codigoCategoria != null) {
                    InvProductoCategoria categoria = productoCategoriaService.buscarInvProductoCategoria(empresa, codigoCategoria);
                    if (categoria == null) {
                        listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => La categoría: <strong class='pl-2'>"
                                + codigoCategoria
                                + " </strong> no existe.<br>");
                    } else {
                        producto.setInvProductoCategoria(categoria);
                        // existe marca
                        String codigoMarca = productosExcel.get(i).getProducto().getInvProductoMarca() != null
                                ? productosExcel.get(i).getProducto().getInvProductoMarca().getInvProductoMarcaPK().getMarCodigo() : null;
                        if (codigoMarca != null) {
                            InvProductoMarca marca = productoMarcaDao.buscarMarcaProducto(empresa, codigoMarca);
                            if (marca == null) {
                                listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => La marca de producto: <strong class='pl-2'>"
                                        + codigoMarca
                                        + " </strong> no existe.<br>");
                            } else {
                                producto.setInvProductoMarca(marca);
                                // existe tipo
                                String codigoTipo = productosExcel.get(i).getProducto().getInvProductoTipo() != null
                                        ? productosExcel.get(i).getProducto().getInvProductoTipo().getInvProductoTipoPK().getTipCodigo() : null;
                                if (codigoTipo != null) {
                                    InvProductoTipo tipo = productoTipoDao.buscarInvProductoTipo(empresa, codigoTipo);
                                    if (tipo == null) {
                                        listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => El tipo de producto: <strong class='pl-2'>"
                                                + codigoTipo
                                                + " </strong> no existe.<br>");
                                    } else {
                                        producto.setInvProductoTipo(tipo);
                                        //medida
                                        String codigoMedida = productosExcel.get(i).getProducto().getInvProductoMedida() != null
                                                ? productosExcel.get(i).getProducto().getInvProductoMedida().getInvProductoMedidaPK().getMedCodigo() : null;
                                        if (codigoMedida != null) {
                                            InvProductoMedida medida = productoMedidaDao.buscarProductoMedida(empresa, codigoMedida);
                                            if (medida == null) {
                                                listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => La unidad de medida de producto: <strong class='pl-2'>"
                                                        + codigoMedida
                                                        + " </strong> no existe.<br>");
                                            } else {
                                                producto.setInvProductoMedida(medida);
                                                //grava iva

                                                if (productosExcel.get(i).getProducto().getProIva() != null) {
                                                    productosExcel.get(i).getProducto().setProIva(productosExcel.get(i).getProducto().getProIva().toUpperCase());
                                                    if (productosExcel.get(i).getProducto().getProIva().equals("GRAVA")
                                                            || productosExcel.get(i).getProducto().getProIva().equals("NO GRAVA")
                                                            || productosExcel.get(i).getProducto().getProIva().equals("EXENTO")
                                                            || productosExcel.get(i).getProducto().getProIva().equals("NO OBJETO")) {
                                                        producto.setProIva(productosExcel.get(i).getProducto().getProIva());
                                                        //credito tributario
                                                        if (productosExcel.get(i).getProducto().getProCreditoTributario() != null) {
                                                            productosExcel.get(i).getProducto().setProCreditoTributario(productosExcel.get(i).getProducto().getProCreditoTributario().toUpperCase());
                                                            if (productosExcel.get(i).getProducto().getProCreditoTributario().equals("NO APLICA")
                                                                    || productosExcel.get(i).getProducto().getProCreditoTributario().equals("DEVOLUCION DE IVA")
                                                                    || productosExcel.get(i).getProducto().getProCreditoTributario().equals("IVA CARGADO AL GASTO")) {
                                                                producto.setProCreditoTributario(productosExcel.get(i).getProducto().getProCreditoTributario());

                                                                producto.setProCuentaInventario(productosExcel.get(i).getProducto().getProCuentaInventario());
                                                                producto.setProCuentaVenta(productosExcel.get(i).getProducto().getProCuentaVenta());
                                                                producto.setProCuentaCostoAutomatico(productosExcel.get(i).getProducto().getProCuentaCostoAutomatico());
                                                                producto.setProCuentaGasto(productosExcel.get(i).getProducto().getProCuentaGasto());
                                                                //validacion de cuentas
                                                                String cuentaInventario = productosExcel.get(i).getProducto().getProCuentaInventario() != null && !productosExcel.get(i).getProducto().getProCuentaInventario().equals("") ? productosExcel.get(i).getProducto().getProCuentaInventario() : null;
                                                                String cuentaVenta = productosExcel.get(i).getProducto().getProCuentaVenta() != null && !productosExcel.get(i).getProducto().getProCuentaVenta().equals("") ? productosExcel.get(i).getProducto().getProCuentaVenta() : null;
                                                                String cuentaCostoAutomatico = productosExcel.get(i).getProducto().getProCuentaCostoAutomatico() != null && !productosExcel.get(i).getProducto().getProCuentaCostoAutomatico().equals("") ? productosExcel.get(i).getProducto().getProCuentaCostoAutomatico() : null;
                                                                String cuentaGasto = productosExcel.get(i).getProducto().getProCuentaGasto() != null && !productosExcel.get(i).getProducto().getProCuentaGasto().equals("") ? productosExcel.get(i).getProducto().getProCuentaGasto() : null;
                                                                String cuentaVentaExterior = productosExcel.get(i).getProducto().getProCuentaVentaExterior() != null && !productosExcel.get(i).getProducto().getProCuentaVentaExterior().equals("") ? productosExcel.get(i).getProducto().getProCuentaVentaExterior() : null;
                                                                SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);

                                                                Map<String, Object> respuesCategorias = productoCategoriaService.validacionGrupoSegunCategoria(empresa, categoria.getInvProductoCategoriaPK().getCatCodigo(), cuentaInventario, cuentaVenta, cuentaCostoAutomatico, cuentaGasto, cuentaVentaExterior, empresaParametros.isIsExportadora(), isActivoFijo);
                                                                if (respuesCategorias != null) {
                                                                    boolean cuentaCompraValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaCompraValido"));
                                                                    boolean cuentaVentaValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaVentaValido"));
                                                                    boolean cuentaCostoAutomaticoValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaCostoAutomaticoValido"));
                                                                    boolean cuentaGastoValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaGastoValido"));
                                                                    boolean cuentaVentaExteriorValido = UtilsJSON.jsonToObjeto(boolean.class, respuesCategorias.get("cuentaVentaExteriorValido"));
                                                                    String mensajeCuentas = UtilsJSON.jsonToObjeto(String.class, respuesCategorias.get("mensaje"));

                                                                    if (mensajeCuentas != null) {
                                                                        if (mensajeCuentas.substring(0, 1).equals("T")) {
                                                                            if (cuentaCompraValido && cuentaVentaValido && cuentaCostoAutomaticoValido && cuentaGastoValido && cuentaVentaExteriorValido) {
                                                                                ImportarProductos productoImp = new ImportarProductos();
                                                                                productoImp.setProducto(producto);
                                                                                //validacion de activo fijo
                                                                                if (isActivoFijo) {
                                                                                    if (producto.getInvProductoTipo().getTipTipo().equals("ACTIVO FIJO")) {
                                                                                        AfActivos AF = productosExcel.get(i).getActivoFijo();

                                                                                        String ubicacionAfCodigo = AF.getAfUbicaciones() != null
                                                                                                ? AF.getAfUbicaciones().getAfUbicacionesPK().getUbiCodigo() : null;

                                                                                        String sectorAfCodigo = AF.getAfUbicaciones() != null
                                                                                                ? AF.getAfUbicaciones().getAfUbicacionesPK().getUbiSector() : null;

                                                                                        AfUbicaciones afUbicacionesLocal = ubicacionActivoFijoDao.getAfUbicaciones(empresa, sectorAfCodigo, ubicacionAfCodigo);
                                                                                        if (afUbicacionesLocal != null) {
                                                                                            AfActivos activo = new AfActivos();
                                                                                            activo.setAfUbicaciones(afUbicacionesLocal);
                                                                                            String categoriaAfCodigo = AF.getAfCategorias() != null
                                                                                                    ? AF.getAfCategorias().getAfCategoriasPK().getCatCodigo() : null;
                                                                                            if (categoriaAfCodigo != null) {
                                                                                                AfCategoriasTO categoriaAF = categoriaActivoFijoService.consultarAfCategoriasTO(empresa, categoriaAfCodigo);
                                                                                                if (categoriaAF != null) {
                                                                                                    AfCategorias catAF = new AfCategorias();
                                                                                                    catAF.setAfCategoriasPK(new AfCategoriasPK(empresa, categoriaAfCodigo));
                                                                                                    catAF.setCatDescripcion(categoriaAF.getCatDescripcion());
                                                                                                    activo.setAfFechaAdquisicion(AF.getAfFechaAdquisicion() != null ? AF.getAfFechaAdquisicion() : UtilsDate.timestamp());
                                                                                                    activo.setAfCategorias(catAF);
                                                                                                    activo.setAfValorResidual(AF.getAfValorResidual());
                                                                                                    activo.setAfValorAdquision(AF.getAfValorAdquision());
                                                                                                    activo.setAfDepreciacionInicialMeses(AF.getAfDepreciacionInicialMeses());
                                                                                                    activo.setAfDepreciacionInicialMonto(AF.getAfDepreciacionInicialMonto());
                                                                                                    activo.setAfVidaUtil(AF.getAfVidaUtil());
                                                                                                    //
                                                                                                    boolean isMayorCero = (AF.getAfValorResidual().compareTo(BigDecimal.ZERO) > 0
                                                                                                            || AF.getAfValorResidual().compareTo(BigDecimal.ZERO) == 0)
                                                                                                            && AF.getAfValorAdquision().compareTo(BigDecimal.ZERO) > 0;

                                                                                                    boolean isMenorIgual = AF.getAfValorResidual().compareTo(AF.getAfValorAdquision()) < 0
                                                                                                            || AF.getAfValorResidual().compareTo(AF.getAfValorAdquision()) == 0; //ValorResidual<=ValorAdquision
                                                                                                    boolean valorResidualValido = isMayorCero && isMenorIgual;
                                                                                                    if (valorResidualValido) {
                                                                                                        //
                                                                                                        AF.setAfDepreciacionInicialMeses(
                                                                                                                AF.getAfDepreciacionInicialMeses() != null
                                                                                                                ? AF.getAfDepreciacionInicialMeses()
                                                                                                                : BigDecimal.ZERO);

                                                                                                        boolean isMayorCero1 = (AF.getAfDepreciacionInicialMeses().compareTo(BigDecimal.ZERO) > 0
                                                                                                                || AF.getAfDepreciacionInicialMeses().compareTo(BigDecimal.ZERO) == 0);

                                                                                                        int vidaUtil = 0;
                                                                                                        if (categoriaAF.getCatVidaUtil() != 0) {
                                                                                                            vidaUtil = categoriaAF.getCatVidaUtil() * 12;
                                                                                                        }
                                                                                                        boolean isMenorIgual1 = (AF.getAfDepreciacionInicialMeses().compareTo(new BigDecimal(vidaUtil)) < 0
                                                                                                                || AF.getAfDepreciacionInicialMeses().compareTo(new BigDecimal(vidaUtil)) == 0);
                                                                                                        boolean valorDepreciacionMesesValido = isMayorCero1 && isMenorIgual1;
                                                                                                        if (valorDepreciacionMesesValido) {
                                                                                                            //
                                                                                                            boolean valorDepreciacionMontoValido = true;
                                                                                                            String mensajeErrorDepreciacionMonto = "";
                                                                                                            if (AF.getAfDepreciacionInicialMeses().compareTo(BigDecimal.ZERO) > 0 && AF.getAfDepreciacionInicialMonto().compareTo(BigDecimal.ZERO) == 0) {
                                                                                                                mensajeErrorDepreciacionMonto = "F" + codigo + " - " + producto.getProNombre() + " => El valor de la depreciación acumulada debe ser diferente de 0.00";
                                                                                                                valorDepreciacionMontoValido = false;
                                                                                                            } else {
                                                                                                                BigDecimal dif = AF.getAfValorAdquision().subtract(AF.getAfValorResidual());
                                                                                                                BigDecimal diferencia = dif.abs();
                                                                                                                boolean isMayorCero2 = AF.getAfDepreciacionInicialMonto().compareTo(BigDecimal.ZERO) > 0
                                                                                                                        || AF.getAfDepreciacionInicialMonto().compareTo(BigDecimal.ZERO) == 0;
                                                                                                                boolean isMenorIgual2 = AF.getAfDepreciacionInicialMonto().compareTo(diferencia) < 0
                                                                                                                        || AF.getAfDepreciacionInicialMonto().compareTo(diferencia) == 0;
                                                                                                                valorDepreciacionMontoValido = isMayorCero && isMenorIgual;
                                                                                                                mensajeErrorDepreciacionMonto = "F" + codigo + " - " + producto.getProNombre() + " => El valor ingresado debe ser menor o igual que la diferencia de valor adquisión y el valor residual (" + diferencia + ")";
                                                                                                            }

                                                                                                            if (valorDepreciacionMontoValido) {
                                                                                                                productoImp.setActivoFijo(activo);
                                                                                                                listaProductos.add(productoImp);
                                                                                                            } else {
                                                                                                                if (!mensajeErrorDepreciacionMonto.equals("")) {
                                                                                                                    listaMensajesEnviar.add(mensajeErrorDepreciacionMonto);
                                                                                                                } else {
                                                                                                                    productoImp.setActivoFijo(activo);
                                                                                                                    listaProductos.add(productoImp);
                                                                                                                }
                                                                                                            }
                                                                                                        } else {
                                                                                                            listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => El valor ingresado debe ser menor o igual que la vida útil multiplicado por 12 (" + vidaUtil + ")");
                                                                                                        }
                                                                                                    } else {
                                                                                                        listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => El valor residual debe ser menor o igual que el valor de adquisición");
                                                                                                    }
                                                                                                } else {
                                                                                                    listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => La categoría de activo fijo: " + categoriaAfCodigo + " no existe...");
                                                                                                }
                                                                                            } else {
                                                                                                listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Debe ingresar categoría de activo fijo");
                                                                                            }
                                                                                        } else {
                                                                                            listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Debe ingresa ubicación y sector de activo fijo");
                                                                                        }
                                                                                    } else {
                                                                                        listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => El tipo de producto: "
                                                                                                + producto.getInvProductoTipo().getInvProductoTipoPK().getTipCodigo()
                                                                                                + " del producto " + producto.getProNombre() + " debe ser de tipo ACTIVO FIJO");
                                                                                    }
                                                                                    //*******************************
                                                                                } else {
                                                                                    listaProductos.add(productoImp);
                                                                                }
                                                                            } else {
                                                                                listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Verificar que las cuentas ingresadas correspondan a las cuentas de la categoría seleccionada");
                                                                            }
                                                                        } else {
                                                                            listaMensajesEnviar.add(mensajeCuentas.substring(0));
                                                                        }
                                                                    } else {
                                                                        listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Hubo un error con la validación de las cuentas. Comuniquese con el administrador");
                                                                    }
                                                                } else {
                                                                    listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Hubo un error con la validación de las cuentas. Comuniquese con el administrador");
                                                                }
                                                            } else {
                                                                listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => El valor de crédito tributario de producto: <strong class='pl-2'>"
                                                                        + productosExcel.get(i).getProducto().getProCreditoTributario()
                                                                        + " </strong> no es válido.<br>");
                                                            }
                                                        } else {
                                                            listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Se debe ingresar el valor de crédito tributario");
                                                        }
                                                    } else {
                                                        listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => El valor de crédito tributario de producto: <strong class='pl-2'>"
                                                                + productosExcel.get(i).getProducto().getProCreditoTributario()
                                                                + " </strong> no es válido.<br>");
                                                    }
                                                } else {
                                                    listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Se debe ingresar el valor de IVA");
                                                }
                                            }
                                        } else {
                                            listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Debe ingresar unidad de medida de producto.<br>");
                                        }
                                    }
                                } else {
                                    listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Debe ingresar tipo de producto.<br>");
                                }
                            }
                        } else {
                            listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Debe ingresar marca de producto.<br>");
                        }
                    }
                } else {
                    listaMensajesEnviar.add("F" + codigo + " - " + producto.getProNombre() + " => Debe ingresar categoría de producto.<br>");
                }

            }
            mapResultadoEnviar = new HashMap<String, Object>();
            mapResultadoEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
            mapResultadoEnviar.put("listaImportarProductos", listaProductos);
            //************************
        }

        return mapResultadoEnviar;
    }

}
