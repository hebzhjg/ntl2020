package net.xdclass.domain;

/**
 * 这个类，用来想客户端返回服务端处理结果
 */
public class JsonData {

    private int code;

    private String msg;

    private Object data;

    /**
     *
     * @param code
     * @param msg
     * @param data
     */
    public JsonData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     *
     * @param code
     * @param msg
     */
    public JsonData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
