/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteContratoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.InvClienteGrupoEmpresarialService;
import ec.com.todocompu.ShrimpSoftServer.isp.dao.SisEmpresaParametrosMikrotikDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteGrupoEmpresarialTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.isp.TO.WisproDataTO;
import ec.com.todocompu.ShrimpSoftUtils.isp.TO.WisproMetaTO;
import ec.com.todocompu.ShrimpSoftUtils.isp.TO.WisproPaginationTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class SisEmpresaParametrosMikrotikServiceImpl implements SisEmpresaParametrosMikrotikService {
    
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private SisEmpresaParametrosMikrotikDao configuracionMikrotikDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private ClienteContratoService clienteContratoService;
    @Autowired
    private InvClienteGrupoEmpresarialService invClienteGrupoEmpresarialService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private SucesoDao sucesoDao;
    
    @Override
    public SisEmpresaParametrosMikrotik obtenerConfiguracionMikrotik(String empresa) throws Exception {
        return configuracionMikrotikDao.obtenerConfiguracionMikrotik(empresa);
    }
    
    @Override
    public SisEmpresaParametrosMikrotik insertarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisInfoTO sisInfoTO) throws Exception {
        sisEmpresaParametrosMikrotik.setParEmpresa(sisInfoTO.getEmpresa());
        susClave = sisEmpresaParametrosMikrotik.getParSecuencial() + " | " + sisEmpresaParametrosMikrotik.getParEmpresa();
        susDetalle = "Se ingresó los parametros de mikrotik: " + susClave;
        susSuceso = "INSERT";
        susTabla = "sistemaweb.sis_empresa_parametros_mikrotik";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisEmpresaParametrosMikrotik = configuracionMikrotikDao.insertarSisEmpresaParametrosMikrotik(sisEmpresaParametrosMikrotik, sisSuceso);
        return sisEmpresaParametrosMikrotik;
    }
    
    @Override
    public SisEmpresaParametrosMikrotik modificarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisInfoTO sisInfoTO) throws Exception {
        susClave = sisEmpresaParametrosMikrotik.getParSecuencial() + " | " + sisEmpresaParametrosMikrotik.getParEmpresa();
        susDetalle = "Se modificó los parametros de mikrotik: " + susClave;
        susSuceso = "UPDATE";
        susTabla = "sistemaweb.sis_empresa_parametros_mikrotik";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisEmpresaParametrosMikrotik = configuracionMikrotikDao.modificarSisEmpresaParametrosMikrotik(sisEmpresaParametrosMikrotik, sisSuceso);
        return sisEmpresaParametrosMikrotik;
        
    }
    
    @Override
    public boolean eliminarSisEmpresaParametrosMikrotik(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        susClave = "" + secuencial;
        susDetalle = "Se eliminó los parametros de mikrotik: " + susClave;
        susSuceso = "DELETE";
        susTabla = "sistemaweb.sis_empresa_parametros_mikrotik";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return configuracionMikrotikDao.eliminarSisEmpresaParametrosMikrotik(new SisEmpresaParametrosMikrotik(secuencial), sisSuceso);
        
    }
    
    @Override
    public Map<String, Object> listarContratos(String empresa) throws Exception {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        List<WisproDataTO> listadoRespuesta = new ArrayList<>();
        List<WisproDataTO> listadoRespuestaEnviar = new ArrayList<>();
        try {
            SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            List<InvClienteContratoTO> listaClientesContratos = clienteContratoService.getListarInvClienteContratoTO(empresa, null, null, null);
            
            if (parametros != null && parametros.getParTokenWispro() != null) {
                URL urlC = new URI("https://www.cloud.wispro.co/api/v1/contracts?page=1").toURL();
                HttpURLConnection conn = (HttpURLConnection) urlC.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization", parametros.getParTokenWispro());
                
                if (conn.getResponseCode() != 200) {
                    conn.disconnect();
                    respuesta.put("mensaje", "Failed : HTTP Error code : " + conn.getResponseCode());
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                    if (sb != null) {
                        Map<String, Object> json = UtilsJSON.jsonToMap(sb.toString());
                        if (json != null) {
                            WisproMetaTO meta = UtilsJSON.jsonToObjeto(WisproMetaTO.class, json.get("meta"));
                            WisproPaginationTO pagination = meta.getPagination();
                            double total_records = pagination.getTotal_records();
                            double cantidadPaginas = Math.floor(total_records / 100) + 1;
                            
                            for (int i = 1; i <= cantidadPaginas; i++) {
                                List<WisproDataTO> listado = obtenerContratos(
                                        parametros.getParTokenWispro(),
                                        "https://www.cloud.wispro.co/api/v1/contracts?page=" + i + "&per_page=100"
                                );
                                if (listado != null && listado.size() > 0) {
                                    listadoRespuesta.addAll(listado);
                                    
                                    if (listaClientesContratos != null && listaClientesContratos.size() > 0) {
                                        List<WisproDataTO> listadoFiltrado = new ArrayList<>();
                                        for (InvClienteContratoTO contrato : listaClientesContratos) {
                                            listadoFiltrado = listadoRespuesta.stream()
                                                    .filter((item) -> (item.getPublic_id().equalsIgnoreCase(contrato.getCliNumeroContrato())))
                                                    .map((inters) -> {
                                                        WisproDataTO firew = inters;
                                                        firew.setCliCodigo(contrato.getCliCodigo());
                                                        firew.setCliId(contrato.getCliIdNumero());
                                                        firew.setCliNombre(contrato.getCliRazonSocial());
                                                        firew.setAddress(contrato.getCliDireccionIp());
                                                        firew.setCliFechaCorte(contrato.getCliFechaCorte());
                                                        return firew;
                                                    })
                                                    .collect(Collectors.toList());
                                        }
                                        
                                        if (listadoFiltrado.size() > 0) {
                                            listadoFiltrado.forEach((f) -> {
                                                listadoRespuesta.forEach((c) -> {
                                                    if (f.getPublic_id().equalsIgnoreCase(c.getPublic_id())) {
                                                        c = f;
                                                    }
                                                });
                                            });
                                        }
                                    }
                                    listadoRespuestaEnviar = listadoRespuesta;
                                }
                            }
                        }
                    }
                    respuesta.put("mensaje", "TSe verificó correctamente URL");
                    respuesta.put("respuesta", listadoRespuestaEnviar);
                }
                conn.disconnect();
                
            } else {
                respuesta.put("mensaje", "FNo tiene token de configuración de wispro");
                respuesta.put("respuesta", new ArrayList<>());
            }
        } catch (Exception e) {
            respuesta.put("mensaje", e.getMessage());
            respuesta.put("respuesta", new ArrayList<>());
        }
        
        return respuesta;
    }
    
    @Override
    public Map<String, Object> listarContratosWispro(String empresa) throws Exception {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        List<WisproDataTO> listadoRespuesta = new ArrayList<>();
        try {
            SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            if (parametros != null && parametros.getParTokenWispro() != null) {
                URL urlC = new URI("https://www.cloud.wispro.co/api/v1/contracts?page=1").toURL();
                HttpURLConnection conn = (HttpURLConnection) urlC.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization", parametros.getParTokenWispro());
                
                if (conn.getResponseCode() != 200) {
                    conn.disconnect();
                    respuesta.put("mensaje", "Failed : HTTP Error code : " + conn.getResponseCode());
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                    if (sb != null) {
                        Map<String, Object> json = UtilsJSON.jsonToMap(sb.toString());
                        if (json != null) {
                            WisproMetaTO meta = UtilsJSON.jsonToObjeto(WisproMetaTO.class, json.get("meta"));
                            WisproPaginationTO pagination = meta.getPagination();
                            double total_records = pagination.getTotal_records();
                            double cantidadPaginas = Math.floor(total_records / 100) + 1;
                            
                            for (int i = 1; i <= cantidadPaginas; i++) {
                                List<WisproDataTO> listado = obtenerContratos(
                                        parametros.getParTokenWispro(),
                                        "https://www.cloud.wispro.co/api/v1/contracts?page=" + i + "&per_page=100"
                                );
                                if (listado != null && listado.size() > 0) {
                                    listadoRespuesta.addAll(listado);
                                }
                            }
                        }
                    }
                    respuesta.put("mensaje", "TSe verificó correctamente URL");
                    respuesta.put("respuesta", listadoRespuesta);
                }
                conn.disconnect();
                
            } else {
                respuesta.put("mensaje", "FNo tiene token de configuración de wispro");
                respuesta.put("respuesta", new ArrayList<>());
            }
        } catch (Exception e) {
            respuesta.put("mensaje", e.getMessage());
            respuesta.put("respuesta", new ArrayList<>());
        }
        
        return respuesta;
    }
    
    @Override
    public Map<String, Object> actualizarEstadoContrato(Map<String, Object> datos) throws Exception {
        String id = UtilsJSON.jsonToObjeto(String.class, datos.get("id"));
        String json = UtilsJSON.jsonToObjeto(String.class, datos.get("json"));
        //enaviar ambos para crear suceso
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, datos.get("sisInfoTO"));
        WisproDataTO wisproDataTO = UtilsJSON.jsonToObjeto(WisproDataTO.class, datos.get("wisproDataTO"));// se envia desde cliente en opcion individual de opciones 

        Map<String, Object> respuesta = new HashMap<String, Object>();
        
        try {
            String token = "a68f7d1e-337f-4ad1-a22e-089176636d49";//obtener de API TOKEN
            String mensaje = "F";
            /**
             * ***
             */
            URL u = new URL("https://www.cloud.wispro.co/api/v1/contracts/" + id);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Object respuestaURL = null;
                if (response != null) {
                    respuestaURL = UtilsJSON.jsonToObjeto(Object.class, response.toString());
                }
                if (conn.getResponseCode() != 200) {
                    mensaje = ("Failed : HTTP Error code : " + conn.getResponseCode());
                    conn.disconnect();
                    respuesta.put("mensaje", mensaje);
                } else {
                    mensaje = "TSe verificó correctamente URL";
                    respuesta.put("mensaje", mensaje);
                    respuesta.put("contrato", respuestaURL);
                    if (sisInfoTO != null && wisproDataTO != null) {
                        //cambiar grupo en NET1,NET2 Y NET3
                        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
                        if (sisEmpresaParametros != null && sisEmpresaParametros.getEmpCodigo() != null
                                && (sisEmpresaParametros.getEmpCodigo().getEmpRuc().equals("0791755093001")
                                || sisEmpresaParametros.getEmpCodigo().getEmpRuc().equals("0791702070001")
                                || sisEmpresaParametros.getEmpCodigo().getEmpRuc().equals("0791702054001"))) {
                            //obtener cliente
                            List<InvClienteGrupoEmpresarialTO> listaGrupoEmpresarial = invClienteGrupoEmpresarialService.getInvClienteGrupoEmpresarialTO(sisInfoTO.getEmpresa(), null);
                            String tipoGrupo = (wisproDataTO.getState().equals("enabled") ? "ACTIVOS" : "SUSPENDIDOS");
                            List<InvClienteGrupoEmpresarialTO> filtrado = listaGrupoEmpresarial.stream()
                                    .filter(item -> item.getGeNombre().equals(tipoGrupo))
                                    .collect(Collectors.toList());
                            if (filtrado != null && filtrado.size() > 0) {
                                clienteService.actualizarGrupoEnCliente(sisInfoTO.getEmpresa(), wisproDataTO.getCliCodigo(), filtrado.get(0).getGeCodigo(), sisInfoTO);
                            }
                            
                        }
                        //actualizar fecha de corte en contrato
                        InvClienteContrato contrato = clienteContratoService.obtenerInvClienteContrato(sisInfoTO.getEmpresa(), wisproDataTO.getPublic_id());
                        if (!wisproDataTO.getState().equals("enabled")) {
                            contrato.setCliFechaCorte(UtilsDate.timestamp());
                        } else {
                            contrato.setCliFechaCorte(null);
                        }
                        //crear suceso 
                        SisSuceso sisSucesoContrato = new SisSuceso();
                        sisSucesoContrato.setSusSuceso("INSERT");
                        sisSucesoContrato.setSusTabla("inventario.inv_cliente_contrato");
                        sisSucesoContrato.setSusDetalle("Se " + (wisproDataTO.getState().equals("enabled") ? "habilitó " : "deshabilitó") + " correctamente el servicio con contrato:" + wisproDataTO.getPublic_id() + "(Wispro)");
                        sisSucesoContrato.setSusClave(wisproDataTO.getPublic_id());
                        sisSucesoContrato.setSusFecha(UtilsDate.timestamp());
                        sisSucesoContrato.setSusMac(sisInfoTO.getMac());
                        sisSucesoContrato.setDetEmpresa(sisInfoTO.getEmpresa());
                        sisSucesoContrato.setSisUsuario(new SisUsuario(sisInfoTO.getUsuario()));
                        sucesoDao.insertar(sisSucesoContrato);
                    }
                    
                }
            }
            conn.disconnect();
            
        } catch (Exception e) {
            respuesta.put("mensaje", e.getMessage());
            respuesta.put("contrato", null);
        }
        return respuesta;
    }
    
    @Override
    public Map<String, Object> consultarContrato(Map<String, Object> datos) throws Exception {
        String id = UtilsJSON.jsonToObjeto(String.class, datos.get("id"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, datos.get("empresa"));
        SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        
        Map<String, Object> respuesta = new HashMap<String, Object>();
        if (parametros != null && parametros.getParTokenWispro() != null) {
            try {
                String urlVerificacion = "https://www.cloud.wispro.co/api/v1/contracts/" + id;
                String token = parametros.getParTokenWispro();//obtener de API TOKEN
                URI uri = new URI(urlVerificacion);
                URL urlC = uri.toURL();
                HttpURLConnection conn = (HttpURLConnection) urlC.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Authorization", parametros.getParTokenWispro());
                
                if (conn.getResponseCode() != 200) {
                    conn.disconnect();
                    respuesta.put("mensaje", "Failed : HTTP Error code : " + conn.getResponseCode());
                } else {
                    InputStreamReader in = new InputStreamReader(conn.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    StringBuilder sb = new StringBuilder();
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                    WisproDataTO respuestaURL = null;
                    String plan_id = null;
                    String client_id = null;
                    if (sb != null) {
                        Map<String, Object> json = UtilsJSON.jsonToMap(sb.toString());
                        WisproDataTO data = null;
                        if (json != null) {
                            data = UtilsJSON.jsonToObjeto(WisproDataTO.class, json.get("data"));
                            plan_id = data.getPlan_id();
                            client_id = data.getClient_id();
                        }
                        respuestaURL = data;
                    }
                    //consultar plan
                    if (plan_id != null) {
                        Map<String, Object> plan = obtenerPlan(plan_id, token);
                        respuesta.put("plan", plan);
                    }
                    //consultar cliente
                    if (client_id != null) {
                        Map<String, Object> cliente = obtenerCliente(client_id, token);
                        respuesta.put("cliente", cliente);
                    }
                    //**********
                    respuesta.put("mensaje", "TSe verificó correctamente URL");
                    respuesta.put("contrato", respuestaURL);
                }
                conn.disconnect();
                
                return respuesta;
            } catch (Exception e) {
                throw new GeneralException("La URL no respondió correctamente", e.getMessage());
            }
        } else {
            return null;
        }
    }
    
    public List<WisproDataTO> obtenerContratos(String token, String url) {
        List<WisproDataTO> listadoRespuesta = new ArrayList<WisproDataTO>();
        try {
            URL urlC = new URI(url).toURL();
            HttpURLConnection conn = (HttpURLConnection) urlC.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", token);
            
            if (conn.getResponseCode() != 200) {
                conn.disconnect();
                return null;
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                if (sb != null) {
                    Map<String, Object> json = UtilsJSON.jsonToMap(sb.toString());
                    if (json != null) {
                        List<WisproDataTO> contratos = UtilsJSON.jsonToList(WisproDataTO.class, json.get("data"));
                        if (contratos != null) {
                            for (WisproDataTO obj : contratos) {
                                listadoRespuesta.add(obj);
                            }
                        }
                        
                    }
                }
            }
            conn.disconnect();
            return listadoRespuesta;
            
        } catch (Exception e) {
            throw new GeneralException("La URL no respondio correctamente", e.getMessage());
        }
    }
    
    public Map<String, Object> obtenerPlan(String id, String token) {
        String urlVerificacion = "https://www.cloud.wispro.co/api/v1/plans/" + id;
        return obtenerDatosWispro(token, urlVerificacion, "GET");
    }
    
    public Map<String, Object> obtenerCliente(String id, String token) {
        String urlVerificacion = "https://www.cloud.wispro.co/api/v1/clients/" + id;
        return obtenerDatosWispro(token, urlVerificacion, "GET");
    }
    
    public Map<String, Object> obtenerDatosWispro(String token, String url, String metodo) {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            URI uri = new URI(url);
            URL urlC = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) urlC.openConnection();
            conn.setRequestMethod(metodo);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", token);
            
            if (conn.getResponseCode() != 200) {
                conn.disconnect();
                respuesta.put("mensaje", "Failed : HTTP Error code : " + conn.getResponseCode());
            } else {
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(in);
                StringBuilder sb = new StringBuilder();
                
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                WisproDataTO data = null;
                if (sb != null) {
                    Map<String, Object> json = UtilsJSON.jsonToMap(sb.toString());
                    if (json != null) {
                        data = UtilsJSON.jsonToObjeto(WisproDataTO.class, json.get("data"));
                    }
                }
                respuesta.put("mensaje", "TSe verificó correctamente URL");
                respuesta.put("data", data);
            }
            conn.disconnect();
            return respuesta;
        } catch (Exception e) {
            throw new GeneralException("La URL no respondió correctamente", e.getMessage());
        }
    }
    
}
