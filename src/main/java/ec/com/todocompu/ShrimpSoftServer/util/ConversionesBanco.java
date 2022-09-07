package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanPreavisosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBanco;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoDebito;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoDebitoPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCaja;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuenta;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCuentaPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanPreavisos;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanPreavisosPK;

public class ConversionesBanco {

    public static BanBanco convertirBanBancoTO_BanBanco(BanBancoTO banBancoTO) {
        BanBanco banBanco = new BanBanco();
        banBanco.setBanBancoPK(new BanBancoPK(banBancoTO.getEmpCodigo(), banBancoTO.getBanCodigo()));
        banBanco.setBanNombre(banBancoTO.getBanNombre());
        banBanco.setUsrEmpresa(banBancoTO.getEmpCodigo());
        banBanco.setUsrCodigo(banBancoTO.getUsrInsertaBanco());
        banBanco.setUsrFechaInserta(UtilsValidacion.fechaString_Date(banBancoTO.getUsrFechaInsertaBanco()));
        return banBanco;
    }

    //bancoDebito
    public static BanBancoDebito convertirBanBancoTO_BanBancoDebito(BanBancoDebitoTO banBancoTO) {
        BanBancoDebito banBanco = new BanBancoDebito();
        banBanco.setBanBancoDebitoPK(new BanBancoDebitoPK(banBancoTO.getEmpCodigo(), banBancoTO.getBanCodigo()));
        banBanco.setBanNombre(banBancoTO.getBanNombre());
        banBanco.setUsrEmpresa(banBancoTO.getEmpCodigo());
        banBanco.setUsrCodigo(banBancoTO.getUsrInsertaBanco());
        banBanco.setUsrFechaInserta(UtilsValidacion.fechaString_Date(banBancoTO.getUsrFechaInsertaBanco()));
        return banBanco;
    }

    public static BanBancoTO convertirBanBanco_BanBancoTO(BanBanco banBanco) {
        BanBancoTO banBancoTO = new BanBancoTO();
        banBancoTO.setEmpCodigo(banBanco.getUsrEmpresa());
        banBancoTO.setBanCodigo(banBanco.getBanBancoPK().getBanCodigo());
        banBancoTO.setBanNombre(banBanco.getBanNombre());
        banBancoTO.setUsrFechaInsertaBanco(UtilsValidacion.fechaDate_String(banBanco.getUsrFechaInserta()));
        banBancoTO.setUsrInsertaBanco(banBanco.getUsrEmpresa());
        return banBancoTO;
    }

    public static BanChequeNumeracion convertirChequesNumeracionTO_BanChequesNumeracion(
            BanChequesNumeracionTO banBancoTO) {
        BanChequeNumeracion banBanco = new BanChequeNumeracion();
        banBanco.setBanSecuencial(banBancoTO.getBanSecuencial());
        banBanco.setBanDesde(banBancoTO.getBanDesde());
        banBanco.setBanHasta(banBancoTO.getBanHasta());
        banBanco.setBanCtaEmpresa(banBancoTO.getBanCtaEmpresa());
        banBanco.setBanCtaContable(banBancoTO.getBanCtaContable());
        return banBanco;
    }

    public static BanConciliacion convertirBanConciliacionTO_BanConciliacion(BanConciliacionTO banConciliacionTO) {
        BanConciliacion banConciliacion = new BanConciliacion();
        banConciliacion.setBanConciliacionPK(new BanConciliacionPK(banConciliacionTO.getConcEmpresa(),
                banConciliacionTO.getConcCuentaContable(), banConciliacionTO.getConcCodigo()));
        banConciliacion.setConcHasta(UtilsValidacion.fecha(banConciliacionTO.getConcHasta(), "yyyy-MM-dd"));
        banConciliacion.setConcSaldoEstadoCuenta(banConciliacionTO.getConcSaldoEstadoCuenta());
        banConciliacion.setConcChequesGiradosYNoCobrados(banConciliacionTO.getConcChequesGiradosYNoCobrados());
        banConciliacion.setConcDepositosEnTransito(banConciliacionTO.getConcDepositosEnTransito());
        banConciliacion.setConcPendiente(banConciliacionTO.isConcPendiente());
        banConciliacion.setUsrEmpresa(banConciliacionTO.getConcEmpresa());
        banConciliacion.setUsrCodigo(banConciliacionTO.getUsrCodigo());
        banConciliacion.setUsrFechaInserta(UtilsValidacion.fechaString_Date(banConciliacionTO.getUsrFechaInserta()));
        return banConciliacion;
    }

