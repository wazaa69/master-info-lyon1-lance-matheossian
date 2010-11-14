#include <iostream>

#include "Operande.hpp"

using namespace std;

//############################################################################## CONSTRUCTEURS


	Operande::Operande(Symbole* _identifiant, Valeur* _valeur)
	{
		
		// on indique que l'opérande est un identifiant et non pas une valeur
		if (_identifiant != NULL){
			
			if((*_identifiant->getCategorie() == "variable ") || (*_identifiant->getCategorie() == "temporaire"))
			{	
	
				identifiant = _identifiant;
				valeur = _valeur;
		
			}

			else { identifiant = _identifiant; valeur = NULL;}
		
		operandeIdentifiant = true;
		}

		else {operandeIdentifiant = false; valeur = _valeur; identifiant = NULL;}

	}


	Operande::Operande(Valeur* _valeur)
	{	
		// on indique que l'opérande est une valeur et non pas un identifiant
		operandeIdentifiant = false;
		identifiant = NULL;

		valeur = _valeur;
	}


	Symbole* Operande::getSymbole() { return identifiant; }

	Valeur* Operande::getValeur(){ return valeur;}


	Operande::Operande(Type* _type, int _valInt){
	
		operandeIdentifiant = false;
		valeur = new Valeur(_type, _valInt);
	
	}

	Operande::Operande(Type* _type, float _valFloat){
	
		operandeIdentifiant = false;
		valeur = new Valeur(_type, _valFloat);
	}

	Operande::Operande(Type* _type, string* _valString){
		
		operandeIdentifiant = false;
		valeur = new Valeur(_type, _valString);
	
	}

	Operande::Operande(Type* _type, bool _valBool){
		
		operandeIdentifiant = false;
		valeur = new Valeur(_type, _valBool);

	}


//############################################################################## ACCESSEURS


	Type* Operande::getType(){return getValeur()->getType();}

	bool Operande::getValBool(){return getValeur()->getValBool();}

	int Operande::getValInteger(){return getValeur()->getValInteger();}

	float Operande::getValFloat(){return getValeur()->getValFloat();}

	string* Operande::getValString(){return getValeur()->getValString();}

	string Operande::getValConvString()
	{
		string valRetour = "";
		string typr = *getType()->getStringType();

	  	 ostringstream oss;

		if(typr == "Integer") 		 oss << getValInteger(); 
		else if (typr ==  "Boolean") 	 oss << getValBool(); 
		else if (typr ==  "String")	return *getValString(); 
		else if (typr ==  "Real")	 oss << getValFloat(); 
		else  valRetour = "";
		
		valRetour = oss.str();
		return valRetour;
	}


//############################################################################## MUTATEURS

	void Operande::setType(Type* _type){ getValeur()->setType(_type);}

	//void Operande::setValBool(bool _valBool){valBool = _valBool;}

//############################################################################## METHODES

	bool Operande::isIdentifiant(){ return operandeIdentifiant;}


	bool Operande::memeType(Type* _type1, Type* _type2)
	{
		string* a1 =  _type1->getStringType();
		string* a2 =  _type2->getStringType();	

		return (*a1 == *a2);
	}

	bool Operande::memeType(Type* _type1, string* _type2)
	{
		string* a1 =  _type1->getStringType();
		string* a2 =  _type2;	

		return (*a1 == *a2);
	}


