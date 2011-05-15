#ifndef COMPLEXECUBIQUE_H
#define COMPLEXECUBIQUE_H

#include <string>
#include <vector>
#include <iostream>

#include "ICellule.h"
#include "Point.h"

/// CLASSE COMPLEXE CUBIQUE SPECIALISATION   ######################################################################

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
class ComplexeCubique : public ComplexeCubique< T_DIMCOMPLEXE - 1, T_TYPE, T_DIMENSION>
{

        public:
        /**
        * @class L'itérateur de la classe ComplexeCubique
        */

        class Iterator
        {
            public:
                Iterator(){}
                Iterator(const unsigned int _position,  ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>* _cellule) :  position(_position), cellule(_cellule){
//                Iterator(const unsigned int _position) : dimension(T_DIMCOMPLEXE), position(_position){

                }

                virtual ~Iterator(){}

//                Iterator get

            private:

                ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>* cellule;
                unsigned int dimension; /** dimension dans laquelle se trouve l'itérateur */
                unsigned int position; /** cellule sur laquelle pointe l'itérateur */
        };

        Iterator begin(const unsigned int _dimension) {
            if(T_DIMCOMPLEXE == _dimension){return Iterator(0,tabCellules[0]);}

            else { return ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::begin(_dimension); }
        }


    /// CONSTRUCTEUR / DESTRUCTEUR

        ComplexeCubique(){std::cout<< "Instanciation complexe cubique de dimension " << T_DIMCOMPLEXE <<  std::endl;}
        virtual ~ComplexeCubique(){ tabCellules.clear(); }

    /// GETTERS / SETTERS

//        std::vector<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>*>* getTab(unsigned int _dimension)
//        {
//            if (T_DIMCOMPLEXE == _dimension){return &tabCellules;}
//            else {return ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::getTab(_dimension);}
//        }


    /// METHODES

        void creer(const unsigned int _dimensionICellule)
        {
            if (T_DIMCOMPLEXE == _dimensionICellule) tabCellules.push_back(new ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>());
            else ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::creer(_dimensionICellule);
        }

        /**
            @brief Test de validité du complexe. Vérifie que chaque i-cellule possède bien 2:i (i-1)cellules dans
            son bord (des pointeurs non NULL).
        */
        bool estValide(bool _init);

        /** @brief création d’une 0-cellule prenant un point en paramètre. */
        void creer0Cell(const Point<T_TYPE, T_DIMENSION> &p);

    protected:

    private:
        std::vector<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>*> tabCellules;

};

/// CLASSE COMPLEXE CUBIQUE SPECIALISATION 0 ######################################################################

template <typename T_TYPE, unsigned int T_DIMENSION>
class ComplexeCubique<0, T_TYPE, T_DIMENSION>
{
    public:

    ComplexeCubique(){};

    void creer(const unsigned int _dimensionICellule){
    tabCellules.push_back(new ICellule<0,T_TYPE,T_DIMENSION>());

    }

    void creer0Cell(const Point<T_TYPE, T_DIMENSION> &p){
        tabCellules.push_back(new ICellule<0, T_TYPE, T_DIMENSION>(p));
    }

    bool estValide(bool _init);

    private:
    std::vector<ICellule<0,T_TYPE,T_DIMENSION>*> tabCellules;
};

/// IMPLEMENTATIONS SPECIALISATION ##################################################################################

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
bool ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::estValide(bool _init = true)
{
    bool retour = _init;

    retour &= ComplexeCubique<T_DIMCOMPLEXE -1, T_TYPE, T_DIMENSION>::estValide(retour);
    std::cout << "Validité complexe dimension: " << T_DIMCOMPLEXE << std::endl;

    for(unsigned int i = 0; i < tabCellules.size(); i++)
    {
        std::cout << "cellule num " << i << " du complexe" << std::endl;
        retour &= tabCellules[i]->estValide(retour);
    }

    std::cout << std::endl;
    return retour;
}

/// IMPLEMENTATIONS SPECIALISATION 0 #################################################################################

template <typename T_TYPE, unsigned int T_DIMENSION>
bool ComplexeCubique<0, T_TYPE, T_DIMENSION>::estValide(bool _init = true)
{
    bool retour = _init;

    std::cout << "Validité complexe dimension: " << 0 << std::endl;

    for(unsigned int i = 0; i < tabCellules.size(); i++)
    {
        std::cout << "cellule num " << i << " du complexe" << std::endl;
        retour &= tabCellules[i]->estValide(retour);
    }

    std::cout << std::endl;
    return retour;
}


template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::creer0Cell(const Point<T_TYPE, T_DIMENSION> &p)
{
    ComplexeCubique<0, T_TYPE, T_DIMENSION>::creer0Cell(p);
}



#endif // COMPLEXECUBIQUE_H




        /**
            @brief Test de validité du complexe. Vérifie que chaque i-cellule possède bien 2:i (i-1)cellules dans
            son bord (des pointeurs non NULL).
        */
//        bool isValideComplexe()
//        {
//            for(unsigned int i = 0; i < DIMCOMPLEXE; i++)
//                for(unsigned int j = 0; j < tabCellules[i].size(); j++)
//                {
//                    ICellule<i, T, DIMPOINT>* tmp = dynamic_cast< ICellule<i, T, DIMPOINT>* >(tabCellules[i][j])
//                    if(!tmp->isValideICellule()) break;
//                }
//
//            return true;
//
//        }


        //--------------------------------------------------------------------------------------->
        //-----------------------------------ITERATOR COMPLEXE-----------------------------------
        //---------------------------------------------------------------------------------------<


//
//        Iterator begin();
//        Iterator end();


        //--------------------------------------------------------------------------------------->
        //---------------------------------------COMPLEXE----------------------------------------
        //---------------------------------------------------------------------------------------<



        /**
            @brief Un ensemble de ICellule pour chaque dimension I :
            liste[0][0] =  ICellule n°0 contenant un point (un sommet)
            liste[1][0] =  ICellule n°0 contenant un segement (en fait elle contiendra deux ICellules <=> deux points)
            etc ..
            La taille initial du "std::vector<...> listeICellules" est DIMCOMPLEXE.
            Si on connait la valeur i de tabCellules[i][j] alors on sait en quoi caster le contenu.
        */
//        std::vector< std::vector<void*> > tabCellules;






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
//        void simplificationDuComplexe();



//        * @class L'itérateur de la classe ComplexeCubique
//        */
//        class Iterator
//        {
//            public:
//                Iterator(){}
//                Iterator(const unsigned int _dimensionActu, const unsigned int _iCellActu) : dimensionActu(_dimensionActu), iCellActu(_iCellActu){}
//
//                virtual ~Iterator(){}
//
//            private:
//
//                unsigned int dimensionActu; /** dimension dans laquelle se trouve l'itérateur */
//                unsigned int iCellActu; /** cellule sur laquelle pointe l'itérateur */
//        };