    public static BanCaja convertirBanCajaTO_BanCaja(BanCajaTO banCajaTO) {
        BanCaja banCaja = new BanCaja();
        banCaja.setBanCajaPK(new BanCajaPK(banCajaTO.getEmpCodigo(), banCajaTO.getCajaCodigo()));
        // banCaja.setCajaCuenta(banCajaTO.getCajaCuenta());
        banCaja.setCajaNombre(banCajaTO.getCajaNombre());
        banCaja.setCtaEmpresa(banCajaTO.getEmpCodigo());
        banCaja.setCtaCuentaContable(banCajaTO.getCajaCuenta());
        banCaja.setUsrEmpresa(banCajaTO.getEmpCodigo());
        banCaja.setUsrCodigo(banCajaTO.getUsrInsertaCaja());
        banCaja.setUsrFechaInserta(UtilsValidacion.fechaString_Date(banCajaTO.getUsrFechaInsertaCaja()));
        return banCaja;
    }

    public static BanCheque convertirBanChequeTO_BanCheque(BanChequeTO banChequeTO) {
        BanCheque banCheque = new BanCheque();

        // banCheque.setBanChequePK(new BanChequePK(
        // banChequeTO.getChqEmpresa(),
        // banChequeTO.getChqCuenta(),
        // banChequeTO.getChqNumero(),
        // banChequeTO.getCbDc()));
        banCheque.setChqCruzado(banChequeTO.isChqCruzado());
        banCheque.setChqNoEsCheque(banChequeTO.isChqNoCheque());
        banCheque.setChqBeneficiario(banChequeTO.getChqBeneficiario());
        banCheque.setChqCantidad(banChequeTO.getChqCantidad());
        banCheque.setChqCiudad(banChequeTO.getChqCiudad());
        banCheque.setChqFecha(UtilsValidacion.fecha(banChequeTO.getChqFecha(), "yyyy-MM-dd"));
        banCheque.setChqImpreso(banChequeTO.isChqImpreso());
        banCheque.setChqRevisado(banChequeTO.isChqRevisado());
        banCheque.setChqEntregado(banChequeTO.isChqEntregado());
        banCheque.setChqEntregadoFecha(UtilsValidacion.fecha(banChequeTO.getChqEntregadoFecha(), "yyyy-MM-dd"));
        banCheque.setChqEntregadoObservacion(banChequeTO.getChqEntregadoObservacion());
        banCheque.setChqSecuencia(banChequeTO.getChqSecuencia());
        banCheque.setDetSecuencia(banChequeTO.getDetSecuencia());
        banCheque.setConcCodigo(banChequeTO.getConcCodigo());
        banCheque.setConcCuentaContable(banChequeTO.getConcCuentaContable());
        banCheque.setConcEmpresa(banChequeTO.getConcEmpresa());
        banCheque.setChqImpresoFecha(UtilsValidacion.fecha(banChequeTO.getChqImpresoFecha(), "yyyy-MM-dd"));
        banCheque.setChqRevisadoFecha(UtilsValidacion.fecha(banChequeTO.getChqRevisadoFecha(), "yyyy-MM-dd"));
        banCheque.setChqRevisadoObservacion(banChequeTO.getChqRevisadoObservacion());
        banCheque.setChqReversado(banChequeTO.isChqReversado());
        banCheque.setChqReversadoFecha(UtilsValidacion.fecha(banChequeTO.getChqReversadoFecha(), "yyyy-MM-dd"));
        banCheque.setChqReversadoObservacion(banChequeTO.getChqReversadoObservacion());
        banCheque.setChqAnulado(banChequeTO.getChqAnulado());
        banCheque.setChqAnuladoFecha(UtilsValidacion.fecha(banChequeTO.getChqAnuladooFecha(), "yyyy-MM-dd"));
        banCheque.setChqAnuladoObservacion(banChequeTO.getChqAnuladoObservacion());
        banCheque.setConcCategoria(banChequeTO.getConcCategoria());

        return banCheque;
    }

