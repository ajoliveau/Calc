/**
* Programme Calc
*
* Lit une expression arithmétique en notation préfixe utilisant les quatres
* opérations basiques et une variable x.
* Le programme offre ensuite la possibilité de spécifier une valeur de x, de 
* calculer le résultat de cette expression, et de l'afficher en notation infixe 
*
* Paramètres :
* 
* - L'expression a évaluer, constituée de caractéres
* - Une ou plusieurs commandes :
*       - La commande 'x' suivie d'un nombre qui assigne ce nombre a la valeur de x
*       - La commande '=' qui calcule le résultat de l'expression
*       - La commande '>' qui affiche l'expression en notation infixe
* - La saisie s'arrête lorsque l'utilisateur entre le caractère '.'
* 
* Fonctionnement :
* 
* L'expression à évaluer est lue caractère par caractère. Le programme fait la différence
* entre les opérateurs (+, - , * , / et x) et les nombres.
* Si le caractère est un chiffre, il lit tous les caractères suivant jusqu'a 
* tomber sur autre chose qu'un chiffre, et reconstitue le nombre complet.
* 
* L'expression est stockée dans un arbre. L'arbre est une succession de branches
* qui contiennent chacune un opérateur ou un nombre. Si la branche contient un 
* opérateur de calcul, elle possède également un  lien vers deux autres branches
* qui représentent les paramètres de cet opérateur.
* 
* Une fois l'expression stockée dans l'arbre, le programme reconnait plusieurs
* commandes qui vont parcourir récursivement toutes les branches de l'arbre pour
* effectuer les opérations demandées.
* 
* Limites :
* 
* - Le programme ne prend en charge que les nombres entiers. L'opérateur division
* est donc une division entière
* - Le programme ne gère pas toutes les erreurs utilisateurs possibles lors de
* l'entrée de l'expression
*
* 
* Autoévaluation :
* 
* Le programme passe tous les tests
*
*
*
* Programme par Arthur Joliveau-Breney
* JOLA11049104
*/


