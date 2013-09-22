package com.whiterabbit.bondi.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import com.whiterabbit.bondi.Messages;

public class ServicesVerticle extends Verticle {
	
	private static final Logger log = LoggerFactory.getLogger(ServicesVerticle.class);

	private HttpServer server;

	@Override
	public void start() {
		
        vertx.eventBus().registerHandler("bondi.client.list",new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> event) {
                final String busLine = event.body().getString("busLine");
                //TODO Buscar en el hashmap los bondis cercanos para la linea "busLine"
                
                event.reply(new JsonObject()
                    .putString("busList", "1,2,3")
                );
            }
        });
		
		log.info("Starting TrackingVerticle");

		server = vertx.createHttpServer();
		RouteMatcher routeMatcher = new RouteMatcher();

		routeMatcher.put("/tracking", new TrackingServiceHandler());
		routeMatcher.post("/getbuses", new BusesServiceHandler());

		server.requestHandler(routeMatcher).listen(8080);

		log.debug("TrackingVerticle started");
	}

	class TrackingServiceHandler implements Handler<HttpServerRequest> {
		@Override
		public void handle(HttpServerRequest req) {
			req.bodyHandler(new Handler<Buffer>() {
				@Override
				public void handle(Buffer body) {
					JsonObject data = new JsonObject(body.toString());
					System.out.println("JSON: " + data.encode());
					vertx.eventBus().publish(Messages.PUT_DATA, data);
				}
			});
			req.response().end();
		}
	}

	class BusesServiceHandler implements Handler<HttpServerRequest> {
		@Override
		public void handle(final HttpServerRequest req) {
			req.bodyHandler(new Handler<Buffer>() {
				@Override
				public void handle(Buffer body) {
					JsonObject data = new JsonObject(body.toString());
					vertx.eventBus().send(Messages.GET_DATA, data,
							new Handler<Message<JsonObject>>() {
								@Override
								public void handle(Message<JsonObject> reply) {
									req.response().setChunked(true)
											.end(reply.body().encode());
								}
							});
				}
			});
		}
	}

}
