package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.BancoCuentaDao;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.ChequeDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.TipoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.DetalleService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.EmpresaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.NumberToLetterConverter;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequePreavisadoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanDetalleChequesPosfechadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoEntregadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoRevisadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesNoImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ChequeNoImpresoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;

@Service
public class ChequeServiceImpl implements ChequeService {

    @Autowired
    private ChequeDao chequeDao;

    @Autowired
    private BancoCuentaDao cuentaDao;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private DetalleService detalleService;

    @Autowired
    private TipoDao tipoDao;

    @Autowired
    private ContableDao contableDao;

    @Autowired
    private DetalleDao detalleDao;

    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private BancoService bancoService;

    private boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<ListaBanChequesNumeracionTO> getListaChequesNumeracionTO(String empresa) throws Exception {
        return chequeDao.getListaChequesNumeracionTO(empresa);
    }

    @Override
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosTO(String empresa, String cuentaBancaria)
            throws Exception {
        return chequeDao.getListaChequesNoImpresosTO(empresa, cuentaBancaria);
    }

    @Override
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosWebTO(String empresa, String cuentaBancaria, String modulo)
            throws Exception {
        return chequeDao.getListaChequesNoImpresosWebTO(empresa, cuentaBancaria, modulo);
    }

    @Override
    public String reutilizarCheque(Long detSecuencia, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        BanCheque banChequeAux = chequeDao.obtenerPorId(BanCheque.class, detSecuencia);
        if (banChequeAux != null) {
            banChequeAux.setChqImpreso(false);
            //det_generado = false y det_pendiente = true de cabecera e item de detalle con el secuencial
            ConDetalle conDetalleAux = detalleDao.obtenerPorId(ConDetalle.class, detSecuencia);
            ConDetalle conDetalle = null;
            if (conDetalleAux != null) {
                //cabecera contable
                ConContable conContable = conDetalleAux.getConContable();
                //suceso cheque
                susClave = "" + detSecuencia;
                susSuceso = "UPDATE";
                susTabla = "banco.ban_cheque";
                susDetalle = "Se reutilizó el cheque con secuencial " + detSecuencia;
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                SisSuceso sisSucesoContable = null;
                //ACTUALIZAR CHEQUE Y CONTABLE
                if (conContable.getConReferencia() == null || conContable.getConReferencia().equals("")) {
                    conContable.setConGenerado(false);
                    //detalle contable
                    conDetalle = conDetalleAux;
                    conDetalle.setDetGenerado(false);
                    conDetalle.setConContable(conContable);
                    //actualizar suceso cheque
                    susDetalle = "Se reutilizó el cheque con secuencial " + detSecuencia + ", y se modificó el contable: "
                            + conContable.getConContablePK().getConTipo() + "|"
                            + conContable.getConContablePK().getConPeriodo() + "|"
                            + conContable.getConContablePK().getConNumero();
                    sisSuceso.setSusDetalle(susDetalle);
                    //suceso contable contabilidad.con_contable
                    String claveContable = conContable.getConContablePK().getConTipo() + "|"
                            + conContable.getConContablePK().getConPeriodo() + "|"
                            + conContable.getConContablePK().getConNumero();
                    String detalleContable = "se modificó el contable: "
                            + conContable.getConContablePK().getConTipo() + "|"
                            + conContable.getConContablePK().getConPeriodo() + "|"
                            + conContable.getConContablePK().getConNumero() + " despúes de reutilizar el cheque con secuencial " + detSecuencia;
                    sisSucesoContable = Suceso.crearSisSuceso("contabilidad.con_contable", claveContable, "UPDATE", detalleContable, sisInfoTO);
                }

                if (chequeDao.reutilizarCheque(banChequeAux, conDetalle, sisSucesoContable, sisSuceso)) {
                    retorno = "T" + susDetalle;
                } else {
                    retorno = "FHubo un error al guardar los datos del cheque";
                }
            } else {
                retorno = "FNo se encontró el contable";
            }
        } else {
            retorno = "FNo se encontró el cheque";
        }

        return retorno;
    }

    @Override
    public boolean isExisteChequeAimprimir(String empresa, String cuentaContable,
            String numeroCheque,
            Long detSecuencia) throws Exception {
        return chequeDao.isExisteChequeAimprimir(empresa, cuentaContable, numeroCheque, detSecuencia);
    }

    @Override
    public boolean isExisteCheque(String empresa, String cuentaContable,
            String numeroCheque) throws Exception {
        return chequeDao.isExisteCheque(empresa, cuentaContable, numeroCheque);
    }

    @Override
    public String insertarBanChequeTO(BanChequeTO banChequeTO, String usrInserta,
            String numeroCheque, String empresa,
            SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        String retorno = "";
        ConDetalle conDetalleAux = detalleDao.obtenerPorIdEvict(ConDetalle.class, banChequeTO.getDetSecuencia());
        if (conDetalleAux != null) {
            ConContable conContable = contableDao.obtenerPorIdEvict(ConContable.class, conDetalleAux.getConContable().getConContablePK());
            conContable.setConBloqueado(true);
            ConCuentas conCuentas = conDetalleAux.getConCuentas();
            ConDetalle conDetalle = ConversionesContabilidad.convertirConDetalle_ConDetalle(conDetalleAux);
            conDetalle.setConContable(conContable);
            conDetalle.setConCuentas(conCuentas);
            conDetalle.setDetSaldo(BigDecimal.ZERO);
            comprobar = false;
            BanCheque banChequeAux = chequeDao.obtenerPorId(BanCheque.class, banChequeTO.getChqSecuencia());
            if (banChequeAux != null) {
                banChequeTO.setConcEmpresa(banChequeAux.getConcEmpresa());
                banChequeTO.setConcCuentaContable(banChequeAux.getConcCuentaContable());
                banChequeTO.setConcCodigo(banChequeAux.getConcCodigo());
            }
            if (conDetalle.getConContable().getConAnulado() || conDetalle.getConContable().getConPendiente()) {
                retorno = "FNo se ha podido cambiar el número de Cheque\nya que el Contable se ha marcado como pendiente o se ha anulado";
            } else {
                susClave = conDetalle.getDetDocumento() == null ? "" : conDetalle.getDetDocumento();
                susSuceso = "INSERT";
                susTabla = "banco.ban_cheque";
                susDetalle = numeroCheque != null && !numeroCheque.equals("") && numeroCheque.trim().equals(conDetalle.getDetDocumento())
                        ? "Se ingreso el cheque con número "
                        + (conDetalle.getDetDocumento() == null ? "" : conDetalle.getDetDocumento())
                        : "Se ingreso el cheque con número " + numeroCheque + " reemplazando al cheque "
                        + (conDetalle.getDetDocumento() == null ? "" : conDetalle.getDetDocumento());
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                BanCheque banCheque = ConversionesBanco.convertirBanChequeTO_BanCheque(banChequeTO);
                banCheque.setChqImpresoFecha(new Date());
                conDetalle.setDetDocumento(numeroCheque
                        .equals((conDetalle.getDetDocumento() == null ? "" : conDetalle.getDetDocumento()))
                        ? (conDetalle.getDetDocumento() == null ? "" : conDetalle.getDetDocumento())
                        : numeroCheque);
                conDetalle.setDetGenerado(true);
                comprobar = chequeDao.insertarBanCheque(banCheque, sisSuceso, conContable, conDetalle);
                if (comprobar) {
                    retorno = "TInformación del cheque guardada...";
                } else {
                    retorno = "FHubo un error al guardar la información del cheque...";
                }
            }
        } else {
            retorno = "FHubo un error al guardar la información del cheque...\nConDetalle null...";
        }
        return retorno;
    }

