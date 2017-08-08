package com.github.osmman.feedback;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ApiModel(value = "Feedback", description = "Feedback resource representation")
public class Feedback {

    @Id
    @ApiModelProperty(value = "Unique identifier", readOnly = true, example = "123", position = 0)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 30)
    @ApiModelProperty(value = "Author's name", required = true, example = "Bob", position = 1)
    private String name;

    @NotEmpty
    @Length(max = 255)
    @ApiModelProperty(value = "Content of feedback", required = true, example = "Your feedback message.", position = 2)
    private String message;

    @ApiModelProperty(value = "Date and time of creation", readOnly = true, position = 3)
    private Date createdAt;
}
