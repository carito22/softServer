package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.EmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.VacacionesGozadasDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesGozadasDatosAdjuntosWebTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.util.ArrayList;

@Service
public class VacacionesGozadasServiceImpl implements VacacionesGozadasService {

    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private VacacionesGozadasDao vacacionesGozadasDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericoDao<RhVacacionesGozadasDatosAdjuntos, Integer> rhVacacionesGozadasDatosAdjuntosDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;

    @Override
    public RhVacacionesGozadas insertarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, List<RhVacacionesGozadasDatosAdjuntosWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception {
        // fecha ultimo sueldo
        String fechaUltimaVacaciones = vacacionesGozadasDao.buscarFechaRhVacaciones(rhVacaciones.getRhVacacionesGozadasPK().getVacEmpresa(),
                rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId());
        if (fechaUltimaVacaciones != null && !(rhVacaciones.getVacHasta().getTime() > UtilsValidacion.fecha(fechaUltimaVacaciones, "yyyy-MM-dd").getTime())) {
            throw new GeneralException("Fecha del Vacaciones tiene que ser mayor a la fecha del ultima Vacaciones.");
        }
        ////// CREANDO NUMERACION
        rhVacaciones.getRhVacacionesGozadasPK().setVacNumero(vacacionesGozadasDao.getInvProximaNumeracion(sisInfoTO.getEmpresa()));
        ///// CREANDO UN SUCESO
        susClave = rhVacaciones.getRhVacacionesGozadasPK().getVacEmpresa() + " " + rhVacaciones.getRhVacacionesGozadasPK().getVacNumero();
        susSuceso = "INSERT";
        susTabla = "recursoshumanos.rh_vacaciones_gozadas";
        susDetalle = "Vacaciones a "
                + empleadoDao.getRhEmpleadoApellidosNombres(
                        rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                        rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId());
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        vacacionesGozadasDao.insertarRhVacacionesGozadas(rhVacaciones, sisSuceso);
        insertarImagenesVacacionesGozadas(listadoImagenes, rhVacaciones.getRhVacacionesGozadasPK());
        return rhVacaciones;
    }

