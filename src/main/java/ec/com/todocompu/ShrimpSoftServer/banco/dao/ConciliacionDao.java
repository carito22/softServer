package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionBancariaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanListaConciliacionTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCheque;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanConciliacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ConciliacionDao extends GenericDao<BanConciliacion, BanConciliacionPK> {

    public Boolean conciliacionHasta(String empresa, String hasta, String cuenta) throws Exception;

    public boolean conciliacionPendiente(String empresa, String cuentaContable) throws Exception;

    public List<BanListaConciliacionTO> getBanListaConciliacionTO(String empresa, String buscar) throws Exception;

    public String getBanConciliacionFechaHasta(String empresa, String cuenta) throws Exception;

    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaDebitoTO(String empresa, String cuenta,
            String codigo, String hasta) throws Exception;

    public List<BanListaConciliacionBancariaTO> getBanListaConciliacionBancariaCreditoTO(String empresa, String cuenta,
            String codigo, String hasta) throws Exception;

    public Boolean accionBanConciliacion(BanConciliacion banConciliacion, List<BanCheque> banCheques,
            SisSuceso sisSuceso, char accion) throws Exception;

    public List<BanConciliacionDatosAdjuntos> listarImagenesDeBanConciliacion(BanConciliacionPK pk) throws Exception;

}
