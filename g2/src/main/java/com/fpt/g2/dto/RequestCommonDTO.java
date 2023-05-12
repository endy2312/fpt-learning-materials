package com.fpt.g2.dto;

import com.fpt.g2.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCommonDTO {
    private Integer page;
    private Integer size;
    private String keyword;
    private String sortBy;
    private String sortType;

    public Integer getPage() {
        return Objects.isNull(page) || page <= 0 ? 0 : page - 1;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return Objects.isNull(size) ? 10 : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getKeyword() {
        return Objects.isNull(keyword) || keyword.isEmpty() ? null : keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSortType() {
        return Objects.isNull(sortType) ? CommonConstant.DESC : sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Sort getSortBy() {
        if (Objects.isNull(sortBy)) {
            if (getSortType().equalsIgnoreCase(CommonConstant.DESC)) {
                return Sort.by(Sort.Order.desc(CommonConstant.UPDATED_AT));
            }
        }
        if (getSortType().equalsIgnoreCase(CommonConstant.DESC)) {
            return Sort.by(Sort.Order.desc(sortBy));
        }
        return Sort.by(Sort.Order.asc(sortBy));
    }
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
