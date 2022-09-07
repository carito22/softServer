/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.web.PermisosEmpresaMenuTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dev-out-03
 */
@Service
@Transactional
public class SistemaWebServicioImpl implements SistemaWebServicio {

    @Autowired
    public UsuarioDetalleService usuarioDetalleService;
    @Autowired
    public GrupoService grupoService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @Override
    public List<PermisosEmpresaMenuTO> getEmpresasPorUsuarioItemAngular(SisInfoTO usuario, String item) throws Exception {
        List<SisEmpresa> listaEmpresa;
        listaEmpresa = usuarioDetalleService.getEmpresasPorUsuarioItem(usuario.getUsuario(), item);
        return listaPermisos(listaEmpresa, usuario);
    }

    public List<PermisosEmpresaMenuTO> listaPermisos(List<SisEmpresa> listaEmpresa, SisInfoTO usuario) throws Exception {
        List<PermisosEmpresaMenuTO> permisos = new ArrayList<>();
        for (SisEmpresa listaEmpresa1 : listaEmpresa) {
            PermisosEmpresaMenuTO permisosEmpresaMenuTO = new PermisosEmpresaMenuTO();
            permisosEmpresaMenuTO.setEmpNombre(listaEmpresa1.getEmpNombre());
            permisosEmpresaMenuTO.setEmpRazonSocial(listaEmpresa1.getEmpRazonSocial());
            permisosEmpresaMenuTO.setEmpCiudad(listaEmpresa1.getEmpCiudad());
            permisosEmpresaMenuTO.setEmpCodigo(listaEmpresa1.getEmpCodigo());
            permisosEmpresaMenuTO.setEmpRuc(listaEmpresa1.getEmpRuc());
            permisosEmpresaMenuTO.setEmpEmail(listaEmpresa1.getEmpEmail());
            permisosEmpresaMenuTO.setEmpDireccion(listaEmpresa1.getEmpDireccion());
            permisosEmpresaMenuTO.setEmpTelefono(listaEmpresa1.getEmpTelefono());
            List<SisEmpresaParametros> parametros = listaEmpresa1.getSisEmpresaParametrosList();
            for (SisEmpresaParametros parametro : parametros) {
                parametro.setEmpCodigo(new SisEmpresa(listaEmpresa1.getEmpCodigo(), usuario.getUsuario()));
            }
            permisosEmpresaMenuTO.setParametros(parametros);
            permisosEmpresaMenuTO.setEmpPais(listaEmpresa1.getEmpPais());
            permisos.add(permisosEmpresaMenuTO);
        }
        return permisos;
    }

    @Override
    public Timestamp getFechaActual() {
        return UtilsDate.timestamp();
    }

    @Override
    public Date getFechaInicioMes() throws Exception {
        return UtilsDate.getPrimerDiaDelMes(new Date());
    }

    @Override
    public Date getFechaFinMes() throws Exception {
        return UtilsDate.getUltimoDiaDelMes(new Date());
    }

    @Override
    public String obtenerRutaImagen(String empresa) throws Exception {
        SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        if (empresaParametros != null) {
            String bucket = empresaParametros.getParRutaImagen();
            if (bucket == null || bucket.isEmpty()) {
                String codigo = empresaParametros.getEmpCodigo().getEmpCodigo().toLowerCase();
                String[] alias = empresaParametros.getEmpCodigo().getEmpNombre().split(" ");
                String nombre = "";
                if (alias != null) {
                    int contador = 0;
                    for (String alia : alias) {
                        if (alia.length() > 1) {
                            contador++;
                            if (contador <= 3) {//Solamente los 4 primeros nombres;
                                nombre = nombre + alia;
                            }
                        }
                    }
                }
                bucket = "acosux-imagenes-" + codigo + "-" + nombre.toLowerCase();
                empresaParametros.setParRutaImagen(bucket);
                empresaParametrosDao.actualizar(empresaParametros);
            }
            return bucket;
        }
        return "";
    }
}
