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
	
	cout << "Code 3@" << endl;

	for (unsigned int i = 0 ; i < tabInstruction.size(); i++)
	{
		//if ( tabInstruction[i]->getOperande1()->getValInteger()
		cout << tabInstruction[i]->getOperande(1)->getValInteger() << " "<< *tabInstruction[i]->getOperation() << endl;
	}

}