/*
* Structure de donnée permettant de stocker l'expression
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
        int valeurX = 1; //valeur par défaut affectée a x s'il n'est pas défini
        
        Branche racine = new Branche(); //reference vers l'arbre au complet
        Branche courant = racine;
        
        constructionArbre(courant); //construit récursivement l'arbre
        
        char caractere = Pep8.chari(); 
        
        while (caractere != '.') { //boucle dans les commandes de calcul et d'affichage
            if (caractere == '=') {
                int total = calculerTotal(racine,valeurX);
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
    }
    
    /**
     * 
     * Construit la branche passée en paramètre  :
     *      - Si le caractère est un chiffre, lis le nombre en entier et le 
     * stocke dans la branche.
     *      - Si le caractère est un 'x', le stocke dans la branche.
     *      - Si le caractère est un opérateur, le stocke dans la branche, puis
     * construis les branches inférieures gauches, puis droites.
     *      - Si le caractère est un espace ou un retour a la ligne, ignore
     * le caractère
     * 
     * Paramètre : 
     *      - Branche courant : branche de l'arbre à traiter
     * 
     * Effet de bord : 
     *      Aucun
     * 
     */
    
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
            courant.filsGauche = new Branche(); // Un opérateur a forcément deux
            constructionArbre(courant.filsGauche);// opérandes. Il faut construire
            courant.filsDroit = new Branche(); // une branche supplémentaire de
            constructionArbre(courant.filsDroit);// l'arbre pour chacun.            
        }
        else if (caractere >= '0' && caractere <= '9') { 
            courant.type = 0;
            courant.val = lectureNombre(conversionCharToInt(caractere));
            // si le caractère est un chiffre, il faut vérifier si c'est un nombre
            
        }
        else if (caractere == 'x') {
            courant.type = 5;
        }
        else if (caractere == ' ' || caractere == '\n') {
            constructionArbre(courant); // on saute le caractère
        }
        else {
            Pep8.stro("L'expression est incorrecte\n");
            Pep8.stop();
        }
    }
    
  
    /**
     * 
     * Affiche l'expression contenue dans la branche et ses filles en 
     * notation infixe :
     *      - Si le noeud est un chiffre, écrit sa valeur
     *      - Si le noeud est un 'x', écrit le caractère.
     *      - Si le noeud est un opérateur, mets entre parenthèses le contenu
     * de l'expression dans les branches inférieures séparé par l'opérateur
     *      
     * 
     * Paramètre : 
     *      - Branche courant : branche de l'arbre à traiter
     * 
     * Effet de bord : 
     *      Ecrit dans la console
     * 
     */
    
    public static void afficherInfixe(Branche courant) {
        if (courant.type == 0) {
           Pep8.deco(courant.val); 
        }
        else if (courant.type == 5) {
            Pep8.charo('x');
        }
        else { // si la branche représente un opérateur, on affiche sous la forme
               // "(valGauche[opérateur]valDroite)"
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
    
    /**
     * 
     * Calcule la valeur de l'expression contenue dans la branche courante
     * et les branches inférieures :
     *      - Si le noeud est un chiffre, renvoie sa valeur
     *      - Si le noeud est un 'x', écrit la valeur enregistrée de x.
     *      - Si le noeud est un opérateur, renvoie le résultat de l'opération
     * entre le total dans la branche droite et le total dans la branche gauche
     *      
     * 
     * Paramètre : 
     *      - Branche courant : branche de l'arbre à traiter
     *      - int valeurX : valeur enregistrée pour x
     * 
     * Retour :
     *      - int total : valeur de l'expression évaluée de la branche
     * 
     * Effet de bord : 
     *      Aucun
     * 
     */
    
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
            
    /**
     * 
     * Lis l'entrée pour reconstituer un nombre a partir des 
     * caractères individuels.
     *      
     * 
     * Paramètre : 
     *      - int nombre : le nombre deja lu et traité
     * 
     * Retour :
     *      - int nombre : la valeur des nombres lus et traités
     * 
     * Effet de bord : 
     *      Aucun
     * 
     */
    
    public static int lectureNombre(int nombre) {
        char nouveauNombre = Pep8.chari();
        if (nouveauNombre >= '0' && nouveauNombre <= '9') {
            return lectureNombre(nombre*10 + conversionCharToInt(nouveauNombre));
        } // car 195 = (1*10+9)*10 + 5
        else {
            return nombre;
        }
 
    }
    
    /**
     * 
     * Additionne les valeurs évaluées de l'expression dans la branche gauche
     * et la branche droite de la branche courante.
     * 
     * Paramètre : 
     *      - Branche courant : branche de l'arbre à traiter
     *      - int valeurX : valeur enregistrée pour x
     * 
     * Retour :
     *      - int nombre : la somme des branches gauches et droites
     * 
     * Effet de bord : 
     *      Aucun
     * 
     */
    
    public static int addition(Branche courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) + calculerTotal(courant.filsDroit, valeurX);
        
    }
    
    /**
     * 
     * Multiplie les valeurs évaluées de l'expression dans la branche gauche
     * et la branche droite de la branche courante.
     * 
     * Paramètre : 
     *      - Branche courant : branche de l'arbre à traiter
     *      - int valeurX : valeur enregistrée pour x
     * 
     * Retour :
     *      - int nombre : le produit des branches gauches et droites
     * 
     * Effet de bord : 
     *      Aucun
     * 
     */
    
    public static int multiplication(Branche courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) * calculerTotal(courant.filsDroit, valeurX);
    }
    
    /**
     * 
     * Divise les valeurs évaluées de l'expression dans la branche gauche
     * et la branche droite de la branche courante.
     * 
     * Paramètre : 
     *      - Branche courant : branche de l'arbre à traiter
     *      - int valeurX : valeur enregistrée pour x
     * 
     * Retour :
     *      - int nombre : le quotient des branches gauches et droites
     * 
     * Effet de bord : 
     *      Aucun
     * 
     */
    
    public static int division(Branche courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) / calculerTotal(courant.filsDroit, valeurX);
    }
    
    /**
     * 
     * Soustrais les valeurs évaluées de l'expression dans la branche gauche
     * et la branche droite de la branche courante.
     * 
     * Paramètre : 
     *      - Branche courant : branche de l'arbre à traiter
     *      - int valeurX : valeur enregistrée pour x
     * 
     * Retour :
     *      - int nombre : la différence des branches gauches et droites
     * 
     * Effet de bord : 
     *      Aucun
     * 
     */
    
    public static int soustraction(Branche courant, int valeurX) {
        return calculerTotal(courant.filsGauche, valeurX) - calculerTotal(courant.filsDroit, valeurX);
    }
    
    /**
     * 
     * Convertit un caractère en chiffre
     * 
     * Paramètre : 
     *      - char caractere : caractere a convertir
     * 
     * Sortie :
     *      - int nombre    : caractere convertit
     * 
     * Effets de bord : 
     *      Aucun 

     */
    
    public static int conversionCharToInt(char caractere)
    {
        return ((int) caractere) - 48; // le code ASCII de '0' est 48
    }
       
    
}
