package lanmapping.template;

/**
 * 映射类型枚举类
 * @author meanvalue
 * Date: 2021/12/5 20:07
 */
public enum LmType {
    /**
     * HTTP模式
     */
    HTTP("http"),
    /**
     * HTTPS模式
     */
    HTTPS("https"),
    /**
     * TCP模式
     */
    TCP("tcp");
    private final String value;
    @Override
    public String toString() {
        return this.value;
    }
    LmType(String value) {
        this.value = value;
    }
}