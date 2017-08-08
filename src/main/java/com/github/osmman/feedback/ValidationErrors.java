package com.github.osmman.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

@JsonFormat
@ApiModel(description = "Response of validation problems")
public class ValidationErrors {

    public ValidationErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    @ApiModelProperty(value = "List of constraint violations")
    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        return errors;
    }
}
