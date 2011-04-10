#include "../include/Accroissement.h"

using namespace std;


Accroissement::Accroissement(const char* chemin, const double& _seuil): seuil(_seuil)
{

    img_src = cvLoadImage(chemin);
    img_seg = cvCreateImage(cvGetSize(img_src), img_src->depth, 3);

    const unsigned int hauteur = img_src->height;
    const unsigned int largeur = img_src->width;

    cout << hauteur << " * " << largeur << " = " << (hauteur * largeur) <<endl;

    for(unsigned int i = 0; i < largeur; i++)
    {
        imgIndexGrow.push_back(vector<int>());
        imgIndexMerge.push_back(vector<bool>());

        for(unsigned int j = 0; j < hauteur; j++)
        {
            imgIndexGrow[i].push_back(-1);
            imgIndexMerge[i].push_back(false);

        }

    }

}


Accroissement::~Accroissement()
{
    cvReleaseImage(&img_src);
    cvReleaseImage(&img_seg);
}

void Accroissement::dispositionAutomatique(vector<Graine> *graines)
{
   unsigned int largeur = getImgSrc()->width;
   unsigned int hauteur = getImgSrc()->height;
   unsigned int pasX = largeur/10;
   unsigned int pasY = hauteur/10;

    for(unsigned int i = 0; i < largeur; i++)
    {

        for(unsigned int j = 0; j < hauteur; j++)
        {

            graines->push_back(Graine(i,j));
            j += pasY;
        }
        i += pasX;
    }

}

void Accroissement::demarrer(vector<Graine> graines)
{
    //Grow
    deposerGraines(graines);
    contaminationPixelsVoisins();


//    afficherDetails(); //seulement pour les commentaires

    //Merge
//    sePositionnerSurlesGraine(graines);
//    contaminationRegionsVoisines();
//    modificationImgSegEtImgIndexGrow();
}




void Accroissement::deposerGraines(vector<Graine> graines)
{
    for(unsigned int i = 0; i < graines.size(); i++)
    {
        CvPoint g = graines[i].getPtStart();

        //création de la région
        CvScalar color = cvGet2D(img_src, g.y, g.x); //y, x et pas l'inverse
        Region region = Region(graines[i], Couleur(color.val[2], color.val[1], color.val[0]));
        region.ajouterPointRegion(g);
        listeIndexRegions.push_back(region);


        //ajout de l'index et de la couleur sur l'image
        imgIndexGrow[g.x][g.y] = i;
        cvSet2D(img_seg, g.y, g.x, region.getCouleurVisuelle().getCvScalar());
        listePointsVoisins.push(cvPoint(g.x, g.y)); //ajouts des graines dans la liste des voisins
    }
}


void Accroissement::sePositionnerSurlesGraine(std::vector<Graine> graines)
{
    for(unsigned int i = 0; i < graines.size(); i++)
    {
        CvPoint g = graines[i].getPtStart();
        imgIndexMerge[g.x][g.y] = true;
        listePointsVoisins.push(cvPoint(g.x, g.y)); //ajouts des graines dans la liste des voisins
    }
}




