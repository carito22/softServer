package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBono;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface BonoDao extends GenericDao<RhBono, Integer> {

    public List<RhBonoTO> getRhBono(String empresa, String periodo, String numero) throws Exception;

    public List<RhListaDetalleBonosLoteTO> getRhDetalleBonosLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public List<RhListaDetalleBonosTO> getRhDetalleBonosTO(String empCodigo, String fechaDesde, String fechaHasta,
            String empCategoria, String empId, String estadoDeducible) throws Exception;

    public List<RhListaDetalleBonosTO> getRhListaDetalleBonosFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String estadoDeducible, String parametro)
            throws Exception;

    public List<RhListaConsolidadoBonosTO> getRhConsolidadoBonosTO(String empCodigo, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<RhListaSaldoConsolidadoBonosTO> getRhSaldoConsolidadoBonosTO(String empCodigo, String fechaHasta)
            throws Exception;

    public boolean insertarModificarRhBono(ConContable conContable, List<RhBono> listaRhBono, SisSuceso sisSuceso)
            throws Exception;

    public List<RhBono> getListRhBono(ConContablePK conContablePK);

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    public List<RhListaConsolidadoBonosHorasExtrasTO> listarConsolidadoBonosHorasExtras(String empCodigo, String fechaDesde, String fechaHasta) throws Exception;

}
