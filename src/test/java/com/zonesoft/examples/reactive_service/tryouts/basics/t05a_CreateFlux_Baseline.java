package com.zonesoft.examples.reactive_service.tryouts.basics;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class t05a_CreateFlux_Baseline {
	private static final String description = "Create a synchronous Ticker and just log the ticks. No streaming and publishing involved";

	private static final Logger LOGGER = LoggerFactory.getLogger(t05a_CreateFlux_Baseline.class);

	@Test
	void runExample() {
		LOGGER.info(description);
		Ticker ticker = new Ticker("BaselineWithoutFlux", 10, new TickerListener());
		ticker.run();
	}
}






//------------------------------------------------------------------------------------
 class Ticker{
	private String tag;
	private int durationSeconds;
	private ITickerListener listener;
	
	public Ticker(String tag, int durationSeconds, ITickerListener listener) {
		this.tag = tag;
		this.durationSeconds = durationSeconds;
		this.listener = listener;
	}
	
	public void run() {
		raiseStartEvent();
		int j = 0;
		for (j = 0; j < durationSeconds; j++) {
			try {
				raiseTickEvent(j);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				raiseInterruptionEvent(e);
			}
		}
		raiseFinishEvent(j);
	}

	private void raiseInterruptionEvent(InterruptedException e) {
		listener.onInterruption(new TickerEvent("INTERRUPT",  this.tag, e.getLocalizedMessage()));
	}

	private void raiseTickEvent(int index) {
		listener.onTick(new TickerEvent("TICK",  this.tag, index + ":" + (" . .".repeat(index))));
	}

	private void raiseFinishEvent(int index) {
		listener.onFinish(new TickerEvent("FINISH",  this.tag, "Finished at " + LocalDateTime.now().toString() + " with " + index + " Ticks emitted."));
		
	}

	private void raiseStartEvent() {
		listener.onStart(new TickerEvent("START",  this.tag, "Started at " + LocalDateTime.now().toString()));
	}	
}


//------------------------------------------------------------------------------------
class TickerEvent{	
	private String eventType;
	private String tag;
	private String info;
	
	public TickerEvent(String eventType, String tag, String info) {
		this.eventType = eventType;
		this.tag = tag;
		this.info = info;
	}
	
	public String getEventType() {
		return this.eventType;
	}
	
	public String getTag() {
		return this.tag;
	}
	
	public String getInfo() {
		return this.info;
	}
	
	@Override
	public String toString() {
		return 
				"eventType: " +  eventType + ". " +
				"tag: " +  tag + ". " +
				"info: " +  info;
	}
}


//------------------------------------------------------------------------------------
class TickerListener implements ITickerListener{
	private static final Logger LISTENER_LOGGER = LoggerFactory.getLogger(TickerListener.class);
	
	@Override
	public void onStart(TickerEvent event) {
		LISTENER_LOGGER.info("EVENT: {}", event);
	}

	@Override
	public void onFinish(TickerEvent event) {
		LISTENER_LOGGER.info("EVENT: {}", event);
	}

	@Override
	public void onTick(TickerEvent event) {
		LISTENER_LOGGER.info("EVENT: {}", event);		
	}

	@Override
	public void onInterruption(TickerEvent event) {
		LISTENER_LOGGER.info("EVENT: {}", event);		
	}
	
}


//------------------------------------------------------------------------------------
interface ITickerListener {
	void onStart(TickerEvent event);
	void onFinish(TickerEvent event);
	void onTick(TickerEvent event);
	void onInterruption(TickerEvent event);
}