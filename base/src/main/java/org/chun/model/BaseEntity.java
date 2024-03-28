package org.chun.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BaseEntity {

  private LocalDateTime createTime;

  private LocalDateTime updateTime;
}