void Accroissement::contaminationPixelsVoisins()
{

    //de cette façon, on fait grandire chacune des régions autour d'une graine
    while (!listePointsVoisins.empty())
    {
        CvPoint pointCentral= listePointsVoisins.front();
        listePointsVoisins.pop();
        const unsigned int x = pointCentral.x;
        const unsigned int y = pointCentral.y;
        Region region = listeIndexRegions[imgIndexGrow[x][y]]; //avec imgIndexGrow[x][y] >= 0

        if(x > 0 && x < img_src->width-2 && y > 0 && y < img_src->height-2)
        {
            contaminationPixel(cvPoint(x-1, y-1),region);//if(imgIndexGrow[x-1][y-1]   == -1)
            contaminationPixel(cvPoint(x, y-1),region);//if(imgIndexGrow[x][y-1]     == -1)
            contaminationPixel(cvPoint(x+1, y-1),region);//if(imgIndexGrow[x+1][y-1]   == -1) E
            contaminationPixel(cvPoint(x+1, y),region);//if(imgIndexGrow[x+1][y]     == -1)
            contaminationPixel(cvPoint(x+1, y+1),region);//if(imgIndexGrow[x+1][y+1]   == -1)
            contaminationPixel(cvPoint(x, y+1),region);//if(imgIndexGrow[x][y+1]     == -1)
            contaminationPixel(cvPoint(x-1, y+1),region);//if(imgIndexGrow[x-1][y+1]   == -1)
            contaminationPixel(cvPoint(x-1, y),region);//if(imgIndexGrow[x-1][y]     == -1)
        }
        else if(x == 0 && y == 0)
        {
            contaminationPixel(cvPoint(x+1, y),region);//if(imgIndexGrow[x+1][y]     == -1)
            contaminationPixel(cvPoint(x+1, y+1),region);//if(imgIndexGrow[x+1][y+1]   == -1)
            contaminationPixel(cvPoint(x, y+1),region);//if(imgIndexGrow[x][y+1]     == -1)
        }
        else  if(x > 0 && x < img_src->width-2 && y == 0)
        {
            contaminationPixel(cvPoint(x-1, 0),region);//if(imgIndexGrow[x-1][0]     == -1)
            contaminationPixel(cvPoint(x-1, 1),region);//if(imgIndexGrow[x-1][1]     == -1)
            contaminationPixel(cvPoint(x, 1),region);//if(imgIndexGrow[x][1]       == -1)
            contaminationPixel(cvPoint(x+1, 1),region);//if(imgIndexGrow[x+1][1]     == -1) E
            contaminationPixel(cvPoint(x+1, 0),region);//if(imgIndexGrow[x+1][0]     == -1)
        }
        else if(x == img_src->width-1 && y == 0)
        {
            contaminationPixel(cvPoint(x-1, 0),region);//if(imgIndexGrow[x-1][0]     == -1)
            contaminationPixel(cvPoint(x-1, 1),region);//if(imgIndexGrow[x-1][1]     == -1)
            contaminationPixel(cvPoint(x, 1),region);//if(imgIndexGrow[x][1]       == -1)
        }
        else if(x == img_src->width-1 && y > 0 && y < img_src->height-2)
        {
            contaminationPixel(cvPoint(x, y-1),region);//if(imgIndexGrow[x][y-1]     == -1)
            contaminationPixel(cvPoint(x-1, y-1),region);//if(imgIndexGrow[x-1][y-1]   == -1)
            contaminationPixel(cvPoint(x-1, y),region);//if(imgIndexGrow[x-1][y]     == -1)
            contaminationPixel(cvPoint(x-1, y+1),region);//if(imgIndexGrow[x-1][y+1]   == -1)
            contaminationPixel(cvPoint(x, y+1),region);//if(imgIndexGrow[x][y+1]     == -1)
        }
        else if(x == img_src->width-1 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(x, y-1),region);//if(imgIndexGrow[x][y-1]     == -1)
            contaminationPixel(cvPoint(x-1, y-1),region);//if(imgIndexGrow[x-1][y-1]   == -1)
            contaminationPixel(cvPoint(x-1, y),region);//if(imgIndexGrow[x-1][y]     == -1)
        }
        else if(x > 0 && x < img_src->width-2 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(x-1, y),region);//if(imgIndexGrow[x-1][y]     == -1)
            contaminationPixel(cvPoint(x-1, y-1),region);//if(imgIndexGrow[x-1][y-1]   == -1)
            contaminationPixel(cvPoint(x, y-1),region);//if(imgIndexGrow[x][y-1]     == -1)
            contaminationPixel(cvPoint(x+1, y-1),region);//if(imgIndexGrow[x+1][y-1]   == -1)
            contaminationPixel(cvPoint(x+1, y),region);//if(imgIndexGrow[x+1][y]     == -1)
        }
        else if(x == 0 && y == img_src->height-1)
        {
            contaminationPixel(cvPoint(0, y-1),region);//if(imgIndexGrow[0][y-1]     == -1)
            contaminationPixel(cvPoint(1, y-1),region);//if(imgIndexGrow[1][y-1]     == -1)
            contaminationPixel(cvPoint(1, y),region);//if(imgIndexGrow[1][y]       == -1)
        }
        else if(x == 0 && y > 0  && y < img_src->height - 2)
        {
            contaminationPixel(cvPoint(0, y-1),region);//if(imgIndexGrow[0][y-1]     == -1)
            contaminationPixel(cvPoint(1, y-1),region);//  if(imgIndexGrow[1][y-1]     == -1)
            contaminationPixel(cvPoint(1, y),region);// if(imgIndexGrow[1][y]       == -1)
            contaminationPixel(cvPoint(1, y+1),region);//if(imgIndexGrow[1][y+1]     == -1)
            contaminationPixel(cvPoint(0, y+1),region);//if(imgIndexGrow[0][y+1]     == -1)
        }

    }

}