    @Override
    public RhVacacionesGozadas modificarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, List<RhVacacionesGozadasDatosAdjuntosWebTO> listado, SisInfoTO sisInfoTO) throws Exception {
        // fecha ultimo sueldo
        String fechaUltimaVacaciones = vacacionesGozadasDao.buscarFechaRhVacaciones(rhVacaciones.getRhVacacionesGozadasPK().getVacEmpresa(),
                rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId());
        if (fechaUltimaVacaciones != null && !(rhVacaciones.getVacHasta().getTime() > UtilsValidacion.fecha(fechaUltimaVacaciones, "yyyy-MM-dd").getTime())) {
            throw new GeneralException("Fecha del Vacaciones tiene que ser mayor a la fecha del ultima Vacaciones.");
        }
        ///// CREANDO UN SUCESO
        susClave = rhVacaciones.getRhVacacionesGozadasPK().getVacEmpresa() + " " + rhVacaciones.getRhVacacionesGozadasPK().getVacNumero();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_vacaciones_gozadas";
        susDetalle = "Vacaciones a "
                + empleadoDao.getRhEmpleadoApellidosNombres(
                        rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                        rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId());
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        vacacionesGozadasDao.modificarRhVacacionesGozadas(rhVacaciones, sisSuceso);
        actualizarImagenesVacacionesGozadas(listado, rhVacaciones.getRhVacacionesGozadasPK());
        return rhVacaciones;
    }

    @Override
    public boolean desmayorizarRhVacacionesGozadas(String numero, SisInfoTO sisInfoTO) throws Exception {
        RhVacacionesGozadas rhVacaciones = vacacionesGozadasDao.obtenerPorId(RhVacacionesGozadas.class, new RhVacacionesGozadasPK(sisInfoTO.getEmpresa(), numero));
        if (rhVacaciones == null) {
            return false;
        }
        rhVacaciones.setVacPendiente(true);
        ///// CREANDO UN SUCESO
        susClave = rhVacaciones.getRhVacacionesGozadasPK().getVacEmpresa() + " " + rhVacaciones.getRhVacacionesGozadasPK().getVacNumero();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_vacaciones_gozadas";
        susDetalle = "Desmayorizar vacaciones N°." + numero + " a "
                + empleadoDao.getRhEmpleadoApellidosNombres(
                        rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                        rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId());
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        vacacionesGozadasDao.modificarRhVacacionesGozadas(rhVacaciones, sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarRhVacacionesGozadas(String numero, SisInfoTO sisInfoTO) throws Exception {
        RhVacacionesGozadas rhVacaciones = vacacionesGozadasDao.obtenerPorId(RhVacacionesGozadas.class, new RhVacacionesGozadasPK(sisInfoTO.getEmpresa(), numero));
        if (rhVacaciones == null) {
            return false;
        }
        ///// CREANDO UN SUCESO
        susClave = rhVacaciones.getRhVacacionesGozadasPK().getVacEmpresa() + " " + rhVacaciones.getRhVacacionesGozadasPK().getVacNumero();
        susSuceso = "DELETE";
        susTabla = "recursoshumanos.rh_vacaciones_gozadas";
        susDetalle = "Eliminar vacaciones N°." + numero + " a "
                + empleadoDao.getRhEmpleadoApellidosNombres(
                        rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa(),
                        rhVacaciones.getRhEmpleado().getRhEmpleadoPK().getEmpId());
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        vacacionesGozadasDao.eliminarRhVacacionesGozadas(rhVacaciones, sisSuceso);
        return true;
    }

    @Override
    public List<RhVacacionesGozadas> listarRhVacacionesGozadas(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception {
        return vacacionesGozadasDao.listarRhVacacionesGozadas(empCodigo, empId, sector, fechaDesde, fechaHasta);
    }
    
    @Override
    public List<RhVacacionesGozadas> listarRhVacacionesGozadasSinContable(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception {
        return vacacionesGozadasDao.listarRhVacacionesGozadasSinContable(empCodigo, empId, sector, fechaDesde, fechaHasta);
    }

    @Override
    public String anularRestaurarRhVacacionesGozadas(RhVacacionesGozadasPK pk, boolean anularRestaurar, SisInfoTO sisInfoTO) {
        susClave = pk.getVacEmpresa() + " | " + pk.getVacNumero();
        susDetalle = "se " + (anularRestaurar ? "anuló" : "restauró") + "correctamente";
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_vacaciones_gozadas";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        vacacionesGozadasDao.anularRestaurarRhVacacionesGozadas(pk, anularRestaurar, sisSuceso);

        return "TLa solicitud de vacaciones se " + (anularRestaurar ? "anuló" : "restauró") + " correctamente " + pk.getVacNumero();
    }

    @Override
    public String eliminarUltimaVacacionesGozadas(String empresa, String numero, String empId, SisInfoTO sisInfoTO) throws Exception {
        susClave = empresa + " | " + numero;
        susSuceso = "DELETE";
        susTabla = "recursoshumanos.rh_vacaciones_gozadas";
        susDetalle = "Eliminar vacaciones N°." + numero + " a "
                + empleadoDao.getRhEmpleadoApellidosNombres(
                        empresa,
                        empId);
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return vacacionesGozadasDao.eliminarUltimaVacacionesGozadas(empresa, numero, sisSuceso);
    }

    @Override
    public List<RhVacacionesGozadasDatosAdjuntosWebTO> convertirStringUTF8(RhVacacionesGozadasPK pk) {
        List<RhVacacionesGozadasDatosAdjuntos> listadoAdjuntos;
        List<RhVacacionesGozadasDatosAdjuntosWebTO> listaRespuesta = new ArrayList<>();
        listadoAdjuntos = vacacionesGozadasDao.getAdjuntosContable(pk);
        for (RhVacacionesGozadasDatosAdjuntos invAdjunto : listadoAdjuntos) {
            RhVacacionesGozadasDatosAdjuntosWebTO datosAdjuntoeWebTO = new RhVacacionesGozadasDatosAdjuntosWebTO();
            datosAdjuntoeWebTO.setAdjTipo(invAdjunto.getAdjTipo());
            datosAdjuntoeWebTO.setAdjSecuencial(invAdjunto.getAdjSecuencial());
            datosAdjuntoeWebTO.setRhVacacionesGozadas(invAdjunto.getRhVacacionesGozadas());
            datosAdjuntoeWebTO.setAdjArchivo(invAdjunto.getAdjArchivo());
            datosAdjuntoeWebTO.setAdjUrlArchivo(invAdjunto.getAdjUrlArchivo());
            datosAdjuntoeWebTO.setAdjClaveArchivo(invAdjunto.getAdjClaveArchivo());
            datosAdjuntoeWebTO.setAdjBucket(invAdjunto.getAdjBucket());
            datosAdjuntoeWebTO.setAdjVerificado(invAdjunto.isAdjVerificado());
            datosAdjuntoeWebTO.setAdjExtension(invAdjunto.getAdjExtension());
//                datosAdjuntoeWebTO.setImagenString(new String(invAdjunto.getAdjArchivo(), "UTF-8"));
            listaRespuesta.add(datosAdjuntoeWebTO);
        }
        return listaRespuesta;
    }

    public boolean insertarImagenesVacacionesGozadas(List<RhVacacionesGozadasDatosAdjuntosWebTO> listado, RhVacacionesGozadasPK pk) throws Exception {
        boolean respuesta = false;
        if (listado == null || listado.isEmpty()) {
            return true;
        }
        String bucket = sistemaWebServicio.obtenerRutaImagen(pk.getVacEmpresa());
        Bucket b = AmazonS3Crud.crearBucket(bucket);
        if (b != null) {
            for (RhVacacionesGozadasDatosAdjuntosWebTO datoAdjuntoWebTO : listado) {
                if (datoAdjuntoWebTO.getAdjSecuencial() == null) {
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(datoAdjuntoWebTO.getAdjClaveArchivo(), datoAdjuntoWebTO.getImagenString());
                    RhVacacionesGozadasDatosAdjuntos datoAdjunto = new RhVacacionesGozadasDatosAdjuntos();
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "vacaciones-gozadas/" + pk.getVacNumero() + "/";
                    datoAdjunto.setAdjTipo(datoAdjuntoWebTO.getAdjTipo());
                    datoAdjunto.setRhVacacionesGozadas(new RhVacacionesGozadas(pk));
                    datoAdjunto.setAdjBucket(bucket);
                    datoAdjunto.setAdjExtension(datoAdjuntoWebTO.getAdjExtension());
                    datoAdjunto.setAdjClaveArchivo(carpeta + nombre);
                    datoAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    respuesta = vacacionesGozadasDao.insertarImagen(datoAdjunto);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, datoAdjuntoWebTO.getImagenString(), combo.getValor());
                } else {
                    respuesta = true;
                }
            }
        } else {
            throw new GeneralException("Error al crear contenedor de imágenes.");
        }
        return respuesta;
    }

    public void actualizarImagenesVacacionesGozadas(List<RhVacacionesGozadasDatosAdjuntosWebTO> listado, RhVacacionesGozadasPK pk) throws Exception {
        List<RhVacacionesGozadasDatosAdjuntos> listAdjuntosEnLaBase = vacacionesGozadasDao.getAdjuntosContable(pk);
        if (listado != null && !listado.isEmpty()) {
            if (listAdjuntosEnLaBase.size() > 0) {
                listado.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                    listAdjuntosEnLaBase.removeIf(n -> (n.getAdjSecuencial().equals(item.getAdjSecuencial())));
                });
                if (listAdjuntosEnLaBase.size() > 0) {
                    listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                        rhVacacionesGozadasDatosAdjuntosDao.eliminar(itemEliminar);
                        AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                    });
                }
            }
            insertarImagenesVacacionesGozadas(listado, pk);
        }
    }

}
