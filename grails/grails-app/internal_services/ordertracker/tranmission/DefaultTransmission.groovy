package ordertracker.tranmission

class DefaultTransmission implements TransmissionMedium {

    private static DefaultTransmission transmission = null

    static obtainDefaultTransmission() {
        if ( DefaultTransmission.transmission == null )
            DefaultTransmission.transmission = new DefaultTransmission()

        return DefaultTransmission.transmission
    }
}
