package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.ActivoFijoDao;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoProductoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosImpresionPlacasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSincronizarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductosConErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoRelacionados;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoProducto;

@Repository
public class ProductoDaoImpl extends GenericDaoImpl<InvProducto, InvProductoPK> implements ProductoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private NumeracionVariosDao numeracionVariosDao;
    @Autowired
    private ProductoCategoriaDao productoCategoriaDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<InvProductoDatosAdjuntos, InvProductoPK> productoDatosAdjuntosDao;
    @Autowired
    private GenericoDao<InvProductoRelacionados, Integer> productoRelacionadoDao;
    @Autowired
    private GenericoDao<InvProductoFormula, Integer> productoFormulaDao;
    @Autowired
    ActivoFijoDao activoFijoDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private SucesoProductoDao sucesoProductoDao;

    @Override
    public InvProducto buscarInvProducto(String empresa, String codigoProducto) throws Exception {
        return obtenerPorId(InvProducto.class, new InvProductoPK(empresa.trim(), codigoProducto.trim()));

    }

    @Override
    public boolean eliminarInvProducto(InvProducto invProducto, SisSuceso sisSuceso) throws Exception {

        eliminar(invProducto);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarInvProducto(InvProducto invProducto, SisSuceso sisSuceso,
            InvNumeracionVarios invNumeracionVarios) throws Exception {
        insertar(invProducto);
        if (invNumeracionVarios != null) {

            numeracionVariosDao.actualizar(invNumeracionVarios);
        }
        return true;
    }

    @Override
    public boolean insertarInvProductoImportacion(InvProducto invProducto, AfActivos afActivo, SisSuceso sisSuceso, SisSuceso sisSucesoAF, InvNumeracionVarios invNumeracionVarios) throws Exception {
        insertar(invProducto);
        //ACTIVO FIJO
        if (afActivo != null) {
            afActivo.setAfActivosPK(new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
            activoFijoDao.insertar(afActivo);
        }

        if (invNumeracionVarios != null) {
            numeracionVariosDao.actualizar(invNumeracionVarios);
        }

        sucesoDao.insertar(sisSuceso);

        if (sisSucesoAF != null) {
            sisSucesoAF.setSusClave(afActivo.getAfActivosPK().getAfCodigo());
            sisSucesoAF.setSusDetalle("Se insertó el activo fijo " + afActivo.getAfDescripcion() + ", con código: " + afActivo.getAfActivosPK().getAfCodigo());
            sucesoDao.insertar(sisSucesoAF);
        }

        return true;
    }

    @Override
    public boolean insertarInvProducto(InvProducto invProducto, AfActivos afActivo, List<InvProductoDatosAdjuntos> listadoImagenes, List<InvProductoRelacionados> listaInvProductoRelacionados, List<InvProductoFormula> listaInvProductoFormula, SisSuceso sisSuceso, InvNumeracionVarios invNumeracionVarios) throws Exception {
        insertar(invProducto);
        //IMAGENESinvProducto
        if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
            for (InvProductoDatosAdjuntos datoAdjunto : listadoImagenes) {
                String archivo = new String(datoAdjunto.getAdjArchivo(), "UTF-8");
                datoAdjunto.setInvProducto(invProducto);
                datoAdjunto.setAdjArchivo(null);
                insertarImagen(datoAdjunto);
                AmazonS3Crud.subirArchivo(datoAdjunto.getAdjBucket(), datoAdjunto.getAdjClaveArchivo(), archivo, "image/jpeg");
            }
        }
        //PRODUCTOS RELACIONADOS
        if (listaInvProductoRelacionados != null) {
            for (InvProductoRelacionados item : listaInvProductoRelacionados) {
                item.setInvProducto(invProducto);
                insertarInvProductoRelacionados(item);
            }
        }
        //PRODUCTO FORMULA
        if (listaInvProductoFormula != null && listaInvProductoFormula.size() > 0) {
            for (InvProductoFormula item : listaInvProductoFormula) {
                item.setInvProducto(invProducto);
                insertarInvProductoFormula(item);
            }
        }
        //ACTIVO FIJO
        if (afActivo != null) {
            afActivo.setAfActivosPK(new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
            activoFijoDao.insertar(afActivo);
        }

        if (invNumeracionVarios != null) {
            numeracionVariosDao.actualizar(invNumeracionVarios);
        }
        sucesoDao.insertar(sisSuceso);
        //suceso personalizado 
        SisSucesoProducto sucesoProducto = new SisSucesoProducto();
        String json = UtilsJSON.objetoToJson(invProducto);
        sucesoProducto.setSusJson(json);
        sucesoProducto.setSisSuceso(sisSuceso);
        sucesoProducto.setInvProducto(invProducto);
        sucesoProductoDao.insertar(sucesoProducto);
        //****
        return true;
    }

    @Override
    public Boolean invCambiarPrecioProductos(List<InvProducto> invProductos, List<SisSuceso> sisSucesos)
            throws Exception {

        for (InvProducto invProducto : invProductos) {
            actualizar(invProducto);
        }
        for (SisSuceso sisSuceso : sisSucesos) {
            sucesoDao.insertar(sisSuceso);
        }
        return true;
    }

    @Override
    public Boolean invSincronizarProductosCategorias(List<InvProducto> invProductos,
            List<InvProductoCategoria> invProductoCategorias, List<SisSuceso> sisSucesos) throws Exception {
        for (InvProducto invProducto : invProductos) {
            actualizar(invProducto);
        }
        for (InvProductoCategoria invProductoCategoria : invProductoCategorias) {
            productoCategoriaDao.actualizar(invProductoCategoria);
        }
        for (SisSuceso sisSuceso : sisSucesos) {
            sucesoDao.actualizar(sisSuceso);
        }
        return true;
    }

    @Override
    public boolean modificarInvProducto(InvProducto invProducto, SisSuceso sisSuceso) throws Exception {
        actualizar(invProducto);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarInvProductoImportacion(InvProducto invProducto, AfActivos afActivo, AfActivos afActivoEliminar, SisSuceso sisSuceso, SisSuceso sisSucesoAF) throws Exception {
        actualizar(invProducto);
        //ACTIVO FIJO
        if (afActivo != null) {
            AfActivos activoFijoInsertado = activoFijoDao.obtenerPorId(AfActivos.class, new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
            if (activoFijoInsertado != null) {
                afActivo.setAfActivosPK(new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                activoFijoDao.actualizar(afActivo);
                if (sisSucesoAF != null) {
                    sisSucesoAF.setSusSuceso("UPDATE");
                    sisSucesoAF.setSusClave(afActivo.getAfActivosPK().getAfCodigo());
                    sisSucesoAF.setSusDetalle("Se modificó el activo fijo " + afActivo.getAfDescripcion() + ", con código: " + afActivo.getAfActivosPK().getAfCodigo() + " desde importación de producto");
                    sucesoDao.insertar(sisSucesoAF);
                }
            } else {
                afActivo.setAfActivosPK(new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                activoFijoDao.insertar(afActivo);
                if (sisSucesoAF != null) {
                    sisSucesoAF.setSusSuceso("INSERT");
                    sisSucesoAF.setSusClave(afActivo.getAfActivosPK().getAfCodigo());
                    sisSucesoAF.setSusDetalle("Se insertó el activo fijo " + afActivo.getAfDescripcion() + ", con código: " + afActivo.getAfActivosPK().getAfCodigo() + " desde importación de producto");
                    sucesoDao.insertar(sisSucesoAF);
                }
            }
        } else {
            if (afActivoEliminar != null) {
                afActivoEliminar.setAfActivosPK(new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                activoFijoDao.eliminar(afActivoEliminar);
                if (sisSucesoAF != null) {
                    sisSucesoAF.setSusSuceso("DELETE");
                    sisSucesoAF.setSusClave(afActivoEliminar.getAfActivosPK().getAfCodigo());
                    sisSucesoAF.setSusDetalle("Se eliminó el activo fijo " + afActivoEliminar.getAfDescripcion() + ", con código: " + afActivoEliminar.getAfActivosPK().getAfCodigo() + " desde importación de producto");
                    sucesoDao.insertar(sisSucesoAF);
                }
            }
        }

        return true;
    }

    @Override
    public boolean modificarInvProducto(InvProducto invProducto, AfActivos afActivo, AfActivos afActivoEliminar, List<InvProductoDatosAdjuntos> listadoImagenes, List<InvProductoDatosAdjuntos> listadoImagenesEliminar, List<InvProductoRelacionados> listaInvProductoRelacionados, List<InvProductoRelacionados> listaInvProductoRelacionadosEliminar, List<InvProductoFormula> listaInvProductoFormula, List<InvProductoFormula> listaInvProductoFormulaEliminar, SisSuceso sisSuceso) throws Exception {
        actualizar(invProducto);
        //IMAGENES
        if (listadoImagenes != null && !listadoImagenes.isEmpty()) {
            String bucket = sistemaWebServicio.obtenerRutaImagen(invProducto.getInvProductoPK().getProEmpresa());
            Bucket b = AmazonS3Crud.crearBucket(bucket);
            if (b != null) {
                for (InvProductoDatosAdjuntos datoAdjunto : listadoImagenes) {
                    datoAdjunto.setInvProducto(invProducto);
                    if (datoAdjunto.getAdjSecuencial() != null && datoAdjunto.getAdjSecuencial() != 0 && !datoAdjunto.getAdjSecuencial().equals("")) {
                        actualizarImagen(datoAdjunto);
                    } else {
                        String nombre = UtilsString.generarNombreAmazonS3() + ".jpg";
                        String carpeta = "productos/" + invProducto.getInvProductoPK().getProCodigoPrincipal() + "/";
                        datoAdjunto.setAdjBucket(bucket);
                        datoAdjunto.setAdjClaveArchivo(carpeta + nombre);
                        datoAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                        String archivo = new String(datoAdjunto.getAdjArchivo(), "UTF-8");
                        datoAdjunto.setAdjArchivo(null);
                        insertarImagen(datoAdjunto);
                        AmazonS3Crud.subirArchivo(datoAdjunto.getAdjBucket(), datoAdjunto.getAdjClaveArchivo(), archivo, "image/jpeg");

                    }
                }
            } else {
                throw new GeneralException("Error al crear contenedor de imágenes.");
            }
        }
        if (listadoImagenesEliminar != null && !listadoImagenesEliminar.isEmpty()) {
            for (InvProductoDatosAdjuntos datoAdjunto : listadoImagenesEliminar) {
                datoAdjunto.setInvProducto(invProducto);
                eliminarImagen(datoAdjunto);
                AmazonS3Crud.eliminarArchivo(datoAdjunto.getAdjBucket(), datoAdjunto.getAdjClaveArchivo());
            }
        }
        //PRODUCTOS RELACIONADOS
        if (listaInvProductoRelacionados != null && listaInvProductoRelacionados.size() > 0) {
            for (InvProductoRelacionados item : listaInvProductoRelacionados) {
                item.setInvProducto(invProducto);
                if (item.getPrSecuencial() != null && item.getPrSecuencial() != 0 && !item.getPrSecuencial().equals("")) {
                    actualizarInvProductoRelacionados(item);
                } else {
                    insertarInvProductoRelacionados(item);
                }
            }
        }
        if (listaInvProductoRelacionadosEliminar != null && listaInvProductoRelacionadosEliminar.size() > 0) {
            for (InvProductoRelacionados item : listaInvProductoRelacionadosEliminar) {
                item.setInvProducto(invProducto);
                eliminarInvProductoRelacionados(item);
            }
        }
        //PRODUCTOS FORMULA
        if (listaInvProductoFormula != null && listaInvProductoFormula.size() > 0) {
            for (InvProductoFormula item : listaInvProductoFormula) {
                item.setInvProducto(invProducto);
                if (item.getPrSecuencial() != null && item.getPrSecuencial() != 0 && !item.getPrSecuencial().equals("")) {
                    actualizarInvProductoFormula(item);
                } else {
                    insertarInvProductoFormula(item);
                }
            }
        }
        if (listaInvProductoFormulaEliminar != null && listaInvProductoFormulaEliminar.size() > 0) {
            for (InvProductoFormula item : listaInvProductoFormulaEliminar) {
                item.setInvProducto(invProducto);
                eliminarInvProductoFormula(item);

            }
        }
        //ACTIVO FIJO
        if (afActivo != null) {
            AfActivos activoFijoInsertado = activoFijoDao.obtenerPorId(AfActivos.class,
                    new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
            if (activoFijoInsertado != null) {
                afActivo.setAfActivosPK(new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                activoFijoDao.actualizar(afActivo);
            } else {
                afActivo.setAfActivosPK(new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                activoFijoDao.insertar(afActivo);
            }
        } else {
            if (afActivoEliminar != null) {
                afActivoEliminar.setAfActivosPK(new AfActivosPK(invProducto.getInvProductoPK().getProEmpresa(), invProducto.getInvProductoPK().getProCodigoPrincipal()));
                activoFijoDao.eliminar(afActivoEliminar);
            }
        }

        sucesoDao.insertar(sisSuceso);
        //suceso personalizado 
        SisSucesoProducto sucesoProducto = new SisSucesoProducto();
        String json = UtilsJSON.objetoToJson(invProducto);
        sucesoProducto.setSusJson(json);
        sucesoProducto.setSisSuceso(sisSuceso);
        sucesoProducto.setInvProducto(invProducto);
        sucesoProductoDao.insertar(sucesoProducto);
        //****
        return true;
    }

    @Override
    public boolean modificarInvProductoLlavePrincipal(InvProducto invProductoEliminar, InvProducto invProducto,
            SisSuceso sisSuceso) throws Exception {
        eliminar(invProductoEliminar);
        actualizar(invProducto);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String getInvProximaNumeracionProducto(String empresa, InvProductoTO invProductoTO) throws Exception {
        String sql = "SELECT num_productos FROM   inventario.inv_numeracion_varios WHERE (num_empresa = '" + empresa
                + "')";

        String consulta = (String) genericSQLDao.obtenerObjetoPorSql(sql);
        if (consulta != null) {
            invProductoTO.setProCodigoPrincipal(consulta.toString());
        } else {
            invProductoTO.setProCodigoPrincipal("00000");
        }

        int numeracion = Integer.parseInt(invProductoTO.getProCodigoPrincipal());
        String rellenarConCeros = "";
        do {
            numeracion++;
            int numeroCerosAponer = 5 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            invProductoTO.setProCodigoPrincipal(rellenarConCeros + numeracion);

        } while (buscarInvProducto(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal()) != null);
        return rellenarConCeros + numeracion;
    }

    @Override
    public Boolean buscarExisteNombreProducto(String empresa, String nombreProducto) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_producto_repetido('" + empresa + "', NULL, NULL,"
                + " NULL, NULL, NULL, NULL, NULL, '" + nombreProducto + "');";
        return (Boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public BigDecimal getCantidad3(String empresa, String codProducto) throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        String sql = "SELECT pro_factor_caja_saco_bulto FROM inventario.inv_producto where (pro_empresa='" + empresa
                + "') and (pro_codigo_principal= '" + codProducto + "')";
        List<BigDecimal> lista = genericSQLDao.obtenerPorSql(sql);
        if (lista != null) {
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        }
        return cero;
    }

    @Override
    public List<InvProductoTO> getProductoTO(String empresa, String codigo) throws Exception {
        codigo = codigo.trim();
        String sql = "SELECT null as \"actProductoTO\",  inv_producto.pro_empresa,  inv_producto.pro_codigo_principal, "
                + "pro_codigo_alterno,  pro_codigo_barra, pro_codigo_barra2, pro_codigo_barra3,"
                + "pro_codigo_barra4, pro_codigo_barra5,  pro_empaque,  pro_nombre,  pro_detalle, "
                + "pro_costo_referencial1,  pro_costo_referencial2,   pro_costo_referencial3, pro_costo_referencial4,   pro_costo_referencial5, "
                + "pro_cantidad1,   pro_cantidad2,   pro_cantidad3,   pro_cantidad4,   pro_cantidad5, "
                + "pro_cantidad6,   pro_cantidad7,   pro_cantidad8,   pro_cantidad9,   pro_cantidad10, "
                + "pro_cantidad11,   pro_cantidad12,   pro_cantidad13,   pro_cantidad14,   pro_cantidad15,   pro_cantidad16, "
                + "pro_precio1,   pro_precio2,   pro_precio3,   pro_precio4,   pro_precio5, "
                + "pro_precio6,   pro_precio7,   pro_precio8,   pro_precio9,   pro_precio10, "
                + "pro_precio11,   pro_precio12,   pro_precio13,   pro_precio14,   pro_precio15,   pro_precio16, "
                + "pro_descuento1,   pro_descuento2,   pro_descuento3,   pro_descuento4,   pro_descuento5, "
                + "pro_descuento6,   pro_descuento7,   pro_descuento8,   pro_descuento9,   pro_descuento10, "
                + "pro_descuento11,   pro_descuento12,   pro_descuento13,   pro_descuento14,   pro_descuento15,   pro_descuento16, "
                + "pro_margen_utilidad1,   pro_margen_utilidad2,   pro_margen_utilidad3, "
                + "pro_margen_utilidad4,   pro_margen_utilidad5,   pro_margen_utilidad6, "
                + "pro_margen_utilidad7,   pro_margen_utilidad8,   pro_margen_utilidad9, "
                + "pro_margen_utilidad10,   pro_margen_utilidad11,   pro_margen_utilidad12, "
                + "pro_margen_utilidad13,   pro_margen_utilidad14,   pro_margen_utilidad15, "
                + "pro_margen_utilidad16,   pro_minimo,   pro_maximo,   pro_iva, pro_ice, "
                + "pro_credito_tributario,  pro_exigir_serie,  pro_inactivo,  pro_cuenta_inventario, "
                + "pro_cuenta_gasto,  pro_cuenta_venta,  cat_empresa, "
                + "cat_codigo, mar_empresa, mar_codigo,  presu_empresa,  presu_codigo, "
                + "presc_empresa, presc_codigo,  tip_empresa,  tip_codigo,  inv_producto.med_empresa, "
                + "inv_producto.med_codigo,  inv_producto.usr_empresa,  inv_producto.usr_codigo, "
                + "inv_producto.usr_fecha_inserta,  med_conversion_libras,  con_codigo , sus_codigo,  "
                + "pro_factor_caja_saco_bulto, inv_producto_detalle_cuenta_contable.det_cuenta as pro_cuenta_costo_automatico,pro_especificaciones, pro_replicar, pro_ecommerce, pro_incluir_webshop, pro_cuenta_venta_exterior, pro_cuenta_transferencia_ipp "
                + "FROM inventario.inv_producto INNER JOIN inventario.inv_producto_medida "
                + "ON inv_producto.med_empresa = inv_producto_medida.med_empresa AND "
                + "inv_producto.med_codigo = inv_producto_medida.med_codigo  "
                + "LEFT JOIN inventario.inv_producto_detalle_cuenta_contable "
                + "ON inv_producto.pro_codigo_principal = inv_producto_detalle_cuenta_contable.det_producto AND "
                + "inv_producto.pro_empresa = inv_producto_detalle_cuenta_contable.det_empresa "
                + "WHERE inv_producto.pro_empresa = ('"
                + empresa + "') AND  inv_producto.pro_codigo_principal = ('" + codigo + "')";

        return genericSQLDao.obtenerPorSql(sql, InvProductoTO.class
        );
    }

    @Override
    public boolean getProductoRepetido(String empresa, String codigo, String alterno, String barras, String barras2,
            String barras3, String barras4, String barras5, String nombre) throws Exception {
        String sql = "SELECT * FROM inventario. fun_sw_producto_repetido(" + empresa + ", " + codigo + ", "
                + alterno + ", " + barras + ", " + barras2 + ", " + barras3 + ", " + barras4 + ", " + barras5 + ", "
                + nombre + ")";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public Boolean getPuedeEliminarProducto(String empresa, String producto) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_producto_eliminar('" + empresa + "', '" + producto + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<InvProductoSincronizarTO> invProductoSincronizar(String empresaOrigen, String empresaDestino) throws Exception {
        String sql = "SELECT * from inventario.fun_sincronizar_productos('" + empresaOrigen + "', '" + empresaDestino
                + "')";

        return genericSQLDao.obtenerPorSql(sql, InvProductoSincronizarTO.class
        );
    }

    @Override
    public List<InvFunListaProductosImpresionPlacasTO> getInvFunListaProductosImpresionPlacasTO(String empresa, String producto, boolean estado) throws Exception {
        producto = producto == null ? producto : "'" + producto + "'";
        String sql = "SELECT * FROM inventario.fun_lista_productos_impresion_placas('" + empresa + "', " + producto
                + ", '" + UtilsValidacion.fechaSistema() + "'," + estado + ")";

        return genericSQLDao.obtenerPorSql(sql, InvFunListaProductosImpresionPlacasTO.class
        );
    }

    @Override
    public List<InvProductosConErrorTO> getListadoProductosConError(String empresa) throws Exception {
        String sql = "SELECT * FROM inventario.fun_errores('" + empresa + "', " + null + ", " + true + ")";

        return genericSQLDao.obtenerPorSql(sql, InvProductosConErrorTO.class
        );
    }

    @Override
    public BigDecimal getPrecioProductoPorCantidad(String empresa, String cliente, String codProducto, BigDecimal cantidad)
            throws Exception {
        BigDecimal cero = new BigDecimal("0.00");
        String sql = "SELECT * FROM inventario.fun_obtener_precio_venta('" + empresa + "', '" + cliente + "', '" + codProducto + "', '"
                + cantidad + "')";
        List<BigDecimal> lista = genericSQLDao.obtenerPorSql(sql);
        if (lista != null) {
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        }
        return cero;
    }

    @Override
    public List<InvFunListadoProductosTO> getInvFunListadoProductosTO(String empresa, String categoria, String busqueda)
            throws Exception {
        categoria = categoria == null ? categoria : "'" + categoria + "'";
        busqueda = busqueda == null ? busqueda : "'" + busqueda + "'";
        String fecha = "'" + UtilsValidacion.fechaSistema("yyyy-MM-dd") + "'";
        String sql = "SELECT * FROM inventario.fun_listado_productos('" + empresa + "', " + categoria + ", " + busqueda
                + ", " + fecha + ")";

        return genericSQLDao.obtenerPorSql(sql, InvFunListadoProductosTO.class
        );
    }

    @Override
    public InvProductoTO obtenerProductoTO(String empresa, String codigo) throws Exception {
        codigo = codigo.trim();
        String sql = "SELECT null as \"actProductoTO\",   inv_producto.pro_empresa,   inv_producto.pro_codigo_principal, "
                + "pro_codigo_alterno,   pro_codigo_barra,  pro_codigo_barra2,  pro_codigo_barra3,"
                + "pro_codigo_barra4,  pro_codigo_barra5,   pro_empaque,   pro_nombre,   pro_detalle, "
                + "pro_costo_referencial1, pro_costo_referencial2, pro_costo_referencial3, pro_costo_referencial4, pro_costo_referencial5, "
                + "pro_cantidad1,   pro_cantidad2,   pro_cantidad3,   pro_cantidad4,   pro_cantidad5, "
                + "pro_cantidad6,   pro_cantidad7,   pro_cantidad8,   pro_cantidad9,   pro_cantidad10, "
                + "pro_cantidad11,   pro_cantidad12,   pro_cantidad13,   pro_cantidad14,   pro_cantidad15,   pro_cantidad16, "
                + "pro_precio1,   pro_precio2,   pro_precio3,   pro_precio4,   pro_precio5, "
                + "pro_precio6,   pro_precio7,   pro_precio8,   pro_precio9,   pro_precio10, "
                + "pro_precio11,   pro_precio12,   pro_precio13,   pro_precio14,   pro_precio15,   pro_precio16, "
                + "pro_descuento1,   pro_descuento2,   pro_descuento3,   pro_descuento4,   pro_descuento5, "
                + "pro_descuento6,   pro_descuento7,   pro_descuento8,   pro_descuento9,   pro_descuento10, "
                + "pro_descuento11,   pro_descuento12,   pro_descuento13,   pro_descuento14,   pro_descuento15,   pro_descuento16, "
                + "pro_margen_utilidad1,   pro_margen_utilidad2,   pro_margen_utilidad3, "
                + "pro_margen_utilidad4,   pro_margen_utilidad5,   pro_margen_utilidad6, "
                + "pro_margen_utilidad7,   pro_margen_utilidad8,   pro_margen_utilidad9, "
                + "pro_margen_utilidad10,   pro_margen_utilidad11,   pro_margen_utilidad12, "
                + "pro_margen_utilidad13,   pro_margen_utilidad14,   pro_margen_utilidad15, "
                + "pro_margen_utilidad16,   pro_minimo,   pro_maximo,   pro_iva, pro_ice, "
                + "pro_credito_tributario,   pro_exigir_serie,   pro_inactivo,   pro_cuenta_inventario, "
                + "pro_cuenta_gasto,   pro_cuenta_venta,   cat_empresa, "
                + "cat_codigo,  mar_empresa,  mar_codigo,   presu_empresa,   presu_codigo, "
                + "presc_empresa,  presc_codigo,   tip_empresa,   tip_codigo,   inv_producto.med_empresa, "
                + "inv_producto.med_codigo,   inv_producto.usr_empresa,   inv_producto.usr_codigo, "
                + "inv_producto.usr_fecha_inserta,   med_conversion_libras,   con_codigo ,  sus_codigo,  "
                + "pro_factor_caja_saco_bulto, inv_producto_detalle_cuenta_contable.det_cuenta as pro_cuenta_costo_automatico,pro_especificaciones, pro_replicar, pro_ecommerce, pro_incluir_webshop, pro_cuenta_venta_exterior, pro_cuenta_transferencia_ipp "
                + "FROM inventario.inv_producto "
                + "INNER JOIN inventario.inv_producto_medida "
                + "ON inv_producto.med_empresa = inv_producto_medida.med_empresa AND "
                + "inv_producto.med_codigo = inv_producto_medida.med_codigo "
                + "LEFT JOIN inventario.inv_producto_detalle_cuenta_contable "
                + "ON inv_producto.pro_codigo_principal = inv_producto_detalle_cuenta_contable.det_producto AND "
                + "inv_producto.pro_empresa = inv_producto_detalle_cuenta_contable.det_empresa "
                + "WHERE inv_producto.pro_empresa = ('"
                + empresa + "') AND   inv_producto.pro_codigo_principal = ('" + codigo + "')";

        return genericSQLDao.obtenerObjetoPorSql(sql, InvProductoTO.class
        );
    }

    @Override
    public InvFunListadoProductosTO obtenerInvFunListadoProductosTO(String empresa, String codigo) throws Exception {
        codigo = codigo.trim();
        String fecha = "'" + UtilsValidacion.fechaSistema("yyyy-MM-dd") + "'";
        String sql = "SELECT * FROM inventario.fun_listado_productos('" + empresa + "'," + null + ",'" + codigo + "', " + fecha + ") where prd_codigo_principal = '" + codigo + "' ";

        return genericSQLDao.obtenerObjetoPorSql(sql, InvFunListadoProductosTO.class
        );
    }

    @Override
    public String obtenerCodigoPorNombre(String empresa, String nombre) throws Exception {
        String sql = "SELECT * "
                + "FROM inventario.inv_producto WHERE inv_producto.pro_empresa = ('"
                + empresa + "') AND   inv_producto.pro_nombre = ('" + nombre + "')";
        InvProducto producto = genericSQLDao.obtenerObjetoPorSql(sql, InvProducto.class
        );

        return producto.getInvProductoPK().getProCodigoPrincipal();
    }

    /*Iamgenes*/
    @Override
    public List<InvProductoDatosAdjuntos> getAdjuntosProducto(InvProductoPK invProductoPK) throws Exception {
        String sql = "select * from inventario.inv_producto_datos_adjuntos where pro_empresa = '"
                + invProductoPK.getProEmpresa()
                + "' and  pro_codigo_principal = '"
                + invProductoPK.getProCodigoPrincipal() + "' ";

        return genericSQLDao.obtenerPorSql(sql, InvProductoDatosAdjuntos.class
        );
    }

    @Override
    public boolean insertarImagen(InvProductoDatosAdjuntos invProductoDatosAdjuntos) throws Exception {
        productoDatosAdjuntosDao.insertar(invProductoDatosAdjuntos);
        return true;
    }

    @Override
    public boolean actualizarImagen(InvProductoDatosAdjuntos invProductoDatosAdjuntos) throws Exception {
        productoDatosAdjuntosDao.actualizar(invProductoDatosAdjuntos);
        return true;
    }

    @Override
    public boolean eliminarImagen(InvProductoDatosAdjuntos invProductoDatosAdjuntos) throws Exception {
        productoDatosAdjuntosDao.eliminar(invProductoDatosAdjuntos);
        return true;
    }

    @Override
    public boolean insertarInvProductoRelacionados(InvProductoRelacionados productoRelacionado) throws Exception {
        productoRelacionadoDao.insertar(productoRelacionado);
        return true;
    }

    @Override
    public boolean actualizarInvProductoRelacionados(InvProductoRelacionados productoRelacionado) throws Exception {
        productoRelacionadoDao.actualizar(productoRelacionado);
        return true;
    }

    @Override
    public boolean eliminarInvProductoRelacionados(InvProductoRelacionados productoRelacionado) throws Exception {
        productoRelacionadoDao.eliminar(productoRelacionado);
        return true;
    }

    @Override
    public boolean insertarInvProductoFormula(InvProductoFormula productoFormula) throws Exception {
        productoFormulaDao.insertar(productoFormula);
        return true;
    }

    @Override
    public boolean actualizarInvProductoFormula(InvProductoFormula productoFormula) throws Exception {
        productoFormulaDao.actualizar(productoFormula);
        return true;
    }

    @Override
    public boolean eliminarInvProductoFormula(InvProductoFormula productoFormula) throws Exception {
        productoFormulaDao.eliminar(productoFormula);
        return true;
    }

    @Override
    public List<InvVentasDetalleProductoTO> obtenerListadoVentasDetalleProducto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception {
        if (sector != null && !sector.equals("")) {
            sector = "'" + sector + "'";
        } else {
            sector = null;
        }
        if (piscina != null && !piscina.equals("")) {
            piscina = "'" + piscina + "'";
        } else {
            piscina = null;
        }
        if (cliente != null && !cliente.equals("")) {
            cliente = "'" + cliente + "'";
        } else {
            cliente = null;
        }
        if (bodega != null && !bodega.equals("")) {
            bodega = "'" + bodega + "'";
        } else {
            bodega = null;
        }
        String sql = "SELECT * FROM inventario.fun_ventas_detalle_producto('" + empresa + "'," + sector + "," + piscina + "," + null + ",'" + fechaDesde + "', '" + fechaHasta + "'," + cliente + "," + bodega + ")";

        return genericSQLDao.obtenerPorSql(sql, InvVentasDetalleProductoTO.class
        );
    }

    @Override
    public List<InvVentasDetalleProductoTO> obtenerListadoVentasDetalleProductoAgrupadoCliente(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception {
        if (sector != null && !sector.equals("")) {
            sector = "'" + sector + "'";
        } else {
            sector = null;
        }
        if (piscina != null && !piscina.equals("")) {
            piscina = "'" + piscina + "'";
        } else {
            piscina = null;
        }
        if (cliente != null && !cliente.equals("")) {
            cliente = "'" + cliente + "'";
        } else {
            cliente = null;
        }
        if (bodega != null && !bodega.equals("")) {
            bodega = "'" + bodega + "'";
        } else {
            bodega = null;
        }
        String sql = "SELECT * FROM inventario.fun_ventas_detalle_producto_agrupado_cliente('" + empresa + "'," + sector + "," + piscina + "," + null + ",'" + fechaDesde + "', '" + fechaHasta + "'," + cliente + "," + bodega + ")";

        return genericSQLDao.obtenerPorSql(sql, InvVentasDetalleProductoTO.class
        );
    }

    @Override
    public List<InvVentasDetalleProductoTO> obtenerListadoVentasDetalleProductoAgrupadoCC(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String cliente, String bodega) throws Exception {
        if (sector != null && !sector.equals("")) {
            sector = "'" + sector + "'";
        } else {
            sector = null;
        }
        if (piscina != null && !piscina.equals("")) {
            piscina = "'" + piscina + "'";
        } else {
            piscina = null;
        }
        if (cliente != null && !cliente.equals("")) {
            cliente = "'" + cliente + "'";
        } else {
            cliente = null;
        }
        if (bodega != null && !bodega.equals("")) {
            bodega = "'" + bodega + "'";
        } else {
            bodega = null;
        }
        String sql = "SELECT * FROM inventario.fun_ventas_detalle_producto_agrupado_centro_costo('" + empresa + "'," + sector + "," + piscina + "," + null + ",'" + fechaDesde + "', '" + fechaHasta + "'," + cliente + "," + bodega + ")";

        return genericSQLDao.obtenerPorSql(sql, InvVentasDetalleProductoTO.class
        );
    }

    @Override
    public List<InvComprasDetalleProductoTO> obtenerListadoComprasDetalleProducto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception {
        if (sector != null && !sector.equals("")) {
            sector = "'" + sector + "'";
        } else {
            sector = null;
        }
        if (piscina != null && !piscina.equals("")) {
            piscina = "'" + piscina + "'";
        } else {
            piscina = null;
        }
        if (bodega != null && !bodega.equals("")) {
            bodega = "'" + bodega + "'";
        } else {
            bodega = null;
        }
        String sql = "SELECT * FROM inventario.fun_compras_detalle_producto('" + empresa + "'," + sector + "," + piscina + "," + null + ",'" + fechaDesde + "', '" + fechaHasta + "'," + bodega + ")";

        return genericSQLDao.obtenerPorSql(sql, InvComprasDetalleProductoTO.class
        );
    }

    @Override
    public List<InvProductoSinMovimientoTO> obtenerProductosSinMovimientos(String empresa) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_reporte_productos_sin_movimientos('" + empresa + "')";

        return genericSQLDao.obtenerPorSql(sql, InvProductoSinMovimientoTO.class
        );
    }

    @Override
    public List<Integer> listaImagenesNoMigradas(String empresa) throws Exception {
        String sql = "select adj_secuencial from inventario.inv_producto_datos_adjuntos \n"
                + "where pro_empresa = '" + empresa + "' and\n"
                + " adj_archivo is not null and adj_url_archivo is null;";
        return genericSQLDao.obtenerPorSql(sql);
    }

    @Override
    public List<Integer> listaImagenesMigradas(String empresa) throws Exception {
        String sql = "select adj_secuencial from inventario.inv_producto_datos_adjuntos \n"
                + "where pro_empresa = '" + empresa + "' and\n"
                + " adj_archivo is not null and adj_url_archivo is not null and not adj_verificado;";
        return genericSQLDao.obtenerPorSql(sql);
    }

    @Override
    public List<InvComprasDetalleProductoTO> obtenerListadoComprasDetalleProductoAgrupadoProveedor(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception {
        if (sector != null && !sector.equals("")) {
            sector = "'" + sector + "'";
        } else {
            sector = null;
        }
        if (piscina != null && !piscina.equals("")) {
            piscina = "'" + piscina + "'";
        } else {
            piscina = null;
        }
        if (bodega != null && !bodega.equals("")) {
            bodega = "'" + bodega + "'";
        } else {
            bodega = null;
        }
        String sql = "SELECT * FROM inventario.fun_compras_detalle_producto_agrupado_proveedor('" + empresa + "'," + sector + "," + piscina + "," + null + ",'" + fechaDesde + "', '" + fechaHasta + "'," + bodega + ")";

        return genericSQLDao.obtenerPorSql(sql, InvComprasDetalleProductoTO.class
        );
    }

    @Override
    public List<InvComprasDetalleProductoTO> obtenerListadoComprasDetalleProductoAgrupadoCentroCosto(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception {
        if (sector != null && !sector.equals("")) {
            sector = "'" + sector + "'";
        } else {
            sector = null;
        }
        if (piscina != null && !piscina.equals("")) {
            piscina = "'" + piscina + "'";
        } else {
            piscina = null;
        }
        if (bodega != null && !bodega.equals("")) {
            bodega = "'" + bodega + "'";
        } else {
            bodega = null;
        }
        String sql = "SELECT * FROM inventario.fun_compras_detalle_producto_agrupado_centro_costo('" + empresa + "'," + sector + "," + piscina + "," + null + ",'" + fechaDesde + "', '" + fechaHasta + "'," + bodega + ")";

        return genericSQLDao.obtenerPorSql(sql, InvComprasDetalleProductoTO.class
        );
    }

    @Override
    public List<InvComprasDetalleProductoTO> obtenerListadoComprasDetalleProductoAgrupadoEquipoControl(String empresa, String fechaDesde, String fechaHasta, String sector, String piscina, String bodega) throws Exception {
        if (sector != null && !sector.equals("")) {
            sector = "'" + sector + "'";
        } else {
            sector = null;
        }
        if (piscina != null && !piscina.equals("")) {
            piscina = "'" + piscina + "'";
        } else {
            piscina = null;
        }
        if (bodega != null && !bodega.equals("")) {
            bodega = "'" + bodega + "'";
        } else {
            bodega = null;
        }
        String sql = "SELECT * FROM inventario.fun_compras_detalle_producto_agrupado_equipo_control('" + empresa + "'," + sector + "," + piscina + "," + null + ",'" + fechaDesde + "', '" + fechaHasta + "'," + bodega + ")";

        return genericSQLDao.obtenerPorSql(sql, InvComprasDetalleProductoTO.class
        );
    }
}
