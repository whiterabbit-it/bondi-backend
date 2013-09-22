package com.whiterabbit.bondi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.sockjs.SockJSServer;
import org.vertx.java.platform.Verticle;

public class SockJsBridgeVerticle extends Verticle {
	
	private static final Logger log = LoggerFactory.getLogger(SockJsBridgeVerticle.class);

    @Override
    public void start() {
    	log.info("Starting SockJsBridgeVerticle");
        HttpServer httpServer = vertx.createHttpServer();
        JsonObject config = new JsonObject().putString("prefix", "/server");

        // client send addresses
        JsonObject requestList = createAddress("bondis\\.client\\.list");

        JsonArray inboundPermitted = new JsonArray();
        inboundPermitted.add(requestList);
        
        // server publish addresses
        //JsonObject publishMessage = createAddress("bondis\\.client\\.list");

        JsonArray outboundPermitted = new JsonArray();
        //outboundPermitted.add(publishMessage);
        
        SockJSServer sockJSServer = vertx.createSockJSServer(httpServer);
        sockJSServer.bridge(config, inboundPermitted, outboundPermitted);

        httpServer.listen(8181);
        log.info("SockJsBridgeVerticle Started");
    }
    
    private JsonObject createAddress(final String address) {
        return new JsonObject().putString("address_re", address);
    }
}
