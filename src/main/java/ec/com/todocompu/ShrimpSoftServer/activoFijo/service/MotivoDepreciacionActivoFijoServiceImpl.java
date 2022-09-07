package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.MotivoDepreciacionActivoFijoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciacionesMotivos;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MotivoDepreciacionActivoFijoServiceImpl implements MotivoDepreciacionActivoFijoService {

    private String modulo = "activosFijos";
    @Autowired
    private MotivoDepreciacionActivoFijoDao motivoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericReporteService genericReporteService;

    @Override
    public List<AfDepreciacionMotivoTO> getListaAfDepreciacionesMotivos(String empresa) throws Exception {
        List<AfDepreciacionesMotivos> listMotivos = motivoDao.getListaAfDepreciacionesMotivos(empresa);
        List<AfDepreciacionMotivoTO> listMotivosTO = new ArrayList<>();
        listMotivos.stream().map((motivo) -> {
            AfDepreciacionMotivoTO motivoTO = new AfDepreciacionMotivoTO();
            motivoTO.convertirObjeto(motivo);
            return motivoTO;
        }).forEach((motivoTO) -> {
            listMotivosTO.add(motivoTO);
        });
        return listMotivosTO;
    }

    @Override
    public AfDepreciacionesMotivos getAfDepreciacionesMotivos(String empresa, String codigoEntidad, String tipo) throws Exception {
        AfDepreciacionesMotivos afDepreciacionesMotivos = null;
        if ("UBICACION".equals(tipo)) {
            afDepreciacionesMotivos = motivoDao.getAfDepreciacionesMotivosUbicacion(empresa, codigoEntidad);
        } else {
            afDepreciacionesMotivos = motivoDao.getAfDepreciacionesMotivosCategoria(empresa, codigoEntidad);
        }
        return afDepreciacionesMotivos;
    }

    @Override
    public String insertarAfDepreciacionMotivoTO(AfDepreciacionMotivoTO afDepreciacionMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfDepreciacionesMotivos afDepreciacionMotivoLocal = motivoDao.getAfDepreciacionesMotivosUbicacion(afDepreciacionMotivoTO.getAfUbicaciones().getUbiEmpresa(), afDepreciacionMotivoTO.getAfUbicaciones().getUbiCodigo());
        if (afDepreciacionMotivoLocal != null) {
            return "FYa existe esta ubicación registrada";
        }
        AfDepreciacionesMotivos afDepreciacionMotivoLocal1 = motivoDao.getAfDepreciacionesMotivosCategoria(afDepreciacionMotivoTO.getConTipo().getEmpCodigo(), afDepreciacionMotivoTO.getConTipo().getTipCodigo());
        if (afDepreciacionMotivoLocal1 != null) {
            return "FYa existe este tipo registrado";
        }
        susClave = afDepreciacionMotivoTO.getConTipo().getTipCodigo();
        susDetalle = "Se insertó el motivo: " + afDepreciacionMotivoTO.getConTipo().getTipDetalle() + " con código " + afDepreciacionMotivoTO.getConTipo().getTipCodigo();
        susSuceso = "INSERT";
        susTabla = "activosfijos.af_depreciaciones_motivos";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        AfDepreciacionesMotivos afDepreciacionesMotivos = new AfDepreciacionesMotivos();
        afDepreciacionesMotivos.convertirObjeto(afDepreciacionMotivoTO);
        if (motivoDao.insertarAfDepreciacionesMotivos(afDepreciacionesMotivos, sisSuceso)) {
            retorno = "TEl motivo se guardo correctamente...";
        } else {
            retorno = "FHubo un error al guardar el motivo...\nIntente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarAfDepreciacionMotivoTO(AfDepreciacionMotivoTO afDepreciacionMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfDepreciacionesMotivos motivoLocal = motivoDao.getAfDepreciacionesMotivos(afDepreciacionMotivoTO.getMotSecuencial());
        if (motivoLocal != null) {
            susClave = afDepreciacionMotivoTO.getConTipo().getTipCodigo();
            susDetalle = "Se modifico el motivo: " + afDepreciacionMotivoTO.getConTipo().getTipDetalle() + " con código " + afDepreciacionMotivoTO.getConTipo().getTipCodigo();
            susSuceso = "UPDATE";
            susTabla = "activosfijos.af_depreciaciones_motivos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            AfDepreciacionesMotivos afDepreciacionesMotivos = new AfDepreciacionesMotivos();
            afDepreciacionesMotivos.convertirObjeto(afDepreciacionMotivoTO);
            if (motivoDao.modificarAfDepreciacionesMotivos(afDepreciacionesMotivos, sisSuceso)) {
                retorno = "TEl motivo se modificó correctamente...";
            } else {
                retorno = "FHubo un error al modificar el motivo...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarAfDepreciacionMotivoTO(AfDepreciacionMotivoTO afDepreciacionMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfDepreciacionesMotivos motivoLocal = motivoDao.getAfDepreciacionesMotivos(afDepreciacionMotivoTO.getMotSecuencial());
        if (motivoLocal != null) {
            susClave = afDepreciacionMotivoTO.getConTipo().getTipCodigo();
            susDetalle = "Se eliminó el motivo: " + afDepreciacionMotivoTO.getConTipo().getTipDetalle() + " con código " + afDepreciacionMotivoTO.getConTipo().getTipCodigo();
            susSuceso = "DELETE";
            susTabla = "activosfijos.af_depreciaciones_motivos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            AfDepreciacionesMotivos afDepreciacionesMotivos = new AfDepreciacionesMotivos();
            afDepreciacionesMotivos.convertirObjeto(afDepreciacionMotivoTO);
            if (motivoDao.eliminarAfDepreciacionesMotivos(afDepreciacionesMotivos, sisSuceso)) {
                retorno = "TEl motivo se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar el motivo...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo que va a eliminar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public byte[] generarReporteAfDepreciacionMotivoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfDepreciacionMotivoTO> listado, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteAfDepreciacionMotivoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfDepreciacionMotivoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de depreciacón");
            listaCabecera.add("S");
            listaCuerpo.add("SSecuencial" + "¬" + "STipo" + "¬" + "SUbicación");
            for (AfDepreciacionMotivoTO afDepreciacionMotivoTO : listado) {
                listaCuerpo.add((afDepreciacionMotivoTO.getMotSecuencial() == null ? "B" : "S" + afDepreciacionMotivoTO.getMotSecuencial())
                        + "¬" + (afDepreciacionMotivoTO.getConTipo().getTipDetalle() == null ? "B" : "S" + afDepreciacionMotivoTO.getConTipo().getTipDetalle())
                        + "¬" + (afDepreciacionMotivoTO.getAfUbicaciones().getUbiDescripcion() == null ? "B" : "S" + afDepreciacionMotivoTO.getAfUbicaciones().getUbiDescripcion()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

}
