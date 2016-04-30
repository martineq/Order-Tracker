package ordertracker

class Client {

    static def orderTrackerEmailService

    static constraints = {
    }

    String name
    String address
    String city
    String state
    String qrcode
    String email

    def afterInsert() {
        this.setQrCode()
        this.save()
    }

    def beforeUpdate() {
        if ( this.qrcode.substring(this.qrcode.indexOf('-')+1, this.qrcode.length()).compareTo(this.email) != 0 )
            this.setQrCode()
    }

    def setQrCode() {
        this.qrcode = this.id + '-' + this.email

        Thread.start {
            if ( id > ClientLoader.clientsLoaded )
                orderTrackerEmailService.sendEmail(this.email.toString(), this.qrcode.toString())
        }
    }
}
