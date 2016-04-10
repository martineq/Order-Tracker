package ordertracker

class BootStrap {

    def init = { servletContext ->
		new UserLoader().load()
        new ClientLoader().load()
        new ProductLoader().load()

        //new BrandLoader().load()
        //new DiscountLoader().load()
    }

    def destroy = {
    }
}
