#include <opencv/cv.h>
#include <opencv/highgui.h>

#include <vector>

#include "include/Accroissement.h"
#include "include/Graine.h"

//Déclaration hors de la classe pour passer du C --> C++
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
    Accroissement acc("images/deuxcarre.pgm", 50);
    cvShowImage( nomFenetre, acc.getImgSrc());


    std::vector<Graine> graines; //pour stocker les cliques de l'utilisateur
    char key;
    std::cout << "Cliquez sur l'image pour déposer les graines" << std::endl;
    while(key != 'q')
    {
        cvSetMouseCallback(nomFenetre, onLClick);

        if(theFlag == 33) //clique gauche
        {
            graines.push_back(Graine(tmp->x,tmp->y));
            std::cout << "Graine " << graines.size() << " : (" << tmp->x << "," << tmp->y << ")." << std::endl;
            theFlag = 0;
        }

        key = cvWaitKey(20);
    }

    key = '-';

    if(graines.size())
    {
        //split & merge
        acc.demarrer(graines);


        //sauvegarde de l'image
        cvSaveImage("images/deuxcarre-seg.pgm",acc.getImgSeg());

        //affichage
        cvShowImage( "img", acc.getImgSeg() );
        while(key != 'q')  key = cvWaitKey(10);
    }
}
