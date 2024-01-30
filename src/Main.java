import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        NavigatePanel navigatePanel = new NavigatePanel();
        Scanner usrInput = new Scanner(System.in);
        Character selectedCharacter = null;
        navigatePanel.StartScene();
        navigatePanel.StartQuitGame(usrInput.next());
        while(true)
        {
            try{
                int characterSelect = usrInput.nextInt();

                if (characterSelect == 1) {
                    selectedCharacter = new BarbarianCharacter(400);
                    System.out.println("Barbarian character has been selected");
                    break;
                } else if (characterSelect == 2) {
                    selectedCharacter = new ArcherCharacter(200);
                    System.out.println("Archer character has been selected");
                    break;
                }
            }
            catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Wrong key!");
                return;
            }
        }

        selectedCharacter.showEQ();
        navigatePanel.SetEnemiesAndPartners();
        Character[] adventurePath = navigatePanel.CreateAdventurePath();
        navigatePanel.ShowAdventurePath();

        for (Character character : adventurePath) {
            if (character instanceof Monster) {
                if (navigatePanel.BeforeMonsterInfo().equals("y"))
                    selectedCharacter.showEQ();
                navigatePanel.IntroduceOpponent(((Monster) character).getCharacterLevel());
                int round = 1;
                while (navigatePanel.SomeoneLost(selectedCharacter, character)) {
                    navigatePanel.Fight(selectedCharacter, character, round);
                    round++;
                }
                if(selectedCharacter.getHP()<0)
                    System.out.println("your hp: 0");
                else
                    System.out.println("your hp: " + selectedCharacter.getHP());

                if (selectedCharacter.getHP() > 0)
                {
                    navigatePanel.Win(selectedCharacter, (Monster) character);
                    if(character.getCharacterLevel()>2)
                    {
                        String mixture = navigatePanel.FindSomething();
                        if(mixture!=null)
                        {
                            System.out.println("You've found something!");
                            if (!navigatePanel.CheckIfThereIsFreeSpaceInEqForMixture(selectedCharacter.getEquipment()))
                            {
                                System.out.println("Use something");
                                selectedCharacter.showEQ();
                            }
                            for (int j = 0; j < selectedCharacter.getEquipment().length; j++) {
                                if (selectedCharacter.getEquipment()[j] == null) {
                                    if(mixture.equals("heal_mixture"))
                                        selectedCharacter.getEquipment()[j] = new HealMixtureItem(mixture, character.getCharacterLevel(), true);
                                    else if(mixture.equals("dexerity_mixture"))
                                        selectedCharacter.getEquipment()[j] = new DexerityMixtureItem(mixture, true);
                                    break;
                                }
                            }
                        }
                    }

                }
                else
                    navigatePanel.Defeat();

            } else if (character instanceof PartnerCharacter) {
                navigatePanel.AcceptRejectNewPartnerInfo(((PartnerCharacter) character).name);
                if (navigatePanel.AcceptRejectNewPartner(selectedCharacter.getEquipment()))
                    for (int j = 0; j < selectedCharacter.getEquipment().length; j++) {
                        if (selectedCharacter.getEquipment()[j] == null) {
                            selectedCharacter.getEquipment()[j] = new Item(((PartnerCharacter) character).name + " talisman");
                            break;
                        }
                    }
                selectedCharacter.showEQ();
            }
        }
        navigatePanel.CompleteGame(selectedCharacter.expBar);

    }
}