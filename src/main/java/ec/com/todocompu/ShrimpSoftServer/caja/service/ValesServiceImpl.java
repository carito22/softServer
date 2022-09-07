package ec.com.todocompu.ShrimpSoftServer.caja.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.caja.dao.ValesDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCaja;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCuadreCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptosComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajVales;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class ValesServiceImpl implements ValesService {

    @Autowired
    private ValesDao valesDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ValesConceptosService valesConceptosService;
    @Autowired
    private VentasMotivoService ventasMotivoService;
    @Autowired
    private CajaService cajaService;
    private boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private BigDecimal cero = new BigDecimal("0.00");

    @Override
    public String accionCajaValesTO(CajCajaValesTO cajCajaValesTO, String accion, SisInfoTO sisInfoTO) throws Exception {
        boolean periodoCerrado = false;
        comprobar = false;
        mensaje = "F¬";
        if (!UtilsValidacion.isFechaSuperior(cajCajaValesTO.getValeFecha(), "yyyy-MM-dd")) {
            ///// BUSCANDO vale
            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(cajCajaValesTO.getValeEmpresa());// invVentasTO.getVtaEmpresa()
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(cajCajaValesTO.getValeFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(cajCajaValesTO.getValeFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    cajCajaValesTO.setValePeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }
            if (comprobar) {
                if (!periodoCerrado) {
                    susClave = cajCajaValesTO.getValeEmpresa() + cajCajaValesTO.getValePeriodo()
                            + cajCajaValesTO.getValeMotivo() + cajCajaValesTO.getValeNumero();
                    if (accion.equals("I")) {
                        susDetalle = "Se insertó el vale de caja " + cajCajaValesTO.getValeEmpresa()
                                + cajCajaValesTO.getValePeriodo() + cajCajaValesTO.getValeMotivo()
                                + cajCajaValesTO.getValeNumero();
                        susSuceso = "INSERT";
                    }
                    if (accion.equals("M")) {
                        susDetalle = "Se anuló el vale de caja " + cajCajaValesTO.getValeEmpresa()
                                + cajCajaValesTO.getValePeriodo() + cajCajaValesTO.getValeMotivo()
                                + cajCajaValesTO.getValeNumero();
                        susSuceso = "UPDATE";
                    }
                    if (accion.equals("E")) {
                        susDetalle = "Se eliminó el vale de caja " + cajCajaValesTO.getValeEmpresa()
                                + cajCajaValesTO.getValePeriodo() + cajCajaValesTO.getValeMotivo()
                                + cajCajaValesTO.getValeNumero();
                        susSuceso = "ANULO";
                    }
                    susTabla = "caja.caj_vales";
                    /**
                     * **********CREAMOS LA ENTIDA****************
                     */
                    CajVales cajVales = ConversionesCaja.convertirCajCajaValesTO_CajVales(cajCajaValesTO);
                    CajValesNumeracion cajValesNumeracion = new CajValesNumeracion(
                            new CajValesNumeracionPK(cajCajaValesTO.getValeEmpresa(),
                                    cajCajaValesTO.getValePeriodo(), cajCajaValesTO.getValeMotivo()));
                    if (accion.equals("I")) {
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);
                        comprobar = valesDao.accionCajaValesTO(cajVales, sisSuceso, cajValesNumeracion, accion);
                    }
                    if (accion.equals("M")) {
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);
                        comprobar = valesDao.accionCajaValesTO(cajVales, sisSuceso, cajValesNumeracion, accion);
                    }
                    if (comprobar) {
                        if (accion.equals("I")) {
                            mensaje = "T<html>El vale de caja, se ha guardado correctamente con la siguiente información:"
                                    + "<br>Número: <font size = 5>" + cajVales.getCajValesPK().getValeNumero()
                                    + "</font>" + "<br>Periodo: <font size = 5>"
                                    + cajVales.getCajValesPK().getValePeriodo() + "</font>"
                                    + "<br>Beneficiario: <font size = 5>" + cajVales.getValeBeneficiario()
                                    + "</font>" + "<br><font size = 6> Valor: " + cajVales.getValeValor().add(cero)
                                    + "</font>" + "<br>Fecha: <font size = 5>" + UtilsValidacion.fechaSistema()
                                    + "</font></html>.";
                        }
                        if (accion.equals("M")) {
                            mensaje = "T<html>El vale de caja, se ha anulado correctamente con la siguiente información:<br><br>Número: <font size = 5>"
                                    + cajVales.getCajValesPK().getValeNumero()
                                    + "</font>.<br>Beneficiario: <font size = 5>" + cajVales.getValeBeneficiario()
                                    + "</font>.</html>";
                        }
                        if (accion.equals("E")) {
                            mensaje = "T<html>El vale de caja, se ha eliminado correctamente con la siguiente información:<br><br>Número: <font size = 5>"
                                    + cajVales.getCajValesPK().getValeNumero()
                                    + "</font>.<br>Beneficiario: <font size = 5>" + cajVales.getValeBeneficiario()
                                    + "</font>.</html>";
                        }

                    } else {
                        mensaje = "FHubo un error al guardar el Vale de Caja...\nIntente de nuevo o contacte con el administrador.";
                    }
                } else {
                    mensaje = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                }
            } else {
                mensaje = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            mensaje = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        return mensaje;
    }

    @Override
    public MensajeTO accionCajaVales(CajCajaValesTO cajCajaValesTO, String accion, SisInfoTO sisInfoTO) throws Exception {
        boolean periodoCerrado = false;
        MensajeTO respuesta = new MensajeTO();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean comprobar = false;
        String mensaje = "F¬";
        CajVales cajVales = null;
        if (!UtilsValidacion.isFechaSuperior(cajCajaValesTO.getValeFecha(), "yyyy-MM-dd")) {
            ///// BUSCANDO vale
            List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
            listaSisPeriodoTO = periodoService.getListaPeriodoTO(cajCajaValesTO.getValeEmpresa());// invVentasTO.getVtaEmpresa()
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(cajCajaValesTO.getValeFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(cajCajaValesTO.getValeFecha(), "yyyy-MM-dd").getTime() <= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                    comprobar = true;
                    cajCajaValesTO.setValePeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }
            if (comprobar) {
                if (!periodoCerrado) {
                    susClave = cajCajaValesTO.getValeEmpresa() + cajCajaValesTO.getValePeriodo()
                            + cajCajaValesTO.getValeMotivo() + cajCajaValesTO.getValeNumero();
                    if (accion.equals("I")) {
                        susDetalle = "Se insertó el vale de caja " + cajCajaValesTO.getValeEmpresa()
                                + cajCajaValesTO.getValePeriodo() + cajCajaValesTO.getValeMotivo()
                                + cajCajaValesTO.getValeNumero();
                        susSuceso = "INSERT";
                    }
                    if (accion.equals("M")) {
                        susDetalle = "Se anuló el vale de caja " + cajCajaValesTO.getValeEmpresa()
                                + cajCajaValesTO.getValePeriodo() + cajCajaValesTO.getValeMotivo()
                                + cajCajaValesTO.getValeNumero();
                        susSuceso = "UPDATE";
                    }
                    if (accion.equals("E")) {
                        susDetalle = "Se eliminó el vale de caja " + cajCajaValesTO.getValeEmpresa()
                                + cajCajaValesTO.getValePeriodo() + cajCajaValesTO.getValeMotivo()
                                + cajCajaValesTO.getValeNumero();
                        susSuceso = "ANULO";
                    }
                    susTabla = "caja.caj_vales";
                    /**
                     * **********CREAMOS LA ENTIDA****************
                     */
                    cajVales = ConversionesCaja.convertirCajCajaValesTO_CajVales(cajCajaValesTO);
                    CajValesNumeracion cajValesNumeracion = new CajValesNumeracion(
                            new CajValesNumeracionPK(cajCajaValesTO.getValeEmpresa(),
                                    cajCajaValesTO.getValePeriodo(), cajCajaValesTO.getValeMotivo()));
                    if (accion.equals("I")) {
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);
                        comprobar = valesDao.accionCajaValesTO(cajVales, sisSuceso, cajValesNumeracion, accion);
                    }
                    if (accion.equals("M")) {
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                sisInfoTO);
                        comprobar = valesDao.accionCajaValesTO(cajVales, sisSuceso, cajValesNumeracion, accion);
                    }
                    if (comprobar) {
                        if (accion.equals("I")) {
                            mensaje = "T<html>El vale de caja, se ha guardado correctamente con la siguiente información:"
                                    + "<br>Número: <font size = 5>" + cajVales.getCajValesPK().getValeNumero()
                                    + "</font>" + "<br>Periodo: <font size = 5>"
                                    + cajVales.getCajValesPK().getValePeriodo() + "</font>"
                                    + "<br>Beneficiario: <font size = 5>" + cajVales.getValeBeneficiario()
                                    + "</font>" + "<br><font size = 6> Valor: " + cajVales.getValeValor().add(cero)
                                    + "</font>" + "<br>Fecha: <font size = 5>" + UtilsValidacion.fechaSistema()
                                    + "</font></html>.";
                        }
                        if (accion.equals("M")) {
                            mensaje = "T<html>El vale de caja, se ha anulado correctamente con la siguiente información:<br><br>Número: <font size = 5>"
                                    + cajVales.getCajValesPK().getValeNumero()
                                    + "</font>.<br>Beneficiario: <font size = 5>" + cajVales.getValeBeneficiario()
                                    + "</font>.</html>";
                        }
                        if (accion.equals("E")) {
                            mensaje = "T<html>El vale de caja, se ha eliminado correctamente con la siguiente información:<br><br>Número: <font size = 5>"
                                    + cajVales.getCajValesPK().getValeNumero()
                                    + "</font>.<br>Beneficiario: <font size = 5>" + cajVales.getValeBeneficiario()
                                    + "</font>.</html>";
                        }

                    } else {
                        mensaje = "FHubo un error al guardar el Vale de Caja...\nIntente de nuevo o contacte con el administrador.";
                    }
                } else {
                    mensaje = "F<html>El periodo que corresponde a la fecha que ingresó se encuentra cerrado...</html>";
                }
            } else {
                mensaje = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
            }
        } else {
            mensaje = "F<html>La fecha que ingresó es superior a la fecha actual del servidor.<br>Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy") + "</html>";
        }
        respuesta.setMensaje(mensaje);
        map.put("pk", cajVales.getCajValesPK());
        respuesta.setMap(map);

        return respuesta;
    }

    @Override
    public List<CajaValesTO> getListadoCajValesTO(String empresa, String motCodigo, String fechaDesde,
            String fechaHasta) throws Exception {
        return valesDao.getListadoCajValesTO(empresa, motCodigo, fechaDesde, fechaHasta);
    }

    @Override
    public CajCajaValesTO getCajCajaValesTO(String empresa, String perCodigo, String motCodigo, String valeNumero)
            throws Exception {
        return valesDao.getCajCajaValesTO(empresa, perCodigo, motCodigo, valeNumero);
    }

    @Override
    public List<CajCuadreCajaTO> getCajCuadreCajaTOs(String empresa, String codigoMotivo, String fechaDesde,
            String fechaHasta) throws Exception {
        return valesDao.getCajCuadreCajaTOs(empresa, codigoMotivo, fechaDesde, fechaHasta);
    }

    @Override
    public Map<String, Object> obtenerDatosParaValeCaja(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
        String motCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("motCodigo"));
        String valeNumero = UtilsJSON.jsonToObjeto(String.class, map.get("valeNumero"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));

        CajCajaValesTO cajCajaValesTO = null;
        List<CajValesConceptosComboTO> listaCajValesConceptosComboTO = valesConceptosService.getCajValesConceptosComboTOs(empresa);
        if (perCodigo != null && perCodigo != null && perCodigo != null) {
            cajCajaValesTO = getCajCajaValesTO(empresa, perCodigo, motCodigo, valeNumero);
        }
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha((cajCajaValesTO != null ? UtilsDate.fechaFormatoDate(cajCajaValesTO.getValeFecha()) : fechaActual), empresa);
        List<InvVentaMotivoTO> listaInvVentaMotivoTO = ventasMotivoService.getListaInvVentasMotivoTO(empresa, true, null);

        if (usuario != null) {
            CajCajaTO caja = cajaService.getCajCajaTO(empresa, usuario);
            campos.put("caja", caja);
        }

        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("fechaActual", fechaActual);
        campos.put("cajCajaValesTO", cajCajaValesTO);
        campos.put("listaCajValesConceptosComboTO", listaCajValesConceptosComboTO);
        campos.put("listaInvVentaMotivoTO", listaInvVentaMotivoTO);

        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosParaValeCaja(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));

        List<InvVentaMotivoTO> listaInvVentaMotivoTO = ventasMotivoService.getListaInvVentasMotivoTO(empresa, true, null);
        if (usuario != null) {
            CajCajaTO caja = cajaService.getCajCajaTO(empresa, usuario);
            campos.put("caja", caja);
        }
        campos.put("listaInvVentaMotivoTO", listaInvVentaMotivoTO);

        return campos;
    }

}
