package ordertracker.util.logger

class Log {
    private static InfoFormatter logFormatter = null
    private static LogWriter logWriter = null

    static def InitializeLogger(Enum logPath, Enum logFileName) {
        logFormatter = new InfoFormatter()
        logWriter = new LogWriter(logPath.toString(), logFileName.toString())
        logWriter.prepareFile()
    }

    static private def show(String formattedMessage) {
        try {
            logWriter.append(formattedMessage+'\n')
            println(formattedMessage)
        }

        catch (NullPointerException npe) {}
    }

    static def info(def message) {
        try {
            show(logFormatter.format(message))
        }

        catch (DateChangedException e) {
            show(logFormatter.format("** "+e.getMessage()+" **"))
            show(logFormatter.format(message))
        }
    }

    static def persist(String formattedMessage) {
        try {
            logWriter.append(formattedMessage+'\n')
        }

        catch (NullPointerException npe) {}
    }

    static def onlyLog(def request) {
        try {
            persist(logFormatter.format(request))
        }

        catch (DateChangedException e) {
            persist(logFormatter.format("** "+e.getMessage()+" **"))
            persist(logFormatter.format(request))
        }
    }

    static def warn(def message) {
        try {
            show(logFormatter.formatWarning(message))
        }

        catch (DateChangedException e) {
            show(logFormatter.formatWarning("** "+e.getMessage()+" **"))
            show(logFormatter.formatWarning(message))
        }
    }
}
