package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfCategoriasTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfCategorias;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.CategoriaActivoFijoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoriaActivoFijoServiceImpl implements CategoriaActivoFijoService {

    private String modulo = "activosFijos";
    @Autowired
    private CategoriaActivoFijoDao categoriaDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private CuentasService cuentasService;
    @Autowired
    private GenericReporteService genericReporteService;

    @Override
    public List<AfCategoriasTO> getListaAfCategorias(String empresa, boolean filtrarInactivos) throws Exception {
        List<AfCategorias> listCategorias = categoriaDao.getListaCategorias(empresa, filtrarInactivos);
        List<AfCategoriasTO> listCategoriasTO = new ArrayList<>();
        listCategorias.stream().map((categoria) -> {
            AfCategoriasTO categoriaTO = new AfCategoriasTO();
            categoriaTO.convertirObjeto(categoria);
            return categoriaTO;
        }).forEach((categoriaTO) -> {
            listCategoriasTO.add(categoriaTO);
        });
        return listCategoriasTO;
    }

    @Override
    public AfCategoriasTO consultarAfCategoriasTO(String empresa, String codigo) throws Exception {
        AfCategorias afCategoriaLocal = categoriaDao.getAfCategorias(empresa, codigo);
        AfCategoriasTO categoriaTO = null;
        if (afCategoriaLocal != null) {
            categoriaTO = new AfCategoriasTO();
            categoriaTO.setCatCodigo(afCategoriaLocal.getAfCategoriasPK().getCatCodigo());
            categoriaTO.setCatCuentaActivo(afCategoriaLocal.getCatCuentaActivo());
            //detalle activo
            ConCuentas cuentaActivo = cuentasService.obtenerConCuenta(empresa, categoriaTO.getCatCuentaActivo());
            if (cuentaActivo != null) {
                categoriaTO.setCatCuentaActivoDetalle(cuentaActivo.getCtaDetalle());
            }
            //detalle depreaciacion
            categoriaTO.setCatCuentaDepreciacion(afCategoriaLocal.getCatCuentaDepreciacion());
            ConCuentas cuentaDepreciacion = cuentasService.obtenerConCuenta(empresa, categoriaTO.getCatCuentaDepreciacion());
            if (cuentaDepreciacion != null) {
                categoriaTO.setCatCuentaDepreciacionDetalle(cuentaDepreciacion.getCtaDetalle());
            }
            //detalle depreaciacion acumulado 
            categoriaTO.setCatCuentaDepreciacionAcumulada(afCategoriaLocal.getCatCuentaDepreciacionAcumulada());
            ConCuentas depreaciacionacumulada = cuentasService.obtenerConCuenta(empresa, categoriaTO.getCatCuentaDepreciacionAcumulada());
            if (depreaciacionacumulada != null) {
                categoriaTO.setCatCuentaDepreciacionAcumuladaDetalle(depreaciacionacumulada.getCtaDetalle());
            }
            //detalle GND 
            categoriaTO.setCatCuentaGnd(afCategoriaLocal.getCatCuentaGnd());
            ConCuentas gnd = cuentasService.obtenerConCuenta(empresa, categoriaTO.getCatCuentaGnd());
            if (gnd != null) {
                categoriaTO.setCatCuentaGnd(gnd.getCtaDetalle());
            }

            categoriaTO.setCatDescripcion(afCategoriaLocal.getCatDescripcion());
            categoriaTO.setCatEmpresa(empresa);
            categoriaTO.setCatInactivo(afCategoriaLocal.getCatInactivo());
            categoriaTO.setCatPorcentajeDepreciacion(afCategoriaLocal.getCatPorcentajeDepreciacion());
            categoriaTO.setCatVidaUtil(afCategoriaLocal.getCatVidaUtil());
            categoriaTO.setUsrCodigo(afCategoriaLocal.getUsrCodigo());
            categoriaTO.setUsrFechaInserta(afCategoriaLocal.getUsrFechaInserta());

        }
        return categoriaTO;
    }

    @Override
    public String insertarAfCategoriasTO(AfCategoriasTO afCategoriasTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfCategorias afCategoriaLocal = categoriaDao.getAfCategorias(afCategoriasTO.getCatEmpresa(), afCategoriasTO.getCatCodigo());
        if (afCategoriaLocal != null) {
            return "FYa existe una categoria con este codigo";
        }
        AfCategorias afCategoriaLocal1 = categoriaDao.getAfCategoriasPorDescripcion(afCategoriasTO.getCatEmpresa(), afCategoriasTO.getCatDescripcion());
        if (afCategoriaLocal1 != null) {
            return "FYa existe una categoria con esta descripcion";
        }
        susClave = afCategoriasTO.getCatCodigo();
        susDetalle = "Se insertó la categoria activos fijos: " + afCategoriasTO.getCatDescripcion() + " con código " + afCategoriasTO.getCatCodigo();
        susSuceso = "INSERT";
        susTabla = "activosfijos.af_categorias";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        AfCategorias afCategorias = new AfCategorias();
        afCategorias.convertirObjeto(afCategoriasTO);
        if (categoriaDao.insertarAfCategorias(afCategorias, sisSuceso)) {
            retorno = "TLa categoría se guardo correctamente...";
        } else {
            retorno = "FHubo un error al guardar la categoría...\nIntente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarAfCategoriasTO(AfCategoriasTO afCategoriasTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfCategorias categoriaLocal = categoriaDao.getAfCategorias(afCategoriasTO.getCatEmpresa(), afCategoriasTO.getCatCodigo());
        if (categoriaLocal != null) {
            AfCategorias afCategoriaLocal1 = categoriaDao.getAfCategoriasPorDescripcion(afCategoriasTO.getCatEmpresa(), afCategoriasTO.getCatDescripcion());
            if (afCategoriaLocal1 != null && !afCategoriaLocal1.getAfCategoriasPK().getCatCodigo().equalsIgnoreCase(afCategoriasTO.getCatCodigo())) {
                return "FYa existe una categoria con esta descripcion";
            }
            susClave = afCategoriasTO.getCatCodigo();
            susDetalle = "Se modificó la categoria activos fijos: " + afCategoriasTO.getCatDescripcion() + " con código " + afCategoriasTO.getCatCodigo();
            susSuceso = "UPDATE";
            susTabla = "activosfijos.af_categorias";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            AfCategorias afCategorias = new AfCategorias();
            afCategorias.convertirObjeto(afCategoriasTO);
            if (categoriaDao.modificarAfCategorias(afCategorias, sisSuceso)) {
                retorno = "TLa categoría se modificó correctamente...";
            } else {
                retorno = "FHubo un error al modificar la categoría...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FLa categoría que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarAfCategoriasTO(AfCategoriasTO afCategoriasTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfCategorias categoriaLocal = categoriaDao.getAfCategorias(afCategoriasTO.getCatEmpresa(), afCategoriasTO.getCatCodigo());
        if (categoriaLocal != null) {
            susClave = afCategoriasTO.getCatCodigo();
            susDetalle = "Se eliminó la categoria activos fijos: " + afCategoriasTO.getCatDescripcion() + " con código " + afCategoriasTO.getCatCodigo();
            susSuceso = "DELETE";
            susTabla = "activosfijos.af_categorias";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            AfCategorias afCategorias = new AfCategorias();
            afCategorias.convertirObjeto(afCategoriasTO);
            if (categoriaDao.eliminarAfCategorias(afCategorias, sisSuceso)) {
                retorno = "TLa categoría se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar la categoría...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FLa categoría que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public byte[] generarReporteAfCategoriasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfCategoriasTO> listado, String nombreReporte) {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteAfCategoriasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfCategoriasTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de grupo y clasificación de activo fijo");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDescripción" + "¬" + "SVida util" + "¬" + "SPorcentaje depreciación" + "¬" + "SCuenta activo" + "¬"
                    + "SCuenta depreciación" + "¬" + "SCuenta depreciación acumulado" + "¬" + "SCuenta GND" + "¬" + "SActivo");
            for (AfCategoriasTO afCategoriasTO : listado) {
                listaCuerpo.add((afCategoriasTO.getCatCodigo() == null ? "B" : "S" + afCategoriasTO.getCatCodigo())
                        + "¬" + (afCategoriasTO.getCatDescripcion() == null ? "B" : "S" + afCategoriasTO.getCatDescripcion())
                        + "¬" + ("D" + afCategoriasTO.getCatVidaUtil())
                        + "¬" + (afCategoriasTO.getCatPorcentajeDepreciacion() == null ? "B" : "D" + afCategoriasTO.getCatPorcentajeDepreciacion())
                        + "¬" + (afCategoriasTO.getCatCuentaActivo() == null ? "B" : "S" + afCategoriasTO.getCatCuentaActivo())
                        + "¬" + (afCategoriasTO.getCatCuentaDepreciacion() == null ? "B" : "S" + afCategoriasTO.getCatCuentaDepreciacion())
                        + "¬" + (afCategoriasTO.getCatCuentaDepreciacionAcumulada() == null ? "B" : "S" + afCategoriasTO.getCatCuentaDepreciacionAcumulada())
                        + "¬" + (afCategoriasTO.getCatCuentaGnd() == null ? "B" : "S" + afCategoriasTO.getCatCuentaGnd())
                        + "¬" + (afCategoriasTO.getCatCuentaActivo().equals(true) ? "SSí" : "SNo")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

}
