package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPcs;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface PcsService {

    @Transactional
    public boolean comprobarSisPcs(String mac) throws Exception;
    
    public List<SisPcs> listarSisPcs(String busqueda, boolean mostrarActivos) throws Exception;
    
    @Transactional
    public SisPcs insertarSisPc(SisPcs sisPcs, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public SisPcs modificarSisPc(SisPcs sisPcs, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarSisPc(String sisPcs, SisInfoTO sisInfoTO) throws Exception;

}
