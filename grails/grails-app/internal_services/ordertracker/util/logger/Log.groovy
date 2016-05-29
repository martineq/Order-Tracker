package ordertracker.util.logger

import ordertracker.constants.ServerDetails

class Log {
    private static InfoFormatter logFormatter = null
    private static LogWriter logWriter = null
    private static final String INFO = ServerDetails.SERVER_LOG_INFO.toString()
    private static final String WARN = ServerDetails.SERVER_LOG_WARN.toString()

    static def InitializeLogger(Enum logPath, Enum logFileName) {
        logFormatter = new InfoFormatter()
        logWriter = new LogWriter(logPath.toString(), logFileName.toString())
        logWriter.prepareFile()
    }

    static def info(def message) {
        try {
            show(logFormatter.format(message, INFO))
        }

        catch (DateChangedException e) {
            show(logFormatter.format("** "+e.getMessage()+" **", INFO))
            show(logFormatter.format(message, INFO))
        }
    }

    static def warn(def message) {
        try {
            show(logFormatter.format(message, WARN))
        }

        catch (DateChangedException e) {
            show(logFormatter.format("** "+e.getMessage()+" **",WARN))
            show(logFormatter.format(message, WARN))
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

    static def onlyLog(def request) {
        try {
            persist(logFormatter.format(request, INFO))
        }

        catch (DateChangedException e) {
            persist(logFormatter.format("** "+e.getMessage()+" **", INFO))
            persist(logFormatter.format(request, INFO))
        }
    }

    static def onlyLogWarn(def request) {
        try {
            persist(logFormatter.format(request, WARN))
        }

        catch (DateChangedException e) {
            persist(logFormatter.format("** "+e.getMessage()+" **", WARN))
            persist(logFormatter.format(request, INFO))
        }
    }


}
