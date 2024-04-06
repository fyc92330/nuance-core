package org.chun.internal;

import org.chun.internal.core.Message;
import org.chun.internal.core.MessageFetchExecutor;
import org.chun.internal.core.MessageFetcher;
import org.chun.internal.core.MessageQueue;
import org.chun.internal.core.MessageQueueFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractSimpleMessageFetcher<T extends Message> implements MessageFetcher<T>, MessageFetchExecutor<T>, MessageQueueFinder<T> {

	@Autowired
	private ApplicationContext applicationContext;

	public abstract Map<String, MessageQueue<T>> getQueueMap();

	public abstract MessageQueue<T>[] getQueues();

	public abstract Class<? extends MessageQueue<T>>[] getQueueClasses();

	public abstract void execute(List<T> messages);


	@Override
	public List<T> fetch(MessageQueue<T> messageQueue) {

		return messageQueue.get();
	}


	@Override
	public void consume(String queueId) {

		Arrays.stream(getQueueClasses())
				.map(applicationContext::getBean)
				.filter(queue -> queueId.equals(queue.id()))
				.map(this::fetch)
				.forEach(this::execute);
	}

}
