package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.ChequeDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDetalleAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDetalleFormaDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDetalleVentasDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDetalleAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDetalleComprasDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDetalleFormaDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.SalarioDignoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.VacacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoCobroDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoCobrosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoContableDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoPagoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoPagosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCar;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobros;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleVentas;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleCompras;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoCierreTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosAntiguoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosVsInventarioDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceCentroProduccionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.PersonaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConAdjuntosContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhSalarioDigno;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCobro;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoContable;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoPagosAnticipos;

@Repository
public class ContableDaoImpl extends GenericDaoImpl<ConContable, ConContablePK> implements ContableDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private DetalleDao detalleDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ChequeDao chequeDao;
    @Autowired
    private CobrosAnticiposDao carCobrosAnticiposDao;
    @Autowired
    private VacacionesDao rhVacacionesDao;
    @Autowired
    private SalarioDignoDao rhSalarioDignoDao;
    @Autowired
    private PagosDao pagosDao;
    @Autowired
    private CobrosDao cobrosDao;
    @Autowired
    private PagosDetalleAnticiposDao pagosDetalleAnticiposDao;
    @Autowired
    private PagosDetalleComprasDao pagosDetalleComprasDao;
    @Autowired
    private PagosDetalleFormaDao pagosDetalleFormaDao;
    @Autowired
    private CobrosDetalleAnticiposDao cobrosDetalleAnticiposDao;
    @Autowired
    private CobrosDetalleFormaDao cobrosDetalleFormaDao;
    @Autowired
    private CobrosDetalleVentasDao cobrosDetalleVentasDao;
    @Autowired
    private PagosAnticiposDao pagosAnticiposDao;
    @Autowired
    private VentasDao invVentasDao;
    @Autowired
    private ComprasDao comprasDao;
    @Autowired
    private SucesoContableDao sucesoContableDao;
    @Autowired
    private GenericoDao<ConAdjuntosContable, Integer> conAdjuntosContableDao;
    @Autowired
    private SucesoPagoDao sucesoPagoDao;
    @Autowired
    private SucesoCobroDao sucesoCobroDao;
    @Autowired
    private SucesoCobrosAnticiposDao sucesoCobrosAnticiposDao;
    @Autowired
    private SucesoPagosAnticiposDao sucesoPagosAnticiposDao;

    @Override
    public boolean modificarConContableVentasMayorizar(ConContable conContable, List<ConDetalle> listaConDetalle,
            List<ConDetalle> listaConDetalleEliminar, InvVentas invVentas, SisSuceso sisSuceso) throws Exception {
        invVentasDao.saveOrUpdate(invVentas);
        saveOrUpdate(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            if (listaConDetalle.get(i).getPrdPiscina() != null
                    && listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK() != null
                    && (listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK().getPisNumero() == null
                    || listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK().getPisNumero()
                            .compareToIgnoreCase("") == 0)) {
                listaConDetalle.get(i).setPrdPiscina(null);
            }
            detalleDao.saveOrUpdate(listaConDetalle.get(i));
        }
        for (ConDetalle conDetalle : listaConDetalleEliminar) {
            conDetalle.setConContable(conContable);
            if (detalleDao.obtenerPorId(ConDetalle.class, conDetalle.getDetSecuencia()) != null) {
                detalleDao.evict(detalleDao.obtenerPorId(ConDetalle.class, conDetalle.getDetSecuencia()));
                detalleDao.eliminar(conDetalle);
            }
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean anularConContable(ConContablePK conContablePK, SisSuceso sisSuceso, boolean isbloqueado) throws Exception {
        String sql = "UPDATE contabilidad.con_contable SET con_bloqueado=" + isbloqueado + " WHERE con_empresa='"
                + conContablePK.getConEmpresa() + "' and  con_periodo='" + conContablePK.getConPeriodo()
                + "' and con_tipo='" + conContablePK.getConTipo() + "' and con_numero='" + conContablePK.getConNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean anularConContable(ConContablePK conContablePK, SisSuceso sisSuceso) throws Exception {
        String sql = "UPDATE contabilidad.con_contable SET con_anulado = true WHERE con_empresa='"
                + conContablePK.getConEmpresa() + "' and  con_periodo='" + conContablePK.getConPeriodo()
                + "' and con_tipo='" + conContablePK.getConTipo() + "' and con_numero='" + conContablePK.getConNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String eliminarConContable(ConContablePK conContablePK) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_eliminar_contables ('"
                + conContablePK.getConEmpresa() + "', '" + conContablePK.getConPeriodo()
                + "', '" + conContablePK.getConTipo() + "', '" + conContablePK.getConNumero()
                + "');";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    private boolean insertarConContable(ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso,
            boolean nuevo) throws Exception {
        conContable.setConReversado(false);
        insertar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            if (listaConDetalle.get(i).getPrdPiscina() != null
                    && listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK() != null
                    && (listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK().getPisNumero() == null
                    || listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK().getPisNumero()
                            .compareToIgnoreCase("") == 0)) {
                listaConDetalle.get(i).setPrdPiscina(null);
            }
            detalleDao.insertar(listaConDetalle.get(i));
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    private boolean insertarVacacionesConContable(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, boolean nuevo, RhVacaciones rhVacaciones) throws Exception {
        insertar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            if (listaConDetalle.get(i).getPrdPiscina() != null
                    && listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK() != null
                    && (listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK().getPisNumero() == null
                    || listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK().getPisNumero()
                            .compareToIgnoreCase("") == 0)) {
                listaConDetalle.get(i).setPrdPiscina(null);
            }
            detalleDao.insertar(listaConDetalle.get(i));
        }
        rhVacacionesDao.insertar(rhVacaciones);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarRhVacaciones(RhVacaciones rhVacaciones, ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, SisInfoTO sisInfoTO) throws Exception {
        saveOrUpdate(conContable);
        rhVacacionesDao.saveOrUpdate(rhVacaciones);
        List<ConDetalle> detalleEnBase = detalleDao.getListConDetalle(conContable.getConContablePK());
        if (detalleEnBase != null && !detalleEnBase.isEmpty()) {
            detalleEnBase.forEach((detalle) -> {
                detalleDao.eliminar(detalle);
            });
        }
        detalleDao.insertar(listaConDetalle);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    private boolean insertarSalarioDignoConContable(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, boolean nuevo, RhSalarioDigno rhSalarioDigno) throws Exception {
        insertar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            detalleDao.insertar(listaConDetalle.get(i));
        }
        rhSalarioDignoDao.insertar(rhSalarioDigno);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    private boolean insertarCarPagos(ConContable conContable, SisSuceso sisSuceso,
            CarPagos carPagos, List<CarPagosDetalleCompras> carPagosDetalleCompras,
            List<CarPagosDetalleForma> carPagosDetalleFormas, List<CarPagosDetalleAnticipos> carPagosDetalleAnticiposes)
            throws Exception {
        List<CarPagosDetalleCompras> listaDetalleCompras = new ArrayList<>();
        List<CarPagosDetalleAnticipos> listaDetalleAnticipos = new ArrayList<>();
        List<CarPagosDetalleForma> listaDetalleForma = new ArrayList<>();

        insertar(conContable);
        pagosDao.insertar(carPagos);

        for (CarPagosDetalleCompras carPagosDetalleCompra : carPagosDetalleCompras) {
            pagosDetalleComprasDao.insertar(carPagosDetalleCompra);
            CarPagosDetalleCompras item = ConversionesCar.convertirCarPagosDetalleCompras_CarPagosDetalleCompras(carPagosDetalleCompra);
            listaDetalleCompras.add(item);
        }

        for (CarPagosDetalleForma carPagosDetalleForma : carPagosDetalleFormas) {
            pagosDetalleFormaDao.insertar(carPagosDetalleForma);
            CarPagosDetalleForma item = ConversionesCar.convertirCarPagosDetalleForma_CarPagosDetalleForma(carPagosDetalleForma);
            listaDetalleForma.add(item);
        }

        for (CarPagosDetalleAnticipos carPagosDetalleAnticipos : carPagosDetalleAnticiposes) {
            pagosDetalleAnticiposDao.insertar(carPagosDetalleAnticipos);
            CarPagosDetalleAnticipos item = ConversionesCar.convertirCarPagosDetalleAnticipos_CarPagosDetalleAnticipos(carPagosDetalleAnticipos);
            listaDetalleAnticipos.add(item);
        }

        sucesoDao.insertar(sisSuceso);
        ////////////////////////insertar suceso/////////////////////////////////////
        SisSucesoPago sucesoPago = new SisSucesoPago();
        CarPagos copia = ConversionesCar.convertirCarPagos_CarPagos(carPagos);

        if (listaDetalleCompras.size() > 0) {
            copia.setCarPagosDetalleComprasList(listaDetalleCompras);
            for (CarPagosDetalleCompras det : copia.getCarPagosDetalleComprasList()) {
                det.setCarPagos(null);
            }
        }
        if (listaDetalleAnticipos.size() > 0) {
            copia.setCarPagosDetalleAnticiposList(listaDetalleAnticipos);
            for (CarPagosDetalleAnticipos det : copia.getCarPagosDetalleAnticiposList()) {
                det.setCarPagos(null);
            }
        }
        if (listaDetalleForma.size() > 0) {
            copia.setCarPagosDetalleFormaList(listaDetalleForma);
            for (CarPagosDetalleForma det : copia.getCarPagosDetalleFormaList()) {
                det.setCarPagos(null);
            }
        }
        String json = UtilsJSON.objetoToJson(copia);
        sucesoPago.setSusJson(json);
        sucesoPago.setSisSuceso(sisSuceso);
        sucesoPago.setCarPagos(copia);
        sucesoPagoDao.insertar(sucesoPago);

        return true;
    }

    @Override
    public boolean activarWispro(SisInfoTO sisSuceso, List<CarCobrosDetalleVentasTO> carCobrosDetalleVentas) throws Exception {
        //contrato
        return true;
    }

    private boolean insertarCarCobros(ConContable conContable, SisSuceso sisSuceso, CarCobros carCobros, List<CarCobrosDetalleVentas> carCobrosDetalleVentas,
            List<CarCobrosDetalleForma> carCobrosDetalleFormas,
            List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticiposes) throws Exception {
        List<CarCobrosDetalleVentas> listaDetalleVentas = new ArrayList<>();
        List<CarCobrosDetalleAnticipos> listaDetalleAnticipos = new ArrayList<>();
        List<CarCobrosDetalleForma> listaDetalleForma = new ArrayList<>();
        insertar(conContable);
        cobrosDao.insertar(carCobros);
        for (CarCobrosDetalleVentas carCobrosDetalleVenta : carCobrosDetalleVentas) {
            cobrosDetalleVentasDao.insertar(carCobrosDetalleVenta);
            CarCobrosDetalleVentas item = ConversionesCar.convertirCarCobrosDetalleVentas_CarCobrosDetalleVentas(carCobrosDetalleVenta);
            listaDetalleVentas.add(item);
        }

        for (CarCobrosDetalleForma carCobrosDetalleForma : carCobrosDetalleFormas) {
            cobrosDetalleFormaDao.insertar(carCobrosDetalleForma);
            CarCobrosDetalleForma item = ConversionesCar.convertirCarCobrosDetalleForma_CarCobrosDetalleForma(carCobrosDetalleForma);
            listaDetalleForma.add(item);
        }

        for (CarCobrosDetalleAnticipos carCobrosDetalleAnticipos : carCobrosDetalleAnticiposes) {
            cobrosDetalleAnticiposDao.insertar(carCobrosDetalleAnticipos);
            CarCobrosDetalleAnticipos item = ConversionesCar.convertirCarCobrosDetalleAnticipos_CarCobrosDetalleAnticipos(carCobrosDetalleAnticipos);
            listaDetalleAnticipos.add(item);
        }

        sucesoDao.insertar(sisSuceso);
        ////////////////////////insertar suceso/////////////////////////////////////
        SisSucesoCobro sucesoCobro = new SisSucesoCobro();
        CarCobros copia = ConversionesCar.convertirCarCobros_CarCobros(carCobros);

        if (listaDetalleVentas.size() > 0) {
            copia.setCarCobrosDetalleVentasList(listaDetalleVentas);
            for (CarCobrosDetalleVentas det : copia.getCarCobrosDetalleVentasList()) {
                det.setCarCobros(null);
            }
        }
        if (listaDetalleAnticipos.size() > 0) {
            copia.setCarCobrosDetalleAnticiposList(listaDetalleAnticipos);
            for (CarCobrosDetalleAnticipos det : copia.getCarCobrosDetalleAnticiposList()) {
                det.setCarCobros(null);
            }
        }
        if (listaDetalleForma.size() > 0) {
            copia.setCarCobrosDetalleFormaList(listaDetalleForma);
            for (CarCobrosDetalleForma det : copia.getCarCobrosDetalleFormaList()) {
                det.setCarCobros(null);
            }
        }
        String json = UtilsJSON.objetoToJson(copia);
        sucesoCobro.setSusJson(json);
        sucesoCobro.setSisSuceso(sisSuceso);
        sucesoCobro.setCarCobros(copia);
        sucesoCobroDao.insertar(sucesoCobro);
        return true;
    }

    private boolean insertarVentasContable(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, boolean nuevo, List<InvVentas> invVentas) throws Exception {

        insertar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            detalleDao.insertar(listaConDetalle.get(i));
        }

        if (invVentas != null) {
            if (!invVentas.isEmpty()) {
                for (int i = 0; i < invVentas.size(); i++) {

                    invVentas.get(i).setConEmpresa(conContable.getConContablePK().getConEmpresa());
                    invVentas.get(i).setConNumero(conContable.getConContablePK().getConNumero());
                    invVentas.get(i).setConPeriodo(conContable.getConContablePK().getConPeriodo());
                    invVentas.get(i).setConTipo(conContable.getConContablePK().getConTipo());
                    invVentasDao.actualizar(invVentas.get(i));
                }
            }
        }

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    private boolean insertarAnticipoPagoCarteraContable(ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, boolean nuevo, CarPagosAnticipos carPagosAnticipos) throws Exception {
        insertar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            detalleDao.insertar(listaConDetalle.get(i));
        }

        carPagosAnticipos.setCarPagosAnticiposPK(new CarPagosAnticiposPK(conContable.getConContablePK().getConEmpresa(),
                conContable.getConContablePK().getConPeriodo(), conContable.getConContablePK().getConTipo(),
                conContable.getConContablePK().getConNumero()));
        pagosAnticiposDao.insertar(carPagosAnticipos);
        sucesoDao.insertar(sisSuceso);
        //crear suceso anticipos
        SisSucesoPagosAnticipos sucesoPagoAnt = new SisSucesoPagosAnticipos();
        CarPagosAnticipos copia = ConversionesCar.convertirCarPagosAnticipos_CarPagosAnticipos(carPagosAnticipos);
        if (copia.getCarPagosDetalleAnticiposList() != null && copia.getCarPagosDetalleAnticiposList().size() > 0) {
            for (CarPagosDetalleAnticipos det : copia.getCarPagosDetalleAnticiposList()) {
                det.setCarPagosAnticipos(null);
            }
        }

        if (copia.getFpSecuencial() != null) {
            copia.getFpSecuencial().setCarPagosAnticiposList(null);
            copia.getFpSecuencial().setCarPagosDetalleFormaList(null);
        }
        String json = UtilsJSON.objetoToJson(copia);
        if (conContable != null) {
            String jsonCont = UtilsJSON.objetoToJson(conContable);
            if (json != null && !json.equals("") && jsonCont != null && !jsonCont.equals("")) {
                json = json.substring(0, json.length() - 1) + "," + "\"conContable\"" + ":" + jsonCont + "}";
            }
        }
        sucesoPagoAnt.setSusJson(json);
        sucesoPagoAnt.setSisSuceso(sisSuceso);
        sucesoPagoAnt.setCarPagosAnticipos(copia);
        sucesoPagosAnticiposDao.insertar(sucesoPagoAnt);
        return true;
    }

    private boolean mayorizarAnticipoPagoCarteraContable(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, CarPagosAnticipos carPagosAnticipos) throws Exception {
        actualizar(conContable);

        List<ConDetalle> detallesEnBase = detalleDao.getListConDetalle(conContable.getConContablePK());
        if (detallesEnBase != null && !detallesEnBase.isEmpty()) {
            detallesEnBase.forEach(detalle -> {
                detalleDao.eliminar(detalle);
            });
        }
        for (int i = 0; i < listaConDetalle.size(); i++) {
            detalleDao.insertar(listaConDetalle.get(i));
        }
        pagosAnticiposDao.actualizar(carPagosAnticipos);
        sucesoDao.insertar(sisSuceso);
        //crear suceso anticipos
        SisSucesoPagosAnticipos sucesoPagoAnt = new SisSucesoPagosAnticipos();
        CarPagosAnticipos copia = ConversionesCar.convertirCarPagosAnticipos_CarPagosAnticipos(carPagosAnticipos);
        if (copia.getCarPagosDetalleAnticiposList() != null && copia.getCarPagosDetalleAnticiposList().size() > 0) {
            for (CarPagosDetalleAnticipos det : copia.getCarPagosDetalleAnticiposList()) {
                det.setCarPagosAnticipos(null);
            }
        }

        if (copia.getFpSecuencial() != null) {
            copia.getFpSecuencial().setCarPagosAnticiposList(null);
            copia.getFpSecuencial().setCarPagosDetalleFormaList(null);
        }
        String json = UtilsJSON.objetoToJson(copia);
        if (conContable != null) {
            String jsonCont = UtilsJSON.objetoToJson(conContable);
            if (json != null && !json.equals("") && jsonCont != null && !jsonCont.equals("")) {
                json = json.substring(0, json.length() - 1) + "," + "\"conContable\"" + ":" + jsonCont + "}";
            }
        }
        sucesoPagoAnt.setSusJson(json);
        sucesoPagoAnt.setSisSuceso(sisSuceso);
        sucesoPagoAnt.setCarPagosAnticipos(copia);
        sucesoPagosAnticiposDao.insertar(sucesoPagoAnt);
        return true;
    }

    private boolean insertarAnticipoCobroCarteraContable(ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, boolean nuevo, CarCobrosAnticipos carCobrosAnticipos) throws Exception {
        conContable.setConReversado(false);
        insertar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            detalleDao.insertar(listaConDetalle.get(i));
        }

        carCobrosAnticipos.setCarCobrosAnticiposPK(new CarCobrosAnticiposPK(
                conContable.getConContablePK().getConEmpresa(), conContable.getConContablePK().getConPeriodo(),
                conContable.getConContablePK().getConTipo(), conContable.getConContablePK().getConNumero()));
        carCobrosAnticiposDao.insertar(carCobrosAnticipos);
        sucesoDao.insertar(sisSuceso);
        /////////////////insertar suceso anticipos
        SisSucesoCobrosAnticipos sucesoCobroAnt = new SisSucesoCobrosAnticipos();
        CarCobrosAnticipos copia = ConversionesCar.convertirCarCobrosAnticipos_CarCobrosAnticipos(carCobrosAnticipos);
        if (copia.getCarCobrosDetalleAnticiposList() != null && copia.getCarCobrosDetalleAnticiposList().size() > 0) {
            for (CarCobrosDetalleAnticipos det : copia.getCarCobrosDetalleAnticiposList()) {
                CarCobrosDetalleAnticipos detalle = ConversionesCar.convertirCarCobrosDetalleAnticipos_CarCobrosDetalleAnticipos(det);
                detalle.setCarCobrosAnticipos(null);
            }
        }

        if (copia.getFpSecuencial() != null) {
            copia.getFpSecuencial().setCarCobrosAnticiposList(null);
            copia.getFpSecuencial().setCarCobrosDetalleFormaList(null);
        }

        String json = UtilsJSON.objetoToJson(copia);
        if (conContable != null) {
            String jsonCont = UtilsJSON.objetoToJson(conContable);
            if (json != null && !json.equals("") && jsonCont != null && !jsonCont.equals("")) {
                json = json.substring(0, json.length() - 1) + "," + "\"conContable\"" + ":" + jsonCont + "}";
            }
        }
        sucesoCobroAnt.setSusJson(json);
        sucesoCobroAnt.setSisSuceso(sisSuceso);
        sucesoCobroAnt.setCarCobrosAnticipos(copia);
        sucesoCobrosAnticiposDao.insertar(sucesoCobroAnt);
        return true;
    }

    private boolean cambioChequeContable(ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso,
            boolean nuevo, BanCheque banCheque) throws Exception {
        conContable.setConReversado(false);
        insertar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            detalleDao.insertar(listaConDetalle.get(i));
        }

        chequeDao.actualizar(banCheque);

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarTransaccionContable(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, ConNumeracion conNumeracion, RhVacaciones rhVacaciones, RhSalarioDigno rhSalarioDigno,
            CarPagos carPagos, List<CarPagosDetalleAnticipos> carPagosDetalleAnticiposes,
            List<CarPagosDetalleCompras> carPagosDetalleCompras, List<CarPagosDetalleForma> carPagosDetalleFormas,
            InvCompras invCompras, CarCobros carCobros, List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticiposes,
            List<CarCobrosDetalleVentas> carCobrosDetalleVentas, List<CarCobrosDetalleForma> carCobrosDetalleFormas,
            List<InvVentas> invVentas, CarPagosAnticipos carPagosAnticipos, CarCobrosAnticipos carCobrosAnticipos,
            BanCheque banCheque, SisInfoTO sisInfoTO) throws Exception {

        conContable.setConPendiente(true);

        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracion(conContable.getConContablePK().getConEmpresa(),
                conContable.getConContablePK().getConPeriodo(), conContable.getConContablePK().getConTipo());
        boolean nuevo = false, retorno = false;
        if (numeracion == 0) {
            nuevo = true;
        }
        if (obtenerPorId(ConContable.class,
                new ConContablePK(conContable.getConContablePK().getConEmpresa(),
                        conContable.getConContablePK().getConPeriodo(), conContable.getConContablePK().getConTipo(),
                        String.format("%07d", conNumeracion.getNumSecuencia()))) == null
                && (invCompras != null || invVentas != null)) {
            conContable.setConContablePK(new ConContablePK(conNumeracion.getConNumeracionPK().getNumEmpresa(),
                    conNumeracion.getConNumeracionPK().getNumPeriodo(), conNumeracion.getConNumeracionPK().getNumTipo(),
                    String.format("%07d", conNumeracion.getNumSecuencia())));
        } else {
            do {
                numeracion++;
                int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
                rellenarConCeros = "";
                for (int i = 0; i < numeroCerosAponer; i++) {
                    rellenarConCeros = rellenarConCeros + "0";
                }
                conContable.setConContablePK(new ConContablePK(conNumeracion.getConNumeracionPK().getNumEmpresa(),
                        conNumeracion.getConNumeracionPK().getNumPeriodo(),
                        conNumeracion.getConNumeracionPK().getNumTipo(), rellenarConCeros + numeracion));
                conNumeracion.setNumSecuencia(numeracion);
            } while (obtenerPorId(ConContable.class,
                    new ConContablePK(conContable.getConContablePK().getConEmpresa(),
                            conContable.getConContablePK().getConPeriodo(), conContable.getConContablePK().getConTipo(),
                            rellenarConCeros + numeracion)) != null);
            conContable.getConContablePK().getConNumero();
        }

        if (rhVacaciones == null && rhSalarioDigno == null) {
            sisSuceso.setSusDetalle("Se insertó contable del periodo " + conContable.getConContablePK().getConPeriodo()
                    + ", del tipo contable " + conContable.getConContablePK().getConTipo() + ", de la numeracion "
                    + conContable.getConContablePK().getConNumero());
        }

        if (listaConDetalle != null && invCompras == null && invVentas == null) {
            for (int i = 0; i < listaConDetalle.size(); i++) {
                listaConDetalle.get(i).setDetSaldo(new BigDecimal("0.00"));
                listaConDetalle.get(i).getPrdSector().getPrdSectorPK()
                        .setSecEmpresa(listaConDetalle.get(i).getConContable().getConContablePK().getConEmpresa());
            }
        }

        sisSuceso.setSusClave(
                conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " "
                + rellenarConCeros + String.valueOf(conNumeracion.getNumSecuencia()));
        ////// aqui va el metodo a llamar

        // CONTABLE
        if (rhVacaciones == null && rhSalarioDigno == null && carPagos == null && carCobros == null
                && invCompras == null && invVentas == null && carPagosAnticipos == null && carCobrosAnticipos == null
                && banCheque == null) {

            retorno = insertarConContable(conContable, listaConDetalle, sisSuceso, nuevo);

        } else {

            // VACACIONES
            if (rhVacaciones != null) {
                rhVacaciones.setConNumero(rellenarConCeros + String.valueOf(conNumeracion.getNumSecuencia()));
                retorno = insertarVacacionesConContable(conContable, listaConDetalle, sisSuceso, nuevo, rhVacaciones);
            }
            // SALARIO DIGNO
            if (rhSalarioDigno != null) {
                rhSalarioDigno.setConNumero(rellenarConCeros + String.valueOf(conNumeracion.getNumSecuencia()));
                retorno = insertarSalarioDignoConContable(conContable, listaConDetalle, sisSuceso, nuevo,
                        rhSalarioDigno);
            }

            // PAGOS
            if (carPagos != null) {
                carPagos.setCarPagosPK(new CarPagosPK(conContable.getConContablePK().getConEmpresa(),
                        conContable.getConContablePK().getConPeriodo(), carPagos.getCarPagosPK().getPagTipo(),
                        conContable.getConContablePK().getConNumero()));
                java.math.BigDecimal valorPago = new java.math.BigDecimal("0.00");
                for (int i = 0; i < carPagosDetalleCompras.size(); i++) {
                    carPagosDetalleCompras.get(i).setCarPagos(carPagos);
                    valorPago = valorPago.add(carPagosDetalleCompras.get(i).getDetValor());
                }
                for (int i = 0; i < carPagosDetalleFormas.size(); i++) {
                    carPagosDetalleFormas.get(i).setCarPagos(carPagos);
                }
                for (int i = 0; i < carPagosDetalleAnticiposes.size(); i++) {
                    carPagosDetalleAnticiposes.get(i).setCarPagos(carPagos);
                }

                String detalle = "Pago a " + carPagos.getProvCodigo() + " por $" + valorPago.toPlainString();
                sisSuceso.setSusDetalle(detalle.length() > 300 ? detalle.substring(0, 300) : detalle);

                retorno = insertarCarPagos(conContable, sisSuceso, carPagos,
                        carPagosDetalleCompras, carPagosDetalleFormas, carPagosDetalleAnticiposes);
            }
            // COBROS
            if (carCobros != null) {
                carCobros.setCarCobrosPK(new CarCobrosPK(conContable.getConContablePK().getConEmpresa(),
                        conContable.getConContablePK().getConPeriodo(), carCobros.getCarCobrosPK().getCobTipo(),
                        conContable.getConContablePK().getConNumero()));
                java.math.BigDecimal valorPago = new java.math.BigDecimal("0.00");
                for (int i = 0; i < carCobrosDetalleVentas.size(); i++) {
                    carCobrosDetalleVentas.get(i).setCarCobros(carCobros);
                    valorPago = valorPago.add(carCobrosDetalleVentas.get(i).getDetValor());
                }
                for (int i = 0; i < carCobrosDetalleFormas.size(); i++) {
                    carCobrosDetalleFormas.get(i).setCarCobros(carCobros);
                }
                for (int i = 0; i < carCobrosDetalleAnticiposes.size(); i++) {
                    carCobrosDetalleAnticiposes.get(i).setCarCobros(carCobros);
                }

                String detalle = "Cobro a " + carCobros.getCliCodigo() + " por $" + valorPago.toPlainString();
                sisSuceso.setSusDetalle(detalle.length() > 300 ? detalle.substring(0, 300) : detalle);

                retorno = insertarCarCobros(conContable, sisSuceso, carCobros,
                        carCobrosDetalleVentas, carCobrosDetalleFormas, carCobrosDetalleAnticiposes);
            }

            // COMPRAS
            if (invCompras != null) {
                conContable.setConPendiente(false);
                retorno = insertarCompraContable(conContable, listaConDetalle, sisSuceso, nuevo, invCompras);
            }
            // VENTAS
            if (invVentas != null) {
                conContable.setConPendiente(false);
                retorno = insertarVentasContable(conContable, listaConDetalle, sisSuceso, nuevo, invVentas);
            }
            // PAGO ANTICIPO - MODULO DE CARTERA
            if (carPagosAnticipos != null) {
                retorno = insertarAnticipoPagoCarteraContable(conContable, listaConDetalle, sisSuceso, nuevo,
                        carPagosAnticipos);
            }
            // COBRO ANTICIPO - MODULO DE CARTERA
            if (carCobrosAnticipos != null) {
                retorno = insertarAnticipoCobroCarteraContable(conContable, listaConDetalle, sisSuceso, nuevo,
                        carCobrosAnticipos);
            }
            // CAMBIO DE CHEQUE
            if (banCheque != null) {
                retorno = cambioChequeContable(conContable, listaConDetalle, sisSuceso, nuevo, banCheque);
            }
        }
        return retorno;
    }

    private boolean insertarCompraContable(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, boolean nuevo, InvCompras invCompras) throws Exception {
        insertar(conContable);
        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            detalleDao.insertar(listaConDetalle.get(i));
        }

        invCompras.setConEmpresa(conContable.getConContablePK().getConEmpresa());
        invCompras.setConNumero(conContable.getConContablePK().getConNumero());
        invCompras.setConPeriodo(conContable.getConContablePK().getConPeriodo());
        invCompras.setConTipo(conContable.getConContablePK().getConTipo());
        comprasDao.actualizar(invCompras);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarModificarIPP(List<ConContable> listConContable, String sql, List<SisSuceso> sisSuceso)
            throws Exception {
        if (listConContable != null && !listConContable.isEmpty()) {
            sql += "INSERT INTO contabilidad.con_contable(con_empresa, con_periodo, con_tipo, con_numero, con_fecha, con_pendiente, "
                    + "con_bloqueado, con_reversado, con_anulado, con_generado, con_referencia, con_concepto, con_detalle, con_observaciones, "
                    + "usr_empresa, usr_codigo, usr_fecha_inserta) VALUES ";
            int numeracion = buscarConteoUltimaNumeracion(listConContable.get(0).getConContablePK().getConEmpresa(),
                    listConContable.get(0).getConContablePK().getConPeriodo(),
                    listConContable.get(0).getConContablePK().getConTipo());

            List<SisSuceso> sisSucesoInsertar = new ArrayList<SisSuceso>();
            for (SisSuceso sus : sisSuceso) {
                if (sus.getSusSuceso().compareToIgnoreCase("INSERT") == 0) {
                    sisSucesoInsertar.add(sus);
                }
            }

            for (int i = 0; i < listConContable.size(); i++) {
                ConContable contable = listConContable.get(i);

                sisSucesoInsertar.get(i).setSusClave(contable.getConContablePK().getConPeriodo() + " "
                        + contable.getConContablePK().getConTipo() + " " + contable.getConContablePK().getConNumero());
                sisSucesoInsertar.get(i).setSusDetalle(contable.getConObservaciones());

                numeracion++;
                contable.getConContablePK().setConNumero(UtilsString.rellenarCeros(7, numeracion));
                contable.setConPendiente(true);

                sql += "(" + contable.string() + "),\n";

                if (i + 1 < listConContable.size() && contable.getConContablePK().getConPeriodo()
                        .compareToIgnoreCase(listConContable.get(i + 1).getConContablePK().getConPeriodo()) != 0) {
                    numeracion = buscarConteoUltimaNumeracion(
                            listConContable.get(i + 1).getConContablePK().getConEmpresa(),
                            listConContable.get(i + 1).getConContablePK().getConPeriodo(),
                            listConContable.get(i + 1).getConContablePK().getConTipo());
                }
            }
            sql = sql.substring(0, sql.length() - 2) + ";\n";
        }

        if (sql != null && sql.compareToIgnoreCase("") != 0) {
            ejecutarSQL(sql);
        }

        sucesoDao.insertar(sisSuceso);

        return true;
    }

    @Override
    public boolean insertarModificarContabilizarCorrida(List<ConContable> listConContable, String sql, boolean isCostoVenta, List<SisSuceso> sisSuceso) throws Exception {
        if (listConContable != null && !listConContable.isEmpty()) {
            String sqlCorrida = "";
            sql += "INSERT INTO contabilidad.con_contable(con_empresa, con_periodo, con_tipo, con_numero, con_fecha, con_pendiente, "
                    + "con_bloqueado, con_reversado, con_anulado, con_generado, con_referencia, con_concepto, con_detalle, con_observaciones, "
                    + "usr_empresa, usr_codigo, usr_fecha_inserta) VALUES ";
            int numeracion = buscarConteoUltimaNumeracion(listConContable.get(0).getConContablePK().getConEmpresa(),
                    listConContable.get(0).getConContablePK().getConPeriodo(),
                    listConContable.get(0).getConContablePK().getConTipo());

            List<SisSuceso> sisSucesoInsertar = new ArrayList<SisSuceso>();
            for (SisSuceso sus : sisSuceso) {
                if (sus.getSusSuceso().compareToIgnoreCase("INSERT") == 0) {
                    sisSucesoInsertar.add(sus);
                }
            }

            for (int i = 0; i < listConContable.size(); i++) {
                ConContable contable = listConContable.get(i);

                sisSucesoInsertar.get(i).setSusClave(contable.getConContablePK().getConPeriodo() + " "
                        + contable.getConContablePK().getConTipo() + " " + contable.getConContablePK().getConNumero());
                sisSucesoInsertar.get(i).setSusDetalle(contable.getConObservaciones());

                numeracion++;
                contable.getConContablePK().setConNumero(UtilsString.rellenarCeros(7, numeracion));
                contable.setConPendiente(true);

                sql += "(" + contable.string() + "),\n";

                PrdCorridaPK cpk = contable.getPrdCorridaList().get(0).getPrdCorridaPK();
                if (isCostoVenta) {
                    sqlCorrida += "UPDATE produccion.prd_corrida " + "SET cv_empresa='"
                            + contable.getConContablePK().getConEmpresa() + "', cv_periodo='"
                            + contable.getConContablePK().getConPeriodo() + "', cv_tipo='"
                            + contable.getConContablePK().getConTipo() + "', cv_numero='"
                            + contable.getConContablePK().getConNumero() + "' " + "WHERE cor_empresa='"
                            + cpk.getCorEmpresa() + "' and cor_sector='" + cpk.getCorSector() + "' and cor_piscina='"
                            + cpk.getCorPiscina() + "' and cor_numero='" + cpk.getCorNumero() + "';\n";
                } else {
                    sqlCorrida += "UPDATE produccion.prd_corrida " + "SET con_empresa='"
                            + contable.getConContablePK().getConEmpresa() + "', con_periodo='"
                            + contable.getConContablePK().getConPeriodo() + "', con_tipo='"
                            + contable.getConContablePK().getConTipo() + "', con_numero='"
                            + contable.getConContablePK().getConNumero() + "' " + "WHERE cor_empresa='"
                            + cpk.getCorEmpresa() + "' and cor_sector='" + cpk.getCorSector() + "' and cor_piscina='"
                            + cpk.getCorPiscina() + "' and cor_numero='" + cpk.getCorNumero() + "';\n";
                }

                if (i + 1 < listConContable.size() && contable.getConContablePK().getConPeriodo()
                        .compareToIgnoreCase(listConContable.get(i + 1).getConContablePK().getConPeriodo()) != 0) {
                    numeracion = buscarConteoUltimaNumeracion(
                            listConContable.get(i + 1).getConContablePK().getConEmpresa(),
                            listConContable.get(i + 1).getConContablePK().getConPeriodo(),
                            listConContable.get(i + 1).getConContablePK().getConTipo());
                }
            }
            sql = sql.substring(0, sql.length() - 2) + ";\n" + sqlCorrida;
        }

        if (sql != null && sql.compareToIgnoreCase("") != 0) {
            ejecutarSQL(sql);
        }

        sucesoDao.insertar(sisSuceso);

        return true;
    }

    @Override
    public int buscarConteoUltimaNumeracion(String empCodigo, String perCodigo, String tipCodigo) throws Exception {
        String sql = "SELECT num_secuencia FROM contabilidad.con_numeracion WHERE num_empresa = '" + empCodigo
                + "' AND num_tipo = '" + tipCodigo + "' AND num_periodo = '" + perCodigo + "'";
        Object obj = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
        return obj == null ? 0 : (int) UtilsConversiones.convertir(obj);
    }

    @Override
    public List<ConListaConsultaConsumosTO> getListaInvConsultaConsumosPendientes(String empCodigo, String fechaDesde,
            String fechaHasta) throws Exception {
        String sql = "SELECT cons_periodo, cons_motivo, cons_numero, cons_fecha FROM inventario.inv_consumos "
                + "WHERE cons_empresa= ('" + empCodigo + "') AND cons_fecha >=  ( "
                + (fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'") + " ) AND cons_fecha <=  ( "
                + (fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'") + " ) AND cons_pendiente "
                + "ORDER BY 1,2,3";

        return genericSQLDao.obtenerPorSql(sql, ConListaConsultaConsumosTO.class);
    }

    @Override
    public int buscarConteoNumeracionEliminarTipo(String empCodigo, String tipCodigo) throws Exception {
        String sql = "SELECT COUNT(*) FROM contabilidad.con_contable WHERE con_empresa = ('" + empCodigo
                + "') AND con_tipo = ('" + tipCodigo + "')";

        return (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public Boolean swCobrosAnticiposEliminar(String codEmpresa, String periodo, String tipo, String numeroContable)
            throws Exception {
        String sql = "";
        if (tipo.compareTo("C-COB") == 0 || tipo.compareTo("C-ACLI") == 0) {
            sql = "SELECT * FROM cartera.fun_sw_cobros_anticipos_eliminar('" + codEmpresa + "', '" + periodo + "', '"
                    + tipo + "', '" + numeroContable + "')";
        } else if (tipo.compareTo("C-PAG") == 0 || tipo.compareTo("C-APRO") == 0) {
            sql = "SELECT * FROM cartera.fun_sw_pagos_anticipos_eliminar('" + codEmpresa + "', '" + periodo + "', '"
                    + tipo + "', '" + numeroContable + "')";
        }
        return (Boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public List<ConContableTO> getListaConContableTO(String empresa, String perCodigo, String tipCodigo,
            String numContable) throws Exception {
        String sql = "SELECT con_empresa, con_periodo, con_tipo, "
                + "con_numero, substring(cast(con_fecha as text), 1, 10) as con_fecha, con_pendiente, "
                + "con_bloqueado, con_anulado, con_generado, con_concepto, con_detalle, con_observaciones, "
                + "usr_codigo, usr_fecha_inserta, con_reversado, con_codigo, con_referencia FROM contabilidad.con_contable WHERE con_empresa = ('"
                + empresa + "') AND con_tipo = ('" + tipCodigo.trim() + "') AND con_periodo = ('" + perCodigo
                + "') AND con_numero = ('" + numContable + "')";

        return genericSQLDao.obtenerPorSql(sql, ConContableTO.class);
    }

    @Override
    public List<ConFunBalanceResultadosNecTO> getConEstadoResultadosIntegralTO(String empresa, String sector,
            String fechaDesde, String fechaHasta, Boolean ocultarDetalle, Boolean excluirAsientoCierre)
            throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        sector = (sector == null || sector.compareTo("''") == 0) ? null : "'" + sector + "'";

        String sql = "SELECT * FROM contabilidad.fun_balance_resultados('" + empresa + "', " + sector + ", " + fechaDesde
                + ", " + fechaHasta + ", " + ocultarDetalle + ", " + excluirAsientoCierre + " )";

        return genericSQLDao.obtenerPorSql(sql, ConFunBalanceResultadosNecTO.class);
    }

    @Override
    public List<ConBalanceResultadoComparativoTO> getConBalanceResultadoComparativoTO(String empresa, String sector, String fechaDesde,
            String fechaHasta, String fechaDesde2, String fechaHasta2, Boolean ocultarDetalle, Boolean excluirCierre) throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        fechaDesde2 = fechaDesde2 == null ? fechaDesde2 : "'" + fechaDesde2 + "'";
        fechaHasta2 = fechaHasta2 == null ? fechaHasta2 : "'" + fechaHasta2 + "'";
        sector = (sector == null || sector.compareTo("''") == 0) ? null : "'" + sector + "'";

        String sql = "SELECT * FROM contabilidad.fun_balance_resultados_comparativo('" + empresa + "', " + sector + ", " + fechaDesde + ", "
                + fechaHasta + ", " + fechaDesde2 + ", " + fechaHasta2 + ", " + ocultarDetalle + ", " + excluirCierre + " )";

        return genericSQLDao.obtenerPorSql(sql, ConBalanceResultadoComparativoTO.class);
    }

    @Override
    public List<ConBalanceResultadoTO> getConFunBalanceFlujoEfectivo(String empresa, String sector, String fechaDesde,
            String fechaHasta) throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        sector = (sector == null || sector.compareTo("''") == 0) ? null : "'" + sector + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_flujo_efectivo('" + empresa + "', " + sector + ", "
                + fechaDesde + ", " + fechaHasta + ")";

        return genericSQLDao.obtenerPorSql(sql, ConBalanceResultadoTO.class);
    }

    @Override
    public List<ConFunBalanceGeneralNecTO> getConEstadoSituacionFinancieraTO(String empresa, String sector, String fecha,
            Boolean ocultar, Boolean ocultarDetalle) throws Exception {
        fecha = fecha == null ? fecha : "'" + fecha + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_general ('" + empresa + "', " + sector + ", " + fecha + ", "
                + ocultar + ", " + ocultarDetalle + ")";
        return genericSQLDao.obtenerPorSql(sql, ConFunBalanceGeneralNecTO.class);
    }

    @Override
    public List<ConFunBalanceGeneralNecTO> getFunCuentasSobregiradasTO(String empresa, String secCodigo, String fecha)
            throws Exception {
        fecha = fecha == null ? fecha : "'" + fecha + "'";
        secCodigo = secCodigo == null ? null : "'" + secCodigo + "'";
        String sql = "SELECT * FROM contabilidad.fun_cuentas_sobregiradas ('" + empresa + "', " + secCodigo + ", "
                + fecha + ")";

        return genericSQLDao.obtenerPorSql(sql, ConFunBalanceGeneralNecTO.class);
    }

    @Override
    public List<ConBalanceGeneralComparativoTO> getFunBalanceGeneralComparativoTO(String empresa, String secCodigo,
            String fechaAnterior, String fechaActual, Boolean ocultar) throws Exception {
        secCodigo = secCodigo == null ? null : "'" + secCodigo + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_general_comparativo ('" + empresa + "', " + secCodigo
                + ", '" + fechaAnterior + "', '" + fechaActual + "', " + ocultar + ")";

        return genericSQLDao.obtenerPorSql(sql, ConBalanceGeneralComparativoTO.class);
    }

    @Override
    public List<ConBalanceComprobacionTO> getListaBalanceComprobacionTO(String empresa, String codigoSector,
            String fechaInicio, String fechaFin, Boolean ocultarDetalle) throws Exception {
        fechaInicio = fechaInicio == null ? fechaInicio : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? fechaFin : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_comprobacion ('" + empresa + "', " + codigoSector + ", "
                + fechaInicio + ", " + fechaFin + ", " + ocultarDetalle + " )";

        return genericSQLDao.obtenerPorSql(sql, ConBalanceComprobacionTO.class);
    }

    @Override
    public List<ConBalanceResultadoTO> getListaBalanceResultadoTO(String empresa, String codigoSector,
            String fechaInicio, String fechaFin, Boolean ocultarDetalle) throws Exception {
        fechaInicio = fechaInicio == null ? fechaInicio : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? fechaFin : "'" + fechaFin + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_resultados ('" + empresa + "', " + codigoSector + ", "
                + fechaInicio + ", " + fechaFin + " ," + ocultarDetalle + ", false )";
        return genericSQLDao.obtenerPorSql(sql, ConBalanceResultadoTO.class);
    }

    @Override
    public List<ConMayorAuxiliarTO> getListaMayorAuxiliarTO(String empresa, String codigoCuentaDesde,
            String codigoCuentaHasta, String fechaInicio, String fechaFin, String sector, boolean estado) throws Exception {
        fechaInicio = fechaInicio == null ? fechaInicio : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? fechaFin : "'" + fechaFin + "'";
        sector = sector == null ? null : "'" + sector + "'";
        String sql = "SELECT * FROM contabilidad.fun_mayor_auxiliar('" + empresa + "', " + sector + ", '"
                + codigoCuentaDesde + "', '" + codigoCuentaHasta + "', " + fechaInicio + ", " + fechaFin + ", " + estado + " )";

        return genericSQLDao.obtenerPorSql(sql, ConMayorAuxiliarTO.class);
    }

    @Override
    public List<ConMayorGeneralTO> getListaMayorGeneralTO(String empresa, String codigoSector, String codigoCuenta,
            String fechaFin) throws Exception {
        codigoSector = codigoSector == null ? codigoSector : "'" + codigoSector + "'";
        fechaFin = fechaFin == null ? fechaFin : "'" + fechaFin + "'";
        String sql = "SELECT * FROM contabilidad.fun_mayor_general ('" + empresa + "', " + codigoSector + ", '"
                + codigoCuenta + "', " + fechaFin + ")";

        return genericSQLDao.obtenerPorSql(sql, ConMayorGeneralTO.class);
    }

    @Override
    public List<ConDiarioAuxiliarTO> getListaDiarioAuxiliarTO(String empresa, String codigoTipo, String fechaInicio,
            String fechaFin) throws Exception {
        fechaInicio = fechaInicio == null ? fechaInicio : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? fechaFin : "'" + fechaFin + "'";
        String sql = "SELECT * FROM contabilidad.fun_libro_diario('" + empresa + "', '" + codigoTipo.trim() + "', "
                + fechaInicio + ", " + fechaFin + ")";

        return genericSQLDao.obtenerPorSql(sql, ConDiarioAuxiliarTO.class);
    }

    @Override
    public List<ConListaBalanceResultadosVsInventarioTO> getConListaBalanceResultadosVsInventarioTO(String empresa,
            String desde, String hasta, String sector) throws Exception {
        desde = desde == null ? null : desde;
        hasta = hasta == null ? null : hasta;
        sector = sector == null ? null : "'" + sector + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_resultados_vs_inventario('" + empresa + "', " + sector
                + ", '" + desde + "', '" + hasta + "')";

        return genericSQLDao.obtenerPorSql(sql, ConListaBalanceResultadosVsInventarioTO.class);
    }

    @Override
    public List<ConBalanceResultadosVsInventarioDetalladoTO> getConListaBalanceResultadosVsInventarioDetalladoTO(String empresa, String desde, String hasta, String sector, boolean estado) throws Exception {
        desde = desde == null ? null : desde;
        hasta = hasta == null ? null : hasta;
        sector = sector == null ? null : "'" + sector + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_resultados_vs_inventario_detallado('" + empresa + "', " + sector
                + ", '" + desde + "', '" + hasta + "', " + estado + ")";
        return genericSQLDao.obtenerPorSql(sql, ConBalanceResultadosVsInventarioDetalladoTO.class);
    }

    @Override
    public List<ConBalanceResultadosMensualizadosTO> getBalanceResultadoMensualizado(String empresa,
            String codigoSector, String fechaInicio, String fechaFin) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_resultados_mensualizado('" + empresa + "', " + codigoSector
                + ", " + fechaInicio + ", " + fechaFin + ")";

        return genericSQLDao.obtenerPorSql(sql, ConBalanceResultadosMensualizadosTO.class);
    }

    @Override
    public List<ConBalanceResultadosMensualizadosAntiguoTO> getBalanceResultadoMensualizadoAntiguo(String empresa,
            String codigoSector, String fechaInicio, String fechaFin) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        String sql = "SELECT * FROM contabilidad.fun_balance_resultados_mensualizado_antiguo('" + empresa + "', " + codigoSector
                + ", " + fechaInicio + ", " + fechaFin + ")";

        return genericSQLDao.obtenerPorSql(sql, ConBalanceResultadosMensualizadosAntiguoTO.class);
    }

    @Override
    public List<ConFunContablesVerificacionesTO> getFunContablesVerificacionesTO(String empresa) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_contables_verificaciones('" + empresa + "', null, null)";

        return genericSQLDao.obtenerPorSql(sql, ConFunContablesVerificacionesTO.class);
    }

    @Override
    public List<ConFunIPPTO> getFunListaIPP(String empresa, String fechaInicio, String fechaFin, String parametro)
            throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        String sql = "";
        if (parametro.trim().equals("DIRECTO")) {
            sql = "SELECT * FROM contabilidad.fun_contabilizar_ipp_material_directo('" + empresa + "'," + fechaInicio
                    + ", " + fechaFin + ", true)";
        } else {
            sql = "SELECT * FROM contabilidad.fun_contabilizar_ipp_material_indirecto('" + empresa + "'," + fechaInicio
                    + ", " + fechaFin + ")";
        }

        return genericSQLDao.obtenerPorSql(sql, ConFunIPPTO.class);
    }

    @Override
    public String getFunListaIPPComplementoValidar(String empresa, String fechaInicio, String fechaFin,
            String parametro) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        String sql = "";
        if (parametro.trim().equals("DIRECTO")) {
            sql = "SELECT * from contabilidad.fun_contabilizar_ipp_material_directo_validacion('" + empresa + "',"
                    + fechaInicio + ", " + fechaFin + ");";
        } else {
            sql = "SELECT * from contabilidad.fun_contabilizar_ipp_material_indirecto_validacion('" + empresa + "',"
                    + fechaInicio + ", " + fechaFin + ");";
        }
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<ConFunIPPComplementoTO> getFunListaIPPComplemento(String empresa, String fechaInicio, String fechaFin,
            String parametro) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        String sql = "";
        if (parametro.trim().equals("DIRECTO")) {
            sql = "SELECT * from contabilidad.fun_contabilizar_ipp_material_directo_complemento('" + empresa + "',"
                    + fechaInicio + ", " + fechaFin + ");";
        } else {
            sql = "SELECT * from contabilidad.fun_contabilizar_ipp_material_indirecto_complemento('" + empresa + "',"
                    + fechaInicio + ", " + fechaFin + ");";
        }

        return genericSQLDao.obtenerPorSql(sql, ConFunIPPComplementoTO.class);
    }

    @Override
    public List<ConBalanceResultadoCierreTO> getfun_balance_resultados_cierre(String empresa, String sector,
            String fecha) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_balance_resultados_cierre('" + empresa + "', '" + sector + "', '"
                + fecha + "')";
        return genericSQLDao.obtenerPorSql(sql, ConBalanceResultadoCierreTO.class);
    }

    @Override
    public List<ConFunContablesVerificacionesComprasTO> getConFunContablesVerificacionesComprasTOs(String empresa,
            String fechaInicio, String fechaFin) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        empresa = "'" + empresa + "'";
        String sql = "SELECT * FROM contabilidad.fun_contables_verificaciones_compras( " + empresa + ", " + fechaInicio
                + ", " + fechaFin + ");";

        return genericSQLDao.obtenerPorSql(sql, ConFunContablesVerificacionesComprasTO.class);
    }

    @Override
    public List<PersonaTO> getFunPersonaTOs(String empresa, String busquedad) throws Exception {
        empresa = "'" + empresa + "'";
        busquedad = busquedad == null ? null : "'" + busquedad + "'";
        String sql = "SELECT * FROM fun_personas( " + empresa + ", " + busquedad + ")";

        return genericSQLDao.obtenerPorSql(sql, PersonaTO.class);
    }

    @Override
    public List<ConFunContabilizarComprasDetalleTO> getConFunContabilizarComprasDetalle(String empresa, String periodo,
            String motivo, String numeroCompra, String validar) throws Exception {
        validar = validar == null ? null : "'" + validar + "'";
        String sql = "SELECT * FROM contabilidad.fun_contabilizar_compras('" + empresa + "','" + periodo + "', '"
                + motivo + "', '" + numeroCompra + "'," + validar + ");";

        return genericSQLDao.obtenerPorSql(sql, ConFunContabilizarComprasDetalleTO.class);
    }

    @Override
    public List<ConFunContabilizarVentasDetalleTO> getConFunContabilizarVentasDetalle(String empresa, String vtaPeriodo,
            String vtaMotivo, String vtaNumero, String validar) throws Exception {
        validar = validar == null ? null : "'" + validar + "'";
        String sql = "SELECT * FROM contabilidad.fun_contabilizar_ventas('" + empresa + "','" + vtaPeriodo + "', '"
                + vtaMotivo + "', '" + vtaNumero + "'," + validar + ");";

        return genericSQLDao.obtenerPorSql(sql, ConFunContabilizarVentasDetalleTO.class);
    }

    @Override
    public List<ConContableTO> listaContablesIPPPorFecha(String empCodigo, String contipo, String concepto,
            String fechaDesde, String fechaHasta, String conSector) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_contable WHERE con_empresa = ('" + empCodigo
                + "') AND con_tipo = ('" + contipo + "') AND con_concepto = ('" + concepto + "')"
                + " AND con_observaciones = ('SECTOR: ' || '" + conSector + "')  AND con_fecha >=  ( "
                + (fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'") + " ) AND con_fecha <=  ( "
                + (fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'") + " ) order by 1, 4";
        return genericSQLDao.obtenerPorSql(sql, ConContableTO.class);
    }

    @Override
    public String buscarContableTrasferencia(String empCodigo, String contipo, String concepto, String fecha,
            String conSector) throws Exception {
        String sql = "SELECT con_numero FROM contabilidad.con_contable WHERE con_empresa = ('" + empCodigo
                + "') AND con_tipo = ('" + contipo + "') AND con_concepto = ('" + concepto + "') AND "
                + "con_observaciones = ('SECTOR: ' || '" + conSector + "') AND con_fecha = ( "
                + (fecha == null ? fecha : "'" + fecha + "'") + " ) order by 1 limit 1";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception {
        String sql = "SELECT con_pendiente as comp_pendiente, con_anulado as comp_anulado, con_bloqueado as est_bloqueado, con_generado as est_generado, con_reversado FROM contabilidad.con_contable "
                + "WHERE con_empresa = '" + empresa + "' AND con_periodo = '" + periodo + "' AND con_tipo = '"
                + motivoTipo + "' AND con_numero = '" + numero + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvEstadoCCCVT.class);
    }

    @Override
    public List<ListaConContableTO> getListConContableTO(String empresa, String periodo, String tipo, String busqueda,
            String referencia, String nRegistros) throws Exception {
        String limit = "";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM contabilidad.fun_contables_listado('" + empresa + "', '" + periodo + "', '" + tipo
                + "', '" + busqueda + "', '" + referencia + "')" + limit + ";";

        return genericSQLDao.obtenerPorSql(sql, ListaConContableTO.class);
    }

    public ConContable buscarConContable(ConContablePK conContablePK) throws Exception {
        return obtenerPorId(ConContable.class, conContablePK);
    }

    @Override
    public ConContablePK buscarConteoUltimaNumeracion(ConContablePK conContablePK) throws Exception {
        int numeracion = buscarConteoUltimaNumeracion(conContablePK.getConEmpresa(), conContablePK.getConPeriodo(),
                conContablePK.getConTipo());
        do {
            numeracion++;
            conContablePK.setConNumero(UtilsString.rellenarCeros(7, numeracion));
        } while (buscarConContable(conContablePK) != null);

        return conContablePK;
    }

    @Override
    public boolean insertarModificarContable(ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso) throws Exception {
        List<ConDetalle> listadoCopia = new ArrayList<>();
        for (int i = 0; i < listaConDetalle.size(); i++) {
            ConDetalle det = ConversionesContabilidad.convertirConDetalle_ConDetalle(listaConDetalle.get(i));
            det.setConContable(null);
            if (listaConDetalle.get(i).getConCuentas() != null && listaConDetalle.get(i).getConCuentas().getConCuentasPK().getCtaCodigo() != null) {
                det.setConCuentas(listaConDetalle.get(i).getConCuentas());
            } else {
                det.setConCuentas(null);
            }
            listadoCopia.add(det);
        }

        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " "
                + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());

        sisSuceso.setSusDetalle("Se insertó contable del periodo " + conContable.getConContablePK().getConPeriodo()
                + ", del tipo contable " + conContable.getConContablePK().getConTipo() + ", de la numeracion "
                + conContable.getConContablePK().getConNumero());

        List<ConDetalle> list = detalleDao.getListConDetalle(conContable.getConContablePK());
        if (listaConDetalle != null) {
            for (int i = 0; i < listaConDetalle.size(); i++) {
                if (listaConDetalle.get(i).getDetOrden() == -1) {
                    listaConDetalle.get(i).setDetSecuencia(null);
                }
                if (listaConDetalle.get(i).getPrdPiscina() == null
                        || listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK().getPisNumero() == null
                        || listaConDetalle.get(i).getPrdPiscina().getPrdPiscinaPK().getPisNumero()
                                .compareToIgnoreCase("") == 0) {
                    listaConDetalle.get(i).setPrdPiscina(null);
                }
                listaConDetalle.get(i).setDetSaldo(new BigDecimal("0.00"));
                listaConDetalle.get(i).setDetOrden(i + 1);
                listaConDetalle.get(i).setConContable(conContable);
            }
        }

        for (ConDetalle conDetalle : list) {
            detalleDao.evict(conDetalle);
        }

        conContable.setConDetalleList(new ArrayList<ConDetalle>());
        conContable.getConDetalleList().addAll(listaConDetalle);

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        if (!conContable.getConReversado()) {
            conContable.setConPendiente(true);//??
        }
        saveOrUpdate(conContable);

        if (list != null && !list.isEmpty()) {
            list.removeAll(listaConDetalle);
            for (int i = 0; i < list.size(); i++) {
                detalleDao.eliminarPorSql(list.get(i).getDetSecuencia());
            }
        }

        sucesoDao.insertar(sisSuceso);
        //SUCESO CONTABLE
        SisSucesoContable sucesoContable = new SisSucesoContable();
        ConContable copia = ConversionesContabilidad.convertirConContable_ConContable(conContable);
        String json = UtilsJSON.objetoToJson(copia);
        String jsonListado = UtilsJSON.objetoToJson(listadoCopia);
        if (json != null && !json.equals("") && jsonListado != null && !jsonListado.equals("")) {
            json = json.substring(0, json.length() - 1) + "," + "\"conDetalleList\"" + ":" + jsonListado + "}";
        }
        sucesoContable.setSusJson(json);
        sucesoContable.setSisSuceso(sisSuceso);
        sucesoContable.setConContable(copia);
        sucesoContableDao.insertar(sucesoContable);
        return true;
    }

    @Override
    public ConContable getConContable(ConContablePK conContablePK) {
        return obtenerObjetoPorHql(
                "select distinct con from ConContable con inner join con.conContablePK conpk "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public void mayorizarDesmayorizarSql(ConContablePK conContablePK, boolean pendiente, SisSuceso sisSuceso) {
        String sql = "UPDATE contabilidad.con_contable SET con_pendiente=" + pendiente + " WHERE con_empresa='"
                + conContablePK.getConEmpresa() + "' and  con_periodo='" + conContablePK.getConPeriodo()
                + "' and con_tipo='" + conContablePK.getConTipo() + "' and con_numero='" + conContablePK.getConNumero()
                + "';";
        sisSuceso.setSusDetalle("Se " + (pendiente ? "Desmayorizo" : "Mayorizo") + " el contable:" + conContablePK.getConPeriodo()
                + " | " + conContablePK.getConTipo() + " | " + conContablePK.getConNumero());

        sucesoDao.insertar(sisSuceso);
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void anularReversarSql(ConContablePK conContablePK, boolean anularReversar, boolean eliminarReferencia) {
        String sql = "UPDATE contabilidad.con_contable SET "
                + (anularReversar ? "con_pendiente = false, con_anulado=true" : "con_reversado=true")
                + (eliminarReferencia ? ", con_referencia = null" : "")
                + " WHERE con_empresa='"
                + conContablePK.getConEmpresa() + "' and  con_periodo='" + conContablePK.getConPeriodo()
                + "' and con_tipo='" + conContablePK.getConTipo() + "' and con_numero='" + conContablePK.getConNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void restaurarSql(ConContablePK conContablePK) {
        String sql = "UPDATE contabilidad.con_contable SET con_anulado=false, con_reversado=false WHERE con_empresa='"
                + conContablePK.getConEmpresa() + "' and  con_periodo='" + conContablePK.getConPeriodo()
                + "' and con_tipo='" + conContablePK.getConTipo() + "' and con_numero='" + conContablePK.getConNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void ejecutarSQL(String sql) {
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public List<String> validarChequesConciliados(String empresa, String periodo, String conTipo, String conNumero) {
        String sql = "SELECT $$Cuenta (Empresa '$$ || con_detalle.cta_empresa || $$', Codigo '$$ || con_detalle.cta_codigo || $$'); $$ || chr(10) || "
                + "$$Cheque (Banco '$$ || replace(con_cuentas.cta_detalle, 'Banco ', '') || $$', Numero '$$ || con_detalle.det_documento || $$'); $$ || chr(10) ||"
                + "$$Conciliacion ('$$ || conc_codigo || $$')$$ || chr(10) || $$Secuencial ($$ || con_detalle.det_secuencia || $$); $$	"
                + "FROM contabilidad.con_detalle INNER JOIN contabilidad.con_cuentas ON con_detalle.cta_empresa = con_cuentas.cta_empresa AND con_detalle.cta_codigo = con_cuentas.cta_codigo "
                + "INNER JOIN banco.ban_cheque ON con_detalle.det_secuencia=ban_cheque.det_secuencia	"
                + "WHERE con_detalle.con_empresa='" + empresa + "' AND con_detalle.con_periodo='" + periodo
                + "'AND con_detalle.con_tipo='" + conTipo + "' AND con_detalle.con_numero='" + conNumero
                + "' AND ban_cheque.conc_codigo IS NOT NULL AND ban_cheque.conc_codigo != ''";
        return genericSQLDao.obtenerPorSql(sql);
    }

    @Override
    public List<String> validarPagoCobroRefernciaAnticipo(String empresa, String periodo, String conTipo,
            String conNumero, String bandera) {
        String sql = "";
        if (bandera.compareTo("COBROS") == 0) {
            sql = "SELECT '(' || con_empresa || ', ' || con_periodo || ', ' || con_tipo || ', ' || con_numero || ')' contable "
                    + "FROM contabilidad.con_contable INNER JOIN cartera.car_cobros INNER JOIN cartera.car_cobros_detalle_anticipos "
                    + "ON car_cobros.cob_empresa = car_cobros_detalle_anticipos.cob_empresa AND "
                    + "car_cobros.cob_periodo = car_cobros_detalle_anticipos.cob_periodo AND "
                    + "car_cobros.cob_tipo = car_cobros_detalle_anticipos.cob_tipo AND "
                    + "car_cobros.cob_numero = car_cobros_detalle_anticipos.cob_numero "
                    + "ON con_contable.con_empresa = car_cobros.cob_empresa AND "
                    + "con_contable.con_periodo = car_cobros.cob_periodo AND "
                    + "con_contable.con_tipo = car_cobros.cob_tipo AND "
                    + "con_contable.con_numero = car_cobros.cob_numero AND "
                    + "NOT (con_contable.con_pendiente OR con_contable.con_reversado OR con_contable.con_anulado) "
                    + "WHERE (car_cobros_detalle_anticipos.ant_empresa, car_cobros_detalle_anticipos.ant_periodo, car_cobros_detalle_anticipos.ant_tipo, car_cobros_detalle_anticipos.ant_numero)="
                    + "('" + empresa + "', '" + periodo + "', '" + conTipo + "', '" + conNumero + "');";
        } else if (bandera.compareTo("PAGOS") == 0) {
            sql = "SELECT '(' || con_empresa || ', ' || con_periodo || ', ' || con_tipo || ', ' || con_numero || ')' contable "
                    + "FROM contabilidad.con_contable INNER JOIN cartera.car_pagos INNER JOIN cartera.car_pagos_detalle_anticipos "
                    + "ON car_pagos.pag_empresa = car_pagos_detalle_anticipos.pag_empresa AND "
                    + "car_pagos.pag_periodo = car_pagos_detalle_anticipos.pag_periodo AND "
                    + "car_pagos.pag_tipo = car_pagos_detalle_anticipos.pag_tipo AND "
                    + "car_pagos.pag_numero = car_pagos_detalle_anticipos.pag_numero "
                    + "ON con_contable.con_empresa = car_pagos.pag_empresa AND "
                    + "con_contable.con_periodo = car_pagos.pag_periodo AND "
                    + "con_contable.con_tipo = car_pagos.pag_tipo AND "
                    + "con_contable.con_numero = car_pagos.pag_numero AND "
                    + "NOT (con_contable.con_pendiente OR con_contable.con_reversado OR con_contable.con_anulado) "
                    + "WHERE (car_pagos_detalle_anticipos.ant_empresa, car_pagos_detalle_anticipos.ant_periodo, car_pagos_detalle_anticipos.ant_tipo, car_pagos_detalle_anticipos.ant_numero) ="
                    + "('" + empresa + "', '" + periodo + "', '" + conTipo + "', '" + conNumero + "');";
        }

        return genericSQLDao.obtenerPorSql(sql);
    }

    @Override
    public int buscarRegistroCotableRRHH(ConContable conContable) throws Exception {
        String referencia = conContable.getConReferencia();
        String conEmpresa = conContable.getConContablePK().getConEmpresa();
        String conPeriodo = conContable.getConContablePK().getConPeriodo();
        String conTipo = conContable.getConContablePK().getConTipo();
        String conNumero = conContable.getConContablePK().getConNumero();
        String conFecha = UtilsValidacion.fechaDate_String(conContable.getConFecha());
        String sql = "";
        if (referencia.compareTo("recursoshumanos.rh_anticipo") == 0) {
            sql = "SELECT COUNT(ant_secuencial) FROM recursoshumanos.rh_anticipo "
                    + "INNER JOIN recursoshumanos.rh_empleado ON "
                    + "(rh_anticipo.emp_empresa, rh_anticipo.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                    + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conEmpresa + "','" + conPeriodo
                    + "','" + conTipo + "','" + conNumero + "') " + "AND ('" + conFecha
                    + "' <= rh_empleado.emp_fecha_ultimo_sueldo)";
        } else if (referencia.compareTo("recursoshumanos.rh_bono") == 0) {
            sql = " SELECT COUNT(bono_secuencial) FROM recursoshumanos.rh_bono "
                    + "INNER JOIN recursoshumanos.rh_empleado ON "
                    + "(rh_bono.emp_empresa, rh_bono.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                    + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conEmpresa + "','" + conPeriodo
                    + "','" + conTipo + "','" + conNumero + "') " + "AND ('" + conFecha
                    + "' <= rh_empleado.emp_fecha_ultimo_sueldo)";
        }

        Object obj = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return new Integer(String.valueOf(UtilsConversiones.convertir(obj)));
        }
        return 0;
    }

    @Override
    public List<ConFunContablesVerificacionesTO> getFunContablesVerificacion(String empresa, String fechaInicio, String fechaFin) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_contables_verificaciones('" + empresa + "', '" + fechaInicio + "', '" + fechaFin + "')";
        return genericSQLDao.obtenerPorSql(sql, ConFunContablesVerificacionesTO.class);
    }

    @Override
    public List<ConDetalleTablaTO> obtenerContableVirtualConsumo(String conEmpresa, String conPeriodo, String conTipo, String conNumero) {
        String sql = "SELECT * FROM contabilidad.fun_listado_contable_consumo_detalle('" + conEmpresa + "', '" + conPeriodo + "' , '" + conTipo + "' , '" + conNumero + "')";
        return genericSQLDao.obtenerPorSql(sql, ConDetalleTablaTO.class);
    }

    @Override
    public List<ConDetalleTablaTO> obtenerContableVirtualVenta(String conEmpresa, String conPeriodo, String conTipo, String conNumero) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_listado_contable_costo_de_venta_detalle('" + conEmpresa + "', '" + conPeriodo + "' , '" + conTipo + "' , '" + conNumero + "')";
        return genericSQLDao.obtenerPorSql(sql, ConDetalleTablaTO.class);
    }

    /*IMAGENES*/
    @Override
    public List<ConAdjuntosContable> getAdjuntosContable(ConContablePK conContablePK) {
        String sql = "select * from contabilidad.con_contable_datos_adjuntos where "
                + "con_empresa = '" + conContablePK.getConEmpresa()
                + "' and con_periodo = '" + conContablePK.getConPeriodo()
                + "' and con_tipo = '" + conContablePK.getConTipo()
                + "' and con_numero = '" + conContablePK.getConNumero() + "' ";
        return genericSQLDao.obtenerPorSql(sql, ConAdjuntosContable.class);
    }

    @Override
    public boolean insertarImagen(ConAdjuntosContable conAdjuntosContable) {
        conAdjuntosContableDao.insertar(conAdjuntosContable);
        return true;
    }

    @Override
    public boolean eliminarImagen(ConAdjuntosContable conAdjuntosContable) {
        conAdjuntosContableDao.eliminar(conAdjuntosContable);
        return true;
    }

    @Override
    public boolean mayorizarTransaccionContableCartera(ConContable conContable, List<ConDetalle> listaConDetalle,
            SisSuceso sisSuceso, CarPagos carPagos, List<CarPagosDetalleAnticipos> carPagosDetalleAnticiposes,
            List<CarPagosDetalleCompras> carPagosDetalleCompras, List<CarPagosDetalleForma> carPagosDetalleFormas,
            CarCobros carCobros, List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticiposes,
            List<CarCobrosDetalleVentas> carCobrosDetalleVentas, List<CarCobrosDetalleForma> carCobrosDetalleFormas,
            CarPagosAnticipos carPagosAnticipos, CarCobrosAnticipos carCobrosAnticipos, SisInfoTO sisInfoTO) throws Exception {
        conContable.setConPendiente(true);
        boolean retorno = false;
        sisSuceso.setSusDetalle("Se insertó contable del periodo " + conContable.getConContablePK().getConPeriodo()
                + ", del tipo contable " + conContable.getConContablePK().getConTipo() + ", de la numeracion "
                + conContable.getConContablePK().getConNumero());
        if (listaConDetalle != null) {
            for (int i = 0; i < listaConDetalle.size(); i++) {
                listaConDetalle.get(i).setDetSaldo(new BigDecimal("0.00"));
                listaConDetalle.get(i).getPrdSector().getPrdSectorPK()
                        .setSecEmpresa(listaConDetalle.get(i).getConContable().getConContablePK().getConEmpresa());
            }
        }
        sisSuceso.setSusClave(
                conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " "
                + conContable.getConContablePK().getConNumero());
        ////// aqui va el metodo a llamar
        // PAGOS
        if (carPagos != null) {
            carPagos.setCarPagosPK(new CarPagosPK(conContable.getConContablePK().getConEmpresa(),
                    conContable.getConContablePK().getConPeriodo(), carPagos.getCarPagosPK().getPagTipo(),
                    conContable.getConContablePK().getConNumero()));
            java.math.BigDecimal valorPago = new java.math.BigDecimal("0.00");
            for (int i = 0; i < carPagosDetalleCompras.size(); i++) {
                carPagosDetalleCompras.get(i).setCarPagos(carPagos);
                valorPago = valorPago.add(carPagosDetalleCompras.get(i).getDetValor());
            }
            for (int i = 0; i < carPagosDetalleFormas.size(); i++) {
                carPagosDetalleFormas.get(i).setCarPagos(carPagos);
            }
            for (int i = 0; i < carPagosDetalleAnticiposes.size(); i++) {
                carPagosDetalleAnticiposes.get(i).setCarPagos(carPagos);
            }

            String detalle = "Pago a " + carPagos.getProvCodigo() + " por $" + valorPago.toPlainString();
            sisSuceso.setSusDetalle(detalle.length() > 300 ? detalle.substring(0, 300) : detalle);

            retorno = mayorizarCarPagos(conContable, sisSuceso, carPagos,
                    carPagosDetalleCompras, carPagosDetalleFormas, carPagosDetalleAnticiposes);
        }
        // COBROS
        if (carCobros != null) {
            carCobros.setCarCobrosPK(new CarCobrosPK(conContable.getConContablePK().getConEmpresa(),
                    conContable.getConContablePK().getConPeriodo(), carCobros.getCarCobrosPK().getCobTipo(),
                    conContable.getConContablePK().getConNumero()));
            java.math.BigDecimal valorPago = new java.math.BigDecimal("0.00");
            for (int i = 0; i < carCobrosDetalleVentas.size(); i++) {
                carCobrosDetalleVentas.get(i).setCarCobros(carCobros);
                valorPago = valorPago.add(carCobrosDetalleVentas.get(i).getDetValor());
            }
            for (int i = 0; i < carCobrosDetalleFormas.size(); i++) {
                carCobrosDetalleFormas.get(i).setCarCobros(carCobros);
            }
            for (int i = 0; i < carCobrosDetalleAnticiposes.size(); i++) {
                carCobrosDetalleAnticiposes.get(i).setCarCobros(carCobros);
            }

            String detalle = "Cobro a " + carCobros.getCliCodigo() + " por $" + valorPago.toPlainString();
            sisSuceso.setSusDetalle(detalle.length() > 300 ? detalle.substring(0, 300) : detalle);

            retorno = mayorizarCarCobros(conContable, sisSuceso, carCobros,
                    carCobrosDetalleVentas, carCobrosDetalleFormas, carCobrosDetalleAnticiposes);
        }
        // PAGO ANTICIPO - MODULO DE CARTERA
        if (carPagosAnticipos != null) {
            retorno = mayorizarAnticipoPagoCarteraContable(conContable, listaConDetalle, sisSuceso, carPagosAnticipos);
        }
        // COBRO ANTICIPO - MODULO DE CARTERA
        if (carCobrosAnticipos != null) {
            retorno = mayorizarAnticipoCobroCarteraContable(conContable, listaConDetalle, sisSuceso, carCobrosAnticipos);
        }
        return retorno;
    }

    private boolean mayorizarCarPagos(ConContable conContable, SisSuceso sisSuceso,
            CarPagos carPagos, List<CarPagosDetalleCompras> carPagosDetalleCompras,
            List<CarPagosDetalleForma> carPagosDetalleFormas, List<CarPagosDetalleAnticipos> carPagosDetalleAnticiposes)
            throws Exception {
        List<CarPagosDetalleCompras> listaDetalleCompras = new ArrayList<>();
        List<CarPagosDetalleAnticipos> listaDetalleAnticipos = new ArrayList<>();
        List<CarPagosDetalleForma> listaDetalleForma = new ArrayList<>();
        actualizar(conContable);
        pagosDao.actualizar(carPagos);
        //eliminar detalles
        carPagosDetalleCompras.forEach(carPagosDetalleCompra -> {
            pagosDetalleComprasDao.insertar(carPagosDetalleCompra);
            try {
                CarPagosDetalleCompras item = ConversionesCar.convertirCarPagosDetalleCompras_CarPagosDetalleCompras(carPagosDetalleCompra);
                listaDetalleCompras.add(item);
            } catch (Exception ex) {

            }
        });
        carPagosDetalleFormas.forEach(carPagosDetalleForma -> {
            pagosDetalleFormaDao.insertar(carPagosDetalleForma);
            try {
                CarPagosDetalleForma item = ConversionesCar.convertirCarPagosDetalleForma_CarPagosDetalleForma(carPagosDetalleForma);
                listaDetalleForma.add(item);
            } catch (Exception ex) {

            }
        });
        carPagosDetalleAnticiposes.forEach(carPagosDetalleAnticipos -> {
            pagosDetalleAnticiposDao.insertar(carPagosDetalleAnticipos);
            CarPagosDetalleAnticipos item;
            try {
                item = ConversionesCar.convertirCarPagosDetalleAnticipos_CarPagosDetalleAnticipos(carPagosDetalleAnticipos);
                listaDetalleAnticipos.add(item);
            } catch (Exception ex) {

            }
        });
        sucesoDao.insertar(sisSuceso);
        ////////////////////////insertar suceso/////////////////////////////////////
        SisSucesoPago sucesoPago = new SisSucesoPago();
        CarPagos copia = ConversionesCar.convertirCarPagos_CarPagos(carPagos);

        if (listaDetalleCompras.size() > 0) {
            copia.setCarPagosDetalleComprasList(listaDetalleCompras);
            for (CarPagosDetalleCompras det : copia.getCarPagosDetalleComprasList()) {
                det.setCarPagos(null);
            }
        }
        if (listaDetalleAnticipos.size() > 0) {
            copia.setCarPagosDetalleAnticiposList(listaDetalleAnticipos);
            for (CarPagosDetalleAnticipos det : copia.getCarPagosDetalleAnticiposList()) {
                det.setCarPagos(null);
            }
        }
        if (listaDetalleForma.size() > 0) {
            copia.setCarPagosDetalleFormaList(listaDetalleForma);
            for (CarPagosDetalleForma det : copia.getCarPagosDetalleFormaList()) {
                det.setCarPagos(null);
            }
        }
        String json = UtilsJSON.objetoToJson(copia);
        sucesoPago.setSusJson(json);
        sucesoPago.setSisSuceso(sisSuceso);
        sucesoPago.setCarPagos(copia);
        sucesoPagoDao.insertar(sucesoPago);
        return true;
    }

    private boolean mayorizarCarCobros(ConContable conContable, SisSuceso sisSuceso, CarCobros carCobros, List<CarCobrosDetalleVentas> carCobrosDetalleVentas,
            List<CarCobrosDetalleForma> carCobrosDetalleFormas, List<CarCobrosDetalleAnticipos> carCobrosDetalleAnticiposes) throws Exception {
        List<CarCobrosDetalleVentas> listaDetalleVentas = new ArrayList<>();
        List<CarCobrosDetalleAnticipos> listaDetalleAnticipos = new ArrayList<>();
        List<CarCobrosDetalleForma> listaDetalleForma = new ArrayList<>();
        actualizar(conContable);
        cobrosDao.actualizar(carCobros);
        carCobrosDetalleVentas.forEach(carCobrosDetalleVenta -> {
            try {
                cobrosDetalleVentasDao.insertar(carCobrosDetalleVenta);
                CarCobrosDetalleVentas item = ConversionesCar.convertirCarCobrosDetalleVentas_CarCobrosDetalleVentas(carCobrosDetalleVenta);
                listaDetalleVentas.add(item);
            } catch (Exception ex) {

            }
        });
        carCobrosDetalleFormas.forEach(carCobrosDetalleForma -> {
            try {
                cobrosDetalleFormaDao.insertar(carCobrosDetalleForma);
                CarCobrosDetalleForma item = ConversionesCar.convertirCarCobrosDetalleForma_CarCobrosDetalleForma(carCobrosDetalleForma);
                listaDetalleForma.add(item);
            } catch (Exception ex) {

            }
        });
        carCobrosDetalleAnticiposes.forEach(carCobrosDetalleAnticipos -> {
            try {
                cobrosDetalleAnticiposDao.insertar(carCobrosDetalleAnticipos);
                CarCobrosDetalleAnticipos item = ConversionesCar.convertirCarCobrosDetalleAnticipos_CarCobrosDetalleAnticipos(carCobrosDetalleAnticipos);
                listaDetalleAnticipos.add(item);
            } catch (Exception ex) {

            }
        });
        sucesoDao.insertar(sisSuceso);
        ////////////////////////insertar suceso/////////////////////////////////////
        SisSucesoCobro sucesoCobro = new SisSucesoCobro();
        CarCobros copia = ConversionesCar.convertirCarCobros_CarCobros(carCobros);

        if (listaDetalleVentas.size() > 0) {
            copia.setCarCobrosDetalleVentasList(listaDetalleVentas);
            for (CarCobrosDetalleVentas det : copia.getCarCobrosDetalleVentasList()) {
                det.setCarCobros(null);
            }
        }
        if (listaDetalleAnticipos.size() > 0) {
            copia.setCarCobrosDetalleAnticiposList(listaDetalleAnticipos);
            for (CarCobrosDetalleAnticipos det : copia.getCarCobrosDetalleAnticiposList()) {
                det.setCarCobros(null);
            }
        }
        if (listaDetalleForma.size() > 0) {
            copia.setCarCobrosDetalleFormaList(listaDetalleForma);
            for (CarCobrosDetalleForma det : copia.getCarCobrosDetalleFormaList()) {
                det.setCarCobros(null);
            }
        }
        String json = UtilsJSON.objetoToJson(copia);
        sucesoCobro.setSusJson(json);
        sucesoCobro.setSisSuceso(sisSuceso);
        sucesoCobro.setCarCobros(copia);
        sucesoCobroDao.insertar(sucesoCobro);
        return true;
    }

    private boolean mayorizarAnticipoCobroCarteraContable(ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, CarCobrosAnticipos carCobrosAnticipos) throws Exception {
        conContable.setConReversado(false);
        actualizar(conContable);

        List<ConDetalle> detallesEnBase = detalleDao.getListConDetalle(conContable.getConContablePK());
        if (detallesEnBase != null && !detallesEnBase.isEmpty()) {
            detallesEnBase.forEach(detalle -> {
                detalleDao.eliminar(detalle);
            });
        }

        for (int i = 0; i < listaConDetalle.size(); i++) {
            listaConDetalle.get(i).setConContable(conContable);
            detalleDao.insertar(listaConDetalle.get(i));
        }

        carCobrosAnticiposDao.actualizar(carCobrosAnticipos);
        sucesoDao.insertar(sisSuceso);
        /////////////////insertar suceso anticipos
        SisSucesoCobrosAnticipos sucesoCobroAnt = new SisSucesoCobrosAnticipos();
        CarCobrosAnticipos copia = ConversionesCar.convertirCarCobrosAnticipos_CarCobrosAnticipos(carCobrosAnticipos);
        if (copia.getCarCobrosDetalleAnticiposList() != null && copia.getCarCobrosDetalleAnticiposList().size() > 0) {
            for (CarCobrosDetalleAnticipos det : copia.getCarCobrosDetalleAnticiposList()) {
                CarCobrosDetalleAnticipos detalle = ConversionesCar.convertirCarCobrosDetalleAnticipos_CarCobrosDetalleAnticipos(det);
                detalle.setCarCobrosAnticipos(null);
            }
        }

        if (copia.getFpSecuencial() != null) {
            copia.getFpSecuencial().setCarCobrosAnticiposList(null);
            copia.getFpSecuencial().setCarCobrosDetalleFormaList(null);
        }

        String json = UtilsJSON.objetoToJson(copia);
        if (conContable != null) {
            String jsonCont = UtilsJSON.objetoToJson(conContable);
            if (json != null && !json.equals("") && jsonCont != null && !jsonCont.equals("")) {
                json = json.substring(0, json.length() - 1) + "," + "\"conContable\"" + ":" + jsonCont + "}";
            }
        }
        sucesoCobroAnt.setSusJson(json);
        sucesoCobroAnt.setSisSuceso(sisSuceso);
        sucesoCobroAnt.setCarCobrosAnticipos(copia);
        sucesoCobrosAnticiposDao.insertar(sucesoCobroAnt);
        return true;
    }

    @Override
    public List<ConFunBalanceCentroProduccionTO> getConBalanceBalanceCentroProduccionTO(String empresa, String fechaDesde, String fechaHasta, Boolean ocultarDetalle, Boolean excluirAsientoCierre)
            throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";

        String sql = "SELECT * FROM contabilidad.fun_estado_resultados_centro_produccion('" + empresa + "', " + fechaDesde
                + ", " + fechaHasta + ", " + ocultarDetalle + ", " + excluirAsientoCierre + " )";

        return genericSQLDao.obtenerPorSql(sql, ConFunBalanceCentroProduccionTO.class);
    }

}
