package org.chun.internal;

import lombok.SneakyThrows;
import org.chun.consts.MessageConst;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageQueue;
import org.chun.internal.core.MessageQueueViewer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class AbstractSimpleMessageQueue<T extends Message> implements MessageQueue<T>, MessageQueueViewer {

	private final ArrayBlockingQueue<T> queue;

	private final String queueId;

	private int prefetchCount = 1;

	private final int maximum;

	private LocalDateTime expiredTime;

	public abstract long maxQueueCount();


	public AbstractSimpleMessageQueue(int maximum) {

		this.queueId = UUID.randomUUID().toString();
		this.maximum = maximum;
		this.expiredTime = LocalDateTime.now().plusMinutes(MessageConst.QUEUE_ALIVE_MINUTES);
		this.queue = new ArrayBlockingQueue<>(maximum);
	}


	public AbstractSimpleMessageQueue(int maximum, int prefetchCount) {

		this.queueId = UUID.randomUUID().toString();
		this.maximum = maximum;
		this.prefetchCount = prefetchCount;
		this.expiredTime = LocalDateTime.now().plusMinutes(MessageConst.QUEUE_ALIVE_MINUTES);
		this.queue = new ArrayBlockingQueue<>(maximum);
	}


	@Override
	public void add(T message) {

		add(message, 30L);
	}


	@Override
	@SneakyThrows
	public void add(T message, long timeout) {

		queue.offer(message, timeout, TimeUnit.SECONDS);
	}


	@Override
	public List<T> get() {

		List<T> messages = new ArrayList<>(prefetchCount);
		for (int i = 0; i < prefetchCount; i++) {

			T message;
			if ((message = queue.poll()) == null) {
				continue;
			}
			messages.add(message);
		}
		return messages;
	}


	@Override
	public long count() {

		return queue.size();
	}


	@Override
	public int prefetchCount() {

		return prefetchCount;
	}


	@Override
	public long maxSize() {

		return maximum;
	}


	@Override
	public LocalDateTime expiredTime() {

		return expiredTime;
	}


	@Override
	public void refreshLatestTime() {

		expiredTime = expiredTime.plusMinutes(MessageConst.QUEUE_ALIVE_MINUTES);
	}


	@Override
	public String id() {

		return queueId;
	}

}
