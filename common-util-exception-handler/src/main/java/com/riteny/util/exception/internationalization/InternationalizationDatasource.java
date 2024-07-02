package com.riteny.util.exception.internationalization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface InternationalizationDatasource {

    Map<String, Map<String, String>> internationalizationProfileMap = new ConcurrentHashMap<>();

    /**
     * 傳入對應的異常内容index
     * 從數據源内獲取到對應的國際化文本
     *
     * @param index 異常内容的索引
     * @return 異常内容的國際化文本
     */
    String getValue(String index, String lang);
}
