package com.whiterabbit.bondi;

import org.vertx.java.platform.Verticle;

import com.whiterabbit.bondi.server.ServicesVerticle;

public class ManagerVerticle extends Verticle {
	@Override
	public void start() {
		container.deployVerticle(ServicesVerticle.class.getName());
		container.deployVerticle(DataVerticle.class.getName());
	}
}
