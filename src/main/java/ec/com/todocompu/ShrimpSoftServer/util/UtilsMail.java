package ec.com.todocompu.ShrimpSoftServer.util;

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.GetIdentityVerificationAttributesRequest;
import com.amazonaws.services.simpleemail.model.GetIdentityVerificationAttributesResult;
import com.amazonaws.services.simpleemail.model.IdentityVerificationAttributes;
import com.amazonaws.services.simpleemail.model.ListIdentitiesRequest;
import com.amazonaws.services.simpleemail.model.ListIdentitiesResult;
import com.amazonaws.services.simpleemail.model.MessageTag;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailIdentityResult;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import ec.com.todocompu.ShrimpSoftUtils.enums.TipoNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisNotificacion;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;

import java.io.File;
import java.io.IOException;

public class UtilsMail {

    public static void subirArchivoS3(String bucket, String fileObkectKeyName, String file) {
        Regions clientRegion = Regions.US_EAST_1;
        String bucketName = bucket;              //"*** Bucket name ***";
        String fileObjKeyName = fileObkectKeyName;   //"*** File object key name ***";
        String fileName = file;                //"*** Path to file to upload ***";

        try {
            //This code expects that you have AWS credentials set up per:
            // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            List<Tag> tags = new ArrayList<Tag>();
            tags.add(new Tag("Tag 1", "This is tag 1"));
            tags.add(new Tag("Tag 2", "This is tag 2"));
            request.setTagging(new ObjectTagging(tags));

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            request.setMetadata(metadata);
            s3Client.putObject(request);

            CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucket, fileObkectKeyName, "palmeras", fileObkectKeyName);
            s3Client.copyObject(copyObjRequest);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    private static InternetAddress[] destinatarios(String destinatarios) {
        String[] tmp = destinatarios.split(";");
        InternetAddress[] address = new InternetAddress[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            try {
                address[i] = new InternetAddress(tmp[i]);
                address[i].validate();
            } catch (AddressException e) {
                return null;
            }
        }
        return address;
    }

    public static boolean comprobarEmails(String destinatarios) {
        InternetAddress[] address = destinatarios(destinatarios);
        return address != null;
    }

    public static String envioErrorAmazonSES(String empresa, String destinatarios, String asunto, String detalle, String detalleTextoPlano, SisNotificacion parametro) {
        String from = empresa + "<" + parametro.getMailEmisor() + ">";
        String configSet = "error";
        String subject = asunto;
        String htmlBody = detalle;
        String textBody = detalleTextoPlano;
        for (String destinatario : destinatarios.split(";")) {
            try {
                AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
                SendEmailRequest request = new SendEmailRequest()
                        .withDestination(new Destination().withToAddresses(destinatario))
                        .withReplyToAddresses(from)
                        .withMessage(new com.amazonaws.services.simpleemail.model.Message()
                                .withBody(new Body()
                                        .withHtml(new Content()
                                                .withCharset("UTF-8").withData(htmlBody))
                                        .withText(new Content()
                                                .withCharset("UTF-8").withData(textBody)))
                                .withSubject(new Content()
                                        .withCharset("UTF-8").withData(subject)))
                        .withSource(from)
                        .withConfigurationSetName(configSet);
                client.sendEmail(request);
            } catch (Exception ex) {
                System.out.println("The email was not sent. Error message: " + ex.getMessage());
            }
        }
        return "";
    }

    public static String envioCorreoPersonalizadoAmazonSES(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO, String destinatarios,
            String asunto, String detalle, String detalleTextoPlano, List<File> listAdjunto, SisNotificacion parametro)
            throws AddressException, MessagingException, IOException {
        String resultadoEnvio = "";
        InternetAddress[] recipient = destinatarios(destinatarios);
        if (recipient == null) {
            resultadoEnvio = "CORREO(S) DEL RECEPTOR INCORRECTO";
        } else {
            String sender = sisEmailComprobanteElectronicoTO.getNombreEmisor() + "<" + sisEmailComprobanteElectronicoTO.getMailEmisor() + ">";//notificaciones@documentos-electronicos.info
            String configurationSet = sisEmailComprobanteElectronicoTO.getClaveEmisor();
            String subject = asunto;
            String bodyText = detalleTextoPlano;
            String bodyHtml = detalle;

            String DefaultCharSet = MimeUtility.getDefaultJavaCharset();
            Session session = Session.getDefaultInstance(new Properties());
            // Create a new MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Add subject, from and to lines.
            message.setSubject(subject, "UTF-8");
            message.setFrom(new InternetAddress(sender));
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, recipient);
            message.setReplyTo(destinatarios(parametro.getMailEmisor()));
            // Create a multipart/alternative child container.
            MimeMultipart msg_body = new MimeMultipart("alternative");
            // Create a wrapper for the HTML and text parts.        
            MimeBodyPart wrap = new MimeBodyPart();
            // Define the text part.
            MimeBodyPart textPart = new MimeBodyPart();
            // Encode the text content and set the character encoding. This step is
            // necessary if you're sending a message with characters outside the
            // ASCII range.
            textPart.setContent(MimeUtility.encodeText(bodyText, DefaultCharSet, "B"), "text/plain; charset=UTF-8");
            textPart.setHeader("Content-Transfer-Encoding", "base64");
            // Define the HTML part.
            MimeBodyPart htmlPart = new MimeBodyPart();
            // Encode the HTML content and set the character encoding.
            htmlPart.setContent(bodyHtml, "text/html; charset=UTF-8");
            htmlPart.setHeader("Content-Transfer-Encoding", "base64");
            // Add the text and HTML parts to the child container.
            msg_body.addBodyPart(textPart);
            msg_body.addBodyPart(htmlPart);
            // Add the child container to the wrapper object.
            wrap.setContent(msg_body);
            // Create a multipart/mixed parent container.
            MimeMultipart msg = new MimeMultipart("mixed");
            // Add the parent container to the message.
            message.setContent(msg);
            // Add the multipart/alternative part to the message.
            msg.addBodyPart(wrap);
            // Define the attachment
            for (File attachment : listAdjunto) {
                if (attachment != null) {
                    MimeBodyPart att = new MimeBodyPart();
                    DataSource fds = new FileDataSource(attachment);
                    att.setDataHandler(new DataHandler(fds));
                    att.setFileName(fds.getName());
                    msg.addBodyPart(att);
                }
            }
            Collection<MessageTag> tags = establecerTags(sisEmailComprobanteElectronicoTO);
            // Try to send the email.
            try {
                // Instantiate an Amazon SES client, which will make the service 
                // call with the supplied AWS credentials.
                AmazonSimpleEmailServiceClientBuilder amazonSimpleEmailServiceClientBuilder = AmazonSimpleEmailServiceClientBuilder.standard();
                //amazonSimpleEmailServiceClientBuilder.setEndpointConfiguration(new EndpointConfiguration("https://email.us-east-1.amazonaws.com", "us-east-1"));
                amazonSimpleEmailServiceClientBuilder.withEndpointConfiguration(new EndpointConfiguration("https://email.us-east-1.amazonaws.com", "us-east-1"));
                AmazonSimpleEmailService client = amazonSimpleEmailServiceClientBuilder.build();
                // Send the email.
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                message.writeTo(outputStream);
                RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
                SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage).
                        withConfigurationSetName(configurationSet).withTags(tags);
                client.sendRawEmail(rawEmailRequest).getMessageId();
                resultadoEnvio = "Email sent!";
            } catch (Exception ex) {
                resultadoEnvio = ex.getMessage();
                ex.printStackTrace();
            }
        }
        return resultadoEnvio;
    }

    public static Collection<MessageTag> establecerTags(SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO) {
        Collection<MessageTag> tags = new ArrayList<>();
        MessageTag tag = new MessageTag();
        if (TipoNotificacion.getTipoNotificacion(sisEmailComprobanteElectronicoTO.getTipoComprobante()) != null) {
            switch (TipoNotificacion.getTipoNotificacion(sisEmailComprobanteElectronicoTO.getTipoComprobante())) {
                case NOTIFICAR_PROVEEDOR_ORDEN_COMPRA:
                    tag.withName("ows-tipo-notificacion");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getTipoComprobante());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-empresa");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getEmpresa());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-sector");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getPeriodo());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-motivo");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getMotivo());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-numero");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getNumero());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-clave");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getPeriodo() + "_" + sisEmailComprobanteElectronicoTO.getMotivo() + "_" + sisEmailComprobanteElectronicoTO.getNumero());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-ruc");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getRucEmisor());
                    tags.add(tag);
                    break;
                case NOTIFICAR_CUENTAS_POR_COBRAR:
                    tag.withName("ows-tipo-notificacion");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getTipoComprobante());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-empresa");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getEmpresa());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-codigo-cliente");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getMotivo());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-documento");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getPeriodo());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-clave");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getPeriodo());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-ruc");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getRucEmisor());
                    tags.add(tag);
                    break;
                case NOTIFICAR_ROL_PAGOS:
                    tag.withName("ows-tipo-notificacion");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getTipoComprobante());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-empresa");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getEmpresa());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-documento");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getNumero());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-motivo");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getMotivo());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-clave");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getNumero());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-ruc");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getRucEmisor());
                    tags.add(tag);
                    break;
                case NOTIFICAR_CONTABLE_ERRORES:
                    tag.withName("ows-tipo-notificacion");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getTipoComprobante());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-empresa");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getEmpresa());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-numero");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getNumero());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-clave");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getNumero());
                    tags.add(tag);
                    tag = new MessageTag();
                    tag.withName("ows-ruc");
                    tag.withValue(sisEmailComprobanteElectronicoTO.getRucEmisor());
                    tags.add(tag);
                    break;
                default:
                    break;
            }
        } else {
            if (sisEmailComprobanteElectronicoTO.getClaveAcceso() != null) {
                MessageTag tag1 = new MessageTag();
                tag1.withName("ows-clave-acceso");
                tag1.withValue(sisEmailComprobanteElectronicoTO.getClaveAcceso());
                tags.add(tag1);
                MessageTag tag2 = new MessageTag();
                tag2.withName("ows-empresa");
                tag2.withValue(sisEmailComprobanteElectronicoTO.getEmpresa());
                tags.add(tag2);
                MessageTag tag3 = new MessageTag();
                tag3.withName("ows-periodo");
                tag3.withValue(sisEmailComprobanteElectronicoTO.getPeriodo());
                tags.add(tag3);
                MessageTag tag4 = new MessageTag();
                tag4.withName("ows-motivo");
                tag4.withValue(sisEmailComprobanteElectronicoTO.getMotivo());
                tags.add(tag4);
                MessageTag tag5 = new MessageTag();
                tag5.withName("ows-numero");
                tag5.withValue(sisEmailComprobanteElectronicoTO.getNumero());
                tags.add(tag5);
                tag = new MessageTag();
                tag.withName("ows-clave");
                tag.withValue(sisEmailComprobanteElectronicoTO.getPeriodo() + "_" + sisEmailComprobanteElectronicoTO.getMotivo() + "_" + sisEmailComprobanteElectronicoTO.getNumero());
                tags.add(tag);
                tag = new MessageTag();
                tag.withName("ows-ruc");
                tag.withValue(sisEmailComprobanteElectronicoTO.getRucEmisor());
                tags.add(tag);
            }
        }

        return tags;
    }

    public static boolean esUnaEntidadVerificada(String email) {
        if (email != null && !email.equals("")) {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
            GetIdentityVerificationAttributesRequest request = new GetIdentityVerificationAttributesRequest().withIdentities(email);
            GetIdentityVerificationAttributesResult response = client.getIdentityVerificationAttributes(request);
            Map<String, IdentityVerificationAttributes> verificationAttributes = response.getVerificationAttributes();
            if (verificationAttributes != null) {
                IdentityVerificationAttributes atributos = verificationAttributes.get(email);
                if (atributos != null) {
                    String estado = atributos.getVerificationStatus();
                    return "Success".equals(estado);
                }

            }

        }
        return false;
    }

    public static void verificarEmail(String email) {
        if (email != null && !email.equals("")) {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
            VerifyEmailIdentityRequest request = new VerifyEmailIdentityRequest().withEmailAddress(email);
            VerifyEmailIdentityResult response = client.verifyEmailIdentity(request);
        }
    }

    public static List<String> listarEntidades() {
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
        ListIdentitiesRequest request = new ListIdentitiesRequest().withIdentityType("EmailAddress").withNextToken("").withMaxItems(123);
        ListIdentitiesResult response = client.listIdentities(request);
        List<String> correos = response.getIdentities();
        return correos;
    }

    //prueba
    public static void cargarObjeto() {
        Regions clientRegion = Regions.DEFAULT_REGION;
        String bucketName = "archivosS3";
        String stringObjKeyName = "*** String object key name ***";
        String fileObjKeyName = "*** File object key name ***";
        String fileName = "*** Path to file to upload ***";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();

            // Upload a text string as a new object.
            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");

            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}
