package me.zhengjie.base;

import lombok.Getter;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
@Component
@Getter
public class ResultMapHandler implements ResultHandler<T> {

    private final HashMap map = new HashMap<>();
    private final HashMap reverseMap = new HashMap<>();

    @Override
    public void handleResult(ResultContext resultContext) {
        final Map resultMap = (Map) resultContext.getResultObject();
        map.put(resultMap.get("key"), resultMap.get("value"));
        reverseMap.put(resultMap.get("value"), resultMap.get("key"));
    }


}
