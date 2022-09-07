package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.ComprobanteElectronicoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteElectronico;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import java.util.HashMap;
import java.util.Map;

@Service
public class ComprobanteElectronicoServiceImpl implements ComprobanteElectronicoService {

    @Autowired
    private ComprobanteElectronicoDao comprobanteElectronicoDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @Override
    public List<ComprobanteElectronico> obtenerDocumentosPorCedulaRucMesAnio(String cedulaRuc, String mes, String anio) {
        return comprobanteElectronicoDao.obtenerDocumentosPorCedulaRucMesAnio(cedulaRuc, mes, anio);
    }

    @Override
    public Map<String, Object> obtenerDatosParaComprobantesElectronicosRecibidos(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<SisPeriodo> listadoPeriodos = periodoService.getListaPeriodo(empresa);
        List<InvProveedorTO> listadoProveedores = proveedorService.getListProveedorTO(empresa, null, false, null);
        SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);

        campos.put("listadoPeriodos", listadoPeriodos);
        campos.put("listadoProveedores", listadoProveedores);
        campos.put("enProceso", parametros.isParDocumentosRecibidosEnProceso());
        return campos;
    }

}
