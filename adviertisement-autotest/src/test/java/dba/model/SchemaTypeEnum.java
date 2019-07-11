package dba.model;

public enum SchemaTypeEnum {

    DING_TONG         ("DING_TONG", "顶通URL"),
    LINK              ("LINK", "H5 链接"),
    POST              ("POST", "帖子详情"),
    PC_BROADCAST      ("PC_BROADCAST", "PC直播间"),
    MOBILE_BROADCAST  ("MOBILE_BROADCAST", "移动直播间"),
    PC_VIDEO          ("PC_VIDEO", "PC视频"),
    MOBILE_VIDEO      ("MOBILE_VIDEO", "移动视频"),
    LIVE_TRAILER      ("LIVE_TRAILER_URL", "直播预告"),
    H5_SHOW_TOPBAR    ("H5_SHOW_TOPBAR", "带topbar链接"),
    H5_HIDE_TOPBAR    ("H5_HIDE_TOPBAR", "不带topbar链接"),
    CUSTOM            ("CUSTOM", "自定义");

    private String value;

    private String text;

    SchemaTypeEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
