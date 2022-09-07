package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.RhPreavisoAnticiposPrestamosSueldoMachalaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoGuayaquilTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface AnticipoDao extends GenericDao<RhAnticipo, String> {

    public List<RhAnticipoTO> getRhAnticipo(String empresa, String periodo, String numero) throws Exception;

    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String formaPago) throws Exception;

    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String formaPago, String parametro) throws Exception;

    public List<RhListaDetalleAnticiposLoteTO> getRhDetalleAnticiposLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoAnticiposBolivariano(String empresa, String fecha,
            String cuenta, String tipoPreAviso, String servicio, String sector, boolean sinCuenta) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> getRhFunPreavisoAnticiposPichincha(String empresa,
            String fecha, String cuenta, String tipo, String banco, String sector) throws Exception;

    //SueldosMachala
    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> getRhFunPreavisoAnticiposMachala(String empresa,
            String fecha, String cuenta, String tipo, String banco, String sector) throws Exception;

    public boolean insertarModificarRhAnticipo(ConContable conContable, List<RhAnticipo> rhAnticipos,
            SisSuceso sisSuceso) throws Exception;

    public List<RhAnticipo> getListRhAnticipo(ConContablePK conContablePK);

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    public List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> getRhFunPreavisoAnticiposGuayaquil(String empresa, String fecha, String cuenta, String tipo, String banco, String sector) throws Exception;
}
