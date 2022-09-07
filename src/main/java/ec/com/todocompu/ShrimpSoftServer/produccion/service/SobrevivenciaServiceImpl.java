package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SobrevivenciaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSobrevivencia;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class SobrevivenciaServiceImpl implements SobrevivenciaService {

    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private SobrevivenciaDao sobrevivenciaDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public boolean insertarPrdSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class,
                new PrdSectorPK(prdSobrevivenciaTO.getSecEmpresa(), prdSobrevivenciaTO.getSecCodigo()));
        if (prdSector != null) {
            susClave = prdSobrevivenciaTO.getSecCodigo();
            susDetalle = "Se insertó la Sobrevivencia " + prdSobrevivenciaTO.getSobCodigo();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_Sobrevivencia";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdSobrevivenciaTO.setUsrFechaInsertaSobrevivencia(UtilsValidacion.fechaSistema());
            PrdSobrevivencia prdSobrevivencia = ConversionesProduccion
                    .convertirPrdSobrevivenciaTO_PrdSobrevivencia(prdSobrevivenciaTO);
            prdSobrevivencia.setPrdSector(prdSector);
            comprobar = sobrevivenciaDao.insertarPrdSobrevivencia(prdSobrevivencia, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public PrdSobrevivencia insertarSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO, SisInfoTO sisInfoTO)
            throws Exception {
        PrdSobrevivencia prdSobrevivencia = null;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(prdSobrevivenciaTO.getSecEmpresa(), prdSobrevivenciaTO.getSecCodigo()));
        if (prdSector != null) {
            susClave = prdSobrevivenciaTO.getSecCodigo();
            susDetalle = "Se insertó la Sobrevivencia " + prdSobrevivenciaTO.getSobCodigo();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_Sobrevivencia";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdSobrevivenciaTO.setUsrFechaInsertaSobrevivencia(UtilsValidacion.fechaSistema());
            prdSobrevivencia = ConversionesProduccion.convertirPrdSobrevivenciaTO_PrdSobrevivencia(prdSobrevivenciaTO);
            prdSobrevivencia.setPrdSector(prdSector);
            comprobar = sobrevivenciaDao.insertarPrdSobrevivencia(prdSobrevivencia, sisSuceso);
        }
        return prdSobrevivencia;
    }

    @Override
    public boolean modificarPrdSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class,
                new PrdSectorPK(prdSobrevivenciaTO.getSecEmpresa(), prdSobrevivenciaTO.getSecCodigo()));
        PrdSobrevivencia prdSobrevivencia = sobrevivenciaDao.obtenerPorId(PrdSobrevivencia.class,
                prdSobrevivenciaTO.getSobCodigo());
        if ((prdSobrevivencia != null) && (prdSector != null)) {
            susClave = prdSobrevivenciaTO.getSecCodigo();
            susDetalle = "Se modificó la Sobrevivencia " + prdSobrevivenciaTO.getSobCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_Sobrevivencia";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdSobrevivenciaTO.setUsrCodigo(prdSobrevivencia.getUsrCodigo());
            prdSobrevivenciaTO.setUsrFechaInsertaSobrevivencia(
                    UtilsValidacion.fecha(prdSobrevivencia.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            // prdSobrevivenciaTO.setUsrFechaInsertaSobrevivencia(UtilsValidacion.fechaSistema());
            prdSobrevivencia = ConversionesProduccion
                    .convertirPrdSobrevivenciaTO_PrdSobrevivencia(prdSobrevivenciaTO);
            prdSobrevivencia.setPrdSector(prdSector);
            comprobar = sobrevivenciaDao.modificarPrdSobrevivencia(prdSobrevivencia, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarPrdSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO, SisInfoTO sisInfoTO)
            throws Exception {
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class,
                new PrdSectorPK(prdSobrevivenciaTO.getSecEmpresa(), prdSobrevivenciaTO.getSecCodigo()));
        /// CREAR SUCESO
        susDetalle = "Se eliminó la Sobrevivencia " + prdSobrevivenciaTO.getSobCodigo();
        susClave = prdSobrevivenciaTO.getSobCodigo().toString();
        susSuceso = "DELETE";
        susTabla = "produccion.prd_Sobrevivencia";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdSobrevivenciaTO.setUsrFechaInsertaSobrevivencia(UtilsValidacion.fechaSistema());
        PrdSobrevivencia prdSobrevivencia = ConversionesProduccion
                .convertirPrdSobrevivenciaTO_PrdSobrevivencia(prdSobrevivenciaTO);
        prdSobrevivencia.setPrdSector(prdSector);
        comprobar = sobrevivenciaDao.eliminarPrdSobrevivencia(prdSobrevivencia, sisSuceso);
        return comprobar;
    }

    @Override
    public List<PrdListaSobrevivenciaTO> getListaSobrevivenciaTO(String empresa, String sector) throws Exception {
        return sobrevivenciaDao.getListaSobrevivencia(empresa, sector);
    }

    @Override
    public List<PrdListaSobrevivenciaTO> sobrevivenciaPorDefecto(String empresa, String sector) throws Exception {
        return sobrevivenciaDao.sobrevivenciaPorDefecto(empresa, sector);
    }

}
