package com.local.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data//包含了@ToString，@EqualsAndHashCode，@Getter / @Setter和@RequiredArgsConstructor的功能，提供类所有属性的 getter 和 setter 方法，此外还提供了equals、canEqual、hashCode、toString 方法
@AllArgsConstructor//为类提供一个全参的构造方法
public class ImgResult {
    private String img;

    private String uuid;
}
