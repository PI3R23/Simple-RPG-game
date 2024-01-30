public class Monster extends Character{
    int EXPtoGive = (int)(Math.random()*((100+characterLevel*10)-characterLevel*10)+characterLevel*10);
    public Monster(int characterLevel, int hp)
    {
        super(characterLevel, hp);
    }

    public int addEXP()
    {
        return EXPtoGive;
    }

    public int giveDamage()
    {
        return (int)(Math.random()*((10+(characterLevel*3))-10)+7);
    }

    @Override
    public int getHP() {
        return super.getHP();
    }
}
