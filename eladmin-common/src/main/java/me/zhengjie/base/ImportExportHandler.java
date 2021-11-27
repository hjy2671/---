package me.zhengjie.base;

public interface ImportExportHandler {

    void handle(Iterable<?> iterable) throws NoSuchFieldException, IllegalAccessException;

}
