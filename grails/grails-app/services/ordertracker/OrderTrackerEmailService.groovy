package ordertracker

import grails.plugins.qrcode.QrCodeService
import ordertracker.util.logger.Log

/**
 * Created by martin on 30/04/16.
 */
class OrderTrackerEmailService {

    def mailService
    private static size

    public OrderTrackerEmailService(){
        this.size = 600
    }

    public void sendEmail(String client_email, String qrCode) {
        mailService.sendMail {
            multipart true
            to client_email
            subject "Nueva cuenta generada"
            html "<b>Bienvenido</b><br><br>Gracias por confiar en nosotros.<br><br>Tenemos el agrado de darle la bienvenida y enviarle su código QR que le permitirá autorizar los pedidos."
            attach 'codigo_qr.png', 'image/png', qrStream(qrCode).toByteArray()
        }
        Log.info('email enviado a '+ client_email)
    }

    private def qrStream(String qrCode) {
        // Stream del archivo png
        OutputStream output_stream_png = new ByteArrayOutputStream()

        // Pasar a Código QR
        QrCodeService qrcs = new QrCodeService()

        qrcs.renderPng(qrCode,this.size,output_stream_png)

        return output_stream_png
    }
}
