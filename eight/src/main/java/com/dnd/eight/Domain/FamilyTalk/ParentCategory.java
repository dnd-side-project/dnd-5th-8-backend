package com.dnd.eight.Domain.FamilyTalk;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="familyTalk_parent_category")
@Getter
@NoArgsConstructor
public class ParentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_category_id")
    private Long id;

    private String content;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentCategory")
    private List<SubCategory> subCategories = new ArrayList<>();
}
