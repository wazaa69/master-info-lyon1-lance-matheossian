#ifndef _TYPERECORD__
#define _TYPERECORD_

#include "Type.hpp"
#include "TDS.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeRecord : public Type {

	private:

		TableDesSymboles* TDSLocale;

	public:
		TypeRecord();
		~TypeRecord();
		
		TypeRecord(TableDesSymboles* _TDSLocale);


};

#endif
