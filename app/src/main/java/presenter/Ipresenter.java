package presenter;

import java.util.Map;

public interface Ipresenter<T> {
    void startRequestGet(String url,Class clazz);
    void startRequestPost(String url, Map<String,String>map, Class clazz);
}
