#include <iostream>

#include "TableDesIdentificateurs.hpp"

using namespace std;


	TableDesIdentificateurs::TableDesIdentificateurs(){
        identificateurs = new vector<string>();
	}

	vector<string> TableDesIdentificateurs::getIdentificateurs(){return identificateurs;}



