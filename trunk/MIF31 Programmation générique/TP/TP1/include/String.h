#ifndef STRING_H
#define STRING_H

#include <iostream>

#ifndef FINCHAINE
#define FINCHAINE '\0'
#endif

/**
* @class Classe String, gère les chaines de caractères
*/
class String
{
    public:

        String();
        String(const char* _chaine);
        String(char _chaine);
        virtual ~String();

        String operator+(const String& _chaine) const;
        bool operator==(const String& _chaine) const;
        bool operator!=(const String& _chaine) const;
        bool operator>(const String& _chaine) const;
        bool operator<(const String& _chaine) const;
        bool operator>=(const String& _chaine) const;
        bool operator<=(const String& _chaine) const;
        char operator[](const unsigned int cellule) const;
        String& operator=(const String& unString);
        String& operator=(const char* _chaine);


        /** @brief Exemple : bonjour -> sousChaine(2,5) -> onjo => [2;5] */
        char* sousChaine(const unsigned int debut, const unsigned int fin) const;

        /** @brief crée une copie de l'attribut chaine de type char* */
        char* convChar() const;

        /** @return retourne un String ne contenant aucune occurence de _chaine */
        String& enleverChaine(const char* _chaine) const;


        static unsigned int getCompteur();

        unsigned int getTaille() const;
        bool isVide() const;

        /** @brief affiche le contenu de la chaine dans le flux de sortie passé en paramètre */
        void afficher(std::ostream& sortie) const;

    private:

        static unsigned int compteur; /** compte le nombre d'instance de la classe String */
        unsigned int taille; /** taille de la chaine sans le caractère de fin */
        char* chaine;

        static unsigned int getTaille(const char* _chaine);

         /** @param plusUn si true alors on ajoute 1 sinon on enlève 1 */
        static unsigned int compteurInstance(const bool plusUn);


};


#endif // STRING_H
