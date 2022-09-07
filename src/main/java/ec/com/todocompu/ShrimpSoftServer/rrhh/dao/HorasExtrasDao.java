package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtras;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface HorasExtrasDao extends GenericDao<RhHorasExtras, Integer> {

    public List<RhListaDetalleHorasExtrasLoteTO> getRhDetalleHorasExtrasLoteTO(String empresa, String periodo, String tipo, String numero) throws Exception;

    public List<RhListaSaldoConsolidadoHorasExtrasTO> getRhSaldoConsolidadoHorasExtrasTO(String empCodigo, String fechaHasta) throws Exception;

    public List<RhListaDetalleHorasExtrasTO> getRhListaDetalleHorasExtrasFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String parametro)
            throws Exception;

    public List<RhHorasExtrasTO> getRhHorasExtras(String empresa, String periodo, String numero) throws Exception;

    public boolean insertarModificarRhHorasExtras(ConContable conContable, List<RhHorasExtras> listaRhHorasExtras, SisSuceso sisSuceso)
            throws Exception;

    public List<RhHorasExtras> getListRhHorasExtras(ConContablePK conContablePK);

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);
    
    public List<RhListaConsolidadoHorasExtrasTO> getRhListadoConsolidadoHorasExtrasTO(String empCodigo, String fechaDesde, String fechaHasta) throws Exception;

}
