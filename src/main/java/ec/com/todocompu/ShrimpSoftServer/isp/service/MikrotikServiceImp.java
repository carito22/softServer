/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.service;

import ec.com.todocompu.ShrimpSoftServer.caja.dao.CajaDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDetalleVentasDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteContratoService;
import ec.com.todocompu.ShrimpSoftServer.mikrotik.ApiConnection;
import ec.com.todocompu.ShrimpSoftServer.mikrotik.MikrotikApiException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleVentas;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.isp.TO.FirewallContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.isp.TO.WisproDataTO;
import ec.com.todocompu.ShrimpSoftUtils.isp.entity.IspReporteArcotel;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.net.SocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class MikrotikServiceImp implements MikrotikService {

    ApiConnection conn;

    @Autowired
    private SisEmpresaParametrosMikrotikService sisEmpresaParametrosMikrotikService;
    @Autowired
    private ClienteContratoService clienteContratoService;
    @Autowired
    private CajaDao cajaDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private CobrosDetalleVentasDao cobrosDetalleVentasDao;
    @Autowired
    private VentasDao invVentasDao;

    @Override
    public boolean conexion(String ip, String name, String password) throws Exception {
        try {
            conn = ApiConnection.connect(SocketFactory.getDefault(), ip, ApiConnection.DEFAULT_PORT, 2000);
            conn.login(name, password);
        } catch (MikrotikApiException m) {
            throw new GeneralException("Error conexión con mikrotik: " + m);
        } catch (Exception e) {
            throw new GeneralException("Error: " + e);
        }
        return true;
    }

    @Override
    public List<Map<String, String>> listarFirewallAddressList(String empresa) throws Exception {
        List<Map<String, String>> resultado = null;
        SisEmpresaParametrosMikrotik conf = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(empresa);
        if (conf != null && conexion(conf.getParIp(), conf.getParName(), conf.getParPassword())) {
            resultado = conn.execute("/ip/firewall/address-list/print");
        }
        return resultado;
    }

    public List<Map<String, String>> listarAddressList(String empresa) throws Exception {
        List<Map<String, String>> resultado = null;
        SisEmpresaParametrosMikrotik conf = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(empresa);
        if (conf != null && conexion(conf.getParIp(), conf.getParName(), conf.getParPassword())) {
            resultado = conn.execute("/ip/address/print");
        }
        return resultado;
    }

    @Override
    public List<FirewallContratoTO> listarClientesAddressFirewall(String empresa) throws Exception {
        List<FirewallContratoTO> respuesta = new ArrayList<>();
        List<Map<String, String>> listaAddressFirewall = listarFirewallAddressList(empresa);
        if (listaAddressFirewall != null && listaAddressFirewall.size() > 0) {
            List<InvClienteContratoTO> listaClientes = clienteContratoService.getListarInvClienteContratoTO(empresa, null, null, null);
            if (listaClientes != null && listaClientes.size() > 0) {
                for (Map<String, String> item : listaAddressFirewall) {
                    listaClientes.stream()
                            .filter((cliente) -> (item.get("address").equalsIgnoreCase(cliente.getCliDireccionIp())))
                            .map((inters) -> {
                                FirewallContratoTO firew = new FirewallContratoTO();
                                firew.setCliCodigo(inters.getCliCodigo());
                                firew.setCliId(inters.getCliIdNumero());
                                firew.setCliNombre(inters.getCliRazonSocial());
                                firew.setAddress(inters.getCliDireccionIp());
                                firew.setCliFechaCorte(inters.getCliFechaCorte());
                                firew.setNombreList(item.get("list"));
                                firew.setComment(item.get("comment"));
                                firew.setDisabled((item.get("disabled") != null && item.get("disabled").equals("true")));
                                return firew;
                            }).forEach((itemDetalle) -> {
                        itemDetalle.setId(respuesta.size());
                        respuesta.add(itemDetalle);
                    });
                }
            }
        }

        return respuesta;
    }

    @Override
    public List<Map<String, String>> listarFirewallRules(String empresa) throws Exception {
        List<Map<String, String>> resultado = null;
        SisEmpresaParametrosMikrotik conf = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(empresa);
        if (conexion(conf.getParIp(), conf.getParName(), conf.getParPassword())) {
            resultado = conn.execute("/ip/firewall/filter/print all stats");
        }
        return resultado;
    }

    @Override
    public String habilitarFirewall(String empresa, String id, boolean habilitar) throws Exception {
        try {
            String resp = "FNo se logró" + (habilitar ? " habilitar" : " deshabilitar") + " firewall id: " + id;
            SisEmpresaParametrosMikrotik conf = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(empresa);
            String disabled = habilitar ? "no" : "yes";
            conexion(conf.getParIp(), conf.getParName(), conf.getParPassword());
            String comando = "/ip/firewall/filter/set" + " .id=" + id + " disabled=" + disabled;
            if (conn.execute(comando) != null) {
                resp = "TSe logró" + (habilitar ? " habilitar" : " deshabilitar") + " firewall id: " + id;
            }
            return resp;
        } catch (MikrotikApiException m) {
            throw new GeneralException("Error conexión con mikrotik: " + m);
        } catch (Exception e) {
            throw new GeneralException("Error: " + e);
        }
    }

    @Override
    public String actualizarEstadoFirewallAddress(String empresa, String ip, boolean habilitar, SisInfoTO sisInfoTO) throws Exception {
        String resp = "FNo se logró" + (habilitar ? " habilitar" : " deshabilitar") + " firewall address: " + ip;
        try {
            SisEmpresaParametrosMikrotik conf = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(empresa);
            String estado = habilitar ? "enable" : "disable";
            boolean allowExecute = true;
            conexion(conf.getParIp(), conf.getParName(), conf.getParPassword());
            List<Map<String, String>> listAddress = conn.execute("/ip/firewall/address-list/print" + " where address=" + ip);

            if (listAddress != null && listAddress.size() > 0) {
                for (int i = 0; i < listAddress.size(); i++) {
                    String id = listAddress.get(i).get(".id");
                    String estadoMikrotik = listAddress.get(i).get("disabled");

                    if (!habilitar && !estadoMikrotik.equals("false")) {
                        resp = "FEl servicio se encuentra cortado de la siguiente IP: " + ip;
                        allowExecute = false;
                    }
                    if (habilitar && estadoMikrotik.equals("false")) {
                        resp = "FEl servicio se encuentra habilitado de la siguiente IP: " + ip;
                        allowExecute = false;
                    }
                    if (allowExecute) {
                        conn.execute("/ip/firewall/address-list/" + estado + " .id=" + id);
                        resp = "TSe logró" + (habilitar ? " habilitar" : " deshabilitar") + " firewall address: " + ip;
                        //crear suceso
                        if (sisInfoTO != null) {
                            SisSuceso sisSucesoContrato = new SisSuceso();
                            sisSucesoContrato.setSusSuceso("INSERT");
                            sisSucesoContrato.setSusTabla("inventario.inv_cliente_contrato");
                            sisSucesoContrato.setSusDetalle("Se " + (habilitar ? "habilitó " : "deshabilitó") + " correctamente el servicio con IP:" + ip + "(Mikrotik)");
                            sisSucesoContrato.setSusClave(ip);
                            sisSucesoContrato.setSusFecha(UtilsDate.timestamp());
                            sisSucesoContrato.setSusMac(sisInfoTO.getMac());
                            sisSucesoContrato.setDetEmpresa(sisInfoTO.getEmpresa());
                            sisSucesoContrato.setSisUsuario(new SisUsuario(sisInfoTO.getUsuario()));
                            sucesoDao.insertar(sisSucesoContrato);
                        }
                        //fecha corte
                        //contrato:"122" | nombres:"" | apellidos"" | cedula:""
                        String contrato = listAddress.get(i).get("comment") != null ? listAddress.get(i).get("comment").split("\\|")[0] : null;
                        String nroContrato = null;
                        if (contrato != null) {
                            if (contrato.split(":") != null && contrato.split(":").length == 2) {
                                nroContrato = contrato.split(":")[1];
                            }
                            if (nroContrato != null) {
                                InvClienteContrato contra = clienteContratoService.obtenerInvClienteContrato(empresa, nroContrato);
                                if (contra != null) {
                                    if (!habilitar) {
                                        contra.setCliFechaCorte(UtilsDate.timestamp());
                                    } else {
                                        contra.setCliFechaCorte(null);
                                    }
                                }
                            }
                        }
                        //

                    }
                }
            } else {
                resp = "FLa IP no se encuentra registrada: " + ip;
            }

        } catch (MikrotikApiException m) {
            resp = "FError conexión con mikrotik: " + m;
        } catch (Exception e) {
            resp = "FError: " + e;
        }
        return resp;
    }

    @Override
    public Map<String, Object> obtenerDatosFirewallAddress(String empresa, String usuarioCodigo) throws Exception {
        Map<String, Object> respuesta = new HashMap<>();
        CajCajaTO caja = cajaDao.getCajCajaTO(empresa, usuarioCodigo);
        respuesta.put("cajCajaTO", caja);
        return respuesta;
    }

    @Override
    public List<IspReporteArcotel> listarIspReporteArcotel(String empresa, String busqueda) throws Exception {
        String sqlBusqueda = "";
        if (busqueda != null && !"".equals(busqueda)) {
            sqlBusqueda = " AND (dir_detalle || cliente.cli_codigo || cli_id_numero || cli_razon_social || cli_direccion_ip || cli_tipo_plan || tipo.cli_descripcion ||  cli_ciudad || cli_distancia "
                    + " LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda + "') END || ' ', ' ', '%'));";
        }
        String sql = "select contrato.cli_secuencial contrato_secuencial, "
                + "contrato.cli_establecimiento contrato_establecimiento, "
                + "CASE WHEN establ.dir_detalle is null THEN "
                + " (SELECT cli_direccion FROM inventario.inv_cliente cl "
                + "  WHERE cl.cli_empresa = contrato.cli_empresa  "
                + "  AND cl.cli_codigo = contrato.cli_codigo) ELSE establ.dir_detalle END as "
                + "contrato_establecimiento_direccion,"
                + "cliente.cli_empresa contrato_cliente_empresa, "
                + "cliente.cli_codigo contrato_cliente_codigo, "
                + "cliente.cli_id_numero contrato_cliente_identificacion, "
                + "cliente.cli_razon_social contrato_cliente_razon_social, "
                + "contrato.cli_direccion_ip contrato_direccion_ip, "
                + "contrato.cli_tipo_plan contrato_tipo_plan, "
                + "contrato.cli_comparticion contrato_comparticion, "
                + "tipo.cli_descripcion contrato_tipo_descripcion, "
                + "cliente.cli_ciudad contrato_cliente_ciudad, "
                + "cliente.cli_celular contrato_cliente_telefono, "
                + "contrato.cli_distancia contrato_distancia, "
                + "contrato.cli_fecha_consumo contrato_fecha_consumo, "
                + "contrato.cli_tarifa contrato_tarifa, "
                + "contrato.cli_downlink contrato_down_link, "
                + "contrato.cli_uplink contrato_up_link "
                + "from inventario.inv_cliente cliente "
                + "inner join inventario.inv_cliente_contrato contrato "
                + "on contrato.cli_codigo = cliente.cli_codigo "
                + "and contrato.cli_empresa = cliente.cli_empresa "
                + "left join inventario.inv_cliente_contrato_tipo tipo "
                + "on tipo.cli_codigo = contrato.cli_tipo_codigo "
                + "and tipo.cli_empresa = contrato.cli_tipo_empresa "
                + "left join inventario.inv_clientes_direcciones establ "
                + "on establ.dir_codigo_establecimiento = contrato.cli_establecimiento "
                + "and establ.cli_codigo = contrato.cli_codigo "
                + "and establ.cli_empresa = contrato.cli_empresa "
                + "where cliente.cli_empresa ='" + empresa + "' " + " AND cliente.cli_inactivo= FALSE " + sqlBusqueda;

        List<IspReporteArcotel> listado = genericSQLDao.obtenerPorSql(sql, IspReporteArcotel.class);
        return listado;
    }

    @Override
    public Map<String, Object> actualizarIpContratosListado(SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> respuesta = new HashMap<>();
        List<String> mensajes = new ArrayList<>();
        String empresa = sisInfoTO.getEmpresa();
        //traer todos los firewall
        List<Map<String, String>> listaAddressFirewall = listarFirewallAddressList(empresa);
        if (listaAddressFirewall != null && listaAddressFirewall.size() > 0) {
            //filtrar quienen tienen comentarios
            List<Map<String, String>> filtrados = new ArrayList<>();
            listaAddressFirewall.stream()
                    .filter((item) -> (item.get("comment") != null))
                    .map((inters) -> {
                        return inters;
                    }).forEach((itemDetalle) -> {
                filtrados.add(itemDetalle);
            });

            for (Map<String, String> item : filtrados) {
                String nroContrato = null;
                //contrato:"122" | nombres:"" | apellidos"" | cedula:""
                String contrato = item.get("comment").split("\\|")[0];
                if (contrato != null) {
                    if (contrato.split(":") != null && contrato.split(":").length == 2) {
                        nroContrato = contrato.split(":")[1];
                    }
                    if (nroContrato != null) {
                        //actualizar IP en contrato
                        InvClienteContrato invClienteContrato = clienteContratoService.obtenerInvClienteContrato(empresa, nroContrato);
                        if (invClienteContrato != null) {
                            String resp = clienteContratoService.modificarIPInvClienteContrato(invClienteContrato.getCliSecuencial(), item.get("address"), sisInfoTO);
                            if (resp != null) {
                                if (resp.substring(0, 1).equals("T")) {

                                }
                                mensajes.add(resp);
                            } else {
                                mensajes.add("FError al actualizar IP del contrato: " + nroContrato + " de la siguiente IP: " + item.get("address"));
                            }
                        } else {
                            mensajes.add("FEl contrato: " + nroContrato + " no existe de la siguiente IP: " + item.get("address"));
                        }
                    } else {
                        mensajes.add("FEl número de contrato no se encuentra dentro del comentario de la siguiente IP: " + item.get("address"));
                    }
                } else {
                    mensajes.add("FLa estructura del comentario debe ser separado por el caracter '|' de la siguiente IP: " + item.get("address"));
                }
            }
        } else {
            mensajes.add("FNo se se encontratos contratos para actualizar.");
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }

    @Override
    public Map<String, Object> cortarServicioPorLote(SisInfoTO sisInfoTO, List<CuentasPorCobrarDetalladoTO> listaEnviar) throws Exception {
        Map<String, Object> respuesta = new HashMap<>();
        List<String> mensajes = new ArrayList<>();
        List<CuentasPorCobrarDetalladoTO> correctos = new ArrayList<>();
        String empresa = sisInfoTO.getEmpresa();
        List<WisproDataTO> listaContratosWispro = new ArrayList<>();
        //verificar si tiene parametrosWispro o config de mikrotik
        SisEmpresaParametros parametrosEmpresa = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
        SisEmpresaParametrosMikrotik conf = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(empresa);
        boolean tieneConfMikrotik = conf != null;
        boolean tieneConfWispro = parametrosEmpresa != null && parametrosEmpresa.getParTokenWispro() != null && !parametrosEmpresa.getParTokenWispro().equals("");

        if (tieneConfWispro) {
            Map<String, Object> rptaContratos = sisEmpresaParametrosMikrotikService.listarContratosWispro(empresa);
            if (rptaContratos != null && rptaContratos.get("mensaje") != null) {
                String mensaje = (String) rptaContratos.get("mensaje");
                if (mensaje.substring(0, 1).equals("T")) {
                    listaContratosWispro = (List<WisproDataTO>) rptaContratos.get("respuesta");
                } else {
                    throw new GeneralException(mensaje);
                }
            }
        }

        if (!tieneConfMikrotik && !tieneConfWispro) {
            throw new GeneralException("No tiene configuración de mikrotik ni token de wispro");
        } else {
            if (listaEnviar != null) {
                for (CuentasPorCobrarDetalladoTO item : listaEnviar) {
                    String mensajeRpta = "";
                    if (item.getCxcdContrato() != null) {
                        if (tieneConfMikrotik) {
                            mensajeRpta = deshabilitarContrato(item, empresa, new ArrayList<WisproDataTO>(), true, false, sisInfoTO);
                        }

                        if (tieneConfWispro && mensajeRpta.isEmpty()) {
                            mensajeRpta = deshabilitarContrato(item, empresa, listaContratosWispro, false, true, sisInfoTO);
                        }

                        //No hubo respuesta de mikrotik ni wispro
                        if (mensajeRpta.isEmpty()) {
                            mensajeRpta = "FNo se obtuvo respuesta en mikrotik ni wispro";
                        } else {
                            if (mensajeRpta.substring(0, 1).equals("T")) {
                                correctos.add(item);
                            }
                        }
                        mensajes.add(mensajeRpta);
                    } else {
                        mensajes.add("FEl contrato: <strong>" + item.getCxcdContrato() + "</strong> del cliente " + item.getCxcdClienteId() + " no es valido");
                    }

                }
                respuesta.put("correctos", correctos);
                respuesta.put("mensajes", mensajes);
                return respuesta;
            } else {
                throw new GeneralException("Lista vacía");
            }
        }
    }

    public String deshabilitarContrato(CuentasPorCobrarDetalladoTO item, String empresa, List<WisproDataTO> listaContratosWispro, boolean esMikrotik, boolean esWispro, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        if (esMikrotik) {
            //obtener contrato segun numero de contrato
            InvClienteContrato contrato = clienteContratoService.obtenerInvClienteContrato(empresa, item.getCxcdContrato());
            if (contrato != null) {
                String rpta = actualizarEstadoFirewallAddress(empresa, contrato.getCliDireccionIp(), false, sisInfoTO);
                if (rpta != null && rpta.substring(0, 1).equals("T")) {
                    mensaje = rpta + " del cliente <strong>" + item.getCxcdClienteId() + "</strong>";
                } else {
                    mensaje = rpta;
                }
            } else {
                mensaje = "FEl contrato: <strong>" + item.getCxcdContrato() + "</strong> del cliente " + item.getCxcdClienteId() + " no existe";
            }
        } else {
            if (esWispro) {
                if (listaContratosWispro != null && listaContratosWispro.size() > 0) {
                    List<WisproDataTO> contratoWisproFiltrado = listaContratosWispro.stream()
                            .filter(x -> x.getPublic_id().equals(item.getCxcdContrato()))
                            .collect(Collectors.toList());
                    if (contratoWisproFiltrado.size() > 0) {
                        Map<String, Object> datos = new HashMap<>();
                        WisproDataTO wisproCon = contratoWisproFiltrado.get(0);
                        wisproCon.setCliCodigo(item.getCxcdClienteCodigo());
                        wisproCon.setState("disabled");
                        datos.put("id", wisproCon.getId());
                        datos.put("json", UtilsJSON.objetoToJson(wisproCon));
                        datos.put("wisproDataTO", wisproCon);
                        datos.put("sisInfoTO", sisInfoTO);
                        Map<String, Object> rptaWispro = sisEmpresaParametrosMikrotikService.actualizarEstadoContrato(datos);
                        if (rptaWispro != null) {
                            if (rptaWispro.get("mensaje") != null) {
                                String mensajeRpta = (String) rptaWispro.get("mensaje");
                                if (mensajeRpta != null && mensajeRpta.substring(0, 1).equals("T")) {
                                    mensaje = "TSe deshabilitó correctamente el servicio con contrato: <strong>" + item.getCxcdContrato() + "</strong> del cliente " + item.getCxcdClienteId();
                                } else {
                                    mensaje = "FOcurrió un error al intentar deshabilitar servicio con contrato: <strong>" + item.getCxcdContrato() + "</strong> del cliente " + item.getCxcdClienteId();
                                }
                            } else {
                                mensaje = "FError al intentar deshabilitar servicio con contrato: <strong>" + item.getCxcdContrato() + "</strong> del cliente " + item.getCxcdClienteId();
                            }
                        } else {
                            mensaje = "FNo se obtuvo respuesta al intentar deshabilitar servicio con contrato: <strong>" + item.getCxcdContrato() + "</strong> del cliente " + item.getCxcdClienteId();
                        }
                    }
                } else {
                    mensaje = "FEl contrato: <strong>" + item.getCxcdContrato() + "</strong> del cliente " + item.getCxcdClienteId() + " no existe en Wispro";
                }
            }
        }
        return mensaje;

    }

    @Override
    public Map<String, Object> activarServicioEnCobros(SisInfoTO sisInfoTO, CarCobrosPK pk) throws Exception {
        Map<String, Object> respuesta = new HashMap<>();
        List<String> mensajes = new ArrayList<>();
        List<CarCobrosDetalleVentas> carCobrosDetalleVentas = cobrosDetalleVentasDao.listarDetallesVentasPorCobro(
                pk.getCobEmpresa(),
                pk.getCobPeriodo(),
                pk.getCobTipo(),
                pk.getCobNumero());
        if (carCobrosDetalleVentas != null && carCobrosDetalleVentas.size() > 0) {
            //contrato
            List<WisproDataTO> listaContratosWispro = new ArrayList<>();
            SisEmpresaParametros parametrosEmpresa = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, pk.getCobEmpresa());
            SisEmpresaParametrosMikrotik conf = sisEmpresaParametrosMikrotikService.obtenerConfiguracionMikrotik(pk.getCobEmpresa());
            boolean tieneConfMikrotik = conf != null;
            boolean tieneConfWispro = parametrosEmpresa != null && parametrosEmpresa.getParTokenWispro() != null && !parametrosEmpresa.getParTokenWispro().equals("");
            if (tieneConfWispro) {
                Map<String, Object> rptaContratos = sisEmpresaParametrosMikrotikService.listarContratosWispro(pk.getCobEmpresa());
                if (rptaContratos != null && rptaContratos.get("mensaje") != null) {
                    String mensaje = (String) rptaContratos.get("mensaje");
                    if (mensaje.substring(0, 1).equals("T")) {
                        listaContratosWispro = (List<WisproDataTO>) rptaContratos.get("respuesta");
                    }
                }
            }

            for (CarCobrosDetalleVentas item : carCobrosDetalleVentas) {
                String vtaPk = item.getVtaPeriodo() + "|" + item.getVtaMotivo() + "|" + item.getVtaNumero();
                InvVentasTO ventaTO = invVentasDao.getInvVentasTO(item.getVtaEmpresa(), item.getVtaPeriodo(), item.getVtaMotivo(), item.getVtaNumero());
                if (ventaTO.getVtaRecurrente() != null && ventaTO.getVtaRecurrente() > 1) {
                    InvClienteContrato invClienteContrato = clienteContratoService.obtenerInvClienteContrato(ventaTO.getVtaRecurrente());
                    if (invClienteContrato != null) {
                        //actualizar estado de wispro o mikrotik
                        String mensajeRpta = "";
                        if (tieneConfMikrotik) {
                            //actualiza fecha de corte segun enabled
                            String rpta = actualizarEstadoFirewallAddress(item.getVtaEmpresa(), invClienteContrato.getCliDireccionIp(), true, null);
                            if (rpta != null && rpta.substring(0, 1).equals("T")) {
                                mensajeRpta = rpta + " con contrato:" + invClienteContrato.getCliNumeroContrato() + " del cliente " + invClienteContrato.getInvCliente().getCliIdNumero();
                                //crear suceso 
                                SisSuceso sisSucesoContrato = new SisSuceso();
                                sisSucesoContrato.setSusSuceso("INSERT");
                                sisSucesoContrato.setSusTabla("inventario.inv_cliente_contrato");
                                sisSucesoContrato.setSusDetalle(mensajeRpta.substring(1) + " (Mikrotik) desde cobro");
                                sisSucesoContrato.setSusClave(invClienteContrato.getCliNumeroContrato() + " | " + invClienteContrato.getInvCliente().getCliIdNumero());
                                sisSucesoContrato.setSusFecha(UtilsDate.timestamp());
                                sisSucesoContrato.setSusMac("");
                                sisSucesoContrato.setDetEmpresa(item.getVtaEmpresa());
                                sisSucesoContrato.setSisUsuario(new SisUsuario(sisInfoTO.getUsuario()));
                                sucesoDao.insertar(sisSucesoContrato);
                            } else {
                                mensajeRpta = rpta;
                            }
                        }

                        if (tieneConfWispro && listaContratosWispro.size() > 0 && mensajeRpta.isEmpty()) {
                            List<WisproDataTO> contratoWisproFiltrado = listaContratosWispro.stream()
                                    .filter(x -> x.getPublic_id().equals(invClienteContrato.getCliNumeroContrato()))
                                    .collect(Collectors.toList());
                            if (contratoWisproFiltrado.size() > 0) {
                                Map<String, Object> datos = new HashMap<>();
                                WisproDataTO wisproCon = contratoWisproFiltrado.get(0);
                                wisproCon.setState("enabled");
                                datos.put("id", wisproCon.getId());
                                datos.put("json", UtilsJSON.objetoToJson(wisproCon));
                                datos.put("enabled", true);
                                datos.put("crearSuceso", false);
                                //actualiza fecha de corte segun enabled
                                Map<String, Object> rptaWispro = sisEmpresaParametrosMikrotikService.actualizarEstadoContrato(datos);

                                if (rptaWispro != null) {
                                    if (rptaWispro.get("mensaje") != null) {
                                        String mensajeRpta2 = (String) rptaWispro.get("mensaje");
                                        if (mensajeRpta2 != null && mensajeRpta2.substring(0, 1).equals("T")) {
                                            mensajeRpta = "TSe habilitó correctamente el servicio con contrato: " + invClienteContrato.getCliNumeroContrato() + " del cliente " + invClienteContrato.getInvCliente().getCliIdNumero();
                                            //crear suceso 
                                            SisSuceso sisSucesoContrato = new SisSuceso();
                                            sisSucesoContrato.setSusSuceso("INSERT");
                                            sisSucesoContrato.setSusTabla("inventario.inv_cliente_contrato");
                                            sisSucesoContrato.setSusDetalle(mensajeRpta + " (Wispro) desde cobro");
                                            sisSucesoContrato.setSusClave(invClienteContrato.getCliNumeroContrato() + " | " + invClienteContrato.getInvCliente().getCliIdNumero());
                                            sisSucesoContrato.setSusFecha(UtilsDate.timestamp());
                                            sisSucesoContrato.setSusMac("");
                                            sisSucesoContrato.setDetEmpresa(item.getVtaEmpresa());
                                            sisSucesoContrato.setSisUsuario(new SisUsuario(sisInfoTO.getUsuario()));
                                            sucesoDao.insertar(sisSucesoContrato);
                                        } else {
                                            mensajeRpta = mensajeRpta2;
                                        }
                                    }
                                }
                            }
                        }

                        if (!tieneConfWispro && !tieneConfMikrotik) {
                            mensajes.add("FNo existe configuración para la siguiente factura -> " + vtaPk);
                        } else {
                            mensajes.add(mensajeRpta + " para la siguiente factura -> " + vtaPk);
                        }
                    } else {
                        mensajes.add("FNo existe contrato para la siguiente factura -> " + vtaPk);
                    }
                } else {
                    mensajes.add("FNo existe venta recurrente asignada para la siguiente factura -> " + vtaPk);
                }
            }
        } else {
            mensajes.add("FNo existe detalle de cobros para actualizar.");
        }

        respuesta.put("mensajes", mensajes);
        return respuesta;
    }

}
