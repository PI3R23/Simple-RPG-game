public class PartnerCharacter extends Character{
    int selectRandomName = (int)(Math.random()*10);
    String[] names = {"John","Joseph","David","Matthew","Mark","Richard","James","Nicholas","Stephen","Edward",};

    String name  = names[selectRandomName];

    public int giveDamage()
    {
        return (int)(Math.random()*(50-10)+10);
    }
    @Override
    public String getName() {
        return name;
    }
}
