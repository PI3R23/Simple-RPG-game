import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Character {
    public String name;
    public int characterLevel =1;
    public int HP;
    public int dexerity;
    public int expBar;
    public static boolean partnerInUse = false;
    public static String currentPartnerName = "";
    static Item[] equipment = new Item[5];

    public Character(String name, int hp)
    {
        this.name = name;
        this.HP = hp;
    }
    public Character(int hp)
    {
        this.HP = hp;
    }

    public Character(int characterLevel, int hp)
    {
        this.characterLevel = characterLevel;
        this.HP = hp;
    }
    public Character()
    {}

    public void levelMenager(int exp)
    {
        expBar+=exp;
        if(expBar/100>0 && characterLevel!=expBar/100)
        {
            setCharacterLevel(expBar/100);
            System.out.println("LEVEL UP!!!");
            System.out.println("Your currnet level: "+getCharacterLevel());
        }
    }

    public void showEQ()
    {
        System.out.println("Your EQ:");
        int count = 1;
        for(Item item : equipment)
        {
            if(item == null)
                continue;
            else if(item.getName().contains("talisman")|| item.getName().contains("dexerity_mixture"))
                System.out.print(count+") "+item.getName()+", ");
            else
                System.out.print(count+") "+item.getName()+" - level: "+ item.getLevel()+", ");
            count++;
        }
        System.out.println();
        System.out.println("Your LEVEL: "+characterLevel);
        System.out.println("!!! If you want to drop some item press (d) !!!");
        System.out.println("!!! If you don't want to use any item press (e) !!!");
        System.out.println("-----end-----");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.next();
        if(userInput.equals("e"))
            System.out.println();
        else if (userInput.equals("d"))
            DropItem();
        else
            try {
                int numberOfItem = Integer.parseInt(userInput);
                if (numberOfItem >= 1 && numberOfItem <= 5) {
                    if (numberOfItem == 1) {
                        System.out.println("Item is used by default");
                        showEQ();
                    }
                    else if (equipment[numberOfItem - 1] == null)
                    {
                        System.out.println("Empty space!");
                        showEQ();
                    }
                    else if (equipment[numberOfItem - 1].getName().contains("talisman")) {
                        System.out.println("talisman has been used");
                        partnerInUse = true;
                        currentPartnerName = equipment[numberOfItem - 1].getName().split(" ")[0];
                        equipment[numberOfItem - 1] = null;
                    }
                    else if (equipment[numberOfItem - 1].getName().contains("mixture")) {
                        System.out.println("mixture has been used");
                        if(equipment[numberOfItem - 1].getName().contains("heal_mixture"))
                        {
                            setHP(getHP()+equipment[numberOfItem - 1].heal());
                            System.out.println("Your HP: "+getHP());
                        }
                        else if (equipment[numberOfItem - 1].getName().contains("dexerity_mixture")) {
                            setDexerity(getDexerity()-1);
                            System.out.println("dexerity has been improved");
                            if (getDexerity()<2)
                            {
                                System.out.println("You reached maximum level of dexerity!");
                                setDexerity(2);
                            }
                        }
                        equipment[numberOfItem - 1] = null;
                        Comparator<Item> comp = (item1,item2) -> {
                            String name1 = (item1 != null) ? item1.getName() : null;
                            String name2 = (item2 != null) ? item2.getName() : null;

                            if ("AXE".equals(name1)) {
                                return -1;
                            } else if ("AXE".equals(name2)) {
                                return 1;
                            } else {
                                return Comparator.nullsLast(Comparator.comparing(Item::getName)).compare(item1, item2);
                            }
                        };
                        Arrays.sort(equipment, comp);
                    }

                }
                else{
                    System.out.println("Wrong key!");
                    showEQ();}
            }catch(NumberFormatException e)
                {
                    System.out.println("Wrong key!");
                    showEQ();
                }
            }

    public int getHP() {
        return HP;
    }

    public static String getCurrentPartnerName() {
        return currentPartnerName;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int giveDamage() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public Item[] getEquipment() {
        return equipment;
    }
    public int getDexerity() {
        return dexerity;
    }
    public void setDexerity(int dexerity) {
        this.dexerity = dexerity;
    }

    public static void DropItem()
    {
        System.out.println("Your EQ:");
        int count = 1;
        for(Item item : equipment)
        {
            if(item == null)
                continue;
            else if(item.getName().contains("talisman"))
                System.out.print(count+") "+item.getName()+", ");
            else
                System.out.print(count+") "+item.getName()+" - level: "+ item.getLevel()+", ");
            count++;
        }
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which item do you want to drop?: ");
        int item = scanner.nextInt();
        if(item==1) {
            System.out.println("You can't drop your weapon");
            DropItem();
        }
        else if(item>1 && item<=5)
        {
            equipment[item - 1] = null;
            Comparator<Item> comp = (item1,item2) -> {
                String name1 = (item1 != null) ? item1.getName() : null;
                String name2 = (item2 != null) ? item2.getName() : null;

                if ("AXE".equals(name1)) {
                    return -1;
                } else if ("AXE".equals(name2)) {
                    return 1;
                } else {
                    return Comparator.nullsLast(Comparator.comparing(Item::getName)).compare(item1, item2);
                }
            };
            Arrays.sort(equipment, comp);
            System.out.println("Item has been dropped");
        }
        else
            DropItem();

    }
}
