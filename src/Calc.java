class Arbre {
    public int type; // 0 = nombre, 1 = addition, 2 = soustraction, 3= multiplication, 4 = division
    public int val;  // valeur de l'entier si type = 0
    public Arbre filsGauche;
    public Arbre filsDroit;
    
    Arbre(int type, int val) {
        this.type = type;
        this.val = val;
    }
    
    Arbre() {
        
    }
}

public class Calc {
     
    
    public static void main(String[] args) {
        int total;
        char caractere = Pep8.chari();
        Arbre racine = new Arbre();
        Arbre courant = racine;

        total = (int) gestionCaractere(caractere);
        caractere = Pep8.chari();
        while (caractere != '.') {
            if (caractere == '=') {
                Pep8.deco(total);
                Pep8.charo('\n');
            }
            caractere = Pep8.chari();
        }
        Pep8.charo('.');
        Pep8.charo('\n');
        
    }
    
    public static void constructionBranche(Arbre courant) {
        char caractere = Pep8.chari();
        if (caractere == '+' || caractere == '-' ||caractere == '*' ||caractere == '/') {            
            courant.val = caractere;
            courant.filsGauche = new Arbre();
            constructionBranche(courant.filsGauche);
            courant.filsDroit = new Arbre();
            constructionBranche(courant.filsDroit);            
        }
        else if (caractere > '0' && caractere < '9') {
            courant.val = caractere;
        }
        else if (caractere == ' ' || caractere == '\n') {
            constructionBranche(courant);
        }
    }
    
    public static int gestionCaractere(char caractere) {
        if (caractere >= '0' && caractere<= '9') {
            //return lectureNombre(conversionCharToInt(caractere));
        }
        else if (caractere == '+' ) {
            return (addition());
        }
        else if (caractere == '-' ) {
            return (soustraction());
        }
        else if (caractere == '*' ) {
            return (multiplication());
        }
        else if (caractere == '/' ) {
            return (division());
        }
        else if (caractere == ' ' || caractere == '\n')
        {
            return gestionCaractere(Pep8.chari());
        }
        return 0;
    }
    
    public static char lectureNombre(char nombre) {
        char nouveauNombre = Pep8.chari();
        if (nouveauNombre >= '0' && nouveauNombre <= '9') {
            return lectureNombre(conversionIntToChar(conversionCharToInt(nombre)*10 + conversionCharToInt(nouveauNombre)));
        }
        else {
            return nombre;
        }
 
    }
    
    public static int addition() {
        int premierElement;
        int deuxiemeElement;
        char temp;
        temp = Pep8.chari();
        premierElement = gestionCaractere(temp);
        temp = Pep8.chari();
        deuxiemeElement = gestionCaractere(temp);
        return (premierElement + deuxiemeElement);
        
    }
    
    public static int multiplication() {
        int premierElement;
        int deuxiemeElement;
        char temp;
        temp = Pep8.chari();
        premierElement = gestionCaractere(temp);
        temp = Pep8.chari();
        deuxiemeElement = gestionCaractere(temp);
        return (premierElement * deuxiemeElement);
    }
    
    public static int division() {
        int premierElement;
        int deuxiemeElement;
        char temp;
        temp = Pep8.chari();
        premierElement = gestionCaractere(temp);
        temp = Pep8.chari();
        deuxiemeElement = gestionCaractere(temp);
        return (premierElement / deuxiemeElement);
    }
    
    public static int soustraction() {
        int premierElement;
        int deuxiemeElement;
        char temp;
        temp = Pep8.chari();
        premierElement = gestionCaractere(
                
                
        temp = Pep8.chari();
        deuxiemeElement = gestionCaractere(temp);
        return (premierElement - deuxiemeElement);
    }
    
    public static int conversionCharToInt(char caractere)
    {
        return ((int) caractere) - 48;
    }
    public static char conversionIntToChar(int nombre){
        return 
    }
            
    
}
