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
	string* op1 = new string("");
	
	cout << "Code 3@" << endl;

	for (unsigned int i = 0 ; i < tabInstruction.size(); i++)
	{
		if ( (tabInstruction[i]->getOperande(1) != NULL) && (tabInstruction[i]->getOperande(2) != NULL) && (tabInstruction[i]->getOperande(3) != NULL) ){

			if ( tabInstruction[i]->getOperande(1)->isIdentifiant() == true )
				 cout << tabInstruction[i]->getOperande(1)->getSymbole()->getNomSymbole() << operation(1) << tabInstruction[i]->getOperande(2)->getValInteger() << " " << *tabInstruction[i]->getOperation()  << " " << tabInstruction[i]->getOperande(3)->getValInteger()  << endl;
			 
			else
				cout << "  "  << operation(1) << tabInstruction[i]->getOperande(2)->getValInteger() << " " << *tabInstruction[i]->getOperation()  << " " << tabInstruction[i]->getOperande(3)->getValInteger()  << endl;
		}


		else if ((tabInstruction[i]->getOperande(1) != NULL) && (tabInstruction[i]->getOperande(2) != NULL))
		{
			cout << *tabInstruction[i]->getOperande(1)->getSymbole()->getNomSymbole() << operation(1) << tabInstruction[i]->getOperande(2)->getValConvString() << endl;
		}
		
	}

}


string ConteneurCode::operation(int _operation)
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


