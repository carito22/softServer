package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCatastroMicroempresa;

@Transactional
public interface AnexoCatastroMicroempresaService {

    public String insertarListadoCatastroMicroempresa(List<AnxCatastroMicroempresa> listado, boolean permitirBorrar) throws Exception;

}
