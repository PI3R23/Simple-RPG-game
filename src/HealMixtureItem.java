public class HealMixtureItem extends Item{

    int hpToHeal = 100+level*50;
    public HealMixtureItem(String name,int level, boolean isMixture)
    {
        super(name, level, isMixture);
    }

    public int heal()
    {
        return hpToHeal;
    }
}
