package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfUbicacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfUbicaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.UbicacionActivoFijoDao;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UbicacionActivoFijoServiceImpl implements UbicacionActivoFijoService {

    private String modulo = "activosFijos";
    @Autowired
    private UbicacionActivoFijoDao ubicacionDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericReporteService genericReporteService;

    @Override
    public List<AfUbicacionesTO> getListaAfUbicaciones(String empresa) throws Exception {
        List<AfUbicaciones> listUbicaciones = ubicacionDao.getListaUbicaciones(empresa);
        List<AfUbicacionesTO> listUbicacionesTO = new ArrayList<>();
        listUbicaciones.stream().map((ubicacion) -> {
            AfUbicacionesTO ubicacionTO = new AfUbicacionesTO();
            ubicacionTO.convertirObjeto(ubicacion);
            return ubicacionTO;
        }).forEach((ubicacionTO) -> {
            listUbicacionesTO.add(ubicacionTO);
        });
        return listUbicacionesTO;
    }

    @Override
    public String insertarAfUbicacionesTO(AfUbicacionesTO afUbicacionesTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfUbicaciones afUbicacionesLocal = ubicacionDao.getAfUbicaciones(afUbicacionesTO.getUbiEmpresa(), afUbicacionesTO.getUbiSector(), afUbicacionesTO.getUbiCodigo());
        if (afUbicacionesLocal != null) {
            return "FYa existe una ubicación con este codigo";
        }
        AfUbicaciones afUbicacionesLocal1 = ubicacionDao.getAfUbicacionesPorDescripcion(afUbicacionesTO.getUbiEmpresa(), afUbicacionesTO.getUbiDescripcion());
        if (afUbicacionesLocal1 != null) {
            return "FYa existe una ubicación con esta descripcion";
        }
        susClave = afUbicacionesTO.getUbiCodigo();
        susDetalle = "Se insertó la ubicaciòn: " + afUbicacionesTO.getUbiDescripcion() + " con código " + afUbicacionesTO.getUbiCodigo();
        susSuceso = "INSERT";
        susTabla = "activosfijos.af_ubicaciones";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        AfUbicaciones afUbicaciones = new AfUbicaciones();
        afUbicaciones.convertirObjeto(afUbicacionesTO);
        if (ubicacionDao.insertarAfUbicaciones(afUbicaciones, sisSuceso)) {
            retorno = "TLa ubicación se guardo correctamente...";
        } else {
            retorno = "FHubo un error al guardar la ubicación...\nIntente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarAfUbicacionesTO(AfUbicacionesTO afUbicacionesTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfUbicaciones ubicacionLocal = ubicacionDao.getAfUbicaciones(afUbicacionesTO.getUbiEmpresa(), afUbicacionesTO.getUbiSector(), afUbicacionesTO.getUbiCodigo());
        if (ubicacionLocal != null) {
            AfUbicaciones afUbicacionesLocal1 = ubicacionDao.getAfUbicacionesPorDescripcion(afUbicacionesTO.getUbiEmpresa(), afUbicacionesTO.getUbiDescripcion());
            if (afUbicacionesLocal1 != null && !afUbicacionesLocal1.getAfUbicacionesPK().getUbiCodigo().equalsIgnoreCase(afUbicacionesTO.getUbiCodigo())) {
                return "FYa existe una categoria con esta descripcion";
            }
            susClave = afUbicacionesTO.getUbiCodigo();
            susDetalle = "Se modificó la ubicacion activos fijos: " + afUbicacionesTO.getUbiDescripcion() + " con código " + afUbicacionesTO.getUbiCodigo();
            susSuceso = "UPDATE";
            susTabla = "activosfijos.af_ubicaciones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            AfUbicaciones afUbicaciones = new AfUbicaciones();
            afUbicaciones.convertirObjeto(afUbicacionesTO);
            if (ubicacionDao.modificarAfUbicaciones(afUbicaciones, sisSuceso)) {
                retorno = "TLa ubicaciòn se modificó correctamente...";
            } else {
                retorno = "FHubo un error al modificar la ubicación...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FLa ubicación que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarAfUbicacionesTO(AfUbicacionesTO afUbicacionesTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfUbicaciones ubicacionLocal = ubicacionDao.getAfUbicaciones(afUbicacionesTO.getUbiEmpresa(), afUbicacionesTO.getUbiSector(), afUbicacionesTO.getUbiCodigo());
        if (ubicacionLocal != null) {
            susClave = afUbicacionesTO.getUbiCodigo();
            susDetalle = "Se eliminó la ubicacion activos fijos: " + afUbicacionesTO.getUbiDescripcion() + " con código " + afUbicacionesTO.getUbiCodigo();
            susSuceso = "UPDATE";
            susTabla = "activosfijos.af_ubicaciones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            AfUbicaciones afUbicaciones = new AfUbicaciones();
            afUbicaciones.convertirObjeto(afUbicacionesTO);
            if (ubicacionDao.eliminarAfUbicaciones(afUbicaciones, sisSuceso)) {
                retorno = "TLa ubicaciòn se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar la ubicación...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FLa ubicación que va a eliminar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public byte[] generarReporteAfUbicacionesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfUbicacionesTO> listado, String nombreReporte) {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteAfUbicacionesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfUbicacionesTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de ubicación de activo fijo");
            listaCabecera.add("S");
            listaCuerpo.add("SSecuencial" + "¬" + "SDescripción");
            for (AfUbicacionesTO afUbicacionesTO : listado) {
                listaCuerpo.add((afUbicacionesTO.getUbiCodigo() == null ? "B" : "S" + afUbicacionesTO.getUbiCodigo())
                        + "¬" + (afUbicacionesTO.getUbiDescripcion() == null ? "B" : "S" + afUbicacionesTO.getUbiDescripcion()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

}
