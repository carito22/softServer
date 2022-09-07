package ec.com.todocompu.ShrimpSoftServer.banco.report;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import java.util.List;

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
import ec.com.todocompu.ShrimpSoftUtils.banco.report.ReporteConciliacionCabecera;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaPostfechadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.Map;

public interface ReporteBancoService {

    public byte[] generarReporteBanco(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaBanBancoTO> listado) throws Exception;

    public byte[] generarReporteCuentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaBanCuentaTO> listado) throws Exception;

    public byte[] generarReporteConciliacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, int estado,
            ReporteConciliacionCabecera conciliacionCabecera,
            List<BanListaConciliacionBancariaTO> listConciliacionCredito,
            List<BanListaConciliacionBancariaTO> listConciliacionDebito) throws Exception;

    public byte[] generarReporteListadoChequeEmisionVencimientoCobrarNumero(
            UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cuenta, String desde, String hasta, String tipo,
            List<BanListaChequeTO> listBanListaChequeTO) throws Exception;

    public byte[] generarReporteCheque(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, BanChequeTO banChequeTO,
            String valorLetra1, String valorLetra2, String nombreReporteCheque, ConDetalle conDetalle,
            String cuentaCodigo) throws Exception;

    public byte[] generarReporteChequesListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado) throws Exception;

    public byte[] generarReporteChequesNoImpresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequesNoImpresosTO> listado) throws Exception;

    public byte[] generarReporteChequesNoRevisados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanFunChequesNoRevisadosTO> listado) throws Exception;

    public byte[] generarReporteChequesNoEntregados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanFunChequesNoEntregadosTO> listado) throws Exception;

    public byte[] generarReporteImprimirChequesPostfechados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanDetalleChequesPosfechadosTO> listado) throws Exception;

    public byte[] generarReporteChequesEmision(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado) throws Exception;

    public byte[] generarReporteChequesImpresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequesImpresosTO> listado) throws Exception;

    public byte[] generarReporteChequesNumero(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado) throws Exception;

    public byte[] generarReporteChequesVencimiento(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado) throws Exception;

    public byte[] generarReporteListadoConciliacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaConciliacionTO> listado) throws Exception;

    public byte[] generarChequesPostfechados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarCobrosDetalleFormaPostfechadoTO> listado, String desde, String hasta) throws Exception;

    public byte[] generarReporteListadoBanCajaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<BanCajaTO> listado) throws Exception;

    public byte[] generarReporteListadoChequesAnulados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConDetalle> listado) throws Exception;

    public Map<String, Object> exportarReporteBancos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaBanBancoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteChequesNoImpresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequesNoImpresosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteChequesNoRevisados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanFunChequesNoRevisadosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteChequesNoEntregados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanFunChequesNoEntregadosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteCuentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaBanCuentaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteCheque(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequeTO> listado, String tipoCheque, String banco, String cuenta) throws Exception;

    public Map<String, Object> exportarReporteChequesPostfechados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanDetalleChequesPosfechadosTO> listado) throws Exception;

    public Map<String, Object> exportarChequesPostfechados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCobrosDetalleFormaPostfechadoTO> listado, String desde, String hasta) throws Exception;

    public Map<String, Object> exportarChequesConciliacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String estado, ReporteConciliacionCabecera conciliacionCabecera, String pendiente, List<BanListaConciliacionBancariaTO> listConciliacionCredito, List<BanListaConciliacionBancariaTO> listConciliacionDebito) throws Exception;

    public Map<String, Object> exportarReporteListadoConciliacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaConciliacionTO> listado) throws Exception;

    public Map<String, Object> exportarReporteBanCajaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanCajaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteChequesImpresos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanListaChequesImpresosTO> listadoChequesCobrar, String tipoCheque) throws Exception;

    public Map<String, Object> exportarReporteChequesReversados(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConDetalle> listado) throws Exception;

    public Map<String, Object> exportarReporteMotivoReverso(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanChequeMotivoReversado> banChequeMotivo) throws Exception;
    
    public byte [] generarReporteMotivoAnulacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<BanChequeMotivoReversado> banChequeMotivo) throws Exception;

}
