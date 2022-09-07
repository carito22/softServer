package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.GrupoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.UsuarioDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class GrupoServiceImpl implements GrupoService {

    @Autowired
    private GrupoDao grupoDao;

    @Autowired
    private UsuarioDetalleDao usuarioDetalleDao;

    @Autowired
    private EmpresaDao empresaDao;

    private Boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public SisGrupoTO sisGrupoUsuariosTO(String infEmpresa, String infUsuario) throws Exception {
        return grupoDao.sisGrupoUsuariosTO(infEmpresa, infUsuario);
    }

    @Override
    public List<SisGrupoTO> getListaSisGrupo(String empresa) throws Exception {
        return grupoDao.getListaSisGrupo(empresa);
    }

    @Override
    public boolean accionSisGrupoTO(SisGrupoTO sisGrupoTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = sisGrupoTO.getGruCodigo();
        susTabla = "sistemaWeb.sis_grupo";
        if (accion == 'I') {
            susDetalle = "Se insertó el grupo " + sisGrupoTO.getGruNombre();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó datos del grupo " + sisGrupoTO.getGruCodigo();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó el grupo " + sisGrupoTO.getGruNombre();
            susSuceso = "DELETE";
        }
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (accion == 'I') {
            if (grupoDao.obtenerPorId(SisGrupo.class,
                    new SisGrupoPK(sisGrupoTO.getGruEmpresa().trim(), sisGrupoTO.getGruCodigo().trim())) == null) {
                SisGrupo sisGrupo = ConversionesSistema.ConvertirSisGrupoTO_SisGrupo(sisGrupoTO);
                sisGrupo.setSisGrupoPK(new SisGrupoPK(sisGrupoTO.getEmpCodigo(), sisGrupoTO.getGruCodigo()));
                sisGrupo.setEmpCodigo(empresaDao.obtenerPorId(SisEmpresa.class, sisGrupoTO.getEmpCodigo()));
                comprobar = grupoDao.insertarSisGrupo(sisGrupo, sisSuceso);
            }
        }
        if (accion == 'M') {
            SisGrupo sisGrupoAux = grupoDao.obtenerPorId(SisGrupo.class,
                    new SisGrupoPK(sisGrupoTO.getGruEmpresa(), sisGrupoTO.getGruCodigo()));
            if (sisGrupoAux != null) {
                sisGrupoTO.setUsrInsertaGrupo(sisGrupoAux.getUsrInsertaGrupo());
                sisGrupoTO.setUsrFechaInsertaGrupo(sisGrupoAux.getUsrFechaInsertaGrupo());
                SisGrupo sisGrupo = ConversionesSistema.ConvertirSisGrupoTO_SisGrupo(sisGrupoTO);
                sisGrupo.setSisGrupoPK(new SisGrupoPK(sisGrupoTO.getGruEmpresa(), sisGrupoTO.getGruCodigo()));
                sisGrupo.setEmpCodigo(empresaDao.obtenerPorId(SisEmpresa.class, sisGrupoTO.getEmpCodigo()));
                comprobar = grupoDao.modificarSisGrupo(sisGrupo, sisSuceso);
            }
        }
        if (accion == 'E') {
            int contador = usuarioDetalleDao.retornoContadoEliminarGrupo(sisGrupoTO.getEmpCodigo(),
                    sisGrupoTO.getGruCodigo());
            System.out.println("sisGrupoTO.getGruCodigo() " + sisGrupoTO.getGruCodigo());
            System.out.println("contador " + contador);
            if (contador == 0) {
                SisGrupo sisGrupo = ConversionesSistema.ConvertirSisGrupoTO_SisGrupo(sisGrupoTO);
                sisGrupo.setSisGrupoPK(new SisGrupoPK(sisGrupoTO.getGruEmpresa(), sisGrupoTO.getGruCodigo()));
                sisGrupo.setEmpCodigo(empresaDao.obtenerPorId(SisEmpresa.class, sisGrupoTO.getEmpCodigo()));
                comprobar = grupoDao.eliminarSisGrupo(sisGrupo, sisSuceso);
            }
        }
        return comprobar;
    }

    @Override
    public List<SisGrupoTO> getSisGrupoTOs(String empresa) throws Exception {
        return grupoDao.getSisGrupoTOs(empresa);
    }

}
