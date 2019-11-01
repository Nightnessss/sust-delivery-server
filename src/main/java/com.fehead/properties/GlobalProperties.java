package com.fehead.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 写代码 敲快乐
 * だからよ...止まるんじゃねぇぞ
 * ▏n
 * █▏　､⺍
 * █▏ ⺰ʷʷｨ
 * █◣▄██◣
 * ◥██████▋
 * 　◥████ █▎
 * 　　███▉ █▎
 * 　◢████◣⌠ₘ℩
 * 　　██◥█◣\≫
 * 　　██　◥█◣
 * 　　█▉　　█▊
 * 　　█▊　　█▊
 * 　　█▊　　█▋
 * 　　 █▏　　█▙
 * 　　 █
 *
 * @author Nightnessss 2019/10/31 20:31
 */
@ConfigurationProperties(prefix = "fehead")
public class GlobalProperties {

    private ConnectionProperties connectionProperties = new ConnectionProperties();

    public ConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(ConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
