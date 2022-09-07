package ec.com.todocompu.ShrimpSoftServer.banco.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.banco.report.ReporteBancoService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BancoCajaService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.BancoService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequeNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.ChequeService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.ConciliacionService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.CuentaService;
import ec.com.todocompu.ShrimpSoftServer.banco.service.PreavisosService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequePreavisadoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanComboCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoEntregadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanFunChequesNoRevisadosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequeTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaChequesNoImpresosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanPreavisosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ConsultaDatosBancoCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanChequesNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.banco.report.ReporteConciliacionCabecera;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;

@RestController
@RequestMapping("/todocompuWS/bancoController")
public class BancoController {

    @Autowired
    private BancoService bancoService;

    @Autowired
    private PreavisosService preavisosService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ConciliacionService conciliacionService;

    @Autowired
    private ChequeNumeracionService chequeNumeracionService;

    @Autowired
    private ChequeService chequeService;

    @Autowired
    private BancoCajaService cajaService;

    @Autowired
    private ReporteBancoService reporteBancoService;

    @Autowired
    private EnviarCorreoService envioCorreoService;

    @Autowired
    private ContableService contableService;

    @RequestMapping("/getListaBanBancoTO")
    public List<ListaBanBancoTO> getListaBanBancoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bancoService.getListaBanBancoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaChequesNumeracionTO")
    public List<ListaBanChequesNumeracionTO> getListaChequesNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getListaChequesNumeracionTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBanCajaTO")
    public List<ListaBanCajaTO> getListaBanCajaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cajaService.getListaBanCajaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaBanCuentaTO")
    public List<ListaBanCuentaTO> getListaBanCuentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.getListaBanCuentaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaChequesNoImpresosTO")
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String cuentaBancaria = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaBancaria"));

