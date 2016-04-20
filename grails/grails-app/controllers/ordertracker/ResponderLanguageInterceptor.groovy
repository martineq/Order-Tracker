package ordertracker


class ResponderLanguageInterceptor {

    ResponderLanguageInterceptor() {
        matchAll()
    }

    boolean before() {
        response.characterEncoding = 'UTF-8'
        true
    }
}
