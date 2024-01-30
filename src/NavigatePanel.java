import java.util.*;

public class NavigatePanel {
    static int monsterCount = (int)((Math.random()*(10-3)+3));
    static  int partnerCount = (int)(Math.random()*(monsterCount/2-1)+1);

    Character tempPartner;

    Character[] adventurePath = new Character[partnerCount+monsterCount];
    public void StartScene()
    {
        System.out.println("------------------WELCOME------------------");
        System.out.println("TO START/QUIT GAME PRESS FOLLOWING BUTTONS:");
        System.out.println("s - START GAME       |         e - END GAME");
    }
    public void StartQuitGame(String usrInput)
    {
        if(usrInput.equals("s"))
            StartGameMenu();
        else {
            System.out.println("Wrong key!");
            QuitGame();
        }
    }

    public void StartGameMenu()
    {
        System.out.println("Your goal is to defeat all monsters in your neighbourhood");
        System.out.println("Which character do you want to play? :");
        System.out.println("1 - Barbarian");
        System.out.println("2 - Archer");
    }

    public void SetEnemiesAndPartners()
    {
        System.out.println("monsters to eliminate: "+monsterCount+" your partners during your adventure: "+partnerCount);
    }

    public Character[] CreateAdventurePath()
    {
        for (int i=0; i<monsterCount; i++)
        {
            adventurePath[i] = new Monster(0,0);
        }
        if(partnerCount==0)
            return adventurePath;
        else
        {
            for (int i = monsterCount; i < monsterCount + partnerCount; i++) {
                adventurePath[i] = new PartnerCharacter();
            }
            List<Character> characters = Arrays.asList(adventurePath);
            while (characters.get(characters.size()-1)  instanceof PartnerCharacter)
                Collections.shuffle(characters);
            adventurePath = characters.toArray(adventurePath);
            int i = 1;
            for(Character ch:adventurePath)
            {
                if (ch instanceof Monster) {
                    ch.setCharacterLevel(i);
                    ch.setHP(100*i);
                    i++;
                }
            }

        }
        return adventurePath;
    }
    public Character getPartner(String name) {
        for (Character ch:adventurePath)
        {
            if(ch.getName()==null)
                continue;
            else if(ch.getName().equals(name) && ch instanceof PartnerCharacter) {
                tempPartner = ch;
                return tempPartner;
            }
        }
        return tempPartner;
    }

    public void ShowAdventurePath()
    {
        System.out.println("Your Adventure Path:");
        for(Character character : adventurePath)
        {
            if(character instanceof Monster)
                System.out.print("Monster level: "+character.getCharacterLevel()+", ");
            else
                System.out.print("Partner: "+character.getName()+", ");
        }
        System.out.println();
        System.out.println("-----end-----");
    }

    public void AcceptRejectNewPartnerInfo(String PartnersName)
    {
        System.out.println("You found "+PartnersName+" who wants to help you!");
        System.out.println("Whether you want to use his help in the future? y/n:");
    }

    public boolean AcceptRejectNewPartner(Item[] eq)
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String yesornot = scanner.next();
            if (yesornot.equals("y") && CheckIfThereIsFreeSpaceInEQ(eq)) {
                return true;
            } else if (yesornot.equals("n")) {
                System.out.println("you refused to help yourself");
                return false;
            } else if (yesornot.equals("y") && !CheckIfThereIsFreeSpaceInEQ(eq)) {
                System.out.println("do you still want this partner? y/n");
                if (scanner.next().equals("y"))
                {
                    AcceptRejectNewPartner(eq);
                    return true;
                }
                else {
                    System.out.println("you refused to help yourself");
                    return false;
                }
            } else {
                System.out.println("Wrong key!");
            }
        }
    }

    public boolean CheckIfThereIsFreeSpaceInEQ(Item[] eq)
    {
        for(Item item:eq)
        {
            if(item==null)
                return true;
        }
        Scanner usrinput = new Scanner(System.in);
        System.out.println("Missing space in your EQ");
        System.out.println("Do you want to drop something? y/n");
        if(usrinput.next().equals("y"))
        {
           Character.DropItem();
           return true;
        }
        return false;
    }

    public boolean CheckIfThereIsFreeSpaceInEqForMixture(Item[] eq)
    {
        for(Item item:eq)
        {
            if(item==null)
                return true;
        }
        System.out.println("Missing space in your EQ");
        return false;
    }

    public String BeforeMonsterInfo()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You are preparing for a fight with the monster...");
        System.out.println("Do you want, before fighting, look at your EQ? y/n");
        String usrInput = scanner.next();
        if(usrInput.equals("y") || usrInput.equals("n"))
            return usrInput;

        return BeforeMonsterInfo();
    }
    public void IntroduceOpponent(int level)
    {
        System.out.println("-------------------------");
        System.out.println("      Your enemy       ");
        System.out.println("     Monster level:");
        System.out.println("          "+level+"            ");
        System.out.println("-------------------------");
    }

    public void Fight(Character you, Character enemy,int round)
    {
        if(round%you.getDexerity()==0)
        {
            int giveDmg = you.giveDamage();
            enemy.setHP(enemy.getHP()- giveDmg);
            System.out.println("Damage that you gave to monster: "+giveDmg);
        }
        else
        {
            if(Character.partnerInUse) {
                int giveDmg = getPartner(Character.getCurrentPartnerName()).giveDamage();
                enemy.setHP(enemy.getHP()- giveDmg);
                System.out.println("Damage that "+getPartner(Character.getCurrentPartnerName()).getName()+" gave to monster: "+giveDmg);
            }

            int recieveDmg = enemy.giveDamage();
            you.setHP(you.getHP()- recieveDmg);
            System.out.println("Damage that you received : "+recieveDmg);
        }

    }

    public String FindSomething()
    {
        int random = (int)((Math.random()*10)+1);
        if(random>0&&random<=3)
            return "heal_mixture";

        else if (random>2&&random<=4)
            return "dexerity_mixture";

        return null;
    }

    public boolean SomeoneLost(Character you, Character monster)
    {
        if(monster.getHP()<=0||you.getHP()<=0)
            return false;
        return true;
    }

    public void Win(Character character,Monster obj)
    {
        int exp = obj.addEXP();
        System.out.println("You won");
        System.out.println("Earned EXP: "+exp);
        character.levelMenager(exp);
        Character.partnerInUse = false;
    }

    public void Defeat()
    {
        System.out.println("You lost..");
        QuitGame();
    }

    public void CompleteGame(int score)
    {
        System.out.println("Congratulations you finished your path!!!");
        System.out.println("Your score: "+score);
    }

    public void QuitGame()
    {
        System.out.println("--------------------END--------------------");
        System.exit(0);
    }

}
