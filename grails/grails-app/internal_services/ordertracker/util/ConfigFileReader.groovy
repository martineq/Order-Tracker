package ordertracker.util

class ConfigFileReader {

    private String fileLocation

    ConfigFileReader(String fileLocation) {
        this.fileLocation = fileLocation
    }

    def readNumbers(String defaultValue) {
        try {
            File file = new File(this.fileLocation)
            return this.restrict(file.text)
        }

        catch (FileNotFoundException f) {
            return defaultValue
        }
    }

    private def restrict(String values) {
        return values.substring(0, values.indexOf('\n'))
    }
}
