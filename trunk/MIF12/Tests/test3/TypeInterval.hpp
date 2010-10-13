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
		
		TypeInterval(const std::string _debut, const std::string _fin);

};

#endif
