package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface XivSueldoDao extends GenericDao<RhXivSueldo, String> {

	public List<RhFunXivSueldoCalcularTO> getRhFunCalcularXivSueldo(String empresa, String sector, String desde,
			String hasta) throws Exception;

	public List<RhFunXivSueldoConsultarTO> getRhFunConsultarXivSueldo(String empresa, String sector, String desde,
			String hasta) throws Exception;

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXivs(String empresa, String fecha, String cuenta)
			throws Exception;

	public boolean insertarModificarRhXivSueldo(ConContable conContable, List<RhXivSueldo> rhXivSueldos,
			SisSuceso sisSuceso) throws Exception;

	public List<RhXivSueldo> getListRhXivSueldo(ConContablePK conContablePK);

	public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);
}
