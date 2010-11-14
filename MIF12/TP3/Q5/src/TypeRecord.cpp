#include "TypeRecord.hpp"


using namespace std;

TypeRecord::TypeRecord()
{
	type = new string("Record");
	
}

TypeRecord::TypeRecord(TableDesSymboles* _TDSLocale)
{
	type = new string("Record");
	TDSLocale = _TDSLocale;
	
}



TypeRecord::~TypeRecord(){
	delete TDSLocale;
}



