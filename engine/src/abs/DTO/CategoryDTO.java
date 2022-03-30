package abs.DTO;

import abs.Category;
import org.jetbrains.annotations.NotNull;

public class CategoryDTO {
    private String categoryName;

    public CategoryDTO(String name) {
        categoryName = name;
    }

    public CategoryDTO(@NotNull Category category) {
        new CategoryDTO(category.getCategoryName());
    }

    public String getCategoryName() {
        return categoryName;
    }
}
