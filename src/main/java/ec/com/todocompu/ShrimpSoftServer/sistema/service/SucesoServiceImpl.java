package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoClienteDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaSusTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisSucesoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoDetalle;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SucesoServiceImpl implements SucesoService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private SucesoClienteDao sucesoClienteDao;

    @Override
    public List<SisSucesoTO> getListaSisSucesoTO(String desde, String hasta, String usuario, String suceso, String cadenaGeneral, String empresa) throws Exception {
        List<SisSucesoTO> sisSucesoTOs = null;
        sisSucesoTOs = sucesoDao.getListaSisSuceso(desde, hasta, usuario, suceso, cadenaGeneral, empresa);
        return sisSucesoTOs;
    }

    @Override
    public List<SisListaSusTablaTO> getListaSisSusTablaTOs(String empresa) throws Exception {
        return sucesoDao.getListaSisSusTablaTOs(empresa);
    }

    @Override
    public List<SisSucesoDetalle> getListaSisSucesoDetalle(Integer secuencial) throws Exception {
        return sucesoDao.getListaSisSucesoDetalle(secuencial);
    }

    @Override
    public Map<String, Object> obtenerDatosSucesoDetalle(Integer secuencial, String empresa) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        boolean esUnicoDetalle = false;
        List<SisSucesoDetalle> listaDetalle = sucesoDao.getListaSisSucesoDetalle(secuencial);
        if (listaDetalle != null && listaDetalle.size() > 0) {
            esUnicoDetalle = listaDetalle.size() == 1;
            List<SisSucesoDetalle> filtrado = listaDetalle.stream()
                    .filter(item -> item.getSusSecuencia().equals(secuencial))
                    .collect(Collectors.toList());

            //buscar objeto actual
            if (filtrado != null && filtrado.size() > 0) {
                String json = filtrado.get(0).getSusJson();
                List<SisSucesoDetalle> filtradoAnterior = null;
                campos.put("detalleSuceso", filtrado.get(0));

                if (!esUnicoDetalle) {
                    //se busca (id+1) para obtener id del anterior ya que está ordenado desc
                    filtradoAnterior = listaDetalle.stream()
                            .filter(item -> item.getId().equals(filtrado.get(0).getId() + 1))
                            .collect(Collectors.toList());
                    if (filtradoAnterior != null && filtradoAnterior.size() > 0) {
                        campos.put("detalleAnterior", filtradoAnterior.get(0));
                    }
                }

                //cliente
                if (json.contains("invClientePK")) {
                    InvCliente cliente = UtilsJSON.jsonToObjeto(InvCliente.class, json);
                    cliente.setCliIdNumero(cliente.getCliIdNumero() != null && !cliente.getCliIdNumero().equals("") ? cliente.getCliIdNumero() : null);
                    campos.put("clienteActual", cliente);
                    if (filtradoAnterior != null && filtradoAnterior.size() > 0) {
                        String jsonAnt = filtradoAnterior.get(0).getSusJson();
                        InvCliente clienteAnterior = UtilsJSON.jsonToObjeto(InvCliente.class, jsonAnt);
                        clienteAnterior.setCliIdNumero(clienteAnterior.getCliIdNumero() != null && !clienteAnterior.getCliIdNumero().equals("") ? clienteAnterior.getCliIdNumero() : null);
                        campos.put("clienteAnterior", clienteAnterior);
                    }
                } else {
                    //producto
                    if (json.contains("invProductoPK") && !json.contains("invCompcrasPK")) {
                        InvProducto producto = UtilsJSON.jsonToObjeto(InvProducto.class, json);
                        campos.put("productoActual", producto);
                        if (filtradoAnterior != null && filtradoAnterior.size() > 0) {
                            String jsonAnt = filtradoAnterior.get(0).getSusJson();
                            InvProducto productoAnterior = UtilsJSON.jsonToObjeto(InvProducto.class, jsonAnt);
                            campos.put("productoAnterior", productoAnterior);
                        }
                    } else {
                        //proveedor
                        if (json.contains("invProveedorPK")) {
                            //compra
                            if (json.contains("invComprasPK")) {
                                InvCompras compra = UtilsJSON.jsonToObjeto(InvCompras.class, json);
                                campos.put("compraActual", compra);
                                if (filtradoAnterior != null && filtradoAnterior.size() > 0) {
                                    String jsonAnt = filtradoAnterior.get(0).getSusJson();
                                    InvCompras compraAnterior = UtilsJSON.jsonToObjeto(InvCompras.class, jsonAnt);
                                    campos.put("compraAnterior", compraAnterior);
                                }
                            } else {
                                InvProveedor proveedor = UtilsJSON.jsonToObjeto(InvProveedor.class, json);
                                campos.put("proveedorActual", proveedor);
                                if (filtradoAnterior != null && filtradoAnterior.size() > 0) {
                                    String jsonAnt = filtradoAnterior.get(0).getSusJson();
                                    InvProveedor proveedorAnterior = UtilsJSON.jsonToObjeto(InvProveedor.class, jsonAnt);
                                    campos.put("proveedorAnterior", proveedorAnterior);
                                }
                            }
                        }
                    }
                }
            }

        }

        return campos;
    }

    @Override
    public void sisRegistrarEventosUsuario(String suceso, String mensaje, String tabla, SisInfoTO sisInfoTO)
            throws Exception {
        SisSucesoTO sisSucesoTO = new SisSucesoTO();
        sisSucesoTO.setUsrEmpresa(sisInfoTO.getEmpresa());
        sisSucesoTO.setUsrCodigo(sisInfoTO.getUsuario());
        sisSucesoTO.setSusDetalle(
                "El usuario " + sisInfoTO.getUsuario() + mensaje + " " + UtilsValidacion.fechaSistema());
        sisSucesoTO.setSusSuceso(suceso);
        sisSucesoTO.setSusSecuencia(0);
        sisSucesoTO.setSusClave(sisInfoTO.getUsuario());
        sisSucesoTO.setSusFecha(UtilsValidacion.fechaSistema());
        sisSucesoTO.setSusTabla(tabla);
        SisSuceso sisSuceso = ConversionesSistema.ConvertirSisSucesoTO_SisSuceso(sisSucesoTO);
        sisSuceso.setSusMac(sisInfoTO.getMac());
        sucesoDao.sisRegistrarSucesosUsuario(sisSuceso);
    }

}