Operande* Operande::operation(Symbole* symboleRetour, Operande* ex1, Operande* ex2, string* _operation)
{	
	int type = 0;
	int operation = 0;
	Operande* exRetour;

	Type* tempType;
	Symbole* s1 = symboleRetour;

	string typeEx1;
	string typeEx2;

	if (ex1 != NULL)  typeEx1 = *(ex1->getType()->getStringType());
	if (ex2 != NULL)  typeEx2 = *(ex2->getType()->getStringType());

	
	if     (typeEx1 == "Integer")	 type = 1;
	else if(typeEx1 == "Real") 	 type = 2;
	else if(typeEx1 == "String") 	 type = 3;	
	else if(typeEx1 == "Pointeur")	 type = 4;
	else if(typeEx1 == "Boolean")	 type = 5;
	else 				 type = 0;

	if     (*_operation == "+")	 operation = 1;
	else if(*_operation == "-") 	 operation = 2;
	else if(*_operation == "*") 	 operation = 3;	
	else if(*_operation == "/")	 operation = 4;
	else if(*_operation == "div") 	 operation = 5;
	else if(*_operation == "mod") 	 operation = 6;
	else if(*_operation == "-a") 	 operation = 7;
	else if(*_operation == "+a") 	 operation = 8;
	else if(*_operation == ":=")	 operation = 9;
	else 				 operation = 0;


	if ((typeEx1 == typeEx2) || ((ex1 != NULL ) && (ex2 == NULL)))
	{
		switch(type)
		{
			case 1: // Integer
				tempType = new TypeInteger();
					
				switch(operation)
				{	
					case 1: // +

						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValInteger() + ex2->getValInteger())   );

					break;
					case 2: // -
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValInteger() - ex2->getValInteger())  );
					break;
					case 3: // *
						
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValInteger() * ex2->getValInteger())  );
					break;
					case 4: // /
						if( ex2->getValInteger() != 0 )
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValInteger() / ex2->getValInteger())  );
						else {std::cerr << "Erreur : division par 0  \n";erreur = true; return 0;}
					break;
					case 5: // div
						if( ex2->getValInteger() != 0 )
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValInteger() / ex2->getValInteger())  );
						else {std::cerr << "Erreur : division par 0  \n";erreur = true; return 0;}
					break;
					case 6: // mod
						if( ex2->getValInteger() != 0 )
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValInteger() % ex2->getValInteger())  );
						else {std::cerr << "Erreur : division par 0  \n";erreur = true; return 0;}
					break;
					case 7: // -a
						exRetour = new Operande(s1,new Valeur(tempType, -ex1->getValInteger())  );
					break;
					case 8: // +a
						exRetour = new Operande(s1,new Valeur(tempType, +ex1->getValInteger())  );
					break;
					case 9: // :=
						exRetour = new Operande(s1,new Valeur(tempType, ex2->getValInteger())  );
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée pour les entiers." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, 0));
					break;
				}
			
			break;
			case 2: // Real
				tempType = new TypeReal();
				switch(operation)
				{		
					case 1: // +
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValFloat() + ex2->getValFloat()));
					break;
					case 2: // -
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValFloat() - ex2->getValFloat()));
					break;
					case 3: // *
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValFloat() * ex2->getValFloat()));
					break;
					case 4: // /
						if( ex2->getValFloat() != 0 )
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValFloat() / ex2->getValFloat())  );
						else {std::cerr << "Erreur : division par 0  \n";erreur = true; return 0;}
					break;
					case 5: // div
						if( ex2->getValFloat() != 0 )
						exRetour = new Operande(s1,new Valeur(tempType, ex1->getValFloat() / ex2->getValFloat())  );
						else {std::cerr << "Erreur : division par 0  \n";erreur = true; return 0;}
					break;
					case 7: // -a
						exRetour = new Operande(s1,new Valeur(tempType, -ex1->getValFloat()));
					break;
					case 8: // +a
						exRetour = new Operande(s1,new Valeur(tempType, +ex1->getValFloat()));
					break;
					case 9: // :=
						exRetour = new Operande(s1,new Valeur(tempType, ex2->getValFloat()));
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée pour les reels." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, 0));
					break;
				}
			break;
			case 3: // String
				tempType = new TypeString();	
				switch(operation)
				{	
					case 1: // +
						//exRetour = new Operande(new TypeString(), new *string(ex1->getValString() + ex2->getValString()));
					break;
					case 9: // :=
						exRetour = new Operande(s1,new Valeur(tempType, ex2->getValString()));
					break;

					default:
						cout << "Erreur: Cette opération n'est pas gérée pour les string." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, 0));
					break;
				}
			break;
			case 4: // Pointeur
				switch(operation)
				{		
					case 10:
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée pour les pointeurs." << endl;
						exRetour = new Operande(new TypeString(), 0);
					break;
					
					
				}
			break;
			case 5: // Boolean
				tempType = new TypeBoolean();
				switch(operation)
				{		
					case 9: // :=
						exRetour = new Operande(s1,new Valeur(tempType, ex2->getValBool()));
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée pour les pointeurs." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, 0));
					break;
					
					
				}
			break;
	
			default:
					cout << "Erreur: Ce type n'est pas géré." << endl;
					exRetour = new Operande(new TypeInteger(), 0);
			break;
		}
	}
	else
	{	
		cout << "Erreur: opérations entre 2 types différents non gérées. " << endl;
		exRetour = new Operande(new TypeInteger(), 0);
	}

	return exRetour;	
}



Operande* Operande::comparaisonBool(Operande* ex1, Operande* ex2, string* _operation)
{
	int operation = 0;
	bool resultat = false;
	Operande* exRetour;

	Type* tempType = new TypeBoolean();
	Symbole* s1 = ex1->getSymbole();
	
	
	bool valEx1 = ex1-> getValBool();
	bool valEx2 = ex2-> getValBool();
	string typeEx1 = *(ex1->getType()->getStringType());
	string typeEx2 = *(ex1->getType()->getStringType());

	if     (*_operation == "and")			 operation = 1;
	else if(*_operation == "or") 			 operation = 2;
	else if(*_operation == "xor") 			 operation = 3;	
	else if(*_operation == "not" && ex2 == NULL )	 operation = 4;
	else 				 		 operation = 0;

	if ((typeEx1 == typeEx2) && (typeEx1 == "Boolean"))
	
		switch(operation)
		{	
			case 1: resultat = valEx1 && valEx2;
				exRetour = new Operande(s1,new Valeur(tempType, resultat));
			break;
			case 2: resultat = valEx1 || valEx2;
				exRetour = new Operande(s1,new Valeur(tempType, resultat));
			break;
			case 3: resultat = valEx1 && valEx2;
				exRetour = new Operande(s1,new Valeur(tempType, resultat));
			break;
			case 4: resultat = !(valEx1 == valEx2);
				exRetour = new Operande(s1,new Valeur(tempType, resultat));
			break;
			default:
				cout << "Erreur: comparaisonBool avec une opération non gérée." << endl;
				exRetour = new Operande(s1,new Valeur(tempType, false));
			break;

	}
	else
	{
		cout << "Erreur: comparaisonBool entre 2 types non booléens." << endl;
		exRetour = new Operande(s1,new Valeur(tempType, false));
	}
	
	return exRetour;
}

