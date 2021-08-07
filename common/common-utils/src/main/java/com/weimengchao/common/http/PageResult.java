package com.weimengchao.common.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Builder
public class PageResult<T> implements Serializable {

    private int pageNo;//页码
    private int size;//每页显示大小
    private long total;//总数
    private List<T> result;//查询结果

}
