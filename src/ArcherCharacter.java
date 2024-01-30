public class ArcherCharacter extends Character{
    int dexerity = 2;
    int strength = 3;
    public ArcherCharacter(int hp)
    {
        super(hp);
        Item bow = new Item("BOW",1,false);
        equipment[0] = bow;
        equipment[1] = new HealMixtureItem("heal_mixture",3,true);
    }
    public int giveDamage()
    {
        return (int)(Math.random()*((20+(characterLevel*10)*strength)-10)+10);
    }

    public int getDexerity() {
        return dexerity;
    }

    public void setDexerity(int dexerity) {
        this.dexerity = dexerity;
    }
}
