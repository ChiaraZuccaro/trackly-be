package com.trackly.models;

import jakarta.persistence.*;

@Entity
public class Subcategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String label;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  // Getter e Setter
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getLabel() { return label; }
  public void setLabel(String label) { this.label = label; }

  public Category getCategory() { return category; }
  public void setCategory(Category category) { this.category = category; }
}
