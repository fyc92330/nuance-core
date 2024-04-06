package org.chun.internal.core;

import org.chun.enums.MessageExchangeType;

import java.util.List;

public interface MessageExchange<T extends Message, E extends MessageEvent<T>, F extends MessageQueueFinder<T>> {

	List<F> finders();

	void push(E event, String[] keys, MessageExchangeType messageExchangeType);

}
