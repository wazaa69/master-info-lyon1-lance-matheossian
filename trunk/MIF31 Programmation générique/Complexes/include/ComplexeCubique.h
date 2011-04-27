#ifndef COMPLEXECUBIQUE_H
#define COMPLEXECUBIQUE_H

#include <string>
#include <vector>

#include "ICellule.h"

template <unsigned int DIMCOMPLEXE, unsigned int DIMPOINT, class T>
class ComplexeCubique
{
    public:
        ComplexeCubique();
        virtual ~ComplexeCubique();

    protected:

    private:

        /**
            @brief Un ensemble de ICellule pour chaque dimenssion I :
            liste[0][0] =  ICellule n°0 contenant un point (un sommet)
            liste[1][0] =  ICellule n°0 contenant un segement (en fait elle contiendra deux ICellules <=> deux points)
            etc ..
        */

        //fonctionne si on met ICellule<5>* mais on veut pas le spécialiser nous !!! Damn c tard, verrai sa demain
        std::vector< std::vector<ICellule*> > listeICellules; // <----------- Regader dans la STL si ya pas un autre truc que ca

        /**
            @brief Test de validité du complexe. Vérifie que chaque i-cellule possède bien 2:i (i􀀀1)-cellules dans
            son bord (des pointeurs non NULL).
        */
        bool isValideComplexe();

        /**
            @brief Recherche d’une i-cellule donnée par son pointeur dans la structure de donnée STL. Retourne
            un itérateur STL vers cette cellule si elle est trouvée, un itérateur sur la fin de la structure
            sinon.
        */
        //Iterator getIterOnICellule(const ICellule* cellule);

        /**
            @brief Recherche d’une (i + 1)-cellule ayant une i-cellule donnée c dans son bord. Retourne un
            itérateur STL vers cette cellule si elle est trouvée, un itérateur sur la fin de la structure
            sinon.
        */
        //


        /** @brief Méthode de test si une i-cellule c1 appartient au bord d’une (i + 1)-cellule c2 */
        //

        /**
            @brief création d’une i-cellule attaché à son bord (pour 1 < i < n). Cette méthode prendra en
            paramètre 2*i pointeurs vers des (i-1)-cellules existantes, et affectera le bord de la nouvelle
            i-cellule.
        */
        //

        /** @brief création d’une 0-cellule prenant un point en paramètre. */
        //


        /**
            @brief destruction d’une i-cellule donnée c. Afin de garantir la validité du complexe, il ne doit pas
            exister de (i + 1)-cellules ayant c dans leur bord. Vous effectuerez ce test mais uniquement
            en mode debug (au moyen d’assert) pour des raisons de complexité.
        */
        //

        /**
            @brief opération de réduction élémentaire : cette méthode prend deux cellules en paramètres, c1
            une i-cellule et c2 une (i + 1)-cellule, tel que :
                – c1 soit dans le bord de c2,
                – il n’existe pas de (i + 1)-cellule (à part c2) ayant c1 dans son bord,
                – il n’existe pas de (i + 2)-cellule ayant c2 dans son bord.

            Dans ces conditions, l’opération de réduction élémentaire consiste simplement à supprimer
            les deux cellules c1 et c2 du complexe. Comme pour la question précédente, vous vérifierez
            ces conditions en mode debug au moyen d’assert.
        */

        /** @brief Chargement des données d’un complexe cubique à partir d’un fichier */
        void chargerDonnees(std::string cheminFichier);

        /** @brief Sauvegarder les données d'un complexe cubique dans un fichier */
        void sauvegarderDonnees(std::string cheminFichier);

        /**
            @brief Simplifier un complex cubique :
            L’idée est d’utiliser l’opération de réduction élémentaire autant que possible.
            Pour cela, l’algorithme travaille en dimension décroissante. Il commence par chercher
            tout couple de cellules pouvant être réduit de dimension n - 1 et n et appelle l’opération
            de réduction élémentaire lorsque c’est possible. Ensuite, il cherche tout couple de cellules
            pouvant être réduit de dimension n - 2 et n - 1 et appelle l’opération de réduction élémentaire
            lorsque c’est possible. Et ainsi de suite jusqu’aux dimensions 0 et 1. Dans le complexe résultat,
            il ne doit normalement plus exister deux cellules pouvant être réduite (quelque soit le couple de
            dimensions (i, i+1)), et le complexe doit être valide. Vous vérifierez ces propriétés au moyen d’un assert.
        */
        void simplificationDuComplexe();


};

#endif // COMPLEXECUBIQUE_H
