package me.hncn.dhtspider.model.mongo.infoHash;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by 001 on 2016/7/13.
 */
@Document(collection = "info_hash")
public class InfoHash implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Field("info_hash_hex")
    private String infoHashHex;
    @Field("info_hash_byte")
    private byte[] infoHashBytes;
    @Field("create_time")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfoHashHex() {
        return infoHashHex;
    }

    public void setInfoHashHex(String infoHashHex) {
        this.infoHashHex = infoHashHex;
    }

    public byte[] getInfoHashBytes() {
        return infoHashBytes;
    }

    public void setInfoHashBytes(byte[] infoHashBytes) {
        this.infoHashBytes = infoHashBytes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}


