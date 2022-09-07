package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSubcategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSubcategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Service
public class ProductoSubcategoriaServiceImpl implements ProductoSubcategoriaService {

    @Autowired
    private GenericoDao<InvProductoSubcategoria, InvProductoSubcategoriaPK> productoSubcategoriaDao;
    @Autowired
    private SucesoDao sucesoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    
    @Override
    public List<InvProductoSubcategoria> listarSubcategoriasProducto(String empresa) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(InvProductoSubcategoria.class);
        filtro.add(Restrictions.eq("invProductoSubcategoriaPK.scatEmpresa", empresa));
        filtro.addOrder(Order.asc("scatDetalle"));
        return productoSubcategoriaDao.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
    public InvProductoSubcategoria insertarInvSubCategoriaProducto(InvProductoSubcategoria invProductoSubcategoria, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        if (productoSubcategoriaDao.obtener(InvProductoSubcategoria.class, invProductoSubcategoria.getInvProductoSubcategoriaPK()) != null) {
            throw new GeneralException("El código ingresado ya está siendo utilizado.", "Código duplicado");
        }
        if (obtenerProductoSubCategoria(invProductoSubcategoria) != null) {
            throw new GeneralException("El detalle ingresado ya está siendo utilizado.", "Detalle duplicado");
        }
        susClave = invProductoSubcategoria.getInvProductoSubcategoriaPK().getScatCodigo();
        susDetalle = "Se insertó la subCategoría: "+invProductoSubcategoria.getScatDetalle() + " con código "+susClave;
        susSuceso = "INSERT";
        susTabla = "inventario.inv_producto_subcategoria";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        // FECHA Y CONVERSION DE TO
        invProductoSubcategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        invProductoSubcategoria = productoSubcategoriaDao.insertar(invProductoSubcategoria);
        sucesoDao.insertar(sisSuceso);
        return invProductoSubcategoria;
    }
    
    private InvProductoSubcategoria obtenerProductoSubCategoria(InvProductoSubcategoria invProductoSubcategoria) {
        Criterio filtro;
        filtro = Criterio.forClass(InvProductoSubcategoria.class);
        filtro.add(Restrictions.eq("invProductoSubcategoriaPK.scatEmpresa", invProductoSubcategoria.getInvProductoSubcategoriaPK().getScatEmpresa()));
        filtro.add(Restrictions.eq("invProductoSubcategoriaPK.scatCodigo", invProductoSubcategoria.getInvProductoSubcategoriaPK().getScatCodigo()));
        filtro.add(Restrictions.eq("scatDetalle", invProductoSubcategoria.getScatDetalle()));
        return productoSubcategoriaDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }

    @Override
    public InvProductoSubcategoria modificarInvSubCategoriaProducto(InvProductoSubcategoria invProductoSubcategoria, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        if (productoSubcategoriaDao.existe(InvProductoSubcategoria.class, invProductoSubcategoria.getInvProductoSubcategoriaPK())) {
            InvProductoSubcategoria sc = obtenerProductoSubCategoria(invProductoSubcategoria);
            if (sc != null && !sc.getInvProductoSubcategoriaPK().getScatCodigo().equalsIgnoreCase(invProductoSubcategoria.getInvProductoSubcategoriaPK().getScatCodigo())) {
                throw new GeneralException("El detalle ingresado ya está siendo utilizado.", "Detalle duplicado");
            }
            susClave = invProductoSubcategoria.getInvProductoSubcategoriaPK().getScatCodigo();
            susDetalle = "Se modificó la subcategoría de producto: " + invProductoSubcategoria.getScatDetalle()+ " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_producto_subcategoria";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invProductoSubcategoria = productoSubcategoriaDao.actualizar(invProductoSubcategoria);
            sucesoDao.insertar(sisSuceso);
            return invProductoSubcategoria;
        } else {
            throw new GeneralException("La subcategoría de producto que va a modificar ya no existe. Intente con otro");
        }
    }

    @Override
    public boolean eliminarInvSubCategoriaProducto(InvProductoSubcategoriaPK invProductoSubcategoriaPK, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        InvProductoSubcategoria local = productoSubcategoriaDao.obtener(InvProductoSubcategoria.class, invProductoSubcategoriaPK);
        if (local != null) {
            susClave = local.getInvProductoSubcategoriaPK().getScatCodigo();
            susDetalle = "Se eliminó la subcategoría de producto: " + local.getScatDetalle()+ " con código " + susClave;
            susSuceso = "DELETE";
            susTabla = "inventario.inv_producto_subcategoria";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            productoSubcategoriaDao.eliminar(local);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("La subcategoría de producto que desea eliminar ya no existe");
        }
    }
    
}
