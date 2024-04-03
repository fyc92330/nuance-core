package org.chun.internal;

import java.util.List;
import lombok.NonNull;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageEvent;
import org.chun.internal.core.MessageFetcher;
import org.chun.internal.core.MessageListenExecutor;
import org.chun.internal.core.MessageListener;
import org.chun.internal.core.MessageQueue;
import org.chun.internal.core.MessageQueueFinder;

public abstract class AbstractSimpleMessageListener<T extends MessageEvent<Message>> implements MessageListener<T>, MessageListenExecutor<T>, MessageQueueFinder {

  public abstract MessageQueue<Message> getQueue();

  public abstract Class<MessageQueue<Message>> getQueueClass();

  public abstract MessageFetcher<?> fetcher();


  @Override
  public void execute(List<T> events) {

    for (T event : events) {

      Message message = convert(event);
      produce(message);
      call(getQueue().id());
    }
  }


  @Override
  public Message convert(T event) {

    return event.get();
  }


  @Override
  public void produce(@NonNull Message message) {

    getQueue().add(message);
  }


  @Override
  public void call(String queueId) {

    fetcher().consume(queueId);
  }
}
