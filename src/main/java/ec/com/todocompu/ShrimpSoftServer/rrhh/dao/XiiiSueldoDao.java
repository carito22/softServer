package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface XiiiSueldoDao extends GenericDao<RhXiiiSueldo, String> {

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXiiis(String empresa, String fecha, String cuenta)
			throws Exception;

	public List<RhFunXiiiSueldoCalcularTO> getRhFunCalcularXiiiSueldo(String empresa, String sector, String desde,
			String hasta) throws Exception;

	public List<RhFunXiiiSueldoConsultarTO> getRhFunConsultarXiiiSueldo(String empresa, String sector, String desde,
			String hasta) throws Exception;

	public boolean insertarModificarRhXiiiSueldo(ConContable conContable, List<RhXiiiSueldo> rhXiiiSueldos,
			SisSuceso sisSuceso) throws Exception;

	public List<RhXiiiSueldo> getListRhXiiiSueldo(ConContablePK conContablePK);

	public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

}
