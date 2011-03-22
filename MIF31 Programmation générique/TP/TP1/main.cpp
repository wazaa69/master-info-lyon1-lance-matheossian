#include "include/String.h"
#include <iostream>
#include <limits.h>

using namespace std;

std::ostream& operator<<(std::ostream& sortie, String& unString)
{
    unString.afficher(sortie);
    return sortie;
}

std::istream& operator>>(std::istream& entree, String& unString)
{
    char* tmp = new char[CHAR_MAX];
    entree >> tmp;
    unString = tmp;
    return entree;
}

int main()
{
    cout << "Creation des String-------------->" << endl;

    String s1;
    String s2("toto");
    String s3('a');
    String s4("titi");

    cout << "s1 : " << s1 << endl;
    cout << "s2 : " << s2 << endl;
    cout << "s3 : " << s3 << endl;
    cout << "s4 : " << s4 << endl << endl;


    cout << "Operateurs-------------->" << endl;

    String* add = new String(s2 + s4);
    cout << "s2 + s4 : " << *add << endl;
    delete add;
    cout << "s2==s4 : " << (s2==s4) << endl;
    cout << "s2 < s4 : " << (s2 < s4) << endl;
    cout << "s2 <= s4 : " << (s2 <= s4) << endl;
    cout << "s2 > s3 : " << (s2 > s3) << endl;
    cout << "s2 >= s3 : " << (s2 >= s3) << endl;
    cout << "s2[2] : " << s2[2] << endl;
    s2 = s4;
    cout << "s2 = s4 : " << s2 << endl << endl;


    cout << "Autres methodes-------------->" << endl;

    cout << "s1.getTaille() : " << s1.getTaille() << endl;
    cout << "s1.isVide() : " << s1.isVide() << endl;
    cout << "s2.sousChaine(2,5) : " << s2.sousChaine(2,5) << endl;
    cout << "Nombre d'instance de String : " << String::getCompteur() << endl;

    String s5("coucou !");
    char* conv = s5.convChar();
    cout << "s5 : Test conversion char : " << conv << endl;
    delete[] conv;

    cout <<  "s5.enleverChaine(\"cou\\0\") : " << s5.enleverChaine("c\0") << endl;;

    cout << "Entrez un nouveau mot pout s5 : ";
    cin >> s5;
    cout <<  "Nouveau mot dans s5 : " << s5 << endl;



    return 0;
}

