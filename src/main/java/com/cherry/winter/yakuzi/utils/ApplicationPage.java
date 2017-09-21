package com.cherry.winter.yakuzi.utils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

/**
 * @author fangwei.f
 * @date 2017/09/14
 */
@Getter
@Setter
public class ApplicationPage {
    @NotNull
    @Min(0)
    @Max(1000)
    private int page;
    @NotNull
    @Min(0)
    @Max(1000)
    private int size;
    private Direction sortDirection;
    private String[] sortProperties;

    public ApplicationPage() {
    }

    public PageRequest toPageRequest() {
        if (sortDirection == null || sortProperties == null) {
            return new PageRequest(page, size);
        } else {
            return new PageRequest(page, size, sortDirection, sortProperties);
        }

    }

}
