package ec.com.todocompu.ShrimpSoftServer.quartz;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.EnviarComprobantesWSService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasProgramadasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.EmpresaService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.quartz.TO.InvComprasProgramadasTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todocompuWS/quartzController")
public class QuartzController {

    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private ComprasProgramadasService comprasProgramadasService;
    @Autowired
    private EnviarComprobantesWSService enviarComprobantesService;
    @Autowired
    private EmpresaService empresaService;

    @RequestMapping("/enviarComprasProgramadasQuartz")
    public void enviarComprasProgramadasQuartz(Map<String, Object> map) throws Exception {
        String fecha = UtilsDate.convetirDateConFormato(new Date());
        try {
            List<InvComprasProgramadasTO> lista = comprasProgramadasService.invListadoComprasProgramadasTO(fecha);
            System.out.println("numero de tareas a ejecutar**********" + lista.size());
            for (InvComprasProgramadasTO itemLista : lista) {
                System.out.println("generando**********" + new Date());
                comprasProgramadasService.generarInvComprasProgramadas(itemLista, fecha);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", new SisInfoTO("QUARTZ", "QUARTZ", "", "", "WEB")));
        }
    }

    @RequestMapping("/enviarFacturasElectronicasQuartz")
    public void enviarFacturasElectronicasQuartz(Map<String, Object> map) throws Exception {
        System.out.println("**************************Empieza a autorizar comprobantes**************************");
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date inicio = new Date();
            System.out.println("Hora y fecha inicio: " + hourdateFormat.format(inicio));
            for (SisEmpresaTO sisEmpresaTO : empresaService.getListaSisEmpresaTOWeb("QUARTZ")) {
                SisInfoTO sisInfoTO = new SisInfoTO(sisEmpresaTO.getEmpCodigo(), "QUARTZ", "QUARTZ", "QUARTZ", "WEB");
                try {
                    enviarComprobantesService.enviarAutorizarFacturasElectronicaLoteAutomatico(sisEmpresaTO, sisInfoTO);
                } catch (Exception e) {
                    envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
                }
            }
            Date fin = new Date();
            System.out.println("Hora y fecha inicio: " + hourdateFormat.format(fin));
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", new SisInfoTO("", "QUARTZ", "QUARTZ", "QUARTZ", "WEB")));
        }
    }

    @RequestMapping("/enviarRetencionesElectronicasQuartz")
    public void enviarRetencionesElectronicasQuartz(Map<String, Object> map) throws Exception {
        System.out.println("**************************Empieza a autorizar retenciones**************************");
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date inicio = new Date();
            System.out.println("Hora y fecha inicio retenciones: " + hourdateFormat.format(inicio));
            for (SisEmpresaTO sisEmpresaTO : empresaService.getListaSisEmpresaTOWeb("QUARTZ")) {
                SisInfoTO sisInfoTO = new SisInfoTO(sisEmpresaTO.getEmpCodigo(), "QUARTZ", "QUARTZ", "QUARTZ", "WEB");
                try {
                    enviarComprobantesService.enviarAutorizarRetencionesElectronicasLoteAutomatico(sisEmpresaTO, sisInfoTO);
                } catch (Exception e) {
                    envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
                }
            }
            Date fin = new Date();
            System.out.println("Hora y fecha inicio retenciones: " + hourdateFormat.format(fin));
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", new SisInfoTO("", "QUARTZ", "QUARTZ", "QUARTZ", "WEB")));
        }
    }

    @RequestMapping("/enviarEmailComprobantesQuartz")
    public void enviarEmailComprobantesQuartz(Map<String, Object> map) throws Exception {
        try {
            for (SisEmpresaTO sisEmpresaTO : empresaService.getListaSisEmpresaTOWeb("QUARTZ")) {
                SisInfoTO sisInfoTO = new SisInfoTO(sisEmpresaTO.getEmpCodigo(), "QUARTZ", "QUARTZ", "QUARTZ", "WEB");
                try {
                    enviarComprobantesService.enviarEmailComprobantesPendientes(sisEmpresaTO, sisInfoTO);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", new SisInfoTO("", "QUARTZ", "QUARTZ", "QUARTZ", "WEB")));
        }
    }

}