    public static BanCheque convertirBanCheque_BanCheque(BanCheque banCheque) {
        BanCheque banChequeAux = new BanCheque();

        banChequeAux.setChqSecuencia(banCheque.getChqSecuencia());
        banChequeAux.setChqBeneficiario(banCheque.getChqBeneficiario());
        banChequeAux.setChqCantidad(banCheque.getChqCantidad());
        banChequeAux.setChqCiudad(banCheque.getChqCiudad());
        banChequeAux.setChqFecha(banCheque.getChqFecha());
        banChequeAux.setChqCruzado(banCheque.getChqCruzado());
        banChequeAux.setChqImpreso(banCheque.getChqImpreso());
        banChequeAux.setChqImpresoFecha(banCheque.getChqImpresoFecha());
        banChequeAux.setChqRevisado(banCheque.getChqRevisado());
        banChequeAux.setChqRevisadoFecha(banCheque.getChqRevisadoFecha());
        banChequeAux.setChqRevisadoObservacion(banCheque.getChqRevisadoObservacion());
        banChequeAux.setChqEntregado(banCheque.getChqEntregado());
        banChequeAux.setChqEntregadoFecha(banCheque.getChqEntregadoFecha());
        banChequeAux.setChqEntregadoObservacion(banCheque.getChqEntregadoObservacion());
        banChequeAux.setChqReversado(banCheque.getChqReversado());
        banChequeAux.setChqReversadoFecha(banCheque.getChqReversadoFecha());
        banChequeAux.setChqReversadoObservacion(banCheque.getChqReversadoObservacion());
        banChequeAux.setChqAnulado(banCheque.getChqAnulado());
        banChequeAux.setChqAnuladoFecha(banCheque.getChqAnuladoFecha());
        banChequeAux.setChqAnuladoObservacion(banCheque.getChqAnuladoObservacion());
        banChequeAux.setChqNoEsCheque(banCheque.getChqNoEsCheque());
        banChequeAux.setDetSecuencia(banCheque.getDetSecuencia());
        banChequeAux.setConcCodigo(banCheque.getConcCodigo());
        banChequeAux.setConcCuentaContable(banCheque.getConcCuentaContable());
        banChequeAux.setConcEmpresa(banCheque.getConcEmpresa());
        banChequeAux.setConcCategoria(banCheque.getConcCategoria());

        return banChequeAux;
    }

    public static BanCuenta convertirBanCuentaTO_BanCuenta(BanCuentaTO banCuentaTO) {
        BanCuenta banCuenta = new BanCuenta();
        banCuenta.setBanCuentaPK(new BanCuentaPK(banCuentaTO.getCtaEmpresa(), banCuentaTO.getCtaContable()));
        // banCuenta.setEmpCodigo(banCuentaTO.getEmpCodigo());
        // banCuenta.setBanCodigo(banCuentaTO.getBanCodigo());
        // banCuenta.setCtaSecuencia(banCuentaTO.getCtaSecuencia() == 0 ? 0 :
        // banCuentaTO.getCtaSecuencia());
        banCuenta.setCtaNumero(banCuentaTO.getCtaNumero());
        banCuenta.setCtaTitular(banCuentaTO.getCtaTitular());
        banCuenta.setCtaOficial(banCuentaTO.getCtaOficial());
        banCuenta.setCtaFormatoCheque(banCuentaTO.getCtaFormatoCheque());
        banCuenta.setCtaNumeracion(banCuentaTO.getCtaNumeracion());

        banCuenta.setCtaCodigoBancario(banCuentaTO.getCtaCodigoBancario());
        banCuenta.setCtaPrefijoBancario(banCuentaTO.getCtaPrefijoBancario());
        // banCuenta.setCtaCuenta(banCuentaTO.getCtaCuenta());
        banCuenta.setUsrEmpresa(banCuentaTO.getCtaEmpresa());
        banCuenta.setUsrCodigo(banCuentaTO.getUsrCodigo());
        banCuenta.setUsrFechaInserta(UtilsValidacion.fechaString_Date(banCuentaTO.getUsrFechaInsertaCuenta()));
        return banCuenta;
    }

