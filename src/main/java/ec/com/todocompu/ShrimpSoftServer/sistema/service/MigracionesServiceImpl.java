package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvAdjuntosCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service
public class MigracionesServiceImpl implements MigracionesService {

    @Autowired
    private ComprasDao comprasDao;
    @Autowired
    private GenericoDao<InvAdjuntosCompras, Integer> invComprasDatosAdjuntosDao;
    @Autowired
    private GenericoDao<InvProductoDatosAdjuntos, Integer> invProductosDatosAdjuntosDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;

    @Override
    public List<String> migrarImagenesDeCompra(String empresa) throws Exception {
        List<Integer> imagenesDeCompras = comprasDao.listaImagenesNoMigradas(empresa);
        List<String> respuestas = new ArrayList<>();
        if (imagenesDeCompras != null && !imagenesDeCompras.isEmpty()) {
            String bucket = sistemaWebServicio.obtenerRutaImagen(empresa);
            Bucket b = AmazonS3Crud.crearBucket(bucket);
            if (b != null) {
                for (Integer imagen : imagenesDeCompras) {
                    InvAdjuntosCompras item = invComprasDatosAdjuntosDao.obtener(InvAdjuntosCompras.class, imagen);
                    InvComprasPK pk = item.getInvCompras().getInvComprasPK();
                    //archivo
                    String archivo = new String(item.getAdjArchivo(), "UTF-8");
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(item.getAdjClaveArchivo(), archivo);
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "compras/" + pk.getCompPeriodo() + "/" + pk.getCompMotivo() + "/" + pk.getCompNumero() + "/";
                    item.setAdjBucket(bucket);
                    item.setAdjClaveArchivo(carpeta + nombre);
                    item.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    invComprasDatosAdjuntosDao.actualizar(item);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, archivo, combo.getValor());
                    respuestas.add("Migracion exitosa de imagen: " + item.getAdjSecuencial() + ", para la compra: " + carpeta);
                }
            } else {
                throw new GeneralException("Error al crear contenedor de imágenes.");
            }
        } else {
            return null;
        }
        return respuestas;
    }

    @Override
    public String migrarImagenDeCompra(Integer secuencial, String bucket) throws Exception {
        String respuesta = "";
        InvAdjuntosCompras item = invComprasDatosAdjuntosDao.obtener(InvAdjuntosCompras.class, secuencial);
        InvComprasPK pk = item.getInvCompras().getInvComprasPK();
        //archivo
        String archivo = new String(item.getAdjArchivo(), "UTF-8");
        ComboGenericoTO combo = UtilsString.completarDatosAmazon(item.getAdjClaveArchivo(), archivo);
        String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
        String carpeta = "compras/" + pk.getCompPeriodo() + "/" + pk.getCompMotivo() + "/" + pk.getCompNumero() + "/";
        item.setAdjBucket(bucket);
        item.setAdjClaveArchivo(carpeta + nombre);
        item.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
        invComprasDatosAdjuntosDao.actualizar(item);
        AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, new String(item.getAdjArchivo(), StandardCharsets.UTF_8), combo.getValor());
        respuesta = "Migracion exitosa de imagen: " + item.getAdjSecuencial() + ", para la compra: " + carpeta;
        return respuesta;
    }

    @Override
    public String verificarImagenesMigradas(Integer imagen) throws Exception {
        InvAdjuntosCompras item = invComprasDatosAdjuntosDao.obtener(InvAdjuntosCompras.class, imagen);
        if (AmazonS3Crud.existeObjeto(item.getAdjBucket(), item.getAdjClaveArchivo())) {
            item.setAdjVerificado(true);
            invComprasDatosAdjuntosDao.actualizar(item);
            return "La imagen fue migrada exitosamente " + item.getAdjSecuencial() + ".";
        } else {
            item.setAdjBucket(null);
            item.setAdjClaveArchivo(null);
            item.setAdjUrlArchivo(null);
            invComprasDatosAdjuntosDao.actualizar(item);
            return "La imagen no fue migrada exitosamente " + item.getAdjSecuencial() + ".";
        }
    }

    @Override
    public String migrarImagenDeProducto(Integer secuencial, String bucket) throws Exception {
        String respuesta = "";
        InvProductoDatosAdjuntos item = invProductosDatosAdjuntosDao.obtener(InvProductoDatosAdjuntos.class, secuencial);
        InvProductoPK pk = item.getInvProducto().getInvProductoPK();
        String archivo = new String(item.getAdjArchivo(), "UTF-8");
        ComboGenericoTO combo = UtilsString.completarDatosAmazon(item.getAdjClaveArchivo(), archivo);
        String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
        String carpeta = "productos/" + pk.getProCodigoPrincipal() + "/";
        item.setAdjBucket(bucket);
        item.setAdjClaveArchivo(carpeta + nombre);
        item.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
        invProductosDatosAdjuntosDao.actualizar(item);
        AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, new String(item.getAdjArchivo(), StandardCharsets.UTF_8), combo.getValor());
        respuesta = "Migracion exitosa de imagen: " + item.getAdjSecuencial() + ", para la compra: " + carpeta;
        return respuesta;
    }

    @Override
    public String verificarImagenesProductos(Integer imagen) throws Exception {
        InvProductoDatosAdjuntos item = invProductosDatosAdjuntosDao.obtener(InvProductoDatosAdjuntos.class, imagen);
        if (AmazonS3Crud.existeObjeto(item.getAdjBucket(), item.getAdjClaveArchivo())) {
            item.setAdjVerificado(true);
            invProductosDatosAdjuntosDao.actualizar(item);
            return "La imagen fue migrada exitosamente " + item.getAdjSecuencial() + ".";
        } else {
            item.setAdjBucket(null);
            item.setAdjClaveArchivo(null);
            item.setAdjUrlArchivo(null);
            invProductosDatosAdjuntosDao.actualizar(item);
            return "La imagen no fue migrada exitosamente " + item.getAdjSecuencial() + ".";
        }
    }

}
