package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.GrupoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.PermisoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisMenu;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPermisoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPermiso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPermisoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PermisoServiceImpl implements PermisoService {

    @Autowired
    private PermisoDao permisoDao;

    @Autowired
    private GrupoDao grupoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private String mensaje = "";

    @Override
    public List<SisMenu> getListaPermisoTO(String empCodigo, String grupoCodigo, String perModulo) throws Exception {
        return permisoDao.getListaPermisoTO(empCodigo, grupoCodigo, perModulo);
    }

    @Override
    public List<String> getListaPermisoModulo(String empCodigo, String grupoCodigo) throws Exception {
        return permisoDao.getListaPermisoModulo(empCodigo, grupoCodigo);
    }

    @Override
    public boolean modificarSisPermiso(List<SisPermisoTO> listaSisPermisoTO, String usuario, String codModifGrupo,
            SisInfoTO sisInfoTO) throws Exception {
        SisGrupo sisGrupo = null;
        List<SisPermiso> sisPermisos = new ArrayList<SisPermiso>();

            for (SisPermisoTO sisPermisoTO : listaSisPermisoTO) {
                SisPermiso sisPermiso = permisoDao.obtenerPorId(SisPermiso.class,
                        new SisPermisoPK(sisPermisoTO.getEmpCodigo(), sisPermisoTO.getPerModulo(),
                                sisPermisoTO.getPerMenu(), sisPermisoTO.getPerItem()));
                sisPermiso.setPerUsuarios(sisPermisoTO.getCodigoGrupo());
                sisPermisos.add(sisPermiso);
            }

            sisGrupo = grupoDao.obtenerPorId(SisGrupo.class, new SisGrupoPK(sisInfoTO.getEmpresa(), codModifGrupo));
            susClave = codModifGrupo;
            susDetalle = "Cambio de permiso a grupo " + sisGrupo.getGruNombre() + "  " + sisGrupo.getGruNombre();
            susSuceso = "UPDATE";
            susTabla = "sistema.sis_permiso";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            return permisoDao.modificarSisPermiso(sisPermisos, sisSuceso);
        }

    @Override
    public List<SisPermisoTO> getListaPermisoTO(String grupoCodigo, String empCodigo) throws Exception {
        return permisoDao.getListaPermisoTO(grupoCodigo, empCodigo);
    }

    @Override
    public List<SisMenu> getMenuWeb(String usuario, boolean produccion) {
        return permisoDao.getMenuWeb(usuario, produccion);
    }

}
