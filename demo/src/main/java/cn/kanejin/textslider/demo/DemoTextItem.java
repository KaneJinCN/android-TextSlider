package cn.kanejin.textslider.demo;

import cn.kanejin.textslider.TextItem;

/**
 * Created by Kane on 8/4/16.
 */
public class DemoTextItem implements TextItem {

    private Long id;
    private String text;
    private String url;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
