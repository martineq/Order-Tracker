package ordertracker.util.logger

class LogWriter {

    private File logFile
    private String logFilePath
    private String logFileName

    LogWriter(Enum logFilePath, Enum logFileName) {
        this.logFile = null
        this.logFilePath = logFilePath.toString()
        this.logFileName = logFileName.toString()
    }

    LogWriter(String logFilePath, String logFileName) {
        this.logFile = null
        this.logFilePath = logFilePath
        this.logFileName = logFileName
    }

    def prepareFile() {
        new File(this.logFilePath).mkdir()
        this.logFile = new File(this.logFilePath+this.logFileName)
    }

    def append(String message) {
        this.logFile.append(message)
    }
}
