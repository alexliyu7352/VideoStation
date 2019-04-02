package com.site.vs.videostation.ui.category;

import com.site.vs.videostation.api.CategoryService;
import com.site.vs.videostation.entity.CategoryDetailEntity;
import com.site.vs.videostation.http.Retrofits;
import com.zhusx.core.network.HttpResult;

import java.util.Map;

import rx.Observable;

/**
 * @author zhangbb
 * @date 2016/12/19
 */
public class CategoryModel implements CategoryDetailContract.Model {

    @Override
    public Observable<HttpResult<CategoryDetailEntity>> getCategoryBy(String id, Map<String, String> map, int page) {
        return Retrofits.createApi(CategoryService.class).getCategoryBy(id, map, page);
    }

}