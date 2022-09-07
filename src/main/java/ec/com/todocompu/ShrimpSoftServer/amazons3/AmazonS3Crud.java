/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.amazons3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration.Transition;
import com.amazonaws.services.s3.model.BucketVersioningConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.SetBucketLifecycleConfigurationRequest;
import com.amazonaws.services.s3.model.SetBucketVersioningConfigurationRequest;
import com.amazonaws.services.s3.model.StorageClass;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author mario
 */
public class AmazonS3Crud {

    public static Regions clientRegion = Regions.US_EAST_1;

    public static Bucket obtenerBucket(String nombre) throws Exception {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(nombre)) {
                return b;
            }
        }
        return null;
    }

    public static Bucket crearBucket(String nombre) throws Exception {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
        boolean existeConfiguracion = false;
        Bucket b;
        BucketLifecycleConfiguration configurationTrancision;
        if (s3.doesBucketExistV2(nombre)) {
            b = obtenerBucket(nombre);
            
            configurationTrancision = s3.getBucketLifecycleConfiguration(nombre);
            if (configurationTrancision!=null) {
                for (int i = 0; i < configurationTrancision.getRules().size(); i++) {
                    if (configurationTrancision.getRules().get(i).getId()=="Transiciones de clase de almacenamiento") {
                        existeConfiguracion = true;
                    }
                }
            }
        } else {
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(nombre).withCannedAcl(CannedAccessControlList.Private);
            b = s3.createBucket(createBucketRequest);
            //para activar el control de versiones
            BucketVersioningConfiguration configuration = new BucketVersioningConfiguration().withStatus("Enabled");
            
            BucketLifecycleConfiguration.Rule regla = new BucketLifecycleConfiguration.Rule()
                .withId("Transiciones de clase de almacenamiento")
                .addTransition(new Transition().withDays(180).withStorageClass(StorageClass.IntelligentTiering))
                .addTransition(new Transition().withDays(365).withStorageClass(StorageClass.OneZoneInfrequentAccess))
                .withStatus(BucketLifecycleConfiguration.ENABLED);
            configurationTrancision = new BucketLifecycleConfiguration().withRules(regla);

            SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(nombre, configuration);
            s3.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
            
            SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest = new SetBucketLifecycleConfigurationRequest(nombre, configurationTrancision);
            s3.setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest);

        }
        if (!existeConfiguracion) {
            BucketLifecycleConfiguration.Rule regla = new BucketLifecycleConfiguration.Rule()
                .withId("Transiciones de clase de almacenamiento")
                .addTransition(new Transition().withDays(180).withStorageClass(StorageClass.IntelligentTiering))
                .addTransition(new Transition().withDays(365).withStorageClass(StorageClass.OneZoneInfrequentAccess))
                .withStatus(BucketLifecycleConfiguration.ENABLED);
            configurationTrancision = new BucketLifecycleConfiguration().withRules(regla);

            SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest = new SetBucketLifecycleConfigurationRequest(nombre, configurationTrancision);
            s3.setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest);
            System.out.println("Se actualizó la configuración del BUCKET " + nombre);
        }
        return b;
    }

    public static void subirArchivo(String nombreBucket, String nombreCompletoDeArchivo, String archivo, String tipo) throws Exception {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
        File temp = File.createTempFile("temp", ".txt");
        String partSeparator = ",";
        if (archivo != null) {
            if (archivo.contains(partSeparator)) {
                String encodedImg = archivo.split(partSeparator)[1];
                byte[] decodedImg = Base64.getMimeDecoder().decode(encodedImg);
                FileUtils.writeByteArrayToFile(temp, decodedImg);
            } else {
                FileUtils.writeByteArrayToFile(temp, archivo.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            throw new GeneralException("se intentó guardar un archivo vacío con el nombre: " + nombreCompletoDeArchivo);
        }
        PutObjectRequest request = new PutObjectRequest(nombreBucket, nombreCompletoDeArchivo, temp).withCannedAcl(CannedAccessControlList.PublicRead);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(tipo);
        metadata.addUserMetadata("title", "archivo");
        request.setMetadata(metadata);
        PutObjectResult r = s3.putObject(request);
        FileUtils.deleteQuietly(temp);
        if (!s3.doesObjectExist(nombreBucket, nombreCompletoDeArchivo)) {
            throw new GeneralException("El archivo no logró guardarse: " + nombreCompletoDeArchivo);
        }
    }
    
    public static void subirFile(String nombreBucket, String nombreCompletoDeArchivo, File temp, String tipo) throws Exception {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
        PutObjectRequest request = new PutObjectRequest(nombreBucket, nombreCompletoDeArchivo, temp).withCannedAcl(CannedAccessControlList.PublicRead);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(tipo);
        metadata.addUserMetadata("title", "archivo");
        request.setMetadata(metadata);
        PutObjectResult r = s3.putObject(request);
        if (!s3.doesObjectExist(nombreBucket, nombreCompletoDeArchivo)) {
            throw new GeneralException("El archivo no logró guardarse: " + nombreCompletoDeArchivo);
        }
    }

    public static void eliminarArchivo(String bucket, String claveArchivo) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
        try {
            s3.deleteObject(bucket, claveArchivo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(e.getMessage());
        }
    }

    public static boolean existeObjeto(String bucket, String claveArchivo) {
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
            return s3.doesObjectExist(bucket, claveArchivo);
        } catch (Exception e) {
            return false;
        }
    }

}