    public static BanCuentaTO convertirBanCuenta_BanCuentaTO(BanCuenta banCuenta) {
        BanCuentaTO banCuentaTO = new BanCuentaTO();
        banCuentaTO.setCtaNumero(banCuenta.getCtaNumero());
        banCuentaTO.setCtaTitular(banCuenta.getCtaTitular());
        banCuentaTO.setCtaOficial(banCuenta.getCtaOficial());
        banCuentaTO.setCtaFormatoCheque(banCuenta.getCtaFormatoCheque());
        banCuentaTO.setCtaCodigoBancario(banCuenta.getCtaCodigoBancario());
        banCuentaTO.setCtaPrefijoBancario(banCuenta.getCtaPrefijoBancario());
        banCuentaTO.setCtaEmpresa(banCuenta.getUsrEmpresa());
        banCuentaTO.setUsrInsertaCuenta(banCuenta.getUsrCodigo());
        banCuentaTO.setUsrInsertaEmpresa(banCuenta.getUsrEmpresa());
        banCuentaTO.setCtaNumeracion(banCuenta.getCtaNumeracion());
        banCuentaTO.setCtaContable(banCuenta.getBanCuentaPK().getCtaCuentaContable());//
        banCuentaTO.setUsrFechaInsertaCuenta(UtilsValidacion.fechaDate_String(banCuenta.getUsrFechaInserta()));
        return banCuentaTO;
    }

    public static BanPreavisos convertirBanPreavisosTO_BanPreavisos(BanPreavisosTO banPreavisosTO) {
        BanPreavisos banPreavisos = new BanPreavisos();
        banPreavisos.setBanPreavisosPK(new BanPreavisosPK(banPreavisosTO.getPreEmpresa(),
                banPreavisosTO.getPreCuentaContable(), banPreavisosTO.getPreNombreArchivoGenerado()));
        banPreavisos.setPreFechaRevisionUltimoCheque(
                UtilsValidacion.fecha(banPreavisosTO.getPreFechaRevisionUltimoCheque(), "yyyy-MM-dd HH:mm:ss"));
        banPreavisos.setUsrEmpresa(banPreavisosTO.getUsrEmpresa());
        banPreavisos.setUsrCodigo(banPreavisosTO.getUsrCodigo());
        banPreavisos.setUsrFechaInserta(UtilsValidacion.fechaString_Date(banPreavisosTO.getUsrFechaInserta()));
        return banPreavisos;
    }

    public static BanConciliacion convertirBanConciliacion_BanConciliacion(BanConciliacion banConciliacionAux) {
        BanConciliacion con = new BanConciliacion();
        con.setBanConciliacionPK(banConciliacionAux.getBanConciliacionPK());
        con.setBanCuenta(banConciliacionAux.getBanCuenta());
        con.setConcChequesGiradosYNoCobrados(banConciliacionAux.getConcChequesGiradosYNoCobrados());
        con.setConcDepositosEnTransito(banConciliacionAux.getConcDepositosEnTransito());
        con.setConcHasta(banConciliacionAux.getConcHasta());
        con.setConcPendiente(banConciliacionAux.getConcPendiente());
        con.setConcSaldoEstadoCuenta(banConciliacionAux.getConcSaldoEstadoCuenta());
        con.setUsrCodigo(banConciliacionAux.getUsrCodigo());
        con.setUsrEmpresa(banConciliacionAux.getUsrEmpresa());
        con.setUsrFechaInserta(banConciliacionAux.getUsrFechaInserta());

        return con;
    }
}
