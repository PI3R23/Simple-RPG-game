public class BarbarianCharacter extends Character{
    int dexerity = 4;
    int strength = 8;

    public BarbarianCharacter(int hp)
    {
        super(hp);
        Item axe = new Item("AXE",1,false);
        equipment[0] = axe;
        equipment[1] = new HealMixtureItem("heal_mixture",1,true);
    }

    public int giveDamage()
    {
        return (int)(Math.random()*((20+(characterLevel*13)*strength)-10)+10);
    }

    public int getDexerity() {
        return dexerity;
    }

    public void setDexerity(int dexerity) {
        this.dexerity = dexerity;
    }
}
