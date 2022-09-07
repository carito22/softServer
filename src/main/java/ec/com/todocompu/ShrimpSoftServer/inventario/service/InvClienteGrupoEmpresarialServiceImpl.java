package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteGrupoEmpresarialTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarial;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarialPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@Service
public class InvClienteGrupoEmpresarialServiceImpl implements InvClienteGrupoEmpresarialService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<InvClienteGrupoEmpresarial, InvClienteGrupoEmpresarialPK> invClienteGrupoEmpresarialDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public InvClienteGrupoEmpresarial insertarInvClienteGrupoEmpresarial(InvClienteGrupoEmpresarial invClienteGrupoEmpresarial, SisInfoTO sisInfoTO) throws Exception {
        if (invClienteGrupoEmpresarialDao.obtener(InvClienteGrupoEmpresarial.class, invClienteGrupoEmpresarial.getInvClienteGrupoEmpresarialPK()) != null) {
            throw new GeneralException("El código ingresado ya está siendo utilizado.", "Código duplicado");
        }
        InvClienteGrupoEmpresarial invClienteGrupoEmpresarialPorDetalle = obtenerGrupoEmpresarialPorDetalle(invClienteGrupoEmpresarial);
        if (invClienteGrupoEmpresarialPorDetalle != null) {
            throw new GeneralException("El detalle ingresado ya está siendo utilizado.", "Detalle duplicado");
        }
        //PREPARANDO SISUSCESO
        susClave = invClienteGrupoEmpresarial.getInvClienteGrupoEmpresarialPK().getGeCodigo() + "";
        susDetalle = "Se insertó grupo empresarial " + invClienteGrupoEmpresarial.getGeNombre() + " con código " + susClave;
        susSuceso = "INSERT";
        susTabla = "inventario.inv_cliente_grupo_empresarial";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        //FECHA Y CONVERSION DE TO
        invClienteGrupoEmpresarial = invClienteGrupoEmpresarialDao.insertar(invClienteGrupoEmpresarial);
        sucesoDao.insertar(sisSuceso);

        return invClienteGrupoEmpresarial;
    }

    @Override
    public InvClienteGrupoEmpresarial obtenerInvClienteGrupoEmpresarial(String empresa, String codigo) throws Exception {
        return invClienteGrupoEmpresarialDao.obtener(InvClienteGrupoEmpresarial.class, new InvClienteGrupoEmpresarialPK(empresa, codigo));
    }

    @Override
    public InvClienteGrupoEmpresarial modificarInvClienteGrupoEmpresarial(InvClienteGrupoEmpresarial invClienteGrupoEmpresarial, SisInfoTO sisInfoTO) throws GeneralException {
        if (invClienteGrupoEmpresarialDao.existe(InvClienteGrupoEmpresarial.class, invClienteGrupoEmpresarial.getInvClienteGrupoEmpresarialPK())) {
            InvClienteGrupoEmpresarial invClienteGrupoEmpresarialPorDetalle = obtenerGrupoEmpresarialPorDetalle(invClienteGrupoEmpresarial);
            if (invClienteGrupoEmpresarialPorDetalle != null && !invClienteGrupoEmpresarialPorDetalle.getInvClienteGrupoEmpresarialPK().getGeCodigo().equalsIgnoreCase(invClienteGrupoEmpresarial.getInvClienteGrupoEmpresarialPK().getGeCodigo())) {
                throw new GeneralException("El detalle ingresado ya está siendo utilizado.", "Detalle duplicado");
            }
            //PREPARANDO SISUSCESO
            susClave = invClienteGrupoEmpresarial.getInvClienteGrupoEmpresarialPK().getGeCodigo() + "";
            susDetalle = "Se insertó grupo empresarial " + invClienteGrupoEmpresarial.getGeNombre() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_cliente_grupo_empresarial";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invClienteGrupoEmpresarial = invClienteGrupoEmpresarialDao.actualizar(invClienteGrupoEmpresarial);
            sucesoDao.insertar(sisSuceso);
            return invClienteGrupoEmpresarial;
        } else {
            throw new GeneralException("El grupo empresarial que va a modificar ya no existe.", "No se puede modificar");
        }
    }

    @Override
    public List<InvClienteGrupoEmpresarial> getInvClienteGrupoEmpresarial(String empresa, String busqueda) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(InvClienteGrupoEmpresarial.class);
        filtro.add(Restrictions.eq("invClienteGrupoEmpresarialPK.geEmpresa", empresa));
        filtro.addOrder(Order.desc("geNombre"));
        return invClienteGrupoEmpresarialDao.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
    public List<InvClienteGrupoEmpresarialTO> getInvClienteGrupoEmpresarialTO(String empresa, String busqueda) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente_grupo_empresarial WHERE ge_empresa = '" + empresa + "' ORDER BY "
                + "inventario.inv_cliente_grupo_empresarial.ge_nombre;";
        List<InvClienteGrupoEmpresarialTO> i = genericSQLDao.obtenerPorSql(sql, InvClienteGrupoEmpresarialTO.class);
        return i;
    }

    @Override
    public boolean eliminarInvClienteGrupoEmpresarial(InvClienteGrupoEmpresarialPK pk, SisInfoTO sisInfoTO) throws GeneralException {
        InvClienteGrupoEmpresarial invClienteGrupoEmpresarial = invClienteGrupoEmpresarialDao.obtener(InvClienteGrupoEmpresarial.class, pk);
        if (invClienteGrupoEmpresarial != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invClienteGrupoEmpresarial.getInvClienteGrupoEmpresarialPK().getGeCodigo() + "";
            susDetalle = "Se eliminó grupo empresarial " + invClienteGrupoEmpresarial.getGeNombre() + " con código " + susClave;
            susSuceso = "DELETE";
            susTabla = "inventario.inv_cliente_grupo_empresarial";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ELIMINAR
            invClienteGrupoEmpresarialDao.eliminar(invClienteGrupoEmpresarial);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El grupo empresarial ya no esta disponible.", "No se puede crear existente");
        }
    }

    private InvClienteGrupoEmpresarial obtenerGrupoEmpresarialPorDetalle(InvClienteGrupoEmpresarial invClienteGrupoEmpresarial) {
        Criterio filtro;
        filtro = Criterio.forClass(InvClienteGrupoEmpresarial.class);
        filtro.add(Restrictions.eq("invClienteGrupoEmpresarialPK.geEmpresa", invClienteGrupoEmpresarial.getInvClienteGrupoEmpresarialPK().getGeEmpresa()));
        filtro.add(Restrictions.eq("geNombre", invClienteGrupoEmpresarial.getGeNombre()));
        return invClienteGrupoEmpresarialDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }

}
