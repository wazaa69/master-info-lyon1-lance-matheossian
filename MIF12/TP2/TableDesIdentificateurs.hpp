#ifndef TableDesIdentificateurs
#define TableDesIdentificateurs


#include <vector>
#include <string>




class TableDesIdentificateurs {


    public:

	TableDesIdentificateurs();
	std::vector<std::string> getIdentificateurs();

	private :
	std::vector<std::string> identificateurs;




}



#endif
