package ordertracker

class BootStrap {

    def init = { servletContext ->
		new UserLoader().load()
        new ClientLoader().load()
        new ProductLoader().load()
    }

    def destroy = {
    }
}
