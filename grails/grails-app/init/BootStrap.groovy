package ordertracker

class BootStrap {

    def init = { servletContext ->
		new UserLoader().load()
    }

    def destroy = {
    }
}
