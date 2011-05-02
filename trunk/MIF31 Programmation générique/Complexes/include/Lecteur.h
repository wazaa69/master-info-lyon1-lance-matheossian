#ifndef LECTEUR_H
#define LECTEUR_H

#include <string>

class Lecteur
{
    public:
        Lecteur();
        virtual ~Lecteur();

        /** @brief Chargement des données d’un complexe cubique à partir d’un fichier */
        static void chargerDonnees(std::string cheminFichier);

        /** @brief Sauvegarder les données d'un complexe cubique dans un fichier */
        static void sauvegarderDonnees(std::string cheminFichier);

};

#endif // LECTEUR_H
