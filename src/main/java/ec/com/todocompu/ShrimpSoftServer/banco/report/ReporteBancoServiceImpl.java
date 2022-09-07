package ec.com.todocompu.ShrimpSoftServer.banco.report;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.ConciliacionDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanDetalleChequesPosfechadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoEntregadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoRevisadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesNoImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversado;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.report.ReporteConciliacionCabecera;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaPostfechadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

@Service
public class ReporteBancoServiceImpl implements ReporteBancoService {

    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private ConciliacionDao conciliacionDao;

    private final String modulo = "banco";

    @Override
    public byte[] generarReporteBanco(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaBanBancoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportBanco.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteCuentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaBanCuentaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuenta.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteChequesListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportChequeCobrar.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteChequesNoImpresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequesNoImpresosTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportChequeNoImpresos.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteChequesNoRevisados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanFunChequesNoRevisadosTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportChequeNoRevisados.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteChequesNoEntregados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanFunChequesNoEntregadosTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportChequeNoEntregados.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteImprimirChequesPostfechados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanDetalleChequesPosfechadosTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportListadoChequesPosfechados.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteChequesEmision(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportChequeEmision.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteChequesImpresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequesImpresosTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportChequesImpresos.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteChequesNumero(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportChequeNumero.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteChequesVencimiento(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportChequeVencimiento.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarChequesPostfechados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarCobrosDetalleFormaPostfechadoTO> listado, String desde, String hasta) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteConciliacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, int estado,
            ReporteConciliacionCabecera conciliacionCabecera,
            List<BanListaConciliacionBancariaTO> listConciliacionCredito,
            List<BanListaConciliacionBancariaTO> listConciliacionDebito) throws Exception {

        List<ReporteConciliacion> conciliaciones = new ArrayList<ReporteConciliacion>();
        //conciliadas
        if (estado == 0) {
            for (BanListaConciliacionBancariaTO blcbto : listConciliacionCredito) {
                if (blcbto.getCbConciliado()) {
                    conciliaciones.add(generarReporteConciliacion(blcbto, conciliacionCabecera, usuarioEmpresaReporteTO.getEmpCodigo()));
                }
            }
            for (BanListaConciliacionBancariaTO blcbto : listConciliacionDebito) {
                if (blcbto.getCbConciliado()) {
                    conciliaciones.add(generarReporteConciliacion(blcbto, conciliacionCabecera, usuarioEmpresaReporteTO.getEmpCodigo()));
                }
            }
        }
        //no conciliadas
        if (estado == 1) {
            for (BanListaConciliacionBancariaTO blcbto : listConciliacionCredito) {
                if (!blcbto.getCbConciliado()) {
                    conciliaciones.add(generarReporteConciliacion(blcbto, conciliacionCabecera, usuarioEmpresaReporteTO.getEmpCodigo()));
                }
            }
            for (BanListaConciliacionBancariaTO blcbto : listConciliacionDebito) {
                if (!blcbto.getCbConciliado()) {
                    conciliaciones.add(generarReporteConciliacion(blcbto, conciliacionCabecera, usuarioEmpresaReporteTO.getEmpCodigo()));
                }
            }
        }
        //genral
        if (estado == 2) {
            for (BanListaConciliacionBancariaTO blcbto : listConciliacionCredito) {
                conciliaciones.add(generarReporteConciliacion(blcbto, conciliacionCabecera, usuarioEmpresaReporteTO.getEmpCodigo()));
            }
            for (BanListaConciliacionBancariaTO blcbto : listConciliacionDebito) {
                conciliaciones.add(generarReporteConciliacion(blcbto, conciliacionCabecera, usuarioEmpresaReporteTO.getEmpCodigo()));
            }
        }

        return genericReporteService.generarReporte(modulo, "reportConciliacion.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), conciliaciones);

    }

    public ReporteConciliacion generarReporteConciliacion(BanListaConciliacionBancariaTO blcbto,
            ReporteConciliacionCabecera cabecera, String empresa) throws Exception {
        ReporteConciliacion conciliacion = new ReporteConciliacion();
        BanConciliacion entidadConciliacion = conciliacionDao.obtenerPorId(BanConciliacion.class, new BanConciliacionPK(empresa, cabecera.getConcCuentaContable(), cabecera.getConcCodigoConciliacion()));
        conciliacion.setCbContable(blcbto.getCbContable());
        conciliacion.setCbFecha(blcbto.getCbFecha());
        conciliacion.setCbDocumento(blcbto.getCbDocumento());
        conciliacion.setCbValor(blcbto.getCbValor());
        conciliacion.setCbObservaciones(blcbto.getCbObservaciones());
        conciliacion.setCbMarcaConciliado(blcbto.getCbConciliado() == true ? "*" : " ");
        conciliacion.setCbDC(String.valueOf(blcbto.getCbDc()));
        if (entidadConciliacion != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            conciliacion.setUsrFechaInserta(dateFormat.format(entidadConciliacion.getUsrFechaInserta()));
        }
        conciliacion.setUsrInserta(entidadConciliacion.getUsrCodigo());
        conciliacion.setConcHasta(cabecera.getConcHasta());
        conciliacion.setConcCodigoConciliacion(cabecera.getConcCodigoConciliacion());
        conciliacion.setConcBanco(cabecera.getConcBanco());
        conciliacion.setConcCuentaContable(cabecera.getConcCuentaContable());
        conciliacion.setConcCuentaBancaria(cabecera.getConcCuentaBancaria());
        conciliacion.setConcTitular(cabecera.getConcTitular());

        conciliacion.setConcSaldoEstadoCuenta(cabecera.getConcSaldoEstadoCuenta());
        conciliacion.setConcChequesEnTransito(cabecera.getConcChequesEnTransito());
        conciliacion.setConcDepositosEnTransito(cabecera.getConcDepositosEnTransito());
        conciliacion.setConcSaldoSistema(cabecera.getConcSaldoSistema());
        conciliacion.setConcDiferenciaNoConciliada(cabecera.getConcDiferenciaNoConciliada());
        return conciliacion;
    }

    @Override
    public byte[] generarReporteListadoChequeEmisionVencimientoCobrarNumero(
            UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cuenta, String desde, String hasta, String tipo,
            List<BanListaChequeTO> listBanListaChequeTO) throws Exception {
        List<ReporteListadoChequeEmisionVencimientoCobrarNumero> reporteListadoChequeEmisionVencimientoCobrarNumeros = new ArrayList<ReporteListadoChequeEmisionVencimientoCobrarNumero>();
        for (BanListaChequeTO blcto : listBanListaChequeTO) {
            ReporteListadoChequeEmisionVencimientoCobrarNumero reporteListadoChequeEmisionVencimientoCobrarNumero = new ReporteListadoChequeEmisionVencimientoCobrarNumero(
                    cuenta, desde, hasta, tipo, blcto.getChqCuentaCodigo(), blcto.getChqCuentaDetalle(),
                    blcto.getChqBeneficiario(), blcto.getChqNumero(), blcto.getChqValor(), blcto.getChqFechaEmision(),
                    blcto.getChqFechaVencimiento(),
                    blcto.getChqContablePeriodo() == null ? null : blcto.getChqContablePeriodo(),
                    blcto.getChqContableTipo() == null ? null : blcto.getChqContableTipo(),
                    blcto.getChqContableNumero(), blcto.getChqSecuencia(), blcto.getChqOrden());
            reporteListadoChequeEmisionVencimientoCobrarNumeros.add(reporteListadoChequeEmisionVencimientoCobrarNumero);
        }

        return genericReporteService.generarReporte(modulo, "reportListadoChequeEVCN.jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), reporteListadoChequeEmisionVencimientoCobrarNumeros);

    }

    @Override
    public byte[] generarReporteCheque(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, BanChequeTO banChequeTO,
            String valorLetra1, String valorLetra2, String nombreReporteCheque, ConDetalle conDetalle,
            String cuentaCodigo) throws Exception {
        List<ObjetoCheque> lista = new ArrayList<>();
        ObjetoCheque objetoCheque = new ObjetoCheque();
        objetoCheque.setChqBeneficiario(banChequeTO.getChqBeneficiario());

        String asteriscos = "";
        for (int i = banChequeTO.getChqCantidad().trim().length(); i < 11; i++) {
            asteriscos += "*";
        }
        objetoCheque.setChqCantidad(asteriscos + banChequeTO.getChqCantidad().trim());
        objetoCheque.setChqCiudad(banChequeTO.getChqCiudad());
        objetoCheque.setChqCuenta(cuentaCodigo);
        objetoCheque.setChqEmpresa(
                conDetalle.getConContable().getUsrEmpresa() == null ? "" : conDetalle.getConContable().getUsrEmpresa());
        objetoCheque.setChqEntregado(banChequeTO.isChqEntregado());
        objetoCheque.setChqFecha(banChequeTO.getChqFecha());
        objetoCheque.setChqImpreso(banChequeTO.isChqImpreso());
        objetoCheque.setConcCodigo(banChequeTO.getConcCodigo());
        objetoCheque.setConcCuentaContable(banChequeTO.getConcCuentaContable());
        objetoCheque.setConcEmpresa(banChequeTO.getConcEmpresa());
        objetoCheque.setCtaCuentaContable(conDetalle.getConCuentas().getConCuentasPK().getCtaCodigo() == null ? ""
                : conDetalle.getConCuentas().getConCuentasPK().getCtaCodigo());
        objetoCheque.setCtaEmpresa(conDetalle.getConCuentas().getConCuentasPK().getCtaEmpresa() == null ? ""
                : conDetalle.getConCuentas().getConCuentasPK().getCtaEmpresa());

        objetoCheque.setValorLetra1(valorLetra1);
        objetoCheque.setValorLetra2(valorLetra2);

        lista.add(objetoCheque);

        return genericReporteService.generarReporte(modulo, nombreReporteCheque + ".jrxml", usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), lista);

    }

    @Override
    public byte[] generarReporteListadoConciliacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaConciliacionTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportListadoConciliacion.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteListadoBanCajaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<BanCajaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteListadoChequesAnulados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConDetalle> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportListadoChequesAnulados.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteBancos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaBanBancoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de bancos");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNombre");
            for (ListaBanBancoTO listaBanBancoTO : listado) {
                listaCuerpo.add((listaBanBancoTO.getBanCodigo() == null ? "B"
                        : "S" + listaBanBancoTO.getBanCodigo())
                        + "¬"
                        + (listaBanBancoTO.getBanNombre() == null ? "B"
                        : "S" + listaBanBancoTO.getBanNombre()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteChequesNoImpresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequesNoImpresosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de cheques no impresos");
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "STipo Contable" + "¬" + "SNúmero" + "¬" + "SEmisión" + "¬" + "SConcepto" + "¬" + "SSecuencia"
                    + "¬" + "SCuenta" + "¬" + "SNúmero documento" + "¬" + "SValor");
            for (BanListaChequesNoImpresosTO listaChequesNoImpresosTO : listado) {
                listaCuerpo.add((listaChequesNoImpresosTO.getChqContablePeriodo() == null ? "B"
                        : "S" + listaChequesNoImpresosTO.getChqContablePeriodo())
                        + "¬"
                        + (listaChequesNoImpresosTO.getChqContableTipo() == null ? "B"
                        : "S" + listaChequesNoImpresosTO.getChqContableTipo())
                        + "¬"
                        + (listaChequesNoImpresosTO.getChqContableNumero() == null ? "B"
                        : "S" + listaChequesNoImpresosTO.getChqContableNumero())
                        + "¬"
                        + (listaChequesNoImpresosTO.getChqFechaEmision() == null ? "B"
                        : "S" + listaChequesNoImpresosTO.getChqFechaEmision())
                        + "¬"
                        + (listaChequesNoImpresosTO.getChqBeneficiario() == null ? "B"
                        : "S" + listaChequesNoImpresosTO.getChqBeneficiario())
                        + "¬"
                        + (listaChequesNoImpresosTO.getChqSecuencia() == null ? "B"
                        : "S" + listaChequesNoImpresosTO.getChqSecuencia())
                        + "¬"
                        + (listaChequesNoImpresosTO.getChqCuentaCodigo() == null ? "B"
                        : "S" + listaChequesNoImpresosTO.getChqCuentaCodigo())
                        + "¬"
                        + (listaChequesNoImpresosTO.getChqNumero() == null ? "B"
                        : "S" + listaChequesNoImpresosTO.getChqNumero())
                        + "¬"
                        + (listaChequesNoImpresosTO.getChqValor() == null ? "B"
                        : "D" + listaChequesNoImpresosTO.getChqValor()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteChequesNoRevisados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanFunChequesNoRevisadosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de cheques no revisados");
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "STipo Contable" + "¬" + "SNúmero" + "¬" + "SEmisión" + "¬" + "SConcepto"
                    + "¬" + "SCuenta" + "¬" + "SNúmero documento" + "¬" + "SValor" + "¬" + "SObservaciones" + "¬" + "SImpreso" + "¬" + "SEntregado");
            for (BanFunChequesNoRevisadosTO listaChequesNoRevisadosTO : listado) {
                listaCuerpo.add((listaChequesNoRevisadosTO.getChqContablePeriodo() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqContablePeriodo())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqContableTipo() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqContableTipo())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqContableNumero() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqContableNumero())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqFechaEmision() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqFechaEmision())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqBeneficiario() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqBeneficiario())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqCuentaCodigo() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqCuentaCodigo())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqNumero() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqNumero())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqValor() == null ? "B"
                        : "D" + listaChequesNoRevisadosTO.getChqValor())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqObservacion() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqObservacion())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqImpreso() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqImpreso())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqEntregado() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqEntregado()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteChequesNoEntregados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanFunChequesNoEntregadosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de cheques no entregados");
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "STipo Contable" + "¬" + "SNúmero" + "¬" + "SEmisión" + "¬" + "SConcepto"
                    + "¬" + "SCuenta" + "¬" + "SNúmero documento" + "¬" + "SValor" + "¬" + "SObservaciones" + "¬" + "SImpreso" + "¬" + "SRevisado");
            for (BanFunChequesNoEntregadosTO listaChequesNoRevisadosTO : listado) {
                listaCuerpo.add((listaChequesNoRevisadosTO.getChqContablePeriodo() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqContablePeriodo())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqContableTipo() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqContableTipo())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqContableNumero() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqContableNumero())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqFechaEmision() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqFechaEmision())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqBeneficiario() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqBeneficiario())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqCuentaCodigo() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqCuentaCodigo())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqNumero() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqNumero())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqValor() == null ? "B"
                        : "D" + listaChequesNoRevisadosTO.getChqValor())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqObservacion() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqObservacion())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqImpreso() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqImpreso())
                        + "¬"
                        + (listaChequesNoRevisadosTO.getChqRevisado() == null ? "B"
                        : "S" + listaChequesNoRevisadosTO.getChqRevisado()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCuentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaBanCuentaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de cuentas");
            listaCabecera.add("S");
            listaCuerpo.add("SBanco" + "¬" + "SNúmero" + "¬" + "STitular" + "¬" + "SOficial" + "¬" + "SFormato Cheque" + "¬" + "SCuenta Contable" + "¬" + "SNumeración actual");
            listado.forEach((listaBanCuentaTO) -> {
                listaCuerpo.add((listaBanCuentaTO.getBanBanco() == null ? "B"
                        : "S" + listaBanCuentaTO.getBanBanco())
                        + "¬"
                        + (listaBanCuentaTO.getCtaNumero() == null ? "B"
                        : "S" + listaBanCuentaTO.getCtaNumero())
                        + "¬"
                        + (listaBanCuentaTO.getCtaTitular() == null ? "B"
                        : "S" + listaBanCuentaTO.getCtaTitular())
                        + "¬"
                        + (listaBanCuentaTO.getCtaOficial() == null ? "B"
                        : "S" + listaBanCuentaTO.getCtaOficial())
                        + "¬"
                        + (listaBanCuentaTO.getCtaFormatoCheque() == null ? "B"
                        : "S" + listaBanCuentaTO.getCtaFormatoCheque())
                        + "¬"
                        + (listaBanCuentaTO.getCtaCuentaContable() == null ? "B"
                        : "S" + listaBanCuentaTO.getCtaCuentaContable())
                        + "¬"
                        + (listaBanCuentaTO.getCtaNumeracion() == null ? "B"
                        : "S" + listaBanCuentaTO.getCtaNumeracion()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCheque(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado, String tipoCheque, String banco, String cuenta) throws Exception {
        try {
            /*
            0791754518001, EL MANGLE
            0791783925001, FRUTAS MARINAS
            0791762529001, LA JACANA
            0791771471001, LA SABANA
            0791795796001, OCEANTRACTOR CIA LTDA
            0702521030001, OLLAGUE PAREDES MAURICIO JAVIER
            0791771447001, SOCIEDAD CIVIL KSIERRA*/
            List<String> rucsEvaluar = Arrays.asList("1801414713001", // MIGUEL ERAZO
                    "0791754518001", // MANGLE
                    "0791783925001", // FRUTAS MARINAS
                    "0791762529001", // JACANA
                    "0791771471001", // SABANA
                    "0791795796001", // OCEANTRACTOR
                    "0702521030001", // MAURICIO OLLAGUE
                    "0791833671001", // PUNASEA
                    "0993312797001", // PUNASEA - BELLITEC
                    "0791771447001", // KSIERRA
                    "0791837014001"); // SABANASEA
            boolean rucEncontrado = rucsEvaluar.contains(usuarioEmpresaReporteTO.getEmpRuc());
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            String nombreCabecera = this.getNombreCabecera(tipoCheque);
            listaCabecera.add(nombreCabecera);
            listaCabecera.add("SBanco: " + banco);
            listaCabecera.add("SCuenta: " + cuenta);
            listaCabecera.add("S");
            listaCuerpo.add("SBeneficiario" + "¬" + "SNúmero" + "¬" + "SValor" + "¬" + "SEmisión" + "¬" + "SVencimiento" + "¬" + "SImpreso" + "¬" + "SRevisado"
                    + "¬" + "SEntregado" + "¬" + "SEstado" + "¬" + "SConciliación" + "¬" + "SContable" + (rucEncontrado ? "" : "¬" + "SObservaciones"));
            for (BanListaChequeTO listaBanBancoTO : listado) {
                String observacion = (listaBanBancoTO.getChqObservaciones() == null ? "B" : "S" + listaBanBancoTO.getChqObservaciones());
                String beneficiario = (listaBanBancoTO.getChqBeneficiario() == null ? "B" : "S" + listaBanBancoTO.getChqBeneficiario());
                String beneficiarioObs = rucEncontrado ? observacion.equals("B") ? beneficiario : beneficiario + " | " + observacion : beneficiario;

                listaCuerpo.add(
                        beneficiarioObs
                        + "¬"
                        + (listaBanBancoTO.getChqNumero() == null ? "B"
                        : "S" + listaBanBancoTO.getChqNumero())
                        + "¬"
                        + (listaBanBancoTO.getChqValor() == null ? "B"
                        : "D" + listaBanBancoTO.getChqValor())
                        + "¬"
                        + (listaBanBancoTO.getChqFechaEmision() == null ? "B"
                        : "S" + listaBanBancoTO.getChqFechaEmision())
                        + "¬"
                        + (listaBanBancoTO.getChqFechaVencimiento() == null ? "B"
                        : "S" + listaBanBancoTO.getChqFechaVencimiento())
                        + "¬"
                        + (listaBanBancoTO.getChqImpreso() == null ? "B"
                        : "S" + listaBanBancoTO.getChqImpreso())
                        + "¬"
                        + (listaBanBancoTO.getChqRevisado() == null ? "B"
                        : "S" + listaBanBancoTO.getChqRevisado())
                        + "¬"
                        + (listaBanBancoTO.getChqEntregado() == null ? "B"
                        : "S" + listaBanBancoTO.getChqEntregado())
                        + "¬"
                        + (listaBanBancoTO.getChqStatus() == null ? "B"
                        : "S" + listaBanBancoTO.getChqStatus())
                        + "¬"
                        + (listaBanBancoTO.getChqConCodigo() == null ? "B"
                        : "S" + listaBanBancoTO.getChqConCodigo())
                        + "¬"
                        + (listaBanBancoTO.getChqContableTipo() == null ? "B"
                        : "S" + (listaBanBancoTO.getChqContablePeriodo() + " | " + listaBanBancoTO.getChqContableTipo() + " | " + listaBanBancoTO.getChqContableNumero()))
                        + "¬"
                        + (rucEncontrado ? "B" : observacion));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteChequesPostfechados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanDetalleChequesPosfechadosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Cheques Postfechados");
            listaCabecera.add("S");
            listaCuerpo.add("SIdentificación" + "¬" + "SRazón social" + "¬" + "SPeríodo" + "¬" + "STipo" + "¬" + "SNúmero" + "¬" + "SBanco" + "¬" + "SCuenta" + "¬" + "SEmisión"
                    + "¬" + "SVencimiento" + "¬" + "SReferencia" + "¬" + "SValor" + "¬" + "SObservaciones" + "¬" + "SSecuencial");
            for (BanDetalleChequesPosfechadosTO DetalleChequesPosfechadosTO : listado) {
                listaCuerpo.add((DetalleChequesPosfechadosTO.getChqClienteId() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqClienteId()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqClienteRazonSocial() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqClienteRazonSocial()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqPeriodo() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqPeriodo()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqTipo() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqTipo()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqNumero() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqNumero()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqBanco() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqBanco()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqCuenta() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqCuenta()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqFechaEmision() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqFechaEmision()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqFechaVencimiento() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqFechaVencimiento()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqReferencia() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqReferencia()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqValor() == null ? "B" : "D" + DetalleChequesPosfechadosTO.getChqValor()) + "¬"
                        + (DetalleChequesPosfechadosTO.getChqObservaciones() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getChqObservaciones()) + "¬"
                        + (DetalleChequesPosfechadosTO.getDetSecuencial() == null ? "B" : "S" + DetalleChequesPosfechadosTO.getDetSecuencial()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    public String getNombreCabecera(String valor) {
        String nombre = "";
        if (valor.equals("PENDIENTES")) {
            nombre = "SReporte de cheques por cobrar";
        } else if (valor.equals("EMISION")) {
            nombre = "SReporte de cheques por emisión";
        } else if (valor.equals("NUMERO")) {
            nombre = "SReporte de cheques por número";
        } else if (valor.equals("VENCIMIENTO")) {
            nombre = "SReporte de cheques por vencimiento";
        }
        return nombre;
    }

    @Override
    public Map<String, Object> exportarChequesPostfechados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCobrosDetalleFormaPostfechadoTO> listado, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCheques posfechados");
            listaCabecera.add("SDesde: " + (desde != null ? desde : " "));
            listaCabecera.add("SHasta: " + (hasta != null ? hasta : " "));
            listaCabecera.add("S");
            listaCuerpo.add("SBanco" + "¬" + "SCliente" + "¬" + "SRazón social" + "¬" + "SCuenta" + "¬" + "SFecha vencimiento" + "¬" + "SReferencia" + "¬" + "SValor" + "¬" + "SObservación");
            for (CarCobrosDetalleFormaPostfechadoTO cheque : listado) {
                listaCuerpo.add((cheque.getBanNombre() == null ? "B" : "S" + cheque.getBanNombre())
                        + "¬"
                        + (cheque.getDetClienteId() == null ? "B" : "S" + cheque.getDetClienteId())
                        + "¬"
                        + (cheque.getDetClienteRazonSocial() == null ? "B" : "S" + cheque.getDetClienteRazonSocial())
                        + "¬"
                        + (cheque.getDetCuenta() == null ? "B" : "S" + cheque.getDetCuenta())
                        + "¬"
                        + (cheque.getDetFechaVencimiento() == null ? "B" : "S" + cheque.getDetFechaVencimiento())
                        + "¬"
                        + (cheque.getDetReferencia() == null ? "B" : "S" + cheque.getDetReferencia())
                        + "¬"
                        + (cheque.getDetValor() == null ? "B" : "D" + cheque.getDetValor().toString())
                        + "¬"
                        + (cheque.getDetObservaciones() == null ? "B" : "S" + cheque.getDetObservaciones())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarChequesConciliacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String estado,
            ReporteConciliacionCabecera conciliacionCabecera,
            String pendiente,
            List<BanListaConciliacionBancariaTO> listConciliacionCredito,
            List<BanListaConciliacionBancariaTO> listConciliacionDebito) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SConciliación Bancaria " + "(" + estado + ")");
            listaCabecera.add("SCta. Bancaria: " + conciliacionCabecera.getConcCuentaBancaria());
            listaCabecera.add("SCta. Contable: " + conciliacionCabecera.getConcCuentaContable());
            listaCabecera.add("SPendiente: " + pendiente);
            listaCabecera.add("SHasta: " + conciliacionCabecera.getConcHasta());
            listaCabecera.add("S");
            listaCabecera.add("SSaldo Estado Cuenta:           " + conciliacionCabecera.getConcSaldoEstadoCuenta());
            listaCabecera.add("SCheques Girados y NO Cobrados: " + conciliacionCabecera.getConcChequesEnTransito());
            listaCabecera.add("SDepósitos en Tránsito:         " + conciliacionCabecera.getConcDepositosEnTransito());
            listaCuerpo.add("SContable" + "¬" + "SFecha" + "¬" + (estado.equals("SIN CONCILIAR") ? "SFecha Vencimiento¬" : "") + "SDocumento" + "¬" + "SValor" + "¬" + "SObservación" + "¬" + "SMarca Conciliado");

            for (BanListaConciliacionBancariaTO banListaConciliacionBancariaTO : listConciliacionCredito) {
                if (estado.equals("CONCILIADAS") && banListaConciliacionBancariaTO.getCbConciliado()) {
                    boolean cbConciliado = banListaConciliacionBancariaTO.getCbConciliado() == null ? false
                            : banListaConciliacionBancariaTO.getCbConciliado();
                    listaCuerpo.add((banListaConciliacionBancariaTO.getCbContable() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbContable().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbFecha() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbFecha().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbDocumento() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbDocumento().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbValor() == null ? "B"
                            : "D" + banListaConciliacionBancariaTO.getCbValor().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbObservaciones() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbObservaciones().toString())
                            + "¬" + (cbConciliado == true ? "S*" : "S "));
                }
                if (estado.equals("SIN CONCILIAR") && !banListaConciliacionBancariaTO.getCbConciliado()) {
                    boolean cbConciliado = banListaConciliacionBancariaTO.getCbConciliado() == null ? false
                            : banListaConciliacionBancariaTO.getCbConciliado();
                    listaCuerpo.add((banListaConciliacionBancariaTO.getCbContable() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbContable().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbFecha() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbFecha().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbFechaVencimiento() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbFechaVencimiento().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbDocumento() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbDocumento().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbValor() == null ? "B"
                            : "D" + banListaConciliacionBancariaTO.getCbValor().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbObservaciones() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbObservaciones().toString())
                            + "¬" + (cbConciliado == true ? "S*" : "S "));
                }
                if (estado.equals("TODO")) {
                    boolean cbConciliado = banListaConciliacionBancariaTO.getCbConciliado() == null ? false
                            : banListaConciliacionBancariaTO.getCbConciliado();
                    listaCuerpo.add((banListaConciliacionBancariaTO.getCbContable() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbContable().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbFecha() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbFecha().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbDocumento() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbDocumento().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbValor() == null ? "B"
                            : "D" + banListaConciliacionBancariaTO.getCbValor().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbObservaciones() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbObservaciones().toString())
                            + "¬" + (cbConciliado == true ? "S*" : "S "));
                }
            }
            for (BanListaConciliacionBancariaTO banListaConciliacionBancariaTO : listConciliacionDebito) {
                if (estado.equals("CONCILIADAS") && banListaConciliacionBancariaTO.getCbConciliado()) {
                    boolean cbConciliado = banListaConciliacionBancariaTO.getCbConciliado() == null ? false
                            : banListaConciliacionBancariaTO.getCbConciliado();
                    listaCuerpo.add((banListaConciliacionBancariaTO.getCbContable() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbContable().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbFecha() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbFecha().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbDocumento() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbDocumento().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbValor() == null ? "B"
                            : "D" + banListaConciliacionBancariaTO.getCbValor().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbObservaciones() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbObservaciones().toString())
                            + "¬" + (cbConciliado == true ? "S*" : "S "));
                }
                if (estado.equals("SIN CONCILIAR") && !banListaConciliacionBancariaTO.getCbConciliado()) {
                    boolean cbConciliado = banListaConciliacionBancariaTO.getCbConciliado() == null ? false
                            : banListaConciliacionBancariaTO.getCbConciliado();
                    listaCuerpo.add((banListaConciliacionBancariaTO.getCbContable() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbContable().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbFecha() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbFecha().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbFechaVencimiento() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbFechaVencimiento().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbDocumento() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbDocumento().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbValor() == null ? "B"
                            : "D" + banListaConciliacionBancariaTO.getCbValor().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbObservaciones() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbObservaciones().toString())
                            + "¬" + (cbConciliado == true ? "S*" : "S "));
                }
                if (estado.equals("TODO")) {
                    boolean cbConciliado = banListaConciliacionBancariaTO.getCbConciliado() == null ? false
                            : banListaConciliacionBancariaTO.getCbConciliado();
                    listaCuerpo.add((banListaConciliacionBancariaTO.getCbContable() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbContable().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbFecha() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbFecha().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbDocumento() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbDocumento().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbValor() == null ? "B"
                            : "D" + banListaConciliacionBancariaTO.getCbValor().toString())
                            + "¬"
                            + (banListaConciliacionBancariaTO.getCbObservaciones() == null ? "B"
                            : "S" + banListaConciliacionBancariaTO.getCbObservaciones().toString())
                            + "¬" + (cbConciliado == true ? "S*" : "S "));
                }
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteListadoConciliacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaConciliacionTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SConciliaciones bancarias");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle cuenta" + "¬" + "SCuenta" + "¬" + "SFecha");
            for (BanListaConciliacionTO cheque : listado) {
                listaCuerpo.add((cheque.getConcCodigo() == null ? "B" : "S" + cheque.getConcCodigo())
                        + "¬"
                        + (cheque.getConCtaDetalle() == null ? "B" : "S" + cheque.getConCtaDetalle())
                        + "¬"
                        + (cheque.getConcCuentaContable() == null ? "B" : "S" + cheque.getConcCuentaContable())
                        + "¬"
                        + (cheque.getConcHasta() == null ? "B" : "S" + cheque.getConcHasta())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteBanCajaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanCajaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCaja");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SCuenta" + "¬" + "SNombre");
            for (BanCajaTO cheque : listado) {
                listaCuerpo.add((cheque.getCajaCodigo() == null ? "B" : "S" + cheque.getCajaCodigo())
                        + "¬"
                        + (cheque.getCajaCuenta() == null ? "B" : "S" + cheque.getCajaCuenta())
                        + "¬"
                        + (cheque.getCajaNombre() == null ? "B" : "S" + cheque.getCajaNombre())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteChequesImpresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequesImpresosTO> listadoChequesCobrar, String tipoCheque) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            String nombreCabecera = this.getNombreCabecera(tipoCheque);
            listaCabecera.add(nombreCabecera);
            listaCabecera.add("S");
            listaCuerpo.add("SBeneficiario" + "¬" + "SNúmero" + "¬" + "SValor" + "¬" + "SEmisión" + "¬" + "SVencimiento" + "¬" + "SImpreso"
                    + "¬" + "SConciliación" + "¬" + "SContable");
            listadoChequesCobrar.forEach((listaBanBancoTO) -> {
                listaCuerpo.add(
                        (listaBanBancoTO.getChqBeneficiario() == null ? "B" : "S" + listaBanBancoTO.getChqBeneficiario())
                        + "¬"
                        + (listaBanBancoTO.getChqNumero() == null ? "B" : "S" + listaBanBancoTO.getChqNumero())
                        + "¬"
                        + (listaBanBancoTO.getChqValor() == null ? "B" : "D" + listaBanBancoTO.getChqValor())
                        + "¬"
                        + (listaBanBancoTO.getChqFechaEmision() == null ? "B" : "S" + listaBanBancoTO.getChqFechaEmision())
                        + "¬"
                        + (listaBanBancoTO.getChqFechaVencimiento() == null ? "B" : "S" + listaBanBancoTO.getChqFechaVencimiento())
                        + "¬"
                        + (listaBanBancoTO.getChqFechaImpresion() == null ? "B" : "S" + listaBanBancoTO.getChqFechaImpresion())
                        + "¬"
                        + (listaBanBancoTO.getChqConCodigo() == null ? "B" : "S" + listaBanBancoTO.getChqConCodigo())
                        + "¬"
                        + (listaBanBancoTO.getChqContableTipo() == null ? "B" : "S" + (listaBanBancoTO.getChqContablePeriodo() + " | " + listaBanBancoTO.getChqContableTipo() + " | " + listaBanBancoTO.getChqContableNumero())));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteChequesReversados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConDetalle> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCHEQUES REVERSADOS");
            listaCabecera.add("S");
            listaCuerpo.add("SContable" + "¬" + "SCuenta" + "¬" + "SEmisión" + "¬" + "SConcepto" + "¬" + "SDocumento" + "¬" + "SValor" + "¬" + "SObservaciones");
            listado.forEach((item) -> {
                listaCuerpo.add(
                        (item.getConContable() == null ? "B" : "S" + item.getConContable().getConContablePK().getConPeriodo() + " | "
                        + item.getConContable().getConContablePK().getConTipo() + "|"
                        + item.getConContable().getConContablePK().getConNumero())
                        + "¬"
                        + (item.getConCuentas() == null ? "B" : "S" + item.getConCuentas().getConCuentasPK().getCtaCodigo() + " | "
                        + item.getConCuentas().getCtaDetalle())
                        + "¬"
                        + (item.getConContable() == null ? "B" : "S" + UtilsDate.fechaFormatoString(item.getConContable().getConFecha(), "yyyy-MM-dd"))
                        + "¬"
                        + (item.getConContable() == null ? "B" : "S" + item.getConContable().getConConcepto())
                        + "¬"
                        + (item.getDetDocumento() == null ? "B" : "S" + item.getDetDocumento())
                        + "¬"
                        + (item.getDetValor() == null ? "B" : "D" + item.getDetValor())
                        + "¬"
                        + (item.getDetObservaciones() == null ? "B" : "S" + item.getDetObservaciones()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoReverso(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanChequeMotivoReversado> banChequeMotivoReversado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReverso motivo");
            listaCabecera.add("S");
            listaCuerpo.add("SCodigo" + "¬" + "SDetalle" + "¬" + "SContable" + "¬" + "SEstado");
            banChequeMotivoReversado.forEach((ChequeMotivoReversado) -> {
                listaCuerpo.add(
                        (ChequeMotivoReversado.getBanChequeMotivoReversadoPK().getBanCodigoMotivo() == null ? "B" : "S" + ChequeMotivoReversado.getBanChequeMotivoReversadoPK().getBanCodigoMotivo()) + "¬"
                        + (ChequeMotivoReversado.getBanDescripcion() == null ? "B" : "S" + ChequeMotivoReversado.getBanDescripcion()) + "¬"
                        + (ChequeMotivoReversado.getBanContable() == null ? "B" : "S" + ChequeMotivoReversado.getBanContable()) + "¬"
                        + (ChequeMotivoReversado.getBanEstado() == true ? "SINACTIVO" : "S") + "¬"
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteMotivoAnulacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanChequeMotivoReversado> banChequeMotivo) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMotivoAnulacion.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), banChequeMotivo);
    }

}
