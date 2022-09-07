package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ConsumosDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ConsumosDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ConsumosMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoSaldosDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.EquipoControlDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ConfiguracionComprasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.ConfiguracionConsumosService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosDatosAdjuntosWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosImportarExportarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControl;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumosServiceImpl implements ConsumosService {

    @Autowired
    private BodegaDao bodegaDao;
    @Autowired
    private ConsumosDao consumosDao;
    @Autowired
    private ConsumosDetalleDao consumosDetalleDao;
    @Autowired
    private ConsumosMotivoDao consumosMotivoDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private ProductoSaldosDao productoSaldosDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private PiscinaDao piscinaDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private ConfiguracionConsumosService configuracionConsumosService;
    @Autowired
    private GenericoDao<InvConsumosDatosAdjuntos, Integer> invConsumosDatosAdjuntosDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private EquipoControlDao equipoControlDao;
    @Autowired
    private ConfiguracionComprasService configuracionComprasService;
    private boolean comprobar = false;
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvFunConsumosTO> getInvFunConsumosTO(String empresa, String desde, String hasta, String bodega)
            throws Exception {
        return consumosDao.getInvFunConsumosTO(empresa, desde, hasta, bodega);
    }

    @Override
    public Boolean reconstruirCostos(String empCodigo, String Producto, String periodoHasta, Boolean periodoEstado)
            throws Exception {
        return consumosDao.reconstruirCostos(empCodigo, Producto, periodoHasta, periodoEstado);
    }

    @Override
    public List<InvFunConsumosConsolidandoProductosTO> getInvFunConsumosConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega) throws Exception {
        return consumosDao.getInvFunConsumosConsolidandoProductosTO(empresa, desde, hasta, sector, bodega);
    }

    @Override
    public InvConsumosTO getInvConsumoCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroConsumo) throws Exception {
        return consumosDao.getInvConsumoCabeceraTO(empresa, codigoPeriodo, motivo, numeroConsumo);
    }

    @Override
    public List<InvListaConsultaConsumosTO> getFunConsumosListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception {
        return consumosDao.getFunConsumosListado(empresa, fechaDesde, fechaHasta, status);
    }

    @Override
    public List<InvListaConsultaConsumosTO> getListaInvConsultaConsumos(String empresa, String periodo, String motivo, String cliente, String proveedor, String producto, String empleado,
            String busqueda, String nRegistros) throws Exception {
        return consumosDao.getListaInvConsultaConsumos(empresa, periodo, motivo, cliente, proveedor, producto, empleado, busqueda, nRegistros);
    }

    @Override
    public List<InvConsumosImportarExportarTO> getListaInvConsumosImportarExportarTO(String empresa, String desde,
            String hasta) throws Exception {
        return consumosDao.getListaInvConsumosImportarExportarTO(empresa, desde, hasta);
    }

    private List<InvConsumosDetalle> agrupraProductosBodegaConsumo(List<InvConsumosDetalle> consumosDetalle)
            throws Exception {

        List<InvConsumosDetalle> listaDetalleFinal = new ArrayList<InvConsumosDetalle>();
        listaDetalleFinal
                .add(ConversionesInventario.convertirInvConsumoDetalle_InvConsumoDetalle(consumosDetalle.get(0)));

        int contador = 0;
        boolean encontro = false;

        for (int i = 1; i < consumosDetalle.size(); i++) {
            contador = 0;
            for (InvConsumosDetalle invConsumosDetalleAux : listaDetalleFinal) {
                if (consumosDetalle.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                        .equals(invConsumosDetalleAux.getInvProducto().getInvProductoPK().getProCodigoPrincipal())
                        && consumosDetalle.get(i).getInvBodega().getInvBodegaPK().getBodCodigo()
                                .equals(invConsumosDetalleAux.getInvBodega().getInvBodegaPK().getBodCodigo())) {
                    encontro = true;
                    break;
                } else {
                    encontro = false;
                    contador++;
                }
            }

            if (encontro) {
                listaDetalleFinal.get(contador).setDetCantidad(listaDetalleFinal.get(contador).getDetCantidad().add(consumosDetalle.get(i).getDetCantidad()));
            } else {
                listaDetalleFinal.add(ConversionesInventario.convertirInvConsumoDetalle_InvConsumoDetalle(consumosDetalle.get(i)));
            }
        }
        return listaDetalleFinal;
    }

    @Override
    public MensajeTO insertarInvConsumosTO(InvConsumosTO invConsumosTO,
            List<InvConsumosDetalleTO> listaInvConsumosDetalleTO, SisInfoTO sisInfoTO)
            throws NumberFormatException, Exception {
        String retorno = "";
        InvProductoSaldos invProductoSaldos = null;
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();
        List<InvProductoSaldos> listaInvProductoSaldos = new ArrayList<InvProductoSaldos>();
        boolean periodoCerrado = false;
        if (!UtilsValidacion.isFechaSuperior(invConsumosTO.getConsFecha(), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(invConsumosTO.getConsEmpresa());
            comprobar = false;
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invConsumosTO.getConsFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(invConsumosTO.getConsFecha(), "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    comprobar = true;
                    invConsumosTO.setConsPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    if (consumosMotivoDao.comprobarInvConsumosMotivo(invConsumosTO.getConsEmpresa(), invConsumosTO.getConsMotivo())) {
                        susSuceso = "INSERT";
                        susTabla = "inventario.inv_consumo";
                        invConsumosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

                        ////// CREANDO CONSUMOS
                        InvConsumos invConsumos = ConversionesInventario
                                .convertirInvConsumosTO_InvConsumos(invConsumosTO);
                        ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                        List<InvConsumosDetalle> listInvConsumosDetalle = new ArrayList<InvConsumosDetalle>();
                        InvConsumosDetalle invConsumosDetalle = null;

                        int estadoDetalle = 0;
                        for (InvConsumosDetalleTO invConsumosDetalleTO : listaInvConsumosDetalleTO) {
                            invConsumosDetalle = new InvConsumosDetalle();
                            invConsumosDetalle = ConversionesInventario
                                    .convertirInvConsumosDetalleTO_InvConsumosDetalle(invConsumosDetalleTO);
                            ///// BUSCANDO EL PRODUCTO EN DETALLE
                            InvProducto invProducto = productoDao.buscarInvProducto(
                                    invConsumosDetalleTO.getConsEmpresa(),
                                    invConsumosDetalleTO.getProCodigoPrincipal());
                            if (invProducto != null) {
                                invConsumosDetalle.setInvProducto(invProducto);
                                ////// BUSCANDO LA BODEGA EN EL DETALLE
                                InvBodega invBodega = bodegaDao.buscarInvBodega(
                                        invConsumosDetalleTO.getConsEmpresa(), invConsumosDetalleTO.getBodCodigo());

                                if (invBodega != null) {
                                    invConsumosDetalle.setInvBodega(invBodega);
                                    listInvConsumosDetalle.add(invConsumosDetalle);
                                } else {
                                    estadoDetalle = 2;
                                }
                            } else {
                                estadoDetalle = 1;
                            }

                            if (estadoDetalle == 1 || estadoDetalle == 2) {
                                break;
                            } else {
                                invProducto = null;
                            }
                        }

                        if (estadoDetalle == 0) {
                            List<InvConsumosDetalle> listadoDetalleConsumos = agrupraProductosBodegaConsumo(listInvConsumosDetalle);
                            if (!invConsumos.getConsPendiente()) {
                                for (int i = 0; i < listadoDetalleConsumos.size(); i++) {
                                    if (!listadoDetalleConsumos.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("COSTO O GASTO")
                                            && !listadoDetalleConsumos.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("SERVICIOS")
                                            && !listadoDetalleConsumos.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("ACTIVO FIJO")) {
                                        invProductoSaldos = new InvProductoSaldos();
                                        invProductoSaldos.setStkSecuencial(0);
                                        //// BUSCO LA BODEGA
                                        invProductoSaldos.setInvBodega(bodegaDao.buscarInvBodega(
                                                invConsumos.getInvConsumosPK().getConsEmpresa(),
                                                listadoDetalleConsumos.get(i).getInvBodega().getInvBodegaPK()
                                                        .getBodCodigo()));
                                        //// PONGO EL PRODUCTO
                                        invProductoSaldos
                                                .setInvProducto(listadoDetalleConsumos.get(i).getInvProducto());
                                        InvProductoSaldos invProductoSaldosConsulta = productoSaldosDao
                                                .getInvProductoSaldo(
                                                        listadoDetalleConsumos.get(i).getInvProducto()
                                                                .getInvProductoPK().getProEmpresa(),
                                                        listadoDetalleConsumos.get(i).getInvBodega()
                                                                .getInvBodegaPK().getBodCodigo(),
                                                        listadoDetalleConsumos.get(i).getInvProducto()
                                                                .getInvProductoPK().getProCodigoPrincipal());
                                        if (invProductoSaldosConsulta != null) {
                                            /// Como si existe el dato se le
                                            /// suma la nueva cantidad con
                                            /// lo que se va a ingresar

                                            invProductoSaldos.setStkSaldoFinal(
                                                    invProductoSaldosConsulta.getStkSaldoFinal().subtract(
                                                            listadoDetalleConsumos.get(i).getDetCantidad()));
                                            if (invProductoSaldos.getStkSaldoFinal().compareTo(cero) >= 0) {
                                                invProductoSaldos
                                                        .setStkFechaUltimaCompraFinal(invConsumos.getConsFecha());
                                                invProductoSaldos.setStkValorUltimaCompraFinal(
                                                        invProductoSaldosConsulta.getStkValorUltimaCompraFinal());

                                                invProductoSaldos.setStkSaldoInicial(
                                                        invProductoSaldosConsulta.getStkSaldoInicial());
                                                invProductoSaldos.setStkValorPromedioInicial(
                                                        invProductoSaldosConsulta.getStkValorPromedioInicial());
                                                invProductoSaldos.setStkValorPromedioFinal(
                                                        invProductoSaldosConsulta.getStkValorPromedioFinal());

                                                listaInvProductoSaldos.add(invProductoSaldos);
                                            } else {
                                                retorno = "F<html>No hay stock suficiente"
                                                        + " en los siguientes productos:</html>";
                                                mensajeClase.add(listadoDetalleConsumos.get(i).getInvProducto()
                                                        .getInvProductoPK().getProCodigoPrincipal()
                                                        + " \t\t"
                                                        + listadoDetalleConsumos.get(i).getInvProducto()
                                                                .getProNombre()
                                                        + " : " + listadoDetalleConsumos.get(i).getDetCantidad()
                                                        + " - Saldo : "
                                                        + invProductoSaldosConsulta.getStkSaldoFinal().setScale(2,
                                                                BigDecimal.ROUND_HALF_UP));
                                            }
                                        } else {
                                            retorno = "F<html>No hay stock suficiente "
                                                    + "en los siguientes productos:</html>";
                                            mensajeClase.add(listadoDetalleConsumos.get(i).getInvProducto()
                                                    .getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                                    + listadoDetalleConsumos.get(i).getInvProducto().getProNombre()
                                                    + " : " + listadoDetalleConsumos.get(i).getDetCantidad()
                                                    + " - Saldo : " + new BigDecimal("0.00"));
                                        }
                                    } else {
                                        retorno = "F<html>No es posible guardar porque los siguientes productos son de tipo 'SERVICIO' o 'ACTIVO FIJO':</html>";
                                        mensajeClase.add(listadoDetalleConsumos.get(i).getInvProducto()
                                                .getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                                + listadoDetalleConsumos.get(i).getInvProducto().getProNombre()
                                                + " - Tipo : " + listadoDetalleConsumos.get(i).getInvProducto()
                                                        .getInvProductoTipo().getTipTipo());
                                    }

                                }
                            }
                            mensajeTO.setMensaje(retorno);
                            mensajeTO.setListaErrores1(mensajeClase);
                            if (!mensajeClase.isEmpty()) {
                                return mensajeTO;
                            }

                            // Valida que sector de los consumos este relacionado con el sector del motivo del consumo
                            InvConsumosMotivoTO invConsumosMotivoTO = consumosMotivoDao.getInvConsumoMotivoTO(invConsumosTO.getConsEmpresa(), invConsumosTO.getConsMotivo());
                            for (int i = 0; i < listadoDetalleConsumos.size(); i++) {
                                if (invConsumosMotivoTO.getCmSector() != null && !invConsumosMotivoTO.getCmSector().equals(listadoDetalleConsumos.get(i).getInvBodega().getSecCodigo())) {
                                    retorno = "F<html>El motivo seleccionado permite SOLAMENTE consumos\ndel sector " + invConsumosMotivoTO.getCmSector() + ", revise los siguientes productos...</html>";
                                    mensajeClase.add(
                                            listadoDetalleConsumos.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                            + listadoDetalleConsumos.get(i).getInvProducto().getProNombre()
                                            + " - Sector: " + listadoDetalleConsumos.get(i).getInvBodega().getSecCodigo());
                                }
                            }
                            if (!mensajeClase.isEmpty()) {
                                mensajeTO.setMensaje(retorno);
                                mensajeTO.setListaErrores1(mensajeClase);
                                return mensajeTO;
                            }

                            for (int i = 0; i < listadoDetalleConsumos.size(); i++) {
                                if (invConsumosMotivoTO.getCmBodega() != null && !invConsumosMotivoTO.getCmBodega().equals(listadoDetalleConsumos.get(i).getInvBodega().getInvBodegaPK().getBodCodigo())) {
                                    retorno = "F<html>El motivo seleccionado permite SOLAMENTE consumos\nde la bodega " + invConsumosMotivoTO.getCmBodega() + ", revise los siguientes productos...</html>";
                                    mensajeClase.add(
                                            listadoDetalleConsumos.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                            + listadoDetalleConsumos.get(i).getInvProducto().getProNombre()
                                            + " - Bodega: " + listadoDetalleConsumos.get(i).getInvBodega().getInvBodegaPK().getBodCodigo());
                                }
                            }
                            if (!mensajeClase.isEmpty()) {
                                mensajeTO.setMensaje(retorno);
                                mensajeTO.setListaErrores1(mensajeClase);
                                return mensajeTO;
                            }

                            // Valida que sector de los consumos este relacionado con el sector de la bodega
                            for (int i = 0; i < listadoDetalleConsumos.size(); i++) {
                                if (!listadoDetalleConsumos.get(i).getSecCodigo().equals(listadoDetalleConsumos.get(i).getInvBodega().getSecCodigo())) {
                                    retorno = "F<html>Existen conflictos bodega/sector, revise los siguientes productos...</html>";
                                    mensajeClase.add(
                                            listadoDetalleConsumos.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                            + listadoDetalleConsumos.get(i).getInvProducto().getProNombre()
                                            + " - Sector Incorrecto: " + listadoDetalleConsumos.get(i).getSecCodigo());
                                }
                            }
                            mensajeTO.setMensaje(retorno);
                            mensajeTO.setListaErrores1(mensajeClase);
                            if (!mensajeClase.isEmpty()) {
                                return mensajeTO;
                            }
                            ////////////////////////////////////////////////

                            if (mensajeClase.isEmpty()) {
                                boolean comprobacionCc = false;
                                String ccNumero = "";
                                for (int i = 0; i < listInvConsumosDetalle.size(); i++) {
                                    ccNumero = "";
                                    List<PrdListaPiscinaTO> prdListaPiscinaTOs = piscinaDao.getListaPiscinaBusqueda(
                                            invConsumos.getInvConsumosPK().getConsEmpresa(),
                                            listInvConsumosDetalle.get(i).getSecCodigo(),
                                            UtilsValidacion.fecha(invConsumos.getConsFecha(), "yyyy-MM-dd"));
                                    for (int j = 0; j < prdListaPiscinaTOs.size(); j++) {
                                        ccNumero = listInvConsumosDetalle.get(i).getPisNumero();
                                        if (listInvConsumosDetalle.get(i).getPisNumero()
                                                .equals(prdListaPiscinaTOs.get(j).getPisNumero())) {
                                            comprobacionCc = true;
                                            j = prdListaPiscinaTOs.size();
                                        } else {
                                            comprobacionCc = false;
                                        }
                                    }
                                    if (!comprobacionCc) {
                                        boolean anadirNuevo = true;
                                        for (int k = 0; k < mensajeClase.size(); k++) {
                                            if (!mensajeClase.get(k)
                                                    .equals("No existe un PERIODO DE CONSUMO para la PISCINA "
                                                            + ccNumero)) {
                                                anadirNuevo = true;
                                            } else {
                                                anadirNuevo = false;
                                            }
                                        }
                                        if (anadirNuevo) {
                                            mensajeClase.add(
                                                    "No existe un PERIODO DE CONSUMO para la PISCINA " + ccNumero);
                                            retorno = "FHubo un error al guardar el Consumo...";
                                        }
                                    }
                                }

                                if (mensajeClase.isEmpty()) {
                                    /// PREPARANDO OBJETO SISSUCESO
                                    /// (susClave y susDetalle se llenan en
                                    /// DAOTransaccion)
                                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                            susDetalle, sisInfoTO);
                                    comprobar = consumosDao.insertarModificarInvConsumos(invConsumos,
                                            listInvConsumosDetalle, sisSuceso, true);
                                    invConsumosTO.setConsNumero(invConsumos.getInvConsumosPK().getConsNumero());
                                    if (comprobar) {
                                        SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                                invConsumosTO.getConsEmpresa(),
                                                invConsumos.getInvConsumosPK().getConsPeriodo());
                                        retorno = "T<html>Se ingreso el consumo con la siguiente informacion:<br><br>"
                                                + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                                + "</font>.<br> Motivo: <font size = 5>"
                                                + invConsumos.getInvConsumosPK().getConsMotivo()
                                                + "</font>.<br> Numero: <font size = 5>"
                                                + invConsumos.getInvConsumosPK().getConsNumero() + "</font>.</html>"
                                                + invConsumos.getInvConsumosPK().getConsNumero() + ","
                                                + sisPeriodo.getSisPeriodoPK().getPerCodigo();
                                        mensajeTO.setFechaCreacion(invConsumos.getUsrFechaInserta().toString());
                                    } else {
                                        retorno = "FHubo un error al guardar el Consumo...\nIntente de nuevo o contacte con el administrador";
                                    }
                                } else {
                                    mensajeTO.setMensaje(retorno);
                                    mensajeTO.setListaErrores1(mensajeClase);
                                }
                            } else {
                                mensajeTO.setMensaje(retorno);
                                mensajeTO.setListaErrores1(mensajeClase);
                            }
                        } else {
                            if (estadoDetalle == 1) {
                                retorno = "F<html>Uno de los Productos que ingreso ya no esta¡ disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                            } else {
                                retorno = "F<html>Una de las Bodega que ingreso ya no esta¡ disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                            }
                        }
                    }
                } else {
                    retorno = "F<html>El periodo que corresponde a la fecha que ingreso se encuentra cerrado...</html>";
                }
            } else {
                retorno = "FNo se encuentra ningun periodo para la fecha que ingreso...";
            }
        } else {
            retorno = "F<html>La fecha que ingreso es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        if (mensajeTO.getMensaje().charAt(0) == 'T' && !invConsumosTO.getConsPendiente()) {
            quitarPendiente(new InvConsumosPK(invConsumosTO.getConsEmpresa(),
                    invConsumosTO.getConsPeriodo(), invConsumosTO.getConsMotivo(), invConsumosTO.getConsNumero()), sisInfoTO);
        }
        return mensajeTO;
    }

    @Override
    public MensajeTO modificarInvConsumosTO(InvConsumosTO invConsumosTO,
            List<InvConsumosDetalleTO> listaInvConsumosDetalleTO,
            List<InvConsumosDetalleTO> listaInvConsumosEliminarDetalleTO, boolean desmayorizar,
            InvConsumosMotivoAnulacion invConsumosMotivoAnulacion, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>(1);
        boolean periodoCerrado = false;
        String retorno = "";
        InvProductoSaldos invProductoSaldos = null;
        List<InvProductoSaldos> listaInvProductoSaldos = new ArrayList<InvProductoSaldos>();
        if (!UtilsValidacion.isFechaSuperior(invConsumosTO.getConsFecha(), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(invConsumosTO.getConsEmpresa());

            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(invConsumosTO.getConsFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(invConsumosTO.getConsFecha(), "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    comprobar = true;
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }

            if (comprobar) {
                if (!periodoCerrado) {
                    if (consumosMotivoDao.comprobarInvConsumosMotivo(invConsumosTO.getConsEmpresa(),
                            invConsumosTO.getConsMotivo())) {
                        InvConsumos invConsumosCreados = consumosDao.buscarInvConsumos(
                                invConsumosTO.getConsEmpresa(), invConsumosTO.getConsPeriodo(),
                                invConsumosTO.getConsMotivo(), invConsumosTO.getConsNumero());
                        if (invConsumosCreados != null) {

                            String detalleError = "";
                            if (desmayorizar && listaInvConsumosDetalleTO == null) {
                                List<InvListaDetalleConsumoTO> invListaDetalleTO = consumosDetalleDao
                                        .getListaInvConsumoDetalleTO(invConsumosTO.getConsEmpresa(),
                                                invConsumosTO.getConsPeriodo(), invConsumosTO.getConsMotivo(),
                                                invConsumosTO.getConsNumero());
                                if (invListaDetalleTO != null) {
                                    listaInvConsumosDetalleTO = new ArrayList<InvConsumosDetalleTO>();
                                    for (int i = 0; i < invListaDetalleTO.size(); i++) {
                                        InvConsumosDetalleTO invConsumosDetalleTO = new InvConsumosDetalleTO();
                                        invConsumosDetalleTO.setConsEmpresa(invConsumosTO.getConsEmpresa());
                                        invConsumosDetalleTO.setConsPeriodo(invConsumosTO.getConsPeriodo());
                                        invConsumosDetalleTO.setConsMotivo(invConsumosTO.getConsMotivo());
                                        invConsumosDetalleTO.setConsNumero(invConsumosTO.getConsNumero());
                                        invConsumosDetalleTO
                                                .setDetSecuencial(invListaDetalleTO.get(i).getSecuencial());
                                        invConsumosDetalleTO.setDetOrden(i + 1);
                                        invConsumosDetalleTO
                                                .setDetCantidad(invListaDetalleTO.get(i).getCantidadProducto());
                                        invConsumosDetalleTO.setBodEmpresa(invConsumosTO.getConsEmpresa());
                                        invConsumosDetalleTO
                                                .setBodCodigo(invListaDetalleTO.get(i).getCodigoBodega());
                                        invConsumosDetalleTO.setProEmpresa(invConsumosTO.getConsEmpresa());
                                        invConsumosDetalleTO.setProCodigoPrincipal(
                                                invListaDetalleTO.get(i).getCodigoProducto());
                                        invConsumosDetalleTO.setSecEmpresa(invConsumosTO.getConsEmpresa());
                                        invConsumosDetalleTO.setSecCodigo(invListaDetalleTO.get(i).getCodigoCP());
                                        invConsumosDetalleTO.setPisEmpresa(invConsumosTO.getConsEmpresa());
                                        invConsumosDetalleTO.setPisSector(invListaDetalleTO.get(i).getCodigoCP());
                                        invConsumosDetalleTO.setPisNumero(invListaDetalleTO.get(i).getCodigoCC());
                                        listaInvConsumosDetalleTO.add(invConsumosDetalleTO);
                                    }
                                } else {
                                    detalleError = "Hubo en error al recuperar el detalle del CONSUMO.\nContacte con el administrador...";
                                }
                            }

                            if (detalleError.trim().isEmpty()) {
                                // preparando suceso
                                susClave = invConsumosTO.getConsPeriodo() + " " + invConsumosTO.getConsMotivo()
                                        + " " + invConsumosTO.getConsNumero();

                                susDetalle = invConsumosTO.getConsAnulado()
                                        ? "Consumo número " + invConsumosTO.getConsNumero() + " se anuló por "
                                        + invConsumosMotivoAnulacion.getAnuComentario()
                                        : "Se modificó el consumo en el periodo " + invConsumosTO.getConsPeriodo()
                                        + ", del motivo " + invConsumosTO.getConsMotivo()
                                        + ", de la numeración " + invConsumosTO.getConsNumero();
                                susSuceso = "UPDATE";
                                susTabla = "inventario.inv_consumo";
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                        susDetalle, sisInfoTO);
                                invConsumosTO.setUsrCodigo(invConsumosCreados.getUsrCodigo());
                                invConsumosTO.setUsrFechaInserta(UtilsValidacion
                                        .fecha(invConsumosCreados.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

                                ////// CREANDO NUMERACION DE CONSUMO
                                ////// CREANDO CONSUMOS
                                InvConsumos invConsumos = ConversionesInventario
                                        .convertirInvConsumosTO_InvConsumos(invConsumosTO);

                                ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                                List<InvConsumosDetalle> listInvConsumosDetalle = new ArrayList<InvConsumosDetalle>();
                                InvConsumosDetalle invConsumosDetalle = null;

                                int estadoDetalle = 0;
                                for (InvConsumosDetalleTO invConsumosDetalleTO : listaInvConsumosDetalleTO) {
                                    invConsumosDetalle = new InvConsumosDetalle();
                                    invConsumosDetalle = ConversionesInventario
                                            .convertirInvConsumosDetalleTO_InvConsumosDetalle(invConsumosDetalleTO);

                                    ///// BUSCANDO EL PRODUCTO EN DETALLE
                                    InvProducto invProducto = productoDao.buscarInvProducto(
                                            invConsumosDetalleTO.getConsEmpresa(),
                                            invConsumosDetalleTO.getProCodigoPrincipal());
                                    if (invProducto != null) {
                                        invConsumosDetalle.setInvProducto(invProducto);
                                        ////// BUSCANDO LA BODEGA EN EL
                                        ////// DETALLE
                                        InvBodega invBodega = bodegaDao.buscarInvBodega(
                                                invConsumosDetalleTO.getConsEmpresa(),
                                                invConsumosDetalleTO.getBodCodigo());

                                        if (invBodega != null) {
                                            invConsumosDetalle.setInvBodega(invBodega);
                                            listInvConsumosDetalle.add(invConsumosDetalle);
                                        } else {
                                            estadoDetalle = 2;
                                        }
                                    } else {
                                        estadoDetalle = 1;
                                    }

                                    if (estadoDetalle == 1 || estadoDetalle == 2) {
                                        break;
                                    } else {
                                        invProducto = null;
                                    }
                                }

                                if (estadoDetalle == 0) {
                                    ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                                    ////// A ELIMINAR
                                    List<InvConsumosDetalle> listInvConsumosDetalleEliminar = new ArrayList<InvConsumosDetalle>();
                                    InvConsumosDetalle invConsumosDetalleEliminar = null;

                                    int estadoDetalleEliminar = 0;
                                    if (listaInvConsumosEliminarDetalleTO != null) {
                                        for (InvConsumosDetalleTO invConsumosDetalleTO : listaInvConsumosEliminarDetalleTO) {
                                            invConsumosDetalleEliminar = new InvConsumosDetalle();
                                            invConsumosDetalleTO.setConsPeriodo(invConsumosTO.getConsPeriodo());
                                            invConsumosDetalleEliminar = ConversionesInventario
                                                    .convertirInvConsumosDetalleTO_InvConsumosDetalle(
                                                            invConsumosDetalleTO);
                                            invConsumosDetalleEliminar.setInvConsumos(invConsumos);

                                            ///// BUSCANDO EL PRODUCTO EN
                                            ///// DETALLE
                                            InvProducto invProducto = productoDao.buscarInvProducto(
                                                    invConsumosDetalleTO.getConsEmpresa(),
                                                    invConsumosDetalleTO.getProCodigoPrincipal());
                                            if (invProducto != null) {
                                                invConsumosDetalleEliminar.setInvProducto(invProducto);
                                                ////// BUSCANDO LA BODEGA EN
                                                ////// EL DETALLE
                                                InvBodega invBodega = bodegaDao.buscarInvBodega(
                                                        invConsumosDetalleTO.getConsEmpresa(),
                                                        invConsumosDetalleTO.getBodCodigo());

                                                if (invBodega != null) {
                                                    invConsumosDetalleEliminar.setInvBodega(invBodega);
                                                    listInvConsumosDetalleEliminar.add(invConsumosDetalleEliminar);
                                                } else {
                                                    estadoDetalleEliminar = 2;
                                                }
                                            } else {
                                                estadoDetalleEliminar = 1;
                                            }

                                            if (estadoDetalleEliminar == 1 || estadoDetalleEliminar == 2) {
                                                break;
                                            } else {
                                                invProducto = null;
                                            }
                                        }
                                    }
                                    if (estadoDetalleEliminar == 0) {

                                        if (!invConsumos.getConsPendiente() || desmayorizar) {
                                            List<InvConsumosDetalle> listadoDetalleConsumos = agrupraProductosBodegaConsumo(
                                                    listInvConsumosDetalle);
                                            for (int i = 0; i < listadoDetalleConsumos.size(); i++) {
                                                if (!listadoDetalleConsumos.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("COSTO O GASTO")
                                                        && !listadoDetalleConsumos.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("SERVICIOS")
                                                        && !listadoDetalleConsumos.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("ACTIVO FIJO")) {
                                                    invProductoSaldos = new InvProductoSaldos();
                                                    invProductoSaldos.setStkSecuencial(0);
                                                    //// BUSCO LA BODEGA
                                                    invProductoSaldos.setInvBodega(bodegaDao.buscarInvBodega(
                                                            invConsumos.getInvConsumosPK().getConsEmpresa(),
                                                            listadoDetalleConsumos.get(i).getInvBodega()
                                                                    .getInvBodegaPK().getBodCodigo()));
                                                    //// PONGO EL PRODUCTO
                                                    invProductoSaldos.setInvProducto(
                                                            listadoDetalleConsumos.get(i).getInvProducto());
                                                    InvProductoSaldos invProductoSaldosConsulta = productoSaldosDao
                                                            .getInvProductoSaldo(
                                                                    invConsumos.getInvConsumosPK().getConsEmpresa(),
                                                                    listadoDetalleConsumos.get(i).getInvBodega()
                                                                            .getInvBodegaPK().getBodCodigo(),
                                                                    listadoDetalleConsumos.get(i).getInvProducto()
                                                                            .getInvProductoPK()
                                                                            .getProCodigoPrincipal());
                                                    if (invProductoSaldosConsulta != null) {
                                                        /// Como si existe
                                                        /// el dato se le
                                                        /// suma la nueva
                                                        /// cantidad con lo
                                                        /// que se va a
                                                        /// ingresar
                                                        if (desmayorizar || invConsumos.getConsAnulado()) {
                                                            invProductoSaldos.setStkSaldoFinal(
                                                                    invProductoSaldosConsulta.getStkSaldoFinal()
                                                                            .add(listadoDetalleConsumos.get(i)
                                                                                    .getDetCantidad()));
                                                        } else {
                                                            invProductoSaldos.setStkSaldoFinal(
                                                                    invProductoSaldosConsulta.getStkSaldoFinal()
                                                                            .subtract(listadoDetalleConsumos.get(i)
                                                                                    .getDetCantidad()));
                                                        }
                                                        if (!desmayorizar) {
                                                            if (invProductoSaldos.getStkSaldoFinal()
                                                                    .compareTo(cero) >= 0 && !desmayorizar) {
                                                                invProductoSaldos.setStkFechaUltimaCompraFinal(
                                                                        invConsumos.getConsFecha());
                                                                invProductoSaldos.setStkValorUltimaCompraFinal(
                                                                        invProductoSaldosConsulta
                                                                                .getStkValorUltimaCompraFinal());

                                                                invProductoSaldos.setStkSaldoInicial(
                                                                        invProductoSaldosConsulta
                                                                                .getStkSaldoInicial());
                                                                invProductoSaldos.setStkValorPromedioInicial(
                                                                        invProductoSaldosConsulta
                                                                                .getStkValorPromedioInicial());
                                                                invProductoSaldos.setStkValorPromedioFinal(
                                                                        invProductoSaldosConsulta
                                                                                .getStkValorPromedioFinal());

                                                                listaInvProductoSaldos.add(invProductoSaldos);
                                                            } else {
                                                                retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                                                mensajeClase.add(listadoDetalleConsumos.get(i)
                                                                        .getInvProducto().getInvProductoPK()
                                                                        .getProCodigoPrincipal()
                                                                        + " \t\t"
                                                                        + listadoDetalleConsumos.get(i)
                                                                                .getInvProducto().getProNombre()
                                                                        + " : "
                                                                        + listadoDetalleConsumos.get(i)
                                                                                .getDetCantidad()
                                                                        + " - Saldo : "
                                                                        + invProductoSaldosConsulta
                                                                                .getStkSaldoFinal().setScale(2,
                                                                                        BigDecimal.ROUND_HALF_UP));
                                                            }
                                                        }
                                                    } else {
                                                        retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                                                        mensajeClase
                                                                .add(listadoDetalleConsumos.get(i).getInvProducto()
                                                                        .getInvProductoPK().getProCodigoPrincipal()
                                                                        + " \t\t"
                                                                        + listadoDetalleConsumos.get(i)
                                                                                .getInvProducto().getProNombre()
                                                                        + " : "
                                                                        + listadoDetalleConsumos.get(i)
                                                                                .getDetCantidad()
                                                                        + " - Saldo : " + new BigDecimal("0.00"));
                                                    }
                                                } else {
                                                    retorno = "F<html>No es posible guardar porque los siguientes productos son de tipo 'SERVICIOS' o 'ACTIVO FIJO':</html>";
                                                    mensajeClase.add(listadoDetalleConsumos.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal() + " \t\t"
                                                            + listadoDetalleConsumos.get(i).getInvProducto().getProNombre() + " - Tipo : "
                                                            + listadoDetalleConsumos.get(i).getInvProducto().getInvProductoTipo().getTipTipo());
                                                }
                                            }
                                        }

                                        if (mensajeClase.isEmpty()) {
                                            boolean comprobacionCc = false;
                                            String ccNumero = "";
                                            for (int i = 0; i < listInvConsumosDetalle.size(); i++) {
                                                ccNumero = "";

                                                List<PrdListaPiscinaTO> prdListaPiscinaTOs = piscinaDao
                                                        .getListaPiscinaBusqueda(
                                                                invConsumos.getInvConsumosPK().getConsEmpresa(),
                                                                listInvConsumosDetalle.get(i).getSecCodigo(),
                                                                UtilsValidacion.fecha(invConsumos.getConsFecha(),
                                                                        "yyyy-MM-dd"));

                                                for (int j = 0; j < prdListaPiscinaTOs.size(); j++) {
                                                    ccNumero = listInvConsumosDetalle.get(i).getPisNumero();
                                                    if (listInvConsumosDetalle.get(i).getPisNumero()
                                                            .equals(prdListaPiscinaTOs.get(j).getPisNumero())) {
                                                        comprobacionCc = true;
                                                        j = prdListaPiscinaTOs.size();
                                                    } else {
                                                        comprobacionCc = false;
                                                    }
                                                }
                                                if (!comprobacionCc) {
                                                    boolean anadirNuevo = false;
                                                    for (int k = 0; k < mensajeClase.size(); k++) {
                                                        if (!mensajeClase.get(k)
                                                                .equals("No existe un PERIODO DE CONSUMO para la PISCINA "
                                                                        + ccNumero)) {
                                                            anadirNuevo = true;
                                                        } else {
                                                            anadirNuevo = false;
                                                        }
                                                    }
                                                    if (anadirNuevo) {
                                                        mensajeClase
                                                                .add("No existe un PERIODO DE CONSUMO para la PISCINA "
                                                                        + ccNumero);
                                                        retorno = "FHubo un error al guardar el Consumo.";
                                                    }
                                                }

                                            }
                                            if (mensajeClase.isEmpty()) {
                                                if (invConsumos.getConsAnulado()) {
                                                    invConsumosMotivoAnulacion.setInvConsumos(invConsumos);
                                                }
                                                comprobar = consumosDao.modificarInvConsumos(invConsumos,
                                                        listInvConsumosDetalle, listInvConsumosDetalleEliminar,
                                                        sisSuceso, listaInvProductoSaldos,
                                                        invConsumosMotivoAnulacion, desmayorizar);
                                                if (comprobar) {
                                                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(
                                                            invConsumosTO.getConsEmpresa(),
                                                            invConsumos.getInvConsumosPK().getConsPeriodo());
                                                    retorno = "T<html>Se  "
                                                            + (invConsumosTO.getConsAnulado() ? "anulo"
                                                            : "modifico")
                                                            + "  el consumo con la siguiente informacion:<br><br>"
                                                            + "Periodo: <font size = 5>"
                                                            + sisPeriodo.getPerDetalle()
                                                            + "</font>.<br> Motivo: <font size = 5>"
                                                            + invConsumos.getInvConsumosPK().getConsMotivo()
                                                            + "</font>.<br> Numero: <font size = 5>"
                                                            + invConsumos.getInvConsumosPK().getConsNumero()
                                                            + "</font>.</html>";
                                                    mensajeTO.setFechaCreacion(
                                                            invConsumos.getUsrFechaInserta().toString());
                                                } else {
                                                    retorno = "FHubo un error al guardar el Consumo...\nIntente de nuevo o contacte con el administrador";
                                                }
                                            } else {
                                                mensajeTO.setMensaje(retorno);
                                                mensajeTO.setListaErrores1(mensajeClase);
                                            }
                                        } else {
                                            mensajeTO.setMensaje(retorno);
                                            mensajeTO.setListaErrores1(mensajeClase);
                                        }
                                    } else {
                                        if (estadoDetalleEliminar == 1) {
                                            retorno = "F<html>Uno de los Productos que escogio ya no esta¡ disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                        } else {
                                            retorno = "F<html>Una de las Bodega que escogio ya no esta¡ disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                        }
                                    }
                                } else {
                                    if (estadoDetalle == 1) {
                                        retorno = "F<html>Uno de los Productos que escogio ya no esta¡ disponible...\nIntente de nuevo, escoja otro Producto o contacte con el administrador</html>";
                                    } else {
                                        retorno = "F<html>Una de las Bodega que escogio ya no esta¡ disponible...\nIntente de nuevo, escoja otra Bodega o contacte con el administrador</html>";
                                    }
                                }
                            } else {
                                retorno = "F<html>" + detalleError + "</html>";
                            }
                        }
                    } else {
                        retorno = "FNo se encuentra el motivo...";
                    }
                } else {
                    retorno = "F<html>No se puede MAYORIZAR, DESMAYORIZAR o ANULAR debido a que el periodo se encuentra cerrado...</html>";
                }
            } else {
                retorno = "F<html>No se encuentra ningun periodo para la fecha que ingreso...</html>";
            }
        } else {
            retorno = "F<html>La fecha que ingreso es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return consumosDao.getEstadoCCCVT(empresa, periodo, motivo, numero);
    }

    @Override
    public MensajeTO insertarModificarInvConsumos(InvConsumos invConsumos, List<InvConsumosDetalle> listaInvConsumosDetalle, SisInfoTO sisInfoTO, boolean ignorarSeries, List<InvConsumosDatosAdjuntosWebTO> listadoImagenes) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        List<String> mensajeClase = new ArrayList<String>();

        if (UtilsValidacion.isFechaSuperior(invConsumos.getConsFecha())) {
            retorno = "FLa fecha que ingreso es superior a la fecha actual del servidor. Fecha actual del servidor: " + UtilsValidacion.fechaSistema("dd-MM-yyyy");
        } else if ((retorno = periodoService.validarPeriodo(invConsumos.getConsFecha(), invConsumos.getInvConsumosPK().getConsEmpresa())) != null) {
        } else if (!consumosMotivoDao.comprobarInvConsumosMotivo(invConsumos.getInvConsumosPK().getConsEmpresa(), invConsumos.getInvConsumosPK().getConsMotivo())) {
            retorno = "F<html>El motivo del consumo no existe...</html>";
        } else if (invConsumos.getConsCodigo() != null && !consumosDao.comprobarInvConsumosCodigoAleatorio(invConsumos.getConsCodigo())) {
            retorno = "FYa se ha registrado este consumo.";
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(invConsumos.getConsFecha(), invConsumos.getInvConsumosPK().getConsEmpresa());
            sisInfoTO.setEmpresa(invConsumos.getInvConsumosPK().getConsEmpresa());
            if (invConsumos.getInvConsumosPK().getConsNumero() == null || invConsumos.getInvConsumosPK().getConsNumero().compareToIgnoreCase("") == 0) {
                invConsumos.getInvConsumosPK().setConsPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                invConsumos.setConsRevisado(false);
                invConsumos.setConsAnulado(false);
                invConsumos.setUsrEmpresa(sisInfoTO.getEmpresa());
                invConsumos.setUsrCodigo(sisInfoTO.getUsuario());
                invConsumos.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            }

            //****************************Series************************************************************************************************
            for (InvConsumosDetalle detalle : listaInvConsumosDetalle) {
                if (detalle.getInvConsumosDetalleSeriesList() != null && detalle.getInvConsumosDetalleSeriesList().size() > 0) {
                    for (InvConsumosDetalleSeries serie : detalle.getInvConsumosDetalleSeriesList()) {
                        serie.setDetSecuencialConsumo(detalle);
                    }
                }
            }
            //********************************************************************************************************************************

            List<InvConsumosDetalle> listadoDetalleConsumos = agrupraProductosBodegaConsumo(listaInvConsumosDetalle);
            for (InvConsumosDetalle icd : listadoDetalleConsumos) {
                InvProductoSaldos invProductoSaldosConsulta = productoSaldosDao.getInvProductoSaldo(
                        icd.getInvProducto().getInvProductoPK().getProEmpresa(),
                        icd.getInvBodega().getInvBodegaPK().getBodCodigo(),
                        icd.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
                if (icd.getInvProducto().getInvProductoTipo().getTipTipo().equals("COSTO O GASTO")
                        || icd.getInvProducto().getInvProductoTipo().getTipTipo().equals("SERVICIOS")
                        || icd.getInvProducto().getInvProductoTipo().getTipTipo().equals("ACTIVO FIJO")) {
                    retorno = "F<html>No es posible guardar porque los siguientes productos son de tipo 'SERVICIO' o 'ACTIVO FIJO':</html>";
                    mensajeClase.add("Cos. O Gas.: " + icd.getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                            + " \t\t" + icd.getInvProducto().getProNombre() + " - Tipo : "
                            + icd.getInvProducto().getInvProductoTipo().getTipTipo());
                } else if (!invConsumos.getConsPendiente() && invProductoSaldosConsulta == null) {
                    retorno = "F<html>EL producto no existe o no hay stock suficiente en los siguientes productos:</html>";
                    mensajeClase.add("NExis. O Stock.: " + icd.getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                            + " \t\t" + icd.getInvProducto().getProNombre() + " Cantidad : "
                            + icd.getDetCantidad() + " - Saldo : " + new BigDecimal("0.00"));
                } else if (!invConsumos.getConsPendiente() && invProductoSaldosConsulta.getStkSaldoFinal().subtract(icd.getDetCantidad()).compareTo(cero) < 0) {
                    retorno = "F<html>No hay stock suficiente en los siguientes productos:</html>";
                    mensajeClase.add("Stock.: " + icd.getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                            + " \t\t" + icd.getInvProducto().getProNombre() + " Cantidad : " + icd.getDetCantidad()
                            + " - Saldo : "
                            + invProductoSaldosConsulta.getStkSaldoFinal().setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }

            if (!mensajeClase.isEmpty()) {
                mensajeTO.setListaErrores1(mensajeClase);
            } else {
                List<PrdListaPiscinaTO> prdListaPiscinaTOs = piscinaDao.getListaPiscinaBusqueda(
                        invConsumos.getInvConsumosPK().getConsEmpresa(), listaInvConsumosDetalle.get(0).getSecCodigo(),
                        UtilsValidacion.fecha(invConsumos.getConsFecha(), "yyyy-MM-dd"));
                for (InvConsumosDetalle icd : listaInvConsumosDetalle) {
                    String ccNumero = icd.getPisNumero();
                    boolean comprobacionCc = false;
                    for (PrdListaPiscinaTO pp : prdListaPiscinaTOs) {
                        if (icd.getPisNumero().compareToIgnoreCase(pp.getPisNumero()) == 0) {
                            comprobacionCc = true;
                            break;
                        }
                    }
                    if (!comprobacionCc) {
                        boolean anadirNuevo = true;
                        for (int k = 0; k < mensajeClase.size(); k++) {
                            if (mensajeClase.get(k).compareToIgnoreCase("No existe un PERIODO DE CONSUMO para la PISCINA " + ccNumero) == 0) {
                                anadirNuevo = false;
                                break;
                            }
                        }
                        if (anadirNuevo) {
                            mensajeClase.add("No existe un PERIODO DE CONSUMO para la PISCINA " + ccNumero);
                            retorno = "FHubo un error al guardar el Consumo...";
                        }
                    }
                }

                if (!mensajeClase.isEmpty()) {
                    mensajeTO.setListaErrores1(mensajeClase);
                } else {
                    susSuceso = (invConsumos.getInvConsumosPK().getConsNumero() == null
                            || invConsumos.getInvConsumosPK().getConsNumero().compareToIgnoreCase("") == 0) ? "INSERT"
                            : "UPDATE";
                    susTabla = "inventario.inv_consumo";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    String mensaje = (invConsumos.getInvConsumosPK().getConsNumero() == null
                            || invConsumos.getInvConsumosPK().getConsNumero().compareToIgnoreCase("") == 0) ? "ingreso"
                            : "modifico";
                    comprobar = consumosDao.insertarModificarInvConsumos(invConsumos, listaInvConsumosDetalle, sisSuceso, ignorarSeries);
                    actualizarImagenesConsumo(listadoImagenes, invConsumos.getInvConsumosPK());
                    if (!comprobar) {
                        retorno = "FHubo un error al guardar el Consumo.\nIntente de nuevo o contacte con el administrador";
                    } else {
                        retorno = "T<html>Se " + mensaje + " el consumo con la siguiente informacion:<br><br>"
                                + "Periodo: <font size = 5>" + periodo.getPerDetalle()
                                + "</font>.<br> Motivo: <font size = 5>"
                                + invConsumos.getInvConsumosPK().getConsMotivo()
                                + "</font>.<br> Numero: <font size = 5>"
                                + invConsumos.getInvConsumosPK().getConsNumero() + "</font>.<br>"
                                + invConsumos.getInvConsumosPK().getConsNumero() + ", "
                                + periodo.getSisPeriodoPK().getPerCodigo() + "</html>";
                        mensajeTO.setFechaCreacion(invConsumos.getUsrFechaInserta().toString());
                        mensajeTO.getMap().put("consumo", invConsumos);
                    }
                }
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public InvConsumos obtenerPorId(String empresa, String periodo, String motivo, String numero) {
        InvConsumos consumo = null;
        try {
            consumo = consumosDao.buscarInvConsumos(empresa, periodo, motivo, numero);
            return consumo;
        } catch (Exception ex) {
            Logger.getLogger(ConsumosServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return consumo;
    }

    @Override
    public void quitarPendiente(InvConsumosPK invConsumosPK, SisInfoTO sisInfoTO) {
        susClave = invConsumosPK.getConsPeriodo() + " " + invConsumosPK.getConsMotivo() + " " + invConsumosPK.getConsNumero();
        susDetalle = "";
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_consumo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                sisInfoTO);
        consumosDao.actualizarPendienteSql(invConsumosPK, false, sisSuceso);
    }

    @Override
    public List<String> desmayorizarConsumos(String empresa, List<InvListaConsultaConsumosTO> lista, SisInfoTO sisInfoTO) throws Exception {
        List<String> respuestas = new ArrayList<>();
        String numero;
        String motivo;
        String periodo;
        List<String> comprobante;
        for (InvListaConsultaConsumosTO consumo : lista) {
            comprobante = UtilsValidacion.separarComprobante(consumo.getConsNumero());
            periodo = comprobante.get(0);
            motivo = comprobante.get(1);
            numero = comprobante.get(2);
            String rpta = desmayorizarConsumo(empresa, periodo, motivo, numero, sisInfoTO);
            if (rpta != null && rpta.charAt(0) != 'T') {
                respuestas.add(rpta.substring(1));
                break;
            }
        }
        return respuestas;
    }

    @Override
    public List<String> desmayorizarConsumosLote(String empresa, List<InvListaConsultaConsumosTO> lista, SisInfoTO sisInfoTO) throws Exception {
        List<String> respuestas = new ArrayList<>();
        String numero;
        String motivo;
        String periodo;
        List<String> comprobante;
        for (InvListaConsultaConsumosTO consumo : lista) {
            comprobante = UtilsValidacion.separarComprobante(consumo.getConsNumero());
            periodo = comprobante.get(0);
            motivo = comprobante.get(1);
            numero = comprobante.get(2);
            String rpta = desmayorizarConsumo(empresa, periodo, motivo, numero, sisInfoTO);
            respuestas.add(rpta);
        }
        return respuestas;
    }

    @Override
    public String desmayorizarConsumo(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        InvConsumos invConsumos = obtenerPorId(empresa, periodo, motivo, numero);
        susClave = periodo + " " + motivo + " " + numero;
        susDetalle = "";
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_consumo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if ((mensaje = periodoService.validarPeriodo(invConsumos.getConsFecha(), invConsumos.getInvConsumosPK().getConsEmpresa())) != null) {
        } else if (invConsumos.getConsAnulado()) {
            mensaje = "FNo se puede Desmayorizar el consumo " + numero + " ya está Anulado";
        } else if (invConsumos.getConsPendiente()) {
            mensaje = "FNo se puede Desmayorizar el consumo " + numero + " ya está Desmayorizado";
        } else {
            consumosDao.actualizarPendienteSql(new InvConsumosPK(empresa, periodo, motivo, numero), true, sisSuceso);
            mensaje = "TEl consumo N. " + numero + " se ha desmayorizado correctamente.";
        }
        return mensaje;
    }

    @Override
    public String eliminarConsumo(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        InvConsumos invConsumos = obtenerPorId(empresa, periodo, motivo, numero);
        List<InvConsumosDatosAdjuntos> listadoAdjuntos = new ArrayList<>();
        if (invConsumos != null) {
            susClave = periodo + " " + motivo + " " + numero;
            susDetalle = "";
            susSuceso = "DELETE";
            susTabla = "inventario.inv_consumo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if ((mensaje = periodoService.validarPeriodo(invConsumos.getConsFecha(), invConsumos.getInvConsumosPK().getConsEmpresa())) != null) {
            } else {
                listadoAdjuntos = consumosDao.listarImagenesDeConsumo(new InvConsumosPK(empresa, periodo, motivo, numero));
                consumosDao.eliminarConsumo(new InvConsumosPK(empresa, periodo, motivo, numero), sisSuceso);
                mensaje = "TEl consumo N. " + numero + " se ha eliminado correctamente.";
            }
        } else {
            mensaje = "FEl consumo N. " + numero + " no se encuentra disponible o ya fue eliminado.";
        }
        if (mensaje.charAt(0) == 'T') {
            if (listadoAdjuntos != null && listadoAdjuntos.size() > 0) {
                listadoAdjuntos.forEach((itemEliminar) -> {
                    AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                });
            }
        }
        return mensaje;
    }

    @Override
    public String anularConsumo(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        InvConsumos invConsumos = obtenerPorId(empresa, periodo, motivo, numero);
        susClave = periodo + " " + motivo + " " + numero;
        susDetalle = "";
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_consumo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if ((mensaje = periodoService.validarPeriodo(invConsumos.getConsFecha(), invConsumos.getInvConsumosPK().getConsEmpresa())) != null) {
        } else if (invConsumos.getConsAnulado()) {
            mensaje = "FNo se puede Anular el consumo " + numero + " ya está Anulado";
        } else {
            consumosDao.anularRestaurarSql(new InvConsumosPK(empresa, periodo, motivo, numero), true, sisSuceso);
            mensaje = "TEl consumo N. " + numero + " se ha anulado correctamente.";
        }
        return mensaje;
    }

    @Override
    public String restaurarConsumo(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        InvConsumos invConsumos = obtenerPorId(empresa, periodo, motivo, numero);
        susClave = periodo + " " + motivo + " " + numero;
        susDetalle = "";
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_consumo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if ((mensaje = periodoService.validarPeriodo(invConsumos.getConsFecha(), invConsumos.getInvConsumosPK().getConsEmpresa())) != null) {
        } else if (!invConsumos.getConsAnulado()) {
            mensaje = "FNo se puede Restaurar el consumo " + numero + " porque no está Anulado";
        } else if (invConsumos.getConsPendiente()) {
            mensaje = "FNo se puede Restaurar el consumo " + numero + " está Pendiente";
        } else {
            consumosDao.anularRestaurarSql(new InvConsumosPK(empresa, periodo, motivo, numero), false, sisSuceso);
            mensaje = "TEl consumo N. " + numero + " se ha restaurado correctamente.";
        }
        return mensaje;
    }

    @Override
    public Map<String, Object> obtenerDatosParaCrudConsumos(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));

        String per = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));//para consultar
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));//para consultar
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));//para consultar
        String codigoUser = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUser"));//para consultar
        InvConsumos invConsumos = null;
        List<InvConsumosMotivoTO> listaConsumoMotivo = consumosMotivoDao.getInvListaConsumoMotivoTO(empresa, inactivo, null);
        List<InvListaBodegasTO> listaBodega = bodegaDao.buscarBodegasTO(empresa, inactivo, null);
        List<PrdListaPiscinaTO> piscinas = piscinaDao.getListaPiscina(empresa);
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo periodo = periodoService.getPeriodoPorFecha(UtilsDate.date(fechaActual), empresa);
        SisConfiguracionConsumosPK pk = new SisConfiguracionConsumosPK();
        pk.setConfEmpresa(empresa);
        pk.setConfUsuarioResponsable(codigoUser);
        SisConfiguracionConsumos conf = configuracionConsumosService.getSisConfiguracionConsumos(pk);
        if (per != null && motivo != null && numero != null) {
            invConsumos = obtenerPorId(empresa, per, motivo, numero);
        }

        //**************ERROR DE SERIES, SE TRABAJA CON ENTIDADES Y NO TO*******
        if (invConsumos != null && invConsumos.getInvConsumosDetalleList() != null && invConsumos.getInvConsumosDetalleList().size() > 0) {
            for (int i = 0; i < invConsumos.getInvConsumosDetalleList().size(); i++) {
                if (invConsumos.getInvConsumosDetalleList().get(i).getInvConsumosDetalleSeriesList() != null && invConsumos.getInvConsumosDetalleList().get(i).getInvConsumosDetalleSeriesList().size() > 0) {
                    for (InvConsumosDetalleSeries serie : invConsumos.getInvConsumosDetalleList().get(i).getInvConsumosDetalleSeriesList()) {
                        InvConsumosDetalle detalle = new InvConsumosDetalle();
                        detalle.setDetSecuencial(serie.getDetSecuencialConsumo().getDetSecuencial());
                        serie.setDetSecuencialConsumo(detalle);
                    }
                }
            }
        }

        //**********************************************************************
        //SI MOTIVO ES CON PRODUCTO
        InvProductoSaldos invProductoSaldos = null;
        if (invConsumos != null) {
            InvConsumosMotivo invConsumosMotivo = consumosMotivoDao.buscarInvConsumosMotivo(invConsumos.getInvConsumosPK().getConsEmpresa(), invConsumos.getInvConsumosPK().getConsMotivo());
            if (invConsumosMotivo != null && invConsumosMotivo.isCmExigirProducto() && invConsumos.getInvProducto() != null && invConsumos.getInvBodega() != null) {
                invProductoSaldos = productoSaldosDao.getInvProductoSaldo(
                        invConsumos.getInvBodega().getInvBodegaPK().getBodEmpresa(),
                        invConsumos.getInvBodega().getInvBodegaPK().getBodCodigo(),
                        invConsumos.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
            }

        }
        List<PrdEquipoControl> listadoEquiposControl = equipoControlDao.listarEquiposControl(empresa);
        campos.put("invProductoSaldos", invProductoSaldos);
        campos.put("listaConsumoMotivo", listaConsumoMotivo);
        campos.put("listaEquiposControl", listadoEquiposControl);
        campos.put("listaBodega", listaBodega);
        campos.put("piscinas", piscinas);
        campos.put("fechaActual", fechaActual);
        campos.put("invConsumos", invConsumos);
        campos.put("periodo", periodo);
        campos.put("configuracion", conf);

        return campos;
    }

    @Override
    public List<InvFunConsumosConsolidandoProductosTO> getInvFunConsumosConsolidandoProductosTOWeb(String empresa, String desde, String hasta, String sector, String bodega, String motivo) throws Exception {
        return consumosDao.obtenerInvFunConsumosConsolidandoProductosTO(empresa, desde, hasta, sector, bodega, motivo);
    }

    /*IMAGENES*/
    @Override
    public List<InvConsumosDatosAdjuntosWebTO> listarImagenesDeConsumo(InvConsumosPK invConsumosPK) throws Exception {
        List<InvConsumosDatosAdjuntos> listadoAdjuntos = null;
        List<InvConsumosDatosAdjuntosWebTO> listaRespuesta = new ArrayList<>();
        listadoAdjuntos = consumosDao.listarImagenesDeConsumo(invConsumosPK);
        for (InvConsumosDatosAdjuntos invAdjunto : listadoAdjuntos) {
            InvConsumosDatosAdjuntosWebTO invConsumosDatosAdjuntosWebTO = new InvConsumosDatosAdjuntosWebTO();
            invConsumosDatosAdjuntosWebTO.setAdjTipo(invAdjunto.getAdjTipo());
            invConsumosDatosAdjuntosWebTO.setAdjSecuencial(invAdjunto.getAdjSecuencial());
            invConsumosDatosAdjuntosWebTO.setInvConsumos(invAdjunto.getInvConsumos());
            invConsumosDatosAdjuntosWebTO.setAdjArchivo(invAdjunto.getAdjArchivo());
            invConsumosDatosAdjuntosWebTO.setAdjUrlArchivo(invAdjunto.getAdjUrlArchivo());
            invConsumosDatosAdjuntosWebTO.setAdjClaveArchivo(invAdjunto.getAdjClaveArchivo());
            invConsumosDatosAdjuntosWebTO.setAdjBucket(invAdjunto.getAdjBucket());
            listaRespuesta.add(invConsumosDatosAdjuntosWebTO);
        }
        return listaRespuesta;
    }

    @Override
    public boolean insertarImagenesConsumo(List<InvConsumosDatosAdjuntosWebTO> listado, InvConsumosPK invConsumosPK) throws Exception {
        String bucket = sistemaWebServicio.obtenerRutaImagen(invConsumosPK.getConsEmpresa());
        Bucket b = AmazonS3Crud.crearBucket(bucket);
        if (b != null) {
            for (InvConsumosDatosAdjuntosWebTO invConsumosDatosAdjuntosWebTO : listado) {
                if (invConsumosDatosAdjuntosWebTO.getAdjSecuencial() == null) {
                    InvConsumosDatosAdjuntos invAdjunto = new InvConsumosDatosAdjuntos();
                    invAdjunto.setAdjArchivo(invConsumosDatosAdjuntosWebTO.getImagenString().getBytes("UTF-8"));
                    String archivo = new String(invAdjunto.getAdjArchivo(), "UTF-8");
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(invAdjunto.getAdjClaveArchivo(), archivo);
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "consumos/" + invConsumosPK.getConsPeriodo() + "/" + invConsumosPK.getConsMotivo() + "/" + invConsumosPK.getConsNumero() + "/";
                    invAdjunto.setAdjTipo(invConsumosDatosAdjuntosWebTO.getAdjTipo());
                    invAdjunto.setInvConsumos(new InvConsumos(invConsumosPK));
                    invAdjunto.setAdjBucket(bucket);
                    invAdjunto.setAdjClaveArchivo(carpeta + nombre);
                    invAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    invConsumosDatosAdjuntosDao.insertar(invAdjunto);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, archivo, combo.getValor());
                }
            }
        } else {
            throw new GeneralException("Error al crear contenedor de imágenes.");
        }
        return true;
    }

    @Override
    public boolean actualizarImagenesConsumo(List<InvConsumosDatosAdjuntosWebTO> listado, InvConsumosPK invConsumosPK) throws Exception {
        SisPeriodo periodo = periodoService.buscarPeriodo(invConsumosPK.getConsEmpresa(), invConsumosPK.getConsPeriodo());
        if (periodo != null && !periodo.getPerCerrado()) {
            List<InvConsumosDatosAdjuntos> listAdjuntosEnLaBase = consumosDao.listarImagenesDeConsumo(invConsumosPK);
            if (listado != null && !listado.isEmpty()) {
                if (listAdjuntosEnLaBase.size() > 0) {
                    listAdjuntosEnLaBase.removeAll(listado);//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                    listado.removeAll(listAdjuntosEnLaBase);//eliminar lo que hay en la base de lo que viene del cliente
                    if (listAdjuntosEnLaBase.size() > 0) {

                        listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                            invConsumosDatosAdjuntosDao.eliminar(itemEliminar);
                            AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                        });

                    }
                }
                insertarImagenesConsumo(listado, invConsumosPK);
            } else {
                if (listAdjuntosEnLaBase.size() > 0) {
                    listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                        invConsumosDatosAdjuntosDao.eliminar(itemEliminar);
                        AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                    });
                }
            }
        } else {
            throw new GeneralException("No se puede adjuntar imágenes debido a que el periodo se encuentra cerrado.");
        }

        return true;
    }

    @Override
    public boolean guardarImagenesConsumos(InvConsumosPK pk, List<InvConsumosDatosAdjuntosWebTO> imagenes, SisInfoTO sisInfoTO) throws Exception {
        List<InvConsumosDatosAdjuntosWebTO> listaImagenes = new ArrayList<>();
        if (imagenes != null && imagenes.size() > 0) {
            for (InvConsumosDatosAdjuntosWebTO item : imagenes) {
                /// PREPARANDO OBJETO SISSUCESO
                if (item.getAdjSecuencial() == null) {
                    susClave = pk.getConsEmpresa() + "|" + pk.getConsPeriodo() + "|" + pk.getConsMotivo() + "|" + pk.getConsNumero();
                    susDetalle = "Se agregó la imagen para el consumo: " + susClave;
                    susSuceso = "INSERT";
                    susTabla = "inventario.inv_consumos_datos_adjuntos";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sucesoDao.insertar(sisSuceso);
                    InvConsumosDatosAdjuntosWebTO imagen = new InvConsumosDatosAdjuntosWebTO();
                    imagen.setInvConsumos(new InvConsumos(pk));
                    imagen.setAdjSecuencial(item.getAdjSecuencial());
                    imagen.setAdjTipo(item.getAdjTipo());
                    imagen.setImagenString(item.getImagenString());
                    listaImagenes.add(imagen);
                } else {
                    InvConsumosDatosAdjuntosWebTO imagen = new InvConsumosDatosAdjuntosWebTO();
                    imagen.setInvConsumos(new InvConsumos(pk));
                    imagen.setAdjSecuencial(item.getAdjSecuencial());
                    imagen.setAdjTipo(item.getAdjTipo());
                    imagen.setImagenString(item.getImagenString());
                    listaImagenes.add(imagen);
                }
            }
        }
        actualizarImagenesConsumo(listaImagenes, pk);
        return true;
    }

}
