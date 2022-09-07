package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface AnexoNumeracionDao extends GenericDao<AnxNumeracion, Integer> {

    public AnxNumeracionTO getAnexoNumeracionTO(Integer secuencia) throws Exception;

    public boolean insertarAnexoNumeracion(AnxNumeracion anxNumeracion, SisSuceso sisSuceso) throws Exception;

    public boolean modificarAnexoNumeracion(AnxNumeracion anxNumeracion, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarAnexoNumeracion(AnxNumeracion anxNumeracion, SisSuceso sisSuceso) throws Exception;

    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa) throws Exception;

    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa, String tipoDocumento) throws Exception;

    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa, String tipoDocumento, String fechaCaduda) throws Exception;

}
