package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequeService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.CategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.RolDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.VacacionesGozadasDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.CalculosGeneralRol;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.UtilTransacciones;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.ItemListaRolTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhContableTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaSueldosLotePreliminarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaSueldosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaRolSaldoEmpleadoDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoSueldosPorPagarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionesListadoTransTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolEmpTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSaldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSueldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.UtilsRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhProvisiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import org.olap4j.impl.ArrayMap;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private ContableService contableService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private CategoriaDao categoriaDao;
    @Autowired
    private VacacionesGozadasDao vacacionesGozadasDao;
    @Autowired
    private RolMotivoService rolMotivoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private CalculosGeneralRol calculosGeneralRol;
    @Autowired
    private GenericoDao<RhProvisiones, String> rhProvisionesDao;
    @Autowired
    private ChequeService chequeService;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SisEmpresaNotificacionesDao sisEmpresaNotificacionesDao;
    private Boolean comprobar = false;
    private String mensaje = "";
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhFunPlantillaSueldosLoteTO> getFunPlantillaSueldosLote(String empCodigo, String fechaDesde, String fechaHasta, String categoria, String sector) throws Exception {
        return rolDao.getFunPlantillaSueldosLote(empCodigo, fechaDesde, fechaHasta, categoria, sector);
    }

    @Override
    public List<RhFunPlantillaSueldosLotePreliminarTO> getFunPlantillaSueldosLotePreliminar(String empCodigo, String fechaDesde, String fechaHasta, String categoria, String sector) throws Exception {
        return rolDao.getFunPlantillaSueldosLotePreliminar(empCodigo, fechaDesde, fechaHasta, categoria, sector);
    }

    @Override
    public List<RhListaRolSaldoEmpleadoDetalladoTO> getRhRolSaldoEmpleadoDetalladoTO(String empCodigo, String empId, String fechaDesde, String fechaHasta) throws Exception {
        return rolDao.getRhRolSaldoEmpleadoDetallado(empCodigo, empId, fechaDesde, fechaHasta);
    }

    @Override
    public RhRolSaldoEmpleadoTO getRhRolSaldoEmpleado(String empCodigo, String empId, String fechaDesde, String fechaHasta, String tipo) throws Exception {
        return rolDao.getRhRolSaldoEmpleado(empCodigo, empId, fechaDesde, fechaHasta, tipo);
    }

    @Override
    public List<RhListaDetalleRolesTO> getRhDetalleRolesTO(String empCodigo, String fechaDesde, String fechaHasta, String sector, String empCategoria, String empId, String filtro) throws Exception {
        return rolDao.getRhDetalleRolesTO(empCodigo, fechaDesde, fechaHasta, sector, empCategoria, empId, filtro);
    }

    @Override
    public List<RhListaConsolidadoRolesTO> getRhConsolidadoRolesTO(String empCodigo, String fechaDesde, String fechaHasta, String sector, String empCategoria, String empId, boolean excluirLiquidacion) throws Exception {
        return rolDao.getRhConsolidadoRolesTO(empCodigo, fechaDesde, fechaHasta, sector, empCategoria, empId, excluirLiquidacion);
    }

    @Override
    public List<RhListaSaldoConsolidadoSueldosPorPagarTO> getRhSaldoConsolidadoSueldosPorPagarTO(String empCodigo, String fechaHasta) throws Exception {
        return rolDao.getRhSaldoConsolidadoSueldosPorPagarTO(empCodigo, fechaHasta);
    }

    @Override
    public List<RhRol> obtenerRolesPorPeriodoDeVacaciones(String empresa, String empId, String desde, String hasta) throws Exception {
        return rolDao.obtenerRolesPorPeriodoDeVacaciones(empresa, empId, desde, hasta);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoSueldos(String empresa, String fecha, String cuenta) throws Exception {
        return rolDao.getRhFunPreavisoSueldos(empresa, fecha, cuenta);
    }

    @Override
    public RhRolSueldoEmpleadoTO getRhRolSueldoEmpleadoTO(String empCodigo, String empId) throws Exception {
        return rolDao.getRhRolSueldoEmpleado(empCodigo, empId);
    }

    @Override
    public RhContableTO insertarRhListaProvisionesConContable(String empresa, String periodo, String sector, String estado, String usuario, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        String status = estado;
        RhContableTO rhContableTO = new RhContableTO();
        String tipDetalle = "C-PRO";
        mensaje = "F";
        String perHasta = "";
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList(1);
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);

        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (sisListaPeriodoTO.getPerCodigo().equals(periodo) && sisListaPeriodoTO.getPerCerrado() == false) {
                perHasta = sisListaPeriodoTO.getPerHasta();
                comprobar = true;
                break;
            }
        }
        if (comprobar) {
            comprobar = true;
            if (tipoDao.buscarTipoContable(empresa, tipDetalle)) {
                // llenar contable
                ConContableTO conContableTO = new ConContableTO();
                conContableTO.setEmpCodigo(empresa);
                conContableTO.setPerCodigo(periodo);
                conContableTO.setTipCodigo(tipDetalle);
                conContableTO.setConFecha(perHasta);
                conContableTO.setConPendiente(false);///
                conContableTO.setConBloqueado(false);
                conContableTO.setConAnulado(false);
                conContableTO.setConGenerado(false);
                conContableTO.setConConcepto("REGISTRO DE PROVISIONES");
                conContableTO.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");//
                conContableTO.setConObservaciones(
                        "CONTABLE DE PROVISIONES DEL PERIODO " + periodo + " DEL SECTOR " + sector);
                conContableTO.setUsrInsertaContable(usuario);
                conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                ///// CREANDO UN SUCESO
                susClave = "";
                susSuceso = "INSERT";
                susDetalle = "Se inserto el rol de pagos numero " + conContableTO.getConNumero() + "en la empresa "
                        + conContableTO.getEmpCodigo() + " en el periodo " + conContableTO.getPerCodigo();
                susTabla = "recursoshumanos.con_contable";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                ////// CREANDO CONTABLE
                ConContable conContable = ConversionesContabilidad.convertirConContableTO_ConContable(conContableTO);
                List<RhListaProvisionesTO> rhListaProvisionesTOs = empleadoDao.getRhListaProvisionesTO(empresa, periodo, sector, status);
                List<RhRol> rhRoles = new ArrayList<>();
                RhRol rhRol;
                for (RhListaProvisionesTO rhListaProvisionesTO : rhListaProvisionesTOs) { //ver si ya hay cointable provision
                    if (rhListaProvisionesTO.getProvId() != null && rhListaProvisionesTO.getProvContableRol() != null && !rhListaProvisionesTO.getProvContableRol().equals("")) {
                        if (rhListaProvisionesTO.getProvContableProvision() != null) {
                            String numeroContable = rhListaProvisionesTO.getProvContableProvision().split(" | ")[4].trim();
                            conContable.getConContablePK().setConNumero(numeroContable);
                            break;
                        }
                    }
                }
                for (RhListaProvisionesTO rhListaProvisionesTO : rhListaProvisionesTOs) {
                    if (rhListaProvisionesTO.getProvId() != null) {
                        rhRol = rolDao.buscarRolSQL(rhListaProvisionesTO.getProvSecuencial());
                        rhRoles.add(rhRol);
                    }
                }

                if (comprobar) {
                    if (rhRoles.size() > 0) {
                        conContable = rolDao.insertarModificarRhProvisiones(conContable, rhRoles, sisSuceso);
                        if (conContable == null || conContable.getConContablePK().getConNumero() == null) {
                            mensaje = "FHubo un error al guardar las provisiones.\nIntente de nuevo o contacte con el administrador";
                        } else {
                            SisPeriodo sisPeriodo = periodoService.buscarPeriodo(conContableTO.getEmpCodigo(),
                                    conContable.getConContablePK().getConPeriodo());

                            ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(
                                    conContableTO.getEmpCodigo(), conContable.getConContablePK().getConTipo()));

                            mensaje = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                                    + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                                    + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                                    + "</font>.<br> Número: <font size = 5>"
                                    + conContable.getConContablePK().getConNumero() + "</font>.</html>";

                            rhContableTO.setPerCodigo(sisPeriodo.getSisPeriodoPK().getPerCodigo());
                            rhContableTO.setTipCodigo(conTipo.getConTipoPK().getTipCodigo());
                            rhContableTO.setConNumero(conContable.getConContablePK().getConNumero());
//                            contableService.mayorizarDesmayorizarSql(new ConContablePK(empresa, rhContableTO.getPerCodigo(),
//                                    rhContableTO.getTipCodigo(), rhContableTO.getConNumero()), false, sisInfoTO);
                        }
                    } else {
                        comprobar = false;
                        mensaje = "FNo hay roles para provisionar...";
                    }
                }
            } else {
                mensaje = "FNo se encuentra tipo de contable...";
            }
        } else {
            mensaje = "FEl periodo seleccionado no se encuentra o esta cerrado...";
        }
        rhContableTO.setMensaje(mensaje);
        return rhContableTO;
    }

    public ConContable generarContableProvision(RhProvisionesListadoTransTO provisionesListadoTransTO, List<RhListaProvisionesTO> listaProvisionesTO) throws Exception {
        String numeroContable = "";
        for (RhListaProvisionesTO prov : listaProvisionesTO) {
            if (prov.getProvContableRol() != null && !prov.getProvContableRol().equals("")) {
                if (prov.getProvContableProvision() != null) {
                    numeroContable = prov.getProvContableProvision().split(" | ")[4].trim();
                    break;
                }
            }
        }
        String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
        ConContable contable = new ConContable(provisionesListadoTransTO.getEmpresa(), provisionesListadoTransTO.getPeriodo(), "C-PRO", numeroContable);
        contable.setConFecha(provisionesListadoTransTO.getDatePeriodoHasta());
        contable.setConPendiente(false);
        contable.setConReferencia("recursoshumanos.rh_provisiones*");
        contable.setConCodigo(secuencia);
        return contable;
    }

    public List<RhRol> generarListaRolesProvision(List<RhListaProvisionesTO> listaProvisionesTO) throws Exception {
        List<RhRol> listRol = new ArrayList<>();
        for (RhListaProvisionesTO prov : listaProvisionesTO) {
            if (prov.getProvContableRol() != null && !prov.getProvContableRol().equals("")) {
                listRol.add(new RhRol(prov.getProvSecuencial()));
            }
        }
        return listRol;
    }

    @Override
    public MensajeTO insertarModificarRhProvisiones(ConContable conContable, List<RhRol> listaRhRol, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
        } else {
            for (int i = 0; i < listaRhRol.size(); i++) {
                listaRhRol.set(i, rolDao.buscarRolSQL(listaRhRol.get(i).getRolSecuencial()));
            }
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                conContable.setConConcepto("REGISTRO DE PROVISIONES");
                conContable.setConObservaciones("CONTABLE DE PROVISIONES DEL PERIODO " + periodo.getSisPeriodoPK().getPerCodigo() + " DEL SECTOR " + listaRhRol.get(0).getPrdSector().getSecNombre());
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("recursoshumanos.rh_provisiones");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }
            susSuceso = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
            susTabla = "recursoshumanos.rh_rol";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingreso" : "modifico";
            conContable = rolDao.insertarModificarRhProvisiones(conContable, listaRhRol, sisSuceso);
            if (conContable == null || conContable.getConContablePK().getConNumero() == null) {
                retorno = "FHubo un error al guardar las provisiones.\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " las provisiones con la siguiente informacion:<br><br>"
                        + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Motivo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Numero: <font size = 5>"
                        + conContable.getConContablePK().getConNumero() + "</font>.<br>"
                        + conContable.getConContablePK().getConNumero() + ", "
                        + periodo.getSisPeriodoPK().getPerCodigo() + "</html>";
                mensajeTO.setFechaCreacion(conContable.getUsrFechaInserta().toString());
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public Map<String, Object> insertarModificarRhProvisionesWeb(RhProvisionesListadoTransTO provisionesListadoTransTO, String contableProvision,
            List<RhListaProvisionesTO> listaProvisionesTO, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        Map<String, Object> respuesta = new ArrayMap<>();
        List<RhRol> listaRhRol = generarListaRolesProvision(listaProvisionesTO);
        if (listaRhRol != null && !listaRhRol.isEmpty()) {
            ConContable conContable = generarContableProvision(provisionesListadoTransTO, listaProvisionesTO);
            mensajeTO = insertarModificarRhProvisiones(conContable, listaRhRol, sisInfoTO);
            respuesta.put("contablePk", conContable.getConContablePK());
        } else {
            mensajeTO.setMensaje("FNo hay roles por provisionar.");
        }
        respuesta.put("mensaje", mensajeTO);
        return respuesta;
    }

    /*ProvisionListadoTrans*/
    @Override
    public List<RhListaProvisionesTO> obtenerListaImprimirProvision(List<RhListaProvisionesTO> listaProvisionesTO) throws Exception {
        // adquirimos las dos siguientes listas
        List<RhListaProvisionesTO> listaAdministrativo = new ArrayList<RhListaProvisionesTO>();
        List<RhListaProvisionesTO> listaCampo = new ArrayList<RhListaProvisionesTO>();

        for (RhListaProvisionesTO rhListaProvisionesTO : listaProvisionesTO) {
            if (rhListaProvisionesTO.getProvCategoria() != null) {
                if (rhListaProvisionesTO.getProvCategoria().equalsIgnoreCase("ADMINISTRATIVO")) {
                    listaAdministrativo.add(rhListaProvisionesTO);
                } else {
                    listaCampo.add(rhListaProvisionesTO);
                }
            }
        }
        // luego:
        BigDecimal cero = new BigDecimal("0.00");
        List<RhListaProvisionesTO> listaRhListaProvisionesTOFinal = new ArrayList<RhListaProvisionesTO>();

        RhListaProvisionesTO rhListaProvisionesTOGeneralTotal = new RhListaProvisionesTO();
        rhListaProvisionesTOGeneralTotal.setProvNombres("TOTAL GENERAL");

        if (!listaAdministrativo.isEmpty()) {
            RhListaProvisionesTO rhListaProvisionesTOAdministrativoTitulo = new RhListaProvisionesTO();
            rhListaProvisionesTOAdministrativoTitulo.setProvNombres("PERSONAL ADMINISTRATIVO");

            RhListaProvisionesTO rhListaProvisionesTOAdministrativoSubtotal = new RhListaProvisionesTO();
            rhListaProvisionesTOAdministrativoSubtotal.setProvCategoria("ADMINISTRATIVO");
            rhListaProvisionesTOAdministrativoSubtotal.setProvNombres("TOTAL PERSONAL ADMINISTRATIVO");
            for (RhListaProvisionesTO item : listaAdministrativo) {
                if (item.getProvAporteIndividual() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvAporteIndividual(rhListaProvisionesTOAdministrativoSubtotal.getProvAporteIndividual() == null ? cero.add(item.getProvAporteIndividual()) : rhListaProvisionesTOAdministrativoSubtotal.getProvAporteIndividual().add(item.getProvAporteIndividual()));
                }
                if (item.getProvAportePatronal() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvAportePatronal(rhListaProvisionesTOAdministrativoSubtotal.getProvAportePatronal() == null ? cero.add(item.getProvAportePatronal()) : rhListaProvisionesTOAdministrativoSubtotal.getProvAportePatronal().add(item.getProvAportePatronal()));
                }
                if (item.getProvIece() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvIece(rhListaProvisionesTOAdministrativoSubtotal.getProvIece() == null ? cero.add(item.getProvIece()) : rhListaProvisionesTOAdministrativoSubtotal.getProvIece().add(item.getProvIece()));
                }
                if (item.getProvSecap() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvSecap(rhListaProvisionesTOAdministrativoSubtotal.getProvSecap() == null ? cero.add(item.getProvSecap()) : rhListaProvisionesTOAdministrativoSubtotal.getProvSecap().add(item.getProvSecap()));
                }
                if (item.getProvXiii() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvXiii(rhListaProvisionesTOAdministrativoSubtotal.getProvXiii() == null ? cero.add(item.getProvXiii()) : rhListaProvisionesTOAdministrativoSubtotal.getProvXiii().add(item.getProvXiii()));
                }
                if (item.getProvXiv() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvXiv(rhListaProvisionesTOAdministrativoSubtotal.getProvXiv() == null ? cero.add(item.getProvXiv()) : rhListaProvisionesTOAdministrativoSubtotal.getProvXiv().add(item.getProvXiv()));
                }
                if (item.getProvFondoReserva() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvFondoReserva(rhListaProvisionesTOAdministrativoSubtotal.getProvFondoReserva() == null ? cero.add(item.getProvFondoReserva()) : rhListaProvisionesTOAdministrativoSubtotal.getProvFondoReserva().add(item.getProvFondoReserva()));
                }
                if (item.getProvVacaciones() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvVacaciones(rhListaProvisionesTOAdministrativoSubtotal.getProvVacaciones() == null ? cero.add(item.getProvVacaciones()) : rhListaProvisionesTOAdministrativoSubtotal.getProvVacaciones().add(item.getProvVacaciones()));
                }
                if (item.getProvDesahucio() != null) {
                    rhListaProvisionesTOAdministrativoSubtotal.setProvDesahucio(rhListaProvisionesTOAdministrativoSubtotal.getProvDesahucio() == null ? cero.add(item.getProvDesahucio()) : rhListaProvisionesTOAdministrativoSubtotal.getProvDesahucio().add(item.getProvDesahucio()));
                }
            }

            rhListaProvisionesTOGeneralTotal.setProvAporteIndividual(rhListaProvisionesTOAdministrativoSubtotal.getProvAporteIndividual());
            rhListaProvisionesTOGeneralTotal.setProvAportePatronal(rhListaProvisionesTOAdministrativoSubtotal.getProvAportePatronal());
            rhListaProvisionesTOGeneralTotal.setProvIece(rhListaProvisionesTOAdministrativoSubtotal.getProvIece());
            rhListaProvisionesTOGeneralTotal.setProvSecap(rhListaProvisionesTOAdministrativoSubtotal.getProvSecap());
            rhListaProvisionesTOGeneralTotal.setProvXiii(rhListaProvisionesTOAdministrativoSubtotal.getProvXiii());
            rhListaProvisionesTOGeneralTotal.setProvXiv(rhListaProvisionesTOAdministrativoSubtotal.getProvXiv());
            rhListaProvisionesTOGeneralTotal.setProvFondoReserva(rhListaProvisionesTOAdministrativoSubtotal.getProvFondoReserva());
            rhListaProvisionesTOGeneralTotal.setProvVacaciones(rhListaProvisionesTOAdministrativoSubtotal.getProvVacaciones());
            rhListaProvisionesTOGeneralTotal.setProvDesahucio(rhListaProvisionesTOAdministrativoSubtotal.getProvDesahucio());

            listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOAdministrativoTitulo);
            for (RhListaProvisionesTO item : listaAdministrativo) {
                listaRhListaProvisionesTOFinal.add(item);
            }
            listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOAdministrativoSubtotal);
            listaRhListaProvisionesTOFinal.add(new RhListaProvisionesTO());
        }

        if (!listaCampo.isEmpty()) {
            RhListaProvisionesTO rhListaProvisionesTOCampoTitulo = new RhListaProvisionesTO();
            rhListaProvisionesTOCampoTitulo.setProvNombres("PERSONAL CAMPO");

            RhListaProvisionesTO rhListaProvisionesTOCampoSubtotal = new RhListaProvisionesTO();
            rhListaProvisionesTOCampoSubtotal.setProvCategoria("CAMPO");
            rhListaProvisionesTOCampoSubtotal.setProvNombres("TOTAL PERSONAL CAMPO");
            for (RhListaProvisionesTO item : listaCampo) {
                if (item.getProvAporteIndividual() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvAporteIndividual(rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual() == null ? cero.add(item.getProvAporteIndividual()) : rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual().add(item.getProvAporteIndividual()));
                }
                if (item.getProvAportePatronal() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvAportePatronal(rhListaProvisionesTOCampoSubtotal.getProvAportePatronal() == null ? cero.add(item.getProvAportePatronal()) : rhListaProvisionesTOCampoSubtotal.getProvAportePatronal().add(item.getProvAportePatronal()));
                }
                if (item.getProvIece() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvIece(rhListaProvisionesTOCampoSubtotal.getProvIece() == null ? cero.add(item.getProvIece()) : rhListaProvisionesTOCampoSubtotal.getProvIece().add(item.getProvIece()));
                }
                if (item.getProvSecap() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvSecap(rhListaProvisionesTOCampoSubtotal.getProvSecap() == null ? cero.add(item.getProvSecap()) : rhListaProvisionesTOCampoSubtotal.getProvSecap().add(item.getProvSecap()));
                }
                if (item.getProvXiii() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvXiii(rhListaProvisionesTOCampoSubtotal.getProvXiii() == null ? cero.add(item.getProvXiii()) : rhListaProvisionesTOCampoSubtotal.getProvXiii().add(item.getProvXiii()));
                }
                if (item.getProvXiv() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvXiv(rhListaProvisionesTOCampoSubtotal.getProvXiv() == null ? cero.add(item.getProvXiv()) : rhListaProvisionesTOCampoSubtotal.getProvXiv().add(item.getProvXiv()));
                }
                if (item.getProvFondoReserva() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvFondoReserva(rhListaProvisionesTOCampoSubtotal.getProvFondoReserva() == null ? cero.add(item.getProvFondoReserva()) : rhListaProvisionesTOCampoSubtotal.getProvFondoReserva().add(item.getProvFondoReserva()));
                }
                if (item.getProvVacaciones() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvVacaciones(rhListaProvisionesTOCampoSubtotal.getProvVacaciones() == null ? cero.add(item.getProvVacaciones()) : rhListaProvisionesTOCampoSubtotal.getProvVacaciones().add(item.getProvVacaciones()));
                }
                if (item.getProvDesahucio() != null) {
                    rhListaProvisionesTOCampoSubtotal.setProvDesahucio(rhListaProvisionesTOCampoSubtotal.getProvDesahucio() == null ? cero.add(item.getProvDesahucio()) : rhListaProvisionesTOCampoSubtotal.getProvDesahucio().add(item.getProvDesahucio()));
                }
            }

            if (rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual() != null) {
                rhListaProvisionesTOGeneralTotal.setProvAporteIndividual(rhListaProvisionesTOGeneralTotal.getProvAporteIndividual() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual()) : rhListaProvisionesTOGeneralTotal.getProvAporteIndividual().add(rhListaProvisionesTOCampoSubtotal.getProvAporteIndividual()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvAportePatronal() != null) {
                rhListaProvisionesTOGeneralTotal.setProvAportePatronal(rhListaProvisionesTOGeneralTotal.getProvAportePatronal() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvAportePatronal()) : rhListaProvisionesTOGeneralTotal.getProvAportePatronal().add(rhListaProvisionesTOCampoSubtotal.getProvAportePatronal()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvIece() != null) {
                rhListaProvisionesTOGeneralTotal.setProvIece(rhListaProvisionesTOGeneralTotal.getProvIece() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvIece()) : rhListaProvisionesTOGeneralTotal.getProvIece().add(rhListaProvisionesTOCampoSubtotal.getProvIece()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvSecap() != null) {
                rhListaProvisionesTOGeneralTotal.setProvSecap(rhListaProvisionesTOGeneralTotal.getProvSecap() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvSecap()) : rhListaProvisionesTOGeneralTotal.getProvSecap().add(rhListaProvisionesTOCampoSubtotal.getProvSecap()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvXiii() != null) {
                rhListaProvisionesTOGeneralTotal.setProvXiii(rhListaProvisionesTOGeneralTotal.getProvXiii() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvXiii()) : rhListaProvisionesTOGeneralTotal.getProvXiii().add(rhListaProvisionesTOCampoSubtotal.getProvXiii()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvXiv() != null) {
                rhListaProvisionesTOGeneralTotal.setProvXiv(rhListaProvisionesTOGeneralTotal.getProvXiv() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvXiv()) : rhListaProvisionesTOGeneralTotal.getProvXiv().add(rhListaProvisionesTOCampoSubtotal.getProvXiv()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvFondoReserva() != null) {
                rhListaProvisionesTOGeneralTotal.setProvFondoReserva(rhListaProvisionesTOGeneralTotal.getProvFondoReserva() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvFondoReserva()) : rhListaProvisionesTOGeneralTotal.getProvFondoReserva().add(rhListaProvisionesTOCampoSubtotal.getProvFondoReserva()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvVacaciones() != null) {
                rhListaProvisionesTOGeneralTotal.setProvVacaciones(rhListaProvisionesTOGeneralTotal.getProvVacaciones() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvVacaciones()) : rhListaProvisionesTOGeneralTotal.getProvVacaciones().add(rhListaProvisionesTOCampoSubtotal.getProvVacaciones()));
            }
            if (rhListaProvisionesTOCampoSubtotal.getProvDesahucio() != null) {
                rhListaProvisionesTOGeneralTotal.setProvDesahucio(rhListaProvisionesTOGeneralTotal.getProvDesahucio() == null ? cero.add(rhListaProvisionesTOCampoSubtotal.getProvDesahucio()) : rhListaProvisionesTOGeneralTotal.getProvDesahucio().add(rhListaProvisionesTOCampoSubtotal.getProvDesahucio()));
            }

            listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOCampoTitulo);
            for (RhListaProvisionesTO item : listaCampo) {
                listaRhListaProvisionesTOFinal.add(item);
            }
            listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOCampoSubtotal);
            listaRhListaProvisionesTOFinal.add(new RhListaProvisionesTO());
        }
        listaRhListaProvisionesTOFinal.add(rhListaProvisionesTOGeneralTotal);
        return listaRhListaProvisionesTOFinal;
    }

    public static boolean isLiquidacion(RhRol rhRol) {
        return rhRol.getRolLiqBonificacion().compareTo(BigDecimal.ZERO) != 0
                || rhRol.getRolLiqDesahucio().compareTo(BigDecimal.ZERO) != 0
                || rhRol.getRolLiqDesahucioIntempestivo().compareTo(BigDecimal.ZERO) != 0
                || rhRol.getRolLiqFondoReserva().compareTo(BigDecimal.ZERO) != 0
                || rhRol.getRolLiqSalarioDigno().compareTo(BigDecimal.ZERO) != 0
                || rhRol.getRolLiqVacaciones().compareTo(BigDecimal.ZERO) != 0
                || rhRol.getRolLiqXiii().compareTo(BigDecimal.ZERO) != 0
                || rhRol.getRolLiqXiv().compareTo(BigDecimal.ZERO) != 0;
    }

    @Override
    public MensajeTO insertarModificarRhRol(ConContable conContable, List<RhRol> listaRhRol, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        String framgmentoMensaje = "";
        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(),
                conContable.getConContablePK().getConEmpresa())) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(),
                    conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                if (listaRhRol.size() == 1) {
                    boolean isLIquidacion = isLiquidacion(listaRhRol.get(0));
                    conContable.setConConcepto(empleadoDao.getRhEmpleadoApellidosNombres(
                            listaRhRol.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                            listaRhRol.get(0).getRhEmpleado().getRhEmpleadoPK().getEmpId()));
                    conContable.setConReferencia(isLIquidacion ? "recursoshumanos.rh_liquidacion" : "recursoshumanos.rh_rol");
                    framgmentoMensaje = isLIquidacion ? "la liquidacion" : "el rol";

                } else {
                    conContable.setConConcepto("ROLES POR LOTES");
                    conContable.setConReferencia("recursoshumanos.rh_rol");
                    framgmentoMensaje = "el rol";
                }
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
            }

            susSuceso = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT"
                    : "UPDATE";
            susTabla = "recursoshumanos.rh_rol";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (conContable.getConContablePK().getConNumero() == null
                    || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingreso"
                    : "modifico";
            comprobar = rolDao.insertarModificarRhRol(conContable, listaRhRol, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar el rol.\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " " + framgmentoMensaje + " con la siguiente informacion:<br><br>"
                        + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Motivo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Numero: <font size = 5>"
                        + conContable.getConContablePK().getConNumero() + "</font>.<br></html>";
                mensajeTO.setFechaCreacion(conContable.getUsrFechaInserta().toString());
                mensajeTO.setContable(conContable.getConContablePK().getConNumero());
                mensajeTO.getMap().put("conContable", conContable);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public List<RhRol> getListRhRol(ConContablePK conContablePK) throws Exception {
        return rolDao.getListRhRol(conContablePK);
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        return rolDao.getPermisoAcciones(conContablePK, fechaContable);
    }

    @Override
    public List<RhListaDetalleRolesTO> getRhSoporteContableRolesTO(String empresa, String periodo, String tipo, String numero) throws Exception {
        return rolDao.getRhSoporteContableRolesTO(empresa, periodo, tipo, numero);
    }

    @Override
    public List<RhRolEmpTO> getListRhLiquidaciones(String empresa, String fechaDesde, String fechaHasta, String sector, String empCategoria, String empId, String nroRegistros) {
        return rolDao.getListRhLiquidaciones(empresa, fechaDesde, fechaHasta, sector, empCategoria, empId, nroRegistros);
    }

    @Override
    public List<ItemListaRolTO> calcularValoresRolesPago(List<RhRol> roles, String empresa, SisInfoTO sisInfoTO) throws Exception {
        List<RhRol> listaRhRol = new ArrayList<>();
        List<ItemListaRolTO> listaARetornar = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            listaRhRol.add(completarDatosDelRolDePago(roles.get(i), sisInfoTO));
        }
        List<RhComboFormaPagoTO> listaRhComboFormaPagoTO = formaPagoService.getComboFormaPagoTO(empresa);
        List<RhRolEmpleadoTO> listaRhRolEmpleadoTO = RhRolEmpleadoTO.convertirListaRhRolAListaRhRolEmpleadoTO(listaRhRol, listaRhComboFormaPagoTO);
        for (int i = 0; i < listaRhRolEmpleadoTO.size(); i++) {
            listaARetornar.add(construirItemListaRolTO(listaRhRolEmpleadoTO.get(i), roles.get(i), empresa, sisInfoTO));
        }
        return listaARetornar;
    }

    @Override
    public ItemListaRolTO obtenerVistaPreliminarRol(RhRol rol, String idEmpleado, String empresa, SisInfoTO sisInfoTO) throws Exception {
        List<RhRol> listaRhRol = new ArrayList<>();
        rol = completarDatosDelRolDePago(rol, sisInfoTO);
        listaRhRol.add(rol);
        List<RhComboFormaPagoTO> listaRhComboFormaPagoTO = formaPagoService.getComboFormaPagoTO(empresa);
        List<RhRolEmpleadoTO> listaRhRolEmpleadoTO = RhRolEmpleadoTO.convertirListaRhRolAListaRhRolEmpleadoTO(listaRhRol, listaRhComboFormaPagoTO);
        ItemListaRolTO itemListaRolTO = construirItemListaRolTO(listaRhRolEmpleadoTO.get(0), rol, empresa, sisInfoTO);
        return itemListaRolTO;
    }

    @Override
    public ItemListaRolTO obtenerItemListaRolTO(ConContablePK contablePk, String empresa, String idEmpleado, SisInfoTO usuario) throws Exception {
        RhEmpleado empleado = empleadoService.getEmpleado(empresa, idEmpleado);
        List<RhRol> listaRhRol = new ArrayList<>();
        listaRhRol.add(obtenerRol(contablePk, empresa, idEmpleado, usuario));
        List<RhComboFormaPagoTO> listaRhComboFormaPagoTO = formaPagoService.getComboFormaPagoTO(empresa);
        List<RhRolEmpleadoTO> listaRhRolEmpleadoTO = RhRolEmpleadoTO.convertirListaRhRolAListaRhRolEmpleadoTO(listaRhRol, listaRhComboFormaPagoTO);
        ItemListaRolTO itemListaRolTO = confirmarRolListadoTransTO(listaRhRolEmpleadoTO.get(0), empleado, empresa, usuario);
        ConContable conContable = contableService.getConContable(contablePk);
        itemListaRolTO.getRhRol().setConContable(conContable);
        return itemListaRolTO;
    }

    private RhRol obtenerRol(ConContablePK contablePk, String empresa, String idEmpleado, SisInfoTO usuario) throws Exception {
        usuario.setEmpresa(empresa);
        RhRol rhRol = new RhRol();
        List<RhRol> listaRhRol = rolDao.getListRhRol(contablePk);
        RhEmpleado empleado = empleadoService.getEmpleado(empresa, idEmpleado);

        for (RhRol item : listaRhRol) {
            if (item.getRhEmpleado().getRhEmpleadoPK().getEmpId().equalsIgnoreCase(idEmpleado)) {
                rhRol = item;
                rhRol.setRhEmpleado(empleado);
                break;
            }
        }
        return rhRol;
    }

    public ItemListaRolTO confirmarRolListadoTransTO(RhRolEmpleadoTO rhRolEmpleadoTO, RhEmpleado empleado, String empresa, SisInfoTO usuario) throws Exception {
        usuario.setEmpresa(rhRolEmpleadoTO.getPrEmpresa());
        ItemListaRolTO itemListaRolTO = new ItemListaRolTO();
        List<RhRol> listaRhRolConfirmar = generarRol(rhRolEmpleadoTO, empleado, true, empresa, usuario);
        if (!listaRhRolConfirmar.isEmpty()) {
            RhRol rhRol = listaRhRolConfirmar.get(0);
            if (!rhRol.isEmpCancelarXiiiSueldoMensualmente()) {
                rhRol.setRolXiii(cero);
            }
            if (!rhRol.isEmpCancelarXivSueldoMensualmente()) {
                rhRol.setRolXiv(cero);
            }
            itemListaRolTO.setRhRol(rhRol);
            itemListaRolTO.setRolHastaTexto(UtilsDate.fechaFormatoString(rhRol.getRolHasta(), "yyyy-MM-dd"));
            itemListaRolTO.setRolDesdeTexto(UtilsDate.fechaFormatoString(rhRol.getRolDesde(), "yyyy-MM-dd"));
            itemListaRolTO.setRolValorPrestamos(rhRol.getRolPrestamos());
            itemListaRolTO.setRolImpuestoRenta((rhRol.getRolRetencionFuente() == null) ? cero : (rhRol.getRolRetencionFuente().add(cero)));
            itemListaRolTO.setRolPermisoMedico((rhRol.getRolDescuentoPermisoMedico() == null) ? cero : (rhRol.getRolDescuentoPermisoMedico().add(cero)));
            itemListaRolTO.setRolVacaciones((rhRol.getRolDescuentoVacaciones() == null) ? cero : (rhRol.getRolDescuentoVacaciones().add(cero)));
            if (rhRol.isEmpCancelarXiiiSueldoMensualmente()) {
                itemListaRolTO.setRolLiqXiiiSueldo((rhRol.getRolLiqXiii() == null) ? cero : (rhRol.getRolLiqXiii().add(rhRol.getRolXiii())));
            } else {
                itemListaRolTO.setRolLiqXiiiSueldo((rhRol.getRolLiqXiii() == null) ? cero : rhRol.getRolLiqXiii());
            }
            if (rhRol.isEmpCancelarXivSueldoMensualmente()) {
                itemListaRolTO.setRolLiqXiiiSueldo((rhRol.getRolLiqXiv() == null) ? cero : (rhRol.getRolLiqXiv().add(rhRol.getRolXiv())));
            } else {
                itemListaRolTO.setRolLiqXiiiSueldo((rhRol.getRolLiqXiv() == null) ? cero : rhRol.getRolLiqXiv());
            }
            itemListaRolTO.setRolDescFondoReserva((!rhRol.isEmpCancelarFondoReservaMensualmente()) ? rhRol.getRolFondoReserva() : BigDecimal.ZERO);
            itemListaRolTO.setTotalIngresos(UtilTransacciones.calcularTotalIngresos(rhRol));
            itemListaRolTO.setTotalDescuentos(UtilTransacciones.calcularTotalEgresos(rhRol, itemListaRolTO.getRolValorPrestamos(), itemListaRolTO.getRhRol().getRolPrestamoHipotecario(), itemListaRolTO.getRhRol().getRolPrestamoQuirografario()));
            itemListaRolTO.setTotalLiquidacion(UtilTransacciones.calcularTotalLiquidacion(rhRol));
            itemListaRolTO.setTotalPagar(UtilTransacciones.calcularTotalPagar(itemListaRolTO.getTotalIngresos(), itemListaRolTO.getTotalDescuentos(), itemListaRolTO.getTotalLiquidacion()));
        }
        return itemListaRolTO;

    }

    public ItemListaRolTO construirItemListaRolTO(RhRolEmpleadoTO rhRolEmpleadoTO, RhRol rhRol, String empresa, SisInfoTO usuario) throws Exception {
        ItemListaRolTO itemListaRolTO = new ItemListaRolTO();
        if (!rhRol.isEmpCancelarXiiiSueldoMensualmente()) {
            rhRol.setRolXiii(cero);
        }
        if (!rhRol.isEmpCancelarXivSueldoMensualmente()) {
            rhRol.setRolXiv(cero);
        }
        itemListaRolTO.setRhRol(rhRol);
        itemListaRolTO.setRolHastaTexto(UtilsDate.fechaFormatoString(rhRol.getRolHasta(), "yyyy-MM-dd"));
        itemListaRolTO.setRolDesdeTexto(UtilsDate.fechaFormatoString(rhRol.getRolDesde(), "yyyy-MM-dd"));
        itemListaRolTO.setRolValorPrestamos(rhRol.getRolPrestamos());
        itemListaRolTO.setRolImpuestoRenta((rhRol.getRolRetencionFuente() == null) ? cero : (rhRol.getRolRetencionFuente().add(cero)));
        itemListaRolTO.setRolPermisoMedico((rhRol.getRolDescuentoPermisoMedico() == null) ? cero : (rhRol.getRolDescuentoPermisoMedico().add(cero)));
        itemListaRolTO.setRolVacaciones((rhRol.getRolDescuentoVacaciones() == null) ? cero : (rhRol.getRolDescuentoVacaciones().add(cero)));
        if (rhRol.isEmpCancelarXiiiSueldoMensualmente()) {
            itemListaRolTO.setRolLiqXiiiSueldo((rhRol.getRolLiqXiii() == null) ? cero : (rhRol.getRolLiqXiii().add(rhRol.getRolXiii())));
        } else {
            itemListaRolTO.setRolLiqXiiiSueldo((rhRol.getRolLiqXiii() == null) ? cero : rhRol.getRolLiqXiii());
        }
        if (rhRol.isEmpCancelarXivSueldoMensualmente()) {
            itemListaRolTO.setRolLiqXiiiSueldo((rhRol.getRolLiqXiv() == null) ? cero : (rhRol.getRolLiqXiv().add(rhRol.getRolXiv())));
        } else {
            itemListaRolTO.setRolLiqXiiiSueldo((rhRol.getRolLiqXiv() == null) ? cero : rhRol.getRolLiqXiv());
        }
        itemListaRolTO.setRolDescFondoReserva((!rhRol.isEmpCancelarFondoReservaMensualmente()) ? rhRol.getRolFondoReserva() : BigDecimal.ZERO);
        itemListaRolTO.setTotalIngresos(UtilTransacciones.calcularTotalIngresos(rhRol));
        itemListaRolTO.setTotalDescuentos(UtilTransacciones.calcularTotalEgresos(rhRol, itemListaRolTO.getRolValorPrestamos(), itemListaRolTO.getRhRol().getRolPrestamoHipotecario(), itemListaRolTO.getRhRol().getRolPrestamoQuirografario()));
        itemListaRolTO.setTotalLiquidacion(UtilTransacciones.calcularTotalLiquidacion(rhRol));
        itemListaRolTO.setTotalPagar(UtilTransacciones.calcularTotalPagar(itemListaRolTO.getTotalIngresos(), itemListaRolTO.getTotalDescuentos(), itemListaRolTO.getTotalLiquidacion()));
        return itemListaRolTO;
    }

    private List<RhRol> generarRol(RhRolEmpleadoTO rhRolEmpleadoTO, RhEmpleado empleadorh, boolean isConsulta, String empresa, SisInfoTO usuario) throws Exception {
        List<RhRol> listaRhRolConfirmar = new ArrayList<>();
        if (!isConsulta) {
            RhRol rhRol = new RhRol();
            RhEmpleado empleado = empleadorh;
            rhRol.setEmpCargo(rhRolEmpleadoTO.getPrCargo());
            rhRol.setRolDocumento(rhRolEmpleadoTO.getNumDocumento());
            rhRol.setRolObservaciones(rhRolEmpleadoTO.getObservacion());
            rhRol.setRolFechaUltimoSueldo(rhRolEmpleadoTO.getPrFechaUltimoSueldo());
            rhRol.setRolDesde(UtilsDate.fechaFormatoDate(rhRolEmpleadoTO.getRolDesde(), "yyyy-MM-dd"));
            rhRol.setRolHasta(UtilsDate.fechaFormatoDate(rhRolEmpleadoTO.getRolHasta(), "yyyy-MM-dd"));
            rhRol.setRolDiasFaltasReales(rhRolEmpleadoTO.getRolDiasFaltasReales());
            rhRol.setRolDiasExtrasReales((short) 0);
            rhRol.setEmpSueldo(rhRolEmpleadoTO.getPrSueldo());
            if (rhRolEmpleadoTO.getFormaPago() != null) {
                rhRol.setRolFormaPago(rhRolEmpleadoTO.getFormaPago().getFpDetalle());
            }
            rhRol.setRolDiasVacaciones(rhRolEmpleadoTO.getRolDiasVacaciones());
            rhRol.setRolDiasPermisoMedico(rhRolEmpleadoTO.getRolDiasPermisoMedico());
            rhRol.setRolAuxiliar(false);
            rhRol.setRolLiqBonificacion(rhRolEmpleadoTO.getRolLiqBonificacion());
            rhRol.setRolPrestamos(rhRolEmpleadoTO.getPrestamo());
            empleado.setEmpSaldoPrestamos(empleado.getEmpSaldoPrestamos().subtract(rhRolEmpleadoTO.getPrestamo()));
            rhRol.setRhEmpleado(empleado);
            rhRol.setRolHorasExtras(rhRolEmpleadoTO.getRolHorasExtrasReales());
            rhRol.setPrdSector(new PrdSector(rhRolEmpleadoTO.getPrEmpresa(), rhRolEmpleadoTO.getPrSector()));
            rhRol.setRolLiqFondoReserva(rhRolEmpleadoTO.getRolLiqFondoReserva());
            rhRol.setRolLiqXiii(rhRolEmpleadoTO.getRolLiqXiii());
            rhRol.setRolLiqXiv(rhRolEmpleadoTO.getRolLiqXiv());
            rhRol.setRolLiqVacaciones(rhRolEmpleadoTO.getRolLiqVacaciones());
            rhRol.setRolLiqSalarioDigno(rhRolEmpleadoTO.getRolLiqSalarioDigno());
            rhRol.setRolLiqDesahucio(rhRolEmpleadoTO.getRolLiqDesahucio());
            rhRol.setRolLiqDesahucioIntempestivo(rhRolEmpleadoTO.getRolLiqDesahucioIntempestivo());
            rhRol.setRolPrestamoQuirografario(rhRolEmpleadoTO.getRolPrestamoQuirografario() == null ? BigDecimal.ZERO : rhRolEmpleadoTO.getRolPrestamoQuirografario());
            rhRol.setRolPrestamoHipotecario(rhRolEmpleadoTO.getRolPrestamoHipotecario() == null ? BigDecimal.ZERO : rhRolEmpleadoTO.getRolPrestamoHipotecario());
            rhRol.setRolHorasExtras100(rhRolEmpleadoTO.getRolHorasExtrasReales100() == null ? BigDecimal.ZERO : rhRolEmpleadoTO.getRolHorasExtrasReales100());

            if (rhRolEmpleadoTO.getFormaPago() != null) {
                rhRol.setConCuentas(new ConCuentas(rhRolEmpleadoTO.getPrEmpresa(), (rhRolEmpleadoTO.getFormaPago().getFpDetalle().equalsIgnoreCase("POR PAGAR") ? empleado.getRhCategoria().getCtaPorPagarSueldo() : rhRolEmpleadoTO.getFormaPago().getCtaCodigo())));
            }
            String mensaje = UtilsRol.generarRhRol(rhRol, usuario);
            String control = String.valueOf(mensaje.charAt(0));
            mensaje = mensaje.substring(1, mensaje.length());

            if (control.equalsIgnoreCase("T")) {
                listaRhRolConfirmar.add(rhRol);
            }
        } else {
            RhRol rhRol = consultarRolConfirmar(empresa, rhRolEmpleadoTO);
            listaRhRolConfirmar.add(rhRol);
        }
        return listaRhRolConfirmar;
    }

    private RhRol consultarRolConfirmar(String empresa, RhRolEmpleadoTO rhRolEmpleadoTO) throws Exception {
        RhRol rhRol = new RhRol();
        List<RhListaDetalleRolesTO> listaDetalleRolesTO = rolDao.getRhDetalleRolesTO(
                empresa,
                UtilsDate.fechaFormatoString(rhRolEmpleadoTO.getRolDesde(), "yyyy-MM-dd"),
                UtilsDate.fechaFormatoString(rhRolEmpleadoTO.getRolHasta(), "yyyy-MM-dd"),
                null,
                null,
                rhRolEmpleadoTO.getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                "Todos");

        for (RhListaDetalleRolesTO item : listaDetalleRolesTO) {
            if (item.getLrpId() != null && item.getLrpId().equalsIgnoreCase(rhRolEmpleadoTO.getRhEmpleado().getRhEmpleadoPK().getEmpId())
                    && item.getLrpDesde().equalsIgnoreCase(UtilsDate.fechaFormatoString(rhRolEmpleadoTO.getRolDesde(), "yyyy-MM-dd"))
                    && item.getLrpHasta().equalsIgnoreCase(UtilsDate.fechaFormatoString(rhRolEmpleadoTO.getRolHasta(), "yyyy-MM-dd"))) {
                rhRol.setRhEmpleado(rhRolEmpleadoTO.getRhEmpleado());
                rhRol.setEmpSueldo(item.getLrpSueldo());
                rhRol.setRolAnticipos(item.getLrpAnticipos() == null ? BigDecimal.ZERO : item.getLrpAnticipos());
                rhRol.setRolBonoFijo(item.getLrpBonosFijo() == null ? BigDecimal.ZERO : item.getLrpBonosFijo());
                rhRol.setRolBonoFijoNd(item.getLrpBonosFijoNd() == null ? BigDecimal.ZERO : item.getLrpBonosFijoNd());
                rhRol.setRolBonos(item.getLrpBonos() == null ? BigDecimal.ZERO : item.getLrpBonos());
                rhRol.setRolBonosnd(item.getLrpBonosnd() == null ? BigDecimal.ZERO : item.getLrpBonosnd());
                rhRol.setRolDescuentoVacaciones(item.getLrpDescuentoVacaciones() == null ? BigDecimal.ZERO : item.getLrpDescuentoVacaciones());
                rhRol.setRolDescuentoPermisoMedico(item.getLrpDescuentoPermisoMedico() == null ? BigDecimal.ZERO : item.getLrpDescuentoPermisoMedico());
                rhRol.setRolDesde(rhRolEmpleadoTO.getRolDesde());
                rhRol.setRolDiasDescansoReales(item.getLrpDd() == null ? 0 : Short.parseShort("" + item.getLrpDd()));
                rhRol.setRolDiasExtrasReales(item.getLrpDe() == null ? 0 : Short.parseShort("" + item.getLrpDe()));
                rhRol.setRolDiasFaltasReales(item.getLrpDf() == null ? 0 : Short.parseShort("" + item.getLrpDf()));
                rhRol.setRolDiasLaboradosReales(item.getLrpDl() == null ? 0 : Short.parseShort("" + item.getLrpDl()));
                rhRol.setRolDiasPagadosReales(item.getLrpDp() == null ? 0 : Short.parseShort("" + item.getLrpDp()));
                rhRol.setRolDiasVacaciones(item.getLrpDvac());
                rhRol.setRolDiasPermisoMedico(item.getLrpDpm() == null ? 0 : Short.parseShort("" + item.getLrpDpm()));
                rhRol.setRolFondoReserva(item.getLrpFondoReserva() == null ? BigDecimal.ZERO : item.getLrpFondoReserva());
                rhRol.setRolHasta(rhRolEmpleadoTO.getRolHasta());
                rhRol.setRolHorasExtras(item.getLrpHorasExtras() == null ? BigDecimal.ZERO : item.getLrpHorasExtras());
                rhRol.setRolHorasExtras100(item.getLrpHorasExtras100() == null ? BigDecimal.ZERO : item.getLrpHorasExtras100());
                rhRol.setRolHorasExtrasExtraordinarias100(item.getLrpHorasExtrasExtraordinarias100() == null ? BigDecimal.ZERO : item.getLrpHorasExtrasExtraordinarias100());
                rhRol.setRolSaldoHorasExtras50(item.getLrpSaldoHorasExtras50() == null ? BigDecimal.ZERO : item.getLrpSaldoHorasExtras50());
                rhRol.setRolSaldoHorasExtras100(item.getLrpSaldoHorasExtras100() == null ? BigDecimal.ZERO : item.getLrpSaldoHorasExtras100());
                rhRol.setRolSaldoHorasExtrasExtraordinarias100(item.getLrpSaldoHorasExtrasExtraordinarias100() == null ? BigDecimal.ZERO : item.getLrpSaldoHorasExtrasExtraordinarias100());
                rhRol.setRolPrestamoQuirografario(item.getLrpPrestamoQuirografario() == null ? BigDecimal.ZERO : item.getLrpPrestamoQuirografario());
                rhRol.setRolPrestamoHipotecario(item.getLrpPrestamoHipotecario() == null ? BigDecimal.ZERO : item.getLrpPrestamoHipotecario());
                rhRol.setRolIess(item.getLrpIess() == null ? BigDecimal.ZERO : item.getLrpIess());
                rhRol.setRolIessExtension(item.getLrpIessExtension() == null ? BigDecimal.ZERO : item.getLrpIessExtension());
                rhRol.setRolIngresos(item.getLrpIngresos() == null ? BigDecimal.ZERO : item.getLrpIngresos());
                rhRol.setRolLiqBonificacion(item.getLrpLiquidacionBonificacion() == null ? BigDecimal.ZERO : item.getLrpLiquidacionBonificacion());
                rhRol.setRolLiqDesahucio(item.getLrpLiquidacionDesahucio() == null ? BigDecimal.ZERO : item.getLrpLiquidacionDesahucio());
                rhRol.setRolLiqDesahucioIntempestivo(item.getLrpLiquidacionDesahucioIntempestivo() == null ? BigDecimal.ZERO : item.getLrpLiquidacionDesahucioIntempestivo());
                rhRol.setRolLiqSalarioDigno(item.getLrpLiquidacionSalarioDigno() == null ? BigDecimal.ZERO : item.getLrpLiquidacionSalarioDigno());
                rhRol.setRolLiqVacaciones(item.getLrpLiquidacionVacaciones() == null ? BigDecimal.ZERO : item.getLrpLiquidacionVacaciones());
                rhRol.setRolLiqXiii(item.getLrpLiquidacionXiii() == null ? BigDecimal.ZERO : item.getLrpLiquidacionXiii());
                rhRol.setRolLiqXiv(item.getLrpLiquidacionXiv() == null ? BigDecimal.ZERO : item.getLrpLiquidacionXiv());
                rhRol.setRolOtrosIngresos(item.getLrpOtrosIngresos() == null ? BigDecimal.ZERO : item.getLrpOtrosIngresos());
                rhRol.setRolOtrosIngresosNd(item.getLrpOtrosIngresosNd() == null ? BigDecimal.ZERO : item.getLrpOtrosIngresosNd());
                rhRol.setRolPrestamos(item.getLrpPrestamos() == null ? BigDecimal.ZERO : item.getLrpPrestamos());
                rhRol.setRolRetencionFuente(item.getLrpRetencion() == null ? BigDecimal.ZERO : item.getLrpRetencion());
                rhRol.setRolSaldoAnterior(item.getLrpSaldo() == null ? BigDecimal.ZERO : item.getLrpSaldo());
                rhRol.setRolXiii(item.getLrpXiiiSueldo() == null ? BigDecimal.ZERO : item.getLrpXiiiSueldo());
                rhRol.setRolXiv(item.getLrpXivSueldo() == null ? BigDecimal.ZERO : item.getLrpXivSueldo());
            }
        }

        return rhRol;
    }

    @Override
    public Map<String, Object> obtenerDatosParaRolesDePago(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<PrdListaSectorTO> sectores = sectorDao.getListaSector(empresa, true);
        List<RhComboCategoriaTO> categorias = categoriaDao.getComboCategoria(empresa);
        List<RhRolMotivo> motivos = rolMotivoService.getListaRhRolMotivo(empresa);
        List<RhComboFormaPagoTO> formasDePago = formaPagoService.getComboFormaPagoTO(empresa);
        Date fechaActual = sistemaWebServicio.getFechaActual();
        List<SisPeriodo> periodos = periodoService.getListaPeriodo(empresa);

        respuesta.put("sectores", sectores);
        respuesta.put("categorias", categorias);
        respuesta.put("motivos", motivos);
        respuesta.put("formasDePago", formasDePago);
        respuesta.put("fechaActual", fechaActual);
        respuesta.put("periodos", periodos);

        return respuesta;
    }

    @Override
    public MensajeTO insertarRhRol(String observacionesContable, List<RhRol> listaRhRol, String perHasta, List<ConAdjuntosContableWebTO> listadoImagenes, List<RhVacacionesGozadas> gozadas, SisInfoTO sisInfoTO) throws Exception {
        List<RhRol> listaRhRolNueva = new ArrayList<>();
        String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
        ConContable contable = new ConContable(sisInfoTO.getEmpresa(), "", "C-ROL", "");
        if ((observacionesContable == null || observacionesContable.equals("")) && listaRhRol.size() >= 1) {
            contable.setConObservaciones("OBSERVACION GENERAL");
        } else {
            contable.setConObservaciones(observacionesContable);
        }
        contable.setConFecha(UtilsDate.fechaFormatoDate(perHasta, "dd-MM-yyyy"));
        contable.setConPendiente(false);
        contable.setConCodigo(secuencia);
        for (RhRol rol : listaRhRol) {
            rol = completarDatosDelRolDePago(rol, sisInfoTO);
            if (rol != null) {
//                RhListaEmpleadoLoteTO empleadoPlantilla = empleadoDao.existeEmpleadoEnPlantilla(rol);
//                if (empleadoPlantilla == null) {
//                    throw new GeneralException("No es posible generar un rol de pagos. "
//                            + " <br> Empleado: " + rol.getRhEmpleado().getEmpApellidos() + " " + rol.getRhEmpleado().getEmpNombres()
//                            + " <br> Ya tiene un rol en esta fecha o periodo.");
//                }
                int res = rol.getRolTotal().compareTo(BigDecimal.ZERO);
                if (res == -1 && !rol.getRolFormaPago().equalsIgnoreCase("POR PAGAR")) {
                    throw new GeneralException("El valor a pagar no puede ser negativo. "
                            + " <br> Empleado: " + rol.getRhEmpleado().getEmpApellidos() + " " + rol.getRhEmpleado().getEmpNombres()
                            + " <br> Considere elegir la forma de pago \"POR PAGAR\"");
                }
            }
            listaRhRolNueva.add(rol);
        }
        MensajeTO mensaje = insertarModificarRhRol(contable, listaRhRolNueva, sisInfoTO);
        if (mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
            if (gozadas != null && !gozadas.isEmpty()) {
                for (RhVacacionesGozadas gozada : gozadas) {
                    gozada.setConEmpresa(contable.getConContablePK().getConEmpresa());
                    gozada.setConNumero(contable.getConContablePK().getConNumero());
                    gozada.setConPeriodo(contable.getConContablePK().getConPeriodo());
                    gozada.setConTipo(contable.getConContablePK().getConTipo());
                    vacacionesGozadasDao.actualizar(gozada);
                }
            }
            contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
            contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
        }
        return mensaje;
    }

    @Override
    public MensajeTO insertarModificarRhRolEscritorio(ConContable conContable, List<RhRol> listaRhRol, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensaje = insertarModificarRhRol(conContable, listaRhRol, sisInfoTO);
        if (mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
            contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
        }
        return mensaje;
    }

    public String validarDatosDeRolObservaciones(List<RhRol> listaRhRol, ConContable contable) {
        if ((contable.getConObservaciones() == null || contable.getConObservaciones().equals("")) && listaRhRol.size() > 1) {
            contable.setConObservaciones("OBSERVACION GENERAL");
        } else if (listaRhRol.size() == 1) {
            if (listaRhRol.get(0).getRolObservaciones() == null) {
                contable.setConObservaciones(contable.getConObservaciones());
            } else {
                contable.setConObservaciones(listaRhRol.get(0).getRolObservaciones());
            }
        } else {
            contable.setConObservaciones(contable.getConObservaciones());
        }
        return contable.getConObservaciones();
    }

    @Override
    public MensajeTO modificarRhRol(List<RhRol> listaRhRol, ConContable contable, SisInfoTO sisInfoTO) throws Exception {
        List<RhRol> listaRhRolNueva = new ArrayList<>();
        //validacion
        contable.setConObservaciones(this.validarDatosDeRolObservaciones(listaRhRol, contable));
        if (listaRhRol != null && !listaRhRol.isEmpty()) {
            for (RhRol rol : listaRhRol) {
                rol.setConContable(new ConContable(contable.getConContablePK()));
                rol = completarDatosDelRolDePago(rol, sisInfoTO);
                if (rol != null) {
                    int res = rol.getRolTotal().compareTo(BigDecimal.ZERO);
                    if (res == -1 && !rol.getRolFormaPago().equalsIgnoreCase("POR PAGAR")) {
                        throw new GeneralException("El valor a pagar no puede ser negativo. "
                                + " <br> Empleado: " + rol.getRhEmpleado().getEmpApellidos() + " " + rol.getRhEmpleado().getEmpNombres()
                                + " <br> Considere elegir la forma de pago \"POR PAGAR\"");
                    }
                }
                listaRhRolNueva.add(rol);
            }
            return insertarModificarRhRol(contable, listaRhRolNueva, sisInfoTO);
        } else {
            throw new GeneralException("No existen roles de pago por procesar.");
        }
    }

    @Override
    public RhRol completarDatosDelRolDePago(RhRol rol, SisInfoTO sisInfoTO) throws Exception {
        RhEmpleado empleado = empleadoDao.obtenerPorIdEvict(RhEmpleado.class, rol.getRhEmpleado().getRhEmpleadoPK());
        rol.setRhEmpleado(empleado);
        if (rol.getRolFormaPago().equalsIgnoreCase("POR PAGAR")) {
            if (empleado.getRhCategoria().getCtaPorPagarSueldo() == null || empleado.getRhCategoria().getCtaPorPagarSueldo().equals("")) {
                throw new GeneralException("La categoría: " + empleado.getRhCategoria().getRhCategoriaPK().getCatNombre() + ","
                        + " <br> del empleado: " + rol.getRhEmpleado().getEmpApellidos() + " " + rol.getRhEmpleado().getEmpNombres()
                        + " <br> carece de CUENTA POR PAGAR SUELDO.");
            }
            rol.setConCuentas(new ConCuentas(sisInfoTO.getEmpresa(), (empleado.getRhCategoria().getCtaPorPagarSueldo())));
        }
        return calculosGeneralRol.generarRhRol(rol, sisInfoTO);
    }

    @Override
    public RhRolSaldoEmpleadoTO actualizarSaldosEmpleado(String empCodigo, String empId, String fechaDesde, String fechaHasta) throws Exception {
        RhRolSaldoEmpleadoTO saldoEmpleado = rolDao.getRhRolSaldoEmpleado(empCodigo, empId, fechaDesde, fechaHasta, null);
        if (saldoEmpleado != null) {
            RhEmpleado empleado = empleadoDao.buscarEmpleado(empCodigo, empId);
            if (empleado != null) {
                empleado.setEmpSaldoAnticipos(saldoEmpleado.getSaldoAnticipo());
                empleado.setEmpSaldoBonos(saldoEmpleado.getSaldoBono());
                empleado.setEmpSaldoBonosNd(saldoEmpleado.getSaldoBonond());
                empleadoDao.actualizar(empleado);
            }
        }
        return saldoEmpleado;
    }

    @Override
    public boolean reconstruirSaldosEmpleadoPorContable(ConContablePK conContablePK) throws Exception {
        return rolDao.reconstruirSaldosEmpleadoPorContable(conContablePK);
    }

    @Override
    public boolean reconstruirSaldosEmpleado(String empresa) throws Exception {
        return rolDao.reconstruirSaldosEmpleado(empresa);
    }

    //cambios
    @Override
    public MensajeTO insertarRhLiquidacion(RhRol rhRol, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        List<RhRol> listaRhRolNueva = new ArrayList<>();
        MensajeTO respuesta = new MensajeTO();
        boolean documentoRepetido = false;
        boolean validarDocumento = rhRol.getRolDocumento() != null;
        if (validarDocumento) {
            documentoRepetido = chequeService.isExisteChequeAimprimir(sisInfoTO.getEmpresa(), rhRol.getRolFormaPago(), rhRol.getRolDocumento(), null);
        }
        if (!documentoRepetido) {
            String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
            ConContable contable = new ConContable(sisInfoTO.getEmpresa(), "", rhRol.getRhEmpleado().getRhCategoria().getTipCodigo(), "");
            contable.setConFecha(rhRol.getRolHasta());
            contable.setConPendiente(false);
            contable.setConCodigo(secuencia);
            contable.setConObservaciones(rhRol.getRolObservaciones());
            if (rhRol.getRolFormaPago().equalsIgnoreCase("POR PAGAR")) {
                rhRol.setConCuentas(new ConCuentas(sisInfoTO.getEmpresa(), (rhRol.getRhEmpleado().getRhCategoria().getCtaPorPagarSueldo())));
            }
            RhRol rolLiquidacion = calculosGeneralRol.generarRhRol(rhRol, sisInfoTO);
            if (rolLiquidacion.getRolTotal().compareTo(BigDecimal.ZERO) >= 0) {
                Date fechaIess = rolLiquidacion.getRhEmpleado().getEmpFechaAfiliacionIess();
                if (fechaIess != null) {
                    if (fechaIess.getTime() > rolLiquidacion.getRolDesde().getTime()) {
                        respuesta.setMensaje("FLa fecha debe ser mayor a la del registro al IESS");
                    }
                }

                listaRhRolNueva.add(rolLiquidacion);
                respuesta = insertarModificarRhRol(contable, listaRhRolNueva, sisInfoTO);
                if (respuesta.getMensaje() != null && respuesta.getMensaje().substring(0, 1).equals("T")) {
                    contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
                    contableService.actualizarImagenesContables(listadoImagenes, contable.getConContablePK());
                }
            } else {
                respuesta.setMensaje("FEl resultado de la liquidación " + rolLiquidacion.getRolTotal() + " no puede ser igual o menor que 0");
            }
        } else {
            respuesta.setMensaje("FCheque repetido");
        }
        return respuesta;
    }

    @Override
    public MensajeTO modificarRhiquidacion(RhRol rhRol, ConContable contable, SisInfoTO sisInfoTO) throws Exception {
        List<RhRol> listaRhRolNueva = new ArrayList<>();
        MensajeTO respuesta = new MensajeTO();
        boolean documentoRepetido = false;
        boolean validarDocumento = rhRol.getRolDocumento() != null;
        RhEmpleado empleadoAntesDeCalculo = rhRol.getRhEmpleado();
        if (validarDocumento) {
            documentoRepetido = chequeService.isExisteChequeAimprimir(sisInfoTO.getEmpresa(), rhRol.getRolFormaPago(), rhRol.getRolDocumento(), null);
        }
        if (!documentoRepetido) {
            if (rhRol.getRolFormaPago().equalsIgnoreCase("POR PAGAR")) {
                rhRol.setConCuentas(new ConCuentas(sisInfoTO.getEmpresa(), (rhRol.getRhEmpleado().getRhCategoria().getCtaPorPagarSueldo())));
            }
            RhRol rolLiquidacion = calculosGeneralRol.generarRhRol(rhRol, sisInfoTO);
            rolLiquidacion.getRhEmpleado().setEmpMotivoSalida(empleadoAntesDeCalculo.getEmpMotivoSalida());
            rolLiquidacion.getRhEmpleado().setEmpFechaPrimeraSalida(empleadoAntesDeCalculo.getEmpFechaPrimeraSalida());
            rolLiquidacion.getRhEmpleado().setEmpFechaUltimaSalida(empleadoAntesDeCalculo.getEmpFechaUltimaSalida());
            if (rolLiquidacion.getRolTotal().compareTo(BigDecimal.ZERO) >= 0) {
                Date fechaIess = rolLiquidacion.getRhEmpleado().getEmpFechaAfiliacionIess();
                if (fechaIess != null) {
                    if (fechaIess.getTime() > rolLiquidacion.getRolDesde().getTime()) {
                        respuesta.setMensaje("FLa fecha debe ser mayor a la del registro al IESS");
                    }
                }

                listaRhRolNueva.add(rolLiquidacion);
                respuesta = insertarModificarRhRol(contable, listaRhRolNueva, sisInfoTO);
            } else {
                respuesta.setMensaje("FEl resultado de la liquidación no puede ser igual o menor que 0");
            }
        } else {
            respuesta.setMensaje("FCheque repetido");
        }
        return respuesta;
    }

    @Override
    public ItemListaRolTO obtenerRolTO(ConContablePK conContablePK, String empresa, String idEmpleado, SisInfoTO sisInfoTO) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_rol WHERE (con_empresa, con_periodo, con_tipo, con_numero, emp_id) =('"
                + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','"
                + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "','"
                + idEmpleado + "')";
        RhRol rol = genericSQLDao.obtenerObjetoPorSql(sql, RhRol.class
        );
        ItemListaRolTO itemListaRolTO = construirItemListaRolTO(null, rol, empresa, sisInfoTO);
        return itemListaRolTO;
    }

    @Override
    public BigDecimal calcularPermisoMedico(String empleadoId, Integer diasPermiso, String fechaDesde, String empresa, SisInfoTO sisInfoTO) throws Exception {

        int totalDiasRol = 0;
        int totalDias = 0;
        BigDecimal totalIngreso = BigDecimal.ZERO;
        BigDecimal bono = BigDecimal.ZERO;
        BigDecimal sueldoDiarioPromedio = BigDecimal.ZERO;
        BigDecimal promedioDiarioSubsidiado = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        RhEmpleadoTO empleadoTO = null;
        List<RhRol> listaRoles = rolDao.obtenerTresUltimosRoles(empleadoId, fechaDesde, empresa);

        if (listaRoles == null || listaRoles.isEmpty()) {
            empleadoTO = empleadoDao.getEmpleadoTO(empresa, empleadoId);

            if (empleadoTO != null) {
                totalDias = (empleadoTO.getEmpDiasTrabajados() * 3);
                total = (empleadoTO.getEmpSueldoIess().add(empleadoTO.getEmpBonoFijo())).multiply(new BigDecimal(3));
            }
        } else {
            switch (listaRoles.size()) {
                case 1:
                    empleadoTO = empleadoDao.getEmpleadoTO(empresa, empleadoId);
                    for (RhRol item : listaRoles) {
                        totalDiasRol += item.getRolDiasLaboradosReales();
                        totalIngreso = totalIngreso.add(item.getRolIngresos()).add(item.getRolBonos()).add(item.getRolBonoFijo())
                                .add(item.getRolHorasExtras()).add(item.getRolHorasExtras100()).add(item.getRolHorasExtrasExtraordinarias100())
                                .add(item.getRolSaldoHorasExtras50()).add(item.getRolSaldoHorasExtras100()).add(item.getRolSaldoHorasExtrasExtraordinarias100());
                    }
                    totalDias = totalDiasRol + (empleadoTO.getEmpDiasTrabajados() * 2);
                    total = totalIngreso.add((empleadoTO.getEmpSueldoIess().add(empleadoTO.getEmpBonoFijo())).multiply(new BigDecimal(2)));
                    break;
                case 2:
                    empleadoTO = empleadoDao.getEmpleadoTO(empresa, empleadoId);
                    for (RhRol item : listaRoles) {
                        totalDiasRol += item.getRolDiasLaboradosReales();
                        totalIngreso = totalIngreso.add(item.getRolIngresos()).add(item.getRolBonos()).add(item.getRolBonoFijo())
                                .add(item.getRolHorasExtras()).add(item.getRolHorasExtras100()).add(item.getRolHorasExtrasExtraordinarias100())
                                .add(item.getRolSaldoHorasExtras50()).add(item.getRolSaldoHorasExtras100()).add(item.getRolSaldoHorasExtrasExtraordinarias100());
                    }
                    totalDias = totalDiasRol + empleadoTO.getEmpDiasTrabajados();
                    total = totalIngreso.add(bono).add(empleadoTO.getEmpSueldoIess()).add(empleadoTO.getEmpBonoFijo());
                    break;
                case 3:
                    for (RhRol item : listaRoles) {
                        totalDias += item.getRolDiasLaboradosReales();
                        total = total.add(item.getRolIngresos()).add(item.getRolBonos()).add(item.getRolBonoFijo())
                                .add(item.getRolHorasExtras()).add(item.getRolHorasExtras100()).add(item.getRolHorasExtrasExtraordinarias100())
                                .add(item.getRolSaldoHorasExtras50()).add(item.getRolSaldoHorasExtras100()).add(item.getRolSaldoHorasExtrasExtraordinarias100());
                    }
                    break;
                default:
                    break;
            }
        }
        sueldoDiarioPromedio = total.divide(new BigDecimal(totalDias), 9, RoundingMode.HALF_UP);
        BigDecimal promedio = new BigDecimal(diasPermiso).multiply(sueldoDiarioPromedio);
        BigDecimal valorFinal = promedio.multiply(new BigDecimal(0.75));
        promedioDiarioSubsidiado = UtilsValidacion.redondeoDecimalBigDecimal(valorFinal);
        return promedioDiarioSubsidiado;
    }

    @Override
    public Map<String, Object> obtenerDatosParaConsultaProvisiones(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        List<PrdListaSectorTO> sectores = sectorDao.getListaSector(empresa, false);
        List<SisPeriodo> periodos = periodoService.getListaPeriodo(empresa);
        RhProvisiones provision = rhProvisionesDao.obtener(RhProvisiones.class,
                empresa);

        respuesta.put("sectores", sectores);
        respuesta.put("periodos", periodos);
        respuesta.put("provision", provision);

        return respuesta;
    }

    @Override
    public Map<String, Object> obtenerDatosParaLiquidacion(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));

        boolean esContable = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("esContable"));
        Date fecha = sistemaWebServicio.getFechaActual();

        boolean isPeriodoAbierto = false;
        SisPeriodo sisPeriodo = null;

        List<RhComboFormaPagoTO> listaFormaPago = formaPagoService.getComboFormaPagoTO(empresa);
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);

        if (!esContable) {
            if (listaPeriodos != null) {
                for (SisPeriodo itemSisPeriodo : listaPeriodos) {
                    //Si la fecha está entre el 'desde' y 'hasta' del periodo, ese periodo es el que corresponde a nuestra fecha pasada como parámetro 
                    if (fecha.getTime() >= itemSisPeriodo.getPerDesde().getTime() && fecha.getTime() <= UtilsDate.dateCompleto(itemSisPeriodo.getPerHasta()).getTime()) {
                        sisPeriodo = itemSisPeriodo;
                    }
                }
                if (sisPeriodo == null || sisPeriodo.getPerCerrado()) {
                    isPeriodoAbierto = false;
                } else {
                    isPeriodoAbierto = true;
                }
            }
        }

        respuesta.put("listaFormaPago", listaFormaPago);
        respuesta.put("isPeriodoAbierto", isPeriodoAbierto);
        respuesta.put("fecha", fecha);
        return respuesta;
    }

    @Override
    public RhRol buscarRolSQL(Integer secuencial) throws Exception {
        return rolDao.buscarRolSQL(secuencial);
    }

    @Override
    public Map<String, Object> obtenerRol(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String idEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("idEmpleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        List<SisEmpresaNotificaciones> listadoConfNoticaciones = sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(empresa);
        ItemListaRolTO itemListaRolTO = obtenerItemListaRolTO(conContablePK, empresa, idEmpleado, sisInfoTO);
        if (itemListaRolTO != null) {
            List<RhListaRolSaldoEmpleadoDetalladoTO> detalle = getRhRolSaldoEmpleadoDetalladoTO(
                    empresa,
                    itemListaRolTO.getRhRol().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    itemListaRolTO.getRolDesdeTexto(),
                    itemListaRolTO.getRolHastaTexto());
            itemListaRolTO.setDetalle(detalle);
        }

        campos.put("listadoConfNoticaciones", listadoConfNoticaciones);
        campos.put("itemListaRolTO", itemListaRolTO);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerRolConsulta(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String idEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("idEmpleado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        List<SisEmpresaNotificaciones> listadoConfNoticaciones = sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(empresa);
        ItemListaRolTO respues = obtenerRolTO(conContablePK, empresa, idEmpleado, sisInfoTO);
        if (respues != null) {
            List<RhListaRolSaldoEmpleadoDetalladoTO> detalle = getRhRolSaldoEmpleadoDetalladoTO(
                    empresa,
                    respues.getRhRol().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    respues.getRolDesdeTexto(),
                    respues.getRolHastaTexto());
            respues.setDetalle(detalle);
        }
        campos.put("listadoConfNoticaciones", listadoConfNoticaciones);
        campos.put("itemListaRolTO", respues);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerVistaPreliminarRol(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        RhRol rol = UtilsJSON.jsonToObjeto(RhRol.class, map.get("rol"));
        String idEmpleado = UtilsJSON.jsonToObjeto(String.class, map.get("idEmpleado"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        List<SisEmpresaNotificaciones> listadoConfNoticaciones = sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(empresa);
        ItemListaRolTO respues = obtenerVistaPreliminarRol(rol, idEmpleado, empresa, sisInfoTO);
        if (respues != null) {
            List<RhListaRolSaldoEmpleadoDetalladoTO> detalle = getRhRolSaldoEmpleadoDetalladoTO(
                    empresa,
                    respues.getRhRol().getRhEmpleado().getRhEmpleadoPK().getEmpId(),
                    respues.getRolDesdeTexto(),
                    respues.getRolHastaTexto());
            respues.setDetalle(detalle);
        }
        campos.put("listadoConfNoticaciones", listadoConfNoticaciones);
        campos.put("itemListaRolTO", respues);

        return campos;
    }

}
