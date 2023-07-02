public class FabriqueCrackerHacherPassword {

  public static PasswordHacherCracher getInstance(int choice){
    
    PasswordHacherCracher passwordHacherCracher = null;
    
    if (choice == 1){
      passwordHacherCracher = new BruteForceCracker();
    }
    else if (choice == 2){
      passwordHacherCracher = new DictionnaryCracker();
    }
    else if (choice == 3){
      passwordHacherCracher = new BruteForceHacher();

    }
    else  {
      passwordHacherCracher = new DictionnaryHacher();
    }

    return passwordHacherCracher;


  }
   

    
}
