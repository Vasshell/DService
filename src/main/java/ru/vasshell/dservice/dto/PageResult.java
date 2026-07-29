package ru.vasshell.dservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;

    public static <T> PageResult<T> from(Page<T> page){
        return new PageResult<>(
                new ArrayList<>(page.getContent()),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements()
        );
    }

    public Page<T> toPage(Pageable pageable){
        return new PageImpl<>(content, pageable, totalElements);
    }
}
