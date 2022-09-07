package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.UsuarioDetalleDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioEmailTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;

@Service
public class UsuarioDetalleServiceImpl implements UsuarioDetalleService {

    @Autowired
    private UsuarioDetalleDao usuarioDetalleDao;

    @Override
    public List<SisListaEmpresaTO> getListaLoginEmpresaTO(String usrCodigo) throws Exception {
        return usuarioDetalleDao.getListaLoginEmpresaTO(usrCodigo);
    }

    @Override
    public List<SisEmpresa> getEmpresasPorUsuarioItem(String usuario, String item, String empresa) throws Exception {
        return usuarioDetalleDao.getEmpresasPorUsuarioItem(usuario, item, empresa);
    }

    @Override
    public List<SisEmpresa> getEmpresasPorUsuarioItem(String usuario, String item) throws Exception {
        return usuarioDetalleDao.getEmpresasPorUsuarioItem(usuario, item);
    }

    @Override
    public String getUsuarioNombreApellido(String usrCodigo) throws Exception {
        return usuarioDetalleDao.getUsuarioNombreApellido(usrCodigo);
    }

    @Override
    public List<SisListaUsuarioTO> getListaSisUsuarios(String empresa) throws Exception {
        return usuarioDetalleDao.getListaSisUsuario(empresa);
    }

    @Override
    public List<SisListaUsuarioTO> getListaSisUsuariosNoTieneConfCompras(String empresa) throws Exception {
        return usuarioDetalleDao.getListaSisUsuariosNoTieneConfCompras(empresa);
    }

    @Override
    public List<SisUsuarioEmailTO> obtenerCorreosADM(String empresa) throws Exception {
        return usuarioDetalleDao.obtenerCorreosADM(empresa);
    }

    @Override
    public SisUsuarioDetalle obtenerDetalleUsuario(String empresa, String usuario) throws Exception {
        return usuarioDetalleDao.obtenerDetalleUsuario(empresa, usuario);
    }

}
