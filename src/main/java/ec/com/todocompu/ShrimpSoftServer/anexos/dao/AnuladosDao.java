package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaComprobanteAnuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxAnulados;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface AnuladosDao extends GenericDao<AnxAnulados, Integer> {

    public AnxAnuladosTO getAnxAnuladosTO(Integer secuencia) throws Exception;

    public boolean insertarAnexoAnulados(AnxAnulados anxAnulados, SisSuceso sisSuceso) throws Exception;

    public boolean modificarAnexoAnulados(AnxAnulados anxAnulados, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarAnexoAnulados(AnxAnulados anxAnulados, SisSuceso sisSuceso) throws Exception;

    public List<AnxAnuladoTablaTO> getListaAnxAnuladoTablaTO(String empresa) throws Exception;

    public List<AnxListaComprobanteAnuladoTO> getAnxListaComprobanteAnuladoTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<AnxAnuladosTO> obtenerAnexoAnulado(AnxAnuladosTO anxAnuladosTO, String empresa) throws Exception;
}
