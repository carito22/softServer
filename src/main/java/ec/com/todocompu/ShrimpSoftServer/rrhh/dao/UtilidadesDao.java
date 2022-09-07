package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleUtilidadesLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface UtilidadesDao extends GenericDao<RhUtilidades, String> {

	public RhUtilidadesPeriodoTO buscarRhUtilidadesPeriodoTO(String descripcion) throws Exception;

	public List<RhFunUtilidadesCalcularTO> getRhFunCalcularUtilidades(String empresa, String sector, String desde,
			String hasta, Integer totalDias, Integer totalCargas, BigDecimal totalPagar) throws Exception;

	public List<RhFunUtilidadesConsultarTO> getRhFunConsultarUtilidades(String empresa, String sector, String desde,
			String hasta) throws Exception;

        public List<RhListaDetalleUtilidadesLoteTO> getRhDetalleUtilidadesLoteTO (String empresa, String periodo, String tipo,
            String numero)throws Exception;
        
	public boolean insertarModificarRhUtilidades(ConContable conContable, List<RhUtilidades> rhUtilidades,
			SisSuceso sisSuceso) throws Exception;

	public List<RhUtilidades> getListRhUtilidades(ConContablePK conContablePK);

	public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

}
