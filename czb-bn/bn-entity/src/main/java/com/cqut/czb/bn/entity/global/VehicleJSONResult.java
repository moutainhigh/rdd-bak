package com.cqut.czb.bn.entity.global;

import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleJSONResult<T> {

    @JsonProperty("ErrorRecog")
    private String ErrorRecog;
    @JsonProperty("Result")
    private int Result;


    public VehicleJSONResult(String ErrorRecog, int Result) {
        this.ErrorRecog = ErrorRecog;
        this.Result = Result;
    }


    private VehicleJSONResult(int Result) {
        this.Result = Result;
    }

    public VehicleJSONResult() {
        this(ResponseCodeConstants.SUCCESS);
    }
    @JsonIgnore
    public String getErrorRecog() {
        return ErrorRecog;
    }
    @JsonIgnore
    public void setErrorRecog(String ErrorRecog) {
        ErrorRecog = ErrorRecog;
    }
    @JsonIgnore
    public int getResult() {
        return Result;
    }
    @JsonIgnore
    public void setResult(int Result) {
        Result = Result;
    }

    public static String CODE_KEY = "Result";

    public static String MESSAGE_KEY = "ErrorRecog";

}
