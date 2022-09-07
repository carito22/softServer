package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciacionesMotivos;
import org.springframework.transaction.annotation.Transactional;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.List;
import java.util.Map;

@Transactional
public interface MotivoDepreciacionActivoFijoService {

    public String insertarAfDepreciacionMotivoTO(AfDepreciacionMotivoTO afDepreciacionMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public List<AfDepreciacionMotivoTO> getListaAfDepreciacionesMotivos(String empresa) throws Exception;

    public AfDepreciacionesMotivos getAfDepreciacionesMotivos(String empresa, String codigoEntidad, String tipo) throws Exception;

    public String modificarAfDepreciacionMotivoTO(AfDepreciacionMotivoTO afDepreciacionMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarAfDepreciacionMotivoTO(AfDepreciacionMotivoTO afDepreciacionMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    /*Reportes*/
    public byte[] generarReporteAfDepreciacionMotivoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfDepreciacionMotivoTO> listado, String nombreReporte) throws Exception;

    public Map<String, Object> exportarReporteAfDepreciacionMotivoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfDepreciacionMotivoTO> listado) throws Exception;

}
