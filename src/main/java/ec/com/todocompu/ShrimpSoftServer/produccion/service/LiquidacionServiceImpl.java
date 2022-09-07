package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorService;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.LiquidacionDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.LiquidacionMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PrdProductoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.TallaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
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
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdComisionista;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTallaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Service
public class LiquidacionServiceImpl implements LiquidacionService {

    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private LiquidacionMotivoDao liquidacionMotivoDao;
    @Autowired
    private TallaDao tallaDao;
    @Autowired
    private PrdProductoDao prdProductoDao;
    @Autowired
    private GenericoDao<PrdLiquidacion, PrdLiquidacionPK> liquidacionCriteriaDao;
    @Autowired
    private LiquidacionDao liquidacionDao;
    @Autowired
    private PrdProductoService prdProductoService;
    @Autowired
    private TallaService tallaService;
    @Autowired
    private LiquidacionMotivoService liquidacionMotivoService;
    @Autowired
    private CorridaService corridaService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private PiscinaService piscinaService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ComisionistaControlService comisionistaControlService;
    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericoDao<PrdLiquidacionDatosAdjuntos, Integer> prdLiquidacionDatosAdjuntosDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProveedorService proveedorService;

    @Override
    public MensajeTO insertarModificarPrdLiquidacion(PrdLiquidacion prdLiquidacion, List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle, List<PrdLiquidacionDatosAdjuntos> listadoImagenes, boolean entregaCasa, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        String mensaje = "";
        MensajeTO mensajeTO = new MensajeTO();
        if (UtilsValidacion.isFechaSuperior(prdLiquidacion.getLiqFecha()) && !entregaCasa) {
            retorno = "FLa fecha que ingreso es superior a la fecha actual del servidor. Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy");
        } else {
            sisInfoTO.setEmpresa(prdLiquidacion.getPrdLiquidacionPK().getLiqEmpresa());
            prdLiquidacion.setLiqAnulado(false);
            //prdLiquidacion.setLiqPendiente(false);
            prdLiquidacion.setUsrFechaInserta(Timestamp.valueOf(LocalDateTime.now()));
            if (prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() == null || prdLiquidacion.getPrdLiquidacionPK().getLiqNumero().compareToIgnoreCase("") == 0) {
                prdLiquidacion.setUsrEmpresa(sisInfoTO.getEmpresa());
                prdLiquidacion.setUsrCodigo(sisInfoTO.getUsuario());
                prdLiquidacion.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
            }
            if (!entregaCasa) {
                susSuceso = (prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() == null || prdLiquidacion.getPrdLiquidacionPK().getLiqNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
                susTabla = "produccion.prd_liquidacion";
                mensaje = (prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() == null || prdLiquidacion.getPrdLiquidacionPK().getLiqNumero().compareToIgnoreCase("") == 0)
                        ? "La liquidación de pesca: Lote " + prdLiquidacion.getLiqLote() + ", se ha guardado correctamente."
                        : "La liquidación de pesca: Lote " + prdLiquidacion.getLiqLote() + ", se ha modificado correctamente.";
            } else {
                susDetalle = (prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() == null || prdLiquidacion.getPrdLiquidacionPK().getLiqNumero().compareToIgnoreCase("") == 0)
                        ? "El registro entrega casa " + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() + ", se ha guardado correctamente."
                        : "El registro entrega casa " + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() + ", se ha modificado correctamente.";
                susSuceso = (prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() == null || prdLiquidacion.getPrdLiquidacionPK().getLiqNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
                susTabla = "produccion.prd_liquidacion";
                mensaje = (prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() == null || prdLiquidacion.getPrdLiquidacionPK().getLiqNumero().compareToIgnoreCase("") == 0)
                        ? "Entrega casa " + prdLiquidacion.getLiqLote() + ", se ha guardado correctamente."
                        : "Entrega casa " + prdLiquidacion.getLiqLote() + ", se ha modificado correctamente.";
            }
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = liquidacionDao.insertarModificarPrdLiquidacion(prdLiquidacion, listaPrdLiquidacionDetalle, sisSuceso);
            actualizarImagenesLiquidacionPesca(listadoImagenes, prdLiquidacion.getPrdLiquidacionPK());
            if (!comprobar) {
                retorno = "FHubo un error al guardar la Liquidacion...\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html> " + mensaje + " la liquidación de pesca tiene la siguiente información:<br><br>"
                        + "Motivo: <font size = 5>" + prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo()
                        + "</font>.<br> Número: <font size = 5>"
                        + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() + "</font>.<br></html>";
                mensajeTO.setFechaCreacion(prdLiquidacion.getUsrFechaInserta().toString());
                mensajeTO.getMap().put("liquidacion", prdLiquidacion);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    public boolean actualizarImagenesLiquidacionPesca(List<PrdLiquidacionDatosAdjuntos> listado, PrdLiquidacionPK pk) throws Exception {
        List<PrdLiquidacionDatosAdjuntos> listAdjuntosEnLaBase = liquidacionDao.listarImagenesDeLiquidacionPesca(pk);
        if (listado != null && !listado.isEmpty()) {
            if (listAdjuntosEnLaBase.size() > 0) {
                listado.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                    listAdjuntosEnLaBase.removeIf(n -> (n.getAdjSecuencial().equals(item.getAdjSecuencial())));
                });
                if (listAdjuntosEnLaBase.size() > 0) {
                    listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                        prdLiquidacionDatosAdjuntosDao.eliminar(itemEliminar);
                        AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                    });
                }
            }
            insertarImagenesLiquidacionPesca(listado, pk);
        } else {
            if (listAdjuntosEnLaBase.size() > 0) {
                listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                    prdLiquidacionDatosAdjuntosDao.eliminar(itemEliminar);
                    AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                });
            }
        }
        return true;
    }

    @Override
    public boolean insertarImagenesLiquidacionPesca(List<PrdLiquidacionDatosAdjuntos> listado, PrdLiquidacionPK pk) throws Exception {
        String bucket = sistemaWebServicio.obtenerRutaImagen(pk.getLiqEmpresa());
        Bucket b = AmazonS3Crud.crearBucket(bucket);
        if (b != null) {
            for (PrdLiquidacionDatosAdjuntos datoAdjunto : listado) {
                if (datoAdjunto.getAdjSecuencial() == null) {
                    PrdLiquidacionDatosAdjuntos invAdjunto = new PrdLiquidacionDatosAdjuntos();
                    byte[] adjArchivo = datoAdjunto.getImagenString().getBytes("UTF-8");
                    String archivo = new String(adjArchivo, "UTF-8");
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(invAdjunto.getAdjClaveArchivo(), archivo);
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "liquidacion_pesca/" + pk.getLiqMotivo() + "/" + pk.getLiqNumero() + "/";
                    invAdjunto.setPrdLiquidacion(new PrdLiquidacion(pk));
                    invAdjunto.setAdjBucket(bucket);
                    invAdjunto.setAdjClaveArchivo(carpeta + nombre);
                    invAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    prdLiquidacionDatosAdjuntosDao.insertar(invAdjunto);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, archivo, combo.getValor());
                }
            }
        } else {
            throw new GeneralException("Error al crear contenedor de imágenes.");
        }
        return true;
    }

    @Override
    public List<PrdLiquidacionDatosAdjuntos> listarImagenesDeLiquidacionPesca(PrdLiquidacionPK pk) throws Exception {
        return liquidacionDao.listarImagenesDeLiquidacionPesca(pk);
    }

    @Override
    public String insertarPrdLiquidacionTO(PrdLiquidacionTO prdLiquidacionTO,
            List<PrdLiquidacionDetalleTO> listaPrdLiquidacionDetalleTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!UtilsValidacion.isFechaSuperior(prdLiquidacionTO.getLiqFecha(), "yyyy-MM-dd")) {
            InvCliente invCliente = clienteDao.buscarInvCliente(prdLiquidacionTO.getLiqEmpresa(),
                    prdLiquidacionTO.getCliCodigo());
            if (invCliente != null) {
                if (liquidacionMotivoDao.getPrdLiquidacionMotivo(prdLiquidacionTO.getLiqEmpresa(),
                        prdLiquidacionTO.getLiqMotivo()) != null) {
                    // CREAR SUCESO
                    susClave = prdLiquidacionTO.getLiqMotivo() + " " + prdLiquidacionTO.getLiqNumero();
                    susDetalle = "";// EL DETALLE SE GENERA EN EL
                    // TRANSACCION
                    susSuceso = "INSERT";
                    susTabla = "produccion.prd_liquidacion";

                    prdLiquidacionTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

                    PrdLiquidacionNumeracion prdLiquidacionNumeracion = new PrdLiquidacionNumeracion(
                            new PrdLiquidacionNumeracionPK(prdLiquidacionTO.getLiqEmpresa(),
                                    prdLiquidacionTO.getLiqMotivo()));

                    PrdLiquidacion prdLiquidacion = ConversionesProduccion
                            .convertirPrdLiquidacionTO_PrdLiquidacion(prdLiquidacionTO);
                    prdLiquidacion.setInvCliente(invCliente);

                    List<PrdLiquidacionDetalle> listPrdLiquidacionDetalle = new ArrayList<PrdLiquidacionDetalle>(0);
                    PrdLiquidacionDetalle prdLiquidacionDetalle = null;
                    int estadoDetalle = 0;
                    int contador = 0;
                    for (PrdLiquidacionDetalleTO prdLiquidacionDetalleTO : listaPrdLiquidacionDetalleTO) {
                        prdLiquidacionDetalleTO.setDetOrden(contador);
                        prdLiquidacionDetalle = new PrdLiquidacionDetalle();
                        prdLiquidacionDetalle = ConversionesProduccion
                                .convertirPrdLiquidacionDetalleTO_PrdLiquidacionDetalle(prdLiquidacionDetalleTO);
                        PrdProducto prdLiquidacionProducto = prdProductoDao.obtenerPorId(PrdProducto.class,
                                new PrdProductoPK(prdLiquidacionDetalleTO.getProdEmpresa(),
                                        prdLiquidacionDetalleTO.getProdCodigo()));
                        if (prdLiquidacionProducto != null) {
                            prdLiquidacionDetalle.setPrdProducto(prdLiquidacionProducto);
                            PrdTalla prdLiquidacionTalla = tallaDao.obtenerPorId(PrdTalla.class,
                                    new PrdTallaPK(prdLiquidacionDetalleTO.getTallaEmpresa(),
                                            prdLiquidacionDetalleTO.getTallaCodigo()));
                            if (prdLiquidacionTalla != null) {
                                prdLiquidacionDetalle.setPrdTalla(prdLiquidacionTalla);
                                listPrdLiquidacionDetalle.add(prdLiquidacionDetalle);
                            } else {
                                estadoDetalle = 2;
                            }
                        } else {
                            estadoDetalle = 1;
                        }
                        if (estadoDetalle == 1 || estadoDetalle == 2) {
                            break;
                        } else {
                            prdLiquidacionProducto = null;
                        }
                        contador++;
                    }
                    if (estadoDetalle == 0) {
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);
                        if (liquidacionDao.insertarTransaccionPrdLiquidacion(prdLiquidacion,
                                listPrdLiquidacionDetalle, sisSuceso, prdLiquidacionNumeracion)) {
                            retorno = "T<html>Se ingresó la liquidacion con la siguiente información:<br><br>"
                                    + "Fecha: <font size = 5>" + prdLiquidacionTO.getLiqFecha()
                                    + "</font>.<br> Motivo: <font size = 5>"
                                    + prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo()
                                    + "</font>.<br> Número: <font size = 5>"
                                    + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() + "</font>.</html>";
                        }
                    } else if (estadoDetalle == 1) {
                        retorno = "F<html>Uno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                    } else {
                        retorno = "F<html>Una de las Tallas que escogió ya no está disponible...\nIntente de nuevo, escoja otra Talla o contacte con el administrador</html>";
                    }

                } else {
                    retorno = "F<html>No se encuentra el motivo...</html>";
                }
            } else {
                retorno = "F<html>El Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        return retorno;
    }

    @Override
    public String modificarPrdLiquidacionTO(PrdLiquidacionTO prdLiquidacionTO,
            List<PrdLiquidacionDetalleTO> listaPrdLiquidacionDetalleTO,
            List<PrdLiquidacionDetalleTO> listaPrdLiquidacionEliminarDetalleTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!UtilsValidacion.isFechaSuperior(prdLiquidacionTO.getLiqFecha(), "yyyy-MM-dd")) {
            InvCliente invCliente = clienteDao.buscarInvCliente(prdLiquidacionTO.getCliEmpresa(),
                    prdLiquidacionTO.getCliCodigo());
            if (invCliente != null) {
                if (liquidacionMotivoDao.getPrdLiquidacionMotivo(prdLiquidacionTO.getLiqEmpresa(),
                        prdLiquidacionTO.getLiqMotivo()) != null) {
                    PrdLiquidacion prdLiquidacionCreada = liquidacionDao.obtenerPorId(PrdLiquidacion.class,
                            new PrdLiquidacionPK(prdLiquidacionTO.getLiqEmpresa(), prdLiquidacionTO.getLiqMotivo(),
                                    prdLiquidacionTO.getLiqNumero()));
                    String estado = "";
                    if (prdLiquidacionTO.isLiqAnulado()) {
                        estado = "anulado ";
                    } else {
                        estado = "modificado ";
                    }
                    if (prdLiquidacionCreada != null) {
                        // CREAR EL SUCESO
                        susClave = prdLiquidacionTO.getLiqMotivo() + " " + prdLiquidacionTO.getLiqNumero();
                        susDetalle = "Se ha " + estado + " la Liquidacion con el motivo "
                                + prdLiquidacionTO.getLiqMotivo() + ", de la numeración "
                                + prdLiquidacionTO.getLiqNumero();
                        susSuceso = "UPDATE";
                        susTabla = "produccion.prd_liquidacion";
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);
                        prdLiquidacionTO.setUsrCodigo(prdLiquidacionCreada.getUsrCodigo());
                        prdLiquidacionTO.setUsrFechaInserta(UtilsValidacion
                                .fecha(prdLiquidacionCreada.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

                        PrdLiquidacion prdLiquidacion = ConversionesProduccion
                                .convertirPrdLiquidacionTO_PrdLiquidacion(prdLiquidacionTO);
                        prdLiquidacion.setInvCliente(invCliente);

                        List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle = new ArrayList<PrdLiquidacionDetalle>(
                                0);
                        PrdLiquidacionDetalle prdLiquidacionDetalle = null;
                        int estadoDetalle = 0;
                        if (listaPrdLiquidacionDetalleTO != null) {
                            for (PrdLiquidacionDetalleTO prdLiquidacionDetalleTO : listaPrdLiquidacionDetalleTO) {
                                prdLiquidacionDetalle = ConversionesProduccion
                                        .convertirPrdLiquidacionDetalleTO_PrdLiquidacionDetalle(
                                                prdLiquidacionDetalleTO);
                                PrdProducto prdLiquidacionProducto = prdProductoDao.obtenerPorId(PrdProducto.class,
                                        new PrdProductoPK(prdLiquidacionDetalleTO.getProdEmpresa(),
                                                prdLiquidacionDetalleTO.getProdCodigo()));
                                if (prdLiquidacionProducto != null) {
                                    prdLiquidacionDetalle.setPrdProducto(prdLiquidacionProducto);
                                    PrdTalla prdLiquidacionTalla = tallaDao.obtenerPorId(PrdTalla.class,
                                            new PrdTallaPK(prdLiquidacionDetalleTO.getTallaEmpresa(),
                                                    prdLiquidacionDetalleTO.getTallaCodigo()));
                                    if (prdLiquidacionTalla != null) {
                                        prdLiquidacionDetalle.setPrdTalla(prdLiquidacionTalla);
                                        listaPrdLiquidacionDetalle.add(prdLiquidacionDetalle);
                                    } else {
                                        estadoDetalle = 2;
                                    }
                                } else {
                                    estadoDetalle = 1;
                                }
                                if (estadoDetalle == 1 || estadoDetalle == 2) {
                                    break;
                                } else {
                                    prdLiquidacionProducto = null;
                                }
                            }
                        }
                        if (estadoDetalle == 0) {
                            List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalleEliminar = new ArrayList<PrdLiquidacionDetalle>(
                                    0);
                            PrdLiquidacionDetalle prdLiquidacionDetalleEliminar = null;

                            int estadoDetalleEliminar = 0;
                            if (listaPrdLiquidacionEliminarDetalleTO != null) {
                                for (PrdLiquidacionDetalleTO prdLiquidacionDetalleTO : listaPrdLiquidacionEliminarDetalleTO) {
                                    prdLiquidacionDetalleEliminar = ConversionesProduccion
                                            .convertirPrdLiquidacionDetalleTO_PrdLiquidacionDetalle(
                                                    prdLiquidacionDetalleTO);
                                    PrdProducto prdLiquidacionProducto = prdProductoDao.obtenerPorId(
                                            PrdProducto.class,
                                            new PrdProductoPK(prdLiquidacionDetalleTO.getLiqEmpresa(),
                                                    prdLiquidacionDetalleTO.getProdCodigo()));

                                    if (prdLiquidacionProducto != null) {
                                        prdLiquidacionDetalleEliminar
                                                .setPrdProducto(prdLiquidacionProducto);
                                        PrdTalla prdLiquidacionTalla = tallaDao.obtenerPorId(PrdTalla.class,
                                                new PrdTallaPK(prdLiquidacionDetalleTO.getTallaEmpresa(),
                                                        prdLiquidacionDetalleTO.getTallaCodigo()));
                                        if (prdLiquidacionTalla != null) {
                                            prdLiquidacionDetalleEliminar
                                                    .setPrdTalla(prdLiquidacionTalla);
                                            listaPrdLiquidacionDetalleEliminar.add(prdLiquidacionDetalleEliminar);
                                        } else {
                                            estadoDetalleEliminar = 2;
                                        }
                                    } else {
                                        estadoDetalleEliminar = 1;
                                    }
                                    if (estadoDetalleEliminar == 1 || estadoDetalleEliminar == 2) {
                                        break;
                                    } else {
                                        prdLiquidacionProducto = null;
                                    }
                                }
                            }
                            if (estadoDetalleEliminar == 0) {
                                if (liquidacionDao.modificarPrdLiquidacion(prdLiquidacion,
                                        listaPrdLiquidacionDetalle, listaPrdLiquidacionDetalleEliminar,
                                        sisSuceso)) {
                                    retorno = "T<html>Se  "
                                            + (prdLiquidacionTO.isLiqAnulado() ? "anuló" : "modificó")
                                            + "  la Liquidacion con la siguiente información:<br><br>"
                                            + "Motivo: <font size = 5>"
                                            + prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo()
                                            + "</font>.<br> Número: <font size = 5>"
                                            + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero()
                                            + "</font>.</html>";
                                } else {
                                    retorno = "FHubo un error al modificar la Liquidacion...\nIntente de nuevo o contacte con el administrador";
                                }
                            } else if (estadoDetalleEliminar == 1) {
                                retorno = "F<html>EUno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                            } else {
                                retorno = "F<html>Una de las Tallas que escogió ya no está disponible...\nIntente de nuevo, escoja otra Talla o contacte con el administrador</html>";
                            }
                        } else if (estadoDetalle == 1) {
                            retorno = "F<html>MUno de los Productos que escogió ya no está disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                        } else {
                            retorno = "F<html>Una de las Tallas que escogió ya no está disponible...\nIntente de nuevo, escoja otra Talla o contacte con el administrador</html>";
                        }

                    } else {
                        retorno = "F<html>La liquidacion que quiere modificar ya no se encuentra disponible</html>";
                    }
                } else {
                    retorno = "F<html>No se encuentra el motivo...</html>";
                }
            } else {
                retorno = "F<html>El Cliente que escogió ya no está disponible...\nIntente de nuevo, escoja otro Cliente o contacte con el administrador</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        return retorno;
    }

    @Override
    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacion(String empresa, String sector,
            String piscina, String busqueda) throws Exception {
        return liquidacionDao.getListaPrdConsultaLiquidacion(empresa, sector, piscina, busqueda);
    }

    @Override
    public PrdEstadoCCCVT getEstadoCCCVT(String empresa, String motivoTipo, String numero) throws Exception {
        return liquidacionDao.getEstadoCCCVT(empresa, motivoTipo, numero);
    }

    @Override
    public PrdLiquidacionCabeceraTO getPrdLiquidacionCabeceraTO(String empresa, String motivo, String numeroLiquidacion)
            throws Exception {
        return liquidacionDao.getPrdLiquidacionCabeceraTO(empresa, motivo, numeroLiquidacion);
    }

    @Override
    public PrdLiquidacion getPrdLiquidacion(PrdLiquidacionPK liquidacionPK) {
        PrdLiquidacion liquidacion = null;
        try {
            liquidacion = liquidacionDao.buscarPrdLiquidacion(liquidacionPK);
        } catch (Exception ex) {
            Logger.getLogger(LiquidacionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liquidacion;
    }

    @Override
    public PrdLiquidacion obtenerLiquidacion(PrdLiquidacionPK liquidacionPK) {
        PrdLiquidacion liquidacion = liquidacionDao.obtenerPorId(PrdLiquidacion.class, liquidacionPK);
        if (liquidacion != null) {
            liquidacion.setPrdLiquidacionDetalleList(null);
        }
        return liquidacion;
    }

    @Override
    public List<PrdLiquidacion> getListaPrdLiquidacion(String empresa) {
        return liquidacionDao.getListaPrdLiquidacion(empresa);
    }

    @Override
    public String desmayorizarPrdLiquidacion(PrdLiquidacionPK liquidacionPK, boolean entregaCasa, SisInfoTO sisInfoTO) {
        boolean sePuedeDesmayorizar = false;
        try {
            sePuedeDesmayorizar = liquidacionDao.verificarSiDesmayorizarAnularLiquidacion(liquidacionPK.getLiqEmpresa(), liquidacionPK.getLiqMotivo(), liquidacionPK.getLiqNumero());
        } catch (Exception ex) {
            Logger.getLogger(LiquidacionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (sePuedeDesmayorizar) {
            if (!entregaCasa) {
                susClave = liquidacionPK.getLiqMotivo() + " " + liquidacionPK.getLiqNumero();
                susDetalle = "";
                susSuceso = "UPDATE";
                susTabla = "produccion.prd_liquidacion";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                liquidacionDao.desmayorizarPrdLiquidacion(liquidacionPK, sisSuceso);
                return "TLa liquidación de pesca, número:" + liquidacionPK.getLiqNumero() + " se ha desmayorizado correctamente.";
            } else {
                susClave = liquidacionPK.getLiqMotivo() + " " + liquidacionPK.getLiqNumero();
                susDetalle = "Entrega casa Lote " + liquidacionPK.getLiqMotivo()
                        + " - " + liquidacionPK.getLiqNumero()
                        + ", se ha modificado correctamente.";;
                susSuceso = "UPDATE";
                susTabla = "produccion.prd_liquidacion";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                liquidacionDao.desmayorizarPrdLiquidacion(liquidacionPK, sisSuceso);
                return "TEntrega casa Lote número:" + liquidacionPK.getLiqNumero() + " se ha desmayorizado correctamente.";
            }
        }
        return "FLa liquidación de pesca tiene movimientos";
    }

    @Override
    public String anularRestaurarPrdLiquidacion(PrdLiquidacionPK liquidacionPK, boolean anularRestaurar, boolean entregaCasa, SisInfoTO sisInfoTO) {
        boolean sePuedeAnular = false;
        try {
            sePuedeAnular = liquidacionDao.verificarSiDesmayorizarAnularLiquidacion(liquidacionPK.getLiqEmpresa(), liquidacionPK.getLiqMotivo(), liquidacionPK.getLiqNumero());
        } catch (Exception ex) {
            Logger.getLogger(LiquidacionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (sePuedeAnular) {
            if (!entregaCasa) {
                susClave = liquidacionPK.getLiqMotivo() + " " + liquidacionPK.getLiqNumero();
                susDetalle = "TLa liquidación de pesca se " + (anularRestaurar ? "anuló" : "restauró") + " correctamente." + liquidacionPK.getLiqNumero();;
                susSuceso = "UPDATE";
                susTabla = "produccion.prd_liquidacion";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                liquidacionDao.anularRestaurarPrdLiquidacion(liquidacionPK, anularRestaurar, sisSuceso);
                return "TLa liquidación de pesca se " + (anularRestaurar ? "anuló" : "restauró") + " correctamente." + liquidacionPK.getLiqNumero();
            } else {
                susClave = liquidacionPK.getLiqMotivo() + " " + liquidacionPK.getLiqNumero();
                susDetalle = "Entrega casa " + liquidacionPK.getLiqNumero() + " se" + (anularRestaurar ? " anuló" : " restauró");
                susSuceso = "UPDATE";
                susTabla = "produccion.prd_liquidacion";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                liquidacionDao.anularRestaurarPrdLiquidacion(liquidacionPK, anularRestaurar, sisSuceso);
                return "TRegistro entrega casa " + (anularRestaurar ? "anuló" : "restauró") + " correctamente." + liquidacionPK.getLiqNumero();
            }
        }
        return "FLa liquidación de pesca tiene movimientos";
    }

    @Override
    public String controlOpcionSegunPeriodoLiquidacion(PrdLiquidacion liquidacion) throws Exception {
        String mensaje = "";
        boolean periodoCerrado = false;
        if (liquidacion != null) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(liquidacion.getPrdLiquidacionPK().getLiqEmpresa());
            DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaLiquidacion = formato.format(liquidacion.getLiqFecha());

            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(fechaLiquidacion, "yyyy-MM-dd")
                        .getTime() >= UtilsValidacion.fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(fechaLiquidacion, "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }
            if (!periodoCerrado) {
                mensaje = "TPermitir";
            } else {
                mensaje = "FPeriodo se encuentra cerrado, no se puede cambiar estado del registro";
            }

        }
        return mensaje;
    }

    @Override
    public PrdLiquidacionTO getBuscaObjLiquidacionPorLote(String liqLote, String empresa) throws Exception {
        return liquidacionDao.getBuscaObjLiquidacionPorLote(liqLote, empresa);
    }

    @Override
    public String desmayorizarPrdLiquidacionLote(List<ListaLiquidacionTO> listado, String empresa, SisInfoTO sisInfoTO) {
        int contador = 0;
        String respuesta = "";
        StringBuilder mensaje = new StringBuilder();
        for (ListaLiquidacionTO liquidacion : listado) {
            if (liquidacion.getLiqAnulado().equalsIgnoreCase("ANULADO") || liquidacion.getLiqPendiente().equalsIgnoreCase("PENDIENTE")) {
                mensaje.append("\n").append("No se puede Desmayorizar la Liquidación de Pesca ").append(liquidacion.getLiqNumero()).append(" ya ha sido Anulada o está Pendiente.");
                contador++;
            } else {
                String cadena = desmayorizarPrdLiquidacion(new PrdLiquidacionPK(empresa,
                        liquidacion.getLiqMotivo(),
                        liquidacion.getLiqNumero()), false, sisInfoTO) + "/";
                mensaje.append("\n").append(cadena.substring(1, cadena.length())).append(".");
                if (cadena.charAt(0) != 'T') {
                    contador++;
                }
                liquidacion.setLiqPendiente("PENDIENTE");
            }
        }
        if (contador == 0) {
            respuesta = 'T' + mensaje.toString();
        } else {
            respuesta = mensaje.toString();
        }

        return respuesta;
    }

    @Override
    public Map<String, Object> obtenerDatosParaLiquidacionPesca(String empresa, String piscina, String sector) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<PrdCorrida> listaCorridas = new ArrayList<PrdCorrida>();
        List<PrdListaPiscinaTO> listaPiscinas = new ArrayList<>();
        if (piscina != null && sector != null) {
            listaCorridas = corridaService.getListaCorridasPorEmpresaSectorPiscina(empresa, sector, piscina);
            listaPiscinas = piscinaService.getListaPiscinaTO(empresa.trim(), sector, true);
        } else {
            listaCorridas = corridaService.getListaCorridasPorEmpresa(empresa);
        }
        List<PrdListaSectorTO> listSectores = sectorService.getListaSectorTO(empresa.trim(), true);
        List<PrdTalla> listaTallas = tallaService.getListaPrdLiquidacionTalla(empresa, false);
        List<PrdProducto> listaProducto = prdProductoService.getListaPrdLiquidacionProductos(empresa, false);
        List<PrdLiquidacionMotivo> listaMotivos = liquidacionMotivoService.getListaPrdLiquidacionMotivoTO(empresa, false);
        List<PrdComisionista> listaComisionista = comisionistaControlService.listarComisionista(empresa);
        InvProductoEtiquetas etiquetas = productoService.traerEtiquetas(empresa);

        campos.put("listaMotivos", listaMotivos);
        campos.put("listaPiscinas", listaPiscinas);
        campos.put("listaSectores", listSectores);
        campos.put("listadoProductos", listaProducto);
        campos.put("listadoTallas", listaTallas);
        campos.put("listadoCorridas", listaCorridas);
        campos.put("etiquetas", etiquetas);
        campos.put("listaComisionistas", listaComisionista);
        return campos;
    }

    @Override
    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacionTO(String empresa, String sector, String piscina, String busqueda, String nRegistros) throws Exception {
        return liquidacionDao.getListaPrdConsultaLiquidacionTO(empresa, sector, piscina, busqueda, nRegistros);
    }

    @Override
    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacionTOSoloActivas(String empresa, String proveedor, String cliente, String motivo, String numero, String nRegistros) throws Exception {
        return liquidacionDao.getListaPrdConsultaLiquidacionTOSoloActivas(empresa, proveedor, cliente, motivo, numero, nRegistros);
    }

    @Override
    public PrdLiquidacion getPrdLiquidacionSoloActivas(String empresa, String proveedor, String cliente, String motivo, String numero) throws Exception {
        List<ListaLiquidacionTO> liqListado = liquidacionDao.getListaPrdConsultaLiquidacionTOSoloActivas(empresa, proveedor, cliente, motivo, numero, null);
        PrdLiquidacion respuesta = null;
        if (liqListado != null && liqListado.size() > 0) {
            PrdLiquidacionPK liquidacionPK = new PrdLiquidacionPK(empresa, motivo, numero);
            respuesta = getPrdLiquidacion(liquidacionPK);
        }

        return respuesta;
    }

    @Override
    public List<PrdLiquidacion> getListPrdLiquidacionParaCompras(String empresa, List<InvComprasLiquidacionTO> listado) throws Exception {
        List<PrdLiquidacion> respuesta = new ArrayList<>();

        if (listado != null && listado.size() > 0) {
            for (InvComprasLiquidacionTO item : listado) {
                PrdLiquidacionPK liquidacionPK = new PrdLiquidacionPK(empresa, item.getLiqMotivo(), item.getLiqNumero());
                PrdLiquidacion liq = getPrdLiquidacion(liquidacionPK);
                respuesta.add(liq);
            }
        }

        return respuesta;
    }

    @Override
    public List<PrdLiquidacion> getListPrdLiquidacionParaVentas(String empresa, List<InvVentasLiquidacionTO> listado) throws Exception {
        List<PrdLiquidacion> respuesta = new ArrayList<>();

        if (listado != null && listado.size() > 0) {
            for (InvVentasLiquidacionTO item : listado) {
                PrdLiquidacionPK liquidacionPK = new PrdLiquidacionPK(empresa, item.getLiqMotivo(), item.getLiqNumero());
                PrdLiquidacion liq = getPrdLiquidacion(liquidacionPK);
                respuesta.add(liq);
            }
        }

        return respuesta;
    }

    @Override
    public PrdCorrida obtenerCorrida(PrdLiquidacionPK pk) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(PrdLiquidacion.class);
        filtro.add(Restrictions.eq("prdLiquidacionPK.liqEmpresa", pk.getLiqEmpresa()));
        filtro.add(Restrictions.eq("prdLiquidacionPK.liqMotivo", pk.getLiqMotivo()));
        filtro.add(Restrictions.eq("prdLiquidacionPK.liqNumero", pk.getLiqNumero()));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("prdCorrida.prdCorridaPK"), "prdCorridaPK")
        );
        List<PrdCorrida> c = liquidacionCriteriaDao.proyeccionPorCriteria(filtro, PrdCorrida.class);
        PrdCorrida corrida = c.size() > 0 ? c.get(0) : null;

        if (corrida != null) {
            return corrida.getPrdCorridaPK() != null ? corrida : null;
        }
        return null;
    }

    @Override
    public List<PrdFunLiquidacionConsolidandoTallasTO> getPrdFunLiquidacionConsolidandoTallasTO(String empresa, String desde, String hasta, String sector, String cliente, String piscina) throws Exception {
        return liquidacionDao.getPrdFunLiquidacionConsolidandoTallasTO(empresa, desde, hasta, sector, cliente, piscina);
    }

    //listar consolidando clientes
    @Override
    public List<PrdLiquidacionConsolidandoClientesTO> getListadoLiquidacionConsolidandoClientesTO(String empresa, String sector, String desde, String hasta, String cliente) throws Exception {
        return liquidacionDao.getPrdFunLiquidacionConsolidandoClientesTO(empresa, sector, desde, hasta, cliente);
    }

    //listar consolidando proveedores
    @Override
    public List<PrdLiquidacionConsolidandoProveedoresTO> getListadoLiquidacionConsolidandoProveedoresTO(String empresa, String sector, String desde, String hasta, String proveedor, String comisionista) throws Exception {
        return liquidacionDao.getPrdFunLiquidacionConsolidandoProveedoresTO(empresa, sector, desde, hasta, proveedor, comisionista);
    }

    //liquidacion listado consulta
    @Override
    public List<PrdLiquidacionConsultaTO> getListadoLiquidacionConsultasTO(String empresa, String sector, String piscina, String desde, String hasta, boolean incluirAnuladas) throws Exception {
        return liquidacionDao.getPrdFunLiquidacionConsultaTO(empresa, sector, piscina, desde, hasta, incluirAnuladas);
    }

    //liquidacion listado consulta
    @Override
    public List<PrdLiquidacionConsultaEmpacadoraTO> getListadoLiquidacionConsultasEmpacadoraTO(String empresa, String sector, String piscina, String desde, String hasta, String proveedor, boolean incluirAnuladas, String comisionista) throws Exception {
        return liquidacionDao.getPrdFunLiquidacionConsultaEmpacadoraTO(empresa, sector, piscina, desde, hasta, proveedor, incluirAnuladas, comisionista);
    }

    //liquidacion detalleProducto
    @Override
    public List<PrdLiquidacionDetalleProductoTO> getListadoLiquidacionDetalleProductoTO(String empresa, String sector, String piscina, String desde, String hasta, String cliente, String talla, String tipo) throws Exception {
        return liquidacionDao.getListadoLiquidacionDetalleProductoTO(empresa, sector, piscina, desde, hasta, cliente, talla, tipo);
    }

    //liquidacion detalleProductoEmpacadora
    @Override
    public List<PrdLiquidacionDetalleProductoEmpacadoraTO> getListadoLiquidacionDetalleProductoEmpacadoraTO(String empresa, String sector, String piscina, String desde, String hasta, String proveedor, String talla, String tipo, String comisionista) throws Exception {
        return liquidacionDao.getListadoLiquidacionDetalleProductoEmpacadoraTO(empresa, sector, piscina, desde, hasta, proveedor, talla, tipo, comisionista);
    }

    @Override
    public List<PrdLiquidacionesDetalleTO> listarLiquidacionesDetalle(String empresa, String desde, String hasta) throws Exception {
        return liquidacionDao.listarLiquidacionesDetalle(empresa, desde, hasta);
    }

    @Override
    public String obtenerSiguienteLote(String empresa, String motivo) throws Exception {
        return liquidacionDao.obtenerSiguienteLote(empresa, motivo);
    }

    @Override
    public boolean verificarSiDesmayorizarAnularLiquidacion(String empresa, String motivo, String eNumero) throws Exception {
        return liquidacionDao.verificarSiDesmayorizarAnularLiquidacion(empresa, motivo, eNumero);
    }

    @Override
    public List<PrdFunLiquidacionConsolidandoTallasProveedorTO> getPrdFunLiquidacionConsolidandoTallasProveedor(String empresa, String desde, String hasta, String sector, String proveedor, String piscina, String comisionista) throws Exception {
        return liquidacionDao.getPrdFunLiquidacionConsolidandoTallasProveedor(empresa, desde, hasta, sector, proveedor, piscina, comisionista);
    }

    @Override
    public boolean guardarImagenesLiquidacionPesca(PrdLiquidacionPK pk, List<PrdLiquidacionDatosAdjuntos> imagenes, SisInfoTO sisInfoTO) throws Exception {
        List<PrdLiquidacionDatosAdjuntos> listaImagenes = new ArrayList<>();
        if (imagenes != null && imagenes.size() > 0) {
            for (PrdLiquidacionDatosAdjuntos item : imagenes) {
                /// PREPARANDO OBJETO SISSUCESO
                if (item.getAdjSecuencial() == null) {
                    susClave = pk.getLiqEmpresa() + "|" + pk.getLiqMotivo() + "|" + pk.getLiqNumero();
                    susDetalle = "Se agregó la imagen para el liquidación de pesca: " + susClave;
                    susSuceso = "INSERT";
                    susTabla = "produccion.prd_liquidacion_datos_adjuntos";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sucesoDao.insertar(sisSuceso);
                    item.setPrdLiquidacion(new PrdLiquidacion(pk));
                    item.setAdjSecuencial(item.getAdjSecuencial());
                    listaImagenes.add(item);
                } else {
                    item.setPrdLiquidacion(new PrdLiquidacion(pk));
                    item.setAdjSecuencial(item.getAdjSecuencial());
                    listaImagenes.add(item);
                }
            }
        }
        actualizarImagenesLiquidacionPesca(listaImagenes, pk);
        return true;

    }

    @Override
    public List<String> listadoComisionista(String empresa) throws Exception {
        return liquidacionDao.listadoComisionista(empresa);
    }

    @Override
    public Map<String, Object> verificarExistenciasLiquidacion(String empresa, PrdLiquidacion liquidicacion, List<PrdLiquidacionDetalle> detalles) throws Exception {
        InvCliente cliente = null;
        InvProveedor proveedor = null;

        PrdLiquidacion liquidicacionEnviar = new PrdLiquidacion();
        List<PrdLiquidacionDetalle> detallesEnviar = new ArrayList<>();
        List<String> listaMensajesEnviar = new ArrayList<>();
        Map<String, Object> respuesta = new HashMap<>();
        String mensaje = "";

        liquidicacionEnviar = liquidicacion;
        //buscar cliente o proveedor
        if (liquidicacion.getInvCliente() != null && liquidicacion.getInvCliente().getCliIdNumero() != null) {
            cliente = clienteService.obtenerInvClientePorCedulaRuc(empresa, liquidicacion.getInvCliente().getCliIdNumero());

        } else {
            if (liquidicacion.getInvProveedor() != null && liquidicacion.getInvProveedor().getProvIdNumero() != null) {
                proveedor = proveedorService.obtenerInvProveedorPorCedulaRuc(empresa, liquidicacion.getInvProveedor().getProvIdNumero());
            }
        }
        if (cliente != null) {
            liquidicacionEnviar.setInvCliente(cliente);
            liquidicacionEnviar.setInvProveedor(null);
        } else {
            if (proveedor != null) {
                liquidicacionEnviar.setInvProveedor(proveedor);
                liquidicacionEnviar.setInvCliente(null);
            }
        }
        //Detalles
        if (detalles != null && !detalles.isEmpty()) {

            List<PrdTalla> listaTallas = tallaService.getListaPrdLiquidacionTalla(empresa, false);
            for (PrdLiquidacionDetalle detalle : detalles) {
                mensaje = "";
                PrdLiquidacionDetalle det = new PrdLiquidacionDetalle();
                det.setDetLibras(detalle.getDetLibras());
                det.setDetPrecio(detalle.getDetPrecio());
                det.setPrdTotal(detalle.getPrdTotal());
                //verificar producto
                List<PrdProducto> listadoProductos = prdProductoService.getListaPrdLiquidacionProductos(empresa, detalle.getPrdProducto().getProdClase(), detalle.getPrdProducto().getProdTipo());
                if (listadoProductos != null && listadoProductos.size() > 0) {
                    det.setPrdProducto(listadoProductos.get(0));
                } else {
                    det.setPrdProducto(new PrdProducto());
                    mensaje = "FNo se encontró producto con clase-tipo: <strong>" + detalle.getPrdProducto().getProdClase() + "-" + detalle.getPrdProducto().getProdTipo() + "</strong><br>";
                }
                //verificar tallas
                List<PrdTalla> listadoTallas = listaTallas.stream()
                        .filter(item -> item.getPrdLiquidacionTallaPK().getTallaCodigo().equals(detalle.getPrdTalla().getPrdLiquidacionTallaPK().getTallaCodigo()))
                        .collect(Collectors.toList());
                if (listadoTallas != null && listadoTallas.size() > 0) {
                    det.setPrdTalla(listadoTallas.get(0));
                } else {
                    det.setPrdTalla(null);
                    if (mensaje.isEmpty()) {
                        mensaje = "F";
                    }
                    mensaje += "No se encontró la talla: <strong>" + detalle.getPrdTalla().getPrdLiquidacionTallaPK().getTallaCodigo() + "</strong><br>";
                }

                if (!mensaje.isEmpty()) {
                    listaMensajesEnviar.add(mensaje);
                }
                detallesEnviar.add(det);
            }
            respuesta.put("listaMensajesEnviar", listaMensajesEnviar);
            respuesta.put("detallesEnviar", detallesEnviar);
            respuesta.put("liquidicacionEnviar", liquidicacionEnviar);

        }

        return respuesta;
    }

}
