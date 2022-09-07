/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaGrupoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class PiscinaGrupoServiceImpl implements PiscinaGrupoService {

    @Autowired
    private PiscinaGrupoDao piscinaGrupoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public List<PrdPiscinaGrupo> listarPiscinaGrupo(String empresa, boolean filtrarInactivos) throws Exception {
        return piscinaGrupoDao.listarPiscinaGrupo(empresa, filtrarInactivos);
    }

    @Override
    public boolean insertarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdPiscinaGrupo grupo = piscinaGrupoDao.obtenerPorId(PrdPiscinaGrupo.class, prdPiscinaGrupo.getPrdPiscinaGrupoPK());

        if (grupo == null) {
            // CREAR EL SUCESO
            susClave = prdPiscinaGrupo.getPrdPiscinaGrupoPK().getGrupoCodigo();
            susDetalle = "Se insertó el grupo de piscina con código: " + prdPiscinaGrupo.getGrupoDescripcion();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_piscina_grupo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdPiscinaGrupo.setUsrFechaInserta(UtilsDate.timestamp());
            prdPiscinaGrupo.setUsrCodigo(sisInfoTO.getUsuario());
            prdPiscinaGrupo.setUsrEmpresa(sisInfoTO.getEmpresa());
            comprobar = piscinaGrupoDao.insertarPrdPiscinaGrupo(prdPiscinaGrupo, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public List<PrdPiscinaGrupoTO> listarPiscinaGrupoTO(String empresa, boolean filtrarInactivos) throws Exception {
        return piscinaGrupoDao.listarPiscinaGrupoTO(empresa, filtrarInactivos);
    }

    @Override
    public boolean modificarPrdPiscinaGrupo(PrdPiscinaGrupo prdPiscinaGrupo, SisInfoTO sisInfoTO) throws Exception {
        prdPiscinaGrupo.setUsrFechaInserta(UtilsDate.timestamp());
        comprobar = false;
        PrdPiscinaGrupo grupo = piscinaGrupoDao.obtenerPorId(PrdPiscinaGrupo.class, prdPiscinaGrupo.getPrdPiscinaGrupoPK());

        if (grupo != null) {
            susClave = prdPiscinaGrupo.getPrdPiscinaGrupoPK().getGrupoCodigo();
            susDetalle = "Se modificó el grupo de piscina con código:" + prdPiscinaGrupo.getPrdPiscinaGrupoPK().getGrupoCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_piscina_grupo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = piscinaGrupoDao.modificarPrdPiscinaGrupo(prdPiscinaGrupo, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarEstadoPiscinaGrupo(PrdPiscinaGrupoPK pk, boolean activar, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdPiscinaGrupo grupo = piscinaGrupoDao.obtenerPorId(PrdPiscinaGrupo.class, pk);

        if (grupo != null) {
            grupo.setGrupoActivo(activar);
            susClave = pk.getGrupoCodigo();
            susDetalle = "Se ha " + (!activar ? "inactivado" : "activado") + " el grupo de piscina con código: " + pk.getGrupoCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_piscina_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = piscinaGrupoDao.modificarPrdPiscinaGrupo(grupo, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarPrdPiscinaGrupo(PrdPiscinaGrupoPK pk, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdPiscinaGrupo grupo = piscinaGrupoDao.obtenerPorId(PrdPiscinaGrupo.class, pk);

        if (grupo != null) {
            susClave = pk.getGrupoCodigo();
            susDetalle = "Se eliminó el grupo de piscina con codigo " + pk.getGrupoCodigo();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_piscina_grupo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = piscinaGrupoDao.eliminarPrdPiscinaGrupo(grupo, sisSuceso);
        }
        return comprobar;
    }

}
