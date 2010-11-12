#include "ConteneurCode.hpp"

using namespace std;

ConteneurCode::ConteneurCode(){

	nombreInstructions = 0;
	
}

ConteneurCode::~ConteneurCode(){}

void ConteneurCode::ajouterInstFinBlocCourant(Instruction* _instruction)
{
	

	//if(_instruction != NULL){
	
	tabInstruction.push_back(_instruction);
	nombreInstructions++;

	//}

}

void ConteneurCode::ajouterLesInstrFinBlocCourant(vector <Instruction*> _tabInstructions)
{
	for (unsigned int i = 0; i < _tabInstructions.size() ; i++)
	{
		tabInstruction.push_back(_tabInstructions[i]);
		nombreInstructions++;
	}
}


void ConteneurCode::affichageCode3AD()
{
	string temp = "";

	Instruction* i1;
	Operande* op1;
	Operande* op2;
	Operande* op3;
	string* operation;

	
	
	cout << "Code 3@" << endl;

	for (unsigned int i = 0 ; i < tabInstruction.size(); i++)
	{
		// On récupère tous les attibuts de l'instruction pour plus de lisibilité
	 	i1 = tabInstruction[i];
		op1 = i1->getOperande(1);
		op2 = i1->getOperande(2);
		op3 = i1->getOperande(3);
		operation = i1->getOperation();
		
		// OPERATIONS
		if ( (op1 != NULL) && (op2 != NULL) && (op3 != NULL) ){

			if (op1->isIdentifiant() == true )
				 cout << *op1->getSymbole()->getNomSymbole() << convOperation(1) << op2->getValConvString() << " " << *operation  << " " << op3 ->getValConvString()  << endl;
			 
			else
				cout << "  "  << convOperation(1) << op2->getValConvString() << " " <<  *operation  << " " << op3->getValConvString()  << endl;
		}


		// AFFECTATION seulement
		else if ((tabInstruction[i]->getOperande(1) != NULL) && (tabInstruction[i]->getOperande(2) != NULL))
		{
			cout << *op1->getSymbole()->getNomSymbole() <<" " << *operation << " " << op2->getValConvString() << endl;
		}
		
	}

}


string ConteneurCode::convOperation(int _operation)
{

	switch(_operation){
		case 1: return " := ";
		break;
		case 2: return " + ";
		break;
		case 3: return " - ";
		break;
		case 4: return " * ";
		break;
		case 5: return " / ";
		break;
		case 6:return " (-) ";
		break;
		default: return "";
		break;
	}
}


