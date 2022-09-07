package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCatastroMicroempresa;
import java.util.List;

public interface AnexoCatastroMicroempresaDao extends GenericDao<AnxCatastroMicroempresa, Integer> {

	public boolean insertarListadoCatastroMicroempresa(List<AnxCatastroMicroempresa> listadoCatastroMicroempresa, boolean permitirBorrar) throws Exception;

}
