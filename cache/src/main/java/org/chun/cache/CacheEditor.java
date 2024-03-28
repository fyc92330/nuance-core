package org.chun.cache;

import java.util.Collection;
import java.util.Map;

public interface CacheEditor<T> {

  void update(String key, T value);

  void update(Map<String, T> data);

  void updateIterator(Collection<T> values);
}
