package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnexoCatastroMicroempresaDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCatastroMicroempresa;

@Service
public class AnexoCatastroMicroempresaServiceImpl implements AnexoCatastroMicroempresaService {

    @Autowired
    private AnexoCatastroMicroempresaDao anexoCatastroMicroempresaDao;

    @Override
    public String insertarListadoCatastroMicroempresa(List<AnxCatastroMicroempresa> listado, boolean permitirBorrar) throws Exception {
        String retorno = "";
        if (anexoCatastroMicroempresaDao.insertarListadoCatastroMicroempresa(listado, permitirBorrar)) {
            retorno = "Listado Ingresado Correctamente";
        } else {
            retorno = "Error al guardar los registros, Intente de nuevo";
        }
        return retorno;
    }

}
