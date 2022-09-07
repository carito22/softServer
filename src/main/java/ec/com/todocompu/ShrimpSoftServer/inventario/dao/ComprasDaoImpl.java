package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDividendoDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraFormaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraReembolsoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableAuxDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoCompraDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxDiferenciasTributacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDividendo;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraReembolso;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDatosBasicoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasPorPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasProgramadasListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasVsVentasTonelajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunUltimasComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvSecuenciaComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvAdjuntosCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleImb;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCompra;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Repository
public class ComprasDaoImpl extends GenericDaoImpl<InvCompras, InvComprasPK> implements ComprasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ComprasDetalleDao comprasDetalleDao;
    @Autowired
    private ComprasMotivoAnuladoDao comprasMotivoAnuladoDao;
    @Autowired
    private ContableAuxDao contableAuxDao;
    @Autowired
    private DetalleDao detalleDao;
    @Autowired
    private CompraDao compraDao;
    @Autowired
    private CompraDetalleDao compraDetalleDao;
    @Autowired
    private CompraDividendoDao compraDividendoDao;
    @Autowired
    private CompraFormaPagoDao compraFormaPagoDao;
    @Autowired
    private CompraReembolsoDao compraReembolsoDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ComprasDetalleSeriesDao comprasDetalleSeriesDao;
    @Autowired
    private ComprasDetalleImbDao comprasDetalleImbDao;
    @Autowired
    private ComprasLiquidacionDao comprasLiquidacionDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private GenericoDao<InvAdjuntosCompras, Integer> invComprasDatosAdjuntosDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private SucesoCompraDao sucesoCompraDao;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @Override
    public String eliminarComprasProgramadas(String empresa, String periodo, String motivo, String numero, SisSuceso sisSuceso) throws Exception {
        String sql = "SELECT * FROM inventario.fun_eliminar_compras_programadas('" + empresa + "', '" + periodo + "', '" + motivo + "', '" + numero + "')";
        String respuesta = "";
        try {
            boolean respuestaFuncion = (boolean) genericSQLDao.obtenerObjetoPorSql(sql);
            if (respuestaFuncion) {
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
    public String eliminarCompras(String empresa, String periodo, String motivo, String numero, SisSuceso sisSuceso) throws Exception {
        String sql = "SELECT * FROM inventario.fun_eliminar_compras('" + empresa + "', '" + periodo + "', '" + motivo + "', '" + numero + "')";
        String respuesta = "";
        try {
            String respuestaFuncion = (String) genericSQLDao.obtenerObjetoPorSql(sql);
            if (respuestaFuncion.substring(0, 1).equalsIgnoreCase("T")) {
                sucesoDao.insertar(sisSuceso);
                List<InvAdjuntosCompras> listadoAdjuntos = getAdjuntosCompra(new InvComprasPK(empresa, periodo, motivo, numero));
                if (!respuestaFuncion.equals("T ->> 0 registros afectados!")) {
                    if (listadoAdjuntos != null && listadoAdjuntos.size() > 0) {
                        listadoAdjuntos.forEach((itemEliminar) -> {
                            AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                        });
                    }
                }
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
    public String duplicarComprasProgramada(String empresa, String periodo, String motivo, String numero, Date date, SisInfoTO sisInfoTO) throws Exception {
        String fecha = UtilsDate.DeDateAString(date);
        String sql = "SELECT * FROM inventario.fun_compras_duplicar('" + empresa + "', '" + periodo + "', '" + motivo + "', '" + numero + "', '" + fecha + "', '" + sisInfoTO.getUsuario() + "')";
        String respuesta;
        try {
            respuesta = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
            if (respuesta != null) {
                String susClave = respuesta;
                String susDetalle = "Se insertó la compra programada: " + respuesta;
                String susSuceso = "INSERT";
                String susTabla = "inventario.inv_compras";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                sucesoDao.insertar(sisSuceso);
            } else {
                respuesta = respuesta + ". Fecha: " + fecha;
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respuesta = e.getMessage() + ". Fecha: " + fecha;
        }
        return respuesta;
    }

    @Override
    public void actualizarPendientePorSql(InvCompras invCompras) {
        String sql = "UPDATE inventario.inv_compras SET comp_pendiente=false WHERE comp_empresa='"
                + invCompras.getInvComprasPK().getCompEmpresa() + "' and " + "comp_periodo='"
                + invCompras.getInvComprasPK().getCompPeriodo() + "' and comp_motivo='"
                + invCompras.getInvComprasPK().getCompMotivo() + "' and comp_numero='"
                + invCompras.getInvComprasPK().getCompNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public InvCompras buscarInvCompras(String empresa, String perCodigo, String motCodigo, String compNumero)
            throws Exception {
        return obtenerPorId(InvCompras.class, new InvComprasPK(empresa, perCodigo, motCodigo, compNumero));
    }

    @Override
    public InvComprasTO getComprasTO(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception {
        return ConversionesInventario.convertirInvCompras_InvComprasTO(
                obtenerPorId(InvCompras.class, new InvComprasPK(empresa, periodo, motivo, numeroCompra)));
    }

    @Override
    public void insertarInvCompra(InvCompras invCompras, List<InvComprasDetalle> listaInvComprasDetalle,
            SisSuceso sisSuceso, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolso,
            List<AnxCompraFormaPago> anxCompraFormaPago) throws Exception {
        insertar(invCompras);
        for (int i = 0; i < listaInvComprasDetalle.size(); i++) {

            listaInvComprasDetalle.get(i).setInvCompras(invCompras);
            comprasDetalleDao.insertar(listaInvComprasDetalle.get(i));
        }

        if (!invCompras.getCompDocumentoTipo().equals("00")) {
            if (invCompras.getCompDocumentoTipo().equals("04")) {
                if (anxCompra != null) {
                    compraDao.insertar(anxCompra);
                    for (int i = 0; i < anxCompraDetalle.size(); i++) {
                        compraDetalleDao.insertar(anxCompraDetalle.get(i));
                    }
                    for (int i = 0; i < anxCompraDividendos.size(); i++) {
                        compraDividendoDao.insertar(anxCompraDividendos.get(i));
                    }
                    for (int i = 0; i < anxCompraFormaPago.size(); i++) {
                        compraFormaPagoDao.insertar(anxCompraFormaPago.get(i));
                    }
                }
            } else {
                compraDao.insertar(anxCompra);
                for (int i = 0; i < anxCompraDetalle.size(); i++) {
                    compraDetalleDao.insertar(anxCompraDetalle.get(i));
                }
                for (int i = 0; i < anxCompraDividendos.size(); i++) {
                    compraDividendoDao.insertar(anxCompraDividendos.get(i));
                }
                for (int i = 0; i < anxCompraFormaPago.size(); i++) {
                    compraFormaPagoDao.insertar(anxCompraFormaPago.get(i));
                }

                for (int i = 0; i < anxCompraReembolso.size(); i++) {
                    compraReembolsoDao.insertar(anxCompraReembolso.get(i));
                }
            }
        }
        sucesoDao.insertar(sisSuceso);

    }

    @Override
    public void insertarInvCompraInventario(InvCompras invCompras, List<InvComprasDetalle> listaInvComprasDetalle, SisSuceso sisSuceso, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle, List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolso, List<AnxCompraFormaPago> anxCompraFormaPago, List<InvComprasDetalleImb> listaCompraImb, List<InvComprasLiquidacion> listInvComprasLiquidacion) throws Exception {
        insertar(invCompras);
        List<InvComprasDetalle> listadoCopia = listaInvComprasDetalle.stream().collect(Collectors.toList());

        //DETALLE COMPRA
        for (int i = 0; i < listaInvComprasDetalle.size(); i++) {
            listaInvComprasDetalle.get(i).setInvCompras(invCompras);
            comprasDetalleDao.insertar(listaInvComprasDetalle.get(i));
        }
        //COMPRA IMB
        for (int i = 0; i < listaCompraImb.size(); i++) {
            listaCompraImb.get(i).setInvCompras(invCompras);
            comprasDetalleImbDao.insertar(listaCompraImb.get(i));
        }

        //COMPRA LIQUIDACION
        for (int i = 0; i < listInvComprasLiquidacion.size(); i++) {
            listInvComprasLiquidacion.get(i).setInvCompras(invCompras);
            comprasLiquidacionDao.insertar(listInvComprasLiquidacion.get(i));
        }
        if (!invCompras.getCompDocumentoTipo().equals("00")) {
            if (invCompras.getCompDocumentoTipo().equals("04")) {
                if (anxCompra != null) {
                    compraDao.insertar(anxCompra);
                    for (int i = 0; i < anxCompraDetalle.size(); i++) {
                        compraDetalleDao.insertar(anxCompraDetalle.get(i));
                    }
                    for (int i = 0; i < anxCompraDividendos.size(); i++) {
                        compraDividendoDao.insertar(anxCompraDividendos.get(i));
                    }
                    for (int i = 0; i < anxCompraFormaPago.size(); i++) {
                        compraFormaPagoDao.insertar(anxCompraFormaPago.get(i));
                    }
                }
            } else {
                if (anxCompra != null) {
                    compraDao.insertar(anxCompra);
                    for (int i = 0; i < anxCompraDetalle.size(); i++) {
                        compraDetalleDao.insertar(anxCompraDetalle.get(i));
                    }
                    for (int i = 0; i < anxCompraDividendos.size(); i++) {
                        compraDividendoDao.insertar(anxCompraDividendos.get(i));
                    }
                    for (int i = 0; i < anxCompraFormaPago.size(); i++) {
                        compraFormaPagoDao.insertar(anxCompraFormaPago.get(i));
                    }

                    for (int i = 0; i < anxCompraReembolso.size(); i++) {
                        compraReembolsoDao.insertar(anxCompraReembolso.get(i));
                    }
                }
            }
        }
        sucesoDao.insertar(sisSuceso);
        //suceso personalizado
        /*for (int i = 0; i < listadoCopia.size(); i++) {
            listadoCopia.get(i).setInvCompras(null);
        }*/
        SisSucesoCompra sucesoCompra = new SisSucesoCompra();
        InvCompras copia = ConversionesInventario.convertirInvCompras_InvCompras(invCompras);
        copia.setInvComprasDetalleList(listadoCopia);
        String json = UtilsJSON.objetoToJson(copia);
        sucesoCompra.setSusJson(json);
        sucesoCompra.setSisSuceso(sisSuceso);
        sucesoCompra.setInvCompras(copia);
        sucesoCompraDao.insertar(sucesoCompra);

    }

    @Override
    public boolean insertarTransaccionInvCompra(InvCompras invCompras, List<InvComprasDetalle> listaInvComprasDetalles,
            SisSuceso sisSuceso, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolso,
            List<AnxCompraFormaPago> anxCompraFormaPago, List<InvAdjuntosCompras> listImagen) throws Exception {

        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionCompra(invCompras.getInvComprasPK().getCompEmpresa(),
                invCompras.getInvComprasPK().getCompPeriodo(), invCompras.getInvComprasPK().getCompMotivo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            invCompras.setInvComprasPK(new InvComprasPK(invCompras.getInvComprasPK().getCompEmpresa(),
                    invCompras.getInvComprasPK().getCompPeriodo(), invCompras.getInvComprasPK().getCompMotivo(),
                    rellenarConCeros + numeracion));

        } while (buscarInvCompras(invCompras.getInvComprasPK().getCompEmpresa(),
                invCompras.getInvComprasPK().getCompPeriodo(), invCompras.getInvComprasPK().getCompMotivo(),
                rellenarConCeros + numeracion) != null);
        sisSuceso.setSusClave(invCompras.getInvComprasPK().getCompPeriodo() + " "
                + invCompras.getInvComprasPK().getCompMotivo() + " " + invCompras.getInvComprasPK().getCompNumero());
        String detalle = "";
        if (invCompras.getCompDocumentoTipo().equals("00")) {
            detalle = "Compra a " + invCompras.getInvProveedor().getProvRazonSocial() + " por "
                    + invCompras.getCompTotal().toPlainString();
        } else {
            detalle = "Compra a " + invCompras.getInvProveedor().getProvRazonSocial() + " según documento # "
                    + invCompras.getCompDocumentoNumero() + " por $" + invCompras.getCompTotal().toPlainString();
        }
        sisSuceso.setSusDetalle(detalle);

        boolean llenarAnexo = true;
        if (!invCompras.getCompDocumentoTipo().equals("00")) {
            if (invCompras.getCompDocumentoTipo().equals("04")) {
                if (anxCompra == null) {
                    llenarAnexo = false;
                }
            }
            if (llenarAnexo) {
                anxCompra.setAnxCompraPK(new AnxCompraPK(invCompras.getInvComprasPK().getCompEmpresa(),
                        invCompras.getInvComprasPK().getCompPeriodo(), invCompras.getInvComprasPK().getCompMotivo(),
                        invCompras.getInvComprasPK().getCompNumero()));
                for (int i = 0; i < anxCompraDetalle.size(); i++) {
                    anxCompraDetalle.get(i).setAnxCompra(anxCompra);
                }
                for (int i = 0; i < anxCompraDividendos.size(); i++) {
                    anxCompraDividendos.get(i).setAnxCompra(anxCompra);
                }
                for (int i = 0; i < anxCompraFormaPago.size(); i++) {
                    anxCompraFormaPago.get(i).setAnxCompra(anxCompra);
                }
                if (anxCompraReembolso != null && !anxCompraReembolso.isEmpty()) {
                    for (int i = 0; i < anxCompraReembolso.size(); i++) {
                        anxCompraReembolso.get(i).setAnxCompra(anxCompra);
                    }
                }
            }
        }
        insertarInvCompra(invCompras, listaInvComprasDetalles, sisSuceso, anxCompra, anxCompraDetalle,
                anxCompraDividendos, anxCompraReembolso, anxCompraFormaPago);
        actualizarImagenesCompra(listImagen, invCompras.getInvComprasPK());
        return true;
    }

    @Override
    public boolean insertarTransaccionInvCompraInventario(InvCompras invCompras, List<InvComprasDetalle> listaInvComprasDetalles, SisSuceso sisSuceso, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle, List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolso, List<AnxCompraFormaPago> anxCompraFormaPago, List<InvAdjuntosCompras> listImagen, List<InvComprasDetalleImb> listaCompraImb, List<InvComprasLiquidacion> listInvComprasLiquidacion) throws Exception {
        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionCompra(invCompras.getInvComprasPK().getCompEmpresa(), invCompras.getInvComprasPK().getCompPeriodo(), invCompras.getInvComprasPK().getCompMotivo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }
            invCompras.setInvComprasPK(new InvComprasPK(invCompras.getInvComprasPK().getCompEmpresa(), invCompras.getInvComprasPK().getCompPeriodo(), invCompras.getInvComprasPK().getCompMotivo(), rellenarConCeros + numeracion));
        } while (buscarInvCompras(invCompras.getInvComprasPK().getCompEmpresa(), invCompras.getInvComprasPK().getCompPeriodo(), invCompras.getInvComprasPK().getCompMotivo(), rellenarConCeros + numeracion) != null);
        sisSuceso.setSusClave(invCompras.getInvComprasPK().getCompPeriodo() + " " + invCompras.getInvComprasPK().getCompMotivo() + " " + invCompras.getInvComprasPK().getCompNumero());
        String detallePendiente = invCompras.getCompPendiente() ? " en pendiente" : "";
        String detalle = "";
        if (invCompras.getCompDocumentoTipo().equals("00")) {
            detalle = "Compra a " + (invCompras.getInvProveedor() != null ? invCompras.getInvProveedor().getProvRazonSocial() : "") + " por " + (invCompras.getCompTotal() != null ? invCompras.getCompTotal().toPlainString() + detallePendiente : "0.00" + detallePendiente);
        } else {
            detalle = "Compra a " + invCompras.getInvProveedor().getProvRazonSocial() + " según documento # " + invCompras.getCompDocumentoNumero() + " por $" + invCompras.getCompTotal().toPlainString() + detallePendiente;
        }
        sisSuceso.setSusDetalle(detalle);

        boolean llenarAnexo = true;
        if (!invCompras.getCompDocumentoTipo().equals("00")) {
            if (invCompras.getCompDocumentoTipo().equals("04")) {
                if (anxCompra == null) {
                    llenarAnexo = false;
                }
            }
            if (llenarAnexo) {
                anxCompra.setAnxCompraPK(new AnxCompraPK(invCompras.getInvComprasPK().getCompEmpresa(), invCompras.getInvComprasPK().getCompPeriodo(), invCompras.getInvComprasPK().getCompMotivo(), invCompras.getInvComprasPK().getCompNumero()));
                boolean esAgente = false;
                SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorIdEvict(SisEmpresaParametros.class, invCompras.getInvComprasPK().getCompEmpresa());
                if (parametros != null && parametros.getParAgenteRetencion() != null && !parametros.getParAgenteRetencion().isEmpty()) {
                    esAgente = true;
                }
                if (anxCompraDetalle.isEmpty() && !esAgente) {
                    anxCompra.setCompRetencionFechaEmision(invCompras.getCompFecha());
                    anxCompra.setCompBase0(invCompras.getCompBase0());
                    anxCompra.setCompBaseimponible(invCompras.getCompBaseImponible());
                    anxCompra.setCompBasenoobjetoiva(invCompras.getCompBaseNoObjeto());
                    anxCompra.setCompMontoice(invCompras.getCompIce());
                    anxCompra.setCompMontoiva(invCompras.getCompMontoIva());
                }
                for (int i = 0; i < anxCompraDetalle.size(); i++) {
                    anxCompraDetalle.get(i).setAnxCompra(anxCompra);
                }
                for (int i = 0; i < anxCompraDividendos.size(); i++) {
                    anxCompraDividendos.get(i).setAnxCompra(anxCompra);
                }
                for (int i = 0; i < anxCompraFormaPago.size(); i++) {
                    anxCompraFormaPago.get(i).setAnxCompra(anxCompra);
                }
                if (anxCompraReembolso != null && !anxCompraReembolso.isEmpty()) {
                    for (int i = 0; i < anxCompraReembolso.size(); i++) {
                        anxCompraReembolso.get(i).setAnxCompra(anxCompra);
                    }
                }
            }
        }
        insertarInvCompraInventario(invCompras, listaInvComprasDetalles, sisSuceso, anxCompra, anxCompraDetalle, anxCompraDividendos, anxCompraReembolso, anxCompraFormaPago, listaCompraImb, listInvComprasLiquidacion);
        actualizarImagenesCompra(listImagen, invCompras.getInvComprasPK());
        return true;

    }

    @Override
    public boolean modificarInvCompras(InvCompras invCompras, List<InvComprasDetalle> listaInvDetalle,
            List<InvComprasDetalle> listaInvDetalleEliminar, AnxCompra anxCompra,
            List<AnxCompraDetalle> anxCompraDetalle, List<AnxCompraDetalle> anxCompraDetalleEliminar,
            List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolsos,
            List<AnxCompraReembolso> anxCompraReembolsoEliminar, List<AnxCompraFormaPago> anxCompraFormaPago,
            List<AnxCompraFormaPago> anxCompraFormaPagoEliminar, SisSuceso sisSuceso, List<SisSuceso> listaSisSuceso,
            ConContable conContable, InvComprasMotivoAnulacion invComprasMotivoAnulacion,
            boolean eliminarMotivoAnulacion, boolean desmayorizar, List<InvAdjuntosCompras> listImagen) {

        try {
            if (anxCompra != null) {
                String delete_anx_compra_detalle = "DELETE FROM anexo.anx_compra_detalle WHERE comp_empresa='" + anxCompra.getAnxCompraPK().getCompEmpresa()
                        + "' AND comp_periodo='" + anxCompra.getAnxCompraPK().getCompPeriodo()
                        + "' AND comp_motivo='" + anxCompra.getAnxCompraPK().getCompMotivo()
                        + "' AND comp_numero='" + anxCompra.getAnxCompraPK().getCompNumero() + "'";
                String delete_anx_compra_dividendo = "DELETE FROM anexo.anx_compra_dividendo WHERE comp_empresa='" + anxCompra.getAnxCompraPK().getCompEmpresa()
                        + "' AND comp_periodo='" + anxCompra.getAnxCompraPK().getCompPeriodo()
                        + "' AND comp_motivo='" + anxCompra.getAnxCompraPK().getCompMotivo()
                        + "' AND comp_numero='" + anxCompra.getAnxCompraPK().getCompNumero() + "'";
                genericSQLDao.ejecutarSQL(delete_anx_compra_detalle);
                genericSQLDao.ejecutarSQL(delete_anx_compra_dividendo);
            } else {
                //No hay retención!
            }

            if (!invCompras.getCompAnulado() && !eliminarMotivoAnulacion) {
                invCompras.setCompPendiente(true);
            }
            invCompras.setCompValorRetenido(invCompras.getCompDocumentoTipo().equals("00") ? BigDecimal.ZERO : invCompras.getCompValorRetenido());
            actualizar(invCompras);
            actualizarImagenesCompra(listImagen, invCompras.getInvComprasPK());

            boolean ignorarSeries = false;

            if (!invCompras.getCompAnulado()) {
                if (!desmayorizar && listaInvDetalle != null) {
                    for (int i = 0; i < listaInvDetalle.size(); i++) {
                        listaInvDetalle.get(i).setDetPendiente(true);
                        listaInvDetalle.get(i).setInvCompras(invCompras);
                        //************SERIES*********************
                        if (listaInvDetalle.get(i).getDetSecuencial() != null && !ignorarSeries) {
                            List<InvComprasDetalleSeries> seriesEnLaBase = comprasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(listaInvDetalle.get(i).getDetSecuencial());
                            if (seriesEnLaBase != null && !seriesEnLaBase.isEmpty()) {
                                List<InvComprasDetalleSeries> seriesEntrantes = listaInvDetalle.get(i).getInvComprasDetalleSeriesList();
                                if (seriesEntrantes == null) {
                                    seriesEntrantes = new ArrayList<>();
                                }
                                seriesEnLaBase.removeAll(seriesEntrantes);
                                seriesEnLaBase.forEach((serie) -> {
                                    comprasDetalleSeriesDao.eliminar(serie);
                                });
                            }
                        }
                        //FIN SERIES
                        comprasDetalleDao.saveOrUpdate(listaInvDetalle.get(i));
                    }
                }
            }

            if (!listaInvDetalleEliminar.isEmpty()) {
                for (int i = 0; i < listaInvDetalleEliminar.size(); i++) {
                    listaInvDetalleEliminar.get(i).setInvCompras(invCompras);
                    comprasDetalleDao.eliminar(listaInvDetalleEliminar.get(i));
                }
            }
            if (!invCompras.getCompDocumentoTipo().equals("00") && anxCompra != null) {

                compraDao.saveOrUpdate(anxCompra);
                if (anxCompraFormaPago != null) {
                    for (int i = 0; i < anxCompraFormaPago.size(); i++) {
                        compraFormaPagoDao.saveOrUpdate(anxCompraFormaPago.get(i));
                    }
                }
                if (anxCompraFormaPagoEliminar != null) {
                    for (int i = 0; i < anxCompraFormaPagoEliminar.size(); i++) {
                        compraFormaPagoDao.eliminar(anxCompraFormaPagoEliminar.get(i));
                    }
                }
                if (anxCompraDetalle != null) {
                    for (int i = 0; i < anxCompraDetalle.size(); i++) {
                        compraDetalleDao.saveOrUpdate(anxCompraDetalle.get(i));
                    }
                    for (int i = 0; i < anxCompraDividendos.size(); i++) {
                        compraDividendoDao.saveOrUpdate(anxCompraDividendos.get(i));
                    }
                }

                if (invCompras.getCompDocumentoTipo().equals("41")) {
                    if (anxCompraReembolsos != null) {
                        for (int i = 0; i < anxCompraReembolsos.size(); i++) {
                            compraReembolsoDao.saveOrUpdate(anxCompraReembolsos.get(i));
                        }
                    }
                    if (anxCompraReembolsoEliminar != null) {
                        for (int i = 0; i < anxCompraReembolsoEliminar.size(); i++) {
                            if (anxCompraReembolsoEliminar.get(i).getReembSecuencial() != null
                                    && anxCompraReembolsoEliminar.get(i).getReembSecuencial() != 0) {
                                compraReembolsoDao.eliminar(anxCompraReembolsoEliminar.get(i));
                            }

                        }
                    }
                }
            }

            if (conContable != null) {
                contableAuxDao.saveOrUpdate(conContable);
            }

            if (sisSuceso != null) {
                sucesoDao.insertar(sisSuceso);
            }

            if (!listaSisSuceso.isEmpty()) {
                for (int i = 0; i < listaSisSuceso.size(); i++) {
                    sucesoDao.saveOrUpdate(listaSisSuceso.get(i));
                }
            }
            if (invComprasMotivoAnulacion != null) {
                if (eliminarMotivoAnulacion) {
                    // comprasMotivoAnuladoDao.eliminar(invComprasMotivoAnulacion);
                } else {
                    comprasMotivoAnuladoDao.insertar(invComprasMotivoAnulacion);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean modificarInvComprasInventario(InvCompras invCompras, List<InvComprasDetalle> listaInvDetalle, List<InvComprasDetalle> listaInvDetalleEliminar, AnxCompra anxCompra, List<AnxCompraDetalle> anxCompraDetalle,
            List<AnxCompraDetalle> anxCompraDetalleEliminar, List<AnxCompraDividendo> anxCompraDividendos, List<AnxCompraReembolso> anxCompraReembolsos, List<AnxCompraReembolso> anxCompraReembolsoEliminar,
            List<AnxCompraFormaPago> anxCompraFormaPago, List<AnxCompraFormaPago> anxCompraFormaPagoEliminar, SisSuceso sisSuceso, List<SisSuceso> listaSisSuceso, ConContable conContable,
            InvComprasMotivoAnulacion invComprasMotivoAnulacion, boolean eliminarMotivoAnulacion, boolean desmayorizar, List<InvAdjuntosCompras> listImagen, List<InvComprasDetalleImb> listaCompraImb,
            List<InvComprasDetalleImb> listaCompraImbEliminar,
            List<InvComprasLiquidacion> listInvComprasLiquidacion, List<InvComprasLiquidacion> listInvComprasLiquidacionEliminar) throws Exception {
        List<InvComprasDetalle> listadoCopia = listaInvDetalle.stream().collect(Collectors.toList());
        for (int i = 0; i < listadoCopia.size(); i++) {
            listadoCopia.get(i).setInvCompras(null);
        }

        if (anxCompra != null) {
            String delete_anx_compra_detalle = "DELETE FROM anexo.anx_compra_detalle WHERE comp_empresa='" + anxCompra.getAnxCompraPK().getCompEmpresa()
                    + "' AND comp_periodo='" + anxCompra.getAnxCompraPK().getCompPeriodo()
                    + "' AND comp_motivo='" + anxCompra.getAnxCompraPK().getCompMotivo()
                    + "' AND comp_numero='" + anxCompra.getAnxCompraPK().getCompNumero() + "'";
            String delete_anx_compra_dividendo = "DELETE FROM anexo.anx_compra_dividendo WHERE comp_empresa='" + anxCompra.getAnxCompraPK().getCompEmpresa()
                    + "' AND comp_periodo='" + anxCompra.getAnxCompraPK().getCompPeriodo()
                    + "' AND comp_motivo='" + anxCompra.getAnxCompraPK().getCompMotivo()
                    + "' AND comp_numero='" + anxCompra.getAnxCompraPK().getCompNumero() + "'";
            genericSQLDao.ejecutarSQL(delete_anx_compra_detalle);
            genericSQLDao.ejecutarSQL(delete_anx_compra_dividendo);

            boolean esAgente = false;
            SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorIdEvict(SisEmpresaParametros.class, invCompras.getInvComprasPK().getCompEmpresa());
            if (parametros != null && parametros.getParAgenteRetencion() != null && !parametros.getParAgenteRetencion().isEmpty()) {
                esAgente = true;
            }
            if (anxCompraDetalle.isEmpty() && !esAgente) {
                anxCompra.setCompRetencionFechaEmision(invCompras.getCompFecha());
                anxCompra.setCompBase0(invCompras.getCompBase0());
                anxCompra.setCompBaseimponible(invCompras.getCompBaseImponible());
                anxCompra.setCompBasenoobjetoiva(invCompras.getCompBaseNoObjeto());
                anxCompra.setCompMontoice(invCompras.getCompIce());
                anxCompra.setCompMontoiva(invCompras.getCompMontoIva());
            }
        } else {
            //No hay retención!
        }

        if (!invCompras.getCompAnulado() && !eliminarMotivoAnulacion) {
            invCompras.setCompPendiente(true);
        }
        invCompras.setCompValorRetenido(invCompras.getCompDocumentoTipo().equals("00") ? BigDecimal.ZERO : invCompras.getCompValorRetenido());
        actualizar(invCompras);
        actualizarImagenesCompra(listImagen, invCompras.getInvComprasPK());

        boolean ignorarSeries = false;

        if (!invCompras.getCompAnulado()) {
            if (!desmayorizar && listaInvDetalle != null) {
                for (int i = 0; i < listaInvDetalle.size(); i++) {
                    listaInvDetalle.get(i).setDetPendiente(true);
                    listaInvDetalle.get(i).setInvCompras(invCompras);
                    //************SERIES*********************
                    if (listaInvDetalle.get(i).getDetSecuencial() != null && !ignorarSeries) {
                        List<InvComprasDetalleSeries> seriesEnLaBase = comprasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(listaInvDetalle.get(i).getDetSecuencial());
                        if (seriesEnLaBase != null && !seriesEnLaBase.isEmpty()) {
                            List<InvComprasDetalleSeries> seriesEntrantes = listaInvDetalle.get(i).getInvComprasDetalleSeriesList();
                            if (seriesEntrantes == null) {
                                seriesEntrantes = new ArrayList<>();
                            }
                            seriesEnLaBase.removeAll(seriesEntrantes);
                            seriesEnLaBase.forEach((serie) -> {
                                comprasDetalleSeriesDao.eliminar(serie);
                            });
                        }
                    }
                    //FIN SERIES
                    comprasDetalleDao.saveOrUpdate(listaInvDetalle.get(i));
                }
            }
        }
        //IMB COMPRAS
        if (!listaCompraImb.isEmpty()) {
            for (int i = 0; i < listaCompraImb.size(); i++) {
                listaCompraImb.get(i).setInvCompras(invCompras);
                comprasDetalleImbDao.saveOrUpdate(listaCompraImb.get(i));
            }
        }

        if (!listaCompraImbEliminar.isEmpty()) {
            for (int i = 0; i < listaCompraImbEliminar.size(); i++) {
                listaCompraImbEliminar.get(i).setInvCompras(invCompras);
                comprasDetalleImbDao.eliminar(listaCompraImbEliminar.get(i));
            }
        }

        //COMPRAS LIQUIDACION
        if (!listInvComprasLiquidacion.isEmpty()) {
            for (int i = 0; i < listInvComprasLiquidacion.size(); i++) {
                listInvComprasLiquidacion.get(i).setInvCompras(invCompras);
                comprasLiquidacionDao.saveOrUpdate(listInvComprasLiquidacion.get(i));
            }
        }

        if (!listInvComprasLiquidacionEliminar.isEmpty()) {
            for (int i = 0; i < listInvComprasLiquidacionEliminar.size(); i++) {
                listInvComprasLiquidacionEliminar.get(i).setInvCompras(invCompras);
                comprasLiquidacionDao.eliminar(listInvComprasLiquidacionEliminar.get(i));
            }
        }

        //
        if (!listaInvDetalleEliminar.isEmpty()) {
            for (int i = 0; i < listaInvDetalleEliminar.size(); i++) {
                comprasDetalleDao.eliminar(listaInvDetalleEliminar.get(i));
            }
        }
        if (!invCompras.getCompDocumentoTipo().equals("00") && anxCompra != null) {

            compraDao.saveOrUpdate(anxCompra);
            if (anxCompraFormaPago != null) {
                for (int i = 0; i < anxCompraFormaPago.size(); i++) {
                    compraFormaPagoDao.saveOrUpdate(anxCompraFormaPago.get(i));
                }
            }
            if (anxCompraFormaPagoEliminar != null) {
                for (int i = 0; i < anxCompraFormaPagoEliminar.size(); i++) {
                    compraFormaPagoDao.eliminar(anxCompraFormaPagoEliminar.get(i));
                }
            }
            if (anxCompraDetalle != null) {
                for (int i = 0; i < anxCompraDetalle.size(); i++) {
                    compraDetalleDao.saveOrUpdate(anxCompraDetalle.get(i));
                }
                for (int i = 0; i < anxCompraDividendos.size(); i++) {
                    compraDividendoDao.saveOrUpdate(anxCompraDividendos.get(i));
                }
            }

            if (invCompras.getCompDocumentoTipo().equals("41")) {
                if (anxCompraReembolsos != null) {
                    for (int i = 0; i < anxCompraReembolsos.size(); i++) {
                        compraReembolsoDao.saveOrUpdate(anxCompraReembolsos.get(i));
                    }
                }
                if (anxCompraReembolsoEliminar != null) {
                    for (int i = 0; i < anxCompraReembolsoEliminar.size(); i++) {
                        if (anxCompraReembolsoEliminar.get(i).getReembSecuencial() != null
                                && anxCompraReembolsoEliminar.get(i).getReembSecuencial() != 0) {
                            compraReembolsoDao.eliminar(anxCompraReembolsoEliminar.get(i));
                        }

                    }
                }
            }
        }

        if (conContable != null) {
            contableAuxDao.saveOrUpdate(conContable);
        }

        if (sisSuceso != null) {
            sucesoDao.insertar(sisSuceso);
        }

        if (!listaSisSuceso.isEmpty()) {
            for (int i = 0; i < listaSisSuceso.size(); i++) {
                sucesoDao.saveOrUpdate(listaSisSuceso.get(i));
            }
        }
        if (invComprasMotivoAnulacion != null) {
            if (eliminarMotivoAnulacion) {
                // comprasMotivoAnuladoDao.eliminar(invComprasMotivoAnulacion);
            } else {
                comprasMotivoAnuladoDao.insertar(invComprasMotivoAnulacion);
            }
        }
        //suceso personalizado
        SisSucesoCompra sucesoCompra = new SisSucesoCompra();
        InvCompras copia = ConversionesInventario.convertirInvCompras_InvCompras(invCompras);
        copia.setInvComprasDetalleList(listadoCopia);
        String json = UtilsJSON.objetoToJson(copia);
        sucesoCompra.setSusJson(json);
        sucesoCompra.setSisSuceso(sisSuceso);
        sucesoCompra.setInvCompras(copia);
        sucesoCompraDao.insertar(sucesoCompra);
        return true;
    }

    @Override
    public boolean modificarConContableCompras(ConContable conContable, List<ConDetalle> listaConDetalle,
            InvCompras invCompras, SisSuceso sisSuceso) throws Exception {
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            detalleDao.actualizar(listaConDetalle.get(i));
        }
        actualizar(invCompras);

        contableAuxDao.actualizar(conContable);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarConContableComprasMayorizar(ConContable conContable, List<ConDetalle> listaConDetalle,
            List<ConDetalle> listaConDetalleEliminar, InvCompras invCompras, SisSuceso sisSuceso) throws Exception {
        saveOrUpdate(invCompras);
        contableAuxDao.actualizar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setDetSecuencia(Long.valueOf("0"));
            listaConDetalle.get(i).setConContable(conContable);
            detalleDao.saveOrUpdate(listaConDetalle.get(i));
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public int buscarConteoUltimaNumeracionCompra(String empCodigo, String perCodigo, String motCodigo)
            throws Exception {
        String sql = "SELECT num_secuencia FROM inventario.inv_compras_numeracion WHERE num_empresa = ('" + empCodigo
                + "') AND num_periodo = " + "('" + perCodigo + "') AND num_motivo = ('" + motCodigo + "')";
        Object obj = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return new Integer(String.valueOf(UtilsConversiones.convertir(obj)));
        }
        return 0;
    }

    @Override
    public Object[] getCompra(String empresa, String periodo, String conTipo, String numero) {
        String sql = "select comp_empresa, comp_periodo, comp_motivo, comp_numero "
                + "from inventario.inv_compras where con_empresa='" + empresa + "' AND " + "con_periodo='" + periodo
                + "' AND con_tipo='" + conTipo + "' AND con_numero='" + numero + "'";
        return (Object[]) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public String getConteoNumeroFacturaCompra(String empresaCodigo, String provCodigo, String compDocumentoTipo,
            String compDocumentoNumero) throws Exception {
        String retorno = "";
        String empresa = "";
        String periodo = "";
        String motivo = "";
        String numero = "";
        compDocumentoNumero = compDocumentoNumero == null ? "999-999-999999999" : compDocumentoNumero;
        String sql = "SELECT comp_empresa, comp_periodo, "
                + "comp_motivo, comp_numero FROM inventario.inv_compras WHERE comp_empresa = ('" + empresaCodigo + "') "
                + "AND prov_codigo = ('" + provCodigo + "') AND comp_documento_tipo = ('" + compDocumentoTipo + "') "
                + "AND comp_documento_numero = ('" + compDocumentoNumero + "')";
        try {
            Object[] array = (Object[]) genericSQLDao.obtenerPorSql(sql).get(0);
            if (array != null) {
                empresa = array[0].toString().trim();
                periodo = array[1].toString().trim();
                motivo = array[2].toString().trim();
                numero = array[3].toString().trim();
                retorno = empresa + periodo + motivo + numero;
            } else {
                retorno = "";
            }
        } catch (IndexOutOfBoundsException e) {
            retorno = "";
        }
        return retorno;
    }

    @Override
    public BigDecimal getPrecioProductoUltimaCompra(String empresa, String produCodigo) throws Exception {
        String sql = "SELECT det_precio FROM inventario.inv_compras_detalle WHERE comp_empresa= '" + empresa + "' "
                + "AND pro_codigo_principal = '" + produCodigo + "' ORDER BY comp_periodo DESC limit 1";
        return (BigDecimal) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public InvCompraCabeceraTO getInvCompraCabeceraTO(String empresa, String periodo, String motivo,
            String numeroCompra) throws Exception {
        String sql = "SELECT comp_documento_tipo, comp_documento_numero, "
                + "SUBSTRING(cast(comp_fecha as TEXT), 1, 10) as comp_fecha, "
                + "SUBSTRING(cast(comp_fecha_vencimiento as TEXT), 1, 10) as comp_fecha_vencimiento, "
                + "comp_iva_vigente, comp_observaciones, comp_electronica, comp_pendiente, "
                + "comp_revisado, comp_anulado, comp_forma_pago, comp_base0, comp_base_imponible, "
                + "comp_base_no_objeto, comp_base_exenta, comp_monto_iva, comp_total, "
                + "comp_valor_retenido, comp_saldo, prov_codigo, sec_codigo, con_periodo, "
                + "con_tipo, con_numero, usr_codigo, cast(usr_fecha_inserta as TEXT), "
                + "comp_retencion_asumida, comp_documento_forma_pago, anu_comentario, comp_activo_fijo, "
                + "comp_importacion, comp_otros_impuestos , comp_ice, comp_propina, "
                + "oc_empresa, oc_sector , oc_motivo, oc_numero, comp_programada,"
                + "comp_usuario_aprueba_pago, comp_fecha_aprueba_pago, comp_imb_facturado, comp_transportista_ruc,fecha_ultima_validacion_sri,comp_saldo_importado , oc_orden_pedido, cta_codigo, fp_secuencial, comp_guia_compra, bod_codigo, comp_es_imb, inv_compras.comp_motivo, inv_compras.comp_periodo, inv_compras.comp_numero "
                + "FROM inventario.inv_compras LEFT JOIN inventario.inv_compras_motivo_anulacion "
                + "ON inv_compras.comp_empresa = inv_compras_motivo_anulacion.comp_empresa AND "
                + "inv_compras.comp_periodo = inv_compras_motivo_anulacion.comp_periodo AND "
                + "inv_compras.comp_motivo  = inv_compras_motivo_anulacion.comp_motivo  AND "
                + "inv_compras.comp_numero  = inv_compras_motivo_anulacion.comp_numero "
                + "WHERE (inv_compras.comp_empresa = '" + empresa + "') AND " + "inv_compras.comp_periodo = ('"
                + periodo + "') AND inv_compras.comp_motivo = ('" + motivo + "') " + "AND inv_compras.comp_numero = '"
                + numeroCompra + "'";

        List<InvCompraCabeceraTO> lista = genericSQLDao.obtenerPorSql(sql, InvCompraCabeceraTO.class);
        return lista.isEmpty() == true ? null : lista.get(0);
    }

    @Override
    public List<InvSecuenciaComprobanteTO> getInvSecuenciaComprobanteTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT inv_compras.comp_empresa || '|' || " + "inv_compras.comp_periodo || '|' || "
                + "inv_compras.comp_motivo || '|' || " + "inv_compras.comp_numero sec_compra, "
                + "CASE WHEN comp_anulado THEN NULL ELSE comp_fecha END sec_fecha, "
                + "CASE WHEN comp_anulado THEN 'ANULADO' ELSE comp_documento_numero END sec_comprobante, "
                + "CASE WHEN comp_anulado THEN NULL ELSE comp_total END sec_total_comprobante, "
                + "CASE WHEN comp_anulado THEN NULL ELSE comp_retencionnumero END sec_retencion, "
                + "CASE WHEN comp_anulado THEN NULL ELSE comp_valorretenido END sec_total_retencion, "
                + "CASE WHEN con_contable.con_anulado THEN 'ANULADO' " + "ELSE con_contable.con_empresa || '|' || "
                + "con_contable.con_periodo || '|' || " + "con_contable.con_tipo || '|' || "
                + "con_contable.con_numero END sec_contable "
                + "FROM inventario.inv_compras INNER JOIN anexo.anx_compra "
                + "ON inv_compras.comp_empresa = anx_compra.comp_empresa AND "
                + "inv_compras.comp_periodo = anx_compra.comp_periodo AND "
                + "inv_compras.comp_motivo = anx_compra.comp_motivo AND "
                + "inv_compras.comp_numero = anx_compra.comp_numero " + "LEFT JOIN contabilidad.con_contable "
                + "ON inv_compras.con_empresa = con_contable.con_empresa AND "
                + "inv_compras.con_periodo = con_contable.con_periodo AND "
                + "inv_compras.con_tipo = con_contable.con_tipo AND "
                + "inv_compras.con_numero = con_contable.con_numero " + "WHERE inv_compras.comp_empresa='" + empresa
                + "' AND comp_fecha>=" + fechaDesde + " AND comp_fecha<=" + fechaHasta + " ORDER BY 1;";

        return genericSQLDao.obtenerPorSql(sql, InvSecuenciaComprobanteTO.class);
    }

    @Override
    public List<InvComprasPorPeriodoTO> getComprasPorPeriodo(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, boolean chk) throws Exception {
        fechaInicio = fechaInicio.isEmpty() ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin.isEmpty() ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector.isEmpty() ? null : "'" + codigoSector + "'";
        String sql = "SELECT * FROM inventario.fun_compras_consolidando_productos_mensual('" + empresa + "', "
                + codigoSector + ", " + fechaInicio + ", " + fechaFin + ", " + chk + ");";
        return genericSQLDao.obtenerPorSql(sql, InvComprasPorPeriodoTO.class);
    }

    @Override
    public List<AnxDiferenciasTributacionTO> listarDiferenciasTributacion(String empresa, String fechaInicio, String fechaFin) throws Exception {
        fechaInicio = fechaInicio.isEmpty() ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin.isEmpty() ? null : "'" + fechaFin + "'";
        String sql = "SELECT * FROM anexo.fun_diferencias_tributacion('" + empresa + "', " + fechaInicio + ", " + fechaFin + ");";
        return genericSQLDao.obtenerPorSql(sql, AnxDiferenciasTributacionTO.class);
    }

    @Override
    public List<InvListaConsultaCompraTO> getFunComprasListado(String empresa, String fechaDesde, String fechaHasta,
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
        String sql = "SELECT * FROM inventario.fun_compras_listado('" + empresa + "', null, null, null) "
                + "WHERE comp_fecha >= " + fechaDesde + " AND comp_fecha <= " + fechaHasta + " "
                + "AND comp_pendiente = " + pendiente + " OR comp_anulado = " + anulado;
        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaCompraTO.class);
    }

    @Override
    public List<InvFunComprasConsolidandoProductosTO> getInvFunComprasConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega, String proveedor) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        bodega = bodega == null ? bodega : "'" + bodega + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        String sql = "SELECT * FROM inventario.fun_compras_consolidando_productos('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + ", " + bodega + ", " + proveedor + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunComprasConsolidandoProductosTO.class);
    }

    @Override
    public List<InvFunComprasVsVentasTonelajeTO> listarComprasVsVentasTonelaje(String empresa, String desde, String hasta, String sector, String bodega, String proveedor) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        bodega = bodega == null ? bodega : "'" + bodega + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        String sql = "SELECT * FROM inventario.fun_compras_vs_ventas_tonelaje('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + ", " + bodega + ", " + proveedor + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunComprasVsVentasTonelajeTO.class);
    }

    @Override
    public List<InvListaConsultaCompraTO> getListaInvConsultaCompra(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros, Boolean listarImb) throws Exception {
        String limit = "";
        String busquedaSql = null;
        String motivoSql = null;
        String periodoSql = null;

        if (busqueda != null && !busqueda.equalsIgnoreCase("")) {
            busquedaSql = "'" + busqueda + "'";
        }

        if (motivo != null && !motivo.equalsIgnoreCase("")) {
            motivoSql = "'" + motivo + "'";
        }

        if (periodo != null && !periodo.equalsIgnoreCase("")) {
            periodoSql = "'" + periodo + "'";
        }

        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_compras_listado('" + empresa + "'," + periodoSql + "," + motivoSql + ", " + busquedaSql + ", " + listarImb + ")" + limit;

        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaCompraTO.class);
    }

    @Override
    public List<InvListaConsultaCompraTO> obtenerListadoIMBPendientes(String empresa, String periodo, String motivo, String proveedor, String producto, String fechaInicio, String fechaFin) throws Exception {
        String productoSql = "";
        String proveedorSql = "";
        String motivoSql = "";
        String periodoSql = "";

        if (periodo != null && !periodo.equalsIgnoreCase("")) {
            periodoSql = " AND c.comp_periodo = '" + periodo + "'";
        }
        if (motivo != null && !motivo.equalsIgnoreCase("")) {
            motivoSql = " AND c.comp_motivo = '" + motivo + "'";
        }
        if (proveedor != null && !proveedor.equalsIgnoreCase("")) {
            proveedorSql = " AND c.prov_codigo = '" + proveedor + "'";
        }
        if (producto != null && !producto.equalsIgnoreCase("")) {
            productoSql = " AND prod.pro_codigo_principal = '" + producto + "'";
        }

        String sql = "SELECT distinct on (c.comp_empresa, c.comp_periodo, c.comp_motivo, c.comp_numero)"
                + "     ROW_NUMBER() OVER() AS id, "
                + "     CASE WHEN comp_anulado THEN 'ANULADO' ELSE CASE WHEN comp_pendiente THEN 'PENDIENTE' ELSE ' ' END END comp_status, "
                + "     comp_documento_numero, comp_fecha, comp_documento_tipo, "
                + "     p.prov_codigo comp_proveedor_codigo, p.prov_razon_social comp_proveedor_razon_social, comp_base0,"
                + "     comp_base_imponible comp_baseimponible, comp_monto_iva comp_montoiva, comp_total, comp_forma_pago, comp_observaciones,"
                + "     (c.comp_periodo || '|' || c.comp_motivo || '|' || c.comp_numero) AS comp_numero,"
                + "     (con_periodo || '|' || con_tipo || '|' || con_numero) comp_contable,"
                + "     CASE WHEN a.comp_numero IS NULL THEN false ELSE true END comp_datos_adjuntos, "
                + "     CASE WHEN c.fp_secuencial IS NULL THEN null ELSE "
                + "     (SELECT fp_tipo_principal FROM inventario.inv_compras_forma_pago WHERE inv_compras_forma_pago.fp_secuencial = c.fp_secuencial)END comp_tipo_fp,"
                + "     c.comp_usuario_aprueba_pago, '' comp_clave_acceso_externa"
                + "     FROM inventario.inv_compras c INNER JOIN inventario.inv_proveedor p"
                + "	  ON c.prov_empresa = p.prov_empresa AND "
                + "	     c.prov_codigo = p.prov_codigo "
                + "	  LEFT JOIN inventario.inv_compras_datos_adjuntos a "
                + "	  ON c.comp_empresa = a.comp_empresa AND "
                + "	     c.comp_periodo = a.comp_periodo AND "
                + "	     c.comp_motivo = a.comp_motivo AND "
                + "	     c.comp_numero = a.comp_numero "
                + "       LEFT JOIN inventario.inv_compras_detalle compDet"
                + "       ON c.comp_empresa = compDet.comp_empresa AND "
                + "          c.comp_periodo = compDet.comp_periodo AND "
                + "          c.comp_motivo = compDet.comp_motivo AND "
                + "          c.comp_numero = compDet.comp_numero"
                + "       LEFT JOIN inventario.inv_producto prod"
                + "       ON prod.pro_empresa = compDet.pro_empresa AND"
                + "          prod.pro_codigo_principal = compDet.pro_codigo_principal"
                + "       WHERE c.comp_empresa = '" + empresa + "' "
                + "       AND comp_fecha BETWEEN '" + fechaInicio + "' AND '" + fechaFin + "'"
                + "       AND ((c.comp_empresa || '|' || c.comp_motivo || '|' || c.comp_periodo || '|' || c.comp_numero) not in (select (comp_imb_empresa || '|' || comp_imb_motivo || '|' || comp_imb_periodo || '|' || comp_imb_numero) from inventario.inv_compras_detalle_imb  imb WHERE imb.comp_empresa = '" + empresa + "'" + motivoSql + periodoSql + "))"
                + periodoSql + motivoSql + proveedorSql + productoSql + " AND c.comp_documento_tipo = '00' AND NOT(c.comp_pendiente or c.comp_anulado)";

        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaCompraTO.class);
    }

    @Override
    public List<InvListaConsultaCompraTO> listarComprasPorOrdenCompra(String empresa, String sector, String motivo, String numero) throws Exception {
        String sql = "SELECT distinct on (c.comp_empresa, c.comp_periodo, c.comp_motivo, c.comp_numero) "
                + "	ROW_NUMBER() OVER() AS id, "
                + "     CASE WHEN comp_anulado THEN 'ANULADO' ELSE CASE WHEN comp_pendiente THEN 'PENDIENTE' ELSE ' ' END END comp_status, "
                + "     comp_documento_numero, comp_fecha, comp_documento_tipo, "
                + "     p.prov_codigo comp_proveedor_codigo, p.prov_razon_social comp_proveedor_razon_social, comp_base0, "
                + "     comp_base_imponible comp_baseimponible, comp_monto_iva comp_montoiva, comp_total, comp_forma_pago, comp_observaciones, "
                + "     (c.comp_periodo || '|' || c.comp_motivo || '|' || c.comp_numero) AS comp_numero, "
                + "     (con_periodo || '|' || con_tipo || '|' || con_numero) comp_contable, "
                + "     CASE WHEN a.comp_numero IS NULL THEN false ELSE true END comp_datos_adjuntos, c.comp_usuario_aprueba_pago, '' comp_clave_acceso_externa "
                + "	FROM inventario.inv_compras c INNER JOIN inventario.inv_proveedor p "
                + "	  ON c.prov_empresa = p.prov_empresa AND "
                + "	     c.prov_codigo = p.prov_codigo "
                + "	  LEFT JOIN inventario.inv_compras_datos_adjuntos a "
                + "	  ON c.comp_empresa = a.comp_empresa AND "
                + "	     c.comp_periodo = a.comp_periodo AND "
                + "	     c.comp_motivo = a.comp_motivo AND "
                + "	     c.comp_numero = a.comp_numero "
                + "	WHERE c.oc_empresa = '" + empresa + "' and c.oc_motivo = '" + motivo + "' and c.oc_sector = '" + sector + "' and c.oc_numero = '" + numero + "' "
                + "     AND not comp_anulado";
        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaCompraTO.class);
    }

    @Override
    public List<InvConsultaCompraTO> listarComprasPorProveedor(String empresa, String periodo, String motivo, String numero, String provCodigo) throws Exception {

        String motivoSql = "";
        String periodoSql = "";
        String numeroSql = "";

        if (periodo != null && !periodo.equalsIgnoreCase("")) {
            periodoSql = " AND c.comp_periodo = '" + periodo + "'";
        }

        if (motivo != null && !motivo.equalsIgnoreCase("")) {
            motivoSql = " AND c.comp_motivo = '" + motivo + "'";
        }

        if (numero != null && !numero.equalsIgnoreCase("")) {
            numeroSql = " AND c.comp_numero = '" + numero + "'";
        }

        String sql = "SELECT distinct on (c.comp_empresa, c.comp_periodo, c.comp_motivo, c.comp_numero) "
                + "	ROW_NUMBER() OVER() AS id, "
                + "     CASE WHEN comp_anulado THEN 'ANULADO' ELSE CASE WHEN comp_pendiente THEN 'PENDIENTE' ELSE ' ' END END comp_status, "
                + "     comp_documento_numero, comp_fecha, "
                + "     p.prov_codigo comp_proveedor_codigo, p.prov_razon_social comp_proveedor_razon_social, comp_base0, "
                + "     comp_base_imponible comp_baseimponible, comp_monto_iva comp_montoiva, comp_total, comp_forma_pago, comp_observaciones, "
                + "     c.comp_numero comp_numero,  (oc_empresa || '|' || oc_motivo || '|' || oc_sector || '|' || oc_numero) pk_compras, "
                + "     (con_periodo || '|' || con_tipo || '|' || con_numero) comp_contable, "
                + "     CASE WHEN a.comp_numero IS NULL THEN false ELSE true END comp_datos_adjuntos, c.comp_usuario_aprueba_pago, c.comp_periodo comp_periodo, c.comp_motivo comp_motivo, c.comp_documento_tipo"
                + "	FROM inventario.inv_compras c INNER JOIN inventario.inv_proveedor p "
                + "	  ON c.prov_empresa = p.prov_empresa AND "
                + "	     c.prov_codigo = p.prov_codigo "
                + "	  LEFT JOIN inventario.inv_compras_datos_adjuntos a "
                + "	  ON c.comp_empresa = a.comp_empresa AND "
                + "	     c.comp_periodo = a.comp_periodo AND "
                + "	     c.comp_motivo = a.comp_motivo AND "
                + "	     c.comp_numero = a.comp_numero "
                + "	WHERE c.comp_empresa = '" + empresa + "' and c.prov_codigo = '" + provCodigo + "' and NOT (c.comp_pendiente OR c.comp_anulado OR c.comp_imb_facturado)" + periodoSql + motivoSql + numeroSql;
        return genericSQLDao.obtenerPorSql(sql, InvConsultaCompraTO.class);
    }

    @Override
    public List<InvConsultaCompraTO> listarComprasPorProveedorSoloNotaEntrega(String empresa, String periodo, String motivo, String numero, String provCodigo) throws Exception {
        String motivoSql = "";
        String motivoImbSql = "";
        String periodoSql = "";
        String periodoImbSql = "";
        String numeroSql = "";

        if (periodo != null && !periodo.equalsIgnoreCase("")) {
            periodoSql = " AND c.comp_periodo = '" + periodo + "'";
            periodoImbSql = " AND c.comp_periodo = '" + periodo + "'";
        }

        if (motivo != null && !motivo.equalsIgnoreCase("")) {
            motivoSql = " AND c.comp_motivo = '" + motivo + "'";
            motivoImbSql = " AND c.comp_motivo = '" + motivo + "'";
        }

        if (numero != null && !numero.equalsIgnoreCase("")) {
            numeroSql = " AND c.comp_numero = '" + numero + "'";
        }

        String sql = "SELECT distinct on (c.comp_empresa, c.comp_periodo, c.comp_motivo, c.comp_numero) "
                + "	ROW_NUMBER() OVER() AS id, "
                + "     CASE WHEN comp_anulado THEN 'ANULADO' ELSE CASE WHEN comp_pendiente THEN 'PENDIENTE' ELSE ' ' END END comp_status, "
                + "     comp_documento_numero, comp_fecha, "
                + "     p.prov_codigo comp_proveedor_codigo, p.prov_razon_social comp_proveedor_razon_social, comp_base0, "
                + "     comp_base_imponible comp_baseimponible, comp_monto_iva comp_montoiva, comp_total, comp_forma_pago, comp_observaciones, "
                + "     c.comp_numero comp_numero, (oc_empresa || '|' || oc_motivo || '|' || oc_sector || '|' || oc_numero) pk_compras,"
                + "     (con_periodo || '|' || con_tipo || '|' || con_numero) comp_contable, "
                + "     CASE WHEN a.comp_numero IS NULL THEN false ELSE true END comp_datos_adjuntos, c.comp_usuario_aprueba_pago, c.comp_periodo comp_periodo, c.comp_motivo comp_motivo, c.comp_documento_tipo"
                + "	FROM inventario.inv_compras c INNER JOIN inventario.inv_proveedor p "
                + "	  ON c.prov_empresa = p.prov_empresa AND "
                + "	     c.prov_codigo = p.prov_codigo "
                + "	  LEFT JOIN inventario.inv_compras_datos_adjuntos a "
                + "	  ON c.comp_empresa = a.comp_empresa AND "
                + "	     c.comp_periodo = a.comp_periodo AND "
                + "	     c.comp_motivo = a.comp_motivo AND "
                + "	     c.comp_numero = a.comp_numero "
                + "       INNER JOIN inventario.inv_compras_motivo m "
                + "         ON c.comp_empresa = m.cm_empresa AND "
                + "         c.comp_motivo = m.cm_codigo "
                + "	WHERE c.comp_empresa = '" + empresa
                + "' and c.prov_codigo = '" + provCodigo + "' and  m.cm_imb = true "
                + " and ((c.comp_empresa || '|' || c.comp_motivo || '|' || c.comp_periodo || '|' || c.comp_numero) not in (select (comp_imb_empresa || '|' || comp_imb_motivo || '|' || comp_imb_periodo || '|' || comp_imb_numero) from inventario.inv_compras_detalle_imb  imb WHERE imb.comp_empresa = '" + empresa + "'" + motivoImbSql + periodoImbSql + "))"
                + periodoSql + motivoSql + numeroSql + " AND c.comp_documento_tipo = '00' AND NOT(c.comp_pendiente or c.comp_anulado)";
        return genericSQLDao.obtenerPorSql(sql, InvConsultaCompraTO.class);
    }

    @Override
    public List<InvConsultaCompraTO> listarComprasImb(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT distinct on (c.comp_empresa, c.comp_periodo, c.comp_motivo, c.comp_numero) "
                + "	ROW_NUMBER() OVER() AS id, "
                + "     CASE WHEN comp_anulado THEN 'ANULADO' ELSE CASE WHEN comp_pendiente THEN 'PENDIENTE' ELSE ' ' END END comp_status, "
                + "     comp_documento_numero, comp_fecha, "
                + "     p.prov_codigo comp_proveedor_codigo, p.prov_razon_social comp_proveedor_razon_social, comp_base0, "
                + "     comp_base_imponible comp_baseimponible, comp_monto_iva comp_montoiva, comp_total, comp_forma_pago, comp_observaciones, "
                + "     c.comp_numero comp_numero,  (oc_empresa || '|' || oc_motivo || '|' || oc_sector || '|' || oc_numero) pk_compras, "
                + "     (con_periodo || '|' || con_tipo || '|' || con_numero) comp_contable, "
                + "     CASE WHEN a.comp_numero IS NULL THEN false ELSE true END comp_datos_adjuntos, c.comp_usuario_aprueba_pago, c.comp_periodo comp_periodo, c.comp_motivo comp_motivo, c.comp_documento_tipo"
                + "	FROM inventario.inv_compras c INNER JOIN inventario.inv_compras_detalle_imb compImb2 "
                + "	   ON compImb2.comp_imb_empresa = c.comp_empresa AND "
                + "	      compImb2.comp_imb_periodo = c.comp_periodo AND "
                + "	      compImb2.comp_imb_motivo = c.comp_motivo AND "
                + "           compImb2.comp_imb_numero = c.comp_numero "
                + "       INNER JOIN inventario.inv_proveedor p"
                + "	  ON c.prov_empresa = p.prov_empresa AND "
                + "	     c.prov_codigo = p.prov_codigo "
                + "	  LEFT JOIN inventario.inv_compras_datos_adjuntos a "
                + "	  ON c.comp_empresa = a.comp_empresa AND "
                + "	     c.comp_periodo = a.comp_periodo AND "
                + "	     c.comp_motivo = a.comp_motivo AND "
                + "	     c.comp_numero = a.comp_numero "
                + "	WHERE compImb2.comp_empresa = '" + empresa
                + "' and compImb2.comp_periodo = '" + periodo
                + "' and compImb2.comp_motivo = '" + motivo
                + "' and compImb2.comp_numero = '" + numero
                + "'";
        return genericSQLDao.obtenerPorSql(sql, InvConsultaCompraTO.class);
    }

    @Override
    public List<InvListaConsultaCompraTO> listarComprasPorOrdenCompraYProducto(String empresa, String sector, String motivo, String numero, String codigoProducto, int ocSecuencial) throws Exception {
        String sql = "SELECT distinct on (c.comp_empresa, c.comp_periodo, c.comp_motivo, c.comp_numero) "
                + "ROW_NUMBER() OVER() AS id, "
                + "CASE WHEN comp_anulado THEN 'ANULADO' ELSE CASE WHEN comp_pendiente THEN 'PENDIENTE' "
                + "ELSE ' ' END END comp_status, comp_documento_numero, comp_fecha,comp_documento_tipo , "
                + "p.prov_codigo comp_proveedor_codigo, p.prov_razon_social comp_proveedor_razon_social, comp_base0, "
                + "comp_base_imponible comp_baseimponible, comp_monto_iva comp_montoiva, ("
                + "	select det.det_cantidad from inventario.inv_compras_detalle det"
                + "	where c.comp_empresa = det.comp_empresa AND c.comp_periodo = det.comp_periodo AND c.comp_motivo = det.comp_motivo AND c.comp_numero = det.comp_numero"
                + "	and pro_codigo_principal = '" + codigoProducto + "'"
                + ") comp_total, comp_forma_pago, comp_observaciones, "
                + "(c.comp_periodo || '|' || c.comp_motivo || '|' || c.comp_numero) AS comp_numero, "
                + "(con_periodo || '|' || con_tipo || '|' || con_numero) comp_contable, "
                + "CASE WHEN a.comp_numero IS NULL THEN false ELSE true END comp_datos_adjuntos, c.comp_usuario_aprueba_pago, '' comp_clave_acceso_externa, '' comp_tipo_fp, (c.oc_motivo || '|' || c.oc_sector || '|' || c.oc_numero) comp_oc "
                + "FROM inventario.inv_compras c "
                + "INNER JOIN inventario.inv_proveedor p "
                + "ON c.prov_empresa = p.prov_empresa AND c.prov_codigo = p.prov_codigo "
                + "INNER JOIN inventario.inv_compras_detalle cd "
                + "on c.comp_empresa = cd.comp_empresa and c.comp_periodo = cd.comp_periodo and c.comp_motivo = cd.comp_motivo and c.comp_numero = cd.comp_numero "
                + "LEFT JOIN inventario.inv_compras_datos_adjuntos a "
                + "ON c.comp_empresa = a.comp_empresa AND c.comp_periodo = a.comp_periodo AND c.comp_motivo = a.comp_motivo AND c.comp_numero = a.comp_numero "
                + "WHERE cd.pro_codigo_principal = '" + codigoProducto + "' AND c.oc_empresa = '" + empresa + "' "
                + "and c.oc_motivo = '" + motivo + "' and c.oc_sector = '" + sector + "' and c.oc_numero = '" + numero + "' AND cd.det_secuencial_orden_compra =" + ocSecuencial + ";";
        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaCompraTO.class);
    }

    @Override
    public List<InvFunComprasProgramadasListadoTO> listarComprasProgramadas(String empresa, String periodo, String motivo, String desde, String hasta, String nRegistros) throws Exception {
        String limit = "";
        String desdeSql = null;
        String hastaSql = null;
        String motivoSql = null;
        String periodoSql = null;
        if (desde != null && !desde.equalsIgnoreCase("")) {
            desdeSql = "'" + desde + "'";
        }
        if (hasta != null && !hasta.equalsIgnoreCase("")) {
            hastaSql = "'" + hasta + "'";
        }
        if (motivo != null && !motivo.equalsIgnoreCase("")) {
            motivoSql = "'" + motivo + "'";
        }
        if (periodo != null && !periodo.equalsIgnoreCase("")) {
            periodoSql = "'" + periodo + "'";
        }
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_compras_programadas_listado('" + empresa + "'," + periodoSql + "," + motivoSql + ", " + desdeSql + ", " + hastaSql + ")" + limit;
        return genericSQLDao.obtenerPorSql(sql, InvFunComprasProgramadasListadoTO.class);
    }

    @Override
    public List<InvFunComprasTO> getInvFunComprasTO(String empresa, String desde, String hasta, String motivo,
            String proveedor, String documento, String formaPago) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        documento = documento == null ? documento : "'" + documento + "'";
        String sql = "SELECT * FROM inventario.fun_compras('" + empresa + "', " + desde + ", " + hasta + ", " + motivo
                + ", " + proveedor + ", " + documento + ")";
        if (formaPago != null && !formaPago.equals("")) {
            sql = sql + " WHERE comp_forma_pago = '" + formaPago + "'";
        }
        return genericSQLDao.obtenerPorSql(sql, InvFunComprasTO.class);
    }

    @Override
    public List<InvFunComprasTO> getInvFunComprasTOAgrupadoTipoDocumento(String empresa, String desde, String hasta, String motivo, String proveedor, String documento, String formaPago) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        documento = documento == null ? documento : "'" + documento + "'";
        String sql = "SELECT * FROM inventario.fun_compras_agrupado_tipo_documento('" + empresa + "', " + desde + ", " + hasta + ", " + motivo
                + ", " + proveedor + ", " + documento + ")";
        if (formaPago != null && !formaPago.equals("")) {
            sql = sql + " WHERE comp_forma_pago = '" + formaPago + "'";
        }
        return genericSQLDao.obtenerPorSql(sql, InvFunComprasTO.class);
    }

    @Override
    public List<InvFunComprasTO> getInvFunComprasTOAgrupadoTipoContribuyente(String empresa, String desde, String hasta, String motivo, String proveedor, String documento, String formaPago) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        documento = documento == null ? documento : "'" + documento + "'";
        String sql = "SELECT * FROM inventario.fun_compras_agrupado_tipo_contribuyente('" + empresa + "', " + desde + ", " + hasta + ", " + motivo
                + ", " + proveedor + ", " + documento + ")";
        if (formaPago != null && !formaPago.equals("")) {
            sql = sql + " WHERE comp_forma_pago = '" + formaPago + "'";
        }
        return genericSQLDao.obtenerPorSql(sql, InvFunComprasTO.class);
    }

    @Override
    public List<InvFunUltimasComprasTO> getInvFunUltimasComprasTOs(String empresa, String producto) throws Exception {
        producto = producto == null ? "" : "'" + producto + "'";
        String sql = "SELECT * FROM inventario.fun_ultimas_compras('" + empresa + "', " + producto + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunUltimasComprasTO.class);
    }

    @Override
    public List<InvListadoPagosTO> invListadoPagosTO(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        String sql = "SELECT * FROM cartera.fun_obtener_pagos('" + empresa + "', '" + periodo + "', '" + motivo + "', '"
                + numero + "')";
        return genericSQLDao.obtenerPorSql(sql, InvListadoPagosTO.class);
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception {
        String sql = "SELECT comp_pendiente, comp_anulado, comp_activo_fijo as est_bloqueado, comp_revisado as est_generado, false as con_reversado FROM inventario.inv_compras WHERE "
                + "comp_empresa = '" + empresa + "' AND comp_periodo = '" + periodo + "' " + "AND comp_motivo = '"
                + motivoTipo + "' AND comp_numero = '" + numero + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvEstadoCCCVT.class);
    }

    @Override
    public void mayorizarDesmayorizarComprasSql(InvComprasPK invComprasPK, boolean pendiente, boolean revisado, SisSuceso sisSuceso) {
        String sql = "UPDATE inventario.inv_compras SET comp_pendiente=" + pendiente + ", comp_revisado = " + revisado + ","
                + " comp_usuario_aprueba_pago = null, comp_fecha_aprueba_pago = null WHERE comp_empresa='"
                + invComprasPK.getCompEmpresa() + "' and  comp_periodo='" + invComprasPK.getCompPeriodo()
                + "' and comp_motivo='" + invComprasPK.getCompMotivo() + "' and comp_numero='" + invComprasPK.getCompNumero()
                + "';";
        sisSuceso.setSusDetalle("Se " + (pendiente ? "desmayorizo" : "mayorizo") + " la compra: " + invComprasPK.getCompPeriodo() + " | " + invComprasPK.getCompMotivo() + " | " + invComprasPK.getCompNumero());
        sucesoDao.insertar(sisSuceso);
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void anularRestaurarComprasSql(InvComprasPK invComprasPK, boolean anulado, boolean actualizarFechaUltimaValidacionSri, SisSuceso sisSuceso) {
        boolean pendiente = false;
        boolean revisado = false;
        this.mayorizarDesmayorizarComprasSql(invComprasPK, pendiente, revisado, sisSuceso);
        String sql = "";
        if (anulado) {
            String sqlFecha = "";
            if (actualizarFechaUltimaValidacionSri) {
                sqlFecha = " ,fecha_ultima_validacion_sri ='" + UtilsDate.timestamp() + "' ";
            }
            sql = "UPDATE inventario.inv_compras SET comp_anulado=" + anulado + ", oc_orden_pedido = null,oc_empresa = null, oc_motivo = null, oc_sector = null, oc_numero = null, comp_pendiente=" + pendiente + sqlFecha + " WHERE comp_empresa='"
                    + invComprasPK.getCompEmpresa() + "' and  comp_periodo='" + invComprasPK.getCompPeriodo()
                    + "' and comp_motivo='" + invComprasPK.getCompMotivo() + "' and comp_numero='" + invComprasPK.getCompNumero()
                    + "';";

            //Eliminar liquidacion de compras
        } else {
            sql = "UPDATE inventario.inv_compras comp "
                    + "SET comp_anulado=" + anulado
                    + ",comp_pendiente=" + pendiente
                    + ",oc_empresa = CASE WHEN comp.oc_sector is NOT NULL and comp.oc_motivo is NOT NULL and comp.oc_numero is NOT NULL "
                    + "                 THEN CASE WHEN (select count(*) from inventario.inv_compras c "
                    + "	                where c.oc_empresa = comp.oc_empresa and "
                    + "			      c.oc_sector = comp.oc_sector and "
                    + "			      c.oc_motivo = comp.oc_motivo and "
                    + "			      c.oc_numero = comp.oc_numero and not( c.comp_anulado OR c.comp_pendiente)) >0"
                    + "	                     THEN NULL ELSE comp.oc_empresa END "
                    + "                ELSE NULL END, "
                    + "oc_sector = CASE WHEN comp.oc_sector is NOT NULL and comp.oc_motivo is NOT NULL  and comp.oc_numero is NOT NULL \n"
                    + "                 THEN CASE WHEN (select count(*) from inventario.inv_compras c "
                    + "	                 where c.oc_empresa = comp.oc_empresa and "
                    + "			       c.oc_sector = comp.oc_sector and "
                    + "			       c.oc_motivo = comp.oc_motivo and "
                    + "			       c.oc_numero = comp.oc_numero and not( c.comp_anulado OR c.comp_pendiente)) >0"
                    + "	                     THEN NULL ELSE comp.oc_sector END "
                    + "                ELSE NULL END, "
                    + "oc_motivo = CASE WHEN comp.oc_sector is NOT NULL and comp.oc_motivo is NOT NULL  and comp.oc_numero is NOT NULL  \n"
                    + "                 THEN CASE WHEN (select count(*) from inventario.inv_compras c "
                    + "	                 where c.oc_empresa = comp.oc_empresa and "
                    + "			       c.oc_sector = comp.oc_sector and "
                    + "			       c.oc_motivo = comp.oc_motivo and "
                    + "			       c.oc_numero = comp.oc_numero and not( c.comp_anulado OR c.comp_pendiente)) >0 "
                    + "	                     THEN NULL ELSE comp.oc_motivo END "
                    + "                ELSE NULL END, "
                    + "oc_numero = CASE WHEN comp.oc_sector is NOT NULL and comp.oc_motivo is NOT NULL  and comp.oc_numero is NOT NULL \n"
                    + "                 THEN CASE WHEN (select count(*) from inventario.inv_compras c\n"
                    + "	                 where c.oc_empresa = comp.oc_empresa and "
                    + "			       c.oc_sector = comp.oc_sector and "
                    + "			       c.oc_motivo = comp.oc_motivo and "
                    + "			       c.oc_numero = comp.oc_numero and not( c.comp_anulado OR c.comp_pendiente)) >0 "
                    + "	                     THEN NULL ELSE comp.oc_numero END "
                    + "                ELSE NULL END "
                    + " WHERE comp_empresa='" + invComprasPK.getCompEmpresa() + "' and  comp_periodo='" + invComprasPK.getCompPeriodo()
                    + "' and comp_motivo='" + invComprasPK.getCompMotivo() + "' and comp_numero='" + invComprasPK.getCompNumero() + "';";

        }

        sisSuceso.setSusDetalle("Se " + (anulado ? "anuló" : "restauró") + " la compra: " + invComprasPK.getCompPeriodo() + " | " + invComprasPK.getCompMotivo() + " | " + invComprasPK.getCompNumero());
        sucesoDao.insertar(sisSuceso);
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public boolean ordenCompraEstaImportada(InvPedidosOrdenCompraPK pk) {
        String sql = "SELECT COUNT(*)!=0 FROM inventario.inv_compras "
                + "WHERE oc_empresa = '" + pk.getOcEmpresa()
                + "' AND oc_sector = '" + pk.getOcSector()
                + "' AND oc_motivo = '" + pk.getOcMotivo()
                + "' AND oc_numero = '" + pk.getOcNumero() + "' AND NOT( comp_anulado OR comp_pendiente)";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public void guardarImagenesCompra(InvComprasPK invComprasPK, List<InvAdjuntosCompras> listInvAdjuntosCompras) {
        try {
            InvCompras invCompras = obtenerPorId(InvCompras.class, new InvComprasPK(invComprasPK.getCompEmpresa(), invComprasPK.getCompPeriodo(),
                    invComprasPK.getCompMotivo(), invComprasPK.getCompNumero()));
            actualizar(invCompras);
            actualizarImagenesCompra(listInvAdjuntosCompras, invComprasPK);
        } catch (Exception ex) {
            Logger.getLogger(ComprasDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public InvComprasDatosBasicoTO getDatosBasicosCompra(ConContablePK conContablePK) throws Exception {

        String sql = "SELECT comp_empresa, comp_periodo, comp_motivo, comp_numero \n"
                + "FROM inventario.inv_compras \n"
                + "WHERE con_empresa='" + conContablePK.getConEmpresa() + "' AND\n"
                + "      con_periodo='" + conContablePK.getConPeriodo() + "' AND\n"
                + "      con_tipo='" + conContablePK.getConTipo() + "' AND\n"
                + "      con_numero='" + conContablePK.getConNumero() + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvComprasDatosBasicoTO.class);
    }

    @Override
    public List<InvAdjuntosCompras> getAdjuntosCompra(InvComprasPK invComprasPK) throws Exception {

        String sql = "select * from inventario.inv_compras_datos_adjuntos \n"
                + "where comp_empresa = '" + invComprasPK.getCompEmpresa() + "' and\n"
                + "comp_periodo = '" + invComprasPK.getCompPeriodo() + "' and\n"
                + "comp_motivo = '" + invComprasPK.getCompMotivo() + "' and\n"
                + "comp_numero = '" + invComprasPK.getCompNumero() + "';";
        List<InvAdjuntosCompras> imagenes = genericSQLDao.obtenerPorSql(sql, InvAdjuntosCompras.class);
        if (imagenes != null && !imagenes.isEmpty()) {
            for (InvAdjuntosCompras imagen : imagenes) {
                imagen.setInvCompras(new InvCompras(invComprasPK));
                imagen.setAdjArchivo(null);
            }
        }
        return imagenes;
    }

    @Override
    public List<InvAdjuntosCompras> listarImagenesNoMigradas(String empresa) throws Exception {
        String sql = "select * from inventario.inv_compras_datos_adjuntos \n"
                + "where comp_empresa = '" + empresa + "' and\n"
                + " adj_archivo is not null and adj_url_archivo is null;";
        return genericSQLDao.obtenerPorSql(sql, InvAdjuntosCompras.class);
    }

    @Override
    public List<Integer> listaImagenesNoMigradas(String empresa) throws Exception {
        String sql = "select adj_secuencial from inventario.inv_compras_datos_adjuntos \n"
                + "where comp_empresa = '" + empresa + "' and\n"
                + " adj_archivo is not null and adj_url_archivo is null;";
        return genericSQLDao.obtenerPorSql(sql);
    }

    @Override
    public List<Integer> listaImagenesMigradas(String empresa) throws Exception {
        String sql = "select adj_secuencial from inventario.inv_compras_datos_adjuntos \n"
                + "where comp_empresa = '" + empresa + "' and\n"
                + " adj_archivo is not null and adj_url_archivo is not null and not adj_verificado;";
        return genericSQLDao.obtenerPorSql(sql);
    }

    public boolean eliminarAdjuntosCOmprass(InvComprasPK invComprasPK) throws Exception {

        String sql = "delete from inventario.inv_compras_datos_adjuntos \n"
                + "where comp_empresa = '" + invComprasPK.getCompEmpresa() + "' and\n"
                + "comp_periodo = '" + invComprasPK.getCompPeriodo() + "' and\n"
                + "comp_motivo = '" + invComprasPK.getCompMotivo() + "' and\n"
                + "comp_numero = '" + invComprasPK.getCompNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
        return true;
    }

    @Override
    public boolean eliminarLiquidacionCompras(InvComprasPK invComprasPK) throws Exception {

        String sql = "delete from inventario.inv_compras_liquidacion \n"
                + "where comp_empresa = '" + invComprasPK.getCompEmpresa() + "' and\n"
                + "comp_periodo = '" + invComprasPK.getCompPeriodo() + "' and\n"
                + "comp_motivo = '" + invComprasPK.getCompMotivo() + "' and\n"
                + "comp_numero = '" + invComprasPK.getCompNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
        return true;
    }

    public boolean insertarImagenesCompra(List<InvAdjuntosCompras> listado, InvComprasPK pk) throws Exception {
        String bucket = sistemaWebServicio.obtenerRutaImagen(pk.getCompEmpresa());
        Bucket b = AmazonS3Crud.crearBucket(bucket);
        if (b != null) {
            for (InvAdjuntosCompras invAdjunto : listado) {
                if (invAdjunto.getAdjSecuencial() == null && invAdjunto.getAdjArchivo() != null) {
                    String archivo = new String(invAdjunto.getAdjArchivo(), "UTF-8");
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(invAdjunto.getAdjClaveArchivo(), archivo);
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "compras/" + pk.getCompPeriodo() + "/" + pk.getCompMotivo() + "/" + pk.getCompNumero() + "/";
                    invAdjunto.setInvCompras(new InvCompras(pk));
                    invAdjunto.setAdjBucket(bucket);
                    invAdjunto.setAdjClaveArchivo(carpeta + nombre);
                    invAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    invAdjunto.setAdjArchivo(null);
                    invComprasDatosAdjuntosDao.insertar(invAdjunto);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, archivo, combo.getValor());
                }
            }
        } else {
            throw new GeneralException("Error al crear contenedor de imágenes.");
        }
        return true;
    }

    @Override
    public boolean actualizarImagenesCompra(List<InvAdjuntosCompras> listado, InvComprasPK pk) throws Exception {

        SisPeriodo periodo = periodoService.buscarPeriodo(pk.getCompEmpresa(), pk.getCompPeriodo());
        if (periodo != null && !periodo.getPerCerrado()) {
            List<InvAdjuntosCompras> listAdjuntosEnLaBase = listarImagenesDeCompra(pk);
            if (listado != null && !listado.isEmpty()) {
                if (listAdjuntosEnLaBase.size() > 0) {
                    listado.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                        listAdjuntosEnLaBase.removeIf(n -> (n.getAdjSecuencial().equals(item.getAdjSecuencial())));
                    });
                    if (listAdjuntosEnLaBase.size() > 0) {
                        listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                            invComprasDatosAdjuntosDao.eliminar(itemEliminar);
                            AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                        });
                    }
                }
                insertarImagenesCompra(listado, pk);
            }
        } else {
            throw new GeneralException("No se puede adjuntar imágenes debido a que el periodo se encuentra cerrado.");
        }

        return true;
    }

    public List<InvAdjuntosCompras> listarImagenesDeCompra(InvComprasPK pk) throws Exception {
        String sql = "select * from inventario.inv_compras_datos_adjuntos where "
                + "comp_empresa = '" + pk.getCompEmpresa()
                + "' and comp_periodo = '" + pk.getCompPeriodo()
                + "' and comp_motivo = '" + pk.getCompMotivo()
                + "' and comp_numero = '" + pk.getCompNumero() + "' ";
        return genericSQLDao.obtenerPorSql(sql, InvAdjuntosCompras.class);
    }

    @Override
    public boolean guardarClaveAcceso(String codigoEmpresa, String motivo, String numero, String periodo, String claveAcceso, SisInfoTO sisInfoTO) throws Exception {
        String sql = "UPDATE anexo.anx_compra SET comp_clave_acceso_externa='" + claveAcceso + "'  WHERE"
                + " comp_empresa = '" + codigoEmpresa
                + "' AND comp_periodo = '" + periodo
                + "' AND comp_motivo = '" + motivo
                + "' AND comp_numero = '" + numero + "' ";
        genericSQLDao.ejecutarSQL(sql);
        return true;
    }

    @Override
    public boolean crearAjusteInventario(InvCompras invComprasNE, InvCompras invComprasNC, List<InvComprasDetalle> listaInvComprasDetallesNE, List<InvComprasDetalle> listaInvComprasDetallesNC, SisSuceso sisSuceso) throws Exception {
        String rellenarConCeros = "";
        invComprasNE.setConEmpresa(null);
        invComprasNC.setConEmpresa(null);
        int numeracion = buscarConteoUltimaNumeracionCompra(
                invComprasNE.getInvComprasPK().getCompEmpresa(),
                invComprasNE.getInvComprasPK().getCompPeriodo(),
                invComprasNE.getInvComprasPK().getCompMotivo());

        if (listaInvComprasDetallesNE.size() > 0 && listaInvComprasDetallesNC.size() == 0) {
            invComprasNC = null;
        } else {
            if (listaInvComprasDetallesNE.size() == 0 && listaInvComprasDetallesNC.size() > 0) {
                invComprasNE = null;
            }
        }

        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            if (invComprasNE != null && invComprasNC != null) {
                invComprasNE.setInvComprasPK(
                        new InvComprasPK(invComprasNE.getInvComprasPK().getCompEmpresa(),
                                invComprasNE.getInvComprasPK().getCompPeriodo(),
                                invComprasNE.getInvComprasPK().getCompMotivo(),
                                rellenarConCeros + numeracion));

                invComprasNC.setInvComprasPK(
                        new InvComprasPK(invComprasNC.getInvComprasPK().getCompEmpresa(),
                                invComprasNC.getInvComprasPK().getCompPeriodo(),
                                invComprasNC.getInvComprasPK().getCompMotivo(),
                                rellenarConCeros + (numeracion + 1)));
            } else {
                if (invComprasNE != null && invComprasNC == null) {
                    invComprasNE.setInvComprasPK(
                            new InvComprasPK(invComprasNE.getInvComprasPK().getCompEmpresa(),
                                    invComprasNE.getInvComprasPK().getCompPeriodo(),
                                    invComprasNE.getInvComprasPK().getCompMotivo(),
                                    rellenarConCeros + numeracion));
                } else {
                    if (invComprasNE == null && invComprasNC != null) {
                        invComprasNC.setInvComprasPK(
                                new InvComprasPK(invComprasNC.getInvComprasPK().getCompEmpresa(),
                                        invComprasNC.getInvComprasPK().getCompPeriodo(),
                                        invComprasNC.getInvComprasPK().getCompMotivo(),
                                        rellenarConCeros + numeracion));
                    }
                }
            }
        } while (buscarInvCompras(
                invComprasNE != null ? invComprasNE.getInvComprasPK().getCompEmpresa() : invComprasNC.getInvComprasPK().getCompEmpresa(),
                invComprasNE != null ? invComprasNE.getInvComprasPK().getCompPeriodo() : invComprasNC.getInvComprasPK().getCompPeriodo(),
                invComprasNE != null ? invComprasNE.getInvComprasPK().getCompMotivo() : invComprasNC.getInvComprasPK().getCompMotivo(),
                rellenarConCeros + numeracion) != null);

        //SUCESOS
        SisSuceso sisSucesoNC = null;
        String detalle = "";
        if (invComprasNE != null) {
            sisSuceso.setSusClave(
                    invComprasNE.getInvComprasPK().getCompPeriodo() + " "
                    + invComprasNE.getInvComprasPK().getCompMotivo() + " "
                    + invComprasNE.getInvComprasPK().getCompNumero());
            detalle = "Compra a " + invComprasNE.getInvProveedor().getProvRazonSocial()
                    + " por " + invComprasNE.getCompTotal().toPlainString();
            sisSuceso.setSusDetalle(detalle);
        }

        if (invComprasNC != null) {
            sisSucesoNC = sisSuceso;
            sisSucesoNC.setSusClave(
                    invComprasNC.getInvComprasPK().getCompPeriodo() + " "
                    + invComprasNC.getInvComprasPK().getCompMotivo() + " "
                    + invComprasNC.getInvComprasPK().getCompNumero());
            detalle = "Compra a " + invComprasNC.getInvProveedor().getProvRazonSocial()
                    + " según documento # " + invComprasNC.getCompDocumentoNumero()
                    + " por $" + invComprasNC.getCompTotal().toPlainString();
            sisSucesoNC.setSusDetalle(detalle);
        }

        if (invComprasNE != null && invComprasNC != null) {
            insertarInvCompraInventario(invComprasNC,
                    listaInvComprasDetallesNC,
                    sisSucesoNC,
                    null,
                    new ArrayList<>(),
                    null, null, null,
                    new ArrayList<>(),
                    new ArrayList<>());
            insertarInvCompraInventario(invComprasNE,
                    listaInvComprasDetallesNE,
                    sisSuceso,
                    null,
                    new ArrayList<>(),
                    null, null, null,
                    new ArrayList<>(),
                    new ArrayList<>());
        } else {
            if (invComprasNE == null && invComprasNC != null) {
                insertarInvCompraInventario(invComprasNC,
                        listaInvComprasDetallesNC,
                        sisSucesoNC,
                        null,
                        new ArrayList<>(),
                        null, null, null,
                        new ArrayList<>(),
                        new ArrayList<>());
            } else {
                insertarInvCompraInventario(invComprasNE,
                        listaInvComprasDetallesNE,
                        sisSuceso,
                        null,
                        new ArrayList<>(),
                        null, null, null,
                        new ArrayList<>(),
                        new ArrayList<>());
            }
        }

        return true;
    }

    @Override
    public InvComprasTO getComprasTO(String empresa, String tipo, String numeroDocumento) throws Exception {
        String sql = "select * from inventario.inv_compras where comp_empresa = '" + empresa
                + "' and comp_documento_numero = '" + numeroDocumento + "' and comp_documento_tipo = '" + tipo + "'";
        InvCompras compra = genericSQLDao.obtenerObjetoPorSql(sql, InvCompras.class);
        InvComprasTO compraTO = null;
        if (compra != null) {
            compraTO = ConversionesInventario.convertirInvCompras_InvComprasTO(compra);
        }
        return compraTO;
    }

    @Override
    public InvCompras obtenerCompras(String empresa, String tipo, String numeroDocumento, String provCodigo) throws Exception {
        String sql = "select * from inventario.inv_compras where comp_empresa = '" + empresa
                + "' and comp_documento_numero = '" + numeroDocumento + "' and comp_documento_tipo = '" + tipo + "' AND prov_codigo='" + provCodigo + "'";
        InvCompras compra = genericSQLDao.obtenerObjetoPorSql(sql, InvCompras.class);
        if (compra != null) {
            compra.setInvComprasDetalleList(null);
            compra.setInvComprasRecepcionList(null);
            compra.setInvComprasMotivoAnulacionList(null);
        }
        return compra;
    }

    @Override
    public Boolean actualizarClaveExterna(InvComprasPK pk, String clave, SisSuceso sisSuceso) throws Exception {
        String sql = "UPDATE inventario.inv_compras SET clave_acceso_externa=" + clave
                + " WHERE comp_empresa='" + pk.getCompEmpresa()
                + "' and comp_periodo='" + pk.getCompPeriodo()
                + "' and comp_motivo='" + pk.getCompMotivo()
                + "' and comp_numero='" + pk.getCompNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<InvCompras> obtenerListadoComprasSaldosImportados(String empresa, String motivo, String sector, String fecha) throws Exception {
        String sqlMotivo = motivo != null ? " AND comp_motivo='" + motivo + "'" : "";
        String sqlSector = sector != null ? " AND sec_codigo='" + sector + "'" : "";
        String sqlFecha = fecha != null ? " AND comp_fecha='" + fecha + "'" : "";
        String sql = "select * from inventario.inv_compras where comp_empresa = '" + empresa + "'"
                + sqlMotivo
                + sqlSector
                + sqlFecha
                + " and comp_saldo_importado is true";
        List<InvCompras> compras = genericSQLDao.obtenerPorSql(sql, InvCompras.class);

        if (compras != null) {
            for (InvCompras compra : compras) {
                compra.setInvComprasRecepcionList(null);
                compra.setInvComprasMotivoAnulacionList(null);
                compra.setInvComprasDetalleList(null);
            }
        }
        return compras;
    }

    @Override
    public boolean insertarInvComprasTO(InvCompras invCompras, SisSuceso sisSuceso) throws Exception {
        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionCompra(
                invCompras.getInvComprasPK().getCompEmpresa(),
                invCompras.getInvComprasPK().getCompPeriodo(),
                invCompras.getInvComprasPK().getCompMotivo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            invCompras.setInvComprasPK(
                    new InvComprasPK(invCompras.getInvComprasPK().getCompEmpresa(),
                            invCompras.getInvComprasPK().getCompPeriodo(),
                            invCompras.getInvComprasPK().getCompMotivo(),
                            rellenarConCeros + numeracion));
        } while (buscarInvCompras(
                invCompras.getInvComprasPK().getCompEmpresa(),
                invCompras.getInvComprasPK().getCompPeriodo(),
                invCompras.getInvComprasPK().getCompMotivo(),
                rellenarConCeros + numeracion) != null);

        insertar(invCompras);

        String susClave = invCompras.getInvComprasPK().getCompPeriodo() + " | " + invCompras.getInvComprasPK().getCompMotivo() + " | " + invCompras.getInvComprasPK().getCompNumero();
        String susDetalle = "Se guardó la compra con código: " + susClave + " desde saldos por pagar";

        sisSuceso.setSusClave(susClave);
        sisSuceso.setSusDetalle(susDetalle);

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarInvCompras(InvCompras invCompras, SisSuceso sisSuceso) throws Exception {
        actualizar(invCompras);
        String susClave = invCompras.getInvComprasPK().getCompPeriodo() + " | " + invCompras.getInvComprasPK().getCompMotivo() + " | " + invCompras.getInvComprasPK().getCompNumero();
        String susDetalle = "Se modificó la compra con código: " + susClave + " desde saldos por pagar";

        sisSuceso.setSusClave(susClave);
        sisSuceso.setSusDetalle(susDetalle);

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvCompraCabeceraTO consultarCompraPorContablePk(String empresa, String periodo, String conTipo, String numero) {
        String sql = "SELECT comp_documento_tipo, comp_documento_numero, "
                + "SUBSTRING(cast(comp_fecha as TEXT), 1, 10) as comp_fecha, "
                + "SUBSTRING(cast(comp_fecha_vencimiento as TEXT), 1, 10) as comp_fecha_vencimiento, "
                + "comp_iva_vigente, comp_observaciones, comp_electronica, comp_pendiente, "
                + "comp_revisado, comp_anulado, comp_forma_pago, comp_base0, comp_base_imponible, "
                + "comp_base_no_objeto, comp_base_exenta, comp_monto_iva, comp_total, "
                + "comp_valor_retenido, comp_saldo, prov_codigo, sec_codigo, con_periodo, "
                + "con_tipo, con_numero, usr_codigo, cast(usr_fecha_inserta as TEXT), "
                + "comp_retencion_asumida, comp_documento_forma_pago, anu_comentario, comp_activo_fijo, "
                + "comp_importacion, comp_otros_impuestos , comp_ice, comp_propina, "
                + "oc_empresa, oc_sector , oc_motivo, oc_numero, comp_programada,"
                + "comp_usuario_aprueba_pago, comp_fecha_aprueba_pago, comp_imb_facturado, comp_transportista_ruc,fecha_ultima_validacion_sri,comp_saldo_importado ,"
                + " oc_orden_pedido, cta_codigo, fp_secuencial, comp_guia_compra, bod_codigo, comp_es_imb,inv_compras.comp_motivo, inv_compras.comp_periodo, inv_compras.comp_numero "
                + " FROM inventario.inv_compras LEFT JOIN inventario.inv_compras_motivo_anulacion "
                + "ON inv_compras.comp_empresa = inv_compras_motivo_anulacion.comp_empresa AND "
                + "inv_compras.comp_periodo = inv_compras_motivo_anulacion.comp_periodo AND "
                + "inv_compras.comp_motivo  = inv_compras_motivo_anulacion.comp_motivo  AND "
                + "inv_compras.comp_numero  = inv_compras_motivo_anulacion.comp_numero "
                + "WHERE (inv_compras.con_empresa = '" + empresa + "') AND " + "inv_compras.con_periodo = ('"
                + periodo + "') AND inv_compras.con_tipo = ('" + conTipo + "') " + "AND inv_compras.con_numero = '"
                + numero + "'";
        List<InvCompraCabeceraTO> lista = genericSQLDao.obtenerPorSql(sql, InvCompraCabeceraTO.class);
        return lista.isEmpty() == true ? null : lista.get(0);
    }

}
