package com.musinsa.shoppingcategory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "unique_category_name_depth", columnList = "name, depth", unique = true)})
public class Category extends BaseTimeEntity implements Serializable {

    private static final long serialVersionUID = 473458725392988376L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_parent_id"))
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    @Builder.Default
    private Long depth = 1L;

    public void updateCategory(String name) {
        if (!StringUtils.hasText(name)) {
            return;
        }
        this.name = name;
    }

}
