package org.springframework.extension.web.method.support;

import com.alibaba.fastjson2.JSONObject;

/**
 * JSONObjectWrapper.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see JSONObjectWrapper
 */
public class JSONObjectWrapper {

    private JSONObject jsonObject;

    public JSONObjectWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }
}
