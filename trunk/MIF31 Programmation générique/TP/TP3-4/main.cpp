#include <iostream>
#include <deque>
#include <set>
#include <list>
#include <iterator>


#include "include/Tableau.h"
#include "include/Generation.h"

using namespace std;

int main()
{
    Tableau<int,6> A(5);
    std::cin >> A;

    Tableau<int,6>::TabIterator it= A.begin();
    Tableau<int,6>::TabIterator ite=A.end();
//    for(;it!=ite;++it)
//    std::cout << *it << std::endl;

        return 0;
}


int mainOld()
{
    std::deque<int> d (5);

    std::generate(d.begin(), d.end(), Generation(4));
    ostream_iterator<int> affichage(cout,";");
    cout << "d: ";
    copy (d.begin(), d.end(), affichage);

    cout << endl;

    std::set<int> s1 (d.begin(), d.end());
    cout << "s1: ";
    copy (s1.begin(), s1.end(), affichage);

    cout << endl;
    d.clear();


    std::set<int> s2;
    Generation g2(2);
    for(int i = 0; i <9; i++) s2.insert(g2());
    cout << "s2: ";
    copy (s2.begin(), s2.end(), affichage);

    std::set<int> s3;

    std::set<int>::iterator it;
    it = s3.begin();

    cout << endl;

    Generation g3(3);
    std::generate_n(std::inserter(s3,s3.begin()), 6, g3);
    cout << "s3: ";
    copy (s3.begin(), s3.end(), affichage);

    //set_union
    //set_intersection
    //set_difference
    //set_symmetric_difference
    //merge

    std::list<int> l;
    set_intersection(s1.begin(), s1.end(), s2.begin(), s2.end(), std::inserter(l,l.begin()));
    cout << endl << "intersection(s1,s2): ";
    copy (l.begin(), l.end(), affichage);

    cout << endl << "union(s1,s3): ";
    set_union(s1.begin(), s1.end(), s3.begin(), s3.end(), affichage);

}
