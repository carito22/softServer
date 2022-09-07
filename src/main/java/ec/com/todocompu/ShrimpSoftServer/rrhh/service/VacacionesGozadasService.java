package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesGozadasDatosAdjuntosWebTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

public interface VacacionesGozadasService {

    public List<RhVacacionesGozadas> listarRhVacacionesGozadas(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception;
    
    public List<RhVacacionesGozadas> listarRhVacacionesGozadasSinContable(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public RhVacacionesGozadas insertarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, List<RhVacacionesGozadasDatosAdjuntosWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public RhVacacionesGozadas modificarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, List<RhVacacionesGozadasDatosAdjuntosWebTO> listado, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean desmayorizarRhVacacionesGozadas(String numero, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarRhVacacionesGozadas(String numero, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarUltimaVacacionesGozadas(String empresa, String numero, String empId, SisInfoTO sisInfoTO) throws Exception;

    public String anularRestaurarRhVacacionesGozadas(RhVacacionesGozadasPK pk, boolean anularRestaurar, SisInfoTO sisInfoTO);

    /*IMAGENES*/
    public List<RhVacacionesGozadasDatosAdjuntosWebTO> convertirStringUTF8(RhVacacionesGozadasPK pk);

}
