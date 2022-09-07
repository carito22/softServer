package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaSusTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisSucesoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoDetalle;

public interface SucesoDao extends GenericDao<SisSuceso, String> {

    public List<SisSucesoTO> getListaSisSuceso(String desde, String hasta, String usuario, String suceso,
            String cadenaGeneral, String empresa) throws Exception;

    public int retornoContadoEliminarUsuario(String codUsuario) throws Exception;

    public List<SisListaSusTablaTO> getListaSisSusTablaTOs(String empresa) throws Exception;

    public void sisRegistrarSucesosUsuario(SisSuceso sisSuceso) throws Exception;

    public List<SisSucesoDetalle> getListaSisSucesoDetalle(Integer secuencial) throws Exception;

}
