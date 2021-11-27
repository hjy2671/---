package me.zhengjie.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportExportMapHandler implements ImportExportHandler{

    private String[] keys;

    private List<HashMap<Object, Object>> maps;

    private HashMap<String, HashMap<Object, Object>> map = new HashMap<>();

    public ImportExportMapHandler(String[] keys, List<HashMap<Object, Object>> maps) {
        this.keys = keys;
        this.maps = maps;
        init();
    }

    public void init(){
        for (String key : keys) {
            if (maps != null && maps.size() > 0){
                map.put(key, maps.remove(0));
            }
        }
    }

    @Override
    public void handle(Iterable<?> iterable) throws NoSuchFieldException, IllegalAccessException {

        for (Object target : iterable) {
            for (Map.Entry<String, HashMap<Object, Object>> entry: map.entrySet()) {

                final Field field = target.getClass().getDeclaredField(entry.getKey());
                boolean accessible = field.isAccessible();
                field.setAccessible(true);

                final Object value = entry.getValue().get(field.get(target));

                if (value != null) {
                    field.set(target, value);
                }

                field.setAccessible(accessible);

            }
        }


    }
}
