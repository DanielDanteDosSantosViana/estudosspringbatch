package br.com.estudosspringbatch.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class JobData {

    private Map<String, Map<Object, Object>> map = new HashMap<>();

    <K, V> void put(String jobId, K key, V value) {
        Map<Object, Object> jobMap = map.get(jobId);
        if (jobMap == null) {
            map.put(jobId, new HashMap<>());
            jobMap = map.get(jobId);
        }
        jobMap.put(key, value);
    }

    <K, V> V get(String jobId, K key) {
        Map<Object, Object> jobMap = map.get(jobId);
        if (jobMap == null) {
            return null;
        }
        return (V) jobMap.get(key);
    }


    <K, V> V getOrDefault(String jobId, K key, V defaultValue) {
        V value = get(jobId, key);
        return value != null ? value : defaultValue;
    }

    public void clear(String jobId) {
        map.getOrDefault(jobId, Collections.EMPTY_MAP).clear();
    }
}