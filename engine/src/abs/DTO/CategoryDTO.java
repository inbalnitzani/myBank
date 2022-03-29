package abs.DTO;

public class CategoryDTO {
    private String categoryName;

    public CategoryDTO(String name){
        categoryName=name;
    }
    public String getCategoryName(){return categoryName;}
}
