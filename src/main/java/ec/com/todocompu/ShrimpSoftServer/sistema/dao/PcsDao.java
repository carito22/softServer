package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPcs;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

public interface PcsDao extends GenericDao<SisPcs, String> {

    public boolean comprobarSisPcs(String mac) throws Exception;
    
    public List<SisPcs> listarSisPcs(String busqueda, boolean mostrarActivos) throws Exception;

    public SisPcs insertarSisPc(SisPcs sisPc, SisSuceso sisSuceso) throws Exception;

    public SisPcs modificarSisPc(SisPcs sisPc, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarSisPc(SisPcs sisPc, SisSuceso sisSuceso) throws Exception;

}
