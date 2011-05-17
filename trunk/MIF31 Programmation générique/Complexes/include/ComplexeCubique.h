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
        virtual ~Iterator(){cellules.clear();}

    /// SETTERS / GETTERS

        TYPE_I* getCellActu(){return cellules[position];}
        TYPE_I* getCellI(const unsigned int _i) {return cellules[_i];}
        unsigned int getTaille()const { return taille;}
        unsigned int getPosition() const {return position;}
        void incPos(){position++;}

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
        typedef ICellule<T_DIMCOMPLEXE-2, T_TYPE, T_DIMENSION> CelluleInfInf;

    /// CONSTRUCTEUR / DESTRUCTEUR

        ComplexeCubique(){}
        virtual ~ComplexeCubique(){ tabCellules.clear(); }

    /// SETTERS / GETTERS

        Iterator< CelluleSelf, T_DIMCOMPLEXE>* getIterator(){return it;}

    /// METHODES

        void ajout(CelluleSelf &_c);
        bool estValide(bool _init);
        void creer0Cell(const Point<T_TYPE, T_DIMENSION> &_p);
        Iterator<CelluleSelf, T_DIMCOMPLEXE>* getIteratorSur(const CelluleSelf* _cell);
        Iterator<CelluleSelf, T_DIMCOMPLEXE>* getIteratorProprioBord(const CelluleInf* _cell);
        bool estDansUnBord(const CelluleInf* _cell1, const CelluleSelf* _cell2);
        void creerICellule(std::vector<CelluleInf*>& _v);
        void detruireICellule(const CelluleInf* _cell);
        void reductionElementaire(const CelluleInfInf* _cell1, const CelluleInf* _cell2);
        void simplificationComplexe();

    private:
        Iterator<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE> * it;
        std::vector<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>*> tabCellules;

};

/// CLASSE COMPLEXE CUBIQUE SPECIALISATION 0 ######################################################################

template <typename T_TYPE, unsigned int T_DIMENSION>
class ComplexeCubique<0, T_TYPE, T_DIMENSION>
{
    public:

        typedef ICellule<0, T_TYPE, T_DIMENSION> CelluleSelf;

    /// CONSTRUCTEUR / DESTRUCTEUR

        ComplexeCubique(){};
        virtual ~ComplexeCubique(){tabCellules.clear(); };

    /// SETTERS / GETTERS

        Iterator<CelluleSelf, 0>* getIterator(){return it;}
        bool estValide(bool _init);

    /// METHODES

        void ajout(CelluleSelf &_c);
        void creer0Cell(const Point<T_TYPE, T_DIMENSION> &_p);

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
//        std::cout << "cellule num " << tabCellules[i]->getNumCellule() << " a "<<  tabCellules[i]->getNbCellulesBord() << " ("<< T_DIMCOMPLEXE - 1<< ") cellules " << std::endl;
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

/// creerIcellule
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::creerICellule(std::vector<ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>*>& _v)
{
    if(_v.size() == 2 * T_DIMCOMPLEXE)
    {
        ICellule<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION> *cell = new ICellule<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>(_v);
        tabCellules.push_back(cell);
        it = new Iterator<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE> (tabCellules);
    }
    else std::cout << "Mauvais nombre de bords en argument. " << std::endl;
}

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::creer0Cell(const Point<T_TYPE, T_DIMENSION> &_p)
{
    ComplexeCubique<0, T_TYPE, T_DIMENSION>::creer0Cell(_p);
}

/// detruireIcellule ( il faut que le ComplexeCubique déclaré soir de dimensions strictement supérieur à celle de la cellule à supprimer
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::detruireICellule(const ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>* _cell)
{
    it = new Iterator<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE> (tabCellules);

    for(unsigned int i = 0; i < it->getTaille(); i++ )
    {
        assert(estDansUnBord( _cell , it->getCellI(i)));
    }

    std::cout << "Supresion sans erreurs (la " <<T_DIMCOMPLEXE-1 << "-cellule n'est pas un bord d'une "<<T_DIMCOMPLEXE <<"-cellule)."<< std::endl;
    delete(_cell);
}

/// réuction élémentaire
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::reductionElementaire(const ICellule<T_DIMCOMPLEXE-2, T_TYPE, T_DIMENSION>* _cell1, const ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>* _cell2)
{

    unsigned int nbBords = 0;
    if(ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::estDansUnBord(_cell1,_cell2)){
        for(unsigned int i = 0; i < ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::getIterator()->getTaille(); i++)
        {
            if(ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::estDansUnBord( _cell1 , ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::getIterator()->getCellI(i))) nbBords++;
        }
    }
    if(nbBords == 1)
    {
        nbBords =0;

        it = new Iterator<ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>, T_DIMCOMPLEXE> (tabCellules);

        for(unsigned int i = 0; i < it->getTaille(); i++)
        {
             if(estDansUnBord( _cell2 , it->getCellI(i))) nbBords++;
        }
        if (nbBords == 0)
        {
            std::cout <<  "Suppression de cell num" <<_cell2->getNumCellule()<< " de dim " << _cell2->getDimension() << std::endl;
            std::cout <<  "Suppression de cell num" <<_cell1->getNumCellule()<< " de dim " << _cell1->getDimension() << std::endl;

            delete(_cell2);
            delete(_cell1);
        }
    }
}


template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::simplificationComplexe()
{
    std::cout << "Simplification du complexe." << std::endl;
    if(T_DIMCOMPLEXE >= 2)
    {
        for (unsigned int j= 0; j < ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::getIterator()->getTaille(); j++)
        {
            for (unsigned int k= 0; k < ComplexeCubique<T_DIMCOMPLEXE-2, T_TYPE, T_DIMENSION>::getIterator()->getTaille(); k++)
            {
                reductionElementaire(ComplexeCubique<T_DIMCOMPLEXE-2, T_TYPE, T_DIMENSION>::getIterator()->getCellI(k),
                                     ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::getIterator()->getCellI(j));
            }
        }

//        ComplexeCubique<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>::simplificationComplexe();
    }


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
        if(validite){}//std::cout << "cellule num " << tabCellules[i]->getNumCellule() << " a un point. " << std::endl;
        else std::cout << "cellule num " << tabCellules[i]->getNumCellule() << " n'a pas de point. " << std::endl;
        retour &= validite;
    }

    std::cout << std::endl;
    return retour;
}


template <typename T_TYPE, unsigned int T_DIMENSION>
void ComplexeCubique<0, T_TYPE, T_DIMENSION>::creer0Cell(const Point<T_TYPE, T_DIMENSION> &_p)
{
    tabCellules.push_back(new ICellule<0, T_TYPE, T_DIMENSION>(_p));
}

#endif // COMPLEXECUBIQUE_H