void Accroissement::contaminationPixel(const CvPoint& pt, Region& region)
{
    CvScalar color = cvGet2D(img_src, pt.y, pt.x);
    double coulPixel = (color.val[2]+color.val[1]+color.val[0])/3;
    double moyCoulRegion = region.getCouleurMoyenne().moyenne();



        if (imgIndexGrow[pt.x][pt.y] == region.getIndexRegion()) // collision avec la même region
        {
                // do nothing

        }
        else if ((imgIndexGrow[pt.x][pt.y] != -1 ) && (region.getTailleRegion() >= listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getTailleRegion())
                    && (abs(listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getCouleurMoyenne().moyenne() - moyCoulRegion) <= seuil)
                    && (listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getSize() > 0))
//                    && (abs(coulPixel - moyCoulRegion) <= seuil))
        { // absorption d'une partie d'une autre région
//cout << listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getListePointsRegion().size() << endl;

//            listeIndexRegions[imgIndexGrow[pt.x][pt.y]].decTailleRegion();
//            if(listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getTailleRegion() == 0){
//
//                 region.decNombreRegion();
//            }
//
//            imgIndexGrow[pt.x][pt.y] = region.getIndexRegion();
//            region.setNouvMoyenne(Couleur(color));
//            cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());
//            region.incTailleRegion();
//            listePointsVoisins.push(pt);

//            cout << region.getTailleRegion() << " " << listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getTailleRegion()  <<  endl;

//            region.setNouvMoyenne(listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getCouleurMoyenne());

//            std::cout << "li " << listeIndexRegions[imgIndexGrow[pt.x][pt.y]].getIndexRegion() << std::endl;
            changerProprietaireRegion(region, listeIndexRegions[imgIndexGrow[pt.x][pt.y]], img_seg);
            listePointsVoisins.push(pt);


        }
        else if((imgIndexGrow[pt.x][pt.y] == -1 ) && (abs(coulPixel - moyCoulRegion) > seuil)) // création d'une nouvelle région
        {
            Region r = Region(Graine(pt), Couleur(color.val[2],color.val[1],color.val[0]));
            r.ajouterPointRegion(pt);
            listeIndexRegions.push_back(r);

            imgIndexGrow[pt.x][pt.y] = r.getIndexRegion();

            listePointsVoisins.push(pt);
            cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());

        }
        else if((imgIndexGrow[pt.x][pt.y] == -1 ) && (abs(coulPixel - moyCoulRegion) <= seuil)) // extenion de la region
        {
            imgIndexGrow[pt.x][pt.y] = region.getIndexRegion();
            region.ajouterPointRegion(pt);


            region.setNouvMoyenne(Couleur(color)); //calcul la moyenne entre la couleur de la région et color
            region.incTailleRegion();
            listePointsVoisins.push(pt);
            cvSet2D(img_seg, pt.y, pt.x, region.getCouleurVisuelle().getCvScalar());

        }

//        cvWaitKey(1);
//        cvShowImage( "img", getImgSeg() );

}

void Accroissement::afficherInformations()
{
//    cout << "Nombre de régions crées au total: " << Region().getNombreRegions() << endl;

}

void Accroissement::changerProprietaireRegion(Region& r_grande, Region& r_petite, IplImage* img)
{
    CvPoint p;
//    cout << r_petite.getListePointsRegion().size() << endl;
        r_petite.setNouvMoyenne(r_petite.getCouleurMoyenne());
        cout << r_petite.getTailleRegion() << endl;

    for (unsigned int i = 0; i < r_petite.listePointsRegion.size(); i++)
    {
        p = r_petite.getListePointsRegion()[i];
//        r_grande.getListePointsRegion().push_back(p);
        r_grande.ajouterPointRegion(p);
//        cout << "a " << imgIndexGrow[p.x][p.y] << endl;
        imgIndexGrow[p.x][p.y] = r_grande.getIndexRegion();
//        cout << "b " << imgIndexGrow[p.x][p.y] << endl << endl;
        r_grande.incTailleRegion();
        r_petite.decTailleRegion();

        cvSet2D(img, p.y, p.x, r_grande.getCouleurVisuelle().getCvScalar());
    }
//    r_petite.getListePointsRegion().clear();

 cout << r_petite.getTailleRegion() << endl << endl;
}



const IplImage* Accroissement::getImgSeg() const{return img_seg;}
const IplImage* Accroissement::getImgSrc() const {return img_src;}
