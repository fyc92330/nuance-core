package org.chun.internal;


import jakarta.annotation.PostConstruct;
import org.chun.enums.MessageExchangeType;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageEvent;
import org.chun.internal.core.MessageExchange;
import org.chun.internal.core.MessageListener;
import org.chun.internal.core.MessageQueue;
import org.chun.internal.core.MessageQueueFinder;
import org.chun.internal.core.MessageQueuePattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public abstract class AbstractMessageExchange<T extends Message, E extends MessageEvent<T>, F extends MessageQueueFinder<T>> implements MessageExchange<T, E, F> {

	protected final List<F> subFinders = new ArrayList<>();

	@PostConstruct
	protected abstract void init();// 將subFinders 填滿

	@Override
	public List<F> finders() {

		return subFinders;
	}

	@Override
	public void push(E event, String[] keys, MessageExchangeType messageExchangeType) {

		List<MessageListener<T, E>> listeners = new ArrayList<>();

		for (F queueFinder : subFinders) {

			MessageQueue<T>[] queues = queueFinder.getQueues();
			boolean isValid = switch (messageExchangeType) {
				case DIRECT -> validDirectQueue(queues, keys);
				case TOPIC -> validTopicQueue(queues, keys);
				case FANOUT -> true;
			};

			if (!isValid) {
				continue;
			}

			if (queueFinder instanceof MessageListener<?, ?>) {
				listeners.add((MessageListener<T, E>) queueFinder);
			}
		}

		for (MessageListener<T, E> listener : listeners) {

			T message = listener.convert(event);
			listener.produceMultiple(message, keys);
		}
	}

	private boolean validDirectQueue(MessageQueue<T>[] queues, String[] keys) {

		Set<String> ids = Arrays.stream(queues).map(MessageQueue::id).collect(Collectors.toSet());
		return Arrays.stream(keys).anyMatch(ids::contains);
	}

	private boolean validTopicQueue(MessageQueue<T>[] queues, String[] keys) {

		boolean isMatch = false;
		for (MessageQueue<T> queue : queues) {
			if (queue instanceof MessageQueuePattern) {
				Pattern pattern = ((MessageQueuePattern) queue).rule();
				isMatch = Arrays.stream(keys).map(pattern::matcher).anyMatch(Matcher::matches);
				if (isMatch) break;
			}
		}
		return isMatch;
	}

}
