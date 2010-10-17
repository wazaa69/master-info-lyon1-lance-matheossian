#ifndef _TYPEINTERVAL_
#define _TYPEINTERVAL_

#include "Type.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeInterval : public Type {

	private:

		 std::string debut;
		 std::string fin;

	public:
		TypeInterval();
		~TypeInterval();
		
		TypeInterval(int _debut, int _fin);

};

#endif
