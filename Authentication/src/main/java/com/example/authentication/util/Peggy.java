package com.example.authentication.util;

import lombok.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Peggy<T> {
    private  int page;
    private int limit;
    private int totalPage;
    private List<T> content;
}
