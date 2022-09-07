package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoCategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaComboProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductoCategoriaServiceImpl implements ProductoCategoriaService {

    @Autowired
    private ProductoCategoriaDao productoCategoriaDao;
    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private EstructuraDao estructuraDao;
    @Autowired
    private CuentasService cuentasService;

    @Override
    public boolean comprobarInvProductoCategoria(String empresa, String codigoCategoria) throws Exception {
        return productoCategoriaDao.comprobarInvProductoCategoria(empresa, codigoCategoria);
    }

    @Override
    public InvProductoCategoria buscarInvProductoCategoria(String empresa, String codigo) throws Exception {
        return productoCategoriaDao.buscarInvProductoCategoria(empresa, codigo);
    }

    @Override
    public List<InvProductoCategoriaTO> getInvProductoCategoriaTO(String empresa) throws Exception {
        return productoCategoriaDao.getInvProductoCategoriaTO(empresa);
    }

    @Override
    public List<InvCategoriaComboProductoTO> getListaCategoriaComboTO(String empresa) throws Exception {
        return productoCategoriaDao.getListaCategoriaComboTO(empresa);
    }

    @Override
    public Boolean getPrecioFijoCategoriaProducto(String empresa, String codigoCategoria) throws Exception {
        return productoCategoriaDao.getPrecioFijoCategoriaProducto(empresa, codigoCategoria);
    }

    @Override
    public String accionInvProductoCategoria(InvProductoCategoriaTO invProductoCategoriaTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = invProductoCategoriaTO.getCatCodigo() + " : " + invProductoCategoriaTO.getCatDetalle();
        if (accion == 'I') {
            susDetalle = "Se insertó categoría producto " + invProductoCategoriaTO.getCatDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó categoría producto " + invProductoCategoriaTO.getCatDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó categoría producto " + invProductoCategoriaTO.getCatDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_producto_catego";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProductoCategoria
        invProductoCategoriaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvProductoCategoria invProductoCategoria = ConversionesInventario
                .convertirInvProductoCategoriaTO_InvProductoCategoria(invProductoCategoriaTO);

        InvProductoCategoria invProductoCategoriaAux = null;

        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (productoCategoriaDao.comprobarInvProductoCategoria(invProductoCategoriaTO.getCatEmpresa(),
                    invProductoCategoriaTO.getCatCodigo())) {
                if (productoCategoriaDao.comprobarEliminarInvProductoCategoria(
                        invProductoCategoriaTO.getCatEmpresa(), invProductoCategoriaTO.getCatCodigo())) {
                    invProductoCategoriaAux = productoCategoriaDao.buscarInvProductoCategoria(
                            invProductoCategoriaTO.getCatEmpresa(), invProductoCategoriaTO.getCatCodigo());
                    invProductoCategoria.setUsrFechaInserta(invProductoCategoriaAux.getUsrFechaInserta());

                    comprobar = productoCategoriaDao.accionInvProductoCategoria(invProductoCategoria, sisSuceso,
                            accion);
                } else {
                    mensaje = "FNo se puede eliminar, la Categoría ya tiene Productos...";
                }
            } else {
                mensaje = "FNo se encuentra la Categoría Producto...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (productoCategoriaDao.comprobarInvProductoCategoria(invProductoCategoriaTO.getCatEmpresa(),
                    invProductoCategoriaTO.getCatCodigo())) {
                invProductoCategoriaAux = productoCategoriaDao.buscarInvProductoCategoria(
                        invProductoCategoriaTO.getCatEmpresa(), invProductoCategoriaTO.getCatCodigo());
                invProductoCategoria.setUsrFechaInserta(invProductoCategoriaAux.getUsrFechaInserta());
                comprobar = productoCategoriaDao.accionInvProductoCategoria(invProductoCategoria, sisSuceso,
                        accion);
            } else {
                mensaje = "FNo se encuentra la Categoría Producto...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!productoCategoriaDao.comprobarInvProductoCategoria(invProductoCategoriaTO.getCatEmpresa(), invProductoCategoriaTO.getCatCodigo())) {
                invProductoCategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = productoCategoriaDao.accionInvProductoCategoria(invProductoCategoria, sisSuceso, accion);
            } else {
                mensaje = "FYa existe la Categoría Producto...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa categoría producto: Código " + invProductoCategoriaTO.getCatCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa categoría producto: Código " + invProductoCategoriaTO.getCatCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa categoría producto: Código " + invProductoCategoriaTO.getCatCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public boolean eliminarInvProductoCategoria(InvProductoCategoriaPK invProductoCategoriaPK, SisInfoTO sisInfoTO) throws Exception {
        InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(invProductoCategoriaPK.getCatEmpresa(), invProductoCategoriaPK.getCatCodigo());
        if (invProductoCategoria != null) {
            comprobar = false;
            ///// CREANDO Suceso
            susClave = invProductoCategoriaPK.getCatCodigo() + " : " + invProductoCategoria.getCatDetalle();
            susDetalle = "Se eliminó categoría producto " + invProductoCategoria.getCatDetalle();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_producto_categoria";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (productoCategoriaDao.comprobarEliminarInvProductoCategoria(invProductoCategoriaPK.getCatEmpresa(), invProductoCategoriaPK.getCatCodigo())) {
                comprobar = productoCategoriaDao.accionInvProductoCategoria(invProductoCategoria, sisSuceso, 'E');
            } else {
                mensaje = "No se puede eliminar, la Categoría ya tiene Productos...";
                throw new GeneralException(mensaje, mensaje);
            }
            return comprobar;
        } else {
            throw new GeneralException("El producto categoría que desea eliminar ya no existe", "El producto categoría que desea eliminar ya no existe");
        }
    }

    @Override
    public Map<String, Object> validacionGrupoSegunCategoria(String empresa, String codigoCategoria, String cuentaInventario, String cuentaVenta, String cuentaCostoAutomatico, String cuentaGasto, String cuentaVentaExterior, boolean isExportadora, boolean isActivoFijo) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        campos.put("cuentaCompraValido", false);
        campos.put("cuentaVentaValido", false);
        campos.put("cuentaVentaLocalValido", false);
        campos.put("cuentaVentaExteriorValido", false);
        campos.put("cuentaCostoAutomaticoValido", true);
        campos.put("cuentaGastoValido", false);
        campos.put("mensaje", "T");

        if (codigoCategoria != null) {
            InvProductoCategoria invProductoCategoria = productoCategoriaDao.buscarInvProductoCategoria(empresa, codigoCategoria);
            if (invProductoCategoria != null) {

                List<ConEstructuraTO> listadoEstructura = estructuraDao.getListaConEstructuraTO(empresa);
                Integer tamanioEstructura = 0;
                if (listadoEstructura != null && !listadoEstructura.isEmpty()) {
                    tamanioEstructura = listadoEstructura.get(0).getEstGrupo1() + listadoEstructura.get(0).getEstGrupo2() + listadoEstructura.get(0).getEstGrupo3() + listadoEstructura.get(0).getEstGrupo4() + listadoEstructura.get(0).getEstGrupo5() + listadoEstructura.get(0).getEstGrupo6();
                }

                if (invProductoCategoria.getCatCuentaCompra() != null && !invProductoCategoria.getCatCuentaCompra().equals("") && invProductoCategoria.getCatCuentaCompra().length() == tamanioEstructura) {//ES cuenta detalle
                    campos.put("cuentaInventario", cuentasService.obtenerConCuentaTO(empresa, invProductoCategoria.getCatCuentaCompra()));
                    cuentaInventario = invProductoCategoria.getCatCuentaCompra();
                }
                if (invProductoCategoria.getCatCuentaVenta() != null && !invProductoCategoria.getCatCuentaVenta().equals("") && invProductoCategoria.getCatCuentaVenta().length() == tamanioEstructura) {//ES cuenta detalle
                    campos.put("cuentaVenta", cuentasService.obtenerConCuentaTO(empresa, invProductoCategoria.getCatCuentaVenta()));
                    cuentaVenta = invProductoCategoria.getCatCuentaVenta();
                }

                //*************validación cuando es exportadora**********************
                if (!isExportadora) {
                    campos.put("cuentaVentaExteriorValido", true);
                } else {
                    //exterior
                    if (cuentaVentaExterior != null) {
                        if (invProductoCategoria.getCatCuentaVenta() != null) {
                            //cuenta detalle
                            if (invProductoCategoria.getCatCuentaVenta().length() == tamanioEstructura) {
                                if (invProductoCategoria.getCatCuentaVenta().equalsIgnoreCase(cuentaVentaExterior)) {
                                    campos.put("cuentaVentaExteriorValido", true);
                                }
                            } else {
                                //cuenta grupo
                                int tamanioGrupo = invProductoCategoria.getCatCuentaVenta().length();
                                String grupoSeleccionadoLocal = cuentaVentaExterior.substring(0, tamanioGrupo);
                                if (grupoSeleccionadoLocal.equals(invProductoCategoria.getCatCuentaVenta())) {
                                    campos.put("cuentaVentaExteriorValido", true);
                                }
                            }
                        } else {
                            campos.put("cuentaVentaExteriorValido", true);
                        }
                    } else {
                        campos.put("cuentaVentaExteriorValido", true);
                    }
                }
                //****************************************************************

                if (!isActivoFijo) {

                    if (invProductoCategoria.getCatCuentaCostoVenta() != null && !invProductoCategoria.getCatCuentaCostoVenta().equals("") && invProductoCategoria.getCatCuentaCostoVenta().length() == tamanioEstructura) {//ES cuenta detalle
                        campos.put("cuentaGasto", cuentasService.obtenerConCuentaTO(empresa, invProductoCategoria.getCatCuentaCostoVenta()));
                        cuentaGasto = invProductoCategoria.getCatCuentaCostoVenta();
                    }
                }

                //**************************************compra******************************************************
                if (invProductoCategoria.getCatCuentaCompra() != null && cuentaInventario != null) {
                    if (invProductoCategoria.getCatCuentaCompra().length() == tamanioEstructura) {//ES cuenta detalle
                        if (invProductoCategoria.getCatCuentaCompra().equalsIgnoreCase(cuentaInventario)) {
                            campos.put("cuentaCompraValido", true);
                        }
                    } else {
                        int tamanioGrupo = invProductoCategoria.getCatCuentaCompra().length();
                        String grupoSeleccionado = cuentaInventario.substring(0, tamanioGrupo);
                        if (grupoSeleccionado.equals(invProductoCategoria.getCatCuentaCompra())) {
                            campos.put("cuentaCompraValido", true);
                        }
                    }
                } else {
                    campos.put("cuentaCompraValido", true);
                }
                //**************************************venta********************************************************
                if (invProductoCategoria.getCatCuentaVenta() != null && cuentaVenta != null) {
                    if (invProductoCategoria.getCatCuentaVenta().length() == tamanioEstructura) {//ES cuenta detalle
                        if (invProductoCategoria.getCatCuentaVenta().equalsIgnoreCase(cuentaVenta)) {
                            campos.put("cuentaVentaValido", true);
                        }
                    } else {
                        int tamanioGrupo = invProductoCategoria.getCatCuentaVenta().length();
                        String grupoSeleccionado = cuentaVenta.substring(0, tamanioGrupo);
                        if (grupoSeleccionado.equals(invProductoCategoria.getCatCuentaVenta())) {
                            campos.put("cuentaVentaValido", true);
                        }
                    }
                } else {
                    campos.put("cuentaVentaValido", true);
                }
                //************************************cuenta gasto*************************************************
                if (invProductoCategoria.getCatCuentaCostoVenta() != null && cuentaGasto != null) {
                    if (invProductoCategoria.getCatCuentaCostoVenta().length() == tamanioEstructura) {//ES cuenta detalle
                        if (invProductoCategoria.getCatCuentaCostoVenta().equalsIgnoreCase(cuentaGasto)) {
                            campos.put("cuentaGastoValido", true);
                        }
                    } else {
                        int tamanioGrupo = invProductoCategoria.getCatCuentaCostoVenta().length();
                        String grupoSeleccionado = cuentaGasto.substring(0, tamanioGrupo);
                        if (grupoSeleccionado.equals(invProductoCategoria.getCatCuentaCostoVenta())) {
                            campos.put("cuentaGastoValido", true);
                        }
                    }
                } else {
                    campos.put("cuentaGastoValido", true);
                }

            } else {
                campos.put("mensaje", "FLa categoría de producto no existe");
            }
        } else {
            campos.put("mensaje", "FDebe seleccionar la categoría");
        }

        return campos;
    }

}
