package ordertracker

class BootStrap {

    def init = { servletContext ->
		new UserLoader().load()
        new ClientLoader().load()
            new ProductLoader().load()

            new BrandLoader().load()
            new DiscountLoader().load()
            new AgendaLoader().load()
            new SellerLoader().load()
            new ClientOrderLoader().load()
            new OrderDetailLoader().load()
    }

    def destroy = {
    }
}
