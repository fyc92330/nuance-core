package org.chun.cache;

import java.util.List;

public interface CacheReader<T> {

  T get(String key);

  List<T> getAll();

  String cacheInfo();


}
