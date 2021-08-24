package com.itauge.blog.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "can't not be null")
    private String name;
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();
}
