package ordertracker.queries

import ordertracker.tranmission.TransmissionMedium

interface Queryingly {

    def validate(Requester requester)

    def generateQuery()

    def obtainResponse(TransmissionMedium transmissionMedium)
}
