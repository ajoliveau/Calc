/**
* Programme Calc
*
* Lit une expression arithm�tique en notation pr�fixe utilisant les quatres
* op�rations basiques et une variable x.
* Le programme offre ensuite la possibilit� de sp�cifier une valeur de x, de 
* calculer le r�sultat de cette expression, et de l'afficher en notation infixe 
*
* Param�tres :
* 
* - L'expression a �valuer, constitu�e de caract�res
* - Une ou plusieurs commandes :
*       - La commande 'x' suivie d'un nombre qui assigne ce nombre a la valeur de x
*       - La commande '=' qui calcule le r�sultat de l'expression
*       - La commande '>' qui affiche l'expression en notation infixe
* - La saisie s'arr�te lorsque l'utilisateur entre le caract�re '.'
* 
* Fonctionnement :
* 
* L'expression � �valuer est lue caract�re par caract�re. Le programme fait la diff�rence
* entre les op�rateurs (+, - , * , / et x) et les nombres.
* Si le caract�re est un chiffre, il lit tous les caract�res suivant jusqu'a 
* tomber sur autre chose qu'un chiffre, et reconstitue le nombre complet.
* 
* L'expression est stock�e dans un arbre. L'arbre est une succession de branches
* qui contiennent chacune un op�rateur ou un nombre. Si la branche contient un 
* op�rateur de calcul, elle poss�de �galement un  lien vers deux autres branches
* qui repr�sentent les param�tres de cet op�rateur.
* 
* Une fois l'expression stock�e dans l'arbre, le programme reconnait plusieurs
* commandes qui vont parcourir r�cursivement toutes les branches de l'arbre pour
* effectuer les op�rations demand�es.
* 
* Limites :
* 
* - Le programme ne prend en charge que les nombres entiers. L'op�rateur division
* est donc une division enti�re
* - Le programme ne g�re pas toutes les erreurs utilisateurs possibles lors de
* l'entr�e de l'expression
*
* 
* Auto�valuation :
* 
* Le programme passe tous les tests
*
*
*
* Programme par Arthur Joliveau-Breney
* JOLA11049104
*/


/*
* Structure de donn�e permettant de stocker l'expression
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
        int valeurX = 1; //valeur par d�faut affect�e a x s'il n'est pas d�fini
        
        Branche racine = new Branche(); //reference vers l'arbre au complet
        Branche courant = racine;
        
        constructionArbre(courant); //construit r�cursivement l'arbre
        
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
     * Construit la branche pass�e en param�tre  :
     *      - Si le caract�re est un chiffre, lis le nombre en entier et le 
     * stocke dans la branche.
     *      - Si le caract�re est un 'x', le stocke dans la branche.
     *      - Si le caract�re est un op�rateur, le stocke dans la branche, puis
     * construis les branches inf�rieures gauches, puis droites.
     *      - Si le caract�re est un espace ou un retour a la ligne, ignore
     * le caract�re
     * 
     * Param�tre : 
     *      - Branche courant : branche de l'arbre � traiter
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
            courant.filsGauche = new Branche(); // Un op�rateur a forc�ment deux
            constructionArbre(courant.filsGauche);// op�randes. Il faut construire
            courant.filsDroit = new Branche(); // une branche suppl�mentaire de
            constructionArbre(courant.filsDroit);// l'arbre pour chacun.            
        }
        else if (caractere >= '0' && caractere <= '9') { 
            courant.type = 0;
            courant.val = lectureNombre(conversionCharToInt(caractere));
            // si le caract�re est un chiffre, il faut v�rifier si c'est un nombre
            
        }
        else if (caractere == 'x') {
            courant.type = 5;
        }
        else if (caractere == ' ' || caractere == '\n') {
            constructionArbre(courant); // on saute le caract�re
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
     *      - Si le noeud est un chiffre, �crit sa valeur
     *      - Si le noeud est un 'x', �crit le caract�re.
     *      - Si le noeud est un op�rateur, mets entre parenth�ses le contenu
     * de l'expression dans les branches inf�rieures s�par� par l'op�rateur
     *      
     * 
     * Param�tre : 
     *      - Branche courant : branche de l'arbre � traiter
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
        else { // si la branche repr�sente un op�rateur, on affiche sous la forme
               // "(valGauche[op�rateur]valDroite)"
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
     * et les branches inf�rieures :
     *      - Si le noeud est un chiffre, renvoie sa valeur
     *      - Si le noeud est un 'x', �crit la valeur enregistr�e de x.
     *      - Si le noeud est un op�rateur, renvoie le r�sultat de l'op�ration
     * entre le total dans la branche droite et le total dans la branche gauche
     *      
     * 
     * Param�tre : 
     *      - Branche courant : branche de l'arbre � traiter
     *      - int valeurX : valeur enregistr�e pour x
     * 
     * Retour :
     *      - int total : valeur de l'expression �valu�e de la branche
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
     * Lis l'entr�e pour reconstituer un nombre a partir des 
     * caract�res individuels.
     *      
     * 
     * Param�tre : 
     *      - int nombre : le nombre deja lu et trait�
     * 
     * Retour :
     *      - int nombre : la valeur des nombres lus et trait�s
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
     * Additionne les valeurs �valu�es de l'expression dans la branche gauche
     * et la branche droite de la branche courante.
     * 
     * Param�tre : 
     *      - Branche courant : branche de l'arbre � traiter
     *      - int valeurX : valeur enregistr�e pour x
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
     * Multiplie les valeurs �valu�es de l'expression dans la branche gauche
     * et la branche droite de la branche courante.
     * 
     * Param�tre : 
     *      - Branche courant : branche de l'arbre � traiter
     *      - int valeurX : valeur enregistr�e pour x
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
     * Divise les valeurs �valu�es de l'expression dans la branche gauche
     * et la branche droite de la branche courante.
     * 
     * Param�tre : 
     *      - Branche courant : branche de l'arbre � traiter
     *      - int valeurX : valeur enregistr�e pour x
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
     * Soustrais les valeurs �valu�es de l'expression dans la branche gauche
     * et la branche droite de la branche courante.
     * 
     * Param�tre : 
     *      - Branche courant : branche de l'arbre � traiter
     *      - int valeurX : valeur enregistr�e pour x
     * 
     * Retour :
     *      - int nombre : la diff�rence des branches gauches et droites
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
     * Convertit un caract�re en chiffre
     * 
     * Param�tre : 
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
