package com.cyhz.extend.view.glyph;

/**
 * Created by liuxiaolong on 17/1/9.
 */

import java.util.List;

/**
 * 图元的数据提供者，实现接口将可以个图元提供数据（在图元的次级父类里有提供set get操作）。
 *
 * liuxiaolong 201601091001.
 * @param <T>
 */
public interface ExtGlyphDataSourceProvider<T,E>{
     List<T> dataSourceX();

     List<E> dataSourceY();
}
