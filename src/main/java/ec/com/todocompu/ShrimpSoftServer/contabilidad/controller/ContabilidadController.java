package ec.com.todocompu.ShrimpSoftServer.contabilidad.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.report.ReporteContabilidadService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasEspecialesService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasFlujoDetalleService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasFlujoService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.DetalleService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstructuraFlujoService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.EstructuraService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.NumeracionService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.TipoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.PersonaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.ArrayList;
import org.postgresql.util.PSQLException;

@RestController
@RequestMapping("/todocompuWS/contabilidadController")
public class ContabilidadController {

    @Autowired
    private NumeracionService numeracionService;
    @Autowired
    private TipoService tipoService;
    @Autowired
    private CuentasService cuentasService;
    @Autowired
    private CuentasEspecialesService cuentasEspecialesService;
    @Autowired
    private CuentasFlujoService cuentasFlujoService;
    @Autowired
    private CuentasFlujoDetalleService cuentasFlujoDetalleService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private EstructuraService estructuraService;
    @Autowired
    private EstructuraFlujoService estructuraFlujoService;
    @Autowired
    private ReporteContabilidadService reporteContabilidadService;
    @Autowired
    private EnviarCorreoService envioCorreoService;

    @RequestMapping("/getListaConTipoReferencia")
    public List<ConTipo> getListaConTipoReferencia(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String referencia = UtilsJSON.jsonToObjeto(String.class, map.get("referencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipo(empresa, referencia);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }

        return null;
    }

    @RequestMapping("/getListaConTipo")
    public List<ConTipo> getListaConTipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }

        return null;
    }

    @RequestMapping("/getListaConTipoTOFiltrado")
    public List<ConTipoTO> getListaConTipoTOFiltrado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String filtro = UtilsJSON.jsonToObjeto(String.class, map.get("filtro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoTOFiltrado(empresa, filtro);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }

        return null;
    }

    @RequestMapping("/getConContable")
    public ConContable getConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getConContable(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }

        return null;
    }

