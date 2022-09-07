package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.ActivoFijoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoTipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfActivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfListadoActivoFijoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivoFijoServiceImpl implements ActivoFijoService {

    private String modulo = "activosFijos";
    @Autowired
    private ActivoFijoDao activoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private ProductoTipoService productoTipoService;
    @Autowired
    private DepreciacionActivoFijoService depreciacionActivoFijoService;

    @Override
    public List<AfActivoTO> getListaAfActivos(String empresa, String sector, String ubicacion) throws Exception {
        List<AfActivos> listAfActivos = activoDao.getListaActivos(empresa, sector, ubicacion);
        List<AfActivoTO> listAfActivosTO = new ArrayList<>();
        listAfActivos.stream().map((activo) -> {
            AfActivoTO activoTO = new AfActivoTO();
            activoTO.convertirObjeto(activo);
            return activoTO;
        }).forEach((activoTO) -> {
            listAfActivosTO.add(activoTO);
        });
        return listAfActivosTO;
    }

    @Override
    public String insertarModificarEliminarAfActivoTODesdeProducto(String empresa, String codigoProducto, String tipo, AfActivoTO afActivoTO, SisInfoTO sisInfoTO) throws Exception {
        String msje = "";

        boolean existeActivoFijo = existeAfActivo(empresa, codigoProducto);
        boolean isTipoActivoFijo = productoTipoService.comprobarInvProductoTipoActivoFijo(empresa, tipo);

        if (isTipoActivoFijo) {
            if (existeActivoFijo) {
                //Actualizamos
                if (afActivoTO != null) {
                    msje = modificarAfActivoTO(afActivoTO, sisInfoTO);
                } else {
                    msje = "FEl tipo de producto es ACTIVO FIJO. revisar";
                }
            } else {
                //insertamos
                if (afActivoTO != null) {
                    afActivoTO.setAfCodigo(codigoProducto);
                    afActivoTO.setAfEmpresa(empresa);
                    afActivoTO.setUsrCodigo(sisInfoTO.getUsuario());
                    afActivoTO.setUsrEmpresa(empresa);
                    msje = insertarAfActivoTO(afActivoTO, sisInfoTO);
                } else {
                    msje = "FEl tipo de producto es ACTIVO FIJO. revisar";
                }
            }
        } else {
            if (existeActivoFijo) {
                //eliminamos
                AfActivos activoLocal = activoDao.getAfActivos(empresa, codigoProducto);
                msje = eliminarAfActivo(activoLocal, sisInfoTO);
            } else {
                msje = "T";
            }
        }

        return msje;
    }

    @Override
    public String insertarAfActivoTO(AfActivoTO afActivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfActivos activoLocal = activoDao.getAfActivos(afActivoTO.getAfEmpresa(), afActivoTO.getAfCodigo());
        if (activoLocal == null) {
            susClave = afActivoTO.getAfCodigo();
            susDetalle = "Se insertó el activo: " + afActivoTO.getAfDescripcion() + " con código " + afActivoTO.getAfCodigo();
            susSuceso = "INSERT";
            susTabla = "activosfijos.af_activos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            AfActivos activoFijo = new AfActivos();
            activoFijo.convertirObjeto(afActivoTO);
            if (activoDao.insertarAfActivos(activoFijo, sisSuceso)) {
                retorno = "TEl activo se guardo correctamente...";
            } else {
                retorno = "FHubo un error al guardar el activo...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FYa existe un activo fijo relacionado a este producto.";
        }
        return retorno;
    }

    @Override
    public String modificarAfActivoTO(AfActivoTO afActivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfActivos activoLocal = activoDao.getAfActivos(afActivoTO.getAfEmpresa(), afActivoTO.getAfCodigo());
        if (activoLocal != null) {
            susClave = afActivoTO.getAfCodigo();
            susDetalle = "Se modificó el activo: " + afActivoTO.getAfDescripcion() + " con código " + afActivoTO.getAfCodigo();
            susSuceso = "UPDATE";
            susTabla = "activosfijos.af_ubicaciones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            AfActivos activoFijo = new AfActivos();
            activoFijo.convertirObjeto(afActivoTO);
            if (activoDao.modificarAfActivos(activoFijo, sisSuceso)) {
                retorno = "TEl activo se modificó correctamente...";
            } else {
                retorno = "FHubo un error al modificar el activo...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl activo que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarAfActivoTO(String empresa, String codigoProducto, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfActivos activoLocal = activoDao.getAfActivos(empresa, codigoProducto);
        if (activoLocal != null) {
            if (!depreciacionActivoFijoService.productoSeEncuentraDepreciacion(empresa, codigoProducto)) {
                susClave = activoLocal.getAfActivosPK().getAfCodigo();
                susDetalle = "Se eliminó el activo: " + activoLocal.getAfDescripcion() + " con código " + activoLocal.getAfActivosPK().getAfCodigo();
                susSuceso = "DELETE";
                susTabla = "activosfijos.af_ubicaciones";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                if (activoDao.eliminarAfActivos(activoLocal, sisSuceso)) {
                    retorno = "TEl activo se eliminó correctamente...";
                } else {
                    retorno = "FHubo un error al eliminar el activo...\nIntente de nuevo o contacte con el administrador";
                }
            } else {
                retorno = "FEl activo que va a eliminar tiene depreciaciones...";
            }
        } else {
            retorno = "FEl activo que va a eliminar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    public String eliminarAfActivo(AfActivos afActivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AfActivos activoLocal = activoDao.getAfActivos(afActivo.getAfActivosPK().getAfEmpresa(), afActivo.getAfActivosPK().getAfCodigo());
        if (activoLocal != null) {
            susClave = afActivo.getAfActivosPK().getAfCodigo();
            susDetalle = "Se eliminó el activo: " + afActivo.getAfDescripcion() + " con código " + afActivo.getAfActivosPK().getAfCodigo();
            susSuceso = "DELETE";
            susTabla = "activosfijos.af_ubicaciones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            if (activoDao.eliminarAfActivos(afActivo, sisSuceso)) {
                retorno = "TEl activo se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar el activo...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl activo que va a eliminar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public boolean existeAfActivo(String empresa, String codigo) throws Exception {
        AfActivos activoLocal = activoDao.getAfActivos(empresa, codigo);
        if (activoLocal == null) {
            return false;
        }

        return true;
    }

    @Override
    public byte[] generarReporteAfActivoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfActivoTO> listado, String nombreReporte) {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteAfActivoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfActivoTO> listado) throws Exception {
        try {
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de activo fijo");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDescripción" + "¬" + "SFecha" + "¬" + "SSector" + "¬" + "SUbicación" + "¬" + "SGrupo" + "¬" + "SValor adquisión" + "¬" + "SValor residual" + "¬" + "SDepreciación (meses)" + "¬" + "SDepreciación (monto)");
            for (AfActivoTO afActivoTO : listado) {
                listaCuerpo.add((afActivoTO.getAfCodigo() == null ? "B" : "S" + afActivoTO.getAfCodigo())
                        + "¬" + (afActivoTO.getAfDescripcion() == null ? "B" : "S" + afActivoTO.getAfDescripcion())
                        + "¬" + (afActivoTO.getAfFechaAdquisicion() == null ? "B" : "S" + fecha.format(afActivoTO.getAfFechaAdquisicion()))
                        + "¬" + (afActivoTO.getPrdListaSectorTO() == null? "B" :  afActivoTO.getPrdListaSectorTO().getSecNombre() == null ? "B" : "S" + afActivoTO.getPrdListaSectorTO().getSecNombre())
                        + "¬" + (afActivoTO.getAfUbicaciones() == null ? "B" : "S" + afActivoTO.getAfUbicaciones().getUbiDescripcion())
                        + "¬" + (afActivoTO.getAfCategorias().getCatDescripcion() == null ? "B" : "S" + afActivoTO.getAfCategorias().getCatDescripcion())
                        + "¬" + (afActivoTO.getAfValorAdquision() == null ? "B" : "D" + afActivoTO.getAfValorAdquision())
                        + "¬" + (afActivoTO.getAfValorResidual() == null ? "B" : "D" + afActivoTO.getAfValorResidual())
                        + "¬" + (afActivoTO.getAfDepreciacionInicialMeses() == null ? "B" : "D" + afActivoTO.getAfDepreciacionInicialMeses())
                        + "¬" + (afActivoTO.getAfDepreciacionInicialMonto() == null ? "B" : "D" + afActivoTO.getAfDepreciacionInicialMonto())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    public Map<String, Object> exportarReporteListadoActivoFijoCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfListadoActivoFijoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de activo fijo");
            listaCabecera.add("S");
            listaCuerpo.add("SCategoria" + "¬" + "SDescripción" + "¬" + "SUbicación" + "¬" + "SValor adquisición" + "¬" + "SValor residual" + "¬" + "SFecha" + "¬" + "SVida útil" + "¬" + "SMarca" + "¬" + "SSerie" + "¬" + "SEspecificaciones" + "¬" + "SCompra" + "¬" + "SProveedor");
            for (AfListadoActivoFijoTO afListadoActivoFijoTO : listado) {
                listaCuerpo.add((afListadoActivoFijoTO.getAfCategoria() == null ? "B" : "S" + afListadoActivoFijoTO.getAfCategoria())
                        + "¬" + (afListadoActivoFijoTO.getAfDescripcion() == null ? "B" : "S" + afListadoActivoFijoTO.getAfDescripcion())
                        + "¬" + (afListadoActivoFijoTO.getAfUbicacion() == null ? "B" : "S" + afListadoActivoFijoTO.getAfUbicacion())
                        + "¬" + (afListadoActivoFijoTO.getAfValorAdquisicion() == null ? "B" : "D" + afListadoActivoFijoTO.getAfValorAdquisicion())
                        + "¬" + (afListadoActivoFijoTO.getAfValorResidual() == null ? "B" : "D" + afListadoActivoFijoTO.getAfValorResidual())
                        + "¬" + (afListadoActivoFijoTO.getAfFechaAdquisicion() == null ? "B" : "S" + afListadoActivoFijoTO.getAfFechaAdquisicion())
                        + "¬" + (afListadoActivoFijoTO.getAfVidaUtil() == null ? "B" : "S" + afListadoActivoFijoTO.getAfVidaUtil())
                        + "¬" + (afListadoActivoFijoTO.getAfMarca() == null ? "B" : "S" + afListadoActivoFijoTO.getAfMarca())
                        + "¬" + (afListadoActivoFijoTO.getAfSerie() == null ? "B" : "S" + afListadoActivoFijoTO.getAfSerie())
                        + "¬" + (afListadoActivoFijoTO.getAfEspecificaciones() == null ? "B" : "S" + afListadoActivoFijoTO.getAfEspecificaciones())
                        + "¬" + (afListadoActivoFijoTO.getAfCompra() == null ? "B" : "S" + afListadoActivoFijoTO.getAfCompra())
                        + "¬" + (afListadoActivoFijoTO.getAfproveedorDescripcion() == null ? "B" : "S" + afListadoActivoFijoTO.getAfproveedorDescripcion()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteListadoActivoCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfListadoActivoFijoTO> listado, String nombreReporte) {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public List<AfListadoActivoFijoTO> listarActivoFijosCompras(String empresa) throws Exception {
        return activoDao.getFunlistarActivoFijosCompras(empresa);
    }

}
