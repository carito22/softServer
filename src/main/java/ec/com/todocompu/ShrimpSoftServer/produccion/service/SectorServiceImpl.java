package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.GrameajeDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorConHectareajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorDao sectorDao;

    @Autowired
    private GrameajeDao grameajeDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public boolean insertarPrdSector(PrdSectorTO prdSectorTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(prdSectorTO.getSecEmpresa(), prdSectorTO.getSecCodigo()));
        //consulta para validar el el codigo del establecimiento
        if (prdSector == null) {
            susClave = prdSectorTO.getSecCodigo();
            susDetalle = "Se insertó el sector " + prdSectorTO.getSecNombre();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_sector";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdSectorTO.setUsrFechaInsertaSector(UtilsValidacion.fechaSistema());
            prdSector = ConversionesProduccion.convertirPrdSectorTO_PrdSector(prdSectorTO);
            comprobar = sectorDao.insertarPrdSector(prdSector, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarPrdSector(PrdSectorTO prdSectorTO, SisInfoTO sisInfoTO) throws Exception {
        prdSectorTO.setUsrFechaInsertaSector(UtilsValidacion.fechaSistema());
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(prdSectorTO.getSecEmpresa(), prdSectorTO.getSecCodigo()));
        if (prdSector != null) {
            susClave = prdSectorTO.getSecCodigo();
            susDetalle = "Se modificó el sector con código " + prdSectorTO.getSecCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_sector";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdSectorTO.setUsrCodigo(prdSector.getUsrCodigo());
            prdSectorTO.setUsrFechaInsertaSector(UtilsValidacion.fecha(prdSector.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            PrdSector prdSectorModifica = ConversionesProduccion.convertirPrdSectorTO_PrdSector(prdSectorTO);
            comprobar = sectorDao.modificarPrdSector(prdSectorModifica, sisSuceso);
        } else {
            throw new GeneralException("No se han realizado cambios");
        }
        return comprobar;
    }

    @Override
    public boolean modificarEstadoPrdSector(PrdSectorTO prdSectorTO, boolean inactivos, SisInfoTO sisInfoTO) throws Exception {
        prdSectorTO.setUsrFechaInsertaSector(UtilsValidacion.fechaSistema());
        comprobar = false;
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(prdSectorTO.getSecEmpresa(), prdSectorTO.getSecCodigo()));
        if (prdSector != null) {
            susClave = prdSectorTO.getSecCodigo();
            susDetalle = "Se modificó el sector con código " + prdSectorTO.getSecCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_sector";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdSectorTO.setUsrCodigo(prdSector.getUsrCodigo());
            prdSectorTO.setUsrFechaInsertaSector(
                    UtilsValidacion.fecha(prdSector.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            PrdSector prdSectorModifica = ConversionesProduccion.convertirPrdSectorTO_PrdSector(prdSectorTO);
            prdSectorModifica.setSecActivo(inactivos);
            comprobar = sectorDao.modificarPrdSector(prdSectorModifica, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarPrdSector(PrdSectorTO prdSectorTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        if (sectorDao.eliminarPrdSector(prdSectorTO.getSecEmpresa(), prdSectorTO.getSecCodigo()) == true) {
            susClave = prdSectorTO.getSecCodigo();
            susDetalle = "Se eliminó el sector " + prdSectorTO.getSecNombre();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_sector";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            prdSectorTO.setUsrFechaInsertaSector(UtilsValidacion.fechaSistema());
            PrdSector prdSectorEliminar = ConversionesProduccion.convertirPrdSectorTO_PrdSector(prdSectorTO);
            comprobar = sectorDao.eliminarPrdSector(prdSectorEliminar, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public String obtenerFechaGrameajeSuperior(String empresa, String sector,
            String numPiscina) throws Exception {
        return grameajeDao.obtenerFechaGrameajeSuperior(empresa, sector, numPiscina);
    }

    @Override
    public List<PrdListaSectorTO> getListaSectorTO(String empresa, boolean activo) throws Exception {
        return sectorDao.getListaSector(empresa, activo);
    }

    @Override
    public List<PrdListaSectorTO> obtenerTodosLosSectores(String empresa) throws Exception {
        return sectorDao.obtenerTodosLosSectores(empresa);
    }

    @Override
    public List<PrdListaSectorConHectareajeTO> getListaSectorConHectareaje(String empresa, String fecha)
            throws Exception {
        return sectorDao.getListaSectorConHectareaje(empresa, fecha);
    }

    @Override
    public String insertarPrdSector(PrdSector prdSector, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        prdSector.getPrdSectorPK().setSecCodigo(prdSector.getPrdSectorPK().getSecCodigo().toUpperCase());

        if (sectorDao.obtenerPorId(PrdSector.class, prdSector.getPrdSectorPK()) != null) {
            retorno = "FEl sector que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(prdSector.getPrdSectorPK().getSecEmpresa());

            susDetalle = "Se insertó el sector " + prdSector.getSecNombre();
            susClave = prdSector.getPrdSectorPK().getSecCodigo();
            susTabla = "produccion.prd_sector";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            prdSector.setSecNombre(prdSector.getSecNombre().toUpperCase());
            prdSector.setSecActivo(true);
            prdSector.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdSector.setUsrCodigo(sisInfoTO.getUsuario());
            prdSector.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (sectorDao.insertarPrdSector(prdSector, sisSuceso)) {
                retorno = "TEl sector se guardo correctamente.";
            } else {
                retorno = "FHubo un error al guardar el sector. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarPrdSector(PrdSector prdSector, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdSector.getPrdSectorPK().getSecEmpresa());

        susDetalle = "Se modifico el sector " + prdSector.getSecNombre();
        susClave = prdSector.getPrdSectorPK().getSecCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_sector";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdSector.setSecNombre(prdSector.getSecNombre().toUpperCase());

        if (sectorDao.modificarPrdSector(prdSector, sisSuceso)) {
            retorno = "TEl sector se modifico correctamente.";
        } else {
            retorno = "FHubo un error al guardar el sector. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdSector(PrdSector prdSector, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdSector.getPrdSectorPK().getSecEmpresa());
        prdSector.setSecNombre(prdSector.getSecNombre().toUpperCase());

        if (!sectorDao.eliminarPrdSector(prdSector.getPrdSectorPK().getSecEmpresa(), prdSector.getPrdSectorPK().getSecCodigo()) == true) {
            retorno = "FNo se puede eliminar el sector porque presenta movimientos";
        } else {
            susDetalle = "Se eliminó el sector " + prdSector.getSecNombre();
            susClave = prdSector.getPrdSectorPK().getSecCodigo();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_sector";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            if (sectorDao.eliminarPrdSector(prdSector, sisSuceso)) {
                retorno = "TEl sector se eliminó correctamente.";
            } else {
                retorno = "FHubo un error al guardar el sector. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public PrdSector obtenerPorEmpresaSector(String empresa, String sector
    ) {
        return sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(empresa, sector));
    }

    @Override
    public List<PrdSector> getListaSectorPorEmpresa(String empresa, boolean activo) throws Exception {
        return sectorDao.getListaSectorPorEmpresa(empresa, activo);
    }
}
