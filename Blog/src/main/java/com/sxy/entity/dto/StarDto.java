package com.sxy.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class StarDto implements Serializable {

    private Long bid;

    private Long num;

    private boolean liked;
}
