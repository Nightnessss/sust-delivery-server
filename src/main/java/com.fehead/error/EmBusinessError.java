package com.fehead.error;

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
 * @author Nightnessss 2019/7/8 16:27
 */
public enum EmBusinessError implements CommonError {
    // 通用错误类型 1000x
    UNKNOWN_ERROR(10000, "未知错误"),
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),

    // 2000x 类型为数据库操作异常
    DATA_ERROR(20000, "数据库操作失败"),
    DATA_INSERT_ERROR(20001, "数据库插入异常"),
    DATA_DELETE_ERROR(20002, "数据库删除异常"),
    DATA_UPDATE_ERROR(20003, "数据库更新异常"),
    DATA_SELECT_ERROR(20004, "数据库查询异常"),

    // 90000 开头为用户认证相关错误
    SERVICE_REQUIRE_AUTHENTICATION(90001,"访问的服务需要身份认证"),
    SERVICE_REQUIRE_ROLE_ADMIN(90002,"访问服务需要管理员权限")
    ;

    private EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;



    @Override
    public int getErrorCode() {
        return errCode;
    }

    @Override
    public String getErrorMsg() {
        return errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
