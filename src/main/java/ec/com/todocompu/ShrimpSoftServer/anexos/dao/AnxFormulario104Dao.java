/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormulario104;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public interface AnxFormulario104Dao extends GenericDao<AnxFormulario104, Integer> {

    public AnxFormulario104 getAnxFormulario104(Integer secuencia) throws Exception;

    public List<AnxFormulario104> getAnxListaAnxFormulario104(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public boolean insertarAnxFormulario104(AnxFormulario104 anxFormulario104, SisSuceso sisSuceso) throws Exception;

    public boolean modificarAnxFormulario104(AnxFormulario104 anxFormulario104, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarAnxFormulario104(AnxFormulario104 anxFormulario104, SisSuceso sisSuceso) throws Exception;
}
