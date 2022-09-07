package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.FormaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhContableTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhSalarioDignoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhSalarioDigno;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;
import java.util.Map;
import org.olap4j.impl.ArrayMap;

@Service
public class SalarioDignoServiceImpl implements SalarioDignoService {

    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private FormaPagoDao formaPagoDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private TipoDao tipoDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private FormaPagoService formaPagoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    private Boolean comprobar = false;
    private String mensaje = "";
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    private boolean insertarRhSalarioDigno(RhSalarioDigno rhSalarioDigno, ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, ConNumeracion conNumeracion, SisInfoTO sisInfoTO) throws Exception {
        return contableDao.insertarTransaccionContable(conContable, listaConDetalle, sisSuceso, conNumeracion, null,
                rhSalarioDigno, null, null, null, null, null, null, null, null, null, null, null, null, null,
                sisInfoTO);
    }

    @Override
    public RhContableTO insertarRhSalarioDignoContable(RhSalarioDignoTO rhSalarioDignoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        RhContableTO rhContableTO = new RhContableTO();
        RhCtaFormaPagoTO rhCtaFormaPagoTO = new RhCtaFormaPagoTO();
        String tipDetalle = "C-PSD";
        mensaje = "F";
        String ctaRhFormasPago = null;
        String ctaRhSalarioDigno = "";
        BigDecimal valor = cero;
        comprobar = false;
        // periodo
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList(1);
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(rhSalarioDignoTO.getEmpEmpresa());
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(rhSalarioDignoTO.getConFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(rhSalarioDignoTO.getConFecha(), "yyyy-MM-dd")
                            .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                            .getTime()
                    && sisListaPeriodoTO.getPerCerrado() == false) {
                comprobar = true;
                rhSalarioDignoTO.setConPeriodo(sisListaPeriodoTO.getPerCodigo());
                break;
            }
        }
        if (comprobar) {
            comprobar = false;
            if (tipoDao.buscarTipoContable(rhSalarioDignoTO.getEmpEmpresa(), tipDetalle)) {
                // llenar contable
                ConContableTO conContableTO = new ConContableTO();
                conContableTO.setEmpCodigo(rhSalarioDignoTO.getEmpEmpresa());
                conContableTO.setPerCodigo(rhSalarioDignoTO.getConPeriodo());
                conContableTO.setTipCodigo(tipDetalle);
                rhSalarioDignoTO.setConTipo(tipDetalle);
                conContableTO.setConFecha(rhSalarioDignoTO.getConFecha());
                conContableTO.setConPendiente(false);
                conContableTO.setConBloqueado(false);
                conContableTO.setConAnulado(false);
                conContableTO.setConGenerado(false); // deberia ser true,
                // para elminar
                // desde el modulo
                // de contabilidad
                conContableTO.setConConcepto(rhSalarioDignoTO.getEmpApellidosNombres());// nombre
                // empleado
                conContableTO.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");//
                conContableTO.setConObservaciones(rhSalarioDignoTO.getConObservaciones());
                conContableTO.setUsrInsertaContable(rhSalarioDignoTO.getUsrCodigo());
                conContableTO.setUsrFechaInsertaContable(UtilsValidacion.fechaSistema());
                ///// CREANDO UN SUCESO
                susClave = rhSalarioDignoTO.getConPeriodo() + " " + rhSalarioDignoTO.getConTipo() + " "
                        + rhSalarioDignoTO.getConNumero();
                susSuceso = "INSERT";
                susTabla = "recursoshumanos.rh_salario_digno";
                ////// CREANDO NUMERACION
                ConNumeracion conNumeracion = new ConNumeracion(new ConNumeracionPK(conContableTO.getEmpCodigo(),
                        conContableTO.getPerCodigo(), conContableTO.getTipCodigo()));
                ////// CREANDO CONTABLE
                ConContable conContable = ConversionesContabilidad
                        .convertirConContableTO_ConContable(conContableTO);
                ////// CREANDO SALARIO DIGNO
                RhSalarioDigno rhSalarioDigno = ConversionesRRHH
                        .convertirRhSalarioDignoTO_RhSalarioDigno(rhSalarioDignoTO);
                rhSalarioDigno.setSdigAuxiliar(false);
                ////// CONVIRTIENDO A ENTIDAD EL DETALLE
                List<ConDetalle> listaConDetalle = new ArrayList(0);
                ConDetalle conDetalle = null;
                /////////////////////////////////////////////////////////////////
                ctaRhSalarioDigno = empleadoDao.buscarCtaRhSalarioDigno(rhSalarioDignoTO.getEmpEmpresa(),
                        rhSalarioDignoTO.getEmpId());
                ConCuentas conCuentasSalariodigno = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(
                        rhSalarioDignoTO.getEmpEmpresa(), (ctaRhSalarioDigno == null) ? "" : ctaRhSalarioDigno));
                /////////////////////////////////////////////////////////////////
                rhCtaFormaPagoTO = formaPagoDao.buscarCtaRhFormaPago(rhSalarioDignoTO.getEmpEmpresa(),
                        rhSalarioDignoTO.getSdigFormaPago());
                ctaRhFormasPago = rhCtaFormaPagoTO.getCtaCodigo();
                ConCuentas conCuentasFormasPagos = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(
                        rhSalarioDignoTO.getEmpEmpresa(), (ctaRhFormasPago == null) ? "" : ctaRhFormasPago));

