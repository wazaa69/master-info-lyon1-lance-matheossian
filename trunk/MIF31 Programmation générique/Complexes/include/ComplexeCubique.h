#ifndef COMPLEXECUBIQUE_H
#define COMPLEXECUBIQUE_H

#include <string>
#include <vector>
#include <iostream>

#include "ICellule.h"
#include "Point.h"




/// DECLARATION CLASSE ITERATOR  ##################################################################################
template <typename TYPE_I, unsigned int DIMENSION>
class Iterator
{
    public:

    /// CONSTRUCTEUR / DESTRUCTEUR

        Iterator(std::vector<TYPE_I*>  &_v) : cellules(_v), position(0), taille(_v.size()) {}
        Iterator(std::vector<TYPE_I*>  &_v, const unsigned int _position) : cellules(_v), position(_position), taille(_v.size()) {}
        virtual ~Iterator(){}

    /// SETTERS / GETTERS

        TYPE_I* getCellActu(){return cellules[position];}
        TYPE_I* getCellI(const unsigned int _i) {return cellules[_i];}
        unsigned int getTaille()const { return taille;}
        unsigned int getPosition() const {return position;}

    private:

        std::vector<TYPE_I*> cellules;
        unsigned int position;
        unsigned int taille;
};

/// CLASSE COMPLEXE CUBIQUE #######################################################################################
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
class ComplexeCubique : public ComplexeCubique< T_DIMCOMPLEXE - 1, T_TYPE, T_DIMENSION>
{

    public:

        typedef ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION> CelluleSelf;
        typedef ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION> CelluleInf;

    /// CONSTRUCTEUR / DESTRUCTEUR

        ComplexeCubique(){}
        virtual ~ComplexeCubique(){ tabCellules.clear(); }

    /// SETTERS / GETTERS

        Iterator< CelluleSelf, T_DIMCOMPLEXE>* getIterator(){return it;}

    /// METHODES

        void ajout(CelluleSelf &_c);
        bool estValide(bool _init);
        void creer0Cell(const Point<T_TYPE, T_DIMENSION> &p);
        Iterator<CelluleSelf, T_DIMCOMPLEXE>* getIteratorSur(const CelluleSelf* _cell);
        Iterator<CelluleSelf, T_DIMCOMPLEXE>* getIteratorProprioBord(const CelluleInf* _cell);
        bool estDansUnBord(const CelluleInf* _cell1, const CelluleSelf* _cell2);

    private:
        std::vector<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>*> tabCellules;
        Iterator<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE> * it;

};

/// CLASSE COMPLEXE CUBIQUE SPECIALISATION 0 ######################################################################

template <typename T_TYPE, unsigned int T_DIMENSION>
class ComplexeCubique<0, T_TYPE, T_DIMENSION>
{
    public:

        typedef ICellule<0, T_TYPE, T_DIMENSION> CelluleSelf;

    /// CONSTRUCTEUR / DESTRUCTEUR

        ComplexeCubique(){};

    /// SETTERS / GETTERS

        Iterator<CelluleSelf, 0>* getIterator(){return it;}
        bool estValide(bool _init);

    /// METHODES

        void ajout(CelluleSelf &_c);
        void creer0Cell(const Point<T_TYPE, T_DIMENSION> &p){
            tabCellules.push_back(new ICellule<0, T_TYPE, T_DIMENSION>(p));
        }

    private:

        std::vector<ICellule<0,T_TYPE,T_DIMENSION>*> tabCellules;
        Iterator<ICellule<0, T_TYPE, T_DIMENSION>, 0> * it;
};

/// IMPLEMENTATIONS #################################################################################################

/// AJOUT
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::ajout(CelluleSelf &_c)
{
    tabCellules.push_back(&_c);
    it = new Iterator<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE> (tabCellules);
}

/// VALIDITE
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
bool ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::estValide(bool _init = true)
{
    bool retour = _init;
    retour &= ComplexeCubique<T_DIMCOMPLEXE -1, T_TYPE, T_DIMENSION>::estValide(retour);

    for(unsigned int i = 0; i < tabCellules.size(); i++)
    {
        std::cout << "cellule num " << tabCellules[i]->getNumCellule() << " a "<<  tabCellules[i]->getNbCellulesBord() << " ("<< T_DIMCOMPLEXE - 1<< ") cellules " << std::endl;
        if( tabCellules[i]->getNbCellulesBord() == 2* T_DIMCOMPLEXE){retour&= true;}
        else retour = false;
    }

    std::cout << std::endl;
    return retour;
}

/// getIterator sur
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
Iterator<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE>* ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::getIteratorSur(const ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>* _cell)
{
    for (unsigned int i = 0; i< tabCellules.size(); i++)
    {
        if (tabCellules[i]->getNumCellule() == _cell->getNumCellule())
        {
            std::cout << "Cellule trouvee. " << std::endl;
            return new Iterator< ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE>(tabCellules,i);
        }
    }
    std::cout << "Cellule non trouvee. " << std::endl;
    return new Iterator< ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE>(tabCellules,tabCellules.size());
}

/// getIteratorProprioBord
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
Iterator<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE>* ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::getIteratorProprioBord(const ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>* _cell)
{
    for (unsigned int i = 0; i < tabCellules.size(); i++)
    {
        for (unsigned int j = 0; j < tabCellules[i]->getBords()->size(); j++)
        {
            if(tabCellules[i]->getBords()->at(j)->getNumCellule() == _cell->getNumCellule())
            {
                std::cout << "Cellule trouvee. " << std::endl;
                return new Iterator< ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE>(tabCellules,j);
            }
        }
    }
    std::cout << "Cellule (i+1) non trouvee. " << std::endl;
    return new Iterator< ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE>(tabCellules,tabCellules.size());
}

/// estDansUnBord
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
bool ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::estDansUnBord(const ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>* _cell1, const ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>* _cell2)
{
    for (unsigned int j = 0; j < _cell2->getBords()->size(); j++)
    {
        if(_cell2->getBords()->at(j)->getNumCellule() == _cell1->getNumCellule()) return true;
    }
    return false;
}

/// IMPLEMENTATIONS SPECIALISATION 0 #################################################################################

template <typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<0, T_TYPE, T_DIMENSION>::ajout(ICellule<0, T_TYPE, T_DIMENSION> &_c)
{
    tabCellules.push_back(&_c);
    it = new Iterator<ICellule<0, T_TYPE, T_DIMENSION>, 0> (tabCellules);
}

template <typename T_TYPE, unsigned int T_DIMENSION>
bool ComplexeCubique<0, T_TYPE, T_DIMENSION>::estValide(bool _init = true)
{
    bool retour = _init;
    bool validite;

    for(unsigned int i = 0; i < tabCellules.size(); i++)
    {
        validite = tabCellules[i]->estInitPoint();
        if(validite)std::cout << "cellule num " << tabCellules[i]->getNumCellule() << " a un point. " << std::endl;
        else std::cout << "cellule num " << tabCellules[i]->getNumCellule() << " n'a pas point. " << std::endl;
        retour &= validite;
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


