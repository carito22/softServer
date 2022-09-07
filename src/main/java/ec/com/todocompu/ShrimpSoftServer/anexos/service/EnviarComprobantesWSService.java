package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasPendientesTO;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EnviarComprobantesWSService {

    //retenciones
    @Transactional(propagation = Propagation.NEVER)
    public void enviarAutorizarRetencionesElectronicasLoteAutomatico(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception;

    public String enviarAutorizarRetencionElectronicaLote(String empresa, List<AnxListaRetencionesPendientesTO> listaEnviar, SisInfoTO sisInfoTO) throws Exception;

    public String enviarAutorizarRetencionElectronica(String empresa, String perCodigo, String motCodigo, String compNumero, String tipoAmbiente, char accion, SisFirmaElectronica cajCajaTO, SisInfoTO sisInfoTO) throws Exception;

    public String descargarAutorizarRetencionElectronica(String empresa, String perCodigo, String motCodigo, String compNumero, String tipoAmbiente, SisInfoTO sisInfoTO) throws Exception;

    //facturas
    @Transactional(propagation = Propagation.NEVER)
    public void enviarAutorizarFacturasElectronicaLoteAutomatico(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional(propagation = Propagation.NEVER)
    public String enviarAutorizarFaturasElectronicaLote(String empresa, List<AnxListaVentasPendientesTO> listaEnviar, SisInfoTO sisInfoTO) throws Exception;

    public String enviarAutorizarFacturaElectronica(String empresa, String vtaPerCodigo, String vtaMotCodigo, String vtaNumero, String tipoAmbiente, char accion, SisFirmaElectronica cajCajaTO, SisInfoTO sisInfoTO) throws Exception;

    public String descargarAutorizarFacturaElectronica(String empresa, String vtaPerCodigo, String vtaMotCodigo, String vtaNumero, String tipoAmbiente, SisInfoTO sisInfoTO) throws Exception;

    //liquidacion compras
    public String enviarAutorizarLiquidacionCompras(String empresa, List<AnxListaLiquidacionComprasPendientesTO> listaEnviar, SisInfoTO sisInfoTO) throws Exception;

    public String enviarAutorizarLiquidacionCompras(String empresa, String perCodigo, String motCodigo, String compNumero, String tipoAmbiente, char accion, SisFirmaElectronica cajCajaTO, SisInfoTO sisInfoTO) throws Exception;

    //guia remision
    public String enviarAutorizarGuiaRemision(String empresa, AnxListaGuiaRemisionPendientesTO anxListaGuiaRemisionPendientesTO, SisInfoTO sisInfoTO) throws Exception;

    public String enviarAutorizarGuiaRemision(String empresa, String periodo, String numero, String tipoAmbiente, char accion, SisFirmaElectronica cajCajaTO, SisInfoTO sisInfoTO) throws Exception;

    public SisFirmaElectronica validarRequisitosParaEnviarAutorizacion(SisInfoTO sisInfoTO) throws Exception;

    public void enviarEmailComprobantesPendientes(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception;
}