                for (int i = 0; i < 2; i++) {
                    conDetalle = new ConDetalle();
                    conDetalle.setConContable(conContable);
                    conDetalle.setDetSecuencia(0L);
                    conDetalle.setDetDocumento("");
                    conDetalle.setPrdPiscina(null);
                    conDetalle.setDetValor(rhSalarioDignoTO.getSdigValor());
                    conDetalle.setDetGenerado(true);
                    if (i == 0) {
                        conDetalle.setPrdSector(
                                new PrdSector(sisInfoTO.getEmpresa(), rhCtaFormaPagoTO.getSecCodigo()));
                        conDetalle.setConCuentas(conCuentasSalariodigno);// categoria
                        conDetalle.setDetDebitoCredito('D');
                        conDetalle.setDetReferencia("SALDIG");//
                        conDetalle.setDetOrden(i + 1);//
                        listaConDetalle.add(i, conDetalle);
                    }
                    if (i == 1) {
                        if (rhCtaFormaPagoTO.getCtaCodigo() != null) {
                            conDetalle.setPrdSector(
                                    new PrdSector(sisInfoTO.getEmpresa(), rhCtaFormaPagoTO.getSecCodigo()));
                            conDetalle.setConCuentas(conCuentasFormasPagos);// forma
                            // pago
                            conDetalle.setDetDebitoCredito('C');
                            conDetalle.setDetDocumento(rhSalarioDignoTO.getConDetDocumento());
                            conDetalle.setDetReferencia("FP");//
                            conDetalle.setDetOrden(i + 1);//
                            valor = valor.add(conDetalle.getDetValor());
                            listaConDetalle.add(i, conDetalle);
                        }
                    }
                    conDetalle = null;
                }

                ctaRhSalarioDigno = ctaRhSalarioDigno == null ? "" : ctaRhSalarioDigno;
                ctaRhFormasPago = ctaRhFormasPago == null ? "" : ctaRhFormasPago;
                comprobar = false;
                if (!ctaRhSalarioDigno.isEmpty() && !ctaRhFormasPago.isEmpty()) {// revisar
                    // si
                    // estan
                    // llenos
                    susDetalle = "Salario Digno a "
                            + empleadoDao.getRhEmpleadoApellidosNombres(
                                    rhSalarioDigno.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                                    rhSalarioDigno.getRhEmpleado().getRhEmpleadoPK().getEmpId())
                            + " por $" + valor.toPlainString();
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    comprobar = insertarRhSalarioDigno(rhSalarioDigno, conContable, listaConDetalle, sisSuceso,
                            conNumeracion, sisInfoTO);
                } else if (ctaRhSalarioDigno.isEmpty()) {
                    mensaje = "FNo se encuentra la cuenta contable de Salario Digno...";
                }
                if (ctaRhFormasPago.isEmpty()) {
                    mensaje = "FNo se encuentra la cuenta contable de forma de pago...";
                }
                if (comprobar) {
                    SisPeriodo sisPeriodo = periodoService.buscarPeriodo(conContableTO.getEmpCodigo(),
                            conContable.getConContablePK().getConPeriodo());

                    ConTipo conTipo = tipoDao.obtenerPorId(ConTipo.class, new ConTipoPK(
                            conContableTO.getEmpCodigo(), conContable.getConContablePK().getConTipo()));

                    mensaje = "T<html>Se ingresó el contable con la siguiente información:<br><br>"
                            + "Periodo: <font size = 5>" + sisPeriodo.getPerDetalle()
                            + "</font>.<br> Tipo: <font size = 5>" + conTipo.getTipDetalle()
                            + "</font>.<br> Número: <font size = 5>" + conContable.getConContablePK().getConNumero()
                            + "</font>.</html>";

                    rhContableTO.setPerCodigo(sisPeriodo.getSisPeriodoPK().getPerCodigo());
                    rhContableTO.setTipCodigo(conTipo.getConTipoPK().getTipCodigo());
                    rhContableTO.setConNumero(conContable.getConContablePK().getConNumero());
                }
            } else {
                mensaje = "FNo se encuentra tipo de contable...";
            }
        } else {
            mensaje = "FNo se encuentra ningún periodo para la fecha que ingresó\nNo esta creado o esta cerrado...";
        }

        rhContableTO.setMensaje(mensaje);
        return rhContableTO;
    }

    @Override
    public Map<String, Object> obtenerDatosSalarioDigno(Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new ArrayMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<RhComboFormaPagoTO> listaFormaPago = formaPagoService.getComboFormaPagoTO(empresa);
        Date fechaActual = sistemaWebServicio.getFechaActual();

        respuesta.put("listaFormaPago", listaFormaPago);
        respuesta.put("fechaActual", fechaActual);

        return respuesta;
    }

}
