package org.chun.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.chun.utils.JsonUtil;

public abstract class AbstractScheduleCache<T> implements CacheReader<T>, CacheEditor<T>, CacheRemover, CacheExecutor, CacheRefresher {

  protected volatile Map<String, T> cache = new ConcurrentHashMap<>();


  public abstract void execute();

  public abstract void update(Map<String, T> data);

  public abstract void updateIterator(Collection<T> values);

  public abstract void refresh();

  public abstract void notification();


  @Override
  public void update(String key, T value) {

  }


  @Override
  public T get(String key) {

    return cache.get(key);
  }


  @Override
  public List<T> getAll() {

    return new ArrayList<>(cache.values());
  }


  @Override
  public String cacheInfo() {

    return JsonUtil.SYSTEM.toJson(cache);
  }


  @Override
  public void remove(String key) {

    cache.remove(key);
  }

}
