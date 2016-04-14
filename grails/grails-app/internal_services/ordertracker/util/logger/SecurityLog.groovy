package ordertracker.util.logger

import ordertracker.constants.ServerDetails

class SecurityLog {
    private static InfoFormatter logFormatter = null
    private static LogWriter logWriter = null
    private static final String SECW = ServerDetails.SERVER_LOG_SECW.toString()

    static def InitializeLogger(Enum logPath, Enum logFileName) {
        logFormatter = new InfoFormatter()
        logWriter = new LogWriter(logPath.toString(), logFileName.toString())
        logWriter.prepareFile()
    }

    static def secw(def message) {
        try {
            show(logFormatter.format(message, SECW))
        }

        catch (DateChangedException e) {
            show(logFormatter.format("** "+e.getMessage()+" **", SECW))
            show(logFormatter.format(message, SECW))
        }
    }

    static private def show(String formattedMessage) {
        try {
            logWriter.append(formattedMessage+'\n')
            println(formattedMessage)
        }

        catch (NullPointerException npe) {}
    }

    static def persist(String formattedMessage) {
        try {
            logWriter.append(formattedMessage+'\n')
        }

        catch (NullPointerException npe) {}
    }
}
