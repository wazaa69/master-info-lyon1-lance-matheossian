#include <opencv/cv.h>
#include <opencv/highgui.h>
// -lcv -lhighgui -lcvaux -lml -lcxcore
#include <vector>
#include <string>

#include "include/Accroissement.h"
#include "include/Graine.h"


int theFlag = 0;
CvPoint* tmp = (CvPoint*) malloc(sizeof( CvPoint ) );
void onLClick(int event, int x, int y, int flags, void* param);
void onLClick(int event, int x, int y, int flags, void* param){
    theFlag = flags;
    tmp->x = x;
    tmp->y = y;
}

int main()
{
    char* nomFenetre = "img";

    cvNamedWindow(nomFenetre, CV_WINDOW_AUTOSIZE);
    cvMoveWindow(nomFenetre,0,0);
    Accroissement acc("images/a.jpg", 15.0);

    std::vector<Graine>* graines = new std::vector<Graine>; //pour stocker les cliques de l'utilisateur

    std::cout << "Disposition des graines (a)utomatique, au (h)asard, au (c)hoix ? " << std::endl;


    char key = '---';
    std::string rep;
    while(rep.compare("a") && rep.compare("h") && rep.compare("c")) cin >> rep;

    if(!rep.compare("c"))
    {
        cvShowImage( nomFenetre, acc.getImgSrc());

        std::cout << "Cliquez sur l'image pour déposer les graines" << std::endl;

        while(key != 'q')
        {
            cvSetMouseCallback(nomFenetre, onLClick);

            if(key == 't')
            {
                graines->push_back(Graine(tmp->x,tmp->y));
                std::cout << "Graine " << graines->size() << " : (" << tmp->x << "," << tmp->y << ")." << std::endl;
                theFlag = 0;
                key = '---';
            }

            key = cvWaitKey(20);
        }

    }
    else if(!rep.compare("a"))
    {
        std::cout << "Disposition automatique des graines. " << std::endl;
        acc.dispositionAutomatique(graines);
    }
    else
    {
        std::cout << "Disposition au hasard des graines. " << std::endl;
        acc.dispositionAleatoire(graines, 100);
    }

    key = '---';

    if(graines->size())
    {
        std::cout << std::endl;

        acc.demarrer(*graines);
        delete graines;

        cvSaveImage("images/resultat.jpg",acc.getImgSeg());

        cvShowImage( "img", acc.getImgSeg() );

        while(key != 'q')  key = cvWaitKey(10);
    }


}
