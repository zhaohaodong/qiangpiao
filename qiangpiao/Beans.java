package com.example.qiangpiao.qiangpiao;

import java.util.List;
import java.util.Map;

/**
 * 作者:赵浩东
 * 时间:2018/9/13 9:43
 *
 * @author zhd
 */
public class Beans {
    private String flag;
    private Map<String , String > map;
    private List<String> result;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
