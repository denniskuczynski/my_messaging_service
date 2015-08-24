package mms;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import mms.resources.MessageResource;

public class MMSApplication extends Application<MMSConfiguration> {
    public static void main(final String[] args) throws Exception {
        new MMSApplication().run(args);
    }

    @Override
    public String getName() {
        return "my_messaging_service";
    }

    @Override
    public void initialize(final Bootstrap<MMSConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(
        final MMSConfiguration configuration,
        final Environment environment
    ) {
        final MessageResource resource = new MessageResource();
        environment.jersey().register(resource);
        environment.servlets().addServlet("ws", mms.MMSWebSocketServlet.class).addMapping("/ws");
    }

}
