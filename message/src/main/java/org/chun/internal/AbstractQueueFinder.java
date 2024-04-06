package org.chun.internal;

import jakarta.annotation.PostConstruct;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageQueue;
import org.chun.internal.core.MessageQueueFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Listener, Fetcher must extends this
 */
public abstract class AbstractQueueFinder<T extends Message> implements MessageQueueFinder<T> {

	protected final Map<String, MessageQueue<T>> MESSAGE_QUEUE_REPOSITORY = new ConcurrentHashMap<>();

	protected final List<Class<? extends MessageQueue<T>>> MESSAGE_QUEUE_CLASSES = new ArrayList<>();

	@PostConstruct
	public abstract void init();

	@Override
	public List<MessageQueue<T>> getQueues() {

		return new ArrayList<>(MESSAGE_QUEUE_REPOSITORY.values());
	}

	@Override
	public List<Class<? extends MessageQueue<T>>> getQueueClasses() {

		return MESSAGE_QUEUE_CLASSES;
	}

	@Override
	public Map<String, MessageQueue<T>> getQueueMap() {

		return MESSAGE_QUEUE_REPOSITORY;
	}

}
