/**
* Programme Calc
*
* Lit une expression arithmétique en notation préfixe utilisant les quatres
* opérations basiques et une variable x.
* Le programme offre ensuite la possibilité de spécifier une valeur de x, de 
* calculer le résultat de cette expression, et de l'afficher en notation infixe 
*
* Paramètres :
* - L'expression a évaluer, constituée de caractéres
* - Une ou plusieurs commandes :
*       - La commande 'x' suivie d'un nombre qui assigne ce nombre a la valeur de x
*       - La commande '=' qui calcule le résultat de l'expression
*       - La commande '>' qui affiche l'expression en notation infixe
* - La saisie s'arrête lorsque l'utilisateur entre le caractère '.'
* 
* Fonctionnement :
* 
*
*
*
* Programme par Arthur Joliveau-Breney
* JOLA11049104
*/



class Branche {
    public int type; // 0 = nombre, 1 = addition, 2 = soustraction, 3= multiplication, 4 = division, 5 = x
    public int val;  // valeur de l'entier si type = 0
    public Branche filsGauche;
    public Branche filsDroit;
    
    Branche(int type, int val) {
        this.type = type;
        this.val = val;
    }
    
    Branche() {
        
    }
}

public class Calc {
     
    
    public static void main(String[] args) {
        int total;
        int valeurX = 1;
        Branche racine = new Branche(); //reference vers l'arbre au complet
        Branche courant = racine;
        
        constructionArbre(courant); //construit récursivement l'arbre
        
        char caractere = Pep8.chari();
        
        while (caractere != '.') { //boucle dans les commandes de calcul et d'affichage
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
                valeurX = lectureNombre(Pep8.deci());
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
    
    public static void constructionArbre(Branche courant) {
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
            courant.filsGauche = new Branche();
            constructionArbre(courant.filsGauche);
            courant.filsDroit = new Branche();
            constructionArbre(courant.filsDroit);            
        }
        else if (caractere >= '0' && caractere <= '9') {
            courant.type = 0;
            courant.val = lectureNombre(conversionCharToInt(caractere));
        }
        else if (caractere == 'x') {
            courant.type = 5;
        }
        else if (caractere == ' ' || caractere == '\n') {
            constructionArbre(courant);
        }
    }
    
  
    
    public static void afficherInfixe(Branche courant) {
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
    
    public static int calculerTotal(Branche courant, int valeurX) {
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
    
    public static int addition(Branche courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) + calculerTotal(courant.filsDroit, valeurX);
        
    }
    
    public static int multiplication(Branche courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) * calculerTotal(courant.filsDroit, valeurX);
    }
    
    public static int division(Branche courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) / calculerTotal(courant.filsDroit, valeurX);
    }
    
    public static int soustraction(Branche courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) - calculerTotal(courant.filsDroit, valeurX);
    }
    
    public static int conversionCharToInt(char caractere)
    {
        return ((int) caractere) - 48;
    }
       
    
}
