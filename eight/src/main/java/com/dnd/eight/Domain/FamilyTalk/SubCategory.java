package com.dnd.eight.Domain.FamilyTalk;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="familyTalk_sub_category")
@Getter
@NoArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_category_id")
    Long id;

    private String content;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private ParentCategory parentCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "subCategory")
    private List<FamilyTalkQuestion> familyTalkQuestions = new ArrayList<>();

    public void setParentCategory(ParentCategory parentCategory) {
        this.parentCategory = parentCategory;
        parentCategory.getSubCategories().add(this);
    }

    public static SubCategory createSubCategory(String content, ParentCategory parentCategory){
        SubCategory subCategory = new SubCategory();

        subCategory.content = content;
        subCategory.setParentCategory(parentCategory);

        return subCategory;
    }
}
