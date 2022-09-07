package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VacacionesGozadasDao extends GenericDao<RhVacacionesGozadas, RhVacacionesGozadasPK> {

    List<RhVacacionesGozadas> listarRhVacacionesGozadas(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception;
    
    List<RhVacacionesGozadas> listarRhVacacionesGozadasSinContable(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception;

    String buscarFechaRhVacaciones(String empCodigo, String empId) throws Exception;

    public void insertarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, SisSuceso sisSuceso) throws Exception;

    public void modificarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, SisSuceso sisSuceso) throws Exception;

    public void eliminarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, SisSuceso sisSuceso) throws Exception;

    public String getInvProximaNumeracion(String empresa) throws Exception;

    public String eliminarUltimaVacacionesGozadas(String empresa, String numero, SisSuceso sisSuceso) throws Exception;

    public void anularRestaurarRhVacacionesGozadas(RhVacacionesGozadasPK pk, boolean anularRestaurar, SisSuceso sisSuceso);

    /*IMAGENES*/
    public List<RhVacacionesGozadasDatosAdjuntos> getAdjuntosContable(RhVacacionesGozadasPK pk);

    public boolean insertarImagen(RhVacacionesGozadasDatosAdjuntos rhVacacionesGozadasDatosAdjuntos);

    public boolean eliminarImagen(RhVacacionesGozadasDatosAdjuntos rhVacacionesGozadasDatosAdjuntos);
}
