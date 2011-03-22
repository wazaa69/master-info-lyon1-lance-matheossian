#include "../include/String.h"

unsigned int String::compteur = 0; //initialisation du compteur


/* <------------------------------
          CONSTRUCTEURS
---------------------------------> */

String::String() : taille(0), chaine(new char[1]) {
    compteurInstance(true);
    chaine[0]=FINCHAINE;
}

String::String(const char* _chaine) : taille(0) {

    compteurInstance(true);

    taille = getTaille(_chaine);

    chaine = new char[taille + 1];

    for (unsigned int i = 0 ; i < taille + 1; i++) chaine[i] = _chaine[i];

}

String::String(char _chaine) : taille(1) {

    compteurInstance(true);

    chaine = new char[2];
    chaine[0] = _chaine;
    chaine[1] = FINCHAINE;
}

String::~String(){
    compteurInstance(false);
    delete[] chaine;
}




/* <------------------------------
          OPERATEURS
---------------------------------> */

String String::operator+(const String& _chaine) const{

    int nouvTaille = taille + _chaine.taille;

    char* nouvChaine = new char[nouvTaille + 1];

    for (unsigned int i = 0; i < taille; i++)
        nouvChaine[i] = chaine[i];

    for (unsigned int i = 0; i < _chaine.taille; i++)
        nouvChaine[taille + i] = _chaine.chaine[i];

    nouvChaine[nouvTaille] = FINCHAINE;

    String retChaine(nouvChaine);

    return retChaine;
}

bool String::operator==(const String& _chaine) const{

    if(_chaine.taille != taille) return false;

    for(unsigned int i = 0; i < taille; i++)
        if(_chaine.chaine[i] != chaine[i]) return false;

    return true;
}

bool String::operator!=(const String& _chaine) const{

    if(_chaine.taille != taille) return true;

    for(unsigned int i = 0; i < taille; i++)
        if(_chaine.chaine[i] != chaine[i]) return true;

    return false;
}

bool String::operator<(const String& _chaine) const{

    if(taille < _chaine.taille) return true;
    else return false;

    return false;
}

bool String::operator>(const String& _chaine) const{

    if(taille > _chaine.taille) return true;
    else return false;

    return false;
}

bool String::operator<=(const String& _chaine) const{

    if(taille <= _chaine.taille) return true;
    else return false;

    return false;
}

bool String::operator>=(const String& _chaine) const{

    if(taille >= _chaine.taille) return true;
    else return false;

    return false;
}

char String::operator[](const unsigned int cellule) const{
    if ((cellule-1) <= taille && !isVide() && (cellule > 0))
        return chaine[cellule-1];
    else
        return ' ';
}


String& String::operator=(const String& unString) {
    taille = unString.taille + 1;
    delete[] chaine;
    chaine = new char[taille];
    for(unsigned int i = 0; i < taille; i++) chaine[i] = unString[i];
    return *this;
}


String& String::operator=(const char* _chaine){
    taille = getTaille(_chaine);
    delete[] chaine;
    chaine = new char[taille + 1];
    for(unsigned int i = 0; i < taille; i++) {chaine[i] = _chaine[i];}
    chaine[taille] = FINCHAINE;
    return *this;
}





/* <------------------------------
        AUTRES METHODES
---------------------------------> */

char* String::sousChaine(const unsigned int debut, const unsigned int fin) const {

    char* nouvelleChaine;

    if (debut < 0 || debut > fin || fin < 0 || fin > taille ){return NULL;}

    const unsigned int nbOfLetters = fin + 1 - debut;

    nouvelleChaine = new char[nbOfLetters + 1];

    for (unsigned int i = 0; i < nbOfLetters; i++)
        nouvelleChaine[i] = chaine[debut + i];

    nouvelleChaine[nbOfLetters] = FINCHAINE;

    return nouvelleChaine;
}


char* String::convChar() const {

    char* retChar = new char[taille + 1];

    for(unsigned int i = 0; i <taille; i++)
        retChar[i] = chaine[i];

    retChar[taille] = FINCHAINE;

    return retChar;
}

//on peut aussi utiliser sousChaine() et l'opérateur + pour créer cette méthode
String& String::enleverChaine(const char* _chaine) const{

    String* tmp = new String("Vide");
    String& chaineRetour = *tmp;

    unsigned int tailleChaine = getTaille(_chaine);

    if (isVide() || tailleChaine > taille || tailleChaine == 0) return chaineRetour;

    int* posDepart = new int[taille]; //pour stocker les positions de départ des chaines à retirer
    for(unsigned int i = 0; i < taille; i++) posDepart[i] = -1;

    unsigned int nbRencontres = 0;
    bool sousChaine;

    for (unsigned int i = 0; i < taille; i++)
    {
        sousChaine = true;

        //on vérifie si les char sont =
        for(unsigned int j = 0; j < tailleChaine; j++)
        {
            if(chaine[i+j] != _chaine[j] || chaine[i+j] == FINCHAINE)
            {
                sousChaine = false;
                posDepart[i] = -1;
                break;
            }
        }

        if(sousChaine == true)
        {
            posDepart[i] = i;
            nbRencontres++;
            i = i + tailleChaine - 1;
        }
    }

    unsigned int nouvTaille = (taille - nbRencontres*tailleChaine);
    char* nouvChaine = new char[nouvTaille + 1];

    int ecrire = 0;
    for(unsigned int i = 0; i < taille; i++)
    {

        if(posDepart[i] >= 0) i += (tailleChaine - 1);
        else
        {
            nouvChaine[ecrire] = chaine[i];
            ecrire++;
        }
    }

    nouvChaine[nouvTaille] = FINCHAINE;

    chaineRetour = nouvChaine;

    delete[] nouvChaine;
    delete[] posDepart;

    return chaineRetour;
}





/* <------------------------------
        METHODES COURTES
---------------------------------> */

unsigned int String::compteurInstance(const bool plusUn){return plusUn?compteur++:compteur--;}
unsigned int String::getCompteur() {return compteur;}

bool String::isVide() const {return taille == 0?true:false;}

unsigned int String::getTaille() const {return taille;}
unsigned int String::getTaille(const char* _chaine){
    unsigned int i;
    for(i = 0; _chaine[i] != FINCHAINE; i++);
    return i;
}

void String::afficher(std::ostream& sortie) const{sortie << chaine;}
