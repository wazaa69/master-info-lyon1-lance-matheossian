#include "../include/Accroissement.h"

using namespace std;

Accroissement::Accroissement(const char* chemin, const int& _seuil): seuil(_seuil) {

    img_src = cvLoadImage(chemin);
    img_seg = cvCreateImage(cvGetSize(img_src), img_src->depth, 3);

    const unsigned int hauteur = img_src->height;
    const unsigned int largeur = img_src->width;

    for(unsigned int i = 0; i < hauteur; i++)
    {
        matriceIndex.push_back(vector<int>());

        for(unsigned int j = 0; j < largeur; j++)
            matriceIndex[i].push_back(-1);
    }


    Graine g = Graine(0, hauteur/2, largeur/2);
    Region r = Region(0,g);
    listeRegions.push_back(r);
}

Accroissement::~Accroissement(){
    cvReleaseImage(&img_src);
    cvReleaseImage(&img_seg);
}




void Accroissement::demarrer(){
    contaminationVoisins(listeRegions[0].getGraine());
}


void Accroissement::contaminationVoisins(const Graine& g)
{
    const unsigned int x = g.getPtStart().x;
    const unsigned int y = g.getPtStart().y;
    const unsigned int gIndex = g.getIndex();

    if(x > 0 && x < img_src->width-2 && y > 0 && y < img_src->height-2)
    {
        contaminationPixel(cvPoint(x-1, y-1), gIndex);
        contaminationPixel(cvPoint(x, y-1), gIndex);
        contaminationPixel(cvPoint(x+1, y-1), gIndex);
        contaminationPixel(cvPoint(x+1, y), gIndex);
        contaminationPixel(cvPoint(x+1, y+1), gIndex);
        contaminationPixel(cvPoint(x, y+1), gIndex);
        contaminationPixel(cvPoint(x-1, y+1), gIndex);
        contaminationPixel(cvPoint(x-1, y), gIndex);
    }
    else if(x == 0 && y == 0)
    {
        contaminationPixel(cvPoint(x+1, y), gIndex);
        contaminationPixel(cvPoint(x+1, y + 1), gIndex);
        contaminationPixel(cvPoint(x, y + 1), gIndex);
    }
    else  if(x > 0 && x < img_src->width-2 && y == 0)
    {
        contaminationPixel(cvPoint(x-1, 0), gIndex);
        contaminationPixel(cvPoint(x-1, 1), gIndex);
        contaminationPixel(cvPoint(x, 1), gIndex);
        contaminationPixel(cvPoint(x+1, 1), gIndex);
        contaminationPixel(cvPoint(x+1, 0), gIndex);
    }
    else if(x == img_src->width-1 && y == 0)
    {
        contaminationPixel(cvPoint(x-1, 0), gIndex);
        contaminationPixel(cvPoint(x-1, 1), gIndex);
        contaminationPixel(cvPoint(x, 1), gIndex);
    }
    else if(x == img_src->width-1 && y > 0 && y < img_src->height-2)
    {
        contaminationPixel(cvPoint(x, y-1), gIndex);
        contaminationPixel(cvPoint(x-1, y-1), gIndex);
        contaminationPixel(cvPoint(x-1, y), gIndex);
        contaminationPixel(cvPoint(x-1, y+1), gIndex);
        contaminationPixel(cvPoint(x, y+1), gIndex);
    }
    else if(x == img_src->width-1 && y == img_src->height-1)
    {
        contaminationPixel(cvPoint(x, y-1), gIndex);
        contaminationPixel(cvPoint(x-1, y-1), gIndex);
        contaminationPixel(cvPoint(x-1, y), gIndex);
    }
    else if(x > 0 && x < img_src->width-2 && y == img_src->height-1)
    {
        contaminationPixel(cvPoint(x-1, y), gIndex);
        contaminationPixel(cvPoint(x-1, y-1), gIndex);
        contaminationPixel(cvPoint(x, y-1), gIndex);
        contaminationPixel(cvPoint(x+1, y-1), gIndex);
        contaminationPixel(cvPoint(x+1, y), gIndex);
    }
    else if(x == 0 && y == img_src->height-1)
    {
        contaminationPixel(cvPoint(0, y-1), gIndex);
        contaminationPixel(cvPoint(1, y-1), gIndex);
        contaminationPixel(cvPoint(1, y), gIndex);
    }
    else if(x == 0 && y > 0  && y < img_src->height - 2)
    {
        contaminationPixel(cvPoint(0, y-1), gIndex);
        contaminationPixel(cvPoint(1, y-1), gIndex);
        contaminationPixel(cvPoint(1, y), gIndex);
        contaminationPixel(cvPoint(1, y+1), gIndex);
        contaminationPixel(cvPoint(0, y+1), gIndex);
    }

}

void Accroissement::contaminationPixel(const CvPoint& pt, const unsigned int& gIndex)
{

    const unsigned int x = pt.x;
    const unsigned int y = pt.y;


    if(matriceIndex[x][y] == -1)
    {
        matriceIndex[x][y] = gIndex;
        //cout <<  x <<  " " << y  << endl;
        cvSet2D(img_seg, x, y, cvScalar(255,255,0));
        cout << "set2D x :  " << x << "  y :  " << y << endl;
        contaminationVoisins(pt);
    }
}
