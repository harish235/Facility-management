package com.quinbay.issues.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Categories {
    @Id
    @SequenceGenerator(name = "cat_seq", sequenceName = "cat_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_seq")
    Integer categoryId;
    String categoryName;
    Integer validityTime;
}
