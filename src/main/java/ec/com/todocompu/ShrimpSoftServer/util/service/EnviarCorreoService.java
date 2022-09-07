package ec.com.todocompu.ShrimpSoftServer.util.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import java.io.File;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioEmailTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.web.ArchivoSoporteTO;
import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EnviarCorreoService {

    @Transactional
    public void enviarError(String clienteServidor, SisErrorTO sisErrorTO);

    @Transactional
    public void enviarErrorPersonalizado(String asunto, String detalle, String codigoEmpresa);

    @Transactional
    public String enviarInformacionPedido(String correoUsuario, String notificacion, SisEmpresaNotificaciones idNotificacion, String empresa) throws GeneralException, Exception;

    @Transactional
    public String enviarCorreoParaTicket(String nombreUsuario, String correoUsuario, String asunto, String descripcion, String empresa, List<ArchivoSoporteTO> listaArchivoSoporteTO) throws GeneralException, Exception;

    @Transactional
    public String enviarComprobantesElectronicos(SisEmailComprobanteElectronicoTO SisEmailComprobanteElectronicoTO, List<File> listAdjunto);

    public String enviarPDFOrdenCompra(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO, List<File> listAdjunto) throws Exception;

    public void enviarPDFOrdenCompraARegistrador(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO, List<File> listAdjunto) throws Exception;

    public boolean comprobarEmails(String destinatarios) throws Exception;

    public void notificarPorCorreoDocumentosNoAutorizadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<String> listado, SisInfoTO sisInfoTO, String titulo) throws GeneralException, MessagingException, AddressException, IOException, Exception;

    //CORREO CUENTAS POR COBRAR
    @Transactional
    public List<String> enviarCorreoParaCuentasPorCobrar(List<CuentasPorCobrarDetalladoTO> lista, String empresa, String asunto, SisEmpresaNotificaciones notificacion) throws GeneralException, Exception;

    // CORREO ROL
    public List<String> enviarCorreoRolPorLote(ConContablePK contablepk, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisEmpresaNotificaciones notificacion) throws Exception;

    public List<String> enviarCorreoRol(Integer rolSecuencial, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisEmpresaNotificaciones notificacion) throws Exception;

    //VENTA AUTORIZADAS 
    public List<String> enviarCorreoFacturasPorLote(List<AnxListaVentaElectronicaTO> listaEnviar, String empresa, SisInfoTO sisInfoTO) throws Exception;

    public List<String> enviarCorreoGuiasPorLote(List<AnxListaGuiaRemisionElectronicaTO> listaEnviar, String empresa) throws Exception;

    public List<String> enviarCorreoRetencionesPorLote(List<AnxListadoCompraElectronicaTO> listaEnviar, String empresa, SisInfoTO sisInfoTO) throws Exception;

    public List<String> enviarCorreoLiquidacionesPorLote(List<AnxListaLiquidacionComprasElectronicaTO> listaEnviar, String empresa, SisInfoTO sisInfoTO) throws Exception;
    
    public String notificarPorCorreoErroresContabilidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<File> listado, List<SisUsuarioEmailTO> receptores, int secuencial) throws Exception;

}
