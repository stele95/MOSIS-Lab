package rs.elfak.mosis.stele.myplaces.Classes;

public class MyPlace {
    private String name;
    private String description;

    public MyPlace(){}

    public MyPlace(String name,String description)
    {
        this.name=name;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
