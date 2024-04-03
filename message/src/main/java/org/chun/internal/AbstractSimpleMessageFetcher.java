package org.chun.internal;

import java.util.List;
import org.chun.enums.GDApiStatusType;
import org.chun.exception.GDInternalException;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageFetchExecutor;
import org.chun.internal.core.MessageFetcher;
import org.chun.internal.core.MessageQueue;
import org.chun.internal.core.MessageQueueFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractSimpleMessageFetcher<T extends Message> implements MessageFetcher<T>, MessageFetchExecutor<T>, MessageQueueFinder<T> {

  @Autowired
  private ApplicationContext applicationContext;


  public abstract MessageQueue<T> getQueue();

  public abstract Class<MessageQueue<T>> getQueueClass();

  public abstract void execute(List<T> messages);


  @Override
  public List<T> fetch(MessageQueue<T> messageQueue) {

    return messageQueue.get();
  }


  @Override
  public void consume(String queueId) {

    MessageQueue<T> queue = applicationContext.getBean(getQueueClass());
    if (!queueId.equals(queue.id())) {
      throw new GDInternalException(GDApiStatusType.KNOWN_ERROR);
    }

    List<T> messages = fetch(queue);
    execute(messages);
  }
}
