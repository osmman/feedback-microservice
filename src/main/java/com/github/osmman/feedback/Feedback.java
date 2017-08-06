package com.github.osmman.feedback;

import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Value
@Builder(toBuilder = true)
public class Feedback {

    @Id
    private Long id;

    @NotEmpty
    @Length(max = 80)
    private String name;

    @NotEmpty
    @Length(max = 255)
    private String message;

    private Date createdAt;
}
