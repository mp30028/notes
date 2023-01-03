package com.zonesoft.examples.reactive_service.tryouts.basics;

import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

class t05b_CreateFlux_SingleSubscription {
	private static final String description = "Create an asynchronous flux with flux.create and stream the ticks";

	private static final Logger LOGGER = LoggerFactory.getLogger(t05b_CreateFlux_SingleSubscription.class);
	
	private Consumer<FluxSink<TickerEvent>> tickEmitter = (FluxSink<TickerEvent> sink) -> {
		Ticker ticker = new Ticker("t05b_CreateFlux_SingleSubscription", 10, new TickerFluxListener(sink));
		ticker.run();
	};
	
		
	@Test
	void runExample() {
		LOGGER.info(description);
		Flux<TickerEvent> flux = Flux.create(tickEmitter);
		flux.log().subscribe();
	}
}


//------------------------------------------------------------------------------------
class TickerFluxListener implements ITickerListener{
//	private static final Logger LOGGER = LoggerFactory.getLogger(TickerFluxListener.class);
	private FluxSink<TickerEvent> sink;
	
	public TickerFluxListener(FluxSink<TickerEvent> sink) {
		
		this.sink = sink;
	}
	
	@Override
	public void onStart(TickerEvent event) {
//		LOGGER.debug("****TickerFluxListener.onStart. event={}",event);
		sink.next(event);
	}

	@Override
	public void onFinish(TickerEvent event) {
//		LOGGER.debug("****TickerFluxListener.onFinish. event={}",event);
		sink.next(event);
		sink.complete();
	}

	@Override
	public void onTick(TickerEvent event) {
//		LOGGER.debug("****TickerFluxListener.onTick. event={}",event);
		sink.next(event);
	}

	@Override
	public void onInterruption(TickerEvent event) {
//		LOGGER.debug("****TickerFluxListener.onInterruption. event={}",event);
		sink.next(event);
	}
	
}