    @Override
    public String modificarFechaBanChequeTO(String empresa, String cuenta,
            String numero, String fecha,
            String usuario,
            SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        String retorno = "";
        BanChequeTO banChequeTO = chequeDao.buscarBanChequeTO(empresa, cuenta, numero);
        if (banChequeTO != null && banChequeTO.getChqSecuencia() != null) {
            susClave = banChequeTO.getChqSecuencia().toString();
            susSuceso = "UPDATE";
            susTabla = "banco.ban_cheque";
            susDetalle = "El cheque numero " + numero + " con la numeración " + banChequeTO.getChqSecuencia()
                    + " con fecha " + banChequeTO.getChqFecha() + " por la fecha " + fecha;
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            banChequeTO.setChqFecha(fecha);
            BanCheque banCheque = ConversionesBanco.convertirBanChequeTO_BanCheque(banChequeTO);
            comprobar = chequeDao.modificarBanCheque(banCheque, sisSuceso);
            if (comprobar) {
                retorno = "TSe modifico la fecha del cheque " + numero + "...";
            } else {
                retorno = "FHubo un error al modificar la fecha del cheque " + numero + "...";
            }
        } else {
            retorno = "FNo se encuentra el cheque " + numero + "...";
        }
        return retorno;
    }

    @Override
    public String modificarNumeroBanChequeTO(String empresa, String cuenta,
            String numero, String numeroNuevo,
            String usuario, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        String retorno = "";
        BanChequeTO banChequeTO = chequeDao.buscarBanChequeTO(empresa, cuenta, numero);
        if (banChequeTO != null && banChequeTO.getChqSecuencia() != null) {
            if (!chequeDao.isExisteChequeAimprimir(empresa, cuenta, numeroNuevo, null)) {
                ConDetalle conDetalle = detalleDao.obtenerPorId(ConDetalle.class, banChequeTO.getChqSecuencia());
                if (conDetalle != null) {
                    susClave = banChequeTO.getChqSecuencia().toString();
                    susSuceso = "UPDATE";
                    susTabla = "banco.ban_cheque";
                    susDetalle = "El cheque numero " + numero + " con la numeración "
                            + banChequeTO.getChqSecuencia() + " con fecha " + banChequeTO.getChqFecha()
                            + " por chqImpreso=false";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);

                    banChequeTO.setChqImpreso(false);
                    BanCheque banCheque = ConversionesBanco.convertirBanChequeTO_BanCheque(banChequeTO);
                    comprobar = chequeDao.modificarBanCheque(banCheque, sisSuceso);

                    susClave = conDetalle.getDetSecuencia().toString();
                    susSuceso = "UPDATE";
                    susTabla = "contabilidad.con_detalle";
                    susDetalle = "Se modifico el cheque numero " + numero + " con la numeración "
                            + banChequeTO.getChqSecuencia() + " con fecha " + banChequeTO.getChqFecha()
                            + " por la numeración " + numeroNuevo;
                    sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    conDetalle.setDetDocumento(numeroNuevo);
                    boolean comprobar1 = detalleDao.modificarConDetalleTO(conDetalle, sisSuceso);
                    if (comprobar && comprobar1) {

                        boolean actualizar = chequeDao.actualizarCambioCheque(empresa, cuenta, numero, numeroNuevo, conDetalle.getConContable().getConContablePK().getConPeriodo(),
                                conDetalle.getConContable().getConContablePK().getConTipo(),
                                conDetalle.getConContable().getConContablePK().getConNumero());

                        if (actualizar) {
                            retorno = "T<html>Se modificó correctamente el CHEQUE: " + numero + " al numero "
                                    + numeroNuevo + "<br><br>" + "Periodo: <font size = 5>"
                                    + conDetalle.getConContable().getConContablePK().getConPeriodo()
                                    + "</font>.<br> Tipo: <font size = 5>"
                                    + conDetalle.getConContable().getConContablePK().getConTipo()
                                    + "</font>.<br> Número: <font size = 5>"
                                    + conDetalle.getConContable().getConContablePK().getConNumero() + "</font>.</html>"
                                    + conDetalle.getConContable().getConContablePK().getConPeriodo() + ", "
                                    + conDetalle.getConContable().getConContablePK().getConNumero();
                        } else {
                            retorno = "F<html>Hubo un error al modificar el numero del cheque " + numero + "...</html>";
                        }

                        // retorno = "TSe modifico el numero del cheque " +
                        // numero + " al numero " + numeroNuevo + "...";
                    } else {
                        retorno = "F<html>Hubo un error al modificar el numero del cheque " + numero + "...</html>";
                    }
                } else {
                    retorno = "F<html>No se pudo recuperar la información contable del cheque...</html>";
                }
            } else {
                retorno = "F<html>El nuevo numero de cheque ya esta registrado...</html>";
            }
        } else {
            retorno = "F<html>No se encuentra el cheque " + numero + "...</html>";
        }
        return retorno;
    }

    @Override
    public List<BanListaChequeTO> getBanListaChequeTO(String empresa, String cuenta,
            String desde, String hasta,
            String tipo) throws Exception {
        return chequeDao.getBanListaChequeTO(empresa, cuenta, desde, hasta, tipo);
    }

    @Override
    public List<BanListaChequesImpresosTO> getBanListaChequesImpresosTO(String empresa, String cuenta,
            String desde, String hasta) throws Exception {
        return chequeDao.getBanListaChequesImpresosTO(empresa, cuenta, desde, hasta);
    }

    @Override
    public List<BanDetalleChequesPosfechadosTO> getBanListaChequePostfechado(String empresa, String sector,
            String cliente, String desde,
            String hasta, String grupo,
            boolean ichfa) throws Exception {
        List<BanDetalleChequesPosfechadosTO> cheques = chequeDao.getBanListaChequePostfechado(empresa, sector, cliente, desde, hasta, grupo, ichfa);
        return cheques;
    }

    @Override
    public List<BanDetalleChequesPosfechadosTO> getBanListaChequePostfechadoAnticipos(String empresa, String sector,
            String cliente, String desde,
            String hasta, String grupo,
            boolean ichfa) throws Exception {
        List<BanDetalleChequesPosfechadosTO> cheques = chequeDao.getBanListaChequePostfechadoAnticipos(empresa, sector, cliente, desde, hasta, grupo, ichfa);
        return cheques;
    }

    @Override
    public List<BanFunChequesNoEntregadosTO> getBanFunChequesNoEntregadosTO(String empresa, String cuenta)
            throws Exception {
        return chequeDao.getBanFunChequesNoEntregadosTO(empresa, cuenta);
    }

    @Override
    public List<String> insertarBanFunChequesNoEntregados(String empresa, String cuenta,
            List<BanFunChequesNoEntregadosTO> banFunChequesNoEntregadosTOs, String usuario,
            SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        List<String> lista = new ArrayList<String>(1);
        mensaje = "";
        SisEmpresa sisEmpresa = empresaService.obtenerPorId(empresa);
        List<SisSuceso> sisSucesos = new ArrayList<SisSuceso>(0);
        SisSuceso sisSuceso = null;
        // /// LISTA CHEQUES NO ENTREGADOS
        List<BanFunChequesNoEntregadosTO> banFunChequesNoEntregadosTOAuxs = chequeDao
                .getBanFunChequesNoEntregadosTO(empresa, cuenta);
        BanCheque banCheque = null;
        BanCheque banChequeAux = null;
        List<BanCheque> banCheques = new ArrayList<BanCheque>(0);
        ConContableConDetalle conContableConDetalle = null;
        List<ConContableConDetalle> conContableConDetalles = new ArrayList<ConContableConDetalle>(0);
        for (BanFunChequesNoEntregadosTO banFunChequesNoEntregadosTOAux : banFunChequesNoEntregadosTOAuxs) {
            for (BanFunChequesNoEntregadosTO banFunChequesNoEntregadosTO : banFunChequesNoEntregadosTOs) {
                // secuencia y valor son iguales
                if (banFunChequesNoEntregadosTOAux.getChqSecuencia()
                        .equals(banFunChequesNoEntregadosTO.getChqSecuencia())) {
                    if (banFunChequesNoEntregadosTOAux.getChqValor()
                            .compareTo(banFunChequesNoEntregadosTO.getChqValor()) == 0) {
                        // /// BUSCANDO banCheque
                        banCheque = chequeDao.obtenerPorId(BanCheque.class,
                                new Long(banFunChequesNoEntregadosTO.getChqSecuencia().toString()));
                        if (banCheque != null) {
                            // /// BUSCANDO CONTABLE
                            conContableConDetalle = detalleService.buscarConContableConDetalle(
                                    Long.parseLong(banFunChequesNoEntregadosTO.getChqSecuencia().toString()));
                            if (conContableConDetalle != null) {
                                if (!conContableConDetalle.getConContable().getConAnulado()
                                        && !conContableConDetalle.getConContable().getConReversado()
                                        && !conContableConDetalle.getConContable().getConPendiente()) {
                                    conContableConDetalle.getConContable().setConBloqueado(true);
                                    conContableConDetalle.getConDetalle().setDetGenerado(true);
                                    conContableConDetalles.add(conContableConDetalle);
                                } else {
                                    mensaje = "FEl contable ya fué anulado, reversado y/o esta como pendiente el cheque "
                                            + banFunChequesNoEntregadosTO.getChqNumero();
                                }
                            } else {
                                mensaje = "FNo se encontro el contable del cheque "
                                        + banFunChequesNoEntregadosTO.getChqNumero();
                            }
                            if (mensaje.isEmpty()) {
                                banChequeAux = ConversionesBanco.convertirBanCheque_BanCheque(banCheque);
                                banChequeAux.setChqEntregado(banFunChequesNoEntregadosTO.isEstado());
                                banChequeAux.setChqEntregadoObservacion(
                                        banFunChequesNoEntregadosTO.getChqObservacion());
                                banChequeAux.setChqEntregadoFecha(UtilsValidacion
                                        .fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
                                banCheques.add(banChequeAux);
                                susDetalle = "";
                                susClave = banFunChequesNoEntregadosTO.getChqSecuencia().toString();
                                susSuceso = "UPDATE";
                                susTabla = "banco.ban_cheque";
                                sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                                        sisInfoTO);
                                sisSucesos.add(sisSuceso);
                            }
                        } else {
                            // /// BUSCANDO CONTABLE
                            conContableConDetalle = detalleService.buscarConContableConDetalle(
                                    Long.parseLong(banFunChequesNoEntregadosTO.getChqSecuencia().toString()));
                            if (conContableConDetalle != null) {
                                if (!conContableConDetalle.getConContable().getConAnulado()
                                        && !conContableConDetalle.getConContable().getConReversado()
                                        && !conContableConDetalle.getConContable().getConPendiente()) {
                                    conContableConDetalle.getConContable().setConBloqueado(true);
                                    conContableConDetalle.getConDetalle().setDetGenerado(true);
                                    conContableConDetalles.add(conContableConDetalle);
                                } else {
                                    mensaje = "FEl contable ya fué anulado, reversado y/o esta como pendiente el cheque "
                                            + banFunChequesNoEntregadosTO.getChqNumero();
                                }
                            } else {
                                mensaje = "FNo se encontro el contable del cheque "
                                        + banFunChequesNoEntregadosTO.getChqNumero();
                            }
                            if (mensaje.isEmpty()) {
                                banChequeAux = new BanCheque();
                                banChequeAux.setChqBeneficiario(
                                        banFunChequesNoEntregadosTO.getChqBeneficiario().trim());
                                banChequeAux.setChqCantidad(NumberToLetterConverter.convertNumberToLetter(
                                        banFunChequesNoEntregadosTO.getChqValor().toPlainString()));
                                banChequeAux.setChqCiudad(sisEmpresa.getEmpCiudad());
                                banChequeAux.setChqFecha(UtilsValidacion
                                        .fecha(banFunChequesNoEntregadosTO.getChqFechaEmision(), "yyyy-MM-dd"));
                                banChequeAux.setChqImpreso(false);
                                banChequeAux.setChqRevisado(false);
                                banChequeAux.setChqReversado(false);
                                banChequeAux.setChqAnulado(false);
                                banChequeAux.setChqEntregado(banFunChequesNoEntregadosTO.isEstado());
                                banChequeAux.setChqEntregadoObservacion(
                                        banFunChequesNoEntregadosTO.getChqObservacion());
                                banChequeAux.setChqEntregadoFecha(UtilsValidacion
                                        .fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
                                banChequeAux.setConcEmpresa(null);
                                banChequeAux.setConcCuentaContable(null);
                                banChequeAux.setConcCodigo(null);
                                banChequeAux
                                        .setDetSecuencia(banFunChequesNoEntregadosTO.getChqSecuencia().longValue());
                                banChequeAux
                                        .setChqSecuencia(banFunChequesNoEntregadosTO.getChqSecuencia().longValue());
                                banChequeAux.setChqCruzado(false);
                                banChequeAux.setChqNoEsCheque(false);
                                banCheques.add(banChequeAux);
                            }
                        }
                    } else {
                        mensaje = "FEl cheque: " + banFunChequesNoEntregadosTO.getChqNumero()
                                + " tiene otro valor..";
                    }
                    break;
                }
            }
            if (!mensaje.isEmpty()) {
                if (lista.isEmpty()) {
                    lista.add("FProblemas con los siguientes cheques");
                    lista.add(mensaje);
                } else {
                    lista.add(mensaje);
                }
            }

            banCheque = null;
            sisSuceso = null;
            mensaje = "";
        }
        if (lista.isEmpty()) {
            comprobar = chequeDao.accionBanCheques(banCheques, conContableConDetalles, sisSucesos, 'I');
            if (conContableConDetalle != null) {
                contableDao.evict(conContableConDetalle.getConContable());
            }
            if (comprobar) {
                lista.add("TLos cheques han sido guardados..");
            } else {
                lista.add("Hubieron problemas al intentar guardar..\nContacte con el administrador..");
            }
        }
        return lista;
    }

    @Override
    public List<BanFunChequesNoRevisadosTO> getBanFunChequesNoRevisadosTO(String empresa, String cuenta)
            throws Exception {
        return chequeDao.getBanFunChequesNoRevisadosTO(empresa, cuenta);
    }

    @Override
    public List<String> insertarBanFunChequesNoRevisados(String empresa, String cuenta,
            List<BanFunChequesNoRevisadosTO> banFunChequesNoRevisadosTOs, String usuario,
            SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        List<String> lista = new ArrayList<>(1);
        mensaje = "";
        SisEmpresa sisEmpresa = empresaService.obtenerPorId(empresa);
        SisSuceso sisSuceso;
        List<SisSuceso> sisSucesos = new ArrayList<>(0);
        // /// LISTA CHEQUES NO Revisados
        List<BanFunChequesNoRevisadosTO> banFunChequesNoRevisadosTOAuxs = chequeDao.getBanFunChequesNoRevisadosTO(empresa, cuenta);
        BanCheque banCheque;
        BanCheque banChequeAux;
        List<BanCheque> banCheques = new ArrayList<>(0);
        ConContableConDetalle conContableConDetalle = null;
        List<ConContableConDetalle> conContableConDetalles = new ArrayList<>(0);
        for (BanFunChequesNoRevisadosTO banFunChequesNoRevisadosTOAux : banFunChequesNoRevisadosTOAuxs) {
            for (BanFunChequesNoRevisadosTO banFunChequesNoRevisadosTO : banFunChequesNoRevisadosTOs) {
                // secuencia y valor son iguales
                if (banFunChequesNoRevisadosTOAux.getChqSecuencia().equals(banFunChequesNoRevisadosTO.getChqSecuencia())) {
                    if (banFunChequesNoRevisadosTOAux.getChqValor().compareTo(banFunChequesNoRevisadosTO.getChqValor()) == 0) {
                        // /// BUSCANDO banCheque
                        banCheque = chequeDao.obtenerPorId(BanCheque.class, new Long(banFunChequesNoRevisadosTO.getChqSecuencia().toString().trim()));
                        if (banCheque != null) {
                            // /// BUSCANDO CONTABLE
                            conContableConDetalle = detalleService.buscarConContableConDetalle(Long.parseLong(banFunChequesNoRevisadosTO.getChqSecuencia().toString()));
                            if (conContableConDetalle != null) {
                                if (!conContableConDetalle.getConContable().getConAnulado() && !conContableConDetalle.getConContable().getConReversado() && !conContableConDetalle.getConContable().getConPendiente()) {
                                    conContableConDetalle.getConContable().setConBloqueado(true);
                                    conContableConDetalle.getConDetalle().setDetGenerado(true);
                                    conContableConDetalles.add(conContableConDetalle);
                                } else {
                                    mensaje = "El contable ya fué anulado, reversado y/o esta como pendiente el cheque " + banFunChequesNoRevisadosTO.getChqNumero();
                                }
                            } else {
                                mensaje = "No se encontro el contable del cheque " + banFunChequesNoRevisadosTO.getChqNumero();
                            }
                            if (mensaje.isEmpty()) {
                                banChequeAux = ConversionesBanco.convertirBanCheque_BanCheque(banCheque);
                                banChequeAux.setChqRevisado(banFunChequesNoRevisadosTO.isEstado());
                                banChequeAux.setChqRevisadoObservacion(banFunChequesNoRevisadosTO.getChqObservacion());
                                banChequeAux.setChqRevisadoFecha(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
                                banCheques.add(banChequeAux);
                                // ///
                                susDetalle = "";
                                susClave = banFunChequesNoRevisadosTO.getChqSecuencia().toString();
                                susSuceso = "UPDATE";
                                susTabla = "banco.ban_cheque";
                                sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                sisSucesos.add(sisSuceso);
                            }
                        } else {
                            // /// BUSCANDO CONTABLE
                            conContableConDetalle = detalleService.buscarConContableConDetalle(Long.parseLong(banFunChequesNoRevisadosTO.getChqSecuencia().toString()));
                            if (conContableConDetalle != null) {
                                if (!conContableConDetalle.getConContable().getConAnulado() && !conContableConDetalle.getConContable().getConReversado() && !conContableConDetalle.getConContable().getConPendiente()) {
                                    conContableConDetalle.getConContable().setConBloqueado(true);
                                    conContableConDetalle.getConDetalle().setDetGenerado(true);
                                    conContableConDetalles.add(conContableConDetalle);
                                } else {
                                    mensaje = "El contable ya fué anulado, reversado y/o esta como pendiente el cheque " + banFunChequesNoRevisadosTO.getChqNumero();
                                }
                            } else {
                                mensaje = "No se encontro el contable del cheque " + banFunChequesNoRevisadosTO.getChqNumero();
                            }
                            if (mensaje.isEmpty()) {
                                banChequeAux = new BanCheque();
                                banChequeAux.setChqBeneficiario(banFunChequesNoRevisadosTO.getChqBeneficiario().trim());
                                banChequeAux.setChqCantidad(NumberToLetterConverter.convertNumberToLetter(banFunChequesNoRevisadosTO.getChqValor().toPlainString()));
                                banChequeAux.setChqCiudad(sisEmpresa.getEmpCiudad());
                                banChequeAux.setChqFecha(UtilsValidacion.fecha(banFunChequesNoRevisadosTO.getChqFechaEmision(), "yyyy-MM-dd"));
                                banChequeAux.setChqImpreso(false);
                                banChequeAux.setChqRevisado(banFunChequesNoRevisadosTO.isEstado());
                                banChequeAux.setChqRevisadoFecha(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
                                banChequeAux.setChqRevisadoObservacion(banFunChequesNoRevisadosTO.getChqObservacion());
                                banChequeAux.setChqEntregado(false);
                                banChequeAux.setChqReversado(false);
                                banChequeAux.setChqAnulado(false);
                                banChequeAux.setConcEmpresa(null);
                                banChequeAux.setConcCuentaContable(null);
                                banChequeAux.setConcCodigo(null);
                                banChequeAux.setDetSecuencia(banFunChequesNoRevisadosTO.getChqSecuencia().longValue());
                                banChequeAux.setChqSecuencia(banFunChequesNoRevisadosTO.getChqSecuencia().longValue());
                                banChequeAux.setChqCruzado(false);
                                banChequeAux.setChqNoEsCheque(false);
                                banCheques.add(banChequeAux);
                            }
                        }
                    } else {
                        mensaje = "El cheque: " + banFunChequesNoRevisadosTO.getChqNumero() + " tiene otro valor..";
                    }
                    break;
                }
            }
            if (!mensaje.isEmpty()) {
                if (lista.isEmpty()) {
                    lista.add("FProblemas con los siguientes cheques. " + mensaje);
                } else {
                    lista.add(mensaje);
                }
            }
            banCheque = null;
            sisSuceso = null;
            mensaje = "";
        }
        if (lista.isEmpty()) {
            comprobar = chequeDao.accionBanCheques(banCheques, conContableConDetalles, sisSucesos, 'I');
            if (conContableConDetalle != null) {
                contableDao.evict(conContableConDetalle.getConContable());
            }
            if (comprobar) {
                lista.add("TLos cheques han sido guardados..");
            } else {
                lista.add("FHubieron problemas al intentar guardar..\nContacte con el administrador..");
            }
        }
        return lista;
    }

    @Override
    public List<BanChequePreavisadoTO> getBanFunChequesPreavisados(String empresa, String cuenta) throws Exception {
        return chequeDao.getBanFunChequesPreavisados(empresa, cuenta, cuentaDao.getNombreBanco(empresa, cuenta));
    }

    @Override
    public int getConteoChequesPreavisados(String empresa, String cuenta) throws Exception {
        return chequeDao.getConteoChequesPreavisados(empresa, cuenta);
    }

    @Override
    public MensajeTO cambioDeCheque(String cuenta, String cuentaActual,
            String chequeAnterior, String chequeNuevo,
            String empresa, String usuario,
            String observaciones, String fecha,
            String con_tipo_cod,
            SisInfoTO sisInfoTO) throws Exception {
        String mensajes = "";
        MensajeTO mensajeTO = new MensajeTO();
        // /// CREANDO Suceso
        susSuceso = "UPDATE";
        susClave = cuenta;
        susTabla = "banco.ban_cheque";
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
        boolean periodoCerrado = false;
        String codPeriodo = "";
        comprobar = false;
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                codPeriodo = sisListaPeriodoTO.getPerCodigo();
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (comprobar) {
            if (!periodoCerrado) {
                if (tipoDao.buscarTipoContable(empresa, con_tipo_cod) != null) {
                    BanChequeTO banChequeTO = chequeDao.buscarBanChequeTO(empresa, cuenta, chequeAnterior);
                    if (banChequeTO != null && banChequeTO
                            .getChqSecuencia() != null/*
                                                                                                                 * && !banChequeTO.
                                                                                                                 * getChqAnulado ()
                             */) {
                        if (!chequeDao.isExisteChequeAimprimir(empresa, cuentaActual, chequeNuevo, null)) {
                            ConDetalle conDetalleAux = detalleDao.obtenerPorId(ConDetalle.class,
                                    banChequeTO.getChqSecuencia());
                            if (conDetalleAux != null) {
                                ConCuentas conCuentas = cuentasDao.obtenerPorId(ConCuentas.class,
                                        new ConCuentasPK(empresa, cuenta));
                                ConCuentas conCuentasActual = cuentasDao.obtenerPorId(ConCuentas.class,
                                        new ConCuentasPK(empresa, cuentaActual));
                                if (conCuentas != null) {
                                    BanCheque banCheque = ConversionesBanco
                                            .convertirBanChequeTO_BanCheque(banChequeTO);
                                    ConContable conContable = new ConContable();
                                    conContable.setConContablePK(
                                            new ConContablePK(empresa, codPeriodo, con_tipo_cod, "0000001"));
                                    conContable.setConFecha(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                    conContable.setConPendiente(false);
                                    conContable.setConBloqueado(false);
                                    conContable.setConAnulado(false);
                                    conContable.setConGenerado(false);
                                    conContable.setConConcepto(banCheque.getChqBeneficiario());
                                    conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                                    conContable.setConObservaciones(observaciones);
                                    conContable.setConReferencia("banco.ban_cheque");
                                    conContable.setUsrEmpresa(empresa);
                                    conContable.setUsrCodigo(usuario);
                                    conContable.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion
                                            .fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                    ConNumeracion conNumeracion = new ConNumeracion();
                                    conNumeracion.setConNumeracionPK(
                                            new ConNumeracionPK(empresa, codPeriodo, con_tipo_cod));
                                    List<ConDetalle> conDetalles = new ArrayList<ConDetalle>();
                                    ConDetalle conDetalle = new ConDetalle();
                                    conDetalle.setDetSecuencia(0L);
                                    conDetalle.setPrdSector(conDetalleAux.getPrdSector());
                                    conDetalle.setPrdPiscina(conDetalleAux.getPrdPiscina());
                                    conDetalle.setDetDocumento(conDetalleAux.getDetDocumento());
                                    conDetalle.setDetDebitoCredito('D');
                                    conDetalle.setDetValor(conDetalleAux.getDetValor());
                                    conDetalle.setDetGenerado(true);
                                    conDetalle.setDetReferencia(conDetalleAux.getDetReferencia());
                                    conDetalle.setDetObservaciones(conDetalleAux.getDetObservaciones());
                                    conDetalle.setDetOrden(1);
                                    conDetalle.setConCuentas(conCuentas);
                                    conDetalle.setConContable(conContable);
                                    conDetalles.add(conDetalle);
                                    conDetalle = new ConDetalle();
                                    conDetalle.setDetSecuencia(0L);
                                    conDetalle.setPrdSector(conDetalleAux.getPrdSector());
                                    conDetalle.setPrdPiscina(conDetalleAux.getPrdPiscina());
                                    conDetalle.setDetDocumento(chequeNuevo);
                                    conDetalle.setDetDebitoCredito('C');
                                    conDetalle.setDetValor(conDetalleAux.getDetValor());
                                    conDetalle.setDetGenerado(true);
                                    conDetalle.setDetReferencia(conDetalleAux.getDetReferencia());
                                    conDetalle.setDetObservaciones(conDetalleAux.getDetObservaciones());
                                    conDetalle.setDetOrden(2);
                                    conDetalle.setConCuentas(conCuentasActual);
                                    conDetalle.setConContable(conContable);
                                    conDetalles.add(conDetalle);
                                    banCheque.setChqAnulado(true);
                                    banCheque.setChqAnuladoObservacion(observaciones);
                                    banCheque.setChqAnuladoFecha(UtilsValidacion
                                            .fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
                                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                            susDetalle, sisInfoTO);
                                    comprobar = false;
                                    comprobar = contableDao.insertarTransaccionContable(conContable, conDetalles,
                                            sisSuceso, conNumeracion, null, null, null, null, null, null, null,
                                            null, null, null, null, null, null, null, banCheque, sisInfoTO);
                                    if (comprobar) {
                                        mensajes = "T<html>Se modificó correctamente el CHEQUE:<br><br>"
                                                + "Periodo: <font size = 5>"
                                                + conContable.getConContablePK().getConPeriodo()
                                                + "</font>.<br> Tipo: <font size = 5>"
                                                + conContable.getConContablePK().getConTipo()
                                                + "</font>.<br> Número: <font size = 5>"
                                                + conContable.getConContablePK().getConNumero() + "</font>.</html>"
                                                + conContable.getConContablePK().getConPeriodo() + ", "
                                                + conContable.getConContablePK().getConNumero();

                                        mensajeTO.getMap().put("conContable", conContable);
                                    } else {
                                        mensajes = "FHubo un error en el lado del servidor al intentar almacenar la información...";
                                    }

                                } else {
                                    mensajes = "F<html>No se encuentra la CUENTA CONTABLE: " + cuenta + "</html>";
                                }

                            } else {
                                mensajes = "F<html>No se pudo recuperar la información contable del cheque...</html>";
                            }
                        } else {
                            mensajes = "F<html>El CHEQUE NUEVO ya existe...</html>";
                        }
                    } else {
                        mensajes = "F<html>El CHEQUE ANTERIOR no existe o ya esta anulado...</html>";
                    }
                } else {
                    mensajes = "F<html>El TIPO DE CONTABLE 'CD' no se encuentra creado...</html>";
                }
            } else {
                mensajes = "F<html>El periodo que corresponde a la fecha " + fecha
                        + " se encuentra cerrado...</html>";
            }
        } else {
            mensajes = "F<html>No se encuentra ningún periodo para la fecha que ingresó...</html>";
        }
        mensajeTO.setMensaje(mensajes);
        return mensajeTO;
    }

    @Override
    public MensajeTO cambioDeChequeWeb(String cuenta, String cuentaActual,
            String chequeAnterior, String chequeNuevo,
            String empresa, String usuario,
            String observaciones, String fecha,
            String con_tipo_cod,
            SisInfoTO sisInfoTO) throws Exception {
        String mensajes = "";
        MensajeTO mensajeTO = new MensajeTO();
        // /// CREANDO Suceso
        susSuceso = "UPDATE";
        susClave = cuenta;
        susTabla = "banco.ban_cheque";
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
        boolean periodoCerrado = false;
        String codPeriodo = "";
        comprobar = false;
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                codPeriodo = sisListaPeriodoTO.getPerCodigo();
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (comprobar) {
            if (!periodoCerrado) {
                if (tipoDao.buscarTipoContable(empresa, con_tipo_cod) != null) {
                    BanChequeTO banChequeTO = chequeDao.buscarBanChequeTO(empresa, cuenta, chequeAnterior);
                    if (banChequeTO != null && banChequeTO
                            .getChqSecuencia() != null/*
                                                                                                                 * && !banChequeTO.
                                                                                                                 * getChqAnulado ()
                             */) {
                        if (!chequeDao.isExisteChequeAimprimir(empresa, cuentaActual, chequeNuevo, null)) {
                            ConDetalle conDetalleAux = detalleDao.obtenerPorId(ConDetalle.class,
                                    banChequeTO.getChqSecuencia());
                            if (conDetalleAux != null) {
                                ConCuentas conCuentas = cuentasDao.obtenerPorId(ConCuentas.class,
                                        new ConCuentasPK(empresa, cuenta));
                                ConCuentas conCuentasActual = cuentasDao.obtenerPorId(ConCuentas.class,
                                        new ConCuentasPK(empresa, cuentaActual));
                                if (conCuentas != null) {
                                    BanCheque banCheque = ConversionesBanco
                                            .convertirBanChequeTO_BanCheque(banChequeTO);
                                    ConContable conContable = new ConContable();
                                    conContable.setConContablePK(
                                            new ConContablePK(empresa, codPeriodo, con_tipo_cod, "0000001"));
                                    conContable.setConFecha(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                    conContable.setConPendiente(false);
                                    conContable.setConBloqueado(false);
                                    conContable.setConAnulado(false);
                                    conContable.setConGenerado(false);
                                    conContable.setConConcepto(banCheque.getChqBeneficiario());
                                    conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                                    conContable.setConObservaciones(observaciones);
                                    conContable.setConReferencia("banco.ban_cheque");
                                    conContable.setUsrEmpresa(empresa);
                                    conContable.setUsrCodigo(usuario);
                                    conContable.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion
                                            .fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));
                                    ConNumeracion conNumeracion = new ConNumeracion();
                                    conNumeracion.setConNumeracionPK(
                                            new ConNumeracionPK(empresa, codPeriodo, con_tipo_cod));
                                    List<ConDetalle> conDetalles = new ArrayList<ConDetalle>();
                                    ConDetalle conDetalle = new ConDetalle();
                                    conDetalle.setDetSecuencia(0L);
                                    conDetalle.setPrdSector(conDetalleAux.getPrdSector());
                                    conDetalle.setPrdPiscina(conDetalleAux.getPrdPiscina());
                                    conDetalle.setDetDocumento(conDetalleAux.getDetDocumento());
                                    conDetalle.setDetDebitoCredito('D');
                                    conDetalle.setDetValor(conDetalleAux.getDetValor());
                                    conDetalle.setDetGenerado(true);
                                    conDetalle.setDetReferencia(conDetalleAux.getDetReferencia());
                                    conDetalle.setDetObservaciones(conDetalleAux.getDetObservaciones());
                                    conDetalle.setDetOrden(1);
                                    conDetalle.setConCuentas(conCuentas);
                                    conDetalle.setConContable(conContable);
                                    conDetalles.add(conDetalle);
                                    conDetalle = new ConDetalle();
                                    conDetalle.setDetSecuencia(0L);
                                    conDetalle.setPrdSector(conDetalleAux.getPrdSector());
                                    conDetalle.setPrdPiscina(conDetalleAux.getPrdPiscina());
                                    conDetalle.setDetDocumento(chequeNuevo);
                                    conDetalle.setDetDebitoCredito('C');
                                    conDetalle.setDetValor(conDetalleAux.getDetValor());
                                    conDetalle.setDetGenerado(true);
                                    conDetalle.setDetReferencia(conDetalleAux.getDetReferencia());
                                    conDetalle.setDetObservaciones(conDetalleAux.getDetObservaciones());
                                    conDetalle.setDetOrden(2);
                                    conDetalle.setConCuentas(conCuentasActual);
                                    conDetalle.setConContable(conContable);
                                    conDetalles.add(conDetalle);
                                    banCheque.setChqAnulado(true);
                                    banCheque.setChqAnuladoObservacion(observaciones);
                                    banCheque.setChqAnuladoFecha(UtilsValidacion
                                            .fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
                                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso,
                                            susDetalle, sisInfoTO);
                                    comprobar = false;
                                    comprobar = contableDao.insertarTransaccionContable(conContable, conDetalles,
                                            sisSuceso, conNumeracion, null, null, null, null, null, null, null,
                                            null, null, null, null, null, null, null, banCheque, sisInfoTO);
                                    if (comprobar) {
                                        mensajes = "TSe modificó correctamente el CHEQUE: "
                                                + "Periodo: "
                                                + conContable.getConContablePK().getConPeriodo()
                                                + ". Tipo: "
                                                + conContable.getConContablePK().getConTipo()
                                                + ". Número: "
                                                + conContable.getConContablePK().getConNumero() + "."
                                                + conContable.getConContablePK().getConPeriodo() + ", "
                                                + conContable.getConContablePK().getConNumero();

                                        mensajeTO.getMap().put("conContable", conContable);
                                    } else {
                                        mensajes = "FHubo un error en el lado del servidor al intentar almacenar la información...";
                                    }

                                } else {
                                    mensajes = "FNo se encuentra la CUENTA CONTABLE: " + cuenta;
                                }

                            } else {
                                mensajes = "FNo se pudo recuperar la información contable del cheque...";
                            }
                        } else {
                            mensajes = "FEl CHEQUE NUEVO ya existe...";
                        }
                    } else {
                        mensajes = "FEl CHEQUE ANTERIOR no existe o ya esta anulado...";
                    }
                } else {
                    mensajes = "FEl TIPO DE CONTABLE 'CD' no se encuentra creado...";
                }
            } else {
                mensajes = "FEl periodo que corresponde a la fecha " + fecha
                        + " se encuentra cerrado...";
            }
        } else {
            mensajes = "FNo se encuentra ningún periodo para la fecha que ingresó...";
        }
        mensajeTO.setMensaje(mensajes);
        return mensajeTO;
    }

    @Override
    public MensajeTO reversarChequeWeb(String empresa, String motivo,
            String cp, String cuenta,
            String cheque, String fecha,
            String observaciones, SisInfoTO sisInfoTO) throws Exception {
        String mensajes = "";
        MensajeTO mensajeTO = new MensajeTO();
        // /// CREANDO Suceso
        susSuceso = "INSERT";
        susClave = cuenta;
        susTabla = "contabilidad.con_contable";
        List<SisListaPeriodoTO> listaSisPeriodoTO = new ArrayList<SisListaPeriodoTO>();
        listaSisPeriodoTO = periodoService.getListaPeriodoTO(empresa);
        boolean periodoCerrado = false;
        String codPeriodo = "";
        comprobar = false;
        for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
            if (UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() >= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                    && UtilsValidacion.fecha(fecha, "yyyy-MM-dd").getTime() <= UtilsValidacion
                    .fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd").getTime()) {
                comprobar = true;
                codPeriodo = sisListaPeriodoTO.getPerCodigo();
                periodoCerrado = sisListaPeriodoTO.getPerCerrado();
            }
        }
        if (comprobar) {
            if (!periodoCerrado) {
                if (tipoDao.buscarTipoContable(empresa, motivo) != null) {
                    if (!chequeDao.isExisteChequeAimprimir(empresa, cuenta, cheque, null)) {
                        ConCuentas conCuentas = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(empresa, cuenta));
                        if (conCuentas != null) {
                            PrdSector sector = sectorService.obtenerPorEmpresaSector(empresa, cp);

                            if (sector != null) {
                                ConContable conContable = new ConContable();
                                conContable.setConContablePK(new ConContablePK(empresa, codPeriodo, motivo, "0000001"));
                                conContable.setConFecha(UtilsValidacion.fecha(fecha, "yyyy-MM-dd"));
                                conContable.setConPendiente(false);
                                conContable.setConBloqueado(false);
                                conContable.setConAnulado(false);
                                conContable.setConGenerado(false);
                                conContable.setConConcepto("CHEQUE ANULADO");
                                conContable.setConDetalle("CONTABLE GENERADO POR EL SISTEMA");
                                conContable.setConObservaciones(observaciones);//OBS
                                conContable.setConReferencia("banco.ban_cheque_reversados");//REF
                                conContable.setUsrEmpresa(empresa);
                                conContable.setUsrCodigo(sisInfoTO.getUsuario());
                                conContable.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss")));

                                ConNumeracion conNumeracion = new ConNumeracion();
                                conNumeracion.setConNumeracionPK(new ConNumeracionPK(empresa, codPeriodo, motivo));

                                List<ConDetalle> conDetalles = new ArrayList<ConDetalle>();
                                ConDetalle conDetalle = new ConDetalle();
                                conDetalle.setDetSecuencia(0L);
                                conDetalle.setPrdSector(sector);
                                conDetalle.setPrdPiscina(null);
                                conDetalle.setDetDocumento(null);
                                conDetalle.setDetDebitoCredito('D');
                                conDetalle.setDetValor(new BigDecimal("0.01"));
                                conDetalle.setDetGenerado(true);
                                conDetalle.setDetReferencia("banco.ban_cheque_reversados");
                                conDetalle.setDetObservaciones(null);
                                conDetalle.setDetOrden(1);
                                conDetalle.setConCuentas(conCuentas);
                                conDetalle.setConContable(conContable);
                                conDetalles.add(conDetalle);

                                conDetalle = new ConDetalle();
                                conDetalle.setDetSecuencia(0L);
                                conDetalle.setPrdSector(sector);
                                conDetalle.setPrdPiscina(null);
                                conDetalle.setDetDocumento(cheque);
                                conDetalle.setDetDebitoCredito('C');
                                conDetalle.setDetValor(new BigDecimal("0.01"));
                                conDetalle.setDetGenerado(true);
                                conDetalle.setDetReferencia("banco.ban_cheque_reversados");
                                conDetalle.setDetObservaciones(null);
                                conDetalle.setDetOrden(2);
                                conDetalle.setConCuentas(conCuentas);
                                conDetalle.setConContable(conContable);
                                conDetalles.add(conDetalle);
                                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                                comprobar = false;
                                comprobar = contableDao.insertarTransaccionContable(conContable, conDetalles,
                                        sisSuceso, conNumeracion, null, null, null, null, null, null, null,
                                        null, null, null, null, null, null, null, null, sisInfoTO);
                                if (comprobar) {
                                    mensajes = "TSe reversó correctamente el CHEQUE: "
                                            + "Periodo: "
                                            + conContable.getConContablePK().getConPeriodo()
                                            + ". Tipo: "
                                            + conContable.getConContablePK().getConTipo()
                                            + ". Número: "
                                            + conContable.getConContablePK().getConNumero() + "."
                                            + conContable.getConContablePK().getConPeriodo() + ", "
                                            + conContable.getConContablePK().getConNumero();

                                    mensajeTO.getMap().put("conContable", conContable);
                                } else {
                                    mensajes = "FHubo un error en el lado del servidor al intentar almacenar la información...";
                                }
                            } else {
                                mensajes = "FNo se encuentra el CENTRO DE PRODUCCIÓN: " + sector;
                            }

                        } else {
                            mensajes = "FNo se encuentra la CUENTA CONTABLE: " + cuenta;
                        }

                    } else {
                        mensajes = "FEl CHEQUE NUEVO ya existe...";
                    }

                } else {
                    mensajes = "FEl TIPO DE CONTABLE 'CD' no se encuentra creado...";
                }
            } else {
                mensajes = "FEl periodo que corresponde a la fecha " + fecha
                        + " se encuentra cerrado...";
            }
        } else {
            mensajes = "FNo se encuentra ningún periodo para la fecha que ingresó...";
        }
        mensajeTO.setMensaje(mensajes);
        return mensajeTO;
    }

    @Override
    public BanCheque buscarBanCheque(Long secuencial) throws Exception {
        return chequeDao.obtenerPorId(BanCheque.class, secuencial);
    }

    @Override
    public Object getBanChequeSecuencial(String empresa, String cuenta) throws Exception {
        return chequeDao.getBanChequeSecuencial(empresa, cuenta);
    }

    @Override
    public ChequeNoImpresoTO visualizarChequeNoImpreso(String empresa, BanListaChequesNoImpresosTO banListaChequesNoImpresosTO) throws Exception {
        ChequeNoImpresoTO chequeNoImpresoTO = new ChequeNoImpresoTO();
        chequeNoImpresoTO.setChqBeneficiario(banListaChequesNoImpresosTO.getChqBeneficiario());
        chequeNoImpresoTO.setChqContableNumero(banListaChequesNoImpresosTO.getChqContableNumero());
        chequeNoImpresoTO.setChqContablePeriodo(banListaChequesNoImpresosTO.getChqContablePeriodo());
        chequeNoImpresoTO.setChqContableTipo(banListaChequesNoImpresosTO.getChqContableTipo());
        chequeNoImpresoTO.setChqCuentaCodigo(banListaChequesNoImpresosTO.getChqCuentaCodigo());
        chequeNoImpresoTO.setChqCuentaDetalle(banListaChequesNoImpresosTO.getChqCuentaDetalle());
        chequeNoImpresoTO.setChqFechaEmision(banListaChequesNoImpresosTO.getChqFechaEmision());
        chequeNoImpresoTO.setChqFechaVencimiento(banListaChequesNoImpresosTO.getChqFechaVencimiento());
        chequeNoImpresoTO.setChqNumero(banListaChequesNoImpresosTO.getChqNumero());
        chequeNoImpresoTO.setChqOrden(banListaChequesNoImpresosTO.getChqOrden());
        chequeNoImpresoTO.setChqSecuencia(banListaChequesNoImpresosTO.getChqSecuencia());
        chequeNoImpresoTO.setChqValor(banListaChequesNoImpresosTO.getChqValor());
        chequeNoImpresoTO.setDatosBancoCuentaTO(bancoService.getConsultaDatosBancoCuentaTO(empresa, banListaChequesNoImpresosTO.getChqCuentaCodigo()));
        chequeNoImpresoTO.setValorChequeLetras(NumberToLetterConverter.convertNumberToLetter(banListaChequesNoImpresosTO.getChqValor().add(new BigDecimal("0.00")) + ""));
        chequeNoImpresoTO.setChqContableReferencia(banListaChequesNoImpresosTO.getChqContableReferencia());
        return chequeNoImpresoTO;
    }

    @Override
    public List<ConDetalle> listarChequesReversados(String empresa, String desde,
            String hasta) throws Exception {
        return chequeDao.listarChequesReversados(empresa, desde, hasta);
    }

}