    @RequestMapping("/desmayorizarConContable")
    public String desmayorizarConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String retorno = "";
        try {
            retorno = contableService.mayorizarDesmayorizarSql(conContablePK, true, sisInfoTO);
            if (retorno.charAt(0) == 'T') {
                contableService.crearSuceso(conContablePK, "U", sisInfoTO);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                retorno = "F" + e.getMessage();
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return retorno;
    }

    @RequestMapping("/eliminarConContable")
    public String eliminarConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String retorno = "";
        try {
            retorno = contableService.eliminarConContable(conContablePK, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                retorno = "F" + e.getMessage();
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return retorno;
    }

    @RequestMapping("/anularReversarConContable")
    public String anularReversarConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        boolean anularReversar = UtilsJSON.jsonToObjeto(boolean.class, map.get("anularReversar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String retorno = "";
        try {
            retorno = contableService.anularReversarSql(conContablePK, anularReversar, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                retorno = "F" + e.getMessage();
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return retorno;
    }

    @RequestMapping("/restaurarConContable")
    public String restaurarConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.restaurarSql(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConTipoTO")
    public List<ConTipoTO> getListaConTipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConTipoTO")
    public ConTipoTO getConTipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getConTipoTO(empresa, tipCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConTipoRrhhTO")
    public List<ConTipoTO> getListaConTipoRrhhTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoRrhhTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConTipoCarteraTO")
    public List<ConTipoTO> getListaConTipoCarteraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoCarteraTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConTipoCarteraAnticiposTO")
    public List<ConTipoTO> getListaConTipoCarteraAnticiposTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoService.getListaConTipoCarteraAnticiposTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConCuentasTO")
    public List<ConCuentasTO> getListaConCuentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasService.getListaConCuentasTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // revisar
    @RequestMapping("/getRangoCuentasTO")
    public List<ConCuentasTO> getRangoCuentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoCuentaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuentaDesde"));
        String codigoCuentaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuentaHasta"));
        int largoCuenta = UtilsJSON.jsonToObjeto(int.class, map.get("largoCuenta"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasService.getRangoCuentasTO(empresa, codigoCuentaDesde, codigoCuentaHasta, largoCuenta, usuario,
                    sisInfoTO);
        } catch (Exception e) {
            e.printStackTrace();
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConCuentasFlujoTO")
    public List<ConCuentasFlujoTO> getListaConCuentasFlujoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasService.getListaConCuentasFlujoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConCuentasFlujoDetalleTO")
    public List<ConCuentasFlujoDetalleTO> getListaConCuentasFlujoDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoDetalleService.getListaConCuentasFlujoDetalleTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBuscarConCuentasTO")
    public List<ConCuentasTO> getListaBuscarConCuentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        String ctaGrupo = UtilsJSON.jsonToObjeto(String.class, map.get("ctaGrupo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasService.getListaBuscarConCuentasTO(empresa, buscar, ctaGrupo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBuscarConCuentasFlujoTOEmpresa")
    public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTOEmpresa(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.getListaBuscarConCuentasFlujoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBuscarConCuentasFlujoTO")
    public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String buscar = UtilsJSON.jsonToObjeto(String.class, map.get("buscar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.getListaBuscarConCuentasFlujoTO(empresa, buscar);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConEstructura")
    public List<ConEstructuraTO> getListaConEstructura(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return estructuraService.getListaConEstructuraTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConEstructuraFlujoTO")
    public List<ConEstructuraFlujoTO> getListaConEstructuraFlujoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return estructuraFlujoService.getListaConEstructuraFlujoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListConContableTO")
    public List<ListaConContableTO> getListConContableTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String referencia = UtilsJSON.jsonToObjeto(String.class, map.get("referencia"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getListConContableTO(empresa, periodo, tipo, busqueda, referencia, nRegistros);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConContableTO")
    public List<ConContableTO> getListaConContableTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        String numContable = UtilsJSON.jsonToObjeto(String.class, map.get("numContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getListaConContableTO(empresa, perCodigo, tipCodigo, numContable);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConDetalleTO")
    public List<ConDetalleTablaTO> getListaConDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.getListaConDetalleTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarConDetallesActivosBiologicos")
    public Boolean buscarConDetallesActivosBiologicos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasEspecialesService.buscarConDetallesActivosBiologicos(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConEstadoResultadosIntegralTO")
    public List<ConFunBalanceResultadosNecTO> getConFunBalanceResultadosNecTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultarDetalle"));
        Boolean excluirAsientoCierre = UtilsJSON.jsonToObjeto(Boolean.class, map.get("excluirAsientoCierre"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getConEstadoResultadosIntegralTO(empresa, sector, fechaDesde, fechaHasta, ocultarDetalle, excluirAsientoCierre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConBalanceResultadoComparativoTO")
    public List<ConBalanceResultadoComparativoTO> getConBalanceResultadoComparativoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde2 = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde2"));
        String fechaHasta2 = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta2"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultarDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        Boolean excluirCierre = false;
        try {
            return contableService.getConBalanceResultadoComparativoTO(empresa, sector, fechaDesde, fechaHasta, fechaDesde2, fechaHasta2, ocultarDetalle, excluirCierre);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConFunBalanceFlujoEfectivo")

    public List<ConBalanceResultadoTO> getConFunBalanceFlujoEfectivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getConFunBalanceFlujoEfectivo(empresa, sector, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConEstadoSituacionFinancieraTO")
    public List<ConFunBalanceGeneralNecTO> getConFunBalanceGeneralNecTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        Boolean ocultar = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultar"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultarDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getConEstadoSituacionFinancieraTO(empresa, sector, fecha, ocultar, ocultarDetalle);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunCuentasSobregiradasTO")
    public List<ConFunBalanceGeneralNecTO> getFunCuentasSobregiradasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("secCodigo"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getFunCuentasSobregiradasTO(empresa, secCodigo, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunBalanceGeneralComparativoTO")
    public List<ConBalanceGeneralComparativoTO> getFunBalanceGeneralComparativoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("secCodigo"));
        String fechaAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("fechaAnterior"));
        String fechaActual = UtilsJSON.jsonToObjeto(String.class, map.get("fechaActual"));
        Boolean ocultar = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getFunBalanceGeneralComparativoTO(empresa, secCodigo, fechaAnterior, fechaActual,
                    ocultar);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBalanceComprobacionTO")
    public List<ConBalanceComprobacionTO> getListaBalanceComprobacionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        Boolean ocultarDetalle = UtilsJSON.jsonToObjeto(Boolean.class, map.get("ocultarDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getListaBalanceComprobacionTO(empresa, codigoSector, fechaInicio, fechaFin,
                    ocultarDetalle);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaMayorAuxiliarTO")
    public List<ConMayorAuxiliarTO> getListaMayorAuxiliarTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoCuentaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuentaDesde"));
        String codigoCuentaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuentaHasta"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getListaMayorAuxiliarTO(empresa, codigoCuentaDesde, codigoCuentaHasta, fechaInicio,
                    fechaFin, sector, false);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaMayorGeneralTO")
    public List<ConMayorGeneralTO> getListaMayorGeneralTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoCuenta = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCuenta"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getListaMayorGeneralTO(empresa, codigoSector, codigoCuenta, fechaFin);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaDiarioAuxiliarTO")
    public List<ConDiarioAuxiliarTO> getListaDiarioAuxiliarTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoTipo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoTipo"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getListaDiarioAuxiliarTO(empresa, codigoTipo, fechaInicio, fechaFin);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarConTipo")
    public MensajeTO insertarConTipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConTipoTO conTipoTO = UtilsJSON.jsonToObjeto(ConTipoTO.class, map.get("conTipoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            return tipoService.insertarConTipo(conTipoTO, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/modificarConTipo")
    public MensajeTO modificarConTipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConTipoTO conTipoTO = UtilsJSON.jsonToObjeto(ConTipoTO.class, map.get("conTipoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            return tipoService.modificarConTipo(conTipoTO, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/eliminarConTipo")
    public MensajeTO eliminarConTipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConTipoTO conTipoTO = UtilsJSON.jsonToObjeto(ConTipoTO.class, map.get("conTipoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            return tipoService.eliminarConTipo(conTipoTO, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/insertarConCuenta")
    public MensajeTO insertarConCuenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasTO conCuentasTO = UtilsJSON.jsonToObjeto(ConCuentasTO.class, map.get("conCuentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(cuentasService.insertarConCuenta(conCuentasTO, sisInfoTO));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/modificarConCuenta")
    public MensajeTO modificarConCuenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasTO conCuentasTO = UtilsJSON.jsonToObjeto(ConCuentasTO.class, map.get("conCuentasTO"));
        String codigoCambiarLlave = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCambiarLlave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(cuentasService.modificarConCuenta(conCuentasTO, codigoCambiarLlave, sisInfoTO));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/eliminarConCuenta")
    public MensajeTO eliminarConCuenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasTO conCuentasTO = UtilsJSON.jsonToObjeto(ConCuentasTO.class, map.get("conCuentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(cuentasService.eliminarConCuenta(conCuentasTO, sisInfoTO));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/buscarConteoDetalleEliminarCuenta")
    public int buscarConteoDetalleEliminarCuenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String cuentaCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.buscarConteoDetalleEliminarCuenta(empCodigo, cuentaCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return 0;
    }

    @RequestMapping("/insertarConCuentaFlujo")
    public boolean insertarConCuentaFlujo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoTO conCuentasFlujoTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoTO.class,
                map.get("conCuentasFlujoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.insertarConCuentaFlujo(conCuentasFlujoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarConCuentaFlujoDetalle")
    public boolean insertarConCuentaFlujoDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoDetalleTO.class,
                map.get("conCuentasFlujoDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoDetalleService.insertarConCuentaFlujoDetalle(conCuentasFlujoDetalleTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarConCuentaFlujo")
    public boolean modificarConCuentaFlujo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoTO conCuentasFlujoTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoTO.class,
                map.get("conCuentasFlujoTO"));
        String codigoCambiarLlave = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCambiarLlave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.modificarConCuentaFlujo(conCuentasFlujoTO, codigoCambiarLlave, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarConCuentaFlujoDetalle")
    public boolean modificarConCuentaFlujoDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoDetalleTO.class,
                map.get("conCuentasFlujoDetalleTO"));
        String codigoCambiarLlave = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCambiarLlave"));
        char formaPagoAnterior = UtilsJSON.jsonToObjeto(char.class, map.get("formaPagoAnterior"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoDetalleService.modificarConCuentaFlujoDetalle(conCuentasFlujoDetalleTO,
                    codigoCambiarLlave, formaPagoAnterior, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarConCuentaFlujoDetalle")
    public String eliminarConCuentaFlujoDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoDetalleTO.class,
                map.get("conCuentasFlujoDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.eliminarConCuentaFlujoDetalle(conCuentasFlujoDetalleTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return "";
    }

    @RequestMapping("/eliminarConCuentaFlujo")
    public String eliminarConCuentaFlujo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConCuentasFlujoTO conCuentasFlujoTO = UtilsJSON.jsonToObjeto(ConCuentasFlujoTO.class,
                map.get("conCuentasFlujoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasFlujoService.eliminarConCuentaFlujo(conCuentasFlujoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return "";
    }

    @RequestMapping("/insertarConContable")
    public MensajeTO insertarConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContableTO conContableTO = UtilsJSON.jsonToObjeto(ConContableTO.class, map.get("conContableTO"));
        List<ConDetalleTO> listaConDetalleTO = UtilsJSON.jsonToList(ConDetalleTO.class, map.get("listaConDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.insertarConContable(conContableTO, listaConDetalleTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarModificarContable")
    public MensajeTO insertarModificarContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContable conContable = UtilsJSON.jsonToObjeto(ConContable.class, map.get("conContable"));
        List<ConDetalle> listaConDetalle = UtilsJSON.jsonToList(ConDetalle.class, map.get("listaConDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            boolean pendiente = conContable.getConPendiente();
            boolean reversado = conContable.getConReversado();
            mensajeTO = contableService.insertarModificarContable(conContable, listaConDetalle, new ArrayList<ConAdjuntosContableWebTO>(), sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T' && !pendiente && !reversado) {
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/getListaConNumeracionTO")
    public List<ConNumeracionTO> getListaConNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return numeracionService.getListaConNumeracionTO(empresa, periodo, tipo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarDetalleContable")
    public ConDetalle buscarDetalleContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Long codDetalle = UtilsJSON.jsonToObjeto(Long.class, map.get("codDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.buscarDetalleContable(codDetalle);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListConDetalle")
    public List<ConDetalle> getListConDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.getListConDetalle(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarConContable")
    public List<ConDetalleTO> buscarConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codEmpresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return detalleService.buscarConContable(codEmpresa, perCodigo, tipCodigo, conNumero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConListaBalanceResultadosVsInventarioTO")
    public List<ConListaBalanceResultadosVsInventarioTO> getConListaBalanceResultadosVsInventarioTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getConListaBalanceResultadosVsInventarioTO(empresa, desde, hasta, sector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // revisar
    @RequestMapping("/getBalanceResultadoMensualizado")
    public List<ConBalanceResultadosMensualizadosTO> getBalanceResultadoMensualizado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getBalanceResultadoMensualizado(empresa, codigoSector, fechaInicio, fechaFin,
                    usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
    
    @RequestMapping("/getBalanceResultadoMensualizadoAntiguo")
    public RetornoTO getBalanceResultadoMensualizadoAntiguo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getBalanceResultadoMensualizadoAntiguo(empresa, codigoSector, fechaInicio, fechaFin,
                    usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/desbloquearConContable")
    public String desbloquearConContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.desbloquearConContable(empresa, periodo, tipo, numero, usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunContablesVerificacionesTO")
    public List<ConFunContablesVerificacionesTO> getFunContablesVerificacionesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getFunContablesVerificacionesTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunListaIPP")
    public List<ConFunIPPTO> getFunListaIPP(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String parametro = UtilsJSON.jsonToObjeto(String.class, map.get("parametro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getFunListaIPP(empresa, fechaInicio, fechaFin, parametro);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaConsumosPendientes")
    public MensajeTO getListaInvConsultaConsumosPendientes(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getListaInvConsultaConsumosPendientes(empCodigo, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/insertarModificarIPP")
    public MensajeTO insertarModificarIPP(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        Date fechaHoraBusqueda = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHoraBusqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = contableService.insertarModificarIPP(empresa, fechaDesde, fechaHasta, tipo,
                    fechaHoraBusqueda, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/insertarModificarContablesIPPTodo")
    public MensajeTO insertarModificarContablesIPPTodo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        Date fechaHoraBusqueda = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHoraBusqueda"));
        List<PrdContabilizarCorridaTO> listaContabilizarCorrida = UtilsJSON.jsonToList(PrdContabilizarCorridaTO.class,
                map.get("listaContabilizarCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            if (listaContabilizarCorrida != null && listaContabilizarCorrida.size() == 1) {
                listaContabilizarCorrida = new ArrayList<>();
            }
            mensajeTO = contableService.insertarModificarContablesIPPTodo(empresa, fechaDesde, fechaHasta,
                    fechaHoraBusqueda, listaContabilizarCorrida, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/insertarModificarContabilizarCorrida")
    public MensajeTO insertarModificarContabilizarCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<PrdContabilizarCorridaTO> listaContabilizarCorrida = UtilsJSON.jsonToList(PrdContabilizarCorridaTO.class,
                map.get("listaContabilizarCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = contableService.insertarModificarContabilizarCorrida(empresa, desde, hasta,
                    listaContabilizarCorrida, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M" + e.getMessage());
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/insertarConContableCierreCuentas")
    public MensajeTO insertarConContableCierreCuentas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContableTO conContableTO = UtilsJSON.jsonToObjeto(ConContableTO.class, map.get("conContableTO"));
        String centroProduccion = UtilsJSON.jsonToObjeto(String.class, map.get("centroProduccion"));
        String conNumero_Contable = UtilsJSON.jsonToObjeto(String.class, map.get("conNumeroContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO = contableService.insertarConContableCierreCuentas(conContableTO, centroProduccion,
                    conNumero_Contable, sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
            return mensajeTO;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            mensajeTO.setMensaje("F<html>" + e.getMessage() + "</html>");
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/getConFunContablesVerificacionesComprasTOs")
    public List<ConFunContablesVerificacionesComprasTO> getConFunContablesVerificacionesComprasTOs(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getConFunContablesVerificacionesComprasTOs(empresa, fechaInicio, fechaFin);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunPersonaTOs")
    public List<PersonaTO> getFunPersonaTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busquedad = UtilsJSON.jsonToObjeto(String.class, map.get("busquedad"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.getFunPersonaTOs(empresa, busquedad);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/validarContables")
    public MensajeTO validarContables(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String conTipo = UtilsJSON.jsonToObjeto(String.class, map.get("conTipo"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        String accionUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("accionUsuario"));
        String bandera = UtilsJSON.jsonToObjeto(String.class, map.get("bandera"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.validarContables(sisInfoTO.getEmpresa(), periodo, conTipo, conNumero, accionUsuario,
                    bandera);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/validarChequesConciliados")
    public List<String> validarChequesConciliados(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String conTipo = UtilsJSON.jsonToObjeto(String.class, map.get("conTipo"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.validarChequesConciliados(sisInfoTO.getEmpresa(), periodo, conTipo, conNumero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteContableDetalle")
    public byte[] generarReporteContableDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ConContableReporteTO> listConContableReporteTO = UtilsJSON.jsonToList(ConContableReporteTO.class,
                map.get("listConContableReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteContableDetalle(usuarioEmpresaReporteTO,
                    listConContableReporteTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteMayorAuxiliar")
    public byte[] generarReporteMayorAuxiliar(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String cuentaContableDesde = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContableDesde"));
        String cuentaContablePadreDesde = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContablePadreDesde"));
        String cuentaContableHasta = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContableHasta"));
        String cuentaContablePadreHasta = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContablePadreHasta"));
        List<ConMayorAuxiliarTO> listConMayorAuxiliarTO = UtilsJSON.jsonToList(ConMayorAuxiliarTO.class,
                map.get("listConMayorAuxiliarTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteMayorAuxiliar(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, cuentaContableDesde, cuentaContablePadreDesde, cuentaContableHasta,
                    cuentaContablePadreHasta, listConMayorAuxiliarTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteDiarioAuxiliar")
    public byte[] generarReporteDiarioAuxiliar(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String codigoTipo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoTipo"));
        List<ConDiarioAuxiliarTO> listConDiarioAuxiliarTO = UtilsJSON.jsonToList(ConDiarioAuxiliarTO.class,
                map.get("listConDiarioAuxiliarTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteDiarioAuxiliar(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, codigoTipo, listConDiarioAuxiliarTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteBalanceComprobacion")
    public byte[] generarReporteBalanceComprobacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConBalanceComprobacionTO> listConBalanceComprobacionTO = UtilsJSON
                .jsonToList(ConBalanceComprobacionTO.class, map.get("listConBalanceComprobacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteBalanceComprobacion(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listConBalanceComprobacionTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteBalanceGeneral")
    public byte[] generarReporteBalanceGeneral(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        List<ConFunBalanceGeneralNecTO> listConFunBalanceGeneralNecTO = UtilsJSON
                .jsonToList(ConFunBalanceGeneralNecTO.class, map.get("listConFunBalanceGeneralNecTO"));
        List<ConBalanceGeneralTO> listConBalanceGeneralTO = UtilsJSON.jsonToList(ConBalanceGeneralTO.class,
                map.get("listConBalanceGeneralTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteBalanceGeneral(usuarioEmpresaReporteTO, fechaHasta,
                    codigoSector, listConFunBalanceGeneralNecTO, listConBalanceGeneralTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteBalanceGeneralComparativo")
    public byte[] generarReporteBalanceGeneralComparativo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("fechaAnterior"));
        String fechaActual = UtilsJSON.jsonToObjeto(String.class, map.get("fechaActual"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        List<ConBalanceGeneralComparativoTO> listConBalanceGeneralComparativoTO = UtilsJSON
                .jsonToList(ConBalanceGeneralComparativoTO.class, map.get("listConBalanceGeneralComparativoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteBalanceGeneralComparativo(usuarioEmpresaReporteTO,
                    fechaAnterior, fechaActual, codigoSector, listConBalanceGeneralComparativoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reportPDF
    @RequestMapping("/generarReporteBalanceResultado")
    public byte[] generarReporteBalanceResultado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        Integer columnasEstadosFinancieros = UtilsJSON.jsonToObjeto(Integer.class,
                map.get("columnasEstadosFinancieros"));
        List<ConFunBalanceResultadosNecTO> listConFunBalanceResultadosNecTO = UtilsJSON
                .jsonToList(ConFunBalanceResultadosNecTO.class, map.get("listConFunBalanceResultadosNecTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteBalanceResultado(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, codigoSector, columnasEstadosFinancieros, listConFunBalanceResultadosNecTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteBalanceResultadoComparativo")
    public byte[] generarReporteBalanceResultadoComparativo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fechaDesde2 = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde2"));
        String fechaHasta2 = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta2"));
        List<ConBalanceResultadoComparativoTO> conBalanceResultadoComparativoTO = UtilsJSON
                .jsonToList(ConBalanceResultadoComparativoTO.class, map.get("conBalanceResultadoComparativoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteBalanceResultadoComparativo(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, fechaDesde2, fechaHasta2, codigoSector, conBalanceResultadoComparativoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteContablesVerificacionesCompras")
    public byte[] generarReporteContablesVerificacionesCompras(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConFunContablesVerificacionesComprasTO> listConFunContablesVerificacionesComprasTO = UtilsJSON.jsonToList(
                ConFunContablesVerificacionesComprasTO.class, map.get("listConFunContablesVerificacionesComprasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteContablesVerificacionesCompras(usuarioEmpresaReporteTO,
                    fechaDesde, fechaHasta, listConFunContablesVerificacionesComprasTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConListaBalanceResultadosVsInventario")
    public byte[] generarReporteConListaBalanceResultadosVsInventario(@RequestBody String json,
            HttpServletResponse response) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<ConListaBalanceResultadosVsInventarioTO> listConListaBalanceResultadosVsInventarioTO = UtilsJSON
                .jsonToList(ConListaBalanceResultadosVsInventarioTO.class,
                        map.get("listConListaBalanceResultadosVsInventarioTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteConListaBalanceResultadosVsInventario(
                    usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listConListaBalanceResultadosVsInventarioTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteContablesVerificacionesErrores")
    public byte[] generarReporteContablesVerificacionesErrores(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO = UtilsJSON
                .jsonToList(ConFunContablesVerificacionesTO.class, map.get("listConFunContablesVerificacionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteContablesVerificacionesErrores(usuarioEmpresaReporteTO,
                    listConFunContablesVerificacionesTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteMayorGeneralDatos")
    public byte[] generarReporteMayorGeneralDatos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContable"));
        Object[][] datos = UtilsJSON.jsonToObjeto(Object[][].class, map.get("datos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteMayorGeneral(usuarioEmpresaReporteTO, fechaHasta,
                    cuentaContable, datos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteMayorGeneral")
    public byte[] generarReporteMayorGeneral(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String cuentaContable = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaContable"));
        List<ConMayorGeneralTO> listConMayorGeneralTO = UtilsJSON.jsonToList(ConMayorGeneralTO.class,
                map.get("listConMayorGeneralTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteMayorGeneral(usuarioEmpresaReporteTO, fechaHasta,
                    cuentaContable, listConMayorGeneralTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCompraContableDetalle")
    public byte[] generarReporteCompraContableDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCompraDetalle> reporteCompraDetalles = UtilsJSON.jsonToList(ReporteCompraDetalle.class,
                map.get("reporteCompraDetalles"));
        List<ConContableReporteTO> list = UtilsJSON.jsonToList(ConContableReporteTO.class, map.get("list"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteContabilidadService.generarReporteCompraContableDetalle(usuarioEmpresaReporteTO,
                    reporteCompraDetalles, list);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerImagenesContable")
    public List<ConAdjuntosContableWebTO> obtenerImagenesContable(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return contableService.convertirStringUTF8(conContablePK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

}
