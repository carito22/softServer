package ec.com.todocompu.ShrimpSoftServer.banco.service;

import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCar;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversado;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaPostfechadoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChequePostFechadoServiceImpl implements ChequePostfechadoService {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private GenericoDao<ConContable, ConContablePK> contableCriteriaDao;
    @Autowired
    private GenericoDao<CarCobrosDetalleForma, Integer> formaCriteriaDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private BanChequeMotivoReversadoService banChequeMotivoReversadoService;
    @Autowired
    private CobrosAnticiposDao cobrosAnticiposDao;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<CarCobrosDetalleFormaPostfechadoTO> getListaChequesPostfechados(String empresa, String desde, String hasta) throws Exception {
        desde = desde == null || desde.equals("") ? null : "'" + desde + "'";
        hasta = hasta == null || hasta.equals("") ? null : "'" + hasta + "'";
        String sql = "SELECT * FROM banco.fun_cheques_en_custodia('"
                + empresa + "', "
                + desde + ", "
                + hasta + ")";

        List<CarCobrosDetalleFormaPostfechadoTO> cheques = genericSQLDao.obtenerPorSql(sql, CarCobrosDetalleFormaPostfechadoTO.class);
        return cheques;
    }

    public List<CarCobrosDetalleFormaPostfechadoTO> getListaChequesPostfechadosAtnticipos(String empresa, String desde, String hasta) throws Exception {
        desde = desde == null || desde.equals("") ? null : "'" + desde + "'";
        hasta = hasta == null || hasta.equals("") ? null : "'" + hasta + "'";
        String sql = "SELECT * FROM banco.fun_cheques_en_custodia_anticipos('" + empresa + "', "
                + desde + ", "
                + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, CarCobrosDetalleFormaPostfechadoTO.class);
    }

    @Override
    public Map<String, Object> obtenerDatosParaChequesPostfechados(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));

        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        List<BanComboBancoTO> bancos = cuentaService.getBanComboBancoTO(empresa);
        List<CarCobrosDetalleFormaPostfechadoTO> cheques = getListaChequesPostfechados(empresa, desde, hasta);
        List<CarCobrosDetalleFormaPostfechadoTO> chequesAnticipos = getListaChequesPostfechadosAtnticipos(empresa, desde, hasta);

        if (cheques == null) {
            cheques = new ArrayList<>();
        }

        if (chequesAnticipos != null && chequesAnticipos.size() > 0) {
            for (CarCobrosDetalleFormaPostfechadoTO entry : chequesAnticipos) {
                entry.setTipo("ANT");//anticipo
                cheques.add(entry);
            }
        }

        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("fechaActual", fechaActual);
        campos.put("bancos", bancos);
        campos.put("cheques", cheques);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaChequesReversar(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        List<BanComboBancoTO> listadoBancoCuenta = cuentaService.getBanComboBancoTO(empresa);
        List<PrdListaSectorTO> listadoSectores = sectorService.getListaSectorTO(empresa, false);
        List<BanChequeMotivoReversado> listadoMotivos = banChequeMotivoReversadoService.getListaMotivoReversadoCheque(empresa);
        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("fechaActual", fechaActual);
        campos.put("listadoBancoCuenta", listadoBancoCuenta);
        campos.put("listadoSectores", listadoSectores);
        campos.put("listadoMotivos", listadoMotivos);
        return campos;
    }

    @Override
    public MensajeTO insertarContableDeposito(String observacionesContable, List<CarCobrosDetalleFormaPostfechadoTO> listaPosfechados, String fecha, String cuenta, SisInfoTO sisInfoTO) throws Exception {
        String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
        ConContable conContable = new ConContable(sisInfoTO.getEmpresa(), "", "C-DEP", "");
        conContable.setConObservaciones(observacionesContable);
        conContable.setConFecha(UtilsDate.fechaFormatoDate(fecha, "yyyy-MM-dd"));
        conContable.setConPendiente(true);
        conContable.setConCodigo(secuencia);
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();
        if ((retorno = periodoService.validarPeriodo(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa())) != null) {
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
            sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
            if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
                conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
                conContable.setConBloqueado(false);
                conContable.setConReversado(false);
                conContable.setConAnulado(false);
                conContable.setConGenerado(true);
                conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
                conContable.setConReferencia("banco.cheque_posfechado");
                conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                conContable.setUsrFechaInserta(UtilsDate.timestamp());
                conContable.setConConcepto("CHEQUES POSFECHADO");
            }
            susSuceso = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
            susTabla = "contabilidad.con_contable";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
            sisSuceso.setSusDetalle("Contable de depósito por " + conContable.getConObservaciones());
            mensaje = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "ingresó" : "modificó";
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
            conContable = contableCriteriaDao.insertar(conContable);
            sucesoDao.insertar(sisSuceso);
            if (conContable == null) {
                retorno = "FHubo un error al guardar el contable de depósito.\nIntente de nuevo o contacte con el administrador";
            } else {
                List<CarCobrosDetalleFormaPostfechadoTO> listaAnticipos = new ArrayList<>();
                List<CarCobrosDetalleFormaPostfechadoTO> listaCobros = new ArrayList<>();
                //evaluar si es anticipo o cobros
                for (CarCobrosDetalleFormaPostfechadoTO entry : listaPosfechados) {
                    if (entry.getTipo() != null && entry.getTipo().equals("ANT")) {
                        listaAnticipos.add(entry);
                    } else {
                        listaCobros.add(entry);
                    }
                }

                //actualizar campos de contable anticipos
                if (listaAnticipos.size() > 0) {
                    actualizarContableAnticipos(listaAnticipos, conContable, true, cuenta, sisInfoTO);
                }
                //actualizar detalle de forma de cobro
                if (listaCobros.size() > 0) {
                    actualizarFomasCobrosDetalle(listaCobros, conContable, sisInfoTO, cuenta);
                }

                retorno = "T<html>Se " + mensaje + " el contable de depósito con la siguiente información:<br><br>"
                        + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Tipo: <font size = 5>"
                        + conContable.getConContablePK().getConTipo() + "</font>.<br> Número: <font size = 5>"
                        + conContable.getConContablePK().getConNumero() + "</font>.<br>"
                        + conContable.getConContablePK().getConNumero() + ", "
                        + periodo.getSisPeriodoPK().getPerCodigo() + "</html>";
                mensajeTO.setFechaCreacion(conContable.getUsrFechaInserta().toString());
                mensajeTO.getMap().put("conContable", conContable);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public boolean actualizarContableAnticipos(List<CarCobrosDetalleFormaPostfechadoTO> listaPosfechadosAnt, ConContable contable, boolean desdeContableDeposito, String cuenta, SisInfoTO sisInfoTO) throws Exception {
        for (int i = 0; i < listaPosfechadosAnt.size(); i++) {
            if (listaPosfechadosAnt.get(i).getPk() != null) {
                String empresa = listaPosfechadosAnt.get(i).getPk().split("\\|")[0];
                CarCobrosAnticiposPK pk = new CarCobrosAnticiposPK(
                        listaPosfechadosAnt.get(i).getPk().split("\\|")[0],
                        listaPosfechadosAnt.get(i).getPk().split("\\|")[1],
                        listaPosfechadosAnt.get(i).getPk().split("\\|")[2],
                        listaPosfechadosAnt.get(i).getPk().split("\\|")[3]);
                CarCobrosAnticipos carCobrosAnticipos = cobrosAnticiposDao.obtenerPorId(CarCobrosAnticipos.class, pk);
                carCobrosAnticipos.setConContableDeposito(contable);
                if (contable != null) {
                    carCobrosAnticipos.setConCuentas(new ConCuentas(new ConCuentasPK(empresa, cuenta)));
                } else {
                    carCobrosAnticipos.setConCuentas(null);
                }

                cobrosAnticiposDao.actualizar(carCobrosAnticipos);
                susSuceso = "UPDATE";
                susTabla = "cartera.car_cobros_anticipos";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                sisSuceso.setSusClave(
                        carCobrosAnticipos.getCarCobrosAnticiposPK().getAntTipo() + "|"
                        + carCobrosAnticipos.getCarCobrosAnticiposPK().getAntPeriodo() + "|"
                        + carCobrosAnticipos.getCarCobrosAnticiposPK().getAntNumero()
                );
                sisSuceso.setSusDetalle("se modificó el anticipo: " + sisSuceso.getSusClave() + (desdeContableDeposito ? " desde contable deposito" : ""));
                sucesoDao.insertar(sisSuceso);
            }
        }

        return true;
    }

    private void actualizarFomasCobrosDetalle(List<CarCobrosDetalleFormaPostfechadoTO> listaPosfechados, ConContable contable, SisInfoTO sisInfoTO, String cuenta) {
        for (int i = 0; i < listaPosfechados.size(); i++) {
            CarCobrosDetalleForma detalle = formaCriteriaDao.obtener(CarCobrosDetalleForma.class, listaPosfechados.get(i).getDetSecuencial());
            detalle.setDepEmpresa(contable.getConContablePK().getConEmpresa());
            detalle.setDepNumero(contable.getConContablePK().getConNumero());
            detalle.setDepPeriodo(contable.getConContablePK().getConPeriodo());
            detalle.setDepTipo(contable.getConContablePK().getConTipo());
            detalle.setCtaCodigo(cuenta);
            detalle.setCtaEmpresa(contable.getConContablePK().getConEmpresa());
            formaCriteriaDao.actualizar(detalle);
            susSuceso = "UPDATE";
            susTabla = "cartera.car_cobros_detalle_forma";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisSuceso.setSusClave(listaPosfechados.get(i).getDetSecuencial() + "");
            sisSuceso.setSusDetalle("Banco " + listaPosfechados.get(i).getBanNombre());
            sucesoDao.insertar(sisSuceso);
        }
    }

    @Override
    public boolean actualizarFechaVencimiento(Integer secuencial, Date fechaVencimiento, String banco, SisInfoTO sisInfoTO) throws Exception {
        CarCobrosDetalleForma detalle = formaCriteriaDao.obtener(CarCobrosDetalleForma.class, secuencial);
        if (detalle != null) {
            susSuceso = "UPDATE";
            susTabla = "cartera.car_cobros_detalle_forma";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisSuceso.setSusClave(detalle.getDetSecuencial() + "");
            sisSuceso.setSusDetalle("Se modificó la fecha de vencimiento del cheque postfechado con referencia "
                    + detalle.getDetReferencia() + " del Banco " + detalle.getDetBanco() + " - " + banco
                    + ". Fecha anterior: " + detalle.getDetFechaVencimiento() + ". Fecha actual: " + UtilsDate.fechaFormatoString(fechaVencimiento, "yyyy-MM-dd"));
            sucesoDao.insertar(sisSuceso);
            detalle.setDetFechaVencimiento(fechaVencimiento);
            formaCriteriaDao.actualizar(detalle);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CarFunCobrosTO obtenerCobroAPartirDeChequePostfechado(Integer secuencial) throws Exception {
        CarCobrosDetalleForma detalle = formaCriteriaDao.obtener(CarCobrosDetalleForma.class, secuencial);
        if (detalle != null) {
            CarFunCobrosTO carFunCobrosTO = ConversionesCar.convertirCarCobros_CarFunCobrosTO(detalle.getCarCobros());
            return carFunCobrosTO;
        } else {
            return null;
        }
    }

}
