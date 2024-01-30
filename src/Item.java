public class Item {
    public String name;
    public int level;
    public boolean isMixture;

    public Item(String name,int level, boolean isMixture)
    {
        this.name = name;
        this.level = level;
        this.isMixture = isMixture;
    }
    public Item(String name, boolean isMixture)
    {
        this.name = name;
        this.isMixture = isMixture;
    }
    public Item(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int heal() {
        return 0;
    }
}
