/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaGrupoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaGrupoRelacionDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoRelacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
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
public class PiscinaGrupoRelacionServiceImpl implements PiscinaGrupoRelacionService {

    @Autowired
    private PiscinaGrupoRelacionDao piscinaGrupoRelacionDao;
    @Autowired
    private PiscinaGrupoDao piscinaGrupoDao;
    @Autowired
    private PiscinaDao piscinaDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public List<PrdPiscinaGrupoRelacionTO> listarPiscinaGrupoRelacionTO(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception {
        return piscinaGrupoRelacionDao.listarPiscinaGrupoRelacionTO(grupoPK, prdPiscinaPK);
    }

    @Override
    public List<PrdPiscinaGrupoRelacion> listarPiscinaGrupoRelacion(PrdPiscinaGrupoPK grupoPK, PrdPiscinaPK prdPiscinaPK) throws Exception {
        return piscinaGrupoRelacionDao.listarPiscinaGrupoRelacion(grupoPK, prdPiscinaPK);
    }

    @Override
    public PrdPiscinaGrupoRelacion insertarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacionTO prdPiscinaGrupo, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;

        PrdPiscinaGrupoPK grupoPk = new PrdPiscinaGrupoPK(prdPiscinaGrupo.getGrupoEmpresa(), prdPiscinaGrupo.getGrupoCodigo());
        PrdPiscinaGrupo grupo = piscinaGrupoDao.obtenerPorId(PrdPiscinaGrupo.class, grupoPk);

        PrdPiscinaPK piscinaPk = new PrdPiscinaPK(prdPiscinaGrupo.getPisEmpresa(), prdPiscinaGrupo.getPisSector(), prdPiscinaGrupo.getPisNumero());
        PrdPiscina piscina = piscinaDao.obtenerPorId(PrdPiscina.class, piscinaPk);
        PrdPiscinaGrupoRelacion prdPiscinaGrupoRel = null;
        if (grupo != null && piscina != null) {
            prdPiscinaGrupoRel = piscinaGrupoRelacionDao.obtenerPiscinaGrupoRelacion(grupo.getPrdPiscinaGrupoPK(), piscina.getPrdPiscinaPK());
            if (prdPiscinaGrupoRel == null) {
                // CREAR EL SUCESO
                susClave = grupo.getPrdPiscinaGrupoPK().getGrupoEmpresa() + '|'
                        + grupo.getPrdPiscinaGrupoPK().getGrupoCodigo() + '|'
                        + piscina.getPrdPiscinaPK().getPisSector() + '|'
                        + piscina.getPrdPiscinaPK().getPisNumero();
                susDetalle = "Se insertó el grupo de piscina relación con empresa: " + grupo.getPrdPiscinaGrupoPK().getGrupoEmpresa()
                        + " grupo:" + grupo.getPrdPiscinaGrupoPK().getGrupoCodigo()
                        + " piscina:" + piscina.getPrdPiscinaPK().getPisSector() + '|' + piscina.getPrdPiscinaPK().getPisNumero();
                susSuceso = "INSERT";
                susTabla = "produccion.prd_piscina_grupo";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                prdPiscinaGrupoRel.setUsrFechaInserta(UtilsDate.timestamp());
                prdPiscinaGrupoRel.setUsrCodigo(sisInfoTO.getUsuario());
                prdPiscinaGrupoRel.setUsrEmpresa(sisInfoTO.getEmpresa());
                comprobar = piscinaGrupoRelacionDao.insertarPrdPiscinaGrupoRelacion(prdPiscinaGrupoRel, sisSuceso);
            }

        }
        return prdPiscinaGrupoRel;
    }

    @Override
    public PrdPiscinaGrupoRelacion modificarPrdPiscinaGrupoRelacion(PrdPiscinaGrupoRelacionTO prdPiscinaGrupo, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;

        PrdPiscinaGrupoPK grupoPk = new PrdPiscinaGrupoPK(prdPiscinaGrupo.getGrupoEmpresa(), prdPiscinaGrupo.getGrupoCodigo());
        PrdPiscinaGrupo grupo = piscinaGrupoDao.obtenerPorId(PrdPiscinaGrupo.class, grupoPk);

        PrdPiscinaPK piscinaPk = new PrdPiscinaPK(prdPiscinaGrupo.getPisEmpresa(), prdPiscinaGrupo.getPisSector(), prdPiscinaGrupo.getPisNumero());
        PrdPiscina piscina = piscinaDao.obtenerPorId(PrdPiscina.class, piscinaPk);
        PrdPiscinaGrupoRelacion prdPiscinaGrupoRel = null;
        if (grupo != null && piscina != null) {
            prdPiscinaGrupoRel = piscinaGrupoRelacionDao.obtenerPiscinaGrupoRelacion(grupo.getPrdPiscinaGrupoPK(), piscina.getPrdPiscinaPK());
            if (prdPiscinaGrupoRel != null) {
                // CREAR EL SUCESO
                susClave = grupo.getPrdPiscinaGrupoPK().getGrupoEmpresa() + '|'
                        + grupo.getPrdPiscinaGrupoPK().getGrupoCodigo() + '|'
                        + piscina.getPrdPiscinaPK().getPisSector() + '|'
                        + piscina.getPrdPiscinaPK().getPisNumero();
                susDetalle = "Se actualizó el grupo de piscina relación con empresa: " + grupo.getPrdPiscinaGrupoPK().getGrupoEmpresa()
                        + " grupo:" + grupo.getPrdPiscinaGrupoPK().getGrupoCodigo()
                        + " piscina:" + piscina.getPrdPiscinaPK().getPisSector() + '|' + piscina.getPrdPiscinaPK().getPisNumero();
                susSuceso = "UPDATE";
                susTabla = "produccion.prd_piscina_grupo";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                comprobar = piscinaGrupoRelacionDao.modificarPrdPiscinaGrupoRelacion(prdPiscinaGrupoRel, sisSuceso);
            }

        }
        return prdPiscinaGrupoRel;
    }

    @Override
    public PrdPiscinaGrupoRelacion eliminarPrdPiscinaGrupoRelacion(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;

        PrdPiscinaGrupoRelacion prdPiscinaGrupoRel = piscinaGrupoRelacionDao.obtenerPorId(
                PrdPiscinaGrupoRelacion.class, secuencial);

        if (prdPiscinaGrupoRel != null) {
            // CREAR EL SUCESO
            susClave = prdPiscinaGrupoRel.getGrupoSecuencial() + "";
            susDetalle = "Se eliminó el grupo de piscina relación con secuencial: " + prdPiscinaGrupoRel.getGrupoSecuencial()
                    + " grupo:" + prdPiscinaGrupoRel.getPrdPiscinaGrupo().getPrdPiscinaGrupoPK().getGrupoCodigo()
                    + " piscina:" + prdPiscinaGrupoRel.getPrdPiscina().getPrdPiscinaPK().getPisSector()
                    + '|' + prdPiscinaGrupoRel.getPrdPiscina().getPrdPiscinaPK().getPisNumero();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_piscina_grupo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = piscinaGrupoRelacionDao.eliminarPrdPiscinaGrupoRelacion(prdPiscinaGrupoRel, sisSuceso);
        }
        return prdPiscinaGrupoRel;
    }

}
