#include <iostream>
#include <string>
#include <string.h>
#include "Expression.hpp"

using namespace std;

//####################################### CONSTRUCTEURS

Expression::Expression(Type* _type, int _valInt){

	valInt = _valInt;

	valFloat = 0;
	valBool = false;
	valString = new string("_");
	type = _type;
	
}

Expression::Expression(Type* _type, float _valFloat){
	
	valFloat = _valFloat;

	valInt = 0;
	valBool = false;
	valString = new string("_");
	
	type = _type;
}

Expression::Expression(Type* _type, string* _valString){
	
	valString = _valString;
	
	valInt = 0;
	valBool = false;
	valFloat = 0;

	type = _type;
	
}

Expression::Expression(Type* _type, bool _valBool){
	
	valBool = _valBool;

	valInt = 0;
	valString = new string("_");
	valFloat = 0;

	type = _type;
}


//####################################### ACCESSEURS


Type* Expression::getType(){return type;}

bool Expression::getValBool(){return valBool;}

int Expression::getValInteger(){return valInt;}

float Expression::getValFloat(){return valFloat;}

string* Expression::getValString(){return valString;}


void Expression::setValBool(bool _valBool){valBool = _valBool;}






bool Expression::memeType(Type* _type1, Type* _type2)
{
	string* a1 =  _type1->getStringType();
	string* a2 =  _type2->getStringType();	

	return (*a1 == *a2);
}

bool Expression::memeType(Type* _type1, string* _type2)
{
	string* a1 =  _type1->getStringType();
	string* a2 =  _type2;	

	return (*a1 == *a2);
}


Expression* Expression::operation(Expression* ex1, Expression* ex2, string* _operation)
{	
	int type = 0;
	int operation = 0;
	Expression* exRetour;

	string typeEx1 = *(ex1->getType()->getStringType());
	string typeEx2 = *(ex1->getType()->getStringType());
	
	if     (typeEx1 == "Integer")	 type = 1;
	else if(typeEx1 == "Real") 	 type = 2;
	else if(typeEx1 == "String") 	 type = 3;	
	else if(typeEx1 == "Pointeur")	 type = 4;
	else 				 type = 0;

	if     (*_operation == "+")	 operation = 1;
	else if(*_operation == "-") 	 operation = 2;
	else if(*_operation == "*") 	 operation = 3;	
	else if(*_operation == "/")	 operation = 4;
	else if(*_operation == "div") 	 operation = 5;
	else if(*_operation == "mod") 	 operation = 6;
	else if(*_operation == "-a") 	 operation = 7;
	else if(*_operation == "+a") 	 operation = 8;
	else 				 operation = 0;

	if (typeEx1 == typeEx2)
	{
		switch(type)
		{
			case 1: // Integer
				switch(operation)
				{		
					case 1: // +
						exRetour = new Expression(new TypeInteger(), ex1->getValInteger() + ex2->getValInteger());
					break;
					case 2: // -
						exRetour = new Expression(new TypeInteger(), ex1->getValInteger() - ex2->getValInteger());
					break;
					case 3: // *
						exRetour = new Expression(new TypeInteger(), ex1->getValInteger() * ex2->getValInteger());
					break;
					case 4: // /
						exRetour = new Expression(new TypeInteger(), ex1->getValInteger() / ex2->getValInteger());
					break;
					case 5: // div
						exRetour = new Expression(new TypeInteger(), ex1->getValInteger() / ex2->getValInteger());
					break;
					case 6: // mod
						exRetour = new Expression(new TypeInteger(), ex1->getValInteger() % ex2->getValInteger());
					break;
					case 7: // -a
						exRetour = new Expression(new TypeInteger(), -ex1->getValInteger() );
					break;
					case 8: // +a
						exRetour = new Expression(new TypeInteger(), +ex1->getValInteger());
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée pour les entiers." << endl;
						exRetour = new Expression(new TypeInteger(), 0);
					break;
				}
			
			break;
			case 2: // Real
				switch(operation)
				{		
					case 1: // +
						exRetour = new Expression(new TypeReal(), ex1->getValFloat() + ex2->getValFloat());
					break;
					case 2: // -
						exRetour = new Expression(new TypeReal(), ex1->getValFloat() - ex2->getValFloat());
					break;
					case 3: // *
						exRetour = new Expression(new TypeReal(), ex1->getValFloat() * ex2->getValFloat());
					break;
					case 4: // /
						exRetour = new Expression(new TypeReal(), ex1->getValFloat() / ex2->getValFloat());
					break;
					case 5: // div
						exRetour = new Expression(new TypeReal(), ex1->getValFloat() / ex2->getValFloat());
					break;
					case 7: // -a
						exRetour = new Expression(new TypeReal(), -ex1->getValFloat() );
					break;
					case 8: // +a
						exRetour = new Expression(new TypeReal(), +ex1->getValFloat());
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée pour les reels." << endl;
						exRetour = new Expression(new TypeReal(), 0);
					break;
				}
			break;
			case 3: // String
				switch(operation)
				{		
					case 1: // +
						//exRetour = new Expression(new TypeString(), new *string(ex1->getValString() + ex2->getValString()));
					break;

					default:
						cout << "Erreur: Cette opération n'est pas gérée pour les string." << endl;
						exRetour = new Expression(new TypeString(), 0);
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
						exRetour = new Expression(new TypeString(), 0);
					break;
					
					
				}
			break;
	
			default:
					cout << "Erreur: Ce type n'est pas gérée." << endl;
					exRetour = new Expression(new TypeInteger(), 0);
			break;
		}
	}
	else
	{	
		cout << "Erreur: opérations entre 2 types différents non gérées. " << endl;
		exRetour = new Expression(new TypeInteger(), 0);
	}

	return exRetour;	
}