        try {
            return chequeService.getListaChequesNoImpresosTO(empresa, cuentaBancaria);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaChequesNoImpresosWebTO")
    public List<BanListaChequesNoImpresosTO> getListaChequesNoImpresosWebTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String cuentaBancaria = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaBancaria"));
        String modulo = UtilsJSON.jsonToObjeto(String.class, map.get("modulo"));

        try {
            return chequeService.getListaChequesNoImpresosWebTO(empresa, cuentaBancaria, modulo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getConsultaDatosBancoCuentaTO")
    public ConsultaDatosBancoCuentaTO getConsultaDatosBancoCuentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bancoService.getConsultaDatosBancoCuentaTO(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/isExisteChequeAimprimir")
    public boolean isExisteChequeAimprimir(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContable"));
        String numeroCheque = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCheque"));
        Long detSecuencia = UtilsJSON.jsonToObjeto(Long.class, map.get("detSecuencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.isExisteChequeAimprimir(empresa, cuentaContable, numeroCheque, detSecuencia);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/insertarBanBancoTO")
    public boolean insertarBanBancoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanBancoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bancoService.insertarBanBancoTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/insertarBanChequeNumeracionTO")
    public boolean insertarBanChequeNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequesNumeracionTO banBancoTO = UtilsJSON.jsonToObjeto(BanChequesNumeracionTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeNumeracionService.insertarBanChequeNumeracionTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/insertarBanCajaTO")
    public boolean insertarBanCajaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCajaTO banCajaTO = UtilsJSON.jsonToObjeto(BanCajaTO.class, map.get("banCajaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cajaService.insertarBanCajaTO(banCajaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/insertarBanCuentaTO")
    public boolean insertarBanCuentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCuentaTO banCuentaTO = UtilsJSON.jsonToObjeto(BanCuentaTO.class, map.get("banCuentaTO"));
        String codigoBanco = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBanco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.insertarBanCuentaTO(banCuentaTO, codigoBanco, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;

    }

    @RequestMapping("/insertarBanChequeTO")
    public String insertarBanChequeTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequeTO banChequeTO = UtilsJSON.jsonToObjeto(BanChequeTO.class, map.get("banChequeTO"));
        String usrInserta = UtilsJSON.jsonToObjeto(String.class, map.get("usrInserta"));
        String numeroCheque = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCheque"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.insertarBanChequeTO(banChequeTO, usrInserta, numeroCheque, empresa, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/modificarFechaBanChequeTO")
    public String modificarFechaBanChequeTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.modificarFechaBanChequeTO(empresa, cuenta, numero, fecha, usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarNumeroBanChequeTO")
    public String modificarNumeroBanChequeTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String numeroNuevo = UtilsJSON.jsonToObjeto(String.class, map.get("numeroNuevo"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.modificarNumeroBanChequeTO(empresa, cuenta, numero, numeroNuevo, usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarBanBancoTO")
    public boolean modificarBanBancoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanBancoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bancoService.modificarBanBancoTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarBanChequeNumeracionTO")
    public boolean modificarBanChequeNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequesNumeracionTO banBancoTO = UtilsJSON.jsonToObjeto(BanChequesNumeracionTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeNumeracionService.modificarBanChequeNumeracionTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarBanCajaTO")
    public boolean modificarBanCajaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCajaTO banCajaTO = UtilsJSON.jsonToObjeto(BanCajaTO.class, map.get("banCajaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cajaService.modificarBanCajaTO(banCajaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarBanCuentaTO")
    public boolean modificarBanCuentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCuentaTO banCuentaTO = UtilsJSON.jsonToObjeto(BanCuentaTO.class, map.get("banCuentaTO"));
        String codigoBanco = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBanco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.modificarBanCuentaTO(banCuentaTO, codigoBanco, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarBanBancoTO")
    public boolean eliminarBanBancoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanBancoTO banBancoTO = UtilsJSON.jsonToObjeto(BanBancoTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bancoService.eliminarBanBancoTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarBanChequeNumeracionTO")
    public boolean eliminarBanChequeNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanChequesNumeracionTO banBancoTO = UtilsJSON.jsonToObjeto(BanChequesNumeracionTO.class, map.get("banBancoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeNumeracionService.eliminarBanChequeNumeracionTO(banBancoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarBanCajaTO")
    public boolean eliminarBanCajaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCajaTO banCajaTO = UtilsJSON.jsonToObjeto(BanCajaTO.class, map.get("banCajaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cajaService.eliminarBanCajaTO(banCajaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarBanCuentaTO")
    public boolean eliminarBanCuentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanCuentaTO banCuentaTO = UtilsJSON.jsonToObjeto(BanCuentaTO.class, map.get("banCuentaTO"));
        String codigoBanco = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBanco"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.eliminarBanCuentaTO(banCuentaTO, codigoBanco, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getBanConciliacionFechaHasta")
    public String getBanConciliacionFechaHasta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.getBanConciliacionFechaHasta(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanComboBancoTO")
    public List<BanComboBancoTO> getBanComboBancoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.getBanComboBancoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/conciliacionHasta")
    public Boolean conciliacionHasta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.conciliacionHasta(empresa, hasta, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/conciliacionPendiente")
    public boolean conciliacionPendiente(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.conciliacionPendiente(empresa, cuentaContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getBanListaConciliacionTO")
    public List<BanListaConciliacionTO> getBanListaConciliacionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.getBanListaConciliacionTO(empresa, buscar);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanListaConciliacionBancariaDebitoTO")
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaDebitoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.getBanListaConciliacionBancariaDebitoTO(empresa, cuenta, codigo, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanListaConciliacionBancariaCreditoTO")
    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaCreditoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.getBanListaConciliacionBancariaCreditoTO(empresa, cuenta, codigo, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanValorSaldoSistema")
    public BigDecimal getBanValorSaldoSistema(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.getBanValorSaldoSistema(empresa, cuenta, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionBanConciliacionTO")
    public String accionBanConciliacionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanConciliacionTO banConciliacionTO = UtilsJSON.jsonToObjeto(BanConciliacionTO.class,
                map.get("banConciliacionTO"));
        List<BanChequeTO> banChequeTOs = UtilsJSON.jsonToList(BanChequeTO.class, map.get("banChequeTOs"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        List<BanConciliacionDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(BanConciliacionDatosAdjuntos.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conciliacionService.accionBanConciliacionTO(banConciliacionTO, banChequeTOs, listadoImagenes, accion, sisInfoTO);
        } catch (GeneralException e) {
            return "F" + e.getMessage();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanListaChequeTO")
    public List<BanListaChequeTO> getBanListaChequeTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getBanListaChequeTO(empresa, cuenta, desde, hasta, tipo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanComboCuentaTO")
    public List<BanComboCuentaTO> getBanComboCuentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return cuentaService.getBanComboCuentaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanFunChequesNoEntregadosTO")
    public List<BanFunChequesNoEntregadosTO> getBanFunChequesNoEntregadosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getBanFunChequesNoEntregadosTO(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarBanFunChequesNoEntregados")
    public List<String> insertarBanFunChequesNoEntregados(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        List<BanFunChequesNoEntregadosTO> banFunChequesNoEntregadosTOs = UtilsJSON
                .jsonToList(BanFunChequesNoEntregadosTO.class, map.get("banFunChequesNoEntregadosTOs"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.insertarBanFunChequesNoEntregados(empresa, cuenta, banFunChequesNoEntregadosTOs,
                    usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanFunChequesNoRevisadosTO")
    public List<BanFunChequesNoRevisadosTO> getBanFunChequesNoRevisadosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getBanFunChequesNoRevisadosTO(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarBanFunChequesNoRevisados")
    public List<String> insertarBanFunChequesNoRevisados(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        List<BanFunChequesNoRevisadosTO> banFunChequesNoRevisadosTOs = UtilsJSON
                .jsonToList(BanFunChequesNoRevisadosTO.class, map.get("banFunChequesNoRevisadosTOs"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respues = chequeService.insertarBanFunChequesNoRevisados(empresa, cuenta, banFunChequesNoRevisadosTOs, usuario,
                    sisInfoTO);
            return respues;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getBanCuentasContablesBancos")
    public List<String> getBanCuentasContablesBancos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.getBanCuentasContablesBancos(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanFunChequesPreavisados")
    public List<BanChequePreavisadoTO> getBanFunChequesPreavisados(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getBanFunChequesPreavisados(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConteoChequesPreavisados")
    public int getConteoChequesPreavisados(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getConteoChequesPreavisados(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return 0;
    }

    @RequestMapping("/insertarPreaviso")
    public String insertarPreaviso(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        BanPreavisosTO banPreavisosTO = UtilsJSON.jsonToObjeto(BanPreavisosTO.class, map.get("banPreavisosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preavisosService.insertarPreaviso(banPreavisosTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarBanPreaviso")
    public String eliminarBanPreaviso(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preavisosService.eliminarBanPreaviso(empresa, usuario, cuenta, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/cambioDeCheque")
    public String cambioDeCheque(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String cuentaActual = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaActual"));
        String chequeAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("chequeAnterior"));
        String chequeNuevo = UtilsJSON.jsonToObjeto(String.class, map.get("chequeNuevo"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String con_tipo_cod = UtilsJSON.jsonToObjeto(String.class, map.get("con_tipo_cod"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = chequeService.cambioDeCheque(cuenta, cuentaActual, chequeAnterior, chequeNuevo,
                    empresa, usuario, observaciones, fecha, con_tipo_cod, sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
            return mensajeTO.getMensaje();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConciliacion")
    public byte[] generarReporteConciliacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        int estado = UtilsJSON.jsonToObjeto(int.class, map.get("estado"));
        ReporteConciliacionCabecera conciliacionCabecera = UtilsJSON.jsonToObjeto(ReporteConciliacionCabecera.class,
                map.get("conciliacionCabecera"));
        List<BanListaConciliacionBancariaTO> listConciliacionCredito = UtilsJSON
                .jsonToList(BanListaConciliacionBancariaTO.class, map.get("listConciliacionCredito"));
        List<BanListaConciliacionBancariaTO> listConciliacionDebito = UtilsJSON
                .jsonToList(BanListaConciliacionBancariaTO.class, map.get("listConciliacionDebito"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteBancoService.generarReporteConciliacion(usuarioEmpresaReporteTO, estado, conciliacionCabecera,
                    listConciliacionCredito, listConciliacionDebito);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoChequeEmisionVencimientoCobrarNumero")
    public byte[] generarReporteListadoChequeEmisionVencimientoCobrarNumero(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        List<BanListaChequeTO> listBanListaChequeTO = UtilsJSON.jsonToList(BanListaChequeTO.class,
                map.get("listBanListaChequeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteBancoService.generarReporteListadoChequeEmisionVencimientoCobrarNumero(
                    usuarioEmpresaReporteTO, cuenta, desde, hasta, tipo, listBanListaChequeTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCheque")
    public byte[] generarReporteCheque(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        BanChequeTO banChequeTO = UtilsJSON.jsonToObjeto(BanChequeTO.class, map.get("banChequeTO"));
        String valorLetra1 = UtilsJSON.jsonToObjeto(String.class, map.get("valorLetra1"));
        String valorLetra2 = UtilsJSON.jsonToObjeto(String.class, map.get("valorLetra2"));
        String nombreReporteCheque = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporteCheque"));
        ConDetalle conDetalle = UtilsJSON.jsonToObjeto(ConDetalle.class, map.get("conDetalle"));
        String cuentaCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteBancoService.generarReporteCheque(usuarioEmpresaReporteTO, banChequeTO, valorLetra1,
                    valorLetra2, nombreReporteCheque, conDetalle, cuentaCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBanChequeSecuencial")
    public Object getBanChequeSecuencial(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cuenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return chequeService.getBanChequeSecuencial(empresa, cuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getValidarCuentaContableConBancoExiste")
    public List<String> getValidarCuentaContableConBancoExiste(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String banEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("banEmpresa"));
        String banCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("banCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentaService.getValidarCuentaContableConBancoExiste(banEmpresa, banCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

}
