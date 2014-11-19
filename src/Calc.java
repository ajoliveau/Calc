class Arbre {
    public int type; // 0 = nombre, 1 = addition, 2 = soustraction, 3= multiplication, 4 = division, 5 = x
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
        int valeurX = 1;
        Arbre racine = new Arbre();
        Arbre courant = racine;
        constructionBranche(courant);
        
        char caractere = Pep8.chari();
        
        while (caractere != '.') {
            if (caractere == '=') {
                total = calculerTotal(racine,valeurX);
                Pep8.deco(total);
                Pep8.charo('\n');
            }
            else if (caractere == '>') {
                afficherInfixe(racine);
                Pep8.charo('\n');
            }
            else if (caractere == 'x') {
                valeurX = Pep8.deci();
            }
            caractere = Pep8.chari();
        }
        Pep8.charo('.');
        Pep8.charo('\n');
        /*
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
                
        */
        
    }
    
    public static void constructionBranche(Arbre courant) {
        char caractere = Pep8.chari();
        if (caractere == '+' || caractere == '-' ||caractere == '*' ||caractere == '/') {     
            switch(caractere) {
                case '+':
                    courant.type = 1;
                    break;
                case '-':
                    courant.type = 2;
                    break;
                case '*':
                    courant.type = 3;
                    break;
                case '/':
                    courant.type = 4;
                    break;
            }
            courant.filsGauche = new Arbre();
            constructionBranche(courant.filsGauche);
            courant.filsDroit = new Arbre();
            constructionBranche(courant.filsDroit);            
        }
        else if (caractere >= '0' && caractere <= '9') {
            courant.type = 0;
            courant.val = lectureNombre(conversionCharToInt(caractere));
        }
        else if (caractere == 'x') {
            courant.type = 5;
        }
        else if (caractere == ' ' || caractere == '\n') {
            constructionBranche(courant);
        }
    }
    
  
    
    public static void afficherInfixe(Arbre courant) {
        if (courant.type == 0) {
           Pep8.deco(courant.val); 
        }
        else if (courant.type == 5) {
            Pep8.charo('x');
        }
        else {
            Pep8.charo('(');
            afficherInfixe(courant.filsGauche);
            switch(courant.type) {
                case 1:
                    Pep8.charo('+');
                    break;
                case 2:
                    Pep8.charo('-');
                    break;
                case 3:
                    Pep8.charo('*');
                    break;
                case 4:
                    Pep8.charo('/');
                    break;
            }
            afficherInfixe(courant.filsDroit);
            Pep8.charo(')');
        }
        
    }
    
    public static int calculerTotal(Arbre courant, int valeurX) {
        int valeur;
        if (courant.type == 0) {
            valeur = courant.val;
        }
        else if (courant.type == 5) {
            valeur = valeurX;
        }
        else {
            switch(courant.type) {
                case 1:
                    valeur = addition(courant, valeurX);
                    break;
                case 2:
                    valeur = soustraction(courant, valeurX);
                    break;
                case 3:
                    valeur = multiplication(courant, valeurX);
                    break;
                case 4:
                    valeur = division(courant, valeurX);
                    break;
                default:
                    valeur = 0;
            }
        }
        return valeur;
    }
            
    
    public static int lectureNombre(int nombre) {
        char nouveauNombre = Pep8.chari();
        if (nouveauNombre >= '0' && nouveauNombre <= '9') {
            return lectureNombre(nombre*10 + conversionCharToInt(nouveauNombre));
        }
        else {
            return nombre;
        }
 
    }
    
    public static int addition(Arbre courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) + calculerTotal(courant.filsDroit, valeurX);
        
    }
    
    public static int multiplication(Arbre courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) * calculerTotal(courant.filsDroit, valeurX);
    }
    
    public static int division(Arbre courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) / calculerTotal(courant.filsDroit, valeurX);
    }
    
    public static int soustraction(Arbre courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) - calculerTotal(courant.filsDroit, valeurX);
    }
    
    public static int conversionCharToInt(char caractere)
    {
        return ((int) caractere) - 48;
    }
       
    
}
