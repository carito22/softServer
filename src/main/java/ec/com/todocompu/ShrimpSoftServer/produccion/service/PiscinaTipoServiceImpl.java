/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaTipoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipoPK;
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
public class PiscinaTipoServiceImpl implements PiscinaTipoService {

    @Autowired
    private PiscinaTipoDao piscinaTipoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public List<PrdPiscinaTipo> listarPiscinaTipo(String empresa, boolean filtrarInactivos) throws Exception {
        return piscinaTipoDao.listarPiscinaTipo(empresa, filtrarInactivos);
    }

    @Override
    public boolean insertarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTipo, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdPiscinaTipo tipo = piscinaTipoDao.obtenerPorId(PrdPiscinaTipo.class, prdPiscinaTipo.getPrdPiscinaTipoPK());

        if (tipo == null) {
            // CREAR EL SUCESO
            susClave = prdPiscinaTipo.getPrdPiscinaTipoPK().getTipoCodigo();
            susDetalle = "Se insertó el tipo de piscina con código: " + prdPiscinaTipo.getTipoDescripcion();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_piscina_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdPiscinaTipo.setUsrFechaInserta(UtilsDate.timestamp());
            prdPiscinaTipo.setUsrCodigo(sisInfoTO.getUsuario());
            prdPiscinaTipo.setUsrEmpresa(sisInfoTO.getEmpresa());
            comprobar = piscinaTipoDao.insertarPrdPiscinaTipo(prdPiscinaTipo, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarPrdPiscinaTipo(PrdPiscinaTipo prdPiscinaTipo, SisInfoTO sisInfoTO) throws Exception {
        prdPiscinaTipo.setUsrFechaInserta(UtilsDate.timestamp());
        comprobar = false;
        PrdPiscinaTipo tipo = piscinaTipoDao.obtenerPorId(PrdPiscinaTipo.class, prdPiscinaTipo.getPrdPiscinaTipoPK());

        if (tipo != null) {
            susClave = prdPiscinaTipo.getPrdPiscinaTipoPK().getTipoCodigo();
            susDetalle = "Se modificó el tipo de piscina con código:" + prdPiscinaTipo.getPrdPiscinaTipoPK().getTipoCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_piscina_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = piscinaTipoDao.modificarPrdPiscinaTipo(prdPiscinaTipo, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarEstadoPiscinaTipo(PrdPiscinaTipoPK pk, boolean activar, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdPiscinaTipo tipo = piscinaTipoDao.obtenerPorId(PrdPiscinaTipo.class, pk);

        if (tipo != null) {
            tipo.setTipoActivo(activar);
            susClave = pk.getTipoCodigo();
            susDetalle = "Se ha " + (!activar ? "inactivado" : "activado") + " el tipo de piscina con código: " + pk.getTipoCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_piscina_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = piscinaTipoDao.modificarPrdPiscinaTipo(tipo, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarPrdPiscinaTipo(PrdPiscinaTipoPK pk, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdPiscinaTipo tipo = piscinaTipoDao.obtenerPorId(PrdPiscinaTipo.class, pk);

        if (tipo != null) {
            susClave = pk.getTipoCodigo();
            susDetalle = "Se eliminó el tipo de piscina con codigo " + pk.getTipoCodigo();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_piscina_tipo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = piscinaTipoDao.eliminarPrdPiscinaTipo(tipo, sisSuceso);
        }
        return comprobar;
    }

}
