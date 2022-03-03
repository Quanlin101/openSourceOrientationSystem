package com.lig.orientationSystem.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum MessageFromEnums {
//从哪里了解到我们的：海报宣传、宣讲会、微信推文、QQ自媒体，冬/夏令营

    FromPoster(1, "海报"),
    FromPropagandaMeeting(2, "宣讲会"),
    FromWeChat(3, "微信推文"),
    FromQQ(4, "QQ自媒体"),
    FromCamp(5, "夏/冬令营")
    ;

    @JsonValue
    private final Integer code;
    @JsonValue
    private final String desc;

    MessageFromEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
