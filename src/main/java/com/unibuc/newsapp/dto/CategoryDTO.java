package com.unibuc.newsapp.dto;

public class CategoryDTO {

        private Long id;
        private String name;

        public CategoryDTO() {
        }

        public CategoryDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
}