Expression* Expression::comparaisonBool(Expression* ex1, Expression* ex2, string* _operation)
{
	int operation = 0;
	bool resultat = false;
	Expression* exRetour;
	
	bool valEx1 = ex1-> getValBool();
	bool valEx2 = ex1-> getValBool();
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
				exRetour = new Expression(new TypeBoolean(), resultat);
			break;
			case 2: resultat = valEx1 || valEx2;
				exRetour = new Expression(new TypeBoolean(), resultat);
			break;
			case 3: resultat = valEx1 && valEx2;
				exRetour = new Expression(new TypeBoolean(), resultat);
			break;
			case 4: resultat = !(valEx1 == valEx2);
				exRetour = new Expression(new TypeBoolean(), resultat);
			break;
			default:
				cout << "Erreur: comparaisonBool avec une opération non gérée." << endl;
				exRetour = new Expression(new TypeBoolean(), false);
			break;

	}
	else
	{
		cout << "Erreur: comparaisonBool entre 2 types non booléens." << endl;
		exRetour = new Expression(new TypeBoolean(), false);
	}
	
	return exRetour;
}

Expression* Expression::comparaison(Expression* ex1, Expression* ex2, string* _operation)
{
	int type = 0;
	int operation = 0;
	Expression* exRetour;

	string typeEx1 = *(ex1->getType()->getStringType());
	string typeEx2 = *(ex1->getType()->getStringType());
	
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
						if (ex1->getValBool() == ex2->getValBool()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 2: // <
						if (ex1->getValBool() < ex2->getValBool()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 3: // >
						if (ex1->getValBool() > ex2->getValBool()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 4: // <=
						if (ex1->getValBool() <= ex2->getValBool()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 5: // >=
						if (ex1->getValBool() >= ex2->getValBool()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 6: // <>
						if (ex1->getValBool() != ex2->getValBool()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée." << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
				}
			
			break;
			case 2: // Integer
				switch(operation)
				{		
					case 1: // =
						if (ex1->getValInteger() == ex2->getValInteger()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);					
					break;
					case 2: // <
						if (ex1->getValInteger() < ex2->getValInteger()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 3: // >
						if (ex1->getValInteger() > ex2->getValInteger()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 4: // <=
						if (ex1->getValInteger() <= ex2->getValInteger()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 5: // >=
						if (ex1->getValInteger() >= ex2->getValInteger()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 6: // <>
						if (ex1->getValInteger() != ex2->getValInteger()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée." << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
				}
			break;
			case 3: // Real
				switch(operation)
				{		
					case 1: // =
						if (ex1->getValFloat() == ex2->getValFloat()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 2: // <
						if (ex1->getValFloat() < ex2->getValFloat()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 3: // >
						if (ex1->getValFloat() > ex2->getValFloat()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 4: // <=
						if (ex1->getValFloat() <= ex2->getValFloat()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 5: // >=
						if (ex1->getValFloat() >= ex2->getValFloat()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 6: // <>
						if (ex1->getValFloat() != ex2->getValFloat()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée." << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
				}
			break;
			case 4: // String
				switch(operation)
				{		
					case 1: // =
						if (ex1->getValString() == ex2->getValString()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 2: // <
						if (ex1->getValString() < ex2->getValString()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 3: // >
						if (ex1->getValString() > ex2->getValString()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 4: // <=
						cout << "Erreur: Cette opération n'est pas gérée pour les string." << endl;
						exRetour = new Expression(new TypeBoolean(), false);	;	
					break;
					case 5: // >=
						cout << "Erreur: Cette opération n'est pas gérée pour les string." << endl;
						exRetour = new Expression(new TypeBoolean(), false);	
					break;
					case 6: // <>
						if (ex1->getValString() != ex2->getValString()) exRetour = new Expression(new TypeBoolean(), true);
						else 	exRetour = new Expression(new TypeBoolean(), false);
					break;
					default:
						cout << "Erreur: Cette opération n'est pas gérée." << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
				}
			break;
			case 5: // Pointeur
				switch(operation)
				{		
					case 1: // =
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 2: // <
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 3: // >
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 4: // <=
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
					case 5: // >=
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Expression(new TypeBoolean(), false);;
					break;
					case 6: // <>
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Expression(new TypeBoolean(), false);;;
					break;
					default:
						cout << "Erreur: comparaisons entre 2 pointeurs non gérées. " << endl;
						exRetour = new Expression(new TypeBoolean(), false);
					break;
				}
			break;
			default:
					cout << "Erreur: Ce type n'est pas gérée." << endl;
					exRetour = new Expression(new TypeBoolean(), false);
			break;
		}
	}
	else
	{	
		cout << "Erreur: comparaisons entre 2 types différents non gérées. " << endl;
		exRetour = new Expression(new TypeBoolean(), false);
	}

	
	return exRetour;
}


//####################################### DESTRUCTEUR

Expression::~Expression(){}





