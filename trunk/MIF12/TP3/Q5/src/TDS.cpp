#include <iostream>

#include "TDS.hpp"

#include "Variable.hpp"
#include "Fonction.hpp"
#include "Procedure.hpp"
#include "Etiquette.hpp"
#include "Programme.hpp"
#include "Temporaire.hpp"
#include "Constante.hpp"
#include "Argument.hpp"
#include "TypeUser.hpp"

#include "TypeArray.hpp"


using namespace std;

//############################################################### CONSTRUCTEURS/DESTRUCTEURS

TableDesSymboles::TableDesSymboles()
{	
	numeroContexteTS = 0;
	portee = string("globale");
	
}

TableDesSymboles::~TableDesSymboles(){}




TableDesSymboles::TableDesSymboles(int numContexte)
{
	numeroContexteTS = numContexte;
	portee = string("locale");
	
}

//############################################################### ACCESSEURS



int TableDesSymboles::getNumIdActuel(bool incrementation)
{	
	static int numeroId = 0;
	if(incrementation)numeroId++;
	return numeroId;
}

int TableDesSymboles::getNumContexteTSActuel(bool incrementation)
{	
	static int numeroContexte = 0;
	if(incrementation)numeroContexte++;
	return numeroContexte;
}



int TableDesSymboles::getNumContexteTS()
{		
	return numeroContexteTS;
}

vector<Symbole*> TableDesSymboles::getTableSymb()
{
	return tableSymb;
}

string TableDesSymboles::getPortee()
{
	return portee;
}

// sert à rien
bool TableDesSymboles::TableSymbContientI(TableDesSymboles* TS, int identifiantSymbole)
{
	bool contient = false;
	
		for (unsigned i = 0; i < TS->getTableSymb().size() ;i++)
		{
			if(TS-> tableSymb[i]->getID() == identifiantSymbole)
			{
				contient = true;
			}
		}
	return contient;
}



TableDesSymboles* TableDesSymboles::getTableSymbContenantI(vector<TableDesSymboles*> listeTDS, int identifiantSymbole)
{
	TableDesSymboles* TDS_vide = NULL;
	
	for (unsigned i = 0; i < listeTDS.size() ; i++)
	{	
		if(TableSymbContientI(listeTDS[i], identifiantSymbole))
		{
			return listeTDS[i];
		}
	

	}
	
	return TDS_vide;
} 

Symbole* TableDesSymboles::getSymboleI(int identifiantSymbole)
{
	Symbole* symb_vide = NULL;	

	for (unsigned i = 0; i < tableSymb.size() ;i++)
		{
			if(tableSymb[i]->getID() == identifiantSymbole)
			{
				return tableSymb[i];
			}
		}

	return symb_vide;
}


//############################################################### MUTATEURS

void TableDesSymboles::incNumIdActuel()
{	
	static int numeroId = 0;
	numeroId++;
	
}

void TableDesSymboles::setContexteTS(int _contexte)
{
	numeroContexteTS = _contexte;
}

//############################################################### METHODES


void TableDesSymboles::ajouter(Symbole* symb){tableSymb.push_back(symb);}


void TableDesSymboles::afficherTable()
{	
	
	for (unsigned int i = 0; i < tableSymb.size(); i++){
		
		 if (*tableSymb[i]->getCategorie() == "variable ")
			{
				Variable* v = static_cast<Variable*>(tableSymb[i]);
	
					if(*v->getType()->getStringType() != "Array")
					{	
						cout << v->getID() << " | " << *v->getCategorie() << " | " << *v->getType()->getStringType() << endl; 
					}
					else
					{	
						TypeArray* ta = static_cast<TypeArray*>(v->getType()); 
						cout << v->getID() << " | " << *v->getCategorie() << " | " << *ta->getStringType()<< " | ["<< ta->getInterval()->getDebut() << "," << ta->getInterval()->getFin() << "] | "<< *ta->getTypeTab()->getStringType() <<  endl; 
					}
			}
		else if (*tableSymb[i]->getCategorie() == "argument ")
			{
				Argument* a = static_cast<Argument*>(tableSymb[i]);
				cout << a->getID() << " | " << *a->getCategorie() << " | " << *a->getType()->getStringType()  <<  endl; 
			}
		else if (*tableSymb[i]->getCategorie() == "constante")
			{
				Constante* c = static_cast<Constante*>(tableSymb[i]);
				cout << c->getID() << " | " << *c->getCategorie() << " | " << *c->getType()->getStringType()  <<  endl; 
			}
		else if (*tableSymb[i]->getCategorie() == "temporaire")
			{
				Temporaire* t = static_cast<Temporaire*>(tableSymb[i]);
				cout << t->getID() << " | " << *t->getCategorie() << "| " << *t->getType()->getStringType() <<  endl; 
	
			}
		else if (*tableSymb[i]->getCategorie() == "  type   ")
			{
				TypeUser* t = static_cast<TypeUser*>(tableSymb[i]);
				cout << t->getID() << " | " << *t->getCategorie()<< " | "  << *t->getType()->getStringType() <<  endl; 
			}		
		else if (*tableSymb[i]->getCategorie() == "procedure")
			{
				Procedure* p = static_cast<Procedure*>(tableSymb[i]);
				cout << p->getID() << " | " << *p->getCategorie()<< " | " <<  p->getArite()  << " | TS" << p->getNomTDS() <<endl;
			}
			
		else if (*tableSymb[i]->getCategorie() == "fonction")
			{	
				Fonction* f = static_cast<Fonction*>(tableSymb[i]);
				cout << f->getID() << " | " << *f->getCategorie() << "  | " <<  f->getArite()  << " | TS" << f->getNomTDS()  <<"  | " <<*f->getType()->getStringType()<<endl; // pour la fonction getType donne le type de retour
			}
		else if (*tableSymb[i]->getCategorie() == "etiquette")
			{
				Etiquette* e = static_cast<Etiquette*>(tableSymb[i]);
				cout << e->getID() << " | " << *e->getCategorie() << " | " << endl; 
			}
		else if (*tableSymb[i]->getCategorie() == "  prog   ")
			{	
				Programme* p = static_cast<Programme*>(tableSymb[i]);
				cout << p->getID() << " | " << *p->getCategorie() << " | "<< endl;
			}
		
	}
}


void TableDesSymboles::afficherTables(std::vector<TableDesSymboles*> listeTDS)
{
	for (unsigned int i = 0; i < listeTDS.size(); i++)
	{
		cout << "Table Des Symboles TS" << listeTDS[i]->numeroContexteTS << endl;
		listeTDS[i]-> afficherTable();
		cout << endl;
	}

}