Operande* Operande::comparaison(Operande* ex1, Operande* ex2, string* _operation)
{
	int type = 0;
	int operation = 0;
	Operande* exRetour;

	Type* tempType = new TypeBoolean();
	Symbole* s1 = ex1->getSymbole();

	string typeEx1 = *(ex1->getType()->getStringType());
	string typeEx2 = *(ex2->getType()->getStringType());
	
	if     (typeEx1 == "Boolean")	 type = 1;
	else if(typeEx1 == "Integer") 	 type = 2;
	else if(typeEx1 == "Real") 	 type = 3;	
	else if(typeEx1 == "String")	 type = 4;
	else if(typeEx1 == "Pointeur") 	 type = 5;
	else 				 type = 0;

	if     (*_operation == "=")	 operation = 1;
	else if(*_operation == "<") 	 operation = 2;
	else if(*_operation == ">") 	 operation = 3;	
	else if(*_operation == "<=")	 operation = 4;
	else if(*_operation == ">=") 	 operation = 5;
	else if(*_operation == "<>") 	 operation = 6;
	else 				 operation = 0;

	if (typeEx1 == typeEx2)
	{
		switch(type)
		{
			case 1: // Boolean
				switch(operation)
				{	
					
					case 1: // =
						if (ex1->getValBool() == ex2->getValBool()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 2: // <
						if (ex1->getValBool() < ex2->getValBool()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 3: // >
						if (ex1->getValBool() > ex2->getValBool()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 4: // <=
						if (ex1->getValBool() <= ex2->getValBool()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 5: // >=
						if (ex1->getValBool() >= ex2->getValBool()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 6: // <>
						if (ex1->getValBool() != ex2->getValBool()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
				}
			
			break;
			case 2: // Integer
				switch(operation)
				{		
					
					case 1: // =
						if (ex1->getValInteger() == ex2->getValInteger()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));					
					break;
					case 2: // <
						if (ex1->getValInteger() < ex2->getValInteger()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 3: // >
						if (ex1->getValInteger() > ex2->getValInteger()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 4: // <=
						if (ex1->getValInteger() <= ex2->getValInteger()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 5: // >=
						if (ex1->getValInteger() >= ex2->getValInteger()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 6: // <>
						if (ex1->getValInteger() != ex2->getValInteger()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
				}
			break;
			case 3: // Real
				switch(operation)
				{		
					case 1: // =
						if (ex1->getValFloat() == ex2->getValFloat()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 2: // <
						if (ex1->getValFloat() < ex2->getValFloat()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 3: // >
						if (ex1->getValFloat() > ex2->getValFloat()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 4: // <=
						if (ex1->getValFloat() <= ex2->getValFloat()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 5: // >=
						if (ex1->getValFloat() >= ex2->getValFloat()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 6: // <>
						if (ex1->getValFloat() != ex2->getValFloat()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
				}
			break;
			case 4: // String
				switch(operation)
				{		
					case 1: // =
						if (ex1->getValString() == ex2->getValString()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 2: // <
						if (ex1->getValString() < ex2->getValString()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 3: // >
						if (ex1->getValString() > ex2->getValString()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 4: // <=
						cout << "Erreur: Cette opération n'est pas gérée pour les string." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));	;	
					break;
					case 5: // >=
						cout << "Erreur: Cette opération n'est pas gérée pour les string." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));	
					break;
					case 6: // <>
						if (ex1->getValString() != ex2->getValString()) exRetour = new Operande(s1,new Valeur(tempType, true));
						else 	exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée." << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
				}
			break;
			case 5: // Pointeur
				switch(operation)
				{		
					case 1: // =
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 2: // <
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 3: // >
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 4: // <=
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
					case 5: // >=
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));;
					break;
					case 6: // <>
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));;;
					break;
					default:
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Operande(s1,new Valeur(tempType, false));
					break;
				}
			break;
			default:
					cout << "Erreur: Ce type n'est pas géré." << endl;
					exRetour = new Operande(s1,new Valeur(tempType, false));
			break;
		}
	}
	else
	{	
		cout << "Erreur: comparaisons entre 2 types différents non gérées. " << endl;
		exRetour = new Operande(s1,new Valeur(tempType, false));
	}

	
	return exRetour;
}


//####################################### DESTRUCTEUR

Operande::~Operande(){}